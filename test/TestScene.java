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


import desertcookie.freed.core.MasterRenderer;
import desertcookie.freed.core.ResourceLoader;
import desertcookie.freed.input.InputHandler;
import desertcookie.freed.model.Model;
import desertcookie.freed.model.ModelCreator;
import desertcookie.freed.scenes.DefaultSceneIDs;
import desertcookie.freed.scenes.GameScene;


public class TestScene extends GameScene {
	
	
	private Model model;
	
	
	public TestScene( ) {
		super( DefaultSceneIDs.Main_Menu );
	}
	
	
	@Override
	public void initialize( ResourceLoader loader ) {
		ModelCreator modelCreator = new ModelCreator( );
		
		float[] vertices = new float[] {
				-0.5f,0.5f,0f,
				-0.5f,-0.5f,0f,
				0.5f,-0.5f,0f,
				0.5f,0.5f,0f
		};
		int[] indices = new int[] {
				0,1,3,
				3,1,2
		};
		float[] colors = new float[] {
				1,0,0,1,
				1,0,0,0.1f,
				0,1,0,0.1f,
				0,1,0,1
		};
		model = modelCreator.createModel( vertices,indices,colors );
	}
	
	@Override
	public void update( double deltaTime,InputHandler input ) {
	
	}
	
	@Override
	public void render( MasterRenderer renderer ) {
		renderer.renderModel( model );
	}
	
	
}
