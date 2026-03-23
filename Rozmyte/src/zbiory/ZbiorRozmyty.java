package zbiory;

public abstract class ZbiorRozmyty {
    protected String nazwa;

    public ZbiorRozmyty(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public abstract double obliczPrzynaleznosc(double x);
}