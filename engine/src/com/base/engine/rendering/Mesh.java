package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.base.engine.core.Util;
import com.base.engine.math.Mathf;
import com.base.engine.math.Vector2f;
import com.base.engine.math.Vector3f;
import com.base.engine.physics.CubicalBodyPhysics;
import com.base.engine.physics.SphericalBodyPhysics;
import com.base.engine.rendering.meshLoading.IndexedModel;
import com.base.engine.rendering.meshLoading.OBJModel;
import com.base.engine.rendering.resourceManagement.MeshResource;

public class Mesh {

	public static final int DEFUALT_SPHERE_MESH_SLICES = 20;
	public static final int DEFUALT_SPHERE_MESH_STACKS = 20;
	
	private static HashMap<String, MeshResource> s_loadedModels = new HashMap<String, MeshResource>();
	private MeshResource resource;
	private String fileName;
	private int drawType;
	
	public Mesh(String fileName){
		this.fileName = fileName;
		this.drawType = GL_TRIANGLES;
		MeshResource oldResource = s_loadedModels.get(fileName);

		if(oldResource != null){
			resource = oldResource;
			resource.addReference();
		}
		else{
			loadMesh(fileName);
			s_loadedModels.put(fileName, resource);
		}
	}
	public Mesh(Vertex[] vertices, int[] indices){
		this(vertices, indices, false);
	}
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals){
		this(vertices, indices, calcNormals, GL_TRIANGLES);
	}
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals, int drawType){
		fileName = "";
		this.drawType = drawType;
		addVertices(vertices, indices, calcNormals);
	}

	@Override
	protected void finalize(){
		if(resource.removeReference() && !fileName.isEmpty()){
			s_loadedModels.remove(fileName);
		}
	}
	
	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals){
		if(calcNormals){
			calcNormals(vertices, indices);
		}

		resource = new MeshResource(indices.length);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void draw(){
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glDrawElements(drawType, resource.getSize(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
	}
	
	private void calcNormals(Vertex[] vertices, int[] indices){
		for(int i = 0; i < indices.length; i += 3){
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
			Vector3f v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());
			
			Vector3f normal = v1.cross(v2).normalized();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
		}
		
		for(int i = 0; i < vertices.length; i++)
			vertices[i].setNormal(vertices[i].getNormal().normalized());
	}
	
	private Mesh loadMesh(String fileName){
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];

		if(!ext.equals("obj")){
			System.err.println("Error: '" + ext + "' file format not supported for mesh data.");
			new Exception().printStackTrace();
			System.exit(1);
		}

		OBJModel test = new OBJModel("./res/models/" + fileName);
		IndexedModel model = test.toIndexedModel();

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for(int i = 0; i < model.getPositions().size(); i++){
			vertices.add(new Vertex(model.getPositions().get(i),
					model.getTexCoords().get(i),
					model.getNormals().get(i),
					model.getTangents().get(i)));
		}

		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);

		addVertices(vertexData, Util.toIntArray(indexData), false);
		
		return this;
	}
	
	public static Mesh createSphereMesh(Vector3f center, float radius, int slices, int stacks){
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
	public static Mesh createCubeMesh(Vector3f... vec){
		Vertex fbr = new Vertex(vec[0]),//body.getForwardBottomRight()), 
			   ftr = new Vertex(vec[1]),//body.getForwardTopRight()),
			   fbl = new Vertex(vec[2]),//body.getForwardBottomLeft()),  
			   ftl = new Vertex(vec[3]),//body.getForwardTopLeft()),
			   bbr = new Vertex(vec[4]),//body.getBackBottomRight()),    
			   btr = new Vertex(vec[5]),//body.getBackTopRight()),
			   bbl = new Vertex(vec[6]),//body.getBackBottomLeft()),     
			   btl = new Vertex(vec[7]);//body.getBackTopLeft());
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
