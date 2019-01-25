Tony Nguyen
Snake Game
12/8/17

Game Play:
 - Use arrow keys to change direction of snake.
 - Eat food, food increments score by 10.

Description of program internals
 - Game logic is contained in GameManager.java
   Classes:
   GameManager:
     - Created a map that mapped Points to a GameObject, which is questionable since my GameObject already extends
       Point. 
     - Used map to check if there was collisions, if mapping exists then collision will occur.
     - Contains method to update Snakes location and add object, with each method call of tick(). 
     - Snake moved by adding int velocitys to current position when tick() is called.
     - Detected end of game by checking head Snake velocity, whenever there is a collision with wall or snake the snake
       stops moving.
     - Moving the snake I used two linked list to keep track of previous x and y velocitys, after the tick() call
       previous velocitys would be assigned to current veloitys, this would make it so a snake object could follow
       its succesors path.
     - Creates walls, food, snake, objects and maps them to a map.
     - Sets up game given input file with the format, 
       width height
       upperLeftPoint wall lowerRightPoint wall
       optional additional walls on new lines.
    GameObject:
     - Used in GameManager to represent location.
     - Extends Point
     - Contains abstract classes for printing symbol representation of class and a method called render() to repaint
       onto window.
    Snake:
     - Represents a snake.
     - Extends GameObject
     - Moves itself given a map from the GameManager.
     - Detects collision by checking what symbol representation was returned when checking in the map.
     - Removes food mapping if collision occurs.
     - Sets velocity x and y to zero if there is a snake or wall collision.
    Food:
     - Represents food.
     - Extends GameObject
     - Can respawn itself if eaten.
    Wall:
     - Represents Wall.
     - Extends GameObject
    SnakeBoard:
     - Creates the GUI for the Snake game by creating a GameManager object to hand logic.
     - Uses a swing timer, increments a variable when timer event occurs.
     - Performs actions after a certain number of ticks, this can be modified to slow or speed up the game.
     - After certain amount of ticks, game is updated using GameManagers tick() and calls repaint.
     - For the input controls I created a KeyListener which listens to input using arrows keys,
        after a key is pressed I update a direction variable to its respective direction that was pressed.
        the velocity is changed based on the swing timer before using tick()
     - Game is initially paused and must be started by pressing the "START" button, the game can paused 
       and continued as desired. Pauses game by keeping track of variable, whenever button is pressed the variable
       is toggled. This is checked for according to swing timer, game will only tick if it's not paused.

    Known Bugs:
     - Sometimes a food or snake head can spawn outside the bounderies of specified width and height.
     - When the game is over the snake falls apart (which I thought was pretty cool, not sure how it does it)

    notes:
     - Tested on a linux computer.
     - May need to rethink design.
     - Snake is choppy.
     - Might need to completely start over for a better Snake game.

    
