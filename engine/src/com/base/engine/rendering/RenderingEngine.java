package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGetString;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.math.Vector3f;
import com.base.engine.rendering.resourceManagement.MappedValues;

public class RenderingEngine extends MappedValues{
	
	private HashMap<String, Integer> samplerMap;
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;

	private Shader forwardAmbient;
	private Camera mainCamera;

	public RenderingEngine(){
		super();
		lights = new ArrayList<BaseLight>();
		samplerMap = new HashMap<String, Integer>();
		samplerMap.put("diffuse", 0);
		samplerMap.put("normalMap", 1);
		samplerMap.put("dispMap", 2);

		addVector3f("ambient", new Vector3f(0.1f, 0.1f, 0.1f));

		forwardAmbient = new Shader("forward-ambient");

		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		//glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D); 
	}

	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType){
		throw new IllegalArgumentException(uniformType + " is not a supported type in RenderingEngine");
	}
	
	public void render(GameObject object){
		if (getMainCamera() == null) System.err.println("Error! Main camera not found. This is very very big bug, and game will crash.");
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		object.renderAll(forwardAmbient, this);

		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		for(BaseLight light : lights){
			activeLight = light;
			object.renderAll(light.getShader(), this);
		}

		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public static String getOpenGLVersion(){
		return glGetString(GL_VERSION);
	}
	public void addLight(BaseLight light){
		lights.add(light);
	}
	public void addCamera(Camera camera){
		mainCamera = camera;
	}
	public int getSamplerSlot(String samplerName){
		return samplerMap.get(samplerName);
	}
	public BaseLight getActiveLight(){
		return activeLight;
	}
	public Camera getMainCamera(){
		return mainCamera;
	}
	public void setMainCamera(Camera mainCamera){
		this.mainCamera = mainCamera;
	}
}
