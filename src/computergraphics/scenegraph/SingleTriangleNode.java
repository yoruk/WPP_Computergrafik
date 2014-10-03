/**
* Praktikum TIPR1, SS 2013
* Gruppe: Max Muster (max.muster@haw-hamburg.de),
* Mia Meister (mia.meister@haw-hamburg.de)
* Aufgabe: Aufgabenblatt 4, Aufgabe 1
* Verwendete Quellen: Wikipedia (Begriff: Kugel)
*/

package computergraphics.scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import computergraphics.math.Vector3;

public class SingleTriangleNode extends Node {
	private Vector3 color = new Vector3();
	private int index = -1;
	
	public SingleTriangleNode() {
		this(new Vector3(0, 0, 0));
	}
	
	public SingleTriangleNode(Vector3 color) {
		this.color.copy(color);
	}
	
	@Override
	public void drawGl(GL2 gl) {
		gl.glColor3f((float)color.get(0), (float)color.get(1), (float)color.get(2)); // aufg1 a)
		
		if(index==-1){
			//quelle : http://www.songho.ca/opengl/gl_displaylist.html
			index = gl.glGenLists(1);
			gl.glNewList(index,GL2.GL_COMPILE);
			gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(-0.5f, 0.5f, 0); // oben links
			gl.glVertex3f(-0.5f, -0.5f, 0); // unten links
			gl.glVertex3f(0.5f, -0.5f, 0); // unten rechts			
			gl.glVertex3f(0.5f, 0.5f, 0); // oben rechts
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
	
//	@Override
//	public void drawGl(GL2 gl) {
//		gl.glColor3f(0.75f, 0.25f, 0.25f);
//		gl.glBegin(GL.GL_TRIANGLES);
//		gl.glNormal3f(0, 0, 1);
//		gl.glColor3d(1.0, 0.0, 0.0);
//		gl.glVertex3f(-0.5f, -0.5f, 0);
//		gl.glNormal3f(0, 0, 1);
//		gl.glColor3d(0.0, 1.0, 0.0);
//		gl.glVertex3f(0.5f, -0.5f, 0);
//		gl.glNormal3f(0, 0, 1);
//		gl.glColor3d(0.0, 0.0, 1.0);
//		gl.glVertex3f(0, 0.5f, 0);
//		gl.glEnd();
//	}
}
