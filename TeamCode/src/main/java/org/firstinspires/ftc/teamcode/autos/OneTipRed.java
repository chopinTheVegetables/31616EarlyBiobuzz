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

    private final Pose start = poseFactory.of(54.8898, 11.8826, 90);
    private final Pose path1 = poseFactory.of(8.2289, 91.7082, 90);

    public Path path1path() {
        return Paths.line(start, path1).linear(start, path1);

    }
    @Override
    public void init() {
        state = 2;
        f = Constants.create(hardwareMap);
        f.setPose(new Pose(55,9,90));
        turret = new TurretSubsystem(hardwareMap, f, RobotConfig.Alliance.RED);
        intake = new IntakeSubsystem(hardwareMap);
        timer = new ElapsedTime();
        timer.reset();
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setPosition(0.5);
    }

    @Override
    public void loop() {
        turret.setBothFlywheelsEven(2700);
        switch ((state)){
            /*case 0:
                gate.setPosition(0.5);
                turret.setBothFlywheelsEven(2700);
                telemetry.addData("speed up", "go");
                telemetry.update();
                if(timer.seconds()>6){
                    timer.reset();
                    state = 1;
                    break;
                }else{
                    turret.setBothFlywheelsEven(2700);
                    break;
                }
            case 1:
                gate.setPosition(1);
                intake.setPower(0.3);
                if(timer.seconds() > 4){
                    state = 2;
                    intake.setPower(0);
                    break;
                }else{
                    break;
                }*/
            case 2:
                f.follow(path1path());
                if(timer.seconds() >3.5){
                    terminateOpModeNow();
                    break;
                }else{
                    break;
                }
        }
        f.update();
        turret.calculatePowers();
    }
}
