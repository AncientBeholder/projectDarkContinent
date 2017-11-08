package net.clockworkgiant.gamebase;

public class Launcher {
	
	private static final int D_WIDTH = 1280, D_HEIGHT = 720;

	public static void main(String[] args) {
		Game game = new Game("[Pre-Alpha] Dark Continent", D_WIDTH, D_HEIGHT);
		game.start();

	}

}
