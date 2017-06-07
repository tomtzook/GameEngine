package main.test;

import com.base.engine.components.DirectionalLight;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.CameraObject;
import com.base.engine.core.CoreEngine;
import com.base.engine.core.Cuboid;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;

public class Main extends Game{

	protected Main(){
		Mesh mesh = new Mesh("plane3.obj");
		Material material = new Material(new Texture("bricks2.jpg"), 1, 8,
				new Texture("bricks2_normal.png"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);
		Material bodyMaterial = new Material(new Texture("mesh_tex.png"), 1, 8,
				new Texture("mesh_tex.png"), new Texture("mesh_tex.png"), 0.04f, -1.0f);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPosition().set(0, 0, 0);

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(Quaternion.AXIS_Z, 0.4f);

		directionalLightObject.addComponent(directionalLight);

		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0, 0, 1)));

		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
				new Attenuation(0,0,0.1f), 0.7f);

		GameObject spotLightObject = new GameObject();
		spotLightObject.addComponent(spotLight);

		spotLightObject.getTransform().getPosition().set(5.0f, 0.0f, 5.0f);
		spotLightObject.getTransform().rotate(Quaternion.AXIS_Y, 90.0f);
		
		Cuboid cuboid = new Cuboid(2.0f, 2.0f, 2.0f, 50.0f, bodyMaterial);
		cuboid.applyForce(new Vector3f(10.0f, 0.0f, 0.0f), 
				          cuboid.getPhysicsObject().getCenter(), 
						  1.0f);
		addObject(cuboid);
		addObject(planeObject);
		//addObject(directionalLightObject);
		//addObject(pointLightObject);
		//addObject(spotLightObject);

		CameraObject o = new CameraObject(70.0f, 0.01f, 1000.0f)
				.setFreeLook(0.5f)
				.setFreeMove(10.0f);
		o.getTransform().getPosition().set(10.0f, 10.0f, 10.0f);
		o.getTransform().rotate(Quaternion.AXIS_Y, 90.0f);
		addObject(o);
	}
	
	public static void main(String[] args) {
		CoreEngine engine = CoreEngine.init("test", 640, 480, 60);
		engine.start(new Main());
	}
}
