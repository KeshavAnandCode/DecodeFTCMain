package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.constants.ServoVars.*;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Robot;

public class Intake implements Subsystem {

    public MultipleTelemetry TELE;

    private final DcMotorEx intake;

    private final Servo kicker;


    private double intakePower = 0.0;

    private double kickerPos = 0.0;

    public Intake (Robot robot, MultipleTelemetry telemetry){

        this.intake = robot.intake;
        this.kicker = robot.rejecter;

    }

    public void setIntakePower (double pow){
        this.intakePower = pow;
    }

    public double getIntakePower() {
        return this.intakePower;
    }

    public void kickOut (){
        this.kickerPos = rejecter_Out;
    }

    public void kickIn (){
        this.kickerPos = rejecter_In;
    }

    @Override
    public void update() {
        kicker.setPosition(kickerPos);
        intake.setPower(intakePower);
    }
}


