package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.FileUtils;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.IsEqual;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.URIPath;
import java.io.File;
import java.io.IOException;
import kawa.lib.ports;
import kawa.standard.readchar;

public class files extends ModuleBody {

   public static final ModuleMethod $Mn$Grpathname;
   public static final ModuleMethod $Pcfile$Mnseparator;
   public static final files $instance = new files();
   static final SimpleSymbol Lit0 = (SimpleSymbol)(new SimpleSymbol("path?")).readResolve();
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("filepath?")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("path-parent")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("path-last")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("path-extension")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("path-port")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("path-query")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("path-fragment")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("file-exists?")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("file-directory?")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("file-readable?")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("file-writable?")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("URI?")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("delete-file")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("rename-file")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("copy-file")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("create-directory")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("directory-files")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("->pathname")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("%file-separator")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("system-tmpdir")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("resolve-uri")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("make-temporary-file")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("absolute-path?")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("path-scheme")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("path-authority")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("path-user-info")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("path-host")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("path-file")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("path-directory")).readResolve();
   public static final ModuleMethod URI$Qu;
   public static final ModuleMethod absolute$Mnpath$Qu;
   public static final ModuleMethod copy$Mnfile;
   public static final ModuleMethod create$Mndirectory;
   public static final ModuleMethod delete$Mnfile;
   public static final ModuleMethod directory$Mnfiles;
   public static final ModuleMethod file$Mndirectory$Qu;
   public static final ModuleMethod file$Mnexists$Qu;
   public static final ModuleMethod file$Mnreadable$Qu;
   public static final ModuleMethod file$Mnwritable$Qu;
   public static final ModuleMethod filepath$Qu;
   public static final ModuleMethod make$Mntemporary$Mnfile;
   public static final ModuleMethod path$Mnauthority;
   public static final ModuleMethod path$Mndirectory;
   public static final ModuleMethod path$Mnextension;
   public static final ModuleMethod path$Mnfile;
   public static final ModuleMethod path$Mnfragment;
   public static final ModuleMethod path$Mnhost;
   public static final ModuleMethod path$Mnlast;
   public static final ModuleMethod path$Mnparent;
   public static final ModuleMethod path$Mnport;
   public static final ModuleMethod path$Mnquery;
   public static final ModuleMethod path$Mnscheme;
   public static final ModuleMethod path$Mnuser$Mninfo;
   public static final ModuleMethod path$Qu;
   public static final ModuleMethod rename$Mnfile;
   public static final ModuleMethod resolve$Mnuri;
   public static final ModuleMethod system$Mntmpdir;


   public static String $PcFileSeparator() {
      return System.getProperty("file.separator");
   }

   public static Path $To$Pathname(Object var0) {
      return Path.valueOf(var0);
   }

   static {
      files var0 = $instance;
      path$Qu = new ModuleMethod(var0, 1, Lit0, 4097);
      filepath$Qu = new ModuleMethod(var0, 2, Lit1, 4097);
      URI$Qu = new ModuleMethod(var0, 3, Lit2, 4097);
      absolute$Mnpath$Qu = new ModuleMethod(var0, 4, Lit3, 4097);
      path$Mnscheme = new ModuleMethod(var0, 5, Lit4, 4097);
      path$Mnauthority = new ModuleMethod(var0, 6, Lit5, 4097);
      path$Mnuser$Mninfo = new ModuleMethod(var0, 7, Lit6, 4097);
      path$Mnhost = new ModuleMethod(var0, 8, Lit7, 4097);
      path$Mnfile = new ModuleMethod(var0, 9, Lit8, 4097);
      path$Mndirectory = new ModuleMethod(var0, 10, Lit9, 4097);
      path$Mnparent = new ModuleMethod(var0, 11, Lit10, 4097);
      path$Mnlast = new ModuleMethod(var0, 12, Lit11, 4097);
      path$Mnextension = new ModuleMethod(var0, 13, Lit12, 4097);
      path$Mnport = new ModuleMethod(var0, 14, Lit13, 4097);
      path$Mnquery = new ModuleMethod(var0, 15, Lit14, 4097);
      path$Mnfragment = new ModuleMethod(var0, 16, Lit15, 4097);
      file$Mnexists$Qu = new ModuleMethod(var0, 17, Lit16, 4097);
      file$Mndirectory$Qu = new ModuleMethod(var0, 18, Lit17, 4097);
      file$Mnreadable$Qu = new ModuleMethod(var0, 19, Lit18, 4097);
      file$Mnwritable$Qu = new ModuleMethod(var0, 20, Lit19, 4097);
      delete$Mnfile = new ModuleMethod(var0, 21, Lit20, 4097);
      rename$Mnfile = new ModuleMethod(var0, 22, Lit21, 8194);
      copy$Mnfile = new ModuleMethod(var0, 23, Lit22, 8194);
      create$Mndirectory = new ModuleMethod(var0, 24, Lit23, 4097);
      directory$Mnfiles = new ModuleMethod(var0, 25, Lit24, 4097);
      $Mn$Grpathname = new ModuleMethod(var0, 26, Lit25, 4097);
      $Pcfile$Mnseparator = new ModuleMethod(var0, 27, Lit26, 0);
      system$Mntmpdir = new ModuleMethod(var0, 28, Lit27, 0);
      resolve$Mnuri = new ModuleMethod(var0, 29, Lit28, 8194);
      make$Mntemporary$Mnfile = new ModuleMethod(var0, 30, Lit29, 4096);
      $instance.run();
   }

   public files() {
      ModuleInfo.register(this);
   }

   public static boolean URI$Qu(Object var0) {
      return var0 instanceof URIPath;
   }

   public static void copyFile(Path var0, Path var1) {
      InPort var2 = ports.openInputFile(var0);
      OutPort var4 = ports.openOutputFile(var1);

      for(Object var3 = readchar.readChar.apply1(var2); !ports.isEofObject(var3); var3 = readchar.readChar.apply1(var2)) {
         ports.writeChar(var3, var4);
      }

      ports.closeOutputPort(var4);
      ports.closeInputPort(var2);
   }

   public static boolean createDirectory(FilePath var0) {
      return var0.toFile().mkdir();
   }

   public static void deleteFile(FilePath var0) {
      if(!var0.delete()) {
         throw (Throwable)(new IOException(Format.formatToString(0, new Object[]{"cannot delete ~a", var0}).toString()));
      }
   }

   public static Object directoryFiles(FilePath var0) {
      File var1 = var0.toFile();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      String[] var3 = (new File(var2)).list();
      return var3 == null?Boolean.FALSE:LList.makeList(var3, 0);
   }

   public static boolean isAbsolutePath(Path var0) {
      return var0.isAbsolute();
   }

   public static boolean isFileDirectory(Path var0) {
      return var0.isDirectory();
   }

   public static boolean isFileExists(Path var0) {
      return var0.exists();
   }

   public static boolean isFileReadable(FilePath var0) {
      return var0.toFile().canRead();
   }

   public static boolean isFileWritable(FilePath var0) {
      return var0.toFile().canWrite();
   }

   public static boolean isFilepath(Object var0) {
      return var0 instanceof FilePath;
   }

   public static boolean isPath(Object var0) {
      return var0 instanceof Path;
   }

   public static FilePath makeTemporaryFile() {
      return makeTemporaryFile("kawa~d.tmp");
   }

   public static FilePath makeTemporaryFile(CharSequence var0) {
      return FilePath.makeFilePath(FileUtils.createTempFile(var0.toString()));
   }

   public static Object pathAuthority(Path var0) {
      String var1 = var0.getAuthority();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static Object pathDirectory(Path var0) {
      var0 = var0.getDirectory();
      return var0 == null?Boolean.FALSE:var0.toString();
   }

   public static Object pathExtension(Path var0) {
      String var1 = var0.getExtension();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static Object pathFile(Path var0) {
      String var1 = var0.getPath();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static Object pathFragment(Path var0) {
      String var1 = var0.getFragment();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static String pathHost(Path var0) {
      return var0.getHost();
   }

   public static Object pathLast(Path var0) {
      String var1 = var0.getLast();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static Object pathParent(Path var0) {
      var0 = var0.getParent();
      return var0 == null?Boolean.FALSE:var0.toString();
   }

   public static int pathPort(Path var0) {
      return var0.getPort();
   }

   public static Object pathQuery(Path var0) {
      String var1 = var0.getQuery();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static Object pathScheme(Path var0) {
      String var1 = var0.getScheme();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static Object pathUserInfo(Path var0) {
      String var1 = var0.getUserInfo();
      Object var2 = var1;
      if(var1 == null) {
         var2 = Boolean.FALSE;
      }

      return var2;
   }

   public static boolean renameFile(FilePath var0, FilePath var1) {
      return var0.toFile().renameTo(var1.toFile());
   }

   public static Path resolveUri(Path var0, Path var1) {
      return var1.resolve((Path)var0);
   }

   public static String systemTmpdir() {
      String var0 = System.getProperty("java.io.tmpdir");
      return var0 != null?var0:(IsEqual.apply($PcFileSeparator(), "\\")?"C:\\temp":"/tmp");
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 27:
         return $PcFileSeparator();
      case 28:
         return systemTmpdir();
      case 29:
      default:
         return super.apply0(var1);
      case 30:
         return makeTemporaryFile();
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      FilePath var25;
      Path var26;
      switch(var1.selector) {
      case 1:
         if(isPath(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
         if(isFilepath(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 3:
         if(URI$Qu(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 4:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "absolute-path?", 1, var2);
         }

         if(isAbsolutePath(var26)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 5:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "path-scheme", 1, var2);
         }

         return pathScheme(var26);
      case 6:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "path-authority", 1, var2);
         }

         return pathAuthority(var26);
      case 7:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "path-user-info", 1, var2);
         }

         return pathUserInfo(var26);
      case 8:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "path-host", 1, var2);
         }

         return pathHost(var26);
      case 9:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "path-file", 1, var2);
         }

         return pathFile(var26);
      case 10:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "path-directory", 1, var2);
         }

         return pathDirectory(var26);
      case 11:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "path-parent", 1, var2);
         }

         return pathParent(var26);
      case 12:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "path-last", 1, var2);
         }

         return pathLast(var26);
      case 13:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "path-extension", 1, var2);
         }

         return pathExtension(var26);
      case 14:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "path-port", 1, var2);
         }

         return Integer.valueOf(pathPort(var26));
      case 15:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "path-query", 1, var2);
         }

         return pathQuery(var26);
      case 16:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "path-fragment", 1, var2);
         }

         return pathFragment(var26);
      case 17:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "file-exists?", 1, var2);
         }

         if(isFileExists(var26)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 18:
         try {
            var26 = Path.valueOf(var2);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "file-directory?", 1, var2);
         }

         if(isFileDirectory(var26)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 19:
         try {
            var25 = FilePath.makeFilePath(var2);
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "file-readable?", 1, var2);
         }

         if(isFileReadable(var25)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 20:
         try {
            var25 = FilePath.makeFilePath(var2);
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "file-writable?", 1, var2);
         }

         if(isFileWritable(var25)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 21:
         try {
            var25 = FilePath.makeFilePath(var2);
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "delete-file", 1, var2);
         }

         deleteFile(var25);
         return Values.empty;
      case 22:
      case 23:
      case 27:
      case 28:
      case 29:
      default:
         return super.apply1(var1, var2);
      case 24:
         try {
            var25 = FilePath.makeFilePath(var2);
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "create-directory", 1, var2);
         }

         if(createDirectory(var25)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 25:
         try {
            var25 = FilePath.makeFilePath(var2);
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "directory-files", 1, var2);
         }

         return directoryFiles(var25);
      case 26:
         return $To$Pathname(var2);
      case 30:
         CharSequence var24;
         try {
            var24 = (CharSequence)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "make-temporary-file", 1, var2);
         }

         return makeTemporaryFile(var24);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      Path var10;
      Path var12;
      switch(var1.selector) {
      case 22:
         FilePath var11;
         try {
            var11 = FilePath.makeFilePath(var2);
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "rename-file", 1, var2);
         }

         FilePath var13;
         try {
            var13 = FilePath.makeFilePath(var3);
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "rename-file", 2, var3);
         }

         if(renameFile(var11, var13)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 23:
         try {
            var10 = Path.valueOf(var2);
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "copy-file", 1, var2);
         }

         try {
            var12 = Path.valueOf(var3);
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "copy-file", 2, var3);
         }

         copyFile(var10, var12);
         return Values.empty;
      case 29:
         try {
            var10 = Path.valueOf(var2);
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "resolve-uri", 1, var2);
         }

         try {
            var12 = Path.valueOf(var3);
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "resolve-uri", 2, var3);
         }

         return resolveUri(var10, var12);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 27:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 28:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 29:
      default:
         return super.match0(var1, var2);
      case 30:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 3:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 4:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 5:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 6:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 7:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 8:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 9:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 10:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 11:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 12:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 13:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 14:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 15:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 16:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 17:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 18:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 19:
         if(FilePath.coerceToFilePathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 20:
         if(FilePath.coerceToFilePathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 21:
         if(FilePath.coerceToFilePathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 22:
      case 23:
      case 27:
      case 28:
      case 29:
      default:
         return super.match1(var1, var2, var3);
      case 24:
         if(FilePath.coerceToFilePathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 25:
         if(FilePath.coerceToFilePathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 26:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 30:
         if(var2 instanceof CharSequence) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return -786431;
         }
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 22:
         if(FilePath.coerceToFilePathOrNull(var2) != null) {
            var4.value1 = var2;
            if(FilePath.coerceToFilePathOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 23:
         if(Path.coerceToPathOrNull(var2) != null) {
            var4.value1 = var2;
            if(Path.coerceToPathOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      case 29:
         if(Path.coerceToPathOrNull(var2) != null) {
            var4.value1 = var2;
            if(Path.coerceToPathOrNull(var3) != null) {
               var4.value2 = var3;
               var4.proc = var1;
               var4.pc = 2;
               return 0;
            }

            return -786430;
         }

         return -786431;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
