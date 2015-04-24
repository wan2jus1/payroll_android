package gnu.kawa.slib;

import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.Format;
import gnu.kawa.models.Box;
import gnu.kawa.models.Button;
import gnu.kawa.models.Column;
import gnu.kawa.models.Display;
import gnu.kawa.models.Label;
import gnu.kawa.models.Model;
import gnu.kawa.models.Row;
import gnu.kawa.models.Text;
import gnu.kawa.models.Window;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.xml.KAttr;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.misc;
import kawa.standard.Scheme;

public class gui extends ModuleBody {

   public static final gui $instance;
   public static final ModuleMethod Button;
   public static final ModuleMethod Column;
   public static final Macro Image;
   public static final ModuleMethod Label;
   static final Class Lit0;
   static final SimpleSymbol Lit1;
   static final SimpleSymbol Lit10;
   static final SyntaxRules Lit11;
   static final SimpleSymbol Lit12;
   static final SimpleSymbol Lit13;
   static final SimpleSymbol Lit14;
   static final SimpleSymbol Lit15;
   static final SimpleSymbol Lit16;
   static final SimpleSymbol Lit17;
   static final SimpleSymbol Lit18;
   static final SimpleSymbol Lit19;
   static final SimpleSymbol Lit2;
   static final SimpleSymbol Lit20;
   static final SimpleSymbol Lit21;
   static final SyntaxRules Lit22;
   static final SimpleSymbol Lit23 = (SimpleSymbol)(new SimpleSymbol("::")).readResolve();
   static final SimpleSymbol Lit24 = (SimpleSymbol)(new SimpleSymbol("<int>")).readResolve();
   static final SimpleSymbol Lit25 = (SimpleSymbol)(new SimpleSymbol("i")).readResolve();
   static final SimpleSymbol Lit26 = (SimpleSymbol)(new SimpleSymbol("num-args")).readResolve();
   static final SimpleSymbol Lit27 = (SimpleSymbol)(new SimpleSymbol("arg")).readResolve();
   static final SimpleSymbol Lit28 = (SimpleSymbol)(new SimpleSymbol("$lookup$")).readResolve();
   static final SimpleSymbol Lit29 = (SimpleSymbol)(new SimpleSymbol("quasiquote")).readResolve();
   static final SimpleSymbol Lit3;
   static final SimpleSymbol Lit30 = (SimpleSymbol)(new SimpleSymbol("primitive-array-get")).readResolve();
   static final SimpleSymbol Lit31 = (SimpleSymbol)(new SimpleSymbol("<object>")).readResolve();
   static final SimpleSymbol Lit32 = (SimpleSymbol)(new SimpleSymbol("loop")).readResolve();
   static final SimpleSymbol Lit33 = (SimpleSymbol)(new SimpleSymbol("+")).readResolve();
   static final SimpleSymbol Lit34 = (SimpleSymbol)(new SimpleSymbol("instance?")).readResolve();
   static final SimpleSymbol Lit35 = (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.xml.KAttr>")).readResolve();
   static final SimpleSymbol Lit36 = (SimpleSymbol)(new SimpleSymbol("attr")).readResolve();
   static final SimpleSymbol Lit37 = (SimpleSymbol)(new SimpleSymbol("quote")).readResolve();
   static final SimpleSymbol Lit38 = (SimpleSymbol)(new SimpleSymbol("getName")).readResolve();
   static final SimpleSymbol Lit39 = (SimpleSymbol)(new SimpleSymbol("invoke")).readResolve();
   static final SimpleSymbol Lit4;
   static final SimpleSymbol Lit40 = (SimpleSymbol)(new SimpleSymbol("name")).readResolve();
   static final SimpleSymbol Lit41 = (SimpleSymbol)(new SimpleSymbol("value")).readResolve();
   static final IntNum Lit42 = IntNum.make(1);
   static final SimpleSymbol Lit5;
   static final SyntaxRules Lit6;
   static final SimpleSymbol Lit7;
   static final SimpleSymbol Lit8;
   static final SimpleSymbol Lit9;
   public static final ModuleMethod Row;
   public static final ModuleMethod Text;
   public static final ModuleMethod Window;
   public static final ModuleMethod as$Mncolor;
   public static final ModuleMethod button;
   public static final ModuleMethod image$Mnheight;
   public static final ModuleMethod image$Mnread;
   public static final ModuleMethod image$Mnwidth;
   static final Location loc$$St$DtgetHeight;
   static final Location loc$$St$DtgetWidth;
   public static final Macro process$Mnkeywords;
   public static final Macro run$Mnapplication;
   public static final ModuleMethod set$Mncontent;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("run-application")).readResolve();
      Lit21 = var0;
      SyntaxRule var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0003", new Object[]{PairWithPosition.make(Lit28, Pair.make((SimpleSymbol)(new SimpleSymbol("gnu.kawa.models.Window")).readResolve(), Pair.make(Pair.make(Lit29, Pair.make((SimpleSymbol)(new SimpleSymbol("open")).readResolve(), LList.Empty)), LList.Empty)), "gui.scm", 749575)}, 0);
      Lit22 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      Lit20 = (SimpleSymbol)(new SimpleSymbol("Window")).readResolve();
      Lit19 = (SimpleSymbol)(new SimpleSymbol("set-content")).readResolve();
      Lit18 = (SimpleSymbol)(new SimpleSymbol("Column")).readResolve();
      Lit17 = (SimpleSymbol)(new SimpleSymbol("Row")).readResolve();
      Lit16 = (SimpleSymbol)(new SimpleSymbol("Text")).readResolve();
      Lit15 = (SimpleSymbol)(new SimpleSymbol("Label")).readResolve();
      Lit14 = (SimpleSymbol)(new SimpleSymbol("image-height")).readResolve();
      Lit13 = (SimpleSymbol)(new SimpleSymbol("image-width")).readResolve();
      Lit12 = (SimpleSymbol)(new SimpleSymbol("image-read")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("text-field")).readResolve();
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\u0003", new Object[0], 1), "\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0002", new Object[]{(SimpleSymbol)(new SimpleSymbol("make")).readResolve(), (SimpleSymbol)(new SimpleSymbol("<gnu.kawa.models.DrawImage>")).readResolve()}, 0);
      Lit11 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 1);
      Lit10 = (SimpleSymbol)(new SimpleSymbol("Image")).readResolve();
      Lit9 = (SimpleSymbol)(new SimpleSymbol("Button")).readResolve();
      Lit8 = (SimpleSymbol)(new SimpleSymbol("button")).readResolve();
      Lit7 = (SimpleSymbol)(new SimpleSymbol("as-color")).readResolve();
      var0 = (SimpleSymbol)(new SimpleSymbol("process-keywords")).readResolve();
      Lit5 = var0;
      var1 = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0091\b\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\t\u000b\u0018,\b\u0011\u0018\u0004\u0011\u00184\u0011\u0018<\b\u0011\u0018D\u0011\u0018L\b\u0011\u0018\u0004a\b\u0011\u0018T\b\u0011\u0018\\\t\u000b\u0018d\b\u0011\u0018l©\u0011\u0018ty\t\u0013\t\u0003\u0011\u0018|\b\u0011\u0018\u0084\t\u000b\u0018\u008c\u0018\u0094\u0099\u0011\u0018\u009ci\u0011\u0018¤\u0011\u0018¬\b\t\u0013\t\u0003\u0018´\u0018¼\b\u0011\u0018Ä1\t\u001b\t\u0003\u0018Ì\u0018Ô", new Object[]{(SimpleSymbol)(new SimpleSymbol("let")).readResolve(), Lit26, Lit23, Lit24, (SimpleSymbol)(new SimpleSymbol("field")).readResolve(), PairWithPosition.make(PairWithPosition.make(Lit37, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("length")).readResolve(), LList.Empty, "gui.scm", 16426), "gui.scm", 16426), LList.Empty, "gui.scm", 16425), Lit32, PairWithPosition.make(PairWithPosition.make(Lit25, PairWithPosition.make(Lit23, PairWithPosition.make(Lit24, PairWithPosition.make(IntNum.make(0), LList.Empty, "gui.scm", 20509), "gui.scm", 20503), "gui.scm", 20500), "gui.scm", 20497), LList.Empty, "gui.scm", 20496), (SimpleSymbol)(new SimpleSymbol("if")).readResolve(), PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("<")).readResolve(), PairWithPosition.make(Lit25, PairWithPosition.make(Lit26, LList.Empty, "gui.scm", 24593), "gui.scm", 24591), "gui.scm", 24588), Lit27, PairWithPosition.make(Lit30, PairWithPosition.make(Lit31, LList.Empty, "gui.scm", 28710), "gui.scm", 28689), PairWithPosition.make(Lit25, LList.Empty, "gui.scm", 28725), (SimpleSymbol)(new SimpleSymbol("cond")).readResolve(), PairWithPosition.make(Lit34, PairWithPosition.make(Lit27, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("<gnu.expr.Keyword>")).readResolve(), LList.Empty, "gui.scm", '耝'), "gui.scm", '耙'), "gui.scm", '耎'), PairWithPosition.make(PairWithPosition.make(Lit28, Pair.make((SimpleSymbol)(new SimpleSymbol("gnu.expr.Keyword")).readResolve(), Pair.make(Pair.make(Lit29, Pair.make(Lit38, LList.Empty)), LList.Empty)), "gui.scm", 'ꀊ'), PairWithPosition.make(Lit27, LList.Empty, "gui.scm", 'ꀣ'), "gui.scm", 'ꀉ'), PairWithPosition.make(Lit30, PairWithPosition.make(Lit31, LList.Empty, "gui.scm", '뀟'), "gui.scm", '뀊'), PairWithPosition.make(PairWithPosition.make(Lit33, PairWithPosition.make(Lit25, PairWithPosition.make(Lit42, LList.Empty, "gui.scm", '뀳'), "gui.scm", '뀱'), "gui.scm", '뀮'), LList.Empty, "gui.scm", '뀮'), PairWithPosition.make(PairWithPosition.make(Lit32, PairWithPosition.make(PairWithPosition.make(Lit33, PairWithPosition.make(Lit25, PairWithPosition.make(IntNum.make(2), LList.Empty, "gui.scm", '쀒'), "gui.scm", '쀐'), "gui.scm", '쀍'), LList.Empty, "gui.scm", '쀍'), "gui.scm", '쀇'), LList.Empty, "gui.scm", '쀇'), PairWithPosition.make(Lit34, PairWithPosition.make(Lit27, PairWithPosition.make(Lit35, LList.Empty, "gui.scm", '퀖'), "gui.scm", '퀒'), "gui.scm", '퀇'), (SimpleSymbol)(new SimpleSymbol("let*")).readResolve(), PairWithPosition.make(PairWithPosition.make(Lit36, PairWithPosition.make(Lit23, PairWithPosition.make(Lit35, PairWithPosition.make(Lit27, LList.Empty, "gui.scm", '\ue02c'), "gui.scm", '\ue017'), "gui.scm", '\ue014'), "gui.scm", '\ue00e'), PairWithPosition.make(PairWithPosition.make(Lit40, PairWithPosition.make(Lit23, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("<java.lang.String>")).readResolve(), PairWithPosition.make(PairWithPosition.make(Lit39, PairWithPosition.make(Lit36, PairWithPosition.make(PairWithPosition.make(Lit37, PairWithPosition.make(Lit38, LList.Empty, "gui.scm", '\uf031'), "gui.scm", '\uf031'), LList.Empty, "gui.scm", '\uf030'), "gui.scm", '\uf02b'), "gui.scm", '\uf023'), LList.Empty, "gui.scm", '\uf023'), "gui.scm", '\uf010'), "gui.scm", '\uf00d'), "gui.scm", '\uf007'), PairWithPosition.make(PairWithPosition.make(Lit41, PairWithPosition.make(PairWithPosition.make(Lit39, PairWithPosition.make(Lit36, PairWithPosition.make(PairWithPosition.make(Lit37, PairWithPosition.make((SimpleSymbol)(new SimpleSymbol("getObjectValue")).readResolve(), LList.Empty, "gui.scm", 65564), "gui.scm", 65564), LList.Empty, "gui.scm", 65563), "gui.scm", 65558), "gui.scm", 65550), LList.Empty, "gui.scm", 65550), "gui.scm", 65543), LList.Empty, "gui.scm", 65543), "gui.scm", '\uf007'), "gui.scm", '\ue00d'), PairWithPosition.make(Lit40, PairWithPosition.make(Lit41, LList.Empty, "gui.scm", 69666), "gui.scm", 69661), PairWithPosition.make(PairWithPosition.make(Lit32, PairWithPosition.make(PairWithPosition.make(Lit33, PairWithPosition.make(Lit25, PairWithPosition.make(Lit42, LList.Empty, "gui.scm", 73746), "gui.scm", 73744), "gui.scm", 73741), LList.Empty, "gui.scm", 73741), "gui.scm", 73735), LList.Empty, "gui.scm", 73735), (SimpleSymbol)(new SimpleSymbol("else")).readResolve(), PairWithPosition.make(Lit27, LList.Empty, "gui.scm", 81951), PairWithPosition.make(PairWithPosition.make(Lit32, PairWithPosition.make(PairWithPosition.make(Lit33, PairWithPosition.make(Lit25, PairWithPosition.make(Lit42, LList.Empty, "gui.scm", 86034), "gui.scm", 86032), "gui.scm", 86029), LList.Empty, "gui.scm", 86029), "gui.scm", 86023), LList.Empty, "gui.scm", 86023)}, 0);
      Lit6 = new SyntaxRules(new Object[]{var0}, new SyntaxRule[]{var1}, 4);
      Lit4 = (SimpleSymbol)(new SimpleSymbol("*.getHeight")).readResolve();
      Lit3 = (SimpleSymbol)(new SimpleSymbol("*.getWidth")).readResolve();
      Lit2 = (SimpleSymbol)(new SimpleSymbol("cell-spacing")).readResolve();
      Lit1 = (SimpleSymbol)(new SimpleSymbol("text")).readResolve();
      Lit0 = Color.class;
      $instance = new gui();
      loc$$St$DtgetWidth = ThreadLocation.getInstance(Lit3, (Object)null);
      loc$$St$DtgetHeight = ThreadLocation.getInstance(Lit4, (Object)null);
      process$Mnkeywords = Macro.make(Lit5, Lit6, $instance);
      gui var2 = $instance;
      as$Mncolor = new ModuleMethod(var2, 1, Lit7, 4097);
      button = new ModuleMethod(var2, 2, Lit8, -4096);
      Button = new ModuleMethod(var2, 3, Lit9, -4096);
      Image = Macro.make(Lit10, Lit11, $instance);
      image$Mnread = new ModuleMethod(var2, 4, Lit12, 4097);
      image$Mnwidth = new ModuleMethod(var2, 5, Lit13, 4097);
      image$Mnheight = new ModuleMethod(var2, 6, Lit14, 4097);
      Label = new ModuleMethod(var2, 7, Lit15, -4096);
      Text = new ModuleMethod(var2, 8, Lit16, -4096);
      Row = new ModuleMethod(var2, 9, Lit17, -4096);
      Column = new ModuleMethod(var2, 10, Lit18, -4096);
      set$Mncontent = new ModuleMethod(var2, 11, Lit19, 8194);
      Window = new ModuleMethod(var2, 12, Lit20, -4096);
      run$Mnapplication = Macro.make(Lit21, Lit22, $instance);
      $instance.run();
   }

   public gui() {
      ModuleInfo.register(this);
   }

   public static Button Button(Object ... var0) {
      Button var2 = new Button();
      int var5 = var0.length;
      int var4 = 0;

      while(var4 < var5) {
         Object var1 = var0[var4];
         if(var1 instanceof Keyword) {
            Keyword var3;
            try {
               var3 = (Keyword)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "gnu.expr.Keyword.getName()", 1, var1);
            }

            buttonKeyword(var2, var3.getName(), var0[var4 + 1]);
            var4 += 2;
         } else if(var1 instanceof KAttr) {
            KAttr var8;
            try {
               var8 = (KAttr)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "attr", -2, var1);
            }

            buttonKeyword(var2, var8.getName(), var8.getObjectValue());
            ++var4;
         } else {
            buttonNonKeyword(var2, var1);
            ++var4;
         }
      }

      return var2;
   }

   public static Column Column(Object ... var0) {
      Column var2 = new Column();
      int var5 = var0.length;
      int var4 = 0;

      while(var4 < var5) {
         Object var1 = var0[var4];
         if(var1 instanceof Keyword) {
            Keyword var3;
            try {
               var3 = (Keyword)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "gnu.expr.Keyword.getName()", 1, var1);
            }

            boxKeyword(var2, var3.getName(), var0[var4 + 1]);
            var4 += 2;
         } else if(var1 instanceof KAttr) {
            KAttr var8;
            try {
               var8 = (KAttr)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "attr", -2, var1);
            }

            boxKeyword(var2, var8.getName(), var8.getObjectValue());
            ++var4;
         } else {
            boxNonKeyword(var2, var1);
            ++var4;
         }
      }

      return var2;
   }

   public static Label Label(Object ... var0) {
      Label var2 = new Label();
      int var5 = var0.length;
      int var4 = 0;

      while(var4 < var5) {
         Object var1 = var0[var4];
         if(var1 instanceof Keyword) {
            Keyword var3;
            try {
               var3 = (Keyword)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "gnu.expr.Keyword.getName()", 1, var1);
            }

            labelKeyword(var2, var3.getName(), var0[var4 + 1]);
            var4 += 2;
         } else if(var1 instanceof KAttr) {
            KAttr var8;
            try {
               var8 = (KAttr)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "attr", -2, var1);
            }

            labelKeyword(var2, var8.getName(), var8.getObjectValue());
            ++var4;
         } else {
            labelNonKeyword(var2, var1);
            ++var4;
         }
      }

      return var2;
   }

   public static Row Row(Object ... var0) {
      Row var2 = new Row();
      int var5 = var0.length;
      int var4 = 0;

      while(var4 < var5) {
         Object var1 = var0[var4];
         if(var1 instanceof Keyword) {
            Keyword var3;
            try {
               var3 = (Keyword)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "gnu.expr.Keyword.getName()", 1, var1);
            }

            boxKeyword(var2, var3.getName(), var0[var4 + 1]);
            var4 += 2;
         } else if(var1 instanceof KAttr) {
            KAttr var8;
            try {
               var8 = (KAttr)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "attr", -2, var1);
            }

            boxKeyword(var2, var8.getName(), var8.getObjectValue());
            ++var4;
         } else {
            boxNonKeyword(var2, var1);
            ++var4;
         }
      }

      return var2;
   }

   public static Text Text(Object ... var0) {
      Text var2 = new Text();
      int var5 = var0.length;
      int var4 = 0;

      while(var4 < var5) {
         Object var1 = var0[var4];
         if(var1 instanceof Keyword) {
            Keyword var3;
            try {
               var3 = (Keyword)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "gnu.expr.Keyword.getName()", 1, var1);
            }

            textKeyword(var2, var3.getName(), var0[var4 + 1]);
            var4 += 2;
         } else if(var1 instanceof KAttr) {
            KAttr var8;
            try {
               var8 = (KAttr)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "attr", -2, var1);
            }

            textKeyword(var2, var8.getName(), var8.getObjectValue());
            ++var4;
         } else {
            textNonKeyword(var2, var1);
            ++var4;
         }
      }

      return var2;
   }

   public static Window Window(Object ... var0) {
      Window var2 = Display.getInstance().makeWindow();
      int var5 = var0.length;
      int var4 = 0;

      while(var4 < var5) {
         Object var1 = var0[var4];
         if(var1 instanceof Keyword) {
            Keyword var3;
            try {
               var3 = (Keyword)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "gnu.expr.Keyword.getName()", 1, var1);
            }

            windowKeyword(var2, var3.getName(), var0[var4 + 1]);
            var4 += 2;
         } else if(var1 instanceof KAttr) {
            KAttr var8;
            try {
               var8 = (KAttr)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "attr", -2, var1);
            }

            windowKeyword(var2, var8.getName(), var8.getObjectValue());
            ++var4;
         } else {
            windowNonKeyword(var2, var1);
            ++var4;
         }
      }

      return var2;
   }

   public static Color asColor(Object var0) {
      if(var0 instanceof Color) {
         return (Color)var0;
      } else if(var0 instanceof Integer) {
         Integer var1;
         try {
            var1 = (Integer)var0;
         } catch (ClassCastException var2) {
            throw new WrongType(var2, "java.lang.Integer.intValue()", 1, var0);
         }

         return new Color(var1.intValue());
      } else {
         return var0 instanceof IntNum?new Color(IntNum.intValue(var0)):(Color)SlotGet.staticField.apply2(Lit0, var0.toString());
      }
   }

   static Model asModel(Object var0) {
      return Display.getInstance().coerceToModel(var0);
   }

   static Object boxKeyword(Box var0, String var1, Object var2) {
      if(var1 == Lit2) {
         var0.setCellSpacing(var2);
         return Values.empty;
      } else {
         return misc.error$V(Format.formatToString(0, new Object[]{"unknown box attribute ~s", var1}), new Object[0]);
      }
   }

   static void boxNonKeyword(Box var0, Object var1) {
      var0.add(asModel(var1));
   }

   public static Button button(Object ... var0) {
      Button var2 = new Button();
      int var5 = var0.length;
      int var4 = 0;

      while(var4 < var5) {
         Object var1 = var0[var4];
         if(var1 instanceof Keyword) {
            Keyword var3;
            try {
               var3 = (Keyword)var1;
            } catch (ClassCastException var6) {
               throw new WrongType(var6, "gnu.expr.Keyword.getName()", 1, var1);
            }

            buttonKeyword(var2, var3.getName(), var0[var4 + 1]);
            var4 += 2;
         } else if(var1 instanceof KAttr) {
            KAttr var8;
            try {
               var8 = (KAttr)var1;
            } catch (ClassCastException var7) {
               throw new WrongType(var7, "attr", -2, var1);
            }

            buttonKeyword(var2, var8.getName(), var8.getObjectValue());
            ++var4;
         } else {
            buttonNonKeyword(var2, var1);
            ++var4;
         }
      }

      return var2;
   }

   static Object buttonKeyword(Button var0, String var1, Object var2) {
      boolean var3 = true;
      if(var1 == "foreground") {
         var0.setForeground(asColor(var2));
         return Values.empty;
      } else if(var1 == "background") {
         var0.setBackground(asColor(var2));
         return Values.empty;
      } else if(var1 == "action") {
         var0.setAction(var2);
         return Values.empty;
      } else if(var1 == "text") {
         if(var2 == null) {
            var1 = null;
         } else {
            var1 = var2.toString();
         }

         var0.setText(var1);
         return Values.empty;
      } else if(var1 == "disabled") {
         Boolean var5;
         try {
            var5 = Boolean.FALSE;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "gnu.kawa.models.Button.setDisabled(boolean)", 2, var2);
         }

         if(var2 == var5) {
            var3 = false;
         }

         var0.setDisabled(var3);
         return Values.empty;
      } else {
         return misc.error$V(Format.formatToString(0, new Object[]{"unknown button attribute ~s", var1}), new Object[0]);
      }
   }

   static Boolean buttonNonKeyword(Button var0, Object var1) {
      return Boolean.TRUE;
   }

   public static int imageHeight(BufferedImage var0) {
      ApplyToArgs var1 = Scheme.applyToArgs;
      Location var2 = loc$$St$DtgetHeight;

      Object var4;
      try {
         var4 = var2.get();
      } catch (UnboundLocationException var3) {
         var3.setLine("gui.scm", 77, 3);
         throw var3;
      }

      return ((Number)var1.apply2(var4, var0)).intValue();
   }

   public static BufferedImage imageRead(Path var0) {
      return ImageIO.read(var0.openInputStream());
   }

   public static int imageWidth(BufferedImage var0) {
      ApplyToArgs var1 = Scheme.applyToArgs;
      Location var2 = loc$$St$DtgetWidth;

      Object var4;
      try {
         var4 = var2.get();
      } catch (UnboundLocationException var3) {
         var3.setLine("gui.scm", 74, 3);
         throw var3;
      }

      return ((Number)var1.apply2(var4, var0)).intValue();
   }

   static Object labelKeyword(Label var0, String var1, Object var2) {
      if(var1 == Lit1) {
         if(var2 == null) {
            var1 = null;
         } else {
            var1 = var2.toString();
         }

         var0.setText(var1);
         return Values.empty;
      } else {
         return misc.error$V(Format.formatToString(0, new Object[]{"unknown label attribute ~s", var1}), new Object[0]);
      }
   }

   static void labelNonKeyword(Label var0, Object var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      var0.setText(var2);
   }

   public static void setContent(Window var0, Object var1) {
      var0.setContent(var1);
   }

   static Object textKeyword(Text var0, String var1, Object var2) {
      if(var1 == Lit1) {
         if(var2 == null) {
            var1 = null;
         } else {
            var1 = var2.toString();
         }

         var0.setText(var1);
         return Values.empty;
      } else {
         return misc.error$V(Format.formatToString(0, new Object[]{"unknown text attribute ~s", var1}), new Object[0]);
      }
   }

   static void textNonKeyword(Text var0, Object var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      var0.setText(var2);
   }

   static Object windowKeyword(Window var0, String var1, Object var2) {
      if(var1 == "title") {
         if(var2 == null) {
            var1 = null;
         } else {
            var1 = var2.toString();
         }

         var0.setTitle(var1);
         return Values.empty;
      } else if(var1 == "content") {
         var0.setContent(var2);
         return Values.empty;
      } else if(var1 == "menubar") {
         var0.setMenuBar(var2);
         return Values.empty;
      } else {
         return misc.error$V(Format.formatToString(0, new Object[]{"unknown window attribute ~s", var1}), new Object[0]);
      }
   }

   static void windowNonKeyword(Window var0, Object var1) {
      var0.setContent(var1);
   }

   public Object apply1(ModuleMethod var1, Object var2) {
      BufferedImage var6;
      switch(var1.selector) {
      case 1:
         return asColor(var2);
      case 2:
      case 3:
      default:
         return super.apply1(var1, var2);
      case 4:
         Path var7;
         try {
            var7 = Path.valueOf(var2);
         } catch (ClassCastException var5) {
            throw new WrongType(var5, "image-read", 1, var2);
         }

         return imageRead(var7);
      case 5:
         try {
            var6 = (BufferedImage)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "image-width", 1, var2);
         }

         return Integer.valueOf(imageWidth(var6));
      case 6:
         try {
            var6 = (BufferedImage)var2;
         } catch (ClassCastException var3) {
            throw new WrongType(var3, "image-height", 1, var2);
         }

         return Integer.valueOf(imageHeight(var6));
      }
   }

   public Object apply2(ModuleMethod var1, Object var2, Object var3) {
      if(var1.selector == 11) {
         Window var5;
         try {
            var5 = (Window)var2;
         } catch (ClassCastException var4) {
            throw new WrongType(var4, "set-content", 1, var2);
         }

         setContent(var5, var3);
         return Values.empty;
      } else {
         return super.apply2(var1, var2, var3);
      }
   }

   public Object applyN(ModuleMethod var1, Object[] var2) {
      switch(var1.selector) {
      case 2:
         return button(var2);
      case 3:
         return Button(var2);
      case 4:
      case 5:
      case 6:
      case 11:
      default:
         return super.applyN(var1, var2);
      case 7:
         return Label(var2);
      case 8:
         return Text(var2);
      case 9:
         return Row(var2);
      case 10:
         return Column(var2);
      case 12:
         return Window(var2);
      }
   }

   public int match1(ModuleMethod var1, Object var2, CallContext var3) {
      int var4 = -786431;
      switch(var1.selector) {
      case 1:
         var3.value1 = var2;
         var3.proc = var1;
         var3.pc = 1;
         return 0;
      case 2:
      case 3:
      default:
         var4 = super.match1(var1, var2, var3);
         break;
      case 4:
         if(Path.coerceToPathOrNull(var2) != null) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 5:
         if(var2 instanceof BufferedImage) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
         break;
      case 6:
         if(var2 instanceof BufferedImage) {
            var3.value1 = var2;
            var3.proc = var1;
            var3.pc = 1;
            return 0;
         }
      }

      return var4;
   }

   public int match2(ModuleMethod var1, Object var2, Object var3, CallContext var4) {
      if(var1.selector == 11) {
         if(!(var2 instanceof Window)) {
            return -786431;
         } else {
            var4.value1 = var2;
            var4.value2 = var3;
            var4.proc = var1;
            var4.pc = 2;
            return 0;
         }
      } else {
         return super.match2(var1, var2, var3, var4);
      }
   }

   public int matchN(ModuleMethod var1, Object[] var2, CallContext var3) {
      switch(var1.selector) {
      case 2:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 3:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 4:
      case 5:
      case 6:
      case 11:
      default:
         return super.matchN(var1, var2, var3);
      case 7:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 8:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 9:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 10:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      case 12:
         var3.values = var2;
         var3.proc = var1;
         var3.pc = 5;
         return 0;
      }
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
   }
}
