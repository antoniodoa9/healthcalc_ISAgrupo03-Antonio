package healthcalc;

import java.util.IdentityHashMap;
import java.util.Map;
import healthcalc.exceptions.InvalidHealthDataException;

public class ProxyHealthCalc implements BodyMassIndex, IdealBodyWeight, BasalMetabolicRate, HealthStats {
    private BodyMassIndex bmiCalc;
    private IdealBodyWeight ibwCalc;
    private BasalMetabolicRate bmrCalc;

    private final Map<Person, PatientStats> patients;

    public ProxyHealthCalc(BodyMassIndex bmiCalc, IdealBodyWeight ibwCalc, BasalMetabolicRate bmrCalc) {
        this.bmiCalc = bmiCalc;
        this.ibwCalc = ibwCalc;
        this.bmrCalc = bmrCalc;
        this.patients = new IdentityHashMap<>();
    }

    @Override
    public BMICategory category(Person person) throws InvalidHealthDataException {
        BMICategory category = bmiCalc.category(person);
        recordPatient(person, false, 0f);
        return category;
    }

    @Override
    public float bodyMassIndex(Person person) throws InvalidHealthDataException {
        float result = bmiCalc.bodyMassIndex(person);
        recordPatient(person, true, result);
        return result;
    }

    @Override
    public float idealBodyWeight(Person person) throws InvalidHealthDataException {
        float result = ibwCalc.idealBodyWeight(person);
        recordPatient(person, false, 0f);
        return result;
    }

    @Override
    public float basalMetabolicRate(Person person) throws InvalidHealthDataException {
        float result = bmrCalc.basalMetabolicRate(person);
        recordPatient(person, false, 0f);
        return result;
    }

    private synchronized void recordPatient(Person person, boolean hasBmi, float bmi) {
        PatientStats stats = patients.get(person);
        if (stats == null) {
            stats = new PatientStats(person.weight(), person.height(), person.gender());
            patients.put(person, stats);
        }
        if (hasBmi && !stats.hasBmi) {
            stats.bmi = bmi;
            stats.hasBmi = true;
        }
    }

    @Override
    public synchronized float alturaMedia() {
        if (patients.isEmpty()) return 0f;
        float sum = 0;
        for (PatientStats stats : patients.values()) sum += stats.height;
        return sum / patients.size();
    }

    @Override
    public synchronized float pesoMedio() {
        if (patients.isEmpty()) return 0f;
        float sum = 0;
        for (PatientStats stats : patients.values()) sum += stats.weight;
        return sum / patients.size();
    }

    @Override
    public synchronized float imcMedio() {
        float sum = 0;
        int count = 0;
        for (PatientStats stats : patients.values()) {
            if (stats.hasBmi) {
                sum += stats.bmi;
                count++;
            }
        }
        if (count == 0) return 0f;
        return sum / count;
    }

    @Override
    public synchronized int numSexoH() {
        int count = 0;
        for (PatientStats stats : patients.values()) {
            if (stats.gender == Gender.MALE) count++;
        }
        return count;
    }

    @Override
    public synchronized int numSexoM() {
        int count = 0;
        for (PatientStats stats : patients.values()) {
            if (stats.gender == Gender.FEMALE) count++;
        }
        return count;
    }

    @Override
    public synchronized int numTotalPacientes() {
        return patients.size();
    }

    private static final class PatientStats {
        private final float weight;
        private final float height;
        private final Gender gender;
        private float bmi;
        private boolean hasBmi;

        private PatientStats(float weight, float height, Gender gender) {
            this.weight = weight;
            this.height = height;
            this.gender = gender;
        }
    }
}
