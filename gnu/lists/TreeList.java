package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.AttributePredicate;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.ElementPredicate;
import gnu.lists.ItemPredicate;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.Sequence;
import gnu.lists.TreePosition;
import gnu.lists.XConsumer;
import gnu.text.Char;
import java.io.PrintWriter;

public class TreeList extends AbstractSequence implements Appendable, XConsumer, PositionConsumer, Consumable {

   protected static final int BEGIN_ATTRIBUTE_LONG = 61705;
   public static final int BEGIN_ATTRIBUTE_LONG_SIZE = 5;
   protected static final int BEGIN_DOCUMENT = 61712;
   protected static final int BEGIN_ELEMENT_LONG = 61704;
   protected static final int BEGIN_ELEMENT_SHORT = 40960;
   protected static final int BEGIN_ELEMENT_SHORT_INDEX_MAX = 4095;
   public static final int BEGIN_ENTITY = 61714;
   public static final int BEGIN_ENTITY_SIZE = 5;
   static final char BOOL_FALSE = '\uf100';
   static final char BOOL_TRUE = '\uf101';
   static final int BYTE_PREFIX = 61440;
   static final int CDATA_SECTION = 61717;
   static final int CHAR_FOLLOWS = 61702;
   static final int COMMENT = 61719;
   protected static final int DOCUMENT_URI = 61720;
   static final int DOUBLE_FOLLOWS = 61701;
   static final int END_ATTRIBUTE = 61706;
   public static final int END_ATTRIBUTE_SIZE = 1;
   protected static final int END_DOCUMENT = 61713;
   protected static final int END_ELEMENT_LONG = 61708;
   protected static final int END_ELEMENT_SHORT = 61707;
   protected static final int END_ENTITY = 61715;
   static final int FLOAT_FOLLOWS = 61700;
   public static final int INT_FOLLOWS = 61698;
   static final int INT_SHORT_ZERO = 49152;
   static final int JOINER = 61718;
   static final int LONG_FOLLOWS = 61699;
   public static final int MAX_CHAR_SHORT = 40959;
   static final int MAX_INT_SHORT = 8191;
   static final int MIN_INT_SHORT = -4096;
   static final char OBJECT_REF_FOLLOWS = '\uf10d';
   static final int OBJECT_REF_SHORT = 57344;
   static final int OBJECT_REF_SHORT_INDEX_MAX = 4095;
   protected static final char POSITION_PAIR_FOLLOWS = '\uf10f';
   static final char POSITION_REF_FOLLOWS = '\uf10e';
   protected static final int PROCESSING_INSTRUCTION = 61716;
   public int attrStart;
   int currentParent;
   public char[] data;
   public int docStart;
   public int gapEnd;
   public int gapStart;
   public Object[] objects;
   public int oindex;


   public TreeList() {
      this.currentParent = -1;
      this.resizeObjects();
      this.gapEnd = 200;
      this.data = new char[this.gapEnd];
   }

   public TreeList(TreeList var1) {
      this(var1, 0, var1.data.length);
   }

   public TreeList(TreeList var1, int var2, int var3) {
      this();
      var1.consumeIRange(var2, var3, this);
   }

   private Object copyToList(int var1, int var2) {
      return new TreeList(this, var1, var2);
   }

   public Consumer append(char var1) {
      this.write(var1);
      return this;
   }

   public Consumer append(CharSequence var1) {
      Object var2 = var1;
      if(var1 == null) {
         var2 = "null";
      }

      return this.append((CharSequence)var2, 0, ((CharSequence)var2).length());
   }

   public Consumer append(CharSequence var1, int var2, int var3) {
      Object var4 = var1;
      if(var1 == null) {
         var4 = "null";
      }

      while(var2 < var3) {
         this.append(((CharSequence)var4).charAt(var2));
         ++var2;
      }

      return this;
   }

   public void beginEntity(Object var1) {
      int var2 = -1;
      if(this.gapStart == 0) {
         this.ensureSpace(6);
         --this.gapEnd;
         int var3 = this.gapStart;
         this.data[var3] = '\uf112';
         this.setIntN(var3 + 1, this.find(var1));
         if(this.currentParent != -1) {
            var2 = this.currentParent - var3;
         }

         this.setIntN(var3 + 3, var2);
         this.gapStart = var3 + 5;
         this.currentParent = var3;
         this.data[this.gapEnd] = '\uf113';
      }
   }

   public void clear() {
      this.gapStart = 0;
      this.gapEnd = this.data.length;
      this.attrStart = 0;
      if(this.gapEnd > 1500) {
         this.gapEnd = 200;
         this.data = new char[this.gapEnd];
      }

      this.objects = null;
      this.oindex = 0;
      this.resizeObjects();
   }

   public int compare(int var1, int var2) {
      var1 = this.posToDataIndex(var1);
      var2 = this.posToDataIndex(var2);
      return var1 < var2?-1:(var1 > var2?1:0);
   }

   public void consume(Consumer var1) {
      this.consumeIRange(0, this.data.length, var1);
   }

   public void consume(SeqPosition var1) {
      this.ensureSpace(3);
      int var2 = this.find(var1.copy());
      char[] var4 = this.data;
      int var3 = this.gapStart;
      this.gapStart = var3 + 1;
      var4[var3] = '\uf10e';
      this.setIntN(this.gapStart, var2);
      this.gapStart += 2;
   }

