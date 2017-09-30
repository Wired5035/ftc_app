package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Owner on 9/30/2017.
 */

public class MechanumWheelsTeleOp2k17 extends OpMode {
    testHardware5035 robot = new testHardware5035();

    @Override
    public void init() {
        robot.init(hardwareMap, null);
    }

    @Override
    public void loop() {

        //motor power set for all four motors for going foreward/backward.
        if (null != robot.leftMotorFront) robot.leftMotorFront.setPower(gamepad1.left_stick_y);
        if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(gamepad1.left_stick_y);
        if (null != robot.rightMotorFront) robot.rightMotorFront.setPower(-gamepad1.left_stick_y);
        if (null != robot.rightMotorBack) robot.leftMotorBack.setPower(-gamepad1.left_stick_y);
        //code for going directly side to side
        if (null != robot.leftMotorFront) robot.leftMotorFront.setPower(-gamepad1.left_stick_x);
        if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(-gamepad1.left_stick_x);
        if (null != robot.rightMotorFront) robot.rightMotorFront.setPower(-gamepad1.left_stick_x);
        if (null != robot.rightMotorBack) robot.leftMotorBack.setPower(-gamepad1.left_stick_x);
        //turning left
        if (null != robot.leftMotorFront) robot.leftMotorFront.setPower(gamepad1.right_stick_x);
        if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(gamepad1.right_stick_x);
        if (null != robot.rightMotorFront) robot.rightMotorFront.setPower(-gamepad1.right_stick_x);
        if (null != robot.rightMotorBack) robot.rightMotorBack.setPower(-gamepad1.right_stick_x);
        //turning right
        if (null != robot.leftMotorFront) robot.leftMotorFront.setPower(-gamepad1.right_stick_x);
        if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(-gamepad1.right_stick_x);
        if (null != robot.rightMotorFront) robot.rightMotorFront.setPower(gamepad1.right_stick_x);
        if (null != robot.rightMotorBack) robot.rightMotorBack.setPower(gamepad1.right_stick_x);


    }

}
