// TO USE THE PARSER DRAG AND DROP YOUR DATA INTO THE FOLDER CONTAINING THIS FILE   
package frc.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataParser {

    public static void main(String[] args) {
        File incramental = new File("src/main/java/frc/parser/incramental.csv");
        File sharp = new File("src/main/java/frc/parser/sharp.csv");
        ArrayList<double[]> incramentalData = parseFile(incramental);
        ArrayList<double[]> sharpData = parseFile(sharp);
        double[] kskV = parseIncramental(incramentalData);
        double ka = parseSharp(sharpData, kskV[0], kskV[1]);
        System.out.println("Ks: " + kskV[0]);
        System.out.println("Kv: " + kskV[1]);
        System.out.println("Ka: " + ka);
    }

    public static ArrayList<double[]> parseFile(File file) {
        ArrayList<double[]> data = new ArrayList<>();
        try {
            Scanner fetch = new Scanner(file);
            while (fetch.hasNextLine()) {
                try {
                    double[] line = new double[3];
                    String[] sLine = fetch.nextLine().split(", ");
                    line[0] = Double.parseDouble(sLine[0]);
                    line[1] = Double.parseDouble(sLine[1]);
                    line[2] = Double.parseDouble(sLine[2]);
                    data.add(line);
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Bad Input!");
                    fetch.close();
                    return null;
                }
            }
            fetch.close();
            return data;
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            e.printStackTrace();
            return null;
        }
    }

    public static double parseSharp(ArrayList<double[]> data, double ks, double kv) {
        ArrayList<Double> slopes = new ArrayList<>();
        for (int i=0; i<data.size() - 1; i++) {
            double acceleration = (data.get(i + 1)[0] - data.get(i)[0])/(data.get(i + 1)[2] - data.get(i)[2]);
            if (acceleration != 0) {
                slopes.add((data.get(i)[1] - ((kv * data.get(i)[0]) + ks))/acceleration);
            }
        }
        double[] slopesAsDouble = new double[slopes.size()];
        for (int i=0; i<slopes.size(); i++) {
            slopesAsDouble[i] = slopes.get(i);
        }
        return average(slopesAsDouble);
    }

    public static double[] parseIncramental(ArrayList<double[]> data) {
        int ksIndex = getKsIndex(data);
        double[] slopes = new double[data.size() - ksIndex];
        for (int i=0; i<slopes.length; i++) {
            slopes[i] = data.get(ksIndex + i)[1]/data.get(ksIndex + i)[0];
        }
        double[] ksKv = {data.get(ksIndex)[1], average(slopes)};
        return ksKv;
    }

    private static int getKsIndex(ArrayList<double[]> data) {
        for (int i=0; data.get(i)[0] == 0.0 || i < data.size(); i++) {
            if (data.get(i)[0] != 0) {
                return i;
            }
        }
        return -1;
    }

    private static double average(double[] arr) {
        double sum = 0;
        for (int i=0; i<arr.length; i++) {
            sum += arr[i];
        }
        return sum/arr.length;
    }


}
