package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Robot {

    // Hardware Components
    public DcMotorEx flywheel1;
    public DcMotorEx flywheel2;

    // Vision Components
    public AprilTagProcessor aprilTag;
    public VisionPortal visionPortal;

    public Robot(HardwareMap hardwareMap) {

        // Initialize motors
        flywheel1 = hardwareMap.get(DcMotorEx.class, "fly1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "fly2");

        // Set motor directions
        flywheel1.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel2.setDirection(DcMotorSimple.Direction.FORWARD);

        // Initialize AprilTag vision
        initAprilTag(hardwareMap);
    }

    /**
     * Initialize AprilTag processor and vision portal
     */
    private void initAprilTag(HardwareMap hardwareMap) {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aprilTag
        );
    }

    /**
     * Get list of currently detected AprilTags
     */
    public List<AprilTagDetection> getAprilTagDetections() {
        return aprilTag.getDetections();
    }

    /**
     * Get a specific AprilTag by ID, returns null if not detected
     */
    public AprilTagDetection getAprilTagById(int id) {
        for (AprilTagDetection detection : getAprilTagDetections()) {
            if (detection.id == id) {
                return detection;
            }
        }
        return null;
    }

    /**
     * Check if a specific AprilTag is currently detected
     */
    public boolean isAprilTagDetected(int id) {
        return getAprilTagById(id) != null;
    }

    //Get the number of AprilTags currently detected
    public int getAprilTagCount() {
        return aprilTag.getDetections().size();
    }


     //Close the vision portal to save resources
    public void closeVisionPortal() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}