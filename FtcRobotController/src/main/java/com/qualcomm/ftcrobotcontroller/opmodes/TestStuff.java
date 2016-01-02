package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Michael Carter on 1/2/2016.
 */


public class TestStuff extends OpMode {

    DcMotor testMotor;
    TouchSensor touchSensor;

    @Override
    public void init() {

        testMotor = hardwareMap.dcMotor.get("test");
        touchSensor = hardwareMap.touchSensor.get("touch");
    }

    @Override
    public void init_loop() {
        if (touchSensor.isPressed())
        {
            testMotor.setPower(0);
            testMotor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
        else
        {
            testMotor.setPower(-0.05);
        }
    }

    @Override
    public void start() {
        testMotor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    @Override
    public void loop() {

        telemetry.addData("Test Encoder: ", testMotor.getCurrentPosition());
        telemetry.addData("Touch Sensor: ", touchSensor.getValue());

        float motorPower = -gamepad1.left_stick_y;
        if (motorPower >= 0 || !touchSensor.isPressed()) {
            testMotor.setPower(motorPower);
        }
    }
}
