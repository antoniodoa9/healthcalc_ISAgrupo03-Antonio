Feature: Cálculo del IMC (Índice de Masa Corporal)
  Como profesional de la salud
  Quiero calcular el IMC de un paciente
  Para valorar su estado nutricional relacionando su peso y altura 

  Background:
  Given la calculadora de salud está iniciada para el IMC

  @HighPriority
  Scenario Outline: Cálculo correcto del IMC con valores válidos 
    Given los datos del paciente son peso <peso> kg y altura <altura> m
    When ejecuto el cálculo del IMC
    Then el resultado del IMC debe ser <resultado_esperado> con una tolerancia de 0.01

    Examples:
      | peso | altura | resultado_esperado |
      | 75.0 | 1.75   | 24.48              |
      | 60.0 | 1.60   | 23.43              |
      | 90.0 | 1.80   | 27.77              |
      | 50.0 | 1.55   | 20.81              |

  Scenario Outline: Intento de cálculo del IMC con datos inválidos
    Given los datos del paciente son peso <peso> kg y altura <altura> m
    When ejecuto el cálculo del IMC
    Then el sistema debe lanzar una excepción indicando que los datos de salud son inválidos para el IMC

    @ErrorHandling
    Examples: Ceros, género invalido y variables negativas
      | peso  | altura |
      | 0.0   | 1.75   |
      | 75.0  | 0.0    |
      | -75.0 | 1.75   |
      | 75.0  | -1.75  |

    @EdgeCase
    Examples: Variables fuera de unos límites razonables
      | peso   | altura |
      | 0.99   | 1.75   |
      | 700.1  | 1.75   |
      | 1000.0 | 1.75   |
      | 75.0   | 0.29   |
      | 75.0   | 3.01   |
      | 75.0   | 5.00   |