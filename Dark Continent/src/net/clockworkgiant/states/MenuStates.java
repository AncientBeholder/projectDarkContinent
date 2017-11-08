package net.clockworkgiant.states;

import java.awt.Color;
import java.awt.Graphics;

import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.ui.ClickListener;
import net.clockworkgiant.ui.UIImageButton;
import net.clockworkgiant.ui.UIManager;

public class MenuStates extends State{
	
	private UIManager uiManager;
	
	public MenuStates(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(10, 10, 200, 70, Assets.menu_btn, new ClickListener() {

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}}));
		
		uiManager.addObject(new UIImageButton(10, handler.getHeight() - 10 - 70, 200, 70, Assets.exit_btn, new ClickListener() {

			@Override
			public void onClick() {
				System.exit(1);;
			}}));
	}

	@Override
	public void update() {
		uiManager.update();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);
	}

}
