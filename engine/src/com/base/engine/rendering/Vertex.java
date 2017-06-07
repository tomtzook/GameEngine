package com.base.engine.rendering;

import com.base.engine.math.Vector2f;
import com.base.engine.math.Vector3f;

public class Vertex {
	public static final int SIZE = 11;
	
	private Vector3f position;
	private Vector2f texCoord;
	private Vector3f normal;
	private Vector3f tangent;
	
	public Vertex(Vector3f pos){
		this(pos, new Vector2f());
	}
	public Vertex(Vector3f pos, Vector2f texCoord){
		this(pos, texCoord, new Vector3f());
	}
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal){
		this(pos, texCoord, normal, new Vector3f());
	}
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal, Vector3f tangent){
		this.position = pos;
		this.texCoord = texCoord;
		this.normal = normal;
		this.tangent = tangent;
	}

	public Vector3f getTangent() {
		return tangent;
	}
	public void setTangent(Vector3f tangent) {
		this.tangent = tangent;
	}
	public Vector3f getPosition(){
		return position;
	}
	public void setPosition(Vector3f pos){
		this.position = pos;
	}
	public Vector2f getTexCoord(){
		return texCoord;
	}
	public void setTexCoord(Vector2f texCoord){
		this.texCoord = texCoord;
	}
	public Vector3f getNormal(){
		return normal;
	}
	public void setNormal(Vector3f normal){
		this.normal = normal;
	}
}
