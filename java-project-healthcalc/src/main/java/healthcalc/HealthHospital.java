package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public interface HealthHospital {
    Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws InvalidHealthDataException;
    int pesoCorporalIdeal(char genero, float altura) throws InvalidHealthDataException;
}