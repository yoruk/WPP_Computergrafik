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

public class TranslationNode extends Node {
	/**
	 * Translation used in this node. The Vector3 indicates translations
	 * of child nodes
	 */
	private final Vector3 translation = new Vector3();
	
	public TranslationNode(Vector3 translation) {
		this.translation.copy(translation);
	}

	@Override
	public void drawGl(GL2 gl) {
		// Remember current state of the render system
		gl.glPushMatrix();

		// Apply scaling
		gl.glTranslatef((float)translation.get(0), (float)translation.get(1), (float)translation.get(2));

		// Draw all children
		for(int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}

		// Restore original state
		gl.glPopMatrix();
	}

}
