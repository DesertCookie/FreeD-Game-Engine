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

package desertcookie.freed.input;


import org.lwjgl.glfw.GLFW;


public class InputHandler {
	
	
	private boolean[] keyboardKeys;
	private boolean[] mouseButtons;
	
	private double cursorX, cursorY;
	
	
	public InputHandler() {
		keyboardKeys = new boolean[386];
		mouseButtons = new boolean[8];
		cursorX = 0;
		cursorY = 0;
	}
	
	
	public boolean isKeyDown( int key ) {
		return keyboardKeys[key];
	}
	
	public boolean isButtonDown( int button ) {
		return mouseButtons[button];
	}
	
	public double getCursorX() {
		return cursorX;
	}
	
	public double getCursorY() {
		return cursorY;
	}
	
	
	///////////////////////// INTERNAL METHODS /////////////////////////
	
	
	public void registerInputHandler( long windowId ) {
		GLFW.glfwSetKeyCallback( windowId,( window,key,scancode,action,mods ) -> {
			if( key >= keyboardKeys.length ) {
				System.err.println( "Unidentifiable keyboard key pressed: "+key );
				return;
			}
			
			keyboardKeys[key] = action != GLFW.GLFW_RELEASE;
		} );
		GLFW.glfwSetMouseButtonCallback( windowId,( window,button,action,mods ) -> {
			if( button >= mouseButtons.length ) {
				System.err.println( "Unidentifiable mouse button pressed: "+button );
				return;
			}
			
			mouseButtons[button] = action != GLFW.GLFW_RELEASE;
		} );
		GLFW.glfwSetCursorPosCallback( windowId,( window,xpos,ypos ) -> {
			cursorX = xpos;
			cursorY = ypos;
		} );
	}
	
	
}
