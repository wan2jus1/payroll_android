package kawa.lib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.F32Vector;
import gnu.lists.F64Vector;
import gnu.lists.LList;
import gnu.lists.S16Vector;
import gnu.lists.S32Vector;
import gnu.lists.S64Vector;
import gnu.lists.S8Vector;
import gnu.lists.U16Vector;
import gnu.lists.U32Vector;
import gnu.lists.U64Vector;
import gnu.lists.U8Vector;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;

public class uniform extends ModuleBody {

   public static final uniform $instance = new uniform();
   static final IntNum Lit0 = IntNum.make(0);
   static final SimpleSymbol Lit1 = (SimpleSymbol)(new SimpleSymbol("s8vector?")).readResolve();
   static final SimpleSymbol Lit10 = (SimpleSymbol)(new SimpleSymbol("make-u8vector")).readResolve();
   static final SimpleSymbol Lit11 = (SimpleSymbol)(new SimpleSymbol("u8vector")).readResolve();
   static final SimpleSymbol Lit12 = (SimpleSymbol)(new SimpleSymbol("u8vector-length")).readResolve();
   static final SimpleSymbol Lit13 = (SimpleSymbol)(new SimpleSymbol("u8vector-ref")).readResolve();
   static final SimpleSymbol Lit14 = (SimpleSymbol)(new SimpleSymbol("u8vector-set!")).readResolve();
   static final SimpleSymbol Lit15 = (SimpleSymbol)(new SimpleSymbol("u8vector->list")).readResolve();
   static final SimpleSymbol Lit16 = (SimpleSymbol)(new SimpleSymbol("list->u8vector")).readResolve();
   static final SimpleSymbol Lit17 = (SimpleSymbol)(new SimpleSymbol("s16vector?")).readResolve();
   static final SimpleSymbol Lit18 = (SimpleSymbol)(new SimpleSymbol("make-s16vector")).readResolve();
   static final SimpleSymbol Lit19 = (SimpleSymbol)(new SimpleSymbol("s16vector")).readResolve();
   static final SimpleSymbol Lit2 = (SimpleSymbol)(new SimpleSymbol("make-s8vector")).readResolve();
   static final SimpleSymbol Lit20 = (SimpleSymbol)(new SimpleSymbol("s16vector-length")).readResolve();
   static final SimpleSymbol Lit21 = (SimpleSymbol)(new SimpleSymbol("s16vector-ref")).readResolve();
   static final SimpleSymbol Lit22 = (SimpleSymbol)(new SimpleSymbol("s16vector-set!")).readResolve();
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("s16vector->list")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("list->s16vector")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("u16vector?")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("make-u16vector")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("u16vector")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("u16vector-length")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("u16vector-ref")).readResolve();
   static final SimpleSymbol Lit3 = (SimpleSymbol)(new SimpleSymbol("s8vector")).readResolve();
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("u16vector-set!")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("u16vector->list")).readResolve();
   static final SimpleSymbol Lit32 = (SimpleSymbol)(new SimpleSymbol("list->u16vector")).readResolve();
   static final SimpleSymbol Lit33 = (SimpleSymbol)(new SimpleSymbol("s32vector?")).readResolve();
   static final SimpleSymbol Lit34 = (SimpleSymbol)(new SimpleSymbol("make-s32vector")).readResolve();
   static final SimpleSymbol Lit35 = (SimpleSymbol)(new SimpleSymbol("s32vector")).readResolve();
   static final SimpleSymbol Lit36 = (SimpleSymbol)(new SimpleSymbol("s32vector-length")).readResolve();
   static final SimpleSymbol Lit37 = (SimpleSymbol)(new SimpleSymbol("s32vector-ref")).readResolve();
   static final SimpleSymbol Lit38 = (SimpleSymbol)(new SimpleSymbol("s32vector-set!")).readResolve();
   static final SimpleSymbol Lit39 = (SimpleSymbol)(new SimpleSymbol("s32vector->list")).readResolve();
   static final SimpleSymbol Lit4 = (SimpleSymbol)(new SimpleSymbol("s8vector-length")).readResolve();
   static final SimpleSymbol Lit40 = (SimpleSymbol)(new SimpleSymbol("list->s32vector")).readResolve();
   static final SimpleSymbol Lit41 = (SimpleSymbol)(new SimpleSymbol("u32vector?")).readResolve();
   static final SimpleSymbol Lit42 = (SimpleSymbol)(new SimpleSymbol("make-u32vector")).readResolve();
   static final SimpleSymbol Lit43 = (SimpleSymbol)(new SimpleSymbol("u32vector")).readResolve();
   static final SimpleSymbol Lit44 = (SimpleSymbol)(new SimpleSymbol("u32vector-length")).readResolve();
   static final SimpleSymbol Lit45 = (SimpleSymbol)(new SimpleSymbol("u32vector-ref")).readResolve();
   static final SimpleSymbol Lit46 = (SimpleSymbol)(new SimpleSymbol("u32vector-set!")).readResolve();
   static final SimpleSymbol Lit47 = (SimpleSymbol)(new SimpleSymbol("u32vector->list")).readResolve();
   static final SimpleSymbol Lit48 = (SimpleSymbol)(new SimpleSymbol("list->u32vector")).readResolve();
   static final SimpleSymbol Lit49 = (SimpleSymbol)(new SimpleSymbol("s64vector?")).readResolve();
   static final SimpleSymbol Lit5 = (SimpleSymbol)(new SimpleSymbol("s8vector-ref")).readResolve();
   static final SimpleSymbol Lit50 = (SimpleSymbol)(new SimpleSymbol("make-s64vector")).readResolve();
   static final SimpleSymbol Lit51 = (SimpleSymbol)(new SimpleSymbol("s64vector")).readResolve();
   static final SimpleSymbol Lit52 = (SimpleSymbol)(new SimpleSymbol("s64vector-length")).readResolve();
   static final SimpleSymbol Lit53 = (SimpleSymbol)(new SimpleSymbol("s64vector-ref")).readResolve();
   static final SimpleSymbol Lit54 = (SimpleSymbol)(new SimpleSymbol("s64vector-set!")).readResolve();
   static final SimpleSymbol Lit55 = (SimpleSymbol)(new SimpleSymbol("s64vector->list")).readResolve();
   static final SimpleSymbol Lit56 = (SimpleSymbol)(new SimpleSymbol("list->s64vector")).readResolve();
   static final SimpleSymbol Lit57 = (SimpleSymbol)(new SimpleSymbol("u64vector?")).readResolve();
   static final SimpleSymbol Lit58 = (SimpleSymbol)(new SimpleSymbol("make-u64vector")).readResolve();
   static final SimpleSymbol Lit59 = (SimpleSymbol)(new SimpleSymbol("u64vector")).readResolve();
   static final SimpleSymbol Lit6 = (SimpleSymbol)(new SimpleSymbol("s8vector-set!")).readResolve();
   static final SimpleSymbol Lit60 = (SimpleSymbol)(new SimpleSymbol("u64vector-length")).readResolve();
   static final SimpleSymbol Lit61 = (SimpleSymbol)(new SimpleSymbol("u64vector-ref")).readResolve();
   static final SimpleSymbol Lit62 = (SimpleSymbol)(new SimpleSymbol("u64vector-set!")).readResolve();
   static final SimpleSymbol Lit63 = (SimpleSymbol)(new SimpleSymbol("u64vector->list")).readResolve();
   static final SimpleSymbol Lit64 = (SimpleSymbol)(new SimpleSymbol("list->u64vector")).readResolve();
   static final SimpleSymbol Lit65 = (SimpleSymbol)(new SimpleSymbol("f32vector?")).readResolve();
   static final SimpleSymbol Lit66 = (SimpleSymbol)(new SimpleSymbol("make-f32vector")).readResolve();
   static final SimpleSymbol Lit67 = (SimpleSymbol)(new SimpleSymbol("f32vector")).readResolve();
   static final SimpleSymbol Lit68 = (SimpleSymbol)(new SimpleSymbol("f32vector-length")).readResolve();
   static final SimpleSymbol Lit69 = (SimpleSymbol)(new SimpleSymbol("f32vector-ref")).readResolve();
   static final SimpleSymbol Lit7 = (SimpleSymbol)(new SimpleSymbol("s8vector->list")).readResolve();
   static final SimpleSymbol Lit70 = (SimpleSymbol)(new SimpleSymbol("f32vector-set!")).readResolve();
   static final SimpleSymbol Lit71 = (SimpleSymbol)(new SimpleSymbol("f32vector->list")).readResolve();
   static final SimpleSymbol Lit72 = (SimpleSymbol)(new SimpleSymbol("list->f32vector")).readResolve();
   static final SimpleSymbol Lit73 = (SimpleSymbol)(new SimpleSymbol("f64vector?")).readResolve();
   static final SimpleSymbol Lit74 = (SimpleSymbol)(new SimpleSymbol("make-f64vector")).readResolve();
   static final SimpleSymbol Lit75 = (SimpleSymbol)(new SimpleSymbol("f64vector")).readResolve();
   static final SimpleSymbol Lit76 = (SimpleSymbol)(new SimpleSymbol("f64vector-length")).readResolve();
   static final SimpleSymbol Lit77 = (SimpleSymbol)(new SimpleSymbol("f64vector-ref")).readResolve();
   static final SimpleSymbol Lit78 = (SimpleSymbol)(new SimpleSymbol("f64vector-set!")).readResolve();
   static final SimpleSymbol Lit79 = (SimpleSymbol)(new SimpleSymbol("f64vector->list")).readResolve();
   static final SimpleSymbol Lit8 = (SimpleSymbol)(new SimpleSymbol("list->s8vector")).readResolve();
   static final SimpleSymbol Lit80 = (SimpleSymbol)(new SimpleSymbol("list->f64vector")).readResolve();
   static final SimpleSymbol Lit9 = (SimpleSymbol)(new SimpleSymbol("u8vector?")).readResolve();
   public static final ModuleMethod f32vector;
   public static final ModuleMethod f32vector$Mn$Grlist;
   public static final ModuleMethod f32vector$Mnlength;
   public static final ModuleMethod f32vector$Mnref;
   public static final ModuleMethod f32vector$Mnset$Ex;
   public static final ModuleMethod f32vector$Qu;
   public static final ModuleMethod f64vector;
   public static final ModuleMethod f64vector$Mn$Grlist;
   public static final ModuleMethod f64vector$Mnlength;
   public static final ModuleMethod f64vector$Mnref;
   public static final ModuleMethod f64vector$Mnset$Ex;
   public static final ModuleMethod f64vector$Qu;
   public static final ModuleMethod list$Mn$Grf32vector;
   public static final ModuleMethod list$Mn$Grf64vector;
   public static final ModuleMethod list$Mn$Grs16vector;
   public static final ModuleMethod list$Mn$Grs32vector;
   public static final ModuleMethod list$Mn$Grs64vector;
   public static final ModuleMethod list$Mn$Grs8vector;
   public static final ModuleMethod list$Mn$Gru16vector;
   public static final ModuleMethod list$Mn$Gru32vector;
   public static final ModuleMethod list$Mn$Gru64vector;
   public static final ModuleMethod list$Mn$Gru8vector;
   public static final ModuleMethod make$Mnf32vector;
   public static final ModuleMethod make$Mnf64vector;
   public static final ModuleMethod make$Mns16vector;
   public static final ModuleMethod make$Mns32vector;
   public static final ModuleMethod make$Mns64vector;
   public static final ModuleMethod make$Mns8vector;
   public static final ModuleMethod make$Mnu16vector;
   public static final ModuleMethod make$Mnu32vector;
   public static final ModuleMethod make$Mnu64vector;
   public static final ModuleMethod make$Mnu8vector;
   public static final ModuleMethod s16vector;
   public static final ModuleMethod s16vector$Mn$Grlist;
   public static final ModuleMethod s16vector$Mnlength;
   public static final ModuleMethod s16vector$Mnref;
   public static final ModuleMethod s16vector$Mnset$Ex;
   public static final ModuleMethod s16vector$Qu;
   public static final ModuleMethod s32vector;
   public static final ModuleMethod s32vector$Mn$Grlist;
   public static final ModuleMethod s32vector$Mnlength;
   public static final ModuleMethod s32vector$Mnref;
   public static final ModuleMethod s32vector$Mnset$Ex;
   public static final ModuleMethod s32vector$Qu;
   public static final ModuleMethod s64vector;
   public static final ModuleMethod s64vector$Mn$Grlist;
   public static final ModuleMethod s64vector$Mnlength;
   public static final ModuleMethod s64vector$Mnref;
   public static final ModuleMethod s64vector$Mnset$Ex;
   public static final ModuleMethod s64vector$Qu;
   public static final ModuleMethod s8vector;
   public static final ModuleMethod s8vector$Mn$Grlist;
   public static final ModuleMethod s8vector$Mnlength;
   public static final ModuleMethod s8vector$Mnref;
   public static final ModuleMethod s8vector$Mnset$Ex;
   public static final ModuleMethod s8vector$Qu;
   public static final ModuleMethod u16vector;
   public static final ModuleMethod u16vector$Mn$Grlist;
   public static final ModuleMethod u16vector$Mnlength;
   public static final ModuleMethod u16vector$Mnref;
   public static final ModuleMethod u16vector$Mnset$Ex;
   public static final ModuleMethod u16vector$Qu;
   public static final ModuleMethod u32vector;
   public static final ModuleMethod u32vector$Mn$Grlist;
   public static final ModuleMethod u32vector$Mnlength;
   public static final ModuleMethod u32vector$Mnref;
   public static final ModuleMethod u32vector$Mnset$Ex;
   public static final ModuleMethod u32vector$Qu;
   public static final ModuleMethod u64vector;
   public static final ModuleMethod u64vector$Mn$Grlist;
   public static final ModuleMethod u64vector$Mnlength;
   public static final ModuleMethod u64vector$Mnref;
   public static final ModuleMethod u64vector$Mnset$Ex;
   public static final ModuleMethod u64vector$Qu;
   public static final ModuleMethod u8vector;
   public static final ModuleMethod u8vector$Mn$Grlist;
   public static final ModuleMethod u8vector$Mnlength;
   public static final ModuleMethod u8vector$Mnref;
   public static final ModuleMethod u8vector$Mnset$Ex;
   public static final ModuleMethod u8vector$Qu;


   static {
      uniform var0 = $instance;
      s8vector$Qu = new ModuleMethod(var0, 1, Lit1, 4097);
      make$Mns8vector = new ModuleMethod(var0, 2, Lit2, 8193);
      s8vector = new ModuleMethod(var0, 4, Lit3, -4096);
      s8vector$Mnlength = new ModuleMethod(var0, 5, Lit4, 4097);
      s8vector$Mnref = new ModuleMethod(var0, 6, Lit5, 8194);
      s8vector$Mnset$Ex = new ModuleMethod(var0, 7, Lit6, 12291);
      s8vector$Mn$Grlist = new ModuleMethod(var0, 8, Lit7, 4097);
      list$Mn$Grs8vector = new ModuleMethod(var0, 9, Lit8, 4097);
      u8vector$Qu = new ModuleMethod(var0, 10, Lit9, 4097);
      make$Mnu8vector = new ModuleMethod(var0, 11, Lit10, 8193);
      u8vector = new ModuleMethod(var0, 13, Lit11, -4096);
      u8vector$Mnlength = new ModuleMethod(var0, 14, Lit12, 4097);
      u8vector$Mnref = new ModuleMethod(var0, 15, Lit13, 8194);
      u8vector$Mnset$Ex = new ModuleMethod(var0, 16, Lit14, 12291);
      u8vector$Mn$Grlist = new ModuleMethod(var0, 17, Lit15, 4097);
      list$Mn$Gru8vector = new ModuleMethod(var0, 18, Lit16, 4097);
      s16vector$Qu = new ModuleMethod(var0, 19, Lit17, 4097);
      make$Mns16vector = new ModuleMethod(var0, 20, Lit18, 8193);
      s16vector = new ModuleMethod(var0, 22, Lit19, -4096);
      s16vector$Mnlength = new ModuleMethod(var0, 23, Lit20, 4097);
      s16vector$Mnref = new ModuleMethod(var0, 24, Lit21, 8194);
      s16vector$Mnset$Ex = new ModuleMethod(var0, 25, Lit22, 12291);
      s16vector$Mn$Grlist = new ModuleMethod(var0, 26, Lit23, 4097);
      list$Mn$Grs16vector = new ModuleMethod(var0, 27, Lit24, 4097);
      u16vector$Qu = new ModuleMethod(var0, 28, Lit25, 4097);
      make$Mnu16vector = new ModuleMethod(var0, 29, Lit26, 8193);
      u16vector = new ModuleMethod(var0, 31, Lit27, -4096);
      u16vector$Mnlength = new ModuleMethod(var0, 32, Lit28, 4097);
      u16vector$Mnref = new ModuleMethod(var0, 33, Lit29, 8194);
      u16vector$Mnset$Ex = new ModuleMethod(var0, 34, Lit30, 12291);
      u16vector$Mn$Grlist = new ModuleMethod(var0, 35, Lit31, 4097);
      list$Mn$Gru16vector = new ModuleMethod(var0, 36, Lit32, 4097);
      s32vector$Qu = new ModuleMethod(var0, 37, Lit33, 4097);
      make$Mns32vector = new ModuleMethod(var0, 38, Lit34, 8193);
      s32vector = new ModuleMethod(var0, 40, Lit35, -4096);
      s32vector$Mnlength = new ModuleMethod(var0, 41, Lit36, 4097);
      s32vector$Mnref = new ModuleMethod(var0, 42, Lit37, 8194);
      s32vector$Mnset$Ex = new ModuleMethod(var0, 43, Lit38, 12291);
      s32vector$Mn$Grlist = new ModuleMethod(var0, 44, Lit39, 4097);
      list$Mn$Grs32vector = new ModuleMethod(var0, 45, Lit40, 4097);
      u32vector$Qu = new ModuleMethod(var0, 46, Lit41, 4097);
      make$Mnu32vector = new ModuleMethod(var0, 47, Lit42, 8193);
      u32vector = new ModuleMethod(var0, 49, Lit43, -4096);
      u32vector$Mnlength = new ModuleMethod(var0, 50, Lit44, 4097);
      u32vector$Mnref = new ModuleMethod(var0, 51, Lit45, 8194);
      u32vector$Mnset$Ex = new ModuleMethod(var0, 52, Lit46, 12291);
      u32vector$Mn$Grlist = new ModuleMethod(var0, 53, Lit47, 4097);
      list$Mn$Gru32vector = new ModuleMethod(var0, 54, Lit48, 4097);
      s64vector$Qu = new ModuleMethod(var0, 55, Lit49, 4097);
      make$Mns64vector = new ModuleMethod(var0, 56, Lit50, 8193);
      s64vector = new ModuleMethod(var0, 58, Lit51, -4096);
      s64vector$Mnlength = new ModuleMethod(var0, 59, Lit52, 4097);
      s64vector$Mnref = new ModuleMethod(var0, 60, Lit53, 8194);
      s64vector$Mnset$Ex = new ModuleMethod(var0, 61, Lit54, 12291);
      s64vector$Mn$Grlist = new ModuleMethod(var0, 62, Lit55, 4097);
      list$Mn$Grs64vector = new ModuleMethod(var0, 63, Lit56, 4097);
      u64vector$Qu = new ModuleMethod(var0, 64, Lit57, 4097);
      make$Mnu64vector = new ModuleMethod(var0, 65, Lit58, 8193);
      u64vector = new ModuleMethod(var0, 67, Lit59, -4096);
      u64vector$Mnlength = new ModuleMethod(var0, 68, Lit60, 4097);
      u64vector$Mnref = new ModuleMethod(var0, 69, Lit61, 8194);
      u64vector$Mnset$Ex = new ModuleMethod(var0, 70, Lit62, 12291);
      u64vector$Mn$Grlist = new ModuleMethod(var0, 71, Lit63, 4097);
      list$Mn$Gru64vector = new ModuleMethod(var0, 72, Lit64, 4097);
      f32vector$Qu = new ModuleMethod(var0, 73, Lit65, 4097);
      make$Mnf32vector = new ModuleMethod(var0, 74, Lit66, 8193);
      f32vector = new ModuleMethod(var0, 76, Lit67, -4096);
      f32vector$Mnlength = new ModuleMethod(var0, 77, Lit68, 4097);
      f32vector$Mnref = new ModuleMethod(var0, 78, Lit69, 8194);
      f32vector$Mnset$Ex = new ModuleMethod(var0, 79, Lit70, 12291);
      f32vector$Mn$Grlist = new ModuleMethod(var0, 80, Lit71, 4097);
      list$Mn$Grf32vector = new ModuleMethod(var0, 81, Lit72, 4097);
      f64vector$Qu = new ModuleMethod(var0, 82, Lit73, 4097);
      make$Mnf64vector = new ModuleMethod(var0, 83, Lit74, 8193);
      f64vector = new ModuleMethod(var0, 85, Lit75, -4096);
      f64vector$Mnlength = new ModuleMethod(var0, 86, Lit76, 4097);
      f64vector$Mnref = new ModuleMethod(var0, 87, Lit77, 8194);
      f64vector$Mnset$Ex = new ModuleMethod(var0, 88, Lit78, 12291);
      f64vector$Mn$Grlist = new ModuleMethod(var0, 89, Lit79, 4097);
      list$Mn$Grf64vector = new ModuleMethod(var0, 90, Lit80, 4097);
      $instance.run();
   }

   public uniform() {
      ModuleInfo.register(this);
   }

   public static LList f32vector$To$List(F32Vector var0) {
      return LList.makeList(var0);
   }

   public static F32Vector f32vector$V(Object[] var0) {
      return list$To$F32vector(LList.makeList(var0, 0));
   }

   public static int f32vectorLength(F32Vector var0) {
      return var0.size();
   }

   public static float f32vectorRef(F32Vector var0, int var1) {
      return var0.floatAt(var1);
   }

   public static void f32vectorSet$Ex(F32Vector var0, int var1, float var2) {
      var0.setFloatAt(var1, var2);
   }

   public static LList f64vector$To$List(F64Vector var0) {
      return LList.makeList(var0);
   }

   public static F64Vector f64vector$V(Object[] var0) {
      return list$To$F64vector(LList.makeList(var0, 0));
   }

   public static int f64vectorLength(F64Vector var0) {
      return var0.size();
   }

   public static double f64vectorRef(F64Vector var0, int var1) {
      return var0.doubleAt(var1);
   }

   public static void f64vectorSet$Ex(F64Vector var0, int var1, double var2) {
      var0.setDoubleAt(var1, var2);
   }

   public static boolean isF32vector(Object var0) {
      return var0 instanceof F32Vector;
   }

   public static boolean isF64vector(Object var0) {
      return var0 instanceof F64Vector;
   }

   public static boolean isS16vector(Object var0) {
      return var0 instanceof S16Vector;
   }

   public static boolean isS32vector(Object var0) {
      return var0 instanceof S32Vector;
   }

   public static boolean isS64vector(Object var0) {
      return var0 instanceof S64Vector;
   }

   public static boolean isS8vector(Object var0) {
      return var0 instanceof S8Vector;
   }

   public static boolean isU16vector(Object var0) {
      return var0 instanceof U16Vector;
   }

   public static boolean isU32vector(Object var0) {
      return var0 instanceof U32Vector;
   }

   public static boolean isU64vector(Object var0) {
      return var0 instanceof U64Vector;
   }

   public static boolean isU8vector(Object var0) {
      return var0 instanceof U8Vector;
   }

   public static F32Vector list$To$F32vector(LList var0) {
      return new F32Vector(var0);
   }

   public static F64Vector list$To$F64vector(LList var0) {
      return new F64Vector(var0);
   }

   public static S16Vector list$To$S16vector(LList var0) {
      return new S16Vector(var0);
   }

   public static S32Vector list$To$S32vector(LList var0) {
      return new S32Vector(var0);
   }

   public static S64Vector list$To$S64vector(LList var0) {
      return new S64Vector(var0);
   }

   public static S8Vector list$To$S8vector(LList var0) {
      return new S8Vector(var0);
   }

   public static U16Vector list$To$U16vector(LList var0) {
      return new U16Vector(var0);
   }

   public static U32Vector list$To$U32vector(LList var0) {
      return new U32Vector(var0);
   }

   public static U64Vector list$To$U64vector(LList var0) {
      return new U64Vector(var0);
   }

   public static U8Vector list$To$U8vector(LList var0) {
      return new U8Vector(var0);
   }

   public static F32Vector makeF32vector(int var0) {
      return makeF32vector(var0, 0.0F);
   }

   public static F32Vector makeF32vector(int var0, float var1) {
      return new F32Vector(var0, var1);
   }

   public static F64Vector makeF64vector(int var0) {
      return makeF64vector(var0, 0.0D);
   }

   public static F64Vector makeF64vector(int var0, double var1) {
      return new F64Vector(var0, var1);
   }

   public static S16Vector makeS16vector(int var0) {
      return makeS16vector(var0, 0);
   }

   public static S16Vector makeS16vector(int var0, int var1) {
      return new S16Vector(var0, (short)var1);
   }

   public static S32Vector makeS32vector(int var0) {
      return makeS32vector(var0, 0);
   }

   public static S32Vector makeS32vector(int var0, int var1) {
      return new S32Vector(var0, var1);
   }

   public static S64Vector makeS64vector(int var0) {
      return makeS64vector(var0, 0L);
   }

   public static S64Vector makeS64vector(int var0, long var1) {
      return new S64Vector(var0, var1);
   }

   public static S8Vector makeS8vector(int var0) {
      return makeS8vector(var0, 0);
   }

   public static S8Vector makeS8vector(int var0, int var1) {
      return new S8Vector(var0, (byte)var1);
   }

   public static U16Vector makeU16vector(int var0) {
      return makeU16vector(var0, 0);
   }

   public static U16Vector makeU16vector(int var0, int var1) {
      return new U16Vector(var0, (short)var1);
   }

   public static U32Vector makeU32vector(int var0) {
      return makeU32vector(var0, 0L);
   }

   public static U32Vector makeU32vector(int var0, long var1) {
      return new U32Vector(var0, (int)var1);
   }

   public static U64Vector makeU64vector(int var0) {
      return makeU64vector(var0, Lit0);
   }

   public static U64Vector makeU64vector(int var0, IntNum var1) {
      long var3;
      try {
         var3 = var1.longValue();
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "gnu.lists.U64Vector.<init>(int,long)", 2, var1);
      }

      return new U64Vector(var0, var3);
   }

   public static U8Vector makeU8vector(int var0) {
      return makeU8vector(var0, 0);
   }

   public static U8Vector makeU8vector(int var0, int var1) {
      return new U8Vector(var0, (byte)var1);
   }

   public static LList s16vector$To$List(S16Vector var0) {
      return LList.makeList(var0);
   }

   public static S16Vector s16vector$V(Object[] var0) {
      return list$To$S16vector(LList.makeList(var0, 0));
   }

   public static int s16vectorLength(S16Vector var0) {
      return var0.size();
   }

   public static int s16vectorRef(S16Vector var0, int var1) {
      return var0.intAt(var1);
   }

   public static void s16vectorSet$Ex(S16Vector var0, int var1, int var2) {
      var0.setShortAt(var1, (short)var2);
   }

   public static LList s32vector$To$List(S32Vector var0) {
      return LList.makeList(var0);
   }

   public static S32Vector s32vector$V(Object[] var0) {
      return list$To$S32vector(LList.makeList(var0, 0));
   }

   public static int s32vectorLength(S32Vector var0) {
      return var0.size();
   }

   public static int s32vectorRef(S32Vector var0, int var1) {
      return var0.intAt(var1);
   }

   public static void s32vectorSet$Ex(S32Vector var0, int var1, int var2) {
      var0.setIntAt(var1, var2);
   }

   public static LList s64vector$To$List(S64Vector var0) {
      return LList.makeList(var0);
   }

   public static S64Vector s64vector$V(Object[] var0) {
      return list$To$S64vector(LList.makeList(var0, 0));
   }

   public static int s64vectorLength(S64Vector var0) {
      return var0.size();
   }

   public static long s64vectorRef(S64Vector var0, int var1) {
      return var0.longAt(var1);
   }

   public static void s64vectorSet$Ex(S64Vector var0, int var1, long var2) {
      var0.setLongAt(var1, var2);
   }

   public static LList s8vector$To$List(S8Vector var0) {
      return LList.makeList(var0);
   }

   public static S8Vector s8vector$V(Object[] var0) {
      return list$To$S8vector(LList.makeList(var0, 0));
   }

   public static int s8vectorLength(S8Vector var0) {
      return var0.size();
   }

   public static int s8vectorRef(S8Vector var0, int var1) {
      return var0.intAt(var1);
   }

   public static void s8vectorSet$Ex(S8Vector var0, int var1, int var2) {
      var0.setByteAt(var1, (byte)var2);
   }

   public static LList u16vector$To$List(U16Vector var0) {
      return LList.makeList(var0);
   }

   public static U16Vector u16vector$V(Object[] var0) {
      return list$To$U16vector(LList.makeList(var0, 0));
   }

   public static int u16vectorLength(U16Vector var0) {
      return var0.size();
   }

   public static int u16vectorRef(U16Vector var0, int var1) {
      return var0.intAt(var1);
   }

   public static void u16vectorSet$Ex(U16Vector var0, int var1, int var2) {
      var0.setShortAt(var1, (short)var2);
   }

   public static LList u32vector$To$List(U32Vector var0) {
      return LList.makeList(var0);
   }

   public static U32Vector u32vector$V(Object[] var0) {
      return list$To$U32vector(LList.makeList(var0, 0));
   }

   public static int u32vectorLength(U32Vector var0) {
      return var0.size();
   }

   public static long u32vectorRef(U32Vector var0, int var1) {
      return ((Number)var0.get(var1)).longValue();
   }

   public static void u32vectorSet$Ex(U32Vector var0, int var1, long var2) {
      var0.setIntAt(var1, (int)var2);
   }

   public static LList u64vector$To$List(U64Vector var0) {
      return LList.makeList(var0);
   }

   public static U64Vector u64vector$V(Object[] var0) {
      return list$To$U64vector(LList.makeList(var0, 0));
   }

   public static int u64vectorLength(U64Vector var0) {
      return var0.size();
   }

   public static IntNum u64vectorRef(U64Vector var0, int var1) {
      return LangObjType.coerceIntNum(var0.get(var1));
   }

   public static void u64vectorSet$Ex(U64Vector var0, int var1, IntNum var2) {
      long var3;
      try {
         var3 = var2.longValue();
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "gnu.lists.U64Vector.setLongAt(int,long)", 3, var2);
      }

      var0.setLongAt(var1, var3);
   }

   public static LList u8vector$To$List(U8Vector var0) {
      return LList.makeList(var0);
   }

   public static U8Vector u8vector$V(Object[] var0) {
      return list$To$U8vector(LList.makeList(var0, 0));
   }

   public static int u8vectorLength(U8Vector var0) {
      return var0.size();
   }

   public static int u8vectorRef(U8Vector var0, int var1) {
      return var0.intAt(var1);
   }

   public static void u8vectorSet$Ex(U8Vector var0, int var1, int var2) {
      var0.setByteAt(var1, (byte)var2);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      int var3;
      F32Vector var46;
      U64Vector var47;
      LList var44;
      F64Vector var45;
      U16Vector var51;
      S32Vector var50;
      U32Vector var49;
      S64Vector var48;
      S8Vector var54;
      U8Vector var53;
      S16Vector var52;
      switch(var1.selector) {
      case 1:
         if(isS8vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 2:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var43) {
            throw new WrongType(var43, "make-s8vector", 1, var2);
         }

         return makeS8vector(var3);
      case 3:
      case 4:
      case 6:
      case 7:
      case 12:
      case 13:
      case 15:
      case 16:
      case 21:
      case 22:
      case 24:
      case 25:
      case 30:
      case 31:
      case 33:
      case 34:
      case 39:
      case 40:
      case 42:
      case 43:
      case 48:
      case 49:
      case 51:
      case 52:
      case 57:
      case 58:
      case 60:
      case 61:
      case 66:
      case 67:
      case 69:
      case 70:
      case 75:
      case 76:
      case 78:
      case 79:
      case 84:
      case 85:
      case 87:
      case 88:
      default:
         return super.apply1(var1, var2);
      case 5:
         try {
            var54 = (S8Vector)var2;
         } catch (ClassCastException var42) {
            throw new WrongType(var42, "s8vector-length", 1, var2);
         }

         return Integer.valueOf(s8vectorLength(var54));
      case 8:
         try {
            var54 = (S8Vector)var2;
         } catch (ClassCastException var41) {
            throw new WrongType(var41, "s8vector->list", 1, var2);
         }

         return s8vector$To$List(var54);
      case 9:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var40) {
            throw new WrongType(var40, "list->s8vector", 1, var2);
         }

         return list$To$S8vector(var44);
      case 10:
         if(isU8vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 11:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var39) {
            throw new WrongType(var39, "make-u8vector", 1, var2);
         }

         return makeU8vector(var3);
      case 14:
         try {
            var53 = (U8Vector)var2;
         } catch (ClassCastException var38) {
            throw new WrongType(var38, "u8vector-length", 1, var2);
         }

         return Integer.valueOf(u8vectorLength(var53));
      case 17:
         try {
            var53 = (U8Vector)var2;
         } catch (ClassCastException var37) {
            throw new WrongType(var37, "u8vector->list", 1, var2);
         }

         return u8vector$To$List(var53);
      case 18:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var36) {
            throw new WrongType(var36, "list->u8vector", 1, var2);
         }

         return list$To$U8vector(var44);
      case 19:
         if(isS16vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 20:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var35) {
            throw new WrongType(var35, "make-s16vector", 1, var2);
         }

         return makeS16vector(var3);
      case 23:
         try {
            var52 = (S16Vector)var2;
         } catch (ClassCastException var34) {
            throw new WrongType(var34, "s16vector-length", 1, var2);
         }

         return Integer.valueOf(s16vectorLength(var52));
      case 26:
         try {
            var52 = (S16Vector)var2;
         } catch (ClassCastException var33) {
            throw new WrongType(var33, "s16vector->list", 1, var2);
         }

         return s16vector$To$List(var52);
      case 27:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var32) {
            throw new WrongType(var32, "list->s16vector", 1, var2);
         }

         return list$To$S16vector(var44);
      case 28:
         if(isU16vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 29:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var31) {
            throw new WrongType(var31, "make-u16vector", 1, var2);
         }

         return makeU16vector(var3);
      case 32:
         try {
            var51 = (U16Vector)var2;
         } catch (ClassCastException var30) {
            throw new WrongType(var30, "u16vector-length", 1, var2);
         }

         return Integer.valueOf(u16vectorLength(var51));
      case 35:
         try {
            var51 = (U16Vector)var2;
         } catch (ClassCastException var29) {
            throw new WrongType(var29, "u16vector->list", 1, var2);
         }

         return u16vector$To$List(var51);
      case 36:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "list->u16vector", 1, var2);
         }

         return list$To$U16vector(var44);
      case 37:
         if(isS32vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 38:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "make-s32vector", 1, var2);
         }

         return makeS32vector(var3);
      case 41:
         try {
            var50 = (S32Vector)var2;
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "s32vector-length", 1, var2);
         }

         return Integer.valueOf(s32vectorLength(var50));
      case 44:
         try {
            var50 = (S32Vector)var2;
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "s32vector->list", 1, var2);
         }

         return s32vector$To$List(var50);
      case 45:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "list->s32vector", 1, var2);
         }

         return list$To$S32vector(var44);
      case 46:
         if(isU32vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 47:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "make-u32vector", 1, var2);
         }

         return makeU32vector(var3);
      case 50:
         try {
            var49 = (U32Vector)var2;
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "u32vector-length", 1, var2);
         }

         return Integer.valueOf(u32vectorLength(var49));
      case 53:
         try {
            var49 = (U32Vector)var2;
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "u32vector->list", 1, var2);
         }

         return u32vector$To$List(var49);
      case 54:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "list->u32vector", 1, var2);
         }

         return list$To$U32vector(var44);
      case 55:
         if(isS64vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 56:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "make-s64vector", 1, var2);
         }

         return makeS64vector(var3);
      case 59:
         try {
            var48 = (S64Vector)var2;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "s64vector-length", 1, var2);
         }

         return Integer.valueOf(s64vectorLength(var48));
      case 62:
         try {
            var48 = (S64Vector)var2;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "s64vector->list", 1, var2);
         }

         return s64vector$To$List(var48);
      case 63:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "list->s64vector", 1, var2);
         }

         return list$To$S64vector(var44);
      case 64:
         if(isU64vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 65:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "make-u64vector", 1, var2);
         }

         return makeU64vector(var3);
      case 68:
         try {
            var47 = (U64Vector)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "u64vector-length", 1, var2);
         }

         return Integer.valueOf(u64vectorLength(var47));
      case 71:
         try {
            var47 = (U64Vector)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "u64vector->list", 1, var2);
         }

         return u64vector$To$List(var47);
      case 72:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "list->u64vector", 1, var2);
         }

         return list$To$U64vector(var44);
      case 73:
         if(isF32vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 74:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "make-f32vector", 1, var2);
         }

         return makeF32vector(var3);
      case 77:
         try {
            var46 = (F32Vector)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "f32vector-length", 1, var2);
         }

         return Integer.valueOf(f32vectorLength(var46));
      case 80:
         try {
            var46 = (F32Vector)var2;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "f32vector->list", 1, var2);
         }

         return f32vector$To$List(var46);
      case 81:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "list->f32vector", 1, var2);
         }

         return list$To$F32vector(var44);
      case 82:
         if(isF64vector(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 83:
         try {
            var3 = ((Number)var2).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "make-f64vector", 1, var2);
         }

         return makeF64vector(var3);
      case 86:
         try {
            var45 = (F64Vector)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "f64vector-length", 1, var2);
         }

         return Integer.valueOf(f64vectorLength(var45));
      case 89:
         try {
            var45 = (F64Vector)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "f64vector->list", 1, var2);
         }

         return f64vector$To$List(var45);
      case 90:
         try {
            var44 = (LList)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "list->f64vector", 1, var2);
         }

         return list$To$F64vector(var44);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      int var7;
      int var8;
      long var9;
      switch(var1.selector) {
      case 2:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var50) {
            throw new WrongType(var50, "make-s8vector", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var49) {
            throw new WrongType(var49, "make-s8vector", 2, var3);
         }

         return makeS8vector(var7, var8);
      case 6:
         S8Vector var61;
         try {
            var61 = (S8Vector)var2;
         } catch (ClassCastException var48) {
            throw new WrongType(var48, "s8vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var47) {
            throw new WrongType(var47, "s8vector-ref", 2, var3);
         }

         return Integer.valueOf(s8vectorRef(var61, var7));
      case 11:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var46) {
            throw new WrongType(var46, "make-u8vector", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var45) {
            throw new WrongType(var45, "make-u8vector", 2, var3);
         }

         return makeU8vector(var7, var8);
      case 15:
         U8Vector var60;
         try {
            var60 = (U8Vector)var2;
         } catch (ClassCastException var44) {
            throw new WrongType(var44, "u8vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var43) {
            throw new WrongType(var43, "u8vector-ref", 2, var3);
         }

         return Integer.valueOf(u8vectorRef(var60, var7));
      case 20:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var42) {
            throw new WrongType(var42, "make-s16vector", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var41) {
            throw new WrongType(var41, "make-s16vector", 2, var3);
         }

         return makeS16vector(var7, var8);
      case 24:
         S16Vector var59;
         try {
            var59 = (S16Vector)var2;
         } catch (ClassCastException var40) {
            throw new WrongType(var40, "s16vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var39) {
            throw new WrongType(var39, "s16vector-ref", 2, var3);
         }

         return Integer.valueOf(s16vectorRef(var59, var7));
      case 29:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var38) {
            throw new WrongType(var38, "make-u16vector", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var37) {
            throw new WrongType(var37, "make-u16vector", 2, var3);
         }

         return makeU16vector(var7, var8);
      case 33:
         U16Vector var58;
         try {
            var58 = (U16Vector)var2;
         } catch (ClassCastException var36) {
            throw new WrongType(var36, "u16vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var35) {
            throw new WrongType(var35, "u16vector-ref", 2, var3);
         }

         return Integer.valueOf(u16vectorRef(var58, var7));
      case 38:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var34) {
            throw new WrongType(var34, "make-s32vector", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var33) {
            throw new WrongType(var33, "make-s32vector", 2, var3);
         }

         return makeS32vector(var7, var8);
      case 42:
         S32Vector var57;
         try {
            var57 = (S32Vector)var2;
         } catch (ClassCastException var32) {
            throw new WrongType(var32, "s32vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var31) {
            throw new WrongType(var31, "s32vector-ref", 2, var3);
         }

         return Integer.valueOf(s32vectorRef(var57, var7));
      case 47:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var30) {
            throw new WrongType(var30, "make-u32vector", 1, var2);
         }

         try {
            var9 = ((Number)var3).longValue();
         } catch (ClassCastException var29) {
            throw new WrongType(var29, "make-u32vector", 2, var3);
         }

         return makeU32vector(var7, var9);
      case 51:
         U32Vector var56;
         try {
            var56 = (U32Vector)var2;
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "u32vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "u32vector-ref", 2, var3);
         }

         return Long.valueOf(u32vectorRef(var56, var7));
      case 56:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "make-s64vector", 1, var2);
         }

         try {
            var9 = ((Number)var3).longValue();
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "make-s64vector", 2, var3);
         }

         return makeS64vector(var7, var9);
      case 60:
         S64Vector var55;
         try {
            var55 = (S64Vector)var2;
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "s64vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "s64vector-ref", 2, var3);
         }

         return Long.valueOf(s64vectorRef(var55, var7));
      case 65:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "make-u64vector", 1, var2);
         }

         IntNum var54;
         try {
            var54 = LangObjType.coerceIntNum(var3);
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "make-u64vector", 2, var3);
         }

         return makeU64vector(var7, var54);
      case 69:
         U64Vector var53;
         try {
            var53 = (U64Vector)var2;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "u64vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "u64vector-ref", 2, var3);
         }

         return u64vectorRef(var53, var7);
      case 74:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "make-f32vector", 1, var2);
         }

         float var6;
         try {
            var6 = ((Number)var3).floatValue();
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "make-f32vector", 2, var3);
         }

         return makeF32vector(var7, var6);
      case 78:
         F32Vector var52;
         try {
            var52 = (F32Vector)var2;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "f32vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "f32vector-ref", 2, var3);
         }

         return Float.valueOf(f32vectorRef(var52, var7));
      case 83:
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "make-f64vector", 1, var2);
         }

         double var4;
         try {
            var4 = ((Number)var3).doubleValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "make-f64vector", 2, var3);
         }

         return makeF64vector(var7, var4);
      case 87:
         F64Vector var51;
         try {
            var51 = (F64Vector)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "f64vector-ref", 1, var2);
         }

         try {
            var7 = ((Number)var3).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "f64vector-ref", 2, var3);
         }

         return Double.valueOf(f64vectorRef(var51, var7));
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      int var8;
      int var9;
      long var10;
      switch(var1.selector) {
      case 7:
         S8Vector var52;
         try {
            var52 = (S8Vector)var2;
         } catch (ClassCastException var41) {
            throw new WrongType(var41, "s8vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var40) {
            throw new WrongType(var40, "s8vector-set!", 2, var3);
         }

         try {
            var9 = ((Number)var4).intValue();
         } catch (ClassCastException var39) {
            throw new WrongType(var39, "s8vector-set!", 3, var4);
         }

         s8vectorSet$Ex(var52, var8, var9);
         return Values.empty;
      case 16:
         U8Vector var51;
         try {
            var51 = (U8Vector)var2;
         } catch (ClassCastException var38) {
            throw new WrongType(var38, "u8vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var37) {
            throw new WrongType(var37, "u8vector-set!", 2, var3);
         }

         try {
            var9 = ((Number)var4).intValue();
         } catch (ClassCastException var36) {
            throw new WrongType(var36, "u8vector-set!", 3, var4);
         }

         u8vectorSet$Ex(var51, var8, var9);
         return Values.empty;
      case 25:
         S16Vector var50;
         try {
            var50 = (S16Vector)var2;
         } catch (ClassCastException var35) {
            throw new WrongType(var35, "s16vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var34) {
            throw new WrongType(var34, "s16vector-set!", 2, var3);
         }

         try {
            var9 = ((Number)var4).intValue();
         } catch (ClassCastException var33) {
            throw new WrongType(var33, "s16vector-set!", 3, var4);
         }

         s16vectorSet$Ex(var50, var8, var9);
         return Values.empty;
      case 34:
         U16Vector var49;
         try {
            var49 = (U16Vector)var2;
         } catch (ClassCastException var32) {
            throw new WrongType(var32, "u16vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var31) {
            throw new WrongType(var31, "u16vector-set!", 2, var3);
         }

         try {
            var9 = ((Number)var4).intValue();
         } catch (ClassCastException var30) {
            throw new WrongType(var30, "u16vector-set!", 3, var4);
         }

         u16vectorSet$Ex(var49, var8, var9);
         return Values.empty;
      case 43:
         S32Vector var48;
         try {
            var48 = (S32Vector)var2;
         } catch (ClassCastException var29) {
            throw new WrongType(var29, "s32vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "s32vector-set!", 2, var3);
         }

         try {
            var9 = ((Number)var4).intValue();
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "s32vector-set!", 3, var4);
         }

         s32vectorSet$Ex(var48, var8, var9);
         return Values.empty;
      case 52:
         U32Vector var47;
         try {
            var47 = (U32Vector)var2;
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "u32vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "u32vector-set!", 2, var3);
         }

         try {
            var10 = ((Number)var4).longValue();
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "u32vector-set!", 3, var4);
         }

         u32vectorSet$Ex(var47, var8, var10);
         return Values.empty;
      case 61:
         S64Vector var45;
         try {
            var45 = (S64Vector)var2;
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "s64vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "s64vector-set!", 2, var3);
         }

         try {
            var10 = ((Number)var4).longValue();
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "s64vector-set!", 3, var4);
         }

         s64vectorSet$Ex(var45, var8, var10);
         return Values.empty;
      case 70:
         U64Vector var44;
         try {
            var44 = (U64Vector)var2;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "u64vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "u64vector-set!", 2, var3);
         }

         IntNum var46;
         try {
            var46 = LangObjType.coerceIntNum(var4);
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "u64vector-set!", 3, var4);
         }

         u64vectorSet$Ex(var44, var8, var46);
         return Values.empty;
      case 79:
         F32Vector var43;
         try {
            var43 = (F32Vector)var2;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "f32vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "f32vector-set!", 2, var3);
         }

         float var7;
         try {
            var7 = ((Number)var4).floatValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "f32vector-set!", 3, var4);
         }

         f32vectorSet$Ex(var43, var8, var7);
         return Values.empty;
      case 88:
         F64Vector var42;
         try {
            var42 = (F64Vector)var2;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "f64vector-set!", 1, var2);
         }

         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "f64vector-set!", 2, var3);
         }

         double var5;
         try {
            var5 = ((Number)var4).doubleValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "f64vector-set!", 3, var4);
         }

         f64vectorSet$Ex(var42, var8, var5);
         return Values.empty;
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      switch(var1.selector) {
      case 4:
         return s8vector$V(var2);
      case 13:
         return u8vector$V(var2);
      case 22:
         return s16vector$V(var2);
      case 31:
         return u16vector$V(var2);
      case 40:
         return s32vector$V(var2);
      case 49:
         return u32vector$V(var2);
      case 58:
         return s64vector$V(var2);
      case 67:
         return u64vector$V(var2);
      case 76:
         return f32vector$V(var2);
      case 85:
         return f64vector$V(var2);
      default:
         return super.applyN(var1, var2);
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
      case 4:
      case 6:
      case 7:
      case 12:
      case 13:
      case 15:
      case 16:
      case 21:
      case 22:
      case 24:
      case 25:
      case 30:
      case 31:
      case 33:
      case 34:
      case 39:
      case 40:
      case 42:
      case 43:
      case 48:
      case 49:
      case 51:
      case 52:
      case 57:
      case 58:
      case 60:
      case 61:
      case 66:
      case 67:
      case 69:
      case 70:
      case 75:
      case 76:
      case 78:
      case 79:
      case 84:
      case 85:
      case 87:
      case 88:
      default:
         return super.match1(var1, var2, var3);
      case 5:
         if(!(var2 instanceof S8Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 8:
         if(!(var2 instanceof S8Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 9:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 10:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 11:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 14:
         if(!(var2 instanceof U8Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 17:
         if(!(var2 instanceof U8Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 18:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 19:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 20:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 23:
         if(!(var2 instanceof S16Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 26:
         if(!(var2 instanceof S16Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 27:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 28:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 29:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 32:
         if(!(var2 instanceof U16Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 35:
         if(!(var2 instanceof U16Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 36:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 37:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 38:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 41:
         if(!(var2 instanceof S32Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 44:
         if(!(var2 instanceof S32Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 45:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 46:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 47:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 50:
         if(!(var2 instanceof U32Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 53:
         if(!(var2 instanceof U32Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 54:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 55:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 56:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 59:
         if(!(var2 instanceof S64Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 62:
         if(!(var2 instanceof S64Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 63:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 64:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 65:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 68:
         if(!(var2 instanceof U64Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 71:
         if(!(var2 instanceof U64Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 72:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 73:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 74:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 77:
         if(!(var2 instanceof F32Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 80:
         if(!(var2 instanceof F32Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 81:
         if(var2 instanceof LList) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }

         return -786431;
      case 82:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 83:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 86:
         if(!(var2 instanceof F64Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 89:
         if(!(var2 instanceof F64Vector)) {
            return -786431;
         }

         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 90:
         if(var2 instanceof LList) {
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
      int var5 = -786431;
      switch(var1.selector) {
      case 2:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 6:
         if(var2 instanceof S8Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 11:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 15:
         if(var2 instanceof U8Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 20:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 24:
         if(var2 instanceof S16Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 29:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 33:
         if(var2 instanceof U16Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 38:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 42:
         if(var2 instanceof S32Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 47:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 51:
         if(var2 instanceof U32Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 56:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 60:
         if(var2 instanceof S64Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 65:
         var4.value1 = var2;
         if(IntNum.asIntNumOrNull(var3) != null) {
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786430;
      case 69:
         if(var2 instanceof U64Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 74:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 78:
         if(var2 instanceof F32Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      case 83:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 87:
         if(var2 instanceof F64Vector) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
         break;
      default:
         var5 = super.match2(var1, var2, var3, var4);
      }

      return var5;
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      int var6 = -786431;
      switch(var1.selector) {
      case 7:
         if(var2 instanceof S8Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 16:
         if(var2 instanceof U8Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 25:
         if(var2 instanceof S16Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 34:
         if(var2 instanceof U16Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 43:
         if(var2 instanceof S32Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 52:
         if(var2 instanceof U32Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 61:
         if(var2 instanceof S64Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 70:
         if(var2 instanceof U64Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            if(IntNum.asIntNumOrNull(var4) != null) {
               var5.value3 = var4;
               var5.proc = var1;
               var5.pc = 3;
               return 0;
            }

            return -786429;
         }
         break;
      case 79:
         if(var2 instanceof F32Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      case 88:
         if(var2 instanceof F64Vector) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }
         break;
      default:
         var6 = super.match3(var1, var2, var3, var4, var5);
      }

      return var6;
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 4:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 13:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 22:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 31:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 40:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 49:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 58:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 67:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 76:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 85:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      default:
         return super.matchN(var1, var2, var3);
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
