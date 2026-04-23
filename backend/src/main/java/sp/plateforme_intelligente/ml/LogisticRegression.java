package sp.plateforme_intelligente.ml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LogisticRegression implements Serializable {
    private static final long serialVersionUID = 1L;
    private double[] weights;
    private double bias;

    public void train(double[][] X, int[] y, double learningRate, int iterations) {
        int n = X.length;
        int m = X[0].length;
        weights = new double[m];
        bias = 0.0;

        for (int iter = 0; iter < iterations; iter++) {
            double[] dw = new double[m];
            double db = 0.0;
            double cost = 0.0;

            for (int i = 0; i < n; i++) {
                double z = bias;
                for (int j = 0; j < m; j++) {
                    z += weights[j] * X[i][j];
                }
                double a = sigmoid(z);
                cost += -y[i] * Math.log(a) - (1 - y[i]) * Math.log(1 - a);
                for (int j = 0; j < m; j++) {
                    dw[j] += (a - y[i]) * X[i][j];
                }
                db += (a - y[i]);
            }
            cost /= n;
            for (int j = 0; j < m; j++) {
                weights[j] -= learningRate * dw[j] / n;
            }
            bias -= learningRate * db / n;

            if (iter % 100 == 0) {
                System.out.printf("Itération %d, coût = %.4f%n", iter, cost);
            }
        }
    }

    private double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    public double predictProbability(double[] x) {
        double z = bias;
        for (int i = 0; i < weights.length; i++) {
            z += weights[i] * x[i];
        }
        return sigmoid(z);
    }

    public void save(String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(this);
        }
    }

    public static LogisticRegression load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (LogisticRegression) ois.readObject();
        }
    }
}