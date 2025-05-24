package edu.up.isgc.cg.raytracer;

public class Vector3D {
    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    public Vector3D(double x, double y, double z){
        setX(x);
        setY(y);
        setZ(z);
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }

    public static Vector3D ZERO(){
        return ZERO.clone();
    }

    @Override
    public String toString(){
        return "Vector3D{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                "}";
    }

    public static double dotProduct(Vector3D vectorA, Vector3D vectorB){
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }

    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }

    public static double magnitude (Vector3D vectorA){
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    public static Vector3D add(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY(), vectorA.getZ() + vectorB.getZ());
    }

    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY(), vectorA.getZ() - vectorB.getZ());
    }

    public static Vector3D normalize(Vector3D vectorA){
        double mag = Vector3D.magnitude(vectorA);
        return new Vector3D(vectorA.getX() / mag, vectorA.getY() / mag, vectorA.getZ() / mag);
    }

    public static Vector3D scalarMultiplication(Vector3D vectorA, double scalar){
        return new Vector3D(vectorA.getX() * scalar, vectorA.getY() * scalar, vectorA.getZ() * scalar);
    }
    public static Vector3D reflect(Vector3D incident, Vector3D normal) {
        double dot = Vector3D.dotProduct(incident, normal);
        return Vector3D.substract(incident, Vector3D.scalarMultiplication(normal, 2.0 * dot));
    }
    public static Vector3D refract(Vector3D incident, Vector3D normal, double n1, double n2) {
        // Normalizar vectores y calcular cosTheta
        Vector3D incidentNorm = Vector3D.normalize(incident);
        Vector3D negIncident = Vector3D.scalarMultiplication(incidentNorm, -1.0);
        double cosTheta = Math.min(Vector3D.dotProduct(negIncident, normal), 1.0);

        // Determinar si el rayo entra o sale del material
        boolean isEntering = cosTheta < 0.0;
        if (isEntering) {
            cosTheta = -cosTheta; // Hacer positivo para cálculos
        } else {
            // Intercambiar índices de refracción e invertir normal si el rayo sale
            double temp = n1;
            n1 = n2;
            n2 = temp;
            normal = Vector3D.scalarMultiplication(normal, -1.0);
        }

        // Calcular relación de índices y sen²(theta)
        double ratio = n1 / n2;
        double sin2Theta = ratio * ratio * (1.0 - cosTheta * cosTheta);

        // Reflexión interna total (no hay refracción)
        if (sin2Theta > 1.0) {
            return null;
        }

        // Calcular dirección refractada usando fórmula de Snell
        double cosThetaT = Math.sqrt(1.0 - sin2Theta);
        Vector3D refractedDir = Vector3D.add(
                Vector3D.scalarMultiplication(incidentNorm, ratio),
                Vector3D.scalarMultiplication(normal, ratio * cosTheta - cosThetaT)
        );

        return Vector3D.normalize(refractedDir);
    }
}
