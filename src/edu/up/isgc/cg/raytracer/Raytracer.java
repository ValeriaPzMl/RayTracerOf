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

//scene01.setCamera(new Camera(new Vector3D(0, 0, -4), 82, 45,4096, 2160, 0.6, 50.0));
public class Raytracer {
    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene01 = new Scene();
        Material luz = new Material(Color.white);
        Material dark = new Material(Color.RED);
        Material setroung = new Material(Color.GREEN, 0, 0.5, 0.6, 0.3, 32.0);
        Material polishedMetal = new Material(new Color(180, 180, 180), 0.8, 0.0, 1.0, 0.9, 256.0);
        Material cutGlass = new Material(new Color(200, 255, 255, 150), 0.15, 0.85, 1.5, 0.95, 512.0);
        Material mattePlastic = new Material(Color.BLUE, 0.05, 0.0, 1.0, 0.3, 32.0);
        Material waterFoam = new Material(new Color(80, 160, 255, 200), 0.1, 0.7, 1.33, 0.8, 128.0);
        Material polishedGold = new Material(new Color(255, 215, 0), 0.85, 0.0, 1.0, 0.95, 1024.0);
        Material glacialIce = new Material(new Color(220, 255, 255, 180), 0.25, 0.6, 1.31, 0.9, 256.0);
        Material sciFiEnergy = new Material(new Color(0, 255, 150, 100), 0.4, 0.5, 2.0, 1.0, 2048.0);
        Material rubber = new Material(Color.BLACK, 0.02, 0.0, 1.0, 0.1, 8.0);

        scene01.setCamera(new Camera(new Vector3D(0, 0, -4), 60, 60,
                800, 800, 0.6, 50.0));
        scene01.addLight(new DirectionalLight(new Vector3D(0,0,1),luz,1.0));
        scene01.addLight(new PointLight(new Vector3D(0,0,4),luz,1.0));
        scene01.addLight(new PointLight(new Vector3D(0,2,4),luz,1.0));
        scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj",new Vector3D(0,0,5),glacialIce,0.5,0,0,0));
        scene01.addObject(OBJReader.getModel3D("espada_com_material.obj",new Vector3D(0,0,8),polishedMetal,1.0,0,0,0));


