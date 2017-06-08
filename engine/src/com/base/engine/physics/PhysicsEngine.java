package com.base.engine.physics;

import java.util.ArrayList;

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
		applyCollisions(root.getAllAttached());
		root.updatePhysicsAll(this, delta);
	}
	public void updateObject(PhysicsObject object, float delta){
		if(!object.isStatic()){
			//apply gravity
			object.applyForce(new Vector3f(0.0f, -getGravitationalConstant(), 0.0f), 
					object.getCenter(), delta);
		}
		
		object.update(delta);
	}
	
	private void applyCollisions(ArrayList<GameObject> allObjects){
		
	}
}
