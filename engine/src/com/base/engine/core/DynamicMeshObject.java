package com.base.engine.core;

import com.base.engine.physics.DynamicPhysicsObject;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class DynamicMeshObject extends MeshObject{

	public DynamicMeshObject(DynamicPhysicsObject physics, Mesh mesh, Material material){
		super(mesh, material);
		setPhysicsObject(physics);
	}

}
