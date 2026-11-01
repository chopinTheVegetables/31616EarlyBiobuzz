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
    ///For use by servo
    public void autoAimTurretNoSOTM(){
        turretRotation.setPosition(calculateAimAngle()*servoMultiplier);
    }
    public void manualAngleSet(double theta){
        turretRotation.setPosition(theta*servoMultiplier);

    }
    public void setFlywheelVelocity(int vel ){
        flywheel.setVelocity(vel*28/60);
    }
    public double getFlywheelVelocity(){
        return flywheel.getVelocity()/28*60;
    }





}
