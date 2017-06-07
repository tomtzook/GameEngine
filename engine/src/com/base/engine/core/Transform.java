package com.base.engine.core;

import com.base.engine.math.Matf;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vector3f;

public class Transform {
	
	private Transform parent;
	private Matf parentMatrix;

	private Vector3f position, pre_position;
	private Quaternion rotation, pre_rotation;
	private Vector3f scale, pre_scale;

	public Transform(){
		position = new Vector3f();
		rotation = new Quaternion(0,0,0,1);
		scale = new Vector3f(1,1,1);

		parentMatrix = Matf.initIdentity3();
	}
	
	public Vector3f getPosition(){
		return position;
	}
	public void setPosition(Vector3f pos){
		this.position = pos;
	}
	public Quaternion getRotation(){
		return rotation;
	}
	public void setRotation(Quaternion rotation){
		this.rotation = rotation;
	}
	public Vector3f getScale(){
		return scale;
	}
	public void setScale(Vector3f scale){
		this.scale = scale;
	}
	public void setParent(Transform parent){
		this.parent = parent;
	}
	
	public void move(Vector3f direction, float amount){
		position.addSelf(direction.multiply(amount));
	}
	public void move(Vector3f vec){
		position.addSelf(vec);
	}
	public void rotate(Vector3f axis, float angle){
		rotation = new Quaternion(axis, angle).multiply(rotation);
		rotation.normalize();
	}
	public void rotate(float x, float y, float z){
		rotate(Quaternion.AXIS_X, x);
		rotate(Quaternion.AXIS_Y, y);
		rotate(Quaternion.AXIS_Z, z);
	}
	public void rotate(Vector3f vec){
		rotate(vec.getX(), vec.getY(), vec.getZ());
	}
	public void lookAt(Vector3f point, Vector3f up){
		rotation = getLookAtRotation(point, up);
	}
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up){
		return new Quaternion(Matf.initRotation3(point.sub(position).normalized(), up));
	}
	
	public void update(){
		if(pre_position != null){
			pre_position.set(position);
			pre_rotation.set(rotation);
			pre_scale.set(scale);
		}
		else{
			pre_position = new Vector3f();
			pre_position.set(position);
			pre_position.addSelf(1.0f);
			pre_rotation = new Quaternion(0,0,0,0);
			pre_rotation.set(rotation);
			pre_rotation.multiply(0.5f);
			pre_scale = new Vector3f();
			pre_scale.set(scale);
			pre_scale.addSelf(1.0f);
		}
	}
	public boolean hasChanged(){
		if(parent != null && parent.hasChanged())
			return true;
		if(!position.equals(pre_position))
			return true;
		if(!rotation.equals(pre_rotation))
			return true;
		if(!scale.equals(pre_scale))
			return true;

		return false;
	}
	public Matf getTransformation(){
		Matf translationMatrix = Matf.initTranslation3(position.getX(), position.getY(), position.getZ());
		Matf rotationMatrix = rotation.toRotationMatrix();
		Matf scaleMatrix = Matf.initScaling3(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().multiply(translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix)));
	}
	private Matf getParentMatrix(){
		if(parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}
	public Vector3f getTransformedPosition(){
		return getParentMatrix().transform(position);
	}
	public Quaternion getTransformedRotation(){
		Quaternion parentRotation = new Quaternion(0,0,0,1);
		if(parent != null)
			parentRotation = parent.getTransformedRotation();
		return parentRotation.multiply(rotation);
	}
}
