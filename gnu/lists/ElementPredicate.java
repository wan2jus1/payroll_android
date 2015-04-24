package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;

public interface ElementPredicate extends NodePredicate {

   boolean isInstance(AbstractSequence var1, int var2, Object var3);
}
