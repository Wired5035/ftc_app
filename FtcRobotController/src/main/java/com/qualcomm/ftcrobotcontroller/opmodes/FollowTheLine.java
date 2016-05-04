package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.LightSensor;

/**
 * Created by Kota Baer on 5/3/2016.
 */
public class FollowTheLine extends AutoHangBot {
LightSensor leftLightSensor;
LightSensor rightLightSensor;
char turn;
double speedmulti = 1;
double turnSpeed = .17 * speedmulti;


    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        leftLightSensor = hardwareMap.lightSensor.get("leftSensor");
        rightLightSensor = hardwareMap.lightSensor.get("rightSensor");
        leftLightSensor.enableLed(true);
        rightLightSensor.enableLed(true);

        waitForStart();

        while(true)
        {
            turn = '0';
            setDrivePower(.1 * speedmulti);
            if(leftLightSensor.getLightDetected() > .5)
            {
                motorLeftRemote1.setPower(-turnSpeed);
                motorRightRemote1.setPower(turnSpeed);
            }
            if(rightLightSensor.getLightDetected() > .5)
            {
                motorRightRemote1.setPower(-turnSpeed);
                motorLeftRemote1.setPower(turnSpeed);
            }
            if(rightLightSensor.getLightDetected() > .7 && leftLightSensor.getLightDetected() > .7)
            {
                motorRightRemote1.setPower(-turnSpeed);
                motorLeftRemote1.setPower(turnSpeed);
            }
            telemetry.addData("RightSensor" , leftLightSensor.getLightDetected());
            telemetry.addData("LeftSensor" , rightLightSensor.getLightDetected());
            if(leftLightSensor.getLightDetected() > .5) turn = 'r';
            if(rightLightSensor.getLightDetected() > .5) turn = 'l';
            if(turn == 'r')
            {
                telemetry.addData("Left Turn", " false");
                telemetry.addData("Right Turn", " true");
            }
            else
            {
                telemetry.addData("Right Turn", " false");
            }
            if(turn == 'l') {
                telemetry.addData("Right Turn", " false");
                telemetry.addData("Left Turn", " true");
            }
            else
            {
                telemetry.addData("Left Turn", " false");
            }
            waitOneFullHardwareCycle();
        }
    }

}
