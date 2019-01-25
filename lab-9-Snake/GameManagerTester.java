import java.io.IOException;

public class GameManagerTester{

    /**
     * Sets up game with basics, a Food object and head
     * Snake. 
     */
    public static void setup(GameManager game){
        game.addHead();
        game.spawnFood();
    } 

    /**
     * Sets up walls, height, width from input file.
     */
    public static void input(GameManager game, String in){
        try{
            game.parseFile(in);

        }catch(IOException e){
            e.printStackTrace();
        } 
    }

    /**
     * Moves Snake according to Velocity and prints
     * state of the game, number (num) of times.
     */
    public static void tickPrint(GameManager game, int num){

        for(int i = 0; i < num; i++){
            game.tick();
            System.out.println(game);
        }
    }



    public static void main(String[] args){
        GameManager game = new GameManager();
        System.out.println("GameManager object 1 testing:");
        input(game,"maze-simple.txt");
        setup(game);

        tickPrint(game,2);
        game.down();
        tickPrint(game,3);
        game.right();
        tickPrint(game,2); 

        GameManager game2 = new GameManager();
        System.out.println("GameManager object 2 testing:");
        input(game2,"maze-cross.txt");
        setup(game2);
        tickPrint(game2,1);
        game2.right();
        tickPrint(game2,3);
        game2.down();
        tickPrint(game2,1);
        game2.right();
        tickPrint(game2,2); 

        System.out.println("resume testing GameManager 1:");
        game.up();
        tickPrint(game,1);
        game.left();
        tickPrint(game,1);
        game.down();
        tickPrint(game,1);


    }



}
