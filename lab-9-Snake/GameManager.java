import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Tony Nguyen 11/30/17
 * Manages Snake game logic, defining width and height of game,
 * this includes adding walls from input file, adding snake head,
 * and food. Handles collision through classes: Snake, Food, Wall.
 * Adds aditional Snakes if needed. 
 *
 */
public class GameManager {

    // Shared across Snake objects and Food objects, changes
    // will affect mentioned objects
    protected StatusMap Points;
    private int WIDTH, HEIGHT;
    private Food food;
    private Snake head;
    private LinkedList<Snake> allSnake;
    private int prevVelX;
    private int prevVelY;
    private LinkedList<Integer> prevTickVelY;
    private LinkedList<Integer> prevTickVelX;
    private int score;

    public GameManager() {
        //this.Points = new StatusMap(WIDTH,HEIGHT);
        this.allSnake = new LinkedList<>();
        this.prevTickVelY = new LinkedList<>();
        this.prevTickVelX = new LinkedList<>();
        this.score = 0;

    }

    @Override
    /**
     * String Representation of game Snake.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        Point temp = new Point();

        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                temp.setLocation(j,i);
                if(!Points.containsKey(temp)){
                    sb.append('.'); 
                }
                else if(temp.equals(head)) {
                    sb.append('S');
                }
                else
                {
                    sb.append(Points.get(temp).printSymbol());
                }
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Sets up default game with only border walls
     * if no input file is specified.
     */
    public void defaultGame() {
        this.WIDTH = 30;
        this.HEIGHT = 30;
        this.Points = new StatusMap(WIDTH,HEIGHT);

        addWalls(new Point(0,0), new Point (30,28)); 
        
    }

    /**
     * Parses file of parameter name to set up walls, width, and height.
     * @param fileName input file must follow format explained
     * in SnakeBoard.
     */
    public void parseFile(String fileName) throws IOException {

        try(BufferedReader in = new BufferedReader(
                    new FileReader(fileName));) {
            String line;
            String[] nums;
            //Sets width and height.
            //if((line = in.readLine()) != null){
                line = in.readLine();
                nums = line.split(" ");
                this.WIDTH = Integer.parseInt(nums[0]);
                this.HEIGHT = Integer.parseInt(nums[1]);
                //note: needed this for testing, might change.
                this.Points = new StatusMap(WIDTH,HEIGHT);

             //}
            //Adds walls until no more lines.
            while((line = in.readLine()) != null){
                nums = line.split(" ");
                //Grabs upper (Left | right) points to create walls.
                Point uppLeft = new Point(Integer.parseInt(nums[0]),
                        Integer.parseInt(nums[1]));    
                Point lowRight = new Point(Integer.parseInt(nums[2]),
                        Integer.parseInt(nums[3]));
                addWalls(uppLeft,lowRight);
            }

                    }
    }

    /**
     * Moves snakes, add more snake objects if needed, if 
     * food was eaten spawns another food and increments score.
     */
    public void tick(){
        int size = allSnake.size();
        Point temp = new Point();

        //Remembers all current velocitys.
        storeVel(); 
        //Snake objects need to be added if 
        if(size != head.getMax() && size == 1){
            temp = allSnake.get(0).getLocation();
            Snake newSnake = new Snake(temp,Points);

            tickSnake();
            Points.put(temp,newSnake);
            allSnake.add(newSnake);
        }
        //Need more Snake objects.
        else if(allSnake.size() != head.getMax()){
            //Keep last Snake obj from moving.
            Snake obj = allSnake.removeLast();
            //New obj Point will be the point of last Snake in collection.
            temp = allSnake.peekLast().getLocation();
            Snake newSnake = new Snake(temp,Points);
            //Move all Snake obj in current collection.
            tickSnake();
            //Maps, updates collection, add removed obj.
            Points.put(temp,newSnake);
            allSnake.add(newSnake);
            allSnake.add(obj);
        }
        else{
            tickSnake();
        }

        if(head.getVelX() == 0 && head.getVelY() == 0){
            stop();
        }
        //Updates subSnake velocitys for next tick.
        else{
            updateSnakeVel();
        }

        //If food does not exist, it was eaten, respawn food.
        if(!(Points.get(food.getLocation()).printSymbol() == 'f') ){
            food.respawnFood();
            this.score += 10;
        }

    }    

    /**
     * Used to stop the game if there is a collision.
     */
    public void stop(){
        head.setVelX(0);
        head.setVelY(0);
        
        for(int i = 1; i < allSnake.size(); i++){
            Snake obj = allSnake.get(i);
            obj.setVelX(0);
            obj.setVelY(0);
        }
        
    }

