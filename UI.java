import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UI {
    private JFrame frame;
    private final String FONT_STYLE = "Arial";
    private State state = new State(); 
    private int playerMoving = 0;
    private int player;
    private int ai;
    
    public UI() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("Tic Tac Toe");
        
        frame.setPreferredSize(new Dimension(600,600));
        
        this.renderStart();
        
        frame.pack();
        frame.setVisible(true);
    }
    
    // @TODO: AI Algorithm
    private Coordinates getBestMove() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(this.state.getBoardState()[i][j] == -1) {
                    return new Coordinates(i, j);
                }
            }
        }
        
        return new Coordinates(0, 0);
    }
    
    private void renderStart() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.revalidate();
        frame.repaint();
        
        JLabel chooseLabel = new JLabel("Play as:");
        chooseLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        chooseLabel.setFont(new Font(this.FONT_STYLE, Font.BOLD, 30));
        
        JButton player1Pick = new JButton("Player 1");
        player1Pick.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        player1Pick.setMargin(new Insets(5, 200, 5, 200));
        player1Pick.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        
        player1Pick.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                player = 0;
                ai = 1;
                
                renderGame();
            }
        });
        
        JButton player2Pick = new JButton("Player 2");
        player2Pick.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        player2Pick.setMargin(new Insets(5, 200, 5, 200));
        player2Pick.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        
        player2Pick.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                player = 1;
                ai = 0;
                
                // AI first turn
                state.playerMove(1, 1, ai);
                
                renderGame();
            }
        });
        
        frame.add(Box.createVerticalGlue());        
        frame.add(chooseLabel);        
        frame.add(Box.createRigidArea(new Dimension(0,40)));        
        frame.add(player1Pick);        
        frame.add(Box.createRigidArea(new Dimension(0,10)));        
        frame.add(player2Pick);
        frame.add(Box.createVerticalGlue());
        
        frame.pack();
    }
    
    private void renderGame() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.revalidate();
        frame.repaint();
        
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Info"));
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 3));
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                String message = "";
                
                if(this.state.getBoardState()[i][j] == 0) {
                    message = "O";
                }
                else if(this.state.getBoardState()[i][j] == 1) {
                    message = "X";
                }
                
                JButton button = new JButton(message);
                
                final int x = i, y = j;
                
                button.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(state.getBoardState()[x][y] == -1) {
                            state.playerMove(x, y, player);
                            
                            int gameState = checkWin(state);
                            
                            if(gameState == player) {
                                renderWin();
                                return;
                            }
                            else if(gameState == 3) {
                                renderDraw();
                                return;
                            }
                            else if(gameState == (player == 0? 1: 0)) {
                                renderLose();
                                return;
                            }
                            
                            // AI
                            Coordinates aiMove = getBestMove();
                            
                            state.playerMove(aiMove.getX(), aiMove.getY(), ai);
                            
                            System.out.println(state.getPossibleMoves(ai));
                            
                            gameState = checkWin(state);
                            
                            if(gameState == player) {
                                renderWin();
                                return;
                            }
                            else if(gameState == 3) {
                                renderDraw();
                                return;
                            }
                            else if(gameState == (player == 0? 1: 0)) {
                                renderLose();
                                return;
                            }
                            
                            renderGame();
                        }
                    }
                });
                
                centerPanel.add(button);
            }
        }
        
        JPanel southPanel = new JPanel();
        
        southPanel.add(new JLabel("Info"));
        
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
        
        frame.pack();
    }
    
    private int checkWin(State state) {
        // check left
        for(int i = 0; i < 3; i++) {
            int current = state.getBoardState()[i][0];
            
            if(current == -1) continue;
            
            boolean flag = true;
            
            for(int j = 1; j < 3; j++) {
                if(current != state.getBoardState()[i][j]) {
                    flag = false;
                    break;
                }
            }
            
            if(flag) {
                return current;
            }
        }
        
        // check down
        for(int i = 0; i < 3; i++) {
            int current = state.getBoardState()[0][i];
            
            if(current == -1) continue;
            
            boolean flag = true;
            
            for(int j = 1; j < 3; j++) {
                if(current != state.getBoardState()[j][i]) {
                    flag = false;
                    break;
                }
            }
            
            if(flag) {
                return current;
            }
        }
        
        // check diagonal
        int current = state.getBoardState()[0][0];
            
        if(current != -1) {
            boolean flag = true;
        
            for(int j = 1; j < 3; j++) {
                if(current != state.getBoardState()[j][j]) {
                    flag = false;
                    break;
                }
            }
            
            if(flag) {
                return current;
            }
        }
        
        current = state.getBoardState()[2][0];
            
        if(current != -1) {
            boolean flag = true;
        
            for(int j = 0; j < 2; j++) {
                if(current != state.getBoardState()[j][2 - j]) {
                    flag = false;
                    break;
                }
            }
            
            if(flag) {
                return current;
            }
        }
        
        // check for draw
        boolean isDraw = true;
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(state.getBoardState()[i][j] == -1) {
                    isDraw = false;
                }
            }
        }
        
        return isDraw? 3: -1;
    }
    
    private void renderWin() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.revalidate();
        frame.repaint();
        
        JLabel winLabel = new JLabel("You win!");
        winLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        winLabel.setFont(new Font(this.FONT_STYLE, Font.BOLD, 30));
        
        JLabel wrongLabel = new JLabel("There's something wrong with your algorithm. ;)");
        wrongLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        wrongLabel.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 15));
        
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playAgainButton.setMargin(new Insets(5, 200, 5, 200));
        playAgainButton.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        
        playAgainButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                state.reset();
                renderStart();
            }
        });
        
        frame.add(Box.createVerticalGlue());        
        frame.add(winLabel);        
        frame.add(Box.createRigidArea(new Dimension(0,5)));        
        frame.add(wrongLabel);        
        frame.add(Box.createRigidArea(new Dimension(0,40)));        
        frame.add(playAgainButton);
        frame.add(Box.createVerticalGlue());
        
        frame.pack();
    }
    
    private void renderDraw() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.revalidate();
        frame.repaint();
        
        JLabel drawLabel = new JLabel("It's a draw!");
        drawLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        drawLabel.setFont(new Font(this.FONT_STYLE, Font.BOLD, 30));
        
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playAgainButton.setMargin(new Insets(5, 200, 5, 200));
        playAgainButton.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        playAgainButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                state.reset();
                renderStart();
            }
        });
                
        frame.add(Box.createVerticalGlue());        
        frame.add(drawLabel);        
        frame.add(Box.createRigidArea(new Dimension(0,40)));        
        frame.add(playAgainButton);
        frame.add(Box.createVerticalGlue());
        
        frame.pack();
    }
    
    private void renderLose() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.revalidate();
        frame.repaint();
        
        JLabel loseLabel = new JLabel("You lose!");
        loseLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loseLabel.setFont(new Font(this.FONT_STYLE, Font.BOLD, 30));
        
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playAgainButton.setMargin(new Insets(5, 200, 5, 200));
        playAgainButton.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        playAgainButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                state.reset();
                renderStart();
            }
        });
        
        frame.add(Box.createVerticalGlue());        
        frame.add(loseLabel);        
        frame.add(Box.createRigidArea(new Dimension(0,40)));        
        frame.add(playAgainButton);
        frame.add(Box.createVerticalGlue());
        
        frame.pack();
    }
}