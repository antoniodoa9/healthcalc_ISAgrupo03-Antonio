package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class Main {
    public static void main(String[] args) {
        HealthCalc healthCalc = new HealthCalcImpl();
        
        try {
            double weight = 75; // kg
            double height = 1.75; // m
            char genero = 'M'; // hombre
            int age = 30; // años

            System.out.println("Datos: Peso=" + weight +"kg, Altura=" + height + "cm, Género=" + genero + ", Edad=" + age + " años");

            // BMI (Cálculo numérico)
            double bmiValue = healthCalc.bmi(weight, height);
            System.out.println("BMI: " + String.format("%.2f", bmiValue));

            // Clasificación del BMI
            String bmiClass = healthCalc.bmiClassification(bmiValue);
            System.out.println("Clasificación del BMI: " + bmiClass);

            // IBW (peso Ideal)
            double ibw = healthCalc.idealBodyWeight(height*100, genero); //paso los m a cm
            System.out.println("Peso Ideal (IBW): " + String.format("%.2f", ibw) + " kg");

            // Harris-Benedict (TMB)
            double tmb = healthCalc.harrisBenedict(weight, height*100, genero, age); // paso los m a cm
            System.out.println("Tasa Metabólica Basal (Harris-Benedict): " + String.format("%.2f", tmb) + " kcal/día");

        } catch (InvalidHealthDataException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
