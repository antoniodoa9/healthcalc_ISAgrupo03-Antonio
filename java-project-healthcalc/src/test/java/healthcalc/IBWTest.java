package healthcalc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import healthcalc.exceptions.InvalidHealthDataException;

/**
 * Tests for the Ideal Body Weight (IBW) metric using the Lorentz formula.
 * 
 * Using the AAA pattern (Arrange, Act, Assert) for the tests.
 * 
 * @author Aissa (ISAgrupo03)
 */
@DisplayName("Tests para la métrica IBW (Fórmula de Lorentz)")
public class IBWTest {

    private HealthCalc healthCalc;

    @BeforeEach
    void setUp() {
        healthCalc = new HealthCalcImpl();
    }

    @Nested
    @DisplayName("Cálculos válidos de la métrica IBW")
    class IBWValidosTests {

        @ParameterizedTest(name = "Hombre: altura {0} cm -> IBW esperado {1} kg")
        @CsvSource({
                "170.0, 65.0",
                "180.0, 72.5",
                "150.0, 50.0" 
        })
        @DisplayName("Cálculo válido de IBW para hombres")
        void testIBWHombre(double height, double expected) throws InvalidHealthDataException {
            double result = healthCalc.idealBodyWeight(height, 'M');
            assertEquals(expected, result, 0.01);
        }

        @ParameterizedTest(name = "Mujer: altura {0} cm -> IBW esperado {1} kg")
        @CsvSource({
                "160.0, 55.0",
                "170.0, 60.0",
                "150.0, 50.0" 
        })
        @DisplayName("Cálculo válido de IBW para mujeres")
        void testIBWMujer(double height, double expected) throws InvalidHealthDataException {
            double result = healthCalc.idealBodyWeight(height, 'W');
            assertEquals(expected, result, 0.01);
        }
    }

    @Nested
    @DisplayName("Cálculos inválidos de la métrica IBW")
    class IBWInvalidosTests {

        @Test
        @DisplayName("Lanzar excepción cuando el género es inválido")
        void testIBWSexoInvalido() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(170.0, 'X')),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(170.0, 'm')), 
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(170.0, ' '))
            );
        }

        @Test
        @DisplayName("Lanzar excepción cuando la altura es cero o negativa")
        void testIBWAlturaCeroONegativa() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(0.0, 'M')),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(-170.0, 'W'))
            );
        }

        @ParameterizedTest(name = "Altura mínima inválida: {0} cm")
        @ValueSource(doubles = {29.9, 15.0})
        @DisplayName("Bloqueo de alturas inferiores al límite biológico mínimo (30 cm)")
        void testIBWAlturaMinimaImposible(double height) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(height, 'M'));
        }

        @ParameterizedTest(name = "Altura máxima inválida: {0} cm")
        @ValueSource(doubles = {300.1, 350.0, 500.0})
        @DisplayName("Bloqueo de alturas superiores al límite biológico máximo (300 cm)")
        void testIBWAlturaMaximaImposible(double height) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(height, 'W'));
        }
    }
}