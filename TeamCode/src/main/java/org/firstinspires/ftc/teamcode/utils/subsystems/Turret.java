package org.firstinspires.ftc.teamcode.utils.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Subsystem;

import java.util.Objects;

public class Turret implements Subsystem {

    private final Servo turret;

    private final MultipleTelemetry telemetry;

    private boolean telemetryOn = false;

    private double servoPos = 0.0;
    private double targetServoPos = 0.0;

    private double targetServoPower = 0.0;






    private String Mode = "SERVO";

    public Turret(Robot robot, MultipleTelemetry TELE) {

        this.turret = robot.turret;
        this.telemetry = TELE;


    }


    public void telemetryUpdate() {

        // Telemetry

        telemetry.addData("Mode", Mode);
        telemetry.addData("ServoPos", getPosition());


    }

    public double getPosition() {
        updatePosition();
        return servoPos;
    }

    public void setMode(String mode) {
        Mode =  mode;
    }


    public double getTargetServoPower() {
        return  targetServoPower;
    }

    public void updatePosition() {
        servoPos = turret.getPosition();
    }

    public void setServoPosition(double pos) {targetServoPos = pos;}


    public void setServoPower(double power){
        targetServoPower = power;
    }


    public void setTelemetryOn(boolean state){telemetryOn = state;}

    @Override
    public void update(){

        if (telemetryOn){
            telemetryUpdate();
        }

        updatePosition();

        if (Objects.equals(Mode, "Servo")){
            turret.setPosition(targetServoPos);
        } else {
            turret.setPosition(servoPos + targetServoPower);
        }



    }




}