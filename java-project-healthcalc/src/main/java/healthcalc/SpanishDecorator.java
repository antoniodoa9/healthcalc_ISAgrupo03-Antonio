package healthcalc;

import healthcalc.exceptions.InvalidHealthDataException;

public class SpanishDecorator extends BaseDecoratorIdioma {

    public SpanishDecorator(HealthHospital h) {
        super(h);
    }

    @Override
    public Tuple<Float, String> indiceMasaCorporal(float altura, int peso) throws InvalidHealthDataException {
        Tuple<Float, String> res = super.indiceMasaCorporal(altura, peso);
        System.out.println(String.format("La persona con altura %.2f metros y %.2f Kg tiene un IMC de %.2f.", altura, (peso / 1000f), res.x));
        return res;
    }

    @Override
    public int pesoCorporalIdeal(char genero, float altura) throws InvalidHealthDataException {
        int res = super.pesoCorporalIdeal(genero, altura);
        System.out.println(String.format("La persona con altura %.2f metros y género %c tiene un PCI de %.2f Kg.", altura, genero, (res / 1000f)));
        return res;
    }
}