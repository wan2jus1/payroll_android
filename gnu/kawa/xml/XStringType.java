package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XString;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import java.util.regex.Pattern;

public class XStringType extends XDataType {

   public static final XStringType ENTITYType = new XStringType("ENTITY", NCNameType, 47, (String)null);
   public static final XStringType IDREFType = new XStringType("IDREF", NCNameType, 46, (String)null);
   public static final XStringType IDType = new XStringType("ID", NCNameType, 45, (String)null);
   public static final XStringType NCNameType = new XStringType("NCName", NameType, 44, (String)null);
   public static final XStringType NMTOKENType = new XStringType("NMTOKEN", tokenType, 42, "\\c+");
   public static final XStringType NameType = new XStringType("Name", tokenType, 43, (String)null);
   static ClassType XStringType = ClassType.make("gnu.kawa.xml.XString");
   public static final XStringType languageType = new XStringType("language", tokenType, 41, "[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*");
   public static final XStringType normalizedStringType = new XStringType("normalizedString", stringType, 39, (String)null);
   public static final XStringType tokenType = new XStringType("token", normalizedStringType, 40, (String)null);
   Pattern pattern;


   public XStringType(String var1, XDataType var2, int var3, String var4) {
      super(var1, XStringType, var3);
      this.baseType = var2;
      if(var4 != null) {
         this.pattern = Pattern.compile(var4);
      }

   }

   public static XString makeNCName(String var0) {
      return (XString)NCNameType.valueOf(var0);
   }

   public Object cast(Object var1) {
      if(var1 instanceof XString) {
         XString var2 = (XString)var1;
         if(var2.getStringType() == this) {
            return var2;
         }
      }

      return this.valueOf((String)stringType.cast(var1));
   }

   public boolean isInstance(Object var1) {
      if(var1 instanceof XString) {
         for(var1 = ((XString)var1).getStringType(); var1 != null; var1 = ((XDataType)var1).baseType) {
            if(var1 == this) {
               return true;
            }
         }
      }

      return false;
   }

   public String matches(String var1) {
      boolean var2;
      switch(this.typeCode) {
      case 39:
      case 40:
         if(this.typeCode != 39) {
            var2 = true;
         } else {
            var2 = false;
         }

         if(var1 == TextUtils.replaceWhitespace(var1, var2)) {
            var2 = true;
         } else {
            var2 = false;
         }
         break;
      case 41:
      default:
         if(this.pattern != null && !this.pattern.matcher(var1).matches()) {
            var2 = false;
         } else {
            var2 = true;
         }
         break;
      case 42:
         var2 = XName.isNmToken(var1);
         break;
      case 43:
         var2 = XName.isName(var1);
         break;
      case 44:
      case 45:
      case 46:
      case 47:
         var2 = XName.isNCName(var1);
      }

      return var2?null:"not a valid XML " + this.getName();
   }

   public Object valueOf(String var1) {
      boolean var2;
      if(this != normalizedStringType) {
         var2 = true;
      } else {
         var2 = false;
      }

      var1 = TextUtils.replaceWhitespace(var1, var2);
      if(this.matches(var1) != null) {
         throw new ClassCastException("cannot cast " + var1 + " to " + this.name);
      } else {
         return new XString(var1, this);
      }
   }
}
