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
    public DigitalChannel color1_0;
    public DigitalChannel color1_1;
    public DigitalChannel color2_0;
    public DigitalChannel color2_1;
    public DigitalChannel color3_0;
    public DigitalChannel color3_1;
    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map

        flywheel1 = hardwareMap.get(DcMotorEx.class, "fly1"); //Port 0
        flywheel2 = hardwareMap.get(DcMotorEx.class, "fly2"); //Port 1

        flywheel1.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);

        color1_0 = hardwareMap.get(DigitalChannel.class,"color10"); //Port0-1
        color1_1 = hardwareMap.get(DigitalChannel.class,"color11"); //Port0-1
        color2_0 = hardwareMap.get(DigitalChannel.class,"color20"); //Port2-3
        color2_1 = hardwareMap.get(DigitalChannel.class,"color21"); //Port2-3
        color3_0 = hardwareMap.get(DigitalChannel.class,"color30"); //Port4-5
        color3_1 = hardwareMap.get(DigitalChannel.class,"color31"); //Port4-5


        if (USING_LL) {
            limelight = hardwareMap.get(Limelight3A.class, "limelight");
            limelight.start(); // This tells Limelight to start looking!
        }


    }
}
