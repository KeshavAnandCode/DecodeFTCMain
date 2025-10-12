package org.firstinspires.ftc.teamcode.utils.subsystems;


import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.Subsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Limelight implements Subsystem {

    private final Limelight3A limelight;
    private final MultipleTelemetry telemetry;

    private LLResult result;
    private LLStatus status;

    private boolean telemetryOn = false;
    private String mode = "AT";

    // ✅ Internal cached data
    private Pose3D botpose;

    private double captureLatency = 0.0;
    private double targetingLatency = 0.0;
    private double parseLatency = 0.0;
    private double[] pythonOutput = new double[0];

    private double tx = 0.0;
    private double txnc = 0.0;
    private double ty = 0.0;
    private double tync = 0.0;

    private List<LLResultTypes.BarcodeResult> barcodeResults = new ArrayList<>();
    private List<LLResultTypes.ClassifierResult> classifierResults = new ArrayList<>();
    private List<LLResultTypes.DetectorResult> detectorResults = new ArrayList<>();
    private List<LLResultTypes.FiducialResult> fiducialResults = new ArrayList<>();
    private List<LLResultTypes.ColorResult> colorResults = new ArrayList<>();

    public Limelight(Robot robot, MultipleTelemetry tele) {
        this.limelight = robot.limelight3A;
        this.telemetry = tele;
        limelight.pipelineSwitch(1);


    }

    public void setPipeline(int pipeline) {limelight.pipelineSwitch(pipeline);}

    public void setTelemetryOn(boolean state) { telemetryOn = state; }

    public void setMode(String newMode) { this.mode = newMode; }

    /** ✅ MAIN UPDATE LOOP */
    @Override
    public void update() {
        result = limelight.getLatestResult();
        status = limelight.getStatus();

        if (result != null && (Objects.equals(status.getPipelineType(), "pipe_python") || result.isValid())){
            // Refresh all cached values
            botpose         = result.getBotpose();
            captureLatency  = result.getCaptureLatency();
            targetingLatency= result.getTargetingLatency();
            parseLatency    = result.getParseLatency();
            pythonOutput    = result.getPythonOutput();
            tx              = result.getTx();
            txnc            = result.getTxNC();
            ty              = result.getTy();
            tync            = result.getTyNC();
            barcodeResults  = result.getBarcodeResults();
            classifierResults = result.getClassifierResults();
            detectorResults   = result.getDetectorResults();
            fiducialResults   = result.getFiducialResults();
            colorResults      = result.getColorResults();
        }

        if (telemetryOn) telemetryUpdate();
    }

    /** ✅ Telemetry Output */
    private void telemetryUpdate() {
// ✅ Use getters instead of directly accessing 'status' or cached fields
        telemetry.addData("Name", "%s", getStatus().getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                getStatus().getTemp(),
                getStatus().getCpu(),
                (int) getStatus().getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                getStatus().getPipelineIndex(),
                getStatus().getPipelineType());
        telemetry.addData("ResultNull", result == null);
        telemetry.addData("ResultValid", result.isValid());



        if (result != null && result.isValid()) {
            telemetry.addData("LL Latency", getTotalLatency());
            telemetry.addData("Capture Latency", getCaptureLatency());
            telemetry.addData("Targeting Latency", getTargetingLatency());
            telemetry.addData("Parse Latency", getParseLatency());
            telemetry.addData("PythonOutput", java.util.Arrays.toString(getPythonOutput()));
            telemetry.addData("tx", getTx());
            telemetry.addData("txnc", getTxNC());
            telemetry.addData("ty", getTy());
            telemetry.addData("tync", getTyNC());
            telemetry.addData("Botpose", getBotPose().toString());


            if (Objects.equals(mode, "BR"))
                for (LLResultTypes.BarcodeResult br : getBarcodeResults())
                    telemetry.addData("Barcode", "Data: %s", br.getData());

            if (Objects.equals(mode, "CL"))
                for (LLResultTypes.ClassifierResult cr : getClassifierResults())
                    telemetry.addData("Classifier", "Class: %s, Confidence: %.2f",
                            cr.getClassName(), cr.getConfidence());

            if (Objects.equals(mode, "DE"))
                for (LLResultTypes.DetectorResult dr : getDetectorResults())
                    telemetry.addData("Detector", "Class: %s, Area: %.2f",
                            dr.getClassName(), dr.getTargetArea());

            if (Objects.equals(mode, "FI"))
                for (LLResultTypes.FiducialResult fr : getFiducialResults())
                    telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f",
                            fr.getFiducialId(), fr.getFamily(),
                            fr.getTargetXDegrees(), fr.getTargetYDegrees());

            if (Objects.equals(mode, "CO"))
                for (LLResultTypes.ColorResult cr : getColorResults())
                    telemetry.addData("Color", "X: %.2f, Y: %.2f",
                            cr.getTargetXDegrees(), cr.getTargetYDegrees());
        } else {
            telemetry.addData("Limelight", "No data available");
        }
    }

    // ✅ Getter methods (for use anywhere else in your code)

    public Pose3D getBotPose() {
        if (botpose == null) {
            botpose = new Pose3D(
                    new Position(),
                    new YawPitchRollAngles(AngleUnit.DEGREES, 0.0, 0.0, 0.0, 0L)
            );
        }
        return botpose;
    }

    public double getCaptureLatency() { return captureLatency; }
    public double getTargetingLatency() { return targetingLatency; }
    public double getTotalLatency() { return captureLatency + targetingLatency; }
    public double getParseLatency() { return parseLatency; }
    public double[] getPythonOutput() { return pythonOutput; }

    public double getTx() { return tx; }
    public double getTxNC() { return txnc; }
    public double getTy() { return ty; }
    public double getTyNC() { return tync; }

    public List<LLResultTypes.BarcodeResult> getBarcodeResults() { return barcodeResults; }
    public List<LLResultTypes.ClassifierResult> getClassifierResults() { return classifierResults; }
    public List<LLResultTypes.DetectorResult> getDetectorResults() { return detectorResults; }
    public List<LLResultTypes.FiducialResult> getFiducialResults() { return fiducialResults; }
    public List<LLResultTypes.ColorResult> getColorResults() { return colorResults; }

    public LLStatus getStatus() { return status; }
    public LLResult getRawResult() { return result; }
}