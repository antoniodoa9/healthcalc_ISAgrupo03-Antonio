package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class AdapterHospital implements HealthHospital {
	
    private BodyMassIndex bmiCalc;
    private IdealBodyWeight ibwCalc;

    public AdapterHospital(BodyMassIndex bmiCalc, IdealBodyWeight ibwCalc) {
        this.bmiCalc = bmiCalc;
        this.ibwCalc = ibwCalc;
    }

    @Override
    public int pesoCorporalIdeal(char genero, float altura) throws InvalidHealthDataException {
    	
        Gender genEnum = Gender.fromChar(genero);

        // The IBW only uses height and gender; weight and age are placeholders
        Person patient = new Patient(70.0f, altura, genEnum, 30);

        float ibwKilos = ibwCalc.idealBodyWeight(patient);

        return (int) (ibwKilos * 1000);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws InvalidHealthDataException {
    	
        float pesoKilos = (float) (peso / 1000.0);

        // The hospital API does not provide the gender for the BMI, so it is left unknown
        Person patient = new Patient(pesoKilos, altura, null, 30);

        float valorBmi = bmiCalc.bodyMassIndex(patient);
        BMICategory catEnum = bmiCalc.category(patient);

        return new Tuple<>((float) valorBmi, catEnum.spanishLabel());
    }
}