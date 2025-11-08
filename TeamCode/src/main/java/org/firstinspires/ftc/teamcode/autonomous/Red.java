package org.firstinspires.ftc.teamcode.autonomous;

import static org.firstinspires.ftc.teamcode.constants.Poses.*;
import static org.firstinspires.ftc.teamcode.constants.ServoPositions.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.constants.ServoPositions;
import org.firstinspires.ftc.teamcode.libs.RR.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.AprilTag;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Spindexer;
import org.firstinspires.ftc.teamcode.subsystems.Transfer;
import org.firstinspires.ftc.teamcode.utils.Robot;


@Config
@Autonomous
public class Red extends LinearOpMode {

    Robot robot;

    MultipleTelemetry TELE;

    MecanumDrive drive;

    AprilTag aprilTag;

    Shooter shooter;



    public static double angle = 0.1;

    Spindexer spindexer;

    Transfer transfer;





    @Override
    public void runOpMode() throws InterruptedException {


        robot = new Robot(hardwareMap);
        TELE = new MultipleTelemetry(
                telemetry, FtcDashboard.getInstance().getTelemetry()
        );

        drive = new MecanumDrive(hardwareMap, new Pose2d(
                0,0,0
        ));

        aprilTag = new AprilTag(robot, TELE);

        shooter = new Shooter(robot, TELE);

        spindexer = new Spindexer(robot, TELE);

        spindexer.outtake3();

        shooter.setShooterMode("MANUAL");

        shooter.sethoodPosition(0.54);

        transfer = new Transfer(robot);

        transfer.setTransferPosition(transferServo_out);








        TrajectoryActionBuilder traj1 = drive.actionBuilder(new Pose2d(0, 0, 0))
                .strafeToLinearHeading(new Vector2d(x1, y1), h1 );


        while(opModeInInit()) {

            aprilTag.initTelemetry();

            aprilTag.update();

            TELE.update();
        }


        waitForStart();

        if (isStopRequested()) return;

        if (opModeIsActive()){

            transfer.setTransferPower(1);

            transfer.update();




            Actions.runBlocking(
                    new ParallelAction(
                            traj1.build()
                    )
            );

            shooter.moveTurret(angle);

            transfer.setTransferPower(1);

            transfer.update();

            shooter.setManualPower(1);

            double stamp = getRuntime();

            boolean gpp =false;

            boolean pgp = false;
            boolean ppg = false;

            aprilTag.turnTelemetryOn(true);

            while(getRuntime()-stamp <4.5) {

                aprilTag.update();


                gpp = aprilTag.isDetected(21);

                 pgp = aprilTag.isDetected(22);

                 ppg = aprilTag.isDetected(23);

                 TELE.update();
            }

            robot.turr1.setPosition(0.9);

            robot.turr2.setPosition(0.1);

            int ticker = 0;

            if (gpp){
                if (time < 0.3) {

                    ticker = 0;


                    transfer.transferOut();
                    transfer.setTransferPower(1);
                } else if (time < 2) {

                    spindexer.outtake2();



                } else if (time < 2.5) {



                    transfer.transferIn();
                } else if (time < 4) {
                    transfer.transferOut();

                    spindexer.outtake1();



                    ticker++;
                } else if (time < 4.5) {


                    transfer.transferIn();
                } else if (time < 6) {

                    transfer.transferOut();

                    spindexer.outtake1();

                } else if (time < 6.5) {
                    transfer.transferIn();
                } else {

                    transfer.transferOut();

                    shooter.setManualPower(0);

                }

            }




        }

    }
}
