import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UI {
    private JFrame frame;
    private final String FONT_STYLE = "Arial"; 
    
    public UI() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("Tic Tac Toe");
        
        frame.setPreferredSize(new Dimension(600,600));
        
        
        this.renderLose();
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private void renderStart() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        JLabel chooseLabel = new JLabel("Play as:");
        chooseLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        chooseLabel.setFont(new Font(this.FONT_STYLE, Font.BOLD, 30));
        
        JButton player1Pick = new JButton("Player 1");
        player1Pick.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        player1Pick.setMargin(new Insets(5, 200, 5, 200));
        player1Pick.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        
        JButton player2Pick = new JButton("Player 2");
        player2Pick.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        player2Pick.setMargin(new Insets(5, 200, 5, 200));
        player2Pick.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        
        frame.add(Box.createVerticalGlue());        
        frame.add(chooseLabel);        
        frame.add(Box.createRigidArea(new Dimension(0,40)));        
        frame.add(player1Pick);        
        frame.add(Box.createRigidArea(new Dimension(0,10)));        
        frame.add(player2Pick);
        frame.add(Box.createVerticalGlue());
        
        frame.pack();
    }
    
    private void renderWin() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
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
        
        JLabel drawLabel = new JLabel("It's a draw!");
        drawLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        drawLabel.setFont(new Font(this.FONT_STYLE, Font.BOLD, 30));
        
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playAgainButton.setMargin(new Insets(5, 200, 5, 200));
        playAgainButton.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        
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
        
        JLabel loseLabel = new JLabel("You lose!");
        loseLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loseLabel.setFont(new Font(this.FONT_STYLE, Font.BOLD, 30));
        
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playAgainButton.setMargin(new Insets(5, 200, 5, 200));
        playAgainButton.setFont(new Font(this.FONT_STYLE, Font.PLAIN, 25));
        
        frame.add(Box.createVerticalGlue());        
        frame.add(loseLabel);        
        frame.add(Box.createRigidArea(new Dimension(0,40)));        
        frame.add(playAgainButton);
        frame.add(Box.createVerticalGlue());
        
        frame.pack();
    }
}