   public int consumeIRange(int var1, int var2, Consumer var3) {
      int var5;
      if(var1 <= this.gapStart && var2 > this.gapStart) {
         var5 = this.gapStart;
         var1 = var1;
      } else {
         var5 = var2;
         var1 = var1;
      }

      while(true) {
         int var6 = var5;
         int var7 = var1;
         if(var1 >= var5) {
            if(var1 != this.gapStart || var2 <= this.gapEnd) {
               return var1;
            }

            var7 = this.gapEnd;
            var6 = var2;
         }

         char[] var4 = this.data;
         var1 = var7 + 1;
         char var11 = var4[var7];
         if(var11 > '\u9fff') {
            if(var11 >= '\ue000' && var11 <= '\uefff') {
               var3.writeObject(this.objects[var11 - '\ue000']);
               var5 = var6;
            } else if(var11 >= 'ꀀ' && var11 <= '꿿') {
               var3.startElement(this.objects[var11 - 'ꀀ']);
               var1 += 2;
               var5 = var6;
            } else if(var11 >= '뀀' && var11 <= '\udfff') {
               var3.writeInt(var11 - '쀀');
               var5 = var6;
            } else {
               switch(var11) {
               case '\uf100':
               case '\uf101':
                  boolean var8;
                  if(var11 != '\uf100') {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  var3.writeBoolean(var8);
                  var5 = var6;
                  break;
               case '\uf102':
                  var3.writeInt(this.getIntN(var1));
                  var1 += 2;
                  var5 = var6;
                  break;
               case '\uf103':
                  var3.writeLong(this.getLongN(var1));
                  var1 += 4;
                  var5 = var6;
                  break;
               case '\uf104':
                  var3.writeFloat(Float.intBitsToFloat(this.getIntN(var1)));
                  var1 += 2;
                  var5 = var6;
                  break;
               case '\uf105':
                  var3.writeDouble(Double.longBitsToDouble(this.getLongN(var1)));
                  var1 += 4;
                  var5 = var6;
                  break;
               case '\uf106':
                  var3.write((char[])this.data, var1, var11 + 1 - '\uf106');
                  ++var1;
                  var5 = var6;
                  break;
               case '\uf107':
               default:
                  throw new Error("unknown code:" + var11);
               case '\uf108':
                  var7 = this.getIntN(var1);
                  if(var7 >= 0) {
                     var5 = var1 - 1;
                  } else {
                     var5 = this.data.length;
                  }

                  var1 += 2;
                  var5 = this.getIntN(var7 + var5 + 1);
                  var3.startElement(this.objects[var5]);
                  var5 = var6;
                  break;
               case '\uf109':
                  var5 = this.getIntN(var1);
                  var3.startAttribute(this.objects[var5]);
                  var1 += 4;
                  var5 = var6;
                  break;
               case '\uf10a':
                  var3.endAttribute();
                  var5 = var6;
                  break;
               case '\uf10b':
                  ++var1;
                  var3.endElement();
                  var5 = var6;
                  break;
               case '\uf10c':
                  this.getIntN(var1);
                  var3.endElement();
                  var1 += 6;
                  var5 = var6;
                  break;
               case '\uf10e':
                  if(var3 instanceof PositionConsumer) {
                     ((PositionConsumer)var3).consume((SeqPosition)this.objects[this.getIntN(var1)]);
                     var1 += 2;
                     var5 = var6;
                     break;
                  }
               case '\uf10d':
                  var3.writeObject(this.objects[this.getIntN(var1)]);
                  var1 += 2;
                  var5 = var6;
                  break;
               case '\uf10f':
                  AbstractSequence var10 = (AbstractSequence)this.objects[this.getIntN(var1)];
                  var5 = this.getIntN(var1 + 2);
                  if(var3 instanceof PositionConsumer) {
                     ((PositionConsumer)var3).writePosition(var10, var5);
                  } else {
                     var3.writeObject(var10.getIteratorAtPos(var5));
                  }

                  var1 += 4;
                  var5 = var6;
                  break;
               case '\uf110':
                  var3.startDocument();
                  var1 += 4;
                  var5 = var6;
                  break;
               case '\uf111':
                  var3.endDocument();
                  var5 = var6;
                  break;
               case '\uf112':
                  if(var3 instanceof TreeList) {
                     ((TreeList)var3).beginEntity(this.objects[this.getIntN(var1)]);
                  }

                  var1 += 4;
                  var5 = var6;
                  break;
               case '\uf113':
                  if(var3 instanceof TreeList) {
                     ((TreeList)var3).endEntity();
                     var5 = var6;
                  } else {
                     var5 = var6;
                  }
                  break;
               case '\uf114':
                  String var9 = (String)this.objects[this.getIntN(var1)];
                  var5 = this.getIntN(var1 + 2);
                  var1 += 4;
                  if(var3 instanceof XConsumer) {
                     ((XConsumer)var3).writeProcessingInstruction(var9, this.data, var1, var5);
                  }

                  var1 += var5;
                  var5 = var6;
                  break;
               case '\uf115':
                  var5 = this.getIntN(var1);
                  var1 += 2;
                  if(var3 instanceof XConsumer) {
                     ((XConsumer)var3).writeCDATA(this.data, var1, var5);
                  } else {
                     var3.write((char[])this.data, var1, var5);
                  }

                  var1 += var5;
                  var5 = var6;
                  break;
               case '\uf116':
                  var3.write("");
                  var5 = var6;
                  break;
               case '\uf117':
                  var5 = this.getIntN(var1);
                  var1 += 2;
                  if(var3 instanceof XConsumer) {
                     ((XConsumer)var3).writeComment(this.data, var1, var5);
                  }

                  var1 += var5;
                  var5 = var6;
                  break;
               case '\uf118':
                  if(var3 instanceof TreeList) {
                     ((TreeList)var3).writeDocumentUri(this.objects[this.getIntN(var1)]);
                  }

                  var1 += 2;
                  var5 = var6;
               }
            }
         } else {
            for(var7 = var1 - 1; var1 < var6; var1 = var5) {
               var4 = this.data;
               var5 = var1 + 1;
               if(var4[var1] > '\u9fff') {
                  var1 = var5 - 1;
                  break;
               }
            }

            var3.write((char[])this.data, var7, var1 - var7);
            var5 = var6;
         }
      }
   }

   public boolean consumeNext(int var1, Consumer var2) {
      if(!this.hasNext(var1)) {
         return false;
      } else {
         int var4 = this.posToDataIndex(var1);
         int var3 = this.nextNodeIndex(var4, Integer.MAX_VALUE);
         var1 = var3;
         if(var3 == var4) {
            var1 = this.nextDataIndex(var4);
         }

         if(var1 >= 0) {
            this.consumeIRange(var4, var1, var2);
         }

         return true;
      }
   }

   public void consumePosRange(int var1, int var2, Consumer var3) {
      this.consumeIRange(this.posToDataIndex(var1), this.posToDataIndex(var2), var3);
   }

   public int createPos(int var1, boolean var2) {
      return this.createRelativePos(0, var1, var2);
   }

   public int createRelativePos(int var1, int var2, boolean var3) {
      int var4 = var2;
      if(var3) {
         if(var2 == 0) {
            if((var1 & 1) != 0) {
               return var1;
            }

            if(var1 == 0) {
               return 1;
            }
         }

         var4 = var2 - 1;
      }

      if(var4 < 0) {
         throw this.unsupported("backwards createRelativePos");
      } else {
         var1 = this.posToDataIndex(var1);

         do {
            --var4;
            if(var4 < 0) {
               var2 = var1;
               if(var1 >= this.gapEnd) {
                  var2 = var1 - (this.gapEnd - this.gapStart);
               }

               if(var3) {
                  var1 = var2 + 1 << 1 | 1;
               } else {
                  var1 = var2 << 1;
               }

               return var1;
            }

            var2 = this.nextDataIndex(var1);
            var1 = var2;
         } while(var2 >= 0);

         throw new IndexOutOfBoundsException();
      }
   }

   public Object documentUriOfPos(int var1) {
      var1 = this.posToDataIndex(var1);
      if(var1 != this.data.length && this.data[var1] == '\uf110') {
         int var2 = var1 + 5;
         var1 = var2;
         if(var2 == this.gapStart) {
            var1 = this.gapEnd;
         }

         if(var1 < this.data.length && this.data[var1] == '\uf118') {
            return this.objects[this.getIntN(var1 + 1)];
         }
      }

      return null;
   }

   public void dump() {
      PrintWriter var1 = new PrintWriter(System.out);
      this.dump(var1);
      var1.flush();
   }

   public void dump(PrintWriter var1) {
      var1.println(this.getClass().getName() + " @" + Integer.toHexString(System.identityHashCode(this)) + " gapStart:" + this.gapStart + " gapEnd:" + this.gapEnd + " length:" + this.data.length);
      this.dump(var1, 0, this.data.length);
   }

   public void dump(PrintWriter var1, int var2, int var3) {
      byte var7 = 0;
      int var6 = var2;

      int var12;
      for(var2 = var7; var6 < var3; var2 = var12) {
         int var8;
         label131: {
            if(var6 >= this.gapStart) {
               var8 = var6;
               var12 = var2;
               if(var6 < this.gapEnd) {
                  break label131;
               }
            }

            char var13 = this.data[var6];
            var1.print("" + var6 + ": 0x" + Integer.toHexString(var13) + '=' + (short)var13);
            var12 = var2 - 1;
            var2 = var12;
            if(var12 < 0) {
               if(var13 <= '\u9fff') {
                  if(var13 >= 32 && var13 < 127) {
                     var1.print("=\'" + (char)var13 + "\'");
                     var2 = var12;
                  } else if(var13 == 10) {
                     var1.print("=\'\\n\'");
                     var2 = var12;
                  } else {
                     var1.print("=\'\\u" + Integer.toHexString(var13) + "\'");
                     var2 = var12;
                  }
               } else if(var13 >= '\ue000' && var13 <= '\uefff') {
                  var2 = var13 - '\ue000';
                  Object var11 = this.objects[var2];
                  var1.print("=Object#" + var2 + '=' + var11 + ':' + var11.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(var11)));
                  var2 = var12;
               } else if(var13 >= 'ꀀ' && var13 <= '꿿') {
                  var2 = var13 - 'ꀀ';
                  char var14 = this.data[var6 + 1];
                  var1.print("=BEGIN_ELEMENT_SHORT end:" + (var14 + var6) + " index#" + var2 + "=<" + this.objects[var2] + '>');
                  var2 = 2;
               } else if(var13 >= '뀀' && var13 <= '\udfff') {
                  var1.print("= INT_SHORT:" + (var13 - '쀀'));
                  var2 = var12;
               } else {
                  long var9;
                  switch(var13) {
                  case '\uf100':
                     var1.print("= false");
                     var2 = var12;
                     break;
                  case '\uf101':
                     var1.print("= true");
                     var2 = var12;
                     break;
                  case '\uf102':
                     var2 = this.getIntN(var6 + 1);
                     var1.print("=INT_FOLLOWS value:" + var2);
                     var2 = 2;
                     break;
                  case '\uf103':
                     var9 = this.getLongN(var6 + 1);
                     var1.print("=LONG_FOLLOWS value:" + var9);
                     var2 = 4;
                     break;
                  case '\uf104':
                     var2 = this.getIntN(var6 + 1);
                     var1.write("=FLOAT_FOLLOWS value:" + Float.intBitsToFloat(var2));
                     var2 = 2;
                     break;
                  case '\uf105':
                     var9 = this.getLongN(var6 + 1);
                     var1.print("=DOUBLE_FOLLOWS value:" + Double.longBitsToDouble(var9));
                     var2 = 4;
                     break;
                  case '\uf106':
                     var1.print("=CHAR_FOLLOWS");
                     var2 = 1;
                     break;
                  case '\uf107':
                  default:
                     var2 = var12;
                     break;
                  case '\uf108':
                     var12 = this.getIntN(var6 + 1);
                     if(var12 < 0) {
                        var2 = this.data.length;
                     } else {
                        var2 = var6;
                     }

                     var2 += var12;
                     var1.print("=BEGIN_ELEMENT_LONG end:");
                     var1.print(var2);
                     var2 = this.getIntN(var2 + 1);
                     var1.print(" -> #");
                     var1.print(var2);
                     if(var2 >= 0 && var2 + 1 < this.objects.length) {
                        var1.print("=<" + this.objects[var2] + '>');
                     } else {
                        var1.print("=<out-of-bounds>");
                     }

                     var2 = 2;
                     break;
                  case '\uf109':
                     var2 = this.getIntN(var6 + 1);
                     var1.print("=BEGIN_ATTRIBUTE name:" + var2 + "=" + this.objects[var2]);
                     var12 = this.getIntN(var6 + 3);
                     if(var12 < 0) {
                        var2 = this.data.length;
                     } else {
                        var2 = var6;
                     }

                     var1.print(" end:" + (var12 + var2));
                     var2 = 4;
                     break;
                  case '\uf10a':
                     var1.print("=END_ATTRIBUTE");
                     var2 = var12;
                     break;
                  case '\uf10b':
                     var1.print("=END_ELEMENT_SHORT begin:");
                     var2 = var6 - this.data[var6 + 1];
                     var1.print(var2);
                     var2 = this.data[var2] - 'ꀀ';
                     var1.print(" -> #");
                     var1.print(var2);
                     var1.print("=<");
                     var1.print(this.objects[var2]);
                     var1.print('>');
                     var2 = 1;
                     break;
                  case '\uf10c':
                     var2 = this.getIntN(var6 + 1);
                     var1.print("=END_ELEMENT_LONG name:" + var2 + "=<" + this.objects[var2] + '>');
                     var12 = this.getIntN(var6 + 3);
                     var2 = var12;
                     if(var12 < 0) {
                        var2 = var12 + var6;
                     }

                     var1.print(" begin:" + var2);
                     var12 = this.getIntN(var6 + 5);
                     var2 = var12;
                     if(var12 < 0) {
                        var2 = var12 + var6;
                     }

                     var1.print(" parent:" + var2);
                     var2 = 6;
                     break;
                  case '\uf10d':
                  case '\uf10e':
                     var2 = 2;
                     break;
                  case '\uf10f':
                     var1.print("=POSITION_PAIR_FOLLOWS seq:");
                     var2 = this.getIntN(var6 + 1);
                     var1.print(var2);
                     var1.print('=');
                     Object var5 = this.objects[var2];
                     String var4;
                     if(var5 == null) {
                        var4 = null;
                     } else {
                        var4 = var5.getClass().getName();
                     }

                     var1.print(var4);
                     var1.print('@');
                     if(var5 == null) {
                        var1.print("null");
                     } else {
                        var1.print(Integer.toHexString(System.identityHashCode(var5)));
                     }

                     var1.print(" ipos:");
                     var1.print(this.getIntN(var6 + 3));
                     var2 = 4;
                     break;
                  case '\uf110':
                     var12 = this.getIntN(var6 + 1);
                     if(var12 < 0) {
                        var2 = this.data.length;
                     } else {
                        var2 = var6;
                     }

                     var1.print("=BEGIN_DOCUMENT end:");
                     var1.print(var12 + var2);
                     var1.print(" parent:");
                     var1.print(this.getIntN(var6 + 3));
                     var2 = 4;
                     break;
                  case '\uf111':
                     var1.print("=END_DOCUMENT");
                     var2 = var12;
                     break;
                  case '\uf112':
                     var2 = this.getIntN(var6 + 1);
                     var1.print("=BEGIN_ENTITY base:");
                     var1.print(var2);
                     var1.print(" parent:");
                     var1.print(this.getIntN(var6 + 3));
                     var2 = 4;
                     break;
                  case '\uf113':
                     var1.print("=END_ENTITY");
                     var2 = var12;
                     break;
                  case '\uf114':
                     var1.print("=PROCESSING_INSTRUCTION: ");
                     var2 = this.getIntN(var6 + 1);
                     var1.print(this.objects[var2]);
                     var1.print(" \'");
                     var2 = this.getIntN(var6 + 3);
                     var1.write(this.data, var6 + 5, var2);
                     var1.print('\'');
                     var2 += 4;
                     break;
                  case '\uf115':
                     var1.print("=CDATA: \'");
                     var2 = this.getIntN(var6 + 1);
                     var1.write(this.data, var6 + 3, var2);
                     var1.print('\'');
                     var2 += 2;
                     break;
                  case '\uf116':
                     var1.print("= joiner");
                     var2 = var12;
                     break;
                  case '\uf117':
                     var1.print("=COMMENT: \'");
                     var2 = this.getIntN(var6 + 1);
                     var1.write(this.data, var6 + 3, var2);
                     var1.print('\'');
                     var2 += 2;
                     break;
                  case '\uf118':
                     var1.print("=DOCUMENT_URI: ");
                     var2 = this.getIntN(var6 + 1);
                     var1.print(this.objects[var2]);
                     var2 = 2;
                  }
               }
            }

            var1.println();
            var8 = var6;
            var12 = var2;
            if(true) {
               var8 = var6;
               var12 = var2;
               if(var2 > 0) {
                  var8 = var6 + var2;
                  var12 = 0;
               }
            }
         }

         var6 = var8 + 1;
      }

   }

