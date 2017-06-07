package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vector2f;
import com.base.engine.rendering.Window;

public class FreeLook extends Script{

	private boolean mouseLocked = false;
	private float sensitivity;
	private int unlockMouseKey;

	private Vector2f centerPosition;
	
	public FreeLook(float sensitivity){
		this(sensitivity, Input.KEY_ESCAPE);
	}
	public FreeLook(float sensitivity, int unlockMouseKey){
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
		centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
	}

	public void setSensitivity(float sensitivity){
		this.sensitivity = sensitivity;
	}
	public float getSensitivity(){
		return sensitivity;
	}
	
	@Override
	public void init() {}
	@Override
	public void input(float delta){
		if(Input.getKey(unlockMouseKey)){
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouseDown(0)){
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}

		if(mouseLocked){
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if(rotY)
				getTransform().rotate(Quaternion.AXIS_Y, deltaPos.getX() * sensitivity);
			if(rotX)
				getTransform().rotate(getTransform().getRotation().getRight(), -deltaPos.getY() * sensitivity);

			if(rotY || rotX)
				Input.setMousePosition(centerPosition);
		}
	}
	@Override
	public void update(float delta) {}
}
