package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Owner on 1/24/2017.
 */
@TeleOp(name = "TeleOp50352 JUST TANK DRIVE!!!")
public class DriveCode extends OpMode{
    Hardware5035 robot = new Hardware5035();

    @Override
    public void init() {
        robot.init(hardwareMap, null);
    }

    @Override
    public void loop() {

            //Forward Drive
            if (null != robot.leftMotor) robot.leftMotor.setPower(gamepad1.left_stick_y);
            if (null != robot.rightMotor) robot.rightMotor.setPower(gamepad1.right_stick_y);
    }


}
