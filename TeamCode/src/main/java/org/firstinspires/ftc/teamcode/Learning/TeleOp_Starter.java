package org.firstinspires.ftc.teamcode.Learning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "TeleOp_Starter")
public class TeleOp_Starter extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
      
        waitForStart();

        SampleMecanumDrive mecanumDrive = new SampleMecanumDrive(hardwareMap);
        while(opModeIsActive()) {

            double max;

            double xInput = -gamepad1.left_stick_x;
            double yInput = -gamepad1.left_stick_y;
            double rotationalInput = gamepad1.right_stick_x;

            double[] FODvalue = mecanumDrive.fieldOrientedDrive(xInput, yInput);

            xInput = FODvalue[0];
            yInput = FODvalue[1];

            double Speed = -yInput;
            double Strafe = xInput;
            double Turn = -rotationalInput;



            double RightFrontWheel = Speed + Strafe - Turn;
            double LeftFrontWheel = Speed - Strafe - Turn;
            double RightBackWheel = Speed - Strafe + Turn;
            double LeftBackWheel = Speed + Strafe + Turn;

            max = Math.max(Math.abs(LeftFrontWheel), Math.abs(RightFrontWheel));
            max = Math.max(max, Math.abs(LeftBackWheel));
            max = Math.max(max, Math.abs(RightBackWheel));
            //Limits power to 1.0 and translates other values relative to the maximum value

            if (max > 1.0) {

                LeftFrontWheel /= max;
                RightFrontWheel /= max;
                LeftBackWheel /= max;
                RightBackWheel /= max;

            }


            telemetry.addData("xInput yInput", "%4.2f, %4.2f", xInput, yInput);


            telemetry.addData("Heading (deg)", Math.toDegrees(mecanumDrive.getRawExternalHeading()));

            telemetry.addData("Front Left/Right", "%4.2f, %4.2f", LeftFrontWheel, RightFrontWheel);
            telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", LeftBackWheel, RightBackWheel);


            telemetry.update();

            mecanumDrive.setMotorPowers(LeftFrontWheel, LeftBackWheel, RightBackWheel, RightFrontWheel);
        }
    }
}
