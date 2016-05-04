package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.LightSensor;

/**
 * Created by Kota Baer on 4/19/2016.
 */
public class ODS extends OpMode {
    int face = 0;
    OpticalDistanceSensor ODSS;
    @Override
    public void init() {
        face = 0;
        ODSS = hardwareMap.opticalDistanceSensor.get("ODS");
    }

    @Override
    public void loop() {
        telemetry.addData("your face is number: ", face);
        telemetry.addData("ODDS: ", ODSS.getLightDetected());
        telemetry.addData("ODDS raw: ", ODSS.getLightDetectedRaw());
        telemetry.addData("ODDS name: ", ODSS.getDeviceName());
    }
}
