# Compilador Bonito (PinkyLab) - Lenguajes y Autómatas II

## Descripción

Este proyecto implementa un **compilador educativo** para un subconjunto definido de Java, diseñado específicamente para la materia de Lenguajes y Autómatas II. El compilador incluye análisis léxico, sintáctico simple, generación de código intermedio y **ejecución semántica simulada**.

## Características Principales

### ✅ Subconjunto de Java Formalizado
- **Tipo de dato único**: `int` (enteros)
- **Operadores aritméticos**: `+`, `-`, `*`, `/`
- **Declaraciones**: `int variable;` o `int variable = expresión;`
- **Asignaciones**: `variable = expresión;`
- **Expresiones**: Variables, números y operaciones aritméticas
- **Documentación completa**: Ver `SUBCONJUNTO_JAVA.md`

### ✅ Análisis Léxico Simplificado
- Reconocimiento de tokens del subconjunto únicamente
- Tabla de símbolos con tipos de datos
- Tabla de tokens categorizados

### ✅ Generación de Código Intermedio
- Código de tres direcciones
- Variables temporales (E0, E1, etc.)
- Formato educativo y legible

### ✅ Ejecución Semántica Simulada ⭐ **NUEVO**
- **Motor de ejecución**: `ExecutionEngine.java`
- Simulación paso a paso del programa
- Tabla de variables en tiempo de ejecución
- Resultados mostrados en consola simulada

## Arquitectura del Compilador

```
Código Fuente → Análisis Léxico → Tabla de Símbolos → Código Intermedio → Ejecución Simulada
```

### Componentes Principales

- **`SubconjuntoJava.java`**: Define las reglas del subconjunto soportado
- **`LexemeGenerator.java`**: Análisis léxico simplificado
- **`IntermediateCodeGenerator.java`**: Generación de código de tres direcciones
- **`ExecutionEngine.java`**: Motor de ejecución semántica ⭐
- **`MainPanel.java`**: Interfaz gráfica integrada

## Ejemplo de Uso

### Código Fuente Válido
```java
int a;
int b = 5;
a = 10;
int c = a + b;
b = c * 2;
```

### Salida de Ejecución
```
=== INICIO DE EJECUCIÓN ===
Línea 1: int a;
  Declarada variable a = 0 (valor por defecto)
Línea 2: int b = 5;
  Declarada variable b = 5
Línea 3: a = 10;
  Asignado a = 10
Línea 4: int c = a + b;
  Declarada variable c = 15
Línea 5: b = c * 2;
  Asignado b = 30
=== FIN DE EJECUCIÓN ===
Valores finales de variables:
a = 10
b = 30
c = 15
```

## Instalación y Uso

### Prerrequisitos
- Java Development Kit (JDK) 15 o superior
- Maven (opcional, para gestión de dependencias)

### Ejecución
1. Compilar el proyecto:
   ```bash
   cd Project
   mvn clean compile
   ```

2. Ejecutar la aplicación:
   ```bash
   mvn exec:java -Dexec.mainClass="com.mycompany.mavenproject1.MainPanel"
   ```

3. En la interfaz:
   - Escribir código fuente en el editor
   - Hacer clic en "Scan" para análisis léxico
   - Ver resultados en tablas de tokens y símbolos
   - Ver código intermedio generado
   - **Ver ejecución simulada en la consola** ⭐

## Documentación

- **`SUBCONJUNTO_JAVA.md`**: Definición formal del subconjunto soportado
- **`ejemplo_subconjunto.java`**: Código de ejemplo válido
- **Comentarios en español**: Todo el código está documentado en español

## Restricciones Educativas

- ✅ **Solo simulación**: No ejecuta Java real
- ✅ **Subconjunto controlado**: Lenguaje mínimo y bien definido
- ✅ **Sin reflexión**: Implementación directa y entendible
- ✅ **Sin compiladores externos**: Todo implementado desde cero
- ✅ **Proyecto 100% educativo**: Enfocado en aprendizaje

## Desarrollo Académico

Este proyecto está diseñado para ser **defendible académicamente** en Lenguajes y Autómatas II, demostrando:

- Comprensión de análisis léxico
- Implementación de tabla de símbolos
- Generación de código intermedio
- **Ejecución semántica simulada** ⭐
- Diseño de subconjunto de lenguaje
- Integración de componentes de compilador

## Autor
**PinkyLab** - Lenguajes y Autómatas II

---
*Proyecto educativo - Diciembre 2025*