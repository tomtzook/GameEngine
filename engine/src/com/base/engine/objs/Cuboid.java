package com.base.engine.objs;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.GameObject;
import com.base.engine.physics.CubicalBody;
import com.base.engine.physics.Dimensions;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

public class Cuboid extends GameObject{
	
	public Cuboid(Dimensions.Cuboid dimensions, Material material) {
		setPhysicsObject(new CubicalBody(dimensions));
		addComponent(new MeshRenderer(createCubeMesh(this), material));
	}
	public Cuboid(float length, float width, float height, float mass, Material material) {
		this(new Dimensions.Cuboid(length, width, height, mass), material);
	}
	
	public CubicalBody getCubicalPhysics(){
		return (CubicalBody)getPhysicsObject();
	}
	
	private static Mesh createCubeMesh(Cuboid cuboid){
		CubicalBody body = cuboid.getCubicalPhysics();
		
		Vertex fbr = new Vertex(body.getForwardBottomRight()), 
			   ftr = new Vertex(body.getForwardTopRight()),
			   fbl = new Vertex(body.getForwardBottomLeft()),  
			   ftl = new Vertex(body.getForwardTopLeft()),
			   bbr = new Vertex(body.getBackBottomRight()),    
			   btr = new Vertex(body.getBackTopRight()),
			   bbl = new Vertex(body.getBackBottomLeft()),     
			   btl = new Vertex(body.getBackTopLeft());
		Vertex[] vertices = {//front, back, top, bottom, right, left
			ftl/*0*/, fbl/*1*/, fbr/*2*/, ftr/*3*/,
			btl/*4*/, bbl/*5*/, bbr/*6*/, btr/*7*/,
		};
		int[] indices = {
			0, 1, 2,//front
			2, 3, 0,
			
			4, 7, 6,//back
			6, 5, 4,
			
			0, 3, 7,//top
			7, 4, 0,
			
			1, 5, 6,//bottom
			6, 2, 1,
			
			3, 2, 6,//right
			6, 7, 3,
			
			1, 0, 4,//left
			4, 5, 1
		};
		return new Mesh(vertices, indices);
	}
}
