package edu.up.isgc.cg.raytracer.tools;

import edu.up.isgc.cg.raytracer.Vector3D;
import edu.up.isgc.cg.raytracer.objects.Material;
import edu.up.isgc.cg.raytracer.objects.Model3D;
import edu.up.isgc.cg.raytracer.objects.Triangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Valeria Pérez Maciel , Jafet Rodríguez and Bernardo Moya
 *
 * Utility class for reading 3D models from Wavefront OBJ files and converting them
 * into a Model3D object compatible with the ray tracer.
 *
 * This class is abstract and not meant to be instantiated.
 */
public abstract class OBJReader {
    /**
     * Reads a Wavefront .OBJ file and constructs a Model3D object.
     * Applies scaling and rotation transformations to vertices and normals.
     *
     * @param path The file path to the .obj model.
     * @param origin The origin position of the model in world coordinates.
     * @param material The material to apply to all the triangles of the model.
     * @param scale The scale factor to apply to all vertex positions.
     * @param rotX Rotation angle around the X-axis in degrees.
     * @param rotY Rotation angle around the Y-axis in degrees.
     * @param rotZ Rotation angle around the Z-axis in degrees.
     * @return A Model3D object representing the parsed and transformed model, or null if an error occurs.
     */
    public static Model3D getModel3D(String path, Vector3D origin, Material material, double scale, double rotX, double rotY, double rotZ) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            List<Triangle> triangles = new ArrayList<Triangle>();
            List<Vector3D> vertices = new ArrayList<Vector3D>();
            List<Vector3D> normals = new ArrayList<Vector3D>();
            String line;
            int defaultSmoothingGroup = -1;
            int smoothingGroup = defaultSmoothingGroup;
            Map<Integer, List<Triangle>> smoothingMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ") || line.startsWith("vn ")) {
                    String[] vertexComponents = line.split("(\\s)+");
                    if (vertexComponents.length >= 4) {
                        double x = Double.parseDouble(vertexComponents[1]);
                        double y = Double.parseDouble(vertexComponents[2]);
                        double z = Double.parseDouble(vertexComponents[3]);
                        Vector3D vector = new Vector3D(x, y, z);
                        if (line.startsWith("v ")) {
                            vector = vector.rotateX(Math.toRadians(rotX))
                                    .rotateY(Math.toRadians(rotY))
                                    .rotateZ(Math.toRadians(rotZ))
                                    .scale(scale);
                            vertices.add(vector);
                        } else {
                            vector = Vector3D.normalize(vector.rotateX(Math.toRadians(rotX))
                                    .rotateY(Math.toRadians(rotY))
                                    .rotateZ(Math.toRadians(rotZ)));
                            normals.add(vector);
                        }
                    }
                }else if (line.startsWith("f ")) {
                    String[] faceComponents = line.split("(\\s)+");
                    List<Integer> faceVertex = new ArrayList<Integer>();
                    List<Integer> faceNormal = new ArrayList<Integer>();

                    for (int i = 1; i < faceComponents.length; i++) {
                        String[] infoVertex = faceComponents[i].split("/");
                        if (infoVertex.length >= 3) {
                            int vertexIndex = Integer.parseInt(infoVertex[0]);
                            int normalIndex = Integer.parseInt(infoVertex[2]);
                            faceVertex.add(vertexIndex);
                            faceNormal.add(normalIndex);
                        }
                    }

                    if (faceVertex.size() >= 3) {
                        Vector3D[] triangleVertices = new Vector3D[faceVertex.size()];
                        Vector3D[] triangleVerticesNormals = new Vector3D[faceNormal.size()];

                        for (int i = 0; i < faceVertex.size(); i++) {
                            triangleVertices[i] = (vertices.get(faceVertex.get(i) - 1));
                        }

                        Vector3D[] arrangedTriangleVertices = null;
                        Vector3D[] arrangedTriangleNormals = null;
                        if (normals.size() > 0) {
                            for (int i = 0; i < faceNormal.size(); i++) {
                                triangleVerticesNormals[i] = (normals.get(faceNormal.get(i) - 1));
                            }
                            arrangedTriangleNormals = new Vector3D[]{triangleVerticesNormals[1], triangleVerticesNormals[0], triangleVerticesNormals[2]};
                        }
                        arrangedTriangleVertices = new Vector3D[]{triangleVertices[1], triangleVertices[0], triangleVertices[2]};

                        Triangle tmpTriangle = new Triangle(arrangedTriangleVertices, arrangedTriangleNormals);
                        triangles.add(tmpTriangle);

                        List<Triangle> trianglesInMap = smoothingMap.get(smoothingGroup);
                        if (trianglesInMap == null) {
                            trianglesInMap = new ArrayList<>();
                        }
                        trianglesInMap.add(tmpTriangle);

                        if (faceVertex.size() == 4) {
                            arrangedTriangleVertices = new Vector3D[]{triangleVertices[2], triangleVertices[0], triangleVertices[3]};
                            if (arrangedTriangleNormals != null) {
                                arrangedTriangleNormals = new Vector3D[]{triangleVerticesNormals[2], triangleVerticesNormals[0], triangleVerticesNormals[3]};
                            }
                            tmpTriangle = new Triangle(arrangedTriangleVertices, arrangedTriangleNormals);
                            triangles.add(tmpTriangle);
                            trianglesInMap.add(tmpTriangle);
                        }

                        if (smoothingGroup != defaultSmoothingGroup) {
                            smoothingMap.put(smoothingGroup, trianglesInMap);
                        }
                    }
                } else if (line.startsWith("s ")) {
                    String[] smoothingComponents = line.split("(\\s)+");
                    if (smoothingComponents.length > 1) {
                        if (smoothingComponents[1].equals("off")) {
                            smoothingGroup = defaultSmoothingGroup;
                        } else {
                            try {
                                smoothingGroup = Integer.parseInt(smoothingComponents[1]);
                            } catch (NumberFormatException nfe) {
                                smoothingGroup = defaultSmoothingGroup;
                            }
                        }
                    }
                }
            }
            reader.close();

            class NormalPair {
                Vector3D normal;
                int count;

                public NormalPair() {
                    normal = Vector3D.ZERO();
                    count = 0;
                }
            }

            for (Integer key : smoothingMap.keySet()) {
                Map<Vector3D, NormalPair> vertexMap = new HashMap<>();
                List<Triangle> trianglesInMap = smoothingMap.get(key);
                for (Triangle triangle : trianglesInMap) {
                    Vector3D[] triangleVertices = triangle.getVertices();
                    Vector3D[] triangleNormals = triangle.getNormals();
                    for (int i = 0; i < triangleVertices.length; i++) {
                        NormalPair normalsVertex = vertexMap.get(triangleVertices[i]);
                        if (normalsVertex == null) {
                            normalsVertex = new NormalPair();
                        }
                        if (triangleNormals.length > 0 && i < triangleNormals.length) {
                            normalsVertex.normal = Vector3D.add(normalsVertex.normal, triangleNormals[i]);
                            normalsVertex.count++;
                        }
                        vertexMap.put(triangleVertices[i], normalsVertex);
                    }
                }
                for (Triangle triangle : trianglesInMap) {
                    Vector3D[] triangleVertices = triangle.getVertices();
                    Vector3D[] triangleNormals = triangle.getNormals();
                    for (int i = 0; i < triangleVertices.length; i++) {
                        NormalPair normalsVertex = vertexMap.get(triangleVertices[i]);
                        triangleNormals[i] = Vector3D.scalarMultiplication(normalsVertex.normal, 1.0 / (double) normalsVertex.count);
                    }
                    triangle.setNormals(triangleNormals);
                }
            }

            return new Model3D(origin, triangles.toArray(new Triangle[triangles.size()]), material);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OBJReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OBJReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
