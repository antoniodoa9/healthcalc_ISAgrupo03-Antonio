Feature: Cálculo de la TMB con ecuación de Harris-Benedict
    Como profesional de la salud
  Quiero calcular la tasa metabólica basal (TMB) de un paciente usando la ecuación de Harris-Benedict
  Para obtener una estimación de la cantidad mínima de energía que necesita y poder planificar un tratamiento o incluso su dieta

  Background:
    Given la calculadora de salud está iniciada

  @HighPriority
  Scenario Outline: Cálculo correcto de la TMB con valores válidos 
    Given los datos del paciente son peso <peso> kg, altura <altura> cm, edad <edad> años y género '<genero>'
    When ejecuto el cálculo de la TMB
    Then el resultado de la TMB debe ser <resultado_esperado> con una tolerancia de 0.01

    Examples:
      | peso | altura | edad | genero | resultado_esperado |
      | 70.0 | 175.0  | 25   | M      | 1724.05            |
      | 85.0 | 180.0  | 30   | M      | 1920.62            |
      | 60.0 | 160.0  | 40   | M      | 1432.94            |
      | 60.0 | 165.0  | 25   | W      | 1405.33            |
      | 75.0 | 170.0  | 30   | W      | 1537.88            |
      | 55.0 | 155.0  | 40   | W      | 1263.17            |

  Scenario Outline: Intento de cálculo de la TMB con datos inválidos
    Given los datos del paciente son peso <peso> kg, altura <altura> cm, edad <edad> años y género '<genero>'
    When ejecuto el cálculo de la TMB
    Then el sistema debe lanzar una excepción indicando que los datos de salud son inválidos

    @ErrorHandling
    Examples: Ceros, género invalido y variables negativas
      | peso  | altura | edad | genero |
      | 0.0   | 175.0  | 25   | M      |
      | 70.0  | 0.0    | 25   | M      |
      | 70.0  | 175.0  | 25   | X      |
      | -70.0 | 175.0  | 25   | M      |
      | 70.0  | -175.0 | 25   | M      |
      | 70.0  | 175.0  | -25  | M      |

    @EdgeCase
    Examples: Variables fuera de unos límites razonables
      | peso   | altura | edad | genero |
      | 0.99   | 175.0  | 25   | M      |
      | 0.50   | 175.0  | 25   | M      |
      | 700.1  | 175.0  | 25   | M      |
      | 1000.0 | 175.0  | 25   | M      |
      | 5000.0 | 175.0  | 25   | M      |
      | 70.0   | 29.9   | 25   | M      |
      | 70.0   | 15.0   | 25   | M      |
      | 70.0   | 300.1  | 25   | M      |
      | 70.0   | 350.0  | 25   | M      |
      | 70.0   | 500.0  | 25   | M      |
      | 70.0   | 175.0  | 121  | M      |
      | 70.0   | 175.0  | 150  | M      |
      | 70.0   | 175.0  | 200  | M      |