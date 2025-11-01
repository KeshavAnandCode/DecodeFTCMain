package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    //Initialize Public Components

    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;

    public DcMotorEx backLeft;

    public DcMotorEx backRight;

    public DcMotorEx intake;

    public Servo rejecter;




    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map

        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        rejecter = hardwareMap.get(Servo.class, "rejecter");






    }
}
