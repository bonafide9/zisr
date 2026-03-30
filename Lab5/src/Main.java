import fuzzlib.FuzzySet;
import fuzzlib.creators.OperationCreator;
import fuzzlib.norms.SNorm;
import fuzzlib.norms.TNorm;

public class Main {
    public static void main(String[] args) {

        double aktualnaTemp = 22.5;
        double aktualnaWilg = 75.0;

        System.out.println("Odczyt z czujników: " + aktualnaTemp + "°C, " + aktualnaWilg + "% wilgotności.\n");

        FuzzySet tempNiska = new FuzzySet();        tempNiska.newTriangle(15, 5);
        FuzzySet tempSrednia = new FuzzySet();      tempSrednia.newTriangle(20, 5);
        FuzzySet tempWysoka = new FuzzySet();       tempWysoka.newTriangle(25, 5, 15);

        FuzzySet wilgNiska = new FuzzySet();        wilgNiska.newTriangle(50, 30, 20);
        FuzzySet wilgSrednia = new FuzzySet();      wilgSrednia.newTriangle(70, 10);
        FuzzySet wilgWysoka = new FuzzySet();       wilgWysoka.newTriangle(80, 10, 20);

        FuzzySet mocMala = new FuzzySet();          mocMala.newTriangle(0, 50);
        FuzzySet mocSrednia = new FuzzySet();       mocSrednia.newTriangle(50, 50);
        FuzzySet mocDuza = new FuzzySet();          mocDuza.newTriangle(100, 50);

        FuzzySet nawiewSlaby = new FuzzySet();      nawiewSlaby.newTriangle(20, 30);
        FuzzySet nawiewSredni = new FuzzySet();     nawiewSredni.newTriangle(60, 30);
        FuzzySet nawiewMocny = new FuzzySet();      nawiewMocny.newTriangle(100, 30);

        double w1 = Math.min(tempNiska.getMembership(aktualnaTemp), wilgNiska.getMembership(aktualnaWilg));
        double w2 = Math.min(tempNiska.getMembership(aktualnaTemp), wilgSrednia.getMembership(aktualnaWilg));
        double w3 = Math.min(tempNiska.getMembership(aktualnaTemp), wilgWysoka.getMembership(aktualnaWilg));

        double w4 = Math.min(tempSrednia.getMembership(aktualnaTemp), wilgNiska.getMembership(aktualnaWilg));
        double w5 = Math.min(tempSrednia.getMembership(aktualnaTemp), wilgSrednia.getMembership(aktualnaWilg));
        double w6 = Math.min(tempSrednia.getMembership(aktualnaTemp), wilgWysoka.getMembership(aktualnaWilg));

        double w7 = Math.min(tempWysoka.getMembership(aktualnaTemp), wilgNiska.getMembership(aktualnaWilg));
        double w8 = Math.min(tempWysoka.getMembership(aktualnaTemp), wilgSrednia.getMembership(aktualnaWilg));
        double w9 = Math.min(tempWysoka.getMembership(aktualnaTemp), wilgWysoka.getMembership(aktualnaWilg));

        TNorm tMin = OperationCreator.newTNorm(TNorm.TN_MINIMUM);
        SNorm sMax = (SNorm) OperationCreator.newSNorm(SNorm.SN_MAXIMUM);

        double[] wagi = {w1, w2, w3, w4, w5, w6, w7, w8, w9};
        FuzzySet[] propMoc = {mocMala, mocMala, mocSrednia, mocMala, mocSrednia, mocSrednia, mocSrednia, mocDuza, mocDuza};
        FuzzySet[] propNawiew = {nawiewSlaby, nawiewSlaby, nawiewSlaby, nawiewSredni, nawiewSredni, nawiewMocny, nawiewMocny, nawiewMocny, nawiewMocny};

        FuzzySet wynikMoc = new FuzzySet();
        wynikMoc.addPoint(0, 0);
        wynikMoc.addPoint(100, 0);

        FuzzySet wynikNawiew = new FuzzySet();
        wynikNawiew.addPoint(0, 0);
        wynikNawiew.addPoint(100, 0);

        for (int i = 0; i < 9; i++) {
            if (wagi[i] <= 0.0) continue;

            FuzzySet wagaSet = new FuzzySet();
            wagaSet.addPoint(-10, wagi[i]);
            wagaSet.addPoint(110, wagi[i]);

            FuzzySet ucietaMoc = new FuzzySet();
            FuzzySet ucietyNawiew = new FuzzySet();

            FuzzySet.processSetsWithNorm(ucietaMoc, propMoc[i], wagaSet, tMin);
            FuzzySet.processSetsWithNorm(ucietyNawiew, propNawiew[i], wagaSet, tMin);

            FuzzySet nowyWynikMoc = new FuzzySet();
            FuzzySet nowyWynikNawiew = new FuzzySet();

            FuzzySet.processSetsWithNorm(nowyWynikMoc, wynikMoc, ucietaMoc, sMax);
            FuzzySet.processSetsWithNorm(nowyWynikNawiew, wynikNawiew, ucietyNawiew, sMax);

            wynikMoc = nowyWynikMoc;
            wynikNawiew = nowyWynikNawiew;
        }

        wynikMoc.PackFlatSections();
        wynikNawiew.PackFlatSections();

        double ustalonaMoc = wynikMoc.DeFuzzyfy();
        double ustalonyNawiew = wynikNawiew.DeFuzzyfy();

        long mocFinal = Math.max(0, Math.min(100, Math.round(ustalonaMoc)));
        long nawiewFinal = Math.max(0, Math.min(100, Math.round(ustalonyNawiew)));

        System.out.println("DECYZJA:");
        System.out.println("Moc chłodzenia: " + mocFinal + "%");
        System.out.println("Siła nawiewu: " + nawiewFinal + "%");
    }
}