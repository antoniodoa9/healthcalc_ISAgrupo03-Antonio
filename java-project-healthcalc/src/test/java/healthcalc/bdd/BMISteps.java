package healthcalc.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import healthcalc.HealthCalc;
import healthcalc.HealthCalcImpl;
import healthcalc.exceptions.InvalidHealthDataException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author palomamtnz13 (ISAgrupo03)
 */
public class BMISteps {
    private HealthCalc healthCalc;
    private double peso;
    private double altura;
    private double imcEntrada;
    private double imcResultado;    
    private String categoriaResultado;
    private boolean exceptionThrown;

    @Given("la calculadora de salud está iniciada para el IMC")
    public void la_calculadora_de_salud_esta_iniciada_para_el_imc() {
        healthCalc = new HealthCalcImpl();
    }

    // CÁLCULO DEL IMC

    @Given("los datos del paciente son peso {double} kg y altura {double} m")
    public void los_datos_del_paciente_son(Double peso, Double altura) {
        this.peso = peso;
        this.altura = altura;
    }

    @When("ejecuto el cálculo del IMC")
    public void ejecuto_el_calculo_del_imc() {
        try {
            imcResultado = healthCalc.bmi(peso, altura);
            exceptionThrown = false;
        } catch (InvalidHealthDataException e) {
            exceptionThrown = true;
        }
    }

    @Then("el resultado del IMC debe ser {double} con una tolerancia de 0.01")
    public void el_resultado_del_imc_debe_ser(Double imcEsperado) {
        assertEquals(imcEsperado, imcResultado, 0.01);
    }

    // CLASIFICACIÓN DEL IMC

    @Given("el valor calculado de IMC es {double}")
    public void el_valor_calculado_de_imc_es(Double imc) {
        this.imcEntrada = imc;
    }

    @When("ejecuto la clasificación del IMC")
    public void ejecuto_la_clasificacion_del_imc() {
        try {
            categoriaResultado = healthCalc.bmiClassification(imcEntrada);
            exceptionThrown = false;
        } catch (InvalidHealthDataException e) {
            exceptionThrown = true;
        }
    }

    @Then("la categoría devuelta debe ser {string}")
    public void la_categoria_del_imc_debe_ser(String categoriaEsperada) {
        assertEquals(categoriaEsperada, categoriaResultado);
    }


    // EXCEPCIONES

    @Then("el sistema debe lanzar una excepción indicando que los datos de salud son inválidos para el IMC")
    public void el_sistema_debe_lanzar_una_excepcion_en_imc() {
        assertEquals(true, exceptionThrown);
    }
}
