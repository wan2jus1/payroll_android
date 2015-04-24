package org.acra.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class BoundedLinkedList extends LinkedList {

   private final int maxSize;


   public BoundedLinkedList(int var1) {
      this.maxSize = var1;
   }

   public void add(int var1, Object var2) {
      if(this.size() == this.maxSize) {
         this.removeFirst();
      }

      super.add(var1, var2);
   }

   public boolean add(Object var1) {
      if(this.size() == this.maxSize) {
         this.removeFirst();
      }

      return super.add(var1);
   }

   public boolean addAll(int var1, Collection var2) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection var1) {
      int var2 = this.size() + var1.size() - this.maxSize;
      if(var2 > 0) {
         this.removeRange(0, var2);
      }

      return super.addAll(var1);
   }

   public void addFirst(Object var1) {
      throw new UnsupportedOperationException();
   }

   public void addLast(Object var1) {
      this.add(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         var1.append(var2.next().toString());
      }

      return var1.toString();
   }
}
