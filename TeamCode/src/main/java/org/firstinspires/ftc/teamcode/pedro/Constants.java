package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("fl");
        c.frontRightName.set("fr");
        c.backLeftName.set("bl");
        c.backRightName.set("br");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(2.7521115400659757);
        c.yPodOffset.set(6.086462426373339);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.013939939382024266);
                Controller secondaryTranslationalForward = Controller.proportional(0.005150435119617578);
                Controller primaryTranslationalLateral = Controller.proportional(0.03718176130460323);
                Controller secondaryTranslationalLateral = Controller.proportional(0.013737667287091007);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.001741201928662021));
                c.brake.set(Controller.proportionalFeedforward(0.001480021639362718));

                c.headingFeedback.set(Controller.proportional(0.1994914400561575));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.006466059489132746, 0.00030082264337217292));

                c.linearBrakeCoefficients.set(Matrix.diag(0.007362471496467779, 0.004422957524598113));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0001348416672506906, 0.00017909406313138282));

                c.maxAchievableForwardVelocity.set(30.0);
                c.maxAchievableStrafeVelocity.set(20.0);
                c.naturalForwardDeceleration.set(30.0);
                c.naturalStrafeDeceleration.set(30.0);
            }
    );
}