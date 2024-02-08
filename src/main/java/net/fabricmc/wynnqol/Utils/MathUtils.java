package net.fabricmc.wynnqol.Utils;

public class MathUtils {
    public static int getRandomIntInclusive(int min, int max){
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
    public static String convertToReadable(double number, int decimals) { // yoinked from avomod
        if (number >= 1000000000) {
            return String.format("%sB", Math.round(number / (1000000000 / Math.pow(10, decimals))) / Math.pow(10, decimals));
        } else if (number >= 1000000) {
            return String.format("%sM", Math.round(number / (1000000 / Math.pow(10, decimals))) / Math.pow(10, decimals));
        } else if (number >= 1000) {
            return String.format("%sK", Math.round(number / (1000 / Math.pow(10, decimals))) / Math.pow(10, decimals));
        }

        return String.valueOf((int) number);
    }
//    end of ctrl c + ctrl v from avomod
}
