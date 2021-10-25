import ciphering.CaesarCipher;
import ciphering.Cipher;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //String[] alphabet = queryAlphabet();
        String[] alphabet = {
               "a", "b", "c", "d", "e", "f",
               "g", "h", "i", " "
        };

        CaesarCipher c = new CaesarCipher("acceded beach ahead", alphabet);
        System.out.println(c.cipher(new ImmutablePair<>(
                MatrixUtils.createRealMatrix(
                        new double[][]{
                                {1, 2, 1},
                                {0, 3, 5},
                                {0, 0, 7}
                        }
                ),
                MatrixUtils.createRealMatrix(new double[][]{
                        {0},{0},{0}
                })
        )));
    }

    public static String[] queryAlphabet() {
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
