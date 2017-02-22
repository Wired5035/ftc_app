package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Hardware;

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
        telemetry.addData("Version","1_1_2_6");
        telemetry.update();
        waitForStart();

////////////////////////////////////////Phase #1 turn on shooter motors, Drive forward, shoot and Drive to wall//////////////////////////////////////////////
// Turn on ball shooter motors
       robot.StartBallLaunchers();

        // Driving the robot in position to shoot the balls
        robot.driveForward(23);


///shooting the balls///

        sleep(100);
        robot.triggered();
        sleep(450);
        robot.detriggered();
        sleep(800);
        robot.triggered();
        sleep(450);
        robot.detriggered();
      //  sleep(800);
      //  robot.triggered();
      //  sleep(450);
      //  robot.detriggered();
        //////////////////turns off shooter///////////////////
        robot.ballBooster1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        robot.ballBooster1.setPower(0);
        robot.ballBooster2.setPower(0);

        /////////////// drive further to the ball     //
        robot.driveForward(3);

        ////////////// turns half way towards the wall and the first beacon////////////////
        robot.turnDegrees(-67);
        // drive halfway toward the first beacon  ////////
        robot.driveForward(28);
        //turns rest of the way
        robot.turnDegrees(-12);


        boolean gotToCorrectDistance = false;
        robot.setDrivePower(.5);
        //  telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        //drive using ultrasonic till ~14cm away from wall
        //  telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        while (!gotToCorrectDistance)
        {
            // Do we know our distance right now?
            if (robot.frontUltra.getUltrasonicLevel() != 0 && robot.frontUltra.getUltrasonicLevel() != 255)
            {
                if ( robot.frontUltra.getUltrasonicLevel() > 22 )
                {
                    robot.setDrivePower(0.25);
                }
                else if ( robot.frontUltra.getUltrasonicLevel() > 16 )
                {
                    robot.setDrivePower(0.15);
                }
                else if (robot.frontUltra.getUltrasonicLevel() > 12)
                {
                    robot.setDrivePower(.1);
                }
                else if (robot.frontUltra.getUltrasonicLevel() < 11)
                {
                    robot.setDrivePower(-.05);
                }
                else
                {
                    robot.setDrivePower(0);
                    sleep(100);
                    if (robot.frontUltra.getUltrasonicLevel() <= 12 && robot.frontUltra.getUltrasonicLevel() >= 11)
                    {
                        gotToCorrectDistance = true;
                    }
                }
            }
            telemetry.addData("front ultra level", robot.frontUltra.getUltrasonicLevel());
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


        sleep(40);


        ////turns to be parallel with the wall
        robot.turnDegrees(-87);
        ////////////////////////////////////////////////////////////////End of Phase #1//////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////Phase #2 Turn and test beacon color for beacon one//////////////////////////////////////////////////////////////////
        robot.rightMotor.setPower(.3);
        robot.leftMotor.setPower(.3);

        sleep(125);

        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);


        /////////// Function connects to nearly the bottom of the code: finds the line, runs color seeking code, then pushes beacon.    /////////////
        FindAndPushBeacon(robot, false);


        //////////////////         pulls beacon pusher arm in some and then keeps running until stopped inside aline_to_wall     /////////////////////
        robot.constServo.setPosition(1);
        sleep(400);
        ///////////////////////////////////////////////////////////////Phase #3 test for Second Beacon//////////////////////////////////////////////////////////////////////////////



/////////// Function connects to the bottom of the code: aligns angle and then corrects distance to the wall    /////////////////////
        aline_to_wall(robot, 14, 14, true);





