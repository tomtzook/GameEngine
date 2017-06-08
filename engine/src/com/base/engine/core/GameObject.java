package com.base.engine.core;

import java.util.ArrayList;

import com.base.engine.components.GameComponent;
import com.base.engine.math.Vector3f;
import com.base.engine.physics.AppliedForce;
import com.base.engine.physics.PhysicsEngine;
import com.base.engine.physics.PhysicsObject;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class GameObject {
	
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	private PhysicsObject physicsObject;
	private CoreEngine engine = null;
	
	public GameObject(){
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}
	
	public void setPhysicsObject(PhysicsObject physicsObject){
		this.physicsObject = physicsObject;
		physicsObject.setParent(this);
	}
	public PhysicsObject getPhysicsObject(){
		return physicsObject;
	}
	public void applyForce(Vector3f force, Vector3f pos, float time){
		if(physicsObject != null)
			physicsObject.applyForce(force, pos, time);
	}
	public void applyForce(AppliedForce obj){
		if(physicsObject != null)
			physicsObject.applyForce(obj);
	}
	public void applyForces(AppliedForce... obj){
		if(physicsObject != null)
			physicsObject.applyForces(obj);
	}
	
	public GameObject addChild(GameObject child){
		children.add(child);
		child.getTransform().setParent(transform);
		if(engine != null)
			child.setEngine(engine);
		
		return this;
	}
	public GameObject addComponent(GameComponent component){
		components.add(component);
		component.setParent(this);
		component.init();
		if(engine != null)
			component.addToEngine(engine);
		
		return this;
	}
	
	public void inputAll(float delta){
		input(delta);

		for(int i = 0; i < children.size(); i++)
			children.get(i).inputAll(delta);
	}

	public void updateAll(float delta){
		update(delta);

		for(int i = 0; i < children.size(); i++)
			children.get(i).updateAll(delta);
	}
	public void updatePhysicsAll(PhysicsEngine engine, float delta){
		if(physicsObject != null)
			engine.updateObject(physicsObject, delta);
		
		for(int i = 0; i < children.size(); i++)
			children.get(i).updatePhysicsAll(engine, delta);
	}
	public void renderAll(Shader shader, RenderingEngine renderingEngine){
		render(shader, renderingEngine);

		for(int i = 0; i < children.size(); i++)
			children.get(i).renderAll(shader, renderingEngine);
	}

	public void input(float delta){
		transform.update();

		for(int i = 0; i < components.size(); i++)
			components.get(i).input(delta);
	}
	public void update(float delta){
		for(int i = 0; i < components.size(); i++)
			components.get(i).update(delta);
	}
	public void render(Shader shader, RenderingEngine renderingEngine){
		for(int i = 0; i < components.size(); i++)
			components.get(i).render(shader, renderingEngine);
	}
	
	public Transform getTransform(){
		return transform;
	}
	public ArrayList<GameObject> getAllAttached(){
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for(int i = 0; i < children.size(); i++)
			result.addAll(children.get(i).getAllAttached());

		return result;
	}
	public ArrayList<GameComponent> getAllComponents(){
		ArrayList<GameComponent> result = new ArrayList<GameComponent>();
		for (int i = 0; i < components.size(); i++)
			result.add(components.get(i));
		
		return result;
	}
	
	public void setEngine(CoreEngine engine){
		if(this.engine != engine){
			this.engine = engine;

			for(GameComponent component : components)
				component.addToEngine(engine);

			for(GameObject child : children)
				child.setEngine(engine);
		}
	}
}
