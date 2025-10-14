package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DifferentialArcadeDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "NextFTC TeleOp Program Java")
public class DriveOpMode extends NextFTCOpMode {
    public DriveOpMode() {
        addComponents(
                new SubsystemComponent(Shooter.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
                //the instance only brings the one instance in and cannot change and only does it at one point
        );
    }

    // change the names and directions to suit your robot
    private final MotorEx frontLeftMotor = new MotorEx("left").reversed();
    private final MotorEx frontRightMotor = new MotorEx("right");

    @Override
    public void onInit() {

    }


    @Override
    public void onStartButtonPressed() {
        Command driverControlled = new DifferentialArcadeDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().rightStickX().negate()
        );
        driverControlled.schedule();
        //Shooter.INSTANCE.spinUp.schedule();
        Gamepads.gamepad1().cross().whenBecomesTrue(Shooter.INSTANCE.spinUp);
        Gamepads.gamepad1().circle().whenBecomesFalse(Shooter.INSTANCE.cutPower);

        Gamepads.gamepad1().dpadDown().whenBecomesTrue(Feeder.INSTANCE.pushDownBalls);
    }
}