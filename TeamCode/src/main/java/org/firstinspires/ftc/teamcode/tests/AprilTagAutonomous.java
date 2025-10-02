package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name = "AprilTag Navigation Example")
public class AprilTagAutonomous extends LinearOpMode {

    Robot robot;

    // Target AprilTag ID for navigation
    private static final int TARGET_TAG_ID = 5;

    // Distance thresholds (in inches)
    private static final double TARGET_DISTANCE = 12.0;
    private static final double DISTANCE_TOLERANCE = 2.0;

    // Angle thresholds (in degrees)
    private static final double ANGLE_TOLERANCE = 5.0;

    @Override
    public void runOpMode() {

        robot = new Robot(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Target Tag", TARGET_TAG_ID);
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // Example 1: Search for and approach a specific AprilTag
            approachAprilTag(TARGET_TAG_ID);

            // Example 2: Align with AprilTag
            alignWithAprilTag(TARGET_TAG_ID);

            // Example 3: Navigate to multiple tags in sequence
            navigateToMultipleTags();

            // Example 4: Use AprilTag for position correction
            correctPositionUsingAprilTag();
        }

        robot.closeVisionPortal();
    }

    /**
     * EXAMPLE 1: Approach a specific AprilTag until within target distance
     */
    private void approachAprilTag(int tagId) {
        telemetry.addData("Action", "Approaching tag " + tagId);
        telemetry.update();

        while (opModeIsActive()) {
            AprilTagDetection tag = robot.getAprilTagById(tagId);

            if (tag == null) {
                // Tag not detected - search for it
                telemetry.addData("Status", "Searching for tag...");
                searchForTag();
            } else {
                // Tag detected - get distance
                double distance = tag.ftcPose.range;
                double bearing = tag.ftcPose.bearing;

                telemetry.addData("Tag Found", tagId);
                telemetry.addData("Distance", "%.1f inches", distance);
                telemetry.addData("Bearing", "%.1f degrees", bearing);

                // Check if we're close enough
                if (Math.abs(distance - TARGET_DISTANCE) < DISTANCE_TOLERANCE) {
                    telemetry.addData("Status", "Target reached!");
                    telemetry.update();
                    stopMotors();
                    break;
                }

                // Move toward tag
                if (distance > TARGET_DISTANCE) {
                    // Too far - move forward
                    moveTowardTag(tag);
                } else {
                    // Too close - move backward
                    moveAwayFromTag(tag);
                }
            }

            telemetry.update();
            sleep(50);
        }
    }

    /**
     * EXAMPLE 2: Align robot to face AprilTag directly
     */
    private void alignWithAprilTag(int tagId) {
        telemetry.addData("Action", "Aligning with tag " + tagId);
        telemetry.update();

        while (opModeIsActive()) {
            AprilTagDetection tag = robot.getAprilTagById(tagId);

            if (tag == null) {
                telemetry.addData("Status", "Tag lost - searching...");
                searchForTag();
            } else {
                double bearing = tag.ftcPose.bearing;
                double yaw = tag.ftcPose.yaw;

                telemetry.addData("Bearing", "%.1f degrees", bearing);
                telemetry.addData("Yaw", "%.1f degrees", yaw);

                // Check if aligned
                if (Math.abs(bearing) < ANGLE_TOLERANCE) {
                    telemetry.addData("Status", "Aligned!");
                    telemetry.update();
                    stopMotors();
                    break;
                }

                // Rotate to align
                if (bearing > ANGLE_TOLERANCE) {
                    // Tag is to the right - turn right
                    turnRight(0.3);
                } else if (bearing < -ANGLE_TOLERANCE) {
                    // Tag is to the left - turn left
                    turnLeft(0.3);
                }
            }

            telemetry.update();
            sleep(50);
        }
    }

    /**
     * EXAMPLE 3: Navigate to multiple AprilTags in sequence
     */
    private void navigateToMultipleTags() {
        int[] tagSequence = {1, 3, 5}; // Navigate to tags 1, 3, then 5

        for (int tagId : tagSequence) {
            telemetry.addData("Navigation", "Going to tag " + tagId);
            telemetry.update();

            // Wait until tag is detected
            while (opModeIsActive() && !robot.isAprilTagDetected(tagId)) {
                telemetry.addData("Searching", "Tag " + tagId);
                telemetry.update();
                searchForTag();
                sleep(100);
            }

            // Approach the tag
            approachAprilTag(tagId);

            // Perform action at this tag location
            performActionAtTag(tagId);

            sleep(1000); // Pause before next tag
        }
    }