    /**
     * Stores all of the current snake velocitys in two LinkedLists.
     */
    private void storeVel(){
        prevTickVelY.clear();
        prevTickVelX.clear();

        for(int i = 0; i < allSnake.size(); i++){
           Snake obj = allSnake.get(i);

           prevTickVelX.add(obj.getVelX());
           prevTickVelY.add(obj.getVelY()); 
        }
    }

    /**
     * Updates sub snakes velocitys to their respective successors
     * velocity.
     */
    private void updateSnakeVel(){
            for(int i = 1; i < allSnake.size(); i++){
            Snake obj = allSnake.get(i);

            obj.setVelY(prevTickVelY.get(i-1));
            obj.setVelX(prevTickVelX.get(i-1));

        }     
    }

    /**
     * Loops over collection of subSnakes and calls tick for all
     * snake objects.
     */
    private void tickSnake(){
        for(int i = 0; i < allSnake.size(); i++){
            Snake obj = allSnake.get(i);
            obj.tick();
        }
    }

    /**
     * Spawns food object in a random available point.
     */
    public void spawnFood(){
        //note: might need to handle special cases for corners of brookes
        //input file.
        Point newPoint = Points.findPoint();
        Food newFood = new Food(newPoint, Points);


        Points.put(newPoint, new Food(newPoint, Points));
        this.food = newFood; 
    }

    /**
     * Uses addWall methods to build walls given upper left and
     * right Points.
     */ 
    public void addWalls(Point uppLeft, Point lowRight){
        Point uppRight = new Point(lowRight.x,uppLeft.y);
        Point lowLeft = new Point(uppLeft.x,lowRight.y);        

        if(uppLeft.x != lowRight.x && uppLeft.y != lowRight.y){
            addXwall(uppLeft,uppRight);
            addXwall(lowLeft,lowRight);
            addYwall(uppLeft,lowLeft);
            addYwall(uppRight,lowRight); 
        } 
        else if(uppLeft.y == lowRight.y){
            addXwall(uppLeft,lowRight);
        }
        else if(uppLeft.x == lowRight.x){
            addYwall(uppLeft,lowRight);
        }
        else{
            System.out.println("Error in addWalls method");
        }

    }

    //Point 'a' and 'b' assumed to have same y.
    /**
     * Used by addWall method to make wall object from point
     * a to b in the x direction. 
     */
    private void addXwall(Point a, Point b){
        //Finds where to start and end.
        int startX = (a.x < b.x) ? a.x : b.x;
        int endX = (a.x < b.x) ? b.x : a.x;

        for(int i = startX; i <= endX; i++){
            Point temp = new Point(i,a.y); 

            if(!Points.containsKey(temp)){
                Points.put(temp,new Wall(temp));
            }

        }

    }

    //Point 'a' and 'b' assumed to have same x.
    //Similar to addXwall, but adds walls in the y direction.
    /**
     * Used by addWall method to make wall object from point
     * a to b in the y direction.
     */
    private void addYwall(Point a, Point b){
        int startY = (a.y < b.y) ? a.y : b.y;
        int endY = (a.y < b.y) ? b.y : a.y;

        for(int i = startY; i <= endY; i++){
            Point temp = new Point(a.x,i); 

            if(!Points.containsKey(temp)){
                Points.put(temp,new Wall(temp));
            }
        } 

    }

    //Adds head at random position.    
    //Not sure if key contained in value is bad practice.
    /**
     * Spawns a Snake head in a random available spot.
     */
    public void addHead(){
        Point newPoint = Points.findPoint();
        this.head = new Snake(newPoint, Points);

        allSnake.add(head);
        Points.put(newPoint,head); 
        this.prevVelX = head.getVelX();
        this.prevVelY = head.getVelY();
    }

    /**
     * Changes head direction.
     */
    public void up(){
           
        head.setVelY(-1);
        head.setVelX(0);
        // System.out.println("Direction change to up.");
        this.prevVelY = -1;
        this.prevVelX = 0;
    }

    /**
     * Changes head direction.
     */
    public void down(){
        head.setVelY(1);
        head.setVelX(0);
        // System.out.println("Direction change to down.");
        this.prevVelY = 1;
        this.prevVelX = 0;
    }

    /**
     * Changes head direction.
     */
    public void left(){
       head.setVelY(0);
       head.setVelX(-1); 
       // System.out.println("Direction change to left.");
        this.prevVelY = 0;
        this.prevVelX = -1;
    }

    /**
     * Changes head direction.
     */
    public void right(){
        head.setVelY(0);
        head.setVelX(1);
        // System.out.println("Direction change to right.");
        this.prevVelY = 0;
        this.prevVelX = 1;
    }

    /**
     * Draws all mapped Objects, look at StatusMap render()
     */
    public void render(Graphics g) {
        Points.render(g); 
    }

    public int getScore() {
        return this.score;
    }

    
    // public static void main(String[] args){
       

    //}    
    
}

