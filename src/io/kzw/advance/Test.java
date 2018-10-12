package io.kzw.advance;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        System.out.println(getDisplayAddr("0xb912c65139a3bb03460975b7512702bce370ed25"));
    }

    private static final int MAX_DISPLAY_ADDR_LEN = 32;

    private static String getDisplayAddr(String addr) {
        if (addr.length() > MAX_DISPLAY_ADDR_LEN) {
            char[] origin = addr.toCharArray();

            char[] display = new char[MAX_DISPLAY_ADDR_LEN];
            Arrays.fill(display, '.');

            int binary = MAX_DISPLAY_ADDR_LEN >> 1;
            System.arraycopy(origin, 0, display, 0, binary);
            int limit = MAX_DISPLAY_ADDR_LEN - binary - 3;
            System.arraycopy(origin, origin.length - limit, display, binary + 3, limit);
            return new String(display);
        } else {
            return addr;
        }
    }
}
