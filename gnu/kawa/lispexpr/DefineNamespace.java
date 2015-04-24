package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class DefineNamespace extends Syntax {

   public static final String XML_NAMESPACE_MAGIC = "&xml&";
   public static final DefineNamespace define_namespace = new DefineNamespace();
   public static final DefineNamespace define_private_namespace = new DefineNamespace();
   public static final DefineNamespace define_xml_namespace = new DefineNamespace();
   private boolean makePrivate;
   private boolean makeXML;


   static {
      define_namespace.setName("define-namespace");
      define_private_namespace.setName("define-private-namespace");
      define_private_namespace.makePrivate = true;
      define_xml_namespace.setName("define-xml-namespace");
      define_xml_namespace.makeXML = true;
   }

   public Expression rewriteForm(Pair var1, Translator var2) {
      return var2.syntaxError("define-namespace is only allowed in a <body>");
   }

   public boolean scanForDefinitions(Pair var1, Vector var2, ScopeExp var3, Translator var4) {
      if(var1.getCdr() instanceof Pair) {
         Pair var6 = (Pair)var1.getCdr();
         if(var6.getCar() instanceof Symbol && var6.getCdr() instanceof Pair) {
            Pair var7 = (Pair)var6.getCdr();
            if(var7.getCdr() == LList.Empty) {
               Symbol var8 = (Symbol)var6.getCar();
               Declaration var5 = var3.getDefine(var8, 'w', var4);
               var4.push(var5);
               var5.setFlag(2375680L);
               if(this.makePrivate) {
                  var5.setFlag(16777216L);
                  var5.setPrivate(true);
               } else if(var3 instanceof ModuleExp) {
                  var5.setCanRead(true);
               }

               Translator.setLine((Declaration)var5, var6);
               Object var9;
               if(var7.getCar() instanceof CharSequence) {
                  String var10 = var7.getCar().toString();
                  if(var10.startsWith("class:")) {
                     var9 = ClassNamespace.getInstance(var10, ClassType.make(var10.substring(6)));
                     var5.setType(ClassType.make("gnu.kawa.lispexpr.ClassNamespace"));
                  } else if(this.makeXML) {
                     var9 = XmlNamespace.getInstance(var8.getName(), var10);
                     var5.setType(ClassType.make("gnu.kawa.xml.XmlNamespace"));
                  } else {
                     var9 = Namespace.valueOf(var10);
                     var5.setType(ClassType.make("gnu.mapping.Namespace"));
                  }

                  var9 = new QuoteExp(var9);
                  var5.setFlag(8192L);
               } else {
                  var9 = var4.rewrite_car(var7, false);
               }

               var5.noteValue((Expression)var9);
               var2.addElement(SetExp.makeDefinition((Declaration)var5, (Expression)var9));
               return true;
            }
         }
      }

      var4.error('e', "invalid syntax for define-namespace");
      return false;
   }
}
