package gnu.lists;


public interface Array {

   Object get(int[] var1);

   int getEffectiveIndex(int[] var1);

   int getLowBound(int var1);

   Object getRowMajor(int var1);

   int getSize(int var1);

   boolean isEmpty();

   int rank();

   Object set(int[] var1, Object var2);

   Array transpose(int[] var1, int[] var2, int var3, int[] var4);
}
