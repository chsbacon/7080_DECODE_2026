package org.firstinspires.ftc.teamcode.Learning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "TeleOp_Starter")
public class TeleOp_Starter extends LinearOpMode{


    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive mecanumDrive = new SampleMecanumDrive(hardwareMap);
        //Sets up all motors and other configurations

        waitForStart();

        double speedMultiplier = 1;
        boolean debounce = false;
        boolean smoothToggle = false;
        //variables for speed manipulation


        while(opModeIsActive()) {

            double max;

            boolean faceButtonB = gamepad1.b;

            boolean bRight = gamepad1.right_bumper;
            boolean bLeft = gamepad1.left_bumper;
            // Bumper controls for speed manipulation

            float tRight = gamepad2.right_trigger;
            float tLeft = gamepad2.left_trigger;
            // triggers give a float from 0-1 instead of just a binary because why the hell not
            // trigger PLACEHOLDER controls for the aiming and firing

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

            if (bLeft && !debounce && speedMultiplier > 0.25){

                speedMultiplier -= 0.25;
                debounce = true;

            }
            // decreases speed multiplier
            if (bRight && !debounce && speedMultiplier < 1){

                speedMultiplier += 0.25;
                debounce = true;

            }
            // increases speed multiplier
            if(!bRight && !bLeft) debounce = false;

            if (speedMultiplier < 0.25) speedMultiplier = 0.25;

            if (tLeft > 0.5){

                // do something with apriltag, either aiming at them or aiming and moving to a certain spot

            }
            // aims at apriltag or moves to certain spot relative to apriltag and also aims at it

            if (tRight > 0.5){

                // shoot the ball

            }
            //shoots ball whe right trigger is pressed

            if (max > 1.0) {

                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;

            }
            // if max is more than one make it one
            leftFrontPower *= speedMultiplier;
            rightFrontPower *= speedMultiplier;
            leftBackPower *= speedMultiplier;
            rightBackPower *= speedMultiplier;
            // multiplies speed by speedMultiplier to actually change it


            telemetry.addData("xInput yInput", "%4.2f, %4.2f", xInput, yInput);
            telemetry.addData("xOutput yOutput", "%4.2f, %4.2f", xOutput, yOutput);

            telemetry.addData("Heading (deg)", Math.toDegrees(mecanumDrive.getRawExternalHeading()));

            telemetry.addData("Front Left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            telemetry.addData("Speed multiplier", speedMultiplier);
            telemetry.addData("rightBumper", bRight);
            telemetry.addData("leftBumper", bLeft);
            telemetry.addData("rightTrigger",tRight);
            telemetry.addData("leftTrigger", tLeft);

            telemetry.update();

            if (faceButtonB && !smoothToggle) {

                smoothToggle = true;

                mecanumDrive.setMotorPowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
                //Smoothing is implemented within the setMotorPowers method

           }
            else {

                smoothToggle = false;

            }
            //this SHOULD make the smoothing toggleable with the b button






        }
    }

}