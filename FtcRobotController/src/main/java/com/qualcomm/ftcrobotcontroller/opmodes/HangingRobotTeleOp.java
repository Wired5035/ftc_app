package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;

/**
 * Created by Kota Baer on 11/17/2015.
 */
public class HangingRobotTeleOp extends OpMode{
    //Servo Arm Positions
    static final float personScoringArmPositionBack=1f;
    static final float personScoringArmPositionForward =0f;
    static final float ziplinerArmUp =0f;
    static final float ziplinerArmDown =1f;


    boolean tankF; //to change the drive direction
    boolean slowD;
    float CurrentArmPosition = 1;
    double lastLoopTime;

    //Motors
    DcMotor motorRightRemote1;
    DcMotor motorLeftRemote1;
    DcMotor platformTilt;
    DcMotor tapeExtendRetractR;
    DcMotor tapeExtendRetractL;
    DcMotor fourWheeler;
    Servo personArmServo;
    Servo ziplinerArmServo;
    LightSensor lightR;
    LightSensor lightL;



    float pvcExtenderL = 0.550f;
    float pvcExtenderR = 0.5f;
    float servoCenter = 0.525f;  //for continues rotation servos to stop them from spinning
    private boolean triggerflag;
    private boolean ATV;
    private float servoLeftTension=.03f;
    private float servoRightTension=.06f;
    private boolean sticks_pushed;
    @Override
    public void init() {

        //setup all Motors and servos for config file on phone
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");  //REVERSED
        motorRightRemote1 = hardwareMap.dcMotor.get("right");  //FORWARD

        if (null!=motorLeftRemote1) motorLeftRemote1.setDirection(DcMotor.Direction.REVERSE);

        if (null!=motorRightRemote1)  motorRightRemote1.setDirection(DcMotor.Direction.FORWARD);
        if (null!=motorLeftRemote1)   motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        if (null!=motorRightRemote1)    motorRightRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        lightL = hardwareMap.lightSensor.get("lightL");
        lightR = hardwareMap.lightSensor.get("lightR");
        if (null!=motorLeftRemote1)   motorLeftRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        if (null!=motorRightRemote1)      motorRightRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        platformTilt=hardwareMap.dcMotor.get("platform");
        tapeExtendRetractR=hardwareMap.dcMotor.get("tapeR");
        tapeExtendRetractL=hardwareMap.dcMotor.get("tapeL");
        fourWheeler=hardwareMap.dcMotor.get("atv");
        personArmServo=hardwareMap.servo.get("person");
        ziplinerArmServo=hardwareMap.servo.get("zipline");
       //initialise tank drive to forward
        tankF = true;
        //boolean slowD = false; // slow drive set to off
    }

    @Override
    public void start() {
        super.start();
        if (null!=personArmServo)        personArmServo.setPosition(personScoringArmPositionBack);
        if (null!=ziplinerArmServo)        ziplinerArmServo.setPosition(ziplinerArmUp);

        //initialise servo positions on start
       boolean slowD = false;
    }

    @Override
    public void loop() {

        telemetry.addData("start", "started");

        if(gamepad1.dpad_left)
        {
            slowD = false;
        }
        else if(gamepad1.dpad_right)
        {
            slowD = true;
        }


/*        if(gamepad1.left_stick_button && gamepad1.right_stick_button)
        {
            if (!sticks_pushed)

            {
                sticks_pushed=true;


                tankF = false;
                telemetry.addData("TankDive: ", "REVERSE");
            }
        }
        else
        {
            sticks_pushed=false;
        }

*/

        if(gamepad2.y)
        {
            if (null!=personArmServo)  personArmServo.setPosition(personScoringArmPositionForward);

        }

        if(gamepad2.a)
        {
            if (null!=personArmServo)   personArmServo.setPosition(personScoringArmPositionBack);
            //popcorn = YAY!!!!!!!!!!
        }





        if (null!=tapeExtendRetractR)
        {
            tapeExtendRetractR.setPower(-gamepad2.left_stick_y);
            if(gamepad2.right_stick_y > 0)
            {
                if (null!=fourWheeler && gamepad2.left_stick_y < 1) fourWheeler.setPower((gamepad2.right_stick_y + -0.10));
            }
        }


        if (null!=tapeExtendRetractL)
        {
            tapeExtendRetractL.setPower(-gamepad2.right_stick_y);
            if(gamepad2.left_stick_y < 0)
            {
                if (null!=fourWheeler && gamepad2.left_stick_y > -1) fourWheeler.setPower((gamepad2.left_stick_y + 0.10));
            }
        }



        if(gamepad2.dpad_up)
        {
            if (null!=platformTilt)      platformTilt.setPower(.05);
        }
        else if(gamepad2.dpad_down)
        {
            if (null!=platformTilt)      platformTilt.setPower(-.05);
        }

        //TANK DRIVE
        if(tankF)//CHANGES OUR DIRECTION BASED ON IF "tankF" IS TRUE OR FALSE
        {
            if(slowD) //CHANGES OUR SPEED BASED ON "slowD"
            {
                //forward tank drive
                if (null!=motorLeftRemote1)          motorLeftRemote1.setPower(gamepad1.left_stick_y / 2);
                if (null!=motorRightRemote1)     motorRightRemote1.setPower(gamepad1.right_stick_y / 2);
            }
            else
            {
                //forward tank drive
                if (null!=motorLeftRemote1)             motorLeftRemote1.setPower(gamepad1.left_stick_y);
                if (null!=motorRightRemote1)             motorRightRemote1.setPower(gamepad1.right_stick_y);
            }
        }
        else if(!tankF)
        {
            if(slowD)//CHANGES OUR SPEED BASED ON "slowD"
            {
                //backward tank drive
                if (null!=motorLeftRemote1)       motorLeftRemote1.setPower(-gamepad1.right_stick_y / 2);
                if (null!=motorRightRemote1)          motorRightRemote1.setPower(-gamepad1.left_stick_y / 2);
            }
            else
            {
                //backward tank drive
                if (null!=motorLeftRemote1)          motorLeftRemote1.setPower(-gamepad1.right_stick_y);
                if (null!=motorRightRemote1)    motorRightRemote1.setPower(-gamepad1.left_stick_y);
            }

        }
//        if(gamepad1.dpad_up)
//        {
//            double newposition=ziplinerArmServo.getPosition()+.01;
//                    if (newposition<=1)
//                    {
//                        if (null!=ziplinerArmServo)              ziplinerArmServo.setPosition(newposition);
//                    }
//        }
//        else if(gamepad1.dpad_down)
//        {
//            double newposition=ziplinerArmServo.getPosition()-.01;
//            if (newposition>=0)
//            {
//                if (null!=ziplinerArmServo)           ziplinerArmServo.setPosition(newposition);
//            }
//        }
        //THE END
    }
}
