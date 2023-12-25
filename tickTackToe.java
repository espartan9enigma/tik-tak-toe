import javax.swing.*; // PAQUETES DE INTERFAZ GRAFICA
import java.awt.*;    // PAQUETE PARA LA DEFINICION DE COLORES 
import java.awt.event.*; // PAQUETE DE EVENTOS DE MOUSE

public class pruebas extends JFrame {
    // Declaramos el tablero como una matriz 3x3 para el juego
    private char[][] tablero = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };
    
    // El jugadorActual representa a 'X' o 'O'
    private char jugadorActual = 'X';
    
    // Componentes gráficos para la interfaz
    private JPanel panel;
    private JLabel mensajeLabel;

    public pruebas() {
        // Configuración de la ventana principal
        setTitle("Tres en Raya");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        getContentPane().setBackground(new Color(0x1d2021));
        setLayout(new BorderLayout());

        // Configuración de la etiqueta de mensaje en la parte superior
        mensajeLabel = new JLabel("Turno de Jugador " + jugadorActual);
        mensajeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mensajeLabel.setForeground(new Color(0x47a04e));
        add(mensajeLabel, BorderLayout.NORTH);

        // Configuración del panel para el tablero de juego
        panel = new JPanel(new GridLayout(3, 3));
        panel.setBackground(new Color(0x1d2021));
        add(panel, BorderLayout.CENTER);

        // Ciclo para crear las etiquetas de las casillas en el tablero
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                JLabel etiqueta = new JLabel("");
                etiqueta.setPreferredSize(new Dimension(80, 80));
                etiqueta.setHorizontalAlignment(JLabel.CENTER);
                etiqueta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                etiqueta.setFont(new Font("Arial", Font.PLAIN, 40));
                etiqueta.setForeground(new Color(0x20b690));

                // Agregamos un MouseListener a cada etiqueta para manejar los clics
                etiqueta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Obtenemos la fila y columna de la etiqueta clickeada
                        int filaClick = panel.getComponentZOrder(etiqueta) / 3;
                        int columnaClick = panel.getComponentZOrder(etiqueta) % 3;

                        // Verificamos si la casilla está vacía
                        if (tablero[filaClick][columnaClick] == ' ') {
                            // Actualizamos el tablero y la etiqueta con el jugador actual
                            tablero[filaClick][columnaClick] = jugadorActual;
                            etiqueta.setText(String.valueOf(jugadorActual));
                            etiqueta.setForeground(jugadorActual == 'X' ? new Color(0xd86467) : new Color(0x20b690));

                            // Verificamos si hay un ganador
                            if (hayGanador(tablero, jugadorActual)) {
                                JOptionPane.showMessageDialog(null,"¡Jugador " + jugadorActual + " ha ganado!");
                                mensajeLabel.setForeground(new Color(0x47a04e));
                                reiniciarJuego();
                            } else {
                                // Cambiamos el turno al siguiente jugador
                                jugadorActual = (jugadorActual == 'X') ? 'O' : 'X';
                                mensajeLabel.setText("Turno de Jugador " + jugadorActual);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Movimiento inválido, intenta nuevamente.");
                        }
                    }
                });

                // Agregamos la etiqueta al panel
                panel.add(etiqueta);
            }
        }
    }

    // Método para verificar si hay un ganador en el tablero
    public static boolean hayGanador(char[][] tablero, char jugador) {
        // Verificamos filas y columnas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == jugador && tablero[i][1] == jugador && tablero[i][2] == jugador) {
                return true;
            }
            if (tablero[0][i] == jugador && tablero[1][i] == jugador && tablero[2][i] == jugador) {
                return true;
            }
        }

        // Verificamos diagonales
        if ((tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == jugador) ||
            (tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == jugador)) {
            return true;
        }

        return false;
    }

    // Método para reiniciar el juego
    private void reiniciarJuego() {
        // Reiniciamos el tablero y las etiquetas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = ' ';
                JLabel etiqueta = (JLabel) panel.getComponent(i * 3 + j);
                etiqueta.setText("");
                etiqueta.setForeground(new Color(0x20b690));
            }
        }
        // Reiniciamos el jugador actual y el mensaje
        jugadorActual = 'X';
        mensajeLabel.setText("Turno de Jugador " + jugadorActual);
    }

    // Método principal para iniciar el juego
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            pruebas juego = new pruebas();
            juego.setVisible(true);
        });
    }
}
