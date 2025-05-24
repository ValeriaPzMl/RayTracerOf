package edu.up.isgc.cg.raytracer.objects;

import java.awt.*;

public class Material {
    private Color diffuseColor;
    private double reflectance;    // [0-1] coeficiente de reflexión (ej: espejo=1.0)
    private double transparency;  // [0-1] coeficiente de transparencia (ej: vidrio=1.0)
    private double refractiveIndex; // Índice de refracción (aire=1.0, vidrio=1.5, agua=1.33)
    private double specularity;   // [0-1] brillo especular (Phong)
    private double shininess;     // Exponente de brillo (ej: 32, 64)


    public Material(Color diffuse) {
        this(diffuse, 0.0, 0.0, 1.0, 0.5, 32.0);
    }

    public Material(Color diffuse, double reflectance, double transparency,
                    double refractiveIndex, double specularity, double shininess) {
        setDiffuseColor(diffuse);
        setReflectance(reflectance);
        setTransparency(transparency);
        setRefractiveIndex(refractiveIndex);
        setSpecularity(specularity);
        setShininess(shininess);
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public double getReflectance() {
        return reflectance;
    }

    public void setReflectance(double reflectance) {
        this.reflectance = reflectance;
    }

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public double getRefractiveIndex() {
        return refractiveIndex;
    }

    public void setRefractiveIndex(double refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    public double getSpecularity() {
        return specularity;
    }

    public void setSpecularity(double specularity) {
        this.specularity = specularity;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }
}