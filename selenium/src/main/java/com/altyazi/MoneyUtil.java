package com.altyazi;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class MoneyUtil {

    public static double toDouble(String moneyWithTL) {
        String money = moneyWithTL.replace(" TL", "");
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        double price = 0;
        try {
            price = df.parse(money).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Exception on parsing " + money, e);
        }

        return price;
    }
}
