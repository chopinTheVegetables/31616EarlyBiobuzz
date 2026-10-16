package org.firstinspires.ftc.teamcode.subsystems;


import static org.firstinspires.ftc.teamcode.utils.robotConfig.flywheelMotorName;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.turretRotName;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TurretSubsystem {
    DcMotorEx flywheel;
    DcMotor turretRotation;

    public TurretSubsystem(HardwareMap hardwareMap){
        flywheel = hardwareMap.get(DcMotorEx.class, flywheelMotorName);
        turretRotation = hardwareMap.get(DcMotor.class, turretRotName);
    }



}
