package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;

/**
 * Created by Kota Baer on 11/17/2015.
 */
public class HangingRobotTeleOp extends OpMode{
    //Bucket Positions
    static final float pickup = 1f;    //for picking up game
    static final float holding = 1f;
    static final float score = 0.34f;  //getting ready to score
    static final float dump = 0f;   //dumping
    //Arm Positions
    static final float pickuparm =1f;
    static final float holdingarm =1f;
    static final float scorearm =0f;
    static final float dumparm =0f;

    boolean tankF; //to change the drive direction
    boolean slowD;
    float CurrentArmPosition = 1;
    double lastLoopTime;
    /*
    5 MOTORS FOR THE TWO SIDES
    rightSide is 2 motors powered as one
    rightSide is 2 motors powered as one
    winch is 2 motors
    teleTubExtender(telescoping tub extender) is one motor
    ////we need six servos:////
    bucket arm
    bucket
    right tub drop
    left tub drop
    right tub crank
    left tub crank
     */
    //Motors
    DcMotor motorRightRemote1;
    DcMotor motorLeftRemote1;
    DcMotor hookWinchRightRemote2;
    DcMotor hookWinchLeftRemote2;
    DcMotor telescopeExtendMotorRemote2;
    //Servos
    Servo bucketServoRemote1;
    Servo bucketArmServoRemote1;
    Servo telescopingPVCDropRightRemote2;
    Servo telescopingPVCDropLeftRemote2;
    Servo firstLinkPVCExtendOrRetractRightRemote2;
    Servo firstLinkPVCExtendOrRetractLeftRemote2;
    //Positions for servos to be set to
    float pvcExtenderL = 0.550f;
    float pvcExtenderR = 0.5f;
    float servoCenter = 0.525f;  //for continues rotation servos to stop them from spinning
    private boolean triggerflag;
    private float servoLeftTension=.03f;
    private float servoRightTension=.06f;

