package healthcalc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import healthcalc.HealthCalc;
import healthcalc.exceptions.InvalidHealthDataException;
import healthcalc.view.ViewHB;

public class CtrHB implements ActionListener {

    private HealthCalc model;
    private ViewHB view;

    public CtrHB(HealthCalc model, ViewHB view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("CalcularHB")){
            try {
                double weight = view.getWeight();
                double height = view.getHeightValue();
                int age = view.getAge();
                char gender = view.getGender();
                double result = model.harrisBenedict(weight, height, gender, age);
                view.setResult(result);
            } catch (NumberFormatException ex) {
                view.setMessage("Error: por favor, introduzca números válidos.");
            } catch (InvalidHealthDataException ex) {
                view.setMessage("Error: " + ex.getMessage());
            }
        }
    }
    
}
