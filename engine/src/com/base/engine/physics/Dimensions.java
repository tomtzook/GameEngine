package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public abstract class Dimensions {
	
	public static class Cuboid extends Dimensions{

		private float length, width, height;
		
		public Cuboid(float length, float width, float height, float mass) {
			super(mass, new Vector3f());
			this.length = length;
			this.width = width;
			this.height = height;
			
			updateMomentOfInertia();
		}
		
		public void setLength(float l){
			length = l;
		}
		public void setWidth(float w){
			width = w;
		}
		public void setHeight(float h){
			height = h;
		}
		
		public float getLength(){
			return length;
		}
		public float getWidth(){
			return width;
		}
		public float getHeight(){
			return height;
		}
		
		@Override
		public void updateMomentOfInertia() {
			momentOfInertia = Physics.centerMomentOfIntertia(Shape.Cuboid.getInstance(), this);
		}
	}
	public static class Sphere extends Dimensions{

		private float radius;
		
		public Sphere(float radius, float mass) {
			super(mass, new Vector3f());
			this.radius = radius;
			
			updateMomentOfInertia();
		}
		
		public float getRadius(){
			return radius;
		}
		@Override
		public void updateMomentOfInertia() {
			momentOfInertia = Physics.centerMomentOfIntertia(Shape.Sphere.getInstance(), this);
		}
	}
	
	private float mass;
	private Vector3f centerMass;
	protected Vector3f momentOfInertia;
	
	public Dimensions(float mass, Vector3f centerMass){
		this.mass = mass;
		this.centerMass = centerMass;
	}
	
	public void setMass(float m){
		mass = m;
	}
	public void setCenterOfMass(Vector3f m){
		centerMass.set(m);
	}
	
	public float getMass() {
		return mass;
	}
	public Vector3f getCenterOfMass() {
		return centerMass;
	}
	public Vector3f getMomentOfInertia(){
		return momentOfInertia;
	}
	
	public abstract void updateMomentOfInertia();
}
