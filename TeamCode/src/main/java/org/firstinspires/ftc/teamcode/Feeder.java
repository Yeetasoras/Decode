package org.firstinspires.ftc.teamcode;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Feeder implements Subsystem {
    public static final Feeder INSTANCE = new Feeder();
    private Feeder() {

    }

    private double power = 0;

    private final MotorEx pusherMotor = new MotorEx("pusherMotor")
            .floatMode()
            .reversed();

    public Command pushDownBalls = new InstantCommand(() -> power = -1).requires(this);


    @Override
    public void periodic() {
        pusherMotor.setPower(power);
    }

}
