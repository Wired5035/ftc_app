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
public class AutoDropOffBlue extends LinearOpMode {

    //Variables section:
    int timeLeft = 10;
    ElapsedTime CountingUp = new ElapsedTime();
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
    //Sensors
    LightSensor lightR;
    LightSensor lightL;
    //Positions for servos to be set to
    float pvcExtenderL = 0.550f;
    float pvcExtenderR = 0.5f;
    float servoCenter = 0.525f;  //for continues rotation servos to stop them from spinning
    private boolean triggerflag;
    private float servoLeftTension=.03f;
    private float servoRightTension=.06f;



    @Override
    public void runOpMode() throws InterruptedException {

        //setup all Motors and servos for config file on phone
        motorRightRemote1 = hardwareMap.dcMotor.get("right");  //FORWARD
        motorLeftRemote1 = hardwareMap.dcMotor.get("left");  //REVERSED
        motorLeftRemote1.setDirection(DcMotor.Direction.REVERSE);
        motorRightRemote1.setDirection(DcMotor.Direction.FORWARD);
        motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
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
        lightL = hardwareMap.lightSensor.get("lightL");
        lightR = hardwareMap.lightSensor.get("lightR");
        motorLeftRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motorRightRemote1.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        CountingUp.reset();
        telemetry.addData("Status: ", "WAITING.....");
        motorLeftRemote1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRightRemote1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);


        waitForStart(); //wait for start


        motorLeftRemote1.setTargetPosition(10000);
        motorRightRemote1.setTargetPosition(100);
        motorLeftRemote1.setPower(.75);
        motorRightRemote1.setPower(.75);
        
        telemetry.addData("CountUp", ((float) CountingUp.time()));
        sleep(2000);
        CountingUp.reset();
        telemetry.addData("Status: ", "STARTING.....");
        telemetry.addData("CountUp", ((int) CountingUp.time()));
        telemetry.addData("TimeLeft: ", timeLeft);
        sleep(1000);
    }
}
