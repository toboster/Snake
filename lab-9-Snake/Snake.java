import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * @author Tony Nguyen 11/30/17
 *
 * Represents a Snake, contains methods which move the snake
 * by using given Map.
 *
 */

@SuppressWarnings("serial")
public class Snake extends GameObject {

    //zero should present all the time
    private int velX, velY;
    private int maxLength;
    StatusMap points;

    public Snake(int x, int y, StatusMap Points){
        super(x,y);
        this.velX = 1;
        this.velY = 0;
        this.points = Points;
        this.maxLength = 10;
    }

    public Snake(Point p, StatusMap Points){
        super(p);
        this.velX = 1;
        this.velY = 0;
        this.points = Points;
        this.maxLength = 30;
    }

    @Override
    public char printSymbol(){
        return 's';
    }

    /**
     * Moves Snake and checks for collision
     */
    public void tick(){
        Point temp = this.getLocation();
        Point newPoint = new Point(this.x + velX,this.y + velY);

        if(isCollision(newPoint)){
            //There is a collision.
            collision(points.get(newPoint));
        }
        //Makes sure to remove mapping of current point
        //and nextPoint, then maps newPoint.
        points.remove(temp);
        this.setLocation(newPoint);
        if(points.containsKey(newPoint)){
            points.remove(newPoint);
        }
        points.put(newPoint,this);
    }

    private void collision(GameObject obj){
        char c = obj.printSymbol();
        String result = "";

        if(c == 's' || c == 'X'){
            pause();
            result = "Game is over, " + c + " collision.";   
        } 
        else if(c == 'f'){
            this.maxLength += 2;
            result = "Snake ate food."; 
            // System.out.println("maxLength = " + maxLength);
        }
        else{
            result = "Error no collision.";
        }

        //System.out.println(result);
    }

    /**
     * Is there collision for given point.
     */
    private boolean isCollision(Point p){

        return points.containsKey(p); 
    }

    /**
     * Returns object of collision. 
     */
    private GameObject getCollision(Point p){
        Point newPoint = new Point(this.x + velX,this.y + velY);
        GameObject obj = points.get(newPoint); 
        this.tick();
        return obj;
    }
    /**
     * Draws Snake object, scaled for window.
     */
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(this.x * 16, this.y * 16, 16 , 16);
    }

    /**
     * Pauses snake.
     */
    private void pause(){
        this.velX = 0;
        this.velY = 0; 
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getMax(){
        return maxLength;
    }
}


