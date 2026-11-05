package org.firstinspires.ftc.teamcode.driveTests;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.utils.robotConfig;
@Configurable
@TeleOp
public class dualFlywheelTest extends OpMode {
    IntakeSubsystem intake;
    TurretSubsystem turret;
    Follower f;
    public static double  mainRPM = 0;
    @Override
    public void init() {
        f = Constants.create(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap);
        turret = new TurretSubsystem(hardwareMap,f, robotConfig.Alliance.RED);
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0.1){
            intake.setPower(gamepad1.right_trigger);
        }else if(gamepad1.left_trigger > 0.5){
            intake.setPower(-gamepad1.left_trigger);
        }else{
            intake.setPower(0);
        }
        turret.setBothFlywheelsEven(mainRPM);
        if(gamepad1.dpadUpWasReleased()){
            mainRPM += 200;
        }else if(gamepad1.dpadDownWasReleased()){
            mainRPM -= 200;
        }
        telemetry.addData("main flywheel", turret.getMainFlywheelVelocity());
        telemetry.addData("counterRoller", turret.getCounterRollerVelocity());
        telemetry.addData("target", mainRPM);
        telemetry.update();
        turret.setBothFlywheelsEven(mainRPM);
        turret.calculatePowers();
    }
}
