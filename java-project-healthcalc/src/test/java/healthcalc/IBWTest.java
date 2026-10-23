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

@DisplayName("Tests para la métrica IBW (Fórmula de Lorentz)")
public class IBWTest {

    private HealthCalcImpl healthCalc;

    @BeforeEach
    void setUp() {
        healthCalc = HealthCalcImpl.getInstance();
    }

    @Nested
    @DisplayName("Cálculos válidos de la métrica IBW")
    class IBWValidosTests {

        @ParameterizedTest(name = "Hombre: altura {0} m -> IBW esperado {1} kg")
        @CsvSource({
                "1.70, 65.0",
                "1.80, 72.5",
                "1.50, 50.0" 
        })
        @DisplayName("Cálculo válido de IBW para hombres")
        void testIBWHombre(float height, float expected) throws InvalidHealthDataException {
            Person patient = new Patient(70.0f, height, Gender.MALE, 30);
            float result = healthCalc.idealBodyWeight(patient);
            assertEquals(expected, result, 0.01f);
        }

        @ParameterizedTest(name = "Mujer: altura {0} m -> IBW esperado {1} kg")
        @CsvSource({
                "1.60, 55.0",
                "1.70, 60.0",
                "1.50, 50.0" 
        })
        @DisplayName("Cálculo válido de IBW para mujeres")
        void testIBWMujer(float height, float expected) throws InvalidHealthDataException {
            Person patient = new Patient(60.0f, height, Gender.FEMALE, 30);
            float result = healthCalc.idealBodyWeight(patient);
            assertEquals(expected, result, 0.01f);
        }
    }

    @Nested
    @DisplayName("Cálculos inválidos de la métrica IBW")
    class IBWInvalidosTests {

        @Test
        @DisplayName("Lanzar excepción cuando el género es nulo")
        void testIBWSexoInvalido() {
            Person patient = new Patient(70.0f, 1.70f, null, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(patient));
        }

        @Test
        @DisplayName("Lanzar excepción cuando la altura es cero o negativa")
        void testIBWAlturaCeroONegativa() {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(new Patient(70.0f, 0.0f, Gender.MALE, 30))),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(new Patient(70.0f, -1.70f, Gender.FEMALE, 30)))
            );
        }

        @ParameterizedTest(name = "Altura mínima inválida: {0} m")
        @ValueSource(floats = {0.299f, 0.15f})
        @DisplayName("Bloqueo de alturas inferiores al límite biológico mínimo (30 cm)")
        void testIBWAlturaMinimaImposible(float height) {
            Person patient = new Patient(70.0f, height, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(patient));
        }

        @ParameterizedTest(name = "Altura máxima inválida: {0} m")
        @ValueSource(floats = {3.001f, 3.50f, 5.00f})
        @DisplayName("Bloqueo de alturas superiores al límite biológico máximo (300 cm)")
        void testIBWAlturaMaximaImposible(float height) {
            Person patient = new Patient(70.0f, height, Gender.FEMALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(patient));
        }

        @ParameterizedTest(name = "Hombre: altura {0} m -> IBW no positivo")
        @ValueSource(floats = {0.30f, 0.50f, 0.83f})
        @DisplayName("Bloqueo de alturas de hombre que producirían un IBW negativo o nulo")
        void testIBWHombreNoPositivo(float height) {
            Person patient = new Patient(70.0f, height, Gender.MALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(patient));
        }

        @ParameterizedTest(name = "Mujer: altura {0} m -> IBW no positivo")
        @ValueSource(floats = {0.30f, 0.40f, 0.50f})
        @DisplayName("Bloqueo de alturas de mujer que producirían un IBW negativo o nulo")
        void testIBWMujerNoPositivo(float height) {
            Person patient = new Patient(70.0f, height, Gender.FEMALE, 30);
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.idealBodyWeight(patient));
        }
    }

    @Nested
    @DisplayName("Conversión del género desde carácter")
    class GenderFromCharTests {

        @Test
        @DisplayName("'M' y 'W' se convierten en MALE y FEMALE")
        void testGeneroValido() {
            assertAll(
                () -> assertEquals(Gender.MALE, Gender.fromChar('M')),
                () -> assertEquals(Gender.FEMALE, Gender.fromChar('W'))
            );
        }

        @ParameterizedTest(name = "Género inválido: ''{0}''")
        @ValueSource(chars = {'X', 'm', 'w', 'F', ' '})
        @DisplayName("Lanzar excepción con cualquier carácter distinto de 'M' o 'W'")
        void testGeneroInvalido(char gender) {
            assertThrows(InvalidHealthDataException.class, () -> Gender.fromChar(gender));
        }
    }
}