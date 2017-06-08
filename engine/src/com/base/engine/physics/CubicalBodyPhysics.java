package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public class CubicalBodyPhysics extends DynamicPhysicsObject{

	public CubicalBodyPhysics(Dimensions.Cuboid dimensions) {
		super(dimensions);
	}
	public CubicalBodyPhysics(float length, float width, float height, float mass) {
		super(new Dimensions.Cuboid(length, width, height, mass));
	}
	
	public Dimensions.Cuboid getCuboidDimensions(){
		return ((Dimensions.Cuboid)getDimensions());
	}
	public Vector3f getLengthVector(){
		return getRotation().getRight().multiply(getCuboidDimensions().getLength());
	}
	public Vector3f getWidthVector(){
		return getRotation().getForward().multiply(getCuboidDimensions().getWidth());
	}
	public Vector3f getHeightVector(){
		return getRotation().getUp().multiply(getCuboidDimensions().getHeight());
	}
	
	public Vector3f getBackTopRight(){
		Vector3f u = getRotation().getUp();
		float du = getCuboidDimensions().getHeight() / u.length();
		return getBackBottomRight().add(u.multiply(du));
	}
	public Vector3f getBackBottomRight(){
		Vector3f r = getRotation().getRight();
		float dr = getCuboidDimensions().getLength() / r.length();
		return getPosition().add(r.multiply(dr));
	}
	public Vector3f getBackTopLeft(){
		Vector3f u = getRotation().getUp();
		float du = getCuboidDimensions().getHeight() / u.length();
		return getPosition().add(u.multiply(du));
	}
	public Vector3f getBackBottomLeft(){
		return getPosition().copy();
	}
	public Vector3f getForwardBottomRight(){
		Vector3f r = getRotation().getRight();
		float dr = getCuboidDimensions().getLength() / r.length();
		return getForwardBottomLeft().add(r.multiply(dr));
	}
	public Vector3f getForwardTopRight(){
		Vector3f u = getRotation().getUp();
		float du = getCuboidDimensions().getHeight() / u.length();
		return getForwardBottomRight().add(u.multiply(du));
	}
	public Vector3f getForwardTopLeft(){
		Vector3f u = getRotation().getUp();
		float du = getCuboidDimensions().getHeight() / u.length();
		return getForwardBottomLeft().add(u.multiply(du));
	}
	public Vector3f getForwardBottomLeft(){
		Vector3f f = getRotation().getForward();
		float df = getCuboidDimensions().getWidth() / f.length();
		return getPosition().add(f.multiply(df));
	}
	
	@Override
	public Vector3f getCenter() {
		return getPosition()
				.add(getLengthVector().multiplySelf(0.5f))
				.addSelf(getWidthVector().multiplySelf(0.5f))
				.addSelf(getHeightVector().multiplySelf(0.5f));
	}
}
