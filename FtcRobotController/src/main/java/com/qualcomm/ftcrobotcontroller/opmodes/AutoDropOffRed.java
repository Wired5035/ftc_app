package com.qualcomm.ftcrobotcontroller.opmodes;

import android.os.CountDownTimer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Kota Baer on 12/15/2015.
 */
public class AutoDropOffRed extends AutoHangBot {

    //Variables section:



    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        waitForStart();
        setDrivePower(.8);
        while (lightR.getLightDetected()<.45)
        {
            waitOneFullHardwareCycle();
        }
        setDrivePower(0);
    }
}
