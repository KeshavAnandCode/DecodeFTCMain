package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.teamcode.constants.HardwareConfig.*;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.utils.subsystems.AprilTag;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class Robot {

    //Initialize Public Components

    public DcMotorEx flywheel1;
    public DcMotorEx flywheel2;
    public Limelight3A limelight;
    public Servo hood;
    public Servo turret;

    public AprilTagProcessor aprilTagProcessor;

    public VisionPortal visionPortal;

    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map

        flywheel1 = hardwareMap.get(DcMotorEx.class, "fly1"); //Port 0
        flywheel2 = hardwareMap.get(DcMotorEx.class, "fly2"); //Port 1

        flywheel1.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        if (USING_LL) {
            limelight = hardwareMap.get(Limelight3A.class, "limelight");
            limelight.start(); // This tells Limelight to start looking!
        }

        hood = hardwareMap.servo.get("hood");

        turret = hardwareMap.servo.get("turret");

        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();

        // Create vision portal with webcam
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                aprilTagProcessor
        );


    }
}
