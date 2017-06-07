package com.base.engine.core;

import com.base.engine.physics.PhysicsEngine;
import com.base.engine.rendering.RenderingEngine;

public abstract class Game {

	private GameObject root;
	
	private GameObject getRootObject(){
		if(root == null)
			root = new GameObject();

		return root;
	}
	
	protected void input(float delta){
		getRootObject().inputAll(delta);
	}
	protected void update(float delta){
		getRootObject().updateAll(delta);
	}
	protected void updatePhysics(PhysicsEngine engine, float delta){
		engine.update(getRootObject(), delta);
	}
	protected void render(RenderingEngine engine){
		engine.render(getRootObject());
	}
	
	protected void addObject(GameObject obj){
		getRootObject().addChild(obj);
	}
	
	public void setEngine(CoreEngine engine) {
		getRootObject().setEngine(engine); 
	}
	
	protected void init(){}
}
