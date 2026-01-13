package org.firstinspires.ftc.teamcode.Learning;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp_Starter")
public class TeleOp_Starter extends LinearOpMode{

//comment
    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive mecanumDrive = new SampleMecanumDrive(hardwareMap);
        //Sets up all motors and other configurations


        waitForStart();

        double speedMultiplier = 1;
        boolean debounce = false;
        //boolean smoothToggle = false;
        //variables for speed manipulation

        DcMotorEx intakeMotorR;
        DcMotorEx intakeMotorL;
        DcMotorEx launchMotorR;
        DcMotorEx launchMotorL;

        launchMotorR = hardwareMap.get(DcMotorEx.class, "launchMotorR");
        launchMotorL = hardwareMap.get(DcMotorEx.class, "launchMotorL");
        intakeMotorR = hardwareMap.get(DcMotorEx.class, "intakeMotorR");
        intakeMotorL = hardwareMap.get(DcMotorEx.class, "intakeMotorL");

        CRServo beltServo;
        Servo launchServo;

        beltServo = hardwareMap.get(CRServo.class, "beltServo");
        launchServo = hardwareMap.get(Servo.class, "launchServo");

        boolean pd2BumperLDebounce = false;
        boolean pd2BumperRDebounce = false;

        double launchMotorRPower = 0.0;
        double launchMotorLPower = 0.0;
        double intakeMotorRPower = 0.0;
        double intakeMotorLPower = 0.0;

        boolean servoState = false;
        boolean aDebounce = false;

        boolean pd2yDebounce = false;
        double servoPosition = 0.3;

