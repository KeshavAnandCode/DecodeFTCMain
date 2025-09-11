package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.subsystems.Shooter;


@Autonomous
@Config
public class ShooterImpl extends LinearOpMode {

    Robot robot;

    public static double pow = 0.0;
    public static double vel = 0.0;

    public static int pos = 0;
    public static double posPower = 0.0;

    public static double p = 0.000003, i = 0, d = 0.000001;


    public static String mode = "MANUAL";

    public static int posTolerance = 40;

    MultipleTelemetry TELE;
    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Shooter shooter = new Shooter(robot, TELE);

        shooter.setTelemetryOn(true);

        shooter.setMode(mode);


        shooter.setControllerCoefficients(p, i, d);


        waitForStart();

        while(opModeIsActive()){

            shooter.setControllerCoefficients(p, i, d);

            shooter.setMode(mode);

            shooter.setManualPower(pow);

            shooter.setVelocity(vel);

            shooter.setTargetPosition(pos);

            shooter.setTolerance(posTolerance);

            shooter.setPosPower(posPower);


            shooter.update();

            TELE.update();


        }



    }
}