   public void endAttribute() {
      if(this.attrStart > 0) {
         if(this.data[this.gapEnd] != '\uf10a') {
            throw new Error("unexpected endAttribute");
         } else {
            ++this.gapEnd;
            this.setIntN(this.attrStart + 2, this.gapStart - this.attrStart + 1);
            this.attrStart = 0;
            char[] var1 = this.data;
            int var2 = this.gapStart;
            this.gapStart = var2 + 1;
            var1[var2] = '\uf10a';
         }
      }
   }

   public void endDocument() {
      if(this.data[this.gapEnd] == '\uf111' && this.docStart > 0 && this.data[this.currentParent] == '\uf110') {
         ++this.gapEnd;
         this.setIntN(this.docStart, this.gapStart - this.docStart + 1);
         this.docStart = 0;
         char[] var1 = this.data;
         int var2 = this.gapStart;
         this.gapStart = var2 + 1;
         var1[var2] = '\uf111';
         var2 = this.getIntN(this.currentParent + 3);
         if(var2 < -1) {
            var2 += this.currentParent;
         }

         this.currentParent = var2;
      } else {
         throw new Error("unexpected endDocument");
      }
   }

   public void endElement() {
      if(this.data[this.gapEnd] != '\uf10c') {
         throw new Error("unexpected endElement");
      } else {
         int var1 = this.getIntN(this.gapEnd + 1);
         int var3 = this.getIntN(this.gapEnd + 3);
         int var2 = this.getIntN(this.gapEnd + 5);
         this.currentParent = var2;
         this.gapEnd += 7;
         int var4 = this.gapStart - var3;
         int var5 = var3 - var2;
         if(var1 < 4095 && var4 < 65536 && var5 < 65536) {
            this.data[var3] = (char)('ꀀ' | var1);
            this.data[var3 + 1] = (char)var4;
            this.data[var3 + 2] = (char)var5;
            this.data[this.gapStart] = '\uf10b';
            this.data[this.gapStart + 1] = (char)var4;
            this.gapStart += 2;
         } else {
            label20: {
               this.data[var3] = '\uf108';
               this.setIntN(var3 + 1, var4);
               this.data[this.gapStart] = '\uf10c';
               this.setIntN(this.gapStart + 1, var1);
               this.setIntN(this.gapStart + 3, -var4);
               if(var2 < this.gapStart) {
                  var1 = var2;
                  if(var3 > this.gapStart) {
                     break label20;
                  }
               }

               var1 = var2 - this.gapStart;
            }

            this.setIntN(this.gapStart + 5, var1);
            this.gapStart += 7;
         }
      }
   }

   public void endEntity() {
      if(this.gapEnd + 1 == this.data.length && this.data[this.gapEnd] == '\uf113') {
         if(this.data[this.currentParent] != '\uf112') {
            throw new Error("unexpected endEntity");
         } else {
            ++this.gapEnd;
            char[] var1 = this.data;
            int var2 = this.gapStart;
            this.gapStart = var2 + 1;
            var1[var2] = '\uf113';
            var2 = this.getIntN(this.currentParent + 3);
            if(var2 < -1) {
               var2 += this.currentParent;
            }

            this.currentParent = var2;
         }
      }
   }

   public void ensureSpace(int var1) {
      int var3 = this.gapEnd - this.gapStart;
      if(var1 > var3) {
         int var5 = this.data.length;
         var3 = var5 - var3 + var1;
         int var4 = var5 * 2;
         var1 = var4;
         if(var4 < var3) {
            var1 = var3;
         }

         char[] var2 = new char[var1];
         if(this.gapStart > 0) {
            System.arraycopy(this.data, 0, var2, 0, this.gapStart);
         }

         var3 = var5 - this.gapEnd;
         if(var3 > 0) {
            System.arraycopy(this.data, this.gapEnd, var2, var1 - var3, var3);
         }

         this.gapEnd = var1 - var3;
         this.data = var2;
      }

   }

   public int find(Object var1) {
      if(this.oindex == this.objects.length) {
         this.resizeObjects();
      }

      this.objects[this.oindex] = var1;
      int var2 = this.oindex;
      this.oindex = var2 + 1;
      return var2;
   }

   public int firstAttributePos(int var1) {
      var1 = this.gotoAttributesStart(this.posToDataIndex(var1));
      return var1 < 0?0:var1 << 1;
   }

   public int firstChildPos(int var1) {
      var1 = this.gotoChildrenStart(this.posToDataIndex(var1));
      return var1 < 0?0:var1 << 1;
   }

