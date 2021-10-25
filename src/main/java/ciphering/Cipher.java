package ciphering;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.AnyMatrix;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.BigReal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class Cipher {

    private String message;
    protected final String[] alphabet = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z", " ", "."
    };
    protected final String[] precisedAlphabet;


    public Cipher(String message, int size) {
        this.message = message;
        this.precisedAlphabet = buildAlphabet(size);
    }

    public Cipher(String message, String[] precisedAlphabet) {
        this.message = message;
        this.precisedAlphabet = precisedAlphabet;
    }

    protected String @NotNull [] buildAlphabet(int size) {
        if (size > 28)
            throw new IllegalStateException("Our alphabet cannot be bigger than 28");

        String[] al = Arrays.copyOf(this.alphabet, size+2);
        al[al.length-2] = this.alphabet[26];
        al[al.length-1] = this.alphabet[27];

        return al;
    }

    protected RealMatrix getAlphabetMatrix(String[] alphabet) {
        double[][] values = new double[1][alphabet.length];

        for (int i = 0; i < alphabet.length; i++) {
            values[0][i] = (double) i % alphabet.length;
        }

        return MatrixUtils.createRealMatrix(values);
    }

    @Contract("_ -> new")
    protected static @NotNull RealMatrix matrix(double[][] toBe) {
        return MatrixUtils.createRealMatrix(toBe);
    }

    protected RealMatrix getMessageMatrix(int columnSize) {
        int rowSize = message.length()/columnSize;
        RealMatrix resultMatrix = MatrixUtils.createRealMatrix(rowSize, columnSize);

        int counter = 0;
        double[][] resultArrMatrix = new double[resultMatrix.getRowDimension()][];

        for (int i = 0; i < rowSize; i++) {

            double[] values = new double[columnSize];

            for (int j = 0; j < columnSize; j++) {
                String letter = String.valueOf(message.charAt(counter));
                values[j] = ArrayUtils.indexOf(precisedAlphabet, letter);
                counter++;
            }

            resultArrMatrix[i] = values;
        }

        resultMatrix = MatrixUtils.createRealMatrix(resultArrMatrix).transpose();

        return resultMatrix;
    }

    public String getMessage() {
        return message;
    }
}
