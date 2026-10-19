package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class shooterTest extends OpMode {
    DcMotorEx shooter;
    double shooterPow =0;
    @Override
    public void init() {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
    }

    @Override
    public void loop() {
        if(gamepad1.dpadUpWasReleased()){
            shooterPow +=500;
        }if(gamepad1.dpadDownWasReleased()){
            shooterPow -=500;
        }
        shooter.setVelocity(shooterPow*28/60);
        shooter.setPower(shooterPow);
        telemetry.addData("rpm,", shooterPow);
        telemetry.addData("actual", shooter.getVelocity()/28*60);
        telemetry.update();

    }
}
