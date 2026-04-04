Feature: Clasificación del IMC
  Como profesional de la salud
  Quiero obtener la clasificación del IMC de un paciente
  Para determinar su categoría nutricional

Background:
  Given la calculadora de salud está iniciada para el IMC

@HighPriority
Scenario Outline: Clasificación correcta del estado nutricional basado en el IMC
  Given el valor calculado de IMC es <imc_calculado>
  When ejecuto la clasificación del IMC
  Then la categoría devuelta debe ser "<categoría_esperada>"

  Examples:
    | imc_calculado | categoria_esperada |
    | 15.0          | Severe Thinness    |
    | 16.5          | Moderate Thinness  |
    | 17.5          | Mild Thinness      |
    | 22.0          | Normal             |
    | 27.5          | Overweight         |
    | 32.0          | Obese Class I      |
    | 37.0          | Obese Class II     |
    | 42.0          | Obese Class III    |

Scenario Outline: Intento de clasificación con valores inválidos de IMC
  Given el valor calculado de IMC es <imc_calculado>
  When ejecuto la clasificación del IMC
  Then el sistema debe lanzar una excepción indicando que los datos de salud son inválidos

  @ErrorHandling
  Examples: Valores negativos o excesivamente altos
    | imc_calculado |
    | -5.0          |
    | -0.1          |
    | 150.1         |
    | 200.0         |