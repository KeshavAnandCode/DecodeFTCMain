package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.subsystems.AprilTag;
import org.firstinspires.ftc.teamcode.utils.subsystems.CameraServo;


@TeleOp
@Config
public class TrackerTest extends LinearOpMode {


    Robot robot;

    MultipleTelemetry TELE;

    CameraServo cameraServo;

    AprilTag webcam;

    public static double p = 0.0003, i = 0, d = 0.00001;

    public static boolean pidMode = false;





    public static double power = 0.0;



    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(telemetry, robot.dashboard.getTelemetry());

        cameraServo = new CameraServo(robot, TELE);


        webcam = new AprilTag(robot, TELE);

        webcam.turnTelemetryOn(true);

        cameraServo.setPID_MODE(pidMode);

        cameraServo.setTelemetryOn(true);


        while(opModeInInit()){

            webcam.initTelemetry();

            TELE.update();

        };



        if(isStopRequested()) return;

        while(opModeIsActive()){

            cameraServo.setPID_MODE(pidMode);


            TELE.addData("Power", cameraServo.getServoPower());

            if (!pidMode) {

                cameraServo.setServoPower(power);
            } else {

                cameraServo.setPIDCoeffs(p,i,d);

                if (webcam.isDetected(24)) {
                    cameraServo.setPIDPower(webcam.getRBE(24).get(1));
                } else {
                    cameraServo.setServoPower(0);
                }
            }

            cameraServo.update();



            webcam.update();

            TELE.update();








        }



    }
}
