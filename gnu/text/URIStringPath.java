package gnu.text;

import gnu.text.URIPath;
import java.net.URI;

class URIStringPath extends URIPath {

   String uriString;


   public URIStringPath(URI var1, String var2) {
      super(var1);
      this.uriString = var2;
   }

   public String toURIString() {
      return this.uriString;
   }
}
