package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.tools.Barycentric;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a 3D model composed of triangles in a ray tracing scene.
 * This model can be translated in world space and supports ray intersection tests.
 * Each triangle can have its own set of normals for shading.
 *
 * Inherits position and material from Object3D.
 */
public class Model3D extends Object3D{
    private List<Triangle> triangles;
    /**
     * Constructs a Model3D object from an array of triangles, a position, and a material.
     * Translates the vertices of the triangles to world space using the position.
     *
     * @param position The position of the model in world coordinates.
     * @param triangles An array of Triangle objects that form the shape of the model.
     * @param material The material that defines the surface properties of the model.
     */
    public Model3D(Vector3D position, Triangle[] triangles, Material material) {
        super(position, material);
        setTriangles(triangles);
    }
    /**
     * Returns the list of triangles that compose this 3D model.
     *
     * @return A list of Triangle objects representing the geometry.
     */
    public List<Triangle> getTriangles() {
        return triangles;
    }
    /**
     * Sets the triangles of the 3D model and translates all unique vertices
     * by the model's position in world space.
     *
     * @param triangles An array of Triangle objects representing the geometry.
     */
    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<>();
        for(Triangle triangle : triangles){
            uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));
        }

        for(Vector3D vertex : uniqueVertices){
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }
        this.triangles = Arrays.asList(triangles);
    }

    /**
     * Calculates the closest intersection between a ray and any triangle in the model.
     * Interpolates the surface normal using barycentric coordinates if an intersection is found.
     *
     * @param ray The Ray to test for intersection with the model.
     * @return An Intersection object containing the hit data, or null if no intersection is found.
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D position = Vector3D.ZERO();
        Vector3D normal = Vector3D.ZERO();

        for(Triangle triangle : getTriangles()){
            Intersection intersection = triangle.getIntersection(ray);
            double intersectionDistance = intersection.getDistance();
            if(intersectionDistance > 0 &&
                    (intersectionDistance < distance || distance < 0)){
                distance = intersectionDistance;
                position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
                normal = Vector3D.ZERO();
                double[] uVw = Barycentric.CalculateBarycentricCoordinates(position, triangle);
                Vector3D[] normals = triangle.getNormals();
                for(int i = 0; i < uVw.length; i++){
                    normal = Vector3D.add(normal, Vector3D.scalarMultiplication(normals[i], uVw[i]));
                }
            }
        }

        if(distance == -1){
            return null;
        }

        return new Intersection(position, distance, normal, this);
    }
}
