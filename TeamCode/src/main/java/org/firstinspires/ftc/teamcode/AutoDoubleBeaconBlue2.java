package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static android.graphics.Color.RED;
import static android.graphics.Color.red;

/**
 * Created by Kota Baer on 10/11/2016.
 */

@Autonomous(name = "BeaconBlue", group = "Concept")
public class AutoDoubleBeaconBlue2 extends LinearOpMode {
    double OneFoot = 12; //in inches

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware5035 robot = new Hardware5035();
        robot.init(hardwareMap, this);
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// sets mode of drive motors for our encoders in autonomous
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();


        ////////////////////////////////

// Turn on ball shooter motors
        robot.ballBooster1.setPower(1);
        robot.ballBooster2.setPower(1);

        // Driving the robot in position to shoot the balls
        robot.driveForward((OneFoot * 2.0) - 1);


///////////////////////shooting the balls////////////////////////////////////

        sleep(200);
        robot.triggered();
        sleep(450);
        robot.detriggered();
        sleep(800);
        robot.triggered();
        sleep(450);
        robot.detriggered();

        //////////////////turns off shooter///////////////////
        robot.ballBooster1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ballBooster1.setPower(0);
        robot.ballBooster2.setPower(0);

        /////////////// drive further to the ball     //
        robot.driveForward(4);

        ////////////// turns half way towards the wall and the first beacon////////////////
        robot.turnDegrees(-66);
        // drive halfway toward the first beacon  ////////
        robot.setDrivePower(.8);
        robot.driveForward(31);
        //turns rest of the way
        robot.turnDegrees(-16);


            telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
            robot.setDrivePower(.25);
            //drive using ultrasonic till ~14cm away from wall
            telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
            while(robot.frontUltra.getUltrasonicLevel() >= 18){
                telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());}//turn off drive power
            robot.setDrivePower(0);
            telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value

            telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());
            //sleep(5000);

            ////turns to be parallel with the wall
            robot.turnDegrees(-85);

            robot.rightMotor.setPower(.3);
            robot.leftMotor.setPower(.3);

            sleep (300);

            robot.rightMotor.setPower(0);
            robot.leftMotor.setPower(0);

            ////////////////////// Drive backwards to white line under the FIRST BEACON//////////////////////////////////

            FindAndPushBeacon(robot);

//////////////////         pulls beacon pusher arm 3/4 of the way back in      /////////////////////
        robot.constServo.setPosition(1);
        sleep(3200);

        robot.constServo.setPosition(.51);

            ///////////       Drives 1 foot toward the next beacon before starting to look for the line. the other line without finding it immediately.
            robot.rightMotor.setPower(-.5);
            robot.leftMotor.setPower(-.51);

            sleep (700);

            int current_tick  = robot.rightMotor.getCurrentPosition();
            if (current_tick - robot.rightMotor.getCurrentPosition() > robot.inchToTickConverter(3))
            {
                if (robot.sideUltra.getUltrasonicLevel() < 100 && robot.sideUltra.getUltrasonicLevel() != 0)
                {
                    if (robot.sideUltra.getUltrasonicLevel() <= 10)
                    {
                        robot.turnDegrees(5);
                        current_tick = robot.rightMotor.getCurrentPosition();
                    }
                    else if (robot.sideUltra.getUltrasonicLevel() >= 12)
                    {
                        robot.turnDegrees(-5);
                        current_tick = robot.rightMotor.getCurrentPosition();
                    }
                }
            }
            // robot.driveReverse(23);

            //////////////////////////           Finds SECOND BEACON  line, and resets color sensor booleans             /////////////////

            FindAndPushBeacon(robot);


            //////////////////     Turn a little so that when we back up we go away from the wall
            robot.turnDegrees(-38);

            ////////////////////         drives towards the ramp to get away from the wall and make a sharper turn      ///
        robot.setDrivePower (1);
        robot.driveForward(54);


        //////////////////         pulls beacon pusher arm 3/4 of the way back in      /////////////////////
        robot.constServo.setPosition(1);
        sleep(3200);

        robot.constServo.setPosition(.51);

            ////////////           Turn toward the center vortex                      ////////////
           robot.turnDegrees(20);



            ////////////////             ensures a complete stop         ///////////////////////
            robot.setDrivePower(0);


        }

    private void FindAndPushBeacon(Hardware5035 robot) throws InterruptedException {
        boolean found_color = false;
        boolean red_first = false;
        boolean found_left = false;
        boolean found_right = false;


        while (!found_left || !found_right)
        {
            if (found_color == false) ;
            {
                if (robot.colorDetector.red() > 1) {
                    red_first = true;
                    found_color = true;

                }
                if (robot.colorDetector.blue() > 1) {
                    red_first = false;
                    found_color = true;

                }
            }

            ////////////////////    tells what color was seen first          /////////////////////////
            telemetry.addData("Red First", red_first);

            /////////////////        Runs motors based on if LEFT LIGHT SENSOR SEES WHITE ///////////////////
            if (found_left == false) {
                if (robot.leftLightSensor.getLightDetected() < .36) {
                    robot.rightMotor.setPower(-.15);
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
                    robot.leftMotor.setPower(-.15);
                }
                if (robot.rightLightSensor.getLightDetected() >= .36)///    white
                {
                    robot.leftMotor.setPower(.09);
                    found_right = true;
                }

            }

            //////       I TOOK CODE FROM BELOW HERE       //////////////
            int current_tick  = robot.rightMotor.getCurrentPosition();
            if (current_tick - robot.rightMotor.getCurrentPosition() > robot.inchToTickConverter(3))
            {
                if (robot.sideUltra.getUltrasonicLevel() < 100 && robot.sideUltra.getUltrasonicLevel() != 0)
                {
                    if (robot.sideUltra.getUltrasonicLevel() <= 10)
                    {
                        robot.turnDegrees(5);
                        current_tick = robot.rightMotor.getCurrentPosition();
                    }
                    else if (robot.sideUltra.getUltrasonicLevel() >= 12)
                    {
                        robot.turnDegrees(-5);
                        current_tick = robot.rightMotor.getCurrentPosition();
                    }
                }
            }


            if (isStopRequested()) return;
            idle();

            // send the info back to driver station using telemetry function.
            telemetry.addData("Clear", robot.colorDetector.alpha());
            telemetry.addData("Red  ", robot.colorDetector.red());
            telemetry.addData("Green", robot.colorDetector.green());
            telemetry.addData("Blue ", robot.colorDetector.blue());
            telemetry.addData("rightpower", robot.rightMotor.getPower());
            telemetry.addData("ultraDistance", robot.sideUltra.getUltrasonicLevel());
            updateTelemetry(telemetry);
        }
        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);
      ///????????  robot.setDrivePower(0);

        if (red_first == false) {

        } else // if red_first is false
        {
            robot.driveForward(3);
        }

        if (isStopRequested()) return;

        ////////////////            moves continuous rotation servo to push button on beacon          ///////////////////
        robot.constServo.setPosition(0);
        sleep(4200);

//////////////////         pulls beacon pusher arm 3/4 of the way back in      /////////////////////
        robot.constServo.setPosition(1);
        sleep(1200);

        robot.constServo.setPosition(.51);
        if (isStopRequested()) return;

    }
}




