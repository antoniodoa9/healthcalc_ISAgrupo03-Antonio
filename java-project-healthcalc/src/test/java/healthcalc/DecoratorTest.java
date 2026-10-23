package healthcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests para el doble patrón Decorator (versión e idioma)")
class DecoratorTest {

    private final HealthCalcImpl calculator = HealthCalcImpl.getInstance();
    private final HealthHospital hospital = new AdapterHospital(calculator, calculator);

    @Nested
    @DisplayName("Decoradores de versión (unidades)")
    class VersionDecoratorTests {

        @Test
        @DisplayName("La versión europea no altera los resultados del hospital")
        void europeanDelegatesUnchanged() throws Exception {
            HealthHospital european = new EuropeanDecorator(hospital);

            assertEquals(hospital.indiceMasaCorporal(1.75f, 70000).x, european.indiceMasaCorporal(1.75f, 70000).x, 0.0001f);
            assertEquals(hospital.pesoCorporalIdeal('M', 1.75f), european.pesoCorporalIdeal('M', 1.75f));
        }

        @Test
        @DisplayName("La versión americana recibe pies y libras para el BMI")
        void americanBmiConvertsFeetAndPounds() throws Exception {
            HealthHospital american = new AmericanDecorator(hospital);
            float meters = 6.0f * 0.3048f;
            float kilos = 180 / 2.20462f;

            Tuple<Float, String> result = american.indiceMasaCorporal(6.0f, 180);

            assertEquals(kilos / (meters * meters), result.x, 0.05f);
            assertEquals("Normal", result.y);
        }

        @Test
        @DisplayName("La versión americana recibe pies y devuelve el IBW en libras")
        void americanIbwReturnsPounds() throws Exception {
            HealthHospital american = new AmericanDecorator(hospital);
            float cm = 6.0f * 30.48f;
            float expectedKilos = (cm - 100) - ((cm - 150) / 4.0f);

            int pounds = american.pesoCorporalIdeal('M', 6.0f);

            assertEquals(Math.round(expectedKilos * 2.20462f), pounds, 1);
        }
    }

    @Nested
    @DisplayName("Decoradores de idioma")
    class LanguageDecoratorTests {

        private final PrintStream originalOut = System.out;
        private final ByteArrayOutputStream output = new ByteArrayOutputStream();

        @BeforeEach
        void captureOutput() {
            System.setOut(new PrintStream(output, true));
        }

        @AfterEach
        void restoreOutput() {
            System.setOut(originalOut);
        }

        @Test
        @DisplayName("El decorador español muestra el mensaje del IMC y del PCI sin alterar el resultado")
        void spanishPrintsMessages() throws Exception {
            HealthHospital spanish = new SpanishDecorator(hospital);

            float bmi = spanish.indiceMasaCorporal(1.75f, 70000).x;
            int ibw = spanish.pesoCorporalIdeal('M', 1.75f);

            assertEquals(hospital.indiceMasaCorporal(1.75f, 70000).x, bmi, 0.0001f);
            assertEquals(68750, ibw);
            String text = output.toString();
            assertTrue(text.contains("tiene un IMC de"), text);
            assertTrue(text.contains("tiene un PCI de"), text);
        }

        @Test
        @DisplayName("El decorador inglés muestra el mensaje del BMI y del IBW sin alterar el resultado")
        void englishPrintsMessages() throws Exception {
            HealthHospital english = new EnglishDecorator(hospital);

            float bmi = english.indiceMasaCorporal(1.75f, 70000).x;
            int ibw = english.pesoCorporalIdeal('W', 1.60f);

            assertEquals(hospital.indiceMasaCorporal(1.75f, 70000).x, bmi, 0.0001f);
            assertEquals(55000, ibw);
            String text = output.toString();
            assertTrue(text.contains("has a BMI of"), text);
            assertTrue(text.contains("has an IBW of"), text);
        }

        @Test
        @DisplayName("Los dos tipos de decorador se pueden combinar (americano + inglés)")
        void decoratorsCanBeCombined() throws Exception {
            HealthHospital combined = new AmericanDecorator(new EnglishDecorator(hospital));

            int pounds = combined.pesoCorporalIdeal('M', 6.0f);

            assertEquals(new AmericanDecorator(hospital).pesoCorporalIdeal('M', 6.0f), pounds);
            assertTrue(output.toString().contains("has an IBW of"));
        }
    }
}
