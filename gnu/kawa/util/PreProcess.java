package gnu.kawa.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class PreProcess {

   static final String JAVA4_FEATURES = "+JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio";
   static final String JAVA5_FEATURES = "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName";
   static final String NO_JAVA4_FEATURES = "-JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android";
   static final String NO_JAVA6_FEATURES = "-JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer";
   static String[] version_features = new String[]{"java1", "-JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java2", "+JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4x", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 +use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java6compat5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6 -JAVA7 +JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java6", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 -JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java7", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 +JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer +use:java.dyn -Android", "android", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 -JAXP-QName -use:javax.xml.transform -JAVA6 -JAVA6COMPAT5 +Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer"};
   String filename;
   Hashtable keywords = new Hashtable();
   int lineno;
   byte[] resultBuffer;
   int resultLength;


   public static void main(String[] var0) {
      PreProcess var1 = new PreProcess();
      var1.keywords.put("true", Boolean.TRUE);
      var1.keywords.put("false", Boolean.FALSE);

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1.handleArg(var0[var2]);
      }

   }

   void error(String var1) {
      System.err.println(this.filename + ':' + this.lineno + ": " + var1);
      System.exit(-1);
   }

   public void filter(String var1) throws Throwable {
      if(this.filter(var1, new BufferedInputStream(new FileInputStream(var1)))) {
         FileOutputStream var2 = new FileOutputStream(var1);
         var2.write(this.resultBuffer, 0, this.resultLength);
         var2.close();
         System.err.println("Pre-processed " + var1);
      }

   }

   public boolean filter(String var1, BufferedInputStream var2) throws Throwable {
      this.filename = var1;
      byte[] var3 = new byte[2000];
      int var8 = 0;
      int var17 = 0;
      int var11 = -1;
      this.lineno = 1;
      int var16 = -1;
      int var10 = 0;
      int var15 = 0;
      int var13 = 0;
      String var4 = null;
      boolean var20 = false;
      byte var12 = 0;
      int var14 = 0;

      byte[] var5;
      boolean var21;
      while(true) {
         int var18 = var2.read();
         if(var18 < 0) {
            var21 = var20;
            var5 = var3;
            var11 = var8;
            break;
         }

         if(var8 + 10 >= var3.length) {
            var5 = new byte[var8 * 2];
            System.arraycopy(var3, 0, var5, 0, var8);
            var3 = var5;
         }

         if(var18 == 10 && var8 > 0 && var3[var8 - 1] == 13) {
            var3[var8] = (byte)var18;
            ++var8;
         } else {
            int var9;
            byte var27;
            label248: {
               if(var16 >= 0 && var11 < 0 && var12 <= 0 && var18 != 13 && var18 != 10 && (var16 == var10 || var18 != 32 && var18 != 9)) {
                  boolean var25;
                  if(var18 == 47) {
                     var2.mark(100);
                     var9 = var2.read();
                     if(var9 == 47) {
                        var25 = false;
                     } else if(var9 == 42) {
                        do {
                           do {
                              var9 = var2.read();
                           } while(var9 == 32);
                        } while(var9 == 9);

                        if(var9 != 35) {
                           var25 = true;
                        } else {
                           var25 = false;
                        }
                     } else {
                        var25 = true;
                     }

                     var2.reset();
                  } else {
                     var25 = true;
                  }

                  if(var25) {
                     var9 = var8 + 1;
                     var3[var8] = 47;
                     var8 = var9 + 1;
                     var3[var9] = 47;
                     var3[var8] = 32;
                     var20 = true;
                     ++var8;
                     var27 = 1;
                     break label248;
                  }
               }

               var27 = var12;
            }

            byte var24;
            int var26;
            if(var18 != 32 && var18 != 9 && var11 < 0) {
               if(var15 > 0 && var16 != var10 && var18 == 47) {
                  var26 = var2.read();
                  var11 = var8;
                  var5 = var3;
                  var21 = var20;
                  if(var26 < 0) {
                     break;
                  }

                  if(var26 != 47) {
                     var3[var8] = 47;
                     var11 = var8 + 1;
                     var9 = var8;
                     var24 = var27;
                  } else {
                     label259: {
                        var26 = var2.read();
                        var11 = var8;
                        var5 = var3;
                        var21 = var20;
                        if(var26 < 0) {
                           break;
                        }

                        var9 = var26;
                        if(var26 == 32) {
                           label205: {
                              var26 = var2.read();
                              if(var26 != 32) {
                                 var9 = var26;
                                 if(var26 != 9) {
                                    break label205;
                                 }
                              }

                              byte var33 = -1;
                              var9 = -1;
                              var11 = var8;
                              var20 = true;
                              var24 = var33;
                              break label259;
                           }
                        }

                        var11 = var8;
                        var20 = true;
                        var26 = var9;
                        var24 = -1;
                        var9 = var8;
                     }
                  }
               } else {
                  var9 = var8;
                  var11 = var8;
                  var26 = var18;
                  var24 = var27;
               }
            } else {
               var24 = var27;
               var9 = var11;
               var11 = var8;
               var26 = var18;
            }

            var3[var11] = (byte)var26;
            var18 = var11 + 1;
            if(var26 != 13 && var26 != 10) {
               if(var9 < 0) {
                  if(var26 == 9) {
                     var10 = var10 + 8 & -8;
                  } else {
                     ++var10;
                  }

                  var11 = var14;
               } else {
                  var11 = var14;
               }
            } else {
               byte var29 = 0;
               int var32 = -1;
               var8 = var17;

               for(var17 = var29; var8 < var18 - 1; var32 = var11) {
                  var9 = var17;
                  var11 = var32;
                  if(var3[var8] != 32) {
                     var9 = var17;
                     var11 = var32;
                     if(var3[var8] != 9) {
                        if(var32 < 0) {
                           var9 = var8;
                           var11 = var8;
                        } else {
                           var9 = var8;
                           var11 = var32;
                        }
                     }
                  }

                  ++var8;
                  var17 = var9;
               }

               var8 = var14;
               String var22 = var4;
               var11 = var13;
               var9 = var16;
               var26 = var15;
               if(var17 - var32 >= 4) {
                  var8 = var14;
                  var22 = var4;
                  var11 = var13;
                  var9 = var16;
                  var26 = var15;
                  if(var3[var32] == 47) {
                     var8 = var14;
                     var22 = var4;
                     var11 = var13;
                     var9 = var16;
                     var26 = var15;
                     if(var3[var32 + 1] == 42) {
                        var8 = var14;
                        var22 = var4;
                        var11 = var13;
                        var9 = var16;
                        var26 = var15;
                        if(var3[var17 - 1] == 42) {
                           var8 = var14;
                           var22 = var4;
                           var11 = var13;
                           var9 = var16;
                           var26 = var15;
                           if(var3[var17] == 47) {
                              for(var32 += 2; var32 < var17 && var3[var32] == 32; ++var32) {
                                 ;
                              }

                              for(var17 -= 2; var17 > var32 && var3[var17] == 32; --var17) {
                                 ;
                              }

                              var8 = var14;
                              var22 = var4;
                              var11 = var13;
                              var9 = var16;
                              var26 = var15;
                              if(var3[var32] == 35) {
                                 String var7 = new String(var3, var32, var17 - var32 + 1, "ISO-8859-1");
                                 var9 = var7.indexOf(32);
                                 var8 = this.lineno;
                                 String var6;
                                 Object var23;
                                 if(var9 > 0) {
                                    var4 = var7.substring(0, var9);
                                    var6 = var7.substring(var9).trim();
                                    var23 = this.keywords.get(var6);
                                 } else {
                                    var6 = "";
                                    var23 = null;
                                    var4 = var7;
                                 }

                                 if(!"#ifdef".equals(var4) && !"#ifndef".equals(var4)) {
                                    if("#else".equals(var4)) {
                                       if(var15 == 0) {
                                          this.error("unexpected " + var4);
                                          var22 = var4;
                                          var11 = var13;
                                          var9 = var16;
                                          var26 = var15;
                                       } else if(var15 == var13) {
                                          var9 = -1;
                                          var11 = 0;
                                          var22 = var4;
                                          var26 = var15;
                                       } else if(var13 == 0) {
                                          var11 = var15;
                                          var9 = var10;
                                          var22 = var4;
                                          var26 = var15;
                                       } else {
                                          var9 = var10;
                                          var22 = var4;
                                          var11 = var13;
                                          var26 = var15;
                                       }
                                    } else if("#endif".equals(var4)) {
                                       if(var15 == 0) {
                                          this.error("unexpected " + var4);
                                       }

                                       if(var15 == var13) {
                                          var9 = 0;
                                          var10 = -1;
                                       } else if(var13 > 0) {
                                          var9 = var13;
                                       } else {
                                          var9 = var13;
                                          var10 = var16;
                                       }

                                       var26 = var15 - 1;
                                       var22 = var4;
                                       var11 = var9;
                                       var9 = var10;
                                    } else {
                                       this.error("unknown command: " + var7);
                                       var22 = var4;
                                       var11 = var13;
                                       var9 = var16;
                                       var26 = var15;
                                    }
                                 } else {
                                    if(var23 == null) {
                                       System.err.println(var1 + ":" + this.lineno + ": warning - undefined keyword: " + var6);
                                       var23 = Boolean.FALSE;
                                    }

                                    var9 = var15 + 1;
                                    if(var13 > 0) {
                                       var26 = var9;
                                       var9 = var10;
                                       var11 = var13;
                                       var22 = var4;
                                    } else {
                                       boolean var30;
                                       if(var4.charAt(3) == 110) {
                                          var30 = true;
                                       } else {
                                          var30 = false;
                                       }

                                       boolean var31;
                                       if(var23 == Boolean.FALSE) {
                                          var31 = true;
                                       } else {
                                          var31 = false;
                                       }

                                       if(var30 != var31) {
                                          var11 = var9;
                                          var26 = var9;
                                          var9 = var10;
                                          var22 = var4;
                                       } else {
                                          var26 = var9;
                                          var22 = var4;
                                          var11 = var13;
                                          var9 = var16;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               byte var19 = -1;
               var10 = 0;
               ++this.lineno;
               var17 = var18;
               byte var28 = 0;
               var15 = var26;
               var16 = var9;
               var13 = var11;
               var9 = var19;
               var4 = var22;
               var11 = var8;
               var24 = var28;
            }

            var14 = var11;
            var12 = var24;
            var11 = var9;
            var8 = var18;
         }
      }

      if(var15 != 0) {
         this.lineno = var14;
         this.error("unterminated " + var4);
      }

      this.resultBuffer = var5;
      this.resultLength = var11;
      return var21;
   }

   void handleArg(String var1) {
      int var5 = 0;
      if(var1.charAt(0) == 37) {
         var1 = var1.substring(1);

         while(true) {
            if(var5 >= version_features.length) {
               System.err.println("Unknown version: " + var1);
               System.exit(-1);
            }

            if(var1.equals(version_features[var5])) {
               String var2 = version_features[var5 + 1];
               System.err.println("(variant " + var1 + " maps to: " + var2 + ")");
               StringTokenizer var8 = new StringTokenizer(var2);

               while(var8.hasMoreTokens()) {
                  this.handleArg(var8.nextToken());
               }
               break;
            }

            var5 += 2;
         }
      } else {
         if(var1.charAt(0) != 43) {
            if(var1.charAt(0) == 45) {
               int var6 = var1.indexOf(61);
               if(var6 > 1) {
                  byte var11;
                  if(var1.charAt(1) == 45) {
                     var11 = 2;
                  } else {
                     var11 = 1;
                  }

                  String var3 = var1.substring(var11, var6);
                  String var4 = var1.substring(var6 + 1);
                  Boolean var10 = Boolean.FALSE;
                  Boolean var9;
                  if(var4.equalsIgnoreCase("true")) {
                     var9 = Boolean.TRUE;
                  } else {
                     var9 = var10;
                     if(!var4.equalsIgnoreCase("false")) {
                        System.err.println("invalid value " + var4 + " for " + var3);
                        System.exit(-1);
                        var9 = var10;
                     }
                  }

                  this.keywords.put(var3, var9);
                  return;
               }

               this.keywords.put(var1.substring(1), Boolean.FALSE);
               return;
            }

            try {
               this.filter(var1);
               return;
            } catch (Throwable var7) {
               System.err.println("caught " + var7);
               var7.printStackTrace();
               System.exit(-1);
               return;
            }
         }

         this.keywords.put(var1.substring(1), Boolean.TRUE);
      }

   }
}
