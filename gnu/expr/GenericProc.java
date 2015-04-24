package gnu.expr;

import gnu.bytecode.Type;
import gnu.expr.Keyword;
import gnu.expr.Language;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.WrongType;

public class GenericProc extends MethodProc {

   int count;
   int maxArgs;
   protected MethodProc[] methods;
   int minArgs;


   public GenericProc() {
   }

   public GenericProc(String var1) {
      this.setName(var1);
   }

   public static GenericProc make(Object[] var0) {
      GenericProc var1 = new GenericProc();
      var1.setProperties(var0);
      return var1;
   }

   public static GenericProc makeWithoutSorting(Object ... var0) {
      GenericProc var1 = new GenericProc();
      int var4 = var0.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Object var2 = var0[var3];
         if(var2 instanceof Keyword) {
            Keyword var5 = (Keyword)var2;
            ++var3;
            var1.setProperty(var5, var0[var3]);
         } else {
            var1.addAtEnd((MethodProc)var2);
         }
      }

      return var1;
   }

   public void add(MethodProc param1) {
      // $FF: Couldn't be decompiled
   }

   protected void add(MethodProc[] param1) {
      // $FF: Couldn't be decompiled
   }

   public void addAtEnd(MethodProc param1) {
      // $FF: Couldn't be decompiled
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(this.count == 1) {
         return this.methods[0].applyN(var1);
      } else {
         checkArgCount(this, var1.length);
         CallContext var2 = CallContext.getInstance();

         for(int var3 = 0; var3 < this.count; ++var3) {
            if(this.methods[var3].matchN(var1, var2) == 0) {
               return var2.runUntilValue();
            }
         }

         throw new WrongType(this, -1, (ClassCastException)null);
      }
   }

   public MethodProc getMethod(int var1) {
      return var1 >= this.count?null:this.methods[var1];
   }

   public int getMethodCount() {
      return this.count;
   }

   public int isApplicable(Type[] var1) {
      byte var2 = -1;
      int var3 = this.count;

      byte var6;
      while(true) {
         int var4 = var3 - 1;
         var6 = var2;
         if(var4 < 0) {
            break;
         }

         int var5 = this.methods[var4].isApplicable(var1);
         if(var5 == 1) {
            var6 = 1;
            break;
         }

         var3 = var4;
         if(var5 == 0) {
            var2 = 0;
            var3 = var4;
         }
      }

      return var6;
   }

   public int match0(CallContext var1) {
      byte var4 = 0;
      int var2;
      if(this.count == 1) {
         var2 = this.methods[0].match0(var1);
      } else {
         int var3 = 0;

         while(true) {
            if(var3 >= this.count) {
               var1.proc = null;
               return -1;
            }

            var2 = var4;
            if(this.methods[var3].match0(var1) == 0) {
               break;
            }

            ++var3;
         }
      }

      return var2;
   }

   public int match1(Object var1, CallContext var2) {
      byte var5 = 0;
      int var3;
      if(this.count == 1) {
         var3 = this.methods[0].match1(var1, var2);
      } else {
         int var4 = 0;

         while(true) {
            if(var4 >= this.count) {
               var2.proc = null;
               return -1;
            }

            var3 = var5;
            if(this.methods[var4].match1(var1, var2) == 0) {
               break;
            }

            ++var4;
         }
      }

      return var3;
   }

   public int match2(Object var1, Object var2, CallContext var3) {
      byte var6 = 0;
      int var4;
      if(this.count == 1) {
         var4 = this.methods[0].match2(var1, var2, var3);
      } else {
         int var5 = 0;

         while(true) {
            if(var5 >= this.count) {
               var3.proc = null;
               return -1;
            }

            var4 = var6;
            if(this.methods[var5].match2(var1, var2, var3) == 0) {
               break;
            }

            ++var5;
         }
      }

      return var4;
   }

   public int match3(Object var1, Object var2, Object var3, CallContext var4) {
      byte var7 = 0;
      int var5;
      if(this.count == 1) {
         var5 = this.methods[0].match3(var1, var2, var3, var4);
      } else {
         int var6 = 0;

         while(true) {
            if(var6 >= this.count) {
               var4.proc = null;
               return -1;
            }

            var5 = var7;
            if(this.methods[var6].match3(var1, var2, var3, var4) == 0) {
               break;
            }

            ++var6;
         }
      }

      return var5;
   }

   public int match4(Object var1, Object var2, Object var3, Object var4, CallContext var5) {
      if(this.count == 1) {
         return this.methods[0].match4(var1, var2, var3, var4, var5);
      } else {
         for(int var6 = 0; var6 < this.count; ++var6) {
            if(this.methods[var6].match4(var1, var2, var3, var4, var5) == 0) {
               return 0;
            }
         }

         var5.proc = null;
         return -1;
      }
   }

   public int matchN(Object[] var1, CallContext var2) {
      if(this.count == 1) {
         return this.methods[0].matchN(var1, var2);
      } else {
         int var7 = var1.length;
         Type[] var4 = new Type[var7];
         Language var5 = Language.getDefaultLanguage();

         int var6;
         for(var6 = 0; var6 < var7; ++var6) {
            Object var3 = var1[var6];
            if(var3 == null) {
               var3 = Type.nullType;
            } else {
               Class var14 = var3.getClass();
               if(var5 != null) {
                  var3 = var5.getTypeFor((Class)var14);
               } else {
                  var3 = Type.make(var14);
               }
            }

            var4[var6] = (Type)var3;
         }

         int[] var15 = new int[this.count];
         var7 = 0;
         int var8 = 0;
         int var12 = -1;

         int var11;
         for(var6 = 0; var6 < this.count; var8 = var11) {
            int var13 = this.methods[var6].isApplicable(var4);
            int var9 = var12;
            if(var7 == 0) {
               var9 = var12;
               if(var13 >= 0) {
                  var9 = var6;
               }
            }

            int var10;
            if(var13 > 0) {
               var10 = var7 + 1;
               var11 = var8;
            } else {
               var10 = var7;
               var11 = var8;
               if(var13 == 0) {
                  var11 = var8 + 1;
                  var10 = var7;
               }
            }

            var15[var6] = var13;
            ++var6;
            var12 = var9;
            var7 = var10;
         }

         if(var7 != 1 && (var7 != 0 || var8 != 1)) {
            for(var6 = 0; var6 < this.count; ++var6) {
               var8 = var15[var6];
               if(var8 >= 0 && (var8 != 0 || var7 <= 0) && this.methods[var6].matchN(var1, var2) == 0) {
                  return 0;
               }
            }

            var2.proc = null;
            return -1;
         } else {
            return this.methods[var12].matchN(var1, var2);
         }
      }
   }

   public int numArgs() {
      return this.minArgs | this.maxArgs << 12;
   }

   public final void setProperties(Object[] var1) {
      int var4 = var1.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Object var2 = var1[var3];
         if(var2 instanceof Keyword) {
            Keyword var5 = (Keyword)var2;
            ++var3;
            this.setProperty(var5, var1[var3]);
         } else {
            this.add((MethodProc)((MethodProc)var2));
         }
      }

   }

   public void setProperty(Keyword var1, Object var2) {
      String var3 = var1.getName();
      if(var3 == "name") {
         this.setName(var2.toString());
      } else if(var3 == "method") {
         this.add((MethodProc)((MethodProc)var2));
      } else {
         super.setProperty(var1.asSymbol(), var2);
      }
   }
}
