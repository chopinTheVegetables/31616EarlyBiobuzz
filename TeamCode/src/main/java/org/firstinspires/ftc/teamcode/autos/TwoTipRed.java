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
import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
@Autonomous
public class TwoTipRed extends OpMode {
    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose start = poseFactory.of(54.8898, 11.8826, 90);
    private final Pose path1 = poseFactory.of(7.0075, 9.7966, -177.5054);
    private final Pose penStroke = poseFactory.of(55.1815, 118.1662, -90);
    private final Pose penStrokeThrough1 = poseFactory.of(11.8777, 25.0122, 0);
    private final Pose penStrokeThrough2 = poseFactory.of(16.7479, 40.2278, 0);
    private final Pose penStrokeThrough3 = poseFactory.of(21.6181, 55.4434, 0);
    private final Pose penStrokeThrough4 = poseFactory.of(24.1644, 71.0219, 0);
    private final Pose penStrokeThrough5 = poseFactory.of(24.1644, 86.9979, 0);
    private final Pose penStrokeThrough6 = poseFactory.of(27.15, 102.5817, 0);
    private final Pose penStrokeThrough7 = poseFactory.of(36.8391, 115.0262, 0);
    private final Pose penStrokeSegment1Start = poseFactory.of(55.1815, 118.1662, 180);
    private final Pose penStrokeSegment1End = poseFactory.of(55.1815, 118.1662, 90);
    private final Pose penStrokeSegment3Start = poseFactory.of(55.1815, 118.1662, 44.7588);
    private final Pose penStrokeSegment3End = poseFactory.of(55.1815, 118.1662, -90);
    private final Pose point3 = poseFactory.of(10.3147, 118.0543, 0);
    private final Pose point3Segment1Start = poseFactory.of(10.3147, 118.0543, 0);
    private final Pose point3Segment1End = poseFactory.of(10.3147, 118.0543, 0);

    public Path path1path() {
        return Paths.line(start, path1).tangent();
    }

    public Path penStrokepath() {
        return Paths.through(path1, penStrokeThrough1, penStrokeThrough2, penStrokeThrough3, penStrokeThrough4, penStrokeThrough5, penStrokeThrough6, penStrokeThrough7, penStroke).heading(Interpolator.piecewise().until(0.3884, Interpolator.linear(penStrokeSegment1Start, penStrokeSegment1End)).until(0.8233, Interpolator.tangent).until(1, Interpolator.linear(penStrokeSegment3Start, penStrokeSegment3End)));
    }

    public Path path3path() {
        return Paths.line(penStroke, point3).heading(Interpolator.piecewise().until(1, Interpolator.linear(point3Segment1Start, point3Segment1End)));
    }
    TurretSubsystem turret;
    IntakeSubsystem intake;
    Follower f;
    ElapsedTime timer;
    Servo gate;
    public int state;

    @Override
    public void init() {
        state = 0;
        f = Constants.create(hardwareMap);
        f.setPose(start);
        turret = new TurretSubsystem(hardwareMap, f, RobotConfig.Alliance.RED);
        intake = new IntakeSubsystem(hardwareMap);
        timer = new ElapsedTime();
        gate = hardwareMap.get(Servo.class, "gate");
        gate.setPosition(0.5);
    }
    @Override
    public void start(){
        timer.reset();
    }
    @Override
    public void loop() {
        switch ((state)){
            case 0:
                //spin up
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
                //shoot balls
                gate.setPosition(1);
                intake.setPower(0.5);
                //stop shooting after timer
                if(timer.seconds() > 5){
                    state = 2;
                    gate.setPosition(0.5);
                    intake.setPower(0);
                    break;
                }else{
                    break;
                }
            case 2:
                //intake balls from corner
                intake.setPower(0.5);
                f.follow(path1path());
                state = 3;
                break;
                //go to the hive after intakiing
            case 3:
                intake.setPower(0.2);
                if(!f.following()){
                    f.follow(penStrokepath());
                    state = 4;
                    break;
                }else{
                    break;
                }
                //check if path is stopped
            case 4:
                if(!f.following()){
                    intake.setPower(0);
                    timer.reset();
                    state = 5;
                    break;
                }else{
                    break;
                }
            case 5:
                gate.setPosition(1);
                intake.setPower(0.5);
                if(timer.seconds() > 4){
                    terminateOpModeNow();
                }else{
                    break;
                }

        }
        f.update();
        turret.calculatePowers();
    }
}




