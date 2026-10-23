package healthcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import healthcalc.exceptions.InvalidHealthDataException;

@DisplayName("Tests para el patrón Adapter (API del hospital)")
class AdapterHospitalTest {

    private final HealthCalcImpl calculator = HealthCalcImpl.getInstance();
    private final HealthHospital hospital = new AdapterHospital(calculator, calculator);

    @Test
    @DisplayName("El BMI recibe el peso en gramos y devuelve valor y clasificación en español")
    void bmiConvertsGramsAndTranslatesCategory() throws Exception {
        Tuple<Float, String> result = hospital.indiceMasaCorporal(1.75f, 70000);

        assertEquals(70f / (1.75f * 1.75f), result.x, 0.01f);
        assertEquals("Normal", result.y);
    }

    @Test
    @DisplayName("La clasificación del BMI se traduce al español")
    void bmiCategoryIsTranslated() throws Exception {
        assertEquals("Obesidad Clase I", hospital.indiceMasaCorporal(1.0f, 32000).y);
        assertEquals("Delgadez severa", hospital.indiceMasaCorporal(1.0f, 15000).y);
    }

    @Test
    @DisplayName("El IBW se devuelve en gramos")
    void ibwIsReturnedInGrams() throws Exception {
        assertEquals(68750, hospital.pesoCorporalIdeal('M', 1.75f));
        assertEquals(55000, hospital.pesoCorporalIdeal('W', 1.60f));
    }

    @ParameterizedTest(name = "Género inválido: {0}")
    @ValueSource(chars = {'X', 'm', 'w', ' '})
    @DisplayName("El IBW rechaza géneros distintos de 'M' o 'W'")
    void ibwRejectsInvalidGender(char gender) {
        assertThrows(InvalidHealthDataException.class, () -> hospital.pesoCorporalIdeal(gender, 1.75f));
    }

    @Test
    @DisplayName("Los datos fuera de rango se siguen rechazando a través del adaptador")
    void invalidDataIsRejected() {
        assertThrows(InvalidHealthDataException.class, () -> hospital.indiceMasaCorporal(1.75f, 0));
        assertThrows(InvalidHealthDataException.class, () -> hospital.pesoCorporalIdeal('M', 3.5f));
    }
}
