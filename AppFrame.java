import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AppFrame extends JFrame{

    private JButton [][] board = new JButton[3][3];
    private int [][] LogicBoard = new int[3][3];
    private JTextField statusBar;
    private GameBoard gameBoard;
    private GameListener listener = new GameListener();
    private String currentPlayer = "x";
    private JMenuItem newGame;
    private JMenuItem quit;
    private int movesLimit = 0;


    AppFrame() {
        setLayout(new BorderLayout());
        gameBoard = new GameBoard();
        add(gameBoard);

        statusBarInit();
        menuBarInit();

        setTitle("Tic Tac Toe!");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400,400,300,300);
        setResizable(false);
    }

    private void statusBarInit() {
        statusBar = new JTextField("Player`s 1 Turn");
        statusBar.setEditable(false);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void menuBarInit() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opcje");
        newGame = new JMenuItem("Nowa gra");
        quit = new JMenuItem("Zakończ grę");
        menu.add(newGame);
        menu.add(quit);
        menuBar.add(menu);
        add(menuBar,BorderLayout.NORTH);
    }

    class GameBoard extends JPanel{
        GameBoard() {
            setLayout(new GridLayout(3,3));
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = new JButton();
                    board[i][j].putClientProperty("i", i);
                    board[i][j].putClientProperty("j", j);
                    board[i][j].addActionListener(listener);
                    board[i][j].setFont(new Font("Arial", Font.PLAIN, 50));
                    add(board[i][j]);
                }
            }
        }
    }

    public class GameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            b.setText(currentPlayer);
            b.setEnabled(false);
            movesLimit++;

            setLogicBoard(b,currentPlayer);

            if (hasWinner()) {
                showWinMessage();
            }
            else if (movesLimit >= 9) {
                showTieMessage();
            }

            togglePlayer();
            menuBarActionHandler();
        }
    }

    private void menuBarActionHandler() {
        newGame.addActionListener(event -> resetBoard());
        quit.addActionListener(event -> System.exit(0));
    }

    private void showWinMessage() {
        if (currentPlayer.equals("o")){
            JOptionPane.showMessageDialog(gameBoard, "Gracz " +2+" wygrał !!!");
            resetBoard();
        }
        else{
            JOptionPane.showMessageDialog(gameBoard, "Gracz " +1+" wygrał !!!");
            resetBoard();
        }
    }

    private void showTieMessage () {
        JOptionPane.showMessageDialog(gameBoard, " Remis, sprubuj ponownie");
        resetBoard();
    }

    private void setLogicBoard(JButton b, String currentPlayer) {
        int firstIndex = (int) b.getClientProperty( "i" );
        int SecondIndex = (int) b.getClientProperty( "j" );
        if (currentPlayer.equals("x")){
            LogicBoard[firstIndex][SecondIndex] = 1;
        }
        else {
            LogicBoard[firstIndex][SecondIndex] = 2;
        }
    }

    private void togglePlayer() {
        if (currentPlayer.equals("x")) {
            currentPlayer = "o";
            statusBar = new JTextField("Player`s 2 Turn");
            add(statusBar, BorderLayout.SOUTH);
        }
        else {
            currentPlayer = "x";
            statusBar = new JTextField("Player`s 1 Turn");
            add(statusBar, BorderLayout.SOUTH);
        }
    }

    private boolean hasWinner() {
        if (LogicBoard[0][0] ==  LogicBoard[0][1] && LogicBoard[0][0] == LogicBoard[0][2] && LogicBoard[0][0] != 0
                || LogicBoard[1][0] ==  LogicBoard[1][1] && LogicBoard[1][0] == LogicBoard[1][2] && LogicBoard[1][0] != 0
                || LogicBoard[2][0] ==  LogicBoard[2][1] && LogicBoard[2][0] == LogicBoard[2][2] && LogicBoard[2][0] != 0 ){
            return true;
        }else if (LogicBoard[0][0] ==  LogicBoard[1][0] && LogicBoard[0][0] == LogicBoard[0][2] && LogicBoard[0][0] != 0
                    || LogicBoard[0][1] ==  LogicBoard[1][1] && LogicBoard[0][1] == LogicBoard[2][1] && LogicBoard[0][1] != 0
                    || LogicBoard[0][2] ==  LogicBoard[1][2] && LogicBoard[0][2] == LogicBoard[2][2] && LogicBoard[0][2] != 0) {
            return true;
        } else
            return LogicBoard[0][0] == LogicBoard[1][1] && LogicBoard[0][0] == LogicBoard[2][2] && LogicBoard[0][0] != 0
                    || LogicBoard[0][2] == LogicBoard[1][1] && LogicBoard[0][2] == LogicBoard[2][0] && LogicBoard[0][2] != 0;
    }

    private void resetBoard () {
        movesLimit = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setText("");
                LogicBoard[i][j] = 0;
                board[i][j].setEnabled(true);
            }
        }
    }
}

