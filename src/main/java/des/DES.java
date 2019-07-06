/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import java.math.BigInteger;
import java.util.*;

/**
 * @author amir
 */
public class DES {

    private List<Integer> initialPermutation;
    private List<Integer> reversePermutation;
    private List<Integer> roundPermutation;
    private List<String> roundKeys = new ArrayList<>();

    private static int sb[][][] = {

            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            }, {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    }, {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    }, {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    }, {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    }, {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    }, {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    }, {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    }};

    private Integer[] straightPermutation = {
            15, 6, 19, 20, 28, 11, 27, 16,
            0, 25, 22, 25, 4, 17, 30, 9,
            1, 7, 23, 13, 31, 26, 2, 8,
            18, 12, 29, 5, 21, 10, 3, 24
    };

    private Integer[] pc1Left = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36
    };

    private Integer[] pc1Right = {
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    private Integer[] pc2 = {
            13, 16, 10, 23, 0, 4, 2, 27,
            14, 5, 20, 9, 22, 18, 11, 3,
            25, 7, 15, 6, 26, 19, 12, 1,
            40, 51, 30, 36, 46, 54, 29, 39,
            50, 44, 32, 47, 43, 48, 38, 55,
            33, 52, 45, 41, 49, 35, 28, 31
    };

    private String leftKey, rightKey;

    private List<Integer> roundExceptionList = new ArrayList<>(Arrays.asList(1, 2, 9, 16));


    public DES(String key) {
        if (key.length() == 8)
            keyGenerationSetup(getBinary(key));
        else
            throw new IllegalArgumentException("ddd");
    }

    public static void main(String[] args) {
        DES des = new DES("asdfghjk");
        String absdfghx = des.encrypt("absdfghx");
        System.out.println(absdfghx);
        System.out.println(des.decrypt(absdfghx));
    }


    private void keyGenerationSetup(String key) {
        if (key.length() == 64) {
            leftKey = permutation(key, Arrays.asList(pc1Left));
            rightKey = permutation(key, Arrays.asList(pc1Right));
            for (int i = 0; i < 16; i++) {
                roundKeys.add(getRoundKey(i));
            }
        }else
            throw new IllegalArgumentException("key is "+ key.length());
    }

    private String getRoundKey(int round) {
        leftShift(leftKey, roundExceptionList.contains(round) ? 1 : 2);
        leftShift(rightKey, roundExceptionList.contains(round) ? 1 : 2);
        return permutation(leftKey + rightKey, Arrays.asList(pc2));
    }

    private void leftShift(String text, int amount) {
        text = text.substring(amount) + text.substring(0, amount);
    }


    private void generateInitialPermutations() {
        initialPermutation = new ArrayList<>();
        Integer[] a = new Integer[64];
        for (int i = 0; i < 64; i++) {
            initialPermutation.add(i);
        }
        Collections.shuffle(initialPermutation, new Random());
        for (Integer i : initialPermutation) {
            a[initialPermutation.get(i)] = i;
        }
        reversePermutation = new ArrayList<>(Arrays.asList(a));
    }

    private void generateRoundPermutations() {
        roundPermutation = new ArrayList<>();
        roundPermutation.add(31);
        for (int i = 0; i < 31; i++) {
            roundPermutation.add(i);
            if ((i + 1) % 4 == 0) {
                roundPermutation.add(i + 1);
                roundPermutation.add(i);
            }
        }
        //fixme
        roundPermutation.add(31);
        roundPermutation.add(0);
    }

    public String encrypt(String text) {
        if (text.length() % 8 == 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < text.length() / 8; i++) {
                builder.append(partialEncryption(text.substring(i * 8, (i + 1) * 8), true));
            }
            return getCharFromBinary(builder.toString());
        }
        return null;
    }

    private String getCharFromBinary(String binary){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < binary.length() / 8; i++) {
            builder.append((char)Integer.parseInt(new BigInteger(binary.substring(i*8,(i+1)*8),2).toString(10)));
        }
        return builder.toString();
    }

    public String decrypt(String text) {
        System.out.println(text.length());
        if (text.length() % 8 == 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < text.length() / 8; i++) {
                builder.append(partialEncryption(text.substring(i * 8, (i + 1) * 8), false));
            }
            return getCharFromBinary(builder.toString());
        }
        return null;
    }


    private String partialEncryption(String text, boolean encrypt) {
        if (text.length() == 8) {
            generateInitialPermutations();
            permutation(getBinary(text), initialPermutation);
            generateRoundPermutations();
            String binaryText = getBinary(text);
            String left;
            String right;
            if (encrypt) {
                left = binaryText.substring(0, 32);
                right = binaryText.substring(32);
            } else {
                right = binaryText.substring(0, 32);
                left = binaryText.substring(32);
            }
            for (int i = 0; i < 16; i++) {
                String temp = round(left, right, encrypt ? roundKeys.get(i) : roundKeys.get(15 - i));
                left = right;
                right = temp;
            }
            return permutation(left + right, reversePermutation);
        }
        return null;
    }

    private String round(String rightString, String left, String Key) {
        String right = String.valueOf(rightString);

        right = permutation(right, roundPermutation);
        BigInteger r = new BigInteger(right, 2);
        BigInteger key = new BigInteger(Key, 2);
        String cipherText = r.xor(key).or(new BigInteger("1000000000000",16)).toString(2).substring(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cipherText.length() / 6; i++) {
            String str = sBox(cipherText.substring(i * 6, (i + 1) * 6), i);
            builder.append(str);
        }

        String permutation = permutation(builder.toString(), Arrays.asList(straightPermutation));
        right = new BigInteger(permutation, 2).xor(new BigInteger(left, 2)).or(new BigInteger("100000000",16)).toString(2).substring(1);
        return right;

    }

    private String sBox(String cipherText, int id) {
        int row = Integer.parseInt(new BigInteger(cipherText.charAt(0) + "" + cipherText.charAt(5), 2).toString());
        int column = Integer.parseInt(new BigInteger(cipherText.substring(1, 5), 2).toString());

        return new BigInteger(String.valueOf(sb[id][row][column])).or(BigInteger.valueOf(16)).toString(2).substring(1);

    }

    private String permutation(String binaryString, List<Integer> pBox) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < pBox.size(); i++) {
            try {

                builder.append(binaryString.charAt(pBox.get(i)));
            }catch (IndexOutOfBoundsException e){
                System.out.println(i + " : " + pBox.get(i) + " : " + binaryString.length());
            }
        }
        return builder.toString();
    }

    private String getBinary(String text) {
        StringBuilder pureText = new StringBuilder();
        for (int i : stringToAsciiArray(text)) {
            pureText.append(Integer.toBinaryString(0x100 | i).substring(1));
        }
        return pureText.toString();
    }

    private int[] stringToAsciiArray(String text) {
        int[] a = new int[8];
        int i = 0;
        for (char c : text.toCharArray()) {
            a[i++] = c;
        }
        return a;
    }

}
