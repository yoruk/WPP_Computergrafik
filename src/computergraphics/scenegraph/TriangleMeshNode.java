package computergraphics.scenegraph;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import computergraphics.datastructures.ITriangleMesh;
import computergraphics.datastructures.ObjIO;
import computergraphics.datastructures.Triangle;
import computergraphics.datastructures.TriangleMesh;
import computergraphics.datastructures.TriangleMesh_tex;
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

	private int index=-1;

	public TriangleMeshNode() {
		
		//mesh=TriangleMesh.genrateMesh(200, 200);
		

//		mesh = TriangleMesh.picToTriangelMesh("Full_Moon_Luc_Viatour_800.png");
//		mesh = TriangleMesh.colorMesh(mesh, "Full_Moon_Luc_Viatour_800.png");
//		mesh = TriangleMesh.terraformMesh(mesh, 1.0f, "Full_Moon_Luc_Viatour_800.png");
		
		
//		mesh = TriangleMesh.picToTriangelMesh("color_100.png");
//		mesh = TriangleMesh.colorMesh(mesh, "color_100.png");
//		mesh = TriangleMesh.terraformMesh(mesh, 0.2f, "heightField_100.png");
		
		mesh=TriangleMesh.genrateMesh(400, 400);
		mesh = TriangleMesh.prozTerraformMesh(mesh,400,400);
	}
	
	public TriangleMeshNode(String textur , String heightField){
		mesh = TriangleMesh.picToTriangelMesh(textur);
		mesh = TriangleMesh.colorMesh(mesh, textur);
		mesh.setTextureFilename(textur);
		mesh = TriangleMesh.terraformMesh(mesh, 0.2f, heightField);
	}
	
	public TriangleMeshNode(TriangleMesh mesh){
		this.mesh=mesh;
	}
	
	public TriangleMeshNode(String fileName){
		mesh=new TriangleMesh_tex();
		ObjIO objIO = new ObjIO();
		objIO.einlesen(fileName, mesh);
	}

	@Override
	public void drawGl(GL2 gl) {
		//gl.glColor3f((float) color.get(0),(float) color.get(1),(float) color.get(2));
		
		if(index==-1){
			int nr;		
			Triangle tria;
			Vector3 normal;
			Vertex a,b,c;
			Vector3 avt,bvt,cvt;
			Texture texture=null;
			
			if(mesh.getTextureFilename()!=null){
				File file = new File(mesh.getTextureFilename());	
				try {
					texture=TextureIO.newTexture(file,false);
				} catch (GLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				gl.glEnable(GL.GL_TEXTURE_2D);
			}
				
			nr=mesh.getNumberOfTriangles();	
			mesh.calcNormals();
			index = gl.glGenLists(1);//quelle : http://www.songho.ca/opengl/gl_displaylist.html
			
			if(texture!=null){
				//quelle : https://open.gl/textures
				//quelle : http://docs.gl/gl3/glTexParameter
				gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
			    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			    gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getTextureObject());
			}			
			
			
			gl.glNewList(index,GL2.GL_COMPILE);
			gl.glBegin(GL2.GL_TRIANGLES);
			
			
			for(int i=0;i<nr;i++){
				tria=mesh.getTriangle(i);
				
				a=mesh.getVertex(tria.getA());
				b=mesh.getVertex(tria.getB());
				c=mesh.getVertex(tria.getC());						
				
				avt=mesh.getTextureCoordinate(tria.getTextureCoordinate(0));
				bvt=mesh.getTextureCoordinate(tria.getTextureCoordinate(1));
				cvt=mesh.getTextureCoordinate(tria.getTextureCoordinate(2));
				
				normal=tria.getNormal();
				gl.glNormal3d(normal.get(0),normal.get(1),normal.get(2));
				
				if(texture!=null)
					gl.glTexCoord2d(avt.get(0), avt.get(1));
				gl.glColor3d(a.getColor().get(0),a.getColor().get(1),a.getColor().get(2));
				gl.glVertex3d(a.getPosition().get(0),a.getPosition().get(1),a.getPosition().get(2));
				
				if(texture!=null)
					gl.glTexCoord2d(bvt.get(0), bvt.get(1));
				gl.glColor3d(b.getColor().get(0),b.getColor().get(1),b.getColor().get(2));
				gl.glVertex3d(b.getPosition().get(0),b.getPosition().get(1),b.getPosition().get(2));
				
				if(texture!=null)
					gl.glTexCoord2d(cvt.get(0), cvt.get(1));
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
	
	public Vector3 getY2(Vector3 onPos){
		Vertex vert;
		Vector3 p , nearestP1=new Vector3();
		double dist, nerestDist1=Double.MAX_VALUE;
		
		for(int i=0;i<mesh.getNumberOfVertices();i++){
			vert=mesh.getVertex(i);
			p=vert.getPosition();
			dist=p.subtract(onPos).getNorm();
			
			if(dist<nerestDist1){
				nerestDist1=dist;
				nearestP1=p;
			}
			
		}
		onPos.set(1, nearestP1.get(1)*10);
		return onPos;

	}
	
	public Vector3 getY(Vector3 onPos){
		double xk=0,yk=0;
		double xg=Double.MAX_VALUE,yg=Double.MAX_VALUE;
		Vector3 p, nearestK =null,nearestG = null;
		
		double aktuellerX , aktuellerY;
		
		
		for(int i=0;i<mesh.getNumberOfVertices();i++){
			p=mesh.getVertex(i).getPosition();
			aktuellerX=p.get(0);
			aktuellerY=p.get(2);
			if(onPos.get(0)>= aktuellerX&& xk<=aktuellerX) 
				if((onPos.get(2)>=aktuellerY || onPos.get(2)>=1) && yk<=aktuellerY){
					xk=p.get(0);
					yk=p.get(2);
					nearestK=p;				
				}
			

			if((onPos.get(0)<=aktuellerX || onPos.get(0)>=1 ) && xg>=aktuellerX)
				if(onPos.get(2)<=aktuellerY && yg>=aktuellerY){
					xg=p.get(0);
					yg=p.get(2);
					nearestG=p;				
				}
			
		}			
		if(nearestG==null||nearestK==null){
			System.out.println("error");
		}
		
		onPos.set(1, (nearestK.get(1)+nearestG.get(1))/2);
		return onPos;

	}
	

}
