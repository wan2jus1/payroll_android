package org.json;

import java.io.StringWriter;
import org.json.JSONWriter;

public class JSONStringer extends JSONWriter {

   public JSONStringer() {
      super(new StringWriter());
   }

   public String toString() {
      return this.mode == 100?this.writer.toString():null;
   }
}
