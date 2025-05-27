package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.objects.Object3D;
import edu.up.isgc.cg.raytracer.objects.Camera;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a 3D scene in the ray tracing engine, containing a camera, a list of objects,
 * and a list of light sources. It also supports gamma correction for color output adjustment.
 *
 * The scene is responsible for storing all renderable elements and light sources that
 * participate in the ray tracing process.
 */
public class Scene {
    private float gammaCorrection = 1.0f;
    private Camera camera;
    private List<Object3D> objects;
    private List<Light> lights;

    public Scene() {
        setObjects(new ArrayList<>());
        setLights(new ArrayList<>());
    }

    public float getGammaCorrection() {
        return gammaCorrection;
    }

    public void setGammaCorrection(float gammaCorrection) {
        this.gammaCorrection = gammaCorrection;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addObject(Object3D object){
        getObjects().add(object);
    }

    public List<Object3D> getObjects() {
        if(objects == null){
            objects = new ArrayList<>();
        }
        return objects;
    }

    public void setObjects(List<Object3D> objects) {
        this.objects = objects;
    }

    public List<Light> getLights() {
        if(lights == null){
            lights = new ArrayList<>();
        }
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public void addLight(Light light){
        getLights().add(light);
    }
}
