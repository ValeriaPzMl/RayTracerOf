package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.DirectionalLight;
import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.lights.PointLight;
import edu.up.isgc.cg.raytracer.objects.*;
import edu.up.isgc.cg.raytracer.tools.OBJReader;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Raytracer {
    public static void main(String[] args) {
        System.out.println(new Date());
        Scene scene01 = new Scene();
        Material polishedMetal = new Material(
                new Color(180, 180, 180), // Color base gris
                0.8,   // Reflectancia (80% reflejo especular)
                0.0,   // Transparencia (0%)
                1.0,   // Índice de refracción (sin refracción)
                0.9,   // Specularity (90% brillo intenso)
                256.0  // Shininess (brillo concentrado)
        );
        Material cutGlass = new Material(
                new Color(200, 255, 255, 150), // Cyan claro translúcido
                0.15,  // Reflectancia (15% Fresnel)
                0.85,  // Transparencia (85%)
                1.5,   // Índice de refracción (vidrio)
                0.95,  // Specularity (95% brillo vítreo)
                512.0  // Shininess (brillo muy concentrado)
        );
        Material waterFoam = new Material(
                new Color(80, 160, 255, 200), // Azul acuático
                0.1,   // Reflectancia (10%)
                0.7,   // Transparencia (70%)
                1.33,  // Índice de refracción (agua)
                0.8,   // Specularity (80% brillo húmedo)
                128.0  // Shininess (brillo moderado)
        );
        Material mattePlastic = new Material(
                Color.BLUE,  // Color azul
                0.05,  // Reflectancia (5% reflejo)
                0.0,    // Transparencia (0%)
                1.0,    // Índice de refracción
                0.3,    // Specularity (30% brillo suave)
                32.0    // Shininess (brillo difuso)
        );
        Material polishedGold = new Material(
                new Color(255, 215, 0), // Dorado RGB
                0.85,  // Reflectancia (85%)
                0.0,   // Transparencia (0%)
                1.0,   // Índice de refracción
                0.95,  // Specularity (95% brillo metálico)
                1024.0 // Shininess (reflejos nítidos)
        );
        Material glacialIce = new Material(
                new Color(220, 255, 255, 180), // Blanco azulado
                0.25,  // Reflectancia (25%)
                0.6,   // Transparencia (60%)
                1.31,  // Índice de refracción (hielo)
                0.9,   // Specularity (90% brillo helado)
                256.0  // Shininess (brillo cristalino)
        );
        Material rubber = new Material(
                Color.BLACK,
                0.02,  // Reflectancia (2%)
                0.0,   // Transparencia (0%)
                1.0,   // Índice de refracción
                0.1,   // Specularity (10% brillo mínimo)
                8.0    // Shininess (brillo muy difuso)
        );
        Material luz = new Material(Color.white,0,0,0,0,0);

        scene01.setCamera(new Camera(new Vector3D(0, 0, -4), 60, 60,
                800, 800, 0.6, 50.0));
        scene01.addLight(new DirectionalLight(new Vector3D(0.0, 0.0, 1.0), luz, 1.1));

        scene01.addObject(OBJReader.getModel3D("SmallTeapot.obj", new Vector3D(0, -2.5, 1),polishedMetal));

        Scene scene02 = new Scene();
        scene02.setCamera(new Camera(new Vector3D(0, 0, -4), 60, 60, 800, 800, 0.6, 50.0));
        scene02.addLight(new PointLight(new Vector3D(0.0, 1.0, 0.0),luz, 0.8));
        /*scene02.addLight(new DirectionalLight(new Vector3D(0.0, 0.0, 1.0), Color.WHITE, 0.8));
        scene02.addLight(new DirectionalLight(new Vector3D(0.0, -0.1, 0.1), Color.WHITE, 0.2));
        scene02.addLight(new DirectionalLight(new Vector3D(-0.2, -0.1, 0.0), Color.WHITE, 0.2));*/
        scene02.addObject(new Sphere(new Vector3D(0.0, 1.0, 5.0), 0.5, mattePlastic));
        scene02.addObject(new Sphere(new Vector3D(0.5, 1.0, 4.5), 0.25, cutGlass));
        scene02.addObject(new Sphere(new Vector3D(0.35, 1.0, 4.5), 0.3, waterFoam));
        //scene02.addObject(new Sphere(new Vector3D(4.85, 1.0, 4.5), 0.3, Color.PINK));
        //scene02.addObject(new Sphere(new Vector3D(2.85, 1.0, 304.5), 0.5, Color.BLUE));
        scene02.addObject(OBJReader.getModel3D("Cube.obj", new Vector3D(-2f, -2.5, 1.0), cutGlass));
        //scene02.addObject(OBJReader.getModel3D("CubeQuad.obj", new Vector3D(-3.0, -2.5, 3.0), Color.GREEN));
        scene02.addObject(OBJReader.getModel3D("SmallTeapot.obj", new Vector3D(2.0, -1.0, 1.5), polishedGold));
        //scene02.addObject(OBJReader.getModel3D("Ring.obj", new Vector3D(2.0, -1.0, 1.5), Color.BLUE));

        // Definir materiales
        Material glass = new Material(
                new Color(200, 255, 255, 150), // Color cyan translúcido
                0.1,   // Reflectancia
                0.9,   // Transparencia
                0.9,   // Índice de refracción (vidrio)
                0.95,  // Specularity
                512.0  // Shininess
        );

        Material reflectiveTeapot = new Material(
                Color.yellow, // Color base metálico
                0.8,   // Reflectancia
                0.0,   // Transparencia
                1.0,   // IOR
                0.9,   // Specularity
                256.0  // Shininess
        );

// Crear escena
        Scene scene03 = new Scene();
        scene03.setCamera(new Camera(new Vector3D(0, 0, -8), 60, 60, 800, 800, 0.6, 250));

// Configurar iluminación
        scene03.addLight(new PointLight(new Vector3D(2.0, 5.0, 1.0), luz, 1.2));
        scene03.addLight(new PointLight(new Vector3D(0, 5.0, 200), luz, 1.2));
        scene03.addLight(new DirectionalLight(new Vector3D(0.0, 0.0, 1.0), luz, 0.8));

        //scene03.addLight(new DirectionalLight(new Vector3D(-0.5, -0.5, 1.0), luz, 0.4));

// Añadir objetos
// Esfera transparente en primer plano
        //scene03.addObject(new Sphere(new Vector3D(0, 1, 0), 1.5, glass));
        scene03.addObject(OBJReader.getModel3D(
                "SmallTeapot.obj",
                new Vector3D(0, 1, 0), // Posición más atrás en Z
                glass
        ));
// Tetera detrás de la esfera
        scene03.addObject(OBJReader.getModel3D(
                "LP_Nosferatus.obj",
                new Vector3D(0, 1, 2), // Posición más atrás en Z
                reflectiveTeapot
        ));

// Suelo para reflejos
        scene03.addObject(OBJReader.getModel3D(
                "brazo.obj",
                new Vector3D(0, -50, 200),
                new Material(Color.blue, 0.1, 0.0, 1.0, 0.3, 32.0) // Material mate
        ));
        BufferedImage image = raytrace(scene03);
        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());
    }
    public static BufferedImage raytrace(Scene scene) {
        return raytrace(scene, 3); // 3 niveles de recursión por defecto
    }

    public static BufferedImage raytrace(Scene scene, int maxDepth) {
        Camera mainCamera = scene.getCamera();
        double[] nearFarPlanes = mainCamera.getNearFarPlanes();
        BufferedImage image = new BufferedImage(
                mainCamera.getResolutionWidth(),
                mainCamera.getResolutionHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        List<Object3D> objects = scene.getObjects();
        List<Light> lights = scene.getLights();
        Vector3D[][] posRaytrace = mainCamera.calculatePositionsToRay();
        Vector3D cameraPos = mainCamera.getPosition();

        for (int i = 0; i < posRaytrace.length; i++) {
            for (int j = 0; j < posRaytrace[i].length; j++) {
                Vector3D rayDir = Vector3D.normalize( Vector3D.substract(
                        Vector3D.add(cameraPos, posRaytrace[i][j]),
                        cameraPos
                ));

                Ray primaryRay = new Ray(cameraPos, rayDir);
                Color pixelColor = traceRay(scene, primaryRay, maxDepth);
                image.setRGB(i, j, pixelColor.getRGB());
            }
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
        double r = Math.clamp((rObj * rLight * intensity), 0.0, 1.0);
        double g = Math.clamp((gObj * gLight * intensity), 0.0, 1.0);
        double b = Math.clamp((bObj * bLight * intensity), 0.0, 1.0);

        // Convertir de vuelta a [0, 255] y crear Color
        return new Color(
                (int) (r * 255),
                (int) (g * 255),
                (int) (b * 255)
        );
    }

    public static Color addColor(Color original, Color otherColor) {
        float red = (float) Math.clamp((original.getRed() / 255.0) + (otherColor.getRed() / 255.0), 0.001, 1.0);
        float green = (float) Math.clamp((original.getGreen() / 255.0) + (otherColor.getGreen() / 255.0), 0.001, 1.0);
        float blue = (float) Math.clamp((original.getBlue() / 255.0) + (otherColor.getBlue() / 255.0), 0.001, 1.0);
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
