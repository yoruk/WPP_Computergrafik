/**
 * 
 */
package computergraphics.datastructures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

import java.util.ArrayList;
import java.util.List;

import computergraphics.math.Vector3;

/**
 * @author Sebastian
 *
 */
public class TriangleMesh_tex implements ITriangleMesh {
	
	private List<Vertex> vertexList;  
	private List<Vector3> texCoordList; 
	private List<Triangle> triangleList;  
	private String textName;
	
	/**
	 * 
	 */
	public TriangleMesh_tex() {
		vertexList=new ArrayList<Vertex>();
		triangleList=new ArrayList<Triangle>();
		texCoordList=new ArrayList<Vector3>();
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#addTriangle(computergraphics.datastructures.Triangle)
	 */
	@Override
	public void addTriangle(Triangle t) {
		triangleList.add(t);
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
	
	private List<Triangle> calcNormals(List<Triangle> tList) {
		Vector3 normal;
		Vector3 a = new Vector3();
		Vector3 b = new Vector3();
		Vector3 c = new Vector3();
		
		for(Triangle t : tList){
			a.copy(getVertex(t.getA()).getPosition());
			b.copy(getVertex(t.getB()).getPosition());
			c.copy(getVertex(t.getC()).getPosition());
			
			a=a.subtract(c);
			b=b.subtract(c);
			
			normal=a.cross(b);
			normal.normalize();
			
			t.setNormal(normal);
		}
		
		return tList;
	}
	
	@Override
	public void calcNormals(){
		triangleList=calcNormals(triangleList);
	}
	
	
	static public ITriangleMesh genrateMesh(int x, int z){
		double deltaX = 1.0/(x-1);
		double deltaZ = 1.0/(z-1);
		
		ITriangleMesh mesh=new TriangleMesh_tex();	
		
		for(int iy=0;iy<z;iy++){
			for(int ix=0;ix<x;ix++){
				mesh.addVertex(new Vertex(new Vector3(deltaX*ix,0.0,deltaZ*iy)));
			}
		}
		
		for(int iy=0;iy<z-1;iy++){
			for(int ix=0;ix<x-1;ix++){
				mesh.addTriangle(new Triangle(ix+iy*x, ix+1+iy*x, ix+(iy+1)*x));
				mesh.addTriangle(new Triangle( ix+1+iy*x,ix+(iy+1)*x ,ix+1+(iy+1)*x  ));
			}
		}
		System.out.println("Mesh angelegt");
		return mesh;
	}
	
	static public ITriangleMesh picToTriangelMesh(String datName){
		BufferedImage bild=null;
		ITriangleMesh mesh;
		int x,z;
		
		try {
			bild = ImageIO.read(new File(datName));
		} catch (IOException e) {			
			e.printStackTrace();
			System.exit(-1);
		}
		
		x=bild.getWidth();
		z=bild.getHeight();
		mesh=TriangleMesh_tex.genrateMesh(x, z);

		return mesh;		
	}
	
	static public ITriangleMesh colorMesh(ITriangleMesh mesh , String datName) {
		BufferedImage bild = null;
		Vertex tmpVertex;
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
		
		for(int iz=0;iz<z;iz++){
			for(int ix=0;ix<x;ix++){
				farbe = new Color(bild.getRGB(ix,iz));
				tmpVertex = mesh.getVertex(ix+iz*x);
				tmpVertex.setColor(new Vector3(farbe.getRed()/255.0, farbe.getGreen()/255.0,farbe.getBlue()/255.0));
			}
		}
		
		return mesh;	
	}
	
	static public ITriangleMesh terraformMesh(ITriangleMesh mesh, float scaleFactor, String datName) {
		BufferedImage bild=null;
		Vertex tmpVertex;
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
		
		for(int iz=0;iz<z;iz++){
			for(int ix=0;ix<x;ix++){
				tmpVertex = mesh.getVertex(ix+iz*x);
				farbe = new Color(bild.getRGB(ix,iz));
				tmpVertex.getPosition().set(1, (farbe.getRed()/255.0) * scaleFactor);
			}
		}
		
		return mesh;	
	}

	static public ITriangleMesh prozTerraformMesh(ITriangleMesh mesh, int x, int z) {
		Vertex tmpVertex;
		double xp,yp,zp;		
		
		for(int iz=0;iz<z;iz++){
			for(int ix=0;ix<x;ix++){
				tmpVertex = mesh.getVertex(ix+iz*x);
				xp=tmpVertex.getPosition().get(0);
				zp=tmpVertex.getPosition().get(2);
				tmpVertex.getPosition().set(1,(double)(xp-0.5)*(xp-0.5)*2+(zp-0.5)*(zp-0.5)*2); //2x²+2z²=y
				yp=tmpVertex.getPosition().get(1);
				
				tmpVertex.setColor(new Vector3(xp, yp, zp));
			}
		}		
		return mesh;	
	}

	@Override
	public void setTextureFilename(String filename) {
		textName=filename;
		
	}

	@Override
	public String getTextureFilename() {
		return textName;
	}

	@Override
	public void addTextureCoordinate(Vector3 texCoord) {
		texCoordList.add(texCoord);
		
	}

	@Override
	public Vector3 getTextureCoordinate(int index) {
		return texCoordList.get(index);
	}
}
