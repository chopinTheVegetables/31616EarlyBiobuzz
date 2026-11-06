package org.firstinspires.ftc.teamcode.autos;
import static com.pedropathing.api.Paths.*;
import static org.firstinspires.ftc.teamcode.utils.RobotConfig.allianceKey;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.utils.RobotConfig;

public class BlueParkAuto extends OpMode {
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(108.6648, 5.9597, 90);
    private final Pose path1 = poseFactory.of(132.3232, 26.583, 90);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }
    Follower f;
    int state = 0;


    @Override
    public void init() {
        f = Constants.create(hardwareMap);
        f.setPose(new Pose(108.66476521164023, 5.959659529320994, 90));
        blackboard.put(allianceKey, RobotConfig.Alliance.BLUE);
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
                f.follow(path1());
                state = 1;
                break;
            case 1:
                if(!f.isBusy()){
                    terminateOpModeNow();
                }
        }

    }
}
