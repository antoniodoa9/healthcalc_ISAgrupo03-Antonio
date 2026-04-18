package healthcalc.view;

import java.awt.event.ActionListener;

/**
 * @author palomamtnz13
 */
public interface ViewBMI {
    
    public double getWeight();

    public double getHeightValue();

    public void setResult(double bmi, String classification);

    public void setMessage(String msg); 

    public void setController(ActionListener ctr);
}
