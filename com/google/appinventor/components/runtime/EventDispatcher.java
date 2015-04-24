package com.google.appinventor.components.runtime;

import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EventDispatcher {

   private static final boolean DEBUG = false;
   private static final Map mapDispatchDelegateToEventRegistry = new HashMap();


   private static boolean delegateDispatchEvent(HandlesEventDispatching var0, Set var1, Component var2, Object ... var3) {
      boolean var5 = false;
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         EventDispatcher.EventClosure var4 = (EventDispatcher.EventClosure)var6.next();
         if(var0.dispatchEvent(var2, var4.componentId, var4.eventName, var3)) {
            var5 = true;
         }
      }

      return var5;
   }

   public static boolean dispatchEvent(Component var0, String var1, Object ... var2) {
      boolean var5 = false;
      HandlesEventDispatching var3 = var0.getDispatchDelegate();
      boolean var4 = var5;
      if(var3.canDispatchEvent(var0, var1)) {
         Set var6 = (Set)getEventRegistry(var3).eventClosuresMap.get(var1);
         var4 = var5;
         if(var6 != null) {
            var4 = var5;
            if(var6.size() > 0) {
               var4 = delegateDispatchEvent(var3, var6, var0, var2);
            }
         }
      }

      return var4;
   }

   private static EventDispatcher.EventRegistry getEventRegistry(HandlesEventDispatching var0) {
      EventDispatcher.EventRegistry var2 = (EventDispatcher.EventRegistry)mapDispatchDelegateToEventRegistry.get(var0);
      EventDispatcher.EventRegistry var1 = var2;
      if(var2 == null) {
         var1 = new EventDispatcher.EventRegistry(var0);
         mapDispatchDelegateToEventRegistry.put(var0, var1);
      }

      return var1;
   }

   public static String makeFullEventName(String var0, String var1) {
      return var0 + '$' + var1;
   }

   public static void registerEventForDelegation(HandlesEventDispatching var0, String var1, String var2) {
      EventDispatcher.EventRegistry var4 = getEventRegistry(var0);
      Set var3 = (Set)var4.eventClosuresMap.get(var2);
      Object var5 = var3;
      if(var3 == null) {
         var5 = new HashSet();
         var4.eventClosuresMap.put(var2, var5);
      }

      ((Set)var5).add(new EventDispatcher.EventClosure(var1, var2, null));
   }

   public static void removeDispatchDelegate(HandlesEventDispatching var0) {
      EventDispatcher.EventRegistry var1 = removeEventRegistry(var0);
      if(var1 != null) {
         var1.eventClosuresMap.clear();
      }

   }

   private static EventDispatcher.EventRegistry removeEventRegistry(HandlesEventDispatching var0) {
      return (EventDispatcher.EventRegistry)mapDispatchDelegateToEventRegistry.remove(var0);
   }

   public static void unregisterAllEventsForDelegation() {
      Iterator var0 = mapDispatchDelegateToEventRegistry.values().iterator();

      while(var0.hasNext()) {
         ((EventDispatcher.EventRegistry)var0.next()).eventClosuresMap.clear();
      }

   }

   public static void unregisterEventForDelegation(HandlesEventDispatching var0, String var1, String var2) {
      Set var5 = (Set)getEventRegistry(var0).eventClosuresMap.get(var2);
      if(var5 != null && !var5.isEmpty()) {
         HashSet var7 = new HashSet();
         Iterator var3 = var5.iterator();

         while(var3.hasNext()) {
            EventDispatcher.EventClosure var4 = (EventDispatcher.EventClosure)var3.next();
            if(var4.componentId.equals(var1)) {
               var7.add(var4);
            }
         }

         Iterator var6 = var7.iterator();

         while(var6.hasNext()) {
            var5.remove((EventDispatcher.EventClosure)var6.next());
         }
      }

   }

   private static final class EventClosure {

      private final String componentId;
      private final String eventName;


      private EventClosure(String var1, String var2) {
         this.componentId = var1;
         this.eventName = var2;
      }

      // $FF: synthetic method
      EventClosure(String var1, String var2, Object var3) {
         this(var1, var2);
      }

      public boolean equals(Object var1) {
         if(this != var1) {
            if(var1 == null || this.getClass() != var1.getClass()) {
               return false;
            }

            EventDispatcher.EventClosure var2 = (EventDispatcher.EventClosure)var1;
            if(!this.componentId.equals(var2.componentId)) {
               return false;
            }

            if(!this.eventName.equals(var2.eventName)) {
               return false;
            }
         }

         return true;
      }

      public int hashCode() {
         return this.eventName.hashCode() * 31 + this.componentId.hashCode();
      }
   }

   private static final class EventRegistry {

      private final HandlesEventDispatching dispatchDelegate;
      private final HashMap eventClosuresMap = new HashMap();


      EventRegistry(HandlesEventDispatching var1) {
         this.dispatchDelegate = var1;
      }
   }
}
