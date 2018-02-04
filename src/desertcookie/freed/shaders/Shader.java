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

package desertcookie.freed.shaders;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public abstract class Shader {
	
	
	private int programId;
	private int vertexShaderId, fragmentShaderId;
	
	
	public Shader( String vertexShaderFile,String fragmentShaderFile ) {
		programId = GL20.glCreateProgram( );
		vertexShaderId = loadShader( vertexShaderFile,GL20.GL_VERTEX_SHADER );
		fragmentShaderId = loadShader( fragmentShaderFile,GL20.GL_FRAGMENT_SHADER );
		
		GL20.glAttachShader( programId,vertexShaderId );
		GL20.glAttachShader( programId,fragmentShaderId );
		bindAttributes( );
		GL20.glLinkProgram( programId );
		GL20.glValidateProgram( programId );
	}
	
	
	protected abstract void bindAttributes( );
	
	
	protected void bindAttributeLocation( int attributeNr,String variableName ) {
		GL20.glBindAttribLocation( programId,attributeNr,variableName );
	}
	
	
	public void startShader( ) {
		GL20.glUseProgram( programId );
	}
	
	public void stopShader( ) {
		GL20.glUseProgram( 0 );
	}
	
	
	private static int loadShader( String file,int type ) {
		InputStream in = Shader.class.getResourceAsStream( file );
		StringBuilder string = new StringBuilder( );
		try {
			BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
			String line;
			while( ( line = reader.readLine( ) ) != null )
				string.append( line + "//\n" );
			reader.close( );
		} catch( IOException e ) {
			if( type == GL20.GL_VERTEX_SHADER )
				System.err.println( "Couldn't load vertex shader file: " + file );
			else
				System.err.println( "Couldn't load fragment shader file: " + file );
			e.printStackTrace( );
		}
		
		int shaderId = GL20.glCreateShader( type );
		GL20.glShaderSource( shaderId,string );
		GL20.glCompileShader( shaderId );
		if( GL20.glGetShaderi( shaderId,GL20.GL_COMPILE_STATUS ) == GL11.GL_FALSE ) {
			if( type == GL20.GL_VERTEX_SHADER )
				System.err.println( "Failed to compile vertex shader: " + file );
			else
				System.err.println( "Failed to compile fragment shader: " + file );
			System.out.println( GL20.glGetShaderInfoLog( shaderId,250 ) );
			System.exit( -1 );
		}
		return shaderId;
	}
	
	
	public void exit( ) {
		stopShader( );
		GL20.glDetachShader( programId,vertexShaderId );
		GL20.glDetachShader( programId,fragmentShaderId );
		GL20.glDeleteShader( vertexShaderId );
		GL20.glDeleteShader( fragmentShaderId );
		GL20.glDeleteProgram( programId );
	}
	
	
}
