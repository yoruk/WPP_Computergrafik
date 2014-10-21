package computergraphics.scenegraph;

import javax.media.opengl.GL2;

import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.Triangle;
import computergraphics.datastructures.TriangleMesh;
import computergraphics.datastructures.Vertex;
import computergraphics.math.Vector3;

/**
* Praktikum Computergrafik, WS 2014
* Gruppe: 
* Sebastian Schrade ,
* Michael Schmidt 
* Aufgabe: Aufgabenblatt 2, Aufgabe 3
*/

public class TriangleMeshNode extends Node {
	private ITriangleMesh mesh;
	private Vector3 color=new Vector3(0,0.5,0);
	private int index=-1;

	public TriangleMeshNode() {
		mesh=new TriangleMesh();		

		int p0,p1,p2,p3,p4;
		Triangle tria;
		
		p0=mesh.addVertex(new Vertex(new Vector3(0,0,0),new Vector3(),new Vector3(1,0,0)));
		p1=mesh.addVertex(new Vertex(new Vector3(1,0,0),new Vector3(),new Vector3(0,1,0)));
		p2=mesh.addVertex(new Vertex(new Vector3(0,0,1),new Vector3(),new Vector3(0,0,1)));
		p3=mesh.addVertex(new Vertex(new Vector3(0,1,0),new Vector3(),new Vector3(1,1,0)));
		tria=new Triangle(p0,p1,p2);
		mesh.addTriangle(tria);

		tria=new Triangle(p0,p1,p3);
		mesh.addTriangle(tria);
		
		tria=new Triangle(p1,p2,p3);
		mesh.addTriangle(tria);		
		
		tria=new Triangle(p0,p2,p3);
		mesh.addTriangle(tria);
	}

	@Override
	public void drawGl(GL2 gl) {
		gl.glColor3f((float) color.get(0),(float) color.get(1),(float) color.get(2));
		
		if(index==-1){
			int nr=mesh.getNumberOfTriangles();
			mesh.calcNormals();
			Triangle tria;
			Vector3 normal;
			Vertex a,b,c;
			index = gl.glGenLists(1);//quelle : http://www.songho.ca/opengl/gl_displaylist.html
			gl.glNewList(index,GL2.GL_COMPILE);
			gl.glBegin(GL2.GL_TRIANGLES);
			for(int i=0;i<nr;i++){
				tria=mesh.getTriangle(i);
				
				a=mesh.getVertex(tria.getA());
				b=mesh.getVertex(tria.getB());
				c=mesh.getVertex(tria.getC());
				
				normal=tria.getNormal();
				gl.glNormal3d(normal.get(0),normal.get(1),normal.get(2));
				
				gl.glColor3d(a.getColor().get(0),a.getColor().get(1),a.getColor().get(2));
				gl.glVertex3d(a.getPosition().get(0),a.getPosition().get(1),a.getPosition().get(2));
				
				gl.glColor3d(b.getColor().get(0),b.getColor().get(1),b.getColor().get(2));
				gl.glVertex3d(b.getPosition().get(0),b.getPosition().get(1),b.getPosition().get(2));
				
				gl.glColor3d(c.getColor().get(0),c.getColor().get(1),c.getColor().get(2));
				gl.glVertex3d(c.getPosition().get(0),c.getPosition().get(1),c.getPosition().get(2));
			}
			gl.glEnd();
			gl.glEndList();
		}
		
		gl.glCallList(index);
		
		// Draw all children
		for (int childIndex = 0; childIndex < getNumberOfChildren(); childIndex++) {
			getChildNode(childIndex).drawGl(gl);
		}
	}

}