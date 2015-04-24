package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.util.Vector;
import kawa.lang.Macro;
import kawa.lang.Pattern;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxForms;
import kawa.lang.Translator;

public class SyntaxPattern extends Pattern implements Externalizable {

   static final int MATCH_ANY = 3;
   static final int MATCH_ANY_CAR = 7;
   static final int MATCH_EQUALS = 2;
   static final int MATCH_IGNORE = 24;
   static final int MATCH_LENGTH = 6;
   static final int MATCH_LREPEAT = 5;
   static final int MATCH_MISC = 0;
   static final int MATCH_NIL = 8;
   static final int MATCH_PAIR = 4;
   static final int MATCH_VECTOR = 16;
   static final int MATCH_WIDE = 1;
   Object[] literals;
   String program;
   int varCount;


   public SyntaxPattern(Object var1, Object[] var2, Translator var3) {
      this(new StringBuffer(), var1, (SyntaxForm)null, var2, var3);
   }

   public SyntaxPattern(String var1, Object[] var2, int var3) {
      this.program = var1;
      this.literals = var2;
      this.varCount = var3;
   }

   SyntaxPattern(StringBuffer var1, Object var2, SyntaxForm var3, Object[] var4, Translator var5) {
      Vector var6 = new Vector();
      this.translate(var2, var1, var4, 0, var6, (SyntaxForm)null, '\u0000', var5);
      this.program = var1.toString();
      this.literals = new Object[var6.size()];
      var6.copyInto(this.literals);
      this.varCount = var5.patternScope.pattern_names.size();
   }

   private static void addInt(StringBuffer var0, int var1) {
      if(var1 > '\uffff') {
         addInt(var0, (var1 << 13) + 1);
      }

      var0.append((char)var1);
   }

   public static Object[] allocVars(int var0, Object[] var1) {
      Object[] var2 = new Object[var0];
      if(var1 != null) {
         System.arraycopy(var1, 0, var2, 0, var1.length);
      }

      return var2;
   }

   public static Object[] getLiteralsList(Object var0, SyntaxForm var1, Translator var2) {
      Object var3 = var2.pushPositionOf(var0);
      int var7 = Translator.listLength(var0);
      int var6 = var7;
      if(var7 < 0) {
         var2.error('e', "missing or malformed literals list");
         var6 = 0;
      }

      Object[] var4 = new Object[var6 + 1];

      for(var7 = 1; var7 <= var6; ++var7) {
         while(var0 instanceof SyntaxForm) {
            var0 = ((SyntaxForm)var0).getDatum();
         }

         Pair var5 = (Pair)var0;
         var2.pushPositionOf(var5);
         Object var8 = var5.getCar();
         var0 = var8;
         if(var8 instanceof SyntaxForm) {
            var0 = ((SyntaxForm)var8).getDatum();
         }

         if(!(var0 instanceof Symbol)) {
            var2.error('e', "non-symbol \'" + var0 + "\' in literals list");
         }

         var4[var7] = var8;
         var0 = var5.getCdr();
      }

      var2.popPositionOf(var3);
      return var4;
   }

   private static int insertInt(int var0, StringBuffer var1, int var2) {
      int var3 = var0;
      if(var2 > '\uffff') {
         var3 = var0 + insertInt(var0, var1, (var2 << 13) + 1);
      }

      var1.insert(var3, (char)var2);
      return var3 + 1;
   }

