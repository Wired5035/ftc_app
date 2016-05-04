package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.backup.RestoreObserver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Kota Baer on 12/15/2015.
 */
public class AutoHangBot extends LinearOpMode {

    //Variables section:
    int timeLeft = 30;
    ElapsedTime CountingUp = new ElapsedTime();
    //Arm Positions
    static final float personScoringArmPositionBack=0f;
    static final float personScoringArmPositionForward =1f;
    static final float leftZiplineInit =.06f;
    static final float rightZiplineInit =.97f;

    //Motors
    DcMotor motorRightRemote1;
    DcMotor motorLeftRemote1;
    DcMotor platformTilt;
    DcMotor tapeExtendRetractR;
    DcMotor tapeExtendRetractL;
    Servo personArmServo;
    Servo ziplinerArmServoRight;
    Servo ziplinerArmServoLeft;
    LightSensor lightR;
    LightSensor lightL;




    @Override
    public void runOpMode() throws InterruptedException {

        //setup all Motors and servos for config file on phone
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");  //RawWwaEVERSED
        motorRightRemote1 = hardwareMap.dcMotor.get("right");  //FORWARD
        motorLeftRemote1.setDirection(DcMotor.Direction.FORWARD);
        motorRightRemote1.setDirection(DcMotor.Direction.REVERSE);
//        lightL = hardwareMap.lightSensor.get("lightL");
//        lightR = hardwareMap.lightSensor.get("lightR");
        platformTilt=hardwareMap.dcMotor.get("platform");
        tapeExtendRetractR=hardwareMap.dcMotor.get("tapeR");
        tapeExtendRetractL=hardwareMap.dcMotor.get("tapeL");
        personArmServo=hardwareMap.servo.get("person");
        ziplinerArmServoRight =hardwareMap.servo.get("ziplineR");
        ziplinerArmServoLeft =hardwareMap.servo.get("ziplineL");
        Reset_All_Encoders();

        CountingUp.reset();
       personArmServo.setPosition(personScoringArmPositionBack);
        if (null!= ziplinerArmServoRight)        ziplinerArmServoRight.setPosition(rightZiplineInit);
        if (null!= ziplinerArmServoLeft)         ziplinerArmServoLeft.setPosition(leftZiplineInit);
    }

    public void Reset_All_Encoders () throws InterruptedException
    {
        while (motorRightRemote1.getCurrentPosition() != 0 || motorLeftRemote1.getCurrentPosition() != 0)
        {
            if(motorRightRemote1 != null)
            {
                motorRightRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            }
            if(motorLeftRemote1 != null)
            {
                motorLeftRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            }
            waitOneFullHardwareCycle();

        }


        motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        waitOneFullHardwareCycle();
    }

    public double getPowerForTicksfordrive (int ticksToGo)
    {
        int multi = 3;
        if (ticksToGo > 1440 * multi)
        {
            return .2;
        }
        if (ticksToGo > 720 * multi)
        {
            return .175;
        }
        if (ticksToGo > 520 * multi)
        {
            return .175;
        }
        if (ticksToGo > 120 * multi)
        {
            return .1555;
        }
        if (ticksToGo >= 0 * multi)
        {
            return .150;
        }
        return 0;
    }

    public double getPowerForTicksforturn (int ticksToGo)
    {
        int multi = 3;
        if (ticksToGo > 1440 * multi)
        {
            return 1;
        }
        if (ticksToGo > 720 * multi)
        {
            return .75;
        }
        if (ticksToGo > 520 * multi)
        {
            return .5;
        }
        if (ticksToGo > 120 * multi)
        {
            return .4;
        }
        if (ticksToGo >= 0 * multi)
        {
            return .2;
        }
        return 0;
    }

    public void setDrivePower (double power)
    {
        motorRightRemote1.setPower(power);
        motorLeftRemote1.setPower(power);
    }

    public int getTicksForTurn (double degrees)
    {
        int ticks = (int)(11.78 * Math.abs(degrees));
        return ticks;
    }

    public int inchToTickConverter (double inches)
    {
        return (int) (65.482 * inches);
    }


    public void turnDegrees (double degrees) throws InterruptedException {
        Reset_All_Encoders();
        int tickR = getTicksForTurn(degrees);
        int tickL = getTicksForTurn(degrees);
        int basePowerL = 0;
        int basePowerR = 0;
        if (degrees < 0)   // - makes the robot turn left + makes the robot turn right
        {
            basePowerL = -1;
            basePowerR = 1;
        } else if (degrees >= 0) {
            basePowerL = 1;
            basePowerR = -1;
        }

        //tickR *= basePowerR;
        //tickL *= basePowerL;

        //telemetry.addData("drive start", String.format("tickR=%d tickL=%d motorR=%d motorL=%d", tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
        int count = 0;

        int rightTicksToGo = (tickR - motorRightRemote1.getCurrentPosition() * basePowerR);
        int leftTicksToGo = (tickL - motorLeftRemote1.getCurrentPosition() * basePowerL);

        while(rightTicksToGo > 0 || leftTicksToGo > 0) {
            double LPower = getPowerForTicksforturn(leftTicksToGo);
            if (leftTicksToGo > 0)
            {
                motorLeftRemote1.setPower(LPower * basePowerL);
            } else {
                motorLeftRemote1.setPower(0);
            }

            double RPower = getPowerForTicksforturn(rightTicksToGo);
            if (rightTicksToGo > 0)
            {
                motorRightRemote1.setPower(RPower * basePowerR);
            } else {
                motorRightRemote1.setPower(0);
            }

            waitOneFullHardwareCycle();

            rightTicksToGo = (tickR - motorRightRemote1.getCurrentPosition() * basePowerR);
            leftTicksToGo = (tickL - motorLeftRemote1.getCurrentPosition() * basePowerL);

            telemetry.addData("drive count", String.format("count=%d right=%d left=%d rPower=%.2f lPower=%.2f", count, rightTicksToGo, leftTicksToGo, RPower, LPower));
            ++count;
        }
        telemetry.addData("drive end", String.format("tickR=%d tickL=%d motorR=%d motorL=%d", tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
        setDrivePower(0);
    }

    public void drive (double inches) throws InterruptedException {
//12.6

        int multier = 0;
        Reset_All_Encoders();
        int tickR = inchToTickConverter(inches);
        int tickL = inchToTickConverter(inches);

        //telemetry.addData("drive start", String.format("tickR=%d tickL=%d motorR=%d motorL=%d", tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
        int count = 0;
        while(motorRightRemote1.getCurrentPosition() < tickR || motorLeftRemote1.getCurrentPosition() < tickL) {
            motorLeftRemote1.setPower(getPowerForTicksfordrive(tickL - motorLeftRemote1.getCurrentPosition()));
            motorRightRemote1.setPower(getPowerForTicksfordrive(tickR - motorRightRemote1.getCurrentPosition()));
            waitOneFullHardwareCycle();
            //telemetry.addData("drive count", String.format("count= %d tickR=%d tickL=%d motorR=%d motorL=%d", count, tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
            ++count;
        }
        //telemetry.addData("drive end", String.format("tickR=%d tickL=%d motorR=%d motorL=%d", tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
        setDrivePower(0);
    }
}
