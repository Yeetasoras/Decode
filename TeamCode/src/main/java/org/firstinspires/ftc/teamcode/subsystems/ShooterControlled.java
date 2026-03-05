package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class ShooterControlled implements Subsystem {
    public static final ShooterControlled INSTANCE = new ShooterControlled();
    private Telemetry telemetry;
    private ShooterControlled() {

    }
    public double velocity = 0;


    private static final ControlSystem flywheelControlSystem = ControlSystem.builder()
            .velPid(-0.0005, 0.0, 0.0)
            .basicFF(-0.000425, 0, -0.04)
            .build();


    private final MotorEx flywheelMotor = new MotorEx("flywheelMotor")
            .floatMode();

    public Command spinUp = new InstantCommand(() -> flywheelControlSystem.setGoal(new KineticState(0.0, 1800.0))).requires(this);
    public Command cutPower = new InstantCommand(() -> flywheelControlSystem.setGoal(new KineticState(0.0, 0.0))).requires(this);


    @Override
    public void initialize() {
        flywheelControlSystem.setGoal(new KineticState(0.0, 0.0));
    }

    @Override
    public void periodic() {
        if (ActiveOpMode.isStarted()) {
            velocity = -flywheelMotor.getVelocity();

            PanelsTelemetry.INSTANCE.getTelemetry().addData("Flywheel Velocity", velocity);

            flywheelMotor.setPower(flywheelControlSystem.calculate(new KineticState(
                    flywheelMotor.getCurrentPosition(),
                    -flywheelMotor.getVelocity()))
            );
        }
    }

}
