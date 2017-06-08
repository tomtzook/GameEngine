package com.base.engine.objs;

import com.base.engine.core.StaticMeshObject;
import com.base.engine.physics.Dimensions;
import com.base.engine.physics.StaticSphericalBodyPhysics;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class StaticSphere extends StaticMeshObject{
	
	public StaticSphere(StaticSphericalBodyPhysics body, Material material, int slices, int stacks) {
		super(body, 
				Mesh.createSphereMesh(body.getCenter(), body.getSphereDimensions().getRadius(), slices, stacks), 
				material);
	}
	public StaticSphere(Dimensions.Sphere dimensions, Material material) {
		this(new StaticSphericalBodyPhysics(dimensions), material, Mesh.DEFUALT_SPHERE_MESH_SLICES, 
				Mesh.DEFUALT_SPHERE_MESH_STACKS);
	}
	public StaticSphere(float radius, float mass, Material material) {
		this(new Dimensions.Sphere(radius, mass), material);
	}

	public StaticSphericalBodyPhysics getSphericalPhysics(){
		return (StaticSphericalBodyPhysics)getPhysicsObject();
	}
}
