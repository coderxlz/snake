package snake;

import java.awt.Dimension;

import javax.swing.*;

public class Snake extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension dim = getToolkit().getScreenSize();
	int a = dim.width/4;
	int b = dim.height/4;
	public void outPut() {
		System.out.println(a);
		System.out.println(b);
	}
	public static void main(String[] args) {
		// 画出 900*700 的白色窗口
		
		// 添加画布
		SnakePanel panel = new SnakePanel();
		new SnakeFrame().add(panel);
				
	}
}

