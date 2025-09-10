package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class Robot {

    //Initialize Public Components

    public DcMotorEx flywheel1;
    public DcMotorEx flywheel2;
    public WebcamName visionProccesorAprilTag;
    public AprilTagProcessor aprilTag;

    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map


        flywheel1 = hardwareMap.get(DcMotorEx.class, "fly1"); //Port 0
        flywheel2 = hardwareMap.get(DcMotorEx.class, "fly2"); //Port 0
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        visionProccesorAprilTag = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag).getActiveCamera();

        //Motor Direction

        flywheel1.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel2.setDirection(DcMotorSimple.Direction.FORWARD);


    }
}
