package org.firstinspires.ftc.teamcode.subsystems;


import static org.firstinspires.ftc.teamcode.utils.robotConfig.counterRollerMotorName;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.flywheelMotorName;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.motorToCounterRollerGearRatio;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.motorToFlywheelGearRatio;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.servoMultiplier;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.turretRotName;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.robotConfig;

public class TurretSubsystem {
    DcMotorEx mainFlywheel;
    DcMotorEx counterRoller;
    Servo turretRotation;
    ShootingZone zoneDetector;
    Follower f;
    public TurretSubsystem(HardwareMap hardwareMap, Follower follower, robotConfig.Alliance alliance){
        mainFlywheel = hardwareMap.get(DcMotorEx.class, flywheelMotorName);
        counterRoller = hardwareMap.get(DcMotorEx.class, counterRollerMotorName);
        counterRoller.setDirection(DcMotorSimple.Direction.REVERSE); //backwards
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
    public void setMainFlywheelVelocity(int vel ){
        mainFlywheel.setVelocity(vel*28/60);
    }
    public void setBothFlywheelsEven(double mainFlywheelSpeed){
        mainFlywheel.setVelocity(mainFlywheelSpeed/60/motorToFlywheelGearRatio*28);
        //convert to counter roller speed
        counterRoller.setVelocity((mainFlywheelSpeed*Math.PI)/60/motorToCounterRollerGearRatio*28);
    }

    public double getmainFlywheelVelocity(){
        return mainFlywheel.getVelocity()/28*60*motorToFlywheelGearRatio;
    }
    public double getCounterRollerVelocity(){
        return counterRoller.getVelocity()/28*60*motorToCounterRollerGearRatio;
    }





}
