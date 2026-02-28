package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class AuxFeeder implements Subsystem {

    public static final AuxFeeder INSTANCE = new AuxFeeder();
    private Telemetry telemetry;
    private AuxFeeder() {}

    private double power = 0;


    private final MotorEx AuxFeederMotor = new MotorEx("AuxFeederMotor")
            .brakeMode();

    public Command spinUp = new InstantCommand(() -> power = 1).requires(this);
    public Command cutPower = new InstantCommand(() -> power = 0).requires(this);

    @Override
    public void initialize() {
        telemetry = ActiveOpMode.telemetry();
    }


    @Override
    public void periodic() {

        AuxFeederMotor.setPower(power);

    }
}