/////////// Function connects to nearly the bottom of the code: finds the line, runs color seeking code, then pushes beacon.    /////////////
        FindAndPushBeacon(robot, false);

        ///////////////////////////////////////////////////////////////End of Phase #3//////////////////////////////////////////////////////////////////////////////////////////////

        /////////////pull arm in just long enough to get off the button///////
        robot.constServo.setPosition(1);
        sleep(300);
        ///////////turns towards the center/////////
        robot.turnDegrees(-38);


        //////////////      drives towards the center vortex while the arm is still pulling in       ////
        robot.rightMotor.setPower(1);
        robot.leftMotor.setPower(1);
        sleep(1850);
        robot.setDrivePower(0);
        //////////////////         stops arm     /////////////////////
        robot.constServo.setPosition(.51);
        ////////////           hits the ball                      ////////////
        robot.turnDegrees(40);

        if (isStopRequested()) return;


        ////////////////             ensures a complete stop         ///////////////////////
        robot.setDrivePower(0);


    }

    private void FindAndPushBeacon(Hardware5035 robot, boolean trackWallDistance) throws InterruptedException {
        boolean found_color = false;
        boolean seeing_red = false;
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
                    robot.rightMotor.setPower(.2);
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
          //  telemetry.addData("ultraDistance", robot.sideUltra.getUltrasonicLevel());
          //  telemetry.update();
        }
        robot.driveForward(3.5);

        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);
     /*   telemetry.addData("green", robot.colorDetector.green());
        telemetry.addData("alpha", robot.colorDetector.alpha());
        telemetry.addData("Red  ", robot.colorDetector.red());
        //  telemetry.addData("Green", robot.colorDetector.green());
        telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
      */  telemetry.update();


        while(found_color == false){
            if(robot.colorDetector.blue() > robot.colorDetector.red())
            {
                seeing_red = false;
                found_color = true;
          /*      telemetry.addData("Blue First", !seeing_red);
                telemetry.update();
                telemetry.addData("green", robot.colorDetector.green());
                telemetry.addData("alpha", robot.colorDetector.alpha());
                telemetry.addData("Red  ", robot.colorDetector.red());
                //  telemetry.addData("Green", robot.colorDetector.green());
                telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
           */     telemetry.update();
            }
            else if (robot.colorDetector.red() > robot.colorDetector.blue())
            {
                seeing_red = true;
                found_color = true;
          /*      telemetry.addData("Red First", seeing_red);
                telemetry.update();
                telemetry.addData("green", robot.colorDetector.green());
                telemetry.addData("alpha", robot.colorDetector.alpha());
                telemetry.addData("Red  ", robot.colorDetector.red());
                //  telemetry.addData("Green", robot.colorDetector.green());
                telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
          */      telemetry.update();
            }
            if (isStopRequested()) return;
            idle();
        }


        ///????????  robot.setDrivePower(0);

        if (seeing_red) {

        }
        else // if red_first is false
        {
            robot.driveReverse(2.8);
        }

        if (isStopRequested()) return;

        ////////////////            moves continuous rotation servo to push button on beacon          ///////////////////
        robot.constServo.setPosition(0);
        telemetry.addData("done","Done");
        sleep(3200);

        robot.constServo.setPosition(.51);
    }

    private void aline_to_wall(Hardware5035 robot, double distanceAway, double distanceNear, boolean active) throws InterruptedException {
        double distance1;
        double distance2;
      //sleep(1000);
        if(active){
            robot.turnDegrees((8));
            sleep(100);
            distance1 = robot.sideUltra.getUltrasonicLevel();
            robot.driveReverse(14);
            distance2 = robot.sideUltra.getUltrasonicLevel();
            double angle = -(90 - Math.toDegrees(Math.atan2((14 * 2.54), (distance2 - distance1))));
            telemetry.addData("angle", angle);
            telemetry.update();
            //sleep(5000);
            robot.turnDegrees(angle);
            //sleep(500);
            robot.constServo.setPosition(.51);
            distance2 = robot.sideUltra.getUltrasonicLevel();
            if(distance2 < (distanceNear )){
                robot.turnDegrees((8));
                double driveDistance = ((distanceNear - distance2)*1.3) /* 1.414) / 2.54*/;
                robot.driveReverse(driveDistance);
                robot.turnDegrees(-8);
                telemetry.addData("drive distance", driveDistance);
                telemetry.addData("distance from wall", distance2);
                telemetry.update();
            } else if (distance2 > (distanceAway)){
                robot.turnDegrees((-8));
                double driveDistance = ((distance2 - distanceAway)*1.3) /* 1.414) / 2.54*/;
                robot.driveReverse(driveDistance);
                robot.turnDegrees(8);
                telemetry.addData("drive distance", driveDistance);
                telemetry.addData("distance from wall", distance2);
                telemetry.update();
            }

           // sleep(2000);

        }

    }
}




