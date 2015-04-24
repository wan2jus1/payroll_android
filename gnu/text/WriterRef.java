package gnu.text;

import java.io.Writer;
import java.lang.ref.WeakReference;

class WriterRef extends WeakReference {

   WriterRef next;
   WriterRef prev;


   public WriterRef(Writer var1) {
      super(var1);
   }
}
