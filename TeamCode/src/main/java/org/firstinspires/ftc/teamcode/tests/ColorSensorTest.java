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
            TELE.addData("Green1:", robot.color1green.getState());
            TELE.addData("Purple1:", robot.color1purple.getState());
            TELE.addData("Green2:", robot.color2green.getState());
            TELE.addData("Purple2:", robot.color2purple.getState());
            TELE.addData("Green3:", robot.color3green.getState());
            TELE.addData("Purple3:", robot.color3purple.getState());

            TELE.update();
        }
    }

}
