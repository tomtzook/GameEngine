package com.base.engine.physics;

import com.base.engine.core.GameObject;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vector3f;

public abstract class PhysicsObject {
	
	private final Dimensions dimensions;
	
	private GameObject parent;
	
	public PhysicsObject(Dimensions dimensions){
		this.dimensions = dimensions;
	}

	public void setParent(GameObject parent){
		this.parent = parent;
	}
	public GameObject getParent(){
		return parent;
	}
	
	public Vector3f getPosition(){
		return parent.getTransform().getPosition();
	}
	public Quaternion getRotation(){
		return parent.getTransform().getRotation();
	}
	public Dimensions getDimensions(){
		return dimensions;
	}
	
	public void move(float x, float y, float z){
		parent.getTransform().move(new Vector3f(x, y, z));
	}
	public void move(Vector3f vec){
		parent.getTransform().move(vec);
	}
	public void move(Vector3f dir, float amount){
		parent.getTransform().move(dir, amount);
	}
	public void rotate(float x, float y, float z){
		parent.getTransform().rotate(x, y, z);
	}
	public void rotate(Vector3f vec){
		parent.getTransform().rotate(vec);
	}
	public void rotate(Vector3f axis, float amount){
		parent.getTransform().rotate(axis, amount);;
	}
	
	protected abstract void update(float delta);
	public abstract boolean isStatic();
	
	public abstract void applyForce(Vector3f force, Vector3f pos, float time);
	public abstract void applyForce(AppliedForce obj);
	public abstract void applyForces(AppliedForce... obj);
	public abstract Vector3f getCenter();
}
