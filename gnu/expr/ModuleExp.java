package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.ModuleInfo;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.text.Path;
import gnu.text.SyntaxException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;

public class ModuleExp extends LambdaExp implements Externalizable {

   public static final int EXPORT_SPECIFIED = 16384;
   public static final int IMMEDIATE = 1048576;
   public static final int LAZY_DECLARATIONS = 524288;
   public static final int NONSTATIC_SPECIFIED = 65536;
   public static final int STATIC_RUN_SPECIFIED = 262144;
   public static final int STATIC_SPECIFIED = 32768;
   public static final int SUPERTYPE_SPECIFIED = 131072;
   public static boolean alwaysCompile = compilerAvailable;
   public static boolean compilerAvailable = true;
   public static String dumpZipPrefix;
   public static int interactiveCounter;
   static int lastZipCounter;
   public static boolean neverCompile = false;
   ModuleInfo info;
   ClassType[] interfaces;
   ClassType superType;


   public static final boolean evalModule(Environment var0, CallContext var1, Compilation var2, URL var3, OutPort var4) throws Throwable {
      ModuleExp var5 = var2.getModule();
      Language var6 = var2.getLanguage();
      Object var7 = evalModule1(var0, var2, var3, var4);
      if(var7 == null) {
         return false;
      } else {
         evalModule2(var0, var1, var6, var5, var7);
         return true;
      }
   }

   public static final Object evalModule1(Environment param0, Compilation param1, URL param2, OutPort param3) throws SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public static final void evalModule2(Environment param0, CallContext param1, Language param2, ModuleExp param3, Object param4) throws Throwable {
      // $FF: Couldn't be decompiled
   }

   public static Class evalToClass(Compilation param0, URL param1) throws SyntaxException {
      // $FF: Couldn't be decompiled
   }

   public static void mustAlwaysCompile() {
      alwaysCompile = true;
      neverCompile = false;
   }

   public static void mustNeverCompile() {
      alwaysCompile = false;
      neverCompile = true;
      compilerAvailable = false;
   }

   public void allocChildClasses(Compilation var1) {
      this.declareClosureEnv();
      if(var1.usingCPStyle()) {
         this.allocFrame(var1);
      }
   }

   void allocFields(Compilation var1) {
      Declaration var2;
      for(var2 = this.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         if((!var2.isSimple() || var2.isPublic()) && var2.field == null && var2.getFlag(65536L) && var2.getFlag(6L)) {
            var2.makeField(var1, (Expression)null);
         }
      }

      for(var2 = this.firstDecl(); var2 != null; var2 = var2.nextDecl()) {
         if(var2.field == null) {
            Expression var4 = var2.getValue();
            if((!var2.isSimple() || var2.isPublic() || var2.isNamespaceDecl() || var2.getFlag(16384L) && var2.getFlag(6L)) && !var2.getFlag(65536L)) {
               if(var4 instanceof LambdaExp && !(var4 instanceof ModuleExp) && !(var4 instanceof ClassExp)) {
                  ((LambdaExp)var4).allocFieldFor(var1);
               } else {
                  Expression var3 = var4;
                  if(!var2.shouldEarlyInit()) {
                     if(var2.isAlias()) {
                        var3 = var4;
                     } else {
                        var3 = null;
                     }
                  }

                  var2.makeField(var1, var3);
               }
            }
         }
      }

   }

