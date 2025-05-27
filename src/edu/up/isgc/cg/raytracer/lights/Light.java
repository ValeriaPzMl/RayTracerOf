package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Ray;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Material;
import edu.up.isgc.cg.raytracer.objects.Object3D;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Abstract base class representing a light source in the scene.
 * Inherits position and material properties from Object3D.
 * Provides a base for different types of light sources such as point lights.
 */
public abstract class Light extends Object3D {
    private double intensity;
    /**
     * Constructs a Light object with a given position, material, and intensity.
     *
     * @param position The position of the light in the scene.
     * @param material The material properties (typically used for light color).
     * @param intensity The intensity or brightness of the light.
     */
    public Light(Vector3D position, Material material, double intensity) {
        super(position, material);
        setIntensity(intensity);
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
    /**
     * Abstract method that calculates the dot product between the surface normal
     * and the light direction at a given intersection point.
     *
     * @param intersection The intersection point to compute lighting for.
     * @return A value between 0.0 and 1.0 representing the cosine of the angle between normal and light vector.
     */
    public abstract double getNDotL(Intersection intersection);

    @Override/**
     * Overrides the base intersection behavior to always return no intersection.
     * Lights do not obstruct rays in this raytracer.
     *
     * @param ray The ray being tested for intersection.
     * @return An Intersection with negative distance, indicating no collision.
     */
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
}
