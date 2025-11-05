package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.constants.ServoPositions.*;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Robot;

public class Transfer implements Subsystem{

    private final Servo servo;

    private final DcMotorEx transfer;

    private double motorPow = 0.0;

    private double servoPos = 0.501;

    public Transfer (Robot robot){

        this.servo = robot.transferServo;

        this.transfer = robot.transfer;

    }

    public void setTransferPositionOn(){
        this.servoPos  = transferServo_in;
    }

    public void setTransferPositionOff(){
        this.servoPos = transferServo_out;
    }

    public void setTransferPowerOn (){
        this.motorPow = -1;
    }

    public void setTransferPowerOff (){
        this.motorPow = 0;
    }

    public void transferOut(){
        this.setTransferPosition(transferServo_out);
    }

    public void transferIn(){
        this.setTransferPosition(transferServo_in);
    }





    @Override
    public void update() {

        if (servoPos!=0.501){
            servo.setPosition(servoPos);
        }

        transfer.setPower(motorPow);

    }
}
