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

@TeleOp(name = "TestServoPosition")
public class TestServoPosition extends OpMode {
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
    static final double BallDumpRiseIdle = 0.2;
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
    }

    @Override
    public void stop() {
        robot.stop();
    }

    @Override
    public void loop() {

        if (gamepad1.dpad_up)
        {
            robot.popUp.setPosition(.70);
        }
        else if (gamepad1.dpad_right)
        {
            robot.popUp.setPosition(.6);
        }
        else if (gamepad1.dpad_down)
        {
            robot.popUp.setPosition(.5);
        }
        else if (gamepad1.dpad_right)
        {
            robot.popUp.setPosition(.4);
        }
        else
        {
            robot.popUp.setPosition(1);
        }
        telemetry.addData("ServoPosition", robot.popUp.getPosition());
        telemetry.addData("front ultra level", robot.frontUltra.getUltrasonicLevel());
        telemetry.update();
    }
}

