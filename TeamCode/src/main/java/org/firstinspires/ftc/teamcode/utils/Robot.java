package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.teamcode.constants.HardwareConfig.*;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

public class Robot {

    //Initialize Public Components

    public DcMotorEx flywheel1;
    public DcMotorEx flywheel2;
    public Limelight3A limelight;
    public Servo hood;
    public Servo turret;
    public CRServo cameraServo;

    public AprilTagProcessor aprilTagProcessor;

    public VisionPortal visionPortal;

    public WebcamName webcam;

    public int cameraMonitorViewId;

    public OpenCvWebcam camera;

    public FtcDashboard dashboard;

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

        cameraServo = hardwareMap.get(CRServo.class, "cameraServo");



        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();


        webcam = hardwareMap.get(WebcamName.class, "Webcam 1");

        // Create vision portal with webcam
        visionPortal = VisionPortal.easyCreateWithDefaults(
                webcam,
                aprilTagProcessor
        );

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);


        dashboard = FtcDashboard.getInstance();

    }
}
