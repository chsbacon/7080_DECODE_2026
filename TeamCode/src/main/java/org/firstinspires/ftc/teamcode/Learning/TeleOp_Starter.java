package org.firstinspires.ftc.teamcode.Learning;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "TeleOp_Starter")
public class TeleOp_Starter extends LinearOpMode{


    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive mecanumDrive = new SampleMecanumDrive(hardwareMap);

        CRServo launchServoL;
        CRServo launchServoR;

        //  DcMotorEx intakeMotor;
        DcMotorEx launchMotorR;
        DcMotorEx launchMotorL;
        DcMotorEx beltMotor;

        waitForStart();



        launchServoL = hardwareMap.get(CRServo.class, "launchServoL");
        launchServoR = hardwareMap.get(CRServo.class, "launchServoR");

        launchMotorR = hardwareMap.get(DcMotorEx.class, "launchMotorR");
        launchMotorL = hardwareMap.get(DcMotorEx.class, "launchMotorL");
       // intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        beltMotor = hardwareMap.get(DcMotorEx.class, "beltMotor");


        boolean pd2BumperLDebounce = false;
        boolean pd2BumperRDebounce = false;

        boolean bumperLDebounce = false;

        double launchMotorPower = 0.5;
        double intakeMotorPower = 0.0;

        boolean intakeState = false;

        boolean servoState = false;

        boolean servoDebounce = false;
       // boolean launchToggle = false;

        boolean launchState = false;
        boolean launchDebounce = false;

        double launchModifier = 0.0;

        while(opModeIsActive()) {

            double maxPower;

            boolean pd2BumperLeft = gamepad2.left_bumper;
            boolean pd2BumperRight = gamepad2.right_bumper;
            boolean pd2FaceButtonA = gamepad2.a;
            boolean pd2FaceButtonY = gamepad2.y;


            double xInput = gamepad1.left_stick_x;
            double yInput = gamepad1.left_stick_y;
            double rotationalInput = gamepad1.right_stick_x;

            boolean bumperLeft = gamepad1.left_bumper;

            double pd2LYInput = gamepad2.left_stick_y;
            double pd2RYInput = gamepad2.right_stick_y;


            double[] FOD = mecanumDrive.fieldOrientedDrive(xInput, yInput);

            double xOutput = FOD[0];
            double yOutput = FOD[1];

            double leftFrontPower, leftBackPower, rightFrontPower, rightBackPower;

            launchServoR.setPower(pd2RYInput/2);
            launchServoL.setPower(-(pd2RYInput/2));

            beltMotor.setPower(pd2LYInput);

            if(launchState) {
                launchMotorR.setPower(launchMotorPower+launchModifier);
                launchMotorL.setPower(-(launchMotorPower+launchModifier));
            }

            leftFrontPower = yOutput - xOutput - rotationalInput;
            rightFrontPower = yOutput - xOutput + rotationalInput;
            leftBackPower = yOutput + xOutput - rotationalInput;
            rightBackPower = yOutput + xOutput + rotationalInput;

            maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            maxPower = Math.max(maxPower, Math.abs(leftBackPower));
            maxPower = Math.max(maxPower, Math.abs(rightBackPower));

            if (bumperLeft && !bumperLDebounce && !intakeState) {
                intakeState = true;
                bumperLDebounce = true;

            }

            if (bumperLeft && !bumperLDebounce && intakeState) {
                intakeState = false;
                bumperLDebounce = true;
            }

            if (!bumperLeft && bumperLDebounce) bumperLDebounce = false;

            if(pd2BumperLeft && !launchDebounce && !launchState){
                launchState = true;
                launchDebounce = true;
            }
            if(pd2BumperLeft && !launchDebounce && launchState){
                launchState = false;
                launchDebounce = true;
            }
            if(pd2FaceButtonA && !launchDebounce && launchState && launchModifier != -0.5){
                launchModifier -= 0.05;
                launchDebounce = true;
            }
            if(pd2FaceButtonY && !launchDebounce && launchState && launchModifier != 0.5){
                launchModifier += 0.05;
                launchDebounce = true;
            }
            if(!(pd2FaceButtonY && pd2FaceButtonA && pd2BumperRight && pd2BumperLeft) && launchDebounce) launchDebounce = false;

            if (maxPower > 1.0) {

                leftFrontPower /= maxPower;
                rightFrontPower /= maxPower;
                leftBackPower /= maxPower;
                rightBackPower /= maxPower;

            }

            telemetry.addData("xInput yInput", "%4.2f, %4.2f", xInput, yInput);

            telemetry.addData("Heading (deg)", Math.toDegrees(mecanumDrive.getRawExternalHeading()));

            telemetry.addData("Front Left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            telemetry.addData("servoState", servoState);

            telemetry.addData("launchServoDebounce", servoDebounce);

            telemetry.addData("pd2lbPressed", pd2BumperLeft);
            telemetry.addData("intakeMotoRPower", intakeMotorPower);;
            telemetry.addData("launchServoDebounce", pd2BumperLDebounce);

            telemetry.addData("launchMotorPower", launchMotorPower);
            telemetry.addData("launchServoDebounce", pd2BumperRDebounce);

            telemetry.update();

            mecanumDrive.setMotorPowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);

        }
    }

}