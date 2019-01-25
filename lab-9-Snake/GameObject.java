import java.awt.Graphics;
import java.awt.Point;

@SuppressWarnings("serial")
public abstract class GameObject extends Point{
    
    public GameObject(int x, int y){
        super(x,y);
    }

    public GameObject(Point p){
        super(p);
    }

    public abstract char printSymbol(); 

    public abstract void render(Graphics g); 

    

}