        while(opModeIsActive()) {

            double maxPower;

            boolean pad2FaceButtonA = gamepad2.a;
            boolean pad2FaceButtonY = gamepad2.y;
            boolean pd2BumperLeft = gamepad2.left_bumper;
            boolean pd2BumperRight = gamepad2.right_bumper;

            boolean faceButtonB = gamepad1.b;

            boolean bumperRight = gamepad1.right_bumper;
            boolean bumperLeft = gamepad1.left_bumper;
            // Bumper controls for speed manipulation

            float triggerRight = gamepad2.right_trigger;
            float triggerLeft = gamepad2.left_trigger;
            // triggers give a float from 0-1 instead of just a boolean because why the hell not
            // trigger PLACEHOLDER controls for the aiming and firing

            double xInput = gamepad1.left_stick_x;
            double yInput = gamepad1.left_stick_y;
            double rotationalInput = gamepad1.right_stick_x;

            double[] FOD = mecanumDrive.fieldOrientedDrive(xInput, yInput);
            //Returns the altered Field Oriented Drive x and y outputs

            double xOutput = FOD[0];
            double yOutput = FOD[1];

            double leftFrontPower, leftBackPower, rightFrontPower, rightBackPower;

            launchServo.setPosition(servoPosition);

            launchMotorR.setPower(launchMotorRPower);
            launchMotorL.setPower(launchMotorLPower);
            intakeMotorR.setPower(intakeMotorRPower);
            intakeMotorL.setPower(intakeMotorLPower);

            //Mecanum wheel equations
            leftFrontPower = yOutput + xOutput - rotationalInput;
            rightFrontPower = yOutput - xOutput + rotationalInput;
            leftBackPower = yOutput - xOutput - rotationalInput;
            rightBackPower = yOutput + xOutput + rotationalInput;
            //THIS IS VERY WRONG, BUT IT WORKS SO IDC!

            maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            maxPower = Math.max(maxPower, Math.abs(leftBackPower));
            maxPower = Math.max(maxPower, Math.abs(rightBackPower));
            //Limits power to 1.0 and translates other values relative to the maximum value

            if (bumperLeft && !debounce && speedMultiplier > 0.25){

                speedMultiplier -= 0.25;
                debounce = true;

            }
            // decreases speed multiplier
            if (bumperRight && !debounce && speedMultiplier < 1){

                speedMultiplier += 0.25;
                debounce = true;

            }
            // increases speed multiplier
            if(!bumperRight && !bumperLeft) debounce = false;

            if (speedMultiplier < 0.25) speedMultiplier = 0.25;

            if (triggerLeft > 0.5){

                // do something with apriltag, either aiming at them or aiming and moving to a certain spot

            }
            // aims at apriltag or moves to certain spot relative to apriltag and also aims at it
            // PROBABLY NOT GETTING DONE IGNORE PLEASE


            if (maxPower > 1.0) {

                leftFrontPower /= maxPower;
                rightFrontPower /= maxPower;
                leftBackPower /= maxPower;
                rightBackPower /= maxPower;

            }
            // scale beltServo inputs to 1

            if (pad2FaceButtonA && !servoState && !aDebounce) {

                beltServo.setPower(1);
                servoState = true;
                aDebounce = true;
            }

            if (pad2FaceButtonA && servoState && !aDebounce) {

                beltServo.setPower(0);
                servoState = false;
                aDebounce = true;
            }

            if(!pad2FaceButtonA) aDebounce = false;

            if (pad2FaceButtonY && !pd2yDebounce && launchServo.getPosition() == 0.3) {
                //when y pressed, if debounce is not activated and the servo is at 0 degrees

                servoPosition = 1.0;
                pd2yDebounce = true;

            }

            if (pad2FaceButtonY && !pd2yDebounce && launchServo.getPosition() == 1.0) {

                servoPosition = 0.3;
                pd2yDebounce = true;

            }

            if (!pad2FaceButtonY) pd2yDebounce = false;

            if (pd2BumperLeft && !pd2BumperLDebounce && intakeMotorRPower == 0.0 && intakeMotorLPower == 0.0){

                intakeMotorRPower = -0.5;
                intakeMotorLPower = 0.5;
                pd2BumperLDebounce = true;

            }

            if (pd2BumperLeft && !pd2BumperLDebounce && !(intakeMotorRPower == 0) && !(intakeMotorLPower == 0.0)){

                intakeMotorRPower = 0.0;
                intakeMotorLPower = 0.0;
                pd2BumperLDebounce = true;

            }

            if (!pd2BumperLeft) pd2BumperLDebounce = false;

            if (pd2BumperRight && !pd2BumperRDebounce && launchMotorRPower == 0.0 && launchMotorLPower == 0.0){

                launchMotorRPower = -1.0;
                launchMotorLPower = 1.0;
                pd2BumperRDebounce = true;

            }

            if (pd2BumperRight && !pd2BumperRDebounce && !(launchMotorRPower == 0) && !(launchMotorLPower == 0.0)){

                launchMotorRPower = 0.0;
                launchMotorLPower = 0.0;
                pd2BumperRDebounce = true;

            }

            if (!pd2BumperRight) pd2BumperRDebounce = false;

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
            telemetry.addData("rightBumper", bumperRight);
            telemetry.addData("leftBumper", bumperLeft);
            telemetry.addData("rightTrigger",triggerRight);
            telemetry.addData("leftTrigger", triggerLeft);

            telemetry.addData("pd2aPressed", pad2FaceButtonA);
            telemetry.addData("servoState", servoState);

            telemetry.addData("pd2yPressed", pad2FaceButtonY);
            telemetry.addData("servoPositon", launchServo.getPosition());
            telemetry.addData("launchServoDebounce", pd2yDebounce);

            telemetry.addData("pd2lbPressed", pd2BumperLeft);
            telemetry.addData("intakeMotorRPower", intakeMotorRPower);
            telemetry.addData("intakeMotorLPower", intakeMotorLPower);
            telemetry.addData("launchServoDebounce", pd2BumperLDebounce);

            telemetry.addData("pd2rbPressed", pd2BumperRight);
            telemetry.addData("launchMotorRPower", launchMotorRPower);
            telemetry.addData("launchMotorLPower", launchMotorLPower);
            telemetry.addData("launchServoDebounce", pd2BumperRDebounce);

            telemetry.update();

            /* if (faceButtonB && !smoothToggle) {

                smoothToggle = true;

                mecanumDrive.setMotorPowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
                //Smoothing is implemented within the setMotorPowers method

           }
            else {

                smoothToggle = false;

            }
            //this SHOULD make the smoothing toggleable with the b button */

            mecanumDrive.setMotorPowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
            //Smoothing is implemented within the setMotorPowers method



        }
    }

}