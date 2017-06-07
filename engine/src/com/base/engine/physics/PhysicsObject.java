package com.base.engine.physics;

import java.util.ArrayList;

import com.base.engine.core.GameObject;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vector3f;

public abstract class PhysicsObject {
	
	private final ArrayList<AppliedForce> forces = new ArrayList<AppliedForce>();
	private final Dimensions dimensions;
	
	private GameObject parent;
	
	private Vector3f linearVelocity;
	private Vector3f linearAcceleration;
	private Vector3f angularVelocity;
	private Vector3f angularAcceleration;
	
	public PhysicsObject(Dimensions dimensions){
		this.dimensions = dimensions;
		
		linearAcceleration = new Vector3f();
		linearVelocity = new Vector3f();
		angularAcceleration = new Vector3f();
		angularVelocity = new Vector3f();
	}

	public void setParent(GameObject parent){
		this.parent = parent;
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
	
	public Vector3f getLinearAcceleration(){
		return linearAcceleration;
	}
	public Vector3f getLinearVelocity(){
		return linearVelocity;
	}
	public Vector3f getAngularAcceleration(){
		return angularAcceleration;
	}
	public Vector3f getAngularVelocity(){
		return angularVelocity;
	}
	
	public void applyForce(Vector3f force, Vector3f pos, float time){
		pos = pos.sub(getCenter());
		forces.add(new AppliedForce(force, pos, time));
	}
	public void applyForce(AppliedForce obj){
		obj.setPosition(obj.getPosition().sub(getCenter()));
		forces.add(obj);
	}
	public void applyForces(AppliedForce... obj){
		for (AppliedForce forceObj : obj)
			applyForce(forceObj);
	}
	
	public void update(PhysicsEngine engine, float delta){
		Vector3f netTorque = new Vector3f();
		Vector3f netForce = new Vector3f();
		
		for (int i = forces.size() - 1; i >= 0; i--) {
			AppliedForce forceObj = forces.get(i);
			netTorque.addSelf(Physics.torque(forceObj.getPosition(), 
					forceObj.getForce()));
			netForce.addSelf(forceObj.getForce());
			
			float time = forceObj.getTime() - delta;
			if(time <= 0.0f)
				forces.remove(i);
			else forceObj.setTime(time);
		}
		
		linearAcceleration = Physics.linearAcceleration(dimensions.getMass(), netForce);
		parent.getTransform().move(Physics.position(linearVelocity, linearAcceleration, delta));
		linearVelocity.addSelf(Physics.velocity(linearAcceleration, delta));
		
		angularAcceleration = Physics.angularAcceleration(dimensions.getMomentOfInertia(), netTorque);
		parent.getTransform().rotate(Physics.position(angularVelocity, angularAcceleration, delta));
		angularVelocity.addSelf(Physics.velocity(angularAcceleration, delta));
	}
	
	public abstract Vector3f getCenter();
}
