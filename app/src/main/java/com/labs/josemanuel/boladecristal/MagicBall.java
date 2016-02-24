package com.labs.josemanuel.boladecristal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;

import java.util.Random;

/**
 * Created by JoseManuel on 28/12/2015.
 */
public class MagicBall {

    public static int colorControl;

    public static String getPrediction(Context context) {

        Resources res = context.getResources();
        String[] answers = res.getStringArray(R.array.answer_array);
        Random randomGenerator = new Random();
        int randomText = randomGenerator.nextInt(20);
        colorControl = randomText;
        return answers[randomText];
    }

    public static int getColor(Context context) {
        Resources res = context.getResources();
        String[] color = res.getStringArray(R.array.color_array);

        if (colorControl <= 9) {
            return Color.parseColor(color[0]);
        }
        else if (colorControl > 10 && colorControl < 14) {
            return Color.parseColor(color[1]);
        } else return Color.parseColor(color[2]);

    }


}
