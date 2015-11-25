package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kota Baer on 11/17/2015.
 */
public class HangingRobotTeleOp extends OpMode{
    static final float pickup =0.255f;
    static final float holding =1f;
    static final float score =0.34f;
    static final float dump =-0.17f;
    static final float pickuparm =1f;
    static final float holdingarm =1f;
    static final float scorearm =0f;
    static final float dumparm =0f;
    /*
    5 MOTORS FOR THE TWO SIDES
    rightSide is 2 motors powered as one
    rightSide is 2 motors powered as one
    winch is 2 motors
    teleTubExtender(telescoping tub extender) is one motor
    ////we need six servos:////
    bucket arm
    bucket
    right tub drop
    left tub drop
    right tub crank
    left tub crank
     */
    DcMotor motorRightRemote1;
    DcMotor motorLeftRemote1;
    DcMotor hookWinchRightRemote2;
    DcMotor hookWinchLeftRemote2;
    DcMotor telescopeExtendMotorRemote2;
    Servo bucketServoRemote1;
    Servo bucketArmServoRemote1;
    Servo telescopingPVCDropRightRemote2;
    Servo telescopingPVCDropLeftRemote2;
    Servo firstLinkPVCExtendOrRetractRightRemote2;
    Servo firstLinkPVCExtendOrRetractLeftRemote2;

    @Override
    public void init() {

        motorRightRemote1 = hardwareMap.dcMotor.get("right");
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");
        motorLeftRemote1.setDirection(DcMotor.Direction.REVERSE);
        motorRightRemote1.setDirection(DcMotor.Direction.FORWARD);
        hookWinchRightRemote2=hardwareMap.dcMotor.get("RightHook");
        hookWinchLeftRemote2=hardwareMap.dcMotor.get("LeftHook");
        telescopeExtendMotorRemote2=hardwareMap.dcMotor.get("TelescopePVC");
        bucketServoRemote1=hardwareMap.servo.get("Bucket");
        bucketArmServoRemote1=hardwareMap.servo.get("Arm");
        telescopingPVCDropRightRemote2=hardwareMap.servo.get("PVCDropRight");
        telescopingPVCDropLeftRemote2=hardwareMap.servo.get("PVCDropLeft");
        firstLinkPVCExtendOrRetractRightRemote2=hardwareMap.servo.get("ServoExtenderRight");
        firstLinkPVCExtendOrRetractLeftRemote2=hardwareMap.servo.get("ServoExtenderLeft");
    }

    @Override
    public void loop() {
        if(gamepad2.left_bumper = true)
        {
            telescopingPVCDropRightRemote2.setPosition(1);
        }

        if(gamepad2.right_bumper = true)
        {
            telescopingPVCDropLeftRemote2.setPosition(1);
        }

        if(gamepad2.left_trigger > .75)
        {
            firstLinkPVCExtendOrRetractRightRemote2.setPosition(1);
        }

        if(gamepad2.right_trigger > .75)
        {
            firstLinkPVCExtendOrRetractLeftRemote2.setPosition(1);
        }

        if(gamepad1.b = true)
        {
            bucketArmServoRemote1.setPosition(pickuparm);
            bucketServoRemote1.setPosition(pickup);
        }

        if(gamepad1.y = true)
        {
            //pizza = yummy
            bucketArmServoRemote1.setPosition(holdingarm);
            bucketServoRemote1.setPosition(holding);
        }

        if(gamepad1.a = true)
        {
            //popcorn = YAY!
            bucketArmServoRemote1.setPosition(dumparm);
            bucketServoRemote1.setPosition(dump);
        }

        if(gamepad1.x = true)
        {
            //Ben = Cool!
            bucketArmServoRemote1.setPosition(scorearm);
            bucketServoRemote1.setPosition(score);
        }

        if(gamepad2.x = true)
        {
            hookWinchRightRemote2.setPower(gamepad2.left_stick_y * 0.90f);
        }
        else
        {
            hookWinchRightRemote2.setPower(gamepad2.left_stick_y);
        }

        if(gamepad2.b = true)
        {
            hookWinchLeftRemote2.setPower(gamepad2.left_stick_y * 0.90f);
        }
        else
        {
            hookWinchLeftRemote2.setPower(gamepad2.left_stick_y);
        }

        telescopeExtendMotorRemote2.setPower(gamepad2.right_stick_y);

        motorLeftRemote1.setPower(gamepad1.left_stick_y);
        motorRightRemote1.setPower(gamepad1.right_stick_y);

    }
}
