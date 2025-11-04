package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.constants.ServoPositions.*;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Robot;

public class Spindexer implements Subsystem{

    private Servo s1;
    private Servo s2;

    private DigitalChannel p0;

    private DigitalChannel p1;
    private DigitalChannel p2;
    private DigitalChannel p3;
    private DigitalChannel p4;

    private DigitalChannel p5;

    private AnalogInput input;

    private AnalogInput input2;


    private MultipleTelemetry TELE;

    private double position = 0.501;

    private boolean telemetryOn = false;

    public Spindexer (Robot robot, MultipleTelemetry tele){

        this.s1 = robot.spin1;
        this.s2 = robot.spin2;

        this.p0 = robot.pin0;
        this.p1 = robot.pin1;
        this.p2 = robot.pin2;
        this.p3 = robot.pin3;
        this.p4 = robot.pin4;
        this.p5 = robot.pin5;

        this.input = robot.analogInput;

        this.input2 = robot.analogInput2;

        this.TELE = tele;

    }

    public void setTelemetryOn(boolean state){
        telemetryOn = state;
    }

    public void colorSensorTelemetry() {
        TELE.addData("pin0", p0.getState());
        TELE.addData("pin1", p1.getState());
        TELE.addData("pin2", p2.getState());
        TELE.addData("pin3", p3.getState());
        TELE.addData("pin4", p4.getState());
        TELE.addData("pin5", p5.getState());
        TELE.addData("Analog", (input.getVoltage()));

        TELE.addData("Analog2", (input2.getVoltage()));

    }

    public void setPosition (double pos) {
        position = pos;
    }

    public void intake () {
        position = spindexer_intakePos;
    }

    public void outtake3 () {
        position = spindexer_outtakeBall3;
    }

    public void outtake2 () {
        position = spindexer_outtakeBall2;
    }

    public void outtake1 () {
        position = spindexer_outtakeBall1;
    }



    @Override
    public void update() {

        if (position !=0.501) {

            s1.setPosition(position);
            s2.setPosition(1 - position);
        }


        if (telemetryOn) {
            colorSensorTelemetry();
        }

    }
}
