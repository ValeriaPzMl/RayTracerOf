package edu.up.isgc.cg.raytracer;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Represents a three-dimensional vector with double precision components.
 * Provides common vector operations such as addition, subtraction, dot product,
 * cross product, normalization, scaling, and rotation, as well as methods for
 * reflection and refraction used in ray tracing.
 */
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

    /**
     * Creates and returns a copy of the current vector.
     *
     * @return A new Vector3D with the same x, y, and z components.
     */
    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }

    /**
     * Returns a zero vector (0, 0, 0).
     *
     * @return A new Vector3D representing the origin.
     */
    public static Vector3D ZERO(){
        return ZERO.clone();
    }

    /**
     * Returns a string representation of the vector.
     *
     * @return A string in the format "Vector3D{x=..., y=..., z=...}".
     */
    @Override
    public String toString(){
        return "Vector3D{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                "}";
    }

    /**
     * Calculates the dot product of two vectors.
     *
     * @param vectorA First vector.
     * @param vectorB Second vector.
     * @return The scalar dot product.
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB){
        return (vectorA.getX() * vectorB.getX()) + (vectorA.getY() * vectorB.getY()) + (vectorA.getZ() * vectorB.getZ());
    }
    /**
     * Calculates the cross product of two vectors.
     *
     * @param vectorA First vector.
     * @param vectorB Second vector.
     * @return A new vector perpendicular to both inputs.
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D((vectorA.getY() * vectorB.getZ()) - (vectorA.getZ() * vectorB.getY()),
                (vectorA.getZ() * vectorB.getX()) - (vectorA.getX() * vectorB.getZ()),
                (vectorA.getX() * vectorB.getY()) - (vectorA.getY() * vectorB.getX()));
    }
    /**
     * Computes the magnitude (length) of a vector.
     *
     * @param vectorA The vector whose magnitude is to be computed.
     * @return The length of the vector.
     */
    public static double magnitude (Vector3D vectorA){
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }
    /**
     * Adds two vectors.
     *
     * @param vectorA First vector.
     * @param vectorB Second vector.
     * @return A new vector that is the sum of both.
     */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() + vectorB.getX(), vectorA.getY() + vectorB.getY(), vectorA.getZ() + vectorB.getZ());
    }
    /**
     * Subtracts one vector from another.
     *
     * @param vectorA The vector to subtract from.
     * @param vectorB The vector to subtract.
     * @return A new vector resulting from the subtraction.
     */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB){
        return new Vector3D(vectorA.getX() - vectorB.getX(), vectorA.getY() - vectorB.getY(), vectorA.getZ() - vectorB.getZ());
    }
    /**
     * Normalizes a vector to have unit length.
     *
     * @param vectorA The vector to normalize.
     * @return A normalized vector in the same direction.
     */
    public static Vector3D normalize(Vector3D vectorA){
        double mag = Vector3D.magnitude(vectorA);
        return new Vector3D(vectorA.getX() / mag, vectorA.getY() / mag, vectorA.getZ() / mag);
    }
    /**
     * Multiplies a vector by a scalar.
     *
     * @param vectorA The vector to scale.
     * @param scalar The scalar value.
     * @return A new scaled vector.
     */
    public static Vector3D scalarMultiplication(Vector3D vectorA, double scalar){
        return new Vector3D(vectorA.getX() * scalar, vectorA.getY() * scalar, vectorA.getZ() * scalar);
    }
    /**
     * Reflects a vector around a given normal.
     *
     * @param incident The incoming vector.
     * @param normal The normal vector to reflect against.
     * @return The reflected vector.
     */
    public static Vector3D reflect(Vector3D incident, Vector3D normal) {
        double dot = Vector3D.dotProduct(incident, normal);
        return Vector3D.substract(incident, Vector3D.scalarMultiplication(normal, 2.0 * dot));
    }
    /**
     * Computes the refraction of a vector passing through two media using Snell's law.
     *
     * @param incident The incoming vector.
     * @param normal The surface normal.
     * @param n1 The index of refraction of the original medium.
     * @param n2 The index of refraction of the second medium.
     * @return The refracted vector, or null if total internal reflection occurs.
     */
    public static Vector3D refract(Vector3D incident, Vector3D normal, double n1, double n2) {
        Vector3D incidentNorm = Vector3D.normalize(incident);
        Vector3D negIncident = Vector3D.scalarMultiplication(incidentNorm, -1.0);
        double cosTheta = Math.min(Vector3D.dotProduct(negIncident, normal), 1.0);

        boolean isEntering = cosTheta < 0.0;
        if (isEntering) {
            cosTheta = -cosTheta;
        } else {
            double temp = n1;
            n1 = n2;
            n2 = temp;
            normal = Vector3D.scalarMultiplication(normal, -1.0);
        }

        double ratio = n1 / n2;
        double sin2Theta = ratio * ratio * (1.0 - cosTheta * cosTheta);

        if (sin2Theta > 1.0) {
            return null;
        }

        double cosThetaT = Math.sqrt(1.0 - sin2Theta);
        Vector3D refractedDir = Vector3D.add(
                Vector3D.scalarMultiplication(incidentNorm, ratio),
                Vector3D.scalarMultiplication(normal, ratio * cosTheta - cosThetaT)
        );

        return Vector3D.normalize(refractedDir);
    }
    /**
     * Rotates the vector around the X-axis.
     *
     * @param angle The angle in radians.
     * @return A new vector rotated around the X-axis.
     */
    public Vector3D rotateX(double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double yNew = this.getY() * cosA - this.getZ() * sinA;
        double zNew = this.getY() * sinA + this.getZ() * cosA;
        return new Vector3D(this.getX(), yNew, zNew);
    }
    /**
     * Rotates the vector around the Y-axis.
     *
     * @param angle The angle in radians.
     * @return A new vector rotated around the Y-axis.
     */
    public Vector3D rotateY(double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double xNew = this.getZ() * sinA + this.getX() * cosA;
        double zNew = this.getZ() * cosA - this.getX() * sinA;
        return new Vector3D(xNew, this.getY(), zNew);
    }

    /**
     * Rotates the vector around the Z-axis.
     *
     * @param angle The angle in radians.
     * @return A new vector rotated around the Z-axis.
     */
    public Vector3D rotateZ(double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double xNew = this.getX() * cosA - this.getY() * sinA;
        double yNew = this.getX() * sinA + this.getY() * cosA;
        return new Vector3D(xNew, yNew, this.getZ());
    }
    /**
     * Scales the vector uniformly by a given factor.
     *
     * @param factor The scale factor.
     * @return A new scaled vector.
     */
    public Vector3D scale(double factor) {
        return new Vector3D(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }
}
