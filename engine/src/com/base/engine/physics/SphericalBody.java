package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public class SphericalBody extends PhysicsObject{

	public SphericalBody(Dimensions.Sphere dimensions) {
		super(dimensions);
	}
	public SphericalBody(float radius, float mass) {
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
