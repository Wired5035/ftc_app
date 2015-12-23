package com.qualcomm.ftcrobotcontroller.opmodes;

import android.os.CountDownTimer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Kota Baer on 12/15/2015.
 */
public class AutoDropOffRed extends LinearOpMode {

    //Variables section:
    int timeLeft = 10;
    ElapsedTime CountingUp = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        CountingUp.reset();
        telemetry.addData("Status: ", "WAITING.....");
        waitForStart();
        telemetry.addData("CountUp", ((float) CountingUp.time()));
        sleep(2000);
        CountingUp.reset();
        telemetry.addData("Status: ", "STARTING.....");
        telemetry.addData("CountUp", ((int) CountingUp.time()));
        telemetry.addData("TimeLeft: ", timeLeft);
        sleep(1000);
    }
}
