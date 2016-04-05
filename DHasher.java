package com.vm.dhasher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.math.BigInteger;

public class DHasher {
    static int WINDOW_SIZE = 8;

    public DHasher() {
    }

    public DHasher(int WINDOW_SIZE) {
        this.WINDOW_SIZE = WINDOW_SIZE;
    }

    public static String hashFromBitmap(Bitmap bitmap) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, WINDOW_SIZE + 1, WINDOW_SIZE + 1, true);

        if (scaledBitmap == null) {
            return null;
        }
        int imageDimention = WINDOW_SIZE + 1;
        int[] pixels = new int[imageDimention * imageDimention];
        scaledBitmap.getPixels(pixels, 0, imageDimention, 0, 0, imageDimention, imageDimention);

        //generate the D-Hash
        BigInteger dHash = generateHash(pixels);

        //clean up
        scaledBitmap.recycle();

        if (dHash == null)
            return null;

        return dHash.toString();
    }

    public static String hashFromPath(String path) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inDither = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, WINDOW_SIZE + 1, WINDOW_SIZE + 1, true);

        if (scaledBitmap == null) {
            return null;
        }
        int imageDimention = WINDOW_SIZE + 1;
        int[] pixels = new int[imageDimention * imageDimention];
        scaledBitmap.getPixels(pixels, 0, imageDimention, 0, 0, imageDimention, imageDimention);

        //generate the D-Hash
        BigInteger dHash = generateHash(pixels);

        //clean up
        bitmap.recycle();
        scaledBitmap.recycle();

        if (dHash == null)
            return null;

        return dHash.toString();
    }

    private static BigInteger generateHash(int[] pixels) {
        int count = 0;
        BigInteger result = new BigInteger("0");

        if (pixels == null || pixels.length == 0) {
            return null;
        }

        for (int y = 0; y < WINDOW_SIZE; y++) {
            for (int x = 0; x < WINDOW_SIZE; x++) {

                int index = y * WINDOW_SIZE + x;
                int R = (pixels[index] >> 16) & 0xff;
                int G = (pixels[index] >> 8) & 0xff;
                int B = pixels[index] & 0xff;

                int greyLeft = (R + G + B) / 3;

                index = y * WINDOW_SIZE + (x + 1);
                R = (pixels[index] >> 16) & 0xff;
                G = (pixels[index] >> 8) & 0xff;
                B = pixels[index] & 0xff;

                int greyRight = (R + G + B) / 3;

                if (greyLeft > greyRight) {
                    BigInteger shifted = new BigInteger("1");
                    shifted = shifted.shiftLeft(count);
                    result = result.or(shifted);
                }

                count++;
            }
        }

        return result;
    }
}
