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
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.utils.Robot;


@Config
@TeleOp

public class TeleopV1 extends LinearOpMode {


    Robot robot;

    Drivetrain drivetrain;

    Intake intake;

    Spindexer spindexer;

    Transfer transfer;

    MultipleTelemetry TELE;

    GamepadEx g1;

    GamepadEx g2;

    public static double defaultSpeed = 1;

    public static double slowMoSpeed = 0.4;

    public static double power = 0.0;

    public static double pos = 0.501;

    ToggleButtonReader g1RightBumper;

    ToggleButtonReader g2Circle;

    ToggleButtonReader g2Square;


    ToggleButtonReader g2Triangle;
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

        g2 = new GamepadEx(gamepad2);

        g2Circle  = new ToggleButtonReader(
                g2, GamepadKeys.Button.B
        );

        g2Triangle  = new ToggleButtonReader(
                g2, GamepadKeys.Button.Y
        );

        g2Square  = new ToggleButtonReader(
                g2, GamepadKeys.Button.X
        );



        drivetrain = new Drivetrain(robot, TELE, g1);

        drivetrain.setMode("Default");

        drivetrain.setDefaultSpeed(defaultSpeed);

        drivetrain.setSlowSpeed(slowMoSpeed);

        intake = new Intake(robot);

        transfer = new Transfer(robot);


        spindexer = new Spindexer(robot, TELE);

        spindexer.setTelemetryOn(true);


        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()){

            intake();

            drivetrain.update();

            TELE.update();

            transfer.setTransferPower(power);

            transfer.setTransferPosition(pos);


            transfer.update();








        }




    }

    public void intake(){


        g1RightBumper.readValue();

        g2Circle.readValue();

        g2Square.readValue();

        g2Triangle.readValue();

        if (g1RightBumper.wasJustPressed()){


            if (getRuntime() - g1RightBumperStamp < 0.3){
                intake.reverse();
            } else {
                intake.toggle();
            }

            spindexer.intake();


            g1RightBumperStamp = getRuntime();

        }

        if (intake.getIntakeState()==1) {
            spindexer.intakeShake(getRuntime());
        } else {


            if (g2Circle.wasJustPressed()){
                spindexer.outtake3();
            }

            if (g2Triangle.wasJustPressed()){
                spindexer.outtake2();
            }

            if (g2Square.wasJustPressed()){
                spindexer.outtake1();
            }

        }

        intake.update();




        spindexer.update();





    }
}
