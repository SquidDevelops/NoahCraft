package net.TeSqGr.NoahCraft.Rendering;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private int tID;
    public int size;

    public Texture(String file) {
        try {
            PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(file));

            size = decoder.getWidth();

            ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();


            tID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, tID);


            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);


            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

            glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glGenerateMipmap(GL_TEXTURE_2D);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getID(){
        return tID;
    }

    public void dispose(){
        glDeleteTextures(tID);
    }
}
