package com.base.engine.components;

public abstract class Script extends GameComponent{
	
	public abstract void init();
	public abstract void input(float delta);
	public abstract void update(float delta);
}
