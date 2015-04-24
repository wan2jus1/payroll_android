package gnu.mapping;

import gnu.mapping.Location;
import gnu.mapping.Symbol;
import gnu.text.SourceLocator;

public class UnboundLocationException extends RuntimeException {

   int column;
   String filename;
   int line;
   Location location;
   public Object symbol;


   public UnboundLocationException() {
   }

   public UnboundLocationException(Location var1) {
      this.location = var1;
   }

   public UnboundLocationException(Object var1) {
      this.symbol = var1;
   }

   public UnboundLocationException(Object var1, SourceLocator var2) {
      this.symbol = var1;
      if(var2 != null) {
         this.filename = var2.getFileName();
         this.line = var2.getLineNumber();
         this.column = var2.getColumnNumber();
      }

   }

   public UnboundLocationException(Object var1, String var2) {
      super(var2);
      this.symbol = var1;
   }

   public UnboundLocationException(Object var1, String var2, int var3, int var4) {
      this.symbol = var1;
      this.filename = var2;
      this.line = var3;
      this.column = var4;
   }

   public String getMessage() {
      String var1 = super.getMessage();
      if(var1 != null) {
         return var1;
      } else {
         StringBuffer var2 = new StringBuffer();
         if(this.filename != null || this.line > 0) {
            if(this.filename != null) {
               var2.append(this.filename);
            }

            if(this.line >= 0) {
               var2.append(':');
               var2.append(this.line);
               if(this.column > 0) {
                  var2.append(':');
                  var2.append(this.column);
               }
            }

            var2.append(": ");
         }

         Symbol var3;
         if(this.location == null) {
            var3 = null;
         } else {
            var3 = this.location.getKeySymbol();
         }

         if(var3 != null) {
            var2.append("unbound location ");
            var2.append(var3);
            Object var4 = this.location.getKeyProperty();
            if(var4 != null) {
               var2.append(" (property ");
               var2.append(var4);
               var2.append(')');
            }
         } else if(this.symbol != null) {
            var2.append("unbound location ");
            var2.append(this.symbol);
         } else {
            var2.append("unbound location");
         }

         return var2.toString();
      }
   }

   public void setLine(String var1, int var2, int var3) {
      this.filename = var1;
      this.line = var2;
      this.column = var3;
   }

   public String toString() {
      return this.getMessage();
   }
}
