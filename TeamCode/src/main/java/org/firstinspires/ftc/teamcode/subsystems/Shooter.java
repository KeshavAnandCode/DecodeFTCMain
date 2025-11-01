package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

import java.util.Objects;

public class Shooter implements Subsystem {
    private final DcMotorEx fly1;
    private final DcMotorEx fly2;
    private final Servo hoodServo;

    private final MultipleTelemetry telemetry;

    private boolean telemetryOn = false;

    private double manualPower = 0.0;
    private double servoPos = 0.0;
    private double velocity = 0.0;
    private double posPower = 0.0;

    private int targetPosition = 0;

    private double p = 0.0003, i = 0, d = 0.00001;

    private PIDController controller;





    private String Mode = "MANUAL";

    public Shooter(Robot robot, MultipleTelemetry TELE) {
        this.fly1 = robot.shooter1;
        this.fly2 = robot.shooter2;
        this.telemetry = TELE;
        this.hoodServo = robot.hood;

        // Reset encoders
        fly1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fly2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        controller = new PIDController(p, i, d);


        controller.setPID(p, i, d);





    }

    public void telemetryUpdate() {

        // Telemetry

        telemetry.addData("Mode", Mode);
        telemetry.addData("ManualPower", manualPower);
        telemetry.addData("Position", getPosition());
        telemetry.addData("TargetPosition", targetPosition);
        telemetry.addData("Velocity", getVelocity());
        telemetry.addData("TargetVelocity", velocity);
        telemetry.addData("ServoPos", getServoPosition());
        telemetry.addData("PID Coefficients", "P: %.6f, I: %.6f, D: %.6f", p, i, d);
        telemetry.addData("Current Fly 1", fly1.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("Current Fly 2", fly2.getCurrent(CurrentUnit.AMPS));

    }

    public double getServoPosition() {
        return (hoodServo.getPosition());
    }

    public void setServoPosition(double pos) {servoPos = pos;}

    public double getVelocity() {
        return ((double) ((fly1.getVelocity(AngleUnit.DEGREES) + fly2.getVelocity(AngleUnit.DEGREES)) /2));
    }

    public void setVelocity(double vel){velocity = vel;}



    public void setPosPower(double power){posPower = power;}


    public void setTargetPosition(int pos){
        targetPosition = pos;
    }

    public void setTolerance(int tolerance){
        controller.setTolerance(tolerance);
    }

    public void setControllerCoefficients(double kp, double ki, double kd){
        p = kp;
        i = ki;
        d = kd;
        controller.setPID(p, i, d);

    }

    public PIDCoefficients  getControllerCoefficients(){

        return new PIDCoefficients(p, i, d);

    }







    public void setManualPower(double power){manualPower = power;}

    public String getMode(){return Mode;}

    public double getPosition(){
        return ((double) ((fly1.getCurrentPosition() + fly2.getCurrentPosition()) /2));
    }

    public void setMode(String mode){ Mode = mode;}

    public void setTelemetryOn(boolean state){telemetryOn = state;}

    @Override
    public void update() {

        if (Objects.equals(Mode, "MANUAL")){
            fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fly2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            fly1.setPower(manualPower);
            fly2.setPower(manualPower);
        }

        else if (Objects.equals(Mode, "VEL")){

            fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fly2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            fly1.setVelocity(velocity, AngleUnit.DEGREES);
            fly2.setVelocity(velocity, AngleUnit.DEGREES);
        }

        else if (Objects.equals(Mode, "POS")){


            double powPID = controller.calculate(getPosition(), targetPosition);



            fly1.setPower(powPID);
            fly2.setPower(powPID);
        }

        hoodServo.setPosition(servoPos);

        if (telemetryOn) {telemetryUpdate();}



    }
}
