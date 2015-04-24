package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;
import kawa.standard.object;

public class define_class extends Syntax {

   public static final define_class define_class = new define_class("define-class", false);
   public static final define_class define_simple_class = new define_class("define-simple-class", true);
   boolean isSimple;
   object objectSyntax;


   define_class(String var1, boolean var2) {
      super(var1);
      this.objectSyntax = object.objectSyntax;
      this.isSimple = var2;
   }

   define_class(object var1, boolean var2) {
      this.objectSyntax = var1;
      this.isSimple = var2;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      Declaration var3 = null;
      Object var4 = var1.getCdr();
      if(var4 instanceof Pair) {
         var1 = (Pair)var4;
         Object var6 = var1.getCar();
         if(!(var6 instanceof Declaration)) {
            return var2.syntaxError(this.getName() + " can only be used in <body>");
         }

         var3 = (Declaration)var6;
      }

      ClassExp var7 = (ClassExp)var3.getValue();
      this.objectSyntax.rewriteClassDef((Object[])((Object[])var1.getCdr()), var2);
      SetExp var5 = new SetExp(var3, var7);
      var5.setDefining(true);
      return var5;
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      Object var6 = var1.getCdr();

      SyntaxForm var5;
      for(var5 = null; var6 instanceof SyntaxForm; var6 = var5.getDatum()) {
         var5 = (SyntaxForm)var6;
      }

      if(!(var6 instanceof Pair)) {
         return super.scanForDefinitions(var1, var2, var3, var4);
      } else {
         Pair var9 = (Pair)var6;

         for(var6 = var9.getCar(); var6 instanceof SyntaxForm; var6 = var5.getDatum()) {
            var5 = (SyntaxForm)var6;
         }

         var6 = var4.namespaceResolve(var6);
         if(!(var6 instanceof String) && !(var6 instanceof Symbol)) {
            var4.error('e', "missing class name");
            return false;
         } else {
            Declaration var7 = var4.define(var6, var5, var3);
            if(var9 instanceof PairWithPosition) {
               var7.setLocation((PairWithPosition)var9);
            }

            ClassExp var8 = new ClassExp(this.isSimple);
            var7.noteValue(var8);
            var7.setFlag(536887296L);
            ClassType var11;
            if(this.isSimple) {
               var11 = Compilation.typeClass;
            } else {
               var11 = Compilation.typeClassType;
            }

            var7.setType(var11);
            var4.mustCompileHere();
            String var12;
            if(var6 instanceof Symbol) {
               var12 = ((Symbol)var6).getName();
            } else {
               var12 = var6.toString();
            }

            int var10 = var12.length();
            String var15 = var12;
            if(var10 > 2) {
               var15 = var12;
               if(var12.charAt(0) == 60) {
                  var15 = var12;
                  if(var12.charAt(var10 - 1) == 62) {
                     var15 = var12.substring(1, var10 - 1);
                  }
               }
            }

            var8.setName(var15);

            Object var13;
            for(var13 = var9.getCdr(); var13 instanceof SyntaxForm; var13 = var5.getDatum()) {
               var5 = (SyntaxForm)var13;
            }

            if(!(var13 instanceof Pair)) {
               var4.error('e', "missing class members");
               return false;
            } else {
               Pair var14 = (Pair)var13;
               ScopeExp var17 = var4.currentScope();
               if(var5 != null) {
                  var4.setCurrentScope(var5.getScope());
               }

               Object[] var16 = this.objectSyntax.scanClassDef(var14, var8, var4);
               if(var5 != null) {
                  var4.setCurrentScope(var17);
               }

               if(var16 == null) {
                  return false;
               } else {
                  var2.addElement(Translator.makePair(var1, this, Translator.makePair(var14, var7, var16)));
                  return true;
               }
            }
         }
      }
   }
}
