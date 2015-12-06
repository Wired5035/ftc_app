
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.apache.http.impl.conn.ProxySelectorRoutePlanner;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Team10618 extends OpMode {

    float bladeLDown = 0;
    float bladeRDown = 0.25f;
    float bladeLUp = 0.25f;
    float bladeRUp = 0;

    float armRDown = 0;
    float armLDown = 0;
    float armRUp = 0.6f;
    float armLUp = 0.6f;

    DcMotor right2;
    DcMotor right1;
    DcMotor left1;
    DcMotor left2;

    Servo bladeL;
    Servo bladeR;

    Servo armL;
    Servo armR;
    /**
     * Constructor
     */
    public Team10618() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {

        right1 = hardwareMap.dcMotor.get("right_drive");
        left1 = hardwareMap.dcMotor.get("left_drive");
        right2 = hardwareMap.dcMotor.get("Right_motor_2");
        left2 = hardwareMap.dcMotor.get("Left_motor_2");
        left1.setDirection(DcMotor.Direction.REVERSE);
        left2.setDirection(DcMotor.Direction.REVERSE);

        armL = hardwareMap.servo.get("larm");
        armR = hardwareMap.servo.get("rarm");
        bladeL = hardwareMap.servo.get("arm");
        bladeR = hardwareMap.servo.get("arm2");

        armL.setPosition(armLUp);
        armR.setPosition(armRUp);
        bladeL.setPosition(bladeLDown);
        bladeR.setPosition(bladeRDown);

    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

        left1.setPower(gamepad1.left_stick_y);
        left2.setPower(gamepad1.left_stick_y);
        right1.setPower(gamepad1.right_stick_y);
        right2.setPower(gamepad1.right_stick_y);

        if(gamepad1.right_bumper)
        {
            bladeR.setPosition(bladeRUp);
            bladeL.setPosition(bladeLUp);
        }
        else if(gamepad1.left_bumper)
        {
            bladeR.setPosition(bladeRDown);
            bladeL.setPosition(bladeLDown);
        }

        if(gamepad1.a)
        {
            armL.setPosition(armLDown);
            armR.setPosition(armRDown);
        }

        if(gamepad1.b)
        {
            armL.setPosition(armLUp);
            armR.setPosition(armRUp);
        }
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
