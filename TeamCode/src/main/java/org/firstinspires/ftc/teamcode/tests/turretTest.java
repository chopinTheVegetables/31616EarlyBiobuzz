package org.firstinspires.ftc.teamcode.tests;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;
@TeleOp
public class turretTest extends OpMode {
    TurretSubsystem turret;
    Follower f;
    int angle = 0;
    IntakeSubsystem intake;
    int speed = 0;

    @Override
    public void init() {
         f = Constants.create(hardwareMap);
         f.setPose(new Pose(54, 9, Math.toRadians(90)));
         turret = new TurretSubsystem(hardwareMap, f, RobotConfig.Alliance.RED);
         intake = new IntakeSubsystem(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0.1){
            intake.setPower(gamepad1.right_trigger);
        }else if(gamepad1.left_trigger > 0.15){
            intake.setPower(-gamepad1.left_trigger);
        }else{
            intake.setPower(0);
        }
        if(gamepad1.x){
            intake.openGate();
        }else{
            intake.closeGate();
        }
        f.manual(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x);
        if(gamepad1.dpadRightWasReleased()){
            speed += 100;
        }else if(gamepad1.dpadLeftWasReleased()){
           speed -= 100;
        }
        telemetry.addData("gtc", turret.calculateAimAngle());
        telemetry.addData("heading", Math.toDegrees(f.pose().heading()));
        telemetry.addData("aimstate", turret.getAimState());
        telemetry.addData("dist", turret.getDist());
        telemetry.addData("rpm target", turret.getRPMtarget());
        telemetry.addData("rpm", turret.getMainFlywheelVelocity());
        telemetry.addData("cr", turret.getCounterRollerVelocity());

        turret.autoAimTurretNoSOTM();
        turret.setFlywheelsUsingRegression();
        turret.calculatePowers();
        telemetry.update();
        f.update();

    }
}
