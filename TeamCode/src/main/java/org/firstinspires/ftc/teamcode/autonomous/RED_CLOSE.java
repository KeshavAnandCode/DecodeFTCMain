package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Robot;


@Autonomous
@Config
public class RED_CLOSE extends LinearOpMode {
    Robot robot;
    MultipleTelemetry TELE;


    @Override
    public void runOpMode() throws InterruptedException{
        robot = new Robot(hardwareMap);
        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());




        waitForStart();
        if (isStopRequested()) return;


        while (opModeIsActive()){
            robot.flywheel1.setPower(0);
        }
    }
}
