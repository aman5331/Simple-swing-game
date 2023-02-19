import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

    static int width = 1200;
    static int height = 600;
    static int unit = 50;
    Timer timer;
    static int delay = 160;
    Random random;

    //food coordinates
    int fx, fy;
    // body length of snake initially
    int body = 3;
    char dir = 'R';
    int score;

    //to keep the check on the state of the game
    boolean flag = false;

    int xsnake[] = new int[288];
    int ysnake[] = new int[288];

    panel(){
        //sets the size of the panel
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        //to enable keyboard input
        this.setFocusable(true);
        //adding a keylistener, it tells me what is pressed
        this.addKeyListener(new mykey());

        random = new Random();
        gamestart();
    }

    public void gamestart(){
        flag = true;
        spawnfood();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void spawnfood(){
        fx = random.nextInt((int) width/unit) * unit;
        fy = random.nextInt((int) height/unit) * unit;
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){
        if(flag == true){
            graphics.setColor(Color.orange);
            graphics.fillOval(fx, fy, unit, unit);

            for(int i=0;i<body;i++){
                if(i==0){
                    graphics.setColor(Color.red);
                    graphics.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
                else{
                    graphics.setColor(Color.green);
                    graphics.fillRect(xsnake[i], ysnake[i], unit, unit);

                }
            }

            //spawning the score display
            graphics.setColor(Color.CYAN);
            //setting the font
            graphics.setFont(new Font("comic sans", Font.BOLD, 40));
            FontMetrics fme = getFontMetrics(graphics.getFont());
            graphics.drawString("Score:"+score, (width - fme.stringWidth("Score:"+score))/2, graphics.getFont().getSize());
        }
        else{
            gameover(graphics);
        }
    }

    public void gameover(Graphics graphics){
        graphics.setColor(Color.CYAN);
        //setting the font
        graphics.setFont(new Font("comic sans", Font.BOLD, 40));
        FontMetrics fme = getFontMetrics(graphics.getFont());
        graphics.drawString("Score:"+score, (width - fme.stringWidth("Score:"+score))/2, graphics.getFont().getSize());

        graphics.setColor(Color.red);
        //setting the font
        graphics.setFont(new Font("comic sans", Font.BOLD, 80));
        FontMetrics fme1 = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (width - fme1.stringWidth("Game Over"))/2, height/2);

        graphics.setColor(Color.green);
        //setting the font
        graphics.setFont(new Font("comic sans", Font.BOLD, 40));
        FontMetrics fme2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Press R to replay", (width - fme2.stringWidth("Press R to replay"))/2, height/2 - 150);

    }

    public void move(){
        for(int i =body;i>0;i--){
            xsnake[i] = xsnake[i-1];
            ysnake[i] = ysnake[i-1];
        }

        switch(dir){
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'U':
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
                break;
        }
    }

    public void check(){
        if(xsnake[0] < 0){
            flag = false;
        }
        else if(xsnake[0]>width){
            flag = false;
        }
        else if(ysnake[0]<0){
            flag = false;
        }
        else if(ysnake[0]>height){
            flag = false;
        }

        for(int i=body; i>0;i--){
            if((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])){
                flag = false;
            }
        }
    }

    public void eat(){
        if((xsnake[0] == fx) && (ysnake[0] == fy)){
            body++;
            score++;
            spawnfood();
        }
    }

















    public  class mykey extends KeyAdapter{

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (dir != 'D') {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'U') {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L') {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (dir != 'R') {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_R:
                    if (!flag) {
                        break;

                    }
            }
        }



}



    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            eat();
            check();
        }
        repaint();

    }
}
