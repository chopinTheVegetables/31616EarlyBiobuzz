package org.firstinspires.ftc.teamcode.teleops;

import static org.firstinspires.ftc.teamcode.utils.RobotConfig.allianceKey;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;

@TeleOp
public class Lm1Teleop extends OpMode {
    IntakeSubsystem intake;
    TurretSubsystem turret;
    Follower f;
    private double  mainRPM = 2600;
    boolean flowerDeployed = false;
    Servo flowerRamp;
    Servo gate;
    double gatePos = 0.5;

    @Override
    public void init() {
        //pedro pathing follower
        f = Constants.create(hardwareMap);
        //intake subsystem
        intake = new IntakeSubsystem(hardwareMap);
        //turret subsystem
        turret = new TurretSubsystem(hardwareMap,f, (RobotConfig.Alliance)blackboard.get(allianceKey));
        flowerRamp = hardwareMap.get(Servo.class, "flower");
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setPosition(0.5);
    }

    @Override
    public void loop() {
        //intake controls
        if(gamepad2.right_trigger > 0.1){
            intake.setPower(gamepad2.right_trigger);
        }else if(gamepad2.left_trigger > 0.15){
            intake.setPower(-gamepad2.left_trigger);
        }else{
            intake.setPower(0);
        }
        if(gamepad2.bWasReleased()){
            flowerDeployed =! flowerDeployed;
        }
        if(flowerDeployed){
            flowerRamp.setPosition(0.9);
        }
        else if(!flowerDeployed){
            flowerRamp.setPosition(0.4);
        }
        if(gamepad2.x) {
            gate.setPosition(1);
        }else{
            gate.setPosition(0.5);
        }

        //ball allow flow:
        //block:
        //fire last ball:
        //manual overrides (if no regression)
        if(gamepad2.dpadUpWasReleased()){
            mainRPM += 100;
        }else if(gamepad2.dpadDownWasReleased()){
            mainRPM -= 100;
        }

        //field centricDrive
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                f.pose().heading()
        );
        f.manual(powers);
        f.update();
        //telemetry
        telemetry.addData("gaet", gate.getPosition());
        telemetry.addData("main flywheel", turret.getMainFlywheelVelocity());
        telemetry.addData("counterRoller", turret.getCounterRollerVelocity());
        telemetry.addData("target", mainRPM);
        telemetry.update();
        //setting powers to the flywheels
        turret.setBothFlywheelsEven(mainRPM);
        turret.calculatePowers();
    }
}
