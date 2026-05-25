import fuzzlib.FuzzySet;

public class Main {

    public static void main(String[] args) throws Exception {
        FuzzySet setosa = new FuzzySet();

        setosa.newGaussian(5.006, 0.352); // SL
        setosa.newGaussian(3.418, 0.381); // SW
        setosa.newGaussian(1.464, 0.174); // PL
        setosa.newGaussian(0.244, 0.107); // PW

        FuzzySet versicolor = new FuzzySet();

        versicolor.newGaussian(5.936, 0.516); // SL
        versicolor.newGaussian(2.770, 0.314); // SW
        versicolor.newGaussian(4.260, 0.470); // PL
        versicolor.newGaussian(1.326, 0.198); // PW

        FuzzySet virginica = new FuzzySet();

        virginica.newGaussian(6.588, 0.636); // SL
        virginica.newGaussian(2.974, 0.322); // SW
        virginica.newGaussian(5.552, 0.552); // PL
        virginica.newGaussian(2.026, 0.275); // PW

        System.out.println("Setosa: ");
        System.out.println(setosa);
        System.out.println("\nVersicolor: ");
        System.out.println(versicolor);
        System.out.println("\nVirginica: ");
        System.out.println(virginica);
    }
}