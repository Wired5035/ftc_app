package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by KotaH on 9/23/2017.
 */
@Autonomous(name = "AutoTestModeGyro", group = "Concept")
public class GyroSensorTest extends LinearOpMode{


    @Override
    public void runOpMode() throws InterruptedException {
        Hardware5035 robot = new Hardware5035();
        robot.init(hardwareMap, this);
        telemetry.addData("Version","1_1");


        waitForStart();
        while(opModeIsActive()) {

            telemetry.addData("gyro x", robot.gyro.rawX());
            telemetry.addData("gyro y", robot.gyro.rawY());
            telemetry.addData("gyro z", robot.gyro.rawZ());

            telemetry.update();
            if(!opModeIsActive()){ break;}

        }
    }
}