        BufferedImage image = raytrace(scene01);
        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());
    }
    public static BufferedImage raytrace(Scene scene) {
        return raytrace(scene, 5); // 3 niveles de recursión por defecto
    }

    public static BufferedImage raytrace(Scene scene, int maxDepth) {
        Camera mainCamera = scene.getCamera();
        BufferedImage image = new BufferedImage(
                mainCamera.getResolutionWidth(),
                mainCamera.getResolutionHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        Vector3D[][] posRaytrace = mainCamera.calculatePositionsToRay();
        int height = posRaytrace.length;

        // Dividir el trabajo por filas
        for (int i = 0; i < height; i++) {
            final int row = i;
            executor.submit(() -> {
                for (int j = 0; j < posRaytrace[row].length; j++) {
                    // Cálculo del color del píxel (igual que antes)
                    Vector3D rayDir = Vector3D.normalize(Vector3D.substract(
                            Vector3D.add(mainCamera.getPosition(), posRaytrace[row][j]),
                            mainCamera.getPosition()
                    ));
                    Ray primaryRay = new Ray(mainCamera.getPosition(), rayDir);
                    Color pixelColor = traceRay(scene, primaryRay, maxDepth);
                    image.setRGB(row, j, pixelColor.getRGB());
                }
            });
            if(i%200==0) {
                //System.out.printf("%" + (i / height * 100));
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return image;
    }

    private static Color traceRay(Scene scene, Ray ray, int depth) {
        if (depth <= 0) return Color.BLACK;

        Intersection closestIntersection = raycast(ray, scene.getObjects(), null,
                new double[]{scene.getCamera().getNearFarPlanes()[0], scene.getCamera().getNearFarPlanes()[1]});

        if (closestIntersection == null) return Color.BLACK;

        Material material = closestIntersection.getObject().getMaterial();
        Color diffuseColor = material.getDiffuseColor();
        Vector3D hitPoint = closestIntersection.getPosition();
        Vector3D normal = closestIntersection.getNormal();

        // Iluminación difusa y sombras
        Color finalColor = calculateDiffuse(scene, closestIntersection, material);

        // Reflexión
        if (material.getReflectance() > 0) {
            Vector3D reflectionDir = Vector3D.reflect(Vector3D.normalize(ray.getDirection()), normal);
            Ray reflectionRay = new Ray(
                    Vector3D.add(hitPoint, Vector3D.scalarMultiplication(normal, 1e-5)),
                    reflectionDir
            );
            Color reflectionColor = traceRay(scene, reflectionRay, depth - 1);
            finalColor = blendColors(finalColor, reflectionColor, material.getReflectance());
        }

        // Refracción
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

    // Función auxiliar para cálculo de iluminación difusa
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

    // Función para mezclar colores con factor
    private static Color blendColors(Color base, Color overlay, double factor) {
        return new Color(
                (int) (base.getRed() * (1 - factor) + overlay.getRed() * factor),
                (int) (base.getGreen() * (1 - factor) + overlay.getGreen() * factor),
                (int) (base.getBlue() * (1 - factor) + overlay.getBlue() * factor)
        );
    }
    public static Color multiplyColor(Color objectColor, Color lightColor, double intensity) {
        // Convertir colores a valores [0, 1]
        double rObj = objectColor.getRed() / 255.0;
        double gObj = objectColor.getGreen() / 255.0;
        double bObj = objectColor.getBlue() / 255.0;

        double rLight = lightColor.getRed() / 255.0;
        double gLight = lightColor.getGreen() / 255.0;
        double bLight = lightColor.getBlue() / 255.0;

        // Multiplicar componentes y aplicar intensidad
        double r = Math.clamp((rObj * rLight * intensity), 0.005, 1.0);
        double g = Math.clamp((gObj * gLight * intensity), 0.005, 1.0);
        double b = Math.clamp((bObj * bLight * intensity), 0.005, 1.0);

        // Convertir de vuelta a [0, 255] y crear Color
        return new Color(
                (int) (r * 255),
                (int) (g * 255),
                (int) (b * 255)
        );
    }

    public static Color addColor(Color original, Color otherColor) {
        float red = (float) Math.clamp((original.getRed() / 255.0) + (otherColor.getRed() / 255.0), 0.005, 1.0);
        float green = (float) Math.clamp((original.getGreen() / 255.0) + (otherColor.getGreen() / 255.0), 0.005, 1.0);
        float blue = (float) Math.clamp((original.getBlue() / 255.0) + (otherColor.getBlue() / 255.0), 0.005, 1.0);
        return new Color(red, green, blue);
    }

    public static Intersection raycast(Ray ray, List<Object3D> objects, Object3D caster, double[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int i = 0; i < objects.size(); i++) {
            Object3D currObj = objects.get(i);
            if (caster == null || !currObj.equals(caster)) {
                Intersection intersection = currObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    // Eliminar la línea que usa intersectionZ
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
    public static boolean isShadowed(Scene scene, Vector3D hitPoint, Vector3D normal, Light light) {
        Vector3D lightDir;
        double distanceToLight = Double.POSITIVE_INFINITY;

        // Calcular dirección y distancia según el tipo de luz
        if (light instanceof PointLight) {
            PointLight pointLight = (PointLight) light;
            Vector3D toLight = Vector3D.substract(pointLight.getPosition(), hitPoint);
            distanceToLight = Vector3D.magnitude(toLight);
            lightDir = Vector3D.normalize(toLight);
        } else if (light instanceof DirectionalLight) {
            DirectionalLight dirLight = (DirectionalLight) light;
            lightDir = Vector3D.scalarMultiplication(dirLight.getDirection(), -1.0); // Invertir dirección
        } else {
            return false; // Tipo de luz no soportado
        }

        // Offset para evitar auto-oclusión
        Vector3D shadowOrig = Vector3D.add(hitPoint, Vector3D.scalarMultiplication(normal, 1e-5));
        Ray shadowRay = new Ray(shadowOrig, lightDir);

        // Lanzar shadow ray (excluyendo el objeto actual si es necesario)
        Intersection shadowIntersection = raycast(shadowRay, scene.getObjects(), null, new double[]{0.0, distanceToLight});

        return shadowIntersection != null;
    }
}
