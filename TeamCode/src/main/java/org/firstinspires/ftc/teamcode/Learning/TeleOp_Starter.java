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
        //variables for speed manipulation
        while(opModeIsActive()) {

            double max;

            boolean dUp = gamepad1.dpad_up;
            boolean dDown = gamepad1.dpad_down;
            boolean dRight = gamepad1.dpad_right;
            boolean dLeft = gamepad1.dpad_left;
            // dpad controls for speed manipulation
//trying to push again
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

            if (dDown && !debounce && speedMultiplier > 0.2){

                speedMultiplier -= 0.2;
                debounce = true;

            }
            // decreases speed multiplier
            if (dUp && !debounce && speedMultiplier < 1){

                speedMultiplier += 0.2;
                debounce = true;

            }
            // increases speed multiplier
            if(!dUp && !dDown) debounce = false;

            // debounce code where speedMultiplier only changes once per press
            if (dRight) speedMultiplier = 1;
            //resets speedMultiplier to max
            if (dLeft) speedMultiplier = 0.2;
            //sets speedMultiplier to min (will probably be removed because of how useless this will probably be)

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
            telemetry.addData("dPadUp", dUp);
            telemetry.addData("dPadDown", dDown);

            telemetry.update();

            //Smoothing is implemented within the setMotorPowers method
            mecanumDrive.setMotorPowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);






        }
    }

}