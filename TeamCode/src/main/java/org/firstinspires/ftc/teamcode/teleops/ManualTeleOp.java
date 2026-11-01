package org.firstinspires.ftc.teamcode.teleops;

import static org.firstinspires.ftc.teamcode.utils.robotConfig.allianceKey;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intakeSubsystem;
import org.firstinspires.ftc.teamcode.utils.robotConfig;

@TeleOp
public class ManualTeleOp extends OpMode {
    Follower f;
    intakeSubsystem intake;
    TurretSubsystem turret;
    int shooterSpeed = 2000;

    @Override
    public void init() {
        f = Constants.create(hardwareMap);
        turret = new TurretSubsystem(hardwareMap, f, (robotConfig.Alliance) blackboard.get(allianceKey));
    }

    @Override
    public void loop() {
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                f.pose().heading()
        );
        f.manual(powers);
        f.update();
        turret.setFlywheelVelocity(shooterSpeed);
        if(gamepad2.dpadUpWasReleased()){
            shooterSpeed += 250;
        }
        if(gamepad2.dpadDownWasReleased()){
            shooterSpeed -= 250;
        }
        if(gamepad2.right_trigger >0.1){
            double conversion = 1150/10;
            intake.setVel(gamepad2.right_trigger * conversion);
        }
        else if (gamepad2.left_trigger > 0.3){
            double conversion = 1150/10;
            intake.setVel(-gamepad2.left_trigger * conversion);
        }
        else{
            intake.setVel(0);
        }
        //TODO gate servo



    }
}
