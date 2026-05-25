package zbiory;

public class ZbiorTrojkatny extends ZbiorRozmyty {
    private double a, b, c;

    public ZbiorTrojkatny(String nazwa, double a, double b, double c) {
        super(nazwa);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double obliczPrzynaleznosc(double x) {
        if (x <= a || x >= c) return 0.0;
        if (x == b) return 1.0;
        if (x < b) return (x - a) / (b - a);
        return (c - x) / (c - b);
    }
}