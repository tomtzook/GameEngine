package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public abstract class StaticPhysicsObject extends PhysicsObject{

	public StaticPhysicsObject(Dimensions dimensions) {
		super(dimensions);
	}

	@Override
	public boolean isStatic(){
		return true;
	}
	
	@Override
	public void applyForce(Vector3f force, Vector3f pos, float time){}
	@Override
	public void applyForce(AppliedForce obj){}
	@Override
	public void applyForces(AppliedForce... obj){}
	@Override
	protected void update(float delta){}
}
