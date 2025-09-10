package org.firstinspires.ftc.teamcode.utils.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Subsystem;

import com.arcrobotics.ftclib.controller.PIDController;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

public class Shooter implements Subsystem {

    private final DcMotorEx fly1;
    private final DcMotorEx fly2;
    private final Telemetry telemetry;

    // PID controllers
    private final PIDController positionPID;
    private final PIDController velocityPID;

    // targets
    private double targetPosition = 0;
    private double targetVelocity = 0;

    // mode flags
    private boolean positionMode = false;
    private boolean velocityMode = false;

    // raw/manual power fallback
    private double manualPower = 0;

    public Shooter(Robot robot, Telemetry opModeTelemetry) {
        this.fly1 = robot.flywheel1;
        this.fly2 = robot.flywheel2;

        // ✅ Combine Driver Station telemetry and FTC Dashboard telemetry
        this.telemetry = new MultipleTelemetry(
                opModeTelemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        // Reset encoders
        fly1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fly2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fly1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fly2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Initialize PID controllers (tune these!)
        positionPID = new PIDController(0.005, 0.0, 0.0001);
        velocityPID = new PIDController(0.002, 0.0, 0.0);
    }

    /* ---------------- PUBLIC API ---------------- */

    public void setPower(double pow) {
        manualPower = pow;
        positionMode = false;
        velocityMode = false;
    }

    public void goToPosition(int ticks) {
        targetPosition = ticks;
        positionMode = true;
        velocityMode = false;
    }

    public void setVelocity(double ticksPerSec) {
        targetVelocity = ticksPerSec;
        velocityMode = true;
        positionMode = false;
    }

    public void stop() {
        manualPower = 0;
        positionMode = false;
        velocityMode = false;
    }

    public boolean isPositionMode() { return positionMode; }
    public boolean isVelocityMode() { return velocityMode; }

    public void setPositionPID(double kP, double kI, double kD) {
        positionPID.setPID(kP, kI, kD);
    }

    public void setVelocityPID(double kP, double kI, double kD) {
        velocityPID.setPID(kP, kI, kD);
    }

    public int getPosition() {
        return fly1.getCurrentPosition();
    }

    public double getVelocity() {
        return fly1.getVelocity();
    }

    /* ---------------- UPDATE LOOP ---------------- */

    @Override
    public void update() {
        double output;

        if (positionMode) {
            output = positionPID.calculate(getPosition(), targetPosition);
        } else if (velocityMode) {
            output = velocityPID.calculate(getVelocity(), targetVelocity);
        } else {
            output = manualPower;
        }

        output = clamp(output, -1, 1);
        fly1.setPower(output);
        fly2.setPower(output);

        // Telemetry goes to BOTH DS and Dashboard
        telemetry.addLine("Shooter:");
        telemetry.addData(" Mode", positionMode ? "POSITION" : velocityMode ? "VELOCITY" : "MANUAL");
        telemetry.addData(" Output", "%.3f", output);
        telemetry.addData(" Position", getPosition());
        telemetry.addData(" TargetPos", targetPosition);
        telemetry.addData(" Velocity", "%.1f", getVelocity());
        telemetry.addData(" TargetVel", targetVelocity);
        telemetry.update(); // ✅ push telemetry each loop
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}