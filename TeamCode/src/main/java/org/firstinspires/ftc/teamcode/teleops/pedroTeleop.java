package org.firstinspires.ftc.teamcode.teleops;

import static org.firstinspires.ftc.teamcode.utils.robotConfig.intakeName;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.LLdetector;

@TeleOp
public class pedroTeleop extends OpMode {
    Path pathGenerated;
    DcMotor intake;
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    double lastIntakePower;
    double intakePower;
    double intakePowerThreshold = 0.05;
    LLdetector LL;
    boolean driveLocked = true;
    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        intake = hardwareMap.get(DcMotor.class, intakeName);
        LL = new LLdetector(hardwareMap, follower);
    }

    @Override
    public void loop() {
        if(gamepad1.leftBumperWasReleased()){
            driveLocked = !driveLocked;
        }
        if(!driveLocked) {
            DrivePowers powers = ManualDrive.fieldCentric(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    follower.pose().heading()
            );
            follower.manual(powers);
            follower.update();
        }
        ///INTAKE -----
        intakePower = 0.0;
        // read trigger inputs

        if (gamepad1.right_trigger > 0.1) {
            intakePower = -gamepad1.right_trigger;
        }else if (gamepad1.left_trigger > 0.2) {
            intakePower = gamepad1.left_trigger/2;
        }
        // nice logic to be nice on battery
        if (Math.abs(intakePower) > Math.abs(lastIntakePower) + intakePowerThreshold + 0.05) {
            double modifier = Math.signum(intakePower) * intakePowerThreshold;
            intakePower = lastIntakePower + modifier;
            telemetry.addData("limiting...","");
            telemetry.update();
        }

        telemetry.addData("robot", follower.pose());
        telemetry.update();
        //spin intake
        intake.setPower(intakePower);
        lastIntakePower = intakePower;
        ///INTAKE END-----

    }

}
