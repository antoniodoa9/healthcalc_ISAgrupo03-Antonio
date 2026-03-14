package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

/**
 * Calculator of some health parameters of persons.
 * 
 * @author ISA
 *
 */
public interface HealthCalc {
	
	/**
	 * Calculate the BMI classification of a person.
	 * The BMI classification is based on the following table:
	 * Underweight: BMI < 18.5
	 * Normal weight: 18.5 <= BMI < 25
	 * Overweight: 25 <= BMI < 30
	 * Obesity: BMI >= 30
	 *
	 * @param bmi	Body Mass Index of the person (kg/m2).
	 * @return	  	The BMI classification of the person.
	 * @throws Exception
	 */
	public String bmiClassification(double bmi) throws InvalidHealthDataException;
	
	/**
	 * Calculate the Body Mass Index (BMI) of a person with the Harris-Benedict formula:
	 *
	 * @param weight	Weight of the person (kg).
	 * @param height 	Height of the person (cm).
	 * @return	  		The Body Mass Index of the person (kg/m2).
	 * @throws Exception
	 */
	public double bmi(double weight, double height) throws InvalidHealthDataException;

	/**
	 * Calculate the BMR of a person with the Harris-Benedict formula:
	 *
	 * @param weight	Weight of the person (kg).
	 * @param height 	Height of the person (cm).
	 * @param gender	Gender of the person ('M' for male, 'W' for woman).
	 * @param age		Age of the person (years).
	 * @return	  		The BMR of the person (kcal/day).
	 * @throws Exception
	 */
	public double harrisBenedict(double weight, double height, char gender, int age) throws InvalidHealthDataException;

	/**
	 * Calculate the Ideal Body Weight (IBW) of a person with the Lorentz formula.
	 *
	 * @param height 	Height of the person (cm).
	 * @param gender	Gender of the person ('M' for male, 'W' for woman).
	 * @return	  		The IBW of the person (kg).
	 * @throws InvalidHealthDataException
	 */
	public double idealBodyWeight(double height, char gender) throws InvalidHealthDataException;
}