   public static boolean literalIdentifierEq(Object var0, ScopeExp var1, Object var2, ScopeExp var3) {
      boolean var10 = true;
      boolean var9;
      if(var0 != var2 && (var0 == null || var2 == null || !var0.equals(var2))) {
         var9 = false;
      } else {
         var9 = var10;
         if(var1 != var3) {
            Declaration var4 = null;
            Object var8 = null;
            ScopeExp var7 = var1;

            Declaration var5;
            ScopeExp var6;
            Declaration var12;
            while(true) {
               var5 = var4;
               var12 = (Declaration)var8;
               var6 = var3;
               if(var7 == null) {
                  break;
               }

               var5 = var4;
               var12 = (Declaration)var8;
               var6 = var3;
               if(var7 instanceof ModuleExp) {
                  break;
               }

               var4 = var7.lookup(var0);
               if(var4 != null) {
                  var6 = var3;
                  var12 = (Declaration)var8;
                  var5 = var4;
                  break;
               }

               var7 = var7.outer;
            }

            Declaration var11;
            while(true) {
               var11 = var12;
               if(var6 == null) {
                  break;
               }

               var11 = var12;
               if(var6 instanceof ModuleExp) {
                  break;
               }

               var12 = var6.lookup(var2);
               if(var12 != null) {
                  var11 = var12;
                  break;
               }

               var6 = var6.outer;
            }

            var9 = var10;
            if(var5 != var11) {
               return false;
            }
         }
      }

      return var9;
   }

   public void disassemble() {
      this.disassemble(OutPort.errDefault(), (Translator)Compilation.getCurrent(), 0, this.program.length());
   }

   public void disassemble(PrintWriter var1, Translator var2) {
      this.disassemble(var1, var2, 0, this.program.length());
   }

   void disassemble(PrintWriter var1, Translator var2, int var3, int var4) {
      String var6 = null;
      Vector var5 = var6;
      if(var2 != null) {
         var5 = var6;
         if(var2.patternScope != null) {
            var5 = var2.patternScope.pattern_names;
         }
      }

      byte var9 = 0;
      int var8 = var3;
      var3 = var9;

      while(var8 < var4) {
         char var13 = this.program.charAt(var8);
         var1.print(" " + var8 + ": " + var13);
         ++var8;
         int var10 = var13 & 7;
         var3 = var3 << 13 | var13 >> 3;
         StringBuilder var7;
         switch(var10) {
         case 0:
            var1.print("[misc ch:" + var13 + " n:" + 8 + "]");
            if(var13 == 8) {
               var1.println(" - NIL");
               break;
            } else if(var13 == 16) {
               var1.println(" - VECTOR");
               break;
            } else if(var13 == 24) {
               var1.println(" - IGNORE");
               break;
            }
         case 1:
            var1.println(" - WIDE " + var3);
            continue;
         case 2:
            var1.print(" - EQUALS[" + var3 + "]");
            if(this.literals != null && var3 >= 0 && var3 < this.literals.length) {
               var1.print(this.literals[var3]);
            }

            var1.println();
            break;
         case 3:
         case 7:
            var7 = new StringBuilder();
            if(var10 == 3) {
               var6 = " - ANY[";
            } else {
               var6 = " - ANY_CAR[";
            }

            var1.print(var7.append(var6).append(var3).append("]").toString());
            if(var5 != null && var3 >= 0 && var3 < var5.size()) {
               var1.print(var5.elementAt(var3));
            }

            var1.println();
            break;
         case 4:
            var1.println(" - PAIR[" + var3 + "]");
            break;
         case 5:
            var1.println(" - LREPEAT[" + var3 + "]");
            this.disassemble(var1, var2, var8, var8 + var3);
            var8 += var3;
            StringBuilder var12 = (new StringBuilder()).append(" ").append(var8).append(": - repeat first var:");
            String var11 = this.program;
            var3 = var8 + 1;
            var1.println(var12.append(var11.charAt(var8) >> 3).toString());
            var12 = (new StringBuilder()).append(" ").append(var3).append(": - repeast nested vars:");
            var11 = this.program;
            var8 = var3 + 1;
            var1.println(var12.append(var11.charAt(var3) >> 3).toString());
            break;
         case 6:
            var7 = (new StringBuilder()).append(" - LENGTH ").append(var3 >> 1).append(" pairs. ");
            if((var3 & 1) == 0) {
               var6 = "pure list";
            } else {
               var6 = "impure list";
            }

            var1.println(var7.append(var6).toString());
            break;
         default:
            var1.println(" - " + var10 + '/' + var3);
         }

         var3 = 0;
      }

   }

