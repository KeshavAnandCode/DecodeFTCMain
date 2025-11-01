package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.libs.RR.MecanumDrive;

public class Robot {

    //Initialize Public Components

    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;

    public DcMotorEx backLeft;

    public DcMotorEx backRight;

    public DcMotorEx intake;

    public DcMotorEx shooter1;
    public DcMotorEx shooter2;
    public Servo hood;

    public Servo rejecter;

    public Servo turr1;

    public Servo turr2;






    public Robot (HardwareMap hardwareMap) {

        //Define components w/ hardware map

        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        rejecter = hardwareMap.get(Servo.class, "rejecter");

        shooter1 = hardwareMap.get(DcMotorEx.class, "shooter1");

        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");

        shooter1.setDirection(DcMotorSimple.Direction.REVERSE);

        hood = hardwareMap.get(Servo.class, "hood");

        turr1 = hardwareMap.get(Servo.class, "t1");

        turr2 = hardwareMap.get(Servo.class, "t2");













    }
}
