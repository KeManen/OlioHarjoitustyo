package com.harkka.livegreen.calculable;

import static java.lang.Math.round;

public class BMI implements Calculable {
    public static BMI bmi = new BMI();

    @Override
        public float calculateBMI(float height, float weight) {
            float index = (weight / (height * height));
            return (float) (round(index * 100.0) / 100.0);
    }
    @Override
    public BMI getCalculable() { return bmi; }
}