   public Object get(int var1) {
      byte var3 = 0;
      int var2 = var1;
      var1 = var3;

      int var4;
      do {
         --var2;
         if(var2 < 0) {
            return this.getPosNext(var1);
         }

         var4 = this.nextPos(var1);
         var1 = var4;
      } while(var4 != 0);

      throw new IndexOutOfBoundsException();
   }

   public int getAttributeCount(int var1) {
      int var2 = 0;

      for(var1 = this.firstAttributePos(var1); var1 != 0 && this.getNextKind(var1) == 35; var1 = this.nextPos(var1)) {
         ++var2;
      }

      return var2;
   }

   protected int getIndexDifference(int var1, int var2) {
      int var4 = this.posToDataIndex(var2);
      int var5 = this.posToDataIndex(var1);
      boolean var6 = false;
      var1 = var4;
      int var3 = var5;
      if(var4 > var5) {
         var6 = true;
         var3 = var4;
         var1 = var5;
      }

      byte var7 = 0;
      var4 = var1;

      for(var1 = var7; var4 < var3; ++var1) {
         var4 = this.nextDataIndex(var4);
      }

      var3 = var1;
      if(var6) {
         var3 = -var1;
      }

      return var3;
   }

   protected final int getIntN(int var1) {
      return this.data[var1] << 16 | this.data[var1 + 1] & '\uffff';
   }

   protected final long getLongN(int var1) {
      char[] var2 = this.data;
      return ((long)var2[var1] & 65535L) << 48 | ((long)var2[var1 + 1] & 65535L) << 32 | ((long)var2[var1 + 2] & 65535L) << 16 | (long)var2[var1 + 3] & 65535L;
   }

   public int getNextKind(int var1) {
      return this.getNextKindI(this.posToDataIndex(var1));
   }

   public int getNextKindI(int var1) {
      if(var1 != this.data.length) {
         char var2 = this.data[var1];
         if(var2 <= '\u9fff') {
            return 29;
         }

         if(var2 >= '\ue000' && var2 <= '\uefff') {
            return 32;
         }

         if(var2 >= 'ꀀ' && var2 <= '꿿') {
            return 33;
         }

         if(('\uff00' & var2) == '\uf000') {
            return 28;
         }

         if(var2 >= '뀀' && var2 <= '\udfff') {
            return 22;
         }

         switch(var2) {
         case '\uf100':
         case '\uf101':
            return 27;
         case '\uf102':
            return 22;
         case '\uf103':
            return 24;
         case '\uf104':
            return 25;
         case '\uf105':
            return 26;
         case '\uf106':
         case '\uf110':
            return 34;
         case '\uf107':
         case '\uf10d':
         case '\uf10e':
         case '\uf10f':
         case '\uf116':
         default:
            return 32;
         case '\uf108':
            return 33;
         case '\uf109':
            return 35;
         case '\uf10a':
         case '\uf10b':
         case '\uf10c':
         case '\uf111':
         case '\uf113':
            break;
         case '\uf112':
            return this.getNextKind(var1 + 5 << 1);
         case '\uf114':
            return 37;
         case '\uf115':
            return 31;
         case '\uf117':
            return 36;
         }
      }

      return 0;
   }

   public String getNextTypeName(int var1) {
      Object var2 = this.getNextTypeObject(var1);
      return var2 == null?null:var2.toString();
   }

   public Object getNextTypeObject(int var1) {
      for(var1 = this.posToDataIndex(var1); var1 != this.data.length; var1 += 5) {
         char var2 = this.data[var1];
         if(var2 != '\uf112') {
            if(var2 >= 'ꀀ' && var2 <= '꿿') {
               var1 = var2 - 'ꀀ';
            } else if(var2 == '\uf108') {
               int var3 = this.getIntN(var1 + 1);
               if(var3 < 0) {
                  var1 = this.data.length;
               }

               var1 = this.getIntN(var3 + var1 + 1);
            } else if(var2 == '\uf109') {
               var1 = this.getIntN(var1 + 1);
            } else {
               if(var2 != '\uf114') {
                  break;
               }

               var1 = this.getIntN(var1 + 1);
            }

            if(var1 >= 0) {
               return this.objects[var1];
            }
            break;
         }
      }

      return null;
   }

   public Object getPosNext(int var1) {
      var1 = this.posToDataIndex(var1);
      if(var1 == this.data.length) {
         return Sequence.eofValue;
      } else {
         char var2 = this.data[var1];
         if(var2 <= '\u9fff') {
            return Convert.toObject((char)var2);
         } else if(var2 >= '\ue000' && var2 <= '\uefff') {
            return this.objects[var2 - '\ue000'];
         } else if(var2 >= 'ꀀ' && var2 <= '꿿') {
            return this.copyToList(var1, this.data[var1 + 1] + var1 + 2);
         } else if(var2 >= '뀀' && var2 <= '\udfff') {
            return Convert.toObject((int)(var2 - '쀀'));
         } else {
            int var3;
            int var4;
            switch(var2) {
            case '\uf100':
            case '\uf101':
               boolean var5;
               if(var2 != '\uf100') {
                  var5 = true;
               } else {
                  var5 = false;
               }

               return Convert.toObject(var5);
            case '\uf102':
               return Convert.toObject((int)this.getIntN(var1 + 1));
            case '\uf103':
               return Convert.toObject(this.getLongN(var1 + 1));
            case '\uf104':
               return Convert.toObject(Float.intBitsToFloat(this.getIntN(var1 + 1)));
            case '\uf105':
               return Convert.toObject(Double.longBitsToDouble(this.getLongN(var1 + 1)));
            case '\uf106':
               return Convert.toObject((char)this.data[var1 + 1]);
            case '\uf107':
            case '\uf112':
            case '\uf113':
            case '\uf114':
            case '\uf115':
            default:
               throw this.unsupported("getPosNext, code=" + Integer.toHexString(var2));
            case '\uf108':
               var4 = this.getIntN(var1 + 1);
               if(var4 < 0) {
                  var3 = this.data.length;
               } else {
                  var3 = var1;
               }

               return this.copyToList(var1, var4 + var3 + 7);
            case '\uf109':
               var4 = this.getIntN(var1 + 3);
               if(var4 < 0) {
                  var3 = this.data.length;
               } else {
                  var3 = var1;
               }

               return this.copyToList(var1, var4 + var3 + 1);
            case '\uf10a':
            case '\uf10b':
            case '\uf10c':
            case '\uf111':
               return Sequence.eofValue;
            case '\uf10d':
            case '\uf10e':
               return this.objects[this.getIntN(var1 + 1)];
            case '\uf10f':
               return ((AbstractSequence)this.objects[this.getIntN(var1 + 1)]).getIteratorAtPos(this.getIntN(var1 + 3));
            case '\uf110':
               var4 = this.getIntN(var1 + 1);
               if(var4 < 0) {
                  var3 = this.data.length;
               } else {
                  var3 = var1;
               }

               return this.copyToList(var1, var4 + var3 + 1);
            case '\uf116':
               return "";
            }
         }
      }
   }

   public int getPosNextInt(int var1) {
      int var2 = this.posToDataIndex(var1);
      if(var2 < this.data.length) {
         char var3 = this.data[var2];
         if(var3 >= '뀀' && var3 <= '\udfff') {
            return var3 - '쀀';
         }

         if(var3 == '\uf102') {
            return this.getIntN(var2 + 1);
         }
      }

      return ((Number)this.getPosNext(var1)).intValue();
   }

   public Object getPosPrevious(int var1) {
      return (var1 & 1) != 0 && var1 != -1?this.getPosNext(var1 - 3):super.getPosPrevious(var1);
   }

   public int gotoAttributesStart(int var1) {
      int var2 = var1;
      if(var1 >= this.gapStart) {
         var2 = var1 + (this.gapEnd - this.gapStart);
      }

      if(var2 != this.data.length) {
         char var3 = this.data[var2];
         if(var3 >= 'ꀀ' && var3 <= '꿿' || var3 == '\uf108') {
            return var2 + 3;
         }
      }

      return -1;
   }

   public boolean gotoAttributesStart(TreePosition var1) {
      int var2 = this.gotoAttributesStart(var1.ipos >> 1);
      if(var2 < 0) {
         return false;
      } else {
         var1.push(this, var2 << 1);
         return true;
      }
   }

   public final int gotoChildrenStart(int var1) {
      if(var1 != this.data.length) {
         char var2 = this.data[var1];
         if((var2 < 'ꀀ' || var2 > '꿿') && var2 != '\uf108') {
            if(var2 != '\uf110' && var2 != '\uf112') {
               return -1;
            }

            var1 += 5;
         } else {
            var1 += 3;
         }

         while(true) {
            int var3 = var1;
            if(var1 >= this.gapStart) {
               var3 = var1 + (this.gapEnd - this.gapStart);
            }

            char var4 = this.data[var3];
            if(var4 == '\uf109') {
               var1 = this.getIntN(var3 + 3);
               if(var1 < 0) {
                  var3 = this.data.length;
               }

               var1 += var3;
            } else if(var4 != '\uf10a' && var4 != '\uf116') {
               if(var4 != '\uf118') {
                  return var3;
               }

               var1 = var3 + 3;
            } else {
               var1 = var3 + 1;
            }
         }
      } else {
         return -1;
      }
   }

