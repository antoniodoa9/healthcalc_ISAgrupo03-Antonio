Feature: Cálculo del PCI (Peso Corporal Ideal) con fórmula de Lorentz
  Como profesional de la salud
  Quiero calcular el peso corporal ideal (PCI o IBW) de un paciente usando la fórmula de Lorentz
  Para establecer un objetivo de peso saludable basado en su género y altura

  Background:
    Given la calculadora de salud está iniciada

  @HighPriority
  Scenario Outline: Cálculo correcto del PCI con valores válidos 
    Given los datos del paciente son altura <altura> cm y género '<genero>'
    When ejecuto el cálculo del PCI
    Then el resultado del PCI debe ser <resultado_esperado> con una tolerancia de 0.01

    Examples:
      | altura | genero | resultado_esperado |
      | 170.0  | M      | 65.0               |
      | 180.0  | M      | 72.5               |
      | 150.0  | M      | 50.0               |
      | 160.0  | W      | 55.0               |
      | 170.0  | W      | 60.0               |
      | 150.0  | W      | 50.0               |

  Scenario Outline: Intento de cálculo del PCI con datos inválidos
    Given los datos del paciente son altura <altura> cm y género '<genero>'
    When ejecuto el cálculo del PCI
    Then el sistema debe lanzar una excepción indicando que los datos de salud son inválidos

    @ErrorHandling
    Examples: Ceros, género invalido y variables negativas
      | altura | genero |
      | 0.0    | M      |
      | -170.0 | W      |
      | 170.0  | X      |
      | 170.0  | m      |

    @EdgeCase
    Examples: Variables fuera de unos límites razonables
      | altura | genero |
      | 29.9   | M      |
      | 15.0   | M      |
      | 300.1  | W      |
      | 350.0  | W      |
      | 500.0  | W      |