package gnu.bytecode;

import gnu.bytecode.ClassType;

public interface Member {

   ClassType getDeclaringClass();

   int getModifiers();

   String getName();

   boolean getStaticFlag();

   void setName(String var1);
}
