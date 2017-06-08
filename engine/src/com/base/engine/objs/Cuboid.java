package com.base.engine.objs;

import com.base.engine.core.DynamicMeshObject;
import com.base.engine.physics.CubicalBodyPhysics;
import com.base.engine.physics.Dimensions;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class Cuboid extends DynamicMeshObject{
	
	public Cuboid(CubicalBodyPhysics body, Material material) {
		super(body, Mesh.createCubeMesh(
				body.getForwardBottomRight(),
				body.getForwardTopRight(),
				body.getForwardBottomLeft(),
				body.getForwardTopLeft(),
				body.getBackBottomRight(),
				body.getBackTopRight(),
				body.getBackBottomLeft(),
				body.getBackTopLeft()
				), 
				material);
	}
	public Cuboid(Dimensions.Cuboid dimensions, Material material) {
		this(new CubicalBodyPhysics(dimensions), material);
	}
	public Cuboid(float length, float width, float height, float mass, Material material) {
		this(new Dimensions.Cuboid(length, width, height, mass), material);
	}
	
	public CubicalBodyPhysics getCubicalPhysics(){
		return (CubicalBodyPhysics)getPhysicsObject();
	}
}
