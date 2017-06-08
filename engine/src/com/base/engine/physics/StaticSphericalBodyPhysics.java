package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public class StaticSphericalBodyPhysics extends StaticPhysicsObject{

	public StaticSphericalBodyPhysics(Dimensions.Sphere dimensions) {
		super(dimensions);
	}
	public StaticSphericalBodyPhysics(float radius, float mass) {
		super(new Dimensions.Sphere(radius, mass));
	}

	public Dimensions.Sphere getSphereDimensions(){
		return ((Dimensions.Sphere)getDimensions());
	}
	
	@Override
	public Vector3f getCenter() {
		return getPosition().copy();
	}
}
