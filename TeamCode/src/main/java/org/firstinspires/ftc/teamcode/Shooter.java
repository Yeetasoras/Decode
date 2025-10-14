package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;
import dev.nextftc.ftc.Gamepads;

public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();
    private Telemetry telemetry;
    private Shooter() {

    }
    private boolean powered = true;
    private final ControlSystem flywheelControlSystem = ControlSystem.builder()
            .velPid(1)
            .build();


    private final MotorEx flywheelMotor = new MotorEx("flywheelMotor")
            .floatMode()
            .reversed();

    public Command spinUp = new ParallelGroup(
            new InstantCommand(() -> powered = true),
            new RunToVelocity(flywheelControlSystem, 2000).requires(flywheelMotor)
    );

    public Command cutPower = new InstantCommand(() -> powered = false);

    @Override
    public void initialize() {
        telemetry = ActiveOpMode.telemetry();
    }


    @Override
    public void periodic() {
        if (powered) {
            telemetry.addData("Flywheel Speed", flywheelMotor.getVelocity());

            flywheelMotor.setPower(
                    flywheelControlSystem.calculate(
                            flywheelMotor.getState()
                    )
            );
        } else {
            flywheelMotor.setPower(0);
        }
    }

}
