package org.firstinspires.ftc.teamcode.Learning;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class Servo_Test extends OpMode {
    public CRServo servo;


    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servo");


    }


    @Override
    public void loop() {

        boolean servoState = false;

        boolean faceButtonA = gamepad1.a;


        while(true) { //this will be changed to while(opModeIsActive()) when implemented i just don't want to import allat rn

            if (faceButtonA && !servoState) {

                servo.setPower(1);
                servoState = true;
            }

            if (faceButtonA && servoState) {

                servo.setPower(0);
                servoState = false;

            }

        }


    }

}
