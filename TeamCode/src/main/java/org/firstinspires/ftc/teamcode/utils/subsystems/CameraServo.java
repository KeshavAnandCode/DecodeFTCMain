package org.firstinspires.ftc.teamcode.utils.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Subsystem;

import java.util.ArrayList;
import java.util.List;

public class CameraServo implements Subsystem {

    private CRServo camServo;

    private MultipleTelemetry TELE;

    private double servoPower = 0.0;

    private PIDController controller;
    private double p = 0.0003, i = 0, d = 0.00001;

    private boolean PID_MODE = false;

    private boolean telemetryOn = false;


    public CameraServo(Robot robot, MultipleTelemetry tele){
        this.camServo = robot.cameraServo;
        this.TELE = tele;

        controller = new PIDController(p, i, d);

        controller.setTolerance(0.2);

    }

    public void setServoPower(double pow){
        this.servoPower = pow;
    }

    public Double getServoPower() {
        return servoPower;
    }

    public void setPIDCoeffs(double p, double i, double d){
        this.p = p;
        this.i = i;
        this.d = d;
    }

    public void setPID_MODE(boolean mode){
        this.PID_MODE = mode;
    }

    public ArrayList<Double> getPIDCoeffs(){
         ArrayList<Double>  returner = new ArrayList<Double>();
         returner.add(this.p);
        returner.add(this.i);
        returner.add(this.d);

        return returner;


    }

    public void setPIDPower(double errorAngle){

        double pow = controller.calculate(errorAngle, 0);

        this.servoPower  = pow;

        if (telemetryOn) {


            // Debug telemetry
            TELE.addData("PID_MODE", PID_MODE);
            TELE.addData("Error Angle", "%.3f", errorAngle);
            TELE.addData("PID Output", "%.5f", pow);
            TELE.addData("Servo Power", "%.5f", servoPower);
            TELE.addData("P Coeff", p);
            TELE.addData("I Coeff", i);
            TELE.addData("D Coeff", d);
            TELE.addLine("------------------");
        }




    }

    public void setTelemetryOn(boolean on){
        telemetryOn = on;
    }





    @Override
    public void update() {

        controller.setPID(p, i, d);

        setServoPower(servoPower);




        camServo.setPower(servoPower);

        if (telemetryOn) {


            // More safety / debugging info
            TELE.addData("Servo Running Power", "%.3f", camServo.getPower());
            TELE.addData("PID_MODE Active", PID_MODE);

        }



    }
}
