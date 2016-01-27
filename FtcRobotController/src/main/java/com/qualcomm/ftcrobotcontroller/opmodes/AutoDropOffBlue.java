package com.qualcomm.ftcrobotcontroller.opmodes;

        import android.os.CountDownTimer;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.LightSensor;
        import com.qualcomm.robotcore.hardware.DcMotorController;


/**
 * Created by Kota Baer on 12/15/2015.
 */
public class AutoDropOffBlue extends AutoHangBot {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        waitForStart(); //wait for start

        drive(12);
        sleep(1000);
        turnDegrees(90);
    }
}
