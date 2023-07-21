import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AhorcadoGUI extends JFrame {
    private String[] palabras = {"programacion", "java", "ordenador", "inteligencia", "algoritmo", "desarrollo"};
    private String palabraSeleccionada;
    private char[] palabraAdivinar;
    private boolean[] letrasAdivinadas;
    private int intentosRestantes = 7;

    private JLabel palabraLabel;
    private JLabel intentosLabel;
    private JTextField letraTextField;
    private JButton adivinarButton;

    public AhorcadoGUI() {
        super("Juego del Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        Random rand = new Random();
        palabraSeleccionada = palabras[rand.nextInt(palabras.length)];
        palabraAdivinar = new char[palabraSeleccionada.length()];
        letrasAdivinadas = new boolean[26];

        // Inicializar el arreglo palabraAdivinar con guiones bajos
        for (int i = 0; i < palabraAdivinar.length; i++) {
            palabraAdivinar[i] = '_';
        }

        palabraLabel = new JLabel("Palabra a adivinar: " + new String(palabraAdivinar));
        intentosLabel = new JLabel("Intentos restantes: " + intentosRestantes);
        letraTextField = new JTextField(1);
        adivinarButton = new JButton("Adivinar");

        adivinarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String letraStr = letraTextField.getText();
                if (!letraStr.isEmpty()) {
                    char letra = letraStr.toLowerCase().charAt(0);

                    if (!Character.isLetter(letra)) {
                        JOptionPane.showMessageDialog(AhorcadoGUI.this, "Ingresa una letra válida.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (letrasAdivinadas[letra - 'a']) {
                        JOptionPane.showMessageDialog(AhorcadoGUI.this, "Ya has adivinado esa letra.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    letrasAdivinadas[letra - 'a'] = true;
                    boolean acierto = false;

                    for (int i = 0; i < palabraSeleccionada.length(); i++) {
                        if (palabraSeleccionada.charAt(i) == letra) {
                            palabraAdivinar[i] = letra;
                            acierto = true;
                        }
                    }

                    if (!acierto) {
                        intentosRestantes--;
                        intentosLabel.setText("Intentos restantes: " + intentosRestantes);
                    }

                    palabraLabel.setText("Palabra a adivinar: " + new String(palabraAdivinar));

                    if (new String(palabraAdivinar).equals(palabraSeleccionada)) {
                        JOptionPane.showMessageDialog(AhorcadoGUI.this, "¡Felicidades! Has adivinado la palabra: " + palabraSeleccionada, "Ganaste", JOptionPane.INFORMATION_MESSAGE);
                        reiniciarJuego();
                    } else if (intentosRestantes == 0) {
                        JOptionPane.showMessageDialog(AhorcadoGUI.this, "¡Lo siento! Has agotado tus intentos. La palabra era: " + palabraSeleccionada, "Perdiste", JOptionPane.ERROR_MESSAGE);
                        reiniciarJuego();
                    }

                    letraTextField.setText("");
                }
            }
        });

        add(palabraLabel);
        add(intentosLabel);
        add(letraTextField);
        add(adivinarButton);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setResizable(false); // Evitar que la ventana sea redimensionable
    }

    private void reiniciarJuego() {
        Random rand = new Random();
        palabraSeleccionada = palabras[rand.nextInt(palabras.length)];
        palabraAdivinar = new char[palabraSeleccionada.length()];
        letrasAdivinadas = new boolean[26];
        intentosRestantes = 7;

        for (int i = 0; i < palabraAdivinar.length; i++) {
            palabraAdivinar[i] = '_';
        }

        palabraLabel.setText("Palabra a adivinar: " + new String(palabraAdivinar));
        intentosLabel.setText("Intentos restantes: " + intentosRestantes);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AhorcadoGUI().setVisible(true);
            }
        });
    }
}
