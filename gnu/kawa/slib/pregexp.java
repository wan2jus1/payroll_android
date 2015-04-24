package gnu.kawa.slib;

import gnu.expr.GenericProc;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.strings;
import kawa.lib.rnrs.unicode;
import kawa.standard.Scheme;
import kawa.standard.append;

public class pregexp extends ModuleBody {

   public static Char $Stpregexp$Mncomment$Mnchar$St;
   public static Object $Stpregexp$Mnnul$Mnchar$Mnint$St;
   public static Object $Stpregexp$Mnreturn$Mnchar$St;
   public static Object $Stpregexp$Mnspace$Mnsensitive$Qu$St;
   public static Object $Stpregexp$Mntab$Mnchar$St;
   public static IntNum $Stpregexp$Mnversion$St;
   public static final pregexp $instance;
   static final IntNum Lit0;
   static final Char Lit1;
   static final SimpleSymbol Lit10;
   static final SimpleSymbol Lit100;
   static final SimpleSymbol Lit101;
   static final SimpleSymbol Lit102;
   static final SimpleSymbol Lit103;
   static final SimpleSymbol Lit104;
   static final SimpleSymbol Lit105;
   static final PairWithPosition Lit106;
   static final SimpleSymbol Lit107;
   static final PairWithPosition Lit108;
   static final SimpleSymbol Lit109;
   static final Char Lit11;
   static final SimpleSymbol Lit110;
   static final SimpleSymbol Lit111;
   static final SimpleSymbol Lit112;
   static final Char Lit113;
   static final SimpleSymbol Lit114;
   static final SimpleSymbol Lit115;
   static final PairWithPosition Lit116;
   static final SimpleSymbol Lit117 = (SimpleSymbol)(new SimpleSymbol("pregexp-reverse!")).readResolve();
   static final SimpleSymbol Lit118 = (SimpleSymbol)(new SimpleSymbol("pregexp-error")).readResolve();
   static final SimpleSymbol Lit119 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-pattern")).readResolve();
   static final SimpleSymbol Lit12;
   static final SimpleSymbol Lit120 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-branch")).readResolve();
   static final SimpleSymbol Lit121 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-escaped-number")).readResolve();
   static final SimpleSymbol Lit122 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-escaped-char")).readResolve();
   static final SimpleSymbol Lit123 = (SimpleSymbol)(new SimpleSymbol("pregexp-invert-char-list")).readResolve();
   static final SimpleSymbol Lit124 = (SimpleSymbol)(new SimpleSymbol("pregexp-string-match")).readResolve();
   static final SimpleSymbol Lit125 = (SimpleSymbol)(new SimpleSymbol("pregexp-char-word?")).readResolve();
   static final SimpleSymbol Lit126 = (SimpleSymbol)(new SimpleSymbol("pregexp-at-word-boundary?")).readResolve();
   static final SimpleSymbol Lit127 = (SimpleSymbol)(new SimpleSymbol("pregexp-list-ref")).readResolve();
   static final SimpleSymbol Lit128 = (SimpleSymbol)(new SimpleSymbol("pregexp-make-backref-list")).readResolve();
   static final SimpleSymbol Lit129 = (SimpleSymbol)(new SimpleSymbol("pregexp-replace-aux")).readResolve();
   static final Char Lit13;
   static final SimpleSymbol Lit130 = (SimpleSymbol)(new SimpleSymbol("pregexp")).readResolve();
   static final SimpleSymbol Lit131 = (SimpleSymbol)(new SimpleSymbol("pregexp-match")).readResolve();
   static final SimpleSymbol Lit132 = (SimpleSymbol)(new SimpleSymbol("pregexp-split")).readResolve();
   static final SimpleSymbol Lit133 = (SimpleSymbol)(new SimpleSymbol("pregexp-replace")).readResolve();
   static final SimpleSymbol Lit134 = (SimpleSymbol)(new SimpleSymbol("pregexp-replace*")).readResolve();
   static final SimpleSymbol Lit135 = (SimpleSymbol)(new SimpleSymbol("pregexp-quote")).readResolve();
   static final SimpleSymbol Lit14;
   static final Char Lit15;
   static final IntNum Lit16;
   static final SimpleSymbol Lit17;
   static final Char Lit18;
   static final Char Lit19;
   static final Char Lit2;
   static final SimpleSymbol Lit20;
   static final SimpleSymbol Lit21;
   static final SimpleSymbol Lit22;
   static final SimpleSymbol Lit23;
   static final Char Lit24;
   static final Char Lit25;
   static final SimpleSymbol Lit26;
   static final Char Lit27;
   static final SimpleSymbol Lit28;
   static final Char Lit29;
   static final Char Lit3;
   static final SimpleSymbol Lit30;
   static final Char Lit31;
   static final PairWithPosition Lit32;
   static final Char Lit33;
   static final Char Lit34;
   static final Char Lit35;
   static final SimpleSymbol Lit36;
   static final Char Lit37;
   static final PairWithPosition Lit38;
   static final Char Lit39;
   static final SimpleSymbol Lit4;
   static final Char Lit40;
   static final SimpleSymbol Lit41;
   static final Char Lit42;
   static final PairWithPosition Lit43;
   static final Char Lit44;
   static final SimpleSymbol Lit45;
   static final Char Lit46;
   static final Char Lit47;
   static final Char Lit48;
   static final PairWithPosition Lit49;
   static final SimpleSymbol Lit5;
   static final Char Lit50;
   static final PairWithPosition Lit51;
   static final Char Lit52;
   static final PairWithPosition Lit53;
   static final Char Lit54;
   static final PairWithPosition Lit55;
   static final PairWithPosition Lit56;
   static final SimpleSymbol Lit57;
   static final Char Lit58;
   static final Char Lit59;
   static final Char Lit6;
   static final SimpleSymbol Lit60;
   static final SimpleSymbol Lit61;
   static final Char Lit62;
   static final PairWithPosition Lit63;
   static final SimpleSymbol Lit64;
   static final Char Lit65;
   static final Char Lit66;
   static final Char Lit67;
   static final SimpleSymbol Lit68;
   static final SimpleSymbol Lit69;
   static final Char Lit7;
   static final SimpleSymbol Lit70;
   static final SimpleSymbol Lit71;
   static final SimpleSymbol Lit72;
   static final IntNum Lit73;
   static final SimpleSymbol Lit74;
   static final SimpleSymbol Lit75;
   static final SimpleSymbol Lit76;
   static final Char Lit77;
   static final Char Lit78;
   static final SimpleSymbol Lit79;
   static final IntNum Lit8;
   static final SimpleSymbol Lit80;
   static final SimpleSymbol Lit81;
   static final SimpleSymbol Lit82;
   static final SimpleSymbol Lit83;
   static final Char Lit84;
   static final SimpleSymbol Lit85;
   static final SimpleSymbol Lit86;
   static final SimpleSymbol Lit87;
   static final SimpleSymbol Lit88;
   static final SimpleSymbol Lit89;
   static final Char Lit9;
   static final SimpleSymbol Lit90;
   static final SimpleSymbol Lit91;
   static final SimpleSymbol Lit92;
   static final SimpleSymbol Lit93;
   static final SimpleSymbol Lit94;
   static final SimpleSymbol Lit95;
   static final Char Lit96;
   static final Char Lit97;
   static final Char Lit98;
   static final SimpleSymbol Lit99;
   static final ModuleMethod lambda$Fn1;
   static final ModuleMethod lambda$Fn10;
   static final ModuleMethod lambda$Fn6;
   static final ModuleMethod lambda$Fn7;
   static final ModuleMethod lambda$Fn8;
   static final ModuleMethod lambda$Fn9;
   public static final ModuleMethod pregexp;
   public static final ModuleMethod pregexp$Mnat$Mnword$Mnboundary$Qu;
   public static final ModuleMethod pregexp$Mnchar$Mnword$Qu;
   public static final ModuleMethod pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu;
   public static final ModuleMethod pregexp$Mnerror;
   public static final ModuleMethod pregexp$Mninvert$Mnchar$Mnlist;
   public static final ModuleMethod pregexp$Mnlist$Mnref;
   public static final ModuleMethod pregexp$Mnmake$Mnbackref$Mnlist;
   public static final ModuleMethod pregexp$Mnmatch;
   public static final ModuleMethod pregexp$Mnmatch$Mnpositions;
   public static final ModuleMethod pregexp$Mnmatch$Mnpositions$Mnaux;
   public static final ModuleMethod pregexp$Mnquote;
   public static final ModuleMethod pregexp$Mnread$Mnbranch;
   public static final ModuleMethod pregexp$Mnread$Mnchar$Mnlist;
   public static final ModuleMethod pregexp$Mnread$Mncluster$Mntype;
   public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnchar;
   public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnnumber;
   public static final ModuleMethod pregexp$Mnread$Mnnums;
   public static final ModuleMethod pregexp$Mnread$Mnpattern;
   public static final ModuleMethod pregexp$Mnread$Mnpiece;
   public static final ModuleMethod pregexp$Mnread$Mnposix$Mnchar$Mnclass;
   public static final ModuleMethod pregexp$Mnread$Mnsubpattern;
   public static final ModuleMethod pregexp$Mnreplace;
   public static final ModuleMethod pregexp$Mnreplace$Mnaux;
   public static final ModuleMethod pregexp$Mnreplace$St;
   public static final ModuleMethod pregexp$Mnreverse$Ex;
   public static final ModuleMethod pregexp$Mnsplit;
   public static final ModuleMethod pregexp$Mnstring$Mnmatch;
   public static final ModuleMethod pregexp$Mnwrap$Mnquantifier$Mnif$Mnany;


