package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public class AppliedForce {
	
	private Vector3f force, position;
	private float time;
	
	public AppliedForce(Vector3f force, Vector3f position, float time){
		this.force = force;
		this.position = position;
		this.time = time;
	}
	
	public void setTime(float time){
		this.time = time;
	}
	public void setForce(Vector3f force){
		this.force = force;
	}
	public void setPosition(Vector3f position){
		this.position = position;
	}
	
	public float getTime(){
		return time;
	}
	public Vector3f getForce(){
		return force;
	}
	public Vector3f getPosition(){
		return position;
	}
}
