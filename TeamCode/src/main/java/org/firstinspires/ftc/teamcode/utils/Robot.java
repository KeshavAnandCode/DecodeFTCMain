package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    //Initialize Public Components

    public Limelight3A limelight3A;

    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

    }
}
