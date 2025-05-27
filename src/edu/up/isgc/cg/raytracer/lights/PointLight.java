package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Material;

/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a point light source that emits light uniformly in all directions
 * from a specific position in 3D space.
 */
public class PointLight extends Light {
    /**
     * Constructs a PointLight with a specified position, material, and intensity.
     *
     * @param position The 3D position of the light source.
     * @param material The material properties (e.g., color) of the light.
     * @param intensity The intensity of the light.
     */    public PointLight(Vector3D position, Material material, double intensity) {
        super(position, material, intensity);
    }
    /**
     * Calculates the dot product between the surface normal and the direction to the light source.
     * This is used for diffuse lighting using Lambert's cosine law.
     *
     * @param intersection The intersection point on the object surface.
     * @return A value between 0.0 and 1.0 representing the diffuse factor.
     */
    @Override
    public double getNDotL(Intersection intersection) {
        return Math.max(
                Vector3D.dotProduct(intersection.getNormal(),
                        Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()))),
                0.0);
    }
}
