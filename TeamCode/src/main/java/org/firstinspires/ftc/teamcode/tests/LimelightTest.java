package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.variables.HardwareConfig;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.subsystems.Limelight;

@Autonomous
@Config

public class LimelightTest extends LinearOpMode {

    public static String MODE = "AT";
    public static int pipe = 1;


    MultipleTelemetry TELE;






    Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {



        HardwareConfig.USING_LL= true;


        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Limelight limelight = new Limelight(robot, TELE);

        limelight.setMode(MODE);

        limelight.setTelemetryOn(true);

        waitForStart();

        if(isStopRequested()) return;

        while(opModeIsActive()){

            limelight.setPipeline(pipe);

            limelight.setMode(MODE);

            limelight.update();

            TELE.update();


        }

    }
}
