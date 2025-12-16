package com.mycompany.mavenproject1;

import java.util.HashMap;
import java.util.Map;

/**
 * Motor de ejecución semántica para el subconjunto de Java.
 * Simula la ejecución del programa compilado paso a paso.
 *
 * @author PinkyLab - Lenguajes y Autómatas II
 * @version 1.0
 */
public class ExecutionEngine {

    // ==========================================
    // TABLA DE VARIABLES EN TIEMPO DE EJECUCIÓN
    // ==========================================

    /** Tabla de variables: nombre -> valor */
    private final Map<String, Integer> variables;

    /** Tabla de temporales: nombre -> valor */
    private final Map<String, Integer> temporals;

    /** Código intermedio a ejecutar */
    private String[] intermediateCode;

    /** Resultados de la ejecución para mostrar en consola */
    private final StringBuilder executionLog;

    /**
     * Constructor del motor de ejecución
     */
    public ExecutionEngine() {
        this.variables = new HashMap<>();
        this.temporals = new HashMap<>();
        this.executionLog = new StringBuilder();
    }

    /**
     * Ejecuta el código intermedio línea por línea
     * @param intermediateCode Código de tres direcciones generado
     * @return Resultado de la ejecución
     */
    public String execute(String intermediateCode) {
        this.intermediateCode = intermediateCode.split("\n");
        this.variables.clear();
        this.temporals.clear();
        this.executionLog.setLength(0);

        executionLog.append("=== INICIO DE EJECUCIÓN ===\n");

        for (int i = 0; i < this.intermediateCode.length; i++) {
            String line = this.intermediateCode[i].trim();
            if (!line.isEmpty()) {
                executeLine(line, i + 1);
            }
        }

        executionLog.append("=== FIN DE EJECUCIÓN ===\n");
        executionLog.append("Valores finales de variables:\n");

        for (Map.Entry<String, Integer> entry : variables.entrySet()) {
            executionLog.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }

        return executionLog.toString();
    }

    /**
     * Ejecuta una línea individual del código intermedio
     * @param line Línea a ejecutar
     * @param lineNumber Número de línea para logging
     */
    private void executeLine(String line, int lineNumber) {
        executionLog.append("Línea ").append(lineNumber).append(": ").append(line).append("\n");

        // Declaración de variable: int x = expresión
        if (line.startsWith("int ")) {
            executeVariableDeclaration(line);
        }
        // Asignación: x = expresión
        else if (line.contains(" = ") && !line.contains("int ")) {
            executeAssignment(line);
        }
        // Código de tres direcciones: temp = op1 operador op2
        else if (line.contains(" = ") && (line.contains(" + ") || line.contains(" - ") ||
                                         line.contains(" * ") || line.contains(" / "))) {
            executeThreeAddressCode(line);
        }
        // Ignorar etiquetas y otros elementos
        else if (line.contains(":") || line.startsWith("//")) {
            // No ejecutar, solo logging
        }
        else {
            executionLog.append("  [Línea ignorada - no soportada en ejecución]\n");
        }
    }

    /**
     * Ejecuta una declaración de variable: int nombre = expresión;
     */
    private void executeVariableDeclaration(String line) {
        try {
            // Remover "int " y ";"
            String declaration = line.substring(4).replace(";", "");

            if (declaration.contains(" = ")) {
                String[] parts = declaration.split(" = ", 2);
                String varName = parts[0].trim();
                String expression = parts[1].trim();

                int value = evaluateExpression(expression);
                variables.put(varName, value);

                executionLog.append("  Declarada variable ").append(varName)
                           .append(" = ").append(value).append("\n");
            } else {
                // Declaración sin inicialización
                String varName = declaration.trim();
                variables.put(varName, 0); // Valor por defecto
                executionLog.append("  Declarada variable ").append(varName)
                           .append(" = 0 (valor por defecto)\n");
            }
        } catch (Exception e) {
            executionLog.append("  ERROR en declaración: ").append(e.getMessage()).append("\n");
        }
    }

