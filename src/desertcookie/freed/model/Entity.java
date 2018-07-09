/*
 * Copyright 2018 Ruben Hahn
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *    and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 *    conditions and the following disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 *    endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package desertcookie.freed.model;


import org.joml.Matrix4f;
import org.joml.Vector3f;


public class Entity {
	
	
	private TexturedModel texturedModel;
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	
	
	public Entity( TexturedModel texturedModel,Vector3f position,Vector3f rotation,Vector3f scale ) {
		this.texturedModel = texturedModel;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	
	public TexturedModel getTexturedModel() {
		return texturedModel;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition( Vector3f position ) {
		this.position = position;
	}
	
	public void move( float dx,float dy,float dz ) {
		position.add( dx,dy,dz );
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation( Vector3f rotation ) {
		this.rotation = rotation;
	}
	
	public void rotate( float drx,float dry,float drz ) {
		rotation.add( drx,dry,drz );
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public void setScale( Vector3f scale ) {
		this.scale = scale;
	}
	
	public void resize( float dsx,float dsy,float dsz ) {
		scale.add( dsx,dsy,dsz );
	}
	
	public Matrix4f createTransformationMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.translate( position );
		matrix.rotate( (float)Math.toRadians( rotation.x ),1f,0f,0f );
		matrix.rotate( (float)Math.toRadians( rotation.y ),0f,1f,0f );
		matrix.rotate( (float)Math.toRadians( rotation.z ),0f,0f,1f );
		matrix.scale( scale );
		return matrix;
	}
	
	
}
