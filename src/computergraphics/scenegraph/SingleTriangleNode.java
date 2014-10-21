package computergraphics.scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;


import computergraphics.math.Vector3;

/**
* Praktikum Computergrafik, WS 2014
* Gruppe: 
* Sebastian Schrade ,
* Michael Schmidt 
* Aufgabe: Aufgabenblatt 2, Aufgabe 3
*/

public class SingleTriangleNode extends Node {
	private Vector3 color=new Vector3();
	private int index=-1;
	
	
	public SingleTriangleNode() {
		this(new Vector3(0, 0, 0));
	}
	
	public SingleTriangleNode(Vector3 color) {
		this.color.copy(color);
	}
	
	@Override
	public void drawGl(GL2 gl) {
		gl.glColor3f((float) color.get(0),(float) color.get(1),(float) color.get(2));
		
		if(index==-1){
			//quelle : http://www.songho.ca/opengl/gl_displaylist.html
			index = gl.glGenLists(1);
			gl.glNewList(index,GL2.GL_COMPILE);
			gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(-1, 1, 0);
			gl.glVertex3f(-1, -1, 0);
			gl.glVertex3f(1, -1, 0);			
			gl.glVertex3f(1, 1, 0);
			gl.glEnd();
			gl.glEndList();
		}
		
		gl.glCallList(index);
		
		
		
		/*		
		gl.glBegin(GL.GL_TRIANGLES);
		
		gl.glVertex3f(-1, 1, 0);
		gl.glVertex3f(-1, -1, 0);
		gl.glVertex3f(1, -1, 0);
		
		gl.glVertex3f(1, 1, 0);
		gl.glVertex3f(-1, 1, 0);
		gl.glVertex3f(1, -1, 0);

		gl.glEnd();*/
		
		/*
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-1, 1, 0);
		gl.glVertex3f(-1, -1, 0);
		gl.glVertex3f(1, -1, 0);			
		gl.glVertex3f(1, 1, 0);
		gl.glEnd();
		*/
		
		

		
		
	}
}
