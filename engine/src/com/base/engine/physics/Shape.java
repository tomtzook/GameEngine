package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public abstract interface Shape {
	
	public static class Cuboid implements Shape{
		private static Shape instance;
		
		public static Shape getInstance(){
			if(instance == null)
				instance = new Cuboid();
			return instance;
		}
		
		private Cuboid(){}
		
		@Override
		public Vector3f getCenterMomentOfInertia(Dimensions dimensions) {
			if(!(dimensions instanceof Dimensions.Cuboid))
				throw new IllegalArgumentException("Dimensions must be Dimensions.Cuboid");
			
			Dimensions.Cuboid dim = (Dimensions.Cuboid)dimensions;
			float m = dim.getMass() / 12.0f,
				   sqLength = dim.getLength() * dim.getLength(),
				   sqWidth = dim.getWidth() * dim.getWidth(),
				   sqHeight = dim.getHeight() * dim.getHeight();
			return new Vector3f(m * (sqWidth + sqHeight), m * (sqLength + sqHeight), m * (sqLength + sqWidth));
		}
	}
	public static class Sphere implements Shape{
		private static Shape instance;
		
		public static Shape getInstance(){
			if(instance == null)
				instance = new Sphere();
			return instance;
		}
		
		private Sphere(){}
		
		@Override
		public Vector3f getCenterMomentOfInertia(Dimensions dimensions) {
			if(!(dimensions instanceof Dimensions.Sphere))
				throw new IllegalArgumentException("Dimensions must be Dimensions.Sphere");
			
			float rad = ((Dimensions.Sphere)dimensions).getRadius();
			float val = 0.4f * dimensions.getMass() * 
					rad * rad;
			return new Vector3f(val, val, val);
		}
	}
	
	Vector3f getCenterMomentOfInertia(Dimensions dimensions);
}
