package net.clockworkgiant.screen;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Screen {
	
	private JFrame frame;
	private Canvas canvas;
	
	//назва, висота та ширина вікна
	private String title;
	private int width, height;
	
	//основний клас вікна
	public Screen(String title, int width, int height){
		this.title=title;
		this.width=width;
		this.height=height;
		
		initScreen();
	}
	
	//створити вікно
	private void initScreen(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		
		//закрити вікно
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.setVisible(true);
		
		//центрувати вікно
		frame.setLocationRelativeTo(null);
		
		//створити робучу область у вікні
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack(); //підігнати вікно та робочу область
	}
	
	public Canvas getCanvas(){
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}
}
