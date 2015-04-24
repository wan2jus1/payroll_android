package com.google.appinventor.components.annotations;


public enum PropertyCategory {

   // $FF: synthetic field
   private static final PropertyCategory[] $VALUES = new PropertyCategory[]{BEHAVIOR, APPEARANCE, DEPRECATED, UNSET};
   APPEARANCE("Appearance"),
   BEHAVIOR("Behavior"),
   DEPRECATED("Deprecated"),
   UNSET("Unspecified");
   private String name;


   private PropertyCategory(String var3) {
      this.name = var3;
   }

   public String getName() {
      return this.name;
   }
}
