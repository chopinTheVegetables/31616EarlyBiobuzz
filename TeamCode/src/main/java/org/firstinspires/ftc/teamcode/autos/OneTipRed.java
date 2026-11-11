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
public class OneTipRed extends OpMode {
    TurretSubsystem turret;
    IntakeSubsystem intake;
    Follower f;
    ElapsedTime timer;
    Servo gate;
    public int state;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(55, 9, 90);
    private final Pose path1 = poseFactory.of(11, 99, 90);
    private final Pose path1Control1 = poseFactory.of(29.1645, 32.3055, 0);

    public Path path1path() {
        return Paths.curve(start, path1Control1, path1).constant(path1);
    }


    @Override
    public void init() {
        state = 0;
        f = Constants.create(hardwareMap);
        f.setPose(start);
        turret = new TurretSubsystem(hardwareMap, f, RobotConfig.Alliance.RED);
        intake = new IntakeSubsystem(hardwareMap);
        timer = new ElapsedTime();
        timer.reset();
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setPosition(0.5);
    }

    @Override
    public void loop() {
        switch ((state)){
            case 0:
                turret.setBothFlywheelsEven(2900);
                gate.setPosition(0.5);
                if(timer.seconds()>2){
                    timer.reset();
                    state = 1;
                    break;
                }else{
                    break;
                }
            case 1:
                gate.setPosition(1);
                intake.setPower(0.5);
                if(timer.seconds() > 3){
                    state = 2;
                    gate.setPosition(0.5);
                    intake.setPower(0);
                    break;
                }else{
                    break;
                }
            case 2:
                f.follow(path1path());
                state = 3;
                break;
            case 3:
                if(!f.isBusy()){
                    terminateOpModeNow();
                    break;
                }
        }
        f.update();
        turret.calculatePowers();
    }
}
