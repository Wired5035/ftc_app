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
    static final float personScoringArmPositionBack=1f;
    static final float personScoringArmPositionForward =0f;
    static final float ziplinerArmUp =0f;
    static final float ziplinerArmDown =1f;

    //Motors
    DcMotor motorRightRemote1;
    DcMotor motorLeftRemote1;
    DcMotor platformTilt;
    DcMotor tapeExtendRetract;
    Servo personArmServo;
    Servo ziplinerArmServo;
    LightSensor lightR;
    LightSensor lightL;




    @Override
    public void runOpMode() throws InterruptedException {

        //setup all Motors and servos for config file on phone
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");  //REVERSED
        motorRightRemote1 = hardwareMap.dcMotor.get("right");  //FORWARD
        motorLeftRemote1.setDirection(DcMotor.Direction.REVERSE);
        motorRightRemote1.setDirection(DcMotor.Direction.FORWARD);
        motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        lightL = hardwareMap.lightSensor.get("lightL");
        lightR = hardwareMap.lightSensor.get("lightR");
        motorLeftRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        platformTilt=hardwareMap.dcMotor.get("platform");
        tapeExtendRetract=hardwareMap.dcMotor.get("tape");
        personArmServo=hardwareMap.servo.get("dump");
        ziplinerArmServo=hardwareMap.servo.get("zipline");

        CountingUp.reset();
       personArmServo.setPosition(personScoringArmPositionBack);
        ziplinerArmServo.setPosition(ziplinerArmUp);

    }
    public void Motor_Right_Encoder_Reset ()
    {
        if(motorLeftRemote1 != null)
        {
            motorLeftRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }
    public  void Motor_Left_Encoder_Reset ()
    {
        if(motorRightRemote1 != null)
        {
            motorRightRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }

    public void Reset_All_Encoders () throws InterruptedException
    {
        Motor_Left_Encoder_Reset();
        Motor_Right_Encoder_Reset();
        waitOneFullHardwareCycle();
    }

    public double getPowerForTicks (int ticksToGo)
    {
        if (ticksToGo > 1440)
        {
            return 1;
        }
        if (ticksToGo > 720)
        {
            return .75;
        }
        if (ticksToGo > 520)
        {
            return .50;
        }
        if (ticksToGo > 120)
        {
            return .25;
        }
        if (ticksToGo >= 0)
        {
            return .10;
        }
        if (ticksToGo >= - 120)
        {
            return -.10;
        }
        if (ticksToGo > -520)
        {
            return -.25;
        }
        if (ticksToGo > -720)
        {
            return -.50;
        }
        if(ticksToGo > -1440)
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

    public void turnLeft90Degrees () throws InterruptedException {
        motorRightRemote1.setPower(1);
        motorLeftRemote1.setPower(-1);
        int startingRightEncoder=motorRightRemote1.getCurrentPosition();
                int startingLeftEncoder=motorLeftRemote1.getCurrentPosition();
        int encoderTicsTraveled=motorRightRemote1.getCurrentPosition()-startingRightEncoder+startingLeftEncoder-motorLeftRemote1.getCurrentPosition();
        while (encoderTicsTraveled<1000){
            waitOneFullHardwareCycle();
            encoderTicsTraveled=motorRightRemote1.getCurrentPosition()-startingRightEncoder+startingLeftEncoder-motorLeftRemote1.getCurrentPosition();

        }
        setDrivePower(0);
    }

    public void turnDegrees (double degrees) throws InterruptedException {
        int basePowerL = 0;
        int basePowerR = 0;
        Reset_All_Encoders();
        if (degrees < 0)   // - makes the robot turn left + makes the robot turn right
        {
            basePowerL = -1;
            basePowerR = 1;
        } else if (degrees >= 0) {
            basePowerL = 1;
            basePowerR = -1;
        }
        int tickR = getTicksForTurn(degrees) * basePowerR;
        int tickL = getTicksForTurn(degrees) * basePowerL;
        //TREDOUBUR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        while(motorRightRemote1.getCurrentPosition() < tickR && motorLeftRemote1.getCurrentPosition() < tickL) {
            telemetry.addData("phase", "#3");
            motorLeftRemote1.setPower(getPowerForTicks(tickL - motorLeftRemote1.getCurrentPosition()));
            motorRightRemote1.setPower(getPowerForTicks(tickR - motorRightRemote1.getCurrentPosition()));
            waitOneFullHardwareCycle();
        }
        setDrivePower(0);
//        int startingRightEncoder=motorRightRemote1.getCurrentPosition();
//        int startingLeftEncoder=motorLeftRemote1.getCurrentPosition();
//        int encoderTicsTraveled=motorRightRemote1.getCurrentPosition()-startingRightEncoder+startingLeftEncoder-motorLeftRemote1.getCurrentPosition();
//        while (encoderTicsTraveled<1000){
//            waitOneFullHardwareCycle();
//            encoderTicsTraveled=motorRightRemote1.getCurrentPosition()-startingRightEncoder+startingLeftEncoder-motorLeftRemote1.getCurrentPosition();
//
//        }
//        setDrivePower(0);
    }
}
