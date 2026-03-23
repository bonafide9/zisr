import zbiory.ZbiorGaussowski;
import zbiory.ZbiorRozmyty;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String[] nazwyKlas = {"Iris-setosa", "Iris-versicolor", "Iris-virginica"};

        ZbiorRozmyty[][] model = zbudujModel("iris.data", nazwyKlas);

        double[][] wektoryTestowe = {
                {5.1, 3.5, 1.4, 0.2},
                {6.5, 2.8, 4.6, 1.5},
                {7.2, 3.6, 6.1, 2.5}
        };

        for (double[] wektor : wektoryTestowe) {
            klasyfikuj(wektor, model, nazwyKlas);
        }
    }


    static ZbiorRozmyty[][] zbudujModel(String plik, String[] nazwyKlas) throws Exception {
        Statystyki[][] stat = new Statystyki[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) stat[i][j] = new Statystyki();
        }

        List<String> linie = Files.readAllLines(Paths.get(plik));
        for (String linia : linie) {
            if (linia.isEmpty()) continue;
            String[] p = linia.split(";");

            int indeksKlasy = 0;
            if (p[4].equals("Iris-versicolor")) indeksKlasy = 1;
            else if (p[4].equals("Iris-virginica")) indeksKlasy = 2;

            for (int i = 0; i < 4; i++) {
                stat[indeksKlasy][i].dodaj(Double.parseDouble(p[i].replace(",", ".")));
            }
        }

        ZbiorRozmyty[][] model = new ZbiorRozmyty[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                stat[i][j].oblicz();
                model[i][j] = new ZbiorGaussowski(nazwyKlas[i], stat[i][j].srednia, stat[i][j].odchylenie);
            }
        }
        return model;
    }

    static void klasyfikuj(double[] wektor, ZbiorRozmyty[][] model, String[] nazwyKlas) {
        System.out.println("Klasyfikacja wektora: [" + wektor[0] + ", " + wektor[1] + ", " + wektor[2] + ", " + wektor[3] + "]");

        int wygranyIndeks = 0;
        double najwyzszyWynik = -1.0;

        for (int i = 0; i < 3; i++) {
            double suma = 0;
            for (int j = 0; j < 4; j++) {
                suma += model[i][j].obliczPrzynaleznosc(wektor[j]);
            }

            System.out.printf(" - %s: %.4f\n", nazwyKlas[i], suma);
            if (suma > najwyzszyWynik) {
                najwyzszyWynik = suma;
                wygranyIndeks = i;
            }
        }
        System.out.println("--> Wygrywa: " + nazwyKlas[wygranyIndeks] + "\n");
    }
}

class Statystyki {
    double suma = 0, sumaKw = 0;
    int ilosc = 0;
    double srednia, odchylenie;

    void dodaj(double v) {
        suma += v;
        sumaKw += v * v;
        ilosc++;
    }

    void oblicz() {
        srednia = suma / ilosc;
        odchylenie = Math.sqrt((sumaKw / ilosc) - (srednia * srednia));
    }
}