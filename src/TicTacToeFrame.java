import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeFrame extends JFrame {

    private TicTacToe game;
    private JButton[][] buttons;
    private JButton quitButton;
    private char currentPlayer = 'X'; // Initialize the current player to 'X'
    private int moves = 0;

    public TicTacToeFrame() {
        super("Tic Tac Toe");

        game = new TicTacToe();

        buttons = new JButton[3][3];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Calibri", Font.PLAIN, 50));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();

                        // Prompt the user to enter 'X' or 'O' based on the current player
                        String move = JOptionPane.showInputDialog("Enter '" + currentPlayer + "':");

                        if (move != null && move.equals(String.valueOf(currentPlayer))) {
                            int row = -1, col = -1;

                            // Find the clicked button's position
                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                    if (button == buttons[i][j]) {
                                        row = i;
                                        col = j;
                                        break;
                                    }
                                }
                            }

                            if (row != -1 && col != -1 && game.isValidMove(row, col)) {
                                // Make a move on the game board.
                                game.makeMove(row, col);

                                // Update the GUI to reflect the new game state.
                                button.setText(move);
                                button.setEnabled(false);

                                // Check if the game is over.
                                moves++;
                                if (moves >= 5) {
                                    if (game.checkForWin(row, col)) {
                                        showWinnerDialog();
                                        resetGame();
                                        return;
                                    }
                                    if (moves == 9) {
                                        showTieDialog();
                                        resetGame();
                                        return;
                                    }
                                }

                                // Switch to the next player
                                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                            } else {
                                // Display a message for an invalid move.
                                JOptionPane.showMessageDialog(null, "Invalid move. Try again.", "Illegal Move",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // Display an error message for an invalid input.
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter '" + currentPlayer + "'.",
                                    "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        }

        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                boardPanel.add(buttons[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(quitButton);

        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void showWinnerDialog() {
        char winner = game.getWinner();
        JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
    }

    private void showTieDialog() {
        JOptionPane.showMessageDialog(this, "The game is a tie!");
    }

    private void resetGame() {
        game.resetBoard();
        moves = 0;
        currentPlayer = 'X'; // Reset the current player to 'X'

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeFrame();
            }
        });
    }

    private class TicTacToe {
        private char[][] board;

        public TicTacToe() {
            board = new char[3][3];
        }

        public boolean isValidMove(int row, int col) {
            return board[row][col] == '\0'; // Check if the cell is empty
        }

        public void makeMove(int row, int col) {
            board[row][col] = currentPlayer;
        }

        public boolean checkForWin(int row, int col) {
            // Check rows, columns, and diagonals for a win
            return (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer)
                    || (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer)
                    || (row == col && board[0][0] == currentPlayer && board[1][1] == currentPlayer
                    && board[2][2] == currentPlayer)
                    || (row + col == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer
                    && board[2][0] == currentPlayer);
        }

        public char getWinner() {
            return currentPlayer;
        }

        public void resetBoard() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = '\0';
                }
            }
        }
    }
}