   public boolean hasNext(int var1) {
      var1 = this.posToDataIndex(var1);
      if(var1 != this.data.length) {
         char var2 = this.data[var1];
         if(var2 != '\uf10a' && var2 != '\uf10b' && var2 != '\uf10c' && var2 != '\uf111') {
            return true;
         }
      }

      return false;
   }

   public int hashCode() {
      return System.identityHashCode(this);
   }

   public boolean ignoring() {
      return false;
   }

   public boolean isEmpty() {
      boolean var2 = false;
      int var1;
      if(this.gapStart == 0) {
         var1 = this.gapEnd;
      } else {
         var1 = 0;
      }

      if(var1 == this.data.length) {
         var2 = true;
      }

      return var2;
   }

   public final int nextDataIndex(int var1) {
      int var3 = var1;
      if(var1 == this.gapStart) {
         var3 = this.gapEnd;
      }

      if(var3 == this.data.length) {
         return -1;
      } else {
         char[] var2 = this.data;
         var1 = var3 + 1;
         char var4 = var2[var3];
         if(var4 > '\u9fff' && (var4 < '\ue000' || var4 > '\uefff') && (var4 < '뀀' || var4 > '\udfff')) {
            if(var4 >= 'ꀀ' && var4 <= '꿿') {
               return this.data[var1] + var1 + 1;
            } else {
               switch(var4) {
               case '\uf100':
               case '\uf101':
               case '\uf116':
                  return var1;
               case '\uf102':
               case '\uf104':
               case '\uf10d':
               case '\uf10e':
                  return var1 + 2;
               case '\uf103':
               case '\uf105':
                  return var1 + 4;
               case '\uf106':
                  return var1 + 1;
               case '\uf107':
               default:
                  throw new Error("unknown code:" + Integer.toHexString(var4));
               case '\uf108':
                  var3 = this.getIntN(var1);
                  if(var3 < 0) {
                     var1 = this.data.length;
                  } else {
                     --var1;
                  }

                  return var3 + var1 + 7;
               case '\uf109':
                  var3 = this.getIntN(var1 + 2);
                  if(var3 < 0) {
                     var1 = this.data.length;
                  } else {
                     --var1;
                  }

                  return var3 + var1 + 1;
               case '\uf10a':
               case '\uf10b':
               case '\uf10c':
               case '\uf111':
               case '\uf113':
                  return -1;
               case '\uf10f':
                  return var1 + 4;
               case '\uf110':
                  var3 = this.getIntN(var1);
                  if(var3 < 0) {
                     var1 = this.data.length;
                  } else {
                     --var1;
                  }

                  return var3 + var1 + 1;
               case '\uf112':
                  var1 += 4;

                  while(true) {
                     var3 = var1;
                     if(var1 == this.gapStart) {
                        var3 = this.gapEnd;
                     }

                     if(var3 == this.data.length) {
                        return -1;
                     }

                     if(this.data[var3] == '\uf113') {
                        return var3 + 1;
                     }

                     var1 = this.nextDataIndex(var3);
                  }
               case '\uf114':
                  var1 += 2;
               case '\uf115':
               case '\uf117':
                  return var1 + 2 + this.getIntN(var1);
               }
            }
         } else {
            return var1;
         }
      }
   }

   public int nextMatching(int var1, ItemPredicate var2, int var3, boolean var4) {
      int var8 = this.posToDataIndex(var1);
      int var9 = this.posToDataIndex(var3);
      var1 = var8;
      if(var2 instanceof NodePredicate) {
         var1 = this.nextNodeIndex(var8, var9);
      }

      boolean var6;
      boolean var7;
      if(var2 instanceof ElementPredicate) {
         var6 = true;
         var7 = false;
      } else if(var2 instanceof AttributePredicate) {
         var6 = false;
         var7 = false;
      } else {
         var6 = true;
         var7 = true;
      }

      while(true) {
         while(true) {
            int var5 = var1;
            if(var1 == this.gapStart) {
               var5 = this.gapEnd;
            }

            if(var5 >= var9 && var9 != -1) {
               return 0;
            }

            char var10 = this.data[var5];
            if(var10 > '\u9fff' && (var10 < '\ue000' || var10 > '\uefff') && (var10 < '뀀' || var10 > '\udfff')) {
               switch(var10) {
               case '\uf100':
               case '\uf101':
                  var3 = var5 + 1;
                  var1 = var3;
                  if(!var7) {
                     continue;
                  }
                  break;
               case '\uf102':
               case '\uf104':
               case '\uf10d':
               case '\uf10e':
                  var3 = var5 + 3;
                  var1 = var3;
                  if(!var7) {
                     continue;
                  }
                  break;
               case '\uf103':
               case '\uf105':
                  var3 = var5 + 5;
                  var1 = var3;
                  if(!var7) {
                     continue;
                  }
                  break;
               case '\uf106':
                  var1 = var5 + 2;
                  continue;
               case '\uf107':
               default:
                  if(var10 < 'ꀀ' || var10 > '꿿') {
                     throw new Error("unknown code:" + var10);
                  }

                  if(var4) {
                     var3 = var5 + 3;
                  } else {
                     var3 = this.data[var5 + 1] + var5 + 2;
                  }

                  var1 = var3;
                  if(!var6) {
                     continue;
                  }
                  break;
               case '\uf108':
                  if(var4) {
                     var3 = var5 + 3;
                  } else {
                     var3 = this.getIntN(var5 + 1);
                     if(var3 < 0) {
                        var1 = this.data.length;
                     } else {
                        var1 = var5;
                     }

                     var3 = var1 + var3 + 7;
                  }

                  var1 = var3;
                  if(!var6) {
                     continue;
                  }
                  break;
               case '\uf109':
                  if(true) {
                     var3 = this.getIntN(var5 + 3);
                     if(var3 < 0) {
                        var1 = this.data.length;
                     } else {
                        var1 = var5;
                     }

                     var3 = var3 + 1 + var1;
                  } else {
                     var3 = var5 + 5;
                  }

                  var1 = var3;
                  if(true) {
                     continue;
                  }
                  break;
               case '\uf10a':
               case '\uf111':
                  if(!var4) {
                     return 0;
                  }
               case '\uf10b':
                  if(!var4) {
                     return 0;
                  }

                  var1 = var5 + 2;
                  continue;
               case '\uf10c':
                  if(!var4) {
                     return 0;
                  }

                  var1 = var5 + 7;
                  continue;
               case '\uf10f':
                  var3 = var5 + 5;
                  var1 = var3;
                  if(!var7) {
                     continue;
                  }
                  break;
               case '\uf110':
                  var3 = var5 + 5;
                  var1 = var3;
                  if(false) {
                     continue;
                  }
                  break;
               case '\uf112':
                  var1 = var5 + 5;
                  continue;
               case '\uf113':
                  var1 = var5 + 1;
                  continue;
               case '\uf114':
                  var3 = var5 + 5 + this.getIntN(var5 + 3);
                  var1 = var3;
                  if(false) {
                     continue;
                  }
                  break;
               case '\uf115':
                  var3 = var5 + 3 + this.getIntN(var5 + 1);
                  var1 = var3;
                  if(!var7) {
                     continue;
                  }
                  break;
               case '\uf116':
                  var1 = var5 + 1;
                  continue;
               case '\uf117':
                  var3 = var5 + 3 + this.getIntN(var5 + 1);
                  var1 = var3;
                  if(false) {
                     continue;
                  }
                  break;
               case '\uf118':
                  var1 = var5 + 3;
                  continue;
               }

               var1 = var3;
               if(var5 > var8) {
                  var1 = var3;
                  if(var2.isInstancePos(this, var5 << 1)) {
                     var1 = var5;
                     if(var5 >= this.gapEnd) {
                        var1 = var5 - (this.gapEnd - this.gapStart);
                     }

                     return var1 << 1;
                  }
               }
            } else {
               if(var7 && var2.isInstancePos(this, var5 << 1)) {
                  var1 = var5;
                  if(var5 >= this.gapEnd) {
                     var1 = var5 - (this.gapEnd - this.gapStart);
                  }

                  return var1 << 1;
               }

               var1 = var5 + 1;
            }
         }
      }
   }

