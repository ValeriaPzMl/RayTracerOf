package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.DirectionalLight;
import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.lights.PointLight;
import edu.up.isgc.cg.raytracer.objects.*;
import edu.up.isgc.cg.raytracer.tools.OBJReader;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//scene01.setCamera(new Camera(new Vector3D(0, 0, -4), 82, 45,4096, 2160, 0.6, 50.0));

/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * The RayTracing class implements a basic ray tracing engine capable of rendering
 * scenes with spheres, including support for reflection, refraction, shadows, and diffuse lighting.
 * It utilizes multithreading for performance optimization.
 */
public class Raytracer {
    /**
     * Entry point of the program. Configures a sample scene with various materials,
     * lights, and 3D models. Then it renders the scene to an image file using ray tracing.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene01 = new Scene();
        Material light = new Material(Color.white);
        Material light01 = new Material(new Color(209, 230, 123));
        Material light02 = new Material(new Color(93, 188, 5));
        Material light03 = new Material(new Color(140, 194, 239));
        Material light04 = new Material(new Color(226, 239, 110));
        Material light05 = new Material(new Color(123, 230, 137));
        Material light06 = new Material(new Color(84, 192, 243));

        Material dark = new Material(Color.RED);
        Material setroung = new Material(Color.GREEN, 0, 0.5, 0.6, 0.3, 32.0);
        Material polishedMetal = new Material(new Color(39, 85, 130), 1.0, 0.6, 1.0, 0.9, 256.0);
        Material cutGlass = new Material(new Color(113, 207, 237, 150), 0.15, 0.85, 1.5, 0.95, 512.0);
        Material mattePlastic = new Material(Color.BLUE, 0.05, 0.0, 1.0, 0.3, 32.0);
        Material handMat = new Material(new Color(144, 185, 232, 200), 0.1, 0.7, 1.33, 0.8, 128.0);
        Material Glass = new Material(new Color(171, 234, 234, 150), 0.15, 0.90, 1.5, 0.95, 128.0);
        Material bowGold = new Material(new Color(255, 223, 3), 0.90, 0.0, 1.0, 0.95, 2048.0);
        Material waterFoam = new Material(new Color(113, 172, 246, 200), 0.1, 0.7, 1.33, 0.8, 128.0);
        Material polishedGold = new Material(new Color(255, 215, 0), 0.85, 0.0, 1.0, 0.95, 1024.0);
        Material glacialIce = new Material(new Color(248, 246, 77), 0.5, 0.8, 1.31, 0.9, 1024.0);
        Material sciFiEnergy = new Material(new Color(0, 255, 150, 100), 0.4, 0.5, 2.0, 1.0, 2048.0);
        Material rubber = new Material(new Color(239, 239, 219), 0.02, 0.0, 1.0, 0.1, 8.0);
        Material glass = new Material(new Color(190, 255, 255, 150), 0.19, 0.92, 1.3, 0.95, 512.0);
        Material mirrorMaterial = new Material(new Color(48, 101, 120), 0.95, 0.0, 1.0, 1.0, 1024.0);
        Material skullMaterial = new Material(new Color(255, 255, 255), 0.95, 0.1, 1.0, 1.0, 1024.0);
        Material sciFiEnergy02 = new Material(new Color(202, 239, 36, 129), 0.95, 0.0, 1.0, 1.0, 2048.0);
        Material mirrorMaterial02 = new Material(new Color(130, 244, 236), 0.95, 0.0, 1.8, 0.8, 1024.0);
        Material perfectMirror = new Material(new Color(100, 100, 100),1.0,0.0,1.0,1.0,1024.0);
        Material sphereGlass = new Material(new Color(171, 234, 234, 150), 0.15, 0.90, 1.5, 0.95, 128.0);
        Material MirrorEx = new Material(new Color(100, 100, 100),1.0,0.0,1.0,1.0,1024.0);


        //Scene 1 check
        scene01.setGammaCorrection(1.6f);
        scene01.setCamera(new Camera(new Vector3D(0, 0, -3), 45, 82,2700, 5120, 0.6, 50.0));
        scene01.addLight(new PointLight(new Vector3D(0,-1,-1),light,1.3));
        scene01.addLight(new PointLight(new Vector3D(0,0,0),light,1.1));
        scene01.addObject(OBJReader.getModel3D("water.obj",new Vector3D(0,-3,1),waterFoam,13.0,0,45,0));
        scene01.addObject(OBJReader.getModel3D("Cube.obj",new Vector3D(0,-6.5,11),mirrorMaterial,15.0,0,0,0));
        scene01.addObject(OBJReader.getModel3D("Trident.obj",new Vector3D(0,1,1),cutGlass,10.0,0,0,0));
        scene01.addObject(OBJReader.getModel3D("seahorse.obj",new Vector3D(1,0,1),polishedGold,1.0,0,180,0));
        scene01.addObject(OBJReader.getModel3D("seahorse.obj",new Vector3D(-1,0,1),polishedGold,1.0,0,0,0));

        //Scene 2 check
        Scene scene02 = new Scene();
        scene02.setGammaCorrection(1.6f);
        scene02.setCamera(new Camera(new Vector3D(0, 0, -4), 82, 45,5120, 2700, 0.0, 50.0));
        scene02.addLight(new PointLight(new Vector3D(-1.1,-0.5,-1.0),light02,7.0));
        scene02.addLight(new PointLight(new Vector3D(-1.1,0.5,-1.0),light01,7));
        scene02.addLight(new PointLight(new Vector3D(1.1,-0.5,-1.0),light02,7));
        scene02.addLight(new PointLight(new Vector3D(1.1,0.5,-1.0),light01,7));
        scene02.addLight(new PointLight(new Vector3D(1.7,0.5,1.0),light,7));
        scene02.addLight(new PointLight(new Vector3D(-1.7,0.5,1.0),light,7));
        scene02.addLight(new PointLight(new Vector3D(0,1.0,2.7),light,7));
        scene02.addObject(OBJReader.getModel3D("Skull1.obj",new Vector3D(1.7,0.4,-0.5),skullMaterial,1.5,0,225,0));
        scene02.addObject(OBJReader.getModel3D("Skull1.obj",new Vector3D(-1.7,0.4,-0.5),skullMaterial,1.5,0,135,0));
        scene02.addObject(OBJReader.getModel3D("Skull1.obj",new Vector3D(0.0,0.95,2.7),sciFiEnergy,1.4,5,180,0));
        scene02.addObject(OBJReader.getModel3D("helmet01.obj",new Vector3D(0,-19.3,2.5),glass,1.0,0,180,0));


        //Scene 3 check
        Scene scene03 = new Scene();
        scene03.setCamera(new Camera(new Vector3D(0, 0, -4), 45, 82,2700, 5120, 0.0, 50.0));
        scene03.setGammaCorrection(1.7f);
        scene03.addObject(OBJReader.getModel3D("bowoflight.obj",new Vector3D(1,0,3),bowGold,4,90,180,0));
        scene03.addObject(OBJReader.getModel3D("bowoflight.obj",new Vector3D(-1,0,3),bowGold,4,90,0,0));
        scene03.addObject(OBJReader.getModel3D("Cube.obj",new Vector3D(0,-7.5,12),rubber,15.0,0,0,0));
        scene03.addObject(OBJReader.getModel3D("Cube.obj",new Vector3D(-1,-0.5,0),glass,1,0,0,0));
        scene03.addObject(OBJReader.getModel3D("Cube.obj",new Vector3D(1,-1.5,6),perfectMirror,3,0,30,0));
        scene03.addLight(new PointLight(new Vector3D(0,-2,0),light,0.5));
        scene03.addLight(new PointLight(new Vector3D(0,2,0),light,0.5));
        scene03.addLight(new PointLight(new Vector3D(0,0,-1),light04,0.7));

        /*Scene sceneRef = new Scene();
        sceneRef.setGammaCorrection(1.5f);
        sceneRef.setCamera(new Camera(new Vector3D(0, 0, -4), 82, 45,800, 400, 0.0, 50.0));
        sceneRef.addObject(OBJReader.getModel3D("Cube.obj",new Vector3D(0,-7.5,12),rubber,15.0,0,0,0));
        sceneRef.addLight(new PointLight(new Vector3D(-2,0,-2),light,0.7));
        sceneRef.addLight(new PointLight(new Vector3D(2,0,-2),light,0.7));
        sceneRef.addObject(OBJReader.getModel3D("Skull1.obj",new Vector3D(0,-0.5,3),sciFiEnergy,1.4,0,180,0));
        sceneRef.addObject(OBJReader.getModel3D("SmallTeapot.obj",new Vector3D(0,-0.5,-1),sphereGlass,1.0,0,180,0));



        BufferedImage image01 = raytrace(sceneRef);
        File outputImage01 = new File("image.png");
        try {
            ImageIO.write(image01, "png", outputImage01);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());*/

