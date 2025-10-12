package org.firstinspires.ftc.teamcode.tests;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Config
@TeleOp

@Disabled
public class ColorSensorTester extends LinearOpMode {


    public static String portAName = "pin0";

    public static String portBName = "pin1";



    @Override
    public void runOpMode() throws InterruptedException {


        DigitalChannel pinA = hardwareMap.digitalChannel.get(portAName);
        DigitalChannel pinB = hardwareMap.digitalChannel.get(portBName);




        MultipleTelemetry TELE = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );



        waitForStart();

        if(isStopRequested()) return;

        while(opModeIsActive()){

            TELE.addData("pinA", pinA.getState());
            TELE.addData("pinB", pinB.getState());

            TELE.update();




        }



    }
}
