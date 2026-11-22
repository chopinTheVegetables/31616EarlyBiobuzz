package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.RobotConfig.counterRollerMotorName;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.flywheelMotorName;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.lowerRedHiveAim;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.motorToCounterRollerGearRatio;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.servoMultiplier;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.turretRotName;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.RegressionTable;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;
import org.opencv.core.Mat;

@Configurable
public class TurretSubsystem {
    DcMotorEx mainFlywheel, counterRoller;
    Servo turretRotation;
    ShootingZone zoneDetector;
    Follower follower;

    // constants for main flywheel
    public static double counterRollerConst = 1.7;
    public static double mkV = 0.0015; // Default baseline for 6000 RPM motors
    public static double mkP = 0.0005;
    public static double mkI = 0.0;
    public static double mkD = 0.0;
    //constants for counterroller pid + kV
    public static double ckV = 0.0003;
    public static double ckP = 0.0005;
    public static double ckI = 0.0;
    public static double ckD = 0.0;

    private double targetVelocity = 0.0;
    ///TUNING PIDS REQUIRES THESE PID DECLERATIONS TO BE MOVED TO calculatePowers()
    Controller mainPower = Controller.pid(mkP, mkI, mkD)
            .plus(Controller.proportionalFeedforward(mkV));
    Controller subPower = Controller.pid(ckP, ckI, ckD)
            .plus(Controller.proportionalFeedforward(ckV));


    public TurretSubsystem(HardwareMap hardwareMap, Follower follower, RobotConfig.Alliance alliance){
        mainFlywheel = hardwareMap.get(DcMotorEx.class, flywheelMotorName);
        counterRoller = hardwareMap.get(DcMotorEx.class, counterRollerMotorName);
        counterRoller.setDirection(DcMotorSimple.Direction.REVERSE);

        turretRotation = hardwareMap.get(Servo.class, turretRotName);
        turretRotation.setDirection(Servo.Direction.REVERSE);

        mainFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        counterRoller.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        counterRoller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mainFlywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.follower = follower;
        turretRotation.setPosition(0.5);
        zoneDetector = new ShootingZone(follower, alliance);
        //this.follower = follower;

    }

    public double calculateAimAngle() {
        Pose currentPose = follower.pose();
        Pose targetPose = zoneDetector.hiveAimingTargetBasedOnZones();
        double globalTargetAngle = Math.toDegrees(Math.atan2(targetPose.y() - currentPose.y(), targetPose.x() - currentPose.x()));
        return wrapDegrees(globalTargetAngle- Math.toDegrees(currentPose.heading()));
    }
    public static double wrapDegrees(double degrees) {
        degrees = (degrees + 180.0) % 360.0;
        if (degrees < 0) {
            degrees += 360.0;
        }
        return degrees - 180.0;
    }
    public double getFieldAngle(){
        Pose currentPose = follower.pose();
        Pose targetPose = zoneDetector.hiveAimingTargetBasedOnZones();
        return Math.toDegrees(Math.atan2(targetPose.y() - currentPose.y(), targetPose.x() - currentPose.x()));
    }

    public double getRPMtarget(){
        return targetVelocity;
    }
    public void setFlywheelsUsingRegression(){
        setBothFlywheelsEven(RegressionTable.getTarget(getDist()));
    }
    // Centers the calculation at the 0.5 servo midpoint position
    public void autoAimTurretNoSOTM() {
            double servoPosition = 0.5 + (calculateAimAngle() * servoMultiplier);
            turretRotation.setPosition(Math.max(0.0, Math.min(1.0, servoPosition)));
    }
    public double getDist(){
        Pose currentPose = follower.pose();
        if(getAimState() == ShootingZone.ZoneState.AimingAtLowHive){
            return Math.hypot(zoneDetector.lowerHive.x() - currentPose.x(), (zoneDetector.lowerHive.y()- currentPose.y()));
        }if(getAimState() == ShootingZone.ZoneState.AimingAtHighHive){
            return Math.hypot(zoneDetector.upperHive.x() - currentPose.x(), (zoneDetector.upperHive.y()- currentPose.y()));
        }else{
            return 0;
        }
    }
    public void manualAngleSet(double theta) {
            double servoPosition = 0.5 + (theta * servoMultiplier);
            turretRotation.setPosition(Math.max(0.0, Math.min(1.0, servoPosition)));
    }
    public void setBothFlywheelsEven(double mainFlywheelSpeed){ this.targetVelocity = mainFlywheelSpeed; }

    public double getMainFlywheelVelocity(){ return mainFlywheel.getVelocity() / 28 * 60 * (66.0 / 40.0); }
    public double getCounterRollerVelocity(){ return counterRoller.getVelocity() / 28 * 60 * motorToCounterRollerGearRatio; }

    public void calculatePowers() {
        if (targetVelocity <= 0) {
            mainFlywheel.setPower(0);
            counterRoller.setPower(0);
            return;
        }

        // RPM --> Ticks per second
        double targetMainTicks = (targetVelocity * (40.0 / 66.0) * 28.0) / 60.0;
        double targetSubTicks = ((targetVelocity * counterRollerConst) * motorToCounterRollerGearRatio * 28.0) / 60.0;
        //pid calculations
        double mainPow = mainPower.calculate(targetMainTicks - mainFlywheel.getVelocity(), targetMainTicks);
        double subPow = subPower.calculate(targetSubTicks - Math.abs(counterRoller.getVelocity()), targetSubTicks);

        //clippingx
        mainFlywheel.setPower(Math.max(0, Math.min(1, mainPow)));
        counterRoller.setPower(Math.max(0, Math.min(1, subPow)));
    }
    public ShootingZone.ZoneState getAimState(){
        zoneDetector.hiveAimingTargetBasedOnZones();
        return zoneDetector.getTarget();
    }
}
