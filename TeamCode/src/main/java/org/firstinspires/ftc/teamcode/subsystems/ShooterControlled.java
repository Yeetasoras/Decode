package org.firstinspires.ftc.teamcode.subsystems;


import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class ShooterControlled implements Subsystem {
    public static final ShooterControlled INSTANCE = new ShooterControlled();
    private Telemetry telemetry;
    private ShooterControlled() {

    }
    public double velocity = 0;
    private final ControlSystem flywheelControlSystem = ControlSystem.builder()
            .velPid(0.001, 0.0, 0.0)
            .basicFF(0.003, 0.08, 0.0)
            .build();


    private final MotorEx flywheelMotor = new MotorEx("flywheelMotor")
            .floatMode()
            .reversed();

    public Command spinUp = new InstantCommand(() -> flywheelControlSystem.setGoal(new KineticState(0.0, 1500.0))).requires(this);
    public Command cutPower = new InstantCommand(() -> flywheelControlSystem.setGoal(new KineticState(0.0, 0.0))).requires(this);


    @Override
    public void initialize() {
        flywheelControlSystem.setGoal(new KineticState(0.0, 0.0));
    }

    @Override
    public void periodic() {
        if (ActiveOpMode.isStarted()) {
            velocity = flywheelMotor.getVelocity();

            ActiveOpMode.telemetry().addData("Flywheel Velocity", "%.1f", velocity);

            flywheelMotor.setPower(flywheelControlSystem.calculate(new KineticState(
                    flywheelMotor.getCurrentPosition(),
                    flywheelMotor.getVelocity()))
            );
        }
    }

}
