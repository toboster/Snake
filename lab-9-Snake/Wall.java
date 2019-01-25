import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * @author Tony Nguyen 11/30/17
 * Represents a wall.
 */
@SuppressWarnings("serial")
public class Wall extends GameObject {

    public Wall(int x, int y){
        super(x,y);
    }

    public Wall(Point p){
        super(p);
    }

    @Override
    public char printSymbol(){
        return 'X';
    }

    /**
     * @param g
     * paints wall based on scale of window.
     */
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(this.x * 16, this.y * 16, 16 , 16);
    }
}
