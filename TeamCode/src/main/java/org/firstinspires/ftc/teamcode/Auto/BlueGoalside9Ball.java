package org.firstinspires.ftc.teamcode.Auto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Feeder;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.ShooterControlled;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "Red Goalside - 9")
public class BlueGoalside9Ball extends NextFTCOpMode {

    private TelemetryManager panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    public BlueGoalside9Ball() {
        addComponents(
                new SubsystemComponent(ShooterControlled.INSTANCE, Feeder.INSTANCE, Intake.INSTANCE, Camera.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)
        );
    }

    private int currentState = 0;

    private void advance() {
        currentState += 1;

    }

    private Follower follower = PedroComponent.follower();

    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;
        public PathChain Path7;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(24.500, 128.000),
                                    new Pose(50.000, 100.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(135))
                    .build();

            Path2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(50.000, 100.000),
                                    new Pose(40.000, 84.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            Path3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(40.000, 84.000),
                                    new Pose(20.000, 84.000)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(180))
                    .build();

            Path4 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(20.000, 84.000),
                                    new Pose(50.000, 100.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            Path5 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(50.000, 100.000),
                                    new Pose(40.000, 60.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            Path6 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(40.000, 60.000),
                                    new Pose(20.000, 60.000)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(180))
                    .build();

            Path7 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(20.000, 60.000),
                                    new Pose(50.000, 100.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();
        }
    }



    private Paths paths = new Paths(follower);


    @Override
    public void onInit() {

    }


    @Override
    public void onStartButtonPressed() {
        advance();
    }

    @Override
    public void onUpdate() {

        switch (currentState) {
            case 1:
                //Drive to shooting spot
                follower.followPath(paths.Path1);
                ShooterControlled.INSTANCE.spinUp.schedule();

                //advance once follower finishes
                if (!follower.isBusy()) {
                    advance();
                }
            case 2:
                //Shoot ball
                Intake.INSTANCE.spinUp.schedule();
                Feeder.INSTANCE.spinUp.schedule();

                new SequentialGroup(
                        new Delay(2),
                        new InstantCommand(this::advance)
                );
            case 3:
                //drive to balls, all still spinning
                follower.followPath(paths.Path2);

                //advance once follower finishes
                if (!follower.isBusy()) {
                    advance();
                }
            case 4:
                follower.followPath(paths.Path3);

                //stop feeding when balls in
                if (ShooterControlled.INSTANCE.ballDetected.get()) {
                    Feeder.INSTANCE.cutPower.schedule();
                }

                if (!follower.isBusy()) {
                    advance();
                }

            case 5:
                follower.followPath(paths.Path4);

                Intake.INSTANCE.cutPower.schedule();


                if (!follower.isBusy()) {
                    advance();
                }

            case 6:
                //Shoot ball
                Intake.INSTANCE.spinUp.schedule();
                Feeder.INSTANCE.spinUp.schedule();

                new SequentialGroup(
                        new Delay(2),
                        new InstantCommand(this::advance)
                );

            case 7:
                //drive to balls, all still spinning
                follower.followPath(paths.Path5);

                //advance once follower finishes
                if (!follower.isBusy()) {
                    advance();
                }

            case 8:
                follower.followPath(paths.Path6);

                //stop feeding when balls in
                if (ShooterControlled.INSTANCE.ballDetected.get()) {
                    Feeder.INSTANCE.cutPower.schedule();
                }

                if (!follower.isBusy()) {
                    advance();
                }
            case 9:
                follower.followPath(paths.Path7);

                Intake.INSTANCE.cutPower.schedule();


                if (!follower.isBusy()) {
                    advance();
                }
            case 10:
                //Shoot ball
                Intake.INSTANCE.spinUp.schedule();
                Feeder.INSTANCE.spinUp.schedule();

                new SequentialGroup(
                        new Delay(2),
                        new InstantCommand(this::advance)
                );
        }

        panelsTelemetry.update(telemetry);
        telemetry.update();
    }
}