   static {
      Char var0 = Char.make(92);
      Lit19 = var0;
      Char var1 = Char.make(46);
      Lit13 = var1;
      Char var2 = Char.make(63);
      Lit47 = var2;
      Char var3 = Char.make(42);
      Lit65 = var3;
      Char var4 = Char.make(43);
      Lit66 = var4;
      Char var5 = Char.make(124);
      Lit7 = var5;
      Char var6 = Char.make(94);
      Lit9 = var6;
      Char var7 = Char.make(36);
      Lit11 = var7;
      Char var8 = Char.make(91);
      Lit15 = var8;
      Char var9 = Char.make(93);
      Lit46 = var9;
      Char var10 = Char.make(123);
      Lit67 = var10;
      Char var11 = Char.make(125);
      Lit78 = var11;
      Char var12 = Char.make(40);
      Lit18 = var12;
      Char var13 = Char.make(41);
      Lit6 = var13;
      Lit116 = PairWithPosition.make(var0, PairWithPosition.make(var1, PairWithPosition.make(var2, PairWithPosition.make(var3, PairWithPosition.make(var4, PairWithPosition.make(var5, PairWithPosition.make(var6, PairWithPosition.make(var7, PairWithPosition.make(var8, PairWithPosition.make(var9, PairWithPosition.make(var10, PairWithPosition.make(var11, PairWithPosition.make(var12, PairWithPosition.make(var13, LList.Empty, "pregexp.scm", 3153977), "pregexp.scm", 3153973), "pregexp.scm", 3153969), "pregexp.scm", 3153965), "pregexp.scm", 3153961), "pregexp.scm", 3153957), "pregexp.scm", 3149885), "pregexp.scm", 3149881), "pregexp.scm", 3149877), "pregexp.scm", 3149873), "pregexp.scm", 3149869), "pregexp.scm", 3149865), "pregexp.scm", 3149861), "pregexp.scm", 3149856);
      Lit115 = (SimpleSymbol)(new SimpleSymbol("pattern-must-be-compiled-or-string-regexp")).readResolve();
      Lit114 = (SimpleSymbol)(new SimpleSymbol("pregexp-match-positions")).readResolve();
      Lit113 = Char.make(38);
      Lit112 = (SimpleSymbol)(new SimpleSymbol("identity")).readResolve();
      Lit111 = (SimpleSymbol)(new SimpleSymbol("fk")).readResolve();
      Lit110 = (SimpleSymbol)(new SimpleSymbol("greedy-quantifier-operand-could-be-empty")).readResolve();
      Lit109 = (SimpleSymbol)(new SimpleSymbol(":no-backtrack")).readResolve();
      SimpleSymbol var14 = (SimpleSymbol)(new SimpleSymbol(":between")).readResolve();
      Lit68 = var14;
      Boolean var15 = Boolean.FALSE;
      IntNum var19 = IntNum.make(0);
      Lit73 = var19;
      Boolean var21 = Boolean.FALSE;
      SimpleSymbol var22 = (SimpleSymbol)(new SimpleSymbol(":any")).readResolve();
      Lit14 = var22;
      Lit108 = PairWithPosition.make(var14, PairWithPosition.make(var15, PairWithPosition.make(var19, PairWithPosition.make(var21, PairWithPosition.make(var22, LList.Empty, "pregexp.scm", 2338881), "pregexp.scm", 2338878), "pregexp.scm", 2338876), "pregexp.scm", 2338873), "pregexp.scm", 2338863);
      Lit107 = (SimpleSymbol)(new SimpleSymbol(":neg-lookbehind")).readResolve();
      Lit106 = PairWithPosition.make(Lit68, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit73, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit14, LList.Empty, "pregexp.scm", 2302017), "pregexp.scm", 2302014), "pregexp.scm", 2302012), "pregexp.scm", 2302009), "pregexp.scm", 2301999);
      Lit105 = (SimpleSymbol)(new SimpleSymbol(":lookbehind")).readResolve();
      Lit104 = (SimpleSymbol)(new SimpleSymbol(":neg-lookahead")).readResolve();
      Lit103 = (SimpleSymbol)(new SimpleSymbol(":lookahead")).readResolve();
      Lit102 = (SimpleSymbol)(new SimpleSymbol("non-existent-backref")).readResolve();
      Lit101 = (SimpleSymbol)(new SimpleSymbol("pregexp-match-positions-aux")).readResolve();
      Lit100 = (SimpleSymbol)(new SimpleSymbol(":sub")).readResolve();
      Lit99 = (SimpleSymbol)(new SimpleSymbol("pregexp-check-if-in-char-class?")).readResolve();
      Lit98 = Char.make(102);
      Lit97 = Char.make(101);
      Lit96 = Char.make(99);
      Lit95 = (SimpleSymbol)(new SimpleSymbol(":xdigit")).readResolve();
      Lit94 = (SimpleSymbol)(new SimpleSymbol(":upper")).readResolve();
      Lit93 = (SimpleSymbol)(new SimpleSymbol(":punct")).readResolve();
      Lit92 = (SimpleSymbol)(new SimpleSymbol(":print")).readResolve();
      Lit91 = (SimpleSymbol)(new SimpleSymbol(":lower")).readResolve();
      Lit90 = (SimpleSymbol)(new SimpleSymbol(":graph")).readResolve();
      Lit89 = (SimpleSymbol)(new SimpleSymbol(":cntrl")).readResolve();
      Lit88 = (SimpleSymbol)(new SimpleSymbol(":blank")).readResolve();
      Lit87 = (SimpleSymbol)(new SimpleSymbol(":ascii")).readResolve();
      Lit86 = (SimpleSymbol)(new SimpleSymbol(":alpha")).readResolve();
      Lit85 = (SimpleSymbol)(new SimpleSymbol(":alnum")).readResolve();
      Lit84 = Char.make(95);
      Lit83 = (SimpleSymbol)(new SimpleSymbol(":char-range")).readResolve();
      Lit82 = (SimpleSymbol)(new SimpleSymbol(":one-of-chars")).readResolve();
      Lit81 = (SimpleSymbol)(new SimpleSymbol("character-class-ended-too-soon")).readResolve();
      Lit80 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-char-list")).readResolve();
      Lit79 = (SimpleSymbol)(new SimpleSymbol(":none-of-chars")).readResolve();
      Lit77 = Char.make(44);
      Lit76 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-nums")).readResolve();
      Lit75 = (SimpleSymbol)(new SimpleSymbol("left-brace-must-be-followed-by-number")).readResolve();
      Lit74 = (SimpleSymbol)(new SimpleSymbol("pregexp-wrap-quantifier-if-any")).readResolve();
      Lit72 = (SimpleSymbol)(new SimpleSymbol("next-i")).readResolve();
      Lit71 = (SimpleSymbol)(new SimpleSymbol("at-most")).readResolve();
      Lit70 = (SimpleSymbol)(new SimpleSymbol("at-least")).readResolve();
      Lit69 = (SimpleSymbol)(new SimpleSymbol("minimal?")).readResolve();
      Lit64 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-subpattern")).readResolve();
      Lit63 = PairWithPosition.make(Lit100, LList.Empty, "pregexp.scm", 942102);
      Lit62 = Char.make(120);
      Lit61 = (SimpleSymbol)(new SimpleSymbol(":case-insensitive")).readResolve();
      Lit60 = (SimpleSymbol)(new SimpleSymbol(":case-sensitive")).readResolve();
      Lit59 = Char.make(105);
      Lit58 = Char.make(45);
      Lit57 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-cluster-type")).readResolve();
      Lit56 = PairWithPosition.make(Lit107, LList.Empty, "pregexp.scm", 876575);
      Lit55 = PairWithPosition.make(Lit105, LList.Empty, "pregexp.scm", 872479);
      Lit54 = Char.make(60);
      Lit53 = PairWithPosition.make(Lit109, LList.Empty, "pregexp.scm", 860188);
      Lit52 = Char.make(62);
      Lit51 = PairWithPosition.make(Lit104, LList.Empty, "pregexp.scm", 856092);
      Lit50 = Char.make(33);
      Lit49 = PairWithPosition.make(Lit103, LList.Empty, "pregexp.scm", 851996);
      Lit48 = Char.make(61);
      Lit45 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-posix-char-class")).readResolve();
      Lit44 = Char.make(58);
      var14 = (SimpleSymbol)(new SimpleSymbol(":neg-char")).readResolve();
      Lit17 = var14;
      SimpleSymbol var17 = (SimpleSymbol)(new SimpleSymbol(":word")).readResolve();
      Lit41 = var17;
      Lit43 = PairWithPosition.make(var14, PairWithPosition.make(var17, LList.Empty, "pregexp.scm", 696359), "pregexp.scm", 696348);
      Lit42 = Char.make(87);
      Lit40 = Char.make(119);
      Lit39 = Char.make(116);
      var14 = Lit17;
      var17 = (SimpleSymbol)(new SimpleSymbol(":space")).readResolve();
      Lit36 = var17;
      Lit38 = PairWithPosition.make(var14, PairWithPosition.make(var17, LList.Empty, "pregexp.scm", 684071), "pregexp.scm", 684060);
      Lit37 = Char.make(83);
      Lit35 = Char.make(115);
      Lit34 = Char.make(114);
      Lit33 = Char.make(110);
      var14 = Lit17;
      var17 = (SimpleSymbol)(new SimpleSymbol(":digit")).readResolve();
      Lit30 = var17;
      Lit32 = PairWithPosition.make(var14, PairWithPosition.make(var17, LList.Empty, "pregexp.scm", 667687), "pregexp.scm", 667676);
      Lit31 = Char.make(68);
      Lit29 = Char.make(100);
      Lit28 = (SimpleSymbol)(new SimpleSymbol(":not-wbdry")).readResolve();
      Lit27 = Char.make(66);
      Lit26 = (SimpleSymbol)(new SimpleSymbol(":wbdry")).readResolve();
      Lit25 = Char.make(98);
      Lit24 = Char.make(10);
      Lit23 = (SimpleSymbol)(new SimpleSymbol(":empty")).readResolve();
      Lit22 = (SimpleSymbol)(new SimpleSymbol("backslash")).readResolve();
      Lit21 = (SimpleSymbol)(new SimpleSymbol("pregexp-read-piece")).readResolve();
      Lit20 = (SimpleSymbol)(new SimpleSymbol(":backref")).readResolve();
      Lit16 = IntNum.make(2);
      Lit12 = (SimpleSymbol)(new SimpleSymbol(":eos")).readResolve();
      Lit10 = (SimpleSymbol)(new SimpleSymbol(":bos")).readResolve();
      Lit8 = IntNum.make(1);
      Lit5 = (SimpleSymbol)(new SimpleSymbol(":seq")).readResolve();
      Lit4 = (SimpleSymbol)(new SimpleSymbol(":or")).readResolve();
      Lit3 = Char.make(32);
      Lit2 = Char.make(97);
      Lit1 = Char.make(59);
      Lit0 = IntNum.make(20050502);
      $instance = new pregexp();
      pregexp var16 = $instance;
      ModuleMethod var20 = new ModuleMethod(var16, 16, Lit117, 4097);
      var20.setProperty("source-location", "pregexp.scm:47");
      pregexp$Mnreverse$Ex = var20;
      var20 = new ModuleMethod(var16, 17, Lit118, -4096);
      var20.setProperty("source-location", "pregexp.scm:57");
      pregexp$Mnerror = var20;
      var20 = new ModuleMethod(var16, 18, Lit119, 12291);
      var20.setProperty("source-location", "pregexp.scm:65");
      pregexp$Mnread$Mnpattern = var20;
      var20 = new ModuleMethod(var16, 19, Lit120, 12291);
      var20.setProperty("source-location", "pregexp.scm:79");
      pregexp$Mnread$Mnbranch = var20;
      var20 = new ModuleMethod(var16, 20, Lit21, 12291);
      var20.setProperty("source-location", "pregexp.scm:91");
      pregexp$Mnread$Mnpiece = var20;
      var20 = new ModuleMethod(var16, 21, Lit121, 12291);
      var20.setProperty("source-location", "pregexp.scm:138");
      pregexp$Mnread$Mnescaped$Mnnumber = var20;
      var20 = new ModuleMethod(var16, 22, Lit122, 12291);
      var20.setProperty("source-location", "pregexp.scm:155");
      pregexp$Mnread$Mnescaped$Mnchar = var20;
      var20 = new ModuleMethod(var16, 23, Lit45, 12291);
      var20.setProperty("source-location", "pregexp.scm:174");
      pregexp$Mnread$Mnposix$Mnchar$Mnclass = var20;
      var20 = new ModuleMethod(var16, 24, Lit57, 12291);
      var20.setProperty("source-location", "pregexp.scm:200");
      pregexp$Mnread$Mncluster$Mntype = var20;
      var20 = new ModuleMethod(var16, 25, Lit64, 12291);
      var20.setProperty("source-location", "pregexp.scm:233");
      pregexp$Mnread$Mnsubpattern = var20;
      var20 = new ModuleMethod(var16, 26, Lit74, 12291);
      var20.setProperty("source-location", "pregexp.scm:254");
      pregexp$Mnwrap$Mnquantifier$Mnif$Mnany = var20;
      var20 = new ModuleMethod(var16, 27, Lit76, 12291);
      var20.setProperty("source-location", "pregexp.scm:300");
      pregexp$Mnread$Mnnums = var20;
      var20 = new ModuleMethod(var16, 28, Lit123, 4097);
      var20.setProperty("source-location", "pregexp.scm:323");
      pregexp$Mninvert$Mnchar$Mnlist = var20;
      var20 = new ModuleMethod(var16, 29, Lit80, 12291);
      var20.setProperty("source-location", "pregexp.scm:330");
      pregexp$Mnread$Mnchar$Mnlist = var20;
      var20 = new ModuleMethod(var16, 30, Lit124, 24582);
      var20.setProperty("source-location", "pregexp.scm:368");
      pregexp$Mnstring$Mnmatch = var20;
      var20 = new ModuleMethod(var16, 31, Lit125, 4097);
      var20.setProperty("source-location", "pregexp.scm:379");
      pregexp$Mnchar$Mnword$Qu = var20;
      var20 = new ModuleMethod(var16, 32, Lit126, 12291);
      var20.setProperty("source-location", "pregexp.scm:387");
      pregexp$Mnat$Mnword$Mnboundary$Qu = var20;
      var20 = new ModuleMethod(var16, 33, Lit99, 8194);
      var20.setProperty("source-location", "pregexp.scm:399");
      pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu = var20;
      var20 = new ModuleMethod(var16, 34, Lit127, 8194);
      var20.setProperty("source-location", "pregexp.scm:429");
      pregexp$Mnlist$Mnref = var20;
      var20 = new ModuleMethod(var16, 35, Lit128, 4097);
      var20.setProperty("source-location", "pregexp.scm:448");
      pregexp$Mnmake$Mnbackref$Mnlist = var20;
      var20 = new ModuleMethod(var16, 36, (Object)null, 0);
      var20.setProperty("source-location", "pregexp.scm:463");
      lambda$Fn1 = var20;
      var20 = new ModuleMethod(var16, 37, (Object)null, 0);
      var20.setProperty("source-location", "pregexp.scm:551");
      lambda$Fn6 = var20;
      var20 = new ModuleMethod(var16, 38, (Object)null, 0);
      var20.setProperty("source-location", "pregexp.scm:556");
      lambda$Fn7 = var20;
      var20 = new ModuleMethod(var16, 39, (Object)null, 0);
      var20.setProperty("source-location", "pregexp.scm:564");
      lambda$Fn8 = var20;
      var20 = new ModuleMethod(var16, 40, (Object)null, 0);
      var20.setProperty("source-location", "pregexp.scm:573");
      lambda$Fn9 = var20;
      var20 = new ModuleMethod(var16, 41, (Object)null, 0);
      var20.setProperty("source-location", "pregexp.scm:578");
      lambda$Fn10 = var20;
      var20 = new ModuleMethod(var16, 42, Lit101, 24582);
      var20.setProperty("source-location", "pregexp.scm:459");
      pregexp$Mnmatch$Mnpositions$Mnaux = var20;
      var20 = new ModuleMethod(var16, 43, Lit129, 16388);
      var20.setProperty("source-location", "pregexp.scm:639");
      pregexp$Mnreplace$Mnaux = var20;
      var20 = new ModuleMethod(var16, 44, Lit130, 4097);
      var20.setProperty("source-location", "pregexp.scm:665");
      pregexp = var20;
      var20 = new ModuleMethod(var16, 45, Lit114, -4094);
      var20.setProperty("source-location", "pregexp.scm:670");
      pregexp$Mnmatch$Mnpositions = var20;
      var20 = new ModuleMethod(var16, 46, Lit131, -4094);
      var20.setProperty("source-location", "pregexp.scm:690");
      pregexp$Mnmatch = var20;
      var20 = new ModuleMethod(var16, 47, Lit132, 8194);
      var20.setProperty("source-location", "pregexp.scm:700");
      pregexp$Mnsplit = var20;
      var20 = new ModuleMethod(var16, 48, Lit133, 12291);
      var20.setProperty("source-location", "pregexp.scm:723");
      pregexp$Mnreplace = var20;
      var20 = new ModuleMethod(var16, 49, Lit134, 12291);
      var20.setProperty("source-location", "pregexp.scm:736");
      pregexp$Mnreplace$St = var20;
      ModuleMethod var18 = new ModuleMethod(var16, 50, Lit135, 4097);
      var18.setProperty("source-location", "pregexp.scm:764");
      pregexp$Mnquote = var18;
      $instance.run();
   }

   public pregexp() {
      ModuleInfo.register(this);
   }

   public static Object isPregexpAtWordBoundary(Object var0, Object var1, Object var2) {
      Object var3 = Scheme.numEqu.apply2(var1, Lit73);

      boolean var6;
      try {
         var6 = ((Boolean)var3).booleanValue();
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "x", -2, var3);
      }

      if(var6) {
         return var6?Boolean.TRUE:Boolean.FALSE;
      } else {
         var2 = Scheme.numGEq.apply2(var1, var2);

         try {
            var6 = ((Boolean)var2).booleanValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "x", -2, var2);
         }

         if(var6) {
            return var6?Boolean.TRUE:Boolean.FALSE;
         } else {
            CharSequence var15;
            try {
               var15 = (CharSequence)var0;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 1, var0);
            }

            int var4;
            try {
               var4 = ((Number)var1).intValue();
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 2, var1);
            }

            char var16 = strings.stringRef(var15, var4);

            try {
               var15 = (CharSequence)var0;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var0);
            }

            var0 = AddOp.$Mn.apply2(var1, Lit8);

            int var5;
            try {
               var5 = ((Number)var0).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-ref", 2, var0);
            }

            char var18 = strings.stringRef(var15, var5);
            var1 = isPregexpCheckIfInCharClass(Char.make(var16), Lit41);
            var2 = isPregexpCheckIfInCharClass(Char.make(var18), Lit41);
            if(var1 != Boolean.FALSE) {
               if(var2 != Boolean.FALSE) {
                  var0 = Boolean.FALSE;
               } else {
                  var0 = Boolean.TRUE;
               }
            } else {
               var0 = var1;
            }

            if(var0 != Boolean.FALSE) {
               return var0;
            } else {
               Boolean var14;
               try {
                  var14 = Boolean.FALSE;
               } catch (ClassCastException var7) {
                  throw new WrongType(var7, "x", -2, var1);
               }

               byte var17;
               if(var1 != var14) {
                  var17 = 1;
               } else {
                  var17 = 0;
               }

               var4 = var17 + 1 & 1;
               return var4 != 0?var2:(var4 != 0?Boolean.TRUE:Boolean.FALSE);
            }
         }
      }
   }

   public static boolean isPregexpCharWord(Object var0) {
      Char var1;
      try {
         var1 = (Char)var0;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "char-alphabetic?", 1, var0);
      }

      boolean var2 = unicode.isCharAlphabetic(var1);
      if(!var2) {
         try {
            var1 = (Char)var0;
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "char-numeric?", 1, var0);
         }

         boolean var3 = unicode.isCharNumeric(var1);
         var2 = var3;
         if(!var3) {
            try {
               var1 = (Char)var0;
            } catch (ClassCastException var4) {
               throw new WrongType(var4, "char=?", 1, var0);
            }

            return characters.isChar$Eq(var1, Lit84);
         }
      }

      return var2;
   }

   public static Object isPregexpCheckIfInCharClass(Object var0, Object var1) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static Object lambda1sub(Object var0) {
      if(lists.isPair(var0)) {
         Object var1 = lists.car.apply1(var0);
         Object var2 = lambda1sub(lists.cdr.apply1(var0));
         return Scheme.isEqv.apply2(var1, Lit100) != Boolean.FALSE?lists.cons(lists.cons(var0, Boolean.FALSE), var2):append.append$V(new Object[]{lambda1sub(var1), var2});
      } else {
         return LList.Empty;
      }
   }

   public static Pair pregexp(Object var0) {
      $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
      SimpleSymbol var1 = Lit100;
      GenericProc var2 = lists.car;
      IntNum var3 = Lit73;

      CharSequence var4;
      try {
         var4 = (CharSequence)var0;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "string-length", 1, var0);
      }

      return LList.list2(var1, var2.apply1(pregexpReadPattern(var0, var3, Integer.valueOf(strings.stringLength(var4)))));
   }

   public static Object pregexpError$V(Object[] var0) {
      Object var3 = LList.makeList(var0, 0);
      ports.display("Error:");

      while(var3 != LList.Empty) {
         Pair var1;
         try {
            var1 = (Pair)var3;
         } catch (ClassCastException var2) {
            throw new WrongType(var2, "arg0", -2, var3);
         }

         var3 = var1.getCar();
         ports.display(Lit3);
         ports.write(var3);
         var3 = var1.getCdr();
      }

      ports.newline();
      return misc.error$V("pregexp-error", new Object[0]);
   }

   public static Object pregexpInvertCharList(Object var0) {
      Object var1 = lists.car.apply1(var0);

      Pair var2;
      try {
         var2 = (Pair)var1;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "set-car!", 1, var1);
      }

      lists.setCar$Ex(var2, Lit79);
      return var0;
   }

   public static Object pregexpListRef(Object var0, Object var1) {
      IntNum var3 = Lit73;
      Object var2 = var0;

      for(var0 = var3; !lists.isNull(var2); var0 = AddOp.$Pl.apply2(var0, Lit8)) {
         if(Scheme.numEqu.apply2(var0, var1) != Boolean.FALSE) {
            return lists.car.apply1(var2);
         }

         var2 = lists.cdr.apply1(var2);
      }

      return Boolean.FALSE;
   }

   public static Object pregexpMakeBackrefList(Object var0) {
      return lambda1sub(var0);
   }

   public static Object pregexpMatch$V(Object var0, Object var1, Object[] var2) {
      LList var12 = LList.makeList(var2, 0);
      var0 = Scheme.apply.apply4(pregexp$Mnmatch$Mnpositions, var0, var1, var12);
      Object var13 = var0;
      if(var0 != Boolean.FALSE) {
         LList var3 = LList.Empty;
         var13 = var0;

         Object var14;
         for(var0 = var3; var13 != LList.Empty; var13 = var14) {
            Pair var4;
            try {
               var4 = (Pair)var13;
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "arg0", -2, var13);
            }

            var14 = var4.getCdr();
            Object var16 = var4.getCar();
            var13 = var16;
            if(var16 != Boolean.FALSE) {
               CharSequence var15;
               try {
                  var15 = (CharSequence)var1;
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "substring", 1, var1);
               }

               Object var5 = lists.car.apply1(var16);

               int var6;
               try {
                  var6 = ((Number)var5).intValue();
               } catch (ClassCastException var9) {
                  throw new WrongType(var9, "substring", 2, var5);
               }

               var16 = lists.cdr.apply1(var16);

               int var7;
               try {
                  var7 = ((Number)var16).intValue();
               } catch (ClassCastException var8) {
                  throw new WrongType(var8, "substring", 3, var16);
               }

               var13 = strings.substring(var15, var6, var7);
            }

            var0 = Pair.make(var13, var0);
         }

         var13 = LList.reverseInPlace(var0);
      }

      return var13;
   }

   public static Object pregexpMatchPositions$V(Object var0, Object var1, Object[] var2) {
      LList var3 = LList.makeList(var2, 0);
      Object var12;
      if(strings.isString(var0)) {
         var12 = pregexp(var0);
      } else {
         var12 = var0;
         if(!lists.isPair(var0)) {
            pregexpError$V(new Object[]{Lit114, Lit115, var0});
            var12 = var0;
         }
      }

      CharSequence var11;
      try {
         var11 = (CharSequence)var1;
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "string-length", 1, var1);
      }

      int var6 = strings.stringLength(var11);
      Object var4;
      if(lists.isNull(var3)) {
         var0 = Lit73;
      } else {
         var0 = lists.car.apply1(var3);
         var4 = lists.cdr.apply1(var3);

         try {
            var3 = (LList)var4;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "opt-args", -2, var4);
         }
      }

      Object var13;
      if(lists.isNull(var3)) {
         var13 = Integer.valueOf(var6);
      } else {
         var13 = lists.car.apply1(var3);
      }

      var4 = var0;

      while(true) {
         Object var5 = Scheme.numLEq.apply2(var4, var13);

         boolean var7;
         try {
            var7 = ((Boolean)var5).booleanValue();
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "x", -2, var5);
         }

         if(!var7) {
            if(var7) {
               return Boolean.TRUE;
            }

            return Boolean.FALSE;
         }

         var5 = pregexpMatchPositionsAux(var12, var1, Integer.valueOf(var6), var0, var13, var4);
         if(var5 != Boolean.FALSE) {
            return var5;
         }

         var4 = AddOp.$Pl.apply2(var4, Lit8);
      }
   }

   public static Object pregexpMatchPositionsAux(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      pregexp.frame var6 = new pregexp.frame();
      var6.s = var1;
      var6.sn = var2;
      var6.start = var3;
      var6.n = var4;
      Procedure var9 = var6.identity;
      var2 = pregexpMakeBackrefList(var0);
      var6.case$Mnsensitive$Qu = Boolean.TRUE;
      var6.backrefs = var2;
      var6.identity = var9;
      var6.lambda3sub(var0, var5, var6.identity, lambda$Fn1);
      var0 = var6.backrefs;

      Pair var10;
      for(var1 = LList.Empty; var0 != LList.Empty; var1 = Pair.make(lists.cdr.apply1(var10.getCar()), var1)) {
         try {
            var10 = (Pair)var0;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "arg0", -2, var0);
         }

         var0 = var10.getCdr();
      }

      LList var8 = LList.reverseInPlace(var1);
      var1 = lists.car.apply1(var8);
      return var1 != Boolean.FALSE?var8:var1;
   }

   public static Object pregexpQuote(Object var0) {
      CharSequence var1;
      try {
         var1 = (CharSequence)var0;
      } catch (ClassCastException var9) {
         throw new WrongType(var9, "string-length", 1, var0);
      }

      Object var2 = Integer.valueOf(strings.stringLength(var1) - 1);

      Object var3;
      Object var11;
      for(var11 = LList.Empty; Scheme.numLss.apply2(var2, Lit73) == Boolean.FALSE; var2 = var3) {
         var3 = AddOp.$Mn.apply2(var2, Lit8);

         CharSequence var4;
         try {
            var4 = (CharSequence)var0;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-ref", 1, var0);
         }

         int var5;
         try {
            var5 = ((Number)var2).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-ref", 2, var2);
         }

         char var12 = strings.stringRef(var4, var5);
         if(lists.memv(Char.make(var12), Lit116) != Boolean.FALSE) {
            var11 = lists.cons(Lit19, lists.cons(Char.make(var12), var11));
         } else {
            var11 = lists.cons(Char.make(var12), var11);
         }
      }

      LList var10;
      try {
         var10 = (LList)var11;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "list->string", 1, var11);
      }

      return strings.list$To$String(var10);
   }

   public static Object pregexpReadBranch(Object var0, Object var1, Object var2) {
      LList var4 = LList.Empty;
      Object var3 = var1;

      for(var1 = var4; Scheme.numGEq.apply2(var3, var2) == Boolean.FALSE; var3 = lists.cadr.apply1(var3)) {
         CharSequence var9;
         try {
            var9 = (CharSequence)var0;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "string-ref", 1, var0);
         }

         int var5;
         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "string-ref", 2, var3);
         }

         char var10 = strings.stringRef(var9, var5);
         boolean var6 = characters.isChar$Eq(Char.make(var10), Lit7);
         if(var6) {
            if(var6) {
               return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(var1)), var3);
            }
         } else if(characters.isChar$Eq(Char.make(var10), Lit6)) {
            return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(var1)), var3);
         }

         var3 = pregexpReadPiece(var0, var3, var2);
         var1 = lists.cons(lists.car.apply1(var3), var1);
      }

      return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(var1)), var3);
   }

   public static Object pregexpReadCharList(Object var0, Object var1, Object var2) {
      LList var4 = LList.Empty;
      Object var3 = var1;
      var1 = var4;

      while(Scheme.numGEq.apply2(var3, var2) == Boolean.FALSE) {
         CharSequence var20;
         try {
            var20 = (CharSequence)var0;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "string-ref", 1, var0);
         }

         int var8;
         try {
            var8 = ((Number)var3).intValue();
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "string-ref", 2, var3);
         }

         char var23 = strings.stringRef(var20, var8);
         if(Scheme.isEqv.apply2(Char.make(var23), Lit46) != Boolean.FALSE) {
            if(!lists.isNull(var1)) {
               return LList.list2(lists.cons(Lit82, pregexpReverse$Ex(var1)), AddOp.$Pl.apply2(var3, Lit8));
            }

            var1 = lists.cons(Char.make(var23), var1);
            var3 = AddOp.$Pl.apply2(var3, Lit8);
         } else if(Scheme.isEqv.apply2(Char.make(var23), Lit19) != Boolean.FALSE) {
            var3 = pregexpReadEscapedChar(var0, var3, var2);
            if(var3 == Boolean.FALSE) {
               return pregexpError$V(new Object[]{Lit80, Lit22});
            }

            var1 = lists.cons(lists.car.apply1(var3), var1);
            var3 = lists.cadr.apply1(var3);
         } else {
            int var9;
            Object var21;
            CharSequence var22;
            if(Scheme.isEqv.apply2(Char.make(var23), Lit58) == Boolean.FALSE) {
               if(Scheme.isEqv.apply2(Char.make(var23), Lit15) != Boolean.FALSE) {
                  try {
                     var22 = (CharSequence)var0;
                  } catch (ClassCastException var12) {
                     throw new WrongType(var12, "string-ref", 1, var0);
                  }

                  var21 = AddOp.$Pl.apply2(var3, Lit8);

                  try {
                     var9 = ((Number)var21).intValue();
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "string-ref", 2, var21);
                  }

                  if(characters.isChar$Eq(Char.make(strings.stringRef(var22, var9)), Lit44)) {
                     var3 = pregexpReadPosixCharClass(var0, AddOp.$Pl.apply2(var3, Lit16), var2);
                     var1 = lists.cons(lists.car.apply1(var3), var1);
                     var3 = lists.cadr.apply1(var3);
                  } else {
                     var1 = lists.cons(Char.make(var23), var1);
                     var3 = AddOp.$Pl.apply2(var3, Lit8);
                  }
               } else {
                  var1 = lists.cons(Char.make(var23), var1);
                  var3 = AddOp.$Pl.apply2(var3, Lit8);
               }
            } else {
               label104: {
                  boolean var10 = lists.isNull(var1);
                  Object var5;
                  if(var10) {
                     if(var10) {
                        break label104;
                     }
                  } else {
                     var21 = AddOp.$Pl.apply2(var3, Lit8);
                     var5 = Scheme.numLss.apply2(var21, var2);

                     try {
                        var10 = ((Boolean)var5).booleanValue();
                     } catch (ClassCastException var17) {
                        throw new WrongType(var17, "x", -2, var5);
                     }

                     if(var10) {
                        try {
                           var22 = (CharSequence)var0;
                        } catch (ClassCastException var16) {
                           throw new WrongType(var16, "string-ref", 1, var0);
                        }

                        try {
                           var9 = ((Number)var21).intValue();
                        } catch (ClassCastException var15) {
                           throw new WrongType(var15, "string-ref", 2, var21);
                        }

                        if(characters.isChar$Eq(Char.make(strings.stringRef(var22, var9)), Lit46)) {
                           break label104;
                        }
                     } else if(var10) {
                        break label104;
                     }
                  }

                  var5 = lists.car.apply1(var1);
                  if(characters.isChar(var5)) {
                     SimpleSymbol var6 = Lit83;

                     CharSequence var7;
                     try {
                        var7 = (CharSequence)var0;
                     } catch (ClassCastException var14) {
                        throw new WrongType(var14, "string-ref", 1, var0);
                     }

                     var21 = AddOp.$Pl.apply2(var3, Lit8);

                     try {
                        var8 = ((Number)var21).intValue();
                     } catch (ClassCastException var13) {
                        throw new WrongType(var13, "string-ref", 2, var21);
                     }

                     var1 = lists.cons(LList.list3(var6, var5, Char.make(strings.stringRef(var7, var8))), lists.cdr.apply1(var1));
                     var3 = AddOp.$Pl.apply2(var3, Lit16);
                  } else {
                     var1 = lists.cons(Char.make(var23), var1);
                     var3 = AddOp.$Pl.apply2(var3, Lit8);
                  }
                  continue;
               }

               var1 = lists.cons(Char.make(var23), var1);
               var3 = AddOp.$Pl.apply2(var3, Lit8);
            }
         }
      }

      return pregexpError$V(new Object[]{Lit80, Lit81});
   }

   public static Object pregexpReadClusterType(Object var0, Object var1, Object var2) {
      CharSequence var16;
      try {
         var16 = (CharSequence)var0;
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "string-ref", 1, var0);
      }

      int var5;
      try {
         var5 = ((Number)var1).intValue();
      } catch (ClassCastException var12) {
         throw new WrongType(var12, "string-ref", 2, var1);
      }

      char var18 = strings.stringRef(var16, var5);
      if(Scheme.isEqv.apply2(Char.make(var18), Lit47) != Boolean.FALSE) {
         var2 = AddOp.$Pl.apply2(var1, Lit8);

         CharSequence var14;
         try {
            var14 = (CharSequence)var0;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-ref", 1, var0);
         }

         try {
            var5 = ((Number)var2).intValue();
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "string-ref", 2, var2);
         }

         var18 = strings.stringRef(var14, var5);
         if(Scheme.isEqv.apply2(Char.make(var18), Lit44) != Boolean.FALSE) {
            return LList.list2(LList.Empty, AddOp.$Pl.apply2(var2, Lit8));
         } else if(Scheme.isEqv.apply2(Char.make(var18), Lit48) != Boolean.FALSE) {
            return LList.list2(Lit49, AddOp.$Pl.apply2(var2, Lit8));
         } else if(Scheme.isEqv.apply2(Char.make(var18), Lit50) != Boolean.FALSE) {
            return LList.list2(Lit51, AddOp.$Pl.apply2(var2, Lit8));
         } else if(Scheme.isEqv.apply2(Char.make(var18), Lit52) != Boolean.FALSE) {
            return LList.list2(Lit53, AddOp.$Pl.apply2(var2, Lit8));
         } else if(Scheme.isEqv.apply2(Char.make(var18), Lit54) != Boolean.FALSE) {
            try {
               var14 = (CharSequence)var0;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "string-ref", 1, var0);
            }

            var0 = AddOp.$Pl.apply2(var2, Lit8);

            try {
               var5 = ((Number)var0).intValue();
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "string-ref", 2, var0);
            }

            var18 = strings.stringRef(var14, var5);
            if(Scheme.isEqv.apply2(Char.make(var18), Lit48) != Boolean.FALSE) {
               var0 = Lit55;
            } else if(Scheme.isEqv.apply2(Char.make(var18), Lit50) != Boolean.FALSE) {
               var0 = Lit56;
            } else {
               var0 = pregexpError$V(new Object[]{Lit57});
            }

            return LList.list2(var0, AddOp.$Pl.apply2(var2, Lit16));
         } else {
            Object var3 = LList.Empty;
            Boolean var15 = Boolean.FALSE;

            while(true) {
               CharSequence var4;
               try {
                  var4 = (CharSequence)var0;
               } catch (ClassCastException var9) {
                  throw new WrongType(var9, "string-ref", 1, var0);
               }

               try {
                  var5 = ((Number)var2).intValue();
               } catch (ClassCastException var8) {
                  throw new WrongType(var8, "string-ref", 2, var2);
               }

               var18 = strings.stringRef(var4, var5);
               if(Scheme.isEqv.apply2(Char.make(var18), Lit58) != Boolean.FALSE) {
                  var2 = AddOp.$Pl.apply2(var2, Lit8);
                  var15 = Boolean.TRUE;
               } else if(Scheme.isEqv.apply2(Char.make(var18), Lit59) != Boolean.FALSE) {
                  var2 = AddOp.$Pl.apply2(var2, Lit8);
                  SimpleSymbol var17;
                  if(var15 != Boolean.FALSE) {
                     var17 = Lit60;
                  } else {
                     var17 = Lit61;
                  }

                  var3 = lists.cons(var17, var3);
                  var15 = Boolean.FALSE;
               } else {
                  if(Scheme.isEqv.apply2(Char.make(var18), Lit62) == Boolean.FALSE) {
                     if(Scheme.isEqv.apply2(Char.make(var18), Lit44) != Boolean.FALSE) {
                        return LList.list2(var3, AddOp.$Pl.apply2(var2, Lit8));
                     }

                     return pregexpError$V(new Object[]{Lit57});
                  }

                  $Stpregexp$Mnspace$Mnsensitive$Qu$St = var15;
                  var2 = AddOp.$Pl.apply2(var2, Lit8);
                  var15 = Boolean.FALSE;
               }
            }
         }
      } else {
         return LList.list2(Lit63, var1);
      }
   }

   public static Object pregexpReadEscapedChar(Object var0, Object var1, Object var2) {
      var2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(var1, Lit8), var2);

      boolean var4;
      try {
         var4 = ((Boolean)var2).booleanValue();
      } catch (ClassCastException var7) {
         throw new WrongType(var7, "x", -2, var2);
      }

      if(var4) {
         CharSequence var8;
         try {
            var8 = (CharSequence)var0;
         } catch (ClassCastException var6) {
            throw new WrongType(var6, "string-ref", 1, var0);
         }

         var0 = AddOp.$Pl.apply2(var1, Lit8);

         int var3;
         try {
            var3 = ((Number)var0).intValue();
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "string-ref", 2, var0);
         }

         char var9 = strings.stringRef(var8, var3);
         return Scheme.isEqv.apply2(Char.make(var9), Lit25) != Boolean.FALSE?LList.list2(Lit26, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit27) != Boolean.FALSE?LList.list2(Lit28, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit29) != Boolean.FALSE?LList.list2(Lit30, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit31) != Boolean.FALSE?LList.list2(Lit32, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit33) != Boolean.FALSE?LList.list2(Lit24, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit34) != Boolean.FALSE?LList.list2($Stpregexp$Mnreturn$Mnchar$St, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit35) != Boolean.FALSE?LList.list2(Lit36, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit37) != Boolean.FALSE?LList.list2(Lit38, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit39) != Boolean.FALSE?LList.list2($Stpregexp$Mntab$Mnchar$St, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit40) != Boolean.FALSE?LList.list2(Lit41, AddOp.$Pl.apply2(var1, Lit16)):(Scheme.isEqv.apply2(Char.make(var9), Lit42) != Boolean.FALSE?LList.list2(Lit43, AddOp.$Pl.apply2(var1, Lit16)):LList.list2(Char.make(var9), AddOp.$Pl.apply2(var1, Lit16))))))))))));
      } else {
         return var4?Boolean.TRUE:Boolean.FALSE;
      }
   }

   public static Object pregexpReadEscapedNumber(Object var0, Object var1, Object var2) {
      Object var3 = Scheme.numLss.apply2(AddOp.$Pl.apply2(var1, Lit8), var2);

      boolean var6;
      try {
         var6 = ((Boolean)var3).booleanValue();
      } catch (ClassCastException var13) {
         throw new WrongType(var13, "x", -2, var3);
      }

      if(!var6) {
         return var6?Boolean.TRUE:Boolean.FALSE;
      } else {
         CharSequence var4;
         try {
            var4 = (CharSequence)var0;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-ref", 1, var0);
         }

         var3 = AddOp.$Pl.apply2(var1, Lit8);

         int var5;
         try {
            var5 = ((Number)var3).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "string-ref", 2, var3);
         }

         char var16 = strings.stringRef(var4, var5);
         var6 = unicode.isCharNumeric(Char.make(var16));
         if(!var6) {
            return var6?Boolean.TRUE:Boolean.FALSE;
         } else {
            var1 = AddOp.$Pl.apply2(var1, Lit16);

            LList var14;
            Pair var15;
            for(var15 = LList.list1(Char.make(var16)); Scheme.numGEq.apply2(var1, var2) == Boolean.FALSE; var15 = lists.cons(Char.make(var16), var15)) {
               try {
                  var4 = (CharSequence)var0;
               } catch (ClassCastException var10) {
                  throw new WrongType(var10, "string-ref", 1, var0);
               }

               try {
                  var5 = ((Number)var1).intValue();
               } catch (ClassCastException var9) {
                  throw new WrongType(var9, "string-ref", 2, var1);
               }

               var16 = strings.stringRef(var4, var5);
               if(!unicode.isCharNumeric(Char.make(var16))) {
                  var0 = pregexpReverse$Ex(var15);

                  try {
                     var14 = (LList)var0;
                  } catch (ClassCastException var8) {
                     throw new WrongType(var8, "list->string", 1, var0);
                  }

                  return LList.list2(numbers.string$To$Number(strings.list$To$String(var14)), var1);
               }

               var1 = AddOp.$Pl.apply2(var1, Lit8);
            }

            var0 = pregexpReverse$Ex(var15);

            try {
               var14 = (LList)var0;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "list->string", 1, var0);
            }

            return LList.list2(numbers.string$To$Number(strings.list$To$String(var14)), var1);
         }
      }
   }

   public static Object pregexpReadNums(Object var0, Object var1, Object var2) {
      Object var5 = LList.Empty;
      Object var4 = LList.Empty;
      IntNum var3 = Lit8;

      int var7;
      char var17;
      while(true) {
         boolean var8;
         label91:
         while(true) {
            while(true) {
               if(Scheme.numGEq.apply2(var1, var2) != Boolean.FALSE) {
                  pregexpError$V(new Object[]{Lit76});
               }

               CharSequence var6;
               try {
                  var6 = (CharSequence)var0;
               } catch (ClassCastException var13) {
                  throw new WrongType(var13, "string-ref", 1, var0);
               }

               try {
                  var7 = ((Number)var1).intValue();
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "string-ref", 2, var1);
               }

               var17 = strings.stringRef(var6, var7);
               if(!unicode.isCharNumeric(Char.make(var17))) {
                  var8 = unicode.isCharWhitespace(Char.make(var17));
                  if(var8) {
                     if($Stpregexp$Mnspace$Mnsensitive$Qu$St != Boolean.FALSE) {
                        break label91;
                     }
                  } else if(!var8) {
                     break label91;
                  }

                  var1 = AddOp.$Pl.apply2(var1, Lit8);
               } else if(Scheme.numEqu.apply2(var3, Lit8) != Boolean.FALSE) {
                  var5 = lists.cons(Char.make(var17), var5);
                  var1 = AddOp.$Pl.apply2(var1, Lit8);
                  var3 = Lit8;
               } else {
                  var4 = lists.cons(Char.make(var17), var4);
                  var1 = AddOp.$Pl.apply2(var1, Lit8);
                  var3 = Lit16;
               }
            }
         }

         var8 = characters.isChar$Eq(Char.make(var17), Lit77);
         if(var8) {
            if(Scheme.numEqu.apply2(var3, Lit8) == Boolean.FALSE) {
               break;
            }
         } else if(!var8) {
            break;
         }

         var1 = AddOp.$Pl.apply2(var1, Lit8);
         var3 = Lit16;
      }

      if(characters.isChar$Eq(Char.make(var17), Lit78)) {
         var0 = pregexpReverse$Ex(var5);

         LList var14;
         try {
            var14 = (LList)var0;
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "list->string", 1, var0);
         }

         var0 = numbers.string$To$Number(strings.list$To$String(var14));
         var2 = pregexpReverse$Ex(var4);

         LList var15;
         try {
            var15 = (LList)var2;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "list->string", 1, var2);
         }

         var2 = numbers.string$To$Number(strings.list$To$String(var15));

         Boolean var16;
         try {
            var16 = Boolean.FALSE;
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "x", -2, var0);
         }

         byte var18;
         if(var0 != var16) {
            var18 = 1;
         } else {
            var18 = 0;
         }

         var7 = var18 + 1 & 1;
         if(var7 != 0) {
            if(Scheme.numEqu.apply2(var3, Lit8) == Boolean.FALSE) {
               return Scheme.numEqu.apply2(var3, Lit8) != Boolean.FALSE?LList.list3(var0, var0, var1):LList.list3(var0, var2, var1);
            }
         } else if(var7 == 0) {
            return Scheme.numEqu.apply2(var3, Lit8) != Boolean.FALSE?LList.list3(var0, var0, var1):LList.list3(var0, var2, var1);
         }

         return LList.list3(Lit73, Boolean.FALSE, var1);
      } else {
         return Boolean.FALSE;
      }
   }

   public static Object pregexpReadPattern(Object var0, Object var1, Object var2) {
      if(Scheme.numGEq.apply2(var1, var2) != Boolean.FALSE) {
         return LList.list2(LList.list2(Lit4, LList.list1(Lit5)), var1);
      } else {
         Object var3 = LList.Empty;

         while(true) {
            Object var4 = Scheme.numGEq.apply2(var1, var2);

            boolean var7;
            try {
               var7 = ((Boolean)var4).booleanValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "x", -2, var4);
            }

            int var6;
            if(var7) {
               if(var7) {
                  break;
               }
            } else {
               CharSequence var13;
               try {
                  var13 = (CharSequence)var0;
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "string-ref", 1, var0);
               }

               try {
                  var6 = ((Number)var1).intValue();
               } catch (ClassCastException var11) {
                  throw new WrongType(var11, "string-ref", 2, var1);
               }

               if(characters.isChar$Eq(Char.make(strings.stringRef(var13, var6)), Lit6)) {
                  break;
               }
            }

            CharSequence var5;
            try {
               var5 = (CharSequence)var0;
            } catch (ClassCastException var10) {
               throw new WrongType(var10, "string-ref", 1, var0);
            }

            try {
               var6 = ((Number)var1).intValue();
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 2, var1);
            }

            var4 = var1;
            if(characters.isChar$Eq(Char.make(strings.stringRef(var5, var6)), Lit7)) {
               var4 = AddOp.$Pl.apply2(var1, Lit8);
            }

            var1 = pregexpReadBranch(var0, var4, var2);
            var3 = lists.cons(lists.car.apply1(var1), var3);
            var1 = lists.cadr.apply1(var1);
         }

         return LList.list2(lists.cons(Lit4, pregexpReverse$Ex(var3)), var1);
      }
   }

   public static Object pregexpReadPiece(Object var0, Object var1, Object var2) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
   }

   public static Object pregexpReadPosixCharClass(Object var0, Object var1, Object var2) {
      Boolean var3 = Boolean.FALSE;
      Pair var4 = LList.list1(Lit44);

      while(Scheme.numGEq.apply2(var1, var2) == Boolean.FALSE) {
         CharSequence var5;
         try {
            var5 = (CharSequence)var0;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-ref", 1, var0);
         }

         int var6;
         try {
            var6 = ((Number)var1).intValue();
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "string-ref", 2, var1);
         }

         char var17 = strings.stringRef(var5, var6);
         if(characters.isChar$Eq(Char.make(var17), Lit9)) {
            var3 = Boolean.TRUE;
            var1 = AddOp.$Pl.apply2(var1, Lit8);
         } else {
            if(!unicode.isCharAlphabetic(Char.make(var17))) {
               if(characters.isChar$Eq(Char.make(var17), Lit44)) {
                  var2 = Scheme.numGEq.apply2(AddOp.$Pl.apply2(var1, Lit8), var2);

                  boolean var7;
                  try {
                     var7 = ((Boolean)var2).booleanValue();
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "x", -2, var2);
                  }

                  label62: {
                     if(var7) {
                        if(!var7) {
                           break label62;
                        }
                     } else {
                        CharSequence var14;
                        try {
                           var14 = (CharSequence)var0;
                        } catch (ClassCastException var10) {
                           throw new WrongType(var10, "string-ref", 1, var0);
                        }

                        var0 = AddOp.$Pl.apply2(var1, Lit8);

                        try {
                           var6 = ((Number)var0).intValue();
                        } catch (ClassCastException var9) {
                           throw new WrongType(var9, "string-ref", 2, var0);
                        }

                        if(characters.isChar$Eq(Char.make(strings.stringRef(var14, var6)), Lit46)) {
                           break label62;
                        }
                     }

                     return pregexpError$V(new Object[]{Lit45});
                  }

                  var0 = pregexpReverse$Ex(var4);

                  LList var15;
                  try {
                     var15 = (LList)var0;
                  } catch (ClassCastException var8) {
                     throw new WrongType(var8, "list->string", 1, var0);
                  }

                  SimpleSymbol var16 = misc.string$To$Symbol(strings.list$To$String(var15));
                  var0 = var16;
                  if(var3 != Boolean.FALSE) {
                     var0 = LList.list2(Lit17, var16);
                  }

                  return LList.list2(var0, AddOp.$Pl.apply2(var1, Lit16));
               }

               return pregexpError$V(new Object[]{Lit45});
            }

            var1 = AddOp.$Pl.apply2(var1, Lit8);
            var4 = lists.cons(Char.make(var17), var4);
         }
      }

      return pregexpError$V(new Object[]{Lit45});
   }

   public static Object pregexpReadSubpattern(Object var0, Object var1, Object var2) {
      Object var4 = $Stpregexp$Mnspace$Mnsensitive$Qu$St;
      var1 = pregexpReadClusterType(var0, var1, var2);
      Object var3 = lists.car.apply1(var1);
      Object var5 = pregexpReadPattern(var0, lists.cadr.apply1(var1), var2);
      $Stpregexp$Mnspace$Mnsensitive$Qu$St = var4;
      var1 = lists.car.apply1(var5);
      var4 = lists.cadr.apply1(var5);
      var2 = Scheme.numLss.apply2(var4, var2);

      boolean var7;
      try {
         var7 = ((Boolean)var2).booleanValue();
      } catch (ClassCastException var10) {
         throw new WrongType(var10, "x", -2, var2);
      }

      label39: {
         if(var7) {
            CharSequence var11;
            try {
               var11 = (CharSequence)var0;
            } catch (ClassCastException var9) {
               throw new WrongType(var9, "string-ref", 1, var0);
            }

            int var6;
            try {
               var6 = ((Number)var4).intValue();
            } catch (ClassCastException var8) {
               throw new WrongType(var8, "string-ref", 2, var4);
            }

            if(characters.isChar$Eq(Char.make(strings.stringRef(var11, var6)), Lit6)) {
               var0 = var3;
               break label39;
            }
         } else {
            var0 = var3;
            if(var7) {
               break label39;
            }
         }

         return pregexpError$V(new Object[]{Lit64});
      }

      while(!lists.isNull(var0)) {
         var2 = lists.cdr.apply1(var0);
         var1 = LList.list2(lists.car.apply1(var0), var1);
         var0 = var2;
      }

      return LList.list2(var1, AddOp.$Pl.apply2(var4, Lit8));
   }

   public static Object pregexpReplace(Object var0, Object var1, Object var2) {
      CharSequence var3;
      try {
         var3 = (CharSequence)var1;
      } catch (ClassCastException var14) {
         throw new WrongType(var14, "string-length", 1, var1);
      }

      int var6 = strings.stringLength(var3);
      Object var16 = pregexpMatchPositions$V(var0, var1, new Object[]{Lit73, Integer.valueOf(var6)});
      if(var16 == Boolean.FALSE) {
         return var1;
      } else {
         CharSequence var15;
         try {
            var15 = (CharSequence)var2;
         } catch (ClassCastException var13) {
            throw new WrongType(var13, "string-length", 1, var2);
         }

         int var7 = strings.stringLength(var15);
         Object var4 = lists.caar.apply1(var16);
         var0 = lists.cdar.apply1(var16);

         CharSequence var5;
         try {
            var5 = (CharSequence)var1;
         } catch (ClassCastException var12) {
            throw new WrongType(var12, "substring", 1, var1);
         }

         int var8;
         try {
            var8 = ((Number)var4).intValue();
         } catch (ClassCastException var11) {
            throw new WrongType(var11, "substring", 3, var4);
         }

         CharSequence var17 = strings.substring(var5, 0, var8);
         var2 = pregexpReplaceAux(var1, var2, Integer.valueOf(var7), var16);

         try {
            var3 = (CharSequence)var1;
         } catch (ClassCastException var10) {
            throw new WrongType(var10, "substring", 1, var1);
         }

         try {
            var7 = ((Number)var0).intValue();
         } catch (ClassCastException var9) {
            throw new WrongType(var9, "substring", 2, var0);
         }

         return strings.stringAppend(new Object[]{var17, var2, strings.substring(var3, var7, var6)});
      }
   }

   public static Object pregexpReplace$St(Object var0, Object var1, Object var2) {
      Object var3 = var0;
      if(strings.isString(var0)) {
         var3 = pregexp(var0);
      }

      CharSequence var19;
      try {
         var19 = (CharSequence)var1;
      } catch (ClassCastException var18) {
         throw new WrongType(var18, "string-length", 1, var1);
      }

      int var8 = strings.stringLength(var19);

      try {
         var19 = (CharSequence)var2;
      } catch (ClassCastException var17) {
         throw new WrongType(var17, "string-length", 1, var2);
      }

      int var9 = strings.stringLength(var19);
      var0 = Lit73;

      Object var4;
      Object var5;
      for(var4 = ""; Scheme.numGEq.apply2(var0, Integer.valueOf(var8)) == Boolean.FALSE; var0 = var5) {
         Object var6 = pregexpMatchPositions$V(var3, var1, new Object[]{var0, Integer.valueOf(var8)});
         if(var6 == Boolean.FALSE) {
            if(Scheme.numEqu.apply2(var0, Lit73) == Boolean.FALSE) {
               CharSequence var20;
               try {
                  var20 = (CharSequence)var1;
               } catch (ClassCastException var13) {
                  throw new WrongType(var13, "substring", 1, var1);
               }

               try {
                  var9 = ((Number)var0).intValue();
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "substring", 2, var0);
               }

               var1 = strings.stringAppend(new Object[]{var4, strings.substring(var20, var9, var8)});
            }

            return var1;
         }

         var5 = lists.cdar.apply1(var6);

         CharSequence var7;
         try {
            var7 = (CharSequence)var1;
         } catch (ClassCastException var16) {
            throw new WrongType(var16, "substring", 1, var1);
         }

         int var10;
         try {
            var10 = ((Number)var0).intValue();
         } catch (ClassCastException var15) {
            throw new WrongType(var15, "substring", 2, var0);
         }

         var0 = lists.caar.apply1(var6);

         int var11;
         try {
            var11 = ((Number)var0).intValue();
         } catch (ClassCastException var14) {
            throw new WrongType(var14, "substring", 3, var0);
         }

         var4 = strings.stringAppend(new Object[]{var4, strings.substring(var7, var10, var11), pregexpReplaceAux(var1, var2, Integer.valueOf(var9), var6)});
      }

      return var4;
   }

   public static Object pregexpReplaceAux(Object var0, Object var1, Object var2, Object var3) {
      Object var5 = Lit73;
      Object var4 = "";

      while(Scheme.numGEq.apply2(var5, var2) == Boolean.FALSE) {
         CharSequence var6;
         try {
            var6 = (CharSequence)var1;
         } catch (ClassCastException var19) {
            throw new WrongType(var19, "string-ref", 1, var1);
         }

         int var9;
         try {
            var9 = ((Number)var5).intValue();
         } catch (ClassCastException var18) {
            throw new WrongType(var18, "string-ref", 2, var5);
         }

         char var22 = strings.stringRef(var6, var9);
         if(characters.isChar$Eq(Char.make(var22), Lit19)) {
            Object var7 = pregexpReadEscapedNumber(var1, var5, var2);
            Object var20;
            if(var7 != Boolean.FALSE) {
               var20 = lists.car.apply1(var7);
            } else {
               CharSequence var8;
               try {
                  var8 = (CharSequence)var1;
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "string-ref", 1, var1);
               }

               var20 = AddOp.$Pl.apply2(var5, Lit8);

               try {
                  var9 = ((Number)var20).intValue();
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "string-ref", 2, var20);
               }

               if(characters.isChar$Eq(Char.make(strings.stringRef(var8, var9)), Lit113)) {
                  var20 = Lit73;
               } else {
                  var20 = Boolean.FALSE;
               }
            }

            if(var7 != Boolean.FALSE) {
               var5 = lists.cadr.apply1(var7);
            } else if(var20 != Boolean.FALSE) {
               var5 = AddOp.$Pl.apply2(var5, Lit16);
            } else {
               var5 = AddOp.$Pl.apply2(var5, Lit8);
            }

            if(var20 == Boolean.FALSE) {
               try {
                  var6 = (CharSequence)var1;
               } catch (ClassCastException var15) {
                  throw new WrongType(var15, "string-ref", 1, var1);
               }

               try {
                  var9 = ((Number)var5).intValue();
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "string-ref", 2, var5);
               }

               var22 = strings.stringRef(var6, var9);
               var5 = AddOp.$Pl.apply2(var5, Lit8);
               if(!characters.isChar$Eq(Char.make(var22), Lit11)) {
                  var4 = strings.stringAppend(new Object[]{var4, strings.$make$string$(new Object[]{Char.make(var22)})});
               }
            } else {
               var7 = pregexpListRef(var3, var20);
               var20 = var4;
               if(var7 != Boolean.FALSE) {
                  try {
                     var6 = (CharSequence)var0;
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "substring", 1, var0);
                  }

                  Object var21 = lists.car.apply1(var7);

                  try {
                     var9 = ((Number)var21).intValue();
                  } catch (ClassCastException var12) {
                     throw new WrongType(var12, "substring", 2, var21);
                  }

                  var7 = lists.cdr.apply1(var7);

                  int var10;
                  try {
                     var10 = ((Number)var7).intValue();
                  } catch (ClassCastException var11) {
                     throw new WrongType(var11, "substring", 3, var7);
                  }

                  var20 = strings.stringAppend(new Object[]{var4, strings.substring(var6, var9, var10)});
               }

               var4 = var20;
            }
         } else {
            var5 = AddOp.$Pl.apply2(var5, Lit8);
            var4 = strings.stringAppend(new Object[]{var4, strings.$make$string$(new Object[]{Char.make(var22)})});
         }
      }

      return var4;
   }

   public static Object pregexpReverse$Ex(Object var0) {
      Object var1;
      Object var2;
      for(var1 = LList.Empty; !lists.isNull(var0); var0 = var2) {
         var2 = lists.cdr.apply1(var0);

         Pair var3;
         try {
            var3 = (Pair)var0;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "set-cdr!", 1, var0);
         }

         lists.setCdr$Ex(var3, var1);
         var1 = var0;
      }

      return var1;
   }

   public static Object pregexpSplit(Object var0, Object var1) {
      CharSequence var2;
      try {
         var2 = (CharSequence)var1;
      } catch (ClassCastException var21) {
         throw new WrongType(var21, "string-length", 1, var1);
      }

      int var8 = strings.stringLength(var2);
      Object var22 = Lit73;
      Object var4 = LList.Empty;
      Boolean var3 = Boolean.FALSE;

      while(Scheme.numGEq.apply2(var22, Integer.valueOf(var8)) == Boolean.FALSE) {
         Object var5 = pregexpMatchPositions$V(var0, var1, new Object[]{var22, Integer.valueOf(var8)});
         int var9;
         CharSequence var23;
         if(var5 != Boolean.FALSE) {
            var5 = lists.car.apply1(var5);
            Object var6 = lists.car.apply1(var5);
            var5 = lists.cdr.apply1(var5);
            int var10;
            if(Scheme.numEqu.apply2(var6, var5) != Boolean.FALSE) {
               Object var24 = AddOp.$Pl.apply2(var5, Lit8);

               CharSequence var25;
               try {
                  var25 = (CharSequence)var1;
               } catch (ClassCastException var14) {
                  throw new WrongType(var14, "substring", 1, var1);
               }

               try {
                  var9 = ((Number)var22).intValue();
               } catch (ClassCastException var13) {
                  throw new WrongType(var13, "substring", 2, var22);
               }

               var22 = AddOp.$Pl.apply2(var6, Lit8);

               try {
                  var10 = ((Number)var22).intValue();
               } catch (ClassCastException var12) {
                  throw new WrongType(var12, "substring", 3, var22);
               }

               var4 = lists.cons(strings.substring(var25, var9, var10), var4);
               Boolean var26 = Boolean.TRUE;
               var22 = var24;
               var3 = var26;
            } else {
               Object var7 = Scheme.numEqu.apply2(var6, var22);

               boolean var11;
               try {
                  var11 = ((Boolean)var7).booleanValue();
               } catch (ClassCastException var20) {
                  throw new WrongType(var20, "x", -2, var7);
               }

               label79: {
                  if(var11) {
                     if(var3 == Boolean.FALSE) {
                        break label79;
                     }
                  } else if(!var11) {
                     break label79;
                  }

                  var3 = Boolean.FALSE;
                  var22 = var5;
                  continue;
               }

               try {
                  var23 = (CharSequence)var1;
               } catch (ClassCastException var19) {
                  throw new WrongType(var19, "substring", 1, var1);
               }

               try {
                  var9 = ((Number)var22).intValue();
               } catch (ClassCastException var18) {
                  throw new WrongType(var18, "substring", 2, var22);
               }

               try {
                  var10 = ((Number)var6).intValue();
               } catch (ClassCastException var17) {
                  throw new WrongType(var17, "substring", 3, var6);
               }

               var4 = lists.cons(strings.substring(var23, var9, var10), var4);
               var3 = Boolean.FALSE;
               var22 = var5;
            }
         } else {
            try {
               var23 = (CharSequence)var1;
            } catch (ClassCastException var16) {
               throw new WrongType(var16, "substring", 1, var1);
            }

            try {
               var9 = ((Number)var22).intValue();
            } catch (ClassCastException var15) {
               throw new WrongType(var15, "substring", 2, var22);
            }

            var4 = lists.cons(strings.substring(var23, var9, var8), var4);
            var3 = Boolean.FALSE;
            var22 = Integer.valueOf(var8);
         }
      }

      return pregexpReverse$Ex(var4);
   }

   public static Object pregexpStringMatch(Object var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      CharSequence var6;
      try {
         var6 = (CharSequence)var0;
      } catch (ClassCastException var15) {
         throw new WrongType(var15, "string-length", 1, var0);
      }

      int var9 = strings.stringLength(var6);
      if(Scheme.numGrt.apply2(Integer.valueOf(var9), var3) != Boolean.FALSE) {
         return Scheme.applyToArgs.apply1(var5);
      } else {
         for(Object var16 = Lit73; Scheme.numGEq.apply2(var16, Integer.valueOf(var9)) == Boolean.FALSE; var2 = AddOp.$Pl.apply2(var2, Lit8)) {
            if(Scheme.numGEq.apply2(var2, var3) != Boolean.FALSE) {
               return Scheme.applyToArgs.apply1(var5);
            }

            CharSequence var7;
            try {
               var7 = (CharSequence)var0;
            } catch (ClassCastException var14) {
               throw new WrongType(var14, "string-ref", 1, var0);
            }

            int var10;
            try {
               var10 = ((Number)var16).intValue();
            } catch (ClassCastException var13) {
               throw new WrongType(var13, "string-ref", 2, var16);
            }

            Char var17 = Char.make(strings.stringRef(var7, var10));

            CharSequence var8;
            try {
               var8 = (CharSequence)var1;
            } catch (ClassCastException var12) {
               throw new WrongType(var12, "string-ref", 1, var1);
            }

            try {
               var10 = ((Number)var2).intValue();
            } catch (ClassCastException var11) {
               throw new WrongType(var11, "string-ref", 2, var2);
            }

            if(!characters.isChar$Eq(var17, Char.make(strings.stringRef(var8, var10)))) {
               return Scheme.applyToArgs.apply1(var5);
            }

            var16 = AddOp.$Pl.apply2(var16, Lit8);
         }

         return Scheme.applyToArgs.apply2(var4, var2);
      }
   }

   public static Object pregexpWrapQuantifierIfAny(Object var0, Object var1, Object var2) {
      Object var4 = lists.car.apply1(var0);
      Object var3 = lists.cadr.apply1(var0);

      while(true) {
         if(Scheme.numGEq.apply2(var3, var2) == Boolean.FALSE) {
            CharSequence var5;
            try {
               var5 = (CharSequence)var1;
            } catch (ClassCastException var26) {
               throw new WrongType(var26, "string-ref", 1, var1);
            }

            int var7;
            try {
               var7 = ((Number)var3).intValue();
            } catch (ClassCastException var25) {
               throw new WrongType(var25, "string-ref", 2, var3);
            }

            char var32;
            boolean var8;
            label196: {
               var32 = strings.stringRef(var5, var7);
               var8 = unicode.isCharWhitespace(Char.make(var32));
               if(var8) {
                  if($Stpregexp$Mnspace$Mnsensitive$Qu$St != Boolean.FALSE) {
                     break label196;
                  }
               } else if(!var8) {
                  break label196;
               }

               var3 = AddOp.$Pl.apply2(var3, Lit8);
               continue;
            }

            Object var30 = Scheme.isEqv.apply2(Char.make(var32), Lit65);
            if(var30 != Boolean.FALSE) {
               if(var30 == Boolean.FALSE) {
                  return var0;
               }
            } else {
               var30 = Scheme.isEqv.apply2(Char.make(var32), Lit66);
               if(var30 != Boolean.FALSE) {
                  if(var30 == Boolean.FALSE) {
                     return var0;
                  }
               } else {
                  var30 = Scheme.isEqv.apply2(Char.make(var32), Lit47);
                  if(var30 != Boolean.FALSE) {
                     if(var30 == Boolean.FALSE) {
                        return var0;
                     }
                  } else if(Scheme.isEqv.apply2(Char.make(var32), Lit67) == Boolean.FALSE) {
                     return var0;
                  }
               }
            }

            Pair var31 = LList.list1(Lit68);
            LList.chain4(var31, Lit69, Lit70, Lit71, var4);
            Pair var28 = LList.list2(var31, Lit72);
            Pair var6;
            if(Scheme.isEqv.apply2(Char.make(var32), Lit65) != Boolean.FALSE) {
               var0 = lists.cddr.apply1(var31);

               try {
                  var6 = (Pair)var0;
               } catch (ClassCastException var24) {
                  throw new WrongType(var24, "set-car!", 1, var0);
               }

               lists.setCar$Ex(var6, Lit73);
               var0 = lists.cdddr.apply1(var31);

               try {
                  var6 = (Pair)var0;
               } catch (ClassCastException var23) {
                  throw new WrongType(var23, "set-car!", 1, var0);
               }

               lists.setCar$Ex(var6, Boolean.FALSE);
               var0 = var3;
            } else if(Scheme.isEqv.apply2(Char.make(var32), Lit66) != Boolean.FALSE) {
               var0 = lists.cddr.apply1(var31);

               try {
                  var6 = (Pair)var0;
               } catch (ClassCastException var22) {
                  throw new WrongType(var22, "set-car!", 1, var0);
               }

               lists.setCar$Ex(var6, Lit8);
               var0 = lists.cdddr.apply1(var31);

               try {
                  var6 = (Pair)var0;
               } catch (ClassCastException var21) {
                  throw new WrongType(var21, "set-car!", 1, var0);
               }

               lists.setCar$Ex(var6, Boolean.FALSE);
               var0 = var3;
            } else if(Scheme.isEqv.apply2(Char.make(var32), Lit47) != Boolean.FALSE) {
               var0 = lists.cddr.apply1(var31);

               try {
                  var6 = (Pair)var0;
               } catch (ClassCastException var20) {
                  throw new WrongType(var20, "set-car!", 1, var0);
               }

               lists.setCar$Ex(var6, Lit73);
               var0 = lists.cdddr.apply1(var31);

               try {
                  var6 = (Pair)var0;
               } catch (ClassCastException var19) {
                  throw new WrongType(var19, "set-car!", 1, var0);
               }

               lists.setCar$Ex(var6, Lit8);
               var0 = var3;
            } else {
               var0 = var3;
               if(Scheme.isEqv.apply2(Char.make(var32), Lit67) != Boolean.FALSE) {
                  var0 = pregexpReadNums(var1, AddOp.$Pl.apply2(var3, Lit8), var2);
                  if(var0 == Boolean.FALSE) {
                     pregexpError$V(new Object[]{Lit74, Lit75});
                  }

                  var3 = lists.cddr.apply1(var31);

                  try {
                     var6 = (Pair)var3;
                  } catch (ClassCastException var18) {
                     throw new WrongType(var18, "set-car!", 1, var3);
                  }

                  lists.setCar$Ex(var6, lists.car.apply1(var0));
                  var3 = lists.cdddr.apply1(var31);

                  try {
                     var6 = (Pair)var3;
                  } catch (ClassCastException var17) {
                     throw new WrongType(var17, "set-car!", 1, var3);
                  }

                  lists.setCar$Ex(var6, lists.cadr.apply1(var0));
                  var0 = lists.caddr.apply1(var0);
               }
            }

            var0 = AddOp.$Pl.apply2(var0, Lit8);

            while(true) {
               Pair var27;
               if(Scheme.numGEq.apply2(var0, var2) != Boolean.FALSE) {
                  var1 = lists.cdr.apply1(var31);

                  try {
                     var27 = (Pair)var1;
                  } catch (ClassCastException var14) {
                     throw new WrongType(var14, "set-car!", 1, var1);
                  }

                  lists.setCar$Ex(var27, Boolean.FALSE);
                  var1 = lists.cdr.apply1(var28);

                  try {
                     var27 = (Pair)var1;
                  } catch (ClassCastException var13) {
                     throw new WrongType(var13, "set-car!", 1, var1);
                  }

                  lists.setCar$Ex(var27, var0);
                  break;
               }

               CharSequence var29;
               try {
                  var29 = (CharSequence)var1;
               } catch (ClassCastException var16) {
                  throw new WrongType(var16, "string-ref", 1, var1);
               }

               try {
                  var7 = ((Number)var0).intValue();
               } catch (ClassCastException var15) {
                  throw new WrongType(var15, "string-ref", 2, var0);
               }

               label173: {
                  var32 = strings.stringRef(var29, var7);
                  var8 = unicode.isCharWhitespace(Char.make(var32));
                  if(var8) {
                     if($Stpregexp$Mnspace$Mnsensitive$Qu$St == Boolean.FALSE) {
                        break label173;
                     }
                  } else if(var8) {
                     break label173;
                  }

                  if(characters.isChar$Eq(Char.make(var32), Lit47)) {
                     var1 = lists.cdr.apply1(var31);

                     try {
                        var27 = (Pair)var1;
                     } catch (ClassCastException var12) {
                        throw new WrongType(var12, "set-car!", 1, var1);
                     }

                     lists.setCar$Ex(var27, Boolean.TRUE);
                     var1 = lists.cdr.apply1(var28);

                     try {
                        var27 = (Pair)var1;
                     } catch (ClassCastException var11) {
                        throw new WrongType(var11, "set-car!", 1, var1);
                     }

                     lists.setCar$Ex(var27, AddOp.$Pl.apply2(var0, Lit8));
                  } else {
                     var1 = lists.cdr.apply1(var31);

                     try {
                        var27 = (Pair)var1;
                     } catch (ClassCastException var10) {
                        throw new WrongType(var10, "set-car!", 1, var1);
                     }

                     lists.setCar$Ex(var27, Boolean.FALSE);
                     var1 = lists.cdr.apply1(var28);

                     try {
                        var27 = (Pair)var1;
                     } catch (ClassCastException var9) {
                        throw new WrongType(var9, "set-car!", 1, var1);
                     }

                     lists.setCar$Ex(var27, var0);
                  }
                  break;
               }

               var0 = AddOp.$Pl.apply2(var0, Lit8);
            }

            return var28;
         }

         return var0;
      }
   }

   public Object apply0(ModuleMethod var1) {
      switch(var1.selector) {
      case 36:
         return pregexp.frame.lambda4();
      case 37:
         return pregexp.frame0.lambda13();
      case 38:
         return pregexp.frame0.lambda14();
      case 39:
         return pregexp.frame0.lambda15();
      case 40:
         return pregexp.frame0.lambda16();
      case 41:
         return pregexp.frame0.lambda17();
      default:
         return super.apply0(var1);
      }
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      switch(var1.selector) {
      case 16:
         return pregexpReverse$Ex(var2);
      case 28:
         return pregexpInvertCharList(var2);
      case 31:
         if(isPregexpCharWord(var2)) {
            return Boolean.TRUE;
         }

         return Boolean.FALSE;
      case 35:
         return pregexpMakeBackrefList(var2);
      case 44:
         return pregexp(var2);
      case 50:
         return pregexpQuote(var2);
      default:
         return super.apply1(var1, var2);
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      switch(var1.selector) {
      case 33:
         return isPregexpCheckIfInCharClass(var2, var3);
      case 34:
         return pregexpListRef(var2, var3);
      case 47:
         return pregexpSplit(var2, var3);
      default:
         return super.apply2(var1, var2, var3);
      }
   }

   public Object apply3(ModuleMethod var1, Object var2, Object var3, Object var4) {
      switch(var1.selector) {
      case 18:
         return pregexpReadPattern(var2, var3, var4);
      case 19:
         return pregexpReadBranch(var2, var3, var4);
      case 20:
         return pregexpReadPiece(var2, var3, var4);
      case 21:
         return pregexpReadEscapedNumber(var2, var3, var4);
      case 22:
         return pregexpReadEscapedChar(var2, var3, var4);
      case 23:
         return pregexpReadPosixCharClass(var2, var3, var4);
      case 24:
         return pregexpReadClusterType(var2, var3, var4);
      case 25:
         return pregexpReadSubpattern(var2, var3, var4);
      case 26:
         return pregexpWrapQuantifierIfAny(var2, var3, var4);
      case 27:
         return pregexpReadNums(var2, var3, var4);
      case 28:
      case 30:
      case 31:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      default:
         return super.apply3(var1, var2, var3, var4);
      case 29:
         return pregexpReadCharList(var2, var3, var4);
      case 32:
         return isPregexpAtWordBoundary(var2, var3, var4);
      case 48:
         return pregexpReplace(var2, var3, var4);
      case 49:
         return pregexpReplace$St(var2, var3, var4);
      }
   }

   public Object apply4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5) {
      return var1.selector == 43?pregexpReplaceAux(var2, var3, var4, var5):super.apply4(var1, var2, var3, var4, var5);
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      Object var3;
      Object[] var4;
      int var5;
      Object var6;
      switch(var1.selector) {
      case 17:
         return pregexpError$V(var2);
      case 30:
         return pregexpStringMatch(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 42:
         return pregexpMatchPositionsAux(var2[0], var2[1], var2[2], var2[3], var2[4], var2[5]);
      case 45:
         var6 = var2[0];
         var3 = var2[1];
         var5 = var2.length - 2;
         var4 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return pregexpMatchPositions$V(var6, var3, var4);
            }

            var4[var5] = var2[var5 + 2];
         }
      case 46:
         var6 = var2[0];
         var3 = var2[1];
         var5 = var2.length - 2;
         var4 = new Object[var5];

         while(true) {
            --var5;
            if(var5 < 0) {
               return pregexpMatch$V(var6, var3, var4);
            }

            var4[var5] = var2[var5 + 2];
         }
      default:
         return super.applyN(var1, var2);
      }
   }

   public int match0(ModuleMethod var1, CallContext var2) {
      switch(var1.selector) {
      case 36:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 37:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 38:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 39:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 40:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      case 41:
         var2.proc = var1;
         var2.pc = 0;
         return 0;
      default:
         return super.match0(var1, var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      switch(var1.selector) {
      case 16:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 28:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 31:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 35:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 44:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 50:
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
      case 33:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 34:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      case 47:
         var4.value1 = var2;
         var4.value2 = var3;
         var4.proc = var1;
         var4.pc = 2;
         return 0;
      default:
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int match3(ModuleMethod var1, Object var2, Object var3, Object var4, CallContext var5) {
      switch(var1.selector) {
      case 18:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 19:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 20:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 21:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 22:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 23:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 24:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 25:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 26:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 27:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 28:
      case 30:
      case 31:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      default:
         return super.match3(var1, var2, var3, var4, var5);
      case 29:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 32:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 48:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      case 49:
         var5.value1 = var2;
         var5.value2 = var3;
         var5.value3 = var4;
         var5.proc = var1;
         var5.pc = 3;
         return 0;
      }
   }

   public int match4(ModuleMethod var1, Object var2, Object var3, Object var4, Object var5, CallContext var6) {
      if(var1.selector == 43) {
         var6.value1 = var2;
         var6.value2 = var3;
         var6.value3 = var4;
         var6.value4 = var5;
         var6.proc = var1;
         var6.pc = 4;
         return 0;
      } else {
         return super.match4(var1, var2, var3, var4, var5, var6);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 17:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 30:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 42:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 45:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 46:
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
      $Stpregexp$Mnversion$St = Lit0;
      $Stpregexp$Mncomment$Mnchar$St = Lit1;
      $Stpregexp$Mnnul$Mnchar$Mnint$St = Integer.valueOf(characters.char$To$Integer(Lit2) - 97);
      $Stpregexp$Mnreturn$Mnchar$St = characters.integer$To$Char(((Number)$Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 13);
      $Stpregexp$Mntab$Mnchar$St = characters.integer$To$Char(((Number)$Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 9);
      $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
   }

   public class frame extends ModuleBody {

      Object backrefs;
      Object case$Mnsensitive$Qu;
      Procedure identity;
      Object n;
      Object s;
      Object sn;
      Object start;


      public frame() {
         ModuleMethod var1 = new ModuleMethod(this, 15, pregexp.Lit112, 4097);
         var1.setProperty("source-location", "pregexp.scm:460");
         this.identity = var1;
      }

      public static Object lambda2identity(Object var0) {
         return var0;
      }

      static Boolean lambda4() {
         return Boolean.FALSE;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 15?lambda2identity(var2):super.apply1(var1, var2);
      }

      public Object lambda3sub(Object var1, Object var2, Object var3, Object var4) {
         throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:632)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:823)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:846)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)\r\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)\r\n\tat java.lang.reflect.Method.invoke(Unknown Source)\r\n\tat com.exe4j.runtime.LauncherEngine.launch(Unknown Source)\r\n\tat com.exe4j.runtime.WinLauncher.main(Unknown Source)\r\n");
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 15) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame0 extends ModuleBody {

      boolean could$Mnloop$Mninfinitely$Qu;
      Object fk;
      Object i;
      final ModuleMethod lambda$Fn11;
      final ModuleMethod lambda$Fn12;
      final ModuleMethod lambda$Fn2;
      final ModuleMethod lambda$Fn3;
      final ModuleMethod lambda$Fn4;
      final ModuleMethod lambda$Fn5;
      boolean maximal$Qu;
      Object old;
      Object p;
      Object q;
      Object re;
      Object re$1;
      Object sk;
      pregexp.frame staticLink;


      public frame0() {
         ModuleMethod var1 = new ModuleMethod(this, 9, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:513");
         this.lambda$Fn2 = var1;
         var1 = new ModuleMethod(this, 10, (Object)null, 0);
         var1.setProperty("source-location", "pregexp.scm:514");
         this.lambda$Fn3 = var1;
         var1 = new ModuleMethod(this, 11, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:541");
         this.lambda$Fn4 = var1;
         var1 = new ModuleMethod(this, 12, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:545");
         this.lambda$Fn5 = var1;
         var1 = new ModuleMethod(this, 13, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:587");
         this.lambda$Fn11 = var1;
         var1 = new ModuleMethod(this, 14, (Object)null, 0);
         var1.setProperty("source-location", "pregexp.scm:590");
         this.lambda$Fn12 = var1;
      }

      static Boolean lambda13() {
         return Boolean.FALSE;
      }

      static Boolean lambda14() {
         return Boolean.FALSE;
      }

      static Boolean lambda15() {
         return Boolean.FALSE;
      }

      static Boolean lambda16() {
         return Boolean.FALSE;
      }

      static Boolean lambda17() {
         return Boolean.FALSE;
      }

      public Object apply0(ModuleMethod var1) {
         switch(var1.selector) {
         case 10:
            return this.lambda10();
         case 14:
            return this.lambda19();
         default:
            return super.apply0(var1);
         }
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         switch(var1.selector) {
         case 9:
            return this.lambda9(var2);
         case 10:
         default:
            return super.apply1(var1, var2);
         case 11:
            return this.lambda11(var2);
         case 12:
            return this.lambda12(var2);
         case 13:
            return this.lambda18(var2);
         }
      }

      Object lambda10() {
         return Scheme.applyToArgs.apply2(this.sk, AddOp.$Pl.apply2(this.i, pregexp.Lit8));
      }

      Object lambda11(Object var1) {
         return Scheme.applyToArgs.apply2(this.sk, var1);
      }

      Object lambda12(Object var1) {
         Object var2 = lists.assv(this.re$1, this.staticLink.backrefs);

         Pair var3;
         try {
            var3 = (Pair)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "set-cdr!", 1, var2);
         }

         lists.setCdr$Ex(var3, lists.cons(this.i, var1));
         return Scheme.applyToArgs.apply2(this.sk, var1);
      }

      Object lambda18(Object var1) {
         this.staticLink.case$Mnsensitive$Qu = this.old;
         return Scheme.applyToArgs.apply2(this.sk, var1);
      }

      Object lambda19() {
         this.staticLink.case$Mnsensitive$Qu = this.old;
         return Scheme.applyToArgs.apply1(this.fk);
      }

      public Object lambda5loupOneOfChars(Object var1) {
         pregexp.frame1 var2 = new pregexp.frame1();
         var2.staticLink = this;
         var2.chars = var1;
         return lists.isNull(var2.chars)?Scheme.applyToArgs.apply1(this.fk):this.staticLink.lambda3sub(lists.car.apply1(var2.chars), this.i, this.sk, var2.lambda$Fn13);
      }

      public Object lambda6loupSeq(Object var1, Object var2) {
         pregexp.frame2 var3 = new pregexp.frame2();
         var3.staticLink = this;
         var3.res = var1;
         return lists.isNull(var3.res)?Scheme.applyToArgs.apply2(this.sk, var2):this.staticLink.lambda3sub(lists.car.apply1(var3.res), var2, var3.lambda$Fn14, this.fk);
      }

      public Object lambda7loupOr(Object var1) {
         pregexp.frame3 var2 = new pregexp.frame3();
         var2.staticLink = this;
         var2.res = var1;
         return lists.isNull(var2.res)?Scheme.applyToArgs.apply1(this.fk):this.staticLink.lambda3sub(lists.car.apply1(var2.res), this.i, var2.lambda$Fn15, var2.lambda$Fn16);
      }

      public Object lambda8loupP(Object var1, Object var2) {
         pregexp.frame4 var3 = new pregexp.frame4();
         var3.staticLink = this;
         var3.k = var1;
         var3.i = var2;
         if(Scheme.numLss.apply2(var3.k, this.p) != Boolean.FALSE) {
            return this.staticLink.lambda3sub(this.re, var3.i, var3.lambda$Fn17, this.fk);
         } else {
            if(this.q != Boolean.FALSE) {
               var1 = AddOp.$Mn.apply2(this.q, this.p);
            } else {
               var1 = this.q;
            }

            var3.q = var1;
            return var3.lambda24loupQ(pregexp.Lit73, var3.i);
         }
      }

      Object lambda9(Object var1) {
         return Scheme.applyToArgs.apply1(this.fk);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         switch(var1.selector) {
         case 10:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         case 14:
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         default:
            return super.match0(var1, var2);
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         switch(var1.selector) {
         case 9:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 10:
         default:
            return super.match1(var1, var2, var3);
         case 11:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 12:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 13:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
      }
   }

   public class frame1 extends ModuleBody {

      Object chars;
      final ModuleMethod lambda$Fn13;
      pregexp.frame0 staticLink;


      public frame1() {
         ModuleMethod var1 = new ModuleMethod(this, 1, (Object)null, 0);
         var1.setProperty("source-location", "pregexp.scm:508");
         this.lambda$Fn13 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 1?this.lambda20():super.apply0(var1);
      }

      Object lambda20() {
         return this.staticLink.lambda5loupOneOfChars(lists.cdr.apply1(this.chars));
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
   }

   public class frame2 extends ModuleBody {

      final ModuleMethod lambda$Fn14;
      Object res;
      pregexp.frame0 staticLink;


      public frame2() {
         ModuleMethod var1 = new ModuleMethod(this, 2, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:519");
         this.lambda$Fn14 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 2?this.lambda21(var2):super.apply1(var1, var2);
      }

      Object lambda21(Object var1) {
         return this.staticLink.lambda6loupSeq(lists.cdr.apply1(this.res), var1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 2) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame3 extends ModuleBody {

      final ModuleMethod lambda$Fn15;
      final ModuleMethod lambda$Fn16;
      Object res;
      pregexp.frame0 staticLink;


      public frame3() {
         ModuleMethod var1 = new ModuleMethod(this, 3, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:526");
         this.lambda$Fn15 = var1;
         var1 = new ModuleMethod(this, 4, (Object)null, 0);
         var1.setProperty("source-location", "pregexp.scm:529");
         this.lambda$Fn16 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 4?this.lambda23():super.apply0(var1);
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 3?this.lambda22(var2):super.apply1(var1, var2);
      }

      Object lambda22(Object var1) {
         var1 = Scheme.applyToArgs.apply2(this.staticLink.sk, var1);
         return var1 != Boolean.FALSE?var1:this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
      }

      Object lambda23() {
         return this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 4) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 3) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame4 extends ModuleBody {

      Object i;
      Object k;
      final ModuleMethod lambda$Fn17;
      Object q;
      pregexp.frame0 staticLink;


      public frame4() {
         ModuleMethod var1 = new ModuleMethod(this, 8, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:602");
         this.lambda$Fn17 = var1;
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         return var1.selector == 8?this.lambda25(var2):super.apply1(var1, var2);
      }

      public Object lambda24loupQ(Object var1, Object var2) {
         pregexp.frame5 var3;
         label29: {
            var3 = new pregexp.frame5();
            var3.staticLink = this;
            var3.k = var1;
            var3.i = var2;
            var3.fk = var3.fk;
            if(this.q != Boolean.FALSE) {
               if(Scheme.numGEq.apply2(var3.k, this.q) != Boolean.FALSE) {
                  break label29;
               }
            } else if(this.q != Boolean.FALSE) {
               break label29;
            }

            if(this.staticLink.maximal$Qu) {
               return this.staticLink.staticLink.lambda3sub(this.staticLink.re, var3.i, var3.lambda$Fn18, var3.fk);
            }

            var2 = var3.lambda26fk();
            var1 = var2;
            if(var2 == Boolean.FALSE) {
               return this.staticLink.staticLink.lambda3sub(this.staticLink.re, var3.i, var3.lambda$Fn19, var3.fk);
            }

            return var1;
         }

         var1 = var3.lambda26fk();
         return var1;
      }

      Object lambda25(Object var1) {
         if(this.staticLink.could$Mnloop$Mninfinitely$Qu) {
            if(Scheme.numEqu.apply2(var1, this.i) == Boolean.FALSE) {
               return this.staticLink.lambda8loupP(AddOp.$Pl.apply2(this.k, pregexp.Lit8), var1);
            }
         } else if(!this.staticLink.could$Mnloop$Mninfinitely$Qu) {
            return this.staticLink.lambda8loupP(AddOp.$Pl.apply2(this.k, pregexp.Lit8), var1);
         }

         pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
         return this.staticLink.lambda8loupP(AddOp.$Pl.apply2(this.k, pregexp.Lit8), var1);
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         if(var1.selector == 8) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         } else {
            return super.match1(var1, var2, var3);
         }
      }
   }

   public class frame5 extends ModuleBody {

      Procedure fk;
      Object i;
      Object k;
      final ModuleMethod lambda$Fn18;
      final ModuleMethod lambda$Fn19;
      pregexp.frame4 staticLink;


      public frame5() {
         ModuleMethod var1 = new ModuleMethod(this, 5, pregexp.Lit111, 0);
         var1.setProperty("source-location", "pregexp.scm:612");
         this.fk = var1;
         var1 = new ModuleMethod(this, 6, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:617");
         this.lambda$Fn18 = var1;
         var1 = new ModuleMethod(this, 7, (Object)null, 4097);
         var1.setProperty("source-location", "pregexp.scm:628");
         this.lambda$Fn19 = var1;
      }

      public Object apply0(ModuleMethod var1) {
         return var1.selector == 5?this.lambda26fk():super.apply0(var1);
      }

      public Object apply1(ModuleMethod var1, Object var2) {
         switch(var1.selector) {
         case 6:
            return this.lambda27(var2);
         case 7:
            return this.lambda28(var2);
         default:
            return super.apply1(var1, var2);
         }
      }

      public Object lambda26fk() {
         return Scheme.applyToArgs.apply2(this.staticLink.staticLink.sk, this.i);
      }

      Object lambda27(Object var1) {
         label18: {
            if(this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu) {
               if(Scheme.numEqu.apply2(var1, this.i) == Boolean.FALSE) {
                  break label18;
               }
            } else if(!this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu) {
               break label18;
            }

            pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
         }

         var1 = this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), var1);
         return var1 != Boolean.FALSE?var1:this.lambda26fk();
      }

      Object lambda28(Object var1) {
         return this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), var1);
      }

      public int match0(ModuleMethod var1, CallContext var2) {
         if(var1.selector == 5) {
            var2.proc = var1;
            var2.pc = 0;
            return 0;
         } else {
            return super.match0(var1, var2);
         }
      }

      public int match1(ModuleMethod var1, Object var2, CallContext var3) {
         switch(var1.selector) {
         case 6:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         case 7:
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         default:
            return super.match1(var1, var2, var3);
         }
      }
   }
}