    @Override
    public void init() {

        //setup all Motors and servos for config file on phone
        motorRightRemote1 = hardwareMap.dcMotor.get("right");  //FORWARD
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");  //REVERSED
        motorLeftRemote1.setDirection(DcMotor.Direction.REVERSE);
        motorRightRemote1.setDirection(DcMotor.Direction.FORWARD);
        hookWinchRightRemote2=hardwareMap.dcMotor.get("RightHook");  //REVERSED
        hookWinchLeftRemote2=hardwareMap.dcMotor.get("LeftHook");
        hookWinchRightRemote2.setDirection(DcMotor.Direction.REVERSE);
        telescopeExtendMotorRemote2=hardwareMap.dcMotor.get("TelescopePVC");
        bucketServoRemote1=hardwareMap.servo.get("Bucket");
        bucketArmServoRemote1=hardwareMap.servo.get("Arm");
        telescopingPVCDropRightRemote2=hardwareMap.servo.get("PVCDropRight");
        telescopingPVCDropLeftRemote2=hardwareMap.servo.get("PVCDropLeft");
        firstLinkPVCExtendOrRetractRightRemote2=hardwareMap.servo.get("ServoExtenderRight");
        firstLinkPVCExtendOrRetractLeftRemote2=hardwareMap.servo.get("ServoExtenderLeft");

        //initialise servo positions
        bucketServoRemote1.setPosition(pickup);
        bucketArmServoRemote1.setPosition(CurrentArmPosition);
        telescopingPVCDropRightRemote2.setPosition(0f);
        telescopingPVCDropLeftRemote2.setPosition(1f);
        firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoCenter);
        firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoCenter);
        //initialise tank drive to forward
        tankF = true;
        //boolean slowD = false; // slow drive set to off
    }

    @Override
    public void start() {
        super.start();
        //initialise servo positions on start
        bucketServoRemote1.setPosition(1);
        bucketArmServoRemote1.setPosition(1);
        telescopingPVCDropRightRemote2.setPosition(0f);
        telescopingPVCDropLeftRemote2.setPosition(1f);
        firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoCenter);
        firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoCenter);
        boolean slowD = false;
    }

    @Override
    public void loop() {

        double deltaTime = time - lastLoopTime;

        if(gamepad1.dpad_left)
        {
            slowD = false;
        }
        else if(gamepad1.dpad_right)
        {
            slowD = true;
        }

        if(gamepad2.left_bumper && gamepad2.right_bumper && gamepad2.guide)
        {
            //Drop The hooks
            telescopingPVCDropRightRemote2.setPosition(1);
            telescopingPVCDropLeftRemote2.setPosition(0);
        }
        

        if(gamepad2.left_trigger > .50) //continues rotation servos
        {
            //triggerflag=true;

            if(gamepad2.a)
            {
                //using math to make a larger range of motion control
                firstLinkPVCExtendOrRetractRightRemote2.setPosition((gamepad2.left_trigger * 2 - 1) * (0 - servoCenter) + servoCenter);
            }
            else
            {
                firstLinkPVCExtendOrRetractRightRemote2.setPosition((gamepad2.left_trigger * 2 - 1) * (1 - servoCenter) + servoCenter);
            }
        }
        else if(gamepad2.left_trigger < .15)
        {
            if (triggerflag)
            {
                firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoRightTension);
            }
            else
            {
                firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoCenter);
            }

        }

        if(gamepad2.right_trigger > .50)
        {
            //triggerflag=true;
            if(gamepad2.a)
            {
                firstLinkPVCExtendOrRetractLeftRemote2.setPosition((gamepad2.right_trigger * 2 - 1) * (1 - servoCenter) + servoCenter);
            } else {
                firstLinkPVCExtendOrRetractLeftRemote2.setPosition((gamepad2.right_trigger * 2 - 1) * (0 - servoCenter) + servoCenter);
            /*((gamepad2.right_trigger * 2 - 1) * (0 - servoCenter) + servoCenter)
            * (([amount that the button is pressed] * 2 - 1) * ([min] - [max]) + [max])
            */
            }
        }
        else if(gamepad2.right_trigger < .15)
        {
            if (triggerflag)
            {
                firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoLeftTension);
            }
            else
            {
                firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoCenter);
            }
        }

        if(gamepad1.guide || true)
        {
            //telemetry data output to the phone
            telemetry.addData("Text", "*** Robot Data***");
            telemetry.addData("arm", "arm:  " + String.format("%.2f", bucketArmServoRemote1.getPosition()));
            telemetry.addData("claw", "claw:  " + String.format("%.2f", bucketServoRemote1.getPosition()));
        }

        if(gamepad1.b)
        {
            tankF = false;
            telemetry.addData("TankDive: ", "REVERSE");
        }

        if(gamepad1.y)
        {
            CurrentArmPosition -= lastLoopTime * 0.5;
            if(CurrentArmPosition < 0)
            {
                CurrentArmPosition = 0;
            }
        }

        if(gamepad1.a)
        {
            //popcorn = YAY!!!!!!!!!!
            CurrentArmPosition += lastLoopTime * 0.5;
            if(CurrentArmPosition > 1)
            {
                CurrentArmPosition = 1;
            }
        }

        if(gamepad1.x)
        {
            //Ben = Cool!
            tankF = true;
            telemetry.addData("TankDive: ", "FORWARD");
        }

        if(gamepad2.left_stick_y < -0.25)
        {
            hookWinchRightRemote2.setPower(gamepad2.left_stick_y);
        }
        else
        {
            hookWinchRightRemote2.setPower(0);
        }

        if(gamepad2.right_stick_y < -0.25)
        {
            hookWinchLeftRemote2.setPower(gamepad2.right_stick_y);
        }
        else
        {
            hookWinchLeftRemote2.setPower(0);
        }

        if (gamepad2.dpad_up)
        {
            telemetry.addData("telescopeExtend", 1);
            telescopeExtendMotorRemote2.setPower(1);
        }
        else if (gamepad2.dpad_down)
        {
            telemetry.addData("telescopeExtend", -1);
            telescopeExtendMotorRemote2.setPower(-1);
        }
        else
        {
            telemetry.addData("telescopeExtend", 0);
            telescopeExtendMotorRemote2.setPower(0);
        }

        if(gamepad1.dpad_up)
        {
            bucketServoRemote1.setPosition(dump);
        }
        else if(gamepad1.dpad_down)
        {
            bucketServoRemote1.setPosition(pickup);
        }
        bucketArmServoRemote1.setPosition(CurrentArmPosition);

        //TANK DRIVE
        if(tankF)//CHANGES OUR DIRECTION BASED ON IF "tankF" IS TRUE OR FALSE
        {
            if(slowD) //CHANGES OUR SPEED BASED ON "slowD"
            {
                //forward tank drive
                motorLeftRemote1.setPower(gamepad1.left_stick_y / 2);
                motorRightRemote1.setPower(gamepad1.right_stick_y / 2);
            }
            else
            {
                //forward tank drive
                motorLeftRemote1.setPower(gamepad1.left_stick_y);
                motorRightRemote1.setPower(gamepad1.right_stick_y);
            }
        }
        else if(!tankF)
        {
            if(slowD)//CHANGES OUR SPEED BASED ON "slowD"
            {
                //backward tank drive
                motorLeftRemote1.setPower(-gamepad1.right_stick_y / 2);
                motorRightRemote1.setPower(-gamepad1.left_stick_y / 2);
            }
            else
            {
                //backward tank drive
                motorLeftRemote1.setPower(-gamepad1.right_stick_y);
                motorRightRemote1.setPower(-gamepad1.left_stick_y);
            }
        }
        //THE END
        lastLoopTime = time;
    }
}
