package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.utils.RobotConfig.allianceKey;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;
@Autonomous
public class RedCloseDeadAuto extends OpMode {
    Follower f;
    @Override
    public void init() {
        f = Constants.create(hardwareMap);
        f.setPose(new Pose(54,9, 90));
        blackboard.put(allianceKey, RobotConfig.Alliance.RED);
    }

    @Override
    public void loop() {

    }
}
