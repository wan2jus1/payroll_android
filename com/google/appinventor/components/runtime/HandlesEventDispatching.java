package com.google.appinventor.components.runtime;

import com.google.appinventor.components.runtime.Component;

public interface HandlesEventDispatching {

   boolean canDispatchEvent(Component var1, String var2);

   boolean dispatchEvent(Component var1, String var2, String var3, Object[] var4);
}
