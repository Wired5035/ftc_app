package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kota Baer on 11/29/2015.
 */


public class testServoPosition extends OpMode {

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
    int pvcExtenderL;
    int pvcExtenderR;
    float servoCenter = 0.53f;

    @Override
    public void init() {

        motorRightRemote1 = hardwareMap.dcMotor.get("right");
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");
        motorLeftRemote1.setDirection(DcMotor.Direction.REVERSE);
        motorRightRemote1.setDirection(DcMotor.Direction.FORWARD);
        hookWinchRightRemote2=hardwareMap.dcMotor.get("RightHook");
        hookWinchLeftRemote2=hardwareMap.dcMotor.get("LeftHook");
        hookWinchRightRemote2.setDirection(DcMotor.Direction.REVERSE);
        telescopeExtendMotorRemote2=hardwareMap.dcMotor.get("TelescopePVC");
        bucketServoRemote1=hardwareMap.servo.get("Bucket");
        bucketArmServoRemote1=hardwareMap.servo.get("Arm");
        telescopingPVCDropRightRemote2=hardwareMap.servo.get("PVCDropRight");
        telescopingPVCDropLeftRemote2=hardwareMap.servo.get("PVCDropLeft");
        firstLinkPVCExtendOrRetractRightRemote2=hardwareMap.servo.get("ServoExtenderRight");
        firstLinkPVCExtendOrRetractLeftRemote2=hardwareMap.servo.get("ServoExtenderLeft");

        bucketServoRemote1.setPosition(0f);
        bucketArmServoRemote1.setPosition(1f);
        telescopingPVCDropRightRemote2.setPosition(0f);
        telescopingPVCDropLeftRemote2.setPosition(1f);
        firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoCenter);
        firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoCenter);

    }

    @Override
    public void loop() {

        telemetry.addData("bucket arm: ", bucketArmServoRemote1.getPosition());
        telemetry.addData("bucket: ", bucketServoRemote1.getPosition());
        telemetry.addData("pvcDropLeft: ", telescopingPVCDropLeftRemote2.getPosition());
        telemetry.addData("pvcDropRight: ", telescopingPVCDropRightRemote2.getPosition());
        telemetry.addData("PVCExtenderLeft: ", firstLinkPVCExtendOrRetractLeftRemote2.getPosition());
        telemetry.addData("PVCExtenderRight: ", firstLinkPVCExtendOrRetractRightRemote2.getPosition());

        if(gamepad2.left_trigger > .75)
        {
            firstLinkPVCExtendOrRetractRightRemote2.setPosition(pvcExtenderR += .1);
        }
        else if(gamepad2.left_trigger < .15)
        {
            firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoCenter);
        }

        if(gamepad2.right_trigger > .75)
        {
            firstLinkPVCExtendOrRetractLeftRemote2.setPosition(pvcExtenderL += .1);
        }
        else if(gamepad2.right_trigger < .15)
        {
            firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoCenter);
        }

    }
}
