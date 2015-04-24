package kawa.lang;

import kawa.lang.TemplateScope;

public interface SyntaxForm {

   Object getDatum();

   TemplateScope getScope();
}
