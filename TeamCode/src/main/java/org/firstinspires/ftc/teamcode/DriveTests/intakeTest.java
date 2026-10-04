package org.firstinspires.ftc.teamcode.DriveTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class intakeTest extends OpMode {
    DcMotor intake;
    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intake");
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger >0){
            intake.setPower(-gamepad1.right_trigger);
        }else{
            intake.setPower(0);
        }

    }
}
