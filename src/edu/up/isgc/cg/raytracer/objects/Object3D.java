package edu.up.isgc.cg.raytracer.objects;

import edu.up.isgc.cg.raytracer.Vector3D;

/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents an abstract 3D object in the raytracer.
 * Stores common properties like position and material.
 * Classes extending Object3D must implement intersection logic.
 */
public abstract class Object3D implements IIntersectable{
    private Material material;
    private Vector3D position;
    /**
     * Constructs a 3D object with the specified position and material.
     *
     * @param position The position of the object in 3D space.
     * @param material The material properties of the object.
     */
    public Object3D(Vector3D position, Material material) {
        setPosition(position);
        setMaterial(material);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }
}
