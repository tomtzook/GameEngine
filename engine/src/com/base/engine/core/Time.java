package com.base.engine.core;

public class Time {
	private Time() {}
	
	private static float dtime = 0;
	
	public static void delay(long ms){
		if(ms <= 0) return;
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}
	public static void delay(float secs){
		delay((long)(secs * 1000));
	}
	public static long millis(){
		return System.currentTimeMillis();
	}
	public static float secs(){
		return millis() / 1000.0f;
	}
	public static double clockSecs(){
		return System.nanoTime() / 1e9;
	}
	public static float deltaTime(){
		return dtime;
	}
	static void setDeltaTime(float dt){
		dtime = dt;
	}
}
