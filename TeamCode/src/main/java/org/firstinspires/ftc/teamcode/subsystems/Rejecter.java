package org.firstinspires.ftc.teamcode.subsystems;


import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Robot;

public class Rejecter implements Subsystem{

    private final Servo servo;

    public double rpos = 0.5;

    public Rejecter(Robot robot){
        this.servo = robot.rejecter;
    }

    public void rejecterPos(double pos){
        this.rpos = pos;
    }

    @Override
    public void update() {
        this.servo.setPosition(rpos);
    }
}
