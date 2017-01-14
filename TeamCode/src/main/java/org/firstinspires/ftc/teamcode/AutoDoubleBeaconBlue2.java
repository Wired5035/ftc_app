package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static android.graphics.Color.RED;
import static android.graphics.Color.red;

/**
 * Created by Kota Baer on 10/11/2016.
 */

@Autonomous(name = "AutoTest", group = "Concept")
public class AutoDoubleBeaconBlue2 extends LinearOpMode {
    double OneFoot = 12; //in inches

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware5035 robot = new Hardware5035();
        robot.init(hardwareMap);
        waitForStart();

        /*///////////////////////////////

// Turn on ball shooter motors
        robot.ballBooster1.setPower(1);
        robot.ballBooster2.setPower(1);
        // allow the motors to spin up
        sleep(2000);

        // Driving the robot in position to shoot the balls
        robot.driveForward((OneFoot * 2.25) - 1);


//////////////////////good code after this////////////////////////////////////////
        //shooting the balls
        sleep(500);
        robot.triggered();
        sleep(500);
        robot.detriggered();
        sleep(500);
        robot.triggered();
        sleep(500);
        robot.detriggered();

        //turns off shooter
        robot.ballBooster1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ballBooster1.setPower(0);
        robot.ballBooster2.setPower(0);

// turns half way towards the wall and the first beacon
        robot.turnDegrees(-60);
        // drive haly way toward the first beacon////////
        robot.driveForward(23);
        //turns rest of the way
        robot.turnDegrees(-20);
        //drives the rest of the way
        robot.driveForward(22);
        // turn left to line up with the wall////////

        ////////////////////turn to 88 maybe///////////////
        robot.turnDegrees(-90);





        //////////Drive until 5 in from field wall using FWD ultrasonic////////////////////////

        ////////turns 90 deg. to be parallel with the wall

        robot.turnDegrees(90);
 *///////////////////////////////////////////////////////////////////////////////////// ALL code before this is good
        // Drive backwards to white line under the first beacon  ////////
        boolean found_color = false;
        boolean red_first = false;
        while (robot.leftLightSensor.getLightDetected() < .38 || robot.rightLightSensor.getLightDetected() < .38) {
            if (found_color == false) ;
            {
                if (robot.colorDetector.red() >= 1) {
                    red_first = true;
                    found_color = true;
//                    robot.setDrivePower(0);
//                    sleep(5000);
                }
                if (robot.colorDetector.blue() >= 1) {
                    red_first = false;
                    found_color = true;
//                    robot.setDrivePower(0);
//                    sleep(5000);
                }
            }
            //tells what color was seen first
            telemetry.addData("Red First", red_first);
            //Runs motors based on where the white line is
            if (robot.leftLightSensor.getLightDetected() < .38) {
                robot.leftMotor.setPower(-.10);
            } else {
                robot.leftMotor.setPower(0);
            }

            if (robot.rightLightSensor.getLightDetected() < .38)
            {
                robot.rightMotor.setPower(-.10);
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
            updateTelemetry(telemetry);
        }

        robot.setDrivePower(0);

        if (red_first == true) {

        } else // if red_first is false
        {
            robot.driveForward(3.5);
        }

        //moves continuous rotation servo to push button on beacon
        robot.constServo.setPosition(0);
        sleep(3500);


        //pulls beacon pusher arm 3/4 of the way back in
        robot.constServo.setPosition(1);
        sleep(2000);

        robot.constServo.setPosition(.51);

        // gets us past the original line, so we can look for the other line without finding it immediately.
       robot.driveReverse(6);

/*
        // Finds second beacon line, and resets color sensor booleans
        found_color = false;
        red_first = false;
        while (robot.leftLightSensor.getLightDetected() < .38 || robot.rightLightSensor.getLightDetected() < .38) {
            //finds first color seen on the second beacon
            if (found_color == false) ;
            {
                if (robot.colorDetector.red() >= 1) {
                    red_first = true;
                    found_color = true;
                }
                if (robot.colorDetector.blue() > 1) {
                    red_first = false;
                    found_color = true;
                }
            }
            //tells what color was seen first
            telemetry.addData("First Color", red_first);
            //Runs motors based on where the white line is
            if (robot.leftLightSensor.getLightDetected() < .38) {
                robot.leftMotor.setPower(-.10);
            } else {
                robot.leftMotor.setPower(0);
            }

            if (robot.rightLightSensor.getLightDetected() < .38)
            {
                robot.rightMotor.setPower(-.10);
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

            // send the info back to driver station using telemetry function.
            telemetry.addData("Clear", robot.colorDetector.alpha());
            telemetry.addData("Red  ", robot.colorDetector.red());
            telemetry.addData("Green", robot.colorDetector.green());
            telemetry.addData("Blue ", robot.colorDetector.blue());
            updateTelemetry(telemetry);
        }

        robot.setDrivePower(0);

        if (red_first == true) {

        } else // if red_first is false
        {
            robot.driveForward(3.5);
        }

        //moves continuous rotation servo to push button on beacon
        robot.constServo.setPosition(0);
        sleep(2000);


        //pulls beacon pusher arm 3/4 of the way back in
        robot.constServo.setPosition(1);
        sleep(2500);

        //stops cont. rotation servo
        robot.constServo.setPosition(.51);
        //turns towards the large ball
        robot.turnDegrees(30);
        //drives towards the ball to park on the center vortex platform
        robot.driveForward(42);
        //ensures a complete stop
        robot.setDrivePower(0);
*/

    }
}



