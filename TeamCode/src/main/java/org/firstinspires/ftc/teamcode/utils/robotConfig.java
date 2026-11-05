package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Point;

import com.pedropathing.math.Pose;

import java.util.ArrayList;

public class robotConfig {
    public static final String intakeRName = "intakeR";
    public static final String intakeLName = "intakeL";
    public static final double intakeMotorEncoderRes = 145.1;

    public static final String flywheelMotorName = "flywheel";
    public static final String counterRollerMotorName = "flywheel2";
    ///FLYWHEELS SCHEMATICS
    public static double motorToFlywheelGearRatio = 66/40;
    public static double motorToCounterRollerGearRatio = 1;

    public static double mainFlywheelSize = 2; //inches
    public static double counterRollerSize = 1; //inches

    public static final String turretRotName = "turret";
    public static int turretRotationDegrees = 360;
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
    ///--- end
    public enum Alliance{
        RED, BLUE
    }
    public static final String allianceKey = "allianceRedOrBlue";





}
