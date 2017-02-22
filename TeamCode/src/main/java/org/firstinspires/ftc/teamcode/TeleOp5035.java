package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 * <p/>
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * <p/>
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 * <p/>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "TeleOp5035")
public class TeleOp5035 extends OpMode {
    Hardware5035 robot = new Hardware5035();


    boolean runFiring = false;
    boolean IsMovingBallPickUpArm = false;
    boolean BallBoosterPoweringUp = false;
    boolean touchPressedLastLoop = false;
    boolean GuidePressedLastFrame = false;
    boolean YPressedLastFrame = false;
    boolean Reverse = false;
    boolean SweeperPower = false;
    boolean TriggerDownLastFrame = false;
    int PosNum = 0;
    int counter = 0;
    static final double DurDown = 350;
    static final double DurUp = 50;
    static final double PickUpSpeed = .60; // power of the arm in the up direction
    static final double BallDumpIdlePower = 0.1;
    static final double BallDumpRiseIdle = 0.35;
    ElapsedTime BallPickUpTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    ElapsedTime SweepOutTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    ElapsedTime TriggerTImer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    ElapsedTime FiringTImer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    //int DesiredPickupAction = 0;
    @Override
    public void init() {
        robot.init(hardwareMap, null);

        // reset all of our state variables
        IsMovingBallPickUpArm = false;
        BallBoosterPoweringUp = false;
        touchPressedLastLoop = false;
        GuidePressedLastFrame = false;
        YPressedLastFrame = false;
        Reverse = false;
        SweeperPower = false;
        runFiring = false;
        PosNum = 0;
        counter = 0;
        BallPickUpTimer.reset();
        SweepOutTimer.reset();
        TriggerTImer.reset();
        FiringTImer.reset();
        telemetry.addData("Version","1_1_2_5");
        telemetry.update();
    }

    @Override
    public void stop() {
        robot.stop();
    }

    @Override
    public void loop() {

        ///////////////////////////////TAKE ALL REVERSE DRIVE CODE WITH BUTTON OUT OF THE PROGRAM PLEASE/////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //   Reverse drive when guide button is pressed
        if (gamepad1.guide && !GuidePressedLastFrame) {
            Reverse = !Reverse;
            robot.setDrivePower(0);
        }

        if (Reverse) {
            //Reverse Drive
            if (gamepad1.left_bumper) {
                //slow Forward Drive
                if (null != robot.leftMotor) robot.leftMotor.setPower(-gamepad1.left_stick_y / 6);
                if (null != robot.rightMotor) robot.rightMotor.setPower(-gamepad1.right_stick_y / 6);
            } else {
                if (null != robot.leftMotor) robot.leftMotor.setPower(-gamepad1.right_stick_y);
                if (null != robot.rightMotor) robot.rightMotor.setPower(-gamepad1.left_stick_y);

            }
        } else {
            if (gamepad1.left_bumper) {
                //slow Forward Drive
                if (null != robot.leftMotor) robot.leftMotor.setPower(gamepad1.left_stick_y / 6);
                if (null != robot.rightMotor) robot.rightMotor.setPower(gamepad1.right_stick_y / 6);
            } else {
                //Forward Drive
                if (null != robot.leftMotor) robot.leftMotor.setPower(gamepad1.left_stick_y);
                if (null != robot.rightMotor) robot.rightMotor.setPower(gamepad1.right_stick_y);
            }
        }

        ballFiringLoop();
        ballPickupLoop();
        beaconPusherLoop();

        touchPressedLastLoop = robot.grabbutton.isPressed();
        GuidePressedLastFrame = gamepad1.guide;
        YPressedLastFrame = gamepad1.guide;
        TriggerDownLastFrame = gamepad2.right_bumper;
    }


    ///////////                                                                                                                         /////////////////////
    ///////////make sure to change ballfiringloop so that The balls will fire while the shooting button is pressed//////////
    //////////                                                                                                                          /////////////////////
    public void ballFiringLoop() {

        if(!TriggerDownLastFrame && gamepad2.right_bumper) { //if you press the new
            TriggerTImer.reset();
            runFiring = false;
            BallBoosterPoweringUp = true;
            if (null != robot.ballBooster1)
                robot.ballBooster1.setPower(.6);
            if (null != robot.ballBooster2)
                robot.ballBooster2.setPower(.6);
        }

        if(!gamepad2.right_bumper){
            robot.ballBooster1.setPower(0);
            robot.ballBooster2.setPower(0);
            BallBoosterPoweringUp = false;
        robot.detriggered();
        }

        if (BallBoosterPoweringUp) {
            if (null != robot.ballBooster1)
                robot.ballBooster1.setPower(.6);
            if (null != robot.ballBooster2)
                robot.ballBooster2.setPower(.6);

            if(!runFiring && TriggerTImer.milliseconds() >= 800) {
                runFiring = true;
                FiringTImer.reset();
            }

            if (runFiring)
            {
                if(gamepad2.left_bumper) {
                    FiringTImer.reset();
                }
                if (FiringTImer.milliseconds() > 1200) {
                    FiringTImer.reset();
                }
                else if (FiringTImer.milliseconds() > 800) {
                    robot.detriggered();
                }
                else if (FiringTImer.milliseconds() > 400) {
                    robot.triggered();
                }
            }
        }

        /*
        // starts ball booster motors when x is pressed and shuts them down when A is pressed.
        if (gamepad2.x == true) {
            BallBoosterPoweringUp = true;
        }
        //when a button is hit on gamepade 2 it turns off the ballBoosters and sets BallBoosterPowerUp
        if (gamepad2.a == true) {
            BallBoosterPoweringUp = false;
            if (null != robot.ballBooster1) robot.ballBooster1.setPower(0);
            if (null != robot.ballBooster2) robot.ballBooster2.setPower(0);
        }


        if (BallBoosterPoweringUp) {
            if (null != robot.ballBooster1)
                robot.ballBooster1.setPower(1);
            if (null != robot.ballBooster2)
                robot.ballBooster2.setPower(1);
        }


        //if ball boosters are on or the gamepad2.guide button is pressed, and the gamepad2.righttrigger is pressed, the pop up servo fires.
        if ((robot.ballBooster1.getPower() > 0.9 || gamepad2.guide == true) && gamepad2.right_bumper) {
            robot.triggered();
            TriggerTImer.reset();
        }
        //if trigger is not pressed then it is reset
        if (!gamepad2.right_bumper && TriggerTImer.milliseconds() > 400) {
            robot.detriggered();

        }
        */
    }

