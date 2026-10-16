package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.robotConfig.LLForwardOffset;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.LLHeight;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.LLRightOffset;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.LLname;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.LLtiltAngle;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.opencv.core.Mat;

public class LLdetector {
    private Limelight3A LL;
    Follower follower;
    public LLdetector(HardwareMap hardwareMap, Follower follower){
        LL = hardwareMap.get(Limelight3A.class, LLname);
        this.follower = follower;
    }

    /**
     * switches LL pipeline
     * @param newPipeline is the new pipeline index number
     */
    public void switchPipeline(int newPipeline){
        LL.pipelineSwitch(newPipeline);
    }
    public Pose getClosestPollenCoords(){
        //null check
        if (LL.getLatestResult() == null || !LL.getLatestResult().isValid()) {
            return null;
        }

        //LL tilt angle from the vertical
        double theta = LLtiltAngle;
        //mount height of LL
        double h1 = LLHeight;
        //height of center of pollen off the ground
        double h2 = 1.5;
        //LL results
        double tx = LL.getLatestResult().getTx();
        double ty = LL.getLatestResult().getTy();

        double totalPitchRad = Math.toRadians(theta - ty);
        if (Math.abs(totalPitchRad) < 0.001) return null;

        double cameraLocalX = (h1 - h2) / Math.tan(totalPitchRad);
        double cameraLocalY = cameraLocalX * Math.tan(Math.toRadians(-tx));

        //robot centric coordinates
        double localX = cameraLocalX + LLForwardOffset;
        double localY = cameraLocalY + LLRightOffset;

        Pose currentPosition = follower.pose();
        double robotHeadingRad = currentPosition.heading();
        //robot centric --> field centric
        double fieldXOffset = (localX * Math.cos(robotHeadingRad)) - (localY * Math.sin(robotHeadingRad));
        double fieldYOffset = (localX * Math.sin(robotHeadingRad)) + (localY * Math.cos(robotHeadingRad));

        return new Pose(
                currentPosition.x() + fieldXOffset,
                currentPosition.y() + fieldYOffset,
                0.0
        );
    }




}
