/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergraphics.applications;


import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.TranslationNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 * 
 */
public class CGFrame extends AbstractCGFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4257130065274995543L;

	/**
	 * Constructor.
	 */
	public CGFrame(int timerInverval) {
		super(timerInverval);
		
		ColorNode colorNode = new ColorNode(new Vector3(0.25, 0.25, 0.75));
		ScaleNode scaleNode = new ScaleNode(new Vector3(2.0, 2.0, 2.0)); // 1.1b
		TranslationNode translationNode = new TranslationNode(new Vector3(1.0f, 1.0f, -1.0f)); // 1.2a
		SingleTriangleNode triangleNode = new SingleTriangleNode(new Vector3(0.0f, 1.0f, 0.0f));
		RotationNode rotationNode = new RotationNode(45, new Vector3(0.0f, 0.0f, 1.0f));
		
		getRoot().addChild(rotationNode);
		rotationNode.addChild(colorNode);
		colorNode.addChild(translationNode);
		translationNode.addChild(scaleNode);
		scaleNode.addChild(triangleNode);
	}

	/*
	 * (nicht-Javadoc)
	 * 
	 * @see computergrafik.framework.ComputergrafikFrame#timerTick()
	 */
	@Override
	protected void timerTick() {
		// System.out.println("Tick");
	}

	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		new CGFrame(1000);
	}
}
