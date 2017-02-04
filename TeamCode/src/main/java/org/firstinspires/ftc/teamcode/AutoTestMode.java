package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Owner on 1/31/2017.
 */
@Autonomous(name = "AutoTestMode", group = "Concept")
public class AutoTestMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware5035 robot = new Hardware5035();
        robot.init(hardwareMap, this);
        waitForStart();

        FindAndPushBeacon(robot, true);

        robot.setDrivePower(0);
        sleep(3000);

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

   /*         if (trackWallDistance && robot.sideUltra.getUltrasonicLevel() < 100 && robot.sideUltra.getUltrasonicLevel() != 0) {
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
*/
            if (isStopRequested()) return;
            idle();

            // send the info back to driver station using telemetry function.
            //   telemetry.addData("Clear", robot.colorDetector.alpha());
            telemetry.addData("ultraDistance", robot.sideUltra.getUltrasonicLevel());
            telemetry.update();
        }
        robot.driveForward(4.5);

        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);
        telemetry.addData("green", robot.colorDetector.green());
        telemetry.addData("alpha", robot.colorDetector.alpha());
        telemetry.addData("Red  ", robot.colorDetector.red());
        //  telemetry.addData("Green", robot.colorDetector.green());
        telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
        telemetry.update();

        while(found_color == false){
            if(robot.colorDetector.blue() > robot.colorDetector.red())
            {
                red_first = false;
                found_color = true;
                telemetry.addData("Blue First", !red_first);
                telemetry.update();
                telemetry.addData("green", robot.colorDetector.green());
                telemetry.addData("alpha", robot.colorDetector.alpha());
                telemetry.addData("Red  ", robot.colorDetector.red());
                //  telemetry.addData("Green", robot.colorDetector.green());
                telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
                telemetry.update();
            }
            else if (robot.colorDetector.red() > robot.colorDetector.blue())
            {
                red_first = true;
                found_color = true;
                telemetry.addData("Red First", red_first);
                telemetry.update();
                telemetry.addData("green", robot.colorDetector.green());
                telemetry.addData("alpha", robot.colorDetector.alpha());
                telemetry.addData("Red  ", robot.colorDetector.red());
                //  telemetry.addData("Green", robot.colorDetector.green());
                telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
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
        //robot.constServo.setPosition(0);
        telemetry.addData("done","Done");
        sleep(3600);

        //robot.constServo.setPosition(.51);
    }
}
