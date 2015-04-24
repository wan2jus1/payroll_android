package gnu.bytecode;

import gnu.bytecode.ClassType;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class AnnotationEntry implements Annotation {

   ClassType annotationType;
   LinkedHashMap elementsValue = new LinkedHashMap(10);


   public void addMember(String var1, Object var2) {
      this.elementsValue.put(var1, var2);
   }

   public Class annotationType() {
      return this.annotationType.getReflectClass();
   }

   public boolean equals(Object var1) {
      if(var1 instanceof AnnotationEntry) {
         AnnotationEntry var5 = (AnnotationEntry)var1;
         if(this.getAnnotationType().getName().equals(var5.getAnnotationType().getName())) {
            Iterator var2 = this.elementsValue.entrySet().iterator();

            while(true) {
               Object var9;
               if(!var2.hasNext()) {
                  Iterator var6 = var5.elementsValue.entrySet().iterator();

                  Object var8;
                  do {
                     do {
                        if(!var6.hasNext()) {
                           return true;
                        }

                        Entry var10 = (Entry)var6.next();
                        String var7 = (String)var10.getKey();
                        var9 = var10.getValue();
                        var8 = this.elementsValue.get(var7);
                     } while(var8 == var9);

                     if(var8 == null || var9 == null) {
                        return false;
                     }
                  } while(var8.equals(var9));

                  return false;
               }

               Entry var4 = (Entry)var2.next();
               String var3 = (String)var4.getKey();
               Object var11 = var4.getValue();
               var9 = var5.elementsValue.get(var3);
               if(var11 != var9) {
                  if(var11 == null || var9 == null) {
                     break;
                  }

                  if(!var11.equals(var9)) {
                     return false;
                  }
               }
            }
         }
      }

      return false;
   }

   public ClassType getAnnotationType() {
      return this.annotationType;
   }

   public int hashCode() {
      int var3 = 0;

      Entry var2;
      for(Iterator var1 = this.elementsValue.entrySet().iterator(); var1.hasNext(); var3 += ((String)var2.getKey()).hashCode() * 127 ^ var2.getValue().hashCode()) {
         var2 = (Entry)var1.next();
      }

      return var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('@');
      var1.append(this.getAnnotationType().getName());
      var1.append('(');
      int var4 = 0;

      for(Iterator var2 = this.elementsValue.entrySet().iterator(); var2.hasNext(); ++var4) {
         Entry var3 = (Entry)var2.next();
         if(var4 > 0) {
            var1.append(", ");
         }

         var1.append((String)var3.getKey());
         var1.append('=');
         var1.append(var3.getValue());
      }

      var1.append(')');
      return var1.toString();
   }
}
