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

package desertcookie.freed.textures;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;


public class TextureLoader {
	
	
	private static ArrayList<Integer> textureIdList = new ArrayList<>(  );
	
	public TextureLoader() {}
	
	
	public Texture loadTexture(String filepath) {
		IntBuffer w = BufferUtils.createIntBuffer( 1 );
		IntBuffer h = BufferUtils.createIntBuffer( 1 );
		IntBuffer c = BufferUtils.createIntBuffer( 1 );
		
		ByteBuffer imageBuffer = null;
		try {
			imageBuffer = STBImage.stbi_load_from_memory( readImageFile(filepath),w,h,c,0 );
			if(imageBuffer==null)
				throw new IllegalStateException( "Couldn't load image file: "+filepath );
		} catch( IOException e ) {
			e.printStackTrace();
		} catch( URISyntaxException e ) {
			e.printStackTrace();
		}
		
		int width = w.get(0);
		int height = h.get( 0 );
		int comp = c.get( 0 );
		int textureId = GL11.glGenTextures();
		textureIdList.add( textureId );
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D,textureId );
		if(comp==3) {
			GL11.glTexImage2D( GL11.GL_TEXTURE_2D,0,GL11.GL_RGB,width,height,0,GL11.GL_RGB,GL11.GL_UNSIGNED_BYTE,imageBuffer );
		} else {
			GL11.glTexImage2D( GL11.GL_TEXTURE_2D,0,GL11.GL_RGBA,width,height,0,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE,imageBuffer );
			GL11.glBlendFunc( GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA );
		}
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_LINEAR );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_LINEAR );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT );
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_WRAP_T,GL11.GL_REPEAT );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D,0 );
		
		return new Texture( textureId,width,height );
	}
	
	
	private ByteBuffer readImageFile(String filepath) throws URISyntaxException, IOException {
		FileInputStream fis = new FileInputStream( TextureLoader.class.getResource( filepath ).toURI().getPath() );
		FileChannel fc = fis.getChannel();
		ByteBuffer buffer = BufferUtils.createByteBuffer( (int)(fc.size()+1) );
		
		while(fc.read( buffer )!=-1)
			;
		fis.close();
		fc.close();
		buffer.flip();
		return buffer;
	}
	
	
	public void exit() {
		for( Integer integer : textureIdList ) {
			GL11.glDeleteTextures( integer );
		}
	}
	
	
}
