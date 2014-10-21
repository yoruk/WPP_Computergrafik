package computergraphics.scenegraph;

import javax.media.opengl.GL2;

import computergraphics.math.Vector3;

/**
* Praktikum TIPR1, SS 2013
* Gruppe: Max Muster (max.muster@haw-hamburg.de),
* Mia Meister (mia.meister@haw-hamburg.de)
* Aufgabe: Aufgabenblatt 4, Aufgabe 1
* Verwendete Quellen: Wikipedia (Begriff: Kugel)
*/

public class RotationNode extends Node {

	private final float angle;
	private final Vector3 rotationAxis = new Vector3();
	public RotationNode(float angle , Vector3 rotationAxis) {
		this.angle=angle;
		this.rotationAxis.copy(rotationAxis);
	}

	@Override
	public void drawGl(GL2 gl) {
		// Remember current state of the render system
		gl.glPushMatrix();

		// Apply scaling
		gl.glRotatef(angle, (float) rotationAxis.get(0), (float) rotationAxis.get(1),	(float) rotationAxis.get(2));

		// Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}

		// Restore original state
		gl.glPopMatrix();

	}

}
