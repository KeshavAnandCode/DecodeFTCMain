package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "AprilTag Detection")
public class AprilTagDetection2 extends LinearOpMode {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {

        initAprilTag();

        // Display camera preview info
        telemetry.addData("Camera Preview", "Check Driver Station for stream");
        telemetry.addData("Status", "Initialized - Press START");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                displayAprilTagDetections();

                telemetry.update();
                sleep(20); // Reduce CPU load
            }
        }

        // Clean up resources
        if (visionPortal != null) {
            visionPortal.close();
        }
    }

    /**
     * Initialize the AprilTag processor and vision portal
     */
    private void initAprilTag() {
        // Create AprilTag processor with default settings
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create vision portal with webcam
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aprilTag
        );
    }

    /**
     * Display telemetry data for all detected AprilTags
     */
    private void displayAprilTagDetections() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Display info for each detected tag
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                // Known AprilTag with metadata
                telemetry.addLine(String.format("\n==== (ID %d) %s ====",
                        detection.id, detection.metadata.name));

                telemetry.addLine(String.format("XYZ: %6.1f %6.1f %6.1f  (inch)",
                        detection.ftcPose.x,
                        detection.ftcPose.y,
                        detection.ftcPose.z));

                telemetry.addLine(String.format("PRY: %6.1f %6.1f %6.1f  (deg)",
                        detection.ftcPose.pitch,
                        detection.ftcPose.roll,
                        detection.ftcPose.yaw));

                telemetry.addLine(String.format("RBE: %6.1f %6.1f %6.1f  (inch, deg, deg)",
                        detection.ftcPose.range,
                        detection.ftcPose.bearing,
                        detection.ftcPose.elevation));
            } else {
                // Unknown AprilTag (no metadata)
                telemetry.addLine(String.format("\n==== (ID %d) Unknown ====", detection.id));
                telemetry.addLine(String.format("Center: %6.0f %6.0f   (pixels)",
                        detection.center.x,
                        detection.center.y));
            }
        }

        // Display legend
        telemetry.addLine("\n----- Key -----");
        telemetry.addLine("XYZ = X (Right), Y (Forward), Z (Up) distance");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
    }
}