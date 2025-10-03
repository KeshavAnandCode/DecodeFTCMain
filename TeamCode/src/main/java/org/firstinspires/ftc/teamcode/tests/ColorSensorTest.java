package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Robot;

@TeleOp
@Config
public class ColorSensorTest extends LinearOpMode{
    Robot robot;
    MultipleTelemetry TELE;

    @Override
    public void runOpMode() throws InterruptedException{
        robot = new Robot(hardwareMap);
        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()){
            TELE.addData("Green:", robot.color1green.getState());
            TELE.addData("Purple:", robot.color1purple.getState());
            TELE.update();
        }
    }

}
