package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name = "TeleOp with AprilTag Assist")
public class AprilTagTeleOp extends LinearOpMode {

    Robot robot;

    // Target tags for different game elements
    private static final int SCORING_TAG_ID = 5;
    private static final int LOADING_TAG_ID = 3;

    // Auto-assist mode
    private boolean autoAssistEnabled = false;
    private boolean previousA = false;

    @Override
    public void runOpMode() {

        robot = new Robot(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Controls", "A = Toggle Auto-Assist");
        telemetry.addData("", "Y = Auto-aim at scoring zone");
        telemetry.addData("", "X = Auto-aim at loading zone");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // === TOGGLE AUTO-ASSIST ===
            if (gamepad1.a && !previousA) {
                autoAssistEnabled = !autoAssistEnabled;
                if (autoAssistEnabled) {
                    gamepad1.rumble(200); // Haptic feedback
                }
            }
            previousA = gamepad1.a;

            // === AUTO-AIM BUTTONS ===
            if (gamepad1.y) {
                // Auto-aim at scoring zone (tag 5)
                autoAimAtTag(SCORING_TAG_ID);
            } else if (gamepad1.x) {
                // Auto-aim at loading zone (tag 3)
                autoAimAtTag(LOADING_TAG_ID);
            } else {
                // Manual control with optional assist
                if (autoAssistEnabled) {
                    driveWithAutoAssist();
                } else {
                    driveManually();
                }
            }

            // === SHOOTER CONTROL ===
            if (gamepad2.right_bumper) {
                robot.flywheel1.setPower(1.0);
                robot.flywheel2.setPower(1.0);
            } else {
                robot.flywheel1.setPower(0);
                robot.flywheel2.setPower(0);
            }

            // === DISPLAY TELEMETRY ===
            displayTelemetry();

            telemetry.update();
        }

        robot.closeVisionPortal();
    }

    /**
     * AUTO-AIM: Automatically align robot with target AprilTag
     */
    private void autoAimAtTag(int tagId) {
        AprilTagDetection tag = robot.getAprilTagById(tagId);

        if (tag == null) {
            telemetry.addData("Auto-Aim", "Tag %d not visible", tagId);
            // Slowly rotate to search
            turnInPlace(0.2);
            return;
        }

        double bearing = tag.ftcPose.bearing;
        double distance = tag.ftcPose.range;

        telemetry.addData("Auto-Aim", "Target: Tag %d", tagId);
        telemetry.addData("Distance", "%.1f inches", distance);
        telemetry.addData("Bearing", "%.1f degrees", bearing);

        // Calculate correction powers
        double turnPower = bearing / 30.0; // Scale bearing to power
        turnPower = Math.max(-0.5, Math.min(0.5, turnPower)); // Clamp

        // If well aligned, move forward/backward to optimal distance
        if (Math.abs(bearing) < 5.0) {
            double optimalDistance = 24.0; // inches
            double distanceError = distance - optimalDistance;
            double drivePower = distanceError / 30.0;
            drivePower = Math.max(-0.4, Math.min(0.4, drivePower));

            moveRobot(drivePower, turnPower);

            // Indicate when locked on target
            if (Math.abs(distanceError) < 3.0) {
                telemetry.addData("Status", "LOCKED ON TARGET!");
                gamepad1.rumble(100);
            }
        } else {
            // Just rotate to align
            moveRobot(0, turnPower);
        }
    }

    /**
     * DRIVE WITH AUTO-ASSIST: Manual control with AprilTag course correction
     */
    private void driveWithAutoAssist() {
        // Get manual inputs
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        // Check if any scoring tags are visible
        AprilTagDetection scoringTag = robot.getAprilTagById(SCORING_TAG_ID);

        if (scoringTag != null && Math.abs(drive) > 0.1) {
            // Driver is moving forward - provide gentle course correction
            double bearing = scoringTag.ftcPose.bearing;

            // Only assist if bearing is significant
            if (Math.abs(bearing) > 10) {
                double correction = bearing / 60.0; // Gentle correction
                turn += correction * 0.5; // Add 50% of correction to manual turn

                telemetry.addData("Assist", "Correcting %.1f degrees", bearing);
            }
        }

        // Apply power with assist
        moveRobot(drive, turn);
    }

    /**
     * MANUAL DRIVE: Pure manual control
     */
    private void driveManually() {
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        moveRobot(drive, turn);
    }

    /**
     * DISTANCE WARNING: Warn driver if too close to obstacles
     */
    private void checkProximityWarning() {
        for (AprilTagDetection tag : robot.getAprilTagDetections()) {
            if (tag.ftcPose.range < 10.0) {
                telemetry.addData("WARNING", "Too close to tag %d!", tag.id);
                gamepad1.rumble(50);
                break;
            }
        }
    }

    /**
     * DISPLAY COMPREHENSIVE TELEMETRY
     */
    private void displayTelemetry() {
        // Auto-assist status
        telemetry.addData("Auto-Assist", autoAssistEnabled ? "ENABLED" : "DISABLED");

        // AprilTag info
        telemetry.addData("Tags Visible", robot.getAprilTagCount());

        // Show closest tag
        AprilTagDetection closestTag = getClosestTag();
        if (closestTag != null) {
            telemetry.addData("Closest Tag", "%d at %.1f inches",
                    closestTag.id, closestTag.ftcPose.range);
        }

        // Show scoring tag status
        if (robot.isAprilTagDetected(SCORING_TAG_ID)) {
            AprilTagDetection tag = robot.getAprilTagById(SCORING_TAG_ID);
            telemetry.addData("Scoring Zone", "%.1f\" @ %.1f°",
                    tag.ftcPose.range, tag.ftcPose.bearing);
        } else {
            telemetry.addData("Scoring Zone", "Not visible");
        }

        // Proximity warning
        checkProximityWarning();

        // Controls reminder
        telemetry.addData("", "---Controls---");
        telemetry.addData("Y", "Auto-aim Scoring");
        telemetry.addData("X", "Auto-aim Loading");
        telemetry.addData("A", "Toggle Assist");
    }

    /**
     * HELPER: Get the closest visible AprilTag
     */
    private AprilTagDetection getClosestTag() {
        AprilTagDetection closest = null;
        double minDistance = Double.MAX_VALUE;

        for (AprilTagDetection tag : robot.getAprilTagDetections()) {
            if (tag.ftcPose.range < minDistance) {
                minDistance = tag.ftcPose.range;
                closest = tag;
            }
        }

        return closest;
    }

    // ===== MOTOR CONTROL METHODS =====
    // Implement these based on your drive train configuration

    /**
     * Move robot with drive and turn power
     */
    private void moveRobot(double drive, double turn) {
        // Example for tank/arcade drive
        // double leftPower = drive + turn;
        // double rightPower = drive - turn;

        // Example for mecanum drive
        // Implement based on your actual drive system

        telemetry.addData("Drive", "%.2f", drive);
        telemetry.addData("Turn", "%.2f", turn);
    }

    /**
     * Rotate robot in place
     */
    private void turnInPlace(double power) {
        moveRobot(0, power);
    }
}