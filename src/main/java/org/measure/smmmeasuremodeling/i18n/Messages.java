package org.measure.smmmeasuremodeling.i18n;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("a35b39bb-e3bc-416f-bda1-c2e414517414")
public class Messages {
    @objid ("74eed250-ec65-4f88-96e0-b9546c8ac7fe")
    private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle ("org.measure.smmmeasuremodeling.i18n.messages");

    @objid ("ac7f17ba-3ab5-421e-9353-9fc309fc8b20")
    private Messages() {
    }

    @objid ("310759a7-75fa-4c90-b832-1b5de1ff96bb")
    public static String getString(final String key) {
        try {
            return RESOURCE_BUNDLE.getString (key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    @objid ("6e2f733e-c398-4e5a-bf10-2221cbc07746")
    public static String getString(final String key, final String... params) {
        try {
            return MessageFormat.format (RESOURCE_BUNDLE.getString (key),(Object[]) params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

}
