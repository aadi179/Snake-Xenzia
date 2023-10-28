 import javax.swing.JFrame;


public class gameframe extends JFrame {
   
    gamepanel panel = new gamepanel();
    gameframe(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake");
        add(panel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String []args){
        new gameframe();
    }
}

    
