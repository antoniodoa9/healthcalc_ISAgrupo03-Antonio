package healthcalc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import healthcalc.HealthCalc;
import healthcalc.exceptions.InvalidHealthDataException;
import healthcalc.view.ViewIBW;

public class CtrIBW implements ActionListener {

    private HealthCalc model;
    private ViewIBW view;

    public CtrIBW(HealthCalc model, ViewIBW view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("CalcularIBW")) {
            try {
                double height = view.getHeightValue();
                char gender = view.getGender();
                
                double result = model.idealBodyWeight(height, gender);
                
                view.setResult(result);
            } catch (NumberFormatException ex) {
                view.setMessage("Error: por favor, introduzca números válidos.");
            } catch (InvalidHealthDataException ex) {
                view.setMessage("Error: " + ex.getMessage());
            }
        }
    }
}