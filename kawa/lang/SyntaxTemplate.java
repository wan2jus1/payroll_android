package kawa.lang;

import gnu.expr.Compilation;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.IdentityHashMap;
import java.util.Vector;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxForms;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class SyntaxTemplate implements Externalizable {

   static final int BUILD_CONS = 1;
   static final int BUILD_DOTS = 5;
   static final int BUILD_LIST1 = 8;
   static final int BUILD_LITERAL = 4;
   static final int BUILD_MISC = 0;
   static final int BUILD_NIL = 16;
   static final int BUILD_SYNTAX = 24;
   static final int BUILD_VAR = 2;
   static final int BUILD_VAR_CAR = 3;
   static final int BUILD_VECTOR = 40;
   static final int BUILD_WIDE = 7;
   static final String dots3 = "...";
   Object[] literal_values;
   int max_nesting;
   String patternNesting;
   String template_program;


   protected SyntaxTemplate() {
   }

   public SyntaxTemplate(Object var1, SyntaxForm var2, Translator var3) {
      String var4;
      if(var3 != null && var3.patternScope != null) {
         var4 = var3.patternScope.patternNesting.toString();
      } else {
         var4 = "";
      }

      this.patternNesting = var4;
      StringBuffer var6 = new StringBuffer();
      Vector var5 = new Vector();
      this.convert_template(var1, var2, var6, 0, var5, new IdentityHashMap(), false, var3);
      this.template_program = var6.toString();
      this.literal_values = new Object[var5.size()];
      var5.copyInto(this.literal_values);
   }

   public SyntaxTemplate(String var1, String var2, Object[] var3, int var4) {
      this.patternNesting = var1;
      this.template_program = var2;
      this.literal_values = var3;
      this.max_nesting = var4;
   }

   private int get_count(Object var1, int var2, int[] var3) {
      for(int var4 = 0; var4 < var2; ++var4) {
         var1 = ((Object[])((Object[])var1))[var3[var4]];
      }

      return ((Object[])((Object[])var1)).length;
   }

   static int indexOf(Vector var0, Object var1) {
      int var3 = var0.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         if(var0.elementAt(var2) == var1) {
            return var2;
         }
      }

      return -1;
   }

   public int convert_template(Object var1, SyntaxForm var2, StringBuffer var3, int var4, Vector var5, Object var6, boolean var7, Translator var8) {
      while(var1 instanceof SyntaxForm) {
         var2 = (SyntaxForm)var1;
         var1 = var2.getDatum();
      }

      if(var1 instanceof Pair || var1 instanceof FVector) {
         IdentityHashMap var9 = (IdentityHashMap)var6;
         if(var9.containsKey(var1)) {
            var8.syntaxError("self-referential (cyclic) syntax template");
            var4 = -2;
            return var4;
         }

         var9.put(var1, var1);
      }

      int var12;
      int var13;
      int var14;
      Object var20;
      if(var1 instanceof Pair) {
         label154: {
            Pair var22 = (Pair)var1;
            var13 = -2;
            int var17 = var3.length();
            Object var10 = var22.getCar();
            Pair var24;
            if(var8.matches(var10, "...")) {
               Object var11 = Translator.stripSyntax(var22.getCdr());
               if(var11 instanceof Pair) {
                  var24 = (Pair)var11;
                  if(var24.getCar() == "..." && var24.getCdr() == LList.Empty) {
                     var20 = "...";
                     break label154;
                  }
               }
            }

            int var18 = var5.size();
            var3.append('\b');
            var12 = 0;
            Object var23 = var22.getCdr();

            while(var23 instanceof Pair) {
               var24 = (Pair)var23;
               if(!var8.matches(var24.getCar(), "...")) {
                  break;
               }

               ++var12;
               var23 = var24.getCdr();
               var3.append('\u0005');
            }

            int var15 = this.convert_template(var10, var2, var3, var4 + var12, var5, var6, false, var8);
            if(var23 != LList.Empty) {
               var3.setCharAt(var17, (char)((var3.length() - var17 - 1 << 3) + 1));
               var13 = this.convert_template(var23, var2, var3, var4, var5, var6, var7, var8);
            }

            if(var12 > 0) {
               if(var15 < 0) {
                  var8.syntaxError("... follows template with no suitably-nested pattern variable");
               }

               var14 = var12;

               while(true) {
                  int var16 = var14 - 1;
                  if(var16 < 0) {
                     break;
                  }

                  var3.setCharAt(var17 + var16 + 1, (char)((var15 << 3) + 5));
                  int var19 = var4 + var12;
                  var14 = var16;
                  if(var19 >= this.max_nesting) {
                     this.max_nesting = var19;
                     var14 = var16;
                  }
               }
            }

            var4 = var15;
            if(var15 >= 0) {
               return var4;
            }

            if(var13 >= 0) {
               return var13;
            }

            if(var15 == -1 || var13 == -1) {
               return -1;
            }

            if(var7) {
               return -2;
            }

            var5.setSize(var18);
            var3.setLength(var17);
            var20 = var1;
         }
      } else {
         if(var1 instanceof FVector) {
            var3.append('(');
            return this.convert_template(LList.makeList((FVector)var1), var2, var3, var4, var5, var6, true, var8);
         }

         if(var1 == LList.Empty) {
            var3.append('\u0010');
            return -2;
         }

         var20 = var1;
         if(var1 instanceof Symbol) {
            var20 = var1;
            if(var8 != null) {
               var20 = var1;
               if(var8.patternScope != null) {
                  var13 = indexOf(var8.patternScope.pattern_names, var1);
                  var20 = var1;
                  if(var13 >= 0) {
                     char var26 = this.patternNesting.charAt(var13);
                     byte var25;
                     if((var26 & 1) != 0) {
                        var25 = 3;
                     } else {
                        var25 = 2;
                     }

                     var14 = var26 >> 1;
                     if(var14 > var4) {
                        var8.syntaxError("inconsistent ... nesting of " + var1);
                     }

                     var3.append((char)(var13 * 8 + var25));
                     if(var14 == var4) {
                        var4 = var13;
                     } else {
                        var4 = -1;
                     }

                     return var4;
                  }
               }
            }
         }
      }

      var12 = indexOf(var5, var20);
      var4 = var12;
      if(var12 < 0) {
         var4 = var5.size();
         var5.addElement(var20);
      }

      if(var20 instanceof Symbol) {
         var8.noteAccess(var20, var8.currentScope());
      }

      if(!(var20 instanceof SyntaxForm) && var20 != "...") {
         var3.append('\u0018');
      }

      var3.append((char)(var4 * 8 + 4));
      byte var21;
      if(var20 == "...") {
         var21 = -1;
      } else {
         var21 = -2;
      }

      return var21;
   }

   Object execute(int var1, Object[] var2, int var3, int[] var4, Translator var5, TemplateScope var6) {
      char var13 = this.template_program.charAt(var1);
      int var12 = var1;

      String var7;
      for(var1 = var13; (var1 & 7) == 7; var1 = var1 - 7 << 13 | var7.charAt(var12)) {
         var7 = this.template_program;
         ++var12;
      }

      Object var15;
      if(var1 == 8) {
         var15 = this.executeToList(var12 + 1, var2, var3, var4, var5, var6);
      } else {
         if(var1 == 16) {
            return LList.Empty;
         }

         Object var16;
         if(var1 != 24) {
            if((var1 & 7) != 1) {
               if(var1 == 40) {
                  return new FVector((LList)this.execute(var12 + 1, var2, var3, var4, var5, var6));
               }

               if((var1 & 7) == 4) {
                  return this.literal_values[var1 >> 3];
               }

               if((var1 & 6) == 2) {
                  var16 = this.get_var(var1 >> 3, var2, var4);
                  var15 = var16;
                  if((var1 & 7) == 3) {
                     var15 = ((Pair)var16).getCar();
                  }

                  return var15;
               }

               throw new Error("unknown template code: " + var1 + " at " + var12);
            }

            Pair var9 = null;
            Object var10 = null;

            Pair var8;
            char var14;
            Object var17;
            int var18;
            do {
               ++var12;
               Object var11 = this.executeToList(var12, var2, var3, var4, var5, var6);
               if(var9 == null) {
                  var17 = var11;
                  var8 = var9;
               } else {
                  var9.setCdrBackdoor(var11);
                  var8 = var9;
                  var17 = var10;
               }

               while(var11 instanceof Pair) {
                  var8 = (Pair)var11;
                  var11 = var8.getCdr();
               }

               var18 = var12 + (var1 >> 3);
               var14 = this.template_program.charAt(var18);
               var1 = var14;
               var9 = var8;
               var10 = var17;
               var12 = var18;
            } while((var14 & 7) == 1);

            var15 = this.execute(var18, var2, var3, var4, var5, var6);
            if(var8 != null) {
               var8.setCdrBackdoor(var15);
               var15 = var17;
            }

            return var15;
         }

         var16 = this.execute(var12 + 1, var2, var3, var4, var5, var6);
         var15 = var16;
         if(var16 != LList.Empty) {
            return SyntaxForms.makeForm(var16, var6);
         }
      }

      return var15;
   }

   public Object execute(Object[] var1, TemplateScope var2) {
      return this.execute(0, var1, 0, new int[this.max_nesting], (Translator)Compilation.getCurrent(), var2);
   }

   public Object execute(Object[] var1, Translator var2, TemplateScope var3) {
      return this.execute(0, var1, 0, new int[this.max_nesting], var2, var3);
   }

   LList executeToList(int var1, Object[] var2, int var3, int[] var4, Translator var5, TemplateScope var6) {
      int var11 = this.template_program.charAt(var1);

      String var7;
      int var10;
      for(var10 = var1; (var11 & 7) == 7; var11 = var11 - 7 << 13 | var7.charAt(var10)) {
         var7 = this.template_program;
         ++var10;
      }

      Object var9;
      if((var11 & 7) == 3) {
         Pair var12 = (Pair)this.get_var(var11 >> 3, var2, var4);
         var9 = Translator.makePair(var12, var12.getCar(), LList.Empty);
      } else {
         if((var11 & 7) != 5) {
            return new Pair(this.execute(var1, var2, var3, var4, var5, var6), LList.Empty);
         }

         var11 = this.get_count(var2[var11 >> 3], var3, var4);
         LList var13 = LList.Empty;
         Pair var8 = null;
         var1 = 0;

         while(true) {
            var9 = var13;
            if(var1 >= var11) {
               break;
            }

            var4[var3] = var1;
            LList var14 = this.executeToList(var10 + 1, var2, var3 + 1, var4, var5, var6);
            if(var8 == null) {
               var13 = var14;
            } else {
               var8.setCdrBackdoor(var14);
            }

            while(var14 instanceof Pair) {
               var8 = (Pair)var14;
               var14 = (LList)var8.getCdr();
            }

            ++var1;
         }
      }

      return (LList)var9;
   }

   Object get_var(int var1, Object[] var2, int[] var3) {
      Object var6 = var2[var1];
      Object var4 = var6;
      if(var1 < this.patternNesting.length()) {
         char var5 = this.patternNesting.charAt(var1);
         var1 = 0;

         while(true) {
            var4 = var6;
            if(var1 >= var5 >> 1) {
               break;
            }

            var6 = ((Object[])((Object[])var6))[var3[var1]];
            ++var1;
         }
      }

      return var4;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.patternNesting = (String)var1.readObject();
      this.template_program = (String)var1.readObject();
      this.literal_values = (Object[])((Object[])var1.readObject());
      this.max_nesting = var1.readInt();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.patternNesting);
      var1.writeObject(this.template_program);
      var1.writeObject(this.literal_values);
      var1.writeInt(this.max_nesting);
   }
}
