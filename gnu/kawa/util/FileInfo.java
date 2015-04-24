package gnu.kawa.util;

import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.text.Path;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileInfo {

   static final Pattern childPat = Pattern.compile("<a .*</a>");
   static Hashtable fileMap = new Hashtable();
   static final Pattern linkPat = Pattern.compile(" href=[\'\"]([^\'\"]*)[\'\"]");
   static final Pattern parentPat = Pattern.compile("<ul[^>]* parent=[\'\"]([^\'\"]*)[\'\"]");
   StringBuffer beforeNavbarText;
   ByteArrayOutputStream bout;
   String[] childLinkText;
   OutPort cout;
   File file;
   FileInputStream fin;
   InPort in;
   int nchildren;
   StringBuffer newNavbarText;
   StringBuffer oldNavbarText;
   FileInfo parent;
   String parentName;
   String path;
   boolean scanned;
   boolean writeNeeded;


   public static FileInfo find(File var0) throws Throwable {
      String var3 = var0.getCanonicalPath();
      FileInfo var2 = (FileInfo)fileMap.get(var3);
      FileInfo var1 = var2;
      if(var2 == null) {
         var1 = new FileInfo();
         var1.file = var0;
         fileMap.put(var3, var1);
      }

      return var1;
   }

   public void scan() throws Throwable {
      if(!this.scanned) {
         this.scanned = true;
         this.fin = new FileInputStream(this.file);
         this.in = new InPort(new BufferedInputStream(this.fin));
         this.oldNavbarText = new StringBuffer();
         this.newNavbarText = new StringBuffer();
         if(this.writeNeeded) {
            this.bout = new ByteArrayOutputStream();
            this.cout = new OutPort(this.bout);
         }

         boolean var6 = false;
         boolean var4 = false;
         Vector var1 = new Vector();

         while(true) {
            String var2 = this.in.readLine();
            if(var2 == null) {
               break;
            }

            boolean var5;
            boolean var7;
            if(var6) {
               if(var2.indexOf("<!--end-generated-navbar-->") >= 0) {
                  break;
               }

               this.oldNavbarText.append(var2);
               this.oldNavbarText.append('\n');
               var5 = var4;
               if(var4) {
                  if(var2.indexOf("<!--end-children-toc-->") >= 0) {
                     var5 = false;
                  } else {
                     Matcher var3 = childPat.matcher(var2);
                     if(var3.find()) {
                        var1.add(var3.group());
                     }

                     var3 = parentPat.matcher(var2);
                     var5 = var4;
                     if(var3.find()) {
                        var5 = var4;
                        if(this.parentName == null) {
                           this.parentName = var3.group(1);
                           var5 = var4;
                        }
                     }
                  }
               }

               var7 = var6;
               if(var2.indexOf("<!--start-children-toc-->") >= 0) {
                  var5 = true;
                  var7 = var6;
               }
            } else {
               var5 = var4;
               var7 = var6;
               if(var2.indexOf("<!--start-generated-navbar-->") >= 0) {
                  var7 = true;
                  var5 = var4;
               }
            }

            var4 = var5;
            var6 = var7;
            if(this.writeNeeded) {
               var4 = var5;
               var6 = var7;
               if(!var7) {
                  this.cout.println(var2);
                  var4 = var5;
                  var6 = var7;
               }
            }
         }

         String[] var9 = new String[var1.size()];
         this.nchildren = var9.length;
         var1.copyInto(var9);
         this.childLinkText = var9;
         if(!this.writeNeeded) {
            this.in.close();
         }

         if(this.parentName != null) {
            FileInfo var8 = find(new File(this.file.toURI().resolve(this.parentName)));
            var8.scan();
            this.parent = var8;
            return;
         }
      }

   }

   public void write() throws Throwable {
      int var2 = 0;

      for(FileInfo var1 = this; var1.parent != null; ++var2) {
         var1 = var1.parent;
      }

      this.cout.println("<!--start-generated-navbar-->");
      this.writeLinks(var2, this.newNavbarText);
      this.cout.print((Object)this.newNavbarText);
      this.cout.println("<!--end-generated-navbar-->");

      while(true) {
         String var3 = this.in.readLine();
         if(var3 == null) {
            new StringBuffer();
            this.in.close();
            if(this.oldNavbarText.toString().equals(this.newNavbarText.toString())) {
               System.err.println("fixup " + this.file + " - no change");
               return;
            } else {
               FileOutputStream var4 = new FileOutputStream(this.file);
               var4.write(this.bout.toByteArray());
               var4.close();
               System.err.println("fixup " + this.file + " - updated");
               return;
            }
         }

         this.cout.println(var3);
      }
   }

   public void writeLinks(int var1, StringBuffer var2) throws Throwable {
      FileInfo var3 = this;
      FileInfo var4 = null;
      int var10 = var1;

      while(true) {
         --var10;
         if(var10 < 0) {
            if(var1 == 0) {
               var2.append("<!--start-children-toc-->\n");
            }

            if(var1 == 0 && this.parentName != null) {
               var2.append("<ul parent=\"");
               var2.append(this.parentName);
               var2.append("\">\n");
            } else {
               var2.append("<ul>\n");
            }

            URI var8 = this.file.toURI();
            URI var9 = var3.file.toURI();

            for(int var11 = 0; var11 < var3.nchildren; ++var11) {
               String var6 = var3.childLinkText[var11];
               boolean var12 = false;
               String var5 = var6;
               if(var1 > 0) {
                  Matcher var13 = linkPat.matcher(var6);
                  var13.find();
                  var6 = var13.group(1);
                  URI var7 = var9.resolve(var6);
                  String var14 = var13.replaceFirst(" href=\"" + Path.relativize(var7.toString(), var8.toString()) + "\"");
                  var10 = var6.indexOf(35);
                  var5 = var6;
                  if(var10 >= 0) {
                     var5 = var6.substring(0, var10);
                  }

                  boolean var15;
                  if(find(new File(var9.resolve(var5))) == var4) {
                     var15 = true;
                  } else {
                     var15 = false;
                  }

                  var12 = var15;
                  var5 = var14;
                  if(!var15) {
                     var12 = var15;
                     var5 = var14;
                     if(var1 > 1) {
                        continue;
                     }
                  }
               }

               var2.append("<li>");
               var2.append(var5);
               if(var12) {
                  var2.append('\n');
                  this.writeLinks(var1 - 1, var2);
               }

               var2.append("</li>\n");
            }

            var2.append("</ul>\n");
            if(var1 == 0) {
               var2.append("<!--end-children-toc-->\n");
            }

            return;
         }

         var4 = var3;
         var3 = var3.parent;
      }
   }
}
