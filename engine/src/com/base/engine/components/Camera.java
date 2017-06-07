package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.math.Matf;
import com.base.engine.math.Vector3f;

public class Camera extends GameComponent{
	
	private Matf projection;

	public Camera(Matf projection){
		this.projection = projection;
	}

	public Matf getViewProjection(){
		Matf cameraRotation = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPosition().multiply(-1);

		Matf cameraTranslation = Matf.initTranslation3(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

		return projection.multiply(cameraRotation.multiply(cameraTranslation));
	}

	@Override
	public void addToEngine(CoreEngine engine){
		engine.getRenderingEngine().addCamera(this);
	}
}
