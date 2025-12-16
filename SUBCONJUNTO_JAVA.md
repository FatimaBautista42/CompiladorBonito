# Subconjunto de Java Soportado por el Compilador Educativo
## Lenguajes y Autómatas II - Mini Compilador PinkyLab

### Fecha: Diciembre 2025
### Versión: 1.0

---

## 1. INTRODUCCIÓN

Este documento define formalmente el **subconjunto de Java** soportado por el mini compilador educativo "Compilador Bonito (PinkyLab)". El objetivo es proporcionar un lenguaje de programación simple y coherente para fines pedagógicos, enfocándose en conceptos fundamentales de compilación sin implementar Java completo.

El subconjunto está diseñado para ser:
- **Simple**: Sintaxis mínima y clara
- **Coherente**: Reglas consistentes
- **Educativo**: Facilita el aprendizaje de análisis léxico, sintáctico y semántico
- **Ejecutable**: Permite simulación de ejecución paso a paso

---

## 2. ELEMENTOS SOPORTADOS

### 2.1 Tipos de Datos
- `int`: Enteros (ej: `int a = 5;`)

### 2.2 Declaraciones de Variables
```java
int a;           // Declaración sin inicialización
int b = 10;      // Declaración con inicialización
int c = a + b;   // Declaración con expresión
```

### 2.3 Asignaciones
```java
a = 5;           // Asignación simple
b = a + 3;       // Asignación con expresión
c = b * 2;       // Asignación con multiplicación
```

### 2.4 Expresiones Aritméticas
- Operadores: `+`, `-`, `*`, `/`
- Operandos: números enteros y variables declaradas
- Paréntesis para agrupación: `(a + b) * c`

### 2.5 Estructura del Programa
- Secuencia de declaraciones y asignaciones
- Una declaración/asignación por línea
- Punto y coma al final de cada línea

---

## 3. ELEMENTOS NO SOPORTADOS

### 3.1 Tipos de Datos
- `double`, `float`, `String`, `boolean`, `char`
- Arrays
- Objetos

### 3.2 Estructuras de Control
- `if`, `else`, `while`, `for`
- `switch`, `case`
- Funciones/métodos

### 3.3 Entrada/Salida
- `Scanner`
- `System.out.println()`
- `System.in`

### 3.4 Programación Orientada a Objetos
- `class`, `public`, `private`
- `extends`, `implements`
- Instanciación de objetos

### 3.5 Otros
- Comentarios (`//`, `/* */`)
- Imports y paquetes
- Librerías externas
- Manejo de excepciones

---

## 4. EJEMPLOS DE CÓDIGO VÁLIDO

### 4.1 Programa Simple
```java
int a;
int b = 5;
a = 10;
int c = a + b;
b = c * 2;
```

### 4.2 Expresiones Complejas
```java
int x = 3;
int y = 4;
int z = (x + y) * 2;
x = z / 3;
```

---

## 5. EJEMPLOS DE CÓDIGO INVÁLIDO

### 5.1 Uso de Tipos No Soportados
```java
double pi = 3.14;        // ERROR: tipo double no soportado
String nombre = "Juan";   // ERROR: tipo String no soportado
```

### 5.2 Estructuras de Control
```java
if (a > 5) {              // ERROR: if no soportado
    b = 10;
}
while (x < 10) {          // ERROR: while no soportado
    x = x + 1;
}
```

### 5.3 Entrada/Salida
```java
Scanner sc = new Scanner(System.in);  // ERROR: Scanner no soportado
System.out.println("Hola");            // ERROR: System.out no soportado
```

### 5.4 Variables No Declaradas
```java
x = 5;                    // ERROR: x no declarada
int y = x + 3;           // ERROR: x no declarada
```

---

## 6. SEMÁNTICA DEL SUBCONJUNTO

### 6.1 Reglas de Alcance
- Todas las variables son globales
- Las variables deben declararse antes de usarse
- No hay bloques de alcance (como `{ }`)

### 6.2 Evaluación de Expresiones
- Las expresiones se evalúan de izquierda a derecha
- División entera: `5 / 2 = 2`
- Precedencia de operadores: `*`, `/` antes que `+`, `-`

### 6.3 Ejecución
- Ejecución secuencial línea por línea
- Las asignaciones actualizan el valor de las variables
- Los valores finales se muestran en la consola simulada

---

## 7. EXTENSIONES FUTURAS (OPCIONALES)

Para versiones futuras del compilador, se podrían agregar:
- Tipos adicionales (`double`, `boolean`)
- Operadores de comparación (`<`, `>`, `==`)
- Estructuras de control básicas (`if`, `while`)
- Arrays unidimensionales

---

## 8. CONCLUSIONES

Este subconjunto proporciona una base sólida para el aprendizaje de:
- Análisis léxico (tokens)
- Análisis sintáctico simple
- Generación de código intermedio
- Ejecución simulada
- Tabla de símbolos

El enfoque educativo permite a los estudiantes comprender los fundamentos de la compilación sin abrumarlos con la complejidad de Java completo.</content>
<filePath>c:\Users\fatim\Documents\Compilador Automatas\CompiladorBonito\SUBCONJUNTO_JAVA.md