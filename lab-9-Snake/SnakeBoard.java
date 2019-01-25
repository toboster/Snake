import java.awt.Color;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Tony Nguyen 12/8/17
 * Contains main method for entire Snake program.
 * Manages the GUI part of snake by using the logic 
 * contained in a GameManager object , contains two JPanels,
 * a label, and button for pausing. 
 *
 * Can take 1 input file argument, format of file must have
 * int width and height, and specified walls.
 *
 * If no files are specified a default game will start with
 * walls along the border of the game.
 *
 * Resizable has been turned off.
 *
 */

@SuppressWarnings("serial")
public class SnakeBoard extends JPanel implements ActionListener, KeyListener {

    GameManager game;
    public JFrame frame;
    private Timer timer;  
    private JPanel botPanel;
    private int width;
    private int height;
    private int score;
    //setup parse file.
    private int gameWidth;
    private int gameHeight;
    private int ticks = 0;
    private int test = 0;
    //Used for keyPressed. 
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    private boolean isPause = true;
    private int direction;
    //Bottum Panel componenets
    private JLabel label;
    private JButton click;


    public SnakeBoard() {
        game = new GameManager();
        direction = UP;
        score = 0;
        width = 500;
        height = 500;
        timer = new Timer(20, this);

        // Bottum Panel specifications.
        botPanel = new JPanel();
        click = new JButton("START");
        label = new JLabel("Score: " + this.score);
        botPanel.add(click);
        botPanel.setBackground(Color.ORANGE);
        botPanel.add(label);
        ActionListener clk = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {

                if(isPause) {
                    setFocusable(false);
                    isPause = false;
                    click.setText("PAUSE"); 
                }
                else {
                    setFocusable(true);
                    isPause = true;
                    click.setText("UNPAUSE");
                }
                // working
            }

        };
        click.addActionListener(clk);
        // Frame specifications.
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.setSize(width, height);
        frame.add(this, BorderLayout.CENTER);
        frame.add(botPanel, BorderLayout.PAGE_END);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        timer.start();

    }

    /**
     * @param ev listens for timer.
     * updates game, changes velocity based on direction,
     * pauses game, based on number of ticks from timer.
     * note: there is a bug where after pausing, the game
     * stops.
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        ticks++;
        botPanel.repaint();
        Toolkit.getDefaultToolkit().sync();

        if(ticks % 20 == 0){
            if(direction == DOWN){
                game.down();
            }
            if(direction == UP){
                game.up();
            }
            if(direction == LEFT){
                game.left();
            }
            if(direction == RIGHT){
                game.right();
            }
            // Pauses game.

            // call tick for gameManager only if not paused.
            if(!isPause) { 
                frame.requestFocusInWindow();
                game.tick();
            }
            this.score = game.getScore();
            label.setText("Score: " + this.score);
            repaint();
        }
    }

    /**
     * @param g 
     * Fills board with grey background, calls game render
     * method to draw rest of objects.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0,0,width,height);
        game.render(g);
    }

    /**
     * If no input file specified sets up normal game
     * with only outside walls.
     */
    private void setGameDefault() {
        game.defaultGame();
        game.addHead();
        game.spawnFood();
    }

    /**
     * @param s String of input file.
     * Uses method to set up game given input file,
     * format of the file is width and height of game,
     * then upp left and bot left points.
     */
    private void setGameFile(String s) {
        try{
            game.parseFile(s); 
            game.addHead();
            game.spawnFood();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param e event that keys are pressed.
     * Listens for key arrow key presses and assigns
     * variables to indicate direction.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if(i == KeyEvent.VK_LEFT && direction != RIGHT) {
            direction = LEFT;
        }     
        if(i == KeyEvent.VK_RIGHT && direction != LEFT) {
            direction = RIGHT;
        }
        if(i == KeyEvent.VK_UP && direction != DOWN) {
            direction = UP;
        }
        if(i == KeyEvent.VK_DOWN && direction != UP) {
            direction = DOWN;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        SnakeBoard board = new SnakeBoard();
        // Checks if args exist.
        if(args.length == 0) {
            board.setGameDefault();            
        }
        else if(args.length == 1) {
            board.setGameFile(args[0]);
        }
        else {
            System.out.println("Invalid # arguments.");
            System.exit(0);
        }
    }

}
