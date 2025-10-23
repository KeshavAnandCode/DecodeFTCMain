package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.constants.HardwareConfig;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.utils.subsystems.Turret;


@Autonomous
@Config

public class TurretV1 extends LinearOpMode {

    public static String MODE = "AT";
    public static int pipe = 1;

    public static String ServoMode = "Servo";

    public static double servoPos = 0.0;

    public static double servoPow = 0.0;



    MultipleTelemetry TELE;

    Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {

        HardwareConfig.USING_LL = true;

        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Turret turret = new Turret(robot, TELE);

        Limelight limelight = new Limelight(robot, TELE);

        limelight.setMode(MODE);

        turret.setMode(ServoMode);


        limelight.setTelemetryOn(true);

        waitForStart();

        if(isStopRequested()) return;

        while(opModeIsActive()){


            turret.setMode(ServoMode);

            turret.setServoPosition(servoPos);

            turret.setServoPower(servoPow);

            limelight.setTelemetryOn(true);

            turret.setTelemetryOn(true);




            limelight.setPipeline(pipe);

            limelight.setMode(MODE);

            limelight.update();

            turret.update();

            TELE.update();

        }

    }
}