   public boolean match(Object var1, Object[] var2, int var3) {
      return this.match(var1, var2, var3, 0, (SyntaxForm)null);
   }

   public boolean match(Object var1, Object[] var2, int var3, int var4, SyntaxForm var5) {
      byte var10 = 0;
      int var9 = var4;
      var4 = var10;

      label211:
      while(true) {
         while(var1 instanceof SyntaxForm) {
            var5 = (SyntaxForm)var1;
            var1 = var5.getDatum();
         }

         String var6 = this.program;
         int var11 = var9 + 1;
         var9 = var6.charAt(var9);
         int var30 = var4 << 13 | var9 >> 3;
         boolean var17;
         SyntaxForm var18;
         Object var25;
         switch(var9 & 7) {
         case 0:
            if(var9 == 8) {
               if(var1 == LList.Empty) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               return var17;
            }

            if(var9 == 16) {
               if(!(var1 instanceof FVector)) {
                  return false;
               }

               return this.match(LList.makeList((FVector)var1), var2, var3, var11, var5);
            }

            if(var9 == 24) {
               return true;
            }

            throw new Error("unknwon pattern opcode");
         case 1:
            var9 = var11;
            var4 = var30;
            break;
         case 2:
            var25 = this.literals[var30];
            Translator var28 = (Translator)Compilation.getCurrent();
            Object var20;
            if(var25 instanceof SyntaxForm) {
               SyntaxForm var19 = (SyntaxForm)var25;
               var25 = var19.getDatum();
               var20 = var19.getScope();
            } else {
               Syntax var21 = var28.getCurrentSyntax();
               if(var21 instanceof Macro) {
                  var20 = ((Macro)var21).getCapturedScope();
               } else {
                  var20 = null;
               }
            }

            Object var27;
            if(var1 instanceof SyntaxForm) {
               var18 = (SyntaxForm)var1;
               var27 = var18.getDatum();
               var1 = var18.getScope();
            } else {
               var27 = var1;
               if(var5 == null) {
                  var1 = var28.currentScope();
               } else {
                  var1 = var5.getScope();
               }
            }

            return literalIdentifierEq(var25, (ScopeExp)var20, var27, (ScopeExp)var1);
         case 3:
            var25 = var1;
            if(var5 != null) {
               var25 = SyntaxForms.fromDatum(var1, var5);
            }

            var2[var3 + var30] = var25;
            return true;
         case 4:
            if(!(var1 instanceof Pair)) {
               return false;
            }

            Pair var22 = (Pair)var1;
            if(!this.match_car(var22, var2, var3, var11, var5)) {
               return false;
            }

            var9 = var11 + var30;
            var4 = 0;
            var1 = var22.getCdr();
            break;
         case 5:
            var4 = var11 + var30;
            var6 = this.program;
            var9 = var4 + 1;
            char var23 = var6.charAt(var4);

            for(var30 = var23 >> 3; (var23 & 7) == 1; ++var9) {
               var23 = this.program.charAt(var9);
               var30 = var30 << 13 | var23 >> 3;
            }

            int var15 = var30 + var3;
            int var12 = this.program.charAt(var9) >> 3;
            var30 = var9 + 1;
            char var31 = var23;

            for(var4 = var30; (var31 & 7) == 1; ++var4) {
               var31 = this.program.charAt(var4);
               var12 = var12 << 13 | var31 >> 3;
            }

            var6 = this.program;
            var30 = var4 + 1;
            var31 = var6.charAt(var4);
            boolean var13 = true;
            boolean var26 = true;
            int var14;
            if(var31 == 8) {
               var14 = 0;
               var13 = var26;
            } else {
               var14 = var31 >> 3;
               var4 = var30;

               for(var30 = var14; (var31 & 7) == 1; ++var4) {
                  var31 = this.program.charAt(var4);
                  var30 = var30 << 13 | var31 >> 3;
               }

               boolean var32 = var13;
               if((var30 & 1) != 0) {
                  var32 = false;
               }

               var14 = var30 >> 1;
               var13 = var32;
               var30 = var4;
            }

            var9 = Translator.listLength(var1);
            if(var9 >= 0) {
               var26 = true;
            } else {
               var26 = false;
               var9 = -1 - var9;
            }

            if(var9 < var14 || var13 && !var26) {
               return false;
            }

            int var16 = var9 - var14;
            Object[][] var8 = new Object[var12][];

            for(var4 = 0; var4 < var12; ++var4) {
               var8[var4] = new Object[var16];
            }

            var4 = 0;
            SyntaxForm var7 = var5;

            for(var25 = var1; var4 < var16; var7 = var18) {
               for(var18 = var7; var25 instanceof SyntaxForm; var25 = var18.getDatum()) {
                  var18 = (SyntaxForm)var25;
               }

               Pair var24 = (Pair)var25;
               if(!this.match_car(var24, var2, var3, var11, var18)) {
                  return false;
               }

               var25 = var24.getCdr();

               for(var9 = 0; var9 < var12; ++var9) {
                  var8[var9][var4] = var2[var15 + var9];
               }

               ++var4;
            }

            for(var4 = 0; var4 < var12; ++var4) {
               var2[var15 + var4] = var8[var4];
            }

            byte var29 = 0;
            var4 = var29;
            var1 = var25;
            var9 = var30;
            var5 = var7;
            if(var14 == 0) {
               var4 = var29;
               var1 = var25;
               var9 = var30;
               var5 = var7;
               if(var13) {
                  return true;
               }
            }
            break;
         case 6:
            var25 = var1;
            var4 = 0;

            while(true) {
               while(!(var25 instanceof SyntaxForm)) {
                  if(var4 == var30 >> 1) {
                     if((var30 & 1) == 0) {
                        if(var25 != LList.Empty) {
                           return false;
                        }
                     } else if(var25 instanceof Pair) {
                        return false;
                     }

                     var4 = 0;
                     var9 = var11;
                     continue label211;
                  }

                  if(!(var25 instanceof Pair)) {
                     return false;
                  }

                  var25 = ((Pair)var25).getCdr();
                  ++var4;
               }

               var25 = ((SyntaxForm)var25).getDatum();
            }
         case 7:
         default:
            this.disassemble();
            throw new Error("unrecognized pattern opcode @pc:" + var11);
         case 8:
            if(var1 == LList.Empty) {
               var17 = true;
            } else {
               var17 = false;
            }

            return var17;
         }
      }
   }

