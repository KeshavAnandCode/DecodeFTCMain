package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.colorSensor;

@Configurable
@Config
@Autonomous
public class colorSensorTest extends LinearOpMode {
    colorSensor colorSensor;

    public static int sensor = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        MultipleTelemetry TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        while (opModeIsActive()){
            if (sensor == 1){
                TELE.addData("Color HSV", colorSensor.hsvColor1());
                TELE.addData("Object Detected?", colorSensor.sensor1Detects());
            } else if (sensor == 2){
                TELE.addData("Color HSV", colorSensor.hsvColor2());
                TELE.addData("Object Detected?", colorSensor.sensor2Detects());
            } else {
                TELE.addData("Color HSV", colorSensor.hsvColor3());
                TELE.addData("Object Detected?", colorSensor.sensor3Detects());
            }
        }
    }

}
