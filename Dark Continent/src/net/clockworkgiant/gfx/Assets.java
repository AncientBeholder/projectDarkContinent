package net.clockworkgiant.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int f_width = 64, f_height = 64, f_width16 = 16, f_height16 = 16;
	private static int width16 = f_width16, height16 = f_height16, width = f_width, height = f_height;
	public static BufferedImage mob, target, floor1, door1, door2, blank, wall, lava, statue, move,
	AOE, floor2, floor3, floor4, floor5, floor6, floor7;
	public static BufferedImage[] riku;
	public static BufferedImage[] menu_btn, exit_btn, resume_btn, main_btn, attack, shoot, magic, 
		miracle, defend, skip, back, ok;
	
	public static void init(){
		SpriteSheet t_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tile_placeholder_test.png"));
		SpriteSheet a_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/64x64.png"));
		SpriteSheet p_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/character_placeholder_test.png"));
		SpriteSheet m_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/title_op.png"));
		SpriteSheet b_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Template_actions.png"));
		
		
		riku = new BufferedImage[128];
		
		for(int i = 0, d = 0; i < 16; i++) {
			for(int j = 0; j < 8; j++) {
				riku[d] =  p_sheet.crop(j*f_width, i*f_height, f_width, f_height);
				d++;
			}
		}
		
		
		menu_btn = new BufferedImage[2];
		menu_btn[0] =  m_sheet.crop(0*400, 0*140, 400, 140);
		menu_btn[1] =  m_sheet.crop(1*400, 0*140, 400, 140);
		

		exit_btn = new BufferedImage[2];
		exit_btn[0] =  m_sheet.crop(0*400, 3*140, 400, 140);
		exit_btn[1] =  m_sheet.crop(1*400, 3*140, 400, 140);
		
		main_btn = new BufferedImage[2];
		main_btn[0] =  m_sheet.crop(0*400, 4*140, 400, 140);
		main_btn[1] =  m_sheet.crop(1*400, 4*140, 400, 140);
		
		resume_btn = new BufferedImage[2];
		resume_btn[0] =  m_sheet.crop(0*400, 5*140, 400, 140);
		resume_btn[1] =  m_sheet.crop(1*400, 5*140, 400, 140);
		
		attack = new BufferedImage[2];
		attack[0] =  b_sheet.crop(0*f_width16, 0*f_height16, f_width16, f_height16);
		attack[1] =  b_sheet.crop(3*f_width16, 0*f_height16, f_width16, f_height16);
		
		shoot = new BufferedImage[2];
		shoot[0] =  b_sheet.crop(1*f_width16, 0*f_height16, f_width16, f_height16);
		shoot[1] =  b_sheet.crop(4*f_width16, 0*f_height16, f_width16, f_height16);
		
		magic = new BufferedImage[2];
		magic[0] =  b_sheet.crop(2*f_width16, 0*f_height16, f_width16, f_height16);
		magic[1] =  b_sheet.crop(5*f_width16, 0*f_height16, f_width16, f_height16);
		
		miracle = new BufferedImage[2];
		miracle[0] =  b_sheet.crop(0*f_width16, 1*f_height16, f_width16, f_height16);
		miracle[1] =  b_sheet.crop(3*f_width16, 1*f_height16, f_width16, f_height16);
		
		defend = new BufferedImage[2];
		defend[0] =  b_sheet.crop(1*f_width16, 1*f_height16, f_width16, f_height16);
		defend[1] =  b_sheet.crop(4*f_width16, 1*f_height16, f_width16, f_height16);
		
		skip = new BufferedImage[2];
		skip[0] =  b_sheet.crop(2*f_width16, 1*f_height16, f_width16, f_height16);
		skip[1] =  b_sheet.crop(5*f_width16, 1*f_height16, f_width16, f_height16);
		
		back = new BufferedImage[2];
		back[0] =  b_sheet.crop(0*f_width16, 2*f_height16, f_width16, f_height16);
		back[1] =  b_sheet.crop(3*f_width16, 2*f_height16, f_width16, f_height16);
		
		ok = new BufferedImage[2];
		ok[0] =  b_sheet.crop(1*f_width16, 2*f_height16, f_width16, f_height16);
		ok[1] =  b_sheet.crop(4*f_width16, 2*f_height16, f_width16, f_height16);

		wall = t_sheet.crop(7*f_width, 0*f_height, f_width, f_height);
		lava = t_sheet.crop(3*f_width, 0*f_height, f_width, f_height);
		target = t_sheet.crop(1*f_width, 1*f_height, f_width, f_height);
		
		floor1 = t_sheet.crop(1*f_width, 0*f_height, f_width, f_height);
//		floor2 = t_sheet.crop(1*f_width, 0*f_height, f_width, f_height);
//		floor3 = t_sheet.crop(6*f_width, 0*f_height, f_width, f_height);
//		floor4 = t_sheet.crop(4*f_width, 1*f_height, f_width, f_height);
//		floor5 = t_sheet.crop(5*f_width, 1*f_height, f_width, f_height);
//		floor6 = t_sheet.crop(7*f_width, 1*f_height, f_width, f_height);
//		floor7 = t_sheet.crop(1*f_width, 2*f_height, f_width, f_height);
		
		door1 = t_sheet.crop(5*f_width, 0*f_height, f_width, f_height);
		door2 = t_sheet.crop(6*f_width, 0*f_height, f_width, f_height);
		blank = t_sheet.crop(0*f_width, 0*f_height, f_width, f_height);
		
		statue = a_sheet.crop(4*f_width, 4*f_height, f_width, f_height);
		move = t_sheet.crop(2*f_width, 1*f_height, f_width, f_height);
		AOE = t_sheet.crop(0*f_width, 1*f_height, f_width, f_height);
	}

}
