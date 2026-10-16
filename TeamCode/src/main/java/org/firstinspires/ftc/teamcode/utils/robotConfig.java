package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Point;

import com.pedropathing.math.Pose;

import java.util.ArrayList;

public class robotConfig {
    public static final String intakeName = "intake";
    public static final String flywheelMotorName = "flywheel";
    public static final String turretRotName = "turret";
    public static int turretRotationDegrees = 270;
    public static double servoMultiplier = 1.0/turretRotationDegrees; //1 div by servo range (deg)

    //LL
    public static final String LLname = "limelight";
    public static final double LLtiltAngle = 30;
    public static final double LLHeight = 10;
    public static final double LLForwardOffset = -1;
    public static final double LLRightOffset = 0;
    //LL

    //for shooting automation: shooting zones
    ///RED
    public static Rectangle upperRedRect = new Rectangle(new Pose(0,78), new Pose(0,144),
            new Pose(100,144), new Pose(100,78));
    public static Rectangle lowerRedRect = new Rectangle(new Pose(0,0), new Pose(0,66),
            new Pose(100,66), new Pose(100,0));
    ///BLUE
    public static Rectangle upperBlueRect = new Rectangle(new Pose(44,78), new Pose(44,144),
            new Pose(144,144), new Pose(144,78));
    public static Rectangle lowerBlueRect = new Rectangle(new Pose(144,0), new Pose(144,66),
            new Pose(44,66), new Pose(44,0));


    ///---Hive points to aim at
    public static Pose lowerRedHiveAim = new Pose(57.5, 56);
    public static Pose upperRedHiveAim = new Pose(57.5, 88);
    public static Pose lowerBlueHiveAim = new Pose(86.4, 56);
    public static Pose upperBlueHiveAim = new Pose(86.4, 88);
    ///---
    public enum Alliance{
        RED, BLUE
    }





}
