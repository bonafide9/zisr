package zbiory;

public class ZbiorGaussowski extends ZbiorRozmyty {
    private double m, sigma;

    public ZbiorGaussowski(String nazwa, double m, double sigma) {
        super(nazwa);
        this.m = m;
        this.sigma = sigma;
    }

    @Override
    public double obliczPrzynaleznosc(double x) {
        if (sigma == 0) return x == m ? 1.0 : 0.0;
        return Math.exp(-Math.pow(x - m, 2) / (2 * Math.pow(sigma, 2)));
    }
}