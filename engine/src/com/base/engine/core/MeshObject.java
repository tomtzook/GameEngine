package com.base.engine.core;

import com.base.engine.components.MeshRenderer;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;

public class MeshObject extends GameObject{

	public MeshObject(Mesh mesh, Material material){
		addComponent(new MeshRenderer(mesh, material));
	}
}
