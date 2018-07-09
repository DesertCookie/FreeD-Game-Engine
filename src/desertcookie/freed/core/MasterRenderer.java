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

package desertcookie.freed.core;


import desertcookie.freed.model.Entity;
import desertcookie.freed.model.Model;
import desertcookie.freed.model.ModelRenderer;
import desertcookie.freed.model.TexturedModel;
import desertcookie.freed.shaders.StaticShader;


public class MasterRenderer {
	
	
	private StaticShader staticShader;
	private ModelRenderer modelRenderer;
	
	
	public MasterRenderer() {
		staticShader = new StaticShader();
		modelRenderer = new ModelRenderer();
	}
	
	
	public void renderModel( Model model ) {
		staticShader.startShader();
		modelRenderer.render( model );
		staticShader.stopShader();
	}
	
	public void renderModel( TexturedModel texturedModel ) {
		staticShader.startShader();
		modelRenderer.render( texturedModel );
		staticShader.stopShader();
	}
	
	public void renderEntity( Entity entity ) {
		staticShader.startShader();
		staticShader.loadTransformationMatrix( entity.createTransformationMatrix() );
		modelRenderer.render( entity.getTexturedModel() );
		staticShader.stopShader();
	}
	
	
}
