package org.firstinspires.ftc.teamcode.utils.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Subsystem;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

public class Shooter implements Subsystem {

    private final DcMotorEx fly1;
    private final DcMotorEx fly2;
    private final Telemetry telemetry;

    // PID controllers
    private final PIDController positionPID;
    private final PIDFController velocityPIDF;

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

        // Combine Driver Station telemetry and FTC Dashboard telemetry
        this.telemetry = new MultipleTelemetry(
                opModeTelemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        // Reset encoders
        fly1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fly2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Use RUN_USING_ENCODER for velocity control
        fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Initialize PID controllers
        positionPID = new PIDController(0.005, 0.0, 0.0001);
        positionPID.setTolerance(10);

        // PIDFController for velocity control: P, I, D, F (feedforward)
        double F = 1.0; // feedforward term; adjust according to motor max velocity
        velocityPIDF = new PIDFController(0.002, 0.0, 0.0, F);
        velocityPIDF.setTolerance(40);
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
        velocityPIDF.reset(); // reset integral for new target
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

    public void setVelocityPID(double kP, double kI, double kD, double kF) {
        velocityPIDF.setPIDF(kP, kI, kD, kF);
    }

    public void setPositionPIDTolerance(double tol) { positionPID.setTolerance(tol); }

    public void setVelocityPIDTolerance(double tol) { velocityPIDF.setTolerance(tol); }

    public int getPosition() {
        return (fly1.getCurrentPosition() + fly2.getCurrentPosition()) / -2;
    }

    public double getVelocity() {
        return (fly1.getVelocity() + fly2.getVelocity()) / -2;
    }

    /* ---------------- UPDATE LOOP ---------------- */

    @Override
    public void update() {
        if (positionMode) {
            double output = positionPID.calculate(getPosition(), targetPosition);
            output = clamp(output, -1, 1);
            fly1.setPower(output);
            fly2.setPower(output);
        } else if (velocityMode) {
            // Calculate PIDF velocity correction
            double correctedVelocity = velocityPIDF.calculate(getVelocity(), targetVelocity);
            fly1.setVelocity(correctedVelocity);
            fly2.setVelocity(correctedVelocity);
        } else {
            fly1.setPower(manualPower);
            fly2.setPower(manualPower);
        }

        // Telemetry
        telemetry.addLine("Shooter:");
        telemetry.addData("Mode", positionMode ? "POSITION" : velocityMode ? "VELOCITY" : "MANUAL");
        telemetry.addData("Position", getPosition());
        telemetry.addData("TargetPos", targetPosition);
        telemetry.addData("Velocity", "%.1f", getVelocity());
        telemetry.addData("TargetVel", targetVelocity);
        telemetry.addData("ManualPower", manualPower);
        telemetry.update();
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}