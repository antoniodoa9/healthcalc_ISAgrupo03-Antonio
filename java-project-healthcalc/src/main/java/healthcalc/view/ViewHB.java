package healthcalc.view;

import java.awt.event.ActionListener;

/**
 * @author antoniodoa9
 */
public interface ViewHB {
    
    public double getWeight();

    public double getHeightValue();

    public int getAge();

    public char getGender();  

    public void setResult(double tmb);

    public void setMessage(String msg); 

    public void setController(ActionListener ctr);
    
}