package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//noah test for pushing but like for real j
public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        Image img = null;
        try {
            // Replace "your_image_name.png" with your actual file name
            // You need to ensure the file path is correct for your project structure
            img = ImageIO.read(new File("FtcRobotController/src/main/res/drawable-xhdpi/field-2025-juice-dark.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 0, 0))

                //.splineTo(new Vector2d(30,30), Math.toRadians(180))
                //.splineToConstantHeading(new Vector2d(0,0), Math.PI)
                .splineToSplineHeading(new Pose2d(30,30, 3*Math.PI/2), 3*Math.PI/2)
                .build());

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}