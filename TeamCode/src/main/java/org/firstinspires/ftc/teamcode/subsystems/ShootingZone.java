package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.robotConfig.redLowerRect;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redLowerRectMaxX;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redLowerRectMaxY;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redLowerRectMinX;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redLowerRectMinY;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redUpperRect;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redUpperRectMaxX;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redUpperRectMaxY;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redUpperRectMinX;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.redUpperRectMinY;

import android.graphics.Point;
import android.graphics.Rect;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.utils.robotConfig;

import java.util.Arrays;

public class ShootingZone {
    //there is on purpose a 12 inch deadzone, this is to allow for switching better
    public enum ZoneState{
        AimingAtHighHive, AimingAtLowHive,
        AimingAtCenter
    }
    public ZoneState state;
    Follower follower;
    robotConfig.Alliance alliance;
    Pose [] upperRect;
    Pose [] lowerRect;

    //TODO implement alliances for each autonomous
    public ShootingZone(Follower follower, robotConfig.Alliance alliance){
        this.alliance = alliance;
        this.follower = follower;
        if(this.alliance == robotConfig.Alliance.RED){
            upperRect = redUpperRect;
            lowerRect = redLowerRect;
        }//TODO ADD BLUE CONDITION
    }

    /**
     * compares current robot position to two rectangles to determine which hive to aim at
     * @return the pose to aim at
     */
    public Pose hiveAimingTargetBasedOnZones(){
        Pose currentPose = follower.pose();
        //aim at high hive
        if(currentPose.x() > redUpperRectMinX && currentPose.x() < redUpperRectMaxX &&
            currentPose.y()> redUpperRectMinY && currentPose.y() < redUpperRectMaxY) {
            state = ZoneState.AimingAtHighHive;
            return robotConfig.upperRedHiveAim;
            //aim at low hive is below
        }else if(currentPose.x() > redLowerRectMinX && currentPose.x() < redLowerRectMaxX &&
                currentPose.y()> redLowerRectMinY && currentPose.y() < redLowerRectMaxY) {
            state = ZoneState.AimingAtLowHive;
            return robotConfig.lowerRedHiveAim;

        }//if not in the zone do nothing
        else{
            state = ZoneState.AimingAtCenter;
            return new Pose(72,72);
        }
    }


}
