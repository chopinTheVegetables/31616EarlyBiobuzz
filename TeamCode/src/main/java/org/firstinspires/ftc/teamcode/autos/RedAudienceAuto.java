package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;

import static org.firstinspires.ftc.teamcode.autos.RedAudienceAuto.AutoState.DRIVE_TO_FIRE2;
import static org.firstinspires.ftc.teamcode.autos.RedAudienceAuto.AutoState.FIRE2;
import static org.firstinspires.ftc.teamcode.autos.RedAudienceAuto.AutoState.INIT;
import static org.firstinspires.ftc.teamcode.autos.RedAudienceAuto.AutoState.PARK;
import static org.firstinspires.ftc.teamcode.autos.RedAudienceAuto.AutoState.PICKUP1;
import static org.firstinspires.ftc.teamcode.autos.RedAudienceAuto.AutoState.ROTATE;
import static org.firstinspires.ftc.teamcode.utils.robotConfig.allianceKey;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptBlackboard;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.utils.robotConfig;

public class RedAudienceAuto extends OpMode {
        public enum AutoState {
            INIT, FIRE1, ROTATE, PICKUP1, DRIVE_TO_FIRE2, FIRE2, PARK
        }
        AutoState state = INIT;
        private final PoseFactory poseFactory = PoseFactory.degrees();
        Follower follower;

        private final Pose afterFire1 = poseFactory.of(56, 8, 90);
        private final Pose turn1 = poseFactory.of(48, 8, 180);
        private final Pose turn1Control1 = poseFactory.of(52.7006, 26.0236, 0);
        private final Pose pickup1 = poseFactory.of(9, 8, 180);
        private final Pose fire2 = poseFactory.of(34.1127, 122.559, 0);
        private final Pose fire2Control1 = poseFactory.of(29.7285, 21.8981, 0);
        private final Pose fire2Segment1Start = poseFactory.of(34.1127, 122.559, 180);
        private final Pose fire2Segment1End = poseFactory.of(34.1127, 122.559, 70);
        private final Pose fire2Segment3Start = poseFactory.of(34.1127, 122.559, 86.194);
        private final Pose fire2Segment3End = poseFactory.of(34.1127, 122.559, 0);
        private final Pose point4 = poseFactory.of(9.2742, 97.7812, 0);

        public Path turn1PATH() {
            return curve(afterFire1, turn1Control1, turn1).linear(afterFire1, turn1);
        }

        public Path pickup1PATH() {
            return line(turn1, pickup1).tangent();
        }

        public Path fire2PATH() {
            return curve(pickup1, fire2Control1, fire2).heading(Interpolator.piecewise().until(0.2113, Interpolator.linear(fire2Segment1Start, fire2Segment1End)).until(0.824, Interpolator.tangent).until(1, Interpolator.linear(fire2Segment3Start, fire2Segment3End)));
        }

        public Path parkPATH() {
            return line(fire2, point4).constant(point4);
        }

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
    }

    @Override
    public void loop() {
            follower.update();
            switch (state) {
                case FIRE1:
                    //TODO fire balls here
                    state = ROTATE;
                    break;
                case ROTATE:
                    follower.follow(turn1PATH());
                    if(!follower.isBusy()){
                        state = PICKUP1;
                    }
                    break;
                case PICKUP1:
                    //TODO SPIN INTAKE HERE
                    follower.follow(pickup1PATH());
                    if (!follower.isBusy()){
                        state = DRIVE_TO_FIRE2;
                    }
                    break;
                case DRIVE_TO_FIRE2:
                    follower.follow(fire2PATH());
                    if(!follower.isBusy()){
                        state = FIRE2;
                    }
                    break;
                case FIRE2:
                    //TODO FIRE POLLEN HERE
                    state = PARK;
                    break;
                case  PARK:
                    follower.follow(parkPATH());
                    if(!follower.isBusy()){
                        blackboard.put(allianceKey, robotConfig.Alliance.RED);
                        terminateOpModeNow();
                    }


            }
    }
}
