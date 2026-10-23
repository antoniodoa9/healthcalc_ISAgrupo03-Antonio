package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public abstract class BaseDecoratorVersion implements HealthHospital {
    
    private HealthHospital hospitalCalc;

    public BaseDecoratorVersion(HealthHospital h) {
        this.hospitalCalc = h;
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws InvalidHealthDataException {
        return hospitalCalc.indiceMasaCorporal(altura, peso);
    }

    @Override
    public int pesoCorporalIdeal(char genero, float altura) throws InvalidHealthDataException {
        return hospitalCalc.pesoCorporalIdeal(genero, altura);
    }
}