package org.firstinspires.ftc.teamcode.Learning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
@TeleOp(name = "Servo_Test66")
public class Servo_Test extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        CRServo servo;

        servo = hardwareMap.get(CRServo.class, "servo");

        boolean servoState = false;
        boolean aDebounce = false;

        waitForStart();

        while(opModeIsActive()) {

            boolean faceButtonA = gamepad1.a;

            if (faceButtonA && !servoState && !aDebounce) {

                servo.setPower(1);
                servoState = true;
                aDebounce = true;
            }

            if (faceButtonA && servoState && !aDebounce) {

                servo.setPower(0);
                servoState = false;
                aDebounce = true;
            }

            if(!faceButtonA) aDebounce = false;


            telemetry.addData("aPressed", faceButtonA);
            telemetry.addData("servoState", servoState);

            telemetry.update();
        }
    }
}