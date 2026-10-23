package healthcalc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import healthcalc.Gender;
import healthcalc.IdealBodyWeight;
import healthcalc.Patient;
import healthcalc.Person;
import healthcalc.exceptions.InvalidHealthDataException;
import healthcalc.view.ViewIBW;

public class CtrIBW implements ActionListener {

    private IdealBodyWeight model;
    private ViewIBW view;

    public CtrIBW(IdealBodyWeight model, ViewIBW view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("CalcularIBW")) {
            try {
                float height = (float) (view.getHeightValue() / 100.0);
                char genderChar = view.getGender();

                Gender gender = Gender.fromChar(genderChar);
                Person patient = new Patient(70.0f, height, gender, 30);
                
                float result = model.idealBodyWeight(patient);
                
                view.setResult(result);
            } catch (NumberFormatException ex) {
                view.setMessage("Error: por favor, introduzca números válidos.");
            } catch (InvalidHealthDataException ex) {
                view.setMessage("Error: " + ex.getMessage());
            }
        }
    }
}