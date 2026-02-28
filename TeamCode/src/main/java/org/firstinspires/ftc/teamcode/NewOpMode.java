package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Feeder;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.ShooterControlled;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "Drive w/ Cam")
public class NewOpMode extends NextFTCOpMode {
    public NewOpMode() {
        addComponents(
                new SubsystemComponent(ShooterControlled.INSTANCE, Feeder.INSTANCE, Intake.INSTANCE, Camera.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
                //the instance only brings the one instance in and cannot change and only does it at one point
        );
    }

    // change the names and directions to suit your robot
    private final MotorEx frontLeftMotor = new MotorEx("frontLeftMotor").reversed().brakeMode();
    private final MotorEx backLeftMotor = new MotorEx("backLeftMotor").reversed().brakeMode();
    private final MotorEx frontRightMotor = new MotorEx("frontRightMotor").brakeMode();
    private final MotorEx backRightMotor = new MotorEx("backRightMotor").reversed().brakeMode();
    //private IMUEx imu = new IMUEx("imu", Direction.LEFT, Direction.UP).zeroed();

    @Override
    public void onInit() {

    }


    @Override
    public void onStartButtonPressed() {
        Command driverControlled  = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
                //new FieldCentric(imu)
        );

        driverControlled.schedule();
        //Intake.INSTANCE.spinUp.schedule();
        Gamepads.gamepad1().touchpad()
                        .whenBecomesTrue(Intake.INSTANCE.changeMotion)
                        .whenBecomesFalse(Intake.INSTANCE.changeMotion);


        Gamepads.gamepad1().dpadDown()
                        .whenBecomesTrue(Intake.INSTANCE.spinUp);


        Gamepads.gamepad1().dpadUp()
                        .whenBecomesTrue(Intake.INSTANCE.cutPower);

        Gamepads.gamepad1().triangle()
                .whenBecomesTrue(Feeder.INSTANCE.spinUp)
                .whenBecomesFalse(Feeder.INSTANCE.cutPower);


        Gamepads.gamepad1().cross()
                .whenBecomesTrue(ShooterControlled.INSTANCE.spinUp);

        Gamepads.gamepad1().circle()
                .whenBecomesTrue(ShooterControlled.INSTANCE.cutPower);



    }

    @Override
    public void onUpdate() {

        telemetry.update();
    }
}