package gnu.lists;

import gnu.lists.Consumable;
import gnu.lists.EofClass;
import java.util.Enumeration;
import java.util.List;

public interface Sequence extends List, Consumable {

   int ATTRIBUTE_VALUE = 35;
   int BOOLEAN_VALUE = 27;
   int CDATA_VALUE = 31;
   int CHAR_VALUE = 29;
   int COMMENT_VALUE = 36;
   int DOCUMENT_VALUE = 34;
   int DOUBLE_VALUE = 26;
   int ELEMENT_VALUE = 33;
   int EOF_VALUE = 0;
   int FLOAT_VALUE = 25;
   int INT_S16_VALUE = 20;
   int INT_S32_VALUE = 22;
   int INT_S64_VALUE = 24;
   int INT_S8_VALUE = 18;
   int INT_U16_VALUE = 19;
   int INT_U32_VALUE = 21;
   int INT_U64_VALUE = 23;
   int INT_U8_VALUE = 17;
   int OBJECT_VALUE = 32;
   int PRIM_VALUE = 16;
   int PROCESSING_INSTRUCTION_VALUE = 37;
   int TEXT_BYTE_VALUE = 28;
   Object eofValue = EofClass.eofValue;


   Enumeration elements();

   void fill(Object var1);

   Object get(int var1);

   boolean isEmpty();

   Object set(int var1, Object var2);

   int size();
}
