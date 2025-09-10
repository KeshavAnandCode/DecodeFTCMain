package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.subsystems.Shooter;

@Config
@TeleOp
public class shooterInterfaceTest extends LinearOpMode {

    Robot robot;

    MultipleTelemetry TELE;
    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Shooter shooterSubsystem = new Shooter(robot, TELE);

        waitForStart();

        while (opModeIsActive()){

            shooterSubsystem.setPower(1);



            shooterSubsystem.update();
        }



    }
}
