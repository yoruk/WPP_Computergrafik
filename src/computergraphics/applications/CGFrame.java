/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * Lecture demo program.
 */
package computergraphics.applications;


import java.util.LinkedList;
import java.util.List;

import computergraphics.framework.AbstractCGFrame;
import computergraphics.math.Vector3;
import computergraphics.scenegraph.ColorNode;
import computergraphics.scenegraph.MovableObject;
import computergraphics.scenegraph.Node;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.ScaleNode;
import computergraphics.scenegraph.SingleTriangleNode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.TriangleMeshNode;
import computergraphics.scenegraph.VertexShaderNode;

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
	static short a=0;
	MovableObject moveObj1;
	MovableObject moveObj2;
	/**
	 * Constructor.
	 */
	public CGFrame(int timerInverval) {
		super(timerInverval);
		
		Node colorNode = new VertexShaderNode(new Vector3(0.25, 0.25, 0.75));
		//Node colorNode = new ColorNode(new Vector3(0.25, 0.25, 0.75));
		TriangleMeshNode area=new TriangleMeshNode("color_100.png", "heightField_100.png");
		
		List<Vector3> way1= new LinkedList<Vector3>();
		way1.add(new Vector3(0, 0, 0));
		way1.add(new Vector3(0.9,0,0));
		way1.add(new Vector3(0.9,0,0.9));
		way1.add(new Vector3(0.5,0,0.5));
		way1.add(new Vector3(0.5,0,1));
		way1.add(new Vector3(0,0,0.9));
		moveObj1= new MovableObject(way1,"cube.obj",new Vector3(0.05, 0.05, 0.05),area);
		
		List<Vector3> way2= new LinkedList<Vector3>();
		way2.add(new Vector3(0, 0, 0.0));
		way2.add(new Vector3(0.5,0,0));
		way2.add(new Vector3(0.1,0,0.9));
		way2.add(new Vector3(0.55,0,0.6));
		way2.add(new Vector3(0.5,0,1));
		way2.add(new Vector3(0.7,0,0.9));
		way2.add(new Vector3(0.1,0,0.1));
		moveObj2= new MovableObject(way2,"cube.obj",new Vector3(0.05, 0.05, 0.05),area);
		
		
		
		Node scaleNode = new ScaleNode(new Vector3(2,2,2));
		Node translationNode = new TranslationNode(new Vector3(-0.5f, 0, -0));//.5f		
		Node rotationNode = new RotationNode(45, new Vector3(1,0,0));
		
		
		
		/*
		getRoot().addChild(colorNode);
		colorNode.addChild(translationNode);
		translationNode.addChild(rotationNode);
		rotationNode.addChild(triangleMeshNode);*/
		
		getRoot().addChild(colorNode);
		colorNode.addChild(translationNode);
		translationNode.addChild(area);
		translationNode.addChild(moveObj1);
		translationNode.addChild(moveObj2);
		
		
		
		
		
	}

	/*
	 * (nicht-Javadoc)
	 * 
	 * @see computergrafik.framework.ComputergrafikFrame#timerTick()
	 */
	@Override
	protected void timerTick() {
		if(a>10){
			moveObj1.tick();
			moveObj2.tick();
		}			
		else
			a++;
	}

	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		CGFrame gc = new CGFrame(34); //14 Aktualisierungen pro sec
	}
}
