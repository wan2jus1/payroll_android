package gnu.lists;

import gnu.lists.Consumer;
import gnu.lists.XConsumer;

public class FilterConsumer implements XConsumer {

   protected Object attributeType;
   protected Consumer base;
   protected boolean inAttribute;
   protected boolean skipping;


   public FilterConsumer(Consumer var1) {
      this.base = var1;
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

      this.write((CharSequence)var2, 0, ((CharSequence)var2).length());
      return this;
   }

   public Consumer append(CharSequence var1, int var2, int var3) {
      Object var4 = var1;
      if(var1 == null) {
         var4 = "null";
      }

      this.write((CharSequence)var4, var2, var3 - var2);
      return this;
   }

   protected void beforeContent() {
   }

   protected void beforeNode() {
   }

   public void beginEntity(Object var1) {
      if(!this.skipping) {
         this.beforeNode();
         if(this.base instanceof XConsumer) {
            ((XConsumer)this.base).beginEntity(var1);
         }
      }

   }

   public void endAttribute() {
      if(!this.skipping) {
         this.base.endAttribute();
      }

      this.inAttribute = false;
   }

   public void endDocument() {
      if(!this.skipping) {
         this.base.endDocument();
      }

   }

   public void endElement() {
      if(!this.skipping) {
         this.base.endElement();
      }

   }

   public void endEntity() {
      if(!this.skipping && this.base instanceof XConsumer) {
         ((XConsumer)this.base).endEntity();
      }

   }

   public boolean ignoring() {
      return this.base.ignoring();
   }

   public void startAttribute(Object var1) {
      this.attributeType = var1;
      this.inAttribute = true;
      if(!this.skipping) {
         this.beforeNode();
         this.base.startAttribute(var1);
      }

   }

   public void startDocument() {
      if(!this.skipping) {
         this.base.startDocument();
      }

   }

   public void startElement(Object var1) {
      if(!this.skipping) {
         this.beforeNode();
         this.base.startElement(var1);
      }

   }

   public void write(int var1) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.write(var1);
      }

   }

   public void write(CharSequence var1, int var2, int var3) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.write((CharSequence)var1, var2, var3);
      }

   }

   public void write(String var1) {
      this.write((CharSequence)var1, 0, var1.length());
   }

   public void write(char[] var1, int var2, int var3) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.write((char[])var1, var2, var3);
      }

   }

   public void writeBoolean(boolean var1) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.writeBoolean(var1);
      }

   }

   public void writeCDATA(char[] var1, int var2, int var3) {
      this.beforeContent();
      if(!this.skipping) {
         if(!(this.base instanceof XConsumer)) {
            this.base.write((char[])var1, var2, var3);
            return;
         }

         ((XConsumer)this.base).writeCDATA(var1, var2, var3);
      }

   }

   public void writeComment(char[] var1, int var2, int var3) {
      if(!this.skipping) {
         this.beforeNode();
         if(this.base instanceof XConsumer) {
            ((XConsumer)this.base).writeComment(var1, var2, var3);
         }
      }

   }

   public void writeDouble(double var1) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.writeDouble(var1);
      }

   }

   public void writeFloat(float var1) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.writeFloat(var1);
      }

   }

   public void writeInt(int var1) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.writeInt(var1);
      }

   }

   public void writeLong(long var1) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.writeLong(var1);
      }

   }

   public void writeObject(Object var1) {
      this.beforeContent();
      if(!this.skipping) {
         this.base.writeObject(var1);
      }

   }

   public void writeProcessingInstruction(String var1, char[] var2, int var3, int var4) {
      if(!this.skipping) {
         this.beforeNode();
         if(this.base instanceof XConsumer) {
            ((XConsumer)this.base).writeProcessingInstruction(var1, var2, var3, var4);
         }
      }

   }
}
