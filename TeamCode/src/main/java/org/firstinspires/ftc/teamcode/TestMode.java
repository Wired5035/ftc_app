package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Kota Baer on 11/22/2016.
 */
@TeleOp(name = "TestModeTele")
public class TestMode extends OpMode  {
    Hardware5035 robot = new Hardware5035();

    @Override
    public void init() {
            robot.init(hardwareMap, null);

    }

    @Override
    public void loop() {
        telemetry.addData("green", robot.colorDetector.green());
        telemetry.addData("alpha", robot.colorDetector.alpha());
        telemetry.addData("Red  ", robot.colorDetector.red());
        //  telemetry.addData("Green", robot.colorDetector.green());
        telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
        telemetry.update();
        /*
        if (gamepad1.x)
        {
            robot.popUp.setPosition(Math.min(robot.popUp.getPosition() + 0.009, 1));
        }
        if (gamepad1.y)
        {
            robot.popUp.setPosition(Math.max(robot.popUp.getPosition() + -0.009, 0));
        }
        telemetry.addData("Position of server", robot.popUp.getPosition());
        telemetry.update();
        */
        /*
        telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        robot.setDrivePower(.25);
        //drive using ultrasonic till ~14cm away from wall
        telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value
        while(robot.frontUltra.getUltrasonicLevel() >= 20){
            telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());}//turn off drive power
        robot.setDrivePower(0);
        telemetry.addData("ultravalue", robot.frontUltra.getUltrasonicLevel());//out put ultra value

        telemetry.addData("getUltrasonicLevel()", robot.frontUltra.getUltrasonicLevel());
        */
    }
}
