/* Copyright (c) 2017, Ruben Hahn
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package desertcookie.freed.core;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;


public class Display {
	
	
	private long windowId;
	
	private String title;
	private int x, y;
	private int w, h;
	
	private int resizable;
	private long fullscreen;
	private int vSync;
	
	
	public Display( ) {
		windowId = 0;
		title = "";
		x = -1;
		y = -1;
		w = 1;
		h = 0;
		resizable = GLFW.GLFW_FALSE;
		fullscreen = 0;
		vSync = GLFW.GLFW_TRUE;
	}
	
	
	public long getWindowId( ) {
		return windowId;
	}
	
	public String getTitle( ) {
		return title;
	}
	
	public void setTitle( String title ) {
		this.title = title;
		if( windowId != 0 )
			GLFW.glfwSetWindowTitle( windowId,title );
	}
	
	public int getX( ) {
		return x;
	}
	
	public void setX( int x ) {
		this.x = x;
		if( windowId != 0 )
			GLFW.glfwSetWindowPos( windowId,x,y );
	}
	
	public int getY( ) {
		return y;
	}
	
	public void setY( int y ) {
		this.y = y;
		if( windowId != 0 )
			GLFW.glfwSetWindowPos( windowId,x,y );
	}
	
	public void setPos( int x,int y ) {
		this.x = x;
		this.y = y;
		if( windowId != 0 )
			GLFW.glfwSetWindowPos( windowId,x,y );
	}
	
	public int getW( ) {
		return w;
	}
	
	public void setW( int w ) {
		this.w = w;
		if( windowId != 0 )
			GLFW.glfwSetWindowSize( windowId,w,h );
	}
	
	public int getH( ) {
		return h;
	}
	
	public void setH( int h ) {
		this.h = h;
		if( windowId != 0 )
			GLFW.glfwSetWindowSize( windowId,w,h );
	}
	
	public void setSize( int w,int h ) {
		this.w = w;
		this.h = h;
		if( windowId != 0 )
			GLFW.glfwSetWindowSize( windowId,w,h );
	}
	
	public boolean getFullscreen( ) {
		return fullscreen == GLFW.GLFW_TRUE;
	}
	
	public void setFullscreen( boolean fullscreen ) {
		if( fullscreen )
			this.fullscreen = GLFW.glfwGetPrimaryMonitor( );
		else
			this.fullscreen = 0;
		if( windowId != 0 )
			createDisplay( 0 ); // EXPERIMENTAL
	}
	
	public boolean getVSync( ) {
		return vSync == GLFW.GLFW_TRUE;
	}
	
	public void setVSync( boolean vSync ) {
		if( vSync )
			this.vSync = GLFW.GLFW_TRUE;
		else
			this.vSync = GLFW.GLFW_FALSE;
		if( windowId != 0 )
			GLFW.glfwSwapInterval( this.vSync );
	}
	
	
	///////////////////////// INTERNAL METHODS /////////////////////////
	
	
	public void setVisible( boolean visible ) {
		if( windowId == 0 )
			return;
		
		if( visible )
			GLFW.glfwShowWindow( windowId );
		else
			GLFW.glfwHideWindow( windowId );
	}
	
	public boolean isClosed( ) {
		if( windowId == 0 )
			return true;
		return GLFW.glfwWindowShouldClose( windowId );
	}
	
	public void createDisplay( long sharedWindow ) {
		boolean initSuccessful = GLFW.glfwInit( );
		if( !initSuccessful )
			throw new IllegalStateException( "Failed to initialize display" );
		
		GLFW.glfwDefaultWindowHints( );
		GLFW.glfwWindowHint( GLFW.GLFW_VISIBLE,GLFW.GLFW_FALSE );
		GLFW.glfwWindowHint( GLFW.GLFW_RESIZABLE,resizable );
		GLFW.glfwWindowHint( GLFW.GLFW_CONTEXT_VERSION_MAJOR,3 );
		GLFW.glfwWindowHint( GLFW.GLFW_CONTEXT_VERSION_MINOR,3 );
		GLFW.glfwWindowHint( GLFW.GLFW_OPENGL_PROFILE,GLFW.GLFW_OPENGL_CORE_PROFILE );
		GLFW.glfwWindowHint( GLFW.GLFW_OPENGL_FORWARD_COMPAT,GLFW.GLFW_TRUE );
		
		if( x == -1 && y == -1 ) {
			GLFWVidMode vidMode = GLFW.glfwGetVideoMode( GLFW.glfwGetPrimaryMonitor( ) );
			this.x = ( vidMode.width( ) - w ) / 2;
			this.y = ( vidMode.height( ) - h ) / 2;
		}
		
		windowId = GLFW.glfwCreateWindow( w,h,title,fullscreen,sharedWindow );
		if( windowId == 0 )
			throw new IllegalStateException( "Failed to create display" );
		
		GLFW.glfwSetWindowPos( windowId,x,y );
		GLFW.glfwMakeContextCurrent( windowId );
		GLFW.glfwSwapInterval( vSync );
		GL.createCapabilities( );
		
		GL11.glClearColor( 1f,1f,1f,1f );
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA );
	}
	
	public void updateDisplay( ) {
		GLFW.glfwPollEvents( );
		GLFW.glfwSwapBuffers( windowId );
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
	}
	
	public void exit( ) {
		setVisible( false );
		GLFW.glfwDestroyWindow( windowId );
		GLFW.glfwTerminate( );
	}
	
	
}
