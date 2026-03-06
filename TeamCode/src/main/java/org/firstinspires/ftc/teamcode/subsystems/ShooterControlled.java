package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.rev.RevColorSensorV3;

import static dev.nextftc.bindings.Bindings.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.nextftc.bindings.Button;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class ShooterControlled implements Subsystem {
    public static final ShooterControlled INSTANCE = new ShooterControlled();
    private TelemetryManager telemetry;
    private ShooterControlled() {

    }

    //Ball detection stuff
    private RevColorSensorV3 ballColour = ActiveOpMode.hardwareMap().get(RevColorSensorV3.class, "ballColour");
    private boolean ballInPlace = false;
    public Button ballDetected = button(() -> ballInPlace);

    public static double ballDetectionDistance = 75;
    public static double ballDetectionAlpha = 150;


    //Flywheel control system
    public double velocity = 0;

    public static PIDCoefficients flywheelPIDCoef = new PIDCoefficients(-0.0008, 0.0, 0.0);
    public static BasicFeedforwardParameters flywheelFFCoef = new BasicFeedforwardParameters(-0.000425, 0, -0.04);

    private static final ControlSystem flywheelControlSystem = ControlSystem.builder()
            .velPid(flywheelPIDCoef)
            .basicFF(flywheelFFCoef)
            .build();


    private final MotorEx flywheelMotor = new MotorEx("flywheelMotor")
            .floatMode();


    //Flywheel commands
    public Command spinUp = new InstantCommand(() -> flywheelControlSystem.setGoal(new KineticState(0.0, 1500.0))).requires(this);

    public Command cutPower = new InstantCommand(() -> flywheelControlSystem.setGoal(new KineticState(0.0, 0.0))).requires(this);


    @Override
    public void initialize() {
        telemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        flywheelControlSystem.setGoal(new KineticState(0.0, 0.0));
    }

    @Override
    public void periodic() {
        if (ActiveOpMode.isStarted()) {
            velocity = -flywheelMotor.getVelocity();

            flywheelMotor.setPower(flywheelControlSystem.calculate(new KineticState(
                    flywheelMotor.getCurrentPosition(),
                    -flywheelMotor.getVelocity()))
            );


            ballInPlace = ballColour.getDistance(DistanceUnit.MM) < ballDetectionDistance && ballColour.alpha() > ballDetectionAlpha;
            telemetry.addLine(String.format("Dist (mm), Alpha, Detected? %6.1f %6.1f", ballColour.getDistance(DistanceUnit.MM), ballColour.alpha(), ballInPlace));

            telemetry.addData("Flywheel Velocity", velocity);
        }
    }

}
