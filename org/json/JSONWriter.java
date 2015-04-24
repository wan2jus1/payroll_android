package org.json;

import java.io.IOException;
import java.io.Writer;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWriter {

   private static final int maxdepth = 200;
   private boolean comma = false;
   protected char mode = 105;
   private final JSONObject[] stack = new JSONObject[200];
   private int top = 0;
   protected Writer writer;


   public JSONWriter(Writer var1) {
      this.writer = var1;
   }

   private JSONWriter append(String var1) throws JSONException {
      if(var1 == null) {
         throw new JSONException("Null pointer");
      } else if(this.mode != 111 && this.mode != 97) {
         throw new JSONException("Value out of sequence.");
      } else {
         try {
            if(this.comma && this.mode == 97) {
               this.writer.write(44);
            }

            this.writer.write(var1);
         } catch (IOException var2) {
            throw new JSONException(var2);
         }

         if(this.mode == 111) {
            this.mode = 107;
         }

         this.comma = true;
         return this;
      }
   }

   private JSONWriter end(char var1, char var2) throws JSONException {
      if(this.mode != var1) {
         String var3;
         if(var1 == 97) {
            var3 = "Misplaced endArray.";
         } else {
            var3 = "Misplaced endObject.";
         }

         throw new JSONException(var3);
      } else {
         this.pop(var1);

         try {
            this.writer.write(var2);
         } catch (IOException var4) {
            throw new JSONException(var4);
         }

         this.comma = true;
         return this;
      }
   }

   private void pop(char var1) throws JSONException {
      byte var2 = 97;
      if(this.top <= 0) {
         throw new JSONException("Nesting error.");
      } else {
         byte var3;
         if(this.stack[this.top - 1] == null) {
            var3 = 97;
         } else {
            var3 = 107;
         }

         if(var3 != var1) {
            throw new JSONException("Nesting error.");
         } else {
            --this.top;
            if(this.top == 0) {
               var2 = 100;
            } else if(this.stack[this.top - 1] != null) {
               var2 = 107;
            }

            this.mode = (char)var2;
         }
      }
   }

   private void push(JSONObject var1) throws JSONException {
      if(this.top >= 200) {
         throw new JSONException("Nesting too deep.");
      } else {
         this.stack[this.top] = var1;
         byte var2;
         if(var1 == null) {
            var2 = 97;
         } else {
            var2 = 107;
         }

         this.mode = (char)var2;
         ++this.top;
      }
   }

   public JSONWriter array() throws JSONException {
      if(this.mode != 105 && this.mode != 111 && this.mode != 97) {
         throw new JSONException("Misplaced array.");
      } else {
         this.push((JSONObject)null);
         this.append("[");
         this.comma = false;
         return this;
      }
   }

   public JSONWriter endArray() throws JSONException {
      return this.end('a', ']');
   }

   public JSONWriter endObject() throws JSONException {
      return this.end('k', '}');
   }

   public JSONWriter key(String var1) throws JSONException {
      if(var1 == null) {
         throw new JSONException("Null key.");
      } else if(this.mode == 107) {
         try {
            this.stack[this.top - 1].putOnce(var1, Boolean.TRUE);
            if(this.comma) {
               this.writer.write(44);
            }

            this.writer.write(JSONObject.quote(var1));
            this.writer.write(58);
            this.comma = false;
            this.mode = 111;
            return this;
         } catch (IOException var2) {
            throw new JSONException(var2);
         }
      } else {
         throw new JSONException("Misplaced key.");
      }
   }

   public JSONWriter object() throws JSONException {
      if(this.mode == 105) {
         this.mode = 111;
      }

      if(this.mode != 111 && this.mode != 97) {
         throw new JSONException("Misplaced object.");
      } else {
         this.append("{");
         this.push(new JSONObject());
         this.comma = false;
         return this;
      }
   }

   public JSONWriter value(double var1) throws JSONException {
      return this.value(new Double(var1));
   }

   public JSONWriter value(long var1) throws JSONException {
      return this.append(Long.toString(var1));
   }

   public JSONWriter value(Object var1) throws JSONException {
      return this.append(JSONObject.valueToString(var1));
   }

   public JSONWriter value(boolean var1) throws JSONException {
      String var2;
      if(var1) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      return this.append(var2);
   }
}
