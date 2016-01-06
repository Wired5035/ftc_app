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
 //   DcMotor hookWinchRightRemote2;
 //   DcMotor hookWinchLeftRemote2;
 //  DcMotor telescopeExtendMotorRemote2;
    DcMotor platformTilt;
    DcMotor tapeExtendRetract;
    //Servos
 //   Servo bucketServoRemote1;
 //   Servo bucketArmServoRemote1;
 //   Servo telescopingPVCDropRightRemote2;
 //   Servo telescopingPVCDropLeftRemote2;
 //   Servo firstLinkPVCExtendOrRetractRightRemote2;
 //   Servo firstLinkPVCExtendOrRetractLeftRemote2;
    Servo personArmServo;
    Servo ziplinerArmServo;
    //Sensors
    LightSensor lightR;
    LightSensor lightL;



    @Override
    public void runOpMode() throws InterruptedException {

        //setup all Motors and servos for config file on phone
        motorRightRemote1 = hardwareMap.dcMotor.get("right");  //FORWARD
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");  //REVERSED
        motorLeftRemote1.setDirection(DcMotor.Direction.REVERSE);
        motorRightRemote1.setDirection(DcMotor.Direction.FORWARD);
        motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    /*    hookWinchRightRemote2=hardwareMap.dcMotor.get("RightHook");  //REVERSED
        hookWinchLeftRemote2=hardwareMap.dcMotor.get("LeftHook");
        hookWinchRightRemote2.setDirection(DcMotor.Direction.REVERSE);
        telescopeExtendMotorRemote2=hardwareMap.dcMotor.get("TelescopePVC");
        bucketServoRemote1=hardwareMap.servo.get("Bucket");
        bucketArmServoRemote1=hardwareMap.servo.get("Arm");
        telescopingPVCDropRightRemote2=hardwareMap.servo.get("PVCDropRight");
        telescopingPVCDropLeftRemote2=hardwareMap.servo.get("PVCDropLeft");
        firstLinkPVCExtendOrRetractRightRemote2=hardwareMap.servo.get("ServoExtenderRight");
        firstLinkPVCExtendOrRetractLeftRemote2=hardwareMap.servo.get("ServoExtenderLeft");
    */  lightL = hardwareMap.lightSensor.get("lightL");
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

    public void setDrivePower (double power)
    {
        motorRightRemote1.setPower(power);
        motorLeftRemote1.setPower(power);
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
}
