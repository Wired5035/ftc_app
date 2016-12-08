package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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

    boolean IsUp = true;
    boolean IsMovingBallPickUpArm = false;
    boolean BallBoosterPoweringUp = false;
    boolean touchPressedLastLoop = false;
    boolean GuidePressedLastFrame = false;
    boolean Reverse = false;
    int PosNum = 0;
    int counter = 0;
    static final double DurDown = 100;
    static final double DurUp = 10;
    static final double PickUpSpeed = .60; // power of the arm in the up direction
    static final double BallDumpIdlePower = 0.10;
    ElapsedTime BallPickUpTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    //int DesiredPickupAction = 0;
    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        //tank driveReverse add
        if (gamepad1.guide && !GuidePressedLastFrame) {
            Reverse = !Reverse;
        }

        if (Reverse) {
            if (null != robot.leftMotor) robot.leftMotor.setPower(-gamepad1.right_stick_y);
            if (null != robot.rightMotor) robot.rightMotor.setPower(-gamepad1.left_stick_y);
        } else {
            if (null != robot.leftMotor) robot.leftMotor.setPower(gamepad1.left_stick_y);
            if (null != robot.rightMotor) robot.rightMotor.setPower(gamepad1.right_stick_y);
        }

        ballFiringLoop();
        ballPickupLoop();
    }

    public void ballFiringLoop() {
        // starts ball booster motors when x is pressed and shuts them down when A is pressed.
        if (gamepad2.x == true) {
            BallBoosterPoweringUp = true;
        }
        if (gamepad2.a == true) {
            BallBoosterPoweringUp = false;
            if (null != robot.ballBooster1) robot.ballBooster1.setPower(0);
            if (null != robot.ballBooster2) robot.ballBooster2.setPower(0);
        }
        if (BallBoosterPoweringUp) {
            if (null != robot.ballBooster1)
                robot.ballBooster1.setPower(Math.max(robot.ballBooster1.getPower() + .000005, 1));
            if (null != robot.ballBooster2)
                robot.ballBooster2.setPower(Math.max(robot.ballBooster2.getPower() + .000005, 1));
        }
        //if ball boosters are on or the gamepad2.guide button is pressed, and the gamepad2.righttrigger is pressed, the pop up servo fires.
        if ((robot.ballBooster1.getPower() > 0.9 || gamepad2.guide == true) && gamepad2.right_trigger > 0.5) {
            robot.triggered();
        }
        if (gamepad2.right_trigger < 0.25) {
            robot.detriggered();
        }
    }

    //pos bot 0 mid 150 top 325
    public void ballPickupLoop() {


        if (!IsMovingBallPickUpArm) {
            if (gamepad2.right_stick_x > .15) {
                BallPickUpTimer.reset();
                robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.ballDump.setPower(0.17);
            } else if (gamepad2.right_stick_y > .15) {
                BallPickUpTimer.reset();
                robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.ballDump.setPower(-0.02);
            } else {
                 //robot.ballDump.setPower(BallDumpIdlePower);
            }
        }


        //Code Bellow is all for driver 1's arm motions
        //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
        if (gamepad1.right_trigger > .15 && !IsMovingBallPickUpArm && !robot.grabbutton.isPressed()) {
            IsMovingBallPickUpArm = true;
            BallPickUpTimer.reset();
            robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.ballDump.setPower(-0.20);// power of arm in the down direction
        }
        /*
        if (gamepad1.right_bumper && !IsMovingBallPickUpArm){
            IsMovingBallPickUpArm = true;
            BallPickUpTimer.reset();
            robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.ballDump.setPower(-1);
        }
        */

        if ((BallPickUpTimer.milliseconds() >= DurDown && robot.ballDump.getPower() < 0)) {
            IsMovingBallPickUpArm = false;
            IsUp = true;
            robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.ballDump.setPower(0);
        }

        if (gamepad1.left_trigger > .15 && !IsMovingBallPickUpArm && !robot.balldumpup.isPressed()) {// if trigger is pressed and the arm is not currently moving and the top button is not currently pressed move arm up
            IsMovingBallPickUpArm = true;
            BallPickUpTimer.reset();
            robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.ballDump.setPower(PickUpSpeed);//setting up arm motor up power
        }
//        if (BallPickUpTimer.time() < 750 && robot.ballDump.getPower() > 0) {
//            robot.ballDump.setPower(Math.max(BallPickUpTimer.time() / 1000.0, .40));
//        }
        if ((robot.balldumpup.isPressed() && robot.ballDump.getPower() > 0) || (BallPickUpTimer.milliseconds() >= DurUp && robot.ballDump.getPower() > 0)) {
            IsMovingBallPickUpArm = false;
            IsUp = false;
            robot.ballDump.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.ballDump.setPower(BallDumpIdlePower);// sets the arm motor to a holding position
        }
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //Driver 2's arm motion Code is above


        telemetry.addData("Values type = Unknown/ BallBooster is active", BallBoosterPoweringUp);
        telemetry.addData("Ball Booster Power", robot.ballBooster1.getPower());
        telemetry.addData("Position of server", robot.popUp.getPosition());
        telemetry.addData("popUp pos", robot.popUp.getPosition());
        telemetry.addData("Triggered Left", gamepad1.left_trigger);
        telemetry.addData("Triggered Right", gamepad1.right_trigger);
        telemetry.addData("motor encode", robot.ballDump.getCurrentPosition());
        telemetry.addData("Button press", robot.grabbutton.isPressed());
        telemetry.addData("this is a message", "you work right!!!");
        telemetry.update(); //this should work
        touchPressedLastLoop = robot.grabbutton.isPressed();
        GuidePressedLastFrame = gamepad1.guide;
    }

}
