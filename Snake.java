import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;

public class gamepanel extends JPanel implements ActionListener{
    static final int Screen_Width = 1300;
    static final int Screen_Height = 750;
    static final int Unit_Size = 50;
    static final int Game_Units = (Screen_Height*Screen_Width)/(Unit_Size);
    static final int Delay = 100;
    final int X[] = new int[Game_Units];// Include al the coordinates of snake including head of snake X coordinates set to gameunits cuz snake will not be bigger than game unit
    final int Y[] = new int[Game_Units];// Same for Y coordinates
    int bodyParts = 6;
    int appleEaten;
    int appleX;// coordinates of apple in X
    int appleY;
    char directions = 'R';// snake will satrt in going right
    boolean Running = false;
    Timer timer ;
    Random random;// instance of random class 

    gamepanel(){
        random = new Random();
        setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());

        startGame();
    }







    
    public void startGame(){

        newapple();// to create new apple 
        Running = true;// to start snake initially it is set to false
        timer = new Timer(Delay, this);
        timer.start();

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);//Call the parent class's paintComponent method
        draw(g);

    }
    public void draw(Graphics g){
        
        if(Running) {
			
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, Unit_Size, Unit_Size);
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(X[i], Y[i], Unit_Size, Unit_Size);// draw snake head
				}
				else {
					g.setColor(new Color(45,180,0));
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(X[i], Y[i], Unit_Size, Unit_Size);// draw snake body
				}			
			}
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
            //  is a method to draw text on the game panel using the Graphics object g.
			g.drawString("Score: "+appleEaten, (Screen_Width - metrics.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());

            //getFontMetrics(g.getFont()) is a method that is used to obtain information about the current font's metrics. 
            // It provides details such as the height of characters in the font, the width of characters, and other font-related metrics.
            // g.getFont().getSize() is used to determine the height of the font and position the text vertically.


		}
		else {
			gameOver(g);
		}
		
	}
       
           

    
    

    public void newapple(){
        appleX = random.nextInt((int)(Screen_Width/Unit_Size))*Unit_Size;
        appleY = random.nextInt((int)(Screen_Height/Unit_Size))*Unit_Size;

    }


    

    public void move(){
// Inside the loop, two lines of code update the X and Y coordinates of each body part:
// X[i] = X[i-1];: This line copies the X coordinate of the body part at position i-1 to the X coordinate of the current body part at position i. 
// This effectively shifts the X coordinate of the current body part to match the X coordinate of the body part in front of it.
// Y[i] = Y[i-1];: Similarly, this line copies the Y coordinate of the body part at position i-1 to the Y coordinate of the current body part at position i.
//  It shifts the Y coordinate of the current body part to match the Y coordinate of the body part in front of it.


        for(int i = bodyParts; i>0; i--){
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        switch(directions){
            case 'U':
            Y[0] = Y[0] -Unit_Size;// Move the head of the snake up
            break;
            case 'D':
            Y[0] = Y[0] +Unit_Size;// Move the head of the snake down
            break;
             case 'R':
            X[0] = X[0] +Unit_Size;// Move the head of the snake Right
            break;
             case 'L':
            X[0] = X[0] -Unit_Size;// Move the head of the snake Left
            break;
            
        }

    }

    public void checkapple(){

        if(X[0]==appleX && Y[0]==appleY){
            bodyParts++;
            appleEaten++;
            newapple();
        }

    }

    public void checkCollision(){
        // checks if head collide with body
        for(int i = bodyParts; i>0;i--){
            if(X[0]==X[i] && Y[0]==Y[i]){
                Running = false;
            }
        }
        // check if head collides with left border
        if(X[0]<0){
            Running = false;
        }
        // if head touches right border
        if(X[0]>Screen_Width){
            Running = false;
        }
        // if head touches top border
        if(Y[0]<0){
            Running = false;
        }
        // if head touches bottom 
        if(Y[0]>Screen_Height){
            Running = false;
        }
        if(!Running){
            timer.stop();
        }

    }

    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());// use to align text at center
		g.drawString("Score: "+appleEaten, (Screen_Width - metrics1.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());//


		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (Screen_Width - metrics2.stringWidth("Game Over"))/2, Screen_Height/2);
            
        

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                if(directions!='R'){
                    directions = 'L';
                }
                break;

            
                case KeyEvent.VK_RIGHT:
                if(directions!='L'){
                    directions = 'R';
                }
                break;

                case KeyEvent.VK_UP:
                if(directions!='D'){
                    directions = 'U';
                }
                break;

                case KeyEvent.VK_DOWN:
                if(directions!='U'){
                    directions = 'D';
                }
                break;
            }

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
       if(Running){
        move();
        checkapple();
        checkCollision();
       }
       
       repaint();
    }
    
}
