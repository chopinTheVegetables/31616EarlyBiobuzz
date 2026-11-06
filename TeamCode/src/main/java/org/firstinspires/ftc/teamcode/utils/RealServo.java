package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.Servo;

public class RealServo {
    private double range; //degrees
    private Servo servo;
    private double conversionFactor;
    public RealServo(Servo servo, double range){
        this.range = range;
        this.servo = servo;
        this.conversionFactor = 1.0/range;
    }
    public void setPositionDegrees(double degrees){
        servo.setPosition(degrees*conversionFactor);

    }
    public double getPosition(){
        return servo.getPosition();
    }


}
