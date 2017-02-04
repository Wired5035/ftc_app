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

@Autonomous(name = "BeaconRed", group = "Concept")
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
        robot.driveForward((OneFoot * 2.0) - 1);

        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);
///////////////////////shooting the balls////////////////////////////////////

        sleep(100);
        robot.triggered();
        sleep(500);
        robot.detriggered();
        sleep(600);
        robot.triggered();
        sleep(450);
        robot.detriggered();

        //////////////////turns off shooter///////////////////
        robot.ballBooster1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ballBooster1.setPower(0);
        robot.ballBooster2.setPower(0);

        /////////////// drive further to the ball     //
        robot.driveForward(5);

        ////////////// turns half way towards the wall and the first beacon////////////////
        robot.turnDegrees(67);
        // drive halfway toward the first beacon  ////////
        robot.setDrivePower(.75);
        robot.driveForward(25);
        //turns rest of the way
        robot.turnDegrees(15);

        //drives the rest of the way
        // robot.setDrivePower(1);
        //robot.driveForward(22);


        //  telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        robot.setDrivePower(.25);
        //drive using ultrasonic till ~14cm away from wall
        //  telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        while (robot.frontUltra.getUltrasonicLevel() >= 17) {
            telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());
        }//turn off drive power
        robot.setDrivePower(0);
        //  telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value

        //  telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());
        sleep(100);

        ////turns to be parallel with the wall
        robot.turnDegrees(-85);


        ///////////////////////
        boolean found_color = false;
        boolean red_first = false;
        boolean found_left = false;
        boolean found_right = false;


        while (!found_left || !found_right) {

            ////////////////////    tells what color was seen first          /////////////////////////
            telemetry.addData("Red First", red_first);

            /////////////////        Runs motors based on if LEFT LIGHT SENSOR SEES WHITE ///////////////////
            if (found_left == false) {
                if (robot.leftLightSensor.getLightDetected() < .36) {
                    robot.rightMotor.setPower(.15);
                }
                if (robot.leftLightSensor.getLightDetected() >= .36)  ///white
                {
                    robot.rightMotor.setPower(-.09);
                    found_left = true;
                }

            }

            /////////////////          Runs motors based on if RIGHT LIGHT SENSOR SEES WHITE     ///////////////////

            if (found_right == false) {
                if (robot.rightLightSensor.getLightDetected() < .36) {
                    robot.leftMotor.setPower(.15);
                }
                if (robot.rightLightSensor.getLightDetected() >= .36)///    white
                {
                    robot.leftMotor.setPower(-.09);
                    found_right = true;
                }

            }
        }
            //while driving find the color  /////////
        int current_tick = robot.rightMotor.getCurrentPosition();
            if (robot.rightMotor.getCurrentPosition() - current_tick < robot.inchToTickConverter(6))
            {
                robot.rightMotor.setPower(.25);
                robot.rightMotor.setPower(.25);
                if (found_color == false) ;
                {
                    if (robot.colorDetector.red() >= 2) {
                        red_first = true;
                        found_color = true;

                    }
                    if (robot.colorDetector.blue() >= 2) {
                        red_first = false;
                        found_color = true;

                    }
                }
            }
            else
            {
                robot.rightMotor.setPower(0);
                robot.rightMotor.setPower(0);
            }
            if (red_first == false) {
                robot.driveForward(3);
            }
            else // if red_first is false
            {
                robot.driveForward(9);
            }
            robot.constServo.setPosition(0);
            sleep(4000);

            robot.constServo.setPosition(1);
            sleep(3200);

            robot.constServo.setPosition(.51);



        while (!found_left || !found_right) {

            ////////////////////    tells what color was seen first          /////////////////////////
            telemetry.addData("Red First", red_first);

            /////////////////        Runs motors based on if LEFT LIGHT SENSOR SEES WHITE ///////////////////
            if (found_left == false) {
                if (robot.leftLightSensor.getLightDetected() < .36) {
                    robot.rightMotor.setPower(.15);
                }
                if (robot.leftLightSensor.getLightDetected() >= .36)  ///white
                {
                    robot.rightMotor.setPower(-.09);
                    found_left = true;
                }

            }

            /////////////////          Runs motors based on if RIGHT LIGHT SENSOR SEES WHITE     ///////////////////


            if (found_right == false) {
                if (robot.rightLightSensor.getLightDetected() < .36) {
                    robot.leftMotor.setPower(.15);
                }
                if (robot.rightLightSensor.getLightDetected() >= .36)///    white
                {
                    robot.leftMotor.setPower(-.09);
                    found_right = true;
                }

            }
        }
        //while driving find the color  /////////

        current_tick = robot.rightMotor.getCurrentPosition();
        if (robot.rightMotor.getCurrentPosition() - current_tick < robot.inchToTickConverter(6))
        {
            robot.rightMotor.setPower(.25);
            robot.rightMotor.setPower(.25);
            if (found_color == false) ;
            {
                if (robot.colorDetector.red() >= 2) {
                    red_first = true;
                    found_color = true;

                }
                if (robot.colorDetector.blue() >= 2) {
                    red_first = false;
                    found_color = true;

                }
            }
        }
        else
        {
            robot.rightMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
        if (red_first == false) {
            robot.driveForward(3);
        }
        else // if red_first is false
        {
            robot.driveForward(9);
        }
        robot.constServo.setPosition(0);
        sleep(4000);

        robot.constServo.setPosition(1);
        sleep(3200);

        robot.constServo.setPosition(.51);







    }
    }
