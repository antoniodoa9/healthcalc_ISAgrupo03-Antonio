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

/**
 * Tests for the Harris-Benedict metric.
 *
 * Using the AAA pattern (Arrange, Act, Assert) for the tests.
 *
 * @author antoniodoa9 (ISAgrupo03)
 */
@DisplayName("Tests para la métrica BMR (Harris-Benedict)")
public class HarrisBenedictTest {

    private HealthCalc healthCalc;

    @BeforeEach
    void setup() {
        healthCalc = new HealthCalcImpl();
    }

    @Nested
    @DisplayName("Cálculos válidos de la métrica Harris-Benedict")
    class HarrisBenedictValidosTests {

        @ParameterizedTest(name = "Hombre: peso {0} kg, altura {1} cm, edad {2} años, BMR esperado {3}")
        @CsvSource({
                "70.0, 175.0, 25, 1724.05",
                "85.0, 180.0, 30, 1920.62",
                "60.0, 160.0, 40, 1432.94"
        })
        @DisplayName("Cálculo válido de la métrica Harris-Benedict para hombres")
        void testHarrisBenedictHombre(double weight, double height, int age, double expected) throws InvalidHealthDataException {
            double result = healthCalc.harrisBenedict(weight, height, 'M', age);
            assertEquals(expected, result, 0.01);
        }

        @ParameterizedTest(name = "Mujer: peso {0} kg, altura {1} cm, edad {2} años -> BMR esperado {3}")
        @CsvSource({
                "60.0, 165.0, 25, 1405.33",
                "75.0, 170.0, 30, 1537.88",
                "55.0, 155.0, 40, 1263.17"
        })
        @DisplayName("Cálculo válido de la métrica Harris-Benedict para mujeres")
        void testHarrisBenedictMujer(double weight, double height, int age, double expected) throws InvalidHealthDataException {
            double result = healthCalc.harrisBenedict(weight, height, 'W', age);
            assertEquals(expected, result, 0.01);
        }
    }

    @Nested
    @DisplayName("Cálculos inválidos de la métrica Harris-Benedict")
    class HarrisBenedictInvalidosTests {

        @Test
        @DisplayName("Lanzar excepción cuando el sexo es inválido")
        void testHarrisBenedictSexoInvalido() {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, 175.0, 'X', 25));
        }

        @Test
        @DisplayName("Lanzar excepción cuando las variables son cero")
        void testHarrisBenedictVariablesCero() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(0, 175.0, 'M', 25)),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, 0, 'M', 25))
                // Note: The age can be 0 (a newborn), so zero is not checked here.
            );
        }

        @Test
        @DisplayName("Lanzar excepción cuando los valores son negativos")
        void testHarrisBenedictNegativos() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(-70.0, 175.0, 'M', 25)),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, -175.0, 'M', 25)),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, 175.0, 'M', -25))
            );
        }

        @ParameterizedTest(name = "Peso mínimo inválido: {0} kg")
        @ValueSource(doubles = {0.99, 0.50})
        @DisplayName("Bloqueo de pesos inferiores al límite biológico mínimo (1 kg)")
        void testHarrisBenedictPesoMinimoImposible(double weight) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(weight, 175.0, 'M', 25));
        }

        @ParameterizedTest(name = "Peso máximo inválido: {0} kg")
        @ValueSource(doubles = {700.1, 1000.0, 5000.0})
        @DisplayName("Bloqueo de pesos superiores al límite biológico máximo (700 kg)")
        void testHarrisBenedictPesoMaximoImposible(double weight) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(weight, 175.0, 'M', 25));
        }

        @ParameterizedTest(name = "Altura mínima inválida: {0} cm")
        @ValueSource(doubles = {29.9, 15.0})
        @DisplayName("Bloqueo de alturas inferiores al límite biológico mínimo (30 cm)")
        void testHarrisBenedictAlturaMinimaImposible(double height) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, height, 'M', 25));
        }

        @ParameterizedTest(name = "Altura máxima inválida: {0} cm")
        @ValueSource(doubles = {300.1, 350.0, 500.0})
        @DisplayName("Bloqueo de alturas superiores al límite biológico máximo (300 cm)")
        void testHarrisBenedictAlturaMaximaImposible(double height) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, height, 'M', 25));
        }

        @ParameterizedTest(name = "Edad máxima inválida: {0} años")
        @ValueSource(ints = {121, 150, 200})
        @DisplayName("Bloqueo de edades superiores al límite biológico máximo (120 años)")
        void testHarrisBenedictEdadMaximaImposible(int age) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.harrisBenedict(70.0, 175.0, 'M', age));
        }
    }
}