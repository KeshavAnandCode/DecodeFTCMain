package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Spindexer;
import org.firstinspires.ftc.teamcode.utils.Robot;


@Config
@TeleOp

public class TeleopV1 extends LinearOpMode {


    Robot robot;

    Drivetrain drivetrain;

    Intake intake;

    Spindexer spindexer;

    MultipleTelemetry TELE;

    GamepadEx g1;

    public static double defaultSpeed = 1;

    public static double slowMoSpeed = 0.4;

    ToggleButtonReader g1RightBumper;

    public double g1RightBumperStamp = 0.0;

    public static int spindexerPos = 0;



    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);

        TELE = new MultipleTelemetry(
                FtcDashboard.getInstance().getTelemetry(),
                telemetry
        );

        g1 = new GamepadEx(gamepad1);

        g1RightBumper  = new ToggleButtonReader(
            g1, GamepadKeys.Button.RIGHT_BUMPER
        );


        drivetrain = new Drivetrain(robot, TELE, g1);

        drivetrain.setMode("Default");

        drivetrain.setDefaultSpeed(defaultSpeed);

        drivetrain.setSlowSpeed(slowMoSpeed);

        intake = new Intake(robot);


        spindexer = new Spindexer(robot, TELE);

        spindexer.setTelemetryOn(true);


        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){

            intake();

            drivetrain.update();

            TELE.update();

            spindexer.update();




        }




    }

    public void intake(){


        g1RightBumper.readValue();

        if (g1RightBumper.wasJustPressed()){


            if (getRuntime() - g1RightBumperStamp < 0.3){
                intake.reverse();
            } else {
                intake.toggle();
            }

            spindexer.intake();

            g1RightBumperStamp = getRuntime();

        }

        intake.update();






    }
}
