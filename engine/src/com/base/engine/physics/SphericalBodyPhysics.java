package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public class SphericalBodyPhysics extends DynamicPhysicsObject{

	public SphericalBodyPhysics(Dimensions.Sphere dimensions) {
		super(dimensions);
	}
	public SphericalBodyPhysics(float radius, float mass) {
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
