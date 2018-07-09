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


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;


public class ModelRenderer {
	
	
	public ModelRenderer() {}
	
	
	public void render( Model model ) {
		bindModel( model );
		renderModel( model );
		unbindModel( model );
	}
	
	public void render( TexturedModel texturedModel ) {
		bindTexturedModel( texturedModel );
		renderTexturedModel( texturedModel );
		unbindTexturedModel( texturedModel );
	}
	
	
	private void bindModel( Model model ) {
		model.bind();
		GL20.glEnableVertexAttribArray( 0 ); // vertex positions
		GL20.glEnableVertexAttribArray( 1 ); // per vertex color data
	}
	
	private void renderModel( Model model ) {
		GL11.glDrawElements( GL11.GL_TRIANGLES,model.getVertexCount(),GL11.GL_UNSIGNED_INT,0 );
	}
	
	private void unbindModel( Model model ) {
		GL20.glDisableVertexAttribArray( 0 );
		GL20.glDisableVertexAttribArray( 1 );
		model.unbind();
	}
	
	private void bindTexturedModel( TexturedModel texturedModel ) {
		texturedModel.bind();
		GL20.glEnableVertexAttribArray( 0 ); // vertex positions
		GL20.glEnableVertexAttribArray( 1 ); // per vertex color data
		GL20.glEnableVertexAttribArray( 2 ); // texture coordinates
		GL13.glActiveTexture( GL13.GL_TEXTURE0 );
		texturedModel.getTexture().bind();
	}
	
	private void renderTexturedModel( TexturedModel texturedModel ) {
		GL11.glDrawElements( GL11.GL_TRIANGLES,texturedModel.getVertexCount(),GL11.GL_UNSIGNED_INT,0 );
	}
	
	private void unbindTexturedModel( TexturedModel texturedModel ) {
		texturedModel.getTexture().unbind();
		GL20.glDisableVertexAttribArray( 0 );
		GL20.glDisableVertexAttribArray( 1 );
		GL20.glDisableVertexAttribArray( 2 );
		texturedModel.unbind();
	}
	
	
}
