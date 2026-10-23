package healthcalc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import healthcalc.exceptions.InvalidHealthDataException;

class ProxyHealthCalcTest {
    private final HealthCalcImpl calculator = HealthCalcImpl.getInstance();

    @Test
    void countsEachPersonOnlyOnceAcrossMetrics() throws InvalidHealthDataException {
        ProxyHealthCalc proxy = new ProxyHealthCalc(calculator, calculator, calculator);
        Person man = new Patient(70f, 1.75f, Gender.MALE, 30);
        Person woman = new Patient(90f, 1.75f, Gender.FEMALE, 30);

        float manBmi = proxy.bodyMassIndex(man);
        proxy.bodyMassIndex(man);
        proxy.idealBodyWeight(man);
        proxy.basalMetabolicRate(man);
        proxy.category(man);
        float womanBmi = proxy.bodyMassIndex(woman);
        proxy.idealBodyWeight(woman);
        proxy.basalMetabolicRate(woman);

        assertEquals(2, proxy.numTotalPacientes());
        assertEquals(1.75f, proxy.alturaMedia(), 0.001f);
        assertEquals(80f, proxy.pesoMedio(), 0.001f);
        assertEquals((manBmi + womanBmi) / 2f, proxy.imcMedio(), 0.001f);
        assertEquals(1, proxy.numSexoH());
        assertEquals(1, proxy.numSexoM());
    }

    @Test
    void countsDistinctPersonObjectsEvenWhenTheirValuesMatch() throws InvalidHealthDataException {
        ProxyHealthCalc proxy = new ProxyHealthCalc(calculator, calculator, calculator);
        proxy.idealBodyWeight(new Patient(70f, 1.75f, Gender.MALE, 30));
        proxy.basalMetabolicRate(new Patient(70f, 1.75f, Gender.MALE, 30));

        assertEquals(2, proxy.numTotalPacientes());
        assertEquals(2, proxy.numSexoH());
    }

    @Test
    void doesNotRecordFailedCalculations() {
        ProxyHealthCalc proxy = new ProxyHealthCalc(calculator, calculator, calculator);

        assertThrows(InvalidHealthDataException.class,
                () -> proxy.bodyMassIndex(new Patient(0f, 1.75f, Gender.MALE, 30)));
        assertEquals(0, proxy.numTotalPacientes());
    }
}
