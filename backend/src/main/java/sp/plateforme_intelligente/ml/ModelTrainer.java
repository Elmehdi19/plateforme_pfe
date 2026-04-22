package sp.plateforme_intelligente.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ModelTrainer {

    public static void main(String[] args) throws Exception {
        // Chemin relatif depuis la racine du projet backend
        String csvPath = "src/main/resources/data/etudiants_risque.csv";
        List<double[]> featuresList = new ArrayList<>();
        List<Integer> labelsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                double moyenne = Double.parseDouble(parts[0]);
                int absences = Integer.parseInt(parts[1]);
                int modulesEchoues = Integer.parseInt(parts[2]);
                int risque = Integer.parseInt(parts[3]);
                featuresList.add(new double[]{moyenne, absences, modulesEchoues});
                labelsList.add(risque);
            }
        }

        double[][] X = featuresList.toArray(new double[0][]);
        int[] y = labelsList.stream().mapToInt(i -> i).toArray();

        // Entraînement du modèle
        LogisticRegression model = new LogisticRegression();
        model.train(X, y, 0.01, 1000);
        model.save("src/main/resources/ml/logistic_model.ser");
        System.out.println("Modèle sauvegardé avec succès !");
    }
}