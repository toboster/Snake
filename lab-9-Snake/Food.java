import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * @author Tony Nguyen 11/30/17
 * Represents Food for snake to eat.
 */

@SuppressWarnings("serial")
public class Food extends GameObject {

    StatusMap points;

    public Food(int x, int y, StatusMap Points){
        super(x,y);
        this.points = Points;
    }

    public Food(Point p, StatusMap Points){
        super(p);
        this.points = Points;
    }

    @Override
    public char printSymbol(){
        return 'f';
    }

    /**
     * Respawns itself, in other words reassigns itself within
     * map.
     */
    public void respawnFood(){
        Point newPoint = points.findPoint();

        this.setLocation(newPoint);
        points.put(newPoint, this);
    }

    /**
     * Paints Food based off of scale of window.
     */
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(this.x * 16, this.y * 16, 16, 16);
    }



}
