package com.base.engine.physics;

import com.base.engine.math.Vector3f;

public class Physics {
	private Physics(){}

	public static class Constants{
		private Constants(){}
		
		public static final float EARTH_GRAVITATIONAL_CONSTANT = 9.80665f; 
	}
	
	public static Vector3f centerMomentOfIntertia(Shape shape, Dimensions dim){
		return shape.getCenterMomentOfInertia(dim);
	}
	public static Vector3f parallelAxisMomentOfIntertia(Shape shape, Dimensions dim, Vector3f newAxis){
		float d = dim.getMass() * dim.getCenterOfMass().sub(newAxis).length();
		return centerMomentOfIntertia(shape, dim).add(d);
	}
	//--------------------------------------------------------------------
	//--------------------------Dynamics----------------------------------
	//--------------------------------------------------------------------
	
	public static Vector3f linearAcceleration(float mass, Vector3f...forces){
		if(mass <= 0)
			throw new IllegalArgumentException("Mass must be positive");
		if(forces.length < 1)
			throw new IllegalArgumentException("forces are needed to calculate acceleration");
		
		Vector3f force = Vector3f.net(forces);
		return force.divSelf(mass);
	}
	public static Vector3f angularAcceleration(float momentOfInertia, Vector3f...torques){
		if(momentOfInertia <= 0)
			throw new IllegalArgumentException("moment of inertia must be positive");
		if(torques.length < 1)
			throw new IllegalArgumentException("torques are needed to calculate acceleration");
		
		Vector3f force = Vector3f.net(torques);
		return force.divSelf(momentOfInertia);
	}
	public static Vector3f angularAcceleration(Vector3f momentOfInertia, Vector3f...torques){
		if(torques.length < 1)
			throw new IllegalArgumentException("torques are needed to calculate acceleration");
		
		Vector3f force = Vector3f.net(torques);
		return force.divSelf(momentOfInertia);
	}
	
	public static Vector3f velocity(Vector3f acceleration, float deltaTime){
		if(deltaTime < 0)
			throw new IllegalArgumentException("deltaTime must be non-negative");
		
		return acceleration.multiply(deltaTime);
	}
	
	public static Vector3f position(Vector3f velocity, float deltaTime){
		if(deltaTime < 0)
			throw new IllegalArgumentException("deltaTime must be non-negative");
		
		return velocity.multiply(deltaTime);
	}
	public static Vector3f position(Vector3f velocity, Vector3f acceleration, float deltaTime){
		if(deltaTime < 0)
			throw new IllegalArgumentException("deltaTime must be non-negative");
		
		return velocity.multiply(deltaTime).addSelf(acceleration.addSelf(deltaTime * deltaTime * 0.5f));
	}
	
	public static Vector3f torque(Vector3f position, Vector3f force){
		return position.cross(force);
	}
	
	//--------------------------------------------------------------------
	//--------------------------Energy-----------------------------------
	//--------------------------------------------------------------------
	
	public static float linearKineticEnergy(float mass, Vector3f velocity){
		if(mass <= 0)
			throw new IllegalArgumentException("Mass must be non-negative");
		float l = velocity.length();
		return 0.5f * mass * l * l;
	}
	public static float angularKineticEnergy(float momentOfInertia, float angularVelocity){
		if(momentOfInertia <= 0)
			throw new IllegalArgumentException("Moment of inertia must be non-negative");
		return 0.5f * momentOfInertia * angularVelocity * angularVelocity;
	}
	public static float angularKineticEnergy(Vector3f momentOfInertia, Vector3f angularVelocity){
		return angularKineticEnergy(momentOfInertia.getX(), angularVelocity.getX()) + 
				angularKineticEnergy(momentOfInertia.getY(), angularVelocity.getY()) +
				angularKineticEnergy(momentOfInertia.getZ(), angularVelocity.getZ());
	}
	public static float potentialEnergy(float mass, float height){
		if(mass <= 0)
			throw new IllegalArgumentException("Mass must be non-negative");
		
		return mass * height * Constants.EARTH_GRAVITATIONAL_CONSTANT;
	}
	
	public static float work(Vector3f force, float min, float max){
		return (float) (force.length() * (max - min) * Math.cos(Math.toRadians(force.azimuth())));
	}
}