   boolean match_car(Pair var1, Object[] var2, int var3, int var4, SyntaxForm var5) {
      String var6 = this.program;
      int var7 = var4 + 1;
      char var9 = var6.charAt(var4);

      int var8;
      for(var8 = var9 >> 3; (var9 & 7) == 1; ++var7) {
         var9 = this.program.charAt(var7);
         var8 = var8 << 13 | var9 >> 3;
      }

      if((var9 & 7) == 7) {
         Pair var10 = var1;
         if(var5 != null) {
            var10 = var1;
            if(!(var1.getCar() instanceof SyntaxForm)) {
               var10 = Translator.makePair(var1, SyntaxForms.fromDatum(var1.getCar(), var5), var1.getCdr());
            }
         }

         var2[var3 + var8] = var10;
         return true;
      } else {
         return this.match(var1.getCar(), var2, var3, var4, var5);
      }
   }

   public void print(Consumer var1) {
      var1.write("#<syntax-pattern>");
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.literals = (Object[])((Object[])var1.readObject());
      this.program = (String)var1.readObject();
      this.varCount = var1.readInt();
   }

   void translate(Object param1, StringBuffer param2, Object[] param3, int param4, Vector param5, SyntaxForm param6, char param7, Translator param8) {
      // $FF: Couldn't be decompiled
   }

   public int varCount() {
      return this.varCount;
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.program);
      var1.writeObject(this.literals);
      var1.writeInt(this.varCount);
   }
}
