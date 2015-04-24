package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KComment;
import gnu.kawa.xml.KDocument;
import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KProcessingInstruction;
import gnu.kawa.xml.OutputAsXML;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;

public class XML extends ModuleBody {

   public static final XML $instance = new XML();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("parse-xml-from-url")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("element-name")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("attribute-name")).readResolve();
   public static OutputAsXML as$Mnxml;
   public static final ModuleMethod attribute$Mnname;
   public static final Class comment = KComment.class;
   public static final ModuleMethod element$Mnname;
   public static final ModuleMethod parse$Mnxml$Mnfrom$Mnurl;
   public static final Class processing$Mninstruction = KProcessingInstruction.class;


   static {
      XML var0 = $instance;
      parse$Mnxml$Mnfrom$Mnurl = new ModuleMethod(var0, 1, Lit0, 4097);
      element$Mnname = new ModuleMethod(var0, 2, Lit1, 4097);
      attribute$Mnname = new ModuleMethod(var0, 3, Lit2, 4097);
      $instance.run();
   }

   public XML() {
      ModuleInfo.register(this);
   }

   public static Symbol attributeName(KAttr var0) {
      return var0.getNodeSymbol();
   }

   public static Symbol elementName(KElement var0) {
      return var0.getNodeSymbol();
   }

   public static KDocument parseXmlFromUrl(Object var0) {
      return Document.parse(var0);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 1:
         return parseXmlFromUrl(var2);
      case 2:
         KElement var6;
         try {
            var6 = (KElement)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "element-name", 1, var2);
         }

         return elementName(var6);
      case 3:
         KAttr var5;
         try {
            var5 = (KAttr)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "attribute-name", 1, var2);
         }

         return attributeName(var5);
      default:
         return super.apply1(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = -786431;
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
         if(var2 instanceof KElement) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 3:
         if(var2 instanceof KAttr) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      default:
         var4 = super.match1(var1, var2, var3);
      }

      return var4;
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
      as$Mnxml = new OutputAsXML();
   }
}
