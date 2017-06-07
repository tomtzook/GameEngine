package com.base.engine.components;

import com.base.engine.core.Input;

public class FreeMove extends GameComponent{
	
	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;

	public FreeMove(float speed){
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey){
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}

	public float getSpeed(){
		return speed;
	}
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	@Override
	public void input(float delta){
		float movAmt = speed * delta;

		if(Input.getKey(forwardKey))
			getTransform().move(getTransform().getRotation().getForward(), movAmt);
		if(Input.getKey(backKey))
			getTransform().move(getTransform().getRotation().getForward(), -movAmt);
		if(Input.getKey(leftKey))
			getTransform().move(getTransform().getRotation().getLeft(), movAmt);
		if(Input.getKey(rightKey))
			getTransform().move(getTransform().getRotation().getRight(), movAmt);
	}
}
