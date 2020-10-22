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
				
	
	//生成食物对象
	private Food newfood = new Food(a,b);
	// 加载蛇头图片
	ImageIcon up = new ImageIcon("up.jpg");
	ImageIcon down = new ImageIcon("down.jpg");
	ImageIcon left = new ImageIcon("left.jpg");
	ImageIcon right = new ImageIcon("right.jpg");
	// 加载其他图片
	ImageIcon title = new ImageIcon("title.png");
	ImageIcon food = new ImageIcon("food.png");
	ImageIcon body = new ImageIcon("body.png");
	ImageIcon wall = new ImageIcon("wall.png");
	
	// 蛇的结构设计
	static int[] snakex = new int[750];
	static int[] snakey = new int[750];
	static int len = 3;
	static String direction = "R";   // R右 L左 U上 D下
	
	// 游戏是否开始的标志
	boolean isStarted = false;
	
	// 游戏是否失败的标志
	boolean isFaild = false;
	
	//设置游戏障碍物的开关，默认为关
	public static boolean condition = false;
	public static boolean conditionUp = false;
	// 设置游戏默认速度和困难等级
	static int initialspeed = 200;
	static int level = 1;
	
	static int HighScore = 0;
	
	//设置地图虫洞开
	public static void setConditionUpOpen() {
		conditionUp = true;
	}
	
	//设置地图虫洞关
	public static void setConditionUpClose() {
		conditionUp = false;
	}
	
	//设置地图障碍物开
	public static void setConditionOpen() {
		condition = true;
	}
	
	//设置地图障碍物关
	public static void setConditionClose() {
		condition = false;
	}
	
	//记录高分
	public void rememberHighScore(int newscore){
		if(HighScore<newscore) 
			HighScore = newscore;
		
	}
	
	//生成障碍物的方法
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
	//生产虫洞的方法
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
	//设置游戏难度
	public static void setInitialSpeed(int newspeed) {
		initialspeed = newspeed;
	}
	// 设置定时器的时长 进行actionPerformed()的方法
	Timer timer = new Timer(initialspeed, this);
	 // Timer(delay, listener)
	// delay 毫秒后执行 listener
	
	// 分数统计
	static int score = 0;
	
	public SnakePanel() {
		setFocusable(true);   // 获取焦点进行操作
		initSnake();          // 放置静态初始化的蛇 
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
						initialspeed = 200;		 //初始化游戏速度	
						initSnake();			 // 游戏失败  重新开始
					}
					else{
						isStarted = !isStarted;  // 切换游戏状态  继续/暂停 
					}
					repaint();
				}
				
				// 蛇头方向的改变
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
	//添加设置蛇长方法
	public static void setSnakeLength(int length) {
		len = length;
	}
		
	// 初始化蛇
	public void initSnake() {
		repaint();
		// 数据初始化
		isStarted = false;	  // 状态判断初始化
		isFaild = false;	  // 失败判断初始化
		score = 0;			  // 得分初始化
		level = 1;			  // 困难等级初始化
		len = 3;			  // 蛇的长度初始化
		direction = "R";	  // 蛇头方向初始化
		snakex[0] = 100;      // 头的坐标位置初始化
		snakey[0] = 100;
		snakex[1] = 75;		  // 身体第一节的坐标初始化
		snakey[1] = 100;
		snakex[2] = 50;		  // 身体第二节的坐标初始化
		snakey[2] = 100;
		loadGame(highscorefile);//加载历史最高分
		setScore();
		if (!this.isFocusable())//确保面板处于聚焦状态
			this.setFocusable(true);
		timer.start();
	}
	
	public void paint(Graphics g) {
		if (!this.isFocusable())//确保面板处于聚焦状态
			this.setFocusable(true);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 900, 720);
		
		// 设置游戏区域的背景颜色
		g.setColor(Color.BLACK);
		
		// x的长为850( 34个格子 * 25格子边长 ) y的长为600( 24个格子 * 25格子边长 )
		g.fillRect(25, 75, 850, 600);
		
		// 设置标题框
		title.paintIcon(this, g, 25, 11);
		
		// 画出蛇头(根据位置和方向)
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
		
		// 画出蛇身的每一节的位置，长度增加蛇身增加
		for( int i=1; i<len; i++ ) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}	
		
		// 画出食物
		food.paintIcon(this, g, newfood.getfoodx(), newfood.getfoody());
		
		//画出墙
		
		if(condition==true) {
			
				drawBarrier(g);
			
		}
		
		//画出虫洞
				if(conditionUp==true) {
					
					drawBarrierUp(g);
				
			}
		//当障碍物开关为打开状态时，蛇与生成障碍物相撞时，游戏结束
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
		//蛇身体与头部相撞时，游戏结束
		for(int k=1;k<len;k++) {
			if(snakex[k]==snakex[0]&&snakey[k]==snakey[0]) {
				isFaild = true;
			}
		}
		
		//当障碍物开关为打开状态时，蛇与生成障碍物相撞时，游戏结束
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
				
				//当虫洞开关为打开状态时，蛇进入虫洞，穿越
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
		
		//速度的提示
		g.setColor(Color.WHITE);
		g.setFont(new Font("宋体",Font.PLAIN,17));
		g.drawString("Speed: " + initialspeed, 620, 30);
		
		// 分数(蛇的长度)统计的提示
		g.setColor(Color.WHITE);
		g.setFont(new Font("宋体", Font.PLAIN, 17));
		g.drawString("Score: "+ score, 750, 30);
		g.drawString("Lengh: "+ len, 750, 50);
		
		// 困难等级的提示
		g.setColor(Color.WHITE);
		g.setFont(new Font("楷体", Font.PLAIN, 20));
		g.drawString("Level: "+ level, 30, 30);
		
		//最高分展示
		g.setColor(Color.WHITE);
		g.setFont(new Font("宋体",Font.BOLD+Font.ITALIC , 20));
		g.drawString("HighScore: "+HighScore, 160, 40);
		
		// 画出开始提示语
		if( !isStarted ) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("宋体", Font.BOLD, 30));
			g.drawString("Press Space to Start/Pause!", 245, 350);
		}
		
		// 画出失败提示语
		if( isFaild ) {
			g.setColor(Color.red);
			g.setFont(new Font("宋体", Font.BOLD, 30));
			g.drawString("Game Over ！！  Press Space to Start!", 195, 350);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {

		
		timer.setDelay(initialspeed);
		// 只有在蛇仍存活且不暂停的情况下前进  
		if( isStarted && !isFaild ) {
			// 移动蛇的身体  每一节的新坐标都是前一节的旧坐标 蛇头的坐标根据朝向进行加减
			for( int i=len; i>0; i-- ) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if( direction.equals("R")) {
				// 蛇头横坐标   + 25
				snakex[0] = snakex[0] + 25;
				if( snakex[0] > 850 ) {
					isFaild = true;       // 游戏结束的判断  即蛇头离开游戏区域
				}
			}
			else if( direction.equals("L")) {
				// 蛇头横坐标   - 25
				snakex[0] = snakex[0] - 25;
				if( snakex[0] < 25 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("U")) {
				// 蛇头纵坐标   - 25
				snakey[0] = snakey[0] - 25;
				if( snakey[0] < 75 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("D")) {
				// 蛇头横坐标   + 25
				snakey[0] = snakey[0] + 25;
				if( snakey[0] > 650 ) {
					isFaild = true;
				}
			}
			
			// 吃食物的判断 吃到食物后食物的坐标消失并更新 蛇变长
			for(int h =0;h<=len-1;h++) {
			if( snakex[h] == newfood.getfoodx() && snakey[h] == newfood.getfoody() ) 
			{
				len++;
				score++;
				
				newfood.getRandomed();
				repaint();
				//对分数进行判断，分数越高，速度越快，对应游戏难度越高
				switch (score) {
				case 1:
					initialspeed -= 10;
					level = 2;
					timer.setDelay(initialspeed);//设置定时器延时
					timer.restart();			 //重新启动定时器
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
		// 重新绘制图像
		repaint();
		
		saveHighScore(highscorefile);//记录最高分
	}
		
	}
	//添加游戏读取存档功能
		public static void saveGame(File file) {
			//游戏存档
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
		   //游戏读档
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
	   
	   //记录最高分
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
	   
	   //读取最高分
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
	