    public void beaconPusherLoop() {
        if (gamepad2.left_trigger > .15) {
            robot.constServo.setPosition(1);
        } else if (gamepad2.right_trigger > .15) {
            robot.constServo.setPosition(0);
        } else {
            if (robot.constServo.getPosition() != .5)
            {
                robot.constServo.setPosition(.50);
            }
        }
    }

    //pos bot 0 mid 150 top 325

    public void ballPickupLoop() {

        //Code Bellow is all for driver 2's arm motions
        //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

        //CHECK TO SEE IF WE WANT TO MOVE THE BALL PICKUP ARM DOWN
        if (gamepad2.dpad_down && !IsMovingBallPickUpArm && !robot.grabbutton.isPressed()) {
            IsMovingBallPickUpArm = true;
            BallPickUpTimer.reset();
            robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.ballDump.setPower(-0.10);// power of arm in the down direction
        }


        if (null != robot.sweeperMotor) {
            if (gamepad2.y) {
                robot.sweeperMotor.setPower(-1);
            }
            else if (gamepad2.b) {
                robot.sweeperMotor.setPower(1);
            }
            else if (robot.grabbutton.isPressed()) {
                robot.sweeperMotor.setPower(1);
            }
            else if (robot.balldumpup.isPressed() && SweepOutTimer.milliseconds() < 1000)
            {
                robot.sweeperMotor.setPower(1);
            }
            else if (robot.sweeperMotor.getPower() != 0)
            {
                robot.sweeperMotor.setPower(0);
            }
        }



        if (gamepad2.dpad_up && !IsMovingBallPickUpArm && !robot.balldumpup.isPressed()) {// if trigger is pressed and the arm is not currently moving and the top button is not currently pressed move arm up
            IsMovingBallPickUpArm = true;
            BallPickUpTimer.reset();
            robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.ballDump.setPower(.6);//setting up arm motor up power
        }



        //checking for reasons to turn of power to arm.
        if (IsMovingBallPickUpArm) {
            if (robot.ballDump.getPower() < 0) {
                if (robot.grabbutton.isPressed()) {
                    robot.ballDump.setPower(0);
                    robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    IsMovingBallPickUpArm = false;
                }
                if (BallPickUpTimer.milliseconds() > 400)
                {
                    robot.ballDump.setPower(0);
                    IsMovingBallPickUpArm = false;
                }
                else if (BallPickUpTimer.milliseconds() > 200) {
                    robot.ballDump.setPower(-.10);
                }
            } else {
                if (robot.balldumpup.isPressed()) {
                    SweepOutTimer.reset();
                    robot.ballDump.setPower(BallDumpIdlePower);
                    IsMovingBallPickUpArm = false;
                }
                if (BallPickUpTimer.milliseconds() > 1000) {
                    robot.ballDump.setPower(BallDumpRiseIdle);
                    IsMovingBallPickUpArm = false;
                }
            }
        }
        if (robot.ballDump.getPower() == BallDumpRiseIdle && robot.balldumpup.isPressed()) {
            robot.ballDump.setPower(BallDumpRiseIdle);
        }

         if (!robot.balldumpup.isPressed() && robot.ballDump.getPower() > 0 && !IsMovingBallPickUpArm)
         {
             robot.ballDump.setPower(BallDumpRiseIdle);
         }
//        telemetry.addData("Values type = Unknown/ BallBooster is active", BallBoosterPoweringUp);
//        telemetry.addData("Ball Booster Power", robot.ballBooster1.getPower());
//        telemetry.addData("Position of server", robot.popUp.getPosition());
//        telemetry.addData("popUp pos", robot.popUp.getPosition());
//        telemetry.addData("Triggered Left", gamepad1.left_trigger);
//        telemetry.addData("Triggered Right", gamepad1.right_trigger);
//        telemetry.addData("motor encode", robot.ballDump.getCurrentPosition());
//        telemetry.addData("Button press", robot.grabbutton.isPressed());
//        telemetry.addData("this is a message", "you work right!!!");
//        telemetry.update(); //this should work
    }


}

