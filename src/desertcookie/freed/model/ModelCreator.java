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

package desertcookie.freed.model;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;


public class ModelCreator {
	
	
	private static ArrayList<Integer> vaoIdList = new ArrayList<>( );
	private static ArrayList<Integer> vboIdList = new ArrayList<>( );
	
	
	public ModelCreator( ) { }
	
	
	public Model createModel( float[] vertices,int[] indices,float[] colors ) {
		int vaoId = createVao( );
		GL30.glBindVertexArray( vaoId );
		
		bindIndices( indices );
		storeDataInAttributeList( 0,3,vertices ); // vertex positions
		storeDataInAttributeList( 1,4,colors ); // per vertex color data
		
		GL30.glBindVertexArray( 0 );
		return new Model( vaoId,indices.length );
	}
	
	
	private void bindIndices( int[] indices ) {
		int vboId = createVbo( );
		GL15.glBindBuffer( GL15.GL_ELEMENT_ARRAY_BUFFER,vboId );
		
		IntBuffer buffer = BufferUtils.createIntBuffer( indices.length );
		buffer.put( indices );
		buffer.flip( );
		GL15.glBufferData( GL15.GL_ELEMENT_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW );
	}
	
	private void storeDataInAttributeList( int attributeNr,int size,float[] data ) {
		int vboId = createVbo( );
		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER,vboId );
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer( data.length );
		buffer.put( data );
		buffer.flip( );
		GL15.glBufferData( GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW );
		GL20.glVertexAttribPointer( attributeNr,size,GL11.GL_FLOAT,false,0,0 );
		
		GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER,0 );
	}
	
	private int createVao( ) {
		int vaoId = GL30.glGenVertexArrays( );
		vaoIdList.add( vaoId );
		return vaoId;
	}
	
	private int createVbo( ) {
		int vboId = GL15.glGenBuffers( );
		vboIdList.add( vboId );
		return vboId;
	}
	
	
	public void exit( ) {
		for( Integer id : vaoIdList )
			GL30.glDeleteVertexArrays( id );
		for( Integer id : vboIdList )
			GL15.glDeleteBuffers( id );
	}
	
	
}
