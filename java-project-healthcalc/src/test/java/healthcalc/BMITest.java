package healthcalc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import healthcalc.exceptions.InvalidHealthDataException;

@DisplayName("Tests para la métrica BMI y su clasificación.")
public class BMITest {

    private HealthCalcImpl healthCalc;

    @BeforeEach
    void setUp() {
        healthCalc = HealthCalcImpl.getInstance();
    }

    @Nested
    @DisplayName("Métrica del BMI")
    class BMIMetricTests {

        @Test
        @DisplayName("Cálculo de BMI con valores estándar válidos")
        void testBmiValido() throws InvalidHealthDataException {
            Person patient = new Patient(70.0f, 1.75f, Gender.MALE, 30);
            float expectedBmi = 70.0f / (float) Math.pow(1.75f, 2);

            float result = healthCalc.bodyMassIndex(patient);

            assertEquals(expectedBmi, result, 0.01f);
        }

        @Test
        @DisplayName("Lanzar excepción cuando el peso es cero")
        void testBmiPesoCero() {
            Person patient = new Patient(0.0f, 1.70f, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(patient));
        }

        @Test
        @DisplayName("Lanzar excepción cuando la altura es cero")
        void testBmiAlturaCero() {
            Person patient = new Patient(70.0f, 0.0f, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(patient));
        }

        @Test
        @DisplayName("Lanzar excepción cuando los valores son negativos")
        void testBmiNegativos() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(new Patient(-70.0f, 1.70f, Gender.MALE, 30))),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(new Patient(70.0f, -1.70f, Gender.MALE, 30)))
            );
        }

        @ParameterizedTest(name = "Peso mínimo inválido: {0} kg")
        @ValueSource(floats = {-10.0f, 0.0f, 0.99f})
        @DisplayName("Bloqueo de pesos inferiores al límite biológico mínimo (1 kg)")
        void testPesoMinimoImposible(float weight) {
            Person patient = new Patient(weight, 1.70f, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(patient));
        }

        @ParameterizedTest(name = "Peso máximo inválido: {0} kg")
        @ValueSource(floats = {700.1f, 1000.0f, 5000.0f})
        @DisplayName("Bloqueo de pesos superiores al límite biológico máximo (700 kg)")
        void testPesoMaximoImposible(float weight) {
            Person patient = new Patient(weight, 1.70f, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(patient));
        }

        @ParameterizedTest(name = "Altura mínima inválida: {0} m")
        @ValueSource(floats = {-0.50f, 0.0f, 0.29f})
        @DisplayName("Bloqueo de alturas inferiores al límite biológico mínimo (0.30 m)")
        void testAlturaMinimaImposible(float height) {
            Person patient = new Patient(70.0f, height, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(patient));
        }

        @ParameterizedTest(name = "Altura máxima inválida: {0} m")
        @ValueSource(floats = {3.01f, 3.50f, 5.00f})
        @DisplayName("Bloqueo de alturas superiores al límite biológico máximo (3.00 m)")
        void testAlturaMaximoImposible(float height) {
            Person patient = new Patient(70.0f, height, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.bodyMassIndex(patient));
        }
    }

    @Nested
    @DisplayName("Clasificación básica a partir del BMI")
    class BMIClassificationTests {

        @ParameterizedTest(name = "BMI {0} debe ser clasificado como {1}")
        @CsvSource({
            "15.0, SEVERE_THINNESS",
            "16.5, MODERATE_THINNESS",
            "18.0, MILD_THINNESS",
            "22.0, NORMAL",
            "27.0, OVERWEIGHT",
            "32.0, OBESE_CLASS_I",
            "37.0, OBESE_CLASS_II",
            "45.0, OBESE_CLASS_III"
        })
        @DisplayName("Clasificación de las 8 categorías de BMI")
        void testBmiClassificationCompleta(float bmi, BMICategory expected) throws InvalidHealthDataException {
            Person patient = new Patient(bmi, 1.0f, Gender.MALE, 30);
            BMICategory result = healthCalc.category(patient);
            assertEquals(expected, result);
        }

        @ParameterizedTest(name = "Peso negativo al clasificar: {0}")
        @ValueSource(floats = {-50.0f, -1.0f, -0.01f})
        @DisplayName("La clasificación rechaza pesos negativos (el BMI nunca llega a ser negativo)")
        void testBmiClassificationMinimoImposible(float bmi) {
            Person patient = new Patient(bmi, 1.0f, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.category(patient));
        }

        @ParameterizedTest(name = "BMI máximo extremo: {0}")
        @ValueSource(floats = {150.1f, 200.0f, 500.0f})
        @DisplayName("Bloqueo de valores de BMI superiores al límite humano razonable (150)")
        void testBmiClassificationMaximoImposible(float bmi) {
            Person patient = new Patient(bmi, 1.0f, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.category(patient));
        }
    }
}