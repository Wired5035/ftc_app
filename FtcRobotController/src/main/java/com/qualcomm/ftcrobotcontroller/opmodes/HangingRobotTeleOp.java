package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Kota Baer on 11/17/2015.
 */
public class HangingRobotTeleOp extends OpMode{
    //Bucket Positions
    static final float pickup =0.847f;    //for picking up game
    static final float holding =1f;
    static final float score =0.34f;  //getting ready to score
    static final float dump =0.10f;   //dumping
    //Arm Positions
    static final float pickuparm =1f;
    static final float holdingarm =1f;
    static final float scorearm =0f;
    static final float dumparm =0f;

    boolean tankF; //to change the drive direction
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
        bucketArmServoRemote1.setPosition(pickuparm);
        telescopingPVCDropRightRemote2.setPosition(0f);
        telescopingPVCDropLeftRemote2.setPosition(1f);
        firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoCenter);
        firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoCenter);
        //initialise tank drive to forward
        tankF = true;
    }

    @Override
    public void start() {
        super.start();
        //initialise servo positions on start
        bucketServoRemote1.setPosition(pickup);
        bucketArmServoRemote1.setPosition(pickuparm);
        telescopingPVCDropRightRemote2.setPosition(0f);
        telescopingPVCDropLeftRemote2.setPosition(1f);
        firstLinkPVCExtendOrRetractRightRemote2.setPosition(servoCenter);
        firstLinkPVCExtendOrRetractLeftRemote2.setPosition(servoCenter);
    }

    @Override
    public void loop() {



        if(gamepad2.left_bumper && gamepad2.right_bumper)
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
            bucketArmServoRemote1.setPosition(pickuparm);
            bucketServoRemote1.setPosition(pickup);
        }

        if(gamepad1.y)
        {
            //pizza = yummy
            bucketArmServoRemote1.setPosition(holdingarm);
            bucketServoRemote1.setPosition(holding);
        }

        if(gamepad1.a)
        {
            //popcorn = YAY!
            bucketArmServoRemote1.setPosition(dumparm);
            bucketServoRemote1.setPosition(dump);
        }

        if(gamepad1.x)
        {
            //Ben = Cool!
            bucketArmServoRemote1.setPosition(scorearm);
            bucketServoRemote1.setPosition(score);
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

        //changes the drive mode based on the Dpad
        if(gamepad1.dpad_up)// UP = FORWARD
        {
            tankF = true;
            telemetry.addData("TankDive: ", "FORWARD");
        }
        else if(gamepad1.dpad_down)// DOWN = REVERSED
        {
            tankF = false;
            telemetry.addData("TankDrive: ", "REVERSED");
        }

        //TANK DRIVE
        if(tankF)//CHANGES OUR DIRECTION BASED ON IF "tankF" IS TRUE OR FALSE
        {
            //forward tank drive
            motorLeftRemote1.setPower(gamepad1.left_stick_y);
            motorRightRemote1.setPower(gamepad1.right_stick_y);
        }
        else if(!tankF)
        {
            //backward tank drive
            motorLeftRemote1.setPower(-gamepad1.left_stick_y);
            motorRightRemote1.setPower(-gamepad1.right_stick_y);
        }
    }
}
