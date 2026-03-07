package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import dev.nextftc.bindings.Button;
import dev.nextftc.control.filters.LowPassFilter;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private TelemetryManager telemetry;
    private Intake() {

    }

    private static double stuckDraw = 5000;
    private double currentDraw = 0;
    private LowPassFilter currentFilter = new LowPassFilter(0.9);

    private boolean powered = true;



    private final MotorEx intakeMotor = new MotorEx("intakeMotor")
            .floatMode()
            .reversed();

    private double power = 0;

    public Command spinUp = new InstantCommand(() -> power = 1).requires(this);
    public Command cutPower = new InstantCommand(() -> power = 0).requires(this);

    public Command pushOut = new InstantCommand(() -> power = -0.4);


    public Button intakeStuck = new Button(() -> currentDraw > stuckDraw);


    @Override
    public void initialize() {
        telemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void periodic() {
        currentDraw = currentFilter.filter(intakeMotor.getMotor().getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("Intake Current (mA)", currentDraw);

        if (intakeStuck.get()) {
            telemetry.addLine("INTAKE STUCK");
        }

        intakeMotor.setPower(power);

    }

}
