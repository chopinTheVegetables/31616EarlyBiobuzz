package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;

@Autonomous
public class OneTipBlue extends OpMode {
    TurretSubsystem turret;
    IntakeSubsystem intake;
    Follower f;
    ElapsedTime timer;
    Servo gate;

    public int state;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    // Rotated 180 degrees around field center (144 - x, 144 - y, heading + 180)
    private final Pose start = poseFactory.of(80.4, 106.55, -90);
    @Override
    public void init() {
        state = 0;
        f = Constants.create(hardwareMap);
        f.setPose(start);
        turret = new TurretSubsystem(hardwareMap, f, RobotConfig.Alliance.BLUE);
        intake = new IntakeSubsystem(hardwareMap);
        timer = new ElapsedTime();
        timer.reset();
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setPosition(0.5);
    }

    @Override
    public void loop() {
        switch (state) {
            case 0:
                turret.setBothFlywheelsEven(2900);
                gate.setPosition(0.5);
                if (timer.seconds() > 2) {
                    timer.reset();
                    state = 1;
                    break;
                } else {
                    break;
                }
            case 1:
                gate.setPosition(1);
                intake.setPower(0.45);
                if (timer.seconds() > 3) {
                    state = 2;
                    gate.setPosition(0.5);
                    intake.setPower(0);
                    break;
                } else {
                    break;
                }
            case 2:
                terminateOpModeNow();
                state = 3;
                break;
            /*case 3:
                if (!f.following()) {
                    terminateOpModeNow();
                    break;
                }*/
        }
        f.update();
        turret.calculatePowers();
    }
}
