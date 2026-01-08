package org.firstinspires.ftc.teamcode.Learning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@TeleOp(name = "Motor_Test66")
public class Motor_Test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx launchMotorR;
        DcMotorEx launchMotorL;
        launchMotorR = hardwareMap.get(DcMotorEx.class, "launchMotorR");
        launchMotorL = hardwareMap.get(DcMotorEx.class, "launchMotorL");

        boolean pd2BumperRDebounce = false;

        double launchMotorRPower = 0.0;
        double launchMotorLPower = 0.0;

        waitForStart();



        while (opModeIsActive()) {

            launchMotorR.setPower(launchMotorRPower);
            launchMotorL.setPower(launchMotorLPower);

            boolean pd2BumperRight = gamepad2.right_bumper;

            if (pd2BumperRight && !pd2BumperRDebounce && launchMotorRPower == 0.0 && launchMotorLPower == 0.0){

                launchMotorRPower = -0.5;
                launchMotorLPower = 0.5;
                pd2BumperRDebounce = true;

            }

            if (pd2BumperRight && !pd2BumperRDebounce && !(launchMotorRPower == 0) && !(launchMotorLPower == 0.0)){

                launchMotorRPower = 0.0;
                launchMotorLPower = 0.0;
                pd2BumperRDebounce = true;

            }

            if (!pd2BumperRight) pd2BumperRDebounce = false;

            telemetry.addData("yPressed", pd2BumperRight);
            telemetry.addData("launchMotorRPower", launchMotorRPower);
            telemetry.addData("launchMotorLPower", launchMotorLPower);
            telemetry.addData("launchServoDebounce", pd2BumperRDebounce);

            telemetry.update();

        }
    }
}