package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/**
 * Created by Kota Baer on 4/19/2016.
 */
public class IRtester extends OpMode {
    double ran;
    String word;

    IrSeekerSensor iRSensor;

    @Override
    public void init() {
        iRSensor = hardwareMap.irSeekerSensor.get("IR");
        ran = 0;
    }

    @Override
    public void loop() {
        ran = Math.random();
        if(ran == 1)
        {
            word = "ARE AWESOME!";
        }
        else if(ran == 0)
        {
            word = "STINK!";
        }
        telemetry.addData("Something we 'angle' want to see: ", iRSensor.getAngle());
        telemetry.addData("Something we 'Strength' want to see: ", iRSensor.getStrength());
        telemetry.addData("Something we 'signal' want to see: ", iRSensor.signalDetected());
        telemetry.addData("YOU ", "" + word);
    }
}