   public final int nextNodeIndex(int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      if((Integer.MIN_VALUE | var2) == -1) {
         var4 = this.data.length;
         var3 = var1;
      }

      while(true) {
         var1 = var3;
         if(var3 == this.gapStart) {
            var1 = this.gapEnd;
         }

         if(var1 >= var4) {
            break;
         }

         char var5 = this.data[var1];
         if(var5 > '\u9fff' && (var5 < '\ue000' || var5 > '\uefff') && (var5 < '뀀' || var5 > '\udfff') && ('\uff00' & var5) != '\uf000') {
            if(var5 >= 'ꀀ' && var5 <= '꿿') {
               break;
            }

            switch(var5) {
            case '\uf108':
            case '\uf109':
            case '\uf10a':
            case '\uf10b':
            case '\uf10c':
            case '\uf110':
            case '\uf111':
            case '\uf113':
            case '\uf114':
            case '\uf117':
               return var1;
            case '\uf10d':
            case '\uf10e':
            case '\uf10f':
            case '\uf115':
            default:
               var3 = this.nextDataIndex(var1);
               break;
            case '\uf112':
               var3 = var1 + 5;
               break;
            case '\uf116':
               var3 = var1 + 1;
               break;
            case '\uf118':
               var3 = var1 + 3;
            }
         } else {
            var3 = var1 + 1;
         }
      }

      return var1;
   }

   public int nextPos(int var1) {
      int var2 = this.posToDataIndex(var1);
      if(var2 == this.data.length) {
         return 0;
      } else {
         var1 = var2;
         if(var2 >= this.gapEnd) {
            var1 = var2 - (this.gapEnd - this.gapStart);
         }

         return (var1 << 1) + 3;
      }
   }

   public int parentOrEntityI(int var1) {
      if(var1 != this.data.length) {
         char var3 = this.data[var1];
         int var2;
         if(var3 == '\uf110' || var3 == '\uf112') {
            var2 = this.getIntN(var1 + 3);
            if(var2 >= -1) {
               return var2;
            }

            return var1 + var2;
         }

         if(var3 >= 'ꀀ' && var3 <= '꿿') {
            char var5 = this.data[var1 + 2];
            if(var5 != 0) {
               return var1 - var5;
            }
         } else {
            var2 = var1;
            int var4;
            if(var3 == '\uf108') {
               var2 = this.getIntN(var1 + 1);
               if(var2 < 0) {
                  var1 = this.data.length;
               }

               var4 = var2 + var1;
               var2 = this.getIntN(var4 + 5);
               if(var2 != 0) {
                  var1 = var2;
                  if(var2 < 0) {
                     var1 = var2 + var4;
                  }

                  return var1;
               }
            } else {
               while(true) {
                  var1 = var2;
                  if(var2 == this.gapStart) {
                     var1 = this.gapEnd;
                  }

                  if(var1 == this.data.length) {
                     break;
                  }

                  switch(this.data[var1]) {
                  case '\uf10a':
                     var2 = var1 + 1;
                     break;
                  case '\uf10b':
                     return var1 - this.data[var1 + 1];
                  case '\uf10c':
                     var4 = this.getIntN(var1 + 3);
                     var2 = var4;
                     if(var4 < 0) {
                        var2 = var4 + var1;
                     }

                     return var2;
                  case '\uf10d':
                  case '\uf10e':
                  case '\uf10f':
                  case '\uf110':
                  default:
                     var1 = this.nextDataIndex(var1);
                     var2 = var1;
                     if(var1 < 0) {
                        return -1;
                     }
                     break;
                  case '\uf111':
                     return -1;
                  }
               }
            }
         }
      }

      return -1;
   }

   public int parentOrEntityPos(int var1) {
      var1 = this.parentOrEntityI(this.posToDataIndex(var1));
      return var1 < 0?-1:var1 << 1;
   }

   public int parentPos(int var1) {
      var1 = this.posToDataIndex(var1);

      int var2;
      do {
         var2 = this.parentOrEntityI(var1);
         if(var2 == -1) {
            return -1;
         }

         var1 = var2;
      } while(this.data[var2] == '\uf112');

      return var2 << 1;
   }

   public final int posToDataIndex(int var1) {
      int var2;
      if(var1 == -1) {
         var2 = this.data.length;
      } else {
         int var3 = var1 >>> 1;
         var2 = var3;
         if((var1 & 1) != 0) {
            var2 = var3 - 1;
         }

         var3 = var2;
         if(var2 >= this.gapStart) {
            var3 = var2 + (this.gapEnd - this.gapStart);
         }

         var2 = var3;
         if((var1 & 1) != 0) {
            var1 = this.nextDataIndex(var3);
            if(var1 < 0) {
               return this.data.length;
            }

            var2 = var1;
            if(var1 == this.gapStart) {
               return var1 + (this.gapEnd - this.gapStart);
            }
         }
      }

      return var2;
   }

   public final void resizeObjects() {
      Object[] var1;
      if(this.objects == null) {
         var1 = new Object[100];
      } else {
         int var2 = this.objects.length;
         var1 = new Object[var2 * 2];
         System.arraycopy(this.objects, 0, var1, 0, var2);
      }

      this.objects = var1;
   }

   public void setAttributeName(int var1, int var2) {
      this.setIntN(var1 + 1, var2);
   }

   public void setElementName(int var1, int var2) {
      int var3 = var1;
      if(this.data[var1] == '\uf108') {
         var3 = this.getIntN(var1 + 1);
         if(var3 < 0) {
            var1 = this.data.length;
         }

         var3 += var1;
      }

      if(var3 < this.gapEnd) {
         throw new Error("setElementName before gapEnd");
      } else {
         this.setIntN(var3 + 1, var2);
      }
   }

   public final void setIntN(int var1, int var2) {
      this.data[var1] = (char)(var2 >> 16);
      this.data[var1 + 1] = (char)var2;
   }

