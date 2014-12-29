package model;

import java.awt.Color;

public class Constants {
	
	public static final int NB_ROW = 6; // 6 for an hexagon, 8 an octagon ...
	
	public static final float ROTATION_SPEED = 0.05f; // 0f for no rotation
	
	public static final float SHEAR_X = 0f; // between 0 and 1, over it became unplayable
	public static final float SHEAR_Y = 0f; // between 0 and 1, over it became unplayable
	
	public static final float PLAYER_SPEED = 1.5f;
	
	public static final float OBSTACLE_SPEED = 0.5f;
	
	public static final int MIN_OBS_RELOAD_TIME = 1000; // The min time between two obstacles in the same row (in ms)
	public static final int MAX_OBS_RELOAD_TIME = 2000; // The max time between two obstacles in the same row (in ms)
	
	public static final boolean PLAYED_BY_IA = false;
	
	public static final boolean INVINCIBLE = false;
	
	public static final Color COLOR = new Color(246, 72, 19);
	
	
	// Size of the window
	public static final int WIDTH = 800; // Will be ajusted according to the nb of rows
	public static final int HEIGHT = 600;
	
	public static final int FPS = 60; // Frame Per Second
}
