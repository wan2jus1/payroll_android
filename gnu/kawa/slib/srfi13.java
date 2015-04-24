package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.BitwiseOp;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.Invoke;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.math.RealNum;
import gnu.text.Char;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.lib.rnrs.unicode;
import kawa.standard.Scheme;
import kawa.standard.call_with_values;

public class srfi13 extends ModuleBody {

   public static final ModuleMethod $Pccheck$Mnbounds;
   public static final ModuleMethod $Pcfinish$Mnstring$Mnconcatenate$Mnreverse;
   public static final ModuleMethod $Pckmp$Mnsearch;
   public static final ModuleMethod $Pcmultispan$Mnrepcopy$Ex;
   public static final ModuleMethod $Pcstring$Mncompare;
   public static final ModuleMethod $Pcstring$Mncompare$Mnci;
   public static final ModuleMethod $Pcstring$Mncopy$Ex;
   public static final ModuleMethod $Pcstring$Mnhash;
   public static final ModuleMethod $Pcstring$Mnmap;
   public static final ModuleMethod $Pcstring$Mnmap$Ex;
   public static final ModuleMethod $Pcstring$Mnprefix$Mnci$Qu;
   public static final ModuleMethod $Pcstring$Mnprefix$Mnlength;
   public static final ModuleMethod $Pcstring$Mnprefix$Mnlength$Mnci;
   public static final ModuleMethod $Pcstring$Mnprefix$Qu;
   public static final ModuleMethod $Pcstring$Mnsuffix$Mnci$Qu;
   public static final ModuleMethod $Pcstring$Mnsuffix$Mnlength;
   public static final ModuleMethod $Pcstring$Mnsuffix$Mnlength$Mnci;
   public static final ModuleMethod $Pcstring$Mnsuffix$Qu;
   public static final ModuleMethod $Pcstring$Mntitlecase$Ex;
   public static final ModuleMethod $Pcsubstring$Slshared;
   public static final srfi13 $instance;
   static final IntNum Lit0;
   static final IntNum Lit1;
   static final IntNum Lit10;
   static final SimpleSymbol Lit100 = (SimpleSymbol)(new SimpleSymbol("string-hash-ci")).readResolve();
   static final SimpleSymbol Lit101 = (SimpleSymbol)(new SimpleSymbol("string-upcase")).readResolve();
   static final SimpleSymbol Lit102 = (SimpleSymbol)(new SimpleSymbol("string-upcase!")).readResolve();
   static final SimpleSymbol Lit103 = (SimpleSymbol)(new SimpleSymbol("string-downcase")).readResolve();
   static final SimpleSymbol Lit104 = (SimpleSymbol)(new SimpleSymbol("string-downcase!")).readResolve();
   static final SimpleSymbol Lit105 = (SimpleSymbol)(new SimpleSymbol("%string-titlecase!")).readResolve();
   static final SimpleSymbol Lit106 = (SimpleSymbol)(new SimpleSymbol("string-titlecase!")).readResolve();
   static final SimpleSymbol Lit107 = (SimpleSymbol)(new SimpleSymbol("string-titlecase")).readResolve();
   static final SimpleSymbol Lit108 = (SimpleSymbol)(new SimpleSymbol("string-take")).readResolve();
   static final SimpleSymbol Lit109 = (SimpleSymbol)(new SimpleSymbol("string-take-right")).readResolve();
   static final SimpleSymbol Lit11;
   static final SimpleSymbol Lit110 = (SimpleSymbol)(new SimpleSymbol("string-drop")).readResolve();
   static final SimpleSymbol Lit111 = (SimpleSymbol)(new SimpleSymbol("string-drop-right")).readResolve();
   static final SimpleSymbol Lit112 = (SimpleSymbol)(new SimpleSymbol("string-trim")).readResolve();
   static final SimpleSymbol Lit113 = (SimpleSymbol)(new SimpleSymbol("string-trim-right")).readResolve();
   static final SimpleSymbol Lit114 = (SimpleSymbol)(new SimpleSymbol("string-trim-both")).readResolve();
   static final SimpleSymbol Lit115 = (SimpleSymbol)(new SimpleSymbol("string-pad-right")).readResolve();
   static final SimpleSymbol Lit116 = (SimpleSymbol)(new SimpleSymbol("string-pad")).readResolve();
   static final SimpleSymbol Lit117 = (SimpleSymbol)(new SimpleSymbol("string-delete")).readResolve();
   static final SimpleSymbol Lit118 = (SimpleSymbol)(new SimpleSymbol("string-filter")).readResolve();
   static final SimpleSymbol Lit119 = (SimpleSymbol)(new SimpleSymbol("string-index")).readResolve();
   static final Char Lit12;
   static final SimpleSymbol Lit120 = (SimpleSymbol)(new SimpleSymbol("string-index-right")).readResolve();
   static final SimpleSymbol Lit121 = (SimpleSymbol)(new SimpleSymbol("string-skip")).readResolve();
   static final SimpleSymbol Lit122 = (SimpleSymbol)(new SimpleSymbol("string-skip-right")).readResolve();
   static final SimpleSymbol Lit123 = (SimpleSymbol)(new SimpleSymbol("string-count")).readResolve();
   static final SimpleSymbol Lit124 = (SimpleSymbol)(new SimpleSymbol("string-fill!")).readResolve();
   static final SimpleSymbol Lit125 = (SimpleSymbol)(new SimpleSymbol("string-copy!")).readResolve();
   static final SimpleSymbol Lit126 = (SimpleSymbol)(new SimpleSymbol("%string-copy!")).readResolve();
   static final SimpleSymbol Lit127 = (SimpleSymbol)(new SimpleSymbol("string-contains")).readResolve();
   static final SimpleSymbol Lit128 = (SimpleSymbol)(new SimpleSymbol("string-contains-ci")).readResolve();
   static final SimpleSymbol Lit129 = (SimpleSymbol)(new SimpleSymbol("%kmp-search")).readResolve();
   static final IntNum Lit13;
   static final SimpleSymbol Lit130 = (SimpleSymbol)(new SimpleSymbol("make-kmp-restart-vector")).readResolve();
   static final SimpleSymbol Lit131 = (SimpleSymbol)(new SimpleSymbol("kmp-step")).readResolve();
   static final SimpleSymbol Lit132 = (SimpleSymbol)(new SimpleSymbol("string-kmp-partial-search")).readResolve();
   static final SimpleSymbol Lit133 = (SimpleSymbol)(new SimpleSymbol("string-null?")).readResolve();
   static final SimpleSymbol Lit134 = (SimpleSymbol)(new SimpleSymbol("string-reverse")).readResolve();
   static final SimpleSymbol Lit135 = (SimpleSymbol)(new SimpleSymbol("string-reverse!")).readResolve();
   static final SimpleSymbol Lit136 = (SimpleSymbol)(new SimpleSymbol("reverse-list->string")).readResolve();
   static final SimpleSymbol Lit137 = (SimpleSymbol)(new SimpleSymbol("string->list")).readResolve();
   static final SimpleSymbol Lit138 = (SimpleSymbol)(new SimpleSymbol("string-append/shared")).readResolve();
   static final SimpleSymbol Lit139 = (SimpleSymbol)(new SimpleSymbol("string-concatenate/shared")).readResolve();
   static final SimpleSymbol Lit14;
   static final SimpleSymbol Lit140 = (SimpleSymbol)(new SimpleSymbol("string-concatenate")).readResolve();
   static final SimpleSymbol Lit141 = (SimpleSymbol)(new SimpleSymbol("string-concatenate-reverse")).readResolve();
   static final SimpleSymbol Lit142 = (SimpleSymbol)(new SimpleSymbol("string-concatenate-reverse/shared")).readResolve();
   static final SimpleSymbol Lit143 = (SimpleSymbol)(new SimpleSymbol("%finish-string-concatenate-reverse")).readResolve();
   static final SimpleSymbol Lit144 = (SimpleSymbol)(new SimpleSymbol("string-replace")).readResolve();
   static final SimpleSymbol Lit145 = (SimpleSymbol)(new SimpleSymbol("string-tokenize")).readResolve();
   static final SimpleSymbol Lit146 = (SimpleSymbol)(new SimpleSymbol("xsubstring")).readResolve();
   static final SimpleSymbol Lit147 = (SimpleSymbol)(new SimpleSymbol("string-xcopy!")).readResolve();
   static final SimpleSymbol Lit148 = (SimpleSymbol)(new SimpleSymbol("%multispan-repcopy!")).readResolve();
   static final SimpleSymbol Lit149 = (SimpleSymbol)(new SimpleSymbol("string-join")).readResolve();
   static final SimpleSymbol Lit15;
   static final SimpleSymbol Lit150 = (SimpleSymbol)(new SimpleSymbol("receive")).readResolve();
   static final SimpleSymbol Lit16;
   static final SimpleSymbol Lit17;
   static final SimpleSymbol Lit18;
   static final SimpleSymbol Lit19;
   static final IntNum Lit2;
   static final SimpleSymbol Lit20;
   static final SimpleSymbol Lit21;
   static final SimpleSymbol Lit22;
   static final SimpleSymbol Lit23;
   static final SimpleSymbol Lit24;
   static final SimpleSymbol Lit25;
   static final SimpleSymbol Lit26;
   static final SimpleSymbol Lit27;
   static final SimpleSymbol Lit28;
   static final SimpleSymbol Lit29;
   static final IntNum Lit3;
   static final SimpleSymbol Lit30;
   static final SimpleSymbol Lit31;
   static final SimpleSymbol Lit32;
   static final SimpleSymbol Lit33;
   static final SimpleSymbol Lit34;
   static final SimpleSymbol Lit35;
   static final SimpleSymbol Lit36;
   static final SimpleSymbol Lit37;
   static final SimpleSymbol Lit38;
   static final SimpleSymbol Lit39;
   static final IntNum Lit4;
   static final SimpleSymbol Lit40;
   static final SimpleSymbol Lit41;
   static final SyntaxRules Lit42;
   static final SimpleSymbol Lit43;
   static final SyntaxRules Lit44;
   static final SimpleSymbol Lit45 = (SimpleSymbol)(new SimpleSymbol("string-parse-start+end")).readResolve();
   static final SimpleSymbol Lit46 = (SimpleSymbol)(new SimpleSymbol("%check-bounds")).readResolve();
   static final SimpleSymbol Lit47 = (SimpleSymbol)(new SimpleSymbol("string-parse-final-start+end")).readResolve();
   static final SimpleSymbol Lit48 = (SimpleSymbol)(new SimpleSymbol("substring-spec-ok?")).readResolve();
   static final SimpleSymbol Lit49 = (SimpleSymbol)(new SimpleSymbol("check-substring-spec")).readResolve();
   static final IntNum Lit5;
   static final SimpleSymbol Lit50 = (SimpleSymbol)(new SimpleSymbol("substring/shared")).readResolve();
   static final SimpleSymbol Lit51 = (SimpleSymbol)(new SimpleSymbol("%substring/shared")).readResolve();
   static final SimpleSymbol Lit52 = (SimpleSymbol)(new SimpleSymbol("string-copy")).readResolve();
   static final SimpleSymbol Lit53 = (SimpleSymbol)(new SimpleSymbol("string-map")).readResolve();
   static final SimpleSymbol Lit54 = (SimpleSymbol)(new SimpleSymbol("%string-map")).readResolve();
   static final SimpleSymbol Lit55 = (SimpleSymbol)(new SimpleSymbol("string-map!")).readResolve();
   static final SimpleSymbol Lit56 = (SimpleSymbol)(new SimpleSymbol("%string-map!")).readResolve();
   static final SimpleSymbol Lit57 = (SimpleSymbol)(new SimpleSymbol("string-fold")).readResolve();
   static final SimpleSymbol Lit58 = (SimpleSymbol)(new SimpleSymbol("string-fold-right")).readResolve();
   static final SimpleSymbol Lit59 = (SimpleSymbol)(new SimpleSymbol("string-unfold")).readResolve();
   static final IntNum Lit6;
   static final SimpleSymbol Lit60 = (SimpleSymbol)(new SimpleSymbol("string-unfold-right")).readResolve();
   static final SimpleSymbol Lit61 = (SimpleSymbol)(new SimpleSymbol("string-for-each")).readResolve();
   static final SimpleSymbol Lit62 = (SimpleSymbol)(new SimpleSymbol("string-for-each-index")).readResolve();
   static final SimpleSymbol Lit63 = (SimpleSymbol)(new SimpleSymbol("string-every")).readResolve();
   static final SimpleSymbol Lit64 = (SimpleSymbol)(new SimpleSymbol("string-any")).readResolve();
   static final SimpleSymbol Lit65 = (SimpleSymbol)(new SimpleSymbol("string-tabulate")).readResolve();
   static final SimpleSymbol Lit66 = (SimpleSymbol)(new SimpleSymbol("%string-prefix-length")).readResolve();
   static final SimpleSymbol Lit67 = (SimpleSymbol)(new SimpleSymbol("%string-suffix-length")).readResolve();
   static final SimpleSymbol Lit68 = (SimpleSymbol)(new SimpleSymbol("%string-prefix-length-ci")).readResolve();
   static final SimpleSymbol Lit69 = (SimpleSymbol)(new SimpleSymbol("%string-suffix-length-ci")).readResolve();
   static final IntNum Lit7;
   static final SimpleSymbol Lit70 = (SimpleSymbol)(new SimpleSymbol("string-prefix-length")).readResolve();
   static final SimpleSymbol Lit71 = (SimpleSymbol)(new SimpleSymbol("string-suffix-length")).readResolve();
   static final SimpleSymbol Lit72 = (SimpleSymbol)(new SimpleSymbol("string-prefix-length-ci")).readResolve();
   static final SimpleSymbol Lit73 = (SimpleSymbol)(new SimpleSymbol("string-suffix-length-ci")).readResolve();
   static final SimpleSymbol Lit74 = (SimpleSymbol)(new SimpleSymbol("string-prefix?")).readResolve();
   static final SimpleSymbol Lit75 = (SimpleSymbol)(new SimpleSymbol("string-suffix?")).readResolve();
   static final SimpleSymbol Lit76 = (SimpleSymbol)(new SimpleSymbol("string-prefix-ci?")).readResolve();
   static final SimpleSymbol Lit77 = (SimpleSymbol)(new SimpleSymbol("string-suffix-ci?")).readResolve();
   static final SimpleSymbol Lit78 = (SimpleSymbol)(new SimpleSymbol("%string-prefix?")).readResolve();
   static final SimpleSymbol Lit79 = (SimpleSymbol)(new SimpleSymbol("%string-suffix?")).readResolve();
   static final IntNum Lit8;
   static final SimpleSymbol Lit80 = (SimpleSymbol)(new SimpleSymbol("%string-prefix-ci?")).readResolve();
   static final SimpleSymbol Lit81 = (SimpleSymbol)(new SimpleSymbol("%string-suffix-ci?")).readResolve();
   static final SimpleSymbol Lit82 = (SimpleSymbol)(new SimpleSymbol("%string-compare")).readResolve();
   static final SimpleSymbol Lit83 = (SimpleSymbol)(new SimpleSymbol("%string-compare-ci")).readResolve();
   static final SimpleSymbol Lit84 = (SimpleSymbol)(new SimpleSymbol("string-compare")).readResolve();
   static final SimpleSymbol Lit85 = (SimpleSymbol)(new SimpleSymbol("string-compare-ci")).readResolve();
   static final SimpleSymbol Lit86 = (SimpleSymbol)(new SimpleSymbol("string=")).readResolve();
   static final SimpleSymbol Lit87 = (SimpleSymbol)(new SimpleSymbol("string<>")).readResolve();
   static final SimpleSymbol Lit88 = (SimpleSymbol)(new SimpleSymbol("string<")).readResolve();
   static final SimpleSymbol Lit89 = (SimpleSymbol)(new SimpleSymbol("string>")).readResolve();
   static final IntNum Lit9;
   static final SimpleSymbol Lit90 = (SimpleSymbol)(new SimpleSymbol("string<=")).readResolve();
   static final SimpleSymbol Lit91 = (SimpleSymbol)(new SimpleSymbol("string>=")).readResolve();
   static final SimpleSymbol Lit92 = (SimpleSymbol)(new SimpleSymbol("string-ci=")).readResolve();
   static final SimpleSymbol Lit93 = (SimpleSymbol)(new SimpleSymbol("string-ci<>")).readResolve();
   static final SimpleSymbol Lit94 = (SimpleSymbol)(new SimpleSymbol("string-ci<")).readResolve();
   static final SimpleSymbol Lit95 = (SimpleSymbol)(new SimpleSymbol("string-ci>")).readResolve();
   static final SimpleSymbol Lit96 = (SimpleSymbol)(new SimpleSymbol("string-ci<=")).readResolve();
   static final SimpleSymbol Lit97 = (SimpleSymbol)(new SimpleSymbol("string-ci>=")).readResolve();
   static final SimpleSymbol Lit98 = (SimpleSymbol)(new SimpleSymbol("%string-hash")).readResolve();
   static final SimpleSymbol Lit99 = (SimpleSymbol)(new SimpleSymbol("string-hash")).readResolve();
   public static final ModuleMethod check$Mnsubstring$Mnspec;
   public static final ModuleMethod kmp$Mnstep;
   static final ModuleMethod lambda$Fn100;
   static final ModuleMethod lambda$Fn105;
   static final ModuleMethod lambda$Fn106;
   static final ModuleMethod lambda$Fn111;
   static final ModuleMethod lambda$Fn116;
   static final ModuleMethod lambda$Fn117;
   static final ModuleMethod lambda$Fn122;
   static final ModuleMethod lambda$Fn123;
   static final ModuleMethod lambda$Fn128;
   static final ModuleMethod lambda$Fn133;
   static final ModuleMethod lambda$Fn138;
   static final ModuleMethod lambda$Fn163;
   static final ModuleMethod lambda$Fn166;
   static final ModuleMethod lambda$Fn17;
   static final ModuleMethod lambda$Fn18;
   static final ModuleMethod lambda$Fn210;
   static final ModuleMethod lambda$Fn216;
   static final ModuleMethod lambda$Fn220;
   static final ModuleMethod lambda$Fn27;
   static final ModuleMethod lambda$Fn5;
   static final ModuleMethod lambda$Fn72;
   static final ModuleMethod lambda$Fn73;
   static final ModuleMethod lambda$Fn78;
   static final ModuleMethod lambda$Fn83;
   static final ModuleMethod lambda$Fn84;
   static final ModuleMethod lambda$Fn89;
   static final ModuleMethod lambda$Fn90;
   static final ModuleMethod lambda$Fn95;
   public static final Macro let$Mnstring$Mnstart$Plend;
   public static final Macro let$Mnstring$Mnstart$Plend2;
   static final Location loc$$Cloptional;
   static final Location loc$base;
   static final Location loc$bound;
   static final Location loc$c$Eq;
   static final Location loc$char$Mncased$Qu;
   static final Location loc$char$Mnset;
   static final Location loc$char$Mnset$Mncontains$Qu;
   static final Location loc$char$Mnset$Qu;
   static final Location loc$check$Mnarg;
   static final Location loc$criterion;
   static final Location loc$delim;
   static final Location loc$end;
   static final Location loc$final;
   static final Location loc$grammar;
   static final Location loc$let$Mnoptionals$St;
   static final Location loc$make$Mnfinal;
   static final Location loc$p$Mnstart;
   static final Location loc$rest;
   static final Location loc$s$Mnend;
   static final Location loc$s$Mnstart;
   static final Location loc$start;
   static final Location loc$token$Mnchars;
   public static final ModuleMethod make$Mnkmp$Mnrestart$Mnvector;
   public static final ModuleMethod reverse$Mnlist$Mn$Grstring;
   public static final ModuleMethod string$Eq;
   public static final ModuleMethod string$Gr;
   public static final ModuleMethod string$Gr$Eq;
   public static final ModuleMethod string$Ls;
   public static final ModuleMethod string$Ls$Eq;
   public static final ModuleMethod string$Ls$Gr;
   public static final ModuleMethod string$Mn$Grlist;
   public static final ModuleMethod string$Mnany;
   public static final ModuleMethod string$Mnappend$Slshared;
   public static final ModuleMethod string$Mnci$Eq;
   public static final ModuleMethod string$Mnci$Gr;
   public static final ModuleMethod string$Mnci$Gr$Eq;
   public static final ModuleMethod string$Mnci$Ls;
   public static final ModuleMethod string$Mnci$Ls$Eq;
   public static final ModuleMethod string$Mnci$Ls$Gr;
   public static final ModuleMethod string$Mncompare;
   public static final ModuleMethod string$Mncompare$Mnci;
   public static final ModuleMethod string$Mnconcatenate;
   public static final ModuleMethod string$Mnconcatenate$Mnreverse;
   public static final ModuleMethod string$Mnconcatenate$Mnreverse$Slshared;
   public static final ModuleMethod string$Mnconcatenate$Slshared;
   public static final ModuleMethod string$Mncontains;
   public static final ModuleMethod string$Mncontains$Mnci;
   public static final ModuleMethod string$Mncopy;
   public static final ModuleMethod string$Mncopy$Ex;
   public static final ModuleMethod string$Mncount;
   public static final ModuleMethod string$Mndelete;
   public static final ModuleMethod string$Mndowncase;
   public static final ModuleMethod string$Mndowncase$Ex;
   public static final ModuleMethod string$Mndrop;
   public static final ModuleMethod string$Mndrop$Mnright;
   public static final ModuleMethod string$Mnevery;
   public static final ModuleMethod string$Mnfill$Ex;
   public static final ModuleMethod string$Mnfilter;
   public static final ModuleMethod string$Mnfold;
   public static final ModuleMethod string$Mnfold$Mnright;
   public static final ModuleMethod string$Mnfor$Mneach;
   public static final ModuleMethod string$Mnfor$Mneach$Mnindex;
   public static final ModuleMethod string$Mnhash;
   public static final ModuleMethod string$Mnhash$Mnci;
   public static final ModuleMethod string$Mnindex;
   public static final ModuleMethod string$Mnindex$Mnright;
   public static final ModuleMethod string$Mnjoin;
   public static final ModuleMethod string$Mnkmp$Mnpartial$Mnsearch;
   public static final ModuleMethod string$Mnmap;
   public static final ModuleMethod string$Mnmap$Ex;
   public static final ModuleMethod string$Mnnull$Qu;
   public static final ModuleMethod string$Mnpad;
   public static final ModuleMethod string$Mnpad$Mnright;
   public static final ModuleMethod string$Mnparse$Mnfinal$Mnstart$Plend;
   public static final ModuleMethod string$Mnparse$Mnstart$Plend;
   public static final ModuleMethod string$Mnprefix$Mnci$Qu;
   public static final ModuleMethod string$Mnprefix$Mnlength;
   public static final ModuleMethod string$Mnprefix$Mnlength$Mnci;
   public static final ModuleMethod string$Mnprefix$Qu;
   public static final ModuleMethod string$Mnreplace;
   public static final ModuleMethod string$Mnreverse;
   public static final ModuleMethod string$Mnreverse$Ex;
   public static final ModuleMethod string$Mnskip;
   public static final ModuleMethod string$Mnskip$Mnright;
   public static final ModuleMethod string$Mnsuffix$Mnci$Qu;
   public static final ModuleMethod string$Mnsuffix$Mnlength;
   public static final ModuleMethod string$Mnsuffix$Mnlength$Mnci;
   public static final ModuleMethod string$Mnsuffix$Qu;
   public static final ModuleMethod string$Mntabulate;
   public static final ModuleMethod string$Mntake;
   public static final ModuleMethod string$Mntake$Mnright;
   public static final ModuleMethod string$Mntitlecase;
   public static final ModuleMethod string$Mntitlecase$Ex;
   public static final ModuleMethod string$Mntokenize;
   public static final ModuleMethod string$Mntrim;
   public static final ModuleMethod string$Mntrim$Mnboth;
   public static final ModuleMethod string$Mntrim$Mnright;
   public static final ModuleMethod string$Mnunfold;
   public static final ModuleMethod string$Mnunfold$Mnright;
   public static final ModuleMethod string$Mnupcase;
   public static final ModuleMethod string$Mnupcase$Ex;
   public static final ModuleMethod string$Mnxcopy$Ex;
   public static final ModuleMethod substring$Mnspec$Mnok$Qu;
   public static final ModuleMethod substring$Slshared;
   public static final ModuleMethod xsubstring;


   public static Object $PcCheckBounds(Object var0, CharSequence var1, int var2, int var3) {
      return var2 < 0?misc.error$V("Illegal substring START spec", new Object[]{var0, Integer.valueOf(var2), var1}):(var2 > var3?misc.error$V("Illegal substring START/END spec", new Object[0]):(var3 > strings.stringLength(var1)?misc.error$V("Illegal substring END spec", new Object[]{var0, Integer.valueOf(var3), var1}):Values.empty));
   }

   static Object $PcCheckSubstringSpec(Object var0, CharSequence var1, int var2, int var3) {
      boolean var4;
      if(var2 < 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         if(var4) {
            return misc.error$V("Illegal substring spec.", new Object[]{var0, var1, Integer.valueOf(var2), Integer.valueOf(var3)});
         }
      } else {
         if(var2 > var3) {
            var4 = true;
         } else {
            var4 = false;
         }

         if(var4) {
            if(var4) {
               return misc.error$V("Illegal substring spec.", new Object[]{var0, var1, Integer.valueOf(var2), Integer.valueOf(var3)});
            }
         } else if(var3 > strings.stringLength(var1)) {
            return misc.error$V("Illegal substring spec.", new Object[]{var0, var1, Integer.valueOf(var2), Integer.valueOf(var3)});
         }
      }

      return Values.empty;
   }

   public static Object $PcFinishStringConcatenateReverse(Object var0, Object var1, Object var2, Object var3) {
      Object var4 = AddOp.$Pl.apply2(var3, var0);

      int var6;
      try {
         var6 = ((Number)var4).intValue();
      } catch (ClassCastException var14) {
         throw new WrongType(var14, "make-string", 1, var4);
      }

      CharSequence var16 = strings.makeString(var6);

      try {
         var6 = ((Number)var0).intValue();
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "%string-copy!", 1, var0);
      }

      CharSequence var5;
      try {
         var5 = (CharSequence)var2;
      } catch (ClassCastException var12) {
         throw new WrongType(var12, "%string-copy!", 2, var2);
      }

