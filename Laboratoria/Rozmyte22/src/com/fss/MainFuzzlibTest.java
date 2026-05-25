package com.fss;

import fuzzlib.FuzzySet;
import fuzzlib.creators.OperationCreator;
import fuzzlib.norms.Norm;
import fuzzlib.norms.SNorm;
import fuzzlib.norms.TNorm;

public class MainFuzzlibTest {
    public static void main(String[] args) {

        FuzzySet fs = new FuzzySet();
        fs.addPoint(-2,0);
        fs.addPoint(0,1);
        fs.addPoint(2,0);

        FuzzySet fs2 = new FuzzySet();
        FuzzySet fs3 = new FuzzySet();
        FuzzySet fs4 = new FuzzySet();

        fs.newTriangle(0,1);
        fs2.newGaussian(0,2);

        fs.fuzzyfy(-5);

        Norm op = OperationCreator.newTNorm(SNorm.SN_MAXIMUM);

        FuzzySet.processSetsWithNorm(fs4,fs,fs2,op);

        fs4.PackFlatSections();

        System.out.println(fs4);
        System.out.println(fs4.getMax_membership().y);
    }
}
