/**
 * 
 */
package computergraphics.datastructures;

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
		vertexList=new LinkedList<Vertex>();
		triangleList=new ArrayList<Triangle>();
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#addTriangle(computergraphics.datastructures.Triangle)
	 */
	@Override
	public int addTriangle(Triangle t) {
		triangleList.add(t);
		return triangleList.lastIndexOf(t);
	}

	/* (non-Javadoc)
	 * @see computergraphics.datastructures.ITriangleMesh#addVertex(computergraphics.datastructures.Vertex)
	 */
	@Override
	public int addVertex(Vertex v) {
		vertexList.add(v);		
		return vertexList.lastIndexOf(v);//size-1 ist schneller aber unsicher
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

}
