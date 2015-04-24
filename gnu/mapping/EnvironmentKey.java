package gnu.mapping;

import gnu.mapping.Symbol;

public interface EnvironmentKey {

   Object FUNCTION = Symbol.FUNCTION;


   Object getKeyProperty();

   Symbol getKeySymbol();

   boolean matches(EnvironmentKey var1);

   boolean matches(Symbol var1, Object var2);
}
