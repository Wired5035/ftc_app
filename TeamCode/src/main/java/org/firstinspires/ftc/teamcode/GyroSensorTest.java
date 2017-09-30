package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by KotaH on 9/23/2017.
 */
@Autonomous(name = "AutoTestModeGyro", group = "Concept")
public class GyroSensorTest extends LinearOpMode{

    //public GyroSensor gyro;
    @Override
    public void runOpMode() throws InterruptedException {

        Hardware5035 robot = new Hardware5035();
        robot.init(hardwareMap, this);
        //gyro = hwMap.gyroSensor.get("gyro");
        telemetry.addData("Version","1_1");
        telemetry.addData("status", robot.gyro.status());
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {

            /*
            if(!(gyro.rawZ() == 0) || !(gyro.rawX() == 0) || !(gyro.rawY() == 0)){//
            telemetry.addData("gyro x", gyro.rawX());
            telemetry.addData("gyro y", gyro.rawY());
            telemetry.addData("gyro z", gyro.rawZ());
            }
            */

            telemetry.update();
            if(!opModeIsActive()){ break;}

        }
    }
}
