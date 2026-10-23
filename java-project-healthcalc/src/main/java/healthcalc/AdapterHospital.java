package healthcalc;

public class AdapterHospital implements HealthHospital {
	
    private BodyMassIndex bmiCalc;
    private IdealBodyWeight ibwCalc;

    public AdapterHospital(BodyMassIndex bmiCalc, IdealBodyWeight ibwCalc) {
        this.bmiCalc = bmiCalc;
        this.ibwCalc = ibwCalc;
    }

    @Override
    public int pesoCorporalIdeal(char genero, float altura) throws Exception {
    	
        Gender genEnum = Gender.fromChar(genero);

        Person patient= new Patient(70.0f, altura, genEnum, 30); 

        float ibwKilos = ibwCalc.idealBodyWeight(patient);

        return (int) (ibwKilos * 1000);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws Exception {
    	
        float pesoKilos = (float) (peso / 1000.0);

        Person patient = new Patient(pesoKilos, altura, Gender.MALE, 30);
        
        float valorBmi = bmiCalc.bodyMassIndex(patient);
        BMICategory catEnum = bmiCalc.category(patient);
        String clasificacion = "";
        switch (catEnum) {
            case SEVERE_THINNESS: clasificacion = "Delgadez severa"; break;
            case MODERATE_THINNESS: clasificacion = "Delgadez moderada"; break;
            case MILD_THINNESS: clasificacion = "Delgadez leve"; break;
            case NORMAL: clasificacion = "Normal"; break;
            case OVERWEIGHT: clasificacion = "Sobrepeso"; break;
            case OBESE_CLASS_I: clasificacion = "Obesidad Clase I"; break;
            case OBESE_CLASS_II: clasificacion = "Obesidad Clase II"; break;
            case OBESE_CLASS_III: clasificacion = "Obesidad Clase III"; break;
            default: clasificacion = catEnum.toString();
        }
        
        return new Tuple<>((float) valorBmi, clasificacion);
    }
}