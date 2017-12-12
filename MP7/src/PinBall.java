import java.awt.*;
import java.awt.event.*; 
import javax.swing.Timer;
import java.util.Random;

public class PinBall {
	private Frame f=new Frame("Pinball");
	 Random rand=new Random();
	 
	 //The location and the width, height of the racket
	 private final int racketWidth = 60;
	 
	 private final int racketHeight = 15;
	 
	 private int racketX=rand.nextInt(24)*10;
	 
	 private int racketY=300;
	 
	 //Width and height of the table
	 private final int tableWidth = 400;
	 
	 private final int tableHeight = 400;
	 
	 //Create the background
	 private MyCanvas tableArea=new MyCanvas();
	 
	 //Pinball's size, moving speed and location
	 
	 private final int BALL_SIZE=16;
	 
	 private int ySpeed=1;
	 
	 private double xyRate=1;
	 
	 private int xSpeed=(int)(xyRate*ySpeed);
	 
	 private int ballX=rand.nextInt(284);
	 
	 private int ballY=1;
	 
	 //Define a timer
	 Timer timer;
	 
	 //Determine whether the game has ended
	 private boolean isLose=false;
	 //Game level
	 
	 private int time_times=1;
	 
	 public void init(){
		 
	  tableArea.setPreferredSize(new Dimension(tableWidth,tableHeight));
	  f.add(tableArea);
	  //Define key processor
	  KeyAdapter keyProcessor=new KeyAdapter() {
		  public void keyPressed(KeyEvent ke){
			  if(ke.getKeyCode()==KeyEvent.VK_LEFT){
				  if(racketX>0)
					  racketX-=10;
			  }
			  if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
				  if(racketX<tableWidth-racketWidth)
					  racketX+=10;
			  }
		  }
	  };
	  f.addKeyListener(keyProcessor);
	  //tableArea.addKeyListener(keyProcessor);
	  ActionListener taskPerformer=evt-> {
	   //When Pinball hit the left or right boundary
	   if(ballX<=0||ballX>=tableWidth-BALL_SIZE){
		   xSpeed=-xSpeed;
	   }
	   if(ballY>racketY-BALL_SIZE&&(ballX<racketX||ballX>racketX+racketWidth-BALL_SIZE)){
		   timer.stop();
		   isLose=true;
		   tableArea.repaint();
	   } else if(ballY<=0||(ballY>=racketY-BALL_SIZE&&ballX>racketX&&ballX<=racketX+racketWidth)){
		   ySpeed=-ySpeed;
	   }
	   ballY += ySpeed;
	   ballX += xSpeed;
	   tableArea.repaint();
	   if((xSpeed<10&&xSpeed>-10)&&(ySpeed<10&&ySpeed>-10)){
		   time_times ++;
	   }
	   if(time_times ==10){
		   if(xSpeed > 0){
			   xSpeed ++;
	   }else{
		   xSpeed --;
	   }
	   if(ySpeed > 0){
		   ySpeed ++;
	   }else {
		   ySpeed --;
	   }
	   time_times -= 10;
	   System.out.println(xSpeed+" "+ySpeed);
	   }
	  };
	  timer=new Timer(100,taskPerformer);
	  timer.start();
	  f.pack();
	  f.setVisible(true);
	 }
	 class MyCanvas extends Canvas {
		 public void paint(Graphics g){
			 if(isLose){
				 g.setColor(new Color(255,0,0));
				 g.setFont(new Font("Times",Font.BOLD,30));
				 g.drawString("Game Over",50,200);
			 }else{
				 g.setColor(new Color(240,140,180));
				 g.fillOval(ballX,ballY,BALL_SIZE,BALL_SIZE);
				 g.setColor(new Color(180,180,200));
				 g.fillRect(racketX,racketY,racketWidth,racketHeight);
			 }
		 }
	 }
	 public static void main(String[] args) {
		 new PinBall().init();
	 }
}
