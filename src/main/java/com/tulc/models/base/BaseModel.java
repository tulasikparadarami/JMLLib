package com.tulc.models.base;

import java.io.IOException;
import java.util.Vector;

import com.tulc.math.Matrix;
import com.tulc.math.MatrixUtils;
import com.tulc.optimization.GradientDescent;
import com.tulc.optimization.GradientDescentOptions;

@SuppressWarnings("rawtypes")
public class BaseModel {
    protected Matrix X;
    protected Vector y;
    protected Vector<Double> theeta;
    protected Matrix train_X;
    protected Matrix test_X;
    protected Vector<Double> train_y;
    protected Vector<Double> test_y;
    
    public BaseModel(Matrix X, Vector<Double> y) {
        this.X = X;
        this.y = y;
        this.train_X = X;
        this.train_y = y;
        this.test_X = X;
        this.test_y = y;
    }
    
    @SuppressWarnings("unchecked")
    public void split(Double trainRatio) throws IOException {
        int splitPoint = (int) (X.numOfRows() * trainRatio);
        train_X = X.subsetRows(1, splitPoint);
        train_y = (Vector) y.subList(1, splitPoint);
        
        test_X = X.subsetRows(splitPoint + 1, X.numOfRows());
        test_y = (Vector) y.subList(splitPoint + 1, y.size());
        
    }
    
    public Vector<Double> train() throws IOException {
        GradientDescentOptions gdo = new GradientDescentOptions();
        gdo.setNumOfIter(1000000);
        gdo.setMseGain(0.00001);
        GradientDescent gd = new GradientDescent(0.01, train_X, train_y, gdo);
        theeta = gd.getTheeta();
        return theeta;
    }
    
    public Vector<Double> getTheeta() {
        return theeta;
    }
    
    public Vector predict(Matrix X) throws IOException {
        Vector<Double> pred_Y = new Vector<Double>(X.numOfRows());
        MatrixUtils mu = new MatrixUtils();
        for(int i = 0; i < X.numOfRows(); i++) {
            pred_Y.add(i, mu.dotProduct(X.getRow(i), theeta));
        }
        return pred_Y;
    }
}
