package gnu.kawa.xml;

import gnu.kawa.xml.XStringType;

public class XString implements CharSequence {

   public String text;
   private XStringType type;


   XString(String var1, XStringType var2) {
      this.text = var1;
      this.type = var2;
   }

   public char charAt(int var1) {
      return this.text.charAt(var1);
   }

   public XStringType getStringType() {
      return this.type;
   }

   public int length() {
      return this.text.length();
   }

   public CharSequence subSequence(int var1, int var2) {
      return this.text.substring(var1, var2);
   }

   public String toString() {
      return this.text;
   }
}
