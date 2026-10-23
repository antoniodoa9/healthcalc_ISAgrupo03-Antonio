package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class HealthCalcImpl implements BodyMassIndex, IdealBodyWeight, BasalMetabolicRate {

    private static HealthCalcImpl instance;

    private HealthCalcImpl() {
    }

    public static synchronized HealthCalcImpl getInstance() {
        if (instance == null) {
            instance = new HealthCalcImpl();
        }
        return instance;
    }

    @Override
    public BMICategory category(Person person) throws InvalidHealthDataException {
        float bmi = this.bodyMassIndex(person); 
        
        if (bmi < 0) {
            throw new InvalidHealthDataException("El IMC no puede ser negativo.");
        }
        if (bmi > 150) {
            throw new InvalidHealthDataException("El IMC debe estar dentro de un rango posible [0-150].");
        }
        
        if (bmi < 16.0f) {
            return BMICategory.SEVERE_THINNESS;
        } else if (bmi < 17.0f) {
            return BMICategory.MODERATE_THINNESS;
        } else if (bmi < 18.5f) {
            return BMICategory.MILD_THINNESS;
        } else if (bmi < 25.0f) {
            return BMICategory.NORMAL;
        } else if (bmi < 30.0f) {
            return BMICategory.OVERWEIGHT;
        } else if (bmi < 35.0f) {
            return BMICategory.OBESE_CLASS_I;
        } else if (bmi < 40.0f) {
            return BMICategory.OBESE_CLASS_II;
        } else {
            return BMICategory.OBESE_CLASS_III;
        }
    }

    @Override
    public float bodyMassIndex(Person person) throws InvalidHealthDataException {
        float weight = person.weight();
        float height = person.height();

        if (weight <= 0) {
            throw new InvalidHealthDataException("El peso debe ser positivo.");
        }
        if (height <= 0) {
            throw new InvalidHealthDataException("La altura debe ser positiva.");
        }
        if (weight < 1 || weight > 700) {
            throw new InvalidHealthDataException("El peso debe estar dentro de un rango posible [1-700] kg.");
        }
        if (height < 0.30 || height > 3.00) {
            throw new InvalidHealthDataException("La altura debe estar dentro de un rango posible [0.30-3.00] m.");
        }
        return (float) (weight / Math.pow(height, 2));
    }

    @Override
    public float basalMetabolicRate(Person person) throws InvalidHealthDataException {
        float weight = person.weight();
        float height = person.height() * 100f;
        Gender gender = person.gender();
        int age = person.age();

        if (gender == null) {
            throw new InvalidHealthDataException("El género no puede ser nulo.");
        }
        if (weight <= 0) {
            throw new InvalidHealthDataException("El peso debe ser positivo.");
        }
        if (height <= 0) {
            throw new InvalidHealthDataException("La altura debe ser positiva.");
        }
        if (age < 0) {
            throw new InvalidHealthDataException("La edad no puede ser negativa.");
        }
        if (weight < 1 || weight > 700) {
            throw new InvalidHealthDataException("El peso debe estar dentro de un rango posible [1-700] kg.");
        }
        if (height < 30 || height > 300) {
            throw new InvalidHealthDataException("La altura debe estar dentro de un rango posible [30-300] cm.");
        }
        if (age > 120) {
            throw new InvalidHealthDataException("La edad debe estar dentro de un rango posible [0-120] años.");
        }

        float bmr = 0.0f;
        if (gender == Gender.MALE) {
            bmr = (float) (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age));
        } else { 
            bmr = (float) (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age));
        }
        return bmr;
    }

    @Override
    public float idealBodyWeight(Person person) throws InvalidHealthDataException {
        float height = person.height() * 100f;
        Gender gender = person.gender();
        
        if (gender == null) {
            throw new InvalidHealthDataException("El género no puede ser nulo.");
        }
        
        if (height <= 0) {
            throw new InvalidHealthDataException("La altura debe ser positiva.");
        }
        if (height < 30 || height > 300) {
            throw new InvalidHealthDataException("La altura debe estar dentro de un rango posible [30-300] cm.");
        }

        float ibw = 0.0f;
        if (gender == Gender.MALE) {
            ibw = (float) ((height - 100) - ((height - 150) / 4.0));
        } else { 
            ibw = (float) ((height - 100) - ((height - 150) / 2.0));
        }
        
        return ibw;
    }
}