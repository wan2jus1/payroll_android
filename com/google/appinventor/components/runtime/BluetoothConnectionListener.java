package com.google.appinventor.components.runtime;

import com.google.appinventor.components.runtime.BluetoothConnectionBase;

interface BluetoothConnectionListener {

   void afterConnect(BluetoothConnectionBase var1);

   void beforeDisconnect(BluetoothConnectionBase var1);
}
