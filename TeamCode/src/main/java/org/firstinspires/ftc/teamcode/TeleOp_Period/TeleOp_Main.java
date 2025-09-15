package org.firstinspires.ftc.teamcode.TeleOp_Period;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "TeleOp_Main")
public class TeleOp_Main extends LinearOpMode{


    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive mecanumDrive = new SampleMecanumDrive(hardwareMap);
        //Sets up all motors and other configurations
        waitForStart();

        while(opModeIsActive()) {

            double max;

            double xInput = gamepad1.left_stick_x;
            double yInput = gamepad1.left_stick_y;
            double rotationalInput = gamepad1.right_stick_x;

            double[] FOD = mecanumDrive.fieldOrientedDrive(xInput, yInput);
            //Returns the altered Field Oriented Drive x and y outputs

            double xOutput = FOD[0];
            double yOutput = FOD[1];

            double leftFrontPower, leftBackPower, rightFrontPower, rightBackPower;

            //Mecanum wheel equations
            leftFrontPower = yOutput - xOutput - rotationalInput;
            rightFrontPower = yOutput - xOutput + rotationalInput;
            leftBackPower = yOutput + xOutput - rotationalInput;
            rightBackPower = yOutput + xOutput + rotationalInput;
            //THIS IS VERY WRONG, BUT IT WORKS SO IDC!

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));
            //Limits power to 1.0 and translates other values relative to the maximum value

            if (max > 1.0) {

                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;

            }

            telemetry.addData("xInput yInput", "%4.2f, %4.2f", xInput, yInput);
            telemetry.addData("xOutput yOutput", "%4.2f, %4.2f", xOutput, yOutput);

            telemetry.addData("Heading (deg)", Math.toDegrees(mecanumDrive.getRawExternalHeading()));

            telemetry.addData("Front Left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            telemetry.update();

            //Smoothing is implemented within the setMotorPowers method
            mecanumDrive.setMotorPowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);


        }
    }

}