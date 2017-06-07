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

		for(GameObject child : children)
			child.inputAll(delta);
	}

	public void updateAll(float delta){
		update(delta);

		for(GameObject child : children)
			child.updateAll(delta);
	}
	public void updatePhysicsAll(PhysicsEngine engine, float delta){
		if(physicsObject != null)
			physicsObject.update(engine, delta);
		
		for(GameObject child : children)
			child.updatePhysicsAll(engine, delta);
	}
	public void renderAll(Shader shader, RenderingEngine renderingEngine){
		render(shader, renderingEngine);

		for(GameObject child : children)
			child.renderAll(shader, renderingEngine);
	}

	public void input(float delta){
		transform.update();

		for(GameComponent component : components)
			component.input(delta);
	}
	public void update(float delta){
		for(GameComponent component : components)
			component.update(delta);
	}
	public void render(Shader shader, RenderingEngine renderingEngine){
		for(GameComponent component : components)
			component.render(shader, renderingEngine);
	}
	
	public Transform getTransform(){
		return transform;
	}
	public ArrayList<GameObject> getAllAttached(){
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for(GameObject child : children)
			result.addAll(child.getAllAttached());

		result.add(this);
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
