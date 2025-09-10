package org.firstinspires.ftc.teamcode.utils.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Subsystem;

public class Shooter implements Subsystem {

    DcMotorEx fly1;
    DcMotorEx fly2;
    @Override
    public void initialize(Robot robot) {
        fly1 = robot.flywheel1;
        fly2 = robot.flywheel2;

    }
}
