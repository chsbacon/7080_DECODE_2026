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

        waitForStart();


        //DcMotorEx intakeMotorR;
        //DcMotorEx intakeMotorL;
        DcMotorEx launchMotorR;
        DcMotorEx launchMotorL;

        launchMotorR = hardwareMap.get(DcMotorEx.class, "launchMotorR");
        launchMotorL = hardwareMap.get(DcMotorEx.class, "launchMotorL");
        //intakeMotorR = hardwareMap.get(DcMotorEx.class, "intakeMotorR");
        //intakeMotorL = hardwareMap.get(DcMotorEx.class, "intakeMotorL");

        CRServo launchServo;

        launchServo = hardwareMap.get(CRServo.class, "launchServo");

        boolean pd2BumperLDebounce = false;
        boolean pd2BumperRDebounce = false;

        double launchMotorPower = 0.0;
        double intakeMotorPower = 0.0;


        boolean servoState = false;

        boolean servoDebounce = false;
       // boolean launchToggle = false;

        while(opModeIsActive()) {

            double maxPower;

            boolean pd2BumperLeft = gamepad2.left_bumper;

            float pd2TriggerRight = gamepad2.right_trigger;
            float pd2TriggerLeft = gamepad2.left_trigger;

            double xInput = gamepad1.left_stick_x;
            double yInput = gamepad1.left_stick_y;
            double rotationalInput = gamepad1.right_stick_x;

            double pd2YInput = gamepad2.left_stick_y;
            double pd2LYInput = gamepad2.right_stick_y;

            double[] FOD = mecanumDrive.fieldOrientedDrive(xInput, yInput);

            double xOutput = FOD[0];
            double yOutput = FOD[1];

            double leftFrontPower, leftBackPower, rightFrontPower, rightBackPower;

            launchServo.setPower(pd2YInput);

                launchMotorR.setPower(launchMotorPower - (pd2LYInput / 8));
                launchMotorL.setPower(-(launchMotorPower - (pd2LYInput / 8)));

            //intakeMotorR.setPower(intakeMotorPower);
            //intakeMotorL.setPower(-intakeMotorPower);


            leftFrontPower = yOutput - xOutput - rotationalInput;
            rightFrontPower = yOutput - xOutput + rotationalInput;
            leftBackPower = yOutput + xOutput - rotationalInput;
            rightBackPower = yOutput + xOutput + rotationalInput;

            maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            maxPower = Math.max(maxPower, Math.abs(leftBackPower));
            maxPower = Math.max(maxPower, Math.abs(rightBackPower));

            if (pd2BumperLeft && !pd2BumperLDebounce && launchMotorPower == 0.0){

                launchMotorPower = 0.625;
                pd2BumperLDebounce = true;

            }

            if (pd2BumperLeft && !pd2BumperLDebounce && launchMotorPower == 0.625){

                launchMotorPower = 0.0;
                pd2BumperLDebounce = true;

            }

            if (!pd2BumperLeft && pd2BumperLDebounce) pd2BumperLDebounce = false;

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


            telemetry.addData("rightTrigger",pd2TriggerRight);
            telemetry.addData("leftTrigger", pd2TriggerLeft);

            telemetry.addData("servoState", servoState);

            telemetry.addData("launchServoPower", launchServo.getPower());
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