    /**
     * EXAMPLE 4: Use AprilTag to correct robot position during autonomous
     */
    private void correctPositionUsingAprilTag() {
        // During autonomous, periodically check AprilTag for position correction

        AprilTagDetection tag = robot.getAprilTagById(TARGET_TAG_ID);

        if (tag != null) {
            // Get current position relative to tag
            double x = tag.ftcPose.x;  // Right (+) / Left (-)
            double y = tag.ftcPose.y;  // Forward (+) / Backward (-)
            double bearing = tag.ftcPose.bearing;

            telemetry.addData("Position Correction", "Using tag " + TARGET_TAG_ID);
            telemetry.addData("X Offset", "%.1f inches", x);
            telemetry.addData("Y Offset", "%.1f inches", y);
            telemetry.addData("Angle Offset", "%.1f degrees", bearing);

            // Correct position based on offsets
            // Example: If X offset is positive, robot is to the left of where it should be
            if (Math.abs(x) > 3.0) {
                strafeToCorrectPosition(x);
            }

            if (Math.abs(bearing) > 10.0) {
                rotateToCorrectAngle(bearing);
            }

            telemetry.update();
        }
    }

    /**
     * Helper: Move toward detected AprilTag
     */
    private void moveTowardTag(AprilTagDetection tag) {
        double bearing = tag.ftcPose.bearing;
        double power = 0.3;

        // Adjust direction based on bearing
        if (Math.abs(bearing) < 10) {
            // Move straight forward
            moveForward(power);
        } else if (bearing > 10) {
            // Move forward-right
            moveForward(power * 0.7);
            turnRight(power * 0.3);
        } else {
            // Move forward-left
            moveForward(power * 0.7);
            turnLeft(power * 0.3);
        }
    }

    /**
     * Helper: Move away from AprilTag
     */
    private void moveAwayFromTag(AprilTagDetection tag) {
        moveBackward(0.3);
    }

    /**
     * Helper: Search for AprilTag by rotating
     */
    private void searchForTag() {
        turnRight(0.2);
        sleep(100);
    }

    /**
     * Helper: Perform action at tag location (customize for your robot)
     */
    private void performActionAtTag(int tagId) {
        telemetry.addData("Action", "Performing task at tag " + tagId);
        telemetry.update();

        // Example: Shoot a game element, place a marker, etc.
        // For your shooter robot:
        robot.flywheel1.setPower(1.0);
        robot.flywheel2.setPower(1.0);
        sleep(2000); // Run shooter for 2 seconds
        robot.flywheel1.setPower(0);
        robot.flywheel2.setPower(0);
    }

    // ===== MOTOR CONTROL HELPER METHODS =====
    // Note: You'll need to add drive motors to your Robot class
    // These are placeholder methods - implement with your actual drive system

    private void moveForward(double power) {
        // Implement based on your drive train
        telemetry.addData("Movement", "Forward at %.2f", power);
    }

    private void moveBackward(double power) {
        // Implement based on your drive train
        telemetry.addData("Movement", "Backward at %.2f", power);
    }

    private void turnRight(double power) {
        // Implement based on your drive train
        telemetry.addData("Movement", "Turn Right at %.2f", power);
    }

    private void turnLeft(double power) {
        // Implement based on your drive train
        telemetry.addData("Movement", "Turn Left at %.2f", power);
    }

    private void strafeToCorrectPosition(double xOffset) {
        // Strafe left or right to correct position
        telemetry.addData("Correcting", "X position by %.1f", xOffset);
    }

    private void rotateToCorrectAngle(double angleOffset) {
        // Rotate to correct angle
        telemetry.addData("Correcting", "Angle by %.1f", angleOffset);
    }

    private void stopMotors() {
        // Stop all drive motors
        telemetry.addData("Movement", "STOP");
    }
}