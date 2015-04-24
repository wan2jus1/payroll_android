package gnu.lists;

import gnu.lists.AbstractSequence;
import gnu.lists.SeqPosition;

public interface PositionConsumer {

   void consume(SeqPosition var1);

   void writePosition(AbstractSequence var1, int var2);
}
