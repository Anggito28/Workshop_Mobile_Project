package com.kelompok2.rudibonsai.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MyFormatter {

    public static String idrFormat(int amount) {
        DecimalFormat idFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols idFormatSymbols = new DecimalFormatSymbols();

        idFormatSymbols.setCurrencySymbol("Rp ");
        idFormatSymbols.setMonetaryDecimalSeparator(',');
        idFormatSymbols.setGroupingSeparator('.');

        idFormat.setDecimalFormatSymbols(idFormatSymbols);

        return idFormat.format(amount);
    }
}
