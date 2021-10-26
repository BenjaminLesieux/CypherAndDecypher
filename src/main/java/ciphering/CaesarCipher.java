package ciphering;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.linear.RealMatrix;


import java.util.ArrayList;
import java.util.List;

public class CaesarCipher extends Cipher {

    public CaesarCipher(String message, int sizeAlphabet, Pair<RealMatrix, RealMatrix> keys) {
        super(message, sizeAlphabet, keys);
    }

    public CaesarCipher(String message, String[] precisedAlphabet, Pair<RealMatrix, RealMatrix> keys) {
        super(message, precisedAlphabet, keys);
    }

    public String cipher() {

        StringBuilder strB = new StringBuilder();

        RealMatrix leftMatrix = keys.getLeft();
        RealMatrix constantMatrix = keys.getRight();

        int columnDimension = leftMatrix.getColumnDimension();

        RealMatrix messageMatrix = getMessageMatrix(columnDimension);
        String str = translateStringMatrix(messageMatrix);
        System.out.println(str);
        System.out.println(precisedAlphabet.length + ":;");

        List<RealMatrix> decompositionMatrix = new ArrayList<>();

        for (int i = 0; i < messageMatrix.getColumnDimension(); i++) {
            decompositionMatrix.add(messageMatrix.getColumnMatrix(i));
        }

        List<RealMatrix> resultedOpMatrix = new ArrayList<>();

        for (RealMatrix decomposedMatrix : decompositionMatrix) {
            RealMatrix op = leftMatrix.multiply(decomposedMatrix).add(constantMatrix);
            resultedOpMatrix.add(op);
        }

        List<RealMatrix> resultedModMatrix = calculateModMatrix(resultedOpMatrix);
        resultedModMatrix.forEach(matrix -> {
            strB.append(translateStringMatrix(matrix.transpose()));
            System.out.println(matrix.transpose());
        });

        return strB.toString();
    }

    public String[] getPrecisedAlphabet() {
        return precisedAlphabet;
    }
}
