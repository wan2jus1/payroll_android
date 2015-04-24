package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

public enum ComponentCategory {

   // $FF: synthetic field
   private static final ComponentCategory[] $VALUES = new ComponentCategory[]{USERINTERFACE, LAYOUT, MEDIA, ANIMATION, SENSORS, SOCIAL, STORAGE, CONNECTIVITY, LEGOMINDSTORMS, INTERNAL, UNINITIALIZED};
   ANIMATION("Drawing and Animation"),
   CONNECTIVITY("Connectivity");
   private static final Map DOC_MAP = new HashMap();
   INTERNAL("For internal use only"),
   LAYOUT("Layout"),
   LEGOMINDSTORMS("LEGO® MINDSTORMS®"),
   MEDIA("Media"),
   SENSORS("Sensors"),
   SOCIAL("Social"),
   STORAGE("Storage"),
   UNINITIALIZED("Uninitialized"),
   USERINTERFACE("User Interface");
   private String name;


   static {
      DOC_MAP.put("User Interface", "userinterface");
      DOC_MAP.put("Layout", "layout");
      DOC_MAP.put("media", "media");
      DOC_MAP.put("Drawing and Animation", "animation");
      DOC_MAP.put("Sensors", "sensors");
      DOC_MAP.put("Social", "social");
      DOC_MAP.put("Storage", "storage");
      DOC_MAP.put("Connectivity", "connectivity");
      DOC_MAP.put("LEGO® MINDSTORMS®", "legomindstorms");
   }

   private ComponentCategory(String var3) {
      this.name = var3;
   }

   public String getDocName() {
      return (String)DOC_MAP.get(this.name);
   }

   public String getName() {
      return this.name;
   }
}
