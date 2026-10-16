package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.robotConfig.lowerRedRect;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.upperRedRect;

import android.graphics.Point;
import android.graphics.Rect;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.utils.Rectangle;
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
    Rectangle lowerRect;
    Rectangle upperRect;

    //TODO implement alliances for each autonomous
    public ShootingZone(Follower follower, robotConfig.Alliance alliance){
        this.alliance = alliance;
        this.follower = follower;
        if(this.alliance == robotConfig.Alliance.RED){
            upperRect = upperRedRect;
            lowerRect = lowerRedRect;
        }//TODO ADD BLUE CONDITION
    }

    /**
     * compares current robot position to two rectangles to determine which hive to aim at
     * @return the pose to aim at
     */
    public Pose hiveAimingTargetBasedOnZones(){
        Pose currentPose = follower.pose();
        //aim at high hive
        if(currentPose.x() > upperRedRect.getLowestY() && currentPose.x() < upperRedRect.getHighestX() &&
            currentPose.y()> upperRedRect.getLowestY() && currentPose.y() < upperRedRect.getHighestY()) {
            state = ZoneState.AimingAtHighHive;
            return robotConfig.upperRedHiveAim;
            //aim at low hive is below
        }else if(currentPose.x() > lowerRect.getLowestX() && currentPose.x() < lowerRect.getHighestX() &&
                 currentPose.y() > lowerRect.getLowestY() && currentPose.y() < lowerRect.getHighestY()) {
            state = ZoneState.AimingAtLowHive;
            return robotConfig.lowerRedHiveAim;

        }//if not in the zone do nothing
        else{
            state = ZoneState.AimingAtCenter;
            return new Pose(72,72);
        }
    }


}
