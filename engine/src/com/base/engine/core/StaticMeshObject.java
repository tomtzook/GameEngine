package com.base.engine.core;

import com.base.engine.physics.StaticPhysicsObject;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class StaticMeshObject extends MeshObject{

	public StaticMeshObject(StaticPhysicsObject physics, Mesh mesh, Material material){
		super(mesh, material);
		setPhysicsObject(physics);
	}
}
