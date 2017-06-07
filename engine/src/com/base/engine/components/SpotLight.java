package com.base.engine.components;

import com.base.engine.math.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class SpotLight extends PointLight{
	
	private float m_cutoff;
	
	public SpotLight(Vector3f color, float intensity, Attenuation attenuation, float cutoff){
		super(color, intensity, attenuation);
		this.m_cutoff = cutoff;

		setShader(new Shader("forward-spot"));
	}
	
	public Vector3f getDirection(){
		return getTransform().getTransformedRotation().getForward();
	}
	public float getCutoff(){
		return m_cutoff;
	}
	public void setCutoff(float cutoff){
		this.m_cutoff = cutoff;
	}

}
