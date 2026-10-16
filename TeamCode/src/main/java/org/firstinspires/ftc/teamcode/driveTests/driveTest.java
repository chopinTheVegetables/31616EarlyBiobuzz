package org.firstinspires.ftc.teamcode.driveTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOpDrive")
public class driveTest extends OpMode {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor intake;

    @Override
    public void init() {
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");

        intake = hardwareMap.get(DcMotor.class, "intake");
    }

    public void loop() {
        double leftPower = gamepad1.left_stick_y;
        double rightPower = -gamepad1.right_stick_y;
        fl.setPower(leftPower);
        fr.setPower(rightPower);
        bl.setPower(leftPower);
        br.setPower(rightPower);
        if(gamepad1.right_trigger != 0) {
            intake.setPower(-gamepad1.right_trigger);
        }else if(gamepad1.left_trigger != 0){
            intake.setPower(-gamepad1.left_trigger);
        }else{
            intake.setPower(0);
        }


        // Math.toDegrees() is a built-in java method
    }

}
