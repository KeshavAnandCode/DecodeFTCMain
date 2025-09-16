package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class colorSensor {
    Robot robot;
    public int hsvColor1(){
        NormalizedRGBA colors = robot.colorSensor1.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        return (int) hsvValues[0];
    }
    public int hsvColor2(){
        NormalizedRGBA colors = robot.colorSensor2.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        return (int) hsvValues[0];
    }
    public int hsvColor3(){
        NormalizedRGBA colors = robot.colorSensor3.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        return (int) hsvValues[0];
    }

    public boolean sensor1Detects(){
        NormalizedRGBA colors = robot.colorSensor1.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        if ((int) hsvValues[2] > 50){
            return true;
        } else {
            return false;
        }
    }
    public boolean sensor2Detects(){
        NormalizedRGBA colors = robot.colorSensor2.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        if ((int) hsvValues[2] > 50){
            return true;
        } else {
            return false;
        }
    }
    public boolean sensor3Detects(){
        NormalizedRGBA colors = robot.colorSensor3.getNormalizedColors();
        float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);
        if ((int) hsvValues[2] > 50){
            return true;
        } else {
            return false;
        }
    }
}
