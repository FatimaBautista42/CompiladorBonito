package com.mycompany.mavenproject1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainPanel extends javax.swing.JFrame {

    String stream;
    TokenTable tokenTable;
    SymbolTable symbolTable;
    IntermediateCodeGenerator intermediateFrame;
    ObjectCodeGenerator objectCodeFrame;
    ExecutionEngine executionEngine;
    
    // Colores PinkyLab
    private static final Color COLOR_FONDO_PRINCIPAL = new Color(255, 255, 255);//Blanco puro
    private static final Color COLOR_BOTON = new Color(255, 182, 210);// Rosa kawaii
    private static final Color COLOR_BOTON_BORDE = new Color(255, 150, 200);// Rosa fuerte
    
    // Simulación de entrada/salida
    private final java.util.Map<String, Integer> simulatedVariables = new java.util.HashMap<>();
    private javax.swing.JTextArea consoleArea;
    private javax.swing.JScrollPane consoleScrollPane;
    
    public MainPanel() {
        initComponents();
        setupPinkyLabStyle();
        tokenTable = new TokenTable();
        symbolTable = new SymbolTable();
    }
    
    private void setupPinkyLabStyle() {
        setTitle("PinkyLab -Compilador bonito ");
        setBackground(COLOR_FONDO_PRINCIPAL);
        setForeground(new Color(100, 100, 100));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel1.setBackground(COLOR_FONDO_PRINCIPAL);
        jPanel1.setLayout(new BorderLayout(10, 10));
        jPanel1.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel encabezado con logo
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(COLOR_FONDO_PRINCIPAL);
        
        try {
            File imageFile = new File("C:\\Users\\fatim\\Documents\\Compilador Automatas\\CompiladorBonito\\PinkyLab.png");
            if (imageFile.exists()) {
                BufferedImage originalImage = ImageIO.read(imageFile);
                Image scaledImage = originalImage.getScaledInstance(350, 120, Image.SCALE_SMOOTH);
                JLabel headerLabel = new JLabel(new ImageIcon(scaledImage));
                headerPanel.add(headerLabel);
            }
        } catch (IOException e) {
            // Si no encuentra la imagen, mostrar texto
            JLabel textHeader = new JLabel("PinkyLab Compilador");
            textHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
            textHeader.setForeground(new Color(220, 100, 180));
            headerPanel.add(textHeader);
        }

        // Panel central con mensaje de bienvenida
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(COLOR_FONDO_PRINCIPAL);
        centerPanel.setLayout(new GridBagLayout());
        
        JLabel welcomeLabel = new JLabel("Bienvenida a tu Compilador PinkyLab");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(220, 100, 180));
        centerPanel.add(welcomeLabel);

        // Panel de botones inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(COLOR_FONDO_PRINCIPAL);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        scanBtn = createPinkyButton("Analizar");
        scanBtn.addActionListener(e -> scanBtnActionPerformed());

        jButton5 = createPinkyButton("Código Intermedio");
        jButton5.addActionListener(e -> jButton5ActionPerformed());

        jButton6 = createPinkyButton("Formato");
        jButton6.addActionListener(e -> jButton6ActionPerformed());

        jButton4 = createPinkyButton("Tabla de Símbolos");
        jButton4.addActionListener(e -> jButton4ActionPerformed());

        jButton1 = createPinkyButton("Ver Tokens");
        jButton1.addActionListener(e -> jButton1ActionPerformed());

        jButton7 = createPinkyButton("Código Objeto");
        jButton7.addActionListener(e -> jButton7ActionPerformed());

        buttonPanel.add(scanBtn);
        buttonPanel.add(jButton5);
        buttonPanel.add(jButton6);
        buttonPanel.add(jButton4);
        buttonPanel.add(jButton1);
        buttonPanel.add(jButton7);

        // Panel para el área de texto
        sourceStream = new javax.swing.JTextArea();
        sourceStream.setColumns(20);
        sourceStream.setFont(new java.awt.Font("Segoe UI", 0, 14));
        sourceStream.setRows(5);
        sourceStream.setBackground(new Color(255, 255, 255));
        sourceStream.setForeground(new Color(80, 80, 80));
        sourceStream.setTabSize(4);
        
        jScrollPane2 = new javax.swing.JScrollPane(sourceStream);
        
        // Crear el panel de números de línea
        LineNumberPanel lineNumberPanel = new LineNumberPanel(sourceStream);
        sourceStream.getDocument().addDocumentListener(lineNumberPanel);
        
        // Agregar el panel de números de línea al JScrollPane
        jScrollPane2.setRowHeaderView(lineNumberPanel);

        // Agregar componentes al panel principal
        jPanel1.add(headerPanel, BorderLayout.NORTH);
        jPanel1.add(jScrollPane2, BorderLayout.CENTER);
        
        // Inicializar consola falsa
        initializeConsole();
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(COLOR_FONDO_PRINCIPAL);
        southPanel.add(centerPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Panel inferior con botones y consola
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(COLOR_FONDO_PRINCIPAL);
        bottomPanel.add(southPanel, BorderLayout.NORTH);
        bottomPanel.add(consoleScrollPane, BorderLayout.CENTER);
        
        jPanel1.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jPanel1, BorderLayout.CENTER);

        // Menú
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Nuevo");
        jMenuItem1.addActionListener(e -> jMenuItem1ActionPerformed());
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(e -> jMenuItem2ActionPerformed());
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Salir");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Acerca de");
        jMenuItem4.addActionListener(e -> jMenuItem4ActionPerformed());
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private JButton createPinkyButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(COLOR_BOTON);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(COLOR_BOTON_BORDE, 2));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // Agregar relleno
        button.setPreferredSize(new Dimension(120, 40));
        
        return button;
    }

    // ==========================================
    // SIMULACIÓN DE ENTRADA/SALIDA
    // ==========================================
    
    /**
     * Procesa las líneas de entrada simulada (sc.nextInt())
     * Busca patrones como: int variable = sc.nextInt();
     */
    private void processInputStatements(String code) {
        simulatedVariables.clear(); // Limpiar variables anteriores
        
        String[] lines = code.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (isInputStatement(line)) {
                String variableName = extractVariableName(line);
                if (variableName != null) {
                    simulateInput(variableName);
                }
            }
        }
    }
    
    /**
     * Verifica si una línea contiene una llamada a sc.nextInt()
     */
    private boolean isInputStatement(String line) {
        // Más flexible: busca sc.nextInt() en cualquier parte de la línea
        return line.contains("sc.nextInt()") && line.contains("int") && line.contains("=");
    }
    
    /**
     * Extrae el nombre de la variable de una línea como: int a = sc.nextInt();
     */
    private String extractVariableName(String line) {
        try {
            // Buscar el patrón más flexible: cualquier cosa entre "int " y " = "
            int intIndex = line.indexOf("int ");
            int equalsIndex = line.indexOf("=");
            
            if (intIndex >= 0 && equalsIndex > intIndex) {
                String variablePart = line.substring(intIndex + 4, equalsIndex).trim();
                // Limpiar espacios y caracteres no deseados
                variablePart = variablePart.replaceAll("\\s+", "");
                return variablePart;
            }
        } catch (Exception e) {
            // Si hay error en el parsing, ignorar
        }
        return null;
    }
    
    /**
     * Simula la entrada pidiendo un valor al usuario
     */
    private void simulateInput(String variableName) {
        try {
            String input = JOptionPane.showInputDialog(
                this,
                "Ingresa un valor entero para la variable '" + variableName + "':",
                "Simulación de Entrada - PinkyLab",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (input != null && !input.trim().isEmpty()) {
                int value = Integer.parseInt(input.trim());
                simulatedVariables.put(variableName, value);
                
                JOptionPane.showMessageDialog(
                    this,
                    "Variable '" + variableName + "' = " + value + " asignada correctamente.",
                    "Entrada Simulada",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error: Debes ingresar un número entero válido.",
                "Error de Entrada",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Inicializa la consola falsa
     */
    private void initializeConsole() {
        consoleArea = new javax.swing.JTextArea();
        consoleArea.setEditable(false);
        consoleArea.setBackground(new Color(20, 20, 20));
        consoleArea.setForeground(Color.GREEN);
        consoleArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        consoleArea.setText("""
PinkyLab Console - Simulación de Salida
=====================================

""");
        
        consoleScrollPane = new javax.swing.JScrollPane(consoleArea);
        consoleScrollPane.setPreferredSize(new Dimension(400, 150));
        consoleScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BOTON_BORDE, 2),
            "Consola PinkyLab",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            COLOR_BOTON_BORDE
        ));
    }
    
    /**
     * Procesa las líneas de salida simulada (System.out.print/println)
     */
    private void processOutputStatements(String code) {
        if (consoleArea == null) {
            initializeConsole();
        }
        
        clearConsole();
        
        String[] lines = code.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (isOutputStatement(line)) {
                String output = extractOutputContent(line);
                if (output != null) {
                    String result = evaluateOutputExpression(output);
                    appendToConsole(result);
                }
            }
        }
    }
    
    /**
     * Verifica si una línea contiene System.out.print o println
     */
    private boolean isOutputStatement(String line) {
        // Más flexible: busca System.out.print o System.out.println
        return (line.contains("System.out.print(") || line.contains("System.out.println(")) && 
               (line.contains("\"") || line.contains("+"));
    }
    
    /**
     * Extrae el contenido dentro de System.out.print/println
     */
    private String extractOutputContent(String line) {
        try {
            // Buscar el contenido entre paréntesis
            int startParen = line.indexOf("(");
            int endParen = line.lastIndexOf(")");
            
            if (startParen >= 0 && endParen > startParen) {
                String content = line.substring(startParen + 1, endParen);
                return content;
            }
        } catch (Exception e) {
            // Si hay error en el parsing, ignorar
        }
        return null;
    }
    
    /**
     * Evalúa expresiones simples dentro del output (solo sumas por ahora)
     */
    private String evaluateOutputExpression(String expression) {
        // Si no hay variables simuladas, devolver la expresión tal cual
        if (simulatedVariables.isEmpty()) {
            return expression;
        }
        
        String result = expression;
        
        // Reemplazar variables individuales primero
        for (String varName : simulatedVariables.keySet()) {
            Integer value = simulatedVariables.get(varName);
            if (value != null) {
                // Reemplazar la variable por su valor
                result = result.replaceAll("\\b" + varName + "\\b", value.toString());
            }
        }
        
        // Evaluar expresiones matemáticas simples (solo sumas por ahora)
        result = evaluateSimpleMath(result);
        
        return result;
    }
    
    /**
     * Evalúa expresiones matemáticas simples (solo sumas)
     */
    private String evaluateSimpleMath(String expression) {
        try {
            // Buscar patrones como: (5 + 3), 5 + 3, etc.
            if (expression.contains("+")) {
                // Remover paréntesis si los hay
                String mathExpr = expression.trim();
                if (mathExpr.startsWith("(") && mathExpr.endsWith(")")) {
                    mathExpr = mathExpr.substring(1, mathExpr.length() - 1);
                }
                
                // Evaluar suma simple
                String[] parts = mathExpr.split("\\+");
                if (parts.length == 2) {
                    int num1 = Integer.parseInt(parts[0].trim());
                    int num2 = Integer.parseInt(parts[1].trim());
                    int sum = num1 + num2;
                    
                    // Reemplazar en la expresión original
                    return expression.replace("(" + mathExpr + ")", String.valueOf(sum))
                                   .replace(mathExpr, String.valueOf(sum));
                }
            }
        } catch (NumberFormatException e) {
            // Si hay error en la evaluación matemática, devolver original
        }
        
        return expression;
    }
    
    /**
     * Agrega texto a la consola falsa
     */
    private void appendToConsole(String text) {
        if (consoleArea != null) {
            consoleArea.append(text + "\n");
        }
    }
    
    /**
     * Limpia la consola falsa
     */
    private void clearConsole() {
        if (consoleArea != null) {
            consoleArea.setText("""
PinkyLab Console - Simulación de Salida
=====================================

""");
        }
    }

    private void jMenuItem1ActionPerformed() {
        this.sourceStream.setText("");
        this.stream = "";
        // Limpiar variables simuladas y consola
        simulatedVariables.clear();
        clearConsole();
    }

    private void jMenuItem2ActionPerformed() {
        // TODO add your handling code here:
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setDirectory("C://");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory() + dialog.getFile();
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String text = "";
            String s;
            while((s = br.readLine()) != null)
                text += s + "\n";
            this.sourceStream.setText(text);
        } catch(IOException e) {}
    }

    private void scanBtnActionPerformed() {
        // TODO add your handling code here:
        this.tokenTable = new TokenTable();
        this.symbolTable = new SymbolTable();
        String text = this.sourceStream.getText();

        if(text.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Please write something before scanning");
            return;
        }
        this.intermediateFrame = new IntermediateCodeGenerator(text);
        this.stream = text;
        
        // NUEVO: Procesar simulación de entrada y salida después del análisis
        processInputStatements(text);
        processOutputStatements(text);
        
        // NUEVO: Ejecutar simulación semántica
        executeSemanticSimulation(text);
    }

    private void jButton5ActionPerformed() {
        // TODO add your handling code here:
        intermediateFrame.setVisible(true);
    }

    private void jButton6ActionPerformed() {
        // TODO add your handling code here:
        sourceStream.setText(IntermediateCodeGenerator.performFormat(sourceStream.getText()));
    }

    private void jButton4ActionPerformed() {
        // TODO add your handling code here:
        this.symbolTable.setVisible(true);
    }

    private void jButton1ActionPerformed() {
        this.tokenTable.setVisible(true);
    }

    private void jMenuItem4ActionPerformed() {
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null,"Developers:\nAmmar\nManaf\nNaveed"); 
    }

    private void jButton7ActionPerformed() {
        // TODO add your handling code here:
        if (this.intermediateFrame == null) {
            JOptionPane.showMessageDialog(this, "Please scan and generate intermediate code first!");
            return;
        }
        String intermediateCode = IntermediateCodeGenerator.ConvertCode(this.stream);
        this.objectCodeFrame = new ObjectCodeGenerator(intermediateCode);
        this.objectCodeFrame.setVisible(true);
    }


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("FaltLaf Light".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainPanel().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton scanBtn;
    private javax.swing.JTextArea sourceStream;
    // End of variables declaration//GEN-END:variables
    
    // Clase interna para mostrar números de línea
    private class LineNumberPanel extends JComponent implements DocumentListener {
        private final javax.swing.JTextArea textArea;
        private int lineCount = 0;
        
        public LineNumberPanel(javax.swing.JTextArea textArea) {
            this.textArea = textArea;
            setBackground(new Color(240, 240, 240));
            setForeground(new Color(150, 150, 150));
            setFont(new Font("Segoe UI", Font.PLAIN, 12));
            setPreferredSize(new Dimension(50, textArea.getHeight()));
            updateLineCount();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g2d.setColor(getForeground());
            g2d.setFont(getFont());
            
            int lineHeight = g2d.getFontMetrics().getHeight();
            int baseline = g2d.getFontMetrics().getAscent();
            
            int y = baseline;
            for (int i = 1; i <= lineCount; i++) {
                g2d.drawString(String.valueOf(i), 10, y);
                y += lineHeight;
            }
        }
        
        private void updateLineCount() {
            int count = textArea.getLineCount();
            if (count != lineCount) {
                lineCount = count;
                setPreferredSize(new Dimension(50, textArea.getHeight()));
                repaint();
            }
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateLineCount();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            updateLineCount();
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
            updateLineCount();
        }
    }
    
    /**
     * Ejecuta la simulación semántica del programa compilado
     */
    private void executeSemanticSimulation(String sourceCode) {
        try {
            // Generar código intermedio
            String intermediateCode = IntermediateCodeGenerator.ConvertCode(sourceCode);
            
            // Crear motor de ejecución
            this.executionEngine = new ExecutionEngine();
            
            // Ejecutar el código
            String executionResult = executionEngine.execute(intermediateCode);
            
            // Mostrar resultados en la consola simulada
            appendToConsole("\n=== RESULTADOS DE EJECUCIÓN ===\n");
            appendToConsole(executionResult);
            
        } catch (Exception e) {
            appendToConsole("ERROR en ejecución semántica: " + e.getMessage() + "\n");
        }
    }
}
