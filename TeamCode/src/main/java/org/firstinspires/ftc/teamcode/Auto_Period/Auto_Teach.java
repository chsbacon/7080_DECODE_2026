package org.firstinspires.ftc.teamcode.Auto_Period;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

//adding test for pushing to github
@TeleOp(name="Auto_Teach")
public class Auto_Teach extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(56, -60, Math.PI/2);

        drive.setPoseEstimate(startPose);
//nineminenine
        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                //.setConstrai  nts(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                //.followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(56, -60, Math.PI/2))

                .splineToConstantHeading(new Vector2d(0,-20), 2*Math.PI/3)
                .splineToConstantHeading(new Vector2d(0,15), Math.PI/3)
                .splineToConstantHeading(new Vector2d(14,23),0)
                .splineToConstantHeading(new Vector2d(27, -2), -Math.PI/2)
                .splineToConstantHeading(new Vector2d(59, 5), Math.PI/2)
                .splineToConstantHeading(new Vector2d(12, 55), 3*Math.PI)
                .splineToConstantHeading(new Vector2d(-35, 10), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-33, -40), 3*Math.PI/2)
                .build();






        ;

        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }
}
