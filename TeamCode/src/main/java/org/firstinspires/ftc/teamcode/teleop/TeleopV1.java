package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.libs.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
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

    public static double pos = 0.5;

    ToggleButtonReader g1RightBumper;

    ToggleButtonReader g2Circle;

    ToggleButtonReader g2Square;


    ToggleButtonReader g2Triangle;

    ToggleButtonReader g2RightBumper;

    ToggleButtonReader g2LeftBumper;

    ToggleButtonReader g2DpadUp;

    ToggleButtonReader g2DpadDown;
    public double g1RightBumperStamp = 0.0;

    public double g2LeftBumperStamp = 0.0;

    public static int spindexerPos = 0;

    Shooter shooter;

    public boolean scoreAll = false;

    MecanumDrive drive ;

    public boolean autotrack = true;



    @Override
    public void runOpMode() throws InterruptedException {

        drive  = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));




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

        g2RightBumper  = new ToggleButtonReader(
                g2, GamepadKeys.Button.RIGHT_BUMPER
        );


        g2LeftBumper  = new ToggleButtonReader(
                g2, GamepadKeys.Button.LEFT_BUMPER
        );

        g2DpadUp  = new ToggleButtonReader(
                g2, GamepadKeys.Button.DPAD_UP
        );


        g2DpadDown  = new ToggleButtonReader(
                g2, GamepadKeys.Button.DPAD_DOWN
        );



        drivetrain = new Drivetrain(robot, TELE, g1);

        drivetrain.setMode("Default");

        drivetrain.setDefaultSpeed(defaultSpeed);

        drivetrain.setSlowSpeed(slowMoSpeed);

        intake = new Intake(robot);

        transfer = new Transfer(robot);


        spindexer = new Spindexer(robot, TELE);

        spindexer.setTelemetryOn(true);

        shooter = new Shooter(robot, TELE);

        shooter.setShooterMode("MANUAL");





        waitForStart();

        if (isStopRequested()) return;

        drive  = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));


        while(opModeIsActive()){

            drive.updatePoseEstimate();

            TELE.addData("pose", drive.localizer.getPose());

            TELE.addData("heading", drive.localizer.getPose().heading.toDouble());


            robot.hood.setPosition(pos);


            intake();

            drivetrain.update();

            TELE.update();

            transfer.update();

            g2RightBumper.readValue();

            g2LeftBumper.readValue();

            g2DpadDown.readValue();

            g2DpadUp.readValue();

            if(g2DpadUp.wasJustPressed()){
                pos -=0.02;
            }

            if(g2DpadDown.wasJustPressed()){
                pos +=0.02;
            }

            if (gamepad2.dpad_left){
                pos = shooter.getAngleByDist(
                        shooter.trackGoal(drive.localizer.getPose(), new Pose2d(-10, 0, 0))
                );

            }

            TELE.addData("hood", pos);






            if (Math.abs(gamepad2.right_stick_x) < 0.1 && autotrack) {




                shooter.trackGoal(drive.localizer.getPose(), new Pose2d(-10, 0, 0));

            } else {

                autotrack = false;

                shooter.moveTurret(shooter.getTurretPosition() - gamepad2.right_stick_x* 0.04);

            }

            if (gamepad2.right_stick_button){
                autotrack = true;
            }



            if (g2RightBumper.wasJustPressed()){
                transfer.setTransferPower(1);
                transfer.transferIn();
                shooter.setManualPower(1);

            }

            if (g2RightBumper.wasJustReleased()){
                transfer.setTransferPower(1);
                transfer.transferOut();
            }

            if (gamepad2.left_stick_y>0.5){

                shooter.setManualPower(0);
            } else if (gamepad2.left_stick_y<-0.5){
                shooter.setManualPower(1);
            }

            if (g2LeftBumper.wasJustPressed()){
                g2LeftBumperStamp = getRuntime();
                scoreAll = true;
            }

            if (scoreAll) {
                double time = getRuntime() - g2LeftBumperStamp;

                shooter.trackGoal(drive.localizer.getPose(), new Pose2d(-2, 0, 0));

                shooter.setManualPower(1);



                if (time < 0.3) {
                    transfer.transferOut();
                    transfer.setTransferPower(1);
                } else if (time < 1.5){
                    spindexer.outtake3();
                } else  if (time < 2.0){
                    transfer.transferIn();
                } else if (time < 3){
                    transfer.transferOut();
                    spindexer.outtake2();
                } else  if (time < 3.5){
                    transfer.transferIn();
                } else if (time < 4.5){
                    transfer.transferOut();
                    spindexer.outtake1();
                }  else  if (time < 5){
                    transfer.transferIn();
                } else {

                    scoreAll = false;
                    transfer.transferOut();

                    shooter.setManualPower(0);
                }



            }


            shooter.update();












        }




    }

    public void intake(){


        g1RightBumper.readValue();

        g2Circle.readValue();

        g2Square.readValue();

        g2Triangle.readValue();

        if (g1RightBumper.wasJustPressed()){

            shooter.setManualPower(0);


            if (getRuntime() - g1RightBumperStamp < 0.3){
                intake.reverse();
            } else {
                intake.toggle();
            }

            spindexer.intake();
            
            transfer.transferOut();


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


            if (g2Circle.wasJustReleased()){
                transfer.setTransferPower(1);

            }

            if (g2Triangle.wasJustReleased()){
                transfer.setTransferPower(1);
            }

            if (g2Square.wasJustReleased()){
                transfer.setTransferPower(1);
            }





        }


        intake.update();




        spindexer.update();





    }
}