    /**
     * Ejecuta una asignación: nombre = expresión;
     */
    private void executeAssignment(String line) {
        try {
            // Remover ";"
            String assignment = line.replace(";", "");

            String[] parts = assignment.split(" = ", 2);
            String varName = parts[0].trim();
            String expression = parts[1].trim();

            // Verificar que la variable esté declarada
            if (!variables.containsKey(varName)) {
                executionLog.append("  ERROR: Variable '").append(varName)
                           .append("' no declarada\n");
                return;
            }

            int value = evaluateExpression(expression);
            variables.put(varName, value);

            executionLog.append("  Asignado ").append(varName)
                       .append(" = ").append(value).append("\n");
        } catch (Exception e) {
            executionLog.append("  ERROR en asignación: ").append(e.getMessage()).append("\n");
        }
    }

    /**
     * Ejecuta código de tres direcciones: temp = op1 operador op2
     */
    private void executeThreeAddressCode(String line) {
        try {
            String[] parts = line.split(" = ");
            if (parts.length != 2) return;

            String target = parts[0].trim();
            String expression = parts[1].trim();

            int value = evaluateExpression(expression);

            // Si es una variable temporal (E0, E1, etc.), guardarla
            if (target.startsWith("E")) {
                temporals.put(target, value);
                executionLog.append("  Calculado ").append(target)
                           .append(" = ").append(value).append("\n");
            } else {
                // Es una asignación a variable
                variables.put(target, value);
                executionLog.append("  Asignado ").append(target)
                           .append(" = ").append(value).append("\n");
            }
        } catch (Exception e) {
            executionLog.append("  ERROR en código de tres direcciones: ")
                       .append(e.getMessage()).append("\n");
        }
    }

    /**
     * Evalúa una expresión aritmética
     * Soporta: números, variables, y operaciones +, -, *, /
     */
    private int evaluateExpression(String expression) {
        // Para simplificar, usamos un evaluador básico de expresiones
        // En una implementación completa, se usaría un parser de expresiones

        expression = expression.trim();

        // Si es un número
        try {
            return Integer.parseInt(expression);
        } catch (NumberFormatException e) {
            // No es un número, continuar
        }

        // Si es una variable
        if (variables.containsKey(expression)) {
            return variables.get(expression);
        }

        // Si es una expresión aritmética simple: op1 operador op2
        String[] operators = {" + ", " - ", " * ", " / "};
        for (String op : operators) {
            if (expression.contains(op)) {
                String[] parts = expression.split("\\s*\\" + op.trim() + "\\s*");
                if (parts.length == 2) {
                    int op1 = evaluateOperand(parts[0]);
                    int op2 = evaluateOperand(parts[1]);

                    switch (op.trim()) {
                        case "+" -> { return op1 + op2; }
                        case "-" -> { return op1 - op2; }
                        case "*" -> { return op1 * op2; }
                        case "/" -> { return op1 / op2; } // División entera
                    }
                }
            }
        }

        throw new RuntimeException("Expresión no soportada: " + expression);
    }

    /**
     * Evalúa un operando (número, variable o temporal)
     */
    private int evaluateOperand(String operand) {
        operand = operand.trim();

        // Si es un número
        try {
            return Integer.parseInt(operand);
        } catch (NumberFormatException e) {
            // No es un número
        }

        // Si es una variable
        if (variables.containsKey(operand)) {
            return variables.get(operand);
        }

        // Si es una variable temporal (E0, E1, etc.)
        if (temporals.containsKey(operand)) {
            return temporals.get(operand);
        }

        throw new RuntimeException("Operando desconocido: " + operand);
    }

    /**
     * Obtiene el estado actual de las variables
     */
    public Map<String, Integer> getVariables() {
        return new HashMap<>(variables);
    }

    /**
     * Obtiene el log de ejecución
     */
    public String getExecutionLog() {
        return executionLog.toString();
    }
}