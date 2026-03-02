package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class HealthCalcImpl implements HealthCalc {

    @Override
    public String bmiClassification(double bmi) throws InvalidHealthDataException {
        if (bmi < 0) {
            throw new InvalidHealthDataException("BMI cannot be negative.");
        }
        if (bmi > 150) {
            throw new InvalidHealthDataException("BMI must be within a possible biological range [0-150].");
        }
        
        if (bmi < 16.0) {
            return "Severe Thinness";
        } else if (bmi < 17.0) {
            return "Moderate Thinness";
        } else if (bmi < 18.5) {
            return "Mild Thinness";
        } else if (bmi < 25.0) {
            return "Normal";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else if (bmi < 35.0) {
            return "Obese Class I";
        } else if (bmi < 40.0) {
            return "Obese Class II";
        } else {
            return "Obese Class III";
        }
        
    }

    @Override
    public double bmi(double weight, double height) throws InvalidHealthDataException {
        if (weight <= 0) {
            throw new InvalidHealthDataException("Weight must be positive.");
        }
        if (height <= 0) {
            throw new InvalidHealthDataException("Height must be positive.");
        }
        if (weight < 1 || weight > 700) {
            throw new InvalidHealthDataException("Weight must be within a possible biological range [1-700] kg.");
        }
        if (height < 0.30 || height > 3.00) {
            throw new InvalidHealthDataException("Height must be within a possible biological range [0.30-3.00] m.");
        }
        return weight / Math.pow(height, 2);
    }

    @Override
    public double harrisBenedict(double weight, double height, char gender, int age) throws InvalidHealthDataException {
        if (gender != 'M' && gender != 'W') {
            throw new InvalidHealthDataException("Gender must be 'M' or 'W'.");
        }
        if (weight <= 0) {
            throw new InvalidHealthDataException("Weight must be positive.");
        }
        if (height <= 0) {
            throw new InvalidHealthDataException("Height must be positive.");
        }
        if (age < 0) {
            throw new InvalidHealthDataException("Age cannot be negative.");
        }
        if (weight < 1 || weight > 700) {
            throw new InvalidHealthDataException("Weight must be within a possible biological range, this is, between 1 and 700 kg.");
        }
        if (height < 30 || height > 300) {
            throw new InvalidHealthDataException("Height must be within a possible biological range, this is, between 30 and 300 cm.");
        }
        if (age > 120) {
            throw new InvalidHealthDataException("Age must be within a possible biological range, this is, between 0 and 120 years.");
        }

        double bmr = 0.0;
        if (gender == 'M') {
            bmr = 88.362 + (13.397*weight) + (4.799*height) - (5.677*age);
        } else { // If the program gets here, it's necessarily 'W' due to the initial exception check.
            bmr = 447.593 + (9.247*weight) + (3.098*height) - (4.330*age);
        }
        return bmr;
    }

    @Override
    public double idealBodyWeight(double height, char gender) throws InvalidHealthDataException {
        if (gender != 'M' && gender != 'W') {
            throw new InvalidHealthDataException("Gender must be 'M' or 'W'.");
        }
        
        if (height <= 0) {
            throw new InvalidHealthDataException("Height must be positive.");
        }
        if (height < 30 || height > 300) {
            throw new InvalidHealthDataException("Height must be within a possible biological range, this is, between 30 and 300 cm.");
        }

        double ibw = 0.0;
        if (gender == 'M') {
            ibw = (height - 100) - ((height - 150) / 4.0);
        } else { 
            // If the program gets here, it's necessarily 'W' due to the initial exception check.
            ibw = (height - 100) - ((height - 150) / 2.0);
        }
        
        return ibw;
    }
}
