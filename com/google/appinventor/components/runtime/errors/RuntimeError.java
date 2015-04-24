package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.errors.ArrayIndexOutOfBoundsError;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.errors.UninitializedInstanceError;

@SimpleObject
public abstract class RuntimeError extends RuntimeException {

   protected RuntimeError() {
   }

   protected RuntimeError(String var1) {
      super(var1);
   }

   public static RuntimeError convertToRuntimeError(Throwable var0) {
      if(var0 instanceof RuntimeError) {
         return (RuntimeError)var0;
      } else if(var0 instanceof ArrayIndexOutOfBoundsException) {
         return new ArrayIndexOutOfBoundsError();
      } else if(var0 instanceof IllegalArgumentException) {
         return new IllegalArgumentError();
      } else if(var0 instanceof NullPointerException) {
         return new UninitializedInstanceError();
      } else {
         throw new UnsupportedOperationException(var0);
      }
   }
}
