package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;


@TeleOp
@Config
public class ShooterTest extends LinearOpMode {

    Robot robot;

    public static double pow = 0.0;
    public static double vel = 0.0;

    public static int pos = 0;
    public static double posPower = 0.0;

    public static double p = 0.000003, i = 0, d = 0.000001;


    public static String flyMode = "MANUAL";

    public static String turrMode = "MANUAL";

    public static int posTolerance = 40;

    public static double servoPosition = 0.501;

    MultipleTelemetry TELE;
    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Shooter shooter = new Shooter(robot, TELE);

        shooter.setTelemetryOn(true);

        shooter.setTurretMode(turrMode);

        shooter.setShooterMode(flyMode);


        shooter.setControllerCoefficients(p, i, d);


        waitForStart();

        if(isStopRequested()) return;


        while(opModeIsActive()){

            shooter.setControllerCoefficients(p, i, d);

            shooter.setTurretMode(turrMode);

            shooter.setShooterMode(flyMode);

            shooter.setManualPower(pow);

            shooter.setVelocity(vel);

            shooter.setTargetPosition(pos);

            shooter.setTolerance(posTolerance);

            shooter.setPosPower(posPower);

            if (servoPosition!=0.501) {shooter.sethoodPosition(servoPosition);}


            shooter.update();

            TELE.update();


        }



    }
}
