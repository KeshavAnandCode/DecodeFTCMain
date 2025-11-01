package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.rowanmcalpin.nextftc.core.Subsystem;

import org.firstinspires.ftc.teamcode.utils.Robot;

public class Intake extends Subsystem {

    private GamepadEx gamepad;

    public MultipleTelemetry TELE;


    private DcMotorEx intake;


    public Intake (Robot robot, MultipleTelemetry telemetry, GamepadEx gamepad){




    }
}
