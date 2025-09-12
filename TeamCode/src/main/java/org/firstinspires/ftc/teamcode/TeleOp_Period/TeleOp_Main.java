package org.firstinspires.ftc.teamcode.TeleOp_Period;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "TeleOp_Main")
public class TeleOp_Main extends LinearOpMode{


    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive mecanumDrive = new SampleMecanumDrive(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {

            double max;

            double xInput = gamepad1.left_stick_x;
            double yInput = gamepad1.left_stick_y;
            double rotationalInput = gamepad1.right_stick_x;

            double leftFrontPower, leftBackPower, rightFrontPower, rightBackPower;

            //Mecanum wheel equations
            leftFrontPower = yInput - xInput - rotationalInput;
            rightFrontPower = yInput - xInput + rotationalInput;
            leftBackPower = yInput + xInput - rotationalInput;
            rightBackPower = yInput + xInput + rotationalInput;
            //THIS IS VERY WRONG, BUT IT WORKS SO IDC!

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {

                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;

            }

            //Smoothing is implemented within the setMotorPowers method
            mecanumDrive.setMotorPowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);



        }
    }

}