package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.utils.RobotConfig.allianceKey;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;
@Autonomous
public class BlueCloseDeadAuto extends OpMode {
    Follower f;
    @Override
    public void init() {
        f = Constants.create(hardwareMap);
        f.setPose(new Pose(86, 133.9, -90));
        blackboard.put(allianceKey, RobotConfig.Alliance.BLUE);
    }

    @Override
    public void loop() {

    }
}
