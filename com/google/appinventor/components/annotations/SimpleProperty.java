package com.google.appinventor.components.annotations;

import com.google.appinventor.components.annotations.PropertyCategory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SimpleProperty {

   PropertyCategory category() default PropertyCategory.UNSET;

   String description() default "";

   boolean userVisible() default true;
}
