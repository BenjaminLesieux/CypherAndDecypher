package ciphering;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CaesarCipher extends Cipher {

    private Pair<RealMatrix, RealMatrix> keys;

    public CaesarCipher(String message, int sizeAlphabet) {
        super(message, sizeAlphabet);
    }

    public CaesarCipher(String message, String[] precisedAlphabet) {
        super(message, precisedAlphabet);
    }

    public Pair<RealMatrix, RealMatrix> getKeys() {
        return keys;
    }

    public String cipher(@NotNull Pair<RealMatrix, RealMatrix> keys) {

        StringBuilder strB = new StringBuilder();

        RealMatrix leftMatrix = keys.getLeft();
        RealMatrix constantMatrix = keys.getRight();

        int columnDimension = leftMatrix.getColumnDimension();

        RealMatrix messageMatrix = getMessageMatrix(columnDimension);
        String str = translateStringMatrix(messageMatrix);
        System.out.println(str);

        List<RealMatrix> decompositionMatrix = new ArrayList<>();

        for (int i = 0; i < messageMatrix.getColumnDimension(); i++) {
            decompositionMatrix.add(messageMatrix.getColumnMatrix(i));
        }

        List<RealMatrix> resultedOpMatrix = new ArrayList<>();

        for (RealMatrix decomposedMatrix : decompositionMatrix) {
            RealMatrix op = leftMatrix.multiply(decomposedMatrix).add(constantMatrix);
            System.out.println(op);
            resultedOpMatrix.add(op);
        }

        List<RealMatrix> resultedModMatrix = calculateModMatrix(resultedOpMatrix);
        resultedModMatrix.forEach(matrix -> {
            strB.append(translateStringMatrix(matrix.transpose()));
            System.out.println(matrix);
        });

        return strB.toString();
    }

    public String[] getPrecisedAlphabet() {
        return precisedAlphabet;
    }

    private List<RealMatrix> calculateModMatrix(List<RealMatrix> resultedOpMatrix) {

        List<RealMatrix> resultedModMatrixes = new ArrayList<>();

        for (RealMatrix resultedMatrix : resultedOpMatrix) {
            double[] modValues = new double[resultedMatrix.getRowDimension()];

            for (int i = 0; i < resultedMatrix.getRowDimension(); i++) {
                modValues[i] = resultedMatrix.getRow(i)[0]%(precisedAlphabet.length);
            }

            resultedModMatrixes.add(MatrixUtils.createColumnRealMatrix(modValues));
        }

        return resultedModMatrixes;
    }

    private String translateStringMatrix(RealMatrix resultedMatrix) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < resultedMatrix.getColumnDimension(); i++) {
            for (int j = 0; j < resultedMatrix.getColumn(i).length; j++) {
                int indexValue = (int) resultedMatrix.getColumn(i)[j];
                stringBuilder.append(precisedAlphabet[indexValue%precisedAlphabet.length]);
            }
        }

        return stringBuilder.toString();
    }

    @Contract("_, _ -> new")
    public static @NotNull CaesarCipher makeCipher(String message, int sizeAlphabet) {
        return new CaesarCipher(message, sizeAlphabet);
    }
}
