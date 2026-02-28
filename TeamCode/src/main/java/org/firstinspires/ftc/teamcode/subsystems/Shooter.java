package org.firstinspires.ftc.teamcode.subsystems;


import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

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

    private double power = 0;
    public Command spinUp = new InstantCommand(() -> power = 1).requires(this);
    public Command cutPower = new InstantCommand(() -> power = 0).requires(this);


    // From here is the changing power system up 10% and down 10%
    public Command upPower10 = new InstantCommand(() -> power += 0.1).requires(this);
    public Command downPower10 = new InstantCommand(() -> power -= 0.1).requires(this);

    //until here
    @Override
    public void periodic() {
        flywheelMotor.setPower(power);
    }

}
