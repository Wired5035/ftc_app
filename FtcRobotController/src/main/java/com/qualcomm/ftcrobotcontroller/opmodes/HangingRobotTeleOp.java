package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kota Baer on 11/17/2015.
 */
public class HangingRobotTeleOp extends OpMode{
    /*
    4 MOTORS FOR THE TWO SIDES
    rightSide is 2 motors powered as one
    rightSide is 2 motors powered as one
    winch is 2 motors powered as one
    teleTubExtender(telescoping tub extender) is one motor
     */
    private DcMotor rightSideMotor;
    private DcMotor leftSideMotor;
    private DcMotor winchMotor;
    private DcMotor teleTubExtenderMotor;

    /*
    ////we need six servos:////
    bucket arm
    bucket
    right tub drop
    left tub drop
    right tub crank
    left tub crank
     */
    private Servo servoBucket;
    private Servo servoBucketArm;
    private Servo servoRightTubDrop;
    private Servo servoLeftTubDrop;
    private Servo servoRightTubCrank;
    private Servo servoLeftTubCrank;

    @Override
    public void init() {
        
        rightSideMotor = hardwareMap.dcMotor.get("rightSide");
        leftSideMotor = hardwareMap.dcMotor.get("leftSide");
        winchMotor = hardwareMap.dcMotor.get("winch");
        teleTubExtenderMotor = hardwareMap.dcMotor.get("teleTubExtender");

        servoBucket = hardwareMap.servo.get("bucket");
        servoBucketArm = hardwareMap.servo.get("bucketArm");
        servoLeftTubCrank = hardwareMap.servo.get("leftTubCrank");
        servoLeftTubDrop = hardwareMap.servo.get("leftTubDrop");
        servoRightTubCrank = hardwareMap.servo.get("rightTubCrank");
        servoRightTubDrop = hardwareMap.servo.get("rightTubDrop");
    }

    @Override
    public void loop() {

    }
}
