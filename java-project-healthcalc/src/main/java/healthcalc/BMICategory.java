package healthcalc;

public enum BMICategory {
    SEVERE_THINNESS("Delgadez severa"),
    MODERATE_THINNESS("Delgadez moderada"),
    MILD_THINNESS("Delgadez leve"),
    NORMAL("Normal"),
    OVERWEIGHT("Sobrepeso"),
    OBESE_CLASS_I("Obesidad Clase I"),
    OBESE_CLASS_II("Obesidad Clase II"),
    OBESE_CLASS_III("Obesidad Clase III");

    private final String spanishLabel;

    BMICategory(String spanishLabel) {
        this.spanishLabel = spanishLabel;
    }

    public String spanishLabel() {
        return spanishLabel;
    }
}
