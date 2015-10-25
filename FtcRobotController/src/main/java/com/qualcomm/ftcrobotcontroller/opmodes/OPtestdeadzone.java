package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Kota Baer on 10/20/2015.
 */
public class OPtestdeadzone extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("deadzone", String.format("%.2f %.2f", gamepad1.left_stick_y , gamepad1.left_stick_x));
    }
}
