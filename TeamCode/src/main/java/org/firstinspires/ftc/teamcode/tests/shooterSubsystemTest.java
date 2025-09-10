package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.subsystems.Shooter;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

@Config
@Autonomous(group="Test")
public class shooterSubsystemTest extends LinearOpMode {

    // ------------------ STATIC VARIABLES FOR DASHBOARD ------------------
    public static double manualPower = 0;           // Manual power for setPower
    public static int positionTarget = 0;           // Target for goToPosition
    public static double velocityTarget = 0;        // Target for setVelocity

    public static boolean useManual = true;         // Mode selector
    public static boolean usePosition = false;
    public static boolean useVelocity = false;

    // PID tuning
    public static double posKP = 0.005;
    public static double posKI = 0.0;
    public static double posKD = 0.0001;

    public static double velKP = 0.002;
    public static double velKI = 0.0;
    public static double velKD = 0.0;
    public static double velKF = 1.0;               // Feedforward for velocity

    public static double posTol = 10;
    public static double velTol = 40;
    // ------------------ END STATIC VARIABLES ------------------

    private Shooter shooter;
    private MultipleTelemetry TELE;

    @Override
    public void runOpMode() {
        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Initialize robot and shooter subsystem
        Robot robot = new Robot(hardwareMap);
        shooter = new Shooter(robot, TELE);

        // Wait for start
        waitForStart();

        while (opModeIsActive()) {

            // ------------------ APPLY PID TUNING ------------------
            shooter.setPositionPID(posKP, posKI, posKD);
            shooter.setVelocityPID(velKP, velKI, velKD, velKF); // Corrected call with feedforward
            shooter.setPositionPIDTolerance(posTol);
            shooter.setVelocityPIDTolerance(velTol);

            // ------------------ APPLY MODE ------------------
            if (useManual) {
                shooter.setPower(manualPower);
            } else if (usePosition) {
                shooter.goToPosition(positionTarget);
            } else if (useVelocity) {
                shooter.setVelocity(velocityTarget);
            }

            // ------------------ UPDATE SHOOTER ------------------
            shooter.update();

            // ------------------ EXTRA TELEMETRY ------------------
            TELE.addLine("=== TEST CONTROLS ===");
            TELE.addData("Manual Power", manualPower);
            TELE.addData("Position Target", positionTarget);
            TELE.addData("Velocity Target", velocityTarget);
            TELE.addData("Current Mode", shooter.isPositionMode() ? "POSITION" : shooter.isVelocityMode() ? "VELOCITY" : "MANUAL");
            TELE.addData("Shooter Pos", shooter.getPosition());
            TELE.addData("Shooter Vel", shooter.getVelocity());
            TELE.update();

            sleep(20); // Small delay to avoid spamming dashboard
        }
    }
}