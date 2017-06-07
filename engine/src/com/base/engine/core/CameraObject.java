package com.base.engine.core;

import com.base.engine.components.Camera;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.math.Matf;
import com.base.engine.rendering.Window;

public class CameraObject extends GameObject{

	public CameraObject(float fov, float zNear, float zFar){
		addComponent(new Camera(Matf.initPerspective(fov,
				(float) Window.getWidth() / (float) Window.getHeight(), zNear, zFar)));
	}
	
	public CameraObject setFreeLook(float sensitivity){
		addComponent(new FreeLook(sensitivity));
		return this;
	}
	public CameraObject setFreeMove(float speed){
		addComponent(new FreeMove(speed));
		return this;
	}
}
