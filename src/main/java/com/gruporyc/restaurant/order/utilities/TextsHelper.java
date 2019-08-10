package com.gruporyc.restaurant.order.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class TextsHelper {

    @Value("${locale}")
    private String locale;

    public String getTranslation(String key) {
        return ResourceBundle.getBundle("texts", new Locale(locale)).getString(key);
    }
}