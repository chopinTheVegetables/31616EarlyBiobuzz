package org.firstinspires.ftc.teamcode.teleops;

import static org.firstinspires.ftc.teamcode.utils.robotConfig.allianceKey;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intakeSubsystem;
import org.firstinspires.ftc.teamcode.utils.robotConfig;

public class autoTeleOp extends OpMode {
    Follower follower;
    TurretSubsystem turret;
    intakeSubsystem intake;
    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        turret = new TurretSubsystem(hardwareMap,follower, (robotConfig.Alliance) blackboard.get(allianceKey));
        intake = new intakeSubsystem(hardwareMap);
    }

    @Override
    public void loop() {
        //TODO use regression
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );
        follower.manual(powers);
        follower.update();
        turret.autoAimTurretNoSOTM();


    }
}
