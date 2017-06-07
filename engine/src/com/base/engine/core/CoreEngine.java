package com.base.engine.core;

import org.lwjgl.LWJGLException;

import com.base.engine.physics.PhysicsEngine;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class CoreEngine {
 
	private static CoreEngine instance;
	
	private boolean isRunning = false;
	private double frameTime;
	private Game game;
	
	private PhysicsEngine physicsEngine;
	private RenderingEngine renderingEngine;
	
	private CoreEngine(String title, int width, int height, double framerate) throws LWJGLException{
		this.isRunning = false;
		this.frameTime = 1.0 / framerate;
		
		init(title, width, height);
		this.renderingEngine = new RenderingEngine();
		this.physicsEngine = new PhysicsEngine();
	}
	
	public void start(Game game){
		if(isRunning)
			return;
		
		this.game = game;
		game.setEngine(this);
		run();
	}
	public void stop(){
		if(!isRunning)
			return;
		
		isRunning = false;
	}
	
	private void init(String title, int width, int height) throws LWJGLException{
		Window.createWindow(width, height, title);
		Input.init();
	}
	private void run(){
		isRunning = true;
		
		int frames = 0;
		double frameCounter = 0;

		game.init();

		double lastTime = Time.clockSecs();
		double unprocessedTime = 0;
		
		while(isRunning){
			boolean render = false;

			double startTime = Time.clockSecs();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime){
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(Window.isCloseRequested())
					stop();

				float fTime = (float) frameTime;
				Time.setDeltaTime(fTime);
				
				game.input(fTime);
				Input.update();
				
				game.update(fTime);
				game.updatePhysics(physicsEngine, fTime);
				
				if(frameCounter >= 1.0){
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render){
				game.render(renderingEngine);
				Window.render();
				frames++;
			}
			else
				Time.delay(1);
		}
		
		cleanUp();
	}

	private void cleanUp(){
		Input.dispose();
		Window.dispose();
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}
	public double getFrameRate(){
		return 1 / frameTime;
	}
	public void setFrameRate(float framerate){
		frameTime = 1.0f / framerate;
	}
	
	public static CoreEngine init(String title, int width, int height, double framerate){
		if(instance == null){
			try {
				instance = new CoreEngine(title, width, height, framerate);
			} catch (LWJGLException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}
	public static CoreEngine getInstance(){
		return instance;
	}
}
