package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.objects.Object3D;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents the result of a ray-object intersection in 3D space.
 * Stores information about the intersection point, the surface normal at that point,
 * the distance from the ray origin, and the object that was hit.
 */
public class Intersection {

    private double distance;
    private Vector3D position;
    private Vector3D normal;
    private Object3D object;

    /**
     * Constructs an Intersection with the given properties.
     *
     * @param position The 3D point where the intersection occurred.
     * @param distance The distance from the ray origin to the intersection point.
     * @param normal The surface normal at the intersection point.
     * @param object The object that the ray intersected with.
     */
    public Intersection(Vector3D position, double distance, Vector3D normal, Object3D object) {
        setPosition(position);
        setDistance(distance);
        setNormal(normal);
        setObject(object);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    public Object3D getObject() {
        return object;
    }

    public void setObject(Object3D object) {
        this.object = object;
    }
}
