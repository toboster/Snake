import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * @author Tony Nguyen 11/30/17
 * Wraps map so it can be accesed between different classes.
 */
public class StatusMap {

    Map<Point,GameObject> Points = new HashMap<>();
    private int width,height;
    private Random r;

    public StatusMap(int Width, int Height){
        this.width = Width; 
        this.height = Height;
        r = new Random();
    }

    public void put(Point p, GameObject obj){
        Points.put(p,obj);
    }

    public boolean containsKey(Point p){
        return Points.containsKey(p);
    }

    public boolean containsFood(Food obj){
        return Points.containsValue(obj);
    }

    public void remove(Point p){
        Points.remove(p);
    }

    public GameObject get(Point p){
        return Points.get(p); 
    }

    /**
     * Finds available point map within the bounderies of
     * assigned width and height.
     */
    public Point findPoint(){
        int randX = r.nextInt(width);
        int randY = r.nextInt(height);
        Point temp = new Point(randX,randY);

        while(true){
            if(!Points.containsKey(temp)){
                return temp;
            }
            else
            {
                randX = r.nextInt(width);
                randY = r.nextInt(height);
                temp.setLocation(randX,randY);
            }
        }
    }

    /**
     * @param g
     * Loops over all values in the Map and calls render, 
     * which repaints all objects.
     */
    public void render(Graphics g) {
        for(Map.Entry<Point,GameObject> entry : Points.entrySet()) {
            GameObject temp;
            temp = entry.getValue();
            temp.render(g);

        }

    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }



}
