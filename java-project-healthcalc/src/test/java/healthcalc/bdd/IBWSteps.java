package healthcalc.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import healthcalc.HealthCalc;
import healthcalc.HealthCalcImpl;
import healthcalc.exceptions.InvalidHealthDataException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author aissaomar1204-dev (ISAgrupo03)
 */
public class IBWSteps {

    private HealthCalc healthCalc;
    private double altura;
    private char genero;
    private double resultado;
    private boolean exceptionThrown;

    @Given("la calculadora de salud está iniciada para el PCI")
    public void la_calculadora_de_salud_esta_iniciada_para_el_pci() {
        healthCalc = new HealthCalcImpl();
    }

    @Given("los datos del paciente son altura {double} cm y género {string}")
    public void los_datos_del_paciente_son(Double altura, String genero) {
        this.altura = altura;
        this.genero = genero.charAt(0);
    }

    @When("ejecuto el cálculo del PCI")
    public void ejecuto_el_calculo_del_pci() {
        try {
            resultado = healthCalc.idealBodyWeight(altura, genero);
            exceptionThrown = false;
        } catch (InvalidHealthDataException e) {
            exceptionThrown = true;
        }
    }

    @Then("el resultado del PCI debe ser {double} con una tolerancia de 0.01")
    public void el_resultado_del_pci_debe_ser(Double esperado) {
        assertEquals(esperado, resultado, 0.01);
    }

    @Then("el sistema debe lanzar una excepción indicando que los datos de salud son inválidos para el PCI")
    public void el_sistema_debe_lanzar_una_excepcion_en_pci() {
        assertEquals(true, exceptionThrown);
    }
}