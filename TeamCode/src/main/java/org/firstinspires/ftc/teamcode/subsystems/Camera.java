package org.firstinspires.ftc.teamcode.subsystems;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.ftc.ActiveOpMode;

public class Camera implements Subsystem {
    public static final Camera INSTANCE = new Camera();
    private Telemetry telemetry;
    private Camera() {

    }

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    private boolean doUpdate = true;

    public List<AprilTagDetection> currentDetections;

    //20 - BLUE GOAL, 21 - GPP, 22 - PGP, 23 - PPG, 23 - RED GOAL
    public int obelisk = 0;
    public Angle bearingToGoal;


    public Command enable = new InstantCommand(() -> doUpdate = true).requires(this);

    public Command disable = new InstantCommand(() -> doUpdate = false).requires(this);


    @Override
    public void initialize() {
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)

                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(ActiveOpMode.hardwareMap().get(WebcamName.class, "Webcam"));

        builder.addProcessor(aprilTag);
        builder.enableLiveView(true);

        visionPortal = builder.build();
    }

    @Override
    public void periodic() {
        if (doUpdate) {
            currentDetections = aprilTag.getDetections();


            for (AprilTagDetection detection : currentDetections) {
                int id = detection.id;

                if (id == 20 || id == 24) {
                    bearingToGoal = Angle.fromDeg(detection.ftcPose.bearing);
                }

                if (id == 21 || id == 22 || id == 23) {
                    obelisk = id;
                }
            }

        }
    }

}
