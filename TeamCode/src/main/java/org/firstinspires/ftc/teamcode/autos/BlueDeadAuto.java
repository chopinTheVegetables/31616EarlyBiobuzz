package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.utils.RobotConfig.allianceKey;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.utils.RobotConfig;

public class BlueDeadAuto extends OpMode {
    @Override
    public void init() {
        blackboard.put(allianceKey, RobotConfig.Alliance.BLUE);
    }

    @Override
    public void loop() {

    }
}
