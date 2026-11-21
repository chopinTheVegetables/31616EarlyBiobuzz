package org.firstinspires.ftc.teamcode.autos;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;

@Autonomous(name = "2tiptest", group = "Autonomous")
public class AutoPath extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(54.8898, 11.8826, 0);
    private final Pose path1 = poseFactory.of(7.0075, 9.7966, -177.5054);
    private final Pose penStroke = poseFactory.of(55.2145, 118.1662, -90);
    private final Pose penStrokeThrough1 = poseFactory.of(11.8777, 25.0122, 0);
    private final Pose penStrokeThrough2 = poseFactory.of(16.7479, 40.2278, 0);
    private final Pose penStrokeThrough3 = poseFactory.of(21.6181, 55.4434, 0);
    private final Pose penStrokeThrough4 = poseFactory.of(24.1644, 71.0219, 0);
    private final Pose penStrokeThrough5 = poseFactory.of(24.1644, 86.9979, 0);
    private final Pose penStrokeThrough6 = poseFactory.of(27.15, 102.5817, 0);
    private final Pose penStrokeThrough7 = poseFactory.of(36.8391, 115.0262, 0);
    private final Pose penStrokeSegment1Start = poseFactory.of(55.2145, 118.1662, 180);
    private final Pose penStrokeSegment1End = poseFactory.of(55.2145, 118.1662, 90);
    private final Pose penStrokeSegment3Start = poseFactory.of(55.2145, 118.1662, 44.7032);
    private final Pose penStrokeSegment3End = poseFactory.of(55.2145, 118.1662, -90);
    private final Pose point3 = poseFactory.of(10.3147, 118.0543, 0);
    private final Pose point3Segment1Start = poseFactory.of(10.3147, 118.0543, 0);
    private final Pose point3Segment1End = poseFactory.of(10.3147, 118.0543, 0);

    // Autonomous routine

    private int autoState = 0;
    public TurretSubsystem turret;
    public IntakeSubsystem intake;
    ElapsedTime timer;

    @Override
    public void runOpMode() {
        timer = new ElapsedTime();
        follower = Constants.create(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap);
        turret = new TurretSubsystem(hardwareMap, follower, RobotConfig.Alliance.RED);
        waitForStart();
        timer.reset();
        intake.closeGate();

        while (opModeIsActive()) {
            follower.update();
            turret.calculatePowers();
            switch (autoState){
                case 0:
                  turret.setBothFlywheelsEven(2700);
                  if(timer.seconds() > 1.5){
                      timer.reset();
                      autoState = 1;
                      break;
                  }else{
                      break;
                  }
                case 1:
                    intake.openGate();
                    intake.setPower(1);
                    if(timer.seconds() > 3){
                        intake.closeGate();
                        intake.setPower(0.4);
                        autoState = 2;
                        break;
                    }else{
                        break;
                    }
                /*case 2:
                    follower.follow(path1());
                    autoState = 3;
                    break;
                case 3:
                    if(!follower.following()){
                        terminateOpModeNow();
                    }*/

            }
            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.line(start, path1).tangent();
    }

    public Path penStroke() {
        return Paths.through(path1, penStrokeThrough1, penStrokeThrough2, penStrokeThrough3, penStrokeThrough4, penStrokeThrough5, penStrokeThrough6, penStrokeThrough7, penStroke).heading(Interpolator.piecewise().until(0.3884, Interpolator.linear(penStrokeSegment1Start, penStrokeSegment1End)).until(0.8233, Interpolator.tangent).until(1, Interpolator.linear(penStrokeSegment3Start, penStrokeSegment3End)));
    }

    public Path path3() {
        return Paths.line(penStroke, point3).heading(Interpolator.piecewise().until(1, Interpolator.linear(point3Segment1Start, point3Segment1End)));
    }
}
