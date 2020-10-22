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
	
	//����file�ļ������ڴ浵
	File file = new File("�浵");
	// ��Ϸ�Ƿ�ʼ�ı�־
		boolean isStarted = false;
		
		// ��Ϸ�Ƿ�ʧ�ܵı�־
		boolean isFaild = false;
				
	public SnakeFrame() {
		// TODO Auto-generated constructor stub
		initMenu();  
		this.setBounds(384, 80, 900, 760);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("snake.png").getImage());
		this.setTitle("̰����");
		jdialog = new MessageJDialog(this);
		this.setVisible(true);
	}
	
	public void initMenu() {
		//��ʼ���˵���
		jbar = new JMenuBar();
		this.setJMenuBar(jbar);
		speed = new JMenu("�Ѷ�");
		start = new JMenu("����");
		game = new JMenu("��Ϸ");
		wallset = new JMenu("�ϰ���");
		help = new JMenu("����");
		hole = new JMenu("�涴");
		jbar.add(start);
		jbar.add(speed);
		jbar.add(game);
		jbar.add(wallset);
		jbar.add(hole);
		jbar.add(help);
		ButtonGroup bg2 = new ButtonGroup();
		low = new JRadioButton("��");
		midual = new JRadioButton("�е�",true);
		fast = new JRadioButton("����");
		bg2.add(low);
		bg2.add(midual);
		bg2.add(fast);
		save = new JMenuItem("����");
		read = new JMenuItem("����");
		snakelength = new JMenu("�߳�");
		length10 = new JMenuItem("10");
		length15 = new JMenuItem("15");
		length20 = new JMenuItem("20");
		regulation = new JMenuItem("����");
		ButtonGroup bg1 = new ButtonGroup();
		ButtonGroup bg3 = new ButtonGroup();
		open = new JRadioButton("��");
		close = new JRadioButton("��",true);
		open1 = new JRadioButton("��");
		close1 = new JRadioButton("��",true);
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
		//�����߳�
		length10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//�����߳�Ϊ10
				SnakePanel.setSnakeLength(10);
			}
		});
		
		length15.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// �����߳�Ϊ15
				SnakePanel.setSnakeLength(15);
			}
		});
		
		length20.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// �����߳�Ϊ20
				SnakePanel.setSnakeLength(20);
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// ����Ϸ������ļ�file
				SnakePanel.saveGame(file);
			}
		});
		read.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// ����Ϸ��file�ж�ȡ����
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
				//���ü��Ѷ�
				 SnakePanel.setInitialSpeed(250);
			}
		});
		
		midual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//�����е��Ѷ�
				 SnakePanel.setInitialSpeed(200);
			}
		});
		
		fast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//���������Ѷ�
				 SnakePanel.setInitialSpeed(150);
			}
		});
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//���ϰ���
				SnakePanel.setConditionOpen();
			}
		});
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//�ر��ϰ���
				SnakePanel.setConditionClose();
			}
		});
		regulation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ʾ��Ϸ����Ի���
				jdialog.show("С���̷�������Ʒ��򣬿�����ͷ���Ե�ʳ�Ӯ�ø߷�");
			}
		});
		
		open1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// �򿪳涴
				SnakePanel.setConditionUpOpen();
			}
		});
		
		close1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// �رճ涴
				SnakePanel.setConditionUpClose();
			}
		});
	}
	//�����ڲ��࣬��ӶԻ��򣬽�����Ϸ����
	private class MessageJDialog extends JDialog{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JFrame jframe;
		private JLabel jlabel;
		
		//��ӶԻ��򣬽�����Ϸ����
		public MessageJDialog(JFrame jframe) {
			super(jframe,"��ʾ",true);
			this.jframe = jframe;
			this.setSize(400,100);
            jlabel = new JLabel("", JLabel.CENTER);  
            //��ǩ���ַ���Ϊ�գ����ж���
            this.add(jlabel); 
            this.setDefaultCloseOperation(HIDE_ON_CLOSE); 
            //�����Ի���Ĺرհ�ťʱ�����ضԻ���
		}
		public void show(String message) {
			jlabel.setText(message);
            this.setLocation(650, 380);//�Ի���λ���ڿ�ܵ����·�
            this.setVisible(true);   //��ʾ�Ի���
		}
	}
}
