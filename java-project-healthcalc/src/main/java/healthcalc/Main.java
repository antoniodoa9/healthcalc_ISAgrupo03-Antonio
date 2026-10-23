package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class Main {
    public static void main(String[] args) {
        
        HealthCalcImpl calculadora = HealthCalcImpl.getInstance();
        ProxyHealthCalc objetoProxy = new ProxyHealthCalc(calculadora, calculadora, calculadora);  
        HealthStats estadisticas = objetoProxy; 
        
        try {
            float weight = 75.0f; 
            float height = 1.75f; 
            Gender gender = Gender.MALE; 
            int age = 30; 
            
            System.out.println("Data: Weight=" + weight +"kg, Height=" + height + "m, Gender=" + gender + ", Age=" + age + " years");

            Person patient = new Patient(weight, height, gender, age);
            
            float bmiValue = objetoProxy.bodyMassIndex(patient);
            System.out.println("BMI: " + String.format("%.2f", bmiValue));
            
            BMICategory bmiClass = objetoProxy.category(patient);
            System.out.println("BMI classification: " + bmiClass);
            
            float ibw = objetoProxy.idealBodyWeight(patient); 
            System.out.println("Ideal body weight (IBW): " + String.format("%.2f", ibw) + " kg");
            
            float tmb = objetoProxy.basalMetabolicRate(patient); 
            System.out.println("Basal metabolic rate (Harris-Benedict): " + String.format("%.2f", tmb) + " kcal/day");
            
        } catch (InvalidHealthDataException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        
        System.out.println("\nPATRÓN ADAPTER");
        HealthHospital hospitalCalc = new AdapterHospital(objetoProxy, objetoProxy);
        
        try {
            float alturaHospital = 1.75f;
            int pesoGramos = 75000; 
            char generoHospital = 'M';
            
            System.out.println("Paciente registrado: Altura=" + alturaHospital + "m, Peso=" + pesoGramos + "g, Género=" + generoHospital);  
            
            Tuple<Float, String> bmiHospital = hospitalCalc.indiceMasaCorporal(alturaHospital, pesoGramos);
            System.out.println("BMI Hospital: " + String.format("%.2f", bmiHospital.x) + " kg/m^2 - " + bmiHospital.y);
            
            int ibwHospital = hospitalCalc.pesoCorporalIdeal(generoHospital, alturaHospital);
            System.out.println("IBW Hospital: " + ibwHospital + " g");
            
        } catch (Exception e) {
            System.err.println("Error en el sistema del hospital: " + e.getMessage());
        }

        System.out.println("\nESTADÍSTICAS DEL PROXY");
        System.out.println("Total pacientes: " + estadisticas.numTotalPacientes());
        System.out.println("Hombres: " + estadisticas.numSexoH());
        System.out.println("Mujeres: " + estadisticas.numSexoM());
        System.out.println("Peso medio: " + String.format("%.2f", estadisticas.pesoMedio()) + " kg");
        System.out.println("Altura media: " + String.format("%.2f", estadisticas.alturaMedia()) + " m");
        System.out.println("IMC medio: " + String.format("%.2f", estadisticas.imcMedio()));

        System.out.println("\nPATRÓN DECORATOR");
        System.out.println("Americano + Inglés:");
        try {
            HealthHospital verEnglish = new EnglishDecorator(hospitalCalc);
            HealthHospital verAmerican = new AmericanDecorator(verEnglish);
            
            float alturaPies = 6.0f;
            int pesoLibras = 180;
            
            System.out.println("Paciente registrado: Altura=" + alturaPies + " pies, Peso=" + pesoLibras + " libras"); //americano
        
            verAmerican.indiceMasaCorporal(alturaPies, pesoLibras);
            int ibwLibras = verAmerican.pesoCorporalIdeal('M', alturaPies);
            System.out.println("IBW: " + ibwLibras + " lb");

        } catch (Exception e) {
            System.err.println("Error en los decoradores: " + e.getMessage());
        }

        System.out.println("\nEuropeo + Español:"); 
        try {
            HealthHospital verSpanish = new SpanishDecorator(hospitalCalc);
            HealthHospital verEuropean = new EuropeanDecorator(verSpanish);
              
            float alturaMetros = 1.75f;
            int pesoGramos = 75000;
            
            System.out.println("Paciente registrado: Altura=" + alturaMetros + " m, Peso=" + pesoGramos + " g");
        
            verEuropean.indiceMasaCorporal(alturaMetros, pesoGramos);
            verEuropean.pesoCorporalIdeal('M', alturaMetros);

        } catch (Exception e) {
            System.err.println("Error en los decoradores: " + e.getMessage());
        }

        
    }
}