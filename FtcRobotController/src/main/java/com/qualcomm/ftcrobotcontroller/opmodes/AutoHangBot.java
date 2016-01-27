package com.qualcomm.ftcrobotcontroller.opmodes;

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
    static final float ziplinerArmUp =0f;
    static final float ziplinerArmDown =1f;

    //Motors
    DcMotor motorRightRemote1;
    DcMotor motorLeftRemote1;
    DcMotor platformTilt;
    DcMotor tapeExtendRetractR;
    DcMotor tapeExtendRetractL;
    Servo personArmServo;
    Servo ziplinerArmServo;
    LightSensor lightR;
    LightSensor lightL;




    @Override
    public void runOpMode() throws InterruptedException {

        //setup all Motors and servos for config file on phone
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");  //REVERSED
        motorRightRemote1 = hardwareMap.dcMotor.get("right");  //FORWARD
        motorLeftRemote1.setDirection(DcMotor.Direction.FORWARD);
        motorRightRemote1.setDirection(DcMotor.Direction.REVERSE);
        motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        lightL = hardwareMap.lightSensor.get("lightL");
        lightR = hardwareMap.lightSensor.get("lightR");
        motorLeftRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        platformTilt=hardwareMap.dcMotor.get("platform");
        tapeExtendRetractR=hardwareMap.dcMotor.get("tapeR");
        tapeExtendRetractL=hardwareMap.dcMotor.get("tapeL");
        personArmServo=hardwareMap.servo.get("person");
        ziplinerArmServo=hardwareMap.servo.get("zipline");

        CountingUp.reset();
       personArmServo.setPosition(personScoringArmPositionBack);
        ziplinerArmServo.setPosition(ziplinerArmUp);

    }

    public void Reset_All_Encoders () throws InterruptedException
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
        motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        waitOneFullHardwareCycle();
    }

    public double getPowerForTicks (int ticksToGo)
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
            return .30;
        }
        if (ticksToGo > 120 * multi)
        {
            return .2;
        }
        if (ticksToGo >= 0 * multi)
        {
            return .2;
        }
        if (ticksToGo >= - 120 * multi)
        {
            return -.2;
        }
        if (ticksToGo > -520 * multi)
        {
            return -.2;
        }
        if (ticksToGo > -720 * multi)
        {
            return -.30;
        }
        if(ticksToGo > -1440 * multi)
        {
            return -.75;
        }
        return -1;
    }

    public void setDrivePower (double power)
    {
        motorRightRemote1.setPower(power);
        motorLeftRemote1.setPower(power);
    }

    public int getTicksForTurn (double degrees)
    {
        int ticks = (int)(14 * Math.abs(degrees));
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

        int rightTicksToGo = (tickR - motorRightRemote1.getCurrentPosition()) * basePowerR;
        int leftTicksToGo = (tickL - motorLeftRemote1.getCurrentPosition()) * basePowerL;

        while(rightTicksToGo > 0 || leftTicksToGo > 0) {
            double LPower = getPowerForTicks(leftTicksToGo);
            if (leftTicksToGo > 0)
            {
                motorLeftRemote1.setPower(LPower * basePowerL);
            } else {
                motorLeftRemote1.setPower(0);
            }

            double RPower = getPowerForTicks(rightTicksToGo);
            if (rightTicksToGo > 0)
            {
                motorRightRemote1.setPower(RPower * basePowerR);
            } else {
                motorRightRemote1.setPower(0);
            }

            waitOneFullHardwareCycle();

            rightTicksToGo = (tickR - motorRightRemote1.getCurrentPosition()) * basePowerR;
            leftTicksToGo = (tickL - motorLeftRemote1.getCurrentPosition()) * basePowerL;

            telemetry.addData("drive count", String.format("count=%d right=%d left=%d rPower=%.2f lPower=%.2f", count, rightTicksToGo, leftTicksToGo, RPower, LPower));
            ++count;
        }
        telemetry.addData("drive end", String.format("tickR=%d tickL=%d motorR=%d motorL=%d", tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
        setDrivePower(0);
    }

    public void drive (double inches) throws InterruptedException {
//12.6
        Reset_All_Encoders();
        int tickR = inchToTickConverter(inches);
        int tickL = inchToTickConverter(inches);

        //telemetry.addData("drive start", String.format("tickR=%d tickL=%d motorR=%d motorL=%d", tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
        int count = 0;
        while(motorRightRemote1.getCurrentPosition() < tickR || motorLeftRemote1.getCurrentPosition() < tickL) {
            motorLeftRemote1.setPower(getPowerForTicks(tickL - motorLeftRemote1.getCurrentPosition()));
            motorRightRemote1.setPower(getPowerForTicks(tickR - motorRightRemote1.getCurrentPosition()));
            waitOneFullHardwareCycle();
            //telemetry.addData("drive count", String.format("count= %d tickR=%d tickL=%d motorR=%d motorL=%d", count, tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
            ++count;
        }
        //telemetry.addData("drive end", String.format("tickR=%d tickL=%d motorR=%d motorL=%d", tickR, tickL, motorRightRemote1.getCurrentPosition(), motorLeftRemote1.getCurrentPosition()));
        setDrivePower(0);
    }
}
