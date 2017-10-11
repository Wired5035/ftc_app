package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Owner on 9/30/2017.
 */
@TeleOp(name = "TeleOpMechanumWheels")
public class MechanumWheelsTeleOp2k17 extends OpMode {
    testHardware5035 robot = new testHardware5035();

    @Override
    public void init() {
        robot.init(hardwareMap, null);
    }

    @Override
    public void loop() {


        final double rightfrontpower = Range.clip(-gamepad1.left_stick_y +gamepad1.left_stick_x -gamepad1.right_stick_x, -1, 1);
        final double rightbackpower = Range.clip(gamepad1.left_stick_y +gamepad1.left_stick_x -gamepad1.right_stick_x, -1, 1);
        final double leftfrontpower = Range.clip(-gamepad1.left_stick_y -gamepad1.left_stick_x -gamepad1.right_stick_x, -1, 1);
        final double leftbackpower = Range.clip(gamepad1.left_stick_y -gamepad1.left_stick_x -gamepad1.right_stick_x, -1, 1);


        robot.rightMotorFront.setPower(rightfrontpower);
        robot.rightMotorBack.setPower(rightbackpower);
        robot.leftMotorFront.setPower(leftfrontpower);
        robot.leftMotorBack.setPower(leftbackpower);

        if(false) {
            /*
            double r = Math.hypot(gamepad1.right_stick_y, gamepad1.right_stick_x);
            double robotAngle = Math.atan2(gamepad1.right_stick_x, gamepad1.right_stick_y) - Math.PI / 4;
            double rightX = gamepad1.left_stick_x;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            robot.leftMotorFront.setPower(v1);
            robot.rightMotorFront.setPower(v2);
            robot.leftMotorBack.setPower(v3);
            robot.rightMotorBack.setPower(v4);
            */
        }

        if (false) {
            //motor power set for all four motors for going foreward/backward.
            if (null != robot.leftMotorFront) robot.leftMotorFront.setPower(gamepad1.left_stick_y);
            if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(gamepad1.left_stick_y);
            if (null != robot.rightMotorFront)
                robot.rightMotorFront.setPower(-gamepad1.left_stick_y);
            if (null != robot.rightMotorBack) robot.rightMotorBack.setPower(-gamepad1.left_stick_y);
            //code for going directly side to side
            if (null != robot.leftMotorFront) robot.leftMotorFront.setPower(-gamepad1.left_stick_x);
            if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(gamepad1.left_stick_x);
            if (null != robot.rightMotorFront)
                robot.rightMotorFront.setPower(-gamepad1.left_stick_x);
            if (null != robot.rightMotorBack) robot.rightMotorBack.setPower(gamepad1.left_stick_x);
            //turning left
            if (null != robot.leftMotorFront) robot.leftMotorFront.setPower(gamepad1.right_stick_x);
            if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(gamepad1.right_stick_x);
            if (null != robot.rightMotorFront)
                robot.rightMotorFront.setPower(-gamepad1.right_stick_x);
            if (null != robot.rightMotorBack)
                robot.rightMotorBack.setPower(-gamepad1.right_stick_x);
            //turning right
            if (null != robot.leftMotorFront)
                robot.leftMotorFront.setPower(-gamepad1.right_stick_x);
            if (null != robot.leftMotorBack) robot.leftMotorBack.setPower(-gamepad1.right_stick_x);
            if (null != robot.rightMotorFront)
                robot.rightMotorFront.setPower(gamepad1.right_stick_x);
            if (null != robot.rightMotorBack) robot.rightMotorBack.setPower(gamepad1.right_stick_x);
        }

    }

}
