package com.harkka.livegreen;

import static java.lang.Math.round;

public class BMI implements Calculable {
    public static BMI bmi = new BMI();

    @Override
        public float calculateBMI(float height, float weight) {
            float index;
            index = (weight / (height * height));
            float bmi =  (float) (round(index * 100.0) / 100.0);

            System.out.println("BMI: " + bmi );

            return bmi;
    }
    @Override
    public BMI getCalculable() { return bmi; }
}

