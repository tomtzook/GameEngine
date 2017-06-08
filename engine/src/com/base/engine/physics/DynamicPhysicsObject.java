package com.base.engine.physics;

import java.util.ArrayList;

import com.base.engine.math.Vector3f;

public abstract class DynamicPhysicsObject extends PhysicsObject{

	private final ArrayList<AppliedForce> forces = new ArrayList<AppliedForce>();
	
	private Vector3f linearVelocity;
	private Vector3f linearAcceleration;
	private Vector3f angularVelocity;
	private Vector3f angularAcceleration;
	
	public DynamicPhysicsObject(Dimensions dimensions) {
		super(dimensions);
		
		linearAcceleration = new Vector3f();
		linearVelocity = new Vector3f();
		angularAcceleration = new Vector3f();
		angularVelocity = new Vector3f();
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
	
	@Override
	public boolean isStatic(){
		return false;
	}
	
	@Override
	public void applyForce(Vector3f force, Vector3f pos, float time){
		pos = pos.sub(getCenter());
		forces.add(new AppliedForce(force, pos, time));
	}
	@Override
	public void applyForce(AppliedForce obj){
		obj.setPosition(obj.getPosition().sub(getCenter()));
		forces.add(obj);
	}
	@Override
	public void applyForces(AppliedForce... obj){
		for (AppliedForce forceObj : obj)
			applyForce(forceObj);
	}
	
	@Override
	protected void update(float delta){
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
		
		linearAcceleration = Physics.linearAcceleration(getDimensions().getMass(), netForce);
		move(Physics.position(linearVelocity, linearAcceleration, delta));
		linearVelocity.addSelf(Physics.velocity(linearAcceleration, delta));
		
		angularAcceleration = Physics.angularAcceleration(getDimensions().getMomentOfInertia(), netTorque);
		rotate(Physics.position(angularVelocity, angularAcceleration, delta));
		angularVelocity.addSelf(Physics.velocity(angularAcceleration, delta));
	}
}
