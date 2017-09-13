package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;



/*
 * This OpMode was written for the Vuforia Basics video. This demonstrates basic principles of
 * using Vuforia in FTC.
 */

/**
 * Created by Owner on 9/12/2017.
 */
@Autonomous(name = "Vuforia5035")
public class VuforiaPictureDecoder {
    VuforiaLocalizer vuforia;
    public VuforiaPictureDecoder(){
        VuforiaTrackables KeyCodes = this.vuforia.loadTrackablesFromAsset("FTC_2016-17"); // change name to new keycode pictures
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AfXb48r/////AAAAGeD2f/Vqr091sIpDI7RLaYYXDM4Ao03klW8aOZpnKhW7owlW94atv0FpmrIMSu8f15XxGzIXZa9xjWrEw+Cqnea2mZE/FuHbD6WUGnU1Mwyy8CzejVRQV0dTu2Y/KuS9nxcCMcMDKnH3OZjFZYJLPgJ3TqqgL47MkEszN/iS8LKg82rPhB81mh3t5c7ZohzPRNDhvrgUOQHruNu+7YcjilNMbtqBGutFkNxJ5qSbA1WajcXwIrgMwvQFDMnr3O1kqo5Mks4lYReyNvczQ4I7TZuRtqox4SzZf9hJN7EfuOGVwRX8YdOTyMMOnekK7lJSNbdydaTQA3ye0eLxO90kOX1zOhexEzGO9WPFiG3hN/s4";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
    }
    //which picture is seen (int)
    public int whatpictureisvisable(){
        return whatpictureisvisable();
    }


}
