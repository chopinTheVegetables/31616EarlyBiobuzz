package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Point;

import com.pedropathing.math.Pose;

import java.util.ArrayList;

public class robotConfig {
    public static final String intakeName = "intake";
    public static final String flywheelMotorName = "flywheel";
    public static final String turretRotName = "turret";
    public static final String LLname = "limelight";
    public static final double LLtiltAngle = 30;
    public static final double LLHeight = 10;
    public static final double LLForwardOffset = -1;
    public static final double LLRightOffset = 0;
    ///____red upper rect start
    public static double redUpperRectMaxX = 100;
    public static double redUpperRectMinX = 0;
    public static double redUpperRectMaxY = 144;
    public static double redUpperRectMinY = 78;

    //for shooting automation
    public static Pose [] redUpperRect = {new Pose(redUpperRectMinX,redUpperRectMinY), new Pose(redUpperRectMinX,redUpperRectMaxY),
                                           new Pose(redUpperRectMaxX, redUpperRectMaxY) ,new Pose(redUpperRectMaxX,redUpperRectMinY)};
    ///____end
      public static double redLowerRectMaxX = 100;
         public static double redLowerRectMinX = 0;
         public static double redLowerRectMaxY = 66;
         public static double redLowerRectMinY = 0;
    public static Pose [] redLowerRect = {new Pose(redLowerRectMinX,redLowerRectMinY), new Pose(redLowerRectMinX,redLowerRectMaxY),
                                            new Pose(redLowerRectMaxX, redLowerRectMaxY),new Pose(redLowerRectMaxX,redLowerRectMinY)};


    ///---Hives
    public static Pose lowerRedHiveAim = new Pose(57.5, 56);
    public static Pose upperRedHiveAim = new Pose(57.5, 88);
    ///---
    public enum Alliance{
        RED, BLUE
    }





}
