package org.firstinspires.ftc.teamcode.utils.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Subsystem;

public class Drivetrain implements Subsystem {

    private GamepadEx gamepad;

    public MultipleTelemetry TELE;

    private DcMotorEx fl;

    private DcMotorEx fr;
    private DcMotorEx bl;
    private DcMotorEx br;
    public Drivetrain(Robot robot, MultipleTelemetry tele, GamepadEx gamepad1){

        this.fl = robot.frontLeft;
        this.fr = robot.frontRight;
        this.br = robot.backRight;
        this.bl = robot.backLeft;

        this.gamepad = gamepad1;

        this.TELE = tele;



    }

    public void FieldCentric(double fwd, double strafe, double turn, double turbo){

        double y = -fwd; // Remember, Y stick value is reversed
        double x = strafe * 1.1; // Counteract imperfect strafing
        double rx = turn;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        fl.setPower(frontLeftPower*turbo);
        bl.setPower(backLeftPower*turbo);
        fr.setPower(frontRightPower*turbo);
        br.setPower(backRightPower*turbo);

    }


    @Override
    public void update() {

        FieldCentric(
                gamepad.getRightY(),
                gamepad.getRightX(),
                gamepad.getLeftX(),
                (gamepad.getTrigger(
                        GamepadKeys.Trigger.RIGHT_TRIGGER)*0.3
                        - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)*0.3
                        +0.7
                )
        );

    }
}
