package healthcalc.view;

import java.awt.event.ActionListener;

public interface ViewIBW {
    
    double getHeightValue();
    char getGender();
    
    void setResult(double pci);
    void setMessage(String msg);
    
    void setController(ActionListener ctr);
}