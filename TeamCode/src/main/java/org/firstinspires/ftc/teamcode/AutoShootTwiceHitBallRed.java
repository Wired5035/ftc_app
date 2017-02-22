package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Kota Baer on 10/11/2016.
 */

@Autonomous(name = "ShootTwiceHitBallRed")
public class AutoShootTwiceHitBallRed extends LinearOpMode {
    double OneFoot = 12; //in inches

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware5035 robot = new Hardware5035();
        robot.init(hardwareMap, this);
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// sets mode of drive motors for our encoders in autonomous
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.ballDump.setPower(.2);
        waitForStart();

////////////////////////////////////////Phase #1 turn on shooter motors, Drive forward, shoot and Drive to wall//////////////////////////////////////////////
// Turn on ball shooter motors
        robot.StartBallLaunchers();

        // Driving the robot in position to shoot the balls
        sleep(1000);
        robot.ballBooster1.setPower(1);/////////////start motors for shooting/////////////
        robot.ballBooster2.setPower(1);
        robot.driveForward(23);



///shooting the balls///

        sleep(100);
        robot.triggered();
        sleep(500);
        robot.detriggered();
        sleep(800);
        robot.triggered();
        sleep(500);
        robot.detriggered();
        sleep(800);
        robot.triggered();
        sleep(500);
        robot.detriggered();
        //////////////////turns off shooter///////////////////
        robot.ballBooster1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ballBooster1.setPower(0);
        robot.ballBooster2.setPower(0);

        /////////////// drive further to the ball     //
        robot.driveForward(15);

        ////////////// turns half way towards the wall and the first beacon////////////////
        robot.turnDegrees(67);
        // drive halfway toward the first beacon  ////////
        robot.driveForward(6);
        //turns rest of the way
        robot.turnDegrees(35);
robot.driveReverse(20);
    }

    private void FindAndPushBeacon(Hardware5035 robot, boolean trackWallDistance) throws InterruptedException {
        boolean found_color = false;
        boolean red_first = false;
        boolean found_left = false;
        boolean found_right = false;

        int current_tick = robot.rightMotor.getCurrentPosition();

        while (!found_left || !found_right) {


            /////////////////        Runs motors based on if LEFT LIGHT SENSOR SEES WHITE ///////////////////
            if (found_left == false) {
                if (robot.leftLightSensor.getLightDetected() < .36) {
                    robot.rightMotor.setPower(-.2);
                }
                if (robot.leftLightSensor.getLightDetected() >= .36)  ///white
                {
                    robot.rightMotor.setPower(.09);
                    found_left = true;
                }

            }

            /////////////////          Runs motors based on if RIGHT LIGHT SENSOR SEES WHITE     ///////////////////

            if (found_right == false) {
                if (robot.rightLightSensor.getLightDetected() < .36) {
                    robot.leftMotor.setPower(-.2);
                }
                if (robot.rightLightSensor.getLightDetected() >= .36)///    white
                {
                    robot.leftMotor.setPower(.09);
                    found_right = true;
                }

            }

            if (trackWallDistance && robot.sideUltra.getUltrasonicLevel() < 100 && robot.sideUltra.getUltrasonicLevel() != 0) {
                if (Math.abs(current_tick - robot.rightMotor.getCurrentPosition()) > robot.inchToTickConverter(6)){
                    if (robot.sideUltra.getUltrasonicLevel() > 12.5) {
                        //robot.turnDegrees(-5);
                        robot.rightMotor.setPower(0);
                        sleep(100);
                        current_tick = robot.rightMotor.getCurrentPosition();
                    } else if (robot.sideUltra.getUltrasonicLevel() < 9.5) {
                        //robot.turnDegrees(5);
                        robot.leftMotor.setPower(0);
                        sleep(100);
                        current_tick = robot.rightMotor.getCurrentPosition();
                    }
                }
            }

            if (isStopRequested()) return;
            idle();

            // send the info back to driver station using telemetry function.
            //   telemetry.addData("Clear", robot.colorDetector.alpha());
            telemetry.addData("ultraDistance", robot.sideUltra.getUltrasonicLevel());
            telemetry.update();
        }
        robot.driveForward(3.5);

        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);

        while(found_color == false){
            if(robot.colorDetector.blue() > robot.colorDetector.red())
            {
                red_first = true;
                found_color = true;
                telemetry.addData("Blue First", !red_first);
                telemetry.update();
            }
            else if (robot.colorDetector.red() > robot.colorDetector.blue())
            {
                red_first = false;
                found_color = true;
                telemetry.addData("Red First", red_first);
                telemetry.update();
            }
            if (isStopRequested()) return;
            idle();
        }


        ///????????  robot.setDrivePower(0);

        if (red_first) {

        }
            else // if red_first is false
        {
            robot.driveReverse(3.5);
        }

        if (isStopRequested()) return;

        ////////////////            moves continuous rotation servo to push button on beacon          ///////////////////
        robot.constServo.setPosition(0);
        sleep(3600);
        robot.constServo.setPosition(.51);
    }
}




