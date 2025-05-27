package edu.up.isgc.cg.raytracer.objects;

import java.awt.*;
/**
 * @author Valeria Pérez Maciel
 *
 * Represents the physical and optical properties of a surface material used in ray tracing.
 * Includes parameters for diffuse color, reflectance, transparency, refraction, and specular highlights.
 */
public class Material {
    private Color diffuseColor;
    private double reflectance;
    private double transparency;
    private double refractiveIndex;
    private double specularity;
    private double shininess;

    /**
     * Constructs a Material with only a diffuse color.
     * Sets default values for other optical properties:
     * - reflectance = 0.0
     * - transparency = 0.0
     * - refractiveIndex = 1.0 (air)
     * - specularity = 0.5
     * - shininess = 32.0
     *
     * @param diffuse The base color of the material.
     */
    public Material(Color diffuse) {
        this(diffuse, 0.0, 0.0, 1.0, 0.5, 32.0);
    }
    /**
     * Constructs a Material with full specification of all surface properties.
     *
     * @param diffuse The base color of the material.
     * @param reflectance The reflectivity coefficient [0.0–1.0].
     * @param transparency The transparency coefficient [0.0–1.0].
     * @param refractiveIndex The index of refraction (e.g., air=1.0, glass=1.5).
     * @param specularity The intensity of specular highlights [0.0–1.0].
     * @param shininess The shininess exponent (e.g., 32, 64) used in Phong lighting.
     */
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