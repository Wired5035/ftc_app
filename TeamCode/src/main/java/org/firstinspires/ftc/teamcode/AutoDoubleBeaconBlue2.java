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
        robot.ballDump.setPower(.3);
        waitForStart();

////////////////////////////////////////Phase #1 turn on shooter motors, Drive forward, shoot and Drive to wall//////////////////////////////////////////////
// Turn on ball shooter motors
        robot.ballBooster1.setPower(1);/////////////start motors for shooting/////////////
        robot.ballBooster2.setPower(1);

        // Driving the robot in position to shoot the balls
        robot.driveForward((OneFoot * 2.0) - 1);


///shooting the balls///

        sleep(100);
        robot.triggered();
        sleep(450);
        robot.detriggered();
        sleep(800);
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
        robot.driveForward(3);

        ////////////// turns half way towards the wall and the first beacon////////////////
        robot.turnDegrees(-60);
        // drive halfway toward the first beacon  ////////
        robot.setDrivePower(.75);
        robot.driveForward(23);
        //turns rest of the way
        robot.turnDegrees(-20);


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


        //drives the rest of the way
 /*ss       boolean gotToCorrectDistance = false;
        robot.setDrivePower(.5);
        //  telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        //drive using ultrasonic till ~14cm away from wall
        //  telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        while (!gotToCorrectDistance)
        {
            // Do we know our distance right now?
            if (robot.frontUltra.getUltrasonicLevel() != 0 && robot.frontUltra.getUltrasonicLevel() != 255)
            {
                if ( robot.frontUltra.getUltrasonicLevel() > 20 )
                {
                    robot.setDrivePower(0.25);
                }
                else if ( robot.frontUltra.getUltrasonicLevel() > 14 )
                {
                    robot.setDrivePower(0.15);
                }
                else if (robot.frontUltra.getUltrasonicLevel() > 10)
                {
                    robot.setDrivePower(.1);
                }
                else if (robot.frontUltra.getUltrasonicLevel() < 9)
                {
                    robot.setDrivePower(-.05);
                }
                else
                {
                    robot.setDrivePower(0);
                    sleep(100);
                    if (robot.frontUltra.getUltrasonicLevel() <= 10 && robot.frontUltra.getUltrasonicLevel() >= 9)
                    {
                        gotToCorrectDistance = true;
                    }
                }
            }
 sss */          telemetry.addData("front ultra level", robot.frontUltra.getUltrasonicLevel());
            telemetry.update();
            idle();
            if (isStopRequested())
            {
                break;
            }
        }
        //turn off drive power
        robot.setDrivePower(0);
        //telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value


        sleep(200);


        ////turns to be parallel with the wall
        robot.turnDegrees(-82);
        ////////////////////////////////////////////////////////////////End of Phase #1//////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////Phase #2 Turn and test beacon color for beacon one//////////////////////////////////////////////////////////////////
        robot.rightMotor.setPower(.3);
        robot.leftMotor.setPower(.3);

        sleep(125);

        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);

        ////////////////////// Drive backwards to white line under the FIRST BEACON//////////////////////////////////

        //
        // ]\\\\\\\\
        FindAndPushBeacon(robot, false);

        //////////////////         pulls beacon pusher arm 3/4 of the way back in      /////////////////////
        robot.constServo.setPosition(1);
        sleep(2800);

        robot.constServo.setPosition(.51);

        ///////////       Drives 1 foot toward the next beacon before starting to look for the line. the other line without finding it immediately.
        robot.driveReverse(8);

        ///////////////////////////////////////////////////////////////Phase #3 test for Second Beacon/////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////End of Phase #3/////////////////////////////////////////////////////////////////

        //////////////////////////           Finds SECOND BEACON  line, and resets color sensor booleans             /////////////////

        FindAndPushBeacon(robot, false);

        //////////////////     Turn a little so that when we back up we vier away from the wall
        robot.turnDegrees(-38);

        ////////////////////         drives towards the ramp to get away from the wall and make a sharper turn      ///
        robot.setDrivePower(1);
        robot.driveForward(54);

        ////////////           Turn toward the center vortex                      ////////////
        robot.turnDegrees(-20);

        if (isStopRequested()) return;
        //////////////////         pulls beacon pusher arm 3/4 of the way back in      /////////////////////
       // robot.constServo.setPosition(1);
        sleep(3000);

        robot.constServo.setPosition(.51);

        ////////////////             ensures a complete stop         ///////////////////////
        robot.setDrivePower(0);


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
        //ssssssssssss

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

        //sssssssss

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


/*ssss
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
sss*/
        if (isStopRequested()) return;

        ////////////////            moves continuous rotation servo to push button on beacon          ///////////////////
        robot.constServo.setPosition(0);
        sleep(2000);
        robot.constServo.setPosition(.51);
    }
}




