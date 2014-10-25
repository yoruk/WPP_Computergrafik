/**
 * 
 */
package computergraphics.datastructures;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import computergraphics.math.Vector3;

/**
 * @author Sebastian
 *
 */
public class TriangleMesh implements ITriangleMesh {
	
	private List<Vertex> vertexList;  
	private List<Triangle> triangleList;  
	
	/**
	 * 
	 */
	public TriangleMesh() {
		vertexList=new ArrayList<Vertex>();
		triangleList=new ArrayList<Triangle>();
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#addTriangle(computergraphics.datastructures.Triangle)
	 */
	@Override
	public int addTriangle(Triangle t) {
		triangleList.add(t);
		return triangleList.size()-1;
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#addVertex(computergraphics.datastructures.Vertex)
	 */
	@Override
	public int addVertex(Vertex v) {
		vertexList.add(v);		
		return vertexList.size()-1;//lastIndexOf(v) ist schneller aber unsicher
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#getNumberOfTriangles()
	 */
	@Override
	public int getNumberOfTriangles() {		
		return triangleList.size();
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#getNumberOfVertices()
	 */
	@Override
	public int getNumberOfVertices() {
		return vertexList.size();
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#getTriangle(int)
	 */
	@Override
	public Triangle getTriangle(int index) {
		return triangleList.get(index);
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#getVertex(int)
	 */
	@Override
	public Vertex getVertex(int index) {
		return vertexList.get(index);
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#clear()
	 */
	@Override
	public void clear() {
		vertexList.clear();
		triangleList.clear();
	}
	
	private List<Triangle> calcNormals(List<Triangle> tList){
		LinkedList<Triangle> temp=new LinkedList<Triangle>(tList);
		Vector3 normal;
		Vector3 a=new Vector3();
		Vector3 b=new Vector3();
		Vector3 c=new Vector3();
		for(Triangle t : temp){
			a.copy(getVertex(t.getA()).getPosition());
			b.copy(getVertex(t.getB()).getPosition());
			c.copy(getVertex(t.getC()).getPosition());
			
			a=a.subtract(c);
			b=b.subtract(c);
			
			normal=a.cross(b);
			normal.normalize();
			
			t.setNormal(normal);
		}
		
		return temp;
	}
	
	@Override
	public void calcNormals(){
		triangleList=calcNormals(triangleList);
	}
	
	
	static public ITriangleMesh genrateMesh(int x, int z){
		double deltaX = 1.0/(x-1);
		double deltaZ = 1.0/(z-1);
		
		ITriangleMesh mesh=new TriangleMesh();	
		
		for(int iy=0;iy<z;iy++){
			for(int ix=0;ix<x;ix++){
				mesh.addVertex(new Vertex(new Vector3(deltaX*ix,0.0,deltaZ*iy)));
			}
		}
		
		for(int iy=0;iy<z-1;iy++){
			for(int ix=0;ix<x-1;ix++){
				mesh.addTriangle(new Triangle(ix+iy*x, ix+1+iy*x, ix+(iy+1)*x));
				//mesh.addTriangle(new Triangle( ix+1+iy*x,ix+(iy+1)*x ,ix+1+(iy+1)*x  ));
			}
		}/**/
		
		/*
		for(int i=0; i < x*y ;i++){
			if(((i==0 ) || (i%x!=0)) && (i<x*y-x)){
				mesh.addTriangle(new Triangle(i, i+1, i+x));
				System.out.println(i + " " + (i+1) +" " + (i+x));
			}
							
		}
		
		for(int i=1; i < x*y ;i++){
			if((i%x!=0) &&(i<x*y-x)){
				mesh.addTriangle(new Triangle(i, i+(x-1), i+x));
				System.out.println("U"+ i + " " + (i+1) +" " + (i+x));
			}
		}
		*/

		System.out.println("Mesh angelegt");
		return mesh;
	}
	
	static public ITriangleMesh picToTriangelMesh(String datName){
		BufferedImage bild=null;
		ITriangleMesh mesh;
		Color farbe;
		int x,z;
		
		try {
			bild = ImageIO.read(new File(datName));
		} catch (IOException e) {			
			e.printStackTrace();
			System.exit(-1);
		}
		
		x=bild.getWidth();
		z=bild.getHeight();
		mesh=TriangleMesh.genrateMesh(x, z);
		
		
		for(int iz=0;iz<z;iz++){
			for(int ix=0;ix<x;ix++){
				farbe = new Color(bild.getRGB(ix,iz));
				mesh.getVertex(ix+iz*x).setColor(new Vector3(farbe.getRed()/255.0, farbe.getGreen()/255.0,farbe.getBlue()/255.0));
			}
		}
		
		
		
		return mesh;		
	}
	

}
