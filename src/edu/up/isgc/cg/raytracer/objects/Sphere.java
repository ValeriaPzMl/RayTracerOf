package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;

/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a 3D sphere object that can interact with rays in a raytracer.
 * Inherits position and material properties from Object3D.
 * Provides functionality to compute ray-sphere intersections.
 */
public class Sphere extends Object3D{
    private double radius;
    /**
     * Constructs a Sphere with a given position, radius, and material.
     *
     * @param position The center position of the sphere.
     * @param radius The radius of the sphere.
     * @param material The material of the sphere, used for rendering properties.
     */
    public Sphere(Vector3D position, double radius, Material material) {
        super(position, material);
        setRadius(radius);
    }
    /**
     * Returns the radius of the sphere.
     *
     * @return The radius as a double.
     */
    public double getRadius() {
        return radius;
    }
    /**
     * Sets the radius of the sphere.
     *
     * @param radius The new radius value.
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }
    /**
     * Computes the intersection of the sphere with a given ray.
     * If the ray intersects the sphere, returns an Intersection object containing
     * the point of contact, the normal at the intersection, and the distance from
     * the ray origin. Returns null if there is no intersection.
     *
     * @param ray The ray to test against the sphere.
     * @return An Intersection object if the ray hits the sphere; otherwise, null.
     */
    @Override
    public Intersection getIntersection(Ray ray) {
        Vector3D L = Vector3D.substract(getPosition(),ray.getOrigin());
        double tca = Vector3D.dotProduct(L, ray.getDirection());
        double L2 = Math.pow(Vector3D.magnitude(L), 2);
        double d2 = L2 - Math.pow(tca, 2);
        if(d2 >= 0){
            double d = Math.sqrt(d2);
            double t0 = tca - Math.sqrt(Math.pow(getRadius(), 2) - Math.pow(d, 2));
            double t1 = tca + Math.sqrt(Math.pow(getRadius(), 2) - Math.pow(d, 2));

            double distance = Math.min(t0, t1);
            Vector3D position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
            Vector3D normal = Vector3D.normalize(Vector3D.substract(position, getPosition()));
            return new Intersection(position, distance, normal, this);
        }

        return null;
    }
}
