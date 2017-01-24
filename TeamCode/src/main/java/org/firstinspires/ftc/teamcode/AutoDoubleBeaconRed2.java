package org.firstinspires.ftc.teamcode;

/**
 * Created by Owner on 1/17/2017.
 */

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


@Autonomous(name = "AutoTestRed", group = "Concept")
public class AutoDoubleBeaconRed2 extends LinearOpMode {
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
        robot.driveForward((OneFoot * 2.25) - 1);


        ///////////////////////shooting the balls////////////////////////////////////

        sleep(200);
        robot.triggered();
        sleep(450);
        robot.detriggered();
        sleep(700);
        robot.triggered();
        sleep(450);
        robot.detriggered();

        //////////////////turns off shooter///////////////////
        robot.ballBooster1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ballBooster1.setPower(0);
        robot.ballBooster2.setPower(0);

        ////////////// turns half way towards the wall and the first beacon////////////////
        robot.turnDegrees(-60);
        // drive haly way toward the first beacon////////
        robot.driveForward(23);
        //turns rest of the way
        robot.turnDegrees(-20);
        //drives the rest of the way
        //robot.driveForward(22);



        telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        robot.setDrivePower(.25);
        //drive using ultrasonic till ~14cm away from wall
        telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        while(robot.frontUltra.getUltrasonicLevel() >= 20){
            telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());}//turn off drive power
        robot.setDrivePower(0);
        telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value

        telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());
        //sleep(5000);

        ////turns to be parallel with the wall
        robot.turnDegrees(-85.5);


        //////////Drive until 5 in from field wall using FWD ultrasonic////////////////////////
        /////////////////////////////If we can get the level then we can determin where we are, I don't know how to write the code so that
        //////////////////////////////Everything is in the hardware section to make it work????????????????????
        /// LegacyModule getLegacyModule();
        ///// double getUltrasonicLevel();
        ////////////////////////////////////////
        ///////////////////////ALL code before this is good/////////////////////////
        ////////////////////// Drive backwards to white line under the FIRST BEACON//////////////////////////////////
        boolean found_color = false;
        boolean red_first = false;
        while (robot.leftLightSensor.getLightDetected() < .38 || robot.rightLightSensor.getLightDetected() < .38) {
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
            ////////////////////tells what color was seen first/////////////////////////
            telemetry.addData("Red First", red_first);

            /////////////////Runs motors based on if it sees the white line///////////////////
            if (robot.leftLightSensor.getLightDetected() < .38) {
                robot.leftMotor.setPower(-.25);
            } else {
                robot.leftMotor.setPower(0);
            }

            if (robot.rightLightSensor.getLightDetected() < .38)
            {
                if(robot.sideUltra.getUltrasonicLevel() > 100 || robot.sideUltra.getUltrasonicLevel() == 0) {
                    robot.rightMotor.setPower(-.25);
                }else if(robot.sideUltra.getUltrasonicLevel() >= 12) {
                    robot.rightMotor.setPower(-.18);
                } else if(robot.sideUltra.getUltrasonicLevel() <= 10) {
                    robot.rightMotor.setPower(-.32);
                }else{
                    robot.rightMotor.setPower(-.25);
                }
            }
            else
            {
                robot.rightMotor.setPower(0);
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

        robot.setDrivePower(0);

        if (red_first == false) {

        } else // if red_first is false
        {
            robot.driveForward(5);
        }

        ////////////////moves continuous rotation servo to push button on beacon///////////////////
        robot.constServo.setPosition(0);
        sleep(4400);


        //////////////////pulls beacon pusher arm 3/4 of the way back in/////////////////////
        robot.constServo.setPosition(1);
        sleep(3200);

        robot.constServo.setPosition(.51);

        /////////// Drives 1 foot toward the next beacon before starting to look for the line. the other line without finding it immediately.
        robot.rightMotor.setPower(-.4);
        robot.leftMotor.setPower(-.4);

        sleep (1500);

        // robot.driveReverse(23);

        ////////////////////////// Finds SECOND BEACON  line, and resets color sensor booleans/////////////////
        found_color = false;
        red_first = false;
        while (robot.leftLightSensor.getLightDetected() < .38 || robot.rightLightSensor.getLightDetected() < .38) {

            ////////finds first color seen on the second beacon//////////////////////////
            if (found_color == false) ;
            {
                if (robot.colorDetector.red() >=2) {
                    red_first = true;
                    found_color = true;
                }
                if (robot.colorDetector.blue() >1) {
                    red_first = false;
                    found_color = true;
                }
            }
            /////////////Tells what color was seen first///////////////////////
            telemetry.addData("First Color", red_first);

            ///////////////Runs motors based on where the white line is/////////////////////////
            if (robot.leftLightSensor.getLightDetected() < .38) {
                robot.leftMotor.setPower(-.10);
            } else {
                robot.leftMotor.setPower(0);
            }

            if (robot.rightLightSensor.getLightDetected() < .38)
            {

                if(robot.sideUltra.getUltrasonicLevel() > 100 || robot.sideUltra.getUltrasonicLevel() == 0) {
                    robot.rightMotor.setPower(-.25);
                } else if(robot.sideUltra.getUltrasonicLevel() >= 12) {
                    robot.rightMotor.setPower(-.18);
                } else if(robot.sideUltra.getUltrasonicLevel() <= 10) {
                    robot.rightMotor.setPower(-.32);
                }else{
                    robot.rightMotor.setPower(-.25);
                }
            }
            else
            {
                robot.rightMotor.setPower(0);
            }
            if (isStopRequested()) return;
            idle();



            robot.setDrivePower(-.25);
            if (isStopRequested()) return;
            idle();

            ///////////////Send the info back to driver station using telemetry function.
            telemetry.addData("Clear", robot.colorDetector.alpha());
            telemetry.addData("Red  ", robot.colorDetector.red());
            telemetry.addData("Green", robot.colorDetector.green());
            telemetry.addData("Blue ", robot.colorDetector.blue());
            telemetry.addData("rightpower", robot.rightMotor.getPower());
            telemetry.addData("ultraDistance", robot.sideUltra.getUltrasonicLevel());
            updateTelemetry(telemetry);
        }

        robot.setDrivePower(0);

        if (red_first == false) {

        } else /////////////////// if red_first is false//////////////////////
        {
            robot.driveForward(4.5);
        }

        /////////////////moves continuous rotation servo to push button on beacon////////////////
        robot.constServo.setPosition(0);
        sleep(3400);


        /////////////pulls beacon pusher arm 3/4 of the way back in
        robot.constServo.setPosition(1);
        sleep(2800);
        //////////////////Turn a little so that when we back up we vier away from the wall
        robot.turnDegrees(-8);

        ////////////////////drives towards the ramp to park on the center vortex platform
        robot.driveForward(36);

        ////////////Turn toward the center vortex////////////
        robot.turnDegrees(-77);

        ///////////////drive to the ball////////////////
        robot.driveForward(34);

        robot.turnDegrees(20);

        ////////////////ensures a complete stop///////////////////////
        robot.setDrivePower(0);

    }
}
