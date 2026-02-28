package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class Feeder implements Subsystem {

    public static final Feeder INSTANCE = new Feeder();
    private Telemetry telemetry;
    private Feeder() {}

    private double power = 0;


    private final MotorEx pusherMotor = new MotorEx("pusherMotor")
            .brakeMode();

    public Command spinUp = new InstantCommand(() -> power = 1).requires(this);
    public Command cutPower = new InstantCommand(() -> power = 0).requires(this);

    @Override
    public void initialize() {
        telemetry = ActiveOpMode.telemetry();
    }


    @Override
    public void periodic() {

            pusherMotor.setPower(power);

    }
}
