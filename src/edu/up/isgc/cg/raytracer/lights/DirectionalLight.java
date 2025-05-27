package edu.up.isgc.cg.raytracer.lights;

import edu.up.isgc.cg.raytracer.Intersection;
import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Material;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a directional light source in the scene.
 * Unlike point lights, directional lights have a direction but no specific position.
 * This type of light simulates sources like the sun, where rays are parallel.
 */
public class DirectionalLight extends Light {
    private Vector3D direction;
    /**
     * Constructs a DirectionalLight with a given direction, material, and intensity.
     * The position is set to the origin by default, since directional lights don't have a spatial position.
     *
     * @param direction The direction vector of the light rays.
     * @param material The material properties (used for the light's color).
     * @param intensity The intensity of the light.
     */
    public DirectionalLight(Vector3D direction, Material material, double intensity) {
        super(Vector3D.ZERO(), material, intensity);
        setDirection(direction);
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = Vector3D.normalize(direction);
    }

    /**
     * Calculates the cosine of the angle between the surface normal at an intersection point
     * and the (inverted) light direction.
     *
     * @param intersection The intersection at which to calculate lighting.
     * @return The dot product clamped to [0, 1], representing the diffuse lighting factor.
     */
    @Override
    public double getNDotL(Intersection intersection) {
        return Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
    }

}
