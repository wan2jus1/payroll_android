package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.errors.RuntimeError;

@SimpleObject
public class FileAlreadyExistsError extends RuntimeError {

   public FileAlreadyExistsError(String var1) {
      super(var1);
   }
}
