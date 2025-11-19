package org.firstinspires.ftc.teamcode.tests;

import android.app.Notification;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@TeleOp
@Config
public class ShooterTest extends LinearOpMode {

    Robot robot;

    public static double pow = 0.0;
    public static double vel = 0.0;
    public static double mcpr = 28.0; // CPR of the motor
    public static double ecpr = 1024.0; // CPR of the encoder
    public static int pos = 0;
    public static double posPower = 0.0;

    public static double posi = 0.5;

    public static double p = 0.001, i = 0, d = 0, f = 0;

    public static String flyMode = "MANUAL";

    public static String turrMode = "MANUAL";

    public static int posTolerance = 40;

    public static double servoPosition = 0.501;

    public double stamp = 0.0;

    public double initPos = 0.0;

    public static double time = 1.0;

    MultipleTelemetry TELE;

    public boolean wait(double time) {
        return (getRuntime() - stamp) > time;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Shooter shooter = new Shooter(robot, TELE);

        shooter.setTelemetryOn(true);

        shooter.setTurretMode(turrMode);

        shooter.setShooterMode(flyMode);

        shooter.setControllerCoefficients(p, i, d, f);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            shooter.setControllerCoefficients(p, i, d, f);

            shooter.setTurretMode(turrMode);

            shooter.setShooterMode(flyMode);

            shooter.setManualPower(pow);

            shooter.setVelocity(vel);

            shooter.setTargetPosition(pos);

            shooter.setTurretPosition(posi);

            shooter.setTolerance(posTolerance);

            shooter.setPosPower(posPower);

            if (servoPosition != 0.501) { shooter.sethoodPosition(servoPosition); }

            shooter.update();

            if (wait(time)){
                telemetry.addData("Velocity", shooter.getVelocity(initPos));
                stamp = getRuntime();
                initPos = shooter.getECPRPosition();
            }
            TELE.update();

        }

    }
}