      int var7;
      try {
         var7 = ((Number)var3).intValue();
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "%string-copy!", 4, var3);
      }

      $PcStringCopy$Ex(var16, var6, var5, 0, var7);

      CharSequence var15;
      for(; lists.isPair(var1); $PcStringCopy$Ex(var16, var7, var15, 0, var6)) {
         var2 = lists.car.apply1(var1);
         var1 = lists.cdr.apply1(var1);

         try {
            var15 = (CharSequence)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-length", 1, var2);
         }

         var6 = strings.stringLength(var15);
         var0 = AddOp.$Mn.apply2(var0, Integer.valueOf(var6));

         try {
            var7 = ((Number)var0).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "%string-copy!", 1, var0);
         }

         try {
            var15 = (CharSequence)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "%string-copy!", 2, var2);
         }
      }

      return var16;
   }

   public static Object $PcKmpSearch(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
      Object var8 = AddOp.$Mn.apply2(var4, var3);
      Object var10 = makeKmpRestartVector$V(var0, new Object[]{var2, var3, var4});
      IntNum var9 = Lit0;
      var6 = AddOp.$Mn.apply2(var6, var5);
      Object var7 = var5;
      var4 = var8;
      var5 = var9;

      while(Scheme.numEqu.apply2(var5, var8) == Boolean.FALSE) {
         Object var24 = Scheme.numLEq.apply2(var4, var6);

         boolean var15;
         try {
            var15 = ((Boolean)var24).booleanValue();
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "x", -2, var24);
         }

         if(!var15) {
            if(var15) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         ApplyToArgs var25 = Scheme.applyToArgs;

         CharSequence var11;
         try {
            var11 = (CharSequence)var1;
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "string-ref", 1, var1);
         }

         int var14;
         try {
            var14 = ((Number)var7).intValue();
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "string-ref", 2, var7);
         }

         Char var12 = Char.make(strings.stringRef(var11, var14));

         CharSequence var13;
         try {
            var13 = (CharSequence)var0;
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "string-ref", 1, var0);
         }

         Object var26 = AddOp.$Pl.apply2(var3, var5);

         try {
            var14 = ((Number)var26).intValue();
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "string-ref", 2, var26);
         }

         if(var25.apply3(var2, var12, Char.make(strings.stringRef(var13, var14))) != Boolean.FALSE) {
            var7 = AddOp.$Pl.apply2(Lit1, var7);
            var5 = AddOp.$Pl.apply2(Lit1, var5);
            var6 = AddOp.$Mn.apply2(var6, Lit1);
            var4 = AddOp.$Mn.apply2(var4, Lit1);
         } else {
            FVector var23;
            try {
               var23 = (FVector)var10;
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "vector-ref", 1, var10);
            }

            try {
               var14 = ((Number)var5).intValue();
            } catch (ClassCastException var17) {
               throw new WrongType(var17, "vector-ref", 2, var5);
            }

            var5 = vectors.vectorRef(var23, var14);
            if(Scheme.numEqu.apply2(var5, Lit13) != Boolean.FALSE) {
               var7 = AddOp.$Pl.apply2(var7, Lit1);
               var5 = Lit0;
               var6 = AddOp.$Mn.apply2(var6, Lit1);
               var4 = var8;
            } else {
               var4 = AddOp.$Mn.apply2(var8, var5);
            }
         }
      }

      return AddOp.$Mn.apply2(var7, var8);
   }

   public static Object $PcMultispanRepcopy$Ex(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
      Object var7 = AddOp.$Mn.apply2(var6, var5);
      Object var9 = AddOp.$Pl.apply2(var5, DivideOp.modulo.apply2(var3, var7));
      Object var8 = AddOp.$Mn.apply2(var4, var3);

      CharSequence var31;
      try {
         var31 = (CharSequence)var0;
      } catch (ClassCastException var29) {
         throw new WrongType(var29, "%string-copy!", 0, var0);
      }

      int var11;
      try {
         var11 = ((Number)var1).intValue();
      } catch (ClassCastException var28) {
         throw new WrongType(var28, "%string-copy!", 1, var1);
      }

      CharSequence var32;
      try {
         var32 = (CharSequence)var2;
      } catch (ClassCastException var27) {
         throw new WrongType(var27, "%string-copy!", 2, var2);
      }

      int var12;
      try {
         var12 = ((Number)var9).intValue();
      } catch (ClassCastException var26) {
         throw new WrongType(var26, "%string-copy!", 3, var9);
      }

      int var13;
      try {
         var13 = ((Number)var6).intValue();
      } catch (ClassCastException var25) {
         throw new WrongType(var25, "%string-copy!", 4, var6);
      }

      $PcStringCopy$Ex(var31, var11, var32, var12, var13);
      var3 = AddOp.$Mn.apply2(var6, var9);
      var4 = AddOp.$Mn.apply2(var8, var3);
      var4 = DivideOp.quotient.apply2(var4, var7);
      var3 = AddOp.$Pl.apply2(var1, var3);

      while(true) {
         Number var33;
         try {
            var33 = (Number)var4;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "zero?", 1, var4);
         }

         if(numbers.isZero(var33)) {
            try {
               var32 = (CharSequence)var0;
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "%string-copy!", 0, var0);
            }

            try {
               var11 = ((Number)var3).intValue();
            } catch (ClassCastException var17) {
               throw new WrongType(var17, "%string-copy!", 1, var3);
            }

            CharSequence var30;
            try {
               var30 = (CharSequence)var2;
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "%string-copy!", 2, var2);
            }

            try {
               var12 = ((Number)var5).intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "%string-copy!", 3, var5);
            }

            var1 = AddOp.$Pl.apply2(var5, AddOp.$Mn.apply2(var8, AddOp.$Mn.apply2(var3, var1)));

            try {
               var13 = ((Number)var1).intValue();
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "%string-copy!", 4, var1);
            }

            return $PcStringCopy$Ex(var32, var11, var30, var12, var13);
         }

         CharSequence var34;
         try {
            var34 = (CharSequence)var0;
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "%string-copy!", 0, var0);
         }

         try {
            var11 = ((Number)var3).intValue();
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "%string-copy!", 1, var3);
         }

         CharSequence var10;
         try {
            var10 = (CharSequence)var2;
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "%string-copy!", 2, var2);
         }

         try {
            var12 = ((Number)var5).intValue();
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "%string-copy!", 3, var5);
         }

         try {
            var13 = ((Number)var6).intValue();
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "%string-copy!", 4, var6);
         }

         $PcStringCopy$Ex(var34, var11, var10, var12, var13);
         var3 = AddOp.$Pl.apply2(var3, var7);
         var4 = AddOp.$Mn.apply2(var4, Lit1);
      }
   }

   public static Object $PcStringCompare(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8) {
      Object var10 = AddOp.$Mn.apply2(var2, var1);
      Object var9 = AddOp.$Mn.apply2(var5, var4);
      var5 = $PcStringPrefixLength(var0, var1, var2, var3, var4, var5);
      if(Scheme.numEqu.apply2(var5, var10) != Boolean.FALSE) {
         ApplyToArgs var17 = Scheme.applyToArgs;
         if(Scheme.numEqu.apply2(var5, var9) == Boolean.FALSE) {
            var7 = var6;
         }

         return var17.apply2(var7, var2);
      } else {
         ApplyToArgs var18 = Scheme.applyToArgs;
         if(Scheme.numEqu.apply2(var5, var9) == Boolean.FALSE) {
            CharSequence var19;
            try {
               var19 = (CharSequence)var0;
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "string-ref", 1, var0);
            }

            var0 = AddOp.$Pl.apply2(var1, var5);

            int var11;
            try {
               var11 = ((Number)var0).intValue();
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "string-ref", 2, var0);
            }

            Char var16 = Char.make(strings.stringRef(var19, var11));

            try {
               var19 = (CharSequence)var3;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-ref", 1, var3);
            }

            var3 = AddOp.$Pl.apply2(var4, var5);

            try {
               var11 = ((Number)var3).intValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-ref", 2, var3);
            }

            if(characters.isChar$Ls(var16, Char.make(strings.stringRef(var19, var11)))) {
               var8 = var6;
            }
         }

         return var18.apply2(var8, AddOp.$Pl.apply2(var5, var1));
      }
   }

   public static Object $PcStringCompareCi(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8) {
      Object var10 = AddOp.$Mn.apply2(var2, var1);
      Object var9 = AddOp.$Mn.apply2(var5, var4);

      int var11;
      try {
         var11 = ((Number)var1).intValue();
      } catch (ClassCastException var22) {
         throw new WrongType(var22, "%string-prefix-length-ci", 1, var1);
      }

      int var12;
      try {
         var12 = ((Number)var2).intValue();
      } catch (ClassCastException var21) {
         throw new WrongType(var21, "%string-prefix-length-ci", 2, var2);
      }

      int var13;
      try {
         var13 = ((Number)var4).intValue();
      } catch (ClassCastException var20) {
         throw new WrongType(var20, "%string-prefix-length-ci", 4, var4);
      }

      int var14;
      try {
         var14 = ((Number)var5).intValue();
      } catch (ClassCastException var19) {
         throw new WrongType(var19, "%string-prefix-length-ci", 5, var5);
      }

      var11 = $PcStringPrefixLengthCi(var0, var11, var12, var3, var13, var14);
      if(Scheme.numEqu.apply2(Integer.valueOf(var11), var10) != Boolean.FALSE) {
         ApplyToArgs var24 = Scheme.applyToArgs;
         if(Scheme.numEqu.apply2(Integer.valueOf(var11), var9) == Boolean.FALSE) {
            var7 = var6;
         }

         return var24.apply2(var7, var2);
      } else {
         ApplyToArgs var25 = Scheme.applyToArgs;
         if(Scheme.numEqu.apply2(Integer.valueOf(var11), var9) == Boolean.FALSE) {
            CharSequence var26;
            try {
               var26 = (CharSequence)var0;
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "string-ref", 1, var0);
            }

            var0 = AddOp.$Pl.apply2(var1, Integer.valueOf(var11));

            try {
               var12 = ((Number)var0).intValue();
            } catch (ClassCastException var17) {
               throw new WrongType(var17, "string-ref", 2, var0);
            }

            Char var23 = Char.make(strings.stringRef(var26, var12));

            try {
               var26 = (CharSequence)var3;
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "string-ref", 1, var3);
            }

            var3 = AddOp.$Pl.apply2(var4, Integer.valueOf(var11));

            try {
               var12 = ((Number)var3).intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "string-ref", 2, var3);
            }

            if(unicode.isCharCi$Ls(var23, Char.make(strings.stringRef(var26, var12)))) {
               var8 = var6;
            }
         }

         return var25.apply2(var8, AddOp.$Pl.apply2(var1, Integer.valueOf(var11)));
      }
   }

   public static Object $PcStringCopy$Ex(CharSequence var0, int var1, CharSequence var2, int var3, int var4) {
      CharSeq var5;
      if(var3 > var1) {
         while(var3 < var4) {
            try {
               var5 = (CharSeq)var0;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-set!", 1, var0);
            }

            strings.stringSet$Ex(var5, var1, strings.stringRef(var2, var3));
            ++var1;
            ++var3;
         }

         return Values.empty;
      } else {
         var1 = var1 - 1 + (var4 - var3);
         int var6 = var4 - 1;
         var4 = var1;

         for(var1 = var6; var1 >= var3; --var1) {
            try {
               var5 = (CharSeq)var0;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-set!", 1, var0);
            }

            strings.stringSet$Ex(var5, var4, strings.stringRef(var2, var1));
            --var4;
         }

         return Values.empty;
      }
   }

   public static Object $PcStringHash(Object var0, Object var1, Object var2, Object var3, Object var4) {
      srfi13.frame55 var6 = new srfi13.frame55();
      var6.char$Mn$Grint = var1;

      for(var1 = Lit5; Scheme.numGEq.apply2(var1, var2) == Boolean.FALSE; var1 = AddOp.$Pl.apply2(var1, var1)) {
         ;
      }

      Object var7 = AddOp.$Mn.apply2(var1, Lit1);
      IntNum var5 = Lit0;
      var1 = var3;

      Object var16;
      for(var3 = var5; Scheme.numGEq.apply2(var1, var4) == Boolean.FALSE; var1 = var16) {
         var16 = AddOp.$Pl.apply2(var1, Lit1);
         BitwiseOp var8 = BitwiseOp.and;
         AddOp var9 = AddOp.$Pl;
         var3 = MultiplyOp.$St.apply2(Lit6, var3);
         ApplyToArgs var10 = Scheme.applyToArgs;
         Object var11 = var6.char$Mn$Grint;

         CharSequence var12;
         try {
            var12 = (CharSequence)var0;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "string-ref", 1, var0);
         }

         int var13;
         try {
            var13 = ((Number)var1).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "string-ref", 2, var1);
         }

         var3 = var8.apply2(var7, var9.apply2(var3, var10.apply2(var11, Char.make(strings.stringRef(var12, var13)))));
      }

      return DivideOp.modulo.apply2(var3, var2);
   }

   public static Object $PcStringMap(Object var0, Object var1, Object var2, Object var3) {
      var2 = AddOp.$Mn.apply2(var3, var2);

      int var9;
      try {
         var9 = ((Number)var2).intValue();
      } catch (ClassCastException var16) {
         throw new WrongType(var16, "make-string", 1, var2);
      }

      CharSequence var5 = strings.makeString(var9);
      var3 = AddOp.$Mn.apply2(var3, Lit1);

      for(var2 = AddOp.$Mn.apply2(var2, Lit1); Scheme.numLss.apply2(var2, Lit0) == Boolean.FALSE; var2 = AddOp.$Mn.apply2(var2, Lit1)) {
         CharSeq var6;
         try {
            var6 = (CharSeq)var5;
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "string-set!", 1, var5);
         }

         try {
            var9 = ((Number)var2).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "string-set!", 2, var2);
         }

         ApplyToArgs var7 = Scheme.applyToArgs;

         CharSequence var8;
         try {
            var8 = (CharSequence)var1;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-ref", 1, var1);
         }

         int var10;
         try {
            var10 = ((Number)var3).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-ref", 2, var3);
         }

         Object var17 = var7.apply2(var0, Char.make(strings.stringRef(var8, var10)));

         char var4;
         try {
            var4 = ((Char)var17).charValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-set!", 3, var17);
         }

         strings.stringSet$Ex(var6, var9, var4);
         var3 = AddOp.$Mn.apply2(var3, Lit1);
      }

      return var5;
   }

   public static Object $PcStringMap$Ex(Object var0, Object var1, Object var2, Object var3) {
      for(var3 = AddOp.$Mn.apply2(var3, Lit1); Scheme.numLss.apply2(var3, var2) == Boolean.FALSE; var3 = AddOp.$Mn.apply2(var3, Lit1)) {
         CharSeq var5;
         try {
            var5 = (CharSeq)var1;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "string-set!", 1, var1);
         }

         int var8;
         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-set!", 2, var3);
         }

         ApplyToArgs var6 = Scheme.applyToArgs;

         CharSequence var7;
         try {
            var7 = (CharSequence)var1;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-ref", 1, var1);
         }

         int var9;
         try {
            var9 = ((Number)var3).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-ref", 2, var3);
         }

         Object var15 = var6.apply2(var0, Char.make(strings.stringRef(var7, var9)));

         char var4;
         try {
            var4 = ((Char)var15).charValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-set!", 3, var15);
         }

         strings.stringSet$Ex(var5, var8, var4);
      }

      return Values.empty;
   }

   public static Object $PcStringPrefix$Qu(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      Object var7 = AddOp.$Mn.apply2(var2, var1);
      Object var6 = Scheme.numLEq.apply2(var7, AddOp.$Mn.apply2(var5, var4));

      boolean var8;
      try {
         var8 = ((Boolean)var6).booleanValue();
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "x", -2, var6);
      }

      return var8?Scheme.numEqu.apply2($PcStringPrefixLength(var0, var1, var2, var3, var4, var5), var7):(var8?Boolean.TRUE:Boolean.FALSE);
   }

   public static Object $PcStringPrefixCi$Qu(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      Object var6 = AddOp.$Mn.apply2(var2, var1);
      Object var7 = Scheme.numLEq.apply2(var6, AddOp.$Mn.apply2(var5, var4));

      boolean var12;
      try {
         var12 = ((Boolean)var7).booleanValue();
      } catch (ClassCastException var17) {
         throw new WrongType(var17, "x", -2, var7);
      }

      if(var12) {
         NumberCompare var18 = Scheme.numEqu;

         int var8;
         try {
            var8 = ((Number)var1).intValue();
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "%string-prefix-length-ci", 1, var1);
         }

         int var9;
         try {
            var9 = ((Number)var2).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "%string-prefix-length-ci", 2, var2);
         }

         int var10;
         try {
            var10 = ((Number)var4).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "%string-prefix-length-ci", 4, var4);
         }

         int var11;
         try {
            var11 = ((Number)var5).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "%string-prefix-length-ci", 5, var5);
         }

         return var18.apply2(var6, Integer.valueOf($PcStringPrefixLengthCi(var0, var8, var9, var3, var10, var11)));
      } else {
         return var12?Boolean.TRUE:Boolean.FALSE;
      }
   }

   public static Object $PcStringPrefixLength(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      boolean var8 = false;
      var2 = numbers.min(new Object[]{AddOp.$Mn.apply2(var2, var1), AddOp.$Mn.apply2(var5, var4)});
      Object var6 = AddOp.$Pl.apply2(var1, var2);
      if(var0 == var3) {
         var8 = true;
      }

      label60: {
         if(var8) {
            if(Scheme.numEqu.apply2(var1, var4) == Boolean.FALSE) {
               break label60;
            }
         } else if(!var8) {
            break label60;
         }

         return var2;
      }

      var2 = var4;
      var4 = var1;

      while(true) {
         var5 = Scheme.numGEq.apply2(var4, var6);

         boolean var9;
         try {
            var9 = ((Boolean)var5).booleanValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "x", -2, var5);
         }

         if(var9) {
            if(var9) {
               break;
            }
         } else {
            CharSequence var16;
            try {
               var16 = (CharSequence)var0;
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "string-ref", 1, var0);
            }

            int var17;
            try {
               var17 = ((Number)var4).intValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-ref", 2, var4);
            }

            Char var15 = Char.make(strings.stringRef(var16, var17));

            CharSequence var7;
            try {
               var7 = (CharSequence)var3;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-ref", 1, var3);
            }

            try {
               var17 = ((Number)var2).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 2, var2);
            }

            if(!characters.isChar$Eq(var15, Char.make(strings.stringRef(var7, var17)))) {
               break;
            }
         }

         var4 = AddOp.$Pl.apply2(var4, Lit1);
         var2 = AddOp.$Pl.apply2(var2, Lit1);
      }

      return AddOp.$Mn.apply2(var4, var1);
   }

   public static int $PcStringPrefixLengthCi(Object var0, int var1, int var2, Object var3, int var4, int var5) {
      Object var6 = numbers.min(new Object[]{Integer.valueOf(var2 - var1), Integer.valueOf(var5 - var4)});

      int var8;
      try {
         var8 = ((Number)var6).intValue();
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "delta", -2, var6);
      }

      boolean var12;
      if(var0 == var3) {
         var12 = true;
      } else {
         var12 = false;
      }

      label56: {
         if(var12) {
            if(var1 != var4) {
               break label56;
            }
         } else if(!var12) {
            break label56;
         }

         return var8;
      }

      var2 = var1;

      while(true) {
         boolean var13;
         if(var2 >= var1 + var8) {
            var13 = true;
         } else {
            var13 = false;
         }

         if(var13) {
            if(var13) {
               break;
            }
         } else {
            CharSequence var15;
            try {
               var15 = (CharSequence)var0;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 1, var0);
            }

            Char var14 = Char.make(strings.stringRef(var15, var2));

            CharSequence var7;
            try {
               var7 = (CharSequence)var3;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var3);
            }

            if(!unicode.isCharCi$Eq(var14, Char.make(strings.stringRef(var7, var4)))) {
               break;
            }
         }

         ++var2;
         ++var4;
      }

      return var2 - var1;
   }

   public static Object $PcStringSuffix$Qu(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      Object var7 = AddOp.$Mn.apply2(var2, var1);
      Object var6 = Scheme.numLEq.apply2(var7, AddOp.$Mn.apply2(var5, var4));

      boolean var8;
      try {
         var8 = ((Boolean)var6).booleanValue();
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "x", -2, var6);
      }

      return var8?Scheme.numEqu.apply2(var7, $PcStringSuffixLength(var0, var1, var2, var3, var4, var5)):(var8?Boolean.TRUE:Boolean.FALSE);
   }

   public static Object $PcStringSuffixCi$Qu(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      Object var6 = AddOp.$Mn.apply2(var2, var1);
      Object var7 = Scheme.numLEq.apply2(var6, AddOp.$Mn.apply2(var5, var4));

      boolean var12;
      try {
         var12 = ((Boolean)var7).booleanValue();
      } catch (ClassCastException var17) {
         throw new WrongType(var17, "x", -2, var7);
      }

      if(var12) {
         NumberCompare var18 = Scheme.numEqu;

         int var8;
         try {
            var8 = ((Number)var1).intValue();
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "%string-suffix-length-ci", 1, var1);
         }

         int var9;
         try {
            var9 = ((Number)var2).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "%string-suffix-length-ci", 2, var2);
         }

         int var10;
         try {
            var10 = ((Number)var4).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "%string-suffix-length-ci", 4, var4);
         }

         int var11;
         try {
            var11 = ((Number)var5).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "%string-suffix-length-ci", 5, var5);
         }

         return var18.apply2(var6, Integer.valueOf($PcStringSuffixLengthCi(var0, var8, var9, var3, var10, var11)));
      } else {
         return var12?Boolean.TRUE:Boolean.FALSE;
      }
   }

   public static Object $PcStringSuffixLength(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      boolean var8 = false;
      var1 = numbers.min(new Object[]{AddOp.$Mn.apply2(var2, var1), AddOp.$Mn.apply2(var5, var4)});
      Object var6 = AddOp.$Mn.apply2(var2, var1);
      if(var0 == var3) {
         var8 = true;
      }

      if(var8) {
         if(Scheme.numEqu.apply2(var2, var5) != Boolean.FALSE) {
            return var1;
         }
      } else if(var8) {
         return var1;
      }

      var4 = AddOp.$Mn.apply2(var2, Lit1);
      var1 = AddOp.$Mn.apply2(var5, Lit1);

      while(true) {
         var5 = Scheme.numLss.apply2(var4, var6);

         boolean var9;
         try {
            var9 = ((Boolean)var5).booleanValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "x", -2, var5);
         }

         if(var9) {
            if(var9) {
               break;
            }
         } else {
            CharSequence var16;
            try {
               var16 = (CharSequence)var0;
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "string-ref", 1, var0);
            }

            int var17;
            try {
               var17 = ((Number)var4).intValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-ref", 2, var4);
            }

            Char var15 = Char.make(strings.stringRef(var16, var17));

            CharSequence var7;
            try {
               var7 = (CharSequence)var3;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-ref", 1, var3);
            }

            try {
               var17 = ((Number)var1).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 2, var1);
            }

            if(!characters.isChar$Eq(var15, Char.make(strings.stringRef(var7, var17)))) {
               break;
            }
         }

         var4 = AddOp.$Mn.apply2(var4, Lit1);
         var1 = AddOp.$Mn.apply2(var1, Lit1);
      }

      return AddOp.$Mn.apply2(AddOp.$Mn.apply2(var2, var4), Lit1);
   }

   public static int $PcStringSuffixLengthCi(Object var0, int var1, int var2, Object var3, int var4, int var5) {
      Object var6 = numbers.min(new Object[]{Integer.valueOf(var2 - var1), Integer.valueOf(var5 - var4)});

      int var8;
      try {
         var8 = ((Number)var6).intValue();
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "delta", -2, var6);
      }

      boolean var12;
      if(var0 == var3) {
         var12 = true;
      } else {
         var12 = false;
      }

      label56: {
         if(var12) {
            if(var2 != var5) {
               break label56;
            }
         } else if(!var12) {
            break label56;
         }

         return var8;
      }

      var1 = var2 - 1;
      var4 = var5 - 1;

      while(true) {
         boolean var13;
         if(var1 < var2 - var8) {
            var13 = true;
         } else {
            var13 = false;
         }

         if(var13) {
            if(var13) {
               break;
            }
         } else {
            CharSequence var15;
            try {
               var15 = (CharSequence)var0;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 1, var0);
            }

            Char var14 = Char.make(strings.stringRef(var15, var1));

            CharSequence var7;
            try {
               var7 = (CharSequence)var3;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var3);
            }

            if(!unicode.isCharCi$Eq(var14, Char.make(strings.stringRef(var7, var4)))) {
               break;
            }
         }

         --var4;
         --var1;
      }

      return var2 - var1 - 1;
   }

   public static Object $PcStringTitlecase$Ex(Object var0, Object var1, Object var2) {
      while(true) {
         Location var4 = loc$char$Mncased$Qu;

         Object var15;
         try {
            var15 = var4.get();
         } catch (UnboundLocationException var14) {
            var14.setLine("srfi13.scm", 955, 28);
            throw var14;
         }

         var1 = stringIndex$V(var0, var15, new Object[]{var1, var2});
         if(var1 != Boolean.FALSE) {
            CharSeq var16;
            try {
               var16 = (CharSeq)var0;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-set!", 1, var0);
            }

            int var6;
            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-set!", 2, var1);
            }

            CharSequence var5;
            try {
               var5 = (CharSequence)var0;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 1, var0);
            }

            int var7;
            try {
               var7 = ((Number)var1).intValue();
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 2, var1);
            }

            Char var17 = unicode.charTitlecase(Char.make(strings.stringRef(var5, var7)));

            char var3;
            try {
               var3 = var17.charValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-set!", 3, var17);
            }

            strings.stringSet$Ex(var16, var6, var3);
            var1 = AddOp.$Pl.apply2(var1, Lit1);
            var4 = loc$char$Mncased$Qu;

            try {
               var15 = var4.get();
            } catch (UnboundLocationException var8) {
               var8.setLine("srfi13.scm", 959, 31);
               throw var8;
            }

            var15 = stringSkip$V(var0, var15, new Object[]{var1, var2});
            if(var15 != Boolean.FALSE) {
               stringDowncase$Ex$V(var0, new Object[]{var1, var15});
               var1 = AddOp.$Pl.apply2(var15, Lit1);
               continue;
            }

            return stringDowncase$Ex$V(var0, new Object[]{var1, var2});
         }

         return Values.empty;
      }
   }

   public static Object $PcSubstring$SlShared(CharSequence var0, int var1, int var2) {
      boolean var3 = numbers.isZero(Integer.valueOf(var1));
      if(var3) {
         if(var2 == strings.stringLength(var0)) {
            return var0;
         }
      } else if(var3) {
         return var0;
      }

      return strings.substring(var0, var1, var2);
   }

   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("l-s-s+e2")).readResolve();
      SyntaxPattern var1 = new SyntaxPattern("\f\u0018L\f\u0007\f\u000f\f\u0017\f\u001f\b\f\'\f/\f7\f?\rG@\b\b", new Object[0], 9);
      SimpleSymbol var2 = (SimpleSymbol)(new SimpleSymbol("let")).readResolve();
      SimpleSymbol var3 = (SimpleSymbol)(new SimpleSymbol("procv")).readResolve();
      SimpleSymbol var4 = (SimpleSymbol)(new SimpleSymbol("let-string-start+end")).readResolve();
      Lit41 = var4;
      SimpleSymbol var5 = (SimpleSymbol)(new SimpleSymbol("rest")).readResolve();
      Lit27 = var5;
      SyntaxRule var7 = new SyntaxRule(var1, "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b#\b\u0011\u0018\u00141\t\u0003\t\u000b\u0018\u001c\u0011\u0018\f\t+\t;\b\u0011\u0018\u0014!\t\u0013\b\u001b\u0011\u0018\f\t3\u0011\u0018$\bEC", new Object[]{var2, var3, var4, PairWithPosition.make(var5, LList.Empty, "srfi13.scm", 553003), Lit27}, 1);
      Lit44 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var7}, 9);
      Lit43 = (SimpleSymbol)(new SimpleSymbol("let-string-start+end2")).readResolve();
      var0 = Lit41;
      var7 = new SyntaxRule(new SyntaxPattern("\f\u0018,\f\u0007\f\u000f\b\f\u0017\f\u001f\f\'\r/(\b\b", new Object[0], 6), "\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u0004!\t\u0003\b\u000bI\u0011\u0018\f\t\u0013\t\u001b\b#\b-+", new Object[]{Lit150, Lit47}, 1);
      SyntaxRule var9 = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u000f\f\u0017\b\f\u001f\f\'\f/\r70\b\b", new Object[0], 7), "\u0001\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\t\u0013\t\u0003\b\u000bI\u0011\u0018\f\t\u001b\t#\b+\b53", new Object[]{Lit150, Lit45}, 1);
      Lit42 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var7, var9}, 7);
      Lit40 = (SimpleSymbol)(new SimpleSymbol("grammar")).readResolve();
      Lit39 = (SimpleSymbol)(new SimpleSymbol("delim")).readResolve();
      Lit38 = (SimpleSymbol)(new SimpleSymbol("token-chars")).readResolve();
      Lit37 = (SimpleSymbol)(new SimpleSymbol("final")).readResolve();
      Lit36 = (SimpleSymbol)(new SimpleSymbol("s-end")).readResolve();
      Lit35 = (SimpleSymbol)(new SimpleSymbol("s-start")).readResolve();
      Lit34 = (SimpleSymbol)(new SimpleSymbol("p-start")).readResolve();
      Lit33 = (SimpleSymbol)(new SimpleSymbol("end")).readResolve();
      Lit32 = (SimpleSymbol)(new SimpleSymbol("start")).readResolve();
      Lit31 = (SimpleSymbol)(new SimpleSymbol("c=")).readResolve();
      Lit30 = (SimpleSymbol)(new SimpleSymbol("char-set")).readResolve();
      Lit29 = (SimpleSymbol)(new SimpleSymbol("criterion")).readResolve();
      Lit28 = (SimpleSymbol)(new SimpleSymbol("char-cased?")).readResolve();
      Lit26 = (SimpleSymbol)(new SimpleSymbol("bound")).readResolve();
      Lit25 = (SimpleSymbol)(new SimpleSymbol("char-set-contains?")).readResolve();
      Lit24 = (SimpleSymbol)(new SimpleSymbol("char-set?")).readResolve();
      Lit23 = (SimpleSymbol)(new SimpleSymbol("make-final")).readResolve();
      Lit22 = (SimpleSymbol)(new SimpleSymbol("base")).readResolve();
      Lit21 = (SimpleSymbol)(new SimpleSymbol("let-optionals*")).readResolve();
      Lit20 = (SimpleSymbol)(new SimpleSymbol(":optional")).readResolve();
      Lit19 = (SimpleSymbol)(new SimpleSymbol("check-arg")).readResolve();
      Lit18 = (SimpleSymbol)(new SimpleSymbol("suffix")).readResolve();
      Lit17 = (SimpleSymbol)(new SimpleSymbol("prefix")).readResolve();
      Lit16 = (SimpleSymbol)(new SimpleSymbol("strict-infix")).readResolve();
      Lit15 = (SimpleSymbol)(new SimpleSymbol("infix")).readResolve();
      Lit14 = (SimpleSymbol)(new SimpleSymbol("graphic")).readResolve();
      Lit13 = IntNum.make(-1);
      Lit12 = Char.make(32);
      Lit11 = (SimpleSymbol)(new SimpleSymbol("whitespace")).readResolve();
      Lit10 = IntNum.make(4194304);
      Lit9 = IntNum.make(4194304);
      Lit8 = IntNum.make(4194304);
      Lit7 = IntNum.make(4194304);
      Lit6 = IntNum.make(37);
      Lit5 = IntNum.make(65536);
      Lit4 = IntNum.make(4096);
      Lit3 = IntNum.make(40);
      Lit2 = IntNum.make(4096);
      Lit1 = IntNum.make(1);
      Lit0 = IntNum.make(0);
      $instance = new srfi13();
      loc$check$Mnarg = ThreadLocation.getInstance(Lit19, (Object)null);
      loc$$Cloptional = ThreadLocation.getInstance(Lit20, (Object)null);
      loc$let$Mnoptionals$St = ThreadLocation.getInstance(Lit21, (Object)null);
      loc$base = ThreadLocation.getInstance(Lit22, (Object)null);
      loc$make$Mnfinal = ThreadLocation.getInstance(Lit23, (Object)null);
      loc$char$Mnset$Qu = ThreadLocation.getInstance(Lit24, (Object)null);
      loc$char$Mnset$Mncontains$Qu = ThreadLocation.getInstance(Lit25, (Object)null);
      loc$bound = ThreadLocation.getInstance(Lit26, (Object)null);
      loc$rest = ThreadLocation.getInstance(Lit27, (Object)null);
      loc$char$Mncased$Qu = ThreadLocation.getInstance(Lit28, (Object)null);
      loc$criterion = ThreadLocation.getInstance(Lit29, (Object)null);
      loc$char$Mnset = ThreadLocation.getInstance(Lit30, (Object)null);
      loc$c$Eq = ThreadLocation.getInstance(Lit31, (Object)null);
      loc$start = ThreadLocation.getInstance(Lit32, (Object)null);
      loc$end = ThreadLocation.getInstance(Lit33, (Object)null);
      loc$p$Mnstart = ThreadLocation.getInstance(Lit34, (Object)null);
      loc$s$Mnstart = ThreadLocation.getInstance(Lit35, (Object)null);
      loc$s$Mnend = ThreadLocation.getInstance(Lit36, (Object)null);
      loc$final = ThreadLocation.getInstance(Lit37, (Object)null);
      loc$token$Mnchars = ThreadLocation.getInstance(Lit38, (Object)null);
      loc$delim = ThreadLocation.getInstance(Lit39, (Object)null);
      loc$grammar = ThreadLocation.getInstance(Lit40, (Object)null);
      let$Mnstring$Mnstart$Plend = Macro.make(Lit41, Lit42, $instance);
      let$Mnstring$Mnstart$Plend2 = Macro.make(Lit43, Lit44, $instance);
      srfi13 var6 = $instance;
      string$Mnparse$Mnstart$Plend = new ModuleMethod(var6, 194, Lit45, 12291);
      $Pccheck$Mnbounds = new ModuleMethod(var6, 195, Lit46, 16388);
      string$Mnparse$Mnfinal$Mnstart$Plend = new ModuleMethod(var6, 196, Lit47, 12291);
      substring$Mnspec$Mnok$Qu = new ModuleMethod(var6, 197, Lit48, 12291);
      check$Mnsubstring$Mnspec = new ModuleMethod(var6, 198, Lit49, 16388);
      ModuleMethod var8 = new ModuleMethod(var6, 199, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:223");
      lambda$Fn5 = var8;
      substring$Slshared = new ModuleMethod(var6, 200, Lit50, -4094);
      $Pcsubstring$Slshared = new ModuleMethod(var6, 201, Lit51, 12291);
      string$Mncopy = new ModuleMethod(var6, 202, Lit52, -4095);
      string$Mnmap = new ModuleMethod(var6, 203, Lit53, -4094);
      $Pcstring$Mnmap = new ModuleMethod(var6, 204, Lit54, 16388);
      string$Mnmap$Ex = new ModuleMethod(var6, 205, Lit55, -4094);
      $Pcstring$Mnmap$Ex = new ModuleMethod(var6, 206, Lit56, 16388);
      string$Mnfold = new ModuleMethod(var6, 207, Lit57, -4093);
      string$Mnfold$Mnright = new ModuleMethod(var6, 208, Lit58, -4093);
      var8 = new ModuleMethod(var6, 209, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:377");
      lambda$Fn17 = var8;
      string$Mnunfold = new ModuleMethod(var6, 210, Lit59, -4092);
      var8 = new ModuleMethod(var6, 211, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:422");
      lambda$Fn18 = var8;
      string$Mnunfold$Mnright = new ModuleMethod(var6, 212, Lit60, -4092);
      string$Mnfor$Mneach = new ModuleMethod(var6, 213, Lit61, -4094);
      string$Mnfor$Mneach$Mnindex = new ModuleMethod(var6, 214, Lit62, -4094);
      string$Mnevery = new ModuleMethod(var6, 215, Lit63, -4094);
      string$Mnany = new ModuleMethod(var6, 216, Lit64, -4094);
      var8 = new ModuleMethod(var6, 217, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:535");
      lambda$Fn27 = var8;
      string$Mntabulate = new ModuleMethod(var6, 218, Lit65, 8194);
      $Pcstring$Mnprefix$Mnlength = new ModuleMethod(var6, 219, Lit66, 24582);
      $Pcstring$Mnsuffix$Mnlength = new ModuleMethod(var6, 220, Lit67, 24582);
      $Pcstring$Mnprefix$Mnlength$Mnci = new ModuleMethod(var6, 221, Lit68, 24582);
      $Pcstring$Mnsuffix$Mnlength$Mnci = new ModuleMethod(var6, 222, Lit69, 24582);
      string$Mnprefix$Mnlength = new ModuleMethod(var6, 223, Lit70, -4094);
      string$Mnsuffix$Mnlength = new ModuleMethod(var6, 224, Lit71, -4094);
      string$Mnprefix$Mnlength$Mnci = new ModuleMethod(var6, 225, Lit72, -4094);
      string$Mnsuffix$Mnlength$Mnci = new ModuleMethod(var6, 226, Lit73, -4094);
      string$Mnprefix$Qu = new ModuleMethod(var6, 227, Lit74, -4094);
      string$Mnsuffix$Qu = new ModuleMethod(var6, 228, Lit75, -4094);
      string$Mnprefix$Mnci$Qu = new ModuleMethod(var6, 229, Lit76, -4094);
      string$Mnsuffix$Mnci$Qu = new ModuleMethod(var6, 230, Lit77, -4094);
      $Pcstring$Mnprefix$Qu = new ModuleMethod(var6, 231, Lit78, 24582);
      $Pcstring$Mnsuffix$Qu = new ModuleMethod(var6, 232, Lit79, 24582);
      $Pcstring$Mnprefix$Mnci$Qu = new ModuleMethod(var6, 233, Lit80, 24582);
      $Pcstring$Mnsuffix$Mnci$Qu = new ModuleMethod(var6, 234, Lit81, 24582);
      $Pcstring$Mncompare = new ModuleMethod(var6, 235, Lit82, '选');
      $Pcstring$Mncompare$Mnci = new ModuleMethod(var6, 236, Lit83, '选');
      string$Mncompare = new ModuleMethod(var6, 237, Lit84, -4091);
      string$Mncompare$Mnci = new ModuleMethod(var6, 238, Lit85, -4091);
      var8 = new ModuleMethod(var6, 239, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:756");
      lambda$Fn72 = var8;
      var8 = new ModuleMethod(var6, 240, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:758");
      lambda$Fn73 = var8;
      string$Eq = new ModuleMethod(var6, 241, Lit86, -4094);
      var8 = new ModuleMethod(var6, 242, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:767");
      lambda$Fn78 = var8;
      string$Ls$Gr = new ModuleMethod(var6, 243, Lit87, -4094);
      var8 = new ModuleMethod(var6, 244, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:778");
      lambda$Fn83 = var8;
      var8 = new ModuleMethod(var6, 245, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:779");
      lambda$Fn84 = var8;
      string$Ls = new ModuleMethod(var6, 246, Lit88, -4094);
      var8 = new ModuleMethod(var6, 247, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:788");
      lambda$Fn89 = var8;
      var8 = new ModuleMethod(var6, 248, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:789");
      lambda$Fn90 = var8;
      string$Gr = new ModuleMethod(var6, 249, Lit89, -4094);
      var8 = new ModuleMethod(var6, 250, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:801");
      lambda$Fn95 = var8;
      string$Ls$Eq = new ModuleMethod(var6, 251, Lit90, -4094);
      var8 = new ModuleMethod(var6, 252, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:810");
      lambda$Fn100 = var8;
      string$Gr$Eq = new ModuleMethod(var6, 253, Lit91, -4094);
      var8 = new ModuleMethod(var6, 254, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:820");
      lambda$Fn105 = var8;
      var8 = new ModuleMethod(var6, 255, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:822");
      lambda$Fn106 = var8;
      string$Mnci$Eq = new ModuleMethod(var6, 256, Lit92, -4094);
      var8 = new ModuleMethod(var6, 257, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:831");
      lambda$Fn111 = var8;
      string$Mnci$Ls$Gr = new ModuleMethod(var6, 258, Lit93, -4094);
      var8 = new ModuleMethod(var6, 259, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:842");
      lambda$Fn116 = var8;
      var8 = new ModuleMethod(var6, 260, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:843");
      lambda$Fn117 = var8;
      string$Mnci$Ls = new ModuleMethod(var6, 261, Lit94, -4094);
      var8 = new ModuleMethod(var6, 262, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:852");
      lambda$Fn122 = var8;
      var8 = new ModuleMethod(var6, 263, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:853");
      lambda$Fn123 = var8;
      string$Mnci$Gr = new ModuleMethod(var6, 264, Lit95, -4094);
      var8 = new ModuleMethod(var6, 265, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:865");
      lambda$Fn128 = var8;
      string$Mnci$Ls$Eq = new ModuleMethod(var6, 266, Lit96, -4094);
      var8 = new ModuleMethod(var6, 267, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:874");
      lambda$Fn133 = var8;
      string$Mnci$Gr$Eq = new ModuleMethod(var6, 268, Lit97, -4094);
      $Pcstring$Mnhash = new ModuleMethod(var6, 269, Lit98, 20485);
      string$Mnhash = new ModuleMethod(var6, 270, Lit99, -4095);
      var8 = new ModuleMethod(var6, 271, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:922");
      lambda$Fn138 = var8;
      string$Mnhash$Mnci = new ModuleMethod(var6, 272, Lit100, -4095);
      string$Mnupcase = new ModuleMethod(var6, 273, Lit101, -4095);
      string$Mnupcase$Ex = new ModuleMethod(var6, 274, Lit102, -4095);
      string$Mndowncase = new ModuleMethod(var6, 275, Lit103, -4095);
      string$Mndowncase$Ex = new ModuleMethod(var6, 276, Lit104, -4095);
      $Pcstring$Mntitlecase$Ex = new ModuleMethod(var6, 277, Lit105, 12291);
      string$Mntitlecase$Ex = new ModuleMethod(var6, 278, Lit106, -4095);
      string$Mntitlecase = new ModuleMethod(var6, 279, Lit107, -4095);
      string$Mntake = new ModuleMethod(var6, 280, Lit108, 8194);
      string$Mntake$Mnright = new ModuleMethod(var6, 281, Lit109, 8194);
      string$Mndrop = new ModuleMethod(var6, 282, Lit110, 8194);
      string$Mndrop$Mnright = new ModuleMethod(var6, 283, Lit111, 8194);
      string$Mntrim = new ModuleMethod(var6, 284, Lit112, -4095);
      string$Mntrim$Mnright = new ModuleMethod(var6, 285, Lit113, -4095);
      string$Mntrim$Mnboth = new ModuleMethod(var6, 286, Lit114, -4095);
      var8 = new ModuleMethod(var6, 287, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:1047");
      lambda$Fn163 = var8;
      string$Mnpad$Mnright = new ModuleMethod(var6, 288, Lit115, -4094);
      var8 = new ModuleMethod(var6, 289, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:1059");
      lambda$Fn166 = var8;
      string$Mnpad = new ModuleMethod(var6, 290, Lit116, -4094);
      string$Mndelete = new ModuleMethod(var6, 291, Lit117, -4094);
      string$Mnfilter = new ModuleMethod(var6, 292, Lit118, -4094);
      string$Mnindex = new ModuleMethod(var6, 293, Lit119, -4094);
      string$Mnindex$Mnright = new ModuleMethod(var6, 294, Lit120, -4094);
      string$Mnskip = new ModuleMethod(var6, 295, Lit121, -4094);
      string$Mnskip$Mnright = new ModuleMethod(var6, 296, Lit122, -4094);
      string$Mncount = new ModuleMethod(var6, 297, Lit123, -4094);
      string$Mnfill$Ex = new ModuleMethod(var6, 298, Lit124, -4094);
      string$Mncopy$Ex = new ModuleMethod(var6, 299, Lit125, 20483);
      $Pcstring$Mncopy$Ex = new ModuleMethod(var6, 302, Lit126, 20485);
      string$Mncontains = new ModuleMethod(var6, 303, Lit127, -4094);
      string$Mncontains$Mnci = new ModuleMethod(var6, 304, Lit128, -4094);
      $Pckmp$Mnsearch = new ModuleMethod(var6, 305, Lit129, 28679);
      make$Mnkmp$Mnrestart$Mnvector = new ModuleMethod(var6, 306, Lit130, -4095);
      kmp$Mnstep = new ModuleMethod(var6, 307, Lit131, 24582);
      string$Mnkmp$Mnpartial$Mnsearch = new ModuleMethod(var6, 308, Lit132, -4092);
      string$Mnnull$Qu = new ModuleMethod(var6, 309, Lit133, 4097);
      string$Mnreverse = new ModuleMethod(var6, 310, Lit134, -4095);
      string$Mnreverse$Ex = new ModuleMethod(var6, 311, Lit135, -4095);
      reverse$Mnlist$Mn$Grstring = new ModuleMethod(var6, 312, Lit136, 4097);
      string$Mn$Grlist = new ModuleMethod(var6, 313, Lit137, -4095);
      string$Mnappend$Slshared = new ModuleMethod(var6, 314, Lit138, -4096);
      string$Mnconcatenate$Slshared = new ModuleMethod(var6, 315, Lit139, 4097);
      string$Mnconcatenate = new ModuleMethod(var6, 316, Lit140, 4097);
      string$Mnconcatenate$Mnreverse = new ModuleMethod(var6, 317, Lit141, -4095);
      string$Mnconcatenate$Mnreverse$Slshared = new ModuleMethod(var6, 318, Lit142, -4095);
      $Pcfinish$Mnstring$Mnconcatenate$Mnreverse = new ModuleMethod(var6, 319, Lit143, 16388);
      string$Mnreplace = new ModuleMethod(var6, 320, Lit144, -4092);
      string$Mntokenize = new ModuleMethod(var6, 321, Lit145, -4095);
      var8 = new ModuleMethod(var6, 322, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:1738");
      lambda$Fn210 = var8;
      xsubstring = new ModuleMethod(var6, 323, Lit146, -4094);
      var8 = new ModuleMethod(var6, 324, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:1779");
      lambda$Fn216 = var8;
      var8 = new ModuleMethod(var6, 325, (Object)null, 4097);
      var8.setProperty("source-location", "srfi13.scm:1785");
      lambda$Fn220 = var8;
      string$Mnxcopy$Ex = new ModuleMethod(var6, 326, Lit147, -4092);
      $Pcmultispan$Mnrepcopy$Ex = new ModuleMethod(var6, 327, Lit148, 28679);
      string$Mnjoin = new ModuleMethod(var6, 328, Lit149, -4095);
      $instance.run();
   }

   public srfi13() {
      ModuleInfo.register(this);
   }

   public static Object checkSubstringSpec(Object var0, Object var1, Object var2, Object var3) {
      return !isSubstringSpecOk(var1, var2, var3)?misc.error$V("Illegal substring spec.", new Object[]{var0, var1, var2, var3}):Values.empty;
   }

   public static boolean isStringNull(Object var0) {
      CharSequence var1;
      try {
         var1 = (CharSequence)var0;
      } catch (ClassCastException var2) {
         throw new WrongType(var2, "string-length", 1, var0);
      }

      return numbers.isZero(Integer.valueOf(strings.stringLength(var1)));
   }

   public static Object isStringPrefix$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame19 var3 = new srfi13.frame19();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn44, var3.lambda$Fn45);
   }

   public static Object isStringPrefixCi$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame23 var3 = new srfi13.frame23();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn52, var3.lambda$Fn53);
   }

   public static Object isStringSuffix$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame21 var3 = new srfi13.frame21();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn48, var3.lambda$Fn49);
   }

   public static Object isStringSuffixCi$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame25 var3 = new srfi13.frame25();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn56, var3.lambda$Fn57);
   }

   public static boolean isSubstringSpecOk(Object var0, Object var1, Object var2) {
      boolean var5 = strings.isString(var0);
      boolean var4 = var5;
      if(var5) {
         var5 = numbers.isInteger(var1);
         var4 = var5;
         if(var5) {
            var5 = numbers.isExact(var1);
            var4 = var5;
            if(var5) {
               var5 = numbers.isInteger(var2);
               var4 = var5;
               if(var5) {
                  var5 = numbers.isExact(var2);
                  var4 = var5;
                  if(var5) {
                     Object var3 = Scheme.numLEq.apply2(Lit0, var1);

                     try {
                        var5 = ((Boolean)var3).booleanValue();
                     } catch (ClassCastException var8) {
                        throw new WrongType(var8, "x", -2, var3);
                     }

                     var4 = var5;
                     if(var5) {
                        var1 = Scheme.numLEq.apply2(var1, var2);

                        try {
                           var5 = ((Boolean)var1).booleanValue();
                        } catch (ClassCastException var7) {
                           throw new WrongType(var7, "x", -2, var1);
                        }

                        var4 = var5;
                        if(var5) {
                           NumberCompare var9 = Scheme.numLEq;

                           CharSequence var10;
                           try {
                              var10 = (CharSequence)var0;
                           } catch (ClassCastException var6) {
                              throw new WrongType(var6, "string-length", 1, var0);
                           }

                           var4 = ((Boolean)var9.apply2(var2, Integer.valueOf(strings.stringLength(var10)))).booleanValue();
                        }
                     }
                  }
               }
            }
         }
      }

      return var4;
   }

   public static Object kmpStep(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      Object var6;
      do {
         ApplyToArgs var7 = Scheme.applyToArgs;

         CharSequence var8;
         try {
            var8 = (CharSequence)var0;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-ref", 1, var0);
         }

         var6 = AddOp.$Pl.apply2(var3, var5);

         int var9;
         try {
            var9 = ((Number)var6).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-ref", 2, var6);
         }

         if(var7.apply3(var4, var2, Char.make(strings.stringRef(var8, var9))) != Boolean.FALSE) {
            return AddOp.$Pl.apply2(var3, Lit1);
         }

         FVector var14;
         try {
            var14 = (FVector)var1;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "vector-ref", 1, var1);
         }

         try {
            var9 = ((Number)var3).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "vector-ref", 2, var3);
         }

         var6 = vectors.vectorRef(var14, var9);
         var3 = var6;
      } while(Scheme.numEqu.apply2(var6, Lit13) == Boolean.FALSE);

      return Lit0;
   }

   static String lambda17(Object var0) {
      return "";
   }

   static String lambda18(Object var0) {
      return "";
   }

   public static Object lambda222buildit(Object var0, Object var1) {
      srfi13.frame96 var2 = new srfi13.frame96();
      var2.final = var1;
      return var2.lambda223recur(var0);
   }

   static boolean lambda27(Object var0) {
      boolean var2 = numbers.isInteger(var0);
      boolean var1 = var2;
      if(var2) {
         var2 = numbers.isExact(var0);
         var1 = var2;
         if(var2) {
            var1 = ((Boolean)Scheme.numLEq.apply2(Lit0, var0)).booleanValue();
         }
      }

      return var1;
   }

   public static Object makeKmpRestartVector$V(Object var0, Object[] var1) {
      srfi13.frame87 var3 = new srfi13.frame87();
      var3.pattern = var0;
      LList var4 = LList.makeList(var1, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var42 = loc$let$Mnoptionals$St;

      Object var6;
      try {
         var6 = var42.get();
      } catch (UnboundLocationException var41) {
         var41.setLine("srfi13.scm", 1397, 3);
         throw var41;
      }

      ApplyToArgs var43 = Scheme.applyToArgs;
      ApplyToArgs var2 = Scheme.applyToArgs;
      var42 = loc$c$Eq;

      Object var7;
      try {
         var7 = var42.get();
      } catch (UnboundLocationException var40) {
         var40.setLine("srfi13.scm", 1398, 20);
         throw var40;
      }

      ModuleMethod var8 = characters.char$Eq$Qu;
      var42 = loc$c$Eq;

      try {
         var0 = var42.get();
      } catch (UnboundLocationException var39) {
         var39.setLine("srfi13.scm", 1398, 43);
         throw var39;
      }

      Boolean var45;
      if(misc.isProcedure(var0)) {
         var45 = Boolean.TRUE;
      } else {
         var45 = Boolean.FALSE;
      }

      var0 = var2.apply3(var7, var8, var45);
      var2 = Scheme.applyToArgs;
      ApplyToArgs var52 = Scheme.applyToArgs;
      Location var54 = loc$start;

      Object var53;
      try {
         var53 = var54.get();
      } catch (UnboundLocationException var38) {
         var38.setLine("srfi13.scm", 1399, 7);
         throw var38;
      }

      Location var9 = loc$end;

      Object var55;
      try {
         var55 = var9.get();
      } catch (UnboundLocationException var37) {
         var37.setLine("srfi13.scm", 1399, 14);
         throw var37;
      }

      var7 = var43.apply2(var0, var2.apply2(var52.apply2(var53, var55), var3.lambda$Fn197));
      AddOp var49 = AddOp.$Mn;
      Location var44 = loc$end;

      Object var46;
      try {
         var46 = var44.get();
      } catch (UnboundLocationException var36) {
         var36.setLine("srfi13.scm", 1402, 22);
         throw var36;
      }

      Location var47 = loc$start;

      Object var48;
      try {
         var48 = var47.get();
      } catch (UnboundLocationException var35) {
         var35.setLine("srfi13.scm", 1402, 26);
         throw var35;
      }

      var0 = var49.apply2(var46, var48);

      int var16;
      try {
         var16 = ((Number)var0).intValue();
      } catch (ClassCastException var34) {
         throw new WrongType(var34, "make-vector", 1, var0);
      }

      FVector var56 = vectors.makeVector(var16, Lit13);
      if(Scheme.numGrt.apply2(var0, Lit0) != Boolean.FALSE) {
         var55 = AddOp.$Mn.apply2(var0, Lit1);
         var46 = var3.pattern;

         CharSequence var50;
         try {
            var50 = (CharSequence)var46;
         } catch (ClassCastException var33) {
            throw new WrongType(var33, "string-ref", 1, var46);
         }

         var44 = loc$start;

         try {
            var46 = var44.get();
         } catch (UnboundLocationException var32) {
            var32.setLine("srfi13.scm", 1406, 27);
            throw var32;
         }

         try {
            var16 = ((Number)var46).intValue();
         } catch (ClassCastException var31) {
            throw new WrongType(var31, "string-ref", 2, var46);
         }

         char var64 = strings.stringRef(var50, var16);
         var48 = Lit0;
         var0 = Lit13;
         var44 = loc$start;

         try {
            var46 = var44.get();
         } catch (UnboundLocationException var30) {
            var30.setLine("srfi13.scm", 1410, 6);
            throw var30;
         }

         label190:
         while(Scheme.numLss.apply2(var48, var55) != Boolean.FALSE) {
            int var17;
            Object var57;
            for(; Scheme.numEqu.apply2(var0, Lit13) == Boolean.FALSE; var0 = vectors.vectorRef(var56, var17)) {
               ApplyToArgs var10 = Scheme.applyToArgs;
               Location var11 = loc$c$Eq;

               try {
                  var57 = var11.get();
               } catch (UnboundLocationException var28) {
                  var28.setLine("srfi13.scm", 1422, 7);
                  throw var28;
               }

               Object var12 = var3.pattern;

               CharSequence var13;
               try {
                  var13 = (CharSequence)var12;
               } catch (ClassCastException var27) {
                  throw new WrongType(var27, "string-ref", 1, var12);
               }

               try {
                  var17 = ((Number)var46).intValue();
               } catch (ClassCastException var26) {
                  throw new WrongType(var26, "string-ref", 2, var46);
               }

               Char var60 = Char.make(strings.stringRef(var13, var17));
               Object var14 = var3.pattern;

               try {
                  var13 = (CharSequence)var14;
               } catch (ClassCastException var25) {
                  throw new WrongType(var25, "string-ref", 1, var14);
               }

               AddOp var63 = AddOp.$Pl;
               Location var15 = loc$start;

               Object var62;
               try {
                  var62 = var15.get();
               } catch (UnboundLocationException var24) {
                  var24.setLine("srfi13.scm", 1422, 59);
                  throw var24;
               }

               var14 = var63.apply2(var0, var62);

               try {
                  var17 = ((Number)var14).intValue();
               } catch (ClassCastException var23) {
                  throw new WrongType(var23, "string-ref", 2, var14);
               }

               if(var10.apply3(var57, var60, Char.make(strings.stringRef(var13, var17))) != Boolean.FALSE) {
                  var48 = AddOp.$Pl.apply2(Lit1, var48);
                  var0 = AddOp.$Pl.apply2(Lit1, var0);

                  try {
                     var17 = ((Number)var48).intValue();
                  } catch (ClassCastException var18) {
                     throw new WrongType(var18, "vector-set!", 2, var48);
                  }

                  vectors.vectorSet$Ex(var56, var17, var0);
                  var46 = AddOp.$Pl.apply2(var46, Lit1);
                  continue label190;
               }

               try {
                  var17 = ((Number)var0).intValue();
               } catch (ClassCastException var29) {
                  throw new WrongType(var29, "vector-ref", 2, var0);
               }
            }

            var48 = AddOp.$Pl.apply2(Lit1, var48);
            ApplyToArgs var51 = Scheme.applyToArgs;
            Location var58 = loc$c$Eq;

            Object var59;
            try {
               var59 = var58.get();
            } catch (UnboundLocationException var22) {
               var22.setLine("srfi13.scm", 1418, 18);
               throw var22;
            }

            var57 = var3.pattern;

            CharSequence var61;
            try {
               var61 = (CharSequence)var57;
            } catch (ClassCastException var21) {
               throw new WrongType(var21, "string-ref", 1, var57);
            }

            var57 = AddOp.$Pl.apply2(var46, Lit1);

            try {
               var17 = ((Number)var57).intValue();
            } catch (ClassCastException var20) {
               throw new WrongType(var20, "string-ref", 2, var57);
            }

            if(var51.apply3(var59, Char.make(strings.stringRef(var61, var17)), Char.make(var64)) == Boolean.FALSE) {
               try {
                  var17 = ((Number)var48).intValue();
               } catch (ClassCastException var19) {
                  throw new WrongType(var19, "vector-set!", 2, var48);
               }

               vectors.vectorSet$Ex(var56, var17, Lit0);
            }

            var0 = Lit0;
            var46 = AddOp.$Pl.apply2(var46, Lit1);
         }
      }

      return var5.apply4(var6, var4, var7, var56);
   }

   public static CharSequence reverseList$To$String(Object var0) {
      LList var2;
      try {
         var2 = (LList)var0;
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "length", 1, var0);
      }

      int var6 = lists.length(var2);
      CharSequence var4 = strings.makeString(var6);
      Integer var3 = Integer.valueOf(var6 - 1);
      Object var11 = var0;

      for(var0 = var3; lists.isPair(var11); var11 = lists.cdr.apply1(var11)) {
         CharSeq var5;
         try {
            var5 = (CharSeq)var4;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string-set!", 1, var4);
         }

         try {
            var6 = ((Number)var0).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-set!", 2, var0);
         }

         Object var12 = lists.car.apply1(var11);

         char var1;
         try {
            var1 = ((Char)var12).charValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-set!", 3, var12);
         }

         strings.stringSet$Ex(var5, var6, var1);
         var0 = AddOp.$Mn.apply2(var0, Lit1);
      }

      return var4;
   }

   public static Object string$Eq$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame31 var3 = new srfi13.frame31();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn68, var3.lambda$Fn69);
   }

   public static Object string$Gr$Eq$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame41 var3 = new srfi13.frame41();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn96, var3.lambda$Fn97);
   }

   public static Object string$Gr$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame37 var3 = new srfi13.frame37();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn85, var3.lambda$Fn86);
   }

   public static Object string$Ls$Eq$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame39 var3 = new srfi13.frame39();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn91, var3.lambda$Fn92);
   }

   public static Object string$Ls$Gr$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame33 var3 = new srfi13.frame33();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn74, var3.lambda$Fn75);
   }

   public static Object string$Ls$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame35 var3 = new srfi13.frame35();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn79, var3.lambda$Fn80);
   }

   public static Object string$To$List$V(Object var0, Object[] var1) {
      srfi13.frame91 var2 = new srfi13.frame91();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn204, var2.lambda$Fn205);
   }

   public static Object stringAny$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame10 var3 = new srfi13.frame10();
      var3.criterion = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn25, var3.lambda$Fn26);
   }

   public static Object stringAppend$SlShared$V(Object[] var0) {
      return stringConcatenate$SlShared(LList.makeList(var0, 0));
   }

   public static Object stringCi$Eq$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame43 var3 = new srfi13.frame43();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn101, var3.lambda$Fn102);
   }

   public static Object stringCi$Gr$Eq$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame53 var3 = new srfi13.frame53();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn129, var3.lambda$Fn130);
   }

   public static Object stringCi$Gr$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame49 var3 = new srfi13.frame49();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn118, var3.lambda$Fn119);
   }

   public static Object stringCi$Ls$Eq$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame51 var3 = new srfi13.frame51();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn124, var3.lambda$Fn125);
   }

   public static Object stringCi$Ls$Gr$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame45 var3 = new srfi13.frame45();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn107, var3.lambda$Fn108);
   }

   public static Object stringCi$Ls$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame47 var3 = new srfi13.frame47();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn112, var3.lambda$Fn113);
   }

   public static Object stringCompare$V(Object var0, Object var1, Object var2, Object var3, Object var4, Object[] var5) {
      srfi13.frame27 var6 = new srfi13.frame27();
      var6.s1 = var0;
      var6.s2 = var1;
      var6.proc$Ls = var2;
      var6.proc$Eq = var3;
      var6.proc$Gr = var4;
      var6.maybe$Mnstarts$Plends = LList.makeList(var5, 0);
      ApplyToArgs var10 = Scheme.applyToArgs;
      Location var11 = loc$check$Mnarg;

      try {
         var1 = var11.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 726, 3);
         throw var9;
      }

      var10.apply4(var1, misc.procedure$Qu, var6.proc$Ls, string$Mncompare);
      var10 = Scheme.applyToArgs;
      var11 = loc$check$Mnarg;

      try {
         var1 = var11.get();
      } catch (UnboundLocationException var8) {
         var8.setLine("srfi13.scm", 727, 3);
         throw var8;
      }

      var10.apply4(var1, misc.procedure$Qu, var6.proc$Eq, string$Mncompare);
      var10 = Scheme.applyToArgs;
      var11 = loc$check$Mnarg;

      try {
         var1 = var11.get();
      } catch (UnboundLocationException var7) {
         var7.setLine("srfi13.scm", 728, 3);
         throw var7;
      }

      var10.apply4(var1, misc.procedure$Qu, var6.proc$Gr, string$Mncompare);
      return call_with_values.callWithValues(var6.lambda$Fn60, var6.lambda$Fn61);
   }

   public static Object stringCompareCi$V(Object var0, Object var1, Object var2, Object var3, Object var4, Object[] var5) {
      srfi13.frame29 var6 = new srfi13.frame29();
      var6.s1 = var0;
      var6.s2 = var1;
      var6.proc$Ls = var2;
      var6.proc$Eq = var3;
      var6.proc$Gr = var4;
      var6.maybe$Mnstarts$Plends = LList.makeList(var5, 0);
      ApplyToArgs var10 = Scheme.applyToArgs;
      Location var11 = loc$check$Mnarg;

      try {
         var1 = var11.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 734, 3);
         throw var9;
      }

      var10.apply4(var1, misc.procedure$Qu, var6.proc$Ls, string$Mncompare$Mnci);
      var10 = Scheme.applyToArgs;
      var11 = loc$check$Mnarg;

      try {
         var1 = var11.get();
      } catch (UnboundLocationException var8) {
         var8.setLine("srfi13.scm", 735, 3);
         throw var8;
      }

      var10.apply4(var1, misc.procedure$Qu, var6.proc$Eq, string$Mncompare$Mnci);
      var10 = Scheme.applyToArgs;
      var11 = loc$check$Mnarg;

      try {
         var1 = var11.get();
      } catch (UnboundLocationException var7) {
         var7.setLine("srfi13.scm", 736, 3);
         throw var7;
      }

      var10.apply4(var1, misc.procedure$Qu, var6.proc$Gr, string$Mncompare$Mnci);
      return call_with_values.callWithValues(var6.lambda$Fn64, var6.lambda$Fn65);
   }

   public static CharSequence stringConcatenate(Object var0) {
      Object var1 = Lit0;

      Object var2;
      Object var3;
      for(var2 = var0; lists.isPair(var2); var2 = var3) {
         var3 = lists.cdr.apply1(var2);
         AddOp var4 = AddOp.$Pl;
         var2 = lists.car.apply1(var2);

         CharSequence var5;
         try {
            var5 = (CharSequence)var2;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-length", 1, var2);
         }

         var1 = var4.apply2(var1, Integer.valueOf(strings.stringLength(var5)));
      }

      int var6;
      try {
         var6 = ((Number)var1).intValue();
      } catch (ClassCastException var11) {
         throw new WrongType(var11, "make-string", 1, var1);
      }

      CharSequence var14 = strings.makeString(var6);
      IntNum var13 = Lit0;
      var1 = var0;

      for(var0 = var13; lists.isPair(var1); var1 = lists.cdr.apply1(var1)) {
         var2 = lists.car.apply1(var1);

         CharSequence var15;
         try {
            var15 = (CharSequence)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-length", 1, var2);
         }

         var6 = strings.stringLength(var15);

         int var7;
         try {
            var7 = ((Number)var0).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "%string-copy!", 1, var0);
         }

         try {
            var15 = (CharSequence)var2;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "%string-copy!", 2, var2);
         }

         $PcStringCopy$Ex(var14, var7, var15, 0, var6);
         var0 = AddOp.$Pl.apply2(var0, Integer.valueOf(var6));
      }

      return var14;
   }

   public static Object stringConcatenate$SlShared(Object var0) {
      Object var2 = Lit0;
      Object var1 = Boolean.FALSE;

      int var6;
      while(lists.isPair(var0)) {
         Object var4 = lists.car.apply1(var0);
         Object var3 = lists.cdr.apply1(var0);

         CharSequence var5;
         try {
            var5 = (CharSequence)var4;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "string-length", 1, var4);
         }

         var6 = strings.stringLength(var5);
         if(numbers.isZero(Integer.valueOf(var6))) {
            var0 = var3;
         } else {
            var2 = AddOp.$Pl.apply2(var2, Integer.valueOf(var6));
            if(var1 == Boolean.FALSE) {
               var1 = var0;
            }

            var0 = var3;
         }
      }

      Number var15;
      try {
         var15 = (Number)var2;
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "zero?", 1, var2);
      }

      if(numbers.isZero(var15)) {
         var2 = "";
      } else {
         NumberCompare var16 = Scheme.numEqu;
         var0 = lists.car.apply1(var1);

         CharSequence var18;
         try {
            var18 = (CharSequence)var0;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-length", 1, var0);
         }

         if(var16.apply2(var2, Integer.valueOf(strings.stringLength(var18))) != Boolean.FALSE) {
            return lists.car.apply1(var1);
         }

         try {
            var6 = ((Number)var2).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "make-string", 1, var2);
         }

         CharSequence var17 = strings.makeString(var6);
         var0 = Lit0;

         while(true) {
            var2 = var17;
            if(!lists.isPair(var1)) {
               break;
            }

            var2 = lists.car.apply1(var1);

            try {
               var18 = (CharSequence)var2;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-length", 1, var2);
            }

            var6 = strings.stringLength(var18);

            int var7;
            try {
               var7 = ((Number)var0).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "%string-copy!", 1, var0);
            }

            try {
               var18 = (CharSequence)var2;
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "%string-copy!", 2, var2);
            }

            $PcStringCopy$Ex(var17, var7, var18, 0, var6);
            var1 = lists.cdr.apply1(var1);
            var0 = AddOp.$Pl.apply2(var0, Integer.valueOf(var6));
         }
      }

      return var2;
   }

   public static Object stringConcatenateReverse$SlShared$V(Object var0, Object[] var1) {
      LList var4 = LList.makeList(var1, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var37 = loc$let$Mnoptionals$St;

      Object var6;
      try {
         var6 = var37.get();
      } catch (UnboundLocationException var34) {
         var34.setLine("srfi13.scm", 1630, 3);
         throw var34;
      }

      ApplyToArgs var2 = Scheme.applyToArgs;
      ApplyToArgs var3 = Scheme.applyToArgs;
      var37 = loc$final;

      Object var7;
      try {
         var7 = var37.get();
      } catch (UnboundLocationException var33) {
         var33.setLine("srfi13.scm", 1630, 36);
         throw var33;
      }

      var37 = loc$final;

      Object var39;
      try {
         var39 = var37.get();
      } catch (UnboundLocationException var32) {
         var32.setLine("srfi13.scm", 1630, 55);
         throw var32;
      }

      Boolean var40;
      if(strings.isString(var39)) {
         var40 = Boolean.TRUE;
      } else {
         var40 = Boolean.FALSE;
      }

      Object var42 = var3.apply3(var7, "", var40);
      ApplyToArgs var47 = Scheme.applyToArgs;
      var37 = loc$end;

      Object var8;
      try {
         var8 = var37.get();
      } catch (UnboundLocationException var31) {
         var31.setLine("srfi13.scm", 1631, 8);
         throw var31;
      }

      var37 = loc$final;

      try {
         var39 = var37.get();
      } catch (UnboundLocationException var30) {
         var30.setLine("srfi13.scm", 1631, 28);
         throw var30;
      }

      CharSequence var9;
      try {
         var9 = (CharSequence)var39;
      } catch (ClassCastException var29) {
         throw new WrongType(var29, "string-length", 1, var39);
      }

      int var13 = strings.stringLength(var9);
      var37 = loc$end;

      try {
         var39 = var37.get();
      } catch (UnboundLocationException var28) {
         var28.setLine("srfi13.scm", 1632, 21);
         throw var28;
      }

      boolean var14 = numbers.isInteger(var39);
      if(var14) {
         var37 = loc$end;

         try {
            var39 = var37.get();
         } catch (UnboundLocationException var27) {
            var27.setLine("srfi13.scm", 1633, 19);
            throw var27;
         }

         var14 = numbers.isExact(var39);
         if(var14) {
            NumberCompare var44 = Scheme.numLEq;
            IntNum var49 = Lit0;
            Location var10 = loc$end;

            Object var52;
            try {
               var52 = var10.get();
            } catch (UnboundLocationException var26) {
               var26.setLine("srfi13.scm", 1634, 17);
               throw var26;
            }

            Location var11 = loc$final;

            Object var51;
            try {
               var51 = var11.get();
            } catch (UnboundLocationException var25) {
               var25.setLine("srfi13.scm", 1634, 36);
               throw var25;
            }

            CharSequence var12;
            try {
               var12 = (CharSequence)var51;
            } catch (ClassCastException var24) {
               throw new WrongType(var24, "string-length", 1, var51);
            }

            var39 = var44.apply3(var49, var52, Integer.valueOf(strings.stringLength(var12)));
         } else if(var14) {
            var39 = Boolean.TRUE;
         } else {
            var39 = Boolean.FALSE;
         }
      } else if(var14) {
         var39 = Boolean.TRUE;
      } else {
         var39 = Boolean.FALSE;
      }

      var7 = var2.apply2(var42, var47.apply3(var8, Integer.valueOf(var13), var39));
      Object var38 = Lit0;

      CharSequence var48;
      for(var39 = Boolean.FALSE; lists.isPair(var0); var39 = var42) {
         var42 = lists.car.apply1(var0);

         try {
            var48 = (CharSequence)var42;
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "string-length", 1, var42);
         }

         var13 = strings.stringLength(var48);
         var38 = AddOp.$Pl.apply2(var38, Integer.valueOf(var13));
         var42 = var39;
         if(var39 == Boolean.FALSE) {
            if(numbers.isZero(Integer.valueOf(var13))) {
               var42 = var39;
            } else {
               var42 = var0;
            }
         }

         var0 = lists.cdr.apply1(var0);
      }

      Number var35;
      try {
         var35 = (Number)var38;
      } catch (ClassCastException var22) {
         throw new WrongType(var22, "zero?", 1, var38);
      }

      Location var36;
      if(numbers.isZero(var35)) {
         var36 = loc$final;

         try {
            var0 = var36.get();
         } catch (UnboundLocationException var21) {
            var21.setLine("srfi13.scm", 1645, 41);
            throw var21;
         }

         IntNum var50 = Lit0;
         Location var41 = loc$end;

         try {
            var38 = var41.get();
         } catch (UnboundLocationException var20) {
            var20.setLine("srfi13.scm", 1645, 49);
            throw var20;
         }

         var0 = substring$SlShared$V(var0, var50, new Object[]{var38});
      } else {
         var36 = loc$end;

         try {
            var0 = var36.get();
         } catch (UnboundLocationException var19) {
            var19.setLine("srfi13.scm", 1649, 16);
            throw var19;
         }

         Number var43;
         try {
            var43 = (Number)var0;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "zero?", 1, var0);
         }

         label199: {
            var14 = numbers.isZero(var43);
            if(var14) {
               NumberCompare var46 = Scheme.numEqu;
               var0 = lists.car.apply1(var39);

               try {
                  var48 = (CharSequence)var0;
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "string-length", 1, var0);
               }

               if(var46.apply2(var38, Integer.valueOf(strings.stringLength(var48))) == Boolean.FALSE) {
                  break label199;
               }
            } else if(!var14) {
               break label199;
            }

            var0 = lists.car.apply1(var39);
            return var5.apply4(var6, var4, var7, var0);
         }

         var36 = loc$final;

         try {
            var0 = var36.get();
         } catch (UnboundLocationException var16) {
            var16.setLine("srfi13.scm", 1652, 56);
            throw var16;
         }

         Location var45 = loc$end;

         try {
            var42 = var45.get();
         } catch (UnboundLocationException var15) {
            var15.setLine("srfi13.scm", 1652, 62);
            throw var15;
         }

         var0 = $PcFinishStringConcatenateReverse(var38, var39, var0, var42);
      }

      return var5.apply4(var6, var4, var7, var0);
   }

   public static Object stringConcatenateReverse$V(Object var0, Object[] var1) {
      LList var3 = LList.makeList(var1, 0);
      ApplyToArgs var4 = Scheme.applyToArgs;
      Location var29 = loc$let$Mnoptionals$St;

      Object var5;
      try {
         var5 = var29.get();
      } catch (UnboundLocationException var28) {
         var28.setLine("srfi13.scm", 1617, 3);
         throw var28;
      }

      ApplyToArgs var2 = Scheme.applyToArgs;
      ApplyToArgs var6 = Scheme.applyToArgs;
      var29 = loc$final;

      Object var7;
      try {
         var7 = var29.get();
      } catch (UnboundLocationException var27) {
         var27.setLine("srfi13.scm", 1617, 36);
         throw var27;
      }

      var29 = loc$final;

      Object var30;
      try {
         var30 = var29.get();
      } catch (UnboundLocationException var26) {
         var26.setLine("srfi13.scm", 1617, 55);
         throw var26;
      }

      Boolean var32;
      if(strings.isString(var30)) {
         var32 = Boolean.TRUE;
      } else {
         var32 = Boolean.FALSE;
      }

      Object var35 = var6.apply3(var7, "", var32);
      ApplyToArgs var36 = Scheme.applyToArgs;
      var29 = loc$end;

      Object var8;
      try {
         var8 = var29.get();
      } catch (UnboundLocationException var25) {
         var25.setLine("srfi13.scm", 1618, 8);
         throw var25;
      }

      var29 = loc$final;

      try {
         var30 = var29.get();
      } catch (UnboundLocationException var24) {
         var24.setLine("srfi13.scm", 1618, 28);
         throw var24;
      }

      CharSequence var9;
      try {
         var9 = (CharSequence)var30;
      } catch (ClassCastException var23) {
         throw new WrongType(var23, "string-length", 1, var30);
      }

      int var13 = strings.stringLength(var9);
      var29 = loc$end;

      try {
         var30 = var29.get();
      } catch (UnboundLocationException var22) {
         var22.setLine("srfi13.scm", 1619, 21);
         throw var22;
      }

      boolean var14 = numbers.isInteger(var30);
      if(var14) {
         var29 = loc$end;

         try {
            var30 = var29.get();
         } catch (UnboundLocationException var21) {
            var21.setLine("srfi13.scm", 1620, 19);
            throw var21;
         }

         var14 = numbers.isExact(var30);
         if(var14) {
            NumberCompare var34 = Scheme.numLEq;
            IntNum var39 = Lit0;
            Location var10 = loc$end;

            Object var41;
            try {
               var41 = var10.get();
            } catch (UnboundLocationException var20) {
               var20.setLine("srfi13.scm", 1621, 17);
               throw var20;
            }

            Location var11 = loc$final;

            Object var40;
            try {
               var40 = var11.get();
            } catch (UnboundLocationException var19) {
               var19.setLine("srfi13.scm", 1621, 36);
               throw var19;
            }

            CharSequence var12;
            try {
               var12 = (CharSequence)var40;
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "string-length", 1, var40);
            }

            var30 = var34.apply3(var39, var41, Integer.valueOf(strings.stringLength(var12)));
         } else if(var14) {
            var30 = Boolean.TRUE;
         } else {
            var30 = Boolean.FALSE;
         }
      } else if(var14) {
         var30 = Boolean.TRUE;
      } else {
         var30 = Boolean.FALSE;
      }

      var35 = var2.apply2(var35, var36.apply3(var8, Integer.valueOf(var13), var30));
      var30 = Lit0;

      Object var31;
      for(var31 = var0; lists.isPair(var31); var31 = lists.cdr.apply1(var31)) {
         AddOp var38 = AddOp.$Pl;
         var7 = lists.car.apply1(var31);

         try {
            var9 = (CharSequence)var7;
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "string-length", 1, var7);
         }

         var30 = var38.apply2(var30, Integer.valueOf(strings.stringLength(var9)));
      }

      Location var33 = loc$final;

      try {
         var31 = var33.get();
      } catch (UnboundLocationException var16) {
         var16.setLine("srfi13.scm", 1627, 59);
         throw var16;
      }

      Location var37 = loc$end;

      try {
         var7 = var37.get();
      } catch (UnboundLocationException var15) {
         var15.setLine("srfi13.scm", 1627, 65);
         throw var15;
      }

      return var4.apply4(var5, var3, var35, $PcFinishStringConcatenateReverse(var30, var0, var31, var7));
   }

   public static Object stringContains$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame83 var3 = new srfi13.frame83();
      var3.text = var0;
      var3.pattern = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn189, var3.lambda$Fn190);
   }

   public static Object stringContainsCi$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame85 var3 = new srfi13.frame85();
      var3.text = var0;
      var3.pattern = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn193, var3.lambda$Fn194);
   }

   public static Object stringCopy$Ex(Object var0, int var1, CharSequence var2) {
      return stringCopy$Ex(var0, var1, var2, 0);
   }

   public static Object stringCopy$Ex(Object var0, int var1, CharSequence var2, int var3) {
      return stringCopy$Ex(var0, var1, var2, var3, var2.length());
   }

   public static Object stringCopy$Ex(Object var0, int var1, CharSequence var2, int var3, int var4) {
      $PcCheckBounds(string$Mncopy$Ex, var2, var3, var4);
      ModuleMethod var5 = string$Mncopy$Ex;

      CharSequence var6;
      try {
         var6 = (CharSequence)var0;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "%check-substring-spec", 1, var0);
      }

      $PcCheckSubstringSpec(var5, var6, var1, var4 - var3 + var1);

      CharSequence var9;
      try {
         var9 = (CharSequence)var0;
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "%string-copy!", 0, var0);
      }

      return $PcStringCopy$Ex(var9, var1, var2, var3, var4);
   }

   public static Object stringCopy$V(Object var0, Object[] var1) {
      srfi13.frame2 var2 = new srfi13.frame2();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn7, var2.lambda$Fn8);
   }

   public static Object stringCount$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame81 var3 = new srfi13.frame81();
      var3.s = var0;
      var3.criterion = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn185, var3.lambda$Fn186);
   }

   public static Object stringDelete$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame73 var3 = new srfi13.frame73();
      var3.criterion = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn167, var3.lambda$Fn168);
   }

   public static Object stringDowncase$Ex$V(Object var0, Object[] var1) {
      srfi13.frame61 var2 = new srfi13.frame61();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn145, var2.lambda$Fn146);
   }

   public static Object stringDowncase$V(Object var0, Object[] var1) {
      srfi13.frame60 var2 = new srfi13.frame60();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn143, var2.lambda$Fn144);
   }

   public static Object stringDrop(CharSequence var0, Object var1) {
      srfi13.frame66 var2 = new srfi13.frame66();
      var2.n = var1;
      var2.len = strings.stringLength(var0);
      ApplyToArgs var7 = Scheme.applyToArgs;
      Location var3 = loc$check$Mnarg;

      Object var8;
      try {
         var8 = var3.get();
      } catch (UnboundLocationException var6) {
         var6.setLine("srfi13.scm", 1010, 5);
         throw var6;
      }

      var7.apply4(var8, var2.lambda$Fn153, var2.n, string$Mndrop);
      var1 = var2.n;

      int var4;
      try {
         var4 = ((Number)var1).intValue();
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "%substring/shared", 1, var1);
      }

      return $PcSubstring$SlShared(var0, var4, var2.len);
   }

   public static Object stringDropRight(CharSequence var0, Object var1) {
      srfi13.frame67 var2 = new srfi13.frame67();
      var2.n = var1;
      var2.len = strings.stringLength(var0);
      ApplyToArgs var7 = Scheme.applyToArgs;
      Location var3 = loc$check$Mnarg;

      Object var8;
      try {
         var8 = var3.get();
      } catch (UnboundLocationException var6) {
         var6.setLine("srfi13.scm", 1016, 5);
         throw var6;
      }

      var7.apply4(var8, var2.lambda$Fn154, var2.n, string$Mndrop$Mnright);
      var1 = AddOp.$Mn.apply2(Integer.valueOf(var2.len), var2.n);

      int var4;
      try {
         var4 = ((Number)var1).intValue();
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "%substring/shared", 2, var1);
      }

      return $PcSubstring$SlShared(var0, 0, var4);
   }

   public static Object stringEvery$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame9 var3 = new srfi13.frame9();
      var3.criterion = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn23, var3.lambda$Fn24);
   }

   public static Object stringFill$Ex$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame82 var3 = new srfi13.frame82();
      var3.s = var0;
      var3.char = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$check$Mnarg;

      try {
         var1 = var6.get();
      } catch (UnboundLocationException var4) {
         var4.setLine("srfi13.scm", 1270, 3);
         throw var4;
      }

      var5.apply4(var1, characters.char$Qu, var3.char, string$Mnfill$Ex);
      return call_with_values.callWithValues(var3.lambda$Fn187, var3.lambda$Fn188);
   }

   public static Object stringFilter$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame75 var3 = new srfi13.frame75();
      var3.criterion = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn172, var3.lambda$Fn173);
   }

   public static Object stringFold$V(Object var0, Object var1, Object var2, Object[] var3) {
      srfi13.frame5 var4 = new srfi13.frame5();
      var4.kons = var0;
      var4.knil = var1;
      var4.s = var2;
      var4.maybe$Mnstart$Plend = LList.makeList(var3, 0);
      ApplyToArgs var6 = Scheme.applyToArgs;
      Location var7 = loc$check$Mnarg;

      try {
         var1 = var7.get();
      } catch (UnboundLocationException var5) {
         var5.setLine("srfi13.scm", 295, 3);
         throw var5;
      }

      var6.apply4(var1, misc.procedure$Qu, var4.kons, string$Mnfold);
      return call_with_values.callWithValues(var4.lambda$Fn13, var4.lambda$Fn14);
   }

   public static Object stringFoldRight$V(Object var0, Object var1, Object var2, Object[] var3) {
      srfi13.frame6 var4 = new srfi13.frame6();
      var4.kons = var0;
      var4.knil = var1;
      var4.s = var2;
      var4.maybe$Mnstart$Plend = LList.makeList(var3, 0);
      ApplyToArgs var6 = Scheme.applyToArgs;
      Location var7 = loc$check$Mnarg;

      try {
         var1 = var7.get();
      } catch (UnboundLocationException var5) {
         var5.setLine("srfi13.scm", 302, 3);
         throw var5;
      }

      var6.apply4(var1, misc.procedure$Qu, var4.kons, string$Mnfold$Mnright);
      return call_with_values.callWithValues(var4.lambda$Fn15, var4.lambda$Fn16);
   }

   public static Object stringForEach$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame7 var3 = new srfi13.frame7();
      var3.proc = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$check$Mnarg;

      try {
         var1 = var6.get();
      } catch (UnboundLocationException var4) {
         var4.setLine("srfi13.scm", 468, 3);
         throw var4;
      }

      var5.apply4(var1, misc.procedure$Qu, var3.proc, string$Mnfor$Mneach);
      return call_with_values.callWithValues(var3.lambda$Fn19, var3.lambda$Fn20);
   }

   public static Object stringForEachIndex$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame8 var3 = new srfi13.frame8();
      var3.proc = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$check$Mnarg;

      try {
         var1 = var6.get();
      } catch (UnboundLocationException var4) {
         var4.setLine("srfi13.scm", 476, 3);
         throw var4;
      }

      var5.apply4(var1, misc.procedure$Qu, var3.proc, string$Mnfor$Mneach$Mnindex);
      return call_with_values.callWithValues(var3.lambda$Fn21, var3.lambda$Fn22);
   }

   public static Object stringHash$V(Object var0, Object[] var1) {
      srfi13.frame56 var2 = new srfi13.frame56();
      var2.s = var0;
      LList var22 = LList.makeList(var1, 0);
      ApplyToArgs var3 = Scheme.applyToArgs;
      Location var21 = loc$let$Mnoptionals$St;

      Object var4;
      try {
         var4 = var21.get();
      } catch (UnboundLocationException var20) {
         var20.setLine("srfi13.scm", 907, 3);
         throw var20;
      }

      ApplyToArgs var5 = Scheme.applyToArgs;
      ApplyToArgs var6 = Scheme.applyToArgs;
      var21 = loc$bound;

      Object var7;
      try {
         var7 = var21.get();
      } catch (UnboundLocationException var19) {
         var19.setLine("srfi13.scm", 907, 42);
         throw var19;
      }

      IntNum var8 = Lit7;
      var21 = loc$bound;

      try {
         var0 = var21.get();
      } catch (UnboundLocationException var18) {
         var18.setLine("srfi13.scm", 907, 72);
         throw var18;
      }

      boolean var11 = numbers.isInteger(var0);
      if(var11) {
         var21 = loc$bound;

         try {
            var0 = var21.get();
         } catch (UnboundLocationException var17) {
            var17.setLine("srfi13.scm", 908, 21);
            throw var17;
         }

         var11 = numbers.isExact(var0);
         if(var11) {
            NumberCompare var23 = Scheme.numLEq;
            IntNum var9 = Lit0;
            Location var10 = loc$bound;

            Object var28;
            try {
               var28 = var10.get();
            } catch (UnboundLocationException var16) {
               var16.setLine("srfi13.scm", 909, 19);
               throw var16;
            }

            var0 = var23.apply2(var9, var28);
         } else if(var11) {
            var0 = Boolean.TRUE;
         } else {
            var0 = Boolean.FALSE;
         }
      } else if(var11) {
         var0 = Boolean.TRUE;
      } else {
         var0 = Boolean.FALSE;
      }

      var0 = var6.apply3(var7, var8, var0);
      Location var26 = loc$rest;

      Object var25;
      try {
         var25 = var26.get();
      } catch (UnboundLocationException var15) {
         var15.setLine("srfi13.scm", 910, 7);
         throw var15;
      }

      Object var24 = var5.apply2(var0, var25);
      var21 = loc$bound;

      try {
         var0 = var21.get();
      } catch (UnboundLocationException var14) {
         var14.setLine("srfi13.scm", 911, 29);
         throw var14;
      }

      Number var27;
      try {
         var27 = (Number)var0;
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "zero?", 1, var0);
      }

      if(numbers.isZero(var27)) {
         var0 = Lit8;
      } else {
         var21 = loc$bound;

         try {
            var0 = var21.get();
         } catch (UnboundLocationException var12) {
            var12.setLine("srfi13.scm", 911, 18);
            throw var12;
         }
      }

      var2.bound = var0;
      return var3.apply4(var4, var22, var24, call_with_values.callWithValues(var2.lambda$Fn134, var2.lambda$Fn135));
   }

   public static Object stringHashCi$V(Object var0, Object[] var1) {
      srfi13.frame57 var2 = new srfi13.frame57();
      var2.s = var0;
      LList var22 = LList.makeList(var1, 0);
      ApplyToArgs var3 = Scheme.applyToArgs;
      Location var21 = loc$let$Mnoptionals$St;

      Object var4;
      try {
         var4 = var21.get();
      } catch (UnboundLocationException var20) {
         var20.setLine("srfi13.scm", 916, 3);
         throw var20;
      }

      ApplyToArgs var5 = Scheme.applyToArgs;
      ApplyToArgs var6 = Scheme.applyToArgs;
      var21 = loc$bound;

      Object var7;
      try {
         var7 = var21.get();
      } catch (UnboundLocationException var19) {
         var19.setLine("srfi13.scm", 916, 42);
         throw var19;
      }

      IntNum var8 = Lit9;
      var21 = loc$bound;

      try {
         var0 = var21.get();
      } catch (UnboundLocationException var18) {
         var18.setLine("srfi13.scm", 916, 72);
         throw var18;
      }

      boolean var11 = numbers.isInteger(var0);
      if(var11) {
         var21 = loc$bound;

         try {
            var0 = var21.get();
         } catch (UnboundLocationException var17) {
            var17.setLine("srfi13.scm", 917, 21);
            throw var17;
         }

         var11 = numbers.isExact(var0);
         if(var11) {
            NumberCompare var23 = Scheme.numLEq;
            IntNum var9 = Lit0;
            Location var10 = loc$bound;

            Object var28;
            try {
               var28 = var10.get();
            } catch (UnboundLocationException var16) {
               var16.setLine("srfi13.scm", 918, 19);
               throw var16;
            }

            var0 = var23.apply2(var9, var28);
         } else if(var11) {
            var0 = Boolean.TRUE;
         } else {
            var0 = Boolean.FALSE;
         }
      } else if(var11) {
         var0 = Boolean.TRUE;
      } else {
         var0 = Boolean.FALSE;
      }

      var0 = var6.apply3(var7, var8, var0);
      Location var26 = loc$rest;

      Object var25;
      try {
         var25 = var26.get();
      } catch (UnboundLocationException var15) {
         var15.setLine("srfi13.scm", 919, 7);
         throw var15;
      }

      Object var24 = var5.apply2(var0, var25);
      var21 = loc$bound;

      try {
         var0 = var21.get();
      } catch (UnboundLocationException var14) {
         var14.setLine("srfi13.scm", 920, 29);
         throw var14;
      }

      Number var27;
      try {
         var27 = (Number)var0;
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "zero?", 1, var0);
      }

      if(numbers.isZero(var27)) {
         var0 = Lit10;
      } else {
         var21 = loc$bound;

         try {
            var0 = var21.get();
         } catch (UnboundLocationException var12) {
            var12.setLine("srfi13.scm", 920, 18);
            throw var12;
         }
      }

      var2.bound = var0;
      return var3.apply4(var4, var22, var24, call_with_values.callWithValues(var2.lambda$Fn136, var2.lambda$Fn137));
   }

   public static Object stringIndex$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame77 var3 = new srfi13.frame77();
      var3.str = var0;
      var3.criterion = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn177, var3.lambda$Fn178);
   }

   public static Object stringIndexRight$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame78 var3 = new srfi13.frame78();
      var3.str = var0;
      var3.criterion = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn179, var3.lambda$Fn180);
   }

   public static Object stringJoin$V(Object var0, Object[] var1) {
      LList var2 = LList.makeList(var1, 0);
      ApplyToArgs var3 = Scheme.applyToArgs;
      Location var16 = loc$let$Mnoptionals$St;

      Object var4;
      try {
         var4 = var16.get();
      } catch (UnboundLocationException var15) {
         var15.setLine("srfi13.scm", 1852, 3);
         throw var15;
      }

      ApplyToArgs var5 = Scheme.applyToArgs;
      ApplyToArgs var6 = Scheme.applyToArgs;
      var16 = loc$delim;

      Object var7;
      try {
         var7 = var16.get();
      } catch (UnboundLocationException var14) {
         var14.setLine("srfi13.scm", 1852, 34);
         throw var14;
      }

      var16 = loc$delim;

      Object var18;
      try {
         var18 = var16.get();
      } catch (UnboundLocationException var13) {
         var13.setLine("srfi13.scm", 1852, 54);
         throw var13;
      }

      Boolean var19;
      if(strings.isString(var18)) {
         var19 = Boolean.TRUE;
      } else {
         var19 = Boolean.FALSE;
      }

      var18 = var6.apply3(var7, " ", var19);
      var6 = Scheme.applyToArgs;
      Location var23 = loc$grammar;

      try {
         var7 = var23.get();
      } catch (UnboundLocationException var12) {
         var12.setLine("srfi13.scm", 1853, 6);
         throw var12;
      }

      var18 = var5.apply2(var18, var6.apply2(var7, Lit15));
      Location var17;
      if(lists.isPair(var0)) {
         Location var20 = loc$grammar;

         Object var21;
         try {
            var21 = var20.get();
         } catch (UnboundLocationException var11) {
            var11.setLine("srfi13.scm", 1862, 14);
            throw var11;
         }

         label91: {
            label90: {
               Object var22 = Scheme.isEqv.apply2(var21, Lit15);
               if(var22 != Boolean.FALSE) {
                  if(var22 != Boolean.FALSE) {
                     break label90;
                  }
               } else if(Scheme.isEqv.apply2(var21, Lit16) != Boolean.FALSE) {
                  break label90;
               }

               if(Scheme.isEqv.apply2(var21, Lit17) != Boolean.FALSE) {
                  var0 = lambda222buildit(var0, LList.Empty);
               } else if(Scheme.isEqv.apply2(var21, Lit18) != Boolean.FALSE) {
                  var21 = lists.car.apply1(var0);
                  var0 = lists.cdr.apply1(var0);
                  Location var24 = loc$delim;

                  try {
                     var22 = var24.get();
                  } catch (UnboundLocationException var10) {
                     var10.setLine("srfi13.scm", 1870, 53);
                     throw var10;
                  }

                  var0 = lists.cons(var21, lambda222buildit(var0, LList.list1(var22)));
               } else {
                  var17 = loc$grammar;

                  try {
                     var0 = var17.get();
                  } catch (UnboundLocationException var9) {
                     var9.setLine("srfi13.scm", 1873, 9);
                     throw var9;
                  }

                  var0 = misc.error$V("Illegal join grammar", new Object[]{var0, string$Mnjoin});
               }
               break label91;
            }

            var0 = lists.cons(lists.car.apply1(var0), lambda222buildit(lists.cdr.apply1(var0), LList.Empty));
         }

         var0 = stringConcatenate(var0);
      } else if(!lists.isNull(var0)) {
         var0 = misc.error$V("STRINGS parameter not list.", new Object[]{var0, string$Mnjoin});
      } else {
         var17 = loc$grammar;

         try {
            var0 = var17.get();
         } catch (UnboundLocationException var8) {
            var8.setLine("srfi13.scm", 1880, 13);
            throw var8;
         }

         if(var0 == Lit16) {
            var0 = misc.error$V("Empty list cannot be joined with STRICT-INFIX grammar.", new Object[]{string$Mnjoin});
         } else {
            var0 = "";
         }
      }

      return var3.apply4(var4, var2, var18, var0);
   }

   public static Object stringKmpPartialSearch$V(Object var0, Object var1, Object var2, Object var3, Object[] var4) {
      srfi13.frame88 var5 = new srfi13.frame88();
      var5.s = var2;
      LList var6 = LList.makeList(var4, 0);
      ApplyToArgs var40 = Scheme.applyToArgs;
      Location var43 = loc$check$Mnarg;

      Object var44;
      try {
         var44 = var43.get();
      } catch (UnboundLocationException var39) {
         var39.setLine("srfi13.scm", 1464, 3);
         throw var39;
      }

      var40.apply4(var44, vectors.vector$Qu, var1, string$Mnkmp$Mnpartial$Mnsearch);
      ApplyToArgs var7 = Scheme.applyToArgs;
      Location var41 = loc$let$Mnoptionals$St;

      Object var8;
      try {
         var8 = var41.get();
      } catch (UnboundLocationException var38) {
         var38.setLine("srfi13.scm", 1465, 3);
         throw var38;
      }

      ApplyToArgs var45 = Scheme.applyToArgs;
      ApplyToArgs var9 = Scheme.applyToArgs;
      var41 = loc$c$Eq;

      Object var10;
      try {
         var10 = var41.get();
      } catch (UnboundLocationException var37) {
         var37.setLine("srfi13.scm", 1466, 6);
         throw var37;
      }

      ModuleMethod var11 = characters.char$Eq$Qu;
      var41 = loc$c$Eq;

      try {
         var2 = var41.get();
      } catch (UnboundLocationException var36) {
         var36.setLine("srfi13.scm", 1466, 34);
         throw var36;
      }

      Boolean var42;
      if(misc.isProcedure(var2)) {
         var42 = Boolean.TRUE;
      } else {
         var42 = Boolean.FALSE;
      }

      Object var49 = var9.apply3(var10, var11, var42);
      ApplyToArgs var53 = Scheme.applyToArgs;
      var41 = loc$p$Mnstart;

      Object var51;
      try {
         var51 = var41.get();
      } catch (UnboundLocationException var35) {
         var35.setLine("srfi13.scm", 1467, 6);
         throw var35;
      }

      IntNum var12 = Lit0;
      var41 = loc$p$Mnstart;

      try {
         var2 = var41.get();
      } catch (UnboundLocationException var34) {
         var34.setLine("srfi13.scm", 1467, 32);
         throw var34;
      }

      boolean var17 = numbers.isInteger(var2);
      Object var64;
      Location var14;
      if(var17) {
         var41 = loc$p$Mnstart;

         try {
            var2 = var41.get();
         } catch (UnboundLocationException var33) {
            var33.setLine("srfi13.scm", 1467, 49);
            throw var33;
         }

         var17 = numbers.isExact(var2);
         if(var17) {
            NumberCompare var46 = Scheme.numLEq;
            IntNum var13 = Lit0;
            var14 = loc$p$Mnstart;

            try {
               var64 = var14.get();
            } catch (UnboundLocationException var32) {
               var32.setLine("srfi13.scm", 1467, 64);
               throw var32;
            }

            var2 = var46.apply2(var13, var64);
         } else if(var17) {
            var2 = Boolean.TRUE;
         } else {
            var2 = Boolean.FALSE;
         }
      } else if(var17) {
         var2 = Boolean.TRUE;
      } else {
         var2 = Boolean.FALSE;
      }

      var2 = var53.apply3(var51, var12, var2);
      var53 = Scheme.applyToArgs;
      ApplyToArgs var57 = Scheme.applyToArgs;
      Location var54 = loc$s$Mnstart;

      Object var56;
      try {
         var56 = var54.get();
      } catch (UnboundLocationException var31) {
         var31.setLine("srfi13.scm", 1468, 7);
         throw var31;
      }

      Location var62 = loc$s$Mnend;

      Object var59;
      try {
         var59 = var62.get();
      } catch (UnboundLocationException var30) {
         var30.setLine("srfi13.scm", 1468, 16);
         throw var30;
      }

      var49 = var45.apply3(var49, var2, var53.apply2(var57.apply2(var56, var59), var5.lambda$Fn198));

      FVector var48;
      try {
         var48 = (FVector)var1;
      } catch (ClassCastException var29) {
         throw new WrongType(var29, "vector-length", 1, var1);
      }

      var5.patlen = vectors.vectorLength(var48);
      var40 = Scheme.applyToArgs;
      var43 = loc$check$Mnarg;

      try {
         var44 = var43.get();
      } catch (UnboundLocationException var28) {
         var28.setLine("srfi13.scm", 1472, 7);
         throw var28;
      }

      var40.apply4(var44, var5.lambda$Fn199, var3, string$Mnkmp$Mnpartial$Mnsearch);
      var41 = loc$s$Mnstart;

      try {
         var44 = var41.get();
      } catch (UnboundLocationException var27) {
         var27.setLine("srfi13.scm", 1476, 7);
         throw var27;
      }

      var2 = var3;
      var3 = var44;

      label193:
      while(true) {
         if(Scheme.numEqu.apply2(var2, Integer.valueOf(var5.patlen)) != Boolean.FALSE) {
            var44 = AddOp.$Mn.apply1(var3);
            break;
         }

         NumberCompare var50 = Scheme.numEqu;
         var43 = loc$s$Mnend;

         try {
            var51 = var43.get();
         } catch (UnboundLocationException var26) {
            var26.setLine("srfi13.scm", 1479, 15);
            throw var26;
         }

         var44 = var2;
         if(var50.apply2(var3, var51) != Boolean.FALSE) {
            break;
         }

         var44 = var5.s;

         CharSequence var52;
         try {
            var52 = (CharSequence)var44;
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "string-ref", 1, var44);
         }

         int var15;
         try {
            var15 = ((Number)var3).intValue();
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "string-ref", 2, var3);
         }

         char var63 = strings.stringRef(var52, var15);
         var3 = AddOp.$Pl.apply2(var3, Lit1);

         do {
            var45 = Scheme.applyToArgs;
            Location var58 = loc$c$Eq;

            try {
               var10 = var58.get();
            } catch (UnboundLocationException var21) {
               var21.setLine("srfi13.scm", 1484, 14);
               throw var21;
            }

            Char var55 = Char.make(var63);

            CharSequence var61;
            try {
               var61 = (CharSequence)var0;
            } catch (ClassCastException var20) {
               throw new WrongType(var20, "string-ref", 1, var0);
            }

            AddOp var60 = AddOp.$Pl;
            var14 = loc$p$Mnstart;

            try {
               var64 = var14.get();
            } catch (UnboundLocationException var19) {
               var19.setLine("srfi13.scm", 1484, 42);
               throw var19;
            }

            var59 = var60.apply2(var2, var64);

            int var16;
            try {
               var16 = ((Number)var59).intValue();
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "string-ref", 2, var59);
            }

            if(var45.apply3(var10, var55, Char.make(strings.stringRef(var61, var16))) != Boolean.FALSE) {
               var2 = AddOp.$Pl.apply2(var2, Lit1);
               continue label193;
            }

            FVector var47;
            try {
               var47 = (FVector)var1;
            } catch (ClassCastException var23) {
               throw new WrongType(var23, "vector-ref", 1, var1);
            }

            try {
               var16 = ((Number)var2).intValue();
            } catch (ClassCastException var22) {
               throw new WrongType(var22, "vector-ref", 2, var2);
            }

            var44 = vectors.vectorRef(var47, var16);
            var2 = var44;
         } while(Scheme.numEqu.apply2(var44, Lit13) == Boolean.FALSE);

         var2 = Lit0;
      }

      return var7.apply4(var8, var6, var49, var44);
   }

   public static Object stringMap$Ex$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame4 var3 = new srfi13.frame4();
      var3.proc = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$check$Mnarg;

      try {
         var1 = var6.get();
      } catch (UnboundLocationException var4) {
         var4.setLine("srfi13.scm", 285, 3);
         throw var4;
      }

      var5.apply4(var1, misc.procedure$Qu, var3.proc, string$Mnmap$Ex);
      return call_with_values.callWithValues(var3.lambda$Fn11, var3.lambda$Fn12);
   }

   public static Object stringMap$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame3 var3 = new srfi13.frame3();
      var3.proc = var0;
      var3.s = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$check$Mnarg;

      try {
         var1 = var6.get();
      } catch (UnboundLocationException var4) {
         var4.setLine("srfi13.scm", 271, 3);
         throw var4;
      }

      var5.apply4(var1, misc.procedure$Qu, var3.proc, string$Mnmap);
      return call_with_values.callWithValues(var3.lambda$Fn9, var3.lambda$Fn10);
   }

   public static Object stringPad$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame72 var3 = new srfi13.frame72();
      var3.s = var0;
      var3.n = var1;
      LList var13 = LList.makeList(var2, 0);
      ApplyToArgs var14 = Scheme.applyToArgs;
      Location var11 = loc$let$Mnoptionals$St;

      Object var4;
      try {
         var4 = var11.get();
      } catch (UnboundLocationException var10) {
         var10.setLine("srfi13.scm", 1057, 3);
         throw var10;
      }

      ApplyToArgs var5 = Scheme.applyToArgs;
      Invoke var6 = Invoke.make;
      LangPrimType var7 = LangPrimType.charType;
      Char var8 = Lit12;
      Boolean var12;
      if(characters.isChar(LangPrimType.charType)) {
         var12 = Boolean.TRUE;
      } else {
         var12 = Boolean.FALSE;
      }

      var0 = var6.apply3(var7, var8, var12);
      Location var16 = loc$rest;

      Object var15;
      try {
         var15 = var16.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 1057, 63);
         throw var9;
      }

      return var14.apply4(var4, var13, var5.apply2(var0, var15), call_with_values.callWithValues(var3.lambda$Fn164, var3.lambda$Fn165));
   }

   public static Object stringPadRight$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame71 var3 = new srfi13.frame71();
      var3.s = var0;
      var3.n = var1;
      LList var13 = LList.makeList(var2, 0);
      ApplyToArgs var14 = Scheme.applyToArgs;
      Location var11 = loc$let$Mnoptionals$St;

      Object var4;
      try {
         var4 = var11.get();
      } catch (UnboundLocationException var10) {
         var10.setLine("srfi13.scm", 1045, 3);
         throw var10;
      }

      ApplyToArgs var5 = Scheme.applyToArgs;
      Invoke var6 = Invoke.make;
      LangPrimType var7 = LangPrimType.charType;
      Char var8 = Lit12;
      Boolean var12;
      if(characters.isChar(LangPrimType.charType)) {
         var12 = Boolean.TRUE;
      } else {
         var12 = Boolean.FALSE;
      }

      var0 = var6.apply3(var7, var8, var12);
      Location var16 = loc$rest;

      Object var15;
      try {
         var15 = var16.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 1045, 63);
         throw var9;
      }

      return var14.apply4(var4, var13, var5.apply2(var0, var15), call_with_values.callWithValues(var3.lambda$Fn161, var3.lambda$Fn162));
   }

   public static Object stringParseFinalStart$PlEnd(Object var0, Object var1, Object var2) {
      srfi13.frame0 var3 = new srfi13.frame0();
      var3.proc = var0;
      var3.s = var1;
      var3.args = var2;
      return call_with_values.callWithValues(var3.lambda$Fn3, var3.lambda$Fn4);
   }

   public static Object stringParseStart$PlEnd(Object var0, Object var1, Object var2) {
      srfi13.frame var3 = new srfi13.frame();
      var3.proc = var0;
      var3.s = var1;
      if(!strings.isString(var3.s)) {
         misc.error$V("Non-string value", new Object[]{var3.proc, var3.s});
      }

      var0 = var3.s;

      CharSequence var6;
      try {
         var6 = (CharSequence)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "string-length", 1, var0);
      }

      var3.slen = strings.stringLength(var6);
      if(!lists.isPair(var2)) {
         return misc.values(new Object[]{LList.Empty, Lit0, Integer.valueOf(var3.slen)});
      } else {
         var0 = lists.car.apply1(var2);
         var3.args = lists.cdr.apply1(var2);
         var3.start = var0;
         boolean var4 = numbers.isInteger(var3.start);
         if(var4) {
            var4 = numbers.isExact(var3.start);
            if(var4) {
               if(Scheme.numGEq.apply2(var3.start, Lit0) != Boolean.FALSE) {
                  return call_with_values.callWithValues(var3.lambda$Fn1, var3.lambda$Fn2);
               }
            } else if(var4) {
               return call_with_values.callWithValues(var3.lambda$Fn1, var3.lambda$Fn2);
            }
         } else if(var4) {
            return call_with_values.callWithValues(var3.lambda$Fn1, var3.lambda$Fn2);
         }

         return misc.error$V("Illegal substring START spec", new Object[]{var3.proc, var3.start, var3.s});
      }
   }

   public static Object stringPrefixLength$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame11 var3 = new srfi13.frame11();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn28, var3.lambda$Fn29);
   }

   public static Object stringPrefixLengthCi$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame15 var3 = new srfi13.frame15();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn36, var3.lambda$Fn37);
   }

   public static Object stringReplace$V(Object var0, Object var1, Object var2, Object var3, Object[] var4) {
      srfi13.frame92 var5 = new srfi13.frame92();
      var5.s1 = var0;
      var5.s2 = var1;
      var5.start1 = var2;
      var5.end1 = var3;
      var5.maybe$Mnstart$Plend = LList.makeList(var4, 0);
      checkSubstringSpec(string$Mnreplace, var5.s1, var5.start1, var5.end1);
      return call_with_values.callWithValues(var5.lambda$Fn206, var5.lambda$Fn207);
   }

   public static Object stringReverse$Ex$V(Object var0, Object[] var1) {
      srfi13.frame90 var2 = new srfi13.frame90();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn202, var2.lambda$Fn203);
   }

   public static Object stringReverse$V(Object var0, Object[] var1) {
      srfi13.frame89 var2 = new srfi13.frame89();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn200, var2.lambda$Fn201);
   }

   public static Object stringSkip$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame79 var3 = new srfi13.frame79();
      var3.str = var0;
      var3.criterion = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn181, var3.lambda$Fn182);
   }

   public static Object stringSkipRight$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame80 var3 = new srfi13.frame80();
      var3.str = var0;
      var3.criterion = var1;
      var3.maybe$Mnstart$Plend = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn183, var3.lambda$Fn184);
   }

   public static Object stringSuffixLength$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame13 var3 = new srfi13.frame13();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn32, var3.lambda$Fn33);
   }

   public static Object stringSuffixLengthCi$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame17 var3 = new srfi13.frame17();
      var3.s1 = var0;
      var3.s2 = var1;
      var3.maybe$Mnstarts$Plends = LList.makeList(var2, 0);
      return call_with_values.callWithValues(var3.lambda$Fn40, var3.lambda$Fn41);
   }

   public static CharSequence stringTabulate(Object var0, int var1) {
      ApplyToArgs var3 = Scheme.applyToArgs;
      Location var4 = loc$check$Mnarg;

      Object var11;
      try {
         var11 = var4.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 534, 3);
         throw var9;
      }

      var3.apply4(var11, misc.procedure$Qu, var0, string$Mntabulate);
      var3 = Scheme.applyToArgs;
      var4 = loc$check$Mnarg;

      try {
         var11 = var4.get();
      } catch (UnboundLocationException var8) {
         var8.setLine("srfi13.scm", 535, 3);
         throw var8;
      }

      var3.apply4(var11, lambda$Fn27, Integer.valueOf(var1), string$Mntabulate);
      CharSequence var10 = strings.makeString(var1);
      --var1;

      while(var1 >= 0) {
         CharSeq var5;
         try {
            var5 = (CharSeq)var10;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-set!", 1, var10);
         }

         var11 = Scheme.applyToArgs.apply2(var0, Integer.valueOf(var1));

         char var2;
         try {
            var2 = ((Char)var11).charValue();
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-set!", 3, var11);
         }

         strings.stringSet$Ex(var5, var1, var2);
         --var1;
      }

      return var10;
   }

   public static Object stringTake(Object var0, Object var1) {
      srfi13.frame64 var2 = new srfi13.frame64();
      var2.s = var0;
      var2.n = var1;
      ApplyToArgs var8 = Scheme.applyToArgs;
      Location var9 = loc$check$Mnarg;

      try {
         var1 = var9.get();
      } catch (UnboundLocationException var7) {
         var7.setLine("srfi13.scm", 995, 3);
         throw var7;
      }

      var8.apply4(var1, strings.string$Qu, var2.s, string$Mntake);
      var8 = Scheme.applyToArgs;
      var9 = loc$check$Mnarg;

      try {
         var1 = var9.get();
      } catch (UnboundLocationException var6) {
         var6.setLine("srfi13.scm", 996, 3);
         throw var6;
      }

      var8.apply4(var1, var2.lambda$Fn151, var2.n, string$Mntake);
      var0 = var2.s;

      CharSequence var10;
      try {
         var10 = (CharSequence)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "%substring/shared", 0, var0);
      }

      var0 = var2.n;

      int var3;
      try {
         var3 = ((Number)var0).intValue();
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "%substring/shared", 2, var0);
      }

      return $PcSubstring$SlShared(var10, 0, var3);
   }

   public static Object stringTakeRight(Object var0, Object var1) {
      srfi13.frame65 var2 = new srfi13.frame65();
      var2.n = var1;
      ApplyToArgs var10 = Scheme.applyToArgs;
      Location var3 = loc$check$Mnarg;

      Object var12;
      try {
         var12 = var3.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 1002, 3);
         throw var9;
      }

      var10.apply4(var12, strings.string$Qu, var0, string$Mntake$Mnright);

      CharSequence var11;
      try {
         var11 = (CharSequence)var0;
      } catch (ClassCastException var8) {
         throw new WrongType(var8, "string-length", 1, var0);
      }

      var2.len = strings.stringLength(var11);
      var10 = Scheme.applyToArgs;
      var3 = loc$check$Mnarg;

      try {
         var12 = var3.get();
      } catch (UnboundLocationException var7) {
         var7.setLine("srfi13.scm", 1004, 5);
         throw var7;
      }

      var10.apply4(var12, var2.lambda$Fn152, var2.n, string$Mntake$Mnright);

      try {
         var11 = (CharSequence)var0;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "%substring/shared", 0, var0);
      }

      var0 = AddOp.$Mn.apply2(Integer.valueOf(var2.len), var2.n);

      int var4;
      try {
         var4 = ((Number)var0).intValue();
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "%substring/shared", 1, var0);
      }

      return $PcSubstring$SlShared(var11, var4, var2.len);
   }

   public static Object stringTitlecase$Ex$V(Object var0, Object[] var1) {
      srfi13.frame62 var2 = new srfi13.frame62();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn147, var2.lambda$Fn148);
   }

   public static Object stringTitlecase$V(Object var0, Object[] var1) {
      srfi13.frame63 var2 = new srfi13.frame63();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn149, var2.lambda$Fn150);
   }

   public static Object stringTokenize$V(Object var0, Object[] var1) {
      srfi13.frame93 var2 = new srfi13.frame93();
      var2.s = var0;
      LList var17 = LList.makeList(var1, 0);
      ApplyToArgs var18 = Scheme.applyToArgs;
      Location var3 = loc$let$Mnoptionals$St;

      Object var19;
      try {
         var19 = var3.get();
      } catch (UnboundLocationException var16) {
         var16.setLine("srfi13.scm", 1694, 3);
         throw var16;
      }

      ApplyToArgs var4 = Scheme.applyToArgs;
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$token$Mnchars;

      Object var21;
      try {
         var21 = var6.get();
      } catch (UnboundLocationException var15) {
         var15.setLine("srfi13.scm", 1695, 20);
         throw var15;
      }

      GetNamedPart var7 = GetNamedPart.getNamedPart;
      Location var8 = loc$char$Mnset;

      Object var24;
      try {
         var24 = var8.get();
      } catch (UnboundLocationException var14) {
         var14.setLine("srfi13.scm", 1695, 33);
         throw var14;
      }

      Object var22 = var7.apply2(var24, Lit14);
      ApplyToArgs var23 = Scheme.applyToArgs;
      Location var9 = loc$char$Mnset$Qu;

      Object var25;
      try {
         var25 = var9.get();
      } catch (UnboundLocationException var13) {
         var13.setLine("srfi13.scm", 1695, 50);
         throw var13;
      }

      Location var10 = loc$token$Mnchars;

      Object var26;
      try {
         var26 = var10.get();
      } catch (UnboundLocationException var12) {
         var12.setLine("srfi13.scm", 1695, 61);
         throw var12;
      }

      Object var20 = var5.apply3(var21, var22, var23.apply2(var25, var26));
      var6 = loc$rest;

      try {
         var21 = var6.get();
      } catch (UnboundLocationException var11) {
         var11.setLine("srfi13.scm", 1695, 75);
         throw var11;
      }

      return var18.apply4(var19, var17, var4.apply2(var20, var21), call_with_values.callWithValues(var2.lambda$Fn208, var2.lambda$Fn209));
   }

   public static Object stringTrim$V(Object var0, Object[] var1) {
      srfi13.frame68 var2 = new srfi13.frame68();
      var2.s = var0;
      LList var13 = LList.makeList(var1, 0);
      ApplyToArgs var14 = Scheme.applyToArgs;
      Location var3 = loc$let$Mnoptionals$St;

      Object var15;
      try {
         var15 = var3.get();
      } catch (UnboundLocationException var12) {
         var12.setLine("srfi13.scm", 1022, 3);
         throw var12;
      }

      ApplyToArgs var4 = Scheme.applyToArgs;
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$criterion;

      Object var17;
      try {
         var17 = var6.get();
      } catch (UnboundLocationException var11) {
         var11.setLine("srfi13.scm", 1022, 40);
         throw var11;
      }

      GetNamedPart var7 = GetNamedPart.getNamedPart;
      Location var8 = loc$char$Mnset;

      Object var18;
      try {
         var18 = var8.get();
      } catch (UnboundLocationException var10) {
         var10.setLine("srfi13.scm", 1022, 51);
         throw var10;
      }

      Object var16 = var5.apply2(var17, var7.apply2(var18, Lit11));
      var6 = loc$rest;

      try {
         var17 = var6.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 1022, 72);
         throw var9;
      }

      return var14.apply4(var15, var13, var4.apply2(var16, var17), call_with_values.callWithValues(var2.lambda$Fn155, var2.lambda$Fn156));
   }

   public static Object stringTrimBoth$V(Object var0, Object[] var1) {
      srfi13.frame70 var2 = new srfi13.frame70();
      var2.s = var0;
      LList var13 = LList.makeList(var1, 0);
      ApplyToArgs var14 = Scheme.applyToArgs;
      Location var3 = loc$let$Mnoptionals$St;

      Object var15;
      try {
         var15 = var3.get();
      } catch (UnboundLocationException var12) {
         var12.setLine("srfi13.scm", 1036, 3);
         throw var12;
      }

      ApplyToArgs var4 = Scheme.applyToArgs;
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$criterion;

      Object var17;
      try {
         var17 = var6.get();
      } catch (UnboundLocationException var11) {
         var11.setLine("srfi13.scm", 1036, 40);
         throw var11;
      }

      GetNamedPart var7 = GetNamedPart.getNamedPart;
      Location var8 = loc$char$Mnset;

      Object var18;
      try {
         var18 = var8.get();
      } catch (UnboundLocationException var10) {
         var10.setLine("srfi13.scm", 1036, 51);
         throw var10;
      }

      Object var16 = var5.apply2(var17, var7.apply2(var18, Lit11));
      var6 = loc$rest;

      try {
         var17 = var6.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 1036, 72);
         throw var9;
      }

      return var14.apply4(var15, var13, var4.apply2(var16, var17), call_with_values.callWithValues(var2.lambda$Fn159, var2.lambda$Fn160));
   }

   public static Object stringTrimRight$V(Object var0, Object[] var1) {
      srfi13.frame69 var2 = new srfi13.frame69();
      var2.s = var0;
      LList var13 = LList.makeList(var1, 0);
      ApplyToArgs var14 = Scheme.applyToArgs;
      Location var3 = loc$let$Mnoptionals$St;

      Object var15;
      try {
         var15 = var3.get();
      } catch (UnboundLocationException var12) {
         var12.setLine("srfi13.scm", 1029, 3);
         throw var12;
      }

      ApplyToArgs var4 = Scheme.applyToArgs;
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$criterion;

      Object var17;
      try {
         var17 = var6.get();
      } catch (UnboundLocationException var11) {
         var11.setLine("srfi13.scm", 1029, 40);
         throw var11;
      }

      GetNamedPart var7 = GetNamedPart.getNamedPart;
      Location var8 = loc$char$Mnset;

      Object var18;
      try {
         var18 = var8.get();
      } catch (UnboundLocationException var10) {
         var10.setLine("srfi13.scm", 1029, 51);
         throw var10;
      }

      Object var16 = var5.apply2(var17, var7.apply2(var18, Lit11));
      var6 = loc$rest;

      try {
         var17 = var6.get();
      } catch (UnboundLocationException var9) {
         var9.setLine("srfi13.scm", 1029, 72);
         throw var9;
      }

      return var14.apply4(var15, var13, var4.apply2(var16, var17), call_with_values.callWithValues(var2.lambda$Fn157, var2.lambda$Fn158));
   }

   public static Object stringUnfold$V(Object var0, Object var1, Object var2, Object var3, Object[] var4) {
      LList var9 = LList.makeList(var4, 0);
      ApplyToArgs var52 = Scheme.applyToArgs;
      Location var6 = loc$check$Mnarg;

      Object var56;
      try {
         var56 = var6.get();
      } catch (UnboundLocationException var45) {
         var45.setLine("srfi13.scm", 372, 3);
         throw var45;
      }

      var52.apply4(var56, misc.procedure$Qu, var0, string$Mnunfold);
      var52 = Scheme.applyToArgs;
      var6 = loc$check$Mnarg;

      try {
         var56 = var6.get();
      } catch (UnboundLocationException var44) {
         var44.setLine("srfi13.scm", 373, 3);
         throw var44;
      }

      var52.apply4(var56, misc.procedure$Qu, var1, string$Mnunfold);
      var52 = Scheme.applyToArgs;
      var6 = loc$check$Mnarg;

      try {
         var56 = var6.get();
      } catch (UnboundLocationException var43) {
         var43.setLine("srfi13.scm", 374, 3);
         throw var43;
      }

      var52.apply4(var56, misc.procedure$Qu, var2, string$Mnunfold);
      ApplyToArgs var10 = Scheme.applyToArgs;
      Location var53 = loc$let$Mnoptionals$St;

      Object var11;
      try {
         var11 = var53.get();
      } catch (UnboundLocationException var42) {
         var42.setLine("srfi13.scm", 375, 3);
         throw var42;
      }

      ApplyToArgs var58 = Scheme.applyToArgs;
      ApplyToArgs var7 = Scheme.applyToArgs;
      var53 = loc$base;

      Object var8;
      try {
         var8 = var53.get();
      } catch (UnboundLocationException var41) {
         var41.setLine("srfi13.scm", 376, 20);
         throw var41;
      }

      var53 = loc$base;

      Object var55;
      try {
         var55 = var53.get();
      } catch (UnboundLocationException var40) {
         var40.setLine("srfi13.scm", 376, 57);
         throw var40;
      }

      Boolean var54;
      if(strings.isString(var55)) {
         var54 = Boolean.TRUE;
      } else {
         var54 = Boolean.FALSE;
      }

      Object var57 = var7.apply3(var8, "", var54);
      ApplyToArgs var62 = Scheme.applyToArgs;
      var53 = loc$make$Mnfinal;

      Object var12;
      try {
         var12 = var53.get();
      } catch (UnboundLocationException var39) {
         var39.setLine("srfi13.scm", 377, 6);
         throw var39;
      }

      ModuleMethod var13 = lambda$Fn17;
      var53 = loc$make$Mnfinal;

      try {
         var55 = var53.get();
      } catch (UnboundLocationException var38) {
         var38.setLine("srfi13.scm", 377, 46);
         throw var38;
      }

      if(misc.isProcedure(var55)) {
         var54 = Boolean.TRUE;
      } else {
         var54 = Boolean.FALSE;
      }

      var12 = var58.apply2(var57, var62.apply3(var12, var13, var54));
      LList var59 = LList.Empty;
      int var14 = 0;
      CharSequence var61 = strings.makeString(40);
      int var15 = 40;
      byte var16 = 0;
      var55 = var3;
      var3 = var59;

      label216:
      while(true) {
         Integer var60 = Integer.valueOf(var16);
         var57 = var55;

         int var65;
         CharSequence var63;
         for(var55 = var60; Scheme.applyToArgs.apply2(var0, var57) == Boolean.FALSE; var55 = AddOp.$Pl.apply2(var55, Lit1)) {
            var8 = Scheme.applyToArgs.apply2(var1, var57);
            var57 = Scheme.applyToArgs.apply2(var2, var57);
            CharSeq var64;
            char var5;
            if(Scheme.numLss.apply2(var55, Integer.valueOf(var15)) == Boolean.FALSE) {
               var55 = numbers.min(new Object[]{Lit2, Integer.valueOf(var15 + var14)});

               try {
                  var65 = ((Number)var55).intValue();
               } catch (ClassCastException var37) {
                  throw new WrongType(var37, "chunk-len2", -2, var55);
               }

               var63 = strings.makeString(var65);

               try {
                  var64 = (CharSeq)var63;
               } catch (ClassCastException var36) {
                  throw new WrongType(var36, "string-set!", 1, var63);
               }

               try {
                  var5 = ((Char)var8).charValue();
               } catch (ClassCastException var35) {
                  throw new WrongType(var35, "string-set!", 3, var8);
               }

               strings.stringSet$Ex(var64, 0, var5);
               var3 = lists.cons(var61, var3);
               var14 += var15;
               byte var17 = 1;
               var15 = var65;
               var61 = var63;
               var16 = var17;
               var55 = var57;
               continue label216;
            }

            try {
               var64 = (CharSeq)var61;
            } catch (ClassCastException var34) {
               throw new WrongType(var34, "string-set!", 1, var61);
            }

            try {
               var65 = ((Number)var55).intValue();
            } catch (ClassCastException var33) {
               throw new WrongType(var33, "string-set!", 2, var55);
            }

            try {
               var5 = ((Char)var8).charValue();
            } catch (ClassCastException var32) {
               throw new WrongType(var32, "string-set!", 3, var8);
            }

            strings.stringSet$Ex(var64, var65, var5);
         }

         ApplyToArgs var46 = Scheme.applyToArgs;
         Location var47 = loc$make$Mnfinal;

         try {
            var1 = var47.get();
         } catch (UnboundLocationException var31) {
            var31.setLine("srfi13.scm", 400, 20);
            throw var31;
         }

         var0 = var46.apply2(var1, var57);

         CharSequence var49;
         try {
            var49 = (CharSequence)var0;
         } catch (ClassCastException var30) {
            throw new WrongType(var30, "string-length", 1, var0);
         }

         var65 = strings.stringLength(var49);
         var47 = loc$base;

         try {
            var1 = var47.get();
         } catch (UnboundLocationException var29) {
            var29.setLine("srfi13.scm", 402, 38);
            throw var29;
         }

         CharSequence var50;
         try {
            var50 = (CharSequence)var1;
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "string-length", 1, var1);
         }

         var15 = strings.stringLength(var50);
         var1 = AddOp.$Pl.apply2(Integer.valueOf(var15 + var14), var55);

         try {
            var14 = ((Number)var1).intValue();
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "j", -2, var1);
         }

         var49 = strings.makeString(var14 + var65);

         try {
            var50 = (CharSequence)var0;
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "%string-copy!", 2, var0);
         }

         $PcStringCopy$Ex(var49, var14, var50, 0, var65);
         var0 = AddOp.$Mn.apply2(Integer.valueOf(var14), var55);

         try {
            var14 = ((Number)var0).intValue();
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "j", -2, var0);
         }

         CharSequence var48;
         try {
            var48 = (CharSequence)var61;
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "%string-copy!", 2, var61);
         }

         try {
            var65 = ((Number)var55).intValue();
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "%string-copy!", 4, var55);
         }

         $PcStringCopy$Ex(var49, var14, var48, 0, var65);

         for(var0 = Integer.valueOf(var14); lists.isPair(var3); $PcStringCopy$Ex(var49, var65, var63, 0, var14)) {
            var2 = lists.car.apply1(var3);
            var3 = lists.cdr.apply1(var3);

            try {
               var63 = (CharSequence)var2;
            } catch (ClassCastException var22) {
               throw new WrongType(var22, "string-length", 1, var2);
            }

            var14 = strings.stringLength(var63);
            var0 = AddOp.$Mn.apply2(var0, Integer.valueOf(var14));

            try {
               var65 = ((Number)var0).intValue();
            } catch (ClassCastException var21) {
               throw new WrongType(var21, "%string-copy!", 1, var0);
            }

            try {
               var63 = (CharSequence)var2;
            } catch (ClassCastException var20) {
               throw new WrongType(var20, "%string-copy!", 2, var2);
            }
         }

         Location var51 = loc$base;

         try {
            var0 = var51.get();
         } catch (UnboundLocationException var19) {
            var19.setLine("srfi13.scm", 416, 29);
            throw var19;
         }

         try {
            var50 = (CharSequence)var0;
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "%string-copy!", 2, var0);
         }

         $PcStringCopy$Ex(var49, 0, var50, 0, var15);
         return var10.apply4(var11, var9, var12, var49);
      }
   }

   public static Object stringUnfoldRight$V(Object var0, Object var1, Object var2, Object var3, Object[] var4) {
      LList var12 = LList.makeList(var4, 0);
      ApplyToArgs var13 = Scheme.applyToArgs;
      Location var55 = loc$let$Mnoptionals$St;

      Object var14;
      try {
         var14 = var55.get();
      } catch (UnboundLocationException var48) {
         var48.setLine("srfi13.scm", 420, 3);
         throw var48;
      }

      ApplyToArgs var6 = Scheme.applyToArgs;
      ApplyToArgs var7 = Scheme.applyToArgs;
      var55 = loc$base;

      Object var8;
      try {
         var8 = var55.get();
      } catch (UnboundLocationException var47) {
         var47.setLine("srfi13.scm", 421, 20);
         throw var47;
      }

      var55 = loc$base;

      Object var56;
      try {
         var56 = var55.get();
      } catch (UnboundLocationException var46) {
         var46.setLine("srfi13.scm", 421, 57);
         throw var46;
      }

      Boolean var57;
      if(strings.isString(var56)) {
         var57 = Boolean.TRUE;
      } else {
         var57 = Boolean.FALSE;
      }

      Object var59 = var7.apply3(var8, "", var57);
      ApplyToArgs var60 = Scheme.applyToArgs;
      var55 = loc$make$Mnfinal;

      Object var9;
      try {
         var9 = var55.get();
      } catch (UnboundLocationException var45) {
         var45.setLine("srfi13.scm", 422, 6);
         throw var45;
      }

      ModuleMethod var10 = lambda$Fn18;
      var55 = loc$make$Mnfinal;

      try {
         var56 = var55.get();
      } catch (UnboundLocationException var44) {
         var44.setLine("srfi13.scm", 422, 46);
         throw var44;
      }

      if(misc.isProcedure(var56)) {
         var57 = Boolean.TRUE;
      } else {
         var57 = Boolean.FALSE;
      }

      Object var15 = var6.apply2(var59, var60.apply3(var9, var10, var57));
      LList var64 = LList.Empty;
      var8 = Lit0;
      CharSequence var58 = strings.makeString(40);
      var59 = Lit3;
      var56 = Lit3;
      var9 = var3;
      var3 = var64;

      int var18;
      while(Scheme.applyToArgs.apply2(var0, var9) == Boolean.FALSE) {
         Object var16 = Scheme.applyToArgs.apply2(var1, var9);
         var9 = Scheme.applyToArgs.apply2(var2, var9);
         char var5;
         if(Scheme.numGrt.apply2(var56, Lit0) != Boolean.FALSE) {
            var56 = AddOp.$Mn.apply2(var56, Lit1);

            CharSeq var65;
            try {
               var65 = (CharSeq)var58;
            } catch (ClassCastException var43) {
               throw new WrongType(var43, "string-set!", 1, var58);
            }

            try {
               var18 = ((Number)var56).intValue();
            } catch (ClassCastException var42) {
               throw new WrongType(var42, "string-set!", 2, var56);
            }

            try {
               var5 = ((Char)var16).charValue();
            } catch (ClassCastException var41) {
               throw new WrongType(var41, "string-set!", 3, var16);
            }

            strings.stringSet$Ex(var65, var18, var5);
         } else {
            var56 = AddOp.$Pl.apply2(var59, var8);
            Object var11 = numbers.min(new Object[]{Lit4, var56});

            try {
               var18 = ((Number)var11).intValue();
            } catch (ClassCastException var40) {
               throw new WrongType(var40, "make-string", 1, var11);
            }

            CharSequence var63 = strings.makeString(var18);
            var56 = AddOp.$Mn.apply2(var11, Lit1);

            CharSeq var17;
            try {
               var17 = (CharSeq)var63;
            } catch (ClassCastException var39) {
               throw new WrongType(var39, "string-set!", 1, var63);
            }

            try {
               var18 = ((Number)var56).intValue();
            } catch (ClassCastException var38) {
               throw new WrongType(var38, "string-set!", 2, var56);
            }

            try {
               var5 = ((Char)var16).charValue();
            } catch (ClassCastException var37) {
               throw new WrongType(var37, "string-set!", 3, var16);
            }

            strings.stringSet$Ex(var17, var18, var5);
            var3 = lists.cons(var58, var3);
            var8 = AddOp.$Pl.apply2(var8, var59);
            var59 = var11;
            var58 = var63;
         }
      }

      ApplyToArgs var49 = Scheme.applyToArgs;
      Location var51 = loc$make$Mnfinal;

      try {
         var1 = var51.get();
      } catch (UnboundLocationException var36) {
         var36.setLine("srfi13.scm", 447, 20);
         throw var36;
      }

      var0 = var49.apply2(var1, var9);

      CharSequence var52;
      try {
         var52 = (CharSequence)var0;
      } catch (ClassCastException var35) {
         throw new WrongType(var35, "string-length", 1, var0);
      }

      int var19 = strings.stringLength(var52);
      var51 = loc$base;

      try {
         var1 = var51.get();
      } catch (UnboundLocationException var34) {
         var34.setLine("srfi13.scm", 449, 31);
         throw var34;
      }

      CharSequence var54;
      try {
         var54 = (CharSequence)var1;
      } catch (ClassCastException var33) {
         throw new WrongType(var33, "string-length", 1, var1);
      }

      var18 = strings.stringLength(var54);
      var2 = AddOp.$Mn.apply2(var59, var56);
      var1 = AddOp.$Pl.apply2(AddOp.$Pl.apply2(Integer.valueOf(var18), var8), var2);
      var1 = AddOp.$Pl.apply2(var1, Integer.valueOf(var19));

      int var20;
      try {
         var20 = ((Number)var1).intValue();
      } catch (ClassCastException var32) {
         throw new WrongType(var32, "make-string", 1, var1);
      }

      var52 = strings.makeString(var20);

      CharSequence var61;
      try {
         var61 = (CharSequence)var0;
      } catch (ClassCastException var31) {
         throw new WrongType(var31, "%string-copy!", 2, var0);
      }

      $PcStringCopy$Ex(var52, 0, var61, 0, var19);

      CharSequence var50;
      try {
         var50 = (CharSequence)var58;
      } catch (ClassCastException var30) {
         throw new WrongType(var30, "%string-copy!", 2, var58);
      }

      try {
         var20 = ((Number)var56).intValue();
      } catch (ClassCastException var29) {
         throw new WrongType(var29, "%string-copy!", 3, var56);
      }

      int var21;
      try {
         var21 = ((Number)var59).intValue();
      } catch (ClassCastException var28) {
         throw new WrongType(var28, "%string-copy!", 4, var59);
      }

      $PcStringCopy$Ex(var52, var19, var50, var20, var21);

      for(var0 = AddOp.$Pl.apply2(Integer.valueOf(var19), var2); lists.isPair(var3); var0 = AddOp.$Pl.apply2(var0, Integer.valueOf(var19))) {
         var2 = lists.car.apply1(var3);
         var3 = lists.cdr.apply1(var3);

         CharSequence var62;
         try {
            var62 = (CharSequence)var2;
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "string-length", 1, var2);
         }

         var19 = strings.stringLength(var62);

         try {
            var20 = ((Number)var0).intValue();
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "%string-copy!", 1, var0);
         }

         try {
            var62 = (CharSequence)var2;
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "%string-copy!", 2, var2);
         }

         $PcStringCopy$Ex(var52, var20, var62, 0, var19);
      }

      try {
         var19 = ((Number)var0).intValue();
      } catch (ClassCastException var24) {
         throw new WrongType(var24, "%string-copy!", 1, var0);
      }

      Location var53 = loc$base;

      try {
         var0 = var53.get();
      } catch (UnboundLocationException var23) {
         var23.setLine("srfi13.scm", 463, 30);
         throw var23;
      }

      try {
         var54 = (CharSequence)var0;
      } catch (ClassCastException var22) {
         throw new WrongType(var22, "%string-copy!", 2, var0);
      }

      $PcStringCopy$Ex(var52, var19, var54, 0, var18);
      return var13.apply4(var14, var12, var15, var52);
   }

   public static Object stringUpcase$Ex$V(Object var0, Object[] var1) {
      srfi13.frame59 var2 = new srfi13.frame59();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn141, var2.lambda$Fn142);
   }

   public static Object stringUpcase$V(Object var0, Object[] var1) {
      srfi13.frame58 var2 = new srfi13.frame58();
      var2.s = var0;
      var2.maybe$Mnstart$Plend = LList.makeList(var1, 0);
      return call_with_values.callWithValues(var2.lambda$Fn139, var2.lambda$Fn140);
   }

   public static Object stringXcopy$Ex$V(Object var0, Object var1, Object var2, Object var3, Object[] var4) {
      srfi13.frame95 var5 = new srfi13.frame95();
      var5.target = var0;
      var5.tstart = var1;
      var5.s = var2;
      var5.sfrom = var3;
      var5.maybe$Mnsto$Plstart$Plend = LList.makeList(var4, 0);
      ApplyToArgs var7 = Scheme.applyToArgs;
      Location var8 = loc$check$Mnarg;

      try {
         var1 = var8.get();
      } catch (UnboundLocationException var6) {
         var6.setLine("srfi13.scm", 1779, 3);
         throw var6;
      }

      var7.apply4(var1, lambda$Fn216, var5.sfrom, string$Mnxcopy$Ex);
      return call_with_values.callWithValues(var5.lambda$Fn217, var5.lambda$Fn221);
   }

   public static Object substring$SlShared$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame1 var3 = new srfi13.frame1();
      var3.start = var1;
      LList var15 = LList.makeList(var2, 0);
      ApplyToArgs var16 = Scheme.applyToArgs;
      Location var4 = loc$check$Mnarg;

      Object var18;
      try {
         var18 = var4.get();
      } catch (UnboundLocationException var13) {
         var13.setLine("srfi13.scm", 221, 3);
         throw var13;
      }

      var16.apply4(var18, strings.string$Qu, var0, substring$Slshared);

      CharSequence var17;
      try {
         var17 = (CharSequence)var0;
      } catch (ClassCastException var12) {
         throw new WrongType(var12, "string-length", 1, var0);
      }

      var3.slen = strings.stringLength(var17);
      var16 = Scheme.applyToArgs;
      var4 = loc$check$Mnarg;

      try {
         var18 = var4.get();
      } catch (UnboundLocationException var11) {
         var11.setLine("srfi13.scm", 223, 5);
         throw var11;
      }

      var16.apply4(var18, lambda$Fn5, var3.start, substring$Slshared);

      try {
         var17 = (CharSequence)var0;
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "%substring/shared", 0, var0);
      }

      var0 = var3.start;

      int var5;
      try {
         var5 = ((Number)var0).intValue();
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "%substring/shared", 1, var0);
      }

      ApplyToArgs var14 = Scheme.applyToArgs;
      var4 = loc$$Cloptional;

      try {
         var18 = var4.get();
      } catch (UnboundLocationException var8) {
         var8.setLine("srfi13.scm", 226, 10);
         throw var8;
      }

      var0 = var14.apply4(var18, var15, Integer.valueOf(var3.slen), var3.lambda$Fn6);

      int var6;
      try {
         var6 = ((Number)var0).intValue();
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "%substring/shared", 2, var0);
      }

      return $PcSubstring$SlShared(var17, var5, var6);
   }

   public static Object xsubstring$V(Object var0, Object var1, Object[] var2) {
      srfi13.frame94 var3 = new srfi13.frame94();
      var3.s = var0;
      var3.from = var1;
      var3.maybe$Mnto$Plstart$Plend = LList.makeList(var2, 0);
      ApplyToArgs var5 = Scheme.applyToArgs;
      Location var6 = loc$check$Mnarg;

      try {
         var1 = var6.get();
      } catch (UnboundLocationException var4) {
         var4.setLine("srfi13.scm", 1738, 3);
         throw var4;
      }

      var5.apply4(var1, lambda$Fn210, var3.from, xsubstring);
      return call_with_values.callWithValues(var3.lambda$Fn211, var3.lambda$Fn215);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 199:
         if(srfi13.frame1.lambda5(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 209:
         return lambda17(var2);
      case 211:
         return lambda18(var2);
      case 217:
         if(lambda27(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 239:
         return srfi13.frame32.lambda72(var2);
      case 240:
         return srfi13.frame32.lambda73(var2);
      case 242:
         return srfi13.frame34.lambda78(var2);
      case 244:
         return srfi13.frame36.lambda83(var2);
      case 245:
         return srfi13.frame36.lambda84(var2);
      case 247:
         return srfi13.frame38.lambda89(var2);
      case 248:
         return srfi13.frame38.lambda90(var2);
      case 250:
         return srfi13.frame40.lambda95(var2);
      case 252:
         return srfi13.frame42.lambda100(var2);
      case 254:
         return srfi13.frame44.lambda105(var2);
      case 255:
         return srfi13.frame44.lambda106(var2);
      case 257:
         return srfi13.frame46.lambda111(var2);
      case 259:
         return srfi13.frame48.lambda116(var2);
      case 260:
         return srfi13.frame48.lambda117(var2);
      case 262:
         return srfi13.frame50.lambda122(var2);
      case 263:
         return srfi13.frame50.lambda123(var2);
      case 265:
         return srfi13.frame52.lambda128(var2);
      case 267:
         return srfi13.frame54.lambda133(var2);
      case 271:
         return Integer.valueOf(srfi13.frame57.lambda138(var2));
      case 287:
         if(srfi13.frame71.lambda163(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 289:
         if(srfi13.frame72.lambda166(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 309:
         if(isStringNull(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 312:
         return reverseList$To$String(var2);
      case 315:
         return stringConcatenate$SlShared(var2);
      case 316:
         return stringConcatenate(var2);
      case 322:
         if(srfi13.frame94.lambda210(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 324:
         if(srfi13.frame95.lambda216(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 325:
         if(srfi13.frame95.lambda220(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      CharSequence var8;
      switch(var1.selector) {
      case 218:
         int var4;
         try {
            var4 = ((Number)var3).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-tabulate", 2, var3);
         }

         return stringTabulate(var2, var4);
      case 280:
         return stringTake(var2, var3);
      case 281:
         return stringTakeRight(var2, var3);
      case 282:
         try {
            var8 = (CharSequence)var2;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-drop", 1, var2);
         }

         return stringDrop(var8, var3);
      case 283:
         try {
            var8 = (CharSequence)var2;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-drop-right", 1, var2);
         }

         return stringDropRight(var8, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      int var5;
      CharSequence var12;
      switch(var1.selector) {
      case 194:
         return stringParseStart$PlEnd(var2, var3, var4);
      case 196:
         return stringParseFinalStart$PlEnd(var2, var3, var4);
      case 197:
         if(isSubstringSpecOk(var2, var3, var4)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 201:
         try {
            var12 = (CharSequence)var2;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "%substring/shared", 1, var2);
         }

         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "%substring/shared", 2, var3);
         }

         int var6;
         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "%substring/shared", 3, var4);
         }

         return $PcSubstring$SlShared(var12, var5, var6);
      case 277:
         return $PcStringTitlecase$Ex(var2, var3, var4);
      case 299:
         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-copy!", 2, var3);
         }

         try {
            var12 = (CharSequence)var4;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-copy!", 3, var4);
         }

         return stringCopy$Ex(var2, var5, var12);
      default:
         return super.apply3(var1, var2, var3, var4);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      int var6;
      int var7;
      CharSequence var14;
      switch(var1.selector) {
      case 195:
         try {
            var14 = (CharSequence)var3;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "%check-bounds", 2, var3);
         }

         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "%check-bounds", 3, var4);
         }

         try {
            var7 = ((Number)var5).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "%check-bounds", 4, var5);
         }

         return $PcCheckBounds(var2, var14, var6, var7);
      case 198:
         return checkSubstringSpec(var2, var3, var4, var5);
      case 204:
         return $PcStringMap(var2, var3, var4, var5);
      case 206:
         return $PcStringMap$Ex(var2, var3, var4, var5);
      case 299:
         try {
            var6 = ((Number)var3).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-copy!", 2, var3);
         }

         try {
            var14 = (CharSequence)var4;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "string-copy!", 3, var4);
         }

         try {
            var7 = ((Number)var5).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-copy!", 4, var5);
         }

         return stringCopy$Ex(var2, var6, var14, var7);
      case 319:
         return $PcFinishStringConcatenateReverse(var2, var3, var4, var5);
      default:
         return super.apply4(var1, var2, var3, var4, var5);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      Object[] var3;
      Object var4;
      Object var5;
      Object[] var6;
      Object[] var7;
      int var8;
      int var9;
      int var10;
      int var11;
      Object var29;
      Object var31;
      Object var30;
      Object[] var34;
      Object var35;
      Object[] var32;
      CharSequence var36;
      switch(var1.selector) {
      case 200:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return substring$SlShared$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 201:
      case 204:
      case 206:
      case 209:
      case 211:
      case 217:
      case 218:
      case 239:
      case 240:
      case 242:
      case 244:
      case 245:
      case 247:
      case 248:
      case 250:
      case 252:
      case 254:
      case 255:
      case 257:
      case 259:
      case 260:
      case 262:
      case 263:
      case 265:
      case 267:
      case 271:
      case 277:
      case 280:
      case 281:
      case 282:
      case 283:
      case 287:
      case 289:
      case 300:
      case 301:
      case 309:
      case 312:
      case 315:
      case 316:
      case 319:
      case 322:
      case 324:
      case 325:
      default:
         return super.applyN(var1, var2);
      case 202:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCopy$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 203:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringMap$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 205:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringMap$Ex$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 207:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var8 = var2.length - 3;
         var34 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringFold$V(var29, var31, var4, var34);
            }

            var34[var8] = var2[var8 + 3];
         }
      case 208:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var8 = var2.length - 3;
         var34 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringFoldRight$V(var29, var31, var4, var34);
            }

            var34[var8] = var2[var8 + 3];
         }
      case 210:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var5 = var2[3];
         var8 = var2.length - 4;
         var6 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringUnfold$V(var29, var31, var4, var5, var6);
            }

            var6[var8] = var2[var8 + 4];
         }
      case 212:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var5 = var2[3];
         var8 = var2.length - 4;
         var6 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringUnfoldRight$V(var29, var31, var4, var5, var6);
            }

            var6[var8] = var2[var8 + 4];
         }
      case 213:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringForEach$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 214:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringForEachIndex$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 215:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringEvery$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 216:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringAny$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 219:
         return $PcStringPrefixLength(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 220:
         return $PcStringSuffixLength(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 221:
         var29 = var2[0];
         var31 = var2[1];

         try {
            var8 = ((Number)var31).intValue();
         } catch (ClassCastException var28) {
            throw new WrongType(var28, "%string-prefix-length-ci", 2, var31);
         }

         var31 = var2[2];

         try {
            var9 = ((Number)var31).intValue();
         } catch (ClassCastException var27) {
            throw new WrongType(var27, "%string-prefix-length-ci", 3, var31);
         }

         var31 = var2[3];
         var4 = var2[4];

         try {
            var10 = ((Number)var4).intValue();
         } catch (ClassCastException var26) {
            throw new WrongType(var26, "%string-prefix-length-ci", 5, var4);
         }

         var30 = var2[5];

         try {
            var11 = ((Number)var30).intValue();
         } catch (ClassCastException var25) {
            throw new WrongType(var25, "%string-prefix-length-ci", 6, var30);
         }

         return Integer.valueOf($PcStringPrefixLengthCi(var29, var8, var9, var31, var10, var11));
      case 222:
         var29 = var2[0];
         var31 = var2[1];

         try {
            var8 = ((Number)var31).intValue();
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "%string-suffix-length-ci", 2, var31);
         }

         var31 = var2[2];

         try {
            var9 = ((Number)var31).intValue();
         } catch (ClassCastException var23) {
            throw new WrongType(var23, "%string-suffix-length-ci", 3, var31);
         }

         var31 = var2[3];
         var4 = var2[4];

         try {
            var10 = ((Number)var4).intValue();
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "%string-suffix-length-ci", 5, var4);
         }

         var30 = var2[5];

         try {
            var11 = ((Number)var30).intValue();
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "%string-suffix-length-ci", 6, var30);
         }

         return Integer.valueOf($PcStringSuffixLengthCi(var29, var8, var9, var31, var10, var11));
      case 223:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringPrefixLength$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 224:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringSuffixLength$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 225:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringPrefixLengthCi$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 226:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringSuffixLengthCi$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 227:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return isStringPrefix$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 228:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return isStringSuffix$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 229:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return isStringPrefixCi$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 230:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return isStringSuffixCi$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 231:
         return $PcStringPrefix$Qu(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 232:
         return $PcStringSuffix$Qu(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 233:
         return $PcStringPrefixCi$Qu(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 234:
         return $PcStringSuffixCi$Qu(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 235:
         return $PcStringCompare(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5], var2[6], var2[7], var2[8]);
      case 236:
         return $PcStringCompareCi(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5], var2[6], var2[7], var2[8]);
      case 237:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var5 = var2[3];
         var35 = var2[4];
         var8 = var2.length - 5;
         var7 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCompare$V(var29, var31, var4, var5, var35, var7);
            }

            var7[var8] = var2[var8 + 5];
         }
      case 238:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var5 = var2[3];
         var35 = var2[4];
         var8 = var2.length - 5;
         var7 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCompareCi$V(var29, var31, var4, var5, var35, var7);
            }

            var7[var8] = var2[var8 + 5];
         }
      case 241:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return string$Eq$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 243:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return string$Ls$Gr$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 246:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return string$Ls$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 249:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return string$Gr$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 251:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return string$Ls$Eq$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 253:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return string$Gr$Eq$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 256:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCi$Eq$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 258:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCi$Ls$Gr$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 261:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCi$Ls$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 264:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCi$Gr$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 266:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCi$Ls$Eq$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 268:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCi$Gr$Eq$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 269:
         return $PcStringHash(var2[0], var2[1], var2[2], var2[3], var2[4]);
      case 270:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringHash$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 272:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringHashCi$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 273:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringUpcase$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 274:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringUpcase$Ex$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 275:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringDowncase$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 276:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringDowncase$Ex$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 278:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringTitlecase$Ex$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 279:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringTitlecase$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 284:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringTrim$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 285:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringTrimRight$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 286:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringTrimBoth$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 288:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringPadRight$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 290:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringPad$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 291:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringDelete$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 292:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringFilter$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 293:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringIndex$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 294:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringIndexRight$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 295:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringSkip$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 296:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringSkipRight$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 297:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringCount$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 298:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringFill$Ex$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 299:
         var9 = var2.length - 3;
         var29 = var2[0];
         var31 = var2[1];

         try {
            var8 = ((Number)var31).intValue();
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "string-copy!", 2, var31);
         }

         var4 = var2[2];

         try {
            var36 = (CharSequence)var4;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "string-copy!", 3, var4);
         }

         if(var9 <= 0) {
            return stringCopy$Ex(var29, var8, var36);
         } else {
            var10 = var9 - 1;
            var4 = var2[3];

            try {
               var9 = ((Number)var4).intValue();
            } catch (ClassCastException var18) {
               throw new WrongType(var18, "string-copy!", 4, var4);
            }

            if(var10 <= 0) {
               return stringCopy$Ex(var29, var8, var36, var9);
            } else {
               var30 = var2[4];

               try {
                  var10 = ((Number)var30).intValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "string-copy!", 5, var30);
               }

               return stringCopy$Ex(var29, var8, var36, var9, var10);
            }
         }
      case 302:
         var31 = var2[0];

         CharSequence var33;
         try {
            var33 = (CharSequence)var31;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "%string-copy!", 1, var31);
         }

         var31 = var2[1];

         try {
            var8 = ((Number)var31).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "%string-copy!", 2, var31);
         }

         var4 = var2[2];

         try {
            var36 = (CharSequence)var4;
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "%string-copy!", 3, var4);
         }

         var4 = var2[3];

         try {
            var9 = ((Number)var4).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "%string-copy!", 4, var4);
         }

         var30 = var2[4];

         try {
            var10 = ((Number)var30).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "%string-copy!", 5, var30);
         }

         return $PcStringCopy$Ex(var33, var8, var36, var9, var10);
      case 303:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringContains$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 304:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringContainsCi$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 305:
         return $PcKmpSearch(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5], var2[6]);
      case 306:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return makeKmpRestartVector$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 307:
         return kmpStep(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 308:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var5 = var2[3];
         var8 = var2.length - 4;
         var6 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringKmpPartialSearch$V(var29, var31, var4, var5, var6);
            }

            var6[var8] = var2[var8 + 4];
         }
      case 310:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringReverse$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 311:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringReverse$Ex$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 313:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return string$To$List$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 314:
         return stringAppend$SlShared$V(var2);
      case 317:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringConcatenateReverse$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 318:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringConcatenateReverse$SlShared$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 320:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var5 = var2[3];
         var8 = var2.length - 4;
         var6 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringReplace$V(var29, var31, var4, var5, var6);
            }

            var6[var8] = var2[var8 + 4];
         }
      case 321:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringTokenize$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      case 323:
         var29 = var2[0];
         var31 = var2[1];
         var8 = var2.length - 2;
         var32 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return xsubstring$V(var29, var31, var32);
            }

            var32[var8] = var2[var8 + 2];
         }
      case 326:
         var29 = var2[0];
         var31 = var2[1];
         var4 = var2[2];
         var5 = var2[3];
         var8 = var2.length - 4;
         var6 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringXcopy$Ex$V(var29, var31, var4, var5, var6);
            }

            var6[var8] = var2[var8 + 4];
         }
      case 327:
         return $PcMultispanRepcopy$Ex(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5], var2[6]);
      case 328:
         var29 = var2[0];
         var8 = var2.length - 1;
         var3 = new Object[var8];

         while(true) {
            --var8;
            if(var8 < 0) {
               return stringJoin$V(var29, var3);
            }

            var3[var8] = var2[var8 + 1];
         }
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 199:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 209:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 211:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 217:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 239:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 240:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 242:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 244:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 245:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 247:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 248:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 250:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 252:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 254:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 255:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 257:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 259:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 260:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 262:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 263:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 265:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 267:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 271:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 287:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 289:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 309:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 312:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 315:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 316:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 322:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 324:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 325:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      default:
         return super.match1(var1, var2, var3);
      }
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      switch(var1.selector) {
      case 218:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 280:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 281:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 282:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      case 283:
         if(var2 instanceof CharSequence) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }

         return -786431;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      switch(var1.selector) {
      case 194:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 196:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 197:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 201:
         if(var2 instanceof CharSequence) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786431;
      case 277:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 299:
         var5.value1 = var2;
         var5.value2 = var3;
         if(var4 instanceof CharSequence) {
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         }

         return -786429;
      default:
         return super.match3(var1, var2, var3, var4, var5);
      }
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      switch(var1.selector) {
      case 195:
         var6.value1 = var2;
         if(var3 instanceof CharSequence) {
            var6.value2 = var3;
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }

         return -786430;
      case 198:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      case 204:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      case 206:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      case 299:
         var6.value1 = var2;
         var6.value2 = var3;
         if(var4 instanceof CharSequence) {
            var6.value3 = var4;
            var6.value4 = var5;
            var6.proc = var1;
            var6.pc = 4;
            return 0;
         }

         return -786429;
      case 319:
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      default:
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 200:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 201:
      case 204:
      case 206:
      case 209:
      case 211:
      case 217:
      case 218:
      case 239:
      case 240:
      case 242:
      case 244:
      case 245:
      case 247:
      case 248:
      case 250:
      case 252:
      case 254:
      case 255:
      case 257:
      case 259:
      case 260:
      case 262:
      case 263:
      case 265:
      case 267:
      case 271:
      case 277:
      case 280:
      case 281:
      case 282:
      case 283:
      case 287:
      case 289:
      case 300:
      case 301:
      case 309:
      case 312:
      case 315:
      case 316:
      case 319:
      case 322:
      case 324:
      case 325:
      default:
         return super.matchN(var1, var2, var3);
      case 202:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 203:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 205:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 207:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 208:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 210:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 212:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 213:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 214:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 215:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 216:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 219:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 220:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 221:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 222:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 223:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 224:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 225:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 226:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 227:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 228:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 229:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 230:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 231:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 232:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 233:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 234:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 235:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 236:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 237:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 238:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 241:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 243:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 246:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 249:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 251:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 253:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 256:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 258:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 261:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 264:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 266:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 268:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 269:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 270:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 272:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 273:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 274:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 275:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 276:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 278:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 279:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 284:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 285:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 286:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 288:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 290:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 291:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 292:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 293:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 294:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 295:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 296:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 297:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 298:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 299:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 302:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 303:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 304:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 305:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 306:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 307:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 308:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 310:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 311:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 313:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 314:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 317:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 318:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 320:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 321:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 323:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 326:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 327:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 328:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }

   public class frame extends ModuleBody {

      Object args;
      final ModuleMethod lambda$Fn1 = new ModuleMethod(this, 1, (Object)null, 0);
      final ModuleMethod lambda$Fn2;
      Object proc;
      Object s;
      int slen;
      Object start;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 2, (Object)null, 8194);
         var1.setProperty("source-location", "srfi13.scm:150");
         this.lambda$Fn2 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 1?this.lambda1():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 2?this.lambda2(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda1() {
         if(!lists.isPair(this.args)) {
            return misc.values(new Object[]{Integer.valueOf(this.slen), this.args});
         } else {
            Object var1 = lists.car.apply1(this.args);
            Object var2 = lists.cdr.apply1(this.args);
            boolean var3 = numbers.isInteger(var1);
            if(var3) {
               var3 = numbers.isExact(var1);
               if(var3) {
                  if(Scheme.numLEq.apply2(var1, Integer.valueOf(this.slen)) != Boolean.FALSE) {
                     return misc.values(new Object[]{var1, var2});
                  }
               } else if(var3) {
                  return misc.values(new Object[]{var1, var2});
               }
            } else if(var3) {
               return misc.values(new Object[]{var1, var2});
            }

            return misc.error$V("Illegal substring END spec", new Object[]{this.proc, var1, this.s});
         }
      }

      Object lambda2(Object var1, Object var2) {
         return Scheme.numLEq.apply2(this.start, var1) != Boolean.FALSE?misc.values(new Object[]{var2, this.start, var1}):misc.error$V("Illegal substring START/END spec", new Object[]{this.proc, this.start, var1, this.s});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 1) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 2) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame0 extends ModuleBody {

      Object args;
      final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, (Object)null, 0);
      final ModuleMethod lambda$Fn4;
      Object proc;
      Object s;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 4, (Object)null, 12291);
         var1.setProperty("source-location", "srfi13.scm:174");
         this.lambda$Fn4 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 3?this.lambda3():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 4?this.lambda4(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda3() {
         return srfi13.stringParseStart$PlEnd(this.proc, this.s, this.args);
      }

      Object lambda4(Object var1, Object var2, Object var3) {
         return lists.isPair(var1)?misc.error$V("Extra arguments to procedure", new Object[]{this.proc, var1}):misc.values(new Object[]{var2, var3});
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 3) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 4) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame1 extends ModuleBody {

      final ModuleMethod lambda$Fn6;
      int slen;
      Object start;


      public frame1() {
         ModuleMethod var1 = new ModuleMethod(this, 5, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:227");
         this.lambda$Fn6 = var1;
      }

      static boolean lambda5(Object var0) {
         boolean var2 = numbers.isInteger(var0);
         boolean var1 = var2;
         if(var2) {
            var2 = numbers.isExact(var0);
            var1 = var2;
            if(var2) {
               var1 = ((Boolean)Scheme.numLEq.apply2(srfi13.Lit0, var0)).booleanValue();
            }
         }

         return var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 5?(this.lambda6(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda6(Object var1) {
         boolean var4 = numbers.isInteger(var1);
         boolean var3 = var4;
         if(var4) {
            var4 = numbers.isExact(var1);
            var3 = var4;
            if(var4) {
               Object var2 = Scheme.numLEq.apply2(this.start, var1);

               try {
                  var4 = ((Boolean)var2).booleanValue();
               } catch (ClassCastException var5) {
                  throw new WrongType(var5, "x", -2, var2);
               }

               var3 = var4;
               if(var4) {
                  var3 = ((Boolean)Scheme.numLEq.apply2(var1, Integer.valueOf(this.slen))).booleanValue();
               }
            }
         }

         return var3;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 5) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame10 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn25 = new ModuleMethod(this, 22, (Object)null, 0);
      final ModuleMethod lambda$Fn26 = new ModuleMethod(this, 23, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 22?this.lambda25():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 23?this.lambda26(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda25() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnany, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda26(Object var1, Object var2) {
         Object var3;
         Object var4;
         int var8;
         boolean var9;
         if(characters.isChar(this.criterion)) {
            while(true) {
               var3 = Scheme.numLss.apply2(var1, var2);

               try {
                  var9 = ((Boolean)var3).booleanValue();
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "x", -2, var3);
               }

               if(!var9) {
                  if(var9) {
                     var1 = Boolean.TRUE;
                  } else {
                     var1 = Boolean.FALSE;
                  }
                  break;
               }

               var4 = this.criterion;

               Char var23;
               try {
                  var23 = (Char)var4;
               } catch (ClassCastException var19) {
                  throw new WrongType(var19, "char=?", 1, var4);
               }

               var4 = this.s;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var4;
               } catch (ClassCastException var18) {
                  throw new WrongType(var18, "string-ref", 1, var4);
               }

               try {
                  var8 = ((Number)var1).intValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "string-ref", 2, var1);
               }

               var9 = characters.isChar$Eq(var23, Char.make(strings.stringRef(var5, var8)));
               if(var9) {
                  if(var9) {
                     var1 = Boolean.TRUE;
                  } else {
                     var1 = Boolean.FALSE;
                  }
                  break;
               }

               var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
            }
         } else {
            ApplyToArgs var24 = Scheme.applyToArgs;
            Location var25 = srfi13.loc$char$Mnset$Qu;

            try {
               var4 = var25.get();
            } catch (UnboundLocationException var21) {
               var21.setLine("srfi13.scm", 515, 5);
               throw var21;
            }

            if(var24.apply2(var4, this.criterion) != Boolean.FALSE) {
               var3 = var1;

               while(true) {
                  var1 = Scheme.numLss.apply2(var3, var2);

                  try {
                     var9 = ((Boolean)var1).booleanValue();
                  } catch (ClassCastException var15) {
                     throw new WrongType(var15, "x", -2, var1);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  ApplyToArgs var22 = Scheme.applyToArgs;
                  var25 = srfi13.loc$char$Mnset$Mncontains$Qu;

                  Object var26;
                  try {
                     var26 = var25.get();
                  } catch (UnboundLocationException var14) {
                     var14.setLine("srfi13.scm", 518, 9);
                     throw var14;
                  }

                  Object var6 = this.criterion;
                  var4 = this.s;

                  CharSequence var7;
                  try {
                     var7 = (CharSequence)var4;
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "string-ref", 1, var4);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var12) {
                     throw new WrongType(var12, "string-ref", 2, var3);
                  }

                  var4 = var22.apply3(var26, var6, Char.make(strings.stringRef(var7, var8)));
                  var1 = var4;
                  if(var4 != Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
               }
            } else {
               if(!misc.isProcedure(this.criterion)) {
                  return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnany, this.criterion});
               }

               var3 = Scheme.numLss.apply2(var1, var2);

               try {
                  var9 = ((Boolean)var3).booleanValue();
               } catch (ClassCastException var20) {
                  throw new WrongType(var20, "x", -2, var3);
               }

               if(!var9) {
                  if(var9) {
                     return Boolean.TRUE;
                  }

                  return Boolean.FALSE;
               }

               while(true) {
                  var3 = this.s;

                  CharSequence var27;
                  try {
                     var27 = (CharSequence)var3;
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "string-ref", 1, var3);
                  }

                  try {
                     var8 = ((Number)var1).intValue();
                  } catch (ClassCastException var10) {
                     throw new WrongType(var10, "string-ref", 2, var1);
                  }

                  char var28 = strings.stringRef(var27, var8);
                  var3 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
                  if(Scheme.numEqu.apply2(var3, var2) != Boolean.FALSE) {
                     return Scheme.applyToArgs.apply2(this.criterion, Char.make(var28));
                  }

                  var4 = Scheme.applyToArgs.apply2(this.criterion, Char.make(var28));
                  var1 = var4;
                  if(var4 != Boolean.FALSE) {
                     break;
                  }

                  var1 = var3;
               }
            }
         }

         return var1;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 22) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 23) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame11 extends ModuleBody {

      final ModuleMethod lambda$Fn28 = new ModuleMethod(this, 26, (Object)null, 0);
      final ModuleMethod lambda$Fn29 = new ModuleMethod(this, 27, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 26?this.lambda28():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 27?this.lambda29(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda28() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnlength, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda29(Object var1, Object var2, Object var3) {
         srfi13.frame12 var4 = new srfi13.frame12();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn30, var4.lambda$Fn31);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 26) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 27) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame12 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn30 = new ModuleMethod(this, 24, (Object)null, 0);
      final ModuleMethod lambda$Fn31 = new ModuleMethod(this, 25, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame11 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 24?this.lambda30():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 25?this.lambda31(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda30() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnlength, this.staticLink.s2, this.rest);
      }

      Object lambda31(Object var1, Object var2) {
         return srfi13.$PcStringPrefixLength(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 24) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 25) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame13 extends ModuleBody {

      final ModuleMethod lambda$Fn32 = new ModuleMethod(this, 30, (Object)null, 0);
      final ModuleMethod lambda$Fn33 = new ModuleMethod(this, 31, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 30?this.lambda32():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 31?this.lambda33(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda32() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnlength, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda33(Object var1, Object var2, Object var3) {
         srfi13.frame14 var4 = new srfi13.frame14();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn34, var4.lambda$Fn35);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 30) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 31) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame14 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn34 = new ModuleMethod(this, 28, (Object)null, 0);
      final ModuleMethod lambda$Fn35 = new ModuleMethod(this, 29, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame13 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 28?this.lambda34():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 29?this.lambda35(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda34() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnlength, this.staticLink.s2, this.rest);
      }

      Object lambda35(Object var1, Object var2) {
         return srfi13.$PcStringSuffixLength(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 28) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 29) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame15 extends ModuleBody {

      final ModuleMethod lambda$Fn36 = new ModuleMethod(this, 34, (Object)null, 0);
      final ModuleMethod lambda$Fn37 = new ModuleMethod(this, 35, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 34?this.lambda36():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 35?this.lambda37(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda36() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnlength$Mnci, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda37(Object var1, Object var2, Object var3) {
         srfi13.frame16 var4 = new srfi13.frame16();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn38, var4.lambda$Fn39);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 34) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 35) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame16 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn38 = new ModuleMethod(this, 32, (Object)null, 0);
      final ModuleMethod lambda$Fn39 = new ModuleMethod(this, 33, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame15 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 32?this.lambda38():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 33?Integer.valueOf(this.lambda39(var2, var3)):super.apply2(var1, var2, var3);
      }

      Object lambda38() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnlength$Mnci, this.staticLink.s2, this.rest);
      }

      int lambda39(Object var1, Object var2) {
         Object var3 = this.staticLink.s1;
         Object var4 = this.start1;

         int var5;
         try {
            var5 = ((Number)var4).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "%string-prefix-length-ci", 1, var4);
         }

         var4 = this.end1;

         int var6;
         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "%string-prefix-length-ci", 2, var4);
         }

         var4 = this.staticLink.s2;

         int var7;
         try {
            var7 = ((Number)var1).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "%string-prefix-length-ci", 4, var1);
         }

         int var8;
         try {
            var8 = ((Number)var2).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "%string-prefix-length-ci", 5, var2);
         }

         return srfi13.$PcStringPrefixLengthCi(var3, var5, var6, var4, var7, var8);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 32) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 33) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame17 extends ModuleBody {

      final ModuleMethod lambda$Fn40 = new ModuleMethod(this, 38, (Object)null, 0);
      final ModuleMethod lambda$Fn41 = new ModuleMethod(this, 39, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 38?this.lambda40():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 39?this.lambda41(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda40() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnlength$Mnci, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda41(Object var1, Object var2, Object var3) {
         srfi13.frame18 var4 = new srfi13.frame18();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn42, var4.lambda$Fn43);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 38) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 39) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame18 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn42 = new ModuleMethod(this, 36, (Object)null, 0);
      final ModuleMethod lambda$Fn43 = new ModuleMethod(this, 37, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame17 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 36?this.lambda42():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 37?Integer.valueOf(this.lambda43(var2, var3)):super.apply2(var1, var2, var3);
      }

      Object lambda42() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnlength$Mnci, this.staticLink.s2, this.rest);
      }

      int lambda43(Object var1, Object var2) {
         Object var3 = this.staticLink.s1;
         Object var4 = this.start1;

         int var5;
         try {
            var5 = ((Number)var4).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "%string-suffix-length-ci", 1, var4);
         }

         var4 = this.end1;

         int var6;
         try {
            var6 = ((Number)var4).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "%string-suffix-length-ci", 2, var4);
         }

         var4 = this.staticLink.s2;

         int var7;
         try {
            var7 = ((Number)var1).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "%string-suffix-length-ci", 4, var1);
         }

         int var8;
         try {
            var8 = ((Number)var2).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "%string-suffix-length-ci", 5, var2);
         }

         return srfi13.$PcStringSuffixLengthCi(var3, var5, var6, var4, var7, var8);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 36) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 37) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame19 extends ModuleBody {

      final ModuleMethod lambda$Fn44 = new ModuleMethod(this, 42, (Object)null, 0);
      final ModuleMethod lambda$Fn45 = new ModuleMethod(this, 43, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 42?this.lambda44():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 43?this.lambda45(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda44() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Qu, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda45(Object var1, Object var2, Object var3) {
         srfi13.frame20 var4 = new srfi13.frame20();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn46, var4.lambda$Fn47);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 42) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 43) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame2 extends ModuleBody {

      final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 6, (Object)null, 0);
      final ModuleMethod lambda$Fn8 = new ModuleMethod(this, 7, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 6?this.lambda7():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 7?this.lambda8(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda7() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncopy, this.s, this.maybe$Mnstart$Plend);
      }

      CharSequence lambda8(Object var1, Object var2) {
         Object var3 = this.s;

         CharSequence var4;
         try {
            var4 = (CharSequence)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "substring", 1, var3);
         }

         int var5;
         try {
            var5 = ((Number)var1).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "substring", 2, var1);
         }

         int var6;
         try {
            var6 = ((Number)var2).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "substring", 3, var2);
         }

         return strings.substring(var4, var5, var6);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 6) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 7) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame20 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn46 = new ModuleMethod(this, 40, (Object)null, 0);
      final ModuleMethod lambda$Fn47 = new ModuleMethod(this, 41, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame19 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 40?this.lambda46():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 41?this.lambda47(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda46() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Qu, this.staticLink.s2, this.rest);
      }

      Object lambda47(Object var1, Object var2) {
         return srfi13.$PcStringPrefix$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 40) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 41) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame21 extends ModuleBody {

      final ModuleMethod lambda$Fn48 = new ModuleMethod(this, 46, (Object)null, 0);
      final ModuleMethod lambda$Fn49 = new ModuleMethod(this, 47, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 46?this.lambda48():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 47?this.lambda49(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda48() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Qu, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda49(Object var1, Object var2, Object var3) {
         srfi13.frame22 var4 = new srfi13.frame22();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn50, var4.lambda$Fn51);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 46) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 47) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame22 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn50 = new ModuleMethod(this, 44, (Object)null, 0);
      final ModuleMethod lambda$Fn51 = new ModuleMethod(this, 45, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame21 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 44?this.lambda50():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 45?this.lambda51(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda50() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Qu, this.staticLink.s2, this.rest);
      }

      Object lambda51(Object var1, Object var2) {
         return srfi13.$PcStringSuffix$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 44) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 45) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame23 extends ModuleBody {

      final ModuleMethod lambda$Fn52 = new ModuleMethod(this, 50, (Object)null, 0);
      final ModuleMethod lambda$Fn53 = new ModuleMethod(this, 51, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 50?this.lambda52():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 51?this.lambda53(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda52() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnci$Qu, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda53(Object var1, Object var2, Object var3) {
         srfi13.frame24 var4 = new srfi13.frame24();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn54, var4.lambda$Fn55);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 50) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 51) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame24 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn54 = new ModuleMethod(this, 48, (Object)null, 0);
      final ModuleMethod lambda$Fn55 = new ModuleMethod(this, 49, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame23 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 48?this.lambda54():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 49?this.lambda55(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda54() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnci$Qu, this.staticLink.s2, this.rest);
      }

      Object lambda55(Object var1, Object var2) {
         return srfi13.$PcStringPrefixCi$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 48) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 49) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame25 extends ModuleBody {

      final ModuleMethod lambda$Fn56 = new ModuleMethod(this, 54, (Object)null, 0);
      final ModuleMethod lambda$Fn57 = new ModuleMethod(this, 55, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 54?this.lambda56():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 55?this.lambda57(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda56() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnci$Qu, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda57(Object var1, Object var2, Object var3) {
         srfi13.frame26 var4 = new srfi13.frame26();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn58, var4.lambda$Fn59);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 54) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 55) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame26 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn58 = new ModuleMethod(this, 52, (Object)null, 0);
      final ModuleMethod lambda$Fn59 = new ModuleMethod(this, 53, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame25 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 52?this.lambda58():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 53?this.lambda59(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda58() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnci$Qu, this.staticLink.s2, this.rest);
      }

      Object lambda59(Object var1, Object var2) {
         return srfi13.$PcStringSuffixCi$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 52) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 53) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame27 extends ModuleBody {

      final ModuleMethod lambda$Fn60 = new ModuleMethod(this, 58, (Object)null, 0);
      final ModuleMethod lambda$Fn61 = new ModuleMethod(this, 59, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object proc$Eq;
      Object proc$Gr;
      Object proc$Ls;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 58?this.lambda60():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 59?this.lambda61(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda60() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mncompare, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda61(Object var1, Object var2, Object var3) {
         srfi13.frame28 var4 = new srfi13.frame28();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn62, var4.lambda$Fn63);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 58) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 59) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame28 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn62 = new ModuleMethod(this, 56, (Object)null, 0);
      final ModuleMethod lambda$Fn63 = new ModuleMethod(this, 57, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame27 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 56?this.lambda62():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 57?this.lambda63(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda62() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncompare, this.staticLink.s2, this.rest);
      }

      Object lambda63(Object var1, Object var2) {
         return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, this.staticLink.proc$Ls, this.staticLink.proc$Eq, this.staticLink.proc$Gr);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 56) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 57) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame29 extends ModuleBody {

      final ModuleMethod lambda$Fn64 = new ModuleMethod(this, 62, (Object)null, 0);
      final ModuleMethod lambda$Fn65 = new ModuleMethod(this, 63, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object proc$Eq;
      Object proc$Gr;
      Object proc$Ls;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 62?this.lambda64():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 63?this.lambda65(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda64() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mncompare$Mnci, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda65(Object var1, Object var2, Object var3) {
         srfi13.frame30 var4 = new srfi13.frame30();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn66, var4.lambda$Fn67);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 62) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 63) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame3 extends ModuleBody {

      final ModuleMethod lambda$Fn10 = new ModuleMethod(this, 9, (Object)null, 8194);
      final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 8, (Object)null, 0);
      LList maybe$Mnstart$Plend;
      Object proc;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 8?this.lambda9():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 9?this.lambda10(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda10(Object var1, Object var2) {
         return srfi13.$PcStringMap(this.proc, this.s, var1, var2);
      }

      Object lambda9() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnmap, this.s, this.maybe$Mnstart$Plend);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 8) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 9) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame30 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn66 = new ModuleMethod(this, 60, (Object)null, 0);
      final ModuleMethod lambda$Fn67 = new ModuleMethod(this, 61, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame29 staticLink;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 60?this.lambda66():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 61?this.lambda67(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda66() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncompare$Mnci, this.staticLink.s2, this.rest);
      }

      Object lambda67(Object var1, Object var2) {
         return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, this.staticLink.proc$Ls, this.staticLink.proc$Eq, this.staticLink.proc$Gr);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 60) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 61) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame31 extends ModuleBody {

      final ModuleMethod lambda$Fn68 = new ModuleMethod(this, 66, (Object)null, 0);
      final ModuleMethod lambda$Fn69 = new ModuleMethod(this, 67, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 66?this.lambda68():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 67?this.lambda69(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda68() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Eq, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda69(Object var1, Object var2, Object var3) {
         srfi13.frame32 var4 = new srfi13.frame32();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn70, var4.lambda$Fn71);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 66) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 67) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame32 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn70 = new ModuleMethod(this, 64, (Object)null, 0);
      final ModuleMethod lambda$Fn71 = new ModuleMethod(this, 65, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame31 staticLink;


      static Boolean lambda72(Object var0) {
         return Boolean.FALSE;
      }

      static Boolean lambda73(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 64?this.lambda70():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 65?this.lambda71(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda70() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Eq, this.staticLink.s2, this.rest);
      }

      Object lambda71(Object var1, Object var2) {
         Object var3 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(var2, var1));

         boolean var4;
         try {
            var4 = ((Boolean)var3).booleanValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "x", -2, var3);
         }

         if(var4) {
            if(this.staticLink.s1 == this.staticLink.s2) {
               var4 = true;
            } else {
               var4 = false;
            }

            boolean var5 = var4;
            if(var4) {
               var3 = Scheme.numEqu.apply2(this.start1, var1);

               try {
                  var5 = ((Boolean)var3).booleanValue();
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "x", -2, var3);
               }
            }

            return var5?(var5?Boolean.TRUE:Boolean.FALSE):srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, srfi13.lambda$Fn72, misc.values, srfi13.lambda$Fn73);
         } else {
            return var4?Boolean.TRUE:Boolean.FALSE;
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 64) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 65) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame33 extends ModuleBody {

      final ModuleMethod lambda$Fn74 = new ModuleMethod(this, 70, (Object)null, 0);
      final ModuleMethod lambda$Fn75 = new ModuleMethod(this, 71, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 70?this.lambda74():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 71?this.lambda75(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda74() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Ls$Gr, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda75(Object var1, Object var2, Object var3) {
         srfi13.frame34 var4 = new srfi13.frame34();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn76, var4.lambda$Fn77);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 70) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 71) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame34 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn76 = new ModuleMethod(this, 68, (Object)null, 0);
      final ModuleMethod lambda$Fn77 = new ModuleMethod(this, 69, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame33 staticLink;


      static Boolean lambda78(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 68?this.lambda76():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 69?this.lambda77(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda76() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls$Gr, this.staticLink.s2, this.rest);
      }

      Object lambda77(Object var1, Object var2) {
         byte var6 = 1;
         Object var3 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(var2, var1));

         Boolean var4;
         try {
            var4 = Boolean.FALSE;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "x", -2, var3);
         }

         byte var5;
         if(var3 != var4) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         int var9 = var5 + 1 & 1;
         if(var9 != 0) {
            return var9 != 0?Boolean.TRUE:Boolean.FALSE;
         } else {
            if(this.staticLink.s1 == this.staticLink.s2) {
               var5 = 1;
            } else {
               var5 = 0;
            }

            if(var5 != 0) {
               var3 = Scheme.numEqu.apply2(this.start1, var1);

               try {
                  var4 = Boolean.FALSE;
               } catch (ClassCastException var7) {
                  throw new WrongType(var7, "x", -2, var3);
               }

               if(var3 != var4) {
                  var5 = var6;
               } else {
                  var5 = 0;
               }
            }

            var9 = var5 + 1 & 1;
            return var9 != 0?srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, misc.values, srfi13.lambda$Fn78, misc.values):(var9 != 0?Boolean.TRUE:Boolean.FALSE);
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 68) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 69) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame35 extends ModuleBody {

      final ModuleMethod lambda$Fn79 = new ModuleMethod(this, 74, (Object)null, 0);
      final ModuleMethod lambda$Fn80 = new ModuleMethod(this, 75, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 74?this.lambda79():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 75?this.lambda80(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda79() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Ls, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda80(Object var1, Object var2, Object var3) {
         srfi13.frame36 var4 = new srfi13.frame36();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn81, var4.lambda$Fn82);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 74) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 75) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame36 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn81 = new ModuleMethod(this, 72, (Object)null, 0);
      final ModuleMethod lambda$Fn82 = new ModuleMethod(this, 73, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame35 staticLink;


      static Boolean lambda83(Object var0) {
         return Boolean.FALSE;
      }

      static Boolean lambda84(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 72?this.lambda81():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 73?this.lambda82(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda81() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls, this.staticLink.s2, this.rest);
      }

      Object lambda82(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numLss.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numLss.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, misc.values, srfi13.lambda$Fn83, srfi13.lambda$Fn84);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 72) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 73) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame37 extends ModuleBody {

      final ModuleMethod lambda$Fn85 = new ModuleMethod(this, 78, (Object)null, 0);
      final ModuleMethod lambda$Fn86 = new ModuleMethod(this, 79, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 78?this.lambda85():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 79?this.lambda86(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda85() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Gr, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda86(Object var1, Object var2, Object var3) {
         srfi13.frame38 var4 = new srfi13.frame38();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn87, var4.lambda$Fn88);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 78) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 79) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame38 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn87 = new ModuleMethod(this, 76, (Object)null, 0);
      final ModuleMethod lambda$Fn88 = new ModuleMethod(this, 77, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame37 staticLink;


      static Boolean lambda89(Object var0) {
         return Boolean.FALSE;
      }

      static Boolean lambda90(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 76?this.lambda87():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 77?this.lambda88(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda87() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Gr, this.staticLink.s2, this.rest);
      }

      Object lambda88(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numGrt.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numGrt.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, srfi13.lambda$Fn89, srfi13.lambda$Fn90, misc.values);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 76) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 77) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame39 extends ModuleBody {

      final ModuleMethod lambda$Fn91 = new ModuleMethod(this, 82, (Object)null, 0);
      final ModuleMethod lambda$Fn92 = new ModuleMethod(this, 83, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 82?this.lambda91():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 83?this.lambda92(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda91() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Ls$Eq, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda92(Object var1, Object var2, Object var3) {
         srfi13.frame40 var4 = new srfi13.frame40();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn93, var4.lambda$Fn94);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 82) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 83) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame4 extends ModuleBody {

      final ModuleMethod lambda$Fn11 = new ModuleMethod(this, 10, (Object)null, 0);
      final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 11, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object proc;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 10?this.lambda11():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 11?this.lambda12(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda11() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnmap$Ex, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda12(Object var1, Object var2) {
         return srfi13.$PcStringMap$Ex(this.proc, this.s, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 10) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 11) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame40 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn93 = new ModuleMethod(this, 80, (Object)null, 0);
      final ModuleMethod lambda$Fn94 = new ModuleMethod(this, 81, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame39 staticLink;


      static Boolean lambda95(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 80?this.lambda93():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 81?this.lambda94(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda93() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls$Eq, this.staticLink.s2, this.rest);
      }

      Object lambda94(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numLEq.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numLEq.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, misc.values, misc.values, srfi13.lambda$Fn95);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 80) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 81) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame41 extends ModuleBody {

      final ModuleMethod lambda$Fn96 = new ModuleMethod(this, 86, (Object)null, 0);
      final ModuleMethod lambda$Fn97 = new ModuleMethod(this, 87, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 86?this.lambda96():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 87?this.lambda97(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda96() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Gr$Eq, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda97(Object var1, Object var2, Object var3) {
         srfi13.frame42 var4 = new srfi13.frame42();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn98, var4.lambda$Fn99);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 86) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 87) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame42 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn98 = new ModuleMethod(this, 84, (Object)null, 0);
      final ModuleMethod lambda$Fn99 = new ModuleMethod(this, 85, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame41 staticLink;


      static Boolean lambda100(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 84?this.lambda98():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 85?this.lambda99(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda98() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Gr$Eq, this.staticLink.s2, this.rest);
      }

      Object lambda99(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numGEq.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numGEq.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, srfi13.lambda$Fn100, misc.values, misc.values);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 84) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 85) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame43 extends ModuleBody {

      final ModuleMethod lambda$Fn101 = new ModuleMethod(this, 90, (Object)null, 0);
      final ModuleMethod lambda$Fn102 = new ModuleMethod(this, 91, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 90?this.lambda101():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 91?this.lambda102(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda101() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Eq, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda102(Object var1, Object var2, Object var3) {
         srfi13.frame44 var4 = new srfi13.frame44();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn103, var4.lambda$Fn104);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 90) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 91) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame44 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn103 = new ModuleMethod(this, 88, (Object)null, 0);
      final ModuleMethod lambda$Fn104 = new ModuleMethod(this, 89, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame43 staticLink;


      static Boolean lambda105(Object var0) {
         return Boolean.FALSE;
      }

      static Boolean lambda106(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 88?this.lambda103():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 89?this.lambda104(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda103() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Eq, this.staticLink.s2, this.rest);
      }

      Object lambda104(Object var1, Object var2) {
         Object var3 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(var2, var1));

         boolean var4;
         try {
            var4 = ((Boolean)var3).booleanValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "x", -2, var3);
         }

         if(var4) {
            if(this.staticLink.s1 == this.staticLink.s2) {
               var4 = true;
            } else {
               var4 = false;
            }

            boolean var5 = var4;
            if(var4) {
               var3 = Scheme.numEqu.apply2(this.start1, var1);

               try {
                  var5 = ((Boolean)var3).booleanValue();
               } catch (ClassCastException var6) {
                  throw new WrongType(var6, "x", -2, var3);
               }
            }

            return var5?(var5?Boolean.TRUE:Boolean.FALSE):srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, srfi13.lambda$Fn105, misc.values, srfi13.lambda$Fn106);
         } else {
            return var4?Boolean.TRUE:Boolean.FALSE;
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 88) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 89) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame45 extends ModuleBody {

      final ModuleMethod lambda$Fn107 = new ModuleMethod(this, 94, (Object)null, 0);
      final ModuleMethod lambda$Fn108 = new ModuleMethod(this, 95, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 94?this.lambda107():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 95?this.lambda108(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda107() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls$Gr, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda108(Object var1, Object var2, Object var3) {
         srfi13.frame46 var4 = new srfi13.frame46();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn109, var4.lambda$Fn110);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 94) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 95) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame46 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn109 = new ModuleMethod(this, 92, (Object)null, 0);
      final ModuleMethod lambda$Fn110 = new ModuleMethod(this, 93, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame45 staticLink;


      static Boolean lambda111(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 92?this.lambda109():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 93?this.lambda110(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda109() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls$Gr, this.staticLink.s2, this.rest);
      }

      Object lambda110(Object var1, Object var2) {
         byte var6 = 1;
         Object var3 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(var2, var1));

         Boolean var4;
         try {
            var4 = Boolean.FALSE;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "x", -2, var3);
         }

         byte var5;
         if(var3 != var4) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         int var9 = var5 + 1 & 1;
         if(var9 != 0) {
            return var9 != 0?Boolean.TRUE:Boolean.FALSE;
         } else {
            if(this.staticLink.s1 == this.staticLink.s2) {
               var5 = 1;
            } else {
               var5 = 0;
            }

            if(var5 != 0) {
               var3 = Scheme.numEqu.apply2(this.start1, var1);

               try {
                  var4 = Boolean.FALSE;
               } catch (ClassCastException var7) {
                  throw new WrongType(var7, "x", -2, var3);
               }

               if(var3 != var4) {
                  var5 = var6;
               } else {
                  var5 = 0;
               }
            }

            var9 = var5 + 1 & 1;
            return var9 != 0?srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, misc.values, srfi13.lambda$Fn111, misc.values):(var9 != 0?Boolean.TRUE:Boolean.FALSE);
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 92) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 93) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame47 extends ModuleBody {

      final ModuleMethod lambda$Fn112 = new ModuleMethod(this, 98, (Object)null, 0);
      final ModuleMethod lambda$Fn113 = new ModuleMethod(this, 99, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 98?this.lambda112():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 99?this.lambda113(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda112() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda113(Object var1, Object var2, Object var3) {
         srfi13.frame48 var4 = new srfi13.frame48();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn114, var4.lambda$Fn115);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 98) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 99) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame48 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn114 = new ModuleMethod(this, 96, (Object)null, 0);
      final ModuleMethod lambda$Fn115 = new ModuleMethod(this, 97, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame47 staticLink;


      static Boolean lambda116(Object var0) {
         return Boolean.FALSE;
      }

      static Boolean lambda117(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 96?this.lambda114():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 97?this.lambda115(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda114() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls, this.staticLink.s2, this.rest);
      }

      Object lambda115(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numLss.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numLss.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, misc.values, srfi13.lambda$Fn116, srfi13.lambda$Fn117);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 96) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 97) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame49 extends ModuleBody {

      final ModuleMethod lambda$Fn118 = new ModuleMethod(this, 102, (Object)null, 0);
      final ModuleMethod lambda$Fn119 = new ModuleMethod(this, 103, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 102?this.lambda118():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 103?this.lambda119(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda118() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Gr, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda119(Object var1, Object var2, Object var3) {
         srfi13.frame50 var4 = new srfi13.frame50();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn120, var4.lambda$Fn121);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 102) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 103) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame5 extends ModuleBody {

      Object knil;
      Object kons;
      final ModuleMethod lambda$Fn13 = new ModuleMethod(this, 12, (Object)null, 0);
      final ModuleMethod lambda$Fn14 = new ModuleMethod(this, 13, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 12?this.lambda13():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 13?this.lambda14(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda13() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfold, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda14(Object var1, Object var2) {
         Object var3;
         for(var3 = this.knil; Scheme.numLss.apply2(var1, var2) != Boolean.FALSE; var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1)) {
            ApplyToArgs var5 = Scheme.applyToArgs;
            Object var6 = this.kons;
            Object var4 = this.s;

            CharSequence var7;
            try {
               var7 = (CharSequence)var4;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 1, var4);
            }

            int var8;
            try {
               var8 = ((Number)var1).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 2, var1);
            }

            var3 = var5.apply3(var6, Char.make(strings.stringRef(var7, var8)), var3);
         }

         return var3;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 12) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 13) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame50 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn120 = new ModuleMethod(this, 100, (Object)null, 0);
      final ModuleMethod lambda$Fn121 = new ModuleMethod(this, 101, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame49 staticLink;


      static Boolean lambda122(Object var0) {
         return Boolean.FALSE;
      }

      static Boolean lambda123(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 100?this.lambda120():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 101?this.lambda121(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda120() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Gr, this.staticLink.s2, this.rest);
      }

      Object lambda121(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numGrt.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numGrt.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, srfi13.lambda$Fn122, srfi13.lambda$Fn123, misc.values);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 100) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 101) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame51 extends ModuleBody {

      final ModuleMethod lambda$Fn124 = new ModuleMethod(this, 106, (Object)null, 0);
      final ModuleMethod lambda$Fn125 = new ModuleMethod(this, 107, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 106?this.lambda124():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 107?this.lambda125(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda124() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls$Eq, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda125(Object var1, Object var2, Object var3) {
         srfi13.frame52 var4 = new srfi13.frame52();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn126, var4.lambda$Fn127);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 106) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 107) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame52 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn126 = new ModuleMethod(this, 104, (Object)null, 0);
      final ModuleMethod lambda$Fn127 = new ModuleMethod(this, 105, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame51 staticLink;


      static Boolean lambda128(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 104?this.lambda126():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 105?this.lambda127(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda126() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls$Eq, this.staticLink.s2, this.rest);
      }

      Object lambda127(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numLEq.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numLEq.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, misc.values, misc.values, srfi13.lambda$Fn128);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 104) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 105) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame53 extends ModuleBody {

      final ModuleMethod lambda$Fn129 = new ModuleMethod(this, 110, (Object)null, 0);
      final ModuleMethod lambda$Fn130 = new ModuleMethod(this, 111, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object s1;
      Object s2;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 110?this.lambda129():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 111?this.lambda130(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda129() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Gr$Eq, this.s1, this.maybe$Mnstarts$Plends);
      }

      Object lambda130(Object var1, Object var2, Object var3) {
         srfi13.frame54 var4 = new srfi13.frame54();
         var4.staticLink = this;
         var4.rest = var1;
         var4.start1 = var2;
         var4.end1 = var3;
         return call_with_values.callWithValues(var4.lambda$Fn131, var4.lambda$Fn132);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 110) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 111) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame54 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn131 = new ModuleMethod(this, 108, (Object)null, 0);
      final ModuleMethod lambda$Fn132 = new ModuleMethod(this, 109, (Object)null, 8194);
      Object rest;
      Object start1;
      srfi13.frame53 staticLink;


      static Boolean lambda133(Object var0) {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 108?this.lambda131():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 109?this.lambda132(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda131() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Gr$Eq, this.staticLink.s2, this.rest);
      }

      Object lambda132(Object var1, Object var2) {
         boolean var3;
         if(this.staticLink.s1 == this.staticLink.s2) {
            var3 = true;
         } else {
            var3 = false;
         }

         if(var3) {
            if(Scheme.numEqu.apply2(this.start1, var1) != Boolean.FALSE) {
               return Scheme.numGEq.apply2(this.end1, var2);
            }
         } else if(var3) {
            return Scheme.numGEq.apply2(this.end1, var2);
         }

         return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, var1, var2, srfi13.lambda$Fn133, misc.values, misc.values);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 108) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 109) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame55 extends ModuleBody {

      Object char$Mn$Grint;


   }

   public class frame56 extends ModuleBody {

      Object bound;
      final ModuleMethod lambda$Fn134 = new ModuleMethod(this, 112, (Object)null, 0);
      final ModuleMethod lambda$Fn135 = new ModuleMethod(this, 113, (Object)null, 8194);
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 112?this.lambda134():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 113?this.lambda135(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda134() {
         ModuleMethod var1 = srfi13.string$Mnhash;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 912, 55);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda135(Object var1, Object var2) {
         return srfi13.$PcStringHash(this.s, characters.char$Mn$Grinteger, this.bound, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 112) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 113) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame57 extends ModuleBody {

      Object bound;
      final ModuleMethod lambda$Fn136 = new ModuleMethod(this, 114, (Object)null, 0);
      final ModuleMethod lambda$Fn137 = new ModuleMethod(this, 115, (Object)null, 8194);
      Object s;


      static int lambda138(Object var0) {
         Char var1;
         try {
            var1 = (Char)var0;
         } catch (ClassCastException var2) {
            throw new WrongType(var2, "char-downcase", 1, var0);
         }

         return characters.char$To$Integer(unicode.charDowncase(var1));
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 114?this.lambda136():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 115?this.lambda137(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda136() {
         ModuleMethod var1 = srfi13.string$Mnhash$Mnci;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 921, 58);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda137(Object var1, Object var2) {
         return srfi13.$PcStringHash(this.s, srfi13.lambda$Fn138, this.bound, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 114) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 115) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame58 extends ModuleBody {

      final ModuleMethod lambda$Fn139 = new ModuleMethod(this, 116, (Object)null, 0);
      final ModuleMethod lambda$Fn140 = new ModuleMethod(this, 117, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 116?this.lambda139():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 117?this.lambda140(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda139() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnupcase, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda140(Object var1, Object var2) {
         return srfi13.$PcStringMap(unicode.char$Mnupcase, this.s, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 116) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 117) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame59 extends ModuleBody {

      final ModuleMethod lambda$Fn141 = new ModuleMethod(this, 118, (Object)null, 0);
      final ModuleMethod lambda$Fn142 = new ModuleMethod(this, 119, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 118?this.lambda141():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 119?this.lambda142(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda141() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnupcase$Ex, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda142(Object var1, Object var2) {
         return srfi13.$PcStringMap$Ex(unicode.char$Mnupcase, this.s, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 118) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 119) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame6 extends ModuleBody {

      Object knil;
      Object kons;
      final ModuleMethod lambda$Fn15 = new ModuleMethod(this, 14, (Object)null, 0);
      final ModuleMethod lambda$Fn16 = new ModuleMethod(this, 15, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 14?this.lambda15():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 15?this.lambda16(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda15() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfold$Mnright, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda16(Object var1, Object var2) {
         Object var3 = this.knil;

         for(var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1); Scheme.numGEq.apply2(var2, var1) != Boolean.FALSE; var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1)) {
            ApplyToArgs var5 = Scheme.applyToArgs;
            Object var6 = this.kons;
            Object var4 = this.s;

            CharSequence var7;
            try {
               var7 = (CharSequence)var4;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 1, var4);
            }

            int var8;
            try {
               var8 = ((Number)var2).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 2, var2);
            }

            var3 = var5.apply3(var6, Char.make(strings.stringRef(var7, var8)), var3);
         }

         return var3;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 14) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 15) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame60 extends ModuleBody {

      final ModuleMethod lambda$Fn143 = new ModuleMethod(this, 120, (Object)null, 0);
      final ModuleMethod lambda$Fn144 = new ModuleMethod(this, 121, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 120?this.lambda143():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 121?this.lambda144(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda143() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndowncase, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda144(Object var1, Object var2) {
         return srfi13.$PcStringMap(unicode.char$Mndowncase, this.s, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 120) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 121) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame61 extends ModuleBody {

      final ModuleMethod lambda$Fn145 = new ModuleMethod(this, 122, (Object)null, 0);
      final ModuleMethod lambda$Fn146 = new ModuleMethod(this, 123, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 122?this.lambda145():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 123?this.lambda146(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda145() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndowncase$Ex, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda146(Object var1, Object var2) {
         return srfi13.$PcStringMap$Ex(unicode.char$Mndowncase, this.s, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 122) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 123) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame62 extends ModuleBody {

      final ModuleMethod lambda$Fn147 = new ModuleMethod(this, 124, (Object)null, 0);
      final ModuleMethod lambda$Fn148 = new ModuleMethod(this, 125, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 124?this.lambda147():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 125?this.lambda148(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda147() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntitlecase$Ex, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda148(Object var1, Object var2) {
         return srfi13.$PcStringTitlecase$Ex(this.s, var1, var2);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 124) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 125) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame63 extends ModuleBody {

      final ModuleMethod lambda$Fn149 = new ModuleMethod(this, 126, (Object)null, 0);
      final ModuleMethod lambda$Fn150 = new ModuleMethod(this, 127, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 126?this.lambda149():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 127?this.lambda150(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda149() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntitlecase$Ex, this.s, this.maybe$Mnstart$Plend);
      }

      CharSequence lambda150(Object var1, Object var2) {
         Object var3 = this.s;

         CharSequence var4;
         try {
            var4 = (CharSequence)var3;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "substring", 1, var3);
         }

         int var5;
         try {
            var5 = ((Number)var1).intValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "substring", 2, var1);
         }

         int var6;
         try {
            var6 = ((Number)var2).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "substring", 3, var2);
         }

         CharSequence var10 = strings.substring(var4, var5, var6);
         srfi13.$PcStringTitlecase$Ex(var10, srfi13.Lit0, AddOp.$Mn.apply2(var2, var1));
         return var10;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 126) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 127) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame64 extends ModuleBody {

      final ModuleMethod lambda$Fn151;
      Object n;
      Object s;


      public frame64() {
         ModuleMethod var1 = new ModuleMethod(this, 128, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:996");
         this.lambda$Fn151 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 128?(this.lambda151(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda151(Object var1) {
         boolean var7 = numbers.isInteger(this.n);
         boolean var6 = var7;
         if(var7) {
            var7 = numbers.isExact(this.n);
            var6 = var7;
            if(var7) {
               NumberCompare var2 = Scheme.numLEq;
               IntNum var3 = srfi13.Lit0;
               Object var4 = this.n;
               var1 = this.s;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var1;
               } catch (ClassCastException var8) {
                  throw new WrongType(var8, "string-length", 1, var1);
               }

               var6 = ((Boolean)var2.apply3(var3, var4, Integer.valueOf(strings.stringLength(var5)))).booleanValue();
            }
         }

         return var6;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 128) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame65 extends ModuleBody {

      final ModuleMethod lambda$Fn152;
      int len;
      Object n;


      public frame65() {
         ModuleMethod var1 = new ModuleMethod(this, 129, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:1004");
         this.lambda$Fn152 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 129?(this.lambda152(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda152(Object var1) {
         boolean var3 = numbers.isInteger(this.n);
         boolean var2 = var3;
         if(var3) {
            var3 = numbers.isExact(this.n);
            var2 = var3;
            if(var3) {
               var2 = ((Boolean)Scheme.numLEq.apply3(srfi13.Lit0, this.n, Integer.valueOf(this.len))).booleanValue();
            }
         }

         return var2;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 129) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame66 extends ModuleBody {

      final ModuleMethod lambda$Fn153;
      int len;
      Object n;


      public frame66() {
         ModuleMethod var1 = new ModuleMethod(this, 130, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:1010");
         this.lambda$Fn153 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 130?(this.lambda153(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda153(Object var1) {
         boolean var3 = numbers.isInteger(this.n);
         boolean var2 = var3;
         if(var3) {
            var3 = numbers.isExact(this.n);
            var2 = var3;
            if(var3) {
               var2 = ((Boolean)Scheme.numLEq.apply3(srfi13.Lit0, this.n, Integer.valueOf(this.len))).booleanValue();
            }
         }

         return var2;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 130) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame67 extends ModuleBody {

      final ModuleMethod lambda$Fn154;
      int len;
      Object n;


      public frame67() {
         ModuleMethod var1 = new ModuleMethod(this, 131, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:1016");
         this.lambda$Fn154 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 131?(this.lambda154(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      boolean lambda154(Object var1) {
         boolean var3 = numbers.isInteger(this.n);
         boolean var2 = var3;
         if(var3) {
            var3 = numbers.isExact(this.n);
            var2 = var3;
            if(var3) {
               var2 = ((Boolean)Scheme.numLEq.apply3(srfi13.Lit0, this.n, Integer.valueOf(this.len))).booleanValue();
            }
         }

         return var2;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 131) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame68 extends ModuleBody {

      final ModuleMethod lambda$Fn155 = new ModuleMethod(this, 132, (Object)null, 0);
      final ModuleMethod lambda$Fn156 = new ModuleMethod(this, 133, (Object)null, 8194);
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 132?this.lambda155():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 133?this.lambda156(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda155() {
         ModuleMethod var1 = srfi13.string$Mntrim;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 1023, 53);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda156(Object var1, Object var2) {
         Object var3 = this.s;
         Location var4 = srfi13.loc$criterion;

         Object var11;
         try {
            var11 = var4.get();
         } catch (UnboundLocationException var10) {
            var10.setLine("srfi13.scm", 1024, 29);
            throw var10;
         }

         var1 = srfi13.stringSkip$V(var3, var11, new Object[]{var1, var2});
         if(var1 != Boolean.FALSE) {
            var3 = this.s;

            CharSequence var12;
            try {
               var12 = (CharSequence)var3;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "%substring/shared", 0, var3);
            }

            int var5;
            try {
               var5 = ((Number)var1).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "%substring/shared", 1, var1);
            }

            int var6;
            try {
               var6 = ((Number)var2).intValue();
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "%substring/shared", 2, var2);
            }

            return srfi13.$PcSubstring$SlShared(var12, var5, var6);
         } else {
            return "";
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 132) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 133) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame69 extends ModuleBody {

      final ModuleMethod lambda$Fn157 = new ModuleMethod(this, 134, (Object)null, 0);
      final ModuleMethod lambda$Fn158 = new ModuleMethod(this, 135, (Object)null, 8194);
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 134?this.lambda157():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 135?this.lambda158(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda157() {
         ModuleMethod var1 = srfi13.string$Mntrim$Mnright;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 1030, 59);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda158(Object var1, Object var2) {
         Object var3 = this.s;
         Location var4 = srfi13.loc$criterion;

         Object var10;
         try {
            var10 = var4.get();
         } catch (UnboundLocationException var8) {
            var8.setLine("srfi13.scm", 1031, 35);
            throw var8;
         }

         var3 = srfi13.stringSkipRight$V(var3, var10, new Object[]{var1, var2});
         if(var3 != Boolean.FALSE) {
            var1 = this.s;

            CharSequence var9;
            try {
               var9 = (CharSequence)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "%substring/shared", 0, var1);
            }

            var1 = AddOp.$Pl.apply2(srfi13.Lit1, var3);

            int var5;
            try {
               var5 = ((Number)var1).intValue();
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "%substring/shared", 2, var1);
            }

            return srfi13.$PcSubstring$SlShared(var9, 0, var5);
         } else {
            return "";
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 134) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 135) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame7 extends ModuleBody {

      final ModuleMethod lambda$Fn19 = new ModuleMethod(this, 16, (Object)null, 0);
      final ModuleMethod lambda$Fn20 = new ModuleMethod(this, 17, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object proc;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 16?this.lambda19():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 17?this.lambda20(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda19() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfor$Mneach, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda20(Object var1, Object var2) {
         while(Scheme.numLss.apply2(var1, var2) != Boolean.FALSE) {
            ApplyToArgs var4 = Scheme.applyToArgs;
            Object var5 = this.proc;
            Object var3 = this.s;

            CharSequence var6;
            try {
               var6 = (CharSequence)var3;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var3);
            }

            int var7;
            try {
               var7 = ((Number)var1).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-ref", 2, var1);
            }

            var4.apply2(var5, Char.make(strings.stringRef(var6, var7)));
            var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
         }

         return Values.empty;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 16) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 17) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame70 extends ModuleBody {

      final ModuleMethod lambda$Fn159 = new ModuleMethod(this, 136, (Object)null, 0);
      final ModuleMethod lambda$Fn160 = new ModuleMethod(this, 137, (Object)null, 8194);
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 136?this.lambda159():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 137?this.lambda160(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda159() {
         ModuleMethod var1 = srfi13.string$Mntrim$Mnboth;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 1037, 58);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda160(Object var1, Object var2) {
         Object var3 = this.s;
         Location var4 = srfi13.loc$criterion;

         Object var16;
         try {
            var16 = var4.get();
         } catch (UnboundLocationException var14) {
            var14.setLine("srfi13.scm", 1038, 29);
            throw var14;
         }

         var1 = srfi13.stringSkip$V(var3, var16, new Object[]{var1, var2});
         if(var1 != Boolean.FALSE) {
            var16 = this.s;

            CharSequence var15;
            try {
               var15 = (CharSequence)var16;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "%substring/shared", 0, var16);
            }

            int var8;
            try {
               var8 = ((Number)var1).intValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "%substring/shared", 1, var1);
            }

            AddOp var17 = AddOp.$Pl;
            IntNum var5 = srfi13.Lit1;
            Object var6 = this.s;
            Location var7 = srfi13.loc$criterion;

            Object var18;
            try {
               var18 = var7.get();
            } catch (UnboundLocationException var11) {
               var11.setLine("srfi13.scm", 1040, 58);
               throw var11;
            }

            var1 = var17.apply2(var5, srfi13.stringSkipRight$V(var6, var18, new Object[]{var1, var2}));

            int var9;
            try {
               var9 = ((Number)var1).intValue();
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "%substring/shared", 2, var1);
            }

            return srfi13.$PcSubstring$SlShared(var15, var8, var9);
         } else {
            return "";
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 136) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 137) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame71 extends ModuleBody {

      final ModuleMethod lambda$Fn161 = new ModuleMethod(this, 138, (Object)null, 0);
      final ModuleMethod lambda$Fn162 = new ModuleMethod(this, 139, (Object)null, 8194);
      Object n;
      Object s;


      static boolean lambda163(Object var0) {
         boolean var2 = numbers.isInteger(var0);
         boolean var1 = var2;
         if(var2) {
            var2 = numbers.isExact(var0);
            var1 = var2;
            if(var2) {
               var1 = ((Boolean)Scheme.numLEq.apply2(srfi13.Lit0, var0)).booleanValue();
            }
         }

         return var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 138?this.lambda161():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 139?this.lambda162(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda161() {
         ModuleMethod var1 = srfi13.string$Mnpad$Mnright;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 1046, 58);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda162(Object var1, Object var2) {
         ApplyToArgs var3 = Scheme.applyToArgs;
         Location var4 = srfi13.loc$check$Mnarg;

         Object var18;
         try {
            var18 = var4.get();
         } catch (UnboundLocationException var15) {
            var15.setLine("srfi13.scm", 1047, 7);
            throw var15;
         }

         var3.apply4(var18, srfi13.lambda$Fn163, this.n, srfi13.string$Mnpad$Mnright);
         Object var16 = AddOp.$Mn.apply2(var2, var1);
         int var6;
         int var7;
         if(Scheme.numLEq.apply2(this.n, var16) != Boolean.FALSE) {
            var2 = this.s;

            CharSequence var17;
            try {
               var17 = (CharSequence)var2;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "%substring/shared", 0, var2);
            }

            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "%substring/shared", 1, var1);
            }

            var1 = AddOp.$Pl.apply2(var1, this.n);

            try {
               var7 = ((Number)var1).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "%substring/shared", 2, var1);
            }

            return srfi13.$PcSubstring$SlShared(var17, var6, var7);
         } else {
            var16 = this.n;

            try {
               var6 = ((Number)var16).intValue();
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "make-string", 1, var16);
            }

            CharSequence var19 = strings.makeString(var6, LangPrimType.charType);
            var16 = this.s;

            CharSequence var5;
            try {
               var5 = (CharSequence)var16;
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "%string-copy!", 2, var16);
            }

            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "%string-copy!", 3, var1);
            }

            try {
               var7 = ((Number)var2).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "%string-copy!", 4, var2);
            }

            srfi13.$PcStringCopy$Ex(var19, 0, var5, var6, var7);
            return var19;
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 138) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 139) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame72 extends ModuleBody {

      final ModuleMethod lambda$Fn164 = new ModuleMethod(this, 140, (Object)null, 0);
      final ModuleMethod lambda$Fn165 = new ModuleMethod(this, 141, (Object)null, 8194);
      Object n;
      Object s;


      static boolean lambda166(Object var0) {
         boolean var2 = numbers.isInteger(var0);
         boolean var1 = var2;
         if(var2) {
            var2 = numbers.isExact(var0);
            var1 = var2;
            if(var2) {
               var1 = ((Boolean)Scheme.numLEq.apply2(srfi13.Lit0, var0)).booleanValue();
            }
         }

         return var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 140?this.lambda164():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 141?this.lambda165(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda164() {
         ModuleMethod var1 = srfi13.string$Mnpad;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 1058, 52);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda165(Object var1, Object var2) {
         ApplyToArgs var3 = Scheme.applyToArgs;
         Location var4 = srfi13.loc$check$Mnarg;

         Object var20;
         try {
            var20 = var4.get();
         } catch (UnboundLocationException var17) {
            var17.setLine("srfi13.scm", 1059, 7);
            throw var17;
         }

         var3.apply4(var20, srfi13.lambda$Fn166, this.n, srfi13.string$Mnpad);
         var20 = AddOp.$Mn.apply2(var2, var1);
         int var6;
         int var7;
         CharSequence var19;
         if(Scheme.numLEq.apply2(this.n, var20) != Boolean.FALSE) {
            var1 = this.s;

            try {
               var19 = (CharSequence)var1;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "%substring/shared", 0, var1);
            }

            var1 = AddOp.$Mn.apply2(var2, this.n);

            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "%substring/shared", 1, var1);
            }

            try {
               var7 = ((Number)var2).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "%substring/shared", 2, var2);
            }

            return srfi13.$PcSubstring$SlShared(var19, var6, var7);
         } else {
            Object var18 = this.n;

            try {
               var6 = ((Number)var18).intValue();
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "make-string", 1, var18);
            }

            var19 = strings.makeString(var6, LangPrimType.charType);
            var20 = AddOp.$Mn.apply2(this.n, var20);

            try {
               var6 = ((Number)var20).intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "%string-copy!", 1, var20);
            }

            var20 = this.s;

            CharSequence var5;
            try {
               var5 = (CharSequence)var20;
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "%string-copy!", 2, var20);
            }

            try {
               var7 = ((Number)var1).intValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "%string-copy!", 3, var1);
            }

            int var8;
            try {
               var8 = ((Number)var2).intValue();
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "%string-copy!", 4, var2);
            }

            srfi13.$PcStringCopy$Ex(var19, var6, var5, var7, var8);
            return var19;
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 140) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 141) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame73 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn167 = new ModuleMethod(this, 145, (Object)null, 0);
      final ModuleMethod lambda$Fn168 = new ModuleMethod(this, 146, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 145?this.lambda167():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 146?this.lambda168(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda167() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndelete, this.s, this.maybe$Mnstart$Plend);
      }

      CharSequence lambda168(Object var1, Object var2) {
         srfi13.frame74 var4 = new srfi13.frame74();
         var4.staticLink = this;
         int var6;
         Object var13;
         if(misc.isProcedure(this.criterion)) {
            var13 = AddOp.$Mn.apply2(var2, var1);

            try {
               var6 = ((Number)var13).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "make-string", 1, var13);
            }

            var4.temp = strings.makeString(var6);
            var1 = srfi13.stringFold$V(var4.lambda$Fn169, srfi13.Lit0, this.s, new Object[]{var1, var2});
            if(Scheme.numEqu.apply2(var1, var13) != Boolean.FALSE) {
               return var4.temp;
            } else {
               CharSequence var12 = var4.temp;

               try {
                  var6 = ((Number)var1).intValue();
               } catch (ClassCastException var7) {
                  throw new WrongType(var7, "substring", 3, var1);
               }

               return strings.substring(var12, 0, var6);
            }
         } else {
            ApplyToArgs var3 = Scheme.applyToArgs;
            Location var5 = srfi13.loc$char$Mnset$Qu;

            Object var14;
            try {
               var14 = var5.get();
            } catch (UnboundLocationException var11) {
               var11.setLine("srfi13.scm", 1096, 22);
               throw var11;
            }

            if(var3.apply2(var14, this.criterion) != Boolean.FALSE) {
               var13 = this.criterion;
            } else if(characters.isChar(this.criterion)) {
               var3 = Scheme.applyToArgs;
               var5 = srfi13.loc$char$Mnset;

               try {
                  var14 = var5.get();
               } catch (UnboundLocationException var10) {
                  var10.setLine("srfi13.scm", 1097, 26);
                  throw var10;
               }

               var13 = var3.apply2(var14, this.criterion);
            } else {
               var13 = misc.error$V("string-delete criterion not predicate, char or char-set", new Object[]{this.criterion});
            }

            var4.cset = var13;
            var13 = srfi13.stringFold$V(var4.lambda$Fn170, srfi13.Lit0, this.s, new Object[]{var1, var2});

            try {
               var6 = ((Number)var13).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "make-string", 1, var13);
            }

            var4.ans = strings.makeString(var6);
            srfi13.stringFold$V(var4.lambda$Fn171, srfi13.Lit0, this.s, new Object[]{var1, var2});
            return var4.ans;
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 145) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 146) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame74 extends ModuleBody {

      CharSequence ans;
      Object cset;
      final ModuleMethod lambda$Fn169;
      final ModuleMethod lambda$Fn170;
      final ModuleMethod lambda$Fn171;
      srfi13.frame73 staticLink;
      CharSequence temp;


      public frame74() {
         ModuleMethod var1 = new ModuleMethod(this, 142, (Object)null, 8194);
         var1.setProperty("source-location", "srfi13.scm:1089");
         this.lambda$Fn169 = var1;
         var1 = new ModuleMethod(this, 143, (Object)null, 8194);
         var1.setProperty("source-location", "srfi13.scm:1099");
         this.lambda$Fn170 = var1;
         var1 = new ModuleMethod(this, 144, (Object)null, 8194);
         var1.setProperty("source-location", "srfi13.scm:1104");
         this.lambda$Fn171 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         switch(var1.selector) {
         case 142:
            return this.lambda169(var2, var3);
         case 143:
            return this.lambda170(var2, var3);
         case 144:
            return this.lambda171(var2, var3);
         default:
            return super.apply2(var1, var2, var3);
         }
      }

      Object lambda169(Object var1, Object var2) {
         if(Scheme.applyToArgs.apply2(this.staticLink.criterion, var1) != Boolean.FALSE) {
            return var2;
         } else {
            CharSequence var4 = this.temp;

            CharSeq var5;
            try {
               var5 = (CharSeq)var4;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-set!", 1, var4);
            }

            int var6;
            try {
               var6 = ((Number)var2).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-set!", 2, var2);
            }

            char var3;
            try {
               var3 = ((Char)var1).charValue();
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-set!", 3, var1);
            }

            strings.stringSet$Ex(var5, var6, var3);
            return AddOp.$Pl.apply2(var2, srfi13.Lit1);
         }
      }

      Object lambda170(Object var1, Object var2) {
         ApplyToArgs var3 = Scheme.applyToArgs;
         Location var4 = srfi13.loc$char$Mnset$Mncontains$Qu;

         Object var6;
         try {
            var6 = var4.get();
         } catch (UnboundLocationException var5) {
            var5.setLine("srfi13.scm", 1099, 45);
            throw var5;
         }

         return var3.apply3(var6, this.cset, var1) != Boolean.FALSE?var2:AddOp.$Pl.apply2(var2, srfi13.Lit1);
      }

      Object lambda171(Object var1, Object var2) {
         ApplyToArgs var4 = Scheme.applyToArgs;
         Location var5 = srfi13.loc$char$Mnset$Mncontains$Qu;

         Object var12;
         try {
            var12 = var5.get();
         } catch (UnboundLocationException var10) {
            var10.setLine("srfi13.scm", 1104, 35);
            throw var10;
         }

         if(var4.apply3(var12, this.cset, var1) != Boolean.FALSE) {
            return var2;
         } else {
            CharSequence var11 = this.ans;

            CharSeq var13;
            try {
               var13 = (CharSeq)var11;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-set!", 1, var11);
            }

            int var6;
            try {
               var6 = ((Number)var2).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-set!", 2, var2);
            }

            char var3;
            try {
               var3 = ((Char)var1).charValue();
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-set!", 3, var1);
            }

            strings.stringSet$Ex(var13, var6, var3);
            return AddOp.$Pl.apply2(var2, srfi13.Lit1);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         switch(var1.selector) {
         case 142:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         case 143:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         case 144:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         default:
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame75 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn172 = new ModuleMethod(this, 150, (Object)null, 0);
      final ModuleMethod lambda$Fn173 = new ModuleMethod(this, 151, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 150?this.lambda172():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 151?this.lambda173(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda172() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfilter, this.s, this.maybe$Mnstart$Plend);
      }

      CharSequence lambda173(Object var1, Object var2) {
         srfi13.frame76 var4 = new srfi13.frame76();
         var4.staticLink = this;
         int var6;
         Object var13;
         if(misc.isProcedure(this.criterion)) {
            var13 = AddOp.$Mn.apply2(var2, var1);

            try {
               var6 = ((Number)var13).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "make-string", 1, var13);
            }

            var4.temp = strings.makeString(var6);
            var1 = srfi13.stringFold$V(var4.lambda$Fn174, srfi13.Lit0, this.s, new Object[]{var1, var2});
            if(Scheme.numEqu.apply2(var1, var13) != Boolean.FALSE) {
               return var4.temp;
            } else {
               CharSequence var12 = var4.temp;

               try {
                  var6 = ((Number)var1).intValue();
               } catch (ClassCastException var7) {
                  throw new WrongType(var7, "substring", 3, var1);
               }

               return strings.substring(var12, 0, var6);
            }
         } else {
            ApplyToArgs var3 = Scheme.applyToArgs;
            Location var5 = srfi13.loc$char$Mnset$Qu;

            Object var14;
            try {
               var14 = var5.get();
            } catch (UnboundLocationException var11) {
               var11.setLine("srfi13.scm", 1124, 22);
               throw var11;
            }

            if(var3.apply2(var14, this.criterion) != Boolean.FALSE) {
               var13 = this.criterion;
            } else if(characters.isChar(this.criterion)) {
               var3 = Scheme.applyToArgs;
               var5 = srfi13.loc$char$Mnset;

               try {
                  var14 = var5.get();
               } catch (UnboundLocationException var10) {
                  var10.setLine("srfi13.scm", 1125, 26);
                  throw var10;
               }

               var13 = var3.apply2(var14, this.criterion);
            } else {
               var13 = misc.error$V("string-delete criterion not predicate, char or char-set", new Object[]{this.criterion});
            }

            var4.cset = var13;
            var13 = srfi13.stringFold$V(var4.lambda$Fn175, srfi13.Lit0, this.s, new Object[]{var1, var2});

            try {
               var6 = ((Number)var13).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "make-string", 1, var13);
            }

            var4.ans = strings.makeString(var6);
            srfi13.stringFold$V(var4.lambda$Fn176, srfi13.Lit0, this.s, new Object[]{var1, var2});
            return var4.ans;
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 150) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 151) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame76 extends ModuleBody {

      CharSequence ans;
      Object cset;
      final ModuleMethod lambda$Fn174;
      final ModuleMethod lambda$Fn175;
      final ModuleMethod lambda$Fn176;
      srfi13.frame75 staticLink;
      CharSequence temp;


      public frame76() {
         ModuleMethod var1 = new ModuleMethod(this, 147, (Object)null, 8194);
         var1.setProperty("source-location", "srfi13.scm:1116");
         this.lambda$Fn174 = var1;
         var1 = new ModuleMethod(this, 148, (Object)null, 8194);
         var1.setProperty("source-location", "srfi13.scm:1128");
         this.lambda$Fn175 = var1;
         var1 = new ModuleMethod(this, 149, (Object)null, 8194);
         var1.setProperty("source-location", "srfi13.scm:1133");
         this.lambda$Fn176 = var1;
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         switch(var1.selector) {
         case 147:
            return this.lambda174(var2, var3);
         case 148:
            return this.lambda175(var2, var3);
         case 149:
            return this.lambda176(var2, var3);
         default:
            return super.apply2(var1, var2, var3);
         }
      }

      Object lambda174(Object var1, Object var2) {
         Object var4 = var2;
         if(Scheme.applyToArgs.apply2(this.staticLink.criterion, var1) != Boolean.FALSE) {
            CharSequence var10 = this.temp;

            CharSeq var5;
            try {
               var5 = (CharSeq)var10;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-set!", 1, var10);
            }

            int var6;
            try {
               var6 = ((Number)var2).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-set!", 2, var2);
            }

            char var3;
            try {
               var3 = ((Char)var1).charValue();
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-set!", 3, var1);
            }

            strings.stringSet$Ex(var5, var6, var3);
            var4 = AddOp.$Pl.apply2(var2, srfi13.Lit1);
         }

         return var4;
      }

      Object lambda175(Object var1, Object var2) {
         ApplyToArgs var4 = Scheme.applyToArgs;
         Location var3 = srfi13.loc$char$Mnset$Mncontains$Qu;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var6) {
            var6.setLine("srfi13.scm", 1128, 45);
            throw var6;
         }

         Object var7 = var2;
         if(var4.apply3(var5, this.cset, var1) != Boolean.FALSE) {
            var7 = AddOp.$Pl.apply2(var2, srfi13.Lit1);
         }

         return var7;
      }

      Object lambda176(Object var1, Object var2) {
         ApplyToArgs var5 = Scheme.applyToArgs;
         Location var4 = srfi13.loc$char$Mnset$Mncontains$Qu;

         Object var6;
         try {
            var6 = var4.get();
         } catch (UnboundLocationException var11) {
            var11.setLine("srfi13.scm", 1133, 35);
            throw var11;
         }

         Object var12 = var2;
         if(var5.apply3(var6, this.cset, var1) != Boolean.FALSE) {
            CharSequence var13 = this.ans;

            CharSeq var14;
            try {
               var14 = (CharSeq)var13;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-set!", 1, var13);
            }

            int var7;
            try {
               var7 = ((Number)var2).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-set!", 2, var2);
            }

            char var3;
            try {
               var3 = ((Char)var1).charValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-set!", 3, var1);
            }

            strings.stringSet$Ex(var14, var7, var3);
            var12 = AddOp.$Pl.apply2(var2, srfi13.Lit1);
         }

         return var12;
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         switch(var1.selector) {
         case 147:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         case 148:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         case 149:
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         default:
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame77 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn177 = new ModuleMethod(this, 152, (Object)null, 0);
      final ModuleMethod lambda$Fn178 = new ModuleMethod(this, 153, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object str;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 152?this.lambda177():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 153?this.lambda178(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda177() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnindex, this.str, this.maybe$Mnstart$Plend);
      }

      Object lambda178(Object var1, Object var2) {
         Object var3;
         Object var4;
         int var8;
         boolean var9;
         if(characters.isChar(this.criterion)) {
            while(true) {
               var3 = Scheme.numLss.apply2(var1, var2);

               try {
                  var9 = ((Boolean)var3).booleanValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "x", -2, var3);
               }

               if(!var9) {
                  if(var9) {
                     return Boolean.TRUE;
                  }

                  return Boolean.FALSE;
               }

               var4 = this.criterion;

               Char var23;
               try {
                  var23 = (Char)var4;
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "char=?", 1, var4);
               }

               var4 = this.str;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var4;
               } catch (ClassCastException var15) {
                  throw new WrongType(var15, "string-ref", 1, var4);
               }

               try {
                  var8 = ((Number)var1).intValue();
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "string-ref", 2, var1);
               }

               if(characters.isChar$Eq(var23, Char.make(strings.stringRef(var5, var8)))) {
                  break;
               }

               var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
            }
         } else {
            ApplyToArgs var24 = Scheme.applyToArgs;
            Location var25 = srfi13.loc$char$Mnset$Qu;

            try {
               var4 = var25.get();
            } catch (UnboundLocationException var21) {
               var21.setLine("srfi13.scm", 1159, 5);
               throw var21;
            }

            Object var27;
            ApplyToArgs var26;
            if(var24.apply2(var4, this.criterion) != Boolean.FALSE) {
               var3 = var1;

               while(true) {
                  var1 = Scheme.numLss.apply2(var3, var2);

                  try {
                     var9 = ((Boolean)var1).booleanValue();
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "x", -2, var1);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var26 = Scheme.applyToArgs;
                  Location var22 = srfi13.loc$char$Mnset$Mncontains$Qu;

                  try {
                     var27 = var22.get();
                  } catch (UnboundLocationException var12) {
                     var12.setLine("srfi13.scm", 1162, 9);
                     throw var12;
                  }

                  Object var6 = this.criterion;
                  var1 = this.str;

                  CharSequence var7;
                  try {
                     var7 = (CharSequence)var1;
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "string-ref", 1, var1);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var10) {
                     throw new WrongType(var10, "string-ref", 2, var3);
                  }

                  var1 = var3;
                  if(var26.apply3(var27, var6, Char.make(strings.stringRef(var7, var8))) != Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
               }
            } else {
               if(!misc.isProcedure(this.criterion)) {
                  return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnindex, this.criterion});
               }

               var3 = var1;

               while(true) {
                  var1 = Scheme.numLss.apply2(var3, var2);

                  try {
                     var9 = ((Boolean)var1).booleanValue();
                  } catch (ClassCastException var18) {
                     throw new WrongType(var18, "x", -2, var1);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var26 = Scheme.applyToArgs;
                  var27 = this.criterion;
                  var1 = this.str;

                  CharSequence var28;
                  try {
                     var28 = (CharSequence)var1;
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "string-ref", 1, var1);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var19) {
                     throw new WrongType(var19, "string-ref", 2, var3);
                  }

                  var1 = var3;
                  if(var26.apply2(var27, Char.make(strings.stringRef(var28, var8))) != Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
               }
            }
         }

         return var1;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 152) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 153) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame78 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn179 = new ModuleMethod(this, 154, (Object)null, 0);
      final ModuleMethod lambda$Fn180 = new ModuleMethod(this, 155, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object str;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 154?this.lambda179():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 155?this.lambda180(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda179() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnindex$Mnright, this.str, this.maybe$Mnstart$Plend);
      }

      Object lambda180(Object var1, Object var2) {
         Object var3;
         Object var4;
         int var8;
         boolean var9;
         if(characters.isChar(this.criterion)) {
            var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1);

            while(true) {
               var3 = Scheme.numGEq.apply2(var2, var1);

               try {
                  var9 = ((Boolean)var3).booleanValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "x", -2, var3);
               }

               if(!var9) {
                  if(var9) {
                     return Boolean.TRUE;
                  }

                  return Boolean.FALSE;
               }

               var4 = this.criterion;

               Char var23;
               try {
                  var23 = (Char)var4;
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "char=?", 1, var4);
               }

               var4 = this.str;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var4;
               } catch (ClassCastException var15) {
                  throw new WrongType(var15, "string-ref", 1, var4);
               }

               try {
                  var8 = ((Number)var2).intValue();
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "string-ref", 2, var2);
               }

               if(characters.isChar$Eq(var23, Char.make(strings.stringRef(var5, var8)))) {
                  break;
               }

               var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1);
            }
         } else {
            ApplyToArgs var24 = Scheme.applyToArgs;
            Location var25 = srfi13.loc$char$Mnset$Qu;

            try {
               var4 = var25.get();
            } catch (UnboundLocationException var21) {
               var21.setLine("srfi13.scm", 1179, 5);
               throw var21;
            }

            Object var27;
            ApplyToArgs var26;
            if(var24.apply2(var4, this.criterion) != Boolean.FALSE) {
               var3 = AddOp.$Mn.apply2(var2, srfi13.Lit1);

               while(true) {
                  var2 = Scheme.numGEq.apply2(var3, var1);

                  try {
                     var9 = ((Boolean)var2).booleanValue();
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "x", -2, var2);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var26 = Scheme.applyToArgs;
                  Location var22 = srfi13.loc$char$Mnset$Mncontains$Qu;

                  try {
                     var27 = var22.get();
                  } catch (UnboundLocationException var12) {
                     var12.setLine("srfi13.scm", 1182, 9);
                     throw var12;
                  }

                  Object var6 = this.criterion;
                  var2 = this.str;

                  CharSequence var7;
                  try {
                     var7 = (CharSequence)var2;
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "string-ref", 1, var2);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var10) {
                     throw new WrongType(var10, "string-ref", 2, var3);
                  }

                  var2 = var3;
                  if(var26.apply3(var27, var6, Char.make(strings.stringRef(var7, var8))) != Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Mn.apply2(var3, srfi13.Lit1);
               }
            } else {
               if(!misc.isProcedure(this.criterion)) {
                  return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnindex$Mnright, this.criterion});
               }

               var3 = AddOp.$Mn.apply2(var2, srfi13.Lit1);

               while(true) {
                  var2 = Scheme.numGEq.apply2(var3, var1);

                  try {
                     var9 = ((Boolean)var2).booleanValue();
                  } catch (ClassCastException var18) {
                     throw new WrongType(var18, "x", -2, var2);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var26 = Scheme.applyToArgs;
                  var27 = this.criterion;
                  var2 = this.str;

                  CharSequence var28;
                  try {
                     var28 = (CharSequence)var2;
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "string-ref", 1, var2);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var19) {
                     throw new WrongType(var19, "string-ref", 2, var3);
                  }

                  var2 = var3;
                  if(var26.apply2(var27, Char.make(strings.stringRef(var28, var8))) != Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Mn.apply2(var3, srfi13.Lit1);
               }
            }
         }

         return var2;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 154) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 155) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame79 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn181 = new ModuleMethod(this, 156, (Object)null, 0);
      final ModuleMethod lambda$Fn182 = new ModuleMethod(this, 157, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object str;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 156?this.lambda181():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 157?this.lambda182(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda181() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnskip, this.str, this.maybe$Mnstart$Plend);
      }

      Object lambda182(Object var1, Object var2) {
         Object var3;
         int var8;
         boolean var9;
         if(characters.isChar(this.criterion)) {
            var3 = var1;

            while(true) {
               var1 = Scheme.numLss.apply2(var3, var2);

               try {
                  var9 = ((Boolean)var1).booleanValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "x", -2, var1);
               }

               if(!var9) {
                  if(!var9) {
                     return Boolean.FALSE;
                  }

                  var1 = Boolean.TRUE;
                  break;
               }

               var1 = this.criterion;

               Char var4;
               try {
                  var4 = (Char)var1;
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "char=?", 1, var1);
               }

               var1 = this.str;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var1;
               } catch (ClassCastException var15) {
                  throw new WrongType(var15, "string-ref", 1, var1);
               }

               try {
                  var8 = ((Number)var3).intValue();
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "string-ref", 2, var3);
               }

               var1 = var3;
               if(!characters.isChar$Eq(var4, Char.make(strings.stringRef(var5, var8)))) {
                  break;
               }

               var3 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
            }
         } else {
            ApplyToArgs var23 = Scheme.applyToArgs;
            Location var24 = srfi13.loc$char$Mnset$Qu;

            Object var25;
            try {
               var25 = var24.get();
            } catch (UnboundLocationException var21) {
               var21.setLine("srfi13.scm", 1200, 5);
               throw var21;
            }

            ApplyToArgs var27;
            Object var26;
            if(var23.apply2(var25, this.criterion) != Boolean.FALSE) {
               var3 = var1;

               while(true) {
                  var1 = Scheme.numLss.apply2(var3, var2);

                  try {
                     var9 = ((Boolean)var1).booleanValue();
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "x", -2, var1);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var27 = Scheme.applyToArgs;
                  Location var22 = srfi13.loc$char$Mnset$Mncontains$Qu;

                  try {
                     var26 = var22.get();
                  } catch (UnboundLocationException var12) {
                     var12.setLine("srfi13.scm", 1203, 9);
                     throw var12;
                  }

                  Object var6 = this.criterion;
                  var1 = this.str;

                  CharSequence var7;
                  try {
                     var7 = (CharSequence)var1;
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "string-ref", 1, var1);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var10) {
                     throw new WrongType(var10, "string-ref", 2, var3);
                  }

                  var1 = var3;
                  if(var27.apply3(var26, var6, Char.make(strings.stringRef(var7, var8))) == Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
               }
            } else {
               if(!misc.isProcedure(this.criterion)) {
                  return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnskip, this.criterion});
               }

               var3 = var1;

               while(true) {
                  var1 = Scheme.numLss.apply2(var3, var2);

                  try {
                     var9 = ((Boolean)var1).booleanValue();
                  } catch (ClassCastException var18) {
                     throw new WrongType(var18, "x", -2, var1);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var27 = Scheme.applyToArgs;
                  var26 = this.criterion;
                  var1 = this.str;

                  CharSequence var28;
                  try {
                     var28 = (CharSequence)var1;
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "string-ref", 1, var1);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var19) {
                     throw new WrongType(var19, "string-ref", 2, var3);
                  }

                  var1 = var3;
                  if(var27.apply2(var26, Char.make(strings.stringRef(var28, var8))) == Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
               }
            }
         }

         return var1;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 156) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 157) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame8 extends ModuleBody {

      final ModuleMethod lambda$Fn21 = new ModuleMethod(this, 18, (Object)null, 0);
      final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 19, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object proc;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 18?this.lambda21():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 19?this.lambda22(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda21() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfor$Mneach$Mnindex, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda22(Object var1, Object var2) {
         while(Scheme.numLss.apply2(var1, var2) != Boolean.FALSE) {
            Scheme.applyToArgs.apply2(this.proc, var1);
            var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
         }

         return Values.empty;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 18) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 19) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame80 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn183 = new ModuleMethod(this, 158, (Object)null, 0);
      final ModuleMethod lambda$Fn184 = new ModuleMethod(this, 159, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object str;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 158?this.lambda183():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 159?this.lambda184(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda183() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnskip$Mnright, this.str, this.maybe$Mnstart$Plend);
      }

      Object lambda184(Object var1, Object var2) {
         Object var3;
         int var8;
         boolean var9;
         if(characters.isChar(this.criterion)) {
            var3 = AddOp.$Mn.apply2(var2, srfi13.Lit1);

            while(true) {
               var2 = Scheme.numGEq.apply2(var3, var1);

               try {
                  var9 = ((Boolean)var2).booleanValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "x", -2, var2);
               }

               if(!var9) {
                  if(!var9) {
                     return Boolean.FALSE;
                  }

                  var2 = Boolean.TRUE;
                  break;
               }

               var2 = this.criterion;

               Char var4;
               try {
                  var4 = (Char)var2;
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "char=?", 1, var2);
               }

               var2 = this.str;

               CharSequence var5;
               try {
                  var5 = (CharSequence)var2;
               } catch (ClassCastException var15) {
                  throw new WrongType(var15, "string-ref", 1, var2);
               }

               try {
                  var8 = ((Number)var3).intValue();
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "string-ref", 2, var3);
               }

               var2 = var3;
               if(!characters.isChar$Eq(var4, Char.make(strings.stringRef(var5, var8)))) {
                  break;
               }

               var3 = AddOp.$Mn.apply2(var3, srfi13.Lit1);
            }
         } else {
            ApplyToArgs var22 = Scheme.applyToArgs;
            Location var24 = srfi13.loc$char$Mnset$Qu;

            Object var25;
            try {
               var25 = var24.get();
            } catch (UnboundLocationException var21) {
               var21.setLine("srfi13.scm", 1222, 5);
               throw var21;
            }

            ApplyToArgs var27;
            Object var26;
            if(var22.apply2(var25, this.criterion) != Boolean.FALSE) {
               var3 = AddOp.$Mn.apply2(var2, srfi13.Lit1);

               while(true) {
                  var2 = Scheme.numGEq.apply2(var3, var1);

                  try {
                     var9 = ((Boolean)var2).booleanValue();
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "x", -2, var2);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var27 = Scheme.applyToArgs;
                  Location var23 = srfi13.loc$char$Mnset$Mncontains$Qu;

                  try {
                     var26 = var23.get();
                  } catch (UnboundLocationException var12) {
                     var12.setLine("srfi13.scm", 1225, 9);
                     throw var12;
                  }

                  Object var6 = this.criterion;
                  var2 = this.str;

                  CharSequence var7;
                  try {
                     var7 = (CharSequence)var2;
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "string-ref", 1, var2);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var10) {
                     throw new WrongType(var10, "string-ref", 2, var3);
                  }

                  var2 = var3;
                  if(var27.apply3(var26, var6, Char.make(strings.stringRef(var7, var8))) == Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Mn.apply2(var3, srfi13.Lit1);
               }
            } else {
               if(!misc.isProcedure(this.criterion)) {
                  return misc.error$V("CRITERION param is neither char-set or char.", new Object[]{srfi13.string$Mnskip$Mnright, this.criterion});
               }

               var3 = AddOp.$Mn.apply2(var2, srfi13.Lit1);

               while(true) {
                  var2 = Scheme.numGEq.apply2(var3, var1);

                  try {
                     var9 = ((Boolean)var2).booleanValue();
                  } catch (ClassCastException var18) {
                     throw new WrongType(var18, "x", -2, var2);
                  }

                  if(!var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var27 = Scheme.applyToArgs;
                  var26 = this.criterion;
                  var2 = this.str;

                  CharSequence var28;
                  try {
                     var28 = (CharSequence)var2;
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "string-ref", 1, var2);
                  }

                  try {
                     var8 = ((Number)var3).intValue();
                  } catch (ClassCastException var19) {
                     throw new WrongType(var19, "string-ref", 2, var3);
                  }

                  var2 = var3;
                  if(var27.apply2(var26, Char.make(strings.stringRef(var28, var8))) == Boolean.FALSE) {
                     break;
                  }

                  var3 = AddOp.$Mn.apply2(var3, srfi13.Lit1);
               }
            }
         }

         return var2;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 158) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 159) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame81 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn185 = new ModuleMethod(this, 160, (Object)null, 0);
      final ModuleMethod lambda$Fn186 = new ModuleMethod(this, 161, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 160?this.lambda185():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 161?this.lambda186(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda185() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncount, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda186(Object var1, Object var2) {
         Object var3;
         Object var4;
         Object var5;
         int var10;
         if(characters.isChar(this.criterion)) {
            var3 = srfi13.Lit0;
            var4 = var1;

            while(true) {
               var1 = var3;
               if(Scheme.numGEq.apply2(var4, var2) != Boolean.FALSE) {
                  break;
               }

               var5 = AddOp.$Pl.apply2(var4, srfi13.Lit1);
               var1 = this.criterion;

               Char var6;
               try {
                  var6 = (Char)var1;
               } catch (ClassCastException var19) {
                  throw new WrongType(var19, "char=?", 1, var1);
               }

               var1 = this.s;

               CharSequence var7;
               try {
                  var7 = (CharSequence)var1;
               } catch (ClassCastException var18) {
                  throw new WrongType(var18, "string-ref", 1, var1);
               }

               try {
                  var10 = ((Number)var4).intValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "string-ref", 2, var4);
               }

               var1 = var3;
               if(characters.isChar$Eq(var6, Char.make(strings.stringRef(var7, var10)))) {
                  var1 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
               }

               var4 = var5;
               var3 = var1;
            }
         } else {
            ApplyToArgs var21 = Scheme.applyToArgs;
            Location var22 = srfi13.loc$char$Mnset$Qu;

            try {
               var4 = var22.get();
            } catch (UnboundLocationException var16) {
               var16.setLine("srfi13.scm", 1246, 5);
               throw var16;
            }

            ApplyToArgs var23;
            Object var24;
            if(var21.apply2(var4, this.criterion) != Boolean.FALSE) {
               var3 = srfi13.Lit0;
               var4 = var1;

               while(true) {
                  var1 = var3;
                  if(Scheme.numGEq.apply2(var4, var2) != Boolean.FALSE) {
                     break;
                  }

                  var5 = AddOp.$Pl.apply2(var4, srfi13.Lit1);
                  var23 = Scheme.applyToArgs;
                  Location var20 = srfi13.loc$char$Mnset$Mncontains$Qu;

                  try {
                     var24 = var20.get();
                  } catch (UnboundLocationException var15) {
                     var15.setLine("srfi13.scm", 1248, 16);
                     throw var15;
                  }

                  Object var8 = this.criterion;
                  var1 = this.s;

                  CharSequence var9;
                  try {
                     var9 = (CharSequence)var1;
                  } catch (ClassCastException var14) {
                     throw new WrongType(var14, "string-ref", 1, var1);
                  }

                  try {
                     var10 = ((Number)var4).intValue();
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "string-ref", 2, var4);
                  }

                  var1 = var3;
                  if(var23.apply3(var24, var8, Char.make(strings.stringRef(var9, var10))) != Boolean.FALSE) {
                     var1 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
                  }

                  var4 = var5;
                  var3 = var1;
               }
            } else if(misc.isProcedure(this.criterion)) {
               var3 = srfi13.Lit0;
               var4 = var1;

               while(true) {
                  var1 = var3;
                  if(Scheme.numGEq.apply2(var4, var2) != Boolean.FALSE) {
                     break;
                  }

                  var5 = AddOp.$Pl.apply2(var4, srfi13.Lit1);
                  var23 = Scheme.applyToArgs;
                  var24 = this.criterion;
                  var1 = this.s;

                  CharSequence var25;
                  try {
                     var25 = (CharSequence)var1;
                  } catch (ClassCastException var12) {
                     throw new WrongType(var12, "string-ref", 1, var1);
                  }

                  try {
                     var10 = ((Number)var4).intValue();
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "string-ref", 2, var4);
                  }

                  var1 = var3;
                  if(var23.apply2(var24, Char.make(strings.stringRef(var25, var10))) != Boolean.FALSE) {
                     var1 = AddOp.$Pl.apply2(var3, srfi13.Lit1);
                  }

                  var4 = var5;
                  var3 = var1;
               }
            } else {
               var1 = misc.error$V("CRITERION param is neither char-set or char.", new Object[]{srfi13.string$Mncount, this.criterion});
            }
         }

         return var1;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 160) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 161) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame82 extends ModuleBody {

      Object char;
      final ModuleMethod lambda$Fn187 = new ModuleMethod(this, 162, (Object)null, 0);
      final ModuleMethod lambda$Fn188 = new ModuleMethod(this, 163, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 162?this.lambda187():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 163?this.lambda188(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda187() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfill$Ex, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda188(Object var1, Object var2) {
         for(var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1); Scheme.numLss.apply2(var2, var1) == Boolean.FALSE; var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1)) {
            Object var4 = this.s;

            CharSeq var5;
            try {
               var5 = (CharSeq)var4;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-set!", 1, var4);
            }

            int var6;
            try {
               var6 = ((Number)var2).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-set!", 2, var2);
            }

            var4 = this.char;

            char var3;
            try {
               var3 = ((Char)var4).charValue();
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-set!", 3, var4);
            }

            strings.stringSet$Ex(var5, var6, var3);
         }

         return Values.empty;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 162) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 163) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame83 extends ModuleBody {

      final ModuleMethod lambda$Fn189 = new ModuleMethod(this, 166, (Object)null, 0);
      final ModuleMethod lambda$Fn190 = new ModuleMethod(this, 167, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object pattern;
      Object text;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 166?this.lambda189():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 167?this.lambda190(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda189() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mncontains, this.text, this.maybe$Mnstarts$Plends);
      }

      Object lambda190(Object var1, Object var2, Object var3) {
         srfi13.frame84 var4 = new srfi13.frame84();
         var4.staticLink = this;
         var4.rest = var1;
         var4.t$Mnstart = var2;
         var4.t$Mnend = var3;
         return call_with_values.callWithValues(var4.lambda$Fn191, var4.lambda$Fn192);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 166) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 167) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame84 extends ModuleBody {

      final ModuleMethod lambda$Fn191 = new ModuleMethod(this, 164, (Object)null, 0);
      final ModuleMethod lambda$Fn192 = new ModuleMethod(this, 165, (Object)null, 8194);
      Object rest;
      srfi13.frame83 staticLink;
      Object t$Mnend;
      Object t$Mnstart;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 164?this.lambda191():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 165?this.lambda192(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda191() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncontains, this.staticLink.pattern, this.rest);
      }

      Object lambda192(Object var1, Object var2) {
         return srfi13.$PcKmpSearch(this.staticLink.pattern, this.staticLink.text, characters.char$Eq$Qu, var1, var2, this.t$Mnstart, this.t$Mnend);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 164) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 165) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame85 extends ModuleBody {

      final ModuleMethod lambda$Fn193 = new ModuleMethod(this, 170, (Object)null, 0);
      final ModuleMethod lambda$Fn194 = new ModuleMethod(this, 171, (Object)null, 12291);
      LList maybe$Mnstarts$Plends;
      Object pattern;
      Object text;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 170?this.lambda193():super.apply0(var1);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 171?this.lambda194(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda193() {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mncontains$Mnci, this.text, this.maybe$Mnstarts$Plends);
      }

      Object lambda194(Object var1, Object var2, Object var3) {
         srfi13.frame86 var4 = new srfi13.frame86();
         var4.staticLink = this;
         var4.rest = var1;
         var4.t$Mnstart = var2;
         var4.t$Mnend = var3;
         return call_with_values.callWithValues(var4.lambda$Fn195, var4.lambda$Fn196);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 170) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 171) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame86 extends ModuleBody {

      final ModuleMethod lambda$Fn195 = new ModuleMethod(this, 168, (Object)null, 0);
      final ModuleMethod lambda$Fn196 = new ModuleMethod(this, 169, (Object)null, 8194);
      Object rest;
      srfi13.frame85 staticLink;
      Object t$Mnend;
      Object t$Mnstart;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 168?this.lambda195():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 169?this.lambda196(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda195() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncontains$Mnci, this.staticLink.pattern, this.rest);
      }

      Object lambda196(Object var1, Object var2) {
         return srfi13.$PcKmpSearch(this.staticLink.pattern, this.staticLink.text, unicode.char$Mnci$Eq$Qu, var1, var2, this.t$Mnstart, this.t$Mnend);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 168) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 169) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame87 extends ModuleBody {

      final ModuleMethod lambda$Fn197;
      Object pattern;


      public frame87() {
         ModuleMethod var1 = new ModuleMethod(this, 172, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:1399");
         this.lambda$Fn197 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 172?this.lambda197(var2):super.apply1(var1, var2);
      }

      Object lambda197(Object var1) {
         return srfi13.stringParseStart$PlEnd(srfi13.make$Mnkmp$Mnrestart$Mnvector, this.pattern, var1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 172) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame88 extends ModuleBody {

      final ModuleMethod lambda$Fn198;
      final ModuleMethod lambda$Fn199;
      int patlen;
      Object s;


      public frame88() {
         ModuleMethod var1 = new ModuleMethod(this, 173, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:1468");
         this.lambda$Fn198 = var1;
         var1 = new ModuleMethod(this, 174, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:1472");
         this.lambda$Fn199 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         switch(var1.selector) {
         case 173:
            return this.lambda198(var2);
         case 174:
            if(this.lambda199(var2)) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         default:
            return super.apply1(var1, var2);
         }
      }

      Object lambda198(Object var1) {
         return srfi13.stringParseStart$PlEnd(srfi13.string$Mnkmp$Mnpartial$Mnsearch, this.s, var1);
      }

      boolean lambda199(Object var1) {
         boolean var4 = numbers.isInteger(var1);
         boolean var3 = var4;
         if(var4) {
            var4 = numbers.isExact(var1);
            var3 = var4;
            if(var4) {
               Object var2 = Scheme.numLEq.apply2(srfi13.Lit0, var1);

               try {
                  var4 = ((Boolean)var2).booleanValue();
               } catch (ClassCastException var5) {
                  throw new WrongType(var5, "x", -2, var2);
               }

               var3 = var4;
               if(var4) {
                  var3 = ((Boolean)Scheme.numLss.apply2(var1, Integer.valueOf(this.patlen))).booleanValue();
               }
            }
         }

         return var3;
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         switch(var1.selector) {
         case 173:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 174:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         default:
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame89 extends ModuleBody {

      final ModuleMethod lambda$Fn200 = new ModuleMethod(this, 175, (Object)null, 0);
      final ModuleMethod lambda$Fn201 = new ModuleMethod(this, 176, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 175?this.lambda200():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 176?this.lambda201(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda200() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreverse, this.s, this.maybe$Mnstart$Plend);
      }

      CharSequence lambda201(Object var1, Object var2) {
         var2 = AddOp.$Mn.apply2(var2, var1);

         int var7;
         try {
            var7 = ((Number)var2).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "make-string", 1, var2);
         }

         CharSequence var4 = strings.makeString(var7);
         Object var3 = AddOp.$Mn.apply2(var2, srfi13.Lit1);
         var2 = var1;

         for(var1 = var3; Scheme.numLss.apply2(var1, srfi13.Lit0) == Boolean.FALSE; var1 = AddOp.$Mn.apply2(var1, srfi13.Lit1)) {
            CharSeq var5;
            try {
               var5 = (CharSeq)var4;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-set!", 1, var4);
            }

            try {
               var7 = ((Number)var1).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-set!", 2, var1);
            }

            var3 = this.s;

            CharSequence var6;
            try {
               var6 = (CharSequence)var3;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 1, var3);
            }

            int var8;
            try {
               var8 = ((Number)var2).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 2, var2);
            }

            strings.stringSet$Ex(var5, var7, strings.stringRef(var6, var8));
            var2 = AddOp.$Pl.apply2(var2, srfi13.Lit1);
         }

         return var4;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 175) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 176) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame9 extends ModuleBody {

      Object criterion;
      final ModuleMethod lambda$Fn23 = new ModuleMethod(this, 20, (Object)null, 0);
      final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 21, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 20?this.lambda23():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 21?this.lambda24(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda23() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnevery, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda24(Object var1, Object var2) {
         int var8;
         boolean var9;
         Object var23;
         Object var22;
         if(characters.isChar(this.criterion)) {
            while(true) {
               var22 = Scheme.numGEq.apply2(var1, var2);

               try {
                  var9 = ((Boolean)var22).booleanValue();
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "x", -2, var22);
               }

               if(var9) {
                  if(var9) {
                     return Boolean.TRUE;
                  }

                  return Boolean.FALSE;
               }

               var23 = this.criterion;

               Char var26;
               try {
                  var26 = (Char)var23;
               } catch (ClassCastException var13) {
                  throw new WrongType(var13, "char=?", 1, var23);
               }

               var23 = this.s;

               CharSequence var25;
               try {
                  var25 = (CharSequence)var23;
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "string-ref", 1, var23);
               }

               try {
                  var8 = ((Number)var1).intValue();
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "string-ref", 2, var1);
               }

               var9 = characters.isChar$Eq(var26, Char.make(strings.stringRef(var25, var8)));
               if(!var9) {
                  if(var9) {
                     return Boolean.TRUE;
                  }

                  return Boolean.FALSE;
               }

               var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
            }
         } else {
            ApplyToArgs var3 = Scheme.applyToArgs;
            Location var4 = srfi13.loc$char$Mnset$Qu;

            try {
               var23 = var4.get();
            } catch (UnboundLocationException var21) {
               var21.setLine("srfi13.scm", 489, 5);
               throw var21;
            }

            if(var3.apply2(var23, this.criterion) != Boolean.FALSE) {
               while(true) {
                  var22 = Scheme.numGEq.apply2(var1, var2);

                  try {
                     var9 = ((Boolean)var22).booleanValue();
                  } catch (ClassCastException var14) {
                     throw new WrongType(var14, "x", -2, var22);
                  }

                  if(var9) {
                     if(var9) {
                        return Boolean.TRUE;
                     }

                     return Boolean.FALSE;
                  }

                  var3 = Scheme.applyToArgs;
                  var4 = srfi13.loc$char$Mnset$Mncontains$Qu;

                  Object var5;
                  try {
                     var5 = var4.get();
                  } catch (UnboundLocationException var17) {
                     var17.setLine("srfi13.scm", 492, 9);
                     throw var17;
                  }

                  Object var6 = this.criterion;
                  var23 = this.s;

                  CharSequence var7;
                  try {
                     var7 = (CharSequence)var23;
                  } catch (ClassCastException var16) {
                     throw new WrongType(var16, "string-ref", 1, var23);
                  }

                  try {
                     var8 = ((Number)var1).intValue();
                  } catch (ClassCastException var15) {
                     throw new WrongType(var15, "string-ref", 2, var1);
                  }

                  var22 = var3.apply3(var5, var6, Char.make(strings.stringRef(var7, var8)));
                  if(var22 == Boolean.FALSE) {
                     return var22;
                  }

                  var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
               }
            } else if(misc.isProcedure(this.criterion)) {
               var22 = Scheme.numEqu.apply2(var1, var2);

               try {
                  var9 = ((Boolean)var22).booleanValue();
               } catch (ClassCastException var20) {
                  throw new WrongType(var20, "x", -2, var22);
               }

               if(var9) {
                  return var9?Boolean.TRUE:Boolean.FALSE;
               } else {
                  do {
                     var22 = this.s;

                     CharSequence var24;
                     try {
                        var24 = (CharSequence)var22;
                     } catch (ClassCastException var19) {
                        throw new WrongType(var19, "string-ref", 1, var22);
                     }

                     try {
                        var8 = ((Number)var1).intValue();
                     } catch (ClassCastException var18) {
                        throw new WrongType(var18, "string-ref", 2, var1);
                     }

                     char var27 = strings.stringRef(var24, var8);
                     var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1);
                     if(Scheme.numEqu.apply2(var1, var2) != Boolean.FALSE) {
                        return Scheme.applyToArgs.apply2(this.criterion, Char.make(var27));
                     }

                     var22 = Scheme.applyToArgs.apply2(this.criterion, Char.make(var27));
                  } while(var22 != Boolean.FALSE);

                  return var22;
               }
            } else {
               return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnevery, this.criterion});
            }
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 20) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 21) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame90 extends ModuleBody {

      final ModuleMethod lambda$Fn202 = new ModuleMethod(this, 177, (Object)null, 0);
      final ModuleMethod lambda$Fn203 = new ModuleMethod(this, 178, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 177?this.lambda202():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 178?this.lambda203(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda202() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreverse$Ex, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda203(Object var1, Object var2) {
         for(var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1); Scheme.numLEq.apply2(var2, var1) == Boolean.FALSE; var1 = AddOp.$Pl.apply2(var1, srfi13.Lit1)) {
            Object var4 = this.s;

            CharSequence var5;
            try {
               var5 = (CharSequence)var4;
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "string-ref", 1, var4);
            }

            int var7;
            try {
               var7 = ((Number)var2).intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "string-ref", 2, var2);
            }

            char var3 = strings.stringRef(var5, var7);
            Object var18 = this.s;

            CharSeq var17;
            try {
               var17 = (CharSeq)var18;
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "string-set!", 1, var18);
            }

            try {
               var7 = ((Number)var2).intValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-set!", 2, var2);
            }

            var18 = this.s;

            CharSequence var6;
            try {
               var6 = (CharSequence)var18;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-ref", 1, var18);
            }

            int var8;
            try {
               var8 = ((Number)var1).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 2, var1);
            }

            strings.stringSet$Ex(var17, var7, strings.stringRef(var6, var8));
            var4 = this.s;

            CharSeq var19;
            try {
               var19 = (CharSeq)var4;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-set!", 1, var4);
            }

            try {
               var7 = ((Number)var1).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-set!", 2, var1);
            }

            strings.stringSet$Ex(var19, var7, var3);
            var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1);
         }

         return Values.empty;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 177) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 178) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame91 extends ModuleBody {

      final ModuleMethod lambda$Fn204 = new ModuleMethod(this, 179, (Object)null, 0);
      final ModuleMethod lambda$Fn205 = new ModuleMethod(this, 180, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 179?this.lambda204():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 180?this.lambda205(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda204() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mn$Grlist, this.s, this.maybe$Mnstart$Plend);
      }

      Object lambda205(Object var1, Object var2) {
         var2 = AddOp.$Mn.apply2(var2, srfi13.Lit1);

         Object var3;
         Object var4;
         for(var3 = LList.Empty; Scheme.numLss.apply2(var2, var1) == Boolean.FALSE; var2 = var4) {
            var4 = AddOp.$Mn.apply2(var2, srfi13.Lit1);
            Object var5 = this.s;

            CharSequence var6;
            try {
               var6 = (CharSequence)var5;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var5);
            }

            int var7;
            try {
               var7 = ((Number)var2).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-ref", 2, var2);
            }

            var3 = lists.cons(Char.make(strings.stringRef(var6, var7)), var3);
         }

         return var3;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 179) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 180) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame92 extends ModuleBody {

      Object end1;
      final ModuleMethod lambda$Fn206 = new ModuleMethod(this, 181, (Object)null, 0);
      final ModuleMethod lambda$Fn207 = new ModuleMethod(this, 182, (Object)null, 8194);
      LList maybe$Mnstart$Plend;
      Object s1;
      Object s2;
      Object start1;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 181?this.lambda206():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 182?this.lambda207(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda206() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreplace, this.s2, this.maybe$Mnstart$Plend);
      }

      CharSequence lambda207(Object var1, Object var2) {
         Object var3 = this.s1;

         CharSequence var4;
         try {
            var4 = (CharSequence)var3;
         } catch (ClassCastException var21) {
            throw new WrongType(var21, "string-length", 1, var3);
         }

         int var7 = strings.stringLength(var4);
         Object var24 = AddOp.$Mn.apply2(var2, var1);
         var3 = AddOp.$Pl.apply2(AddOp.$Mn.apply2(Integer.valueOf(var7), AddOp.$Mn.apply2(this.end1, this.start1)), var24);

         int var8;
         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var20) {
            throw new WrongType(var20, "make-string", 1, var3);
         }

         CharSequence var23 = strings.makeString(var8);
         Object var5 = this.s1;

         CharSequence var6;
         try {
            var6 = (CharSequence)var5;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "%string-copy!", 2, var5);
         }

         var5 = this.start1;

         try {
            var8 = ((Number)var5).intValue();
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "%string-copy!", 4, var5);
         }

         srfi13.$PcStringCopy$Ex(var23, 0, var6, 0, var8);
         var5 = this.start1;

         try {
            var8 = ((Number)var5).intValue();
         } catch (ClassCastException var17) {
            throw new WrongType(var17, "%string-copy!", 1, var5);
         }

         var5 = this.s2;

         try {
            var6 = (CharSequence)var5;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "%string-copy!", 2, var5);
         }

         int var9;
         try {
            var9 = ((Number)var1).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "%string-copy!", 3, var1);
         }

         int var10;
         try {
            var10 = ((Number)var2).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "%string-copy!", 4, var2);
         }

         srfi13.$PcStringCopy$Ex(var23, var8, var6, var9, var10);
         var1 = AddOp.$Pl.apply2(this.start1, var24);

         try {
            var8 = ((Number)var1).intValue();
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "%string-copy!", 1, var1);
         }

         var1 = this.s1;

         CharSequence var22;
         try {
            var22 = (CharSequence)var1;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "%string-copy!", 2, var1);
         }

         var1 = this.end1;

         try {
            var9 = ((Number)var1).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "%string-copy!", 3, var1);
         }

         srfi13.$PcStringCopy$Ex(var23, var8, var22, var9, var7);
         return var23;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 181) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 182) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame93 extends ModuleBody {

      final ModuleMethod lambda$Fn208 = new ModuleMethod(this, 183, (Object)null, 0);
      final ModuleMethod lambda$Fn209 = new ModuleMethod(this, 184, (Object)null, 8194);
      Object s;


      public Object apply0(ModuleMethod var1) {
         return var1.selector == 183?this.lambda208():super.apply0(var1);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 184?this.lambda209(var2, var3):super.apply2(var1, var2, var3);
      }

      Object lambda208() {
         ModuleMethod var1 = srfi13.string$Mntokenize;
         Object var2 = this.s;
         Location var3 = srfi13.loc$rest;

         Object var5;
         try {
            var5 = var3.get();
         } catch (UnboundLocationException var4) {
            var4.setLine("srfi13.scm", 1696, 57);
            throw var4;
         }

         return srfi13.stringParseFinalStart$PlEnd(var1, var2, var5);
      }

      Object lambda209(Object var1, Object var2) {
         LList var4 = LList.Empty;
         Object var3 = var2;
         var2 = var4;

         Object var19;
         while(true) {
            var19 = Scheme.numLss.apply2(var1, var3);

            boolean var9;
            try {
               var9 = ((Boolean)var19).booleanValue();
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "x", -2, var19);
            }

            Object var20;
            if(var9) {
               var19 = this.s;
               Location var5 = srfi13.loc$token$Mnchars;

               try {
                  var20 = var5.get();
               } catch (UnboundLocationException var13) {
                  var13.setLine("srfi13.scm", 1698, 48);
                  throw var13;
               }

               var3 = srfi13.stringIndexRight$V(var19, var20, new Object[]{var1, var3});
            } else if(var9) {
               var3 = Boolean.TRUE;
            } else {
               var3 = Boolean.FALSE;
            }

            var19 = var2;
            if(var3 == Boolean.FALSE) {
               break;
            }

            var19 = AddOp.$Pl.apply2(srfi13.Lit1, var3);
            var20 = this.s;
            Location var6 = srfi13.loc$token$Mnchars;

            Object var23;
            try {
               var23 = var6.get();
            } catch (UnboundLocationException var18) {
               var18.setLine("srfi13.scm", 1701, 34);
               throw var18;
            }

            var3 = srfi13.stringSkipRight$V(var20, var23, new Object[]{var1, var3});
            int var7;
            int var8;
            if(var3 == Boolean.FALSE) {
               var3 = this.s;

               CharSequence var22;
               try {
                  var22 = (CharSequence)var3;
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "substring", 1, var3);
               }

               try {
                  var7 = ((Number)var1).intValue();
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "substring", 2, var1);
               }

               try {
                  var8 = ((Number)var19).intValue();
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "substring", 3, var19);
               }

               var19 = lists.cons(strings.substring(var22, var7, var8), var2);
               break;
            }

            var20 = this.s;

            CharSequence var21;
            try {
               var21 = (CharSequence)var20;
            } catch (ClassCastException var17) {
               throw new WrongType(var17, "substring", 1, var20);
            }

            var20 = AddOp.$Pl.apply2(srfi13.Lit1, var3);

            try {
               var7 = ((Number)var20).intValue();
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "substring", 2, var20);
            }

            try {
               var8 = ((Number)var19).intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "substring", 3, var19);
            }

            var2 = lists.cons(strings.substring(var21, var7, var8), var2);
         }

         return var19;
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 183) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 184) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }
   }

   public class frame94 extends ModuleBody {

      Object from;
      final ModuleMethod lambda$Fn211;
      final ModuleMethod lambda$Fn212 = new ModuleMethod(this, 185, (Object)null, 0);
      final ModuleMethod lambda$Fn213;
      final ModuleMethod lambda$Fn214;
      final ModuleMethod lambda$Fn215;
      LList maybe$Mnto$Plstart$Plend;
      Object s;


      public frame94() {
         ModuleMethod var1 = new ModuleMethod(this, 186, (Object)null, 4097);
         var1.setProperty("source-location", "srfi13.scm:1744");
         this.lambda$Fn214 = var1;
         this.lambda$Fn213 = new ModuleMethod(this, 187, (Object)null, 8194);
         this.lambda$Fn211 = new ModuleMethod(this, 188, (Object)null, 0);
         var1 = new ModuleMethod(this, 189, (Object)null, 12291);
         var1.setProperty("source-location", "srfi13.scm:1740");
         this.lambda$Fn215 = var1;
      }

      static boolean lambda210(Object var0) {
         boolean var2 = numbers.isInteger(var0);
         boolean var1 = var2;
         if(var2) {
            var1 = numbers.isExact(var0);
         }

         return var1;
      }

      public Object apply0(ModuleMethod var1) {
         switch(var1.selector) {
         case 185:
            return this.lambda212();
         case 186:
         case 187:
         default:
            return super.apply0(var1);
         case 188:
            return this.lambda211();
         }
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 186?(this.lambda214(var2)?Boolean.TRUE:Boolean.FALSE):super.apply1(var1, var2);
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 187?this.lambda213(var2, var3):super.apply2(var1, var2, var3);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 189?this.lambda215(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda211() {
         if(lists.isPair(this.maybe$Mnto$Plstart$Plend)) {
            return call_with_values.callWithValues(this.lambda$Fn212, this.lambda$Fn213);
         } else {
            ApplyToArgs var1 = Scheme.applyToArgs;
            Location var2 = srfi13.loc$check$Mnarg;

            Object var7;
            try {
               var7 = var2.get();
            } catch (UnboundLocationException var5) {
               var5.setLine("srfi13.scm", 1749, 36);
               throw var5;
            }

            Object var6 = var1.apply4(var7, strings.string$Qu, this.s, srfi13.xsubstring);

            CharSequence var8;
            try {
               var8 = (CharSequence)var6;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "string-length", 1, var6);
            }

            int var3 = strings.stringLength(var8);
            return misc.values(new Object[]{AddOp.$Pl.apply2(this.from, Integer.valueOf(var3)), srfi13.Lit0, Integer.valueOf(var3)});
         }
      }

      Object lambda212() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.xsubstring, this.s, lists.cdr.apply1(this.maybe$Mnto$Plstart$Plend));
      }

      Object lambda213(Object var1, Object var2) {
         Object var3 = lists.car.apply1(this.maybe$Mnto$Plstart$Plend);
         ApplyToArgs var4 = Scheme.applyToArgs;
         Location var5 = srfi13.loc$check$Mnarg;

         Object var7;
         try {
            var7 = var5.get();
         } catch (UnboundLocationException var6) {
            var6.setLine("srfi13.scm", 1744, 6);
            throw var6;
         }

         var4.apply4(var7, this.lambda$Fn214, var3, srfi13.xsubstring);
         return misc.values(new Object[]{var3, var1, var2});
      }

      boolean lambda214(Object var1) {
         boolean var3 = numbers.isInteger(var1);
         boolean var2 = var3;
         if(var3) {
            var3 = numbers.isExact(var1);
            var2 = var3;
            if(var3) {
               var2 = ((Boolean)Scheme.numLEq.apply2(this.from, var1)).booleanValue();
            }
         }

         return var2;
      }

      Object lambda215(Object var1, Object var2, Object var3) {
         Object var6 = AddOp.$Mn.apply2(var3, var2);
         Object var7 = AddOp.$Mn.apply2(var1, this.from);

         Number var8;
         try {
            var8 = (Number)var7;
         } catch (ClassCastException var22) {
            throw new WrongType(var22, "zero?", 1, var7);
         }

         if(numbers.isZero(var8)) {
            return "";
         } else {
            try {
               var8 = (Number)var6;
            } catch (ClassCastException var21) {
               throw new WrongType(var21, "zero?", 1, var6);
            }

            if(numbers.isZero(var8)) {
               return misc.error$V("Cannot replicate empty (sub)string", new Object[]{srfi13.xsubstring, this.s, this.from, var1, var2, var3});
            } else {
               int var10;
               int var11;
               CharSequence var23;
               if(Scheme.numEqu.apply2(srfi13.Lit1, var6) != Boolean.FALSE) {
                  try {
                     var10 = ((Number)var7).intValue();
                  } catch (ClassCastException var14) {
                     throw new WrongType(var14, "make-string", 1, var7);
                  }

                  var1 = this.s;

                  try {
                     var23 = (CharSequence)var1;
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "string-ref", 1, var1);
                  }

                  try {
                     var11 = ((Number)var2).intValue();
                  } catch (ClassCastException var12) {
                     throw new WrongType(var12, "string-ref", 2, var2);
                  }

                  return strings.makeString(var10, Char.make(strings.stringRef(var23, var11)));
               } else {
                  Object var25 = DivideOp.$Sl.apply2(this.from, var6);

                  RealNum var9;
                  try {
                     var9 = LangObjType.coerceRealNum(var25);
                  } catch (ClassCastException var20) {
                     throw new WrongType(var20, "floor", 1, var25);
                  }

                  double var4 = numbers.floor(var9).doubleValue();
                  var25 = DivideOp.$Sl.apply2(var1, var6);

                  try {
                     var9 = LangObjType.coerceRealNum(var25);
                  } catch (ClassCastException var19) {
                     throw new WrongType(var19, "floor", 1, var25);
                  }

                  if(var4 == numbers.floor(var9).doubleValue()) {
                     var7 = this.s;

                     try {
                        var23 = (CharSequence)var7;
                     } catch (ClassCastException var17) {
                        throw new WrongType(var17, "substring", 1, var7);
                     }

                     var7 = AddOp.$Pl.apply2(var2, DivideOp.modulo.apply2(this.from, var6));

                     try {
                        var10 = ((Number)var7).intValue();
                     } catch (ClassCastException var16) {
                        throw new WrongType(var16, "substring", 2, var7);
                     }

                     var1 = AddOp.$Pl.apply2(var2, DivideOp.modulo.apply2(var1, var6));

                     try {
                        var11 = ((Number)var1).intValue();
                     } catch (ClassCastException var15) {
                        throw new WrongType(var15, "substring", 3, var1);
                     }

                     return strings.substring(var23, var10, var11);
                  } else {
                     try {
                        var10 = ((Number)var7).intValue();
                     } catch (ClassCastException var18) {
                        throw new WrongType(var18, "make-string", 1, var7);
                     }

                     CharSequence var24 = strings.makeString(var10);
                     srfi13.$PcMultispanRepcopy$Ex(var24, srfi13.Lit0, this.s, this.from, var1, var2, var3);
                     return var24;
                  }
               }
            }
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         switch(var1.selector) {
         case 185:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 186:
         case 187:
         default:
            return super.match0(var1, var2);
         case 188:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 186) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 187) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 189) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame95 extends ModuleBody {

      final ModuleMethod lambda$Fn217 = new ModuleMethod(this, 192, (Object)null, 0);
      final ModuleMethod lambda$Fn218 = new ModuleMethod(this, 190, (Object)null, 0);
      final ModuleMethod lambda$Fn219 = new ModuleMethod(this, 191, (Object)null, 8194);
      final ModuleMethod lambda$Fn221;
      LList maybe$Mnsto$Plstart$Plend;
      Object s;
      Object sfrom;
      Object target;
      Object tstart;


      public frame95() {
         ModuleMethod var1 = new ModuleMethod(this, 193, (Object)null, 12291);
         var1.setProperty("source-location", "srfi13.scm:1781");
         this.lambda$Fn221 = var1;
      }

      static boolean lambda216(Object var0) {
         boolean var2 = numbers.isInteger(var0);
         boolean var1 = var2;
         if(var2) {
            var1 = numbers.isExact(var0);
         }

         return var1;
      }

      static boolean lambda220(Object var0) {
         boolean var2 = numbers.isInteger(var0);
         boolean var1 = var2;
         if(var2) {
            var1 = numbers.isExact(var0);
         }

         return var1;
      }

      public Object apply0(ModuleMethod var1) {
         switch(var1.selector) {
         case 190:
            return this.lambda218();
         case 191:
         default:
            return super.apply0(var1);
         case 192:
            return this.lambda217();
         }
      }

      public Object apply2(ModuleMethod var1, Object var2, Object var3) {
         return var1.selector == 191?this.lambda219(var2, var3):super.apply2(var1, var2, var3);
      }

      public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
         return var1.selector == 193?this.lambda221(var2, var3, var4):super.apply3(var1, var2, var3, var4);
      }

      Object lambda217() {
         if(lists.isPair(this.maybe$Mnsto$Plstart$Plend)) {
            return call_with_values.callWithValues(this.lambda$Fn218, this.lambda$Fn219);
         } else {
            Object var1 = this.s;

            CharSequence var2;
            try {
               var2 = (CharSequence)var1;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "string-length", 1, var1);
            }

            int var3 = strings.stringLength(var2);
            return misc.values(new Object[]{AddOp.$Pl.apply2(this.sfrom, Integer.valueOf(var3)), srfi13.Lit0, Integer.valueOf(var3)});
         }
      }

      Object lambda218() {
         return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnxcopy$Ex, this.s, lists.cdr.apply1(this.maybe$Mnsto$Plstart$Plend));
      }

      Object lambda219(Object var1, Object var2) {
         Object var3 = lists.car.apply1(this.maybe$Mnsto$Plstart$Plend);
         ApplyToArgs var4 = Scheme.applyToArgs;
         Location var5 = srfi13.loc$check$Mnarg;

         Object var7;
         try {
            var7 = var5.get();
         } catch (UnboundLocationException var6) {
            var6.setLine("srfi13.scm", 1785, 6);
            throw var6;
         }

         var4.apply4(var7, srfi13.lambda$Fn220, var3, srfi13.string$Mnxcopy$Ex);
         return misc.values(new Object[]{var3, var1, var2});
      }

      Object lambda221(Object var1, Object var2, Object var3) {
         Object var8 = AddOp.$Mn.apply2(var1, this.sfrom);
         Object var7 = AddOp.$Pl.apply2(this.tstart, var8);
         Object var6 = AddOp.$Mn.apply2(var3, var2);
         srfi13.checkSubstringSpec(srfi13.string$Mnxcopy$Ex, this.target, this.tstart, var7);

         Number var9;
         try {
            var9 = (Number)var8;
         } catch (ClassCastException var24) {
            throw new WrongType(var24, "zero?", 1, var8);
         }

         boolean var13 = numbers.isZero(var9);
         if(var13) {
            return var13?Boolean.TRUE:Boolean.FALSE;
         } else {
            Number var29;
            try {
               var29 = (Number)var6;
            } catch (ClassCastException var23) {
               throw new WrongType(var23, "zero?", 1, var6);
            }

            if(numbers.isZero(var29)) {
               return misc.error$V("Cannot replicate empty (sub)string", new Object[]{srfi13.string$Mnxcopy$Ex, this.target, this.tstart, this.s, this.sfrom, var1, var2, var3});
            } else {
               int var10;
               if(Scheme.numEqu.apply2(srfi13.Lit1, var6) != Boolean.FALSE) {
                  var3 = this.target;
                  var1 = this.s;

                  CharSequence var26;
                  try {
                     var26 = (CharSequence)var1;
                  } catch (ClassCastException var15) {
                     throw new WrongType(var15, "string-ref", 1, var1);
                  }

                  try {
                     var10 = ((Number)var2).intValue();
                  } catch (ClassCastException var14) {
                     throw new WrongType(var14, "string-ref", 2, var2);
                  }

                  return srfi13.stringFill$Ex$V(var3, Char.make(strings.stringRef(var26, var10)), new Object[]{this.tstart, var7});
               } else {
                  var7 = DivideOp.$Sl.apply2(this.sfrom, var6);

                  RealNum var27;
                  try {
                     var27 = LangObjType.coerceRealNum(var7);
                  } catch (ClassCastException var22) {
                     throw new WrongType(var22, "floor", 1, var7);
                  }

                  double var4 = numbers.floor(var27).doubleValue();
                  var7 = DivideOp.$Sl.apply2(var1, var6);

                  try {
                     var27 = LangObjType.coerceRealNum(var7);
                  } catch (ClassCastException var21) {
                     throw new WrongType(var21, "floor", 1, var7);
                  }

                  if(var4 == numbers.floor(var27).doubleValue()) {
                     var7 = this.target;

                     CharSequence var25;
                     try {
                        var25 = (CharSequence)var7;
                     } catch (ClassCastException var20) {
                        throw new WrongType(var20, "%string-copy!", 0, var7);
                     }

                     var7 = this.tstart;

                     try {
                        var10 = ((Number)var7).intValue();
                     } catch (ClassCastException var19) {
                        throw new WrongType(var19, "%string-copy!", 1, var7);
                     }

                     var8 = this.s;

                     CharSequence var28;
                     try {
                        var28 = (CharSequence)var8;
                     } catch (ClassCastException var18) {
                        throw new WrongType(var18, "%string-copy!", 2, var8);
                     }

                     var8 = AddOp.$Pl.apply2(var2, DivideOp.modulo.apply2(this.sfrom, var6));

                     int var11;
                     try {
                        var11 = ((Number)var8).intValue();
                     } catch (ClassCastException var17) {
                        throw new WrongType(var17, "%string-copy!", 3, var8);
                     }

                     var1 = AddOp.$Pl.apply2(var2, DivideOp.modulo.apply2(var1, var6));

                     int var12;
                     try {
                        var12 = ((Number)var1).intValue();
                     } catch (ClassCastException var16) {
                        throw new WrongType(var16, "%string-copy!", 4, var1);
                     }

                     return srfi13.$PcStringCopy$Ex(var25, var10, var28, var11, var12);
                  } else {
                     return srfi13.$PcMultispanRepcopy$Ex(this.target, this.tstart, this.s, this.sfrom, var1, var2, var3);
                  }
               }
            }
         }
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         switch(var1.selector) {
         case 190:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 191:
         default:
            return super.match0(var1, var2);
         case 192:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         }
      }

      public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
         if(var1.selector == 191) {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         } else {
            return super.match2(var1, var2, var3, var4);
         }
      }

      public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
         if(var1.selector == 193) {
            var5.value1 = var2;
            var5.value2 = var3;
            var5.value3 = var4;
            var5.proc = var1;
            var5.pc = 3;
            return 0;
         } else {
            return super.match3(var1, var2, var3, var4, var5);
         }
      }
   }

   public class frame96 extends ModuleBody {

      Object final;


      public Object lambda223recur(Object var1) {
         if(lists.isPair(var1)) {
            Location var2 = srfi13.loc$delim;

            Object var4;
            try {
               var4 = var2.get();
            } catch (UnboundLocationException var3) {
               var3.setLine("srfi13.scm", 1857, 13);
               throw var3;
            }

            return lists.cons(var4, lists.cons(lists.car.apply1(var1), this.lambda223recur(lists.cdr.apply1(var1))));
         } else {
            return this.final;
         }
      }
   }
}