   public ClassType classFor(Compilation var1) {
      ClassType var11;
      if(this.type != null && this.type != Compilation.typeProcedure) {
         var11 = this.type;
      } else {
         String var3 = this.getFileName();
         String var2 = this.getName();
         Path var4 = null;
         Path var7;
         if(var2 != null) {
            var7 = var4;
         } else {
            String var5;
            if(var3 == null) {
               var5 = this.getName();
               var2 = var5;
               var7 = var4;
               if(var5 == null) {
                  var2 = "$unnamed_input_file$";
                  var7 = var4;
               }
            } else if(!this.filename.equals("-") && !this.filename.equals("/dev/stdin")) {
               var4 = Path.valueOf(var3);
               var5 = var4.getLast();
               int var6 = var5.lastIndexOf(46);
               var2 = var5;
               var7 = var4;
               if(var6 > 0) {
                  var2 = var5.substring(0, var6);
                  var7 = var4;
               }
            } else {
               var5 = this.getName();
               var2 = var5;
               var7 = var4;
               if(var5 == null) {
                  var2 = "$stdin$";
                  var7 = var4;
               }
            }
         }

         if(this.getName() == null) {
            this.setName(var2);
         }

         label56: {
            String var8 = Compilation.mangleNameIfNeeded(var2);
            if(var1.classPrefix.length() == 0 && var7 != null && !var7.isAbsolute()) {
               Path var10 = var7.getParent();
               if(var10 != null) {
                  var2 = var10.toString();
                  if(var2.length() > 0 && var2.indexOf("..") < 0) {
                     var3 = var2.replaceAll(System.getProperty("file.separator"), "/");
                     var2 = var3;
                     if(var3.startsWith("./")) {
                        var2 = var3.substring(2);
                     }

                     if(var2.equals(".")) {
                        var2 = var8;
                     } else {
                        var2 = Compilation.mangleURI(var2) + "." + var8;
                     }
                     break label56;
                  }
               }
            }

            var2 = var1.classPrefix + var8;
         }

         ClassType var9 = new ClassType(var2);
         this.setType(var9);
         var11 = var9;
         if(var1.mainLambda == this) {
            if(var1.mainClass == null) {
               var1.mainClass = var9;
               return var9;
            }

            var11 = var9;
            if(!var2.equals(var1.mainClass.getName())) {
               var1.error('e', "inconsistent main class name: " + var2 + " - old name: " + var1.mainClass.getName());
               return var9;
            }
         }
      }

      return var11;
   }

   public Declaration firstDecl() {
      // $FF: Couldn't be decompiled
   }

   public final ClassType[] getInterfaces() {
      return this.interfaces;
   }

   public String getNamespaceUri() {
      return this.info.uri;
   }

   public final ClassType getSuperType() {
      return this.superType;
   }

   public final boolean isStatic() {
      return this.getFlag('耀') || (Compilation.moduleStatic >= 0 || this.getFlag(1048576)) && !this.getFlag(131072) && !this.getFlag(65536);
   }

   public void print(OutPort var1) {
      var1.startLogicalBlock("(Module/", ")", 2);
      Object var2 = this.getSymbol();
      if(var2 != null) {
         var1.print((Object)var2);
         var1.print('/');
      }

      var1.print(this.id);
      var1.print('/');
      var1.writeSpaceFill();
      var1.startLogicalBlock("(", false, ")");
      Declaration var3 = this.firstDecl();
      if(var3 != null) {
         var1.print((String)"Declarations:");

         while(var3 != null) {
            var1.writeSpaceFill();
            var3.printInfo((OutPort)var1);
            var3 = var3.nextDecl();
         }
      }

      var1.endLogicalBlock(")");
      var1.writeSpaceLinear();
      if(this.body == null) {
         var1.print((String)"<null body>");
      } else {
         this.body.print((OutPort)var1);
      }

      var1.endLogicalBlock(")");
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      Object var2 = var1.readObject();
      if(var2 instanceof ClassType) {
         this.type = (ClassType)var2;
         this.setName(this.type.getName());
      } else {
         this.setName((String)var2);
      }

      this.flags |= 524288;
   }

   public final void setInterfaces(ClassType[] var1) {
      this.interfaces = var1;
   }

   public final void setSuperType(ClassType var1) {
      this.superType = var1;
   }

   public boolean staticInitRun() {
      return this.isStatic() && (this.getFlag(262144) || Compilation.moduleStatic == 2);
   }

   protected Object visit(ExpVisitor var1, Object var2) {
      return var1.visitModuleExp(this, var2);
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      String var2 = null;
      if(this.type != null && this.type != Compilation.typeProcedure && !this.type.isExisting()) {
         var1.writeObject(this.type);
      } else {
         if(true) {
            var2 = this.getName();
         }

         String var3 = var2;
         if(var2 == null) {
            var3 = this.getFileName();
         }

         var1.writeObject(var3);
      }
   }
}
