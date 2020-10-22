package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;

public class SnakePanel extends JPanel implements  ActionListener {

	private static final long serialVersionUID = 1L;
	
	File highscorefile = new File("highscore");
	
	Random r = new Random();
	int a = r.nextInt(34)*25 + 25;
	int b = r.nextInt(24)*25 + 75;
				
	
	//����ʳ�����
	private Food newfood = new Food(a,b);
	// ������ͷͼƬ
	ImageIcon up = new ImageIcon("up.jpg");
	ImageIcon down = new ImageIcon("down.jpg");
	ImageIcon left = new ImageIcon("left.jpg");
	ImageIcon right = new ImageIcon("right.jpg");
	// ��������ͼƬ
	ImageIcon title = new ImageIcon("title.png");
	ImageIcon food = new ImageIcon("food.png");
	ImageIcon body = new ImageIcon("body.png");
	ImageIcon wall = new ImageIcon("wall.png");
	
	// �ߵĽṹ���
	static int[] snakex = new int[750];
	static int[] snakey = new int[750];
	static int len = 3;
	static String direction = "R";   // R�� L�� U�� D��
	
	// ��Ϸ�Ƿ�ʼ�ı�־
	boolean isStarted = false;
	
	// ��Ϸ�Ƿ�ʧ�ܵı�־
	boolean isFaild = false;
	
	//������Ϸ�ϰ���Ŀ��أ�Ĭ��Ϊ��
	public static boolean condition = false;
	public static boolean conditionUp = false;
	// ������ϷĬ���ٶȺ����ѵȼ�
	static int initialspeed = 200;
	static int level = 1;
	
	static int HighScore = 0;
	
	//���õ�ͼ�涴��
	public static void setConditionUpOpen() {
		conditionUp = true;
	}
	
	//���õ�ͼ�涴��
	public static void setConditionUpClose() {
		conditionUp = false;
	}
	
	//���õ�ͼ�ϰ��￪
	public static void setConditionOpen() {
		condition = true;
	}
	
	//���õ�ͼ�ϰ����
	public static void setConditionClose() {
		condition = false;
	}
	
	//��¼�߷�
	public void rememberHighScore(int newscore){
		if(HighScore<newscore) 
			HighScore = newscore;
		
	}
	
	//�����ϰ���ķ���
	public void drawBarrier(Graphics g) {
		for(int i=10;i<21;i++) {
			g.setColor(Color.WHITE);
			g.fillRect(10*25, i*25, 25, 25);
		}
		for(int i=10;i<21;i++) {
			g.setColor(Color.WHITE);
			g.fillRect(24*25, i*25, 25, 25);
		}
	}
	
	//
	//�����涴�ķ���
		public void drawBarrierUp(Graphics g) {
			g.setColor(Color.red);
			g.fillRect(5*25, 5*25, 25, 25);
			g.setColor(Color.red);
			g.fillRect(5*25, 6*25, 25, 25);
			g.setColor(Color.red);
			g.fillRect(5*25, 7*25, 25, 25);
			
			g.setColor(Color.red);
			g.fillRect(30*25, 20*25, 25, 25);
			g.setColor(Color.red);
			g.fillRect(30*25, 19*25, 25, 25);
			g.setColor(Color.red);
			g.fillRect(30*25, 18*25, 25, 25);
			
			g.setColor(Color.yellow);
			g.fillRect(5*25, 20*25, 25, 25);
			g.setColor(Color.yellow);
			g.fillRect(5*25, 19*25, 25, 25);
			g.setColor(Color.yellow);
			g.fillRect(5*25, 18*25, 25, 25);
			
			g.setColor(Color.yellow);
			g.fillRect(30*25, 5*25, 25, 25);
			g.setColor(Color.yellow);
			g.fillRect(30*25, 6*25, 25, 25);
			g.setColor(Color.yellow);
			g.fillRect(30*25, 7*25, 25, 25);
			

		}
	//������Ϸ�Ѷ�
	public static void setInitialSpeed(int newspeed) {
		initialspeed = newspeed;
	}
	// ���ö�ʱ����ʱ�� ����actionPerformed()�ķ���
	Timer timer = new Timer(initialspeed, this);
	 // Timer(delay, listener)
	// delay �����ִ�� listener
	
	// ����ͳ��
	static int score = 0;
	
