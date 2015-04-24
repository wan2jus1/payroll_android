package gnu.bytecode;

import gnu.bytecode.AttrContainer;
import gnu.bytecode.Attribute;
import gnu.bytecode.ClassFileInput;
import gnu.bytecode.ClassType;
import gnu.bytecode.ClassTypeWriter;
import gnu.bytecode.ConstantPool;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class dump extends ClassFileInput {

   ClassTypeWriter writer;


   dump(InputStream var1, ClassTypeWriter var2) throws IOException, ClassFormatError {
      super(var1);
      this.readFormatVersion();
      this.readConstants();
      this.readClassInfo();
      this.readFields();
      this.readMethods();
      this.readAttributes(this.ctype);
      var2.print(this.ctype);
      var2.flush();
   }

   public static void main(String[] param0) {
      // $FF: Couldn't be decompiled
   }

   public static void process(InputStream var0, String var1, ClassTypeWriter var2) throws IOException {
      BufferedInputStream var5 = new BufferedInputStream(var0);
      var5.mark(5);
      int var4 = readMagic(var5);
      if(var4 == -889275714) {
         var2.print("Reading .class from ");
         var2.print(var1);
         var2.println('.');
         new dump(var5, var2);
      } else if(var4 == 1347093252) {
         var5.reset();
         var2.print("Reading classes from archive ");
         var2.print(var1);
         var2.println('.');
         ZipInputStream var6 = new ZipInputStream(var5);

         while(true) {
            ZipEntry var7 = var6.getNextEntry();
            if(var7 == null) {
               System.exit(-1);
               return;
            }

            String var3 = var7.getName();
            if(var7.isDirectory()) {
               var2.print("Archive directory: ");
               var2.print(var3);
               var2.println('.');
            } else {
               var2.println();
               if(readMagic(var6) == -889275714) {
                  var2.print("Reading class member: ");
                  var2.print(var3);
                  var2.println('.');
                  new dump(var6, var2);
               } else {
                  var2.print("Skipping non-class member: ");
                  var2.print(var3);
                  var2.println('.');
               }
            }
         }
      } else {
         System.err.println("File " + var1 + " is not a valid .class file");
         System.exit(-1);
      }
   }

   public static void process(InputStream var0, String var1, OutputStream var2, int var3) throws IOException {
      process(var0, var1, new ClassTypeWriter((ClassType)null, var2, var3));
   }

   public static void process(InputStream var0, String var1, Writer var2, int var3) throws IOException {
      process(var0, var1, new ClassTypeWriter((ClassType)null, var2, var3));
   }

   static int readMagic(InputStream var0) throws IOException {
      int var2 = 0;

      for(int var1 = 0; var1 < 4; ++var1) {
         int var3 = var0.read();
         if(var3 < 0) {
            break;
         }

         var2 = var2 << 8 | var3 & 255;
      }

      return var2;
   }

   static int uriSchemeLength(String var0) {
      int var3 = var0.length();
      int var2 = 0;

      while(true) {
         if(var2 >= var3) {
            return -1;
         }

         char var1 = var0.charAt(var2);
         if(var1 == 58) {
            return var2;
         }

         if(var2 == 0) {
            if(!Character.isLetter(var1)) {
               break;
            }
         } else if(!Character.isLetterOrDigit(var1) && var1 != 43 && var1 != 45 && var1 != 46) {
            break;
         }

         ++var2;
      }

      return -1;
   }

   static boolean uriSchemeSpecified(String var0) {
      boolean var2 = true;
      boolean var3 = false;
      int var1 = uriSchemeLength(var0);
      if(var1 == 1 && File.separatorChar == 92) {
         char var4 = var0.charAt(0);
         if(var4 >= 97) {
            var2 = var3;
            if(var4 <= 122) {
               return var2;
            }
         }

         if(var4 >= 65) {
            var2 = var3;
            if(var4 <= 90) {
               return var2;
            }
         }

         var2 = true;
         return var2;
      } else {
         if(var1 <= 0) {
            var2 = false;
         }

         return var2;
      }
   }

   public static void usage(PrintStream var0) {
      var0.println("Prints and dis-assembles the contents of JVM .class files.");
      var0.println("Usage: [--verbose] class-or-jar ...");
      var0.println("where a class-or-jar can be one of:");
      var0.println("- a fully-qualified class name; or");
      var0.println("- the name of a .class file, or a URL reference to one; or");
      var0.println("- the name of a .jar or .zip archive file, or a URL reference to one.");
      var0.println("If a .jar/.zip archive is named, all its.class file members are printed.");
      var0.println();
      var0.println("You can name a single .class member of an archive with a jar: URL,");
      var0.println("which looks like: jar:jar-spec!/p1/p2/cl.class");
      var0.println("The jar-spec can be a URL or the name of the .jar file.");
      var0.println("You can also use the shorthand syntax: jar:jar-spec!p1.p2.cl");
      System.exit(-1);
   }

   public Attribute readAttribute(String var1, int var2, AttrContainer var3) throws IOException {
      return super.readAttribute(var1, var2, var3);
   }

   public ConstantPool readConstants() throws IOException {
      this.ctype.constants = super.readConstants();
      return this.ctype.constants;
   }
}
