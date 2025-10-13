package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.teamcode.constants.HardwareConfig.*;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    //Initialize Public Components

    public DcMotorEx flywheel1;
    public DcMotorEx flywheel2;
    public Limelight3A limelight;
    public DigitalChannel color1green;
    public DigitalChannel color1purple;
    public DigitalChannel color2green;
    public DigitalChannel color2purple;
    public DigitalChannel color3green;
    public DigitalChannel color3purple;
    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map

        flywheel1 = hardwareMap.get(DcMotorEx.class, "fly1"); //Port 0
        flywheel2 = hardwareMap.get(DcMotorEx.class, "fly2"); //Port 1

        flywheel1.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        color1green = hardwareMap.get(DigitalChannel.class,"color1green"); //Port0-1
        color1purple = hardwareMap.get(DigitalChannel.class,"color1purple"); //Port0-1
        color2green = hardwareMap.get(DigitalChannel.class,"color2green"); //Port2-3
        color2purple = hardwareMap.get(DigitalChannel.class,"color2purple"); //Port2-3
        color3green = hardwareMap.get(DigitalChannel.class,"color3green"); //Port4-5
        color3purple = hardwareMap.get(DigitalChannel.class,"color3purple"); //Port4-5


        if (USING_LL) {
            limelight = hardwareMap.get(Limelight3A.class, "limelight");
            limelight.start(); // This tells Limelight to start looking!
        }


    }
}
