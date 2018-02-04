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


import desertcookie.freed.input.InputHandler;
import desertcookie.freed.scenes.GameSceneHandler;


public abstract class Game {
	
	
	private static Display display;
	private static GameSceneHandler gameSceneHandler;
	private static InputHandler inputHandler;
	private static ResourceLoader resourceLoader;
	
	private static boolean run;
	
	
	public Game( ) {
		display = new Display( );
		gameSceneHandler = new GameSceneHandler( );
		inputHandler = new InputHandler( );
	}
	
	
	public static Display getDisplay( ) {
		return display;
	}
	
	public static GameSceneHandler getGameSceneHandler( ) {
		return gameSceneHandler;
	}
	
	public static InputHandler getInputHandler( ) {
		return inputHandler;
	}
	
	public static void stopGame( ) {
		run = false;
	}
	
	public void start( ) {
		display.createDisplay( 0 );
		inputHandler.registerInputHandler( display.getWindowId( ) );
		resourceLoader = new ResourceLoader();
		gameSceneHandler.initializeRegisteredScenes(resourceLoader);
		Thread updateThread = new Thread( updateLoop() );
		updateThread.setDaemon( true );
		
		run = true;
		updateThread.start();
		display.setVisible( true );
		renderLoop();
	}
	
	public void exit( ) {
		gameSceneHandler.exit( );
		resourceLoader.exit();
		display.exit();
	}
	
	
	///////////////////////// INTERNAL METHODS /////////////////////////
	
	
	private Runnable updateLoop( ) {
		return ( ) -> {
			final long TIME_PER_TICK = (long) ( 1e9 / Settings.maxTPS );
			long lastTime, thisTime, elapsedTime, sleepTime;
			double deltaTime;
			lastTime = System.nanoTime( );
			
			while( run ) {
				thisTime = System.nanoTime( );
				elapsedTime = thisTime - lastTime;
				deltaTime = elapsedTime / 1e9;
				sleepTime = TIME_PER_TICK - elapsedTime;
				lastTime = thisTime;
				
				gameSceneHandler.updateActiveScene( deltaTime,inputHandler );
				sleep( sleepTime );
			}
		};
	}
	
	private void renderLoop( ) {
		MasterRenderer masterRenderer = new MasterRenderer( );
		
		while( run ) {
			gameSceneHandler.renderActiveScene( masterRenderer );
			display.updateDisplay( );
			
			run = !display.isClosed();
		}
		exit();
	}
	
	private void sleep( long time ) {
		if( time <= 0 )
			return;
		
		long millis = (long) ( time / 1e6 );
		int nanos = (int) ( time - millis * 1e6 );
		try {
			Thread.sleep( millis,nanos );
		} catch( InterruptedException e ) {
			System.err.println( "Error in game loops!" );
			e.printStackTrace( );
		}
	}
	
	
}
