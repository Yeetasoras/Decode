package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private TelemetryManager telemetry;
    private Intake() {

    }
    private boolean powered = true;



    private final MotorEx intakeMotor = new MotorEx("intakeMotor")
            .floatMode()
            .reversed();

    private double power = 0;

    public Command spinUp = new InstantCommand(() -> power = 1).requires(this);
    public Command cutPower = new InstantCommand(() -> power = 0).requires(this);

    @Override
    public void initialize() {
        telemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void periodic() {
        intakeMotor.setPower(power);

    }

}
