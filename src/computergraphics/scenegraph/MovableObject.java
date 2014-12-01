package computergraphics.scenegraph;

import java.util.List;

import javax.media.opengl.GL2;

import computergraphics.math.Vector3;


/**
* Praktikum Computergrafik, WS 2014
* Gruppe: 
* Sebastian Schrade ,
* Michael Schmidt 
* Aufgabe: Aufgabenblatt 5, Aufgabe 1
*/
public class MovableObject extends Node {
	
	private TranslationNode tranObj;
	private RotationNode rotObj;
	private ScaleNode scaleObj;
	private TriangleMeshNode geoObj;
	
	private List<Vector3> waypoints;
	private Vector3 curPos;
	private Vector3 nextPos;
	private float alpha;
	
	private TriangleMeshNode world;

	public MovableObject(List<Vector3> waypoints , String obj) {
		this(waypoints,obj,new Vector3(1, 1, 1));		
	}
	
	public MovableObject(List<Vector3> waypoints , String obj , Vector3 scale) {
		this(waypoints,obj,new Vector3(1, 1, 1),null);
	}
	
	public MovableObject(List<Vector3> waypoints , String obj , Vector3 scale , TriangleMeshNode world) {
		this.world=world;
		curPos=waypoints.remove(0);	
		nextPos=waypoints.get(0);
		waypoints.add(curPos);
		
		this.waypoints=waypoints;
		
		alpha=0;
		
		tranObj=new TranslationNode(new Vector3(curPos));
		rotObj=new RotationNode(0, new Vector3(0,0,0));
		scaleObj=new ScaleNode(scale);
		geoObj = new TriangleMeshNode(obj);
		
		
		this.addChild(tranObj);
		tranObj.addChild(rotObj);
		rotObj.addChild(scaleObj);
		scaleObj.addChild(geoObj);
		//tranObj.addChild(geoObj);
		
	}
	
	
	
	
	
	

	@Override
	public void drawGl(GL2 gl) {
		// Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}
	}
	
	public void tick(){
		Vector3 newPos;
		alpha+=0.005;
		if(alpha>=1){
			alpha=0;//-=1; //ggf = 0
			curPos=waypoints.remove(0);	
			nextPos=waypoints.get(0);
			waypoints.add(curPos);			
		}
		/*
		newPos=curPos.add(nextPos);
		newPos=newPos.multiply(alpha);
		newPos=curPos.subtract(newPos);*/
		
		newPos=curPos.multiply(1-alpha);
		newPos=newPos.add(nextPos.multiply(alpha));
		
		
		
		if(world!=null){
			newPos.set(1, world.getY(newPos).get(1)); //setze Höhe auf Höhe Mesh
			//System.out.println(newPos.get(1));
		}
			
		
		tranObj.setTranslation(newPos);
	}

}




























