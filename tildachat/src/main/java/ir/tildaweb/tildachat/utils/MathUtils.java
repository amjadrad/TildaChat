package ir.tildaweb.tildachat.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;


public class MathUtils {

    private static String TAG = "MathUtils";

    public static int convertDipToPixels(Context context, float dips) {
        return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int convertPxToDp(Context context, float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density - 0.5f);
    }

    public static String convertNumberToKilo(int number) {
        String result = number + "";
        if (number > 1000) {
            return String.format("%.1f", (float) number / 1000) + "k";
        } else {
            return result;
        }
    }

    public static String convertToWithComma(int number) {

        String num = String.valueOf(number);
        StringBuilder stringBuilder = new StringBuilder();

        int counter = 0;
        for (int i = num.length() - 1; i > -1; i--) {
            if (counter == 3) {
                stringBuilder.append(",");
                stringBuilder.append(num.charAt(i));
                counter = 1;
                continue;
            }
            counter++;
            stringBuilder.append(num.charAt(i));
        }
        return stringBuilder.reverse().toString();


    }

    public static int computeDiscount(int number, int discount) {
        return (int) ((number * discount) / 100f);
    }

    public static int computeNumberWithDiscount(int number, int discount) {
        return number - (int) ((number * discount) / 100f);
    }

    public static String computeDiscountTwoNumber(int numberFrom, int numberTo) {
        String discount = String.format("%.1f", (Math.abs((numberFrom - numberTo) / (float) numberFrom) * 100));
        if (discount.equals("NaN")) {
            discount = "0";
        }
        return discount;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
