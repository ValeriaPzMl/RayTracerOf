package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a triangle defined by three vertices and optional vertex normals.
 * Provides functionality for computing surface normals and intersections with rays.
 */
public class Triangle implements IIntersectable {
    public static final double EPSILON = 0.0000000000001;
    private Vector3D[] vertices;
    private Vector3D[] normals;
    /**
     * Constructs a Triangle from three vertices with no normals.
     *
     * @param v0 First vertex of the triangle.
     * @param v1 Second vertex of the triangle.
     * @param v2 Third vertex of the triangle.
     */
    public Triangle(Vector3D v0, Vector3D v1, Vector3D v2) {
        setVertices(v0, v1, v2);
        setNormals(null);
    }
    /**
     * Constructs a Triangle from a vertex array and an optional normal array.
     * If the vertex array is not of size 3, a default triangle at the origin is created.
     *
     * @param vertices Array of three vertices.
     * @param normals Optional array of normals, one per vertex. Can be null.
     */
    public Triangle(Vector3D[] vertices, Vector3D[] normals) {
        if(vertices.length == 3){
            setVertices(vertices[0], vertices[1], vertices[2]);
        } else {
            setVertices(Vector3D.ZERO(),Vector3D.ZERO(),Vector3D.ZERO());
        }
        setNormals(normals);
    }
    /**
     * Returns the vertices of the triangle.
     *
     * @return An array of three Vector3D objects representing the triangle's vertices.
     */
    public Vector3D[] getVertices() {
        return vertices;
    }

    private void setVertices(Vector3D[] vertices) {
        this.vertices = vertices;
    }
    /**
     * Sets the vertices of the triangle using three individual Vector3D values.
     *
     * @param v0 First vertex.
     * @param v1 Second vertex.
     * @param v2 Third vertex.
     */
    public void setVertices(Vector3D v0, Vector3D v1, Vector3D v2) {
        setVertices(new Vector3D[]{v0, v1, v2});
    }
    /**
     * Calculates and returns the averaged surface normal of the triangle.
     * If vertex normals are defined, the average of those is used. Otherwise,
     * it computes the geometric normal from the cross product of the triangle's edges.
     *
     * @return A normalized Vector3D representing the triangle's surface normal.
     */
    public Vector3D getNormal(){
        Vector3D normal = Vector3D.ZERO();
        Vector3D[] normals = this.normals;

        if(normals ==null) {
            Vector3D[] vertices = getVertices();
            Vector3D v = Vector3D.substract(vertices[1], vertices[0]);
            Vector3D w = Vector3D.substract(vertices[0], vertices[2]);
            normal = Vector3D.normalize(Vector3D.crossProduct(v, w));
        } else{
            for(int i = 0; i < normals.length; i++){
                normal.setX(normal.getX() + normals[i].getX());
                normal.setY(normal.getY() + normals[i].getY());
                normal.setZ(normal.getZ() + normals[i].getZ());
            }
            normal.setX(normal.getX() / normals.length);
            normal.setY(normal.getY() / normals.length);
            normal.setZ(normal.getZ() / normals.length);
        }
        return normal;
    }
    /**
     * Returns the per-vertex normals of the triangle.
     * If normals are not defined, they are computed based on the surface normal.
     *
     * @return An array of Vector3D normals corresponding to each vertex.
     */
    public Vector3D[] getNormals() {
        if(normals == null) {
            Vector3D normal = getNormal();
            setNormals(new Vector3D[]{normal, normal, normal});
        }
        return normals;
    }
    /**
     * Sets the normals of the triangle.
     *
     * @param normals An array of three Vector3D objects representing normals.
     */
    public void setNormals(Vector3D[] normals) {
        this.normals = normals;
    }
    /**
     * Sets the normals of the triangle using three individual Vector3D values.
     *
     * @param vn0 Normal at first vertex.
     * @param vn1 Normal at second vertex.
     * @param vn2 Normal at third vertex.
     */
    public void setNormals(Vector3D vn0, Vector3D vn1, Vector3D vn2) {
        setNormals(new Vector3D[]{vn0, vn1, vn2});
    }
    /**
     * Computes the intersection of a ray with the triangle using the Möller–Trumbore algorithm.
     *
     * @param ray The Ray to test against the triangle.
     * @return An Intersection object containing the intersection distance and default values
     *         for position and normal if there is a hit; otherwise, distance is -1.
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        Intersection intersection = new Intersection(null, -1, null, null);

        Vector3D[] vert = getVertices();
        Vector3D v2v0 = Vector3D.substract(vert[2], vert[0]);
        Vector3D v1v0 = Vector3D.substract(vert[1], vert[0]);
        Vector3D vectorP = Vector3D.crossProduct(ray.getDirection(), v1v0);
        double det = Vector3D.dotProduct(v2v0, vectorP);
        double invDet = 1.0 / det;
        Vector3D vectorT = Vector3D.substract(ray.getOrigin(), vert[0]);
        double u = invDet * Vector3D.dotProduct(vectorT, vectorP);

        if (!(u < 0 || u > 1)) {
            Vector3D vectorQ = Vector3D.crossProduct(vectorT, v2v0);
            double v = invDet * Vector3D.dotProduct(ray.getDirection(), vectorQ);
            if (!(v < 0 || (u + v) > (1.0 + EPSILON))) {
                double t = invDet * Vector3D.dotProduct(vectorQ, v1v0);
                intersection.setDistance(t);
            }
        }

        return intersection;
    }
}
