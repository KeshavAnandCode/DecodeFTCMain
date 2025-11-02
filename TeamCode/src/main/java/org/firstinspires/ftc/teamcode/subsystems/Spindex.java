package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.constants.ServoVars.*;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.utils.Robot;

import java.util.Objects;


public class Spindex implements Subsystem{

    public MultipleTelemetry telemetry;

    private final DcMotorEx ballUpMotor;

    private final Servo ballUpServo;

    private final Servo spindex1;

    private final Servo spindex2;

    private final DigitalChannel color1Green;
    private final DigitalChannel color1Purple;
    private final DigitalChannel color2Green;
    private final DigitalChannel color2Purple;
    private final DigitalChannel color3Green;
    private final DigitalChannel color3Purple;

    private double ballUpMotorPower = 0.0;

    private double ballUpServoPos = 0.0;

    private double manualSpindexPos = 0.0;

    private double autoSpindexPos = 0.0;

    public String spindexMode = "MANUAL";

    private boolean telemetryOn = false;

    public Spindex (Robot robot, MultipleTelemetry TELE){
        this.ballUpMotor = robot.ballUpMotor;
        this.ballUpServo = robot.ballUpServo;

        this.spindex1 = robot.spindex1;
        this.spindex2 = robot.spindex2;

        this.color1Green = robot.color1Green;
        this.color1Purple = robot.color1Purple;
        this.color2Green = robot.color2Green;
        this.color2Purple = robot.color2Purple;
        this.color3Green = robot.color3Green;
        this.color3Purple = robot.color3Purple;

        this.telemetry = TELE;
    }

    public String getSpindexMode(){return spindexMode;}

    public void telemetryUpdate() {
        String color1 = "";
        String color2 = "";
        String color3 = "";
        // Telemetry
        if(this.color1Green.getState()){
            color1 = "green";
        } else if (this.color1Purple.getState()){
            color1 = "purple";
        }

        if(this.color2Green.getState()){
            color2 = "green";
        } else if (this.color2Purple.getState()){
            color2 = "purple";
        }

        if(this.color3Green.getState()){
            color3 = "green";
        } else if (this.color3Purple.getState()){
            color3 = "purple";
        }

        telemetry.addData("Color 1", color1);
        telemetry.addData("Color2", color2);
        telemetry.addData("Color3", color3);
    }
    public void setballUpMotorPower (double pow){
        this.ballUpMotorPower = pow;
    }

    public double getballUpMotorPower (){
        return this.ballUpMotorPower;
    }

    public void ballUpOn(){
        this.ballUpServoPos = ballUpServo_On;
    }

    public void ballUpOff(){
        this.ballUpServoPos = ballUpServo_Off;
    }

    //Manual spindex:

    public void spindexPos1(){
        this.manualSpindexPos = spindex_Pos1;
    }

    public void spindexPos2(){
        this.manualSpindexPos = spindex_Pos2;
    }

    public void spindexPos3(){
        this.manualSpindexPos = spindex_Pos3;
    }

    public void spindexIntakeColorPos(){
        this.manualSpindexPos = spindex_IntakeColor;
    }

    public void spindexColorBackupPos(){
        this.manualSpindexPos = spindex_BackupColor;
    }

    //Automatic spindex (adjust position based on coloring):

    public void spindexGreen(){
        if (this.color1Green.getState()){
            this.autoSpindexPos = spindex_Pos1;
        } else if (this.color2Green.getState()){
            this.autoSpindexPos = spindex_Pos2;
        } else if (this.color3Green.getState()){
            this.autoSpindexPos = spindex_Pos3;
        } else {
            this.autoSpindexPos = spindex_IntakeColor;
        }
    }

    public void spindexPurple(){
        if (this.color1Purple.getState()){
            this.autoSpindexPos = spindex_Pos1;
        } else if (this.color2Purple.getState()){
            this.autoSpindexPos = spindex_Pos2;
        } else if (this.color3Purple.getState()){
            this.autoSpindexPos = spindex_Pos3;
        } else {
            this.autoSpindexPos = spindex_IntakeColor;
        }
    }

    @Override
    public void update() {
        if (Objects.equals(spindexMode, "MANUAL")){
            spindex1.setPosition(manualSpindexPos);
            spindex2.setPosition(1-manualSpindexPos);
        } else if (Objects.equals(spindexMode, "AUTO")){
            spindex1.setPosition(autoSpindexPos);
            spindex2.setPosition(1-autoSpindexPos);
        }

        ballUpServo.setPosition(ballUpServoPos);
        ballUpMotor.setPower(ballUpMotorPower);

        telemetryUpdate();
    }
}
