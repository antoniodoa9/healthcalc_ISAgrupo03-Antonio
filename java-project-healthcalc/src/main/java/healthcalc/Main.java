package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class Main {
    public static void main(String[] args) {
        HealthCalc healthCalc = new HealthCalcImpl();
        
        try {
            double weight = 75.0; // kg
            double height = 1.75; // m
            char gender = 'M'; // male
            int age = 30; // years

            System.out.println("Data: Weight=" + weight +"kg, Height=" + height + "m, Gender=" + gender + ", Age=" + age + " years");

            // BMI 
            double bmiValue = healthCalc.bmi(weight, height);
            System.out.println("BMI: " + String.format("%.2f", bmiValue));

            // BMI classification
            String bmiClass = healthCalc.bmiClassification(bmiValue);
            System.out.println("BMI classification: " + bmiClass);

            // IBW (ideal body weigth)
            double ibw = healthCalc.idealBodyWeight(height*100, gender); // m to cm
            System.out.println("Ideal body weight (IBW): " + String.format("%.2f", ibw) + " kg");

            // Harris-Benedict (BMR)
            double tmb = healthCalc.harrisBenedict(weight, height*100, gender, age); // m to cm
            System.out.println("Basal metabolic rate (Harris-Benedict): " + String.format("%.2f", tmb) + " kcal/day");

        } catch (InvalidHealthDataException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
