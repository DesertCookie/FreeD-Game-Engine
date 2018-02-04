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

package desertcookie.freed.scenes;


import desertcookie.freed.core.MasterRenderer;
import desertcookie.freed.core.ResourceLoader;
import desertcookie.freed.input.InputHandler;

import java.util.HashMap;


public class GameSceneHandler {
	
	
	private HashMap<Enum<?>,GameScene> registeredScenes;
	private GameScene activeScene;
	
	
	public GameSceneHandler() {
		registeredScenes = new HashMap<>(  );
		
		GameScene splashScreen = new SplashScreen();
		registeredScenes.put( splashScreen.getSceneId(),splashScreen );
		splashScreen.enter();
		activeScene = splashScreen;
	}
	
	
	public void registerScene(GameScene scene) {
		Enum<?> sceneId = scene.getSceneId();
		if(registeredScenes.containsKey( sceneId ))
			throw new IllegalArgumentException( "There is already a scene registered with the ID " + sceneId );
			
		registeredScenes.put( sceneId,scene );
	}
	
	public void removeScene(Enum<?> sceneId) {
		if(!registeredScenes.containsKey( sceneId ))
			throw new IllegalArgumentException( "There is no scene registered with the ID "+sceneId );
		
		registeredScenes.remove( sceneId );
	}
	
	public void setScene(Enum<?> sceneId) {
		if(!registeredScenes.containsKey( sceneId ))
			throw new IllegalArgumentException( "There is no scene registered with the ID "+sceneId );
		
		GameScene oldScene = activeScene;
		GameScene newScene = registeredScenes.get( sceneId );
		activeScene = null;
		
		oldScene.leave();
		newScene.enter();
		activeScene = newScene;
	}
	
	
	///////////////////////// INTERNAL METHODS /////////////////////////
	
	
	public void initializeRegisteredScenes( ResourceLoader resourceLoader ) {
		for( GameScene scene : registeredScenes.values( ) ) {
			scene.initialize( resourceLoader );
		}
	}
	
	public void updateActiveScene( double deltaTime,InputHandler inputHandler) {
		if(activeScene!=null)
			activeScene.update( deltaTime,inputHandler );
	}
	
	public void renderActiveScene( MasterRenderer masterRenderer) {
		if(activeScene != null)
			activeScene.render( masterRenderer );
	}
	
	public void exit() {
		activeScene.leave();
		activeScene = null;
		for( GameScene scene : registeredScenes.values( ) ) {
			scene.exit();
		}
		registeredScenes.clear();
	}
	
	
}
