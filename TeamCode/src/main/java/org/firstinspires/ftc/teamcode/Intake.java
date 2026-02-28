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

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Telemetry telemetry;
    private Intake() {

    }
    private boolean powered = true;



    private final MotorEx intakeMotor = new MotorEx("intakeMotor")
            .floatMode()
            .reversed();

    private double power = 0;

    public Command spinUp = new InstantCommand(() -> power = 1).requires(this);
    public Command cutPower = new InstantCommand(() -> power = 0).requires(this);

    public Command changeMotion = new InstantCommand(() -> intakeMotor.reverse()).requires(this);


    @Override
    public void periodic() {
        intakeMotor.setPower(power);

    }

}
