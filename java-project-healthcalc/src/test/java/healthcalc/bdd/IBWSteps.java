package healthcalc.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import healthcalc.Gender;
import healthcalc.HealthCalcImpl;
import healthcalc.IdealBodyWeight;
import healthcalc.Patient;
import healthcalc.Person;
import healthcalc.exceptions.InvalidHealthDataException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author aissaomar1204-dev (ISAgrupo03)
 */
public class IBWSteps {

    private IdealBodyWeight healthCalc;
    private double altura;
    private char generoIngresado;
    private double resultado;
    private boolean exceptionThrown;

    @Given("la calculadora de salud está iniciada para el PCI")
    public void la_calculadora_de_salud_esta_iniciada_para_el_pci() {
        healthCalc = HealthCalcImpl.getInstance();
    }

    @Given("los datos del paciente son altura {double} cm y género {string}")
    public void los_datos_del_paciente_son(Double altura, String genero) {
        this.altura = altura;
        this.generoIngresado = genero.charAt(0);
    }

    @When("ejecuto el cálculo del PCI")
    public void ejecuto_el_calculo_del_pci() {
        try {
            Person paciente = new Patient(70.0f, (float) (altura / 100.0), Gender.fromChar(generoIngresado), 30);
            resultado = healthCalc.idealBodyWeight(paciente);
            exceptionThrown = false;
        } catch (InvalidHealthDataException e) {
            exceptionThrown = true;
        }
    }

    @Then("el resultado del PCI debe ser {double} con una tolerancia de {double}")
    public void el_resultado_del_pci_debe_ser(Double esperado, Double tolerancia) {
        assertEquals(esperado, resultado, tolerancia);
    }

    @Then("el sistema debe lanzar una excepción indicando que los datos de salud son inválidos para el PCI")
    public void el_sistema_debe_lanzar_una_excepcion_en_pci() {
        assertEquals(true, exceptionThrown);
    }
}