package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents any object that can be intersected by a ray in a ray tracing context.
 * Implementing classes must define how they compute intersections with a given ray.
 */
public interface IIntersectable {
    /**
     * Computes the intersection of the object with a given ray.
     * Must be overrided
     *
     * @param ray The ray to test for intersection.
     * @return An Intersection object containing the details of the hit,
     *         or null if no intersection occurs.
     */
    public abstract Intersection getIntersection(Ray ray);
}
