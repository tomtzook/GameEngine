package com.base.engine.rendering;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;

import com.base.engine.math.Vector2f;

import javafx.scene.image.WritableImage;

public class Window {
	private Window(){}
	
	private static final int TEX_ROW_ALIGNMENT = 16 * 4;
	
	private static Pbuffer pbuffer;
	private static PixelFormat format;
	private static ByteBuffer buffer;
	private static WritableImage image;
	private static int stride;
	
	public static void createWindow(int width, int height, String title){
		Display.setTitle(title);
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	public static void createBufferDisplay(int width, int height) throws LWJGLException{
		format = new PixelFormat();
		pbuffer = new Pbuffer(width, height, format, null, null, new ContextAttribs().withDebug(true));
		pbuffer.makeCurrent();
		buffer = BufferUtils.createByteBuffer(width*height*3);
	    image = new WritableImage(width, height);
	    stride = getStride(width, TEX_ROW_ALIGNMENT);
	}
	@SuppressWarnings("unchecked")
	public static void render(){
		Display.update();
		if(pbuffer != null){
			GL11.glReadPixels(0, 0, pbuffer.getWidth(), pbuffer.getHeight(), GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
			image.getPixelWriter().setPixels(0, 0, pbuffer.getWidth(), pbuffer.getHeight(), image.getPixelWriter().getPixelFormat(), 
					buffer, stride);
		}
	}
	public static void dispose(){
		if(pbuffer != null) pbuffer.destroy();
		Display.destroy();
	}
	public static boolean isCloseRequested(){
		if(pbuffer != null) return false;
		return Display.isCloseRequested();
	}
	public static int getWidth(){
		if(pbuffer != null) return pbuffer.getWidth();
		return Display.getDisplayMode().getWidth();
	}
	public static int getHeight(){
		if(pbuffer != null) return pbuffer.getHeight();
		return Display.getDisplayMode().getHeight();
	}
	public static String getTitle(){
		return Display.getTitle();
	}
	public Vector2f getCenter(){
		return new Vector2f(getWidth()/2, getHeight()/2);
	}
	
	public static Pbuffer getBuffer(){
		return pbuffer;
	}
	public static WritableImage getWritableImage(){
		return image;
	}
	
	private static int getStride(final int width, final int aligment) {
		int stride = width * 4;

		if ( (stride & (aligment - 1)) != 0 )
			stride += aligment - (stride & (aligment - 1));

		return stride;
	}
}
