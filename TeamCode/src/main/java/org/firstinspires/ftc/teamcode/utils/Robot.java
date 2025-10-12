package org.firstinspires.ftc.teamcode.utils;

import static org.firstinspires.ftc.teamcode.variables.HardwareConfig.*;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class Robot {

    //Initialize Public Components

    public Limelight3A limelight3A;

    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");

        if (USING_LL) {
            limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
            limelight3A.start(); // This tells Limelight to start looking!
        }



    }
}
