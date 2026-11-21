package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.RobotConfig.gateServoName;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.intakeLName;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.intakeMotorEncoderRes;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.intakeRName;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class IntakeSubsystem {
    ///PLUG ENCODER INTO RIGHT PORT
    DcMotorEx rIntake;
    DcMotorEx lIntake;
    Servo gateServo;
    private double p = 0;
    private double i = 0;
    private double d = 0;
    private double targetRPM = 0;
    public IntakeSubsystem(HardwareMap hardwareMap){
        rIntake = hardwareMap.get(DcMotorEx.class, intakeRName);
        lIntake = hardwareMap.get(DcMotorEx.class, intakeLName);
        lIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        gateServo = hardwareMap.get(Servo.class, gateServoName);
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
    public void closeGate(){
        gateServo.setPosition(0.5);
    }
    public void openGate(){
        gateServo.setPosition(1);
    }


}