	public SnakePanel() {
		setFocusable(true);   // ��ȡ������в���
		initSnake();          // ���þ�̬��ʼ������ 
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int keyCode = e.getKeyCode();
				if( keyCode == KeyEvent.VK_SPACE ) {
					if( isFaild ) {
						initialspeed = 200;		 //��ʼ����Ϸ�ٶ�	
						initSnake();			 // ��Ϸʧ��  ���¿�ʼ
					}
					else{
						isStarted = !isStarted;  // �л���Ϸ״̬  ����/��ͣ 
					}
					repaint();
				}
				
				// ��ͷ����ĸı�
				else if( keyCode == KeyEvent.VK_UP && !direction.equals("D")) {
					direction = "U";
				}
				else if( keyCode == KeyEvent.VK_DOWN && !direction.equals("U")) {
					direction = "D";
				}
				else if( keyCode == KeyEvent.VK_LEFT && !direction.equals("R")) {
					direction = "L";
				}
				else if( keyCode == KeyEvent.VK_RIGHT && !direction.equals("L")) {
					direction = "R";
				}
			}
		}); 
	}
	
	public static void setScore() {
		score = 0;
	}
	//��������߳�����
	public static void setSnakeLength(int length) {
		len = length;
	}
		
	// ��ʼ����
	public void initSnake() {
		repaint();
		// ���ݳ�ʼ��
		isStarted = false;	  // ״̬�жϳ�ʼ��
		isFaild = false;	  // ʧ���жϳ�ʼ��
		score = 0;			  // �÷ֳ�ʼ��
		level = 1;			  // ���ѵȼ���ʼ��
		len = 3;			  // �ߵĳ��ȳ�ʼ��
		direction = "R";	  // ��ͷ�����ʼ��
		snakex[0] = 100;      // ͷ������λ�ó�ʼ��
		snakey[0] = 100;
		snakex[1] = 75;		  // �����һ�ڵ������ʼ��
		snakey[1] = 100;
		snakex[2] = 50;		  // ����ڶ��ڵ������ʼ��
		snakey[2] = 100;
		loadGame(highscorefile);//������ʷ��߷�
		setScore();
		if (!this.isFocusable())//ȷ����崦�ھ۽�״̬
			this.setFocusable(true);
		timer.start();
	}
	
	public void paint(Graphics g) {
		if (!this.isFocusable())//ȷ����崦�ھ۽�״̬
			this.setFocusable(true);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 900, 720);
		
		// ������Ϸ����ı�����ɫ
		g.setColor(Color.BLACK);
		
		// x�ĳ�Ϊ850( 34������ * 25���ӱ߳� ) y�ĳ�Ϊ600( 24������ * 25���ӱ߳� )
		g.fillRect(25, 75, 850, 600);
		
		// ���ñ����
		title.paintIcon(this, g, 25, 11);
		
		// ������ͷ(����λ�úͷ���)
		if( direction.equals("R")) {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if( direction.equals("L") ) {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if( direction.equals("U") ) {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if( direction.equals("D") ) {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		
		// ���������ÿһ�ڵ�λ�ã�����������������
		for( int i=1; i<len; i++ ) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}	
		
		// ����ʳ��
		food.paintIcon(this, g, newfood.getfoodx(), newfood.getfoody());
		
		//����ǽ
		
		if(condition==true) {
			
				drawBarrier(g);
			
		}
		
		//�����涴
				if(conditionUp==true) {
					
					drawBarrierUp(g);
				
			}
		//���ϰ��￪��Ϊ��״̬ʱ�����������ϰ�����ײʱ����Ϸ����
		if(condition==true) {
		for(int i=10;i<21;i++) {
			if( 10*25 == snakex[0] && i*25 == snakey[0]) 
				isFaild = true;	
		}
		}
		if(condition==true) {
			for(int i=10;i<21;i++){
			if( 24*25 == snakex[0] && i*25 == snakey[0]) 
					isFaild = true;	
			}
		}
		//��������ͷ����ײʱ����Ϸ����
		for(int k=1;k<len;k++) {
			if(snakex[k]==snakex[0]&&snakey[k]==snakey[0]) {
				isFaild = true;
			}
		}
		
		//���ϰ��￪��Ϊ��״̬ʱ�����������ϰ�����ײʱ����Ϸ����
				if(condition==true) {
				for(int i=10;i<21;i++) {
					if( 10*25 == snakex[0] && i*25 == snakey[0]) 
						isFaild = true;	
				}
				}
				if(condition==true) {
					for(int i=10;i<21;i++){
					if( 24*25 == snakex[0] && i*25 == snakey[0]) 
							isFaild = true;	
					}
				}
				
				//���涴����Ϊ��״̬ʱ���߽���涴����Խ
				if(conditionUp==true) {		
						if( 5*25 == snakex[0] && 5*25 == snakey[0]) {
							snakex[0]=30*25;
							snakey[0]=20*25;
						}
						else if( 30*25 == snakex[0] && 20*25 == snakey[0]) {
							snakex[0]=5*25;
							snakey[0]=5*25;
						}					
				}
				
				if(conditionUp==true) {		
					if( 5*25 == snakex[0] && 6*25 == snakey[0]) {
						snakex[0]=30*25;
						snakey[0]=19*25;
					}
					else if( 30*25 == snakex[0] && 19*25 == snakey[0]) {
						snakex[0]=5*25;
						snakey[0]=6*25;
					}					
			}
				
				if(conditionUp==true) {		
					if( 5*25 == snakex[0] && 7*25 == snakey[0]) {
						snakex[0]=30*25;
						snakey[0]=18*25;
					}
					else if( 30*25 == snakex[0] && 18*25 == snakey[0]) {
						snakex[0]=5*25;
						snakey[0]=7*25;
					}					
			}
				
				//
				
				if(conditionUp==true) {		
					if( 5*25 == snakex[0] && 20*25 == snakey[0]) {
						snakex[0]=30*25;
						snakey[0]=5*25;
					}
					else if( 30*25 == snakex[0] && 5*25 == snakey[0]) {
						snakex[0]=5*25;
						snakey[0]=20*25;
					}					
			}
				
				if(conditionUp==true) {		
					if( 5*25 == snakex[0] && 19*25 == snakey[0]) {
						snakex[0]=30*25;
						snakey[0]=5*25;
					}
					else if( 30*25 == snakex[0] && 6*25 == snakey[0]) {
						snakex[0]=5*25;
						snakey[0]=19*25;
					}					
			}
				
				if(conditionUp==true) {		
					if( 5*25 == snakex[0] && 18*25 == snakey[0]) {
						snakex[0]=30*25;
						snakey[0]=5*25;
					}
					else if( 30*25 == snakex[0] && 7*25 == snakey[0]) {
						snakex[0]=5*25;
						snakey[0]=18*25;
					}					
			}
		
		//�ٶȵ���ʾ
		g.setColor(Color.WHITE);
		g.setFont(new Font("����",Font.PLAIN,17));
		g.drawString("Speed: " + initialspeed, 620, 30);
		
		// ����(�ߵĳ���)ͳ�Ƶ���ʾ
		g.setColor(Color.WHITE);
		g.setFont(new Font("����", Font.PLAIN, 17));
		g.drawString("Score: "+ score, 750, 30);
		g.drawString("Lengh: "+ len, 750, 50);
		
		// ���ѵȼ�����ʾ
		g.setColor(Color.WHITE);
		g.setFont(new Font("����", Font.PLAIN, 20));
		g.drawString("Level: "+ level, 30, 30);
		
		//��߷�չʾ
		g.setColor(Color.WHITE);
		g.setFont(new Font("����",Font.BOLD+Font.ITALIC , 20));
		g.drawString("HighScore: "+HighScore, 160, 40);
		
		// ������ʼ��ʾ��
		if( !isStarted ) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("����", Font.BOLD, 30));
			g.drawString("Press Space to Start/Pause!", 245, 350);
		}
		
		// ����ʧ����ʾ��
		if( isFaild ) {
			g.setColor(Color.red);
			g.setFont(new Font("����", Font.BOLD, 30));
			g.drawString("Game Over ����  Press Space to Start!", 195, 350);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {

		
		timer.setDelay(initialspeed);
		// ֻ�������Դ���Ҳ���ͣ�������ǰ��  
		if( isStarted && !isFaild ) {
			// �ƶ��ߵ�����  ÿһ�ڵ������궼��ǰһ�ڵľ����� ��ͷ��������ݳ�����мӼ�
			for( int i=len; i>0; i-- ) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if( direction.equals("R")) {
				// ��ͷ������   + 25
				snakex[0] = snakex[0] + 25;
				if( snakex[0] > 850 ) {
					isFaild = true;       // ��Ϸ�������ж�  ����ͷ�뿪��Ϸ����
				}
			}
			else if( direction.equals("L")) {
				// ��ͷ������   - 25
				snakex[0] = snakex[0] - 25;
				if( snakex[0] < 25 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("U")) {
				// ��ͷ������   - 25
				snakey[0] = snakey[0] - 25;
				if( snakey[0] < 75 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("D")) {
				// ��ͷ������   + 25
				snakey[0] = snakey[0] + 25;
				if( snakey[0] > 650 ) {
					isFaild = true;
				}
			}
			
			// ��ʳ����ж� �Ե�ʳ���ʳ���������ʧ������ �߱䳤
			for(int h =0;h<=len-1;h++) {
			if( snakex[h] == newfood.getfoodx() && snakey[h] == newfood.getfoody() ) 
			{
				len++;
				score++;
				
				newfood.getRandomed();
				repaint();
				//�Է��������жϣ�����Խ�ߣ��ٶ�Խ�죬��Ӧ��Ϸ�Ѷ�Խ��
				switch (score) {
				case 1:
					initialspeed -= 10;
					level = 2;
					timer.setDelay(initialspeed);//���ö�ʱ����ʱ
					timer.restart();			 //����������ʱ��
					break;
				case 5:
					initialspeed -= 10;
					level = 3;
					timer.setDelay(initialspeed);
					timer.restart();
					break;
				case 15:
					initialspeed -= 10;
					level = 4;
					timer.setDelay(initialspeed);
					timer.restart();
					break;
				case 30:
					initialspeed -= 10;
					level = 5;
					timer.setDelay(initialspeed);
					timer.restart();
					break;
				case 50:
					initialspeed -= 10;
					level = 6;
					timer.setDelay(initialspeed);
					timer.restart();
					break;
				case 80:
					initialspeed -= 10;
					level = 7;
					timer.setDelay(initialspeed);
					timer.restart();
					break;
				default:
					break;
				}		
		}
			rememberHighScore(score);
			}
		// ���»���ͼ��
		repaint();
		
		saveHighScore(highscorefile);//��¼��߷�
	}
		
	}
	//�����Ϸ��ȡ�浵����
		public static void saveGame(File file) {
			//��Ϸ�浵
			try {
				FileOutputStream fileStream = new FileOutputStream(file); 
				ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
				objectStream.writeObject(score);  
	            objectStream.writeObject(level);  
	            objectStream.writeObject(len);  
	            objectStream.writeObject(direction);
	            objectStream.writeObject(initialspeed);
	            objectStream.writeObject(HighScore);
	            for( int i=0;i<=len-1;i++) {
	            	 objectStream.writeObject(snakex[i]);
	            	 objectStream.writeObject(snakey[i]);
	            }
	            objectStream.close(); 
	            fileStream.close();
			}
			 catch (Exception e) {
			}
		}
	    
		
	   public static  void loadGame(File file) {
		   //��Ϸ����
	    	try {
	    		
	    		FileInputStream fileStream = new FileInputStream(file); 
	    		ObjectInputStream objectStream = new ObjectInputStream(fileStream);  
	    		score=(int)objectStream.readObject();
	    		level=(int)objectStream.readObject();
	    		len=(int)objectStream.readObject();
	    		initialspeed=(int)objectStream.readObject();
	    		direction=(String)objectStream.readObject();
	    		HighScore=(int)objectStream.readObject();
	    		for(int i=0;i<=len-1;i++) {
	    			snakex[i]=(int)objectStream.readObject();
	    			snakey[i]=(int)objectStream.readObject();
	           }
	    		objectStream.close();  
	    		fileStream.close(); 
	    	
	    	} catch (Exception e) {
				// TODO: handle exception
			}
	    }
	   
	   //��¼��߷�
	   public static void saveHighScore(File file) {
		   try {
				FileOutputStream fileStream = new FileOutputStream(file); 
				ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);				
	            objectStream.writeObject(HighScore);
	            objectStream.close(); 
	            fileStream.close();
			}
			 catch (Exception e) {
			}
	   }
	   
	   //��ȡ��߷�
	   public static void loadHighScore(File file) {
		   try {
			FileInputStream fileStream = new FileInputStream(file);
			   ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			   objectStream.close();
			   fileStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
}
	
