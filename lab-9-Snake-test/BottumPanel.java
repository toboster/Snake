import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author Tony Nguyen 12/8/17
 * NOT USED IN FINAL SNAKE GAME
 *
 */

@SuppressWarnings("serial")
public class BottumPanel extends JPanel {
    
    private JLabel label;
    private JButton click;
    private int score = 0;

    public BottumPanel() {
        click = new JButton("START");
        label = new JLabel("Score: " + this.score);
        this.add(click);
        this.setBackground(Color.ORANGE);
        this.add(label);
        ActionListener clk = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                requestFocus();
                
                // need pause here
                // working
            }

        };
        click.addActionListener(clk);
    } 

    public void setScore(int x) {
        this.score = x;
        label.setText("Score: " + this.score);
    }

    

}
