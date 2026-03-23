import fuzzlib.FuzzySet;

public class Main {

    public static void main(String[] args) {
        String[] nazwyKlas = {"Iris-setosa", "Iris-versicolor", "Iris-virginica"};
        FuzzySet[][] model = new FuzzySet[3][4];

        // 1. SETOSA
        model[0][0] = new FuzzySet(); model[0][0].newGaussian(5.006, 0.352);
        model[0][1] = new FuzzySet(); model[0][1].newGaussian(3.428, 0.379);
        model[0][2] = new FuzzySet(); model[0][2].newGaussian(1.462, 0.174);
        model[0][3] = new FuzzySet(); model[0][3].newGaussian(0.246, 0.105);

        // 2. VERSICOLOR
        model[1][0] = new FuzzySet(); model[1][0].newGaussian(5.936, 0.516);
        model[1][1] = new FuzzySet(); model[1][1].newGaussian(2.770, 0.314);
        model[1][2] = new FuzzySet(); model[1][2].newGaussian(4.260, 0.470);
        model[1][3] = new FuzzySet(); model[1][3].newGaussian(1.326, 0.198);

        // 3. VIRGINICA
        model[2][0] = new FuzzySet(); model[2][0].newGaussian(6.588, 0.636);
        model[2][1] = new FuzzySet(); model[2][1].newGaussian(2.974, 0.322);
        model[2][2] = new FuzzySet(); model[2][2].newGaussian(5.552, 0.552);
        model[2][3] = new FuzzySet(); model[2][3].newGaussian(2.026, 0.275);

        System.out.println("Model rozmyty (dane statyczne) został zbudowany!\n");

        double[][] wektoryTestowe = {
                {5.1, 3.5, 1.4, 0.2},
                {6.5, 2.8, 4.6, 1.5},
                {7.2, 3.6, 6.1, 2.5}
        };

        for (double[] wektor : wektoryTestowe) {
            klasyfikujWektor(wektor, nazwyKlas, model);
        }
    }

    private static void klasyfikujWektor(double[] wektor, String[] nazwyKlas, FuzzySet[][] model) {
        System.out.printf("Klasyfikacja wektora: [%.1f, %.1f, %.1f, %.1f]\n", wektor[0], wektor[1], wektor[2], wektor[3]);
        String najlepszaKlasa = "Nieznany";
        double najwyzszyWynik = -1.0;

        for (int i = 0; i < 3; i++) {
            double sumaPrzynaleznosci = 0.0;
            for (int j = 0; j < 4; j++) {

                sumaPrzynaleznosci += model[i][j].getMembership(wektor[j]);
            }
            System.out.printf(" - Wynik dla %s: %.4f\n", nazwyKlas[i], sumaPrzynaleznosci);
            if (sumaPrzynaleznosci > najwyzszyWynik) {
                najwyzszyWynik = sumaPrzynaleznosci;
                najlepszaKlasa = nazwyKlas[i];
            }
        }
        System.out.println("--> WERDYKT: " + najlepszaKlasa + "!\n");
    }
}