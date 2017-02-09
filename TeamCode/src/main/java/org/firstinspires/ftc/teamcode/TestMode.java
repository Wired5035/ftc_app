package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Kota Baer on 11/22/2016.
 */
@TeleOp(name = "TestModeTele")
public class TestMode extends OpMode  {
    Hardware5035 robot = new Hardware5035();

    boolean lastGamePad1A = false;
    boolean lastGamePad1B = false;
    ElapsedTime servoTimer;
    boolean stopServo = false;

    @Override
    public void init() {
            robot.init(hardwareMap, null);
        lastGamePad1A = false;
        lastGamePad1B = false;
        servoTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    @Override
    public void loop() {
        telemetry.addData("green", robot.colorDetector.green());
        telemetry.addData("alpha", robot.colorDetector.alpha());
        telemetry.addData("Red  ", robot.colorDetector.red());
        //  telemetry.addData("Green", robot.colorDetector.green());
        telemetry.addData("Blue ", robot.colorDetector.blue());//telemetry.addData("rightpower", robot.rightMotor.getPower());
        telemetry.addData("sideultra ", robot.sideUltra.getUltrasonicLevel());
        telemetry.addData("frontultra ", robot.frontUltra.getUltrasonicLevel());
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

        if (!lastGamePad1A && gamepad1.a)
        {
            robot.constServo.setPosition(0);
            stopServo = true;
            servoTimer.reset();
        }

        if (!lastGamePad1B && gamepad1.b)
        {
            robot.constServo.setPosition(1);
            stopServo = true;
            servoTimer.reset();
        }

        if (stopServo && servoTimer.milliseconds() >= 3000)
        {
            robot.constServo.setPosition(.5);
        }

        lastGamePad1A = gamepad1.a;
        lastGamePad1B = gamepad1.b;
    }
}
