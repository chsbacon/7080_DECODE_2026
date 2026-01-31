package org.firstinspires.ftc.teamcode.Auto_Period;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

//adding test for pushing to github
@Autonomous (name = "RedFarAuto")
public class RedFarAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(64, 22, Math.PI);

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                // WARNING: INTAKE AND FIRING CODE NOT ADDED, CURRENTLY COLLIDES WITH GATE ON PATH
                .splineToLinearHeading(new Pose2d(0, 0, 3*Math.PI/4), Math.PI)
                // Insert firing code here for preloaded three
                .splineToLinearHeading(new Pose2d(-11.8, 30, 3*Math.PI/2), Math.PI/2) // Spike 1
                .splineToConstantHeading(new Vector2d(-11.8,45), Math.PI/2)
                .splineToLinearHeading(new Pose2d(0, 0, 3*Math.PI/4), 3*Math.PI/2)
                // Insert more firing code, etc.
                .splineToLinearHeading(new Pose2d(11.6, 30, 3*Math.PI/2), Math.PI/2) // Spike 2
                .splineToConstantHeading(new Vector2d(11.6,50), Math.PI/2)
                .splineToLinearHeading(new Pose2d(0, 0, 3*Math.PI/4), Math.PI)
                .splineToLinearHeading(new Pose2d(34, 30, 3*Math.PI/2), Math.PI/2) // Spike 3
                .splineToConstantHeading(new Vector2d(34,50), Math.PI/2)
                .splineToLinearHeading(new Pose2d(0, 0, 3*Math.PI/4), Math.PI)
                .splineToLinearHeading(new Pose2d(10, 20, 3*Math.PI/2), Math.PI/2)
                .build();

        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }
}