import fuzzlib.FuzzySet;
import fuzzlib.creators.OperationCreator;
import fuzzlib.norms.TNorm;

/*

    kllimatyzator: chłodzenie i nawiew na podstawie wilgotnosci i temp w pomeiszczeniu na za tydzien
    podajemy mu wartosc temp i wilgotnosci i on ma zdeycydowac jaka moc chłodzenia i wiania ustawic

    1. deklarujemy wartosci dla temperatury i wilgotnosci
    2. tworzymy zbiory rozmyte
    3. łączymy te zbiory t norma?
    4.????
    5. defuzzyfikacja, jakies wartosci wychodza



    kontroler rozmyty
    2 cechy wejsciowe (temp i wilgotnosc, 3 stopnienatężenia jakby)
    2 parametry wyjsciowe (moc i nawies)
 */
/*

    kllimatyzator: chłodzenie i nawiew na podstawie wilgotnosci i temp w pomeiszczeniu na za tydzien
    podajemy mu wartosc temp i wilgotnosci i on ma zdeycydowac jaka moc chłodzenia i wiania ustawic
 */


public class x {
    public static void main(String[] args) {

//        FuzzySet tempNiska = new FuzzySet();
//        tempNiska.newTriangle(10.0, 20.0, 10.0);
//
//        FuzzySet tempWysoka = new FuzzySet();
//        tempWysoka.newTriangle(40.0, 20.0, 10.0);
//
//
//        FuzzySet wilgNiska = new FuzzySet();
//        wilgNiska.newTrapezium(20.0, 40.0, 20.0);
//
//        FuzzySet wilgWysoka = new FuzzySet();
//        wilgWysoka.newTrapezium(80.0, 40.0, 20.0);
//
//
//        double testTemp = 25.0;
//        double testWilg = 45.0;
//
//        System.out.println("--- WYNIKI DLA TEMPERATURY " + testTemp + "°C ---");
//        System.out.println("Przynależność do Niskiej: " + tempNiska.getMembership(testTemp));
//        System.out.println("Przynależność do Wysokiej: " + tempWysoka.getMembership(testTemp));
//
//        System.out.println("\n--- WYNIKI DLA WILGOTNOŚCI " + testWilg + "% ---");
//        System.out.println("Przynależność do Niskiej: " + wilgNiska.getMembership(testWilg));
//        System.out.println("Przynależność do Wysokiej: " + wilgWysoka.getMembership(testWilg));

        FuzzySet f = new FuzzySet();
        FuzzySet g = new FuzzySet();
        FuzzySet h = new FuzzySet();
//		f.AddPoint(0,0);
//		f.AddPoint(2,1);
//		f.AddPoint(2.001,0);
//		f.AddPoint(3,0);
        f.newGaussian(4,0.5);
        g.newGaussian(6,0.5);

        TNorm t = OperationCreator.newTNorm(TNorm.TN_PRODUCT);
        FuzzySet.processSetsWithNorm(h, f, g, t);
        System.out.println("Zbiór f:" + f);
        System.out.println("Zbiór g:" + g);
        System.out.println("f *T g:" + h);
        System.out.println("Wyostrzenie h:" + h.DeFuzzyfy());
        h.PackFlatSections();
        System.out.println("f *T g:" + h);
        System.out.println("Wyostrzenie h:" + h.DeFuzzyfy());
    }
}