   public int size() {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         var2 = this.nextPos(var2);
         if(var2 == 0) {
            return var1;
         }

         ++var1;
      }
   }

   public void startAttribute(int var1) {
      this.ensureSpace(6);
      --this.gapEnd;
      char[] var2 = this.data;
      int var3 = this.gapStart;
      this.gapStart = var3 + 1;
      var2[var3] = '\uf109';
      if(this.attrStart != 0) {
         throw new Error("nested attribute");
      } else {
         this.attrStart = this.gapStart;
         this.setIntN(this.gapStart, var1);
         this.setIntN(this.gapStart + 2, this.gapEnd - this.data.length);
         this.gapStart += 4;
         this.data[this.gapEnd] = '\uf10a';
      }
   }

   public void startAttribute(Object var1) {
      this.startAttribute(this.find(var1));
   }

   public void startDocument() {
      int var1 = -1;
      this.ensureSpace(6);
      --this.gapEnd;
      int var2 = this.gapStart;
      this.data[var2] = '\uf110';
      if(this.docStart != 0) {
         throw new Error("nested document");
      } else {
         this.docStart = var2 + 1;
         this.setIntN(var2 + 1, this.gapEnd - this.data.length);
         if(this.currentParent != -1) {
            var1 = this.currentParent - var2;
         }

         this.setIntN(var2 + 3, var1);
         this.currentParent = var2;
         this.gapStart = var2 + 5;
         this.currentParent = var2;
         this.data[this.gapEnd] = '\uf111';
      }
   }

   public void startElement(int var1) {
      this.ensureSpace(10);
      this.gapEnd -= 7;
      char[] var2 = this.data;
      int var3 = this.gapStart;
      this.gapStart = var3 + 1;
      var2[var3] = '\uf108';
      this.setIntN(this.gapStart, this.gapEnd - this.data.length);
      this.gapStart += 2;
      this.data[this.gapEnd] = '\uf10c';
      this.setIntN(this.gapEnd + 1, var1);
      this.setIntN(this.gapEnd + 3, this.gapStart - 3);
      this.setIntN(this.gapEnd + 5, this.currentParent);
      this.currentParent = this.gapStart - 3;
   }

   public void startElement(Object var1) {
      this.startElement(this.find(var1));
   }

   public void statistics() {
      PrintWriter var1 = new PrintWriter(System.out);
      this.statistics(var1);
      var1.flush();
   }

   public void statistics(PrintWriter var1) {
      var1.print("data array length: ");
      var1.println(this.data.length);
      var1.print("data array gap: ");
      var1.println(this.gapEnd - this.gapStart);
      var1.print("object array length: ");
      var1.println(this.objects.length);
   }

   public int stringValue(int var1, StringBuffer var2) {
      int var3 = this.nextNodeIndex(var1, Integer.MAX_VALUE);
      if(var3 > var1) {
         this.stringValue(var1, var3, var2);
         return var1;
      } else {
         return this.stringValue(false, var1, var2);
      }
   }

   public int stringValue(boolean var1, int var2, StringBuffer var3) {
      Object var5 = null;
      byte var9 = 0;
      byte var8 = 0;
      int var7 = var2;
      if(var2 >= this.gapStart) {
         var7 = var2 + (this.gapEnd - this.gapStart);
      }

      if(var7 == this.data.length) {
         return -1;
      } else {
         char var4 = this.data[var7];
         ++var7;
         if(var4 <= '\u9fff') {
            var3.append(var4);
            return var7;
         } else {
            int var10;
            if(var4 >= '\ue000' && var4 <= '\uefff') {
               if(false) {
                  var3.append(' ');
               }

               var5 = this.objects[var4 - '\ue000'];
               var2 = var8;
            } else if(var4 >= 'ꀀ' && var4 <= '꿿') {
               var2 = var7 + 2;
               var7 = this.data[var7] + var7 + 1;
            } else {
               if(('\uff00' & var4) == '\uf000') {
                  var3.append(var4 & 255);
                  return var7;
               }

               if(var4 >= '뀀' && var4 <= '\udfff') {
                  var3.append(var4 - '쀀');
                  return var7;
               }

               var2 = var7;
               switch(var4) {
               case '\uf100':
               case '\uf101':
                  if(false) {
                     var3.append(' ');
                  }

                  if(var4 != '\uf100') {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  var3.append(var1);
                  return var7;
               case '\uf102':
                  if(false) {
                     var3.append(' ');
                  }

                  var3.append(this.getIntN(var7));
                  return var7 + 2;
               case '\uf103':
                  if(false) {
                     var3.append(' ');
                  }

                  var3.append(this.getLongN(var7));
                  return var7 + 4;
               case '\uf104':
                  if(false) {
                     var3.append(' ');
                  }

                  var3.append(Float.intBitsToFloat(this.getIntN(var7)));
                  return var7 + 2;
               case '\uf105':
                  if(false) {
                     var3.append(' ');
                  }

                  var3.append(Double.longBitsToDouble(this.getLongN(var7)));
                  return var7 + 4;
               case '\uf106':
                  var3.append(this.data[var7]);
                  return var7 + 1;
               case '\uf107':
               case '\uf10d':
               case '\uf10e':
               default:
                  throw new Error("unimplemented: " + Integer.toHexString(var4) + " at:" + var7);
               case '\uf108':
                  var10 = var7 + 2;
                  int var11 = this.getIntN(var7);
                  if(var11 < 0) {
                     var2 = this.data.length;
                  } else {
                     var2 = var7 - 1;
                  }

                  var7 = var11 + var2 + 7;
                  var2 = var10;
                  break;
               case '\uf109':
                  var2 = var9;
                  if(!var1) {
                     var2 = var7 + 4;
                  }

                  var10 = this.getIntN(var7 + 2);
                  if(var10 < 0) {
                     var7 = this.data.length + 1;
                  }

                  var7 += var10;
                  break;
               case '\uf10a':
               case '\uf10b':
               case '\uf10c':
               case '\uf111':
               case '\uf113':
                  return -1;
               case '\uf10f':
                  AbstractSequence var6 = (AbstractSequence)this.objects[this.getIntN(var7)];
                  var2 = this.getIntN(var7 + 2);
                  ((TreeList)var6).stringValue(var1, var2 >> 1, var3);
                  var7 += 4;
                  var2 = var8;
                  break;
               case '\uf110':
               case '\uf112':
                  var2 = var7 + 4;
                  var7 = this.nextDataIndex(var7 - 1);
                  break;
               case '\uf114':
                  var2 = var7 + 2;
               case '\uf115':
               case '\uf117':
                  var7 = this.getIntN(var2);
                  var2 += 2;
                  if(!var1 || var4 == '\uf115') {
                     var3.append(this.data, var2, var7);
                  }

                  return var2 + var7;
               case '\uf116':
                  var2 = var8;
                  break;
               case '\uf118':
                  return var7 + 2;
               }
            }

            if(var5 != null) {
               var3.append(var5);
            }

            if(var2 > 0) {
               do {
                  var10 = this.stringValue(true, var2, var3);
                  var2 = var10;
               } while(var10 >= 0);
            }

            return var7;
         }
      }
   }

   public void stringValue(int var1, int var2, StringBuffer var3) {
      while(var1 < var2 && var1 >= 0) {
         var1 = this.stringValue(false, var1, var3);
      }

   }

   public void toString(String var1, StringBuffer var2) {
      int var4 = 0;
      int var8 = this.gapStart;
      boolean var6 = false;
      boolean var5 = false;

      while(true) {
         int var7 = var8;
         int var9 = var4;
         if(var4 >= var8) {
            if(var4 != this.gapStart) {
               break;
            }

            var4 = this.gapEnd;
            var8 = this.data.length;
            var7 = var8;
            var9 = var4;
            if(var4 == var8) {
               break;
            }
         }

         char[] var3 = this.data;
         var4 = var9 + 1;
         char var13 = var3[var9];
         int var12;
         boolean var16;
         if(var13 > '\u9fff') {
            boolean var14;
            if(var13 >= '\ue000' && var13 <= '\uefff') {
               var14 = var5;
               if(var5) {
                  var2.append('>');
                  var14 = false;
               }

               if(var6) {
                  var2.append(var1);
               } else {
                  var6 = true;
               }

               var2.append(this.objects[var13 - '\ue000']);
               var5 = var14;
               var8 = var7;
            } else if(var13 >= 'ꀀ' && var13 <= '꿿') {
               if(var5) {
                  var2.append('>');
               }

               if(var6) {
                  var2.append(var1);
               }

               var2.append('<');
               var2.append(this.objects[var13 - 'ꀀ'].toString());
               var4 += 2;
               var6 = false;
               var5 = true;
               var8 = var7;
            } else if(var13 >= '뀀' && var13 <= '\udfff') {
               var14 = var5;
               if(var5) {
                  var2.append('>');
                  var14 = false;
               }

               if(var6) {
                  var2.append(var1);
               } else {
                  var6 = true;
               }

               var2.append(var13 - '쀀');
               var5 = var14;
               var8 = var7;
            } else {
               int var11;
               switch(var13) {
               case '\uf100':
               case '\uf101':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  if(var6) {
                     var2.append(var1);
                  } else {
                     var6 = true;
                  }

                  boolean var10;
                  if(var13 != '\uf100') {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  var2.append(var10);
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf102':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  if(var6) {
                     var2.append(var1);
                  } else {
                     var6 = true;
                  }

                  var2.append(this.getIntN(var4));
                  var4 += 2;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf103':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  if(var6) {
                     var2.append(var1);
                  } else {
                     var6 = true;
                  }

                  var2.append(this.getLongN(var4));
                  var4 += 4;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf104':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  if(var6) {
                     var2.append(var1);
                  } else {
                     var6 = true;
                  }

                  var2.append(Float.intBitsToFloat(this.getIntN(var4)));
                  var4 += 2;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf105':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  if(var6) {
                     var2.append(var1);
                  } else {
                     var6 = true;
                  }

                  var2.append(Double.longBitsToDouble(this.getLongN(var4)));
                  var4 += 4;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf106':
                  var6 = var5;
                  if(var5) {
                     var2.append('>');
                     var6 = false;
                  }

                  var2.append(this.data, var4, var13 + 1 - '\uf106');
                  var16 = false;
                  ++var4;
                  var5 = var6;
                  var8 = var7;
                  var6 = var16;
                  break;
               case '\uf107':
               default:
                  throw new Error("unknown code:" + var13);
               case '\uf108':
                  var9 = this.getIntN(var4);
                  if(var9 >= 0) {
                     var8 = var4 - 1;
                  } else {
                     var8 = this.data.length;
                  }

                  var4 += 2;
                  var8 = this.getIntN(var9 + var8 + 1);
                  if(var5) {
                     var2.append('>');
                  } else if(var6) {
                     var2.append(var1);
                  }

                  var2.append('<');
                  var2.append(this.objects[var8]);
                  var6 = false;
                  var5 = true;
                  var8 = var7;
                  break;
               case '\uf109':
                  var11 = this.getIntN(var4);
                  var2.append(' ');
                  var2.append(this.objects[var11]);
                  var2.append("=\"");
                  var5 = false;
                  var4 += 4;
                  var8 = var7;
                  break;
               case '\uf10a':
                  var2.append('\"');
                  var5 = true;
                  var6 = false;
                  var8 = var7;
                  break;
               case '\uf10b':
               case '\uf10c':
                  if(var13 == '\uf10b') {
                     var3 = this.data;
                     var12 = var4 + 1;
                     char var15 = var3[var4];
                     var8 = this.data[var12 - 2 - var15] - 'ꀀ';
                     var4 = var12;
                     var12 = var8;
                  } else {
                     var12 = this.getIntN(var4);
                     var4 += 6;
                  }

                  if(var5) {
                     var2.append("/>");
                  } else {
                     var2.append("</");
                     var2.append(this.objects[var12]);
                     var2.append('>');
                  }

                  var5 = false;
                  var6 = true;
                  var8 = var7;
                  break;
               case '\uf10d':
               case '\uf10e':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  if(var6) {
                     var2.append(var1);
                  } else {
                     var6 = true;
                  }

                  var2.append(this.objects[this.getIntN(var4)]);
                  var4 += 2;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf10f':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  if(var6) {
                     var2.append(var1);
                  } else {
                     var6 = true;
                  }

                  var2.append(((AbstractSequence)this.objects[this.getIntN(var4)]).getIteratorAtPos(this.getIntN(var4 + 2)));
                  var4 += 4;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf110':
               case '\uf112':
                  var4 += 4;
                  var8 = var7;
                  break;
               case '\uf111':
               case '\uf113':
                  var8 = var7;
                  break;
               case '\uf114':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  var2.append("<?");
                  var11 = this.getIntN(var4);
                  var4 += 2;
                  var2.append(this.objects[var11]);
                  var9 = this.getIntN(var4);
                  var11 = var4 + 2;
                  var4 = var11;
                  if(var9 > 0) {
                     var2.append(' ');
                     var2.append(this.data, var11, var9);
                     var4 = var11 + var9;
                  }

                  var2.append("?>");
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf115':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  var11 = this.getIntN(var4);
                  var4 += 2;
                  var2.append("<![CDATA[");
                  var2.append(this.data, var4, var11);
                  var2.append("]]>");
                  var4 += var11;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf116':
                  var8 = var7;
                  break;
               case '\uf117':
                  var14 = var5;
                  if(var5) {
                     var2.append('>');
                     var14 = false;
                  }

                  var11 = this.getIntN(var4);
                  var4 += 2;
                  var2.append("<!--");
                  var2.append(this.data, var4, var11);
                  var2.append("-->");
                  var4 += var11;
                  var5 = var14;
                  var8 = var7;
                  break;
               case '\uf118':
                  var4 += 2;
                  var8 = var7;
               }
            }
         } else {
            for(var8 = var4 - 1; var4 < var7; var4 = var12) {
               var3 = this.data;
               var12 = var4 + 1;
               if(var3[var4] > '\u9fff') {
                  var4 = var12 - 1;
                  break;
               }
            }

            var6 = var5;
            if(var5) {
               var2.append('>');
               var6 = false;
            }

            var2.append(this.data, var8, var4 - var8);
            var16 = false;
            var5 = var6;
            var8 = var7;
            var6 = var16;
         }
      }

   }

   public void write(int var1) {
      this.ensureSpace(3);
      char[] var2;
      int var3;
      if(var1 <= '\u9fff') {
         var2 = this.data;
         var3 = this.gapStart;
         this.gapStart = var3 + 1;
         var2[var3] = (char)var1;
      } else if(var1 < 65536) {
         var2 = this.data;
         var3 = this.gapStart;
         this.gapStart = var3 + 1;
         var2[var3] = '\uf106';
         var2 = this.data;
         var3 = this.gapStart;
         this.gapStart = var3 + 1;
         var2[var3] = (char)var1;
      } else {
         Char.print(var1, this);
      }
   }

   public void write(CharSequence var1, int var2, int var3) {
      if(var3 == 0) {
         this.writeJoiner();
      }

      this.ensureSpace(var3);

      for(; var3 > 0; ++var2) {
         char var4 = var1.charAt(var2);
         --var3;
         if(var4 <= '\u9fff') {
            char[] var5 = this.data;
            int var6 = this.gapStart;
            this.gapStart = var6 + 1;
            var5[var6] = var4;
         } else {
            this.write(var4);
            this.ensureSpace(var3);
         }
      }

   }

   public void write(String var1) {
      this.write((CharSequence)var1, 0, var1.length());
   }

   public void write(char[] var1, int var2, int var3) {
      if(var3 == 0) {
         this.writeJoiner();
      }

      this.ensureSpace(var3);

      for(; var3 > 0; ++var2) {
         char var4 = var1[var2];
         --var3;
         if(var4 <= '\u9fff') {
            char[] var5 = this.data;
            int var6 = this.gapStart;
            this.gapStart = var6 + 1;
            var5[var6] = var4;
         } else {
            this.write(var4);
            this.ensureSpace(var3);
         }
      }

   }

   public void writeBoolean(boolean var1) {
      this.ensureSpace(1);
      char[] var3 = this.data;
      int var4 = this.gapStart;
      this.gapStart = var4 + 1;
      char var2;
      if(var1) {
         var2 = '\uf101';
      } else {
         var2 = '\uf100';
      }

      var3[var4] = var2;
   }

   public void writeByte(int var1) {
      this.ensureSpace(1);
      char[] var2 = this.data;
      int var3 = this.gapStart;
      this.gapStart = var3 + 1;
      var2[var3] = (char)('\uf000' + (var1 & 255));
   }

   public void writeCDATA(char[] var1, int var2, int var3) {
      this.ensureSpace(var3 + 3);
      int var5 = this.gapStart;
      char[] var4 = this.data;
      int var6 = var5 + 1;
      var4[var5] = '\uf115';
      this.setIntN(var6, var3);
      var5 = var6 + 2;
      System.arraycopy(var1, var2, this.data, var5, var3);
      this.gapStart = var5 + var3;
   }

   public void writeComment(String var1, int var2, int var3) {
      this.ensureSpace(var3 + 3);
      int var5 = this.gapStart;
      char[] var4 = this.data;
      int var6 = var5 + 1;
      var4[var5] = '\uf117';
      this.setIntN(var6, var3);
      var5 = var6 + 2;
      var1.getChars(var2, var2 + var3, this.data, var5);
      this.gapStart = var5 + var3;
   }

   public void writeComment(char[] var1, int var2, int var3) {
      this.ensureSpace(var3 + 3);
      int var5 = this.gapStart;
      char[] var4 = this.data;
      int var6 = var5 + 1;
      var4[var5] = '\uf117';
      this.setIntN(var6, var3);
      var5 = var6 + 2;
      System.arraycopy(var1, var2, this.data, var5, var3);
      this.gapStart = var5 + var3;
   }

   public void writeDocumentUri(Object var1) {
      this.ensureSpace(3);
      int var2 = this.find(var1);
      char[] var4 = this.data;
      int var3 = this.gapStart;
      this.gapStart = var3 + 1;
      var4[var3] = '\uf118';
      this.setIntN(this.gapStart, var2);
      this.gapStart += 2;
   }

   public void writeDouble(double var1) {
      this.ensureSpace(5);
      long var5 = Double.doubleToLongBits(var1);
      char[] var3 = this.data;
      int var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = '\uf105';
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)(var5 >>> 48));
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)(var5 >>> 32));
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)(var5 >>> 16));
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)var5);
   }

   public void writeFloat(float var1) {
      this.ensureSpace(3);
      int var3 = Float.floatToIntBits(var1);
      char[] var2 = this.data;
      int var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var2[var4] = '\uf104';
      var2 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var2[var4] = (char)(var3 >>> 16);
      var2 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var2[var4] = (char)var3;
   }

   public void writeInt(int var1) {
      this.ensureSpace(3);
      char[] var2;
      int var3;
      if(var1 >= -4096 && var1 <= 8191) {
         var2 = this.data;
         var3 = this.gapStart;
         this.gapStart = var3 + 1;
         var2[var3] = (char)('쀀' + var1);
      } else {
         var2 = this.data;
         var3 = this.gapStart;
         this.gapStart = var3 + 1;
         var2[var3] = '\uf102';
         this.setIntN(this.gapStart, var1);
         this.gapStart += 2;
      }
   }

   public void writeJoiner() {
      this.ensureSpace(1);
      char[] var1 = this.data;
      int var2 = this.gapStart;
      this.gapStart = var2 + 1;
      var1[var2] = '\uf116';
   }

   public void writeLong(long var1) {
      this.ensureSpace(5);
      char[] var3 = this.data;
      int var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = '\uf103';
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)(var1 >>> 48));
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)(var1 >>> 32));
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)(var1 >>> 16));
      var3 = this.data;
      var4 = this.gapStart;
      this.gapStart = var4 + 1;
      var3[var4] = (char)((int)var1);
   }

   public void writeObject(Object var1) {
      this.ensureSpace(3);
      int var2 = this.find(var1);
      int var3;
      char[] var4;
      if(var2 < 4096) {
         var4 = this.data;
         var3 = this.gapStart;
         this.gapStart = var3 + 1;
         var4[var3] = (char)('\ue000' | var2);
      } else {
         var4 = this.data;
         var3 = this.gapStart;
         this.gapStart = var3 + 1;
         var4[var3] = '\uf10d';
         this.setIntN(this.gapStart, var2);
         this.gapStart += 2;
      }
   }

   public void writePosition(AbstractSequence var1, int var2) {
      this.ensureSpace(5);
      this.data[this.gapStart] = '\uf10f';
      int var3 = this.find(var1);
      this.setIntN(this.gapStart + 1, var3);
      this.setIntN(this.gapStart + 3, var2);
      this.gapStart += 5;
   }

   public void writeProcessingInstruction(String var1, String var2, int var3, int var4) {
      this.ensureSpace(var4 + 5);
      int var6 = this.gapStart;
      char[] var5 = this.data;
      int var7 = var6 + 1;
      var5[var6] = '\uf114';
      this.setIntN(var7, this.find(var1));
      this.setIntN(var7 + 2, var4);
      var6 = var7 + 4;
      var2.getChars(var3, var3 + var4, this.data, var6);
      this.gapStart = var6 + var4;
   }

   public void writeProcessingInstruction(String var1, char[] var2, int var3, int var4) {
      this.ensureSpace(var4 + 5);
      int var6 = this.gapStart;
      char[] var5 = this.data;
      int var7 = var6 + 1;
      var5[var6] = '\uf114';
      this.setIntN(var7, this.find(var1));
      this.setIntN(var7 + 2, var4);
      var6 = var7 + 4;
      System.arraycopy(var2, var3, this.data, var6, var4);
      this.gapStart = var6 + var4;
   }
}
