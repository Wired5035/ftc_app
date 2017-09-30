package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

/**
 * Created by KotaH on 9/23/2017.
 */



@Autonomous(name = "AutoTestModeGyro", group = "Concept")
public class GyroSensorTest extends LinearOpMode{
    //public GyroSensor modernRoboticsI2cGyro;
    public IntegratingGyroscope gyro;
    public ModernRoboticsI2cGyro modernRoboticsI2cGyro;

    //public GyroSensor gyro;
    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware5035 robot = new Hardware5035(); <---this makes the program error out.
        //robot.init(hardwareMap, this);  <---this makes the program error out.
        modernRoboticsI2cGyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
        gyro = (IntegratingGyroscope)modernRoboticsI2cGyro;
        telemetry.addData("Version","2_1");
        //telemetry.addData("status", gyro.toString());
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {



            telemetry.addData("gyro x", (modernRoboticsI2cGyro.rawX() / 100));
            telemetry.addData("gyro y", (modernRoboticsI2cGyro.rawY() / 100));
            telemetry.addData("gyro z", (modernRoboticsI2cGyro.rawZ() / 100));


            telemetry.update();
            if(!opModeIsActive()){ break;}

        }
    }
}
