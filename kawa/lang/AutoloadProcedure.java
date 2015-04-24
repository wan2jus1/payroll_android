package kawa.lang;

import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;

public class AutoloadProcedure extends Procedure implements Externalizable {

   static final Class classModuleBody = ModuleBody.class;
   String className;
   Language language;
   Procedure loaded;


   public AutoloadProcedure() {
   }

   public AutoloadProcedure(String var1, String var2) {
      super(var1);
      this.className = var2;
   }

   public AutoloadProcedure(String var1, String var2, Language var3) {
      super(var1);
      this.className = var2;
      this.language = var3;
   }

   private void throw_error(String var1) {
      this.loaded = null;
      String var3 = this.getName();
      StringBuilder var2 = (new StringBuilder()).append(var1).append(this.className).append(" while autoloading ");
      if(var3 == null) {
         var1 = "";
      } else {
         var1 = var3.toString();
      }

      throw new RuntimeException(var2.append(var1).toString());
   }

   public Object apply0() throws Throwable {
      return this.getLoaded().apply0();
   }

   public Object apply1(Object var1) throws Throwable {
      return this.getLoaded().apply1(var1);
   }

   public Object apply2(Object var1, Object var2) throws Throwable {
      return this.getLoaded().apply2(var1, var2);
   }

   public Object apply3(Object var1, Object var2, Object var3) throws Throwable {
      return this.getLoaded().apply3(var1, var2, var3);
   }

   public Object apply4(Object var1, Object var2, Object var3, Object var4) throws Throwable {
      return this.getLoaded().apply4(var1, var2, var3, var4);
   }

   public Object applyN(Object[] var1) throws Throwable {
      if(this.loaded == null) {
         this.load();
      }

      if(this.loaded instanceof AutoloadProcedure) {
         throw new InternalError("circularity in autoload of " + this.getName());
      } else {
         return this.loaded.applyN(var1);
      }
   }

   public Procedure getLoaded() {
      if(this.loaded == null) {
         this.load();
      }

      return this.loaded;
   }

   public Object getProperty(Object var1, Object var2) {
      Object var3 = super.getProperty(var1, (Object)null);
      return var3 != null?var3:this.getLoaded().getProperty(var1, var2);
   }

   public Procedure getSetter() {
      if(this.loaded == null) {
         this.load();
      }

      return this.loaded instanceof HasSetter?this.loaded.getSetter():super.getSetter();
   }

   void load() {
      // $FF: Couldn't be decompiled
   }

   public int numArgs() {
      return this.getLoaded().numArgs();
   }

   public void print(PrintWriter var1) {
      var1.print("#<procedure ");
      String var2 = this.getName();
      if(var2 != null) {
         var1.print(var2);
      }

      var1.print('>');
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      this.setName((String)var1.readObject());
      this.className = (String)var1.readObject();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      var1.writeObject(this.getName());
      var1.writeObject(this.className);
   }
}
