package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.SeqPosition;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import gnu.mapping.WrongType;

public class NodeCompare extends Procedure2 {

   public static final NodeCompare $Eq = make("is", 8);
   public static final NodeCompare $Gr = make(">>", 16);
   public static final NodeCompare $Ls = make("<<", 4);
   public static final NodeCompare $Ne = make("isnot", 20);
   static final int RESULT_EQU = 0;
   static final int RESULT_GRT = 1;
   static final int RESULT_LSS = -1;
   static final int TRUE_IF_EQU = 8;
   static final int TRUE_IF_GRT = 16;
   static final int TRUE_IF_LSS = 4;
   int flags;


   public static NodeCompare make(String var0, int var1) {
      NodeCompare var2 = new NodeCompare();
      var2.setName(var0);
      var2.flags = var1;
      return var2;
   }

   public Object apply2(Object var1, Object var2) {
      Object var3;
      if(var1 != null && var2 != null) {
         var3 = var1;
         if(var1 != Values.empty) {
            if(var2 == Values.empty) {
               return var2;
            }

            SeqPosition var4;
            int var5;
            AbstractSequence var9;
            AbstractSequence var11;
            if(var1 instanceof AbstractSequence) {
               var9 = (AbstractSequence)var1;
               var5 = var9.startPos();
            } else {
               try {
                  var4 = (SeqPosition)var1;
                  var11 = var4.sequence;
                  var5 = var4.getPos();
               } catch (ClassCastException var8) {
                  throw WrongType.make(var8, (Procedure)this, 1, var1);
               }

               var9 = var11;
            }

            int var6;
            AbstractSequence var10;
            if(var2 instanceof AbstractSequence) {
               var10 = (AbstractSequence)var2;
               var6 = var10.startPos();
            } else {
               try {
                  var4 = (SeqPosition)var2;
                  var11 = var4.sequence;
                  var6 = var4.getPos();
               } catch (ClassCastException var7) {
                  throw WrongType.make(var7, (Procedure)this, 2, var2);
               }

               var10 = var11;
            }

            if(var9 == var10) {
               var5 = var9.compare(var5, var6);
            } else {
               if(this == $Eq) {
                  return Boolean.FALSE;
               }

               if(this == $Ne) {
                  return Boolean.TRUE;
               }

               var5 = var9.stableCompare(var10);
            }

            if((1 << var5 + 3 & this.flags) != 0) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }
      } else {
         var3 = null;
      }

      return var3;
   }
}
