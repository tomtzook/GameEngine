package com.base.engine.physics;

import com.base.engine.core.GameObject;
import com.base.engine.math.Vector3f;

public class PhysicsEngine {
	
	private float gravitaionalConstant;
	
	public PhysicsEngine(){
		gravitaionalConstant = Physics.Constants.EARTH_GRAVITATIONAL_CONSTANT;
	}
	
	public void setGravitationalConstant(float constant){
		this.gravitaionalConstant = constant;
	}
	public float getGravitationalConstant(){
		return gravitaionalConstant;
	}
	
	public void update(GameObject root, float delta){
		root.updatePhysicsAll(this, delta);
	}
	public void updateObject(GameObject object, float delta){
		object.applyForce(new Vector3f(0.0f, -getGravitationalConstant(), 0.0f), 
				object.getPhysicsObject().getCenter(), delta);
	}
}
