package gnu.mapping;


public class WrappedException extends RuntimeException {

   public WrappedException() {
   }

   public WrappedException(String var1) {
      super(var1);
   }

   public WrappedException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public WrappedException(Throwable var1) {
      this(var1.toString(), var1);
   }

   public static RuntimeException wrapIfNeeded(Throwable var0) {
      return (RuntimeException)(var0 instanceof RuntimeException?(RuntimeException)var0:new WrappedException(var0));
   }

   public Throwable getException() {
      return this.getCause();
   }

   public String toString() {
      return this.getMessage();
   }
}
