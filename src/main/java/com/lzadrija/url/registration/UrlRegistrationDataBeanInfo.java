package com.lzadrija.url.registration;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import org.springframework.beans.propertyeditors.URLEditor;

public class UrlRegistrationDataBeanInfo extends SimpleBeanInfo {

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {

        PropertyDescriptor urlDescriptor;
        try {
            urlDescriptor = new UrlDescriptor("url", UrlRegistrationData.class, new URLEditor());
            return new PropertyDescriptor[]{urlDescriptor};
        } catch (IntrospectionException ex) {
            throw new Error(ex.toString());
        }
    }

    private static class UrlDescriptor extends PropertyDescriptor {

        private final URLEditor urlEditor;

        public UrlDescriptor(String propertyName, Class<?> beanClass, URLEditor urlEditor) throws IntrospectionException {
            super(propertyName, beanClass);
            this.urlEditor = urlEditor;
        }

        @Override
        public URLEditor createPropertyEditor(Object bean) {
            return urlEditor;
        }
    }

}
