package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTokener {

   private long character;
   private boolean eof;
   private long index;
   private long line;
   private char previous;
   private Reader reader;
   private boolean usePrevious;


   public JSONTokener(InputStream var1) throws JSONException {
      this((Reader)(new InputStreamReader(var1)));
   }

   public JSONTokener(Reader var1) {
      if(!((Reader)var1).markSupported()) {
         var1 = new BufferedReader((Reader)var1);
      }

      this.reader = (Reader)var1;
      this.eof = false;
      this.usePrevious = false;
      this.previous = 0;
      this.index = 0L;
      this.character = 1L;
      this.line = 1L;
   }

   public JSONTokener(String var1) {
      this((Reader)(new StringReader(var1)));
   }

   public static int dehexchar(char var0) {
      return var0 >= 48 && var0 <= 57?var0 - 48:(var0 >= 65 && var0 <= 70?var0 - 55:(var0 >= 97 && var0 <= 102?var0 - 87:-1));
   }

   public void back() throws JSONException {
      if(!this.usePrevious && this.index > 0L) {
         --this.index;
         --this.character;
         this.usePrevious = true;
         this.eof = false;
      } else {
         throw new JSONException("Stepping back two steps is not supported");
      }
   }

   public boolean end() {
      return this.eof && !this.usePrevious;
   }

   public boolean more() throws JSONException {
      this.next();
      if(this.end()) {
         return false;
      } else {
         this.back();
         return true;
      }
   }

   public char next() throws JSONException {
      long var4 = 0L;
      int var2;
      if(this.usePrevious) {
         this.usePrevious = false;
         var2 = this.previous;
      } else {
         int var3;
         try {
            var3 = this.reader.read();
         } catch (IOException var6) {
            throw new JSONException(var6);
         }

         var2 = var3;
         if(var3 <= 0) {
            this.eof = true;
            var2 = 0;
         }
      }

      ++this.index;
      if(this.previous == 13) {
         ++this.line;
         if(var2 != 10) {
            var4 = 1L;
         }

         this.character = var4;
      } else if(var2 == 10) {
         ++this.line;
         this.character = 0L;
      } else {
         ++this.character;
      }

      this.previous = (char)var2;
      return this.previous;
   }

   public char next(char var1) throws JSONException {
      char var2 = this.next();
      if(var2 != var1) {
         throw this.syntaxError("Expected \'" + var1 + "\' and instead saw \'" + var2 + "\'");
      } else {
         return var2;
      }
   }

   public String next(int var1) throws JSONException {
      if(var1 == 0) {
         return "";
      } else {
         char[] var2 = new char[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = this.next();
            if(this.end()) {
               throw this.syntaxError("Substring bounds error");
            }
         }

         return new String(var2);
      }
   }

   public char nextClean() throws JSONException {
      char var1;
      do {
         var1 = this.next();
      } while(var1 != 0 && var1 <= 32);

      return var1;
   }

   public String nextString(char var1) throws JSONException {
      StringBuffer var3 = new StringBuffer();

      while(true) {
         char var2 = this.next();
         switch(var2) {
         case 0:
         case 10:
         case 13:
            throw this.syntaxError("Unterminated string");
         case 92:
            var2 = this.next();
            switch(var2) {
            case 34:
            case 39:
            case 47:
            case 92:
               var3.append(var2);
               continue;
            case 98:
               var3.append('\b');
               continue;
            case 102:
               var3.append('\f');
               continue;
            case 110:
               var3.append('\n');
               continue;
            case 114:
               var3.append('\r');
               continue;
            case 116:
               var3.append('\t');
               continue;
            case 117:
               var3.append((char)Integer.parseInt(this.next((int)4), 16));
               continue;
            default:
               throw this.syntaxError("Illegal escape.");
            }
         default:
            if(var2 == var1) {
               return var3.toString();
            }

            var3.append(var2);
         }
      }
   }

   public String nextTo(char var1) throws JSONException {
      StringBuffer var3 = new StringBuffer();

      while(true) {
         char var2 = this.next();
         if(var2 == var1 || var2 == 0 || var2 == 10 || var2 == 13) {
            if(var2 != 0) {
               this.back();
            }

            return var3.toString().trim();
         }

         var3.append(var2);
      }
   }

   public String nextTo(String var1) throws JSONException {
      StringBuffer var3 = new StringBuffer();

      while(true) {
         char var2 = this.next();
         if(var1.indexOf(var2) >= 0 || var2 == 0 || var2 == 10 || var2 == 13) {
            if(var2 != 0) {
               this.back();
            }

            return var3.toString().trim();
         }

         var3.append(var2);
      }
   }

   public Object nextValue() throws JSONException {
      char var1 = this.nextClean();
      switch(var1) {
      case 34:
      case 39:
         return this.nextString(var1);
      case 91:
         this.back();
         return new JSONArray(this);
      case 123:
         this.back();
         return new JSONObject(this);
      default:
         StringBuffer var2;
         for(var2 = new StringBuffer(); var1 >= 32 && ",:]}/\\\"[{;=#".indexOf(var1) < 0; var1 = this.next()) {
            var2.append(var1);
         }

         this.back();
         String var3 = var2.toString().trim();
         if("".equals(var3)) {
            throw this.syntaxError("Missing value");
         } else {
            return JSONObject.stringToValue(var3);
         }
      }
   }

   public char skipTo(char param1) throws JSONException {
      // $FF: Couldn't be decompiled
   }

   public JSONException syntaxError(String var1) {
      return new JSONException(var1 + this.toString());
   }

   public String toString() {
      return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
   }
}
