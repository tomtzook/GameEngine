package com.base.engine.objs;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.base.engine.components.MeshRenderer;
import com.base.engine.core.GameObject;
import com.base.engine.core.Util;
import com.base.engine.math.Mathf;
import com.base.engine.math.Vector2f;
import com.base.engine.math.Vector3f;
import com.base.engine.physics.Dimensions;
import com.base.engine.physics.SphericalBody;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

public class Sphere extends GameObject{
	
	public static final int DEFUALT_SLICES = 20;
	public static final int DEFUALT_STACKS = 50;
	
	public Sphere(Dimensions.Sphere dimensions, Material material, int slices, int stacks) {
		setPhysicsObject(new SphericalBody(dimensions));
		addComponent(new MeshRenderer(createSphereMesh(this, slices, stacks), material));
	}
	public Sphere(Dimensions.Sphere dimensions, Material material){
		this(dimensions, material, DEFUALT_SLICES, DEFUALT_STACKS);
	}
	public Sphere(float radius, float mass, Material material) {
		this(new Dimensions.Sphere(radius, mass), material);
	}

	public SphericalBody getSphericalPhysics(){
		return (SphericalBody)getPhysicsObject();
	}
	
	private static Mesh createSphereMesh(Sphere sphere, int slices, int stacks){
		SphericalBody body = sphere.getSphericalPhysics();
		float radius = body.getSphereDimensions().getRadius();
		Vector3f center = body.getCenter();
		
		ArrayList<Vertex> vertecies = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		float PI = Mathf.PI;
		float rho, drho = PI / stacks, theta, dtheta = 2.0f * PI / slices;
		float x, y, z;
		float s, t = 1.0f, ds = 1.0f / slices, dt = 1.0f / stacks;
		int i, j, imin = 0, imax = stacks;
		float nsign = 1.0f;
		
		for (i = imin; i < imax; i++) {
			rho = i * drho;
			s = 0.0f;
			for (j = 0; j <= slices; j++) {
				theta = (j == slices) ? 0.0f : j * dtheta;
				x = center.getX() + (-Mathf.sin(theta) * Mathf.sin(rho));
				y = center.getY() + (Mathf.cos(theta) * Mathf.sin(rho));
				z = center.getZ() + (nsign * Mathf.cos(rho));
				vertecies.add(new Vertex(
						new Vector3f(x * radius, y * radius, z * radius),
						new Vector2f(s, t)));
				indices.add(vertecies.size()-1);
				
				x = center.getX() + (-Mathf.sin(theta) * Mathf.sin(rho + drho));
				y = center.getY() + (Mathf.cos(theta) * Mathf.sin(rho + drho));
				z = center.getZ() + (nsign * Mathf.cos(rho + drho));
				s += ds;
				vertecies.add(new Vertex(
						new Vector3f(x * radius, y * radius, z * radius),
						new Vector2f(s, t - dt)));
				indices.add(vertecies.size()-1);
			}
			t -= dt;
		}
		
		return new Mesh(vertecies.toArray(new Vertex[0]),
				Util.toIntArray(indices.toArray(new Integer[0])), 
				true, GL11.GL_QUAD_STRIP);
	}
}
