package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.runtime.errors.RuntimeError;

public class YailRuntimeError extends RuntimeError {

   private String errorType;


   public YailRuntimeError(String var1, String var2) {
      super(var1);
      this.errorType = var2;
   }

   public String getErrorType() {
      return this.errorType;
   }
}
