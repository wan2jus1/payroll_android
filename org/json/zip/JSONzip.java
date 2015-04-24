package org.json.zip;

import org.json.zip.Huff;
import org.json.zip.MapKeep;
import org.json.zip.None;
import org.json.zip.PostMortem;
import org.json.zip.TrieKeep;

public abstract class JSONzip implements None, PostMortem {

   public static final byte[] bcd = new byte[]{(byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)46, (byte)45, (byte)43, (byte)69};
   public static final int end = 256;
   public static final int endOfNumber = bcd.length;
   public static final long int14 = 16384L;
   public static final long int4 = 16L;
   public static final long int7 = 128L;
   public static final int maxSubstringLength = 10;
   public static final int minSubstringLength = 3;
   public static final boolean probe = false;
   public static final int substringLimit = 40;
   public static final int[] twos = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, '耀', 65536};
   public static final int zipArrayString = 6;
   public static final int zipArrayValue = 7;
   public static final int zipEmptyArray = 1;
   public static final int zipEmptyObject = 0;
   public static final int zipFalse = 3;
   public static final int zipNull = 4;
   public static final int zipObject = 5;
   public static final int zipTrue = 2;
   protected final Huff namehuff = new Huff(257);
   protected final MapKeep namekeep = new MapKeep(9);
   protected final MapKeep stringkeep = new MapKeep(11);
   protected final Huff substringhuff = new Huff(257);
   protected final TrieKeep substringkeep = new TrieKeep(12);
   protected final MapKeep values = new MapKeep(10);


   protected JSONzip() {
      this.namehuff.tick(32, 125);
      this.namehuff.tick(97, 122);
      this.namehuff.tick(256);
      this.namehuff.tick(256);
      this.substringhuff.tick(32, 125);
      this.substringhuff.tick(97, 122);
      this.substringhuff.tick(256);
      this.substringhuff.tick(256);
   }

   static void log() {
      log("\n");
   }

   static void log(int var0) {
      log(var0 + " ");
   }

   static void log(int var0, int var1) {
      log(var0 + ":" + var1 + " ");
   }

   static void log(String var0) {
      System.out.print(var0);
   }

   static void logchar(int var0, int var1) {
      if(var0 > 32 && var0 <= 125) {
         log("\'" + (char)var0 + "\':" + var1 + " ");
      } else {
         log(var0, var1);
      }
   }

   protected void begin() {
      this.namehuff.generate();
      this.substringhuff.generate();
   }

   public boolean postMortem(PostMortem var1) {
      JSONzip var2 = (JSONzip)var1;
      return this.namehuff.postMortem(var2.namehuff) && this.namekeep.postMortem(var2.namekeep) && this.stringkeep.postMortem(var2.stringkeep) && this.substringhuff.postMortem(var2.substringhuff) && this.substringkeep.postMortem(var2.substringkeep) && this.values.postMortem(var2.values);
   }
}
