package edu.up.isgc.cg.raytracer;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a ray in 3D space with an origin and a direction.
 * Commonly used in ray tracing to determine intersections with objects.
 */
public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    /**
     * Constructs a Ray with a given origin and direction.
     *
     * @param origin The starting point of the ray.
     * @param direction The direction in which the ray travels.
     */
    public Ray(Vector3D origin, Vector3D direction) {
        setOrigin(origin);
        setDirection(direction);
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    public Vector3D getDirection() {
        return Vector3D.normalize(direction);
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
}
