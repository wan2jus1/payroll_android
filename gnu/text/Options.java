package gnu.text;

import gnu.text.SourceMessages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Options {

   public static final int BOOLEAN_OPTION = 1;
   public static final int STRING_OPTION = 2;
   public static final String UNKNOWN = "unknown option name";
   Options.OptionInfo first;
   HashMap infoTable;
   Options.OptionInfo last;
   Options previous;
   HashMap valueTable;


   public Options() {
   }

   public Options(Options var1) {
      this.previous = var1;
   }

   private void error(String var1, SourceMessages var2) {
      if(var2 == null) {
         throw new RuntimeException(var1);
      } else {
         var2.error('e', var1);
      }
   }

   static Object valueOf(Options.OptionInfo var0, String var1) {
      Object var2 = var1;
      if((var0.kind & 1) != 0) {
         if(var1 != null && !var1.equals("1") && !var1.equals("on") && !var1.equals("yes") && !var1.equals("true")) {
            if(!var1.equals("0") && !var1.equals("off") && !var1.equals("no") && !var1.equals("false")) {
               return null;
            }

            return Boolean.FALSE;
         }

         var2 = Boolean.TRUE;
      }

      return var2;
   }

   public Options.OptionInfo add(String var1, int var2, Object var3, String var4) {
      if(this.infoTable == null) {
         this.infoTable = new HashMap();
      } else if(this.infoTable.get(var1) != null) {
         throw new RuntimeException("duplicate option key: " + var1);
      }

      Options.OptionInfo var5 = new Options.OptionInfo();
      var5.key = var1;
      var5.kind = var2;
      var5.defaultValue = var3;
      var5.documentation = var4;
      if(this.first == null) {
         this.first = var5;
      } else {
         this.last.next = var5;
      }

      this.last = var5;
      this.infoTable.put(var1, var5);
      return var5;
   }

   public Options.OptionInfo add(String var1, int var2, String var3) {
      return this.add(var1, var2, (Object)null, var3);
   }

   public Object get(Options.OptionInfo var1) {
      return this.get((Options.OptionInfo)var1, (Object)null);
   }

   public Object get(Options.OptionInfo var1, Object var2) {
      Object var3 = var2;
      Options var6 = this;

      while(var6 != null) {
         Options.OptionInfo var4 = var1;

         while(true) {
            Object var5;
            if(var6.valueTable == null) {
               var5 = null;
            } else {
               var5 = var6.valueTable.get(var4.key);
            }

            if(var5 != null) {
               return var5;
            }

            if(!(var4.defaultValue instanceof Options.OptionInfo)) {
               if(var4.defaultValue != null) {
                  var3 = var4.defaultValue;
               }

               var6 = var6.previous;
               break;
            }

            var4 = (Options.OptionInfo)var4.defaultValue;
         }
      }

      return var3;
   }

   public Object get(String var1, Object var2) {
      Options.OptionInfo var3 = this.getInfo(var1);
      if(var3 == null) {
         throw new RuntimeException("invalid option key: " + var1);
      } else {
         return this.get((Options.OptionInfo)var3, var2);
      }
   }

   public boolean getBoolean(Options.OptionInfo var1) {
      Object var2 = this.get((Options.OptionInfo)var1, (Object)null);
      return var2 == null?false:((Boolean)var2).booleanValue();
   }

   public boolean getBoolean(Options.OptionInfo var1, boolean var2) {
      Boolean var3;
      if(var2) {
         var3 = Boolean.TRUE;
      } else {
         var3 = Boolean.FALSE;
      }

      return ((Boolean)this.get((Options.OptionInfo)var1, var3)).booleanValue();
   }

   public boolean getBoolean(String var1) {
      return ((Boolean)this.get((String)var1, Boolean.FALSE)).booleanValue();
   }

   public boolean getBoolean(String var1, boolean var2) {
      Boolean var3;
      if(var2) {
         var3 = Boolean.TRUE;
      } else {
         var3 = Boolean.FALSE;
      }

      return ((Boolean)this.get((String)var1, var3)).booleanValue();
   }

   public String getDoc(String var1) {
      Options.OptionInfo var2 = this.getInfo(var1);
      return var1 == null?null:var2.documentation;
   }

   public Options.OptionInfo getInfo(String var1) {
      Options.OptionInfo var2;
      if(this.infoTable == null) {
         var2 = null;
      } else {
         var2 = (Options.OptionInfo)this.infoTable.get(var1);
      }

      Options.OptionInfo var3 = var2;
      if(var2 == null) {
         var3 = var2;
         if(this.previous != null) {
            var3 = this.previous.getInfo(var1);
         }
      }

      return (Options.OptionInfo)var3;
   }

   public Object getLocal(String var1) {
      return this.valueTable == null?null:this.valueTable.get(var1);
   }

   public ArrayList keys() {
      ArrayList var2 = new ArrayList();

      for(Options var1 = this; var1 != null; var1 = var1.previous) {
         if(var1.infoTable != null) {
            Iterator var3 = var1.infoTable.keySet().iterator();

            while(var3.hasNext()) {
               String var4 = (String)var3.next();
               if(!var2.contains(var4)) {
                  var2.add(var4);
               }
            }
         }
      }

      return var2;
   }

   public void popOptionValues(Vector var1) {
      int var4 = var1.size();

      while(true) {
         var4 -= 3;
         if(var4 < 0) {
            return;
         }

         String var2 = (String)var1.elementAt(var4);
         Object var3 = var1.elementAt(var4 + 1);
         var1.setElementAt((Object)null, var4 + 1);
         this.reset(var2, var3);
      }
   }

   public void pushOptionValues(Vector var1) {
      int var5 = var1.size();

      for(int var4 = 0; var4 < var5; ++var4) {
         int var6 = var4 + 1;
         String var2 = (String)var1.elementAt(var4);
         Object var3 = var1.elementAt(var6);
         var4 = var6 + 1;
         var1.setElementAt(var3, var6);
         this.set(var2, (Object)var1.elementAt(var4));
      }

   }

   public void reset(String var1, Object var2) {
      if(this.valueTable == null) {
         this.valueTable = new HashMap();
      }

      if(var2 == null) {
         this.valueTable.remove(var1);
      } else {
         this.valueTable.put(var1, var2);
      }
   }

   public String set(String var1, String var2) {
      Options.OptionInfo var3 = this.getInfo(var1);
      if(var3 == null) {
         return "unknown option name";
      } else {
         Object var4 = valueOf(var3, var2);
         if(var4 == null && (var3.kind & 1) != 0) {
            return "value of option " + var1 + " must be yes/no/true/false/on/off/1/0";
         } else {
            if(this.valueTable == null) {
               this.valueTable = new HashMap();
            }

            this.valueTable.put(var1, var4);
            return null;
         }
      }
   }

   public void set(String var1, Object var2) {
      this.set(var1, var2, (SourceMessages)null);
   }

   public void set(String var1, Object var2, SourceMessages var3) {
      Options.OptionInfo var5 = this.getInfo(var1);
      if(var5 == null) {
         this.error("invalid option key: " + var1, var3);
      } else {
         Object var6;
         if((var5.kind & 1) != 0) {
            Object var4 = var2;
            if(var2 instanceof String) {
               var4 = valueOf(var5, (String)var2);
            }

            var6 = var4;
            if(!(var4 instanceof Boolean)) {
               this.error("value for option " + var1 + " must be boolean or yes/no/true/false/on/off/1/0", var3);
               return;
            }
         } else {
            var6 = var2;
            if(var2 == null) {
               var6 = "";
            }
         }

         if(this.valueTable == null) {
            this.valueTable = new HashMap();
         }

         this.valueTable.put(var1, var6);
      }
   }

   public static final class OptionInfo {

      Object defaultValue;
      String documentation;
      String key;
      int kind;
      Options.OptionInfo next;


   }
}
