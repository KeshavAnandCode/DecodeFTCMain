package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.Robot;

@Configurable
@Config
@Autonomous
public class shooterTest extends LinearOpMode {

    Robot robot;
    public static String DIRECTION = "0";


    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);

        MultipleTelemetry TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());



        waitForStart();

        while (opModeIsActive()){

            TELE.addLine("-1, 1, or 0");

            robot.flywheel1.setPower(Double.parseDouble(DIRECTION));
            robot.flywheel2.setPower(Double.parseDouble(DIRECTION));



        }



    }
}
