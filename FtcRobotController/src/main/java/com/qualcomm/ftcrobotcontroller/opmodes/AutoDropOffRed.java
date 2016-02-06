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
        drive (40);
        sleep(9000);
        drive (44);
        turnDegrees(-90);
        sleep (1000);
        drive (72);
        personArmServo.setPosition(personScoringArmPositionForward);
        sleep (2000);
        personArmServo.setPosition(personScoringArmPositionBack);
    }
}
