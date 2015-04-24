package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.errors.RuntimeError;

@SimpleObject
public class FileIOError extends RuntimeError {

   public FileIOError(String var1) {
      super(var1);
   }
}
