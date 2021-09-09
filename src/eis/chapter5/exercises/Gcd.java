package eis.chapter5.exercises;

import java.util.Random;

public class Gcd {

    private static int greatestCommonDivisor(int u, int v) {
        if (u == 0 || v == 0) {
            if (u == Integer.MIN_VALUE || v == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: gcd is 2^31");
            }
            return Math.abs(u) + Math.abs(v);
        }
        if (Math.abs(u) == 1 || Math.abs(v) == 1) {
            return 1;
        }
        if (u > 0) { u = -u; } 
        if (v > 0) { v = -v; } 
        int k = 0;
        while ((u & 1) == 0 && (v & 1) == 0 && k < 31) { 
            u /= 2;
            v /= 2;
            k++; 
        }
        if (k == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
        }
        int t = (u & 1) == 1 ? v : -(u / 2); 
        do {
            while ((t & 1) == 0) { t /= 2; }
            if (t > 0) { u = -t; } 
            else { v = t; }
            t = (v - u) / 2;
        } while (t != 0);

        return -u * (1 << k); 
    }

    public static void main(String ... args) {
        final Random random = new Random();
        final int ITERATIONS = 1000;

        for (int i=0; i<ITERATIONS; i++) {
            int a = random.nextInt(), b = random.nextInt();
            int g = greatestCommonDivisor(a, b);
            System.out.println(g);
        }
        
    }
}
