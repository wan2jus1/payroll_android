package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.errors.RuntimeError;

@SimpleObject
public class IllegalArgumentError extends RuntimeError {

   public IllegalArgumentError() {
   }

   public IllegalArgumentError(String var1) {
      super(var1);
   }
}
