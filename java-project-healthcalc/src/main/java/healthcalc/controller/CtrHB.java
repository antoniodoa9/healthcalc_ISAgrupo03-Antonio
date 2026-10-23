package healthcalc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import healthcalc.BasalMetabolicRate;
import healthcalc.Gender;
import healthcalc.Patient;
import healthcalc.Person;
import healthcalc.exceptions.InvalidHealthDataException;
import healthcalc.view.ViewHB;

public class CtrHB implements ActionListener {

    private BasalMetabolicRate model;
    private ViewHB view;

    public CtrHB(BasalMetabolicRate model, ViewHB view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("CalcularHB")){
            try {
                float weight = (float) view.getWeight();
                float height = (float) (view.getHeightValue() / 100.0);
                int age = view.getAge();
                char genderChar = view.getGender();

                Gender gender = Gender.fromChar(genderChar);
                Person patient = new Patient(weight, height, gender, age);

                float result = model.basalMetabolicRate(patient);
                view.setResult(result);
            } catch (NumberFormatException ex) {
                view.setMessage("Error: por favor, introduzca números válidos.");
            } catch (InvalidHealthDataException ex) {
                view.setMessage("Error: " + ex.getMessage());
            }
        }
    }
    
}
