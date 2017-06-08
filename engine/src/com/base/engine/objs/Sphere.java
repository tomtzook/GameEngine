package com.base.engine.objs;

import com.base.engine.core.DynamicMeshObject;
import com.base.engine.physics.Dimensions;
import com.base.engine.physics.SphericalBodyPhysics;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class Sphere extends DynamicMeshObject{
	
	public Sphere(SphericalBodyPhysics body, Material material, int slices, int stacks) {
		super(body, 
				Mesh.createSphereMesh(body.getCenter(), body.getSphereDimensions().getRadius(), slices, stacks), 
				material);
	}
	public Sphere(Dimensions.Sphere dimensions, Material material) {
		this(new SphericalBodyPhysics(dimensions), material, Mesh.DEFUALT_SPHERE_MESH_SLICES, 
				Mesh.DEFUALT_SPHERE_MESH_STACKS);
	}
	public Sphere(float radius, float mass, Material material) {
		this(new Dimensions.Sphere(radius, mass), material);
	}

	public SphericalBodyPhysics getSphericalPhysics(){
		return (SphericalBodyPhysics)getPhysicsObject();
	}
}
