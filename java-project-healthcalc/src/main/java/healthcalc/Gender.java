package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public enum Gender {
    FEMALE,
    MALE;

    public static Gender fromChar(char gender) throws InvalidHealthDataException {
        switch (gender) {
            case 'M': return MALE;
            case 'W': return FEMALE;
            default: throw new InvalidHealthDataException("El género debe ser 'M' o 'W'.");
        }
    }
}
