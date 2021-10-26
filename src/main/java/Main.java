import ciphering.CaesarCipher;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.math3.linear.MatrixUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //String[] alphabet = queryAlphabet();

        CaesarCipher c = new CaesarCipher("hgtszxu.uar", 28,
                new ImmutablePair<>(
                        MatrixUtils.createRealMatrix(
                                new double[][]{
                                        {15}
                                }
                        ),
                        MatrixUtils.createRealMatrix(new double[][]{
                                {24}
                        })
                ));
        System.out.println(c.cipher());
    }

    public static String @NotNull [] queryAlphabet() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Combien de lettres souhaitez vous dans l'alphabet: ");

        int size = scanner.nextInt();
        String[] alph = new String[size];

        for (int i = 0; i < size; i++) {
            System.out.printf("lettre(%d): ", i+1);
            alph[i] = scanner.next();
        }

        return alph;
    }
}
