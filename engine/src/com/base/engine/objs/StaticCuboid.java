package com.base.engine.objs;

import com.base.engine.core.StaticMeshObject;
import com.base.engine.physics.Dimensions;
import com.base.engine.physics.StaticCubicalBodyPhysics;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class StaticCuboid extends StaticMeshObject{
	
	public StaticCuboid(StaticCubicalBodyPhysics body, Material material) {
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
	public StaticCuboid(Dimensions.Cuboid dimensions, Material material) {
		this(new StaticCubicalBodyPhysics(dimensions), material);
	}
	public StaticCuboid(float length, float width, float height, float mass, Material material) {
		this(new Dimensions.Cuboid(length, width, height, mass), material);
	}
	
	public StaticCubicalBodyPhysics getCubicalPhysics(){
		return (StaticCubicalBodyPhysics)getPhysicsObject();
	}
}
