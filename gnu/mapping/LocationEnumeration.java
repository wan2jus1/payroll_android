package gnu.mapping;

import gnu.mapping.Location;
import gnu.mapping.NamedLocation;
import gnu.mapping.SimpleEnvironment;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LocationEnumeration implements Iterator, Enumeration {

   NamedLocation[] bindings;
   SimpleEnvironment env;
   int index;
   LocationEnumeration inherited;
   NamedLocation nextLoc;
   NamedLocation prevLoc;


   public LocationEnumeration(SimpleEnvironment var1) {
      this(var1.table, 1 << var1.log2Size);
   }

   public LocationEnumeration(NamedLocation[] var1, int var2) {
      this.bindings = var1;
      this.index = var2;
   }

   public boolean hasMoreElements() {
      return this.env.hasMoreElements(this);
   }

   public boolean hasNext() {
      return this.hasMoreElements();
   }

   public Location next() {
      return this.nextElement();
   }

   public Location nextElement() {
      return this.nextLocation();
   }

   public Location nextLocation() {
      if(this.nextLoc == null && !this.hasMoreElements()) {
         throw new NoSuchElementException();
      } else {
         NamedLocation var1 = this.prevLoc;
         if(this.prevLoc == null) {
            var1 = this.bindings[this.index];
            if(this.nextLoc != var1) {
               this.prevLoc = var1;
            }
         }

         while(this.prevLoc != null && this.prevLoc.next != this.nextLoc) {
            this.prevLoc = this.prevLoc.next;
         }

         var1 = this.nextLoc;
         this.nextLoc = this.nextLoc.next;
         return var1;
      }
   }

   public void remove() {
      NamedLocation var1;
      if(this.prevLoc != null) {
         var1 = this.prevLoc.next;
      } else {
         var1 = this.bindings[this.index];
      }

      if(var1 != null && var1.next == this.nextLoc) {
         var1.next = null;
         if(this.prevLoc != null) {
            this.prevLoc.next = this.nextLoc;
         } else {
            this.bindings[this.index] = this.nextLoc;
         }

         SimpleEnvironment var2 = this.env;
         --var2.num_bindings;
      } else {
         throw new IllegalStateException();
      }
   }
}
