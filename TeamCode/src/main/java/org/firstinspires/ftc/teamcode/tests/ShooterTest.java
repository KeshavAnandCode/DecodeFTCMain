package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    public static double p = 0.0003, i = 0, d = 0, f = 0;

    public static String flyMode = "MANUAL";

    public static String turrMode = "MANUAL";

    public static int posTolerance = 40;

    public static double servoPosition = 0.501;

    public double stamp = 0.0;

    public double initPos = 0.0;

    public static double time = 1.0;

    public double velo = 0.0;

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

        initPos = shooter.getECPRPosition();

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

            if (wait(time)){
                velo = 60*((shooter.getECPRPosition() - initPos) / time);
                stamp = getRuntime();
                initPos = shooter.getECPRPosition();
            }

            shooter.update();

            TELE.addData("ECPR Revolutions", shooter.getECPRPosition());
            TELE.addData("MCPR Revolutions", shooter.getMCPRPosition());
            TELE.addData("Velocity", shooter.getVelocity(velo));
            TELE.addData("hoodPos", shooter.gethoodPosition());
            TELE.addData("turretPos", shooter.getTurretPosition());
            TELE.addData("Power Fly 1", robot.shooter1.getPower());
            TELE.addData("Power Fly 2", robot.shooter2.getPower());

            TELE.update();

        }

    }
}
