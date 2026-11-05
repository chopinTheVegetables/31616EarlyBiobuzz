package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.robotConfig.intakeLName;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.intakeMotorEncoderRes;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.intakeRName;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class IntakeSubsystem {
    ///PLUG ENCODER INTO RIGHT PORT
    DcMotorEx rIntake;
    DcMotorEx lIntake;
    private double p = 0;
    private double i = 0;
    private double d = 0;
    private double targetRPM = 0;
    public IntakeSubsystem(HardwareMap hardwareMap){
        rIntake = hardwareMap.get(DcMotorEx.class, intakeRName);
        lIntake = hardwareMap.get(DcMotorEx.class, intakeLName);
        lIntake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setVel(double targetVel){
        targetRPM = targetVel;
        rIntake.setVelocity(targetVel*intakeMotorEncoderRes/60);
        lIntake.setPower(rIntake.getPower());
    }
    public void setPower(double power){
        rIntake.setPower(power);
        lIntake.setPower(power);
    }


}
