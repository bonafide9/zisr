package zbiory;

public class ZbiorTrapezowy extends ZbiorRozmyty {
    private double a, b, c, d;

    public ZbiorTrapezowy(String nazwa, double a, double b, double c, double d) {
        super(nazwa);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double obliczPrzynaleznosc(double x) {
        if (x <= a || x >= d) return 0.0;
        if (x >= b && x <= c) return 1.0;
        if (x < b) return (x - a) / (b - a);
        return (d - x) / (d - c);
    }
}