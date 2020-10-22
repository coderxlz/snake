package snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class SnakeFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar jbar;
	private JMenu speed,start,game,snakelength,wallset,help,hole;
	private JMenuItem save,read,length10,length15,length20,regulation;
	private JRadioButton low,midual,fast,open,close,open1,close1;
	
	private MessageJDialog jdialog;
	
	//生成file文件，用于存档
	File file = new File("存档");
	// 游戏是否开始的标志
		boolean isStarted = false;
		
		// 游戏是否失败的标志
		boolean isFaild = false;
				
	public SnakeFrame() {
		// TODO Auto-generated constructor stub
		initMenu();  
		this.setBounds(384, 80, 900, 760);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("snake.png").getImage());
		this.setTitle("贪吃蛇");
		jdialog = new MessageJDialog(this);
		this.setVisible(true);
	}
	
	public void initMenu() {
		//初始化菜单栏
		jbar = new JMenuBar();
		this.setJMenuBar(jbar);
		speed = new JMenu("难度");
		start = new JMenu("设置");
		game = new JMenu("游戏");
		wallset = new JMenu("障碍物");
		help = new JMenu("帮助");
		hole = new JMenu("虫洞");
		jbar.add(start);
		jbar.add(speed);
		jbar.add(game);
		jbar.add(wallset);
		jbar.add(hole);
		jbar.add(help);
		ButtonGroup bg2 = new ButtonGroup();
		low = new JRadioButton("简单");
		midual = new JRadioButton("中等",true);
		fast = new JRadioButton("困难");
		bg2.add(low);
		bg2.add(midual);
		bg2.add(fast);
		save = new JMenuItem("保存");
		read = new JMenuItem("读档");
		snakelength = new JMenu("蛇长");
		length10 = new JMenuItem("10");
		length15 = new JMenuItem("15");
		length20 = new JMenuItem("20");
		regulation = new JMenuItem("规则");
		ButtonGroup bg1 = new ButtonGroup();
		ButtonGroup bg3 = new ButtonGroup();
		open = new JRadioButton("开");
		close = new JRadioButton("关",true);
		open1 = new JRadioButton("开");
		close1 = new JRadioButton("关",true);
		bg1.add(open);
		bg1.add(close);
		bg3.add(open1);
		bg3.add(close1);
		hole.add(open1);
		hole.add(close1);
		start.add(snakelength);
		snakelength.add(length10);
		snakelength.add(length15);
		snakelength.add(length20);
		wallset.add(open);
		wallset.add(close);
		help.add(regulation);
		//设置蛇长
		length10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//设置蛇长为10
				SnakePanel.setSnakeLength(10);
			}
		});
		
		length15.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 设置蛇长为15
				SnakePanel.setSnakeLength(15);
			}
		});
		
		length20.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 设置蛇长为20
				SnakePanel.setSnakeLength(20);
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 将游戏储存进文件file
				SnakePanel.saveGame(file);
			}
		});
		read.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 将游戏从file中读取出来
				 SnakePanel.loadGame(file);
			}
		});
		speed.add(low);
		speed.add(midual);
		speed.add(fast);
		game.add(save);
		game.add(read);
		low.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//设置简单难度
				 SnakePanel.setInitialSpeed(250);
			}
		});
		
		midual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//设置中等难度
				 SnakePanel.setInitialSpeed(200);
			}
		});
		
		fast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//设置困难难度
				 SnakePanel.setInitialSpeed(150);
			}
		});
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//打开障碍物
				SnakePanel.setConditionOpen();
			}
		});
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//关闭障碍物
				SnakePanel.setConditionClose();
			}
		});
		regulation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//显示游戏规则对话框
				jdialog.show("小键盘方向键控制方向，控制蛇头，吃得食物，赢得高分");
			}
		});
		
		open1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 打开虫洞
				SnakePanel.setConditionUpOpen();
			}
		});
		
		close1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 关闭虫洞
				SnakePanel.setConditionUpClose();
			}
		});
	}
	//定义内部类，添加对话框，介绍游戏规则
	private class MessageJDialog extends JDialog{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JFrame jframe;
		private JLabel jlabel;
		
		//添加对话框，介绍游戏规则
		public MessageJDialog(JFrame jframe) {
			super(jframe,"提示",true);
			this.jframe = jframe;
			this.setSize(400,100);
            jlabel = new JLabel("", JLabel.CENTER);  
            //标签的字符串为空，居中对齐
            this.add(jlabel); 
            this.setDefaultCloseOperation(HIDE_ON_CLOSE); 
            //单击对话框的关闭按钮时，隐藏对话框
		}
		public void show(String message) {
			jlabel.setText(message);
            this.setLocation(650, 380);//对话框位置在框架的右下方
            this.setVisible(true);   //显示对话框
		}
	}
}
