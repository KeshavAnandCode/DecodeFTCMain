package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.libs.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.Robot;


@Config
@Autonomous
public class Red extends LinearOpMode {

    Robot robot;

    MultipleTelemetry TELE;

    MecanumDrive drive;



    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        if (isStopRequested()) return;

        if (opModeIsActive()){

        }

    }
}
