package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.constants.ServoVars.*;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Robot;

import java.util.Objects;


public class Spindex implements Subsystem{

    public MultipleTelemetry TELE;

    private final DcMotorEx ballUpMotor;

    private final Servo ballUpServo;

    private final Servo spindex1;

    private final Servo spindex2;

    private double ballUpMotorPower = 0.0;

    private double ballUpServoPos = 0.0;

    private double manualSpindexPos = 0.0;

    private double autoSpindexPos = 0.0;

    public String spindexMode = "MANUAL";

    public Spindex (Robot robot, MultipleTelemetry telemetry){
        this.ballUpMotor = robot.ballUpMotor;
        this.ballUpServo = robot.ballUpServo;
        this.spindex1 = robot.spindex1;
        this.spindex2 = robot.spindex2;
    }

    public String getSpindexMode(){return spindexMode}

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

    public void spindexGreen(boolean green1, boolean green2, boolean green3){
        if (green1){
            this.autoSpindexPos = spindex_Pos1;
        } else if (green2){
            this.autoSpindexPos = spindex_Pos2;
        } else if (green3){
            this.autoSpindexPos = spindex_Pos3;
        } else {
            this.autoSpindexPos = spindex_IntakeColor;
        }
    }

    public void spindexPurple(boolean purple1, boolean purple2, boolean purple3){
        if (purple1){
            this.autoSpindexPos = spindex_Pos1;
        } else if (purple2){
            this.autoSpindexPos = spindex_Pos2;
        } else if (purple3){
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
    }
}
