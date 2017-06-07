package com.base.engine.math;

public class Quaternion {
	public static final Vector3f 
			AXIS_X = new Vector3f(1, 0, 0),
			AXIS_Y = new Vector3f(0, 1, 0),
			AXIS_Z = new Vector3f(0, 0, 1);
			
	private float x;
	private float y;
	private float z;
	private float w;
	
	
	public Quaternion(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public Quaternion(Vector3f axis, float angle){
		angle =  Mathf.toRadians(angle);
		float sinHalfAngle = Mathf.sin(angle / 2);
		float cosHalfAngle = Mathf.cos(angle / 2);

		this.x = axis.getX() * sinHalfAngle;
		this.y = axis.getY() * sinHalfAngle;
		this.z = axis.getZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}
	public Quaternion(Matf rot){ 		
		float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2); 		
		if(trace > 0){ 			
			float s = 0.5f / (float)Math.sqrt(trace+ 1.0f); 			
			w = 0.25f / s; 			
			x = (rot.get(1, 2) - rot.get(2, 1)) * s; 			
			y = (rot.get(2, 0) - rot.get(0, 2)) * s; 			
			z = (rot.get(0, 1) - rot.get(1, 0)) * s; 		
		} 		
		else{ 			
			if(rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2)){ 
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2)); 				
				w = (rot.get(1, 2) - rot.get(2, 1)) / s; 				
				x = 0.25f * s; 				
				y = (rot.get(1, 0) + rot.get(0, 1)) / s; 				
				z = (rot.get(2, 0) + rot.get(0, 2)) / s; 			
			} 			
			else if(rot.get(1, 1) > rot.get(2, 2)){ 				
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2)); 				
				w = (rot.get(2, 0) - rot.get(0, 2)) / s; 				
				x = (rot.get(1, 0) + rot.get(0, 1)) / s; 				
				y = 0.25f * s; 				
				z = (rot.get(2, 1) + rot.get(1, 2)) / s; 			
			} 			
			else{ 				
				float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1)); 			
				w = (rot.get(0, 1) - rot.get(1, 0) ) / s; 				
				x = (rot.get(2, 0) + rot.get(0, 2) ) / s; 				
				y = (rot.get(1, 2) + rot.get(2, 1) ) / s; 				
				z = 0.25f * s; 			
			} 		
		} 		
		float length = (float)Math.sqrt(x * x + y * y + z * z + w * w); 		
		x /= length; 		
		y /= length; 		
		z /= length; 		
		w /= length; 	
	}
	public float length(){
		return Mathf.sqrt(x * x + y * y + z * z + w * w);
	}
	public void normalize(){
		float l = length();
		this.x /= l;
		this.y /= l;
		this.z /= l;
		this.w /= l;
	}
	public Quaternion normalized(){
		float l = length();
		return new Quaternion(x/l, y/l, z/l, w/l);
	}
	public Quaternion conjugate(){
		return new Quaternion(-x, -y, -z, w);
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public float getZ(){
		return z;
	}
	public float getW(){
		return w;
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public void setZ(float z){
		this.z = z;
	}
	public void setW(float w){
		this.w = w;
	}
	public void set(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public void set(Quaternion quaternion){
		this.x = quaternion.x;
		this.y = quaternion.y;
		this.z = quaternion.z;
		this.w = quaternion.w;
	}
	
	public void add(Quaternion quaternion){
		this.x += quaternion.x;
		this.y += quaternion.y;
		this.z += quaternion.z;
		this.w += quaternion.w;
	}
	public void sub(Quaternion quaternion){
		this.x -= quaternion.x;
		this.y -= quaternion.y;
		this.z -= quaternion.z;
		this.w -= quaternion.w;
	}
	public void multiply(float scalar){
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		this.w *= scalar;
	}

	public float dot(Quaternion r){
		return x * r.x + y * r.y + z * r.z + w * r.w;
	}
	
	public Quaternion nLerp(Quaternion dest, float lerpFactor, boolean shortest){
		Quaternion correctedDest = dest;

		if(shortest && dot(dest) < 0)
			correctedDest = new Quaternion(-dest.x, -dest.y, -dest.z, -dest.w);

		return correctedDest.sub2(this).multiply2(lerpFactor).add2(this).normalized();
	}

	public Quaternion sLerp(Quaternion dest, float lerpFactor, boolean shortest){
		final float EPSILON = 1e3f;

		float cos = dot(dest);
		Quaternion correctedDest = dest;

		if(shortest && cos < 0){
			cos = -cos;
			correctedDest = new Quaternion(-dest.x, -dest.y, -dest.z, -dest.w);
		}

		if(Math.abs(cos) >= 1 - EPSILON)
			return nLerp(correctedDest, lerpFactor, false);

		float sin = Mathf.sqrt(1.0f - cos * cos);
		float angle = Mathf.atan2(sin, cos);
		float invSin =  1.0f/sin;

		float srcFactor = (float) (Math.sin((1.0f - lerpFactor) * angle) * invSin);
		float destFactor = (float) (Math.sin((lerpFactor) * angle) * invSin);

		return multiply2(srcFactor).add2(correctedDest.multiply2(destFactor));
	}
	
	public Quaternion add2(Quaternion quaternion){
		return new Quaternion(x + quaternion.x, y + quaternion.y, z + quaternion.z, w + quaternion.w);
	}
	public Quaternion sub2(Quaternion quaternion){
		return new Quaternion(x - quaternion.x, y - quaternion.y, z - quaternion.z, w - quaternion.w);
	}
	public Quaternion multiply2(float scalar){
		return new Quaternion(x * scalar, y * scalar, z * scalar, w * scalar);
	}
	public Quaternion multiply(Quaternion quaternion){
		float w_ = w * quaternion.w - x * quaternion.x - y * quaternion.y - z * quaternion.z;
		float x_ = x * quaternion.w + w * quaternion.x + y * quaternion.z - z * quaternion.y;
		float y_ = y * quaternion.w + w * quaternion.y + z * quaternion.x - x * quaternion.z;
		float z_ = z * quaternion.w + w * quaternion.z + x * quaternion.y - y * quaternion.x;
		
		return new Quaternion(x_, y_, z_, w_);
	}
	public Quaternion multiply(Vector3f vec){
		float w_ = -x * vec.getX() - y * vec.getY() - z * vec.getZ();
		float x_ =  w * vec.getX() + y * vec.getZ() - z * vec.getY();
		float y_ =  w * vec.getY() + z * vec.getX() - x * vec.getZ();
		float z_ =  w * vec.getZ() + x * vec.getY() - y * vec.getX();
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Vector3f getForward(){
		Vector3f vec = new Vector3f(0, 0, 1);
		return vec.rotate(this);
	}

	public Vector3f getBack(){
		Vector3f vec = new Vector3f(0, 0, -1);
		return vec.rotate(this);
	}

	public Vector3f getUp(){
		Vector3f vec = new Vector3f(0, 1, 0);
		return vec.rotate(this);
	}

	public Vector3f getDown(){
		Vector3f vec = new Vector3f(0,-1, 0);
		return vec.rotate(this);
	}

	public Vector3f getRight(){
		Vector3f vec = new Vector3f(1, 0, 0);
		return vec.rotate(this);
	}

	public Vector3f getLeft(){
		Vector3f vec = new Vector3f(-1, 0, 0);
		return vec.rotate(this);
	}
	
	public Matf toRotationMatrix(){ 		
		Vector3f forward =  new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y)); 		
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x)); 		
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y)); 		
		return Matf.initRotation3(forward, up, right); 	
	}
}
