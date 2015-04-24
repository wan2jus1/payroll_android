package com.google.appinventor.components.runtime;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.TextBoxBase;

@DesignerComponent(
   category = ComponentCategory.USERINTERFACE,
   description = "<p>A box for entering passwords.  This is the same as the ordinary <code>TextBox</code> component except this does not display the characters typed by the user.</p><p>The value of the text in the box can be found or set through the <code>Text</code> property. If blank, the <code>Hint</code> property, which appears as faint text in the box, can provide the user with guidance as to what to type.</p> <p>Text boxes are usually used with the <code>Button</code> component, with the user clicking on the button when text entry is complete.</p>",
   version = 3
)
@SimpleObject
public final class PasswordTextBox extends TextBoxBase {

   public PasswordTextBox(ComponentContainer var1) {
      super(var1, new EditText(var1.$context()));
      this.view.setRawInputType(128);
      this.view.setSingleLine(true);
      this.view.setTransformationMethod(new PasswordTransformationMethod());
      this.view.setImeOptions(6);
   }
}