        //image 1
        BufferedImage image01 = raytrace(scene01);
        File outputImage01 = new File("image01.png");
        try {
            ImageIO.write(image01, "png", outputImage01);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());

        //image 2
        BufferedImage image02 = raytrace(scene02);
        File outputImage02 = new File("image02.png");
        try {
            ImageIO.write(image02, "png", outputImage02);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());

        //image 3
        BufferedImage image03 = raytrace(scene03);
        File outputImage03 = new File("image03.png");
        try {
            ImageIO.write(image03, "png", outputImage03);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());
    }

    /**
     * Renders the given scene using ray tracing with a default maximum recursion depth.
     * This method is a wrapper for the overloaded version with explicit depth.
     *
     * @param scene The scene to render.
     * @return A BufferedImage containing the rendered scene.
     */

    public static BufferedImage raytrace(Scene scene) {
        return raytrace(scene, 5); // 3 niveles de recursión por defecto
    }

    /**
     * Renders the given scene using ray tracing, allowing for custom recursion depth.
     * This method divides the image into rows and processes them in parallel using multithreading.
     * Supports gamma correction and recursive reflection/refraction effects.
     *
     * @param scene The scene to render.
     * @param maxDepth Maximum recursion depth for reflection and refraction.
     * @return A BufferedImage containing the rendered scene.
     */
    public static BufferedImage raytrace(Scene scene, int maxDepth) {
        Camera mainCamera = scene.getCamera();
        BufferedImage image = new BufferedImage(
                mainCamera.getResolutionWidth(),
                mainCamera.getResolutionHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        float gamma= scene.getGammaCorrection();
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        Vector3D[][] posRaytrace = mainCamera.calculatePositionsToRay();
        int height = posRaytrace.length;
        int width = posRaytrace[0].length;

        AtomicInteger completedRows = new AtomicInteger(0);
        int totalRows = height;
        int progressStep = Math.max(1, totalRows / 50);

        for (int i = 0; i < height; i++) {
            final int row = i;
            executor.submit(() -> {
                for (int j = 0; j < posRaytrace[row].length; j++) {
                    Vector3D rayDir = Vector3D.normalize(Vector3D.substract(
                            Vector3D.add(mainCamera.getPosition(), posRaytrace[row][j]),
                            mainCamera.getPosition()
                    ));
                    Ray primaryRay = new Ray(mainCamera.getPosition(), rayDir);
                    Color pixelColor = traceRay(scene, primaryRay, maxDepth);
                    Color corrected = applyGammaCorrection(pixelColor, gamma);

                    image.setRGB(row,j, corrected.getRGB());
                }

                int current = completedRows.incrementAndGet();
                if (current % progressStep == 0 || current == totalRows) {
                    double progress = (current * 100.0) / totalRows;
                    System.out.printf("Progress: %.1f%% (%d/%d row)%n", progress, current, totalRows);
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return image;
    }

    /**
     * Applies gamma correction to a given color.
     * Converts the color from linear space to gamma space to simulate realistic brightness.
     *
     * @param color The original linear color.
     * @param gamma The gamma correction value.
     * @return A new Color object with gamma correction applied.
     */
    private static Color applyGammaCorrection(Color color, double gamma) {
        double invGamma = 1.0 / gamma;
        double r = color.getRed() / 255.0;
        double g = color.getGreen() / 255.0;
        double b = color.getBlue() / 255.0;

        r = Math.pow(r, invGamma);
        g = Math.pow(g, invGamma);
        b = Math.pow(b, invGamma);

        int rInt = (int) (r * 255 + 0.5);
        int gInt = (int) (g * 255 + 0.5);
        int bInt = (int) (b * 255 + 0.5);

        rInt = Math.max(0, Math.min(255, rInt));
        gInt = Math.max(0, Math.min(255, gInt));
        bInt = Math.max(0, Math.min(255, bInt));

        return new Color(rInt, gInt, bInt);
    }

    /**
     * Recursively traces a ray through the scene to compute the color seen in its direction.
     * Handles diffuse shading, reflection, and refraction based on object materials.
     *
     * @param scene The scene to trace through.
     * @param ray The ray being cast.
     * @param depth The remaining recursion depth.
     * @return The resulting Color after lighting and material calculations.
     */
    private static Color traceRay(Scene scene, Ray ray, int depth) {
        if (depth <= 0) return Color.BLACK;

        Intersection closestIntersection = raycast(ray, scene.getObjects(), null,
                new double[]{scene.getCamera().getNearFarPlanes()[0], scene.getCamera().getNearFarPlanes()[1]});

        if (closestIntersection == null) return Color.BLACK;

        Material material = closestIntersection.getObject().getMaterial();
        Color diffuseColor = material.getDiffuseColor();
        Vector3D hitPoint = closestIntersection.getPosition();
        Vector3D normal = closestIntersection.getNormal();

        Color finalColor = calculateDiffuse(scene, closestIntersection, material);

        if (material.getReflectance() > 0) {
            Vector3D reflectionDir = Vector3D.reflect(Vector3D.normalize(ray.getDirection()), normal);
            Ray reflectionRay = new Ray(
                    Vector3D.add(hitPoint, Vector3D.scalarMultiplication(normal, 1e-5)),
                    reflectionDir
            );
            Color reflectionColor = traceRay(scene, reflectionRay, depth - 1);
            finalColor = blendColors(finalColor, reflectionColor, material.getReflectance());
        }

        if (material.getTransparency() > 0) {
            Vector3D refractionDir = Vector3D.refract(Vector3D.normalize(ray.getDirection()),normal, 1.0, material.getRefractiveIndex());

            if (refractionDir != null) {
                Ray refractionRay = new Ray(
                        Vector3D.add(hitPoint, Vector3D.scalarMultiplication(refractionDir, 1e-5)),
                        refractionDir
                );
                Color refractionColor = traceRay(scene, refractionRay, depth - 1);
                finalColor = blendColors(finalColor, refractionColor, material.getTransparency());
            }
        }

        return finalColor;
    }

    /**
     * Calculates the diffuse lighting component at a given intersection point.
     * It checks whether the point is in shadow with respect to each light in the scene,
     * and if not, it adds the light's contribution based on the Lambertian model.
     *
     * @param scene The scene containing the lights.
     * @param intersection The intersection point on an object surface.
     * @param material The material of the intersected object.
     * @return The resulting color from all diffuse light contributions.
     */
    private static Color calculateDiffuse(Scene scene, Intersection intersection, Material material) {
        Color diffuseColor = material.getDiffuseColor();
        Color result = Color.BLACK;

        for (Light light : scene.getLights()) {
            if (isShadowed(scene, intersection.getPosition(), intersection.getNormal(), light)) {
                continue;
            }

            double nDotL = light.getNDotL(intersection);
            Color lightColor = light.getMaterial().getDiffuseColor();
            double intensity = light.getIntensity() * nDotL;

            result = addColor(result, multiplyColor(diffuseColor, lightColor, intensity));
        }
        return result;
    }

    /**
     * Blends two colors based on a specified blend factor.
     *
     * @param base The base color.
     * @param overlay The overlay color to blend with the base.
     * @param factor A value between 0 and 1 indicating the blend ratio (0 = only base, 1 = only overlay).
     * @return A new Color representing the blend of the two input colors.
     */
    private static Color blendColors(Color base, Color overlay, double factor) {
        return new Color(
                (int) (base.getRed() * (1 - factor) + overlay.getRed() * factor),
                (int) (base.getGreen() * (1 - factor) + overlay.getGreen() * factor),
                (int) (base.getBlue() * (1 - factor) + overlay.getBlue() * factor)
        );
    }

    /**
     * Multiplies an object color with a light color and scales it by the light's intensity.
     * This simulates the modulation of surface color by incoming light.
     *
     * @param objectColor The color of the object surface.
     * @param lightColor The color of the light source.
     * @param intensity The intensity factor of the light.
     * @return A new Color representing the result of the modulation.
     */
    public static Color multiplyColor(Color objectColor, Color lightColor, double intensity) {
        double rObj = objectColor.getRed() / 255.0;
        double gObj = objectColor.getGreen() / 255.0;
        double bObj = objectColor.getBlue() / 255.0;

        double rLight = lightColor.getRed() / 255.0;
        double gLight = lightColor.getGreen() / 255.0;
        double bLight = lightColor.getBlue() / 255.0;

        double r = Math.clamp((rObj * rLight * intensity), 0.005, 1.0);
        double g = Math.clamp((gObj * gLight * intensity), 0.005, 1.0);
        double b = Math.clamp((bObj * bLight * intensity), 0.005, 1.0);

        return new Color(
                (int) (r * 255),
                (int) (g * 255),
                (int) (b * 255)
        );
    }

    /**
     * Adds two colors together with clamping to keep the result in the valid range.
     *
     * @param original The base color.
     * @param otherColor The color to add to the base color.
     * @return A new Color resulting from the addition of the two input colors.
     */
    public static Color addColor(Color original, Color otherColor) {
        float red = (float) Math.clamp((original.getRed() / 255.0) + (otherColor.getRed() / 255.0), 0.005, 1.0);
        float green = (float) Math.clamp((original.getGreen() / 255.0) + (otherColor.getGreen() / 255.0), 0.005, 1.0);
        float blue = (float) Math.clamp((original.getBlue() / 255.0) + (otherColor.getBlue() / 255.0), 0.005, 1.0);
        return new Color(red, green, blue);
    }

    /**
     * Casts a ray against a list of objects and returns the closest intersection, if any.
     * Can ignore the casting object itself and apply optional near/far clipping planes.
     *
     * @param ray The ray to cast.
     * @param objects The list of objects to test for intersections.
     * @param caster The object casting the ray (can be null to check all).
     * @param clippingPlanes Optional array of two values [near, far] to filter intersections by distance.
     * @return The closest valid Intersection, or null if no intersection occurs.
     */
    public static Intersection raycast(Ray ray, List<Object3D> objects, Object3D caster, double[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int i = 0; i < objects.size(); i++) {
            Object3D currObj = objects.get(i);
            if (caster == null || !currObj.equals(caster)) {
                Intersection intersection = currObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (distance >= clippingPlanes[0] && distance <= clippingPlanes[1]))) { // Modificado

                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }

    /**
     * Determines whether a point on a surface is in shadow with respect to a given light source.
     * Casts a shadow ray from the hit point toward the light and checks if any object blocks it.
     *
     * @param scene The scene containing all objects and lights.
     * @param hitPoint The point on the surface to test.
     * @param normal The surface normal at the hit point.
     * @param light The light source being tested for shadowing.
     * @return True if the point is in shadow with respect to the light; false otherwise.
     */
    public static boolean isShadowed(Scene scene, Vector3D hitPoint, Vector3D normal, Light light) {
        Vector3D lightDir;
        double distanceToLight = Double.POSITIVE_INFINITY;

        if (light instanceof PointLight) {
            PointLight pointLight = (PointLight) light;
            Vector3D toLight = Vector3D.substract(pointLight.getPosition(), hitPoint);
            distanceToLight = Vector3D.magnitude(toLight);
            lightDir = Vector3D.normalize(toLight);
        } else if (light instanceof DirectionalLight) {
            DirectionalLight dirLight = (DirectionalLight) light;
            lightDir = Vector3D.scalarMultiplication(dirLight.getDirection(), -1.0); // Invertir dirección
        } else{
            return false;
        }
        Vector3D shadowOrig = Vector3D.add(hitPoint, Vector3D.scalarMultiplication(normal, 1e-5));
        Ray shadowRay = new Ray(shadowOrig, lightDir);

        Intersection shadowIntersection = raycast(shadowRay, scene.getObjects(), null, new double[]{0.0, distanceToLight});

        return shadowIntersection != null;
    }
}
