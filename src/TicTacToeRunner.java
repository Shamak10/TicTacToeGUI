import javax.swing.*;

public class TicTacToeRunner {

    private static javax.swing.SwingUtilities SwingUtilities;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeFrame();
            }
        });
    }
}