package com.mycompany.mavenproject1;

/**
 * Clase que define el subconjunto de Java soportado por el compilador educativo.
 * Centraliza todas las reglas del lenguaje permitido.
 *
 * Proyecto: Lenguajes y Autómatas II
 */
public class SubconjuntoJava {

    // =========================
    // TIPOS DE DATOS SOPORTADOS
    // =========================

    public static final String[] TIPOS_SOPORTADOS = { "int" };

    public static boolean esTipoSoportado(String tipo) {
        for (String t : TIPOS_SOPORTADOS) {
            if (t.equals(tipo)) return true;
        }
        return false;
    }

    // =========================
    // OPERADORES SOPORTADOS
    // =========================

    public static final String[] OPERADORES_ARITMETICOS = { "+", "-", "*", "/" };

    public static boolean esOperadorAritmetico(String op) {
        for (String o : OPERADORES_ARITMETICOS) {
            if (o.equals(op)) return true;
        }
        return false;
    }

    // =========================
    // PALABRAS PROHIBIDAS
    // =========================

    public static final String[] PALABRAS_PROHIBIDAS = {
        "double", "float", "string", "boolean", "char",
        "if", "else", "while", "for", "switch", "case",
        "class", "public", "private", "protected", "static",
        "scanner", "system", "out", "println", "print",
        "new", "extends", "implements", "interface",
        "try", "catch", "finally", "throw",
        "import", "package"
    };

    public static boolean esPalabraProhibida(String palabra) {
        palabra = palabra.toLowerCase();
        for (String p : PALABRAS_PROHIBIDAS) {
            if (p.equals(palabra)) return true;
        }
        return false;
    }

    // =========================
    // VALIDACIONES DE SINTAXIS
    // =========================

    public static boolean esDeclaracionVariable(String linea) {
        linea = linea.trim();
        if (!linea.startsWith("int ")) return false;
        if (!linea.endsWith(";")) return false;

        String resto = linea.substring(4, linea.length() - 1).trim();

        if (resto.contains("=")) {
            String[] partes = resto.split("=", 2);
            if (partes.length != 2) return false;

            return esNombreVariableValido(partes[0].trim())
                    && !partes[1].trim().isEmpty();
        }

        return esNombreVariableValido(resto);
    }

    public static boolean esAsignacion(String linea) {
        linea = linea.trim();

        if (linea.startsWith("int ")) return false;
        if (!linea.endsWith(";")) return false;
        if (!linea.contains("=")) return false;

        String[] partes = linea.substring(0, linea.length() - 1).split("=", 2);
        if (partes.length != 2) return false;

        return esNombreVariableValido(partes[0].trim())
                && !partes[1].trim().isEmpty();
    }

    public static boolean esNombreVariableValido(String nombre) {
        if (nombre.isEmpty()) return false;
        //if (!Character.isLetter(nombre.charAt(0)) return false;

        for (char c : nombre.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') return false;
        }
        return true;
    }

    public static boolean contieneElementosProhibidos(String linea) {
        String[] palabras = linea.split("\\s+");
        for (String palabra : palabras) {
            palabra = palabra.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (esPalabraProhibida(palabra)) return true;
        }
        return false;
    }

    // =========================
    // MENSAJES DE ERROR
    // =========================

    public static final String ERROR_TIPO_NO_SOPORTADO =
            "Tipo de dato no soportado. Solo se permite int.";

    public static final String ERROR_PALABRA_PROHIBIDA =
            "Palabra reservada no soportada.";

    public static final String ERROR_SINTAXIS_DECLARACION =
            "Sintaxis incorrecta en la declaración.";

    public static final String ERROR_SINTAXIS_ASIGNACION =
            "Sintaxis incorrecta en la asignación.";
}
