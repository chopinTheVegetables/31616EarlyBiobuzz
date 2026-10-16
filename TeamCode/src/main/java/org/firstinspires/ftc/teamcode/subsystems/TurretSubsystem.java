package org.firstinspires.ftc.teamcode.subsystems;


import static org.firstinspires.ftc.teamcode.utils.robotConfig.flywheelMotorName;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.servoMultiplier;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.turretRotName;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.robotConfig;

public class TurretSubsystem {
    DcMotorEx flywheel;
    Servo turretRotation;
    ShootingZone zoneDetector;
    Follower f;

    public TurretSubsystem(HardwareMap hardwareMap, Follower follower, robotConfig.Alliance alliance){
        flywheel = hardwareMap.get(DcMotorEx.class, flywheelMotorName);
        turretRotation = hardwareMap.get(Servo.class, turretRotName);
        zoneDetector = new ShootingZone(follower, alliance);
        this.f = follower;
    }
    private double calculateAimAngle(){
        Pose currentPose = f.pose();//degrees
        Pose targetPose = zoneDetector.hiveAimingTargetBasedOnZones();
        return currentPose.heading() + Math.toDegrees(Math.atan2(targetPose.y() - currentPose.y(),
                targetPose.x() - currentPose.x()));
    }
    public void aimTurretByServo(){
        turretRotation.setPosition(calculateAimAngle()*servoMultiplier);
    }




}
