package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.expr.Expression;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class MakeXmlElement extends Syntax {

   public static final MakeXmlElement makeXml = new MakeXmlElement();
   static final ClassType typeNamespace;


   static {
      makeXml.setName("$make-xml$");
      typeNamespace = ClassType.make("gnu.mapping.Namespace");
   }

   public Expression rewriteForm(Pair param1, Translator param2) {
      // $FF: Couldn't be decompiled
   }
}
