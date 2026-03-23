import fuzzlib.FuzzySet;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    static class Statystyki {
        double suma = 0, sumaKwadratow = 0;
        int liczbaElementow = 0;
        double srednia, odchylenieStd;

        void dodajWartosc(double val) {
            suma += val;
            sumaKwadratow += val * val;
            liczbaElementow++;
        }

        void oblicz() {
            srednia = suma / liczbaElementow;
            double wariancja = (sumaKwadratow / liczbaElementow) - (srednia * srednia);
            odchylenieStd = wariancja > 0 ? Math.sqrt(wariancja) : 0;
        }
    }

    public static void main(String[] args) throws Exception {
        String[] nazwyKlas = {"Iris-setosa", "Iris-versicolor", "Iris-virginica"};

        Statystyki[][] statystykiKlas = new Statystyki[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) statystykiKlas[i][j] = new Statystyki();
        }

        List<String> linie = Files.readAllLines(Paths.get("iris.data"));
        for (String linia : linie) {
            if (linia.isEmpty()) continue;
            String[] czesci = linia.split(";");

            int klasa = 0;
            if (czesci[4].equals("Iris-versicolor")) klasa = 1;
            else if (czesci[4].equals("Iris-virginica")) klasa = 2;

            for (int i = 0; i < 4; i++) {
                statystykiKlas[klasa][i].dodajWartosc(Double.parseDouble(czesci[i].replace(",", ".")));
            }
        }

        FuzzySet[][] model = new FuzzySet[3][4];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                statystykiKlas[i][j].oblicz();

                // Tworzymy nowy zbiór rozmyty z biblioteki fuzzlib
                FuzzySet fs = new FuzzySet();

                // fuzzlib posiada wbudowaną metodę do generowania dzwonu Gaussa
                // Przyjmuje ona środek (średnią) oraz szerokość (odchylenie)
                fs.newGaussian(statystykiKlas[i][j].srednia, statystykiKlas[i][j].odchylenieStd);

                model[i][j] = fs;
            }
        }

        System.out.println("Model rozmyty (oparty na fuzzlib) został zbudowany pomyślnie!\n");

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

            System.out.printf(" - Wynik sumaryczny dla %s: %.4f\n", nazwyKlas[i], sumaPrzynaleznosci);

            if (sumaPrzynaleznosci > najwyzszyWynik) {
                najwyzszyWynik = sumaPrzynaleznosci;
                najlepszaKlasa = nazwyKlas[i];
            }
        }

        System.out.println("--> WERDYKT: Zwycięża " + najlepszaKlasa + "!\n");
    }
}