package appinventor.ai_greatbattlewar.new_thinker;

import appinventor.ai_greatbattlewar.new_thinker.Screen1$frame;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.ReplApplication;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.runtime;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;

public class Screen1 extends Form implements Runnable {

   static final SimpleSymbol Lit0;
   static final SimpleSymbol Lit1;
   static final SimpleSymbol Lit10;
   static final SimpleSymbol Lit100;
   static final FString Lit101;
   static final PairWithPosition Lit102;
   static final PairWithPosition Lit103;
   static final PairWithPosition Lit104;
   static final SimpleSymbol Lit105;
   static final SimpleSymbol Lit106;
   static final PairWithPosition Lit107;
   static final SimpleSymbol Lit108;
   static final FString Lit109;
   static final SimpleSymbol Lit11;
   static final SimpleSymbol Lit110;
   static final FString Lit111;
   static final SimpleSymbol Lit112;
   static final FString Lit113;
   static final SimpleSymbol Lit114;
   static final SimpleSymbol Lit115;
   static final SimpleSymbol Lit116;
   static final FString Lit117;
   static final SimpleSymbol Lit118;
   static final PairWithPosition Lit119;
   static final SimpleSymbol Lit12;
   static final IntNum Lit120;
   static final PairWithPosition Lit121;
   static final PairWithPosition Lit122;
   static final IntNum Lit123;
   static final PairWithPosition Lit124;
   static final PairWithPosition Lit125;
   static final IntNum Lit126;
   static final PairWithPosition Lit127;
   static final PairWithPosition Lit128;
   static final IntNum Lit129;
   static final SimpleSymbol Lit13;
   static final PairWithPosition Lit130;
   static final PairWithPosition Lit131;
   static final IntNum Lit132;
   static final PairWithPosition Lit133;
   static final SimpleSymbol Lit134;
   static final SimpleSymbol Lit135;
   static final FString Lit136;
   static final FString Lit137;
   static final PairWithPosition Lit138;
   static final PairWithPosition Lit139;
   static final SimpleSymbol Lit14;
   static final PairWithPosition Lit140;
   static final PairWithPosition Lit141;
   static final PairWithPosition Lit142;
   static final PairWithPosition Lit143;
   static final PairWithPosition Lit144;
   static final PairWithPosition Lit145;
   static final SimpleSymbol Lit146 = (SimpleSymbol)(new SimpleSymbol("Elements")).readResolve();
   static final SimpleSymbol Lit147 = (SimpleSymbol)(new SimpleSymbol("list")).readResolve();
   static final SimpleSymbol Lit148 = (SimpleSymbol)(new SimpleSymbol("Open")).readResolve();
   static final SimpleSymbol Lit149 = (SimpleSymbol)(new SimpleSymbol("Web1$GotText")).readResolve();
   static final SimpleSymbol Lit15;
   static final SimpleSymbol Lit150 = (SimpleSymbol)(new SimpleSymbol("GotText")).readResolve();
   static final FString Lit151 = new FString("com.google.appinventor.components.runtime.Notifier");
   static final FString Lit152 = new FString("com.google.appinventor.components.runtime.Notifier");
   static final SimpleSymbol Lit153 = (SimpleSymbol)(new SimpleSymbol("android-log-form")).readResolve();
   static final SimpleSymbol Lit154 = (SimpleSymbol)(new SimpleSymbol("add-to-form-environment")).readResolve();
   static final SimpleSymbol Lit155 = (SimpleSymbol)(new SimpleSymbol("lookup-in-form-environment")).readResolve();
   static final SimpleSymbol Lit156 = (SimpleSymbol)(new SimpleSymbol("is-bound-in-form-environment")).readResolve();
   static final SimpleSymbol Lit157 = (SimpleSymbol)(new SimpleSymbol("add-to-global-var-environment")).readResolve();
   static final SimpleSymbol Lit158 = (SimpleSymbol)(new SimpleSymbol("add-to-events")).readResolve();
   static final SimpleSymbol Lit159 = (SimpleSymbol)(new SimpleSymbol("add-to-components")).readResolve();
   static final SimpleSymbol Lit16;
   static final SimpleSymbol Lit160 = (SimpleSymbol)(new SimpleSymbol("add-to-global-vars")).readResolve();
   static final SimpleSymbol Lit161 = (SimpleSymbol)(new SimpleSymbol("add-to-form-do-after-creation")).readResolve();
   static final SimpleSymbol Lit162 = (SimpleSymbol)(new SimpleSymbol("send-error")).readResolve();
   static final SimpleSymbol Lit163 = (SimpleSymbol)(new SimpleSymbol("dispatchEvent")).readResolve();
   static final SimpleSymbol Lit164 = (SimpleSymbol)(new SimpleSymbol("lookup-handler")).readResolve();
   static final SimpleSymbol Lit165 = (SimpleSymbol)(new SimpleSymbol("any")).readResolve();
   static final IntNum Lit17;
   static final SimpleSymbol Lit18;
   static final SimpleSymbol Lit19;
   static final SimpleSymbol Lit2;
   static final FString Lit20;
   static final SimpleSymbol Lit21;
   static final SimpleSymbol Lit22;
   static final IntNum Lit23;
   static final FString Lit24;
   static final FString Lit25;
   static final SimpleSymbol Lit26;
   static final SimpleSymbol Lit27;
   static final SimpleSymbol Lit28;
   static final IntNum Lit29;
   static final SimpleSymbol Lit3;
   static final FString Lit30;
   static final FString Lit31;
   static final SimpleSymbol Lit32;
   static final IntNum Lit33;
   static final FString Lit34;
   static final FString Lit35;
   static final SimpleSymbol Lit36;
   static final SimpleSymbol Lit37;
   static final FString Lit38;
   static final FString Lit39;
   static final SimpleSymbol Lit4;
   static final SimpleSymbol Lit40;
   static final FString Lit41;
   static final FString Lit42;
   static final SimpleSymbol Lit43;
   static final IntNum Lit44;
   static final FString Lit45;
   static final FString Lit46;
   static final SimpleSymbol Lit47;
   static final FString Lit48;
   static final FString Lit49;
   static final SimpleSymbol Lit5;
   static final SimpleSymbol Lit50;
   static final SimpleSymbol Lit51;
   static final IntNum Lit52;
   static final FString Lit53;
   static final FString Lit54;
   static final SimpleSymbol Lit55;
   static final SimpleSymbol Lit56;
   static final IntNum Lit57;
   static final IntNum Lit58;
   static final FString Lit59;
   static final IntNum Lit6;
   static final FString Lit60;
   static final SimpleSymbol Lit61;
   static final IntNum Lit62;
   static final FString Lit63;
   static final FString Lit64;
   static final SimpleSymbol Lit65;
   static final FString Lit66;
   static final FString Lit67;
   static final SimpleSymbol Lit68;
   static final FString Lit69;
   static final SimpleSymbol Lit7;
   static final FString Lit70;
   static final SimpleSymbol Lit71;
   static final IntNum Lit72;
   static final IntNum Lit73;
   static final FString Lit74;
   static final FString Lit75;
   static final SimpleSymbol Lit76;
   static final FString Lit77;
   static final FString Lit78;
   static final SimpleSymbol Lit79;
   static final SimpleSymbol Lit8;
   static final FString Lit80;
   static final FString Lit81;
   static final SimpleSymbol Lit82;
   static final IntNum Lit83;
   static final FString Lit84;
   static final FString Lit85;
   static final SimpleSymbol Lit86;
   static final FString Lit87;
   static final FString Lit88;
   static final SimpleSymbol Lit89;
   static final SimpleSymbol Lit9;
   static final IntNum Lit90;
   static final FString Lit91;
   static final FString Lit92;
   static final SimpleSymbol Lit93;
   static final FString Lit94;
   static final PairWithPosition Lit95;
   static final PairWithPosition Lit96;
   static final SimpleSymbol Lit97;
   static final SimpleSymbol Lit98;
   static final FString Lit99;
   public static Screen1 Screen1;
   static final ModuleMethod lambda$Fn1;
   static final ModuleMethod lambda$Fn10;
   static final ModuleMethod lambda$Fn11;
   static final ModuleMethod lambda$Fn12;
   static final ModuleMethod lambda$Fn13;
   static final ModuleMethod lambda$Fn14;
   static final ModuleMethod lambda$Fn15;
   static final ModuleMethod lambda$Fn16;
   static final ModuleMethod lambda$Fn17;
   static final ModuleMethod lambda$Fn18;
   static final ModuleMethod lambda$Fn19;
   static final ModuleMethod lambda$Fn2;
   static final ModuleMethod lambda$Fn20;
   static final ModuleMethod lambda$Fn21;
   static final ModuleMethod lambda$Fn22;
   static final ModuleMethod lambda$Fn23;
   static final ModuleMethod lambda$Fn24;
   static final ModuleMethod lambda$Fn25;
   static final ModuleMethod lambda$Fn26;
   static final ModuleMethod lambda$Fn27;
   static final ModuleMethod lambda$Fn28;
   static final ModuleMethod lambda$Fn29;
   static final ModuleMethod lambda$Fn3;
   static final ModuleMethod lambda$Fn30;
   static final ModuleMethod lambda$Fn31;
   static final ModuleMethod lambda$Fn32;
   static final ModuleMethod lambda$Fn33;
   static final ModuleMethod lambda$Fn34;
   static final ModuleMethod lambda$Fn35;
   static final ModuleMethod lambda$Fn36;
   static final ModuleMethod lambda$Fn37;
   static final ModuleMethod lambda$Fn38;
   static final ModuleMethod lambda$Fn39;
   static final ModuleMethod lambda$Fn4;
   static final ModuleMethod lambda$Fn40;
   static final ModuleMethod lambda$Fn41;
   static final ModuleMethod lambda$Fn42;
   static final ModuleMethod lambda$Fn43;
   static final ModuleMethod lambda$Fn44;
   static final ModuleMethod lambda$Fn45;
   static final ModuleMethod lambda$Fn46;
   static final ModuleMethod lambda$Fn47;
   static final ModuleMethod lambda$Fn48;
   static final ModuleMethod lambda$Fn49;
   static final ModuleMethod lambda$Fn5;
   static final ModuleMethod lambda$Fn50;
   static final ModuleMethod lambda$Fn51;
   static final ModuleMethod lambda$Fn52;
   static final ModuleMethod lambda$Fn53;
   static final ModuleMethod lambda$Fn54;
   static final ModuleMethod lambda$Fn55;
   static final ModuleMethod lambda$Fn56;
   static final ModuleMethod lambda$Fn6;
   static final ModuleMethod lambda$Fn7;
   static final ModuleMethod lambda$Fn8;
   static final ModuleMethod lambda$Fn9;
   public Boolean $Stdebug$Mnform$St;
   public final ModuleMethod $define;
   public HorizontalArrangement HorizontalArrangement1;
   public HorizontalArrangement HorizontalArrangement2;
   public HorizontalArrangement HorizontalArrangement3;
   public HorizontalArrangement HorizontalArrangement4;
   public HorizontalArrangement HorizontalArrangement5;
   public HorizontalArrangement HorizontalArrangement6;
   public Label Label1;
   public Label Label2;
   public Label Label4;
   public Label Label5;
   public Label Label6;
   public Label Label7;
   public Label Label8;
   public ListPicker ListPicker1;
   public final ModuleMethod ListPicker1$AfterPicking;
   public Notifier Notifier1;
   public Button View_Customers;
   public final ModuleMethod View_Customers$Click;
   public Web Web1;
   public final ModuleMethod Web1$GotText;
   public final ModuleMethod add$Mnto$Mncomponents;
   public final ModuleMethod add$Mnto$Mnevents;
   public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
   public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
   public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
   public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
   public Button add_emp;
   public final ModuleMethod add_emp$Click;
   public final ModuleMethod android$Mnlog$Mnform;
   public LList components$Mnto$Mncreate;
   public final ModuleMethod dispatchEvent;
   public TextBox emp_address;
   public TextBox emp_id;
   public TextBox emp_name;
   public LList events$Mnto$Mnregister;
   public LList form$Mndo$Mnafter$Mncreation;
   public Environment form$Mnenvironment;
   public Symbol form$Mnname$Mnsymbol;
   public Environment global$Mnvar$Mnenvironment;
   public LList global$Mnvars$Mnto$Mncreate;
   public TextBox hourly_rate;
   public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
   public final ModuleMethod lookup$Mnhandler;
   public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
   public TextBox member_id;
   public final ModuleMethod process$Mnexception;
   public final ModuleMethod send$Mnerror;
   public Button update_emp;
   public final ModuleMethod update_emp$Click;


   static {
      SimpleSymbol var0 = (SimpleSymbol)(new SimpleSymbol("number")).readResolve();
      Lit18 = var0;
      Lit145 = PairWithPosition.make(var0, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959570), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959562);
      Lit144 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit165, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959434), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959428);
      var0 = (SimpleSymbol)(new SimpleSymbol("text")).readResolve();
      Lit10 = var0;
      Lit143 = PairWithPosition.make(var0, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959410), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959405), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959399);
      Lit142 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959368), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959362);
      Lit141 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959244), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 959238);
      Lit140 = PairWithPosition.make(Lit18, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 958976), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 958968);
      Lit139 = PairWithPosition.make(Lit147, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 958867);
      Lit138 = PairWithPosition.make(Lit10, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 958593);
      Lit137 = new FString("com.google.appinventor.components.runtime.Web");
      Lit136 = new FString("com.google.appinventor.components.runtime.Web");
      Lit135 = (SimpleSymbol)(new SimpleSymbol("AfterPicking")).readResolve();
      Lit134 = (SimpleSymbol)(new SimpleSymbol("ListPicker1$AfterPicking")).readResolve();
      Lit133 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 931296), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 931290);
      Lit132 = IntNum.make(1);
      Lit131 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 931258), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 931252);
      Lit130 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 931002), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930996);
      Lit129 = IntNum.make(5);
      Lit128 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930964), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930958);
      Lit127 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930693), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930687);
      Lit126 = IntNum.make(4);
      Lit125 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930655), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930649);
      Lit124 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930382), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930376);
      Lit123 = IntNum.make(3);
      Lit122 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930344), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930338);
      Lit121 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930071), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930065);
      Lit120 = IntNum.make(2);
      Lit119 = PairWithPosition.make(Lit147, PairWithPosition.make(Lit18, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930033), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 930027);
      Lit118 = (SimpleSymbol)(new SimpleSymbol("SelectionIndex")).readResolve();
      Lit117 = new FString("com.google.appinventor.components.runtime.ListPicker");
      Lit116 = (SimpleSymbol)(new SimpleSymbol("boolean")).readResolve();
      Lit115 = (SimpleSymbol)(new SimpleSymbol("Visible")).readResolve();
      Lit114 = (SimpleSymbol)(new SimpleSymbol("ListPicker1")).readResolve();
      Lit113 = new FString("com.google.appinventor.components.runtime.ListPicker");
      Lit112 = (SimpleSymbol)(new SimpleSymbol("View_Customers$Click")).readResolve();
      Lit111 = new FString("com.google.appinventor.components.runtime.Button");
      Lit110 = (SimpleSymbol)(new SimpleSymbol("View_Customers")).readResolve();
      Lit109 = new FString("com.google.appinventor.components.runtime.Button");
      Lit108 = (SimpleSymbol)(new SimpleSymbol("update_emp$Click")).readResolve();
      Lit107 = PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832431), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832426), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832420);
      Lit106 = (SimpleSymbol)(new SimpleSymbol("ShowMessageDialog")).readResolve();
      Lit105 = (SimpleSymbol)(new SimpleSymbol("Notifier1")).readResolve();
      Lit104 = PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832158), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832153), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832147);
      Lit103 = PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832024), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832019), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832014), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832009), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 832004), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 831999), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 831994), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 831989), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 831984), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 831978);
      Lit102 = PairWithPosition.make(Lit165, PairWithPosition.make(Lit165, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 831591), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 831586);
      Lit101 = new FString("com.google.appinventor.components.runtime.Button");
      Lit100 = (SimpleSymbol)(new SimpleSymbol("update_emp")).readResolve();
      Lit99 = new FString("com.google.appinventor.components.runtime.Button");
      Lit98 = (SimpleSymbol)(new SimpleSymbol("Click")).readResolve();
      Lit97 = (SimpleSymbol)(new SimpleSymbol("add_emp$Click")).readResolve();
      Lit96 = PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786966), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786961), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786955);
      Lit95 = PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786832), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786827), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786822), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786817), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786812), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786807), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786802), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786797), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786792), "/tmp/1429098297625_0.24439226491623978-0/youngandroidproject/../src/appinventor/ai_greatbattlewar/new_thinker/Screen1.yail", 786786);
      Lit94 = new FString("com.google.appinventor.components.runtime.Button");
      Lit93 = (SimpleSymbol)(new SimpleSymbol("add_emp")).readResolve();
      Lit92 = new FString("com.google.appinventor.components.runtime.Button");
      Lit91 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit90 = IntNum.make(30);
      Lit89 = (SimpleSymbol)(new SimpleSymbol("HorizontalArrangement6")).readResolve();
      Lit88 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit87 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit86 = (SimpleSymbol)(new SimpleSymbol("member_id")).readResolve();
      Lit85 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit84 = new FString("com.google.appinventor.components.runtime.Label");
      int[] var1 = new int[]{-1, 0};
      Lit83 = IntNum.make(var1);
      Lit82 = (SimpleSymbol)(new SimpleSymbol("Label8")).readResolve();
      Lit81 = new FString("com.google.appinventor.components.runtime.Label");
      Lit80 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit79 = (SimpleSymbol)(new SimpleSymbol("HorizontalArrangement5")).readResolve();
      Lit78 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit77 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit76 = (SimpleSymbol)(new SimpleSymbol("hourly_rate")).readResolve();
      Lit75 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit74 = new FString("com.google.appinventor.components.runtime.Label");
      Lit73 = IntNum.make(70);
      var1 = new int[]{-1, 0};
      Lit72 = IntNum.make(var1);
      Lit71 = (SimpleSymbol)(new SimpleSymbol("Label7")).readResolve();
      Lit70 = new FString("com.google.appinventor.components.runtime.Label");
      Lit69 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit68 = (SimpleSymbol)(new SimpleSymbol("HorizontalArrangement4")).readResolve();
      Lit67 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit66 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit65 = (SimpleSymbol)(new SimpleSymbol("emp_address")).readResolve();
      Lit64 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit63 = new FString("com.google.appinventor.components.runtime.Label");
      Lit62 = IntNum.make(6);
      Lit61 = (SimpleSymbol)(new SimpleSymbol("Label5")).readResolve();
      Lit60 = new FString("com.google.appinventor.components.runtime.Label");
      Lit59 = new FString("com.google.appinventor.components.runtime.Label");
      var1 = new int[]{-1, 0};
      Lit58 = IntNum.make(var1);
      Lit57 = IntNum.make(14);
      Lit56 = (SimpleSymbol)(new SimpleSymbol("FontSize")).readResolve();
      Lit55 = (SimpleSymbol)(new SimpleSymbol("Label4")).readResolve();
      Lit54 = new FString("com.google.appinventor.components.runtime.Label");
      Lit53 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit52 = IntNum.make(80);
      Lit51 = (SimpleSymbol)(new SimpleSymbol("Height")).readResolve();
      Lit50 = (SimpleSymbol)(new SimpleSymbol("HorizontalArrangement3")).readResolve();
      Lit49 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit48 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit47 = (SimpleSymbol)(new SimpleSymbol("emp_name")).readResolve();
      Lit46 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit45 = new FString("com.google.appinventor.components.runtime.Label");
      var1 = new int[]{-1, 0};
      Lit44 = IntNum.make(var1);
      Lit43 = (SimpleSymbol)(new SimpleSymbol("Label2")).readResolve();
      Lit42 = new FString("com.google.appinventor.components.runtime.Label");
      Lit41 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit40 = (SimpleSymbol)(new SimpleSymbol("HorizontalArrangement2")).readResolve();
      Lit39 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit38 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit37 = (SimpleSymbol)(new SimpleSymbol("Hint")).readResolve();
      Lit36 = (SimpleSymbol)(new SimpleSymbol("emp_id")).readResolve();
      Lit35 = new FString("com.google.appinventor.components.runtime.TextBox");
      Lit34 = new FString("com.google.appinventor.components.runtime.Label");
      Lit33 = IntNum.make(15);
      Lit32 = (SimpleSymbol)(new SimpleSymbol("Label6")).readResolve();
      Lit31 = new FString("com.google.appinventor.components.runtime.Label");
      Lit30 = new FString("com.google.appinventor.components.runtime.Label");
      var1 = new int[]{-1, 0};
      Lit29 = IntNum.make(var1);
      Lit28 = (SimpleSymbol)(new SimpleSymbol("TextColor")).readResolve();
      Lit27 = (SimpleSymbol)(new SimpleSymbol("Text")).readResolve();
      Lit26 = (SimpleSymbol)(new SimpleSymbol("Label1")).readResolve();
      Lit25 = new FString("com.google.appinventor.components.runtime.Label");
      Lit24 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit23 = IntNum.make(-2);
      Lit22 = (SimpleSymbol)(new SimpleSymbol("Width")).readResolve();
      Lit21 = (SimpleSymbol)(new SimpleSymbol("HorizontalArrangement1")).readResolve();
      Lit20 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
      Lit19 = (SimpleSymbol)(new SimpleSymbol("Title")).readResolve();
      var1 = new int[]{-16777216, 0};
      Lit17 = IntNum.make(var1);
      Lit16 = (SimpleSymbol)(new SimpleSymbol("BackgroundColor")).readResolve();
      Lit15 = (SimpleSymbol)(new SimpleSymbol("AppName")).readResolve();
      Lit14 = (SimpleSymbol)(new SimpleSymbol("g$employeesForListPicker")).readResolve();
      Lit13 = (SimpleSymbol)(new SimpleSymbol("g$LengthOfList")).readResolve();
      Lit12 = (SimpleSymbol)(new SimpleSymbol("g$employees")).readResolve();
      Lit11 = (SimpleSymbol)(new SimpleSymbol("Get")).readResolve();
      Lit9 = (SimpleSymbol)(new SimpleSymbol("Url")).readResolve();
      Lit8 = (SimpleSymbol)(new SimpleSymbol("Web1")).readResolve();
      Lit7 = (SimpleSymbol)(new SimpleSymbol("p$UpdateListPicker")).readResolve();
      Lit6 = IntNum.make(0);
      Lit5 = (SimpleSymbol)(new SimpleSymbol("g$currentEmployee")).readResolve();
      Lit4 = (SimpleSymbol)(new SimpleSymbol("g$TempURL")).readResolve();
      Lit3 = (SimpleSymbol)(new SimpleSymbol("g$URL")).readResolve();
      Lit2 = (SimpleSymbol)(new SimpleSymbol("*the-null-value*")).readResolve();
      Lit1 = (SimpleSymbol)(new SimpleSymbol("getMessage")).readResolve();
      Lit0 = (SimpleSymbol)(new SimpleSymbol("Screen1")).readResolve();
   }

   public Screen1() {
      ModuleInfo.register(this);
      Screen1$frame var1 = new Screen1$frame();
      var1.$main = this;
      this.android$Mnlog$Mnform = new ModuleMethod(var1, 1, Lit153, 4097);
      this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(var1, 2, Lit154, 8194);
      this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(var1, 3, Lit155, 8193);
      this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(var1, 5, Lit156, 4097);
      this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(var1, 6, Lit157, 8194);
      this.add$Mnto$Mnevents = new ModuleMethod(var1, 7, Lit158, 8194);
      this.add$Mnto$Mncomponents = new ModuleMethod(var1, 8, Lit159, 16388);
      this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(var1, 9, Lit160, 8194);
      this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(var1, 10, Lit161, 4097);
      this.send$Mnerror = new ModuleMethod(var1, 11, Lit162, 4097);
      this.process$Mnexception = new ModuleMethod(var1, 12, "process-exception", 4097);
      this.dispatchEvent = new ModuleMethod(var1, 13, Lit163, 16388);
      this.lookup$Mnhandler = new ModuleMethod(var1, 14, Lit164, 8194);
      ModuleMethod var2 = new ModuleMethod(var1, 15, (Object)null, 0);
      var2.setProperty("source-location", "/tmp/runtime913627884221531399.scm:541");
      lambda$Fn1 = var2;
      this.$define = new ModuleMethod(var1, 16, "$define", 0);
      lambda$Fn2 = new ModuleMethod(var1, 17, (Object)null, 0);
      lambda$Fn3 = new ModuleMethod(var1, 18, (Object)null, 0);
      lambda$Fn4 = new ModuleMethod(var1, 19, (Object)null, 0);
      lambda$Fn5 = new ModuleMethod(var1, 20, (Object)null, 0);
      lambda$Fn7 = new ModuleMethod(var1, 21, (Object)null, 0);
      lambda$Fn6 = new ModuleMethod(var1, 22, (Object)null, 0);
      lambda$Fn8 = new ModuleMethod(var1, 23, (Object)null, 0);
      lambda$Fn9 = new ModuleMethod(var1, 24, (Object)null, 0);
      lambda$Fn10 = new ModuleMethod(var1, 25, (Object)null, 0);
      lambda$Fn11 = new ModuleMethod(var1, 26, (Object)null, 0);
      lambda$Fn12 = new ModuleMethod(var1, 27, (Object)null, 0);
      lambda$Fn13 = new ModuleMethod(var1, 28, (Object)null, 0);
      lambda$Fn14 = new ModuleMethod(var1, 29, (Object)null, 0);
      lambda$Fn15 = new ModuleMethod(var1, 30, (Object)null, 0);
      lambda$Fn16 = new ModuleMethod(var1, 31, (Object)null, 0);
      lambda$Fn17 = new ModuleMethod(var1, 32, (Object)null, 0);
      lambda$Fn18 = new ModuleMethod(var1, 33, (Object)null, 0);
      lambda$Fn19 = new ModuleMethod(var1, 34, (Object)null, 0);
      lambda$Fn20 = new ModuleMethod(var1, 35, (Object)null, 0);
      lambda$Fn21 = new ModuleMethod(var1, 36, (Object)null, 0);
      lambda$Fn22 = new ModuleMethod(var1, 37, (Object)null, 0);
      lambda$Fn23 = new ModuleMethod(var1, 38, (Object)null, 0);
      lambda$Fn24 = new ModuleMethod(var1, 39, (Object)null, 0);
      lambda$Fn25 = new ModuleMethod(var1, 40, (Object)null, 0);
      lambda$Fn26 = new ModuleMethod(var1, 41, (Object)null, 0);
      lambda$Fn27 = new ModuleMethod(var1, 42, (Object)null, 0);
      lambda$Fn28 = new ModuleMethod(var1, 43, (Object)null, 0);
      lambda$Fn29 = new ModuleMethod(var1, 44, (Object)null, 0);
      lambda$Fn30 = new ModuleMethod(var1, 45, (Object)null, 0);
      lambda$Fn31 = new ModuleMethod(var1, 46, (Object)null, 0);
      lambda$Fn32 = new ModuleMethod(var1, 47, (Object)null, 0);
      lambda$Fn33 = new ModuleMethod(var1, 48, (Object)null, 0);
      lambda$Fn34 = new ModuleMethod(var1, 49, (Object)null, 0);
      lambda$Fn35 = new ModuleMethod(var1, 50, (Object)null, 0);
      lambda$Fn36 = new ModuleMethod(var1, 51, (Object)null, 0);
      lambda$Fn37 = new ModuleMethod(var1, 52, (Object)null, 0);
      lambda$Fn38 = new ModuleMethod(var1, 53, (Object)null, 0);
      lambda$Fn39 = new ModuleMethod(var1, 54, (Object)null, 0);
      lambda$Fn40 = new ModuleMethod(var1, 55, (Object)null, 0);
      lambda$Fn41 = new ModuleMethod(var1, 56, (Object)null, 0);
      lambda$Fn42 = new ModuleMethod(var1, 57, (Object)null, 0);
      lambda$Fn43 = new ModuleMethod(var1, 58, (Object)null, 0);
      lambda$Fn44 = new ModuleMethod(var1, 59, (Object)null, 0);
      lambda$Fn45 = new ModuleMethod(var1, 60, (Object)null, 0);
      lambda$Fn46 = new ModuleMethod(var1, 61, (Object)null, 0);
      lambda$Fn47 = new ModuleMethod(var1, 62, (Object)null, 0);
      lambda$Fn48 = new ModuleMethod(var1, 63, (Object)null, 0);
      lambda$Fn49 = new ModuleMethod(var1, 64, (Object)null, 0);
      this.add_emp$Click = new ModuleMethod(var1, 65, Lit97, 0);
      lambda$Fn50 = new ModuleMethod(var1, 66, (Object)null, 0);
      lambda$Fn51 = new ModuleMethod(var1, 67, (Object)null, 0);
      this.update_emp$Click = new ModuleMethod(var1, 68, Lit108, 0);
      lambda$Fn52 = new ModuleMethod(var1, 69, (Object)null, 0);
      lambda$Fn53 = new ModuleMethod(var1, 70, (Object)null, 0);
      this.View_Customers$Click = new ModuleMethod(var1, 71, Lit112, 0);
      lambda$Fn54 = new ModuleMethod(var1, 72, (Object)null, 0);
      lambda$Fn55 = new ModuleMethod(var1, 73, (Object)null, 0);
      this.ListPicker1$AfterPicking = new ModuleMethod(var1, 74, Lit134, 0);
      lambda$Fn56 = new ModuleMethod(var1, 75, (Object)null, 4097);
      this.Web1$GotText = new ModuleMethod(var1, 76, Lit149, 16388);
   }

   static IntNum lambda10() {
      return Lit6;
   }

   static Object lambda11() {
      return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
   }

   static Object lambda12() {
      runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "new_thinker", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Lit17, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Payroll App", Lit10);
   }

   static Object lambda13() {
      return runtime.setAndCoerceProperty$Ex(Lit21, Lit22, Lit23, Lit18);
   }

   static Object lambda14() {
      return runtime.setAndCoerceProperty$Ex(Lit21, Lit22, Lit23, Lit18);
   }

   static Object lambda15() {
      runtime.setAndCoerceProperty$Ex(Lit26, Lit27, "Emp ID", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit26, Lit28, Lit29, Lit18);
   }

   static Object lambda16() {
      runtime.setAndCoerceProperty$Ex(Lit26, Lit27, "Emp ID", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit26, Lit28, Lit29, Lit18);
   }

   static Object lambda17() {
      runtime.setAndCoerceProperty$Ex(Lit32, Lit27, "T", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit32, Lit22, Lit33, Lit18);
   }

   static Object lambda18() {
      runtime.setAndCoerceProperty$Ex(Lit32, Lit27, "T", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit32, Lit22, Lit33, Lit18);
   }

   static Object lambda19() {
      runtime.setAndCoerceProperty$Ex(Lit36, Lit37, "Enter the employee ID", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit36, Lit22, Lit23, Lit18);
   }

   public static SimpleSymbol lambda1symbolAppend$V(Object[] var0) {
      Object var1 = LList.makeList(var0, 0);
      Apply var2 = Scheme.apply;
      ModuleMethod var3 = strings.string$Mnappend;

      Symbol var5;
      Object var9;
      for(var9 = LList.Empty; var1 != LList.Empty; var9 = Pair.make(misc.symbol$To$String(var5), var9)) {
         Pair var4;
         try {
            var4 = (Pair)var1;
         } catch (ClassCastException var8) {
            throw new WrongType(var8, "arg0", -2, var1);
         }

         var1 = var4.getCdr();
         Object var11 = var4.getCar();

         try {
            var5 = (Symbol)var11;
         } catch (ClassCastException var7) {
            throw new WrongType(var7, "symbol->string", 1, var11);
         }
      }

      var9 = var2.apply2(var3, LList.reverseInPlace(var9));

      CharSequence var10;
      try {
         var10 = (CharSequence)var9;
      } catch (ClassCastException var6) {
         throw new WrongType(var6, "string->symbol", 1, var9);
      }

      return misc.string$To$Symbol(var10);
   }

   static Object lambda2() {
      return null;
   }

   static Object lambda20() {
      runtime.setAndCoerceProperty$Ex(Lit36, Lit37, "Enter the employee ID", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit36, Lit22, Lit23, Lit18);
   }

   static Object lambda21() {
      return runtime.setAndCoerceProperty$Ex(Lit40, Lit22, Lit23, Lit18);
   }

   static Object lambda22() {
      return runtime.setAndCoerceProperty$Ex(Lit40, Lit22, Lit23, Lit18);
   }

   static Object lambda23() {
      runtime.setAndCoerceProperty$Ex(Lit43, Lit27, "Emp Name", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit43, Lit28, Lit44, Lit18);
   }

   static Object lambda24() {
      runtime.setAndCoerceProperty$Ex(Lit43, Lit27, "Emp Name", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit43, Lit28, Lit44, Lit18);
   }

   static Object lambda25() {
      runtime.setAndCoerceProperty$Ex(Lit47, Lit37, "Enter name", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit47, Lit22, Lit23, Lit18);
   }

   static Object lambda26() {
      runtime.setAndCoerceProperty$Ex(Lit47, Lit37, "Enter name", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit47, Lit22, Lit23, Lit18);
   }

   static Object lambda27() {
      runtime.setAndCoerceProperty$Ex(Lit50, Lit22, Lit23, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit50, Lit51, Lit52, Lit18);
   }

   static Object lambda28() {
      runtime.setAndCoerceProperty$Ex(Lit50, Lit22, Lit23, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit50, Lit51, Lit52, Lit18);
   }

   static Object lambda29() {
      runtime.setAndCoerceProperty$Ex(Lit55, Lit56, Lit57, Lit18);
      runtime.setAndCoerceProperty$Ex(Lit55, Lit27, "Address  ", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit55, Lit28, Lit58, Lit18);
   }

   static String lambda3() {
      return "";
   }

   static Object lambda30() {
      runtime.setAndCoerceProperty$Ex(Lit55, Lit56, Lit57, Lit18);
      runtime.setAndCoerceProperty$Ex(Lit55, Lit27, "Address  ", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit55, Lit28, Lit58, Lit18);
   }

   static Object lambda31() {
      return runtime.setAndCoerceProperty$Ex(Lit61, Lit22, Lit62, Lit18);
   }

   static Object lambda32() {
      return runtime.setAndCoerceProperty$Ex(Lit61, Lit22, Lit62, Lit18);
   }

   static Object lambda33() {
      runtime.setAndCoerceProperty$Ex(Lit65, Lit37, "Enter address", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit65, Lit22, Lit23, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit65, Lit51, Lit23, Lit18);
   }

   static Object lambda34() {
      runtime.setAndCoerceProperty$Ex(Lit65, Lit37, "Enter address", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit65, Lit22, Lit23, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit65, Lit51, Lit23, Lit18);
   }

   static Object lambda35() {
      return runtime.setAndCoerceProperty$Ex(Lit68, Lit22, Lit23, Lit18);
   }

   static Object lambda36() {
      return runtime.setAndCoerceProperty$Ex(Lit68, Lit22, Lit23, Lit18);
   }

   static Object lambda37() {
      runtime.setAndCoerceProperty$Ex(Lit71, Lit27, "Hourly rate", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit71, Lit28, Lit72, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit71, Lit22, Lit73, Lit18);
   }

   static Object lambda38() {
      runtime.setAndCoerceProperty$Ex(Lit71, Lit27, "Hourly rate", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit71, Lit28, Lit72, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit71, Lit22, Lit73, Lit18);
   }

   static Object lambda39() {
      runtime.setAndCoerceProperty$Ex(Lit76, Lit37, "enter hourly rate for the employee", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit76, Lit22, Lit23, Lit18);
   }

   static String lambda4() {
      return "";
   }

   static Object lambda40() {
      runtime.setAndCoerceProperty$Ex(Lit76, Lit37, "enter hourly rate for the employee", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit76, Lit22, Lit23, Lit18);
   }

   static Object lambda41() {
      return runtime.setAndCoerceProperty$Ex(Lit79, Lit22, Lit23, Lit18);
   }

   static Object lambda42() {
      return runtime.setAndCoerceProperty$Ex(Lit79, Lit22, Lit23, Lit18);
   }

   static Object lambda43() {
      runtime.setAndCoerceProperty$Ex(Lit82, Lit27, "Member ID", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit82, Lit28, Lit83, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit82, Lit22, Lit73, Lit18);
   }

   static Object lambda44() {
      runtime.setAndCoerceProperty$Ex(Lit82, Lit27, "Member ID", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit82, Lit28, Lit83, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit82, Lit22, Lit73, Lit18);
   }

   static Object lambda45() {
      runtime.setAndCoerceProperty$Ex(Lit86, Lit37, "Enter member id if available", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit86, Lit22, Lit23, Lit18);
   }

   static Object lambda46() {
      runtime.setAndCoerceProperty$Ex(Lit86, Lit37, "Enter member id if available", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit86, Lit22, Lit23, Lit18);
   }

   static Object lambda47() {
      runtime.setAndCoerceProperty$Ex(Lit89, Lit22, Lit23, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit89, Lit51, Lit90, Lit18);
   }

   static Object lambda48() {
      runtime.setAndCoerceProperty$Ex(Lit89, Lit22, Lit23, Lit18);
      return runtime.setAndCoerceProperty$Ex(Lit89, Lit51, Lit90, Lit18);
   }

   static Object lambda49() {
      runtime.setAndCoerceProperty$Ex(Lit93, Lit27, "Add Employee", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit93, Lit22, Lit23, Lit18);
   }

   static IntNum lambda5() {
      return Lit6;
   }

   static Object lambda50() {
      runtime.setAndCoerceProperty$Ex(Lit93, Lit27, "Add Employee", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit93, Lit22, Lit23, Lit18);
   }

   static Object lambda51() {
      runtime.setAndCoerceProperty$Ex(Lit100, Lit27, "Update Employees", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit100, Lit22, Lit23, Lit18);
   }

   static Object lambda52() {
      runtime.setAndCoerceProperty$Ex(Lit100, Lit27, "Update Employees", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit100, Lit22, Lit23, Lit18);
   }

   static Object lambda53() {
      runtime.setAndCoerceProperty$Ex(Lit110, Lit27, "View Customers", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit110, Lit22, Lit23, Lit18);
   }

   static Object lambda54() {
      runtime.setAndCoerceProperty$Ex(Lit110, Lit27, "View Customers", Lit10);
      return runtime.setAndCoerceProperty$Ex(Lit110, Lit22, Lit23, Lit18);
   }

   static Object lambda55() {
      runtime.setAndCoerceProperty$Ex(Lit114, Lit27, "listpicker", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit114, Lit115, Boolean.FALSE, Lit116);
      return runtime.setAndCoerceProperty$Ex(Lit114, Lit22, Lit23, Lit18);
   }

   static Object lambda56() {
      runtime.setAndCoerceProperty$Ex(Lit114, Lit27, "listpicker", Lit10);
      runtime.setAndCoerceProperty$Ex(Lit114, Lit115, Boolean.FALSE, Lit116);
      return runtime.setAndCoerceProperty$Ex(Lit114, Lit22, Lit23, Lit18);
   }

   static Object lambda57(Object var0) {
      runtime.addGlobalVarToCurrentFormEnvironment(Lit13, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnlength, LList.list1(var0), Lit139, "length of list"));
      return runtime.callYailPrimitive(Scheme.numGrt, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St), Lit132), Lit140, ">") != Boolean.FALSE?runtime.callYailPrimitive(runtime.yail$Mnlist$Mnadd$Mnto$Mnlist$Ex, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit14, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.callYailPrimitive(strings.string$Mnappend, LList.list3(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(var0, Lit132), Lit141, "select list item"), "-", runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(var0, Lit120), Lit142, "select list item")), Lit143, "join")), Lit144, "add items to list"):Values.empty;
   }

   static Object lambda6() {
      runtime.setAndCoerceProperty$Ex(Lit8, Lit9, "http://payroll-assignment/getdata.php", Lit10);
      return runtime.callComponentMethod(Lit8, Lit11, LList.Empty, LList.Empty);
   }

   static Procedure lambda7() {
      return lambda$Fn7;
   }

   static Object lambda8() {
      runtime.setAndCoerceProperty$Ex(Lit8, Lit9, "http://payroll-assignment/getdata.php", Lit10);
      return runtime.callComponentMethod(Lit8, Lit11, LList.Empty, LList.Empty);
   }

   static Object lambda9() {
      return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
   }

   public void $define() {
      // $FF: Couldn't be decompiled
   }

   public Object ListPicker1$AfterPicking() {
      runtime.setThisForm();
      runtime.setAndCoerceProperty$Ex(Lit47, Lit27, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.getProperty$1(Lit114, Lit118)), Lit119, "select list item"), Lit120), Lit121, "select list item"), Lit10);
      runtime.setAndCoerceProperty$Ex(Lit65, Lit27, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.getProperty$1(Lit114, Lit118)), Lit122, "select list item"), Lit123), Lit124, "select list item"), Lit10);
      runtime.setAndCoerceProperty$Ex(Lit76, Lit27, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.getProperty$1(Lit114, Lit118)), Lit125, "select list item"), Lit126), Lit127, "select list item"), Lit10);
      runtime.setAndCoerceProperty$Ex(Lit86, Lit27, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.getProperty$1(Lit114, Lit118)), Lit128, "select list item"), Lit129), Lit130, "select list item"), Lit10);
      return runtime.addGlobalVarToCurrentFormEnvironment(Lit5, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), runtime.getProperty$1(Lit114, Lit118)), Lit131, "select list item"), Lit132), Lit133, "select list item"));
   }

   public Object View_Customers$Click() {
      runtime.setThisForm();
      return Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St));
   }

   public Object Web1$GotText(Object var1, Object var2, Object var3, Object var4) {
      runtime.sanitizeComponentData(var1);
      runtime.sanitizeComponentData(var2);
      runtime.sanitizeComponentData(var3);
      var1 = runtime.sanitizeComponentData(var4);
      runtime.setThisForm();
      runtime.addGlobalVarToCurrentFormEnvironment(Lit12, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnfrom$Mncsv$Mntable, LList.list1(var1), Lit138, "list from csv table"));
      runtime.addGlobalVarToCurrentFormEnvironment(Lit14, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list"));
      runtime.yailForEach(lambda$Fn56, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St));
      if(runtime.callYailPrimitive(Scheme.numGrt, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St), Lit132), Lit145, ">") != Boolean.FALSE) {
         runtime.setAndCoerceProperty$Ex(Lit114, Lit146, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit14, runtime.$Stthe$Mnnull$Mnvalue$St), Lit147);
         return runtime.callComponentMethod(Lit114, Lit148, LList.Empty, LList.Empty);
      } else {
         return Values.empty;
      }
   }

   public void addToComponents(Object var1, Object var2, Object var3, Object var4) {
      this.components$Mnto$Mncreate = lists.cons(LList.list4(var1, var2, var3, var4), this.components$Mnto$Mncreate);
   }

   public void addToEvents(Object var1, Object var2) {
      this.events$Mnto$Mnregister = lists.cons(lists.cons(var1, var2), this.events$Mnto$Mnregister);
   }

   public void addToFormDoAfterCreation(Object var1) {
      this.form$Mndo$Mnafter$Mncreation = lists.cons(var1, this.form$Mndo$Mnafter$Mncreation);
   }

   public void addToFormEnvironment(Symbol var1, Object var2) {
      this.androidLogForm(Format.formatToString(0, new Object[]{"Adding ~A to env ~A with value ~A", var1, this.form$Mnenvironment, var2}));
      this.form$Mnenvironment.put((Symbol)var1, var2);
   }

   public void addToGlobalVarEnvironment(Symbol var1, Object var2) {
      this.androidLogForm(Format.formatToString(0, new Object[]{"Adding ~A to env ~A with value ~A", var1, this.global$Mnvar$Mnenvironment, var2}));
      this.global$Mnvar$Mnenvironment.put((Symbol)var1, var2);
   }

   public void addToGlobalVars(Object var1, Object var2) {
      this.global$Mnvars$Mnto$Mncreate = lists.cons(LList.list2(var1, var2), this.global$Mnvars$Mnto$Mncreate);
   }

   public Object add_emp$Click() {
      runtime.setThisForm();
      SimpleSymbol var1 = Lit4;
      ModuleMethod var2 = strings.string$Mnappend;
      Pair var3 = LList.list1("http://payroll-assignment.esy.es/submit.php?emp_id=");
      LList.chain1(LList.chain4(LList.chain4(var3, runtime.getProperty$1(Lit36, Lit27), "&emp_name=", runtime.getProperty$1(Lit47, Lit27), "&emp_address="), runtime.getProperty$1(Lit65, Lit27), "&hourly_rate=", runtime.getProperty$1(Lit76, Lit27), "member_id"), runtime.getProperty$1(Lit86, Lit27));
      runtime.addGlobalVarToCurrentFormEnvironment(var1, runtime.callYailPrimitive(var2, var3, Lit95, "join"));
      runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), " ", "%20"), Lit96, "replace all"));
      runtime.setAndCoerceProperty$Ex(Lit8, Lit9, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit10);
      return runtime.callComponentMethod(Lit8, Lit11, LList.Empty, LList.Empty);
   }

   public void androidLogForm(Object var1) {
   }

   public boolean dispatchEvent(Component var1, String var2, String var3, Object[] var4) {
      boolean var6 = false;
      SimpleSymbol var5 = misc.string$To$Symbol(var2);
      if(this.isBoundInFormEnvironment(var5)) {
         if(this.lookupInFormEnvironment(var5) == var1) {
            Object var8 = this.lookupHandler(var2, var3);

            try {
               Scheme.apply.apply2(var8, LList.makeList(var4, 0));
            } catch (Throwable var7) {
               this.androidLogForm(var7.getMessage());
               var7.printStackTrace();
               this.processException(var7);
               return false;
            }

            var6 = true;
         }

         return var6;
      } else {
         EventDispatcher.unregisterEventForDelegation(this, var2, var3);
         return false;
      }
   }

   public boolean isBoundInFormEnvironment(Symbol var1) {
      return this.form$Mnenvironment.isBound(var1);
   }

   public Object lookupHandler(Object var1, Object var2) {
      Object var3 = null;
      String var4;
      if(var1 == null) {
         var4 = null;
      } else {
         var4 = var1.toString();
      }

      String var5;
      if(var2 == null) {
         var5 = (String)var3;
      } else {
         var5 = var2.toString();
      }

      return this.lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(var4, var5)));
   }

   public Object lookupInFormEnvironment(Symbol var1) {
      return this.lookupInFormEnvironment(var1, Boolean.FALSE);
   }

   public Object lookupInFormEnvironment(Symbol var1, Object var2) {
      byte var3;
      if(this.form$Mnenvironment == null) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var4 = var3 + 1 & 1;
      if(var4 != 0) {
         if(!this.form$Mnenvironment.isBound(var1)) {
            return var2;
         }
      } else if(var4 == 0) {
         return var2;
      }

      var2 = this.form$Mnenvironment.get((Symbol)var1);
      return var2;
   }

   public void processException(Object var1) {
      Throwable var2;
      try {
         var2 = (Throwable)var1;
      } catch (ClassCastException var3) {
         throw new WrongType(var3, "com.google.appinventor.components.runtime.ReplApplication.reportError(java.lang.Throwable)", 1, var1);
      }

      ReplApplication.reportError(var2);
      Object var5 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(var1, Lit1));
      String var6;
      if(var5 == null) {
         var6 = null;
      } else {
         var6 = var5.toString();
      }

      String var4;
      if(var1 instanceof YailRuntimeError) {
         var4 = ((YailRuntimeError)var1).getErrorType();
      } else {
         var4 = "Runtime Error";
      }

      RuntimeErrorAlert.alert(this, var6, var4, "End Application");
   }

   public void run() {
      CallContext var2 = CallContext.getInstance();
      Consumer var3 = var2.consumer;
      var2.consumer = VoidConsumer.instance;

      Throwable var1;
      label13: {
         try {
            this.run(var2);
         } catch (Throwable var4) {
            var1 = var4;
            break label13;
         }

         var1 = null;
      }

      ModuleBody.runCleanup(var2, var1, var3);
   }

   public final void run(CallContext var1) {
      Consumer var2 = var1.consumer;
      Object var6 = require.find("com.google.youngandroid.runtime");

      Runnable var3;
      try {
         var3 = (Runnable)var6;
      } catch (ClassCastException var5) {
         throw new WrongType(var5, "java.lang.Runnable.run()", 1, var6);
      }

      var3.run();
      this.$Stdebug$Mnform$St = Boolean.FALSE;
      this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
      FString var7 = strings.stringAppend(new Object[]{misc.symbol$To$String(Lit0), "-global-vars"});
      String var8;
      if(var7 == null) {
         var8 = null;
      } else {
         var8 = var7.toString();
      }

      this.global$Mnvar$Mnenvironment = Environment.make(var8);
      Screen1 = null;
      this.form$Mnname$Mnsymbol = Lit0;
      this.events$Mnto$Mnregister = LList.Empty;
      this.components$Mnto$Mncreate = LList.Empty;
      this.global$Mnvars$Mnto$Mncreate = LList.Empty;
      this.form$Mndo$Mnafter$Mncreation = LList.Empty;
      var6 = require.find("com.google.youngandroid.runtime");

      try {
         var3 = (Runnable)var6;
      } catch (ClassCastException var4) {
         throw new WrongType(var4, "java.lang.Runnable.run()", 1, var6);
      }

      var3.run();
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, ""), var2);
      } else {
         this.addToGlobalVars(Lit3, lambda$Fn2);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit4, ""), var2);
      } else {
         this.addToGlobalVars(Lit4, lambda$Fn3);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, Lit6), var2);
      } else {
         this.addToGlobalVars(Lit5, lambda$Fn4);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, lambda$Fn5), var2);
      } else {
         this.addToGlobalVars(Lit7, lambda$Fn6);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit12, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), var2);
      } else {
         this.addToGlobalVars(Lit12, lambda$Fn8);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit13, Lit6), var2);
      } else {
         this.addToGlobalVars(Lit13, lambda$Fn9);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit14, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), var2);
      } else {
         this.addToGlobalVars(Lit14, lambda$Fn10);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "new_thinker", Lit10);
         runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Lit17, Lit18);
         Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Payroll App", Lit10), var2);
      } else {
         this.addToFormDoAfterCreation(new Promise(lambda$Fn11));
      }

      this.HorizontalArrangement1 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit20, Lit21, lambda$Fn12), var2);
      } else {
         this.addToComponents(Lit0, Lit24, Lit21, lambda$Fn13);
      }

      this.Label1 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit25, Lit26, lambda$Fn14), var2);
      } else {
         this.addToComponents(Lit21, Lit30, Lit26, lambda$Fn15);
      }

      this.Label6 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit31, Lit32, lambda$Fn16), var2);
      } else {
         this.addToComponents(Lit21, Lit34, Lit32, lambda$Fn17);
      }

      this.emp_id = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit35, Lit36, lambda$Fn18), var2);
      } else {
         this.addToComponents(Lit21, Lit38, Lit36, lambda$Fn19);
      }

      this.HorizontalArrangement2 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit39, Lit40, lambda$Fn20), var2);
      } else {
         this.addToComponents(Lit0, Lit41, Lit40, lambda$Fn21);
      }

      this.Label2 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit42, Lit43, lambda$Fn22), var2);
      } else {
         this.addToComponents(Lit40, Lit45, Lit43, lambda$Fn23);
      }

      this.emp_name = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit46, Lit47, lambda$Fn24), var2);
      } else {
         this.addToComponents(Lit40, Lit48, Lit47, lambda$Fn25);
      }

      this.HorizontalArrangement3 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit49, Lit50, lambda$Fn26), var2);
      } else {
         this.addToComponents(Lit0, Lit53, Lit50, lambda$Fn27);
      }

      this.Label4 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit50, Lit54, Lit55, lambda$Fn28), var2);
      } else {
         this.addToComponents(Lit50, Lit59, Lit55, lambda$Fn29);
      }

      this.Label5 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit50, Lit60, Lit61, lambda$Fn30), var2);
      } else {
         this.addToComponents(Lit50, Lit63, Lit61, lambda$Fn31);
      }

      this.emp_address = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit50, Lit64, Lit65, lambda$Fn32), var2);
      } else {
         this.addToComponents(Lit50, Lit66, Lit65, lambda$Fn33);
      }

      this.HorizontalArrangement4 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit67, Lit68, lambda$Fn34), var2);
      } else {
         this.addToComponents(Lit0, Lit69, Lit68, lambda$Fn35);
      }

      this.Label7 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit68, Lit70, Lit71, lambda$Fn36), var2);
      } else {
         this.addToComponents(Lit68, Lit74, Lit71, lambda$Fn37);
      }

      this.hourly_rate = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit68, Lit75, Lit76, lambda$Fn38), var2);
      } else {
         this.addToComponents(Lit68, Lit77, Lit76, lambda$Fn39);
      }

      this.HorizontalArrangement5 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit78, Lit79, lambda$Fn40), var2);
      } else {
         this.addToComponents(Lit0, Lit80, Lit79, lambda$Fn41);
      }

      this.Label8 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit79, Lit81, Lit82, lambda$Fn42), var2);
      } else {
         this.addToComponents(Lit79, Lit84, Lit82, lambda$Fn43);
      }

      this.member_id = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit79, Lit85, Lit86, lambda$Fn44), var2);
      } else {
         this.addToComponents(Lit79, Lit87, Lit86, lambda$Fn45);
      }

      this.HorizontalArrangement6 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit88, Lit89, lambda$Fn46), var2);
      } else {
         this.addToComponents(Lit0, Lit91, Lit89, lambda$Fn47);
      }

      this.add_emp = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit92, Lit93, lambda$Fn48), var2);
      } else {
         this.addToComponents(Lit0, Lit94, Lit93, lambda$Fn49);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         runtime.addToCurrentFormEnvironment(Lit97, this.add_emp$Click);
      } else {
         this.addToFormEnvironment(Lit97, this.add_emp$Click);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         EventDispatcher.registerEventForDelegation((HandlesEventDispatching)runtime.$Stthis$Mnform$St, "add_emp", "Click");
      } else {
         this.addToEvents(Lit93, Lit98);
      }

      this.update_emp = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit99, Lit100, lambda$Fn50), var2);
      } else {
         this.addToComponents(Lit0, Lit101, Lit100, lambda$Fn51);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         runtime.addToCurrentFormEnvironment(Lit108, this.update_emp$Click);
      } else {
         this.addToFormEnvironment(Lit108, this.update_emp$Click);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         EventDispatcher.registerEventForDelegation((HandlesEventDispatching)runtime.$Stthis$Mnform$St, "update_emp", "Click");
      } else {
         this.addToEvents(Lit100, Lit98);
      }

      this.View_Customers = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit109, Lit110, lambda$Fn52), var2);
      } else {
         this.addToComponents(Lit0, Lit111, Lit110, lambda$Fn53);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         runtime.addToCurrentFormEnvironment(Lit112, this.View_Customers$Click);
      } else {
         this.addToFormEnvironment(Lit112, this.View_Customers$Click);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         EventDispatcher.registerEventForDelegation((HandlesEventDispatching)runtime.$Stthis$Mnform$St, "View_Customers", "Click");
      } else {
         this.addToEvents(Lit110, Lit98);
      }

      this.ListPicker1 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit113, Lit114, lambda$Fn54), var2);
      } else {
         this.addToComponents(Lit0, Lit117, Lit114, lambda$Fn55);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         runtime.addToCurrentFormEnvironment(Lit134, this.ListPicker1$AfterPicking);
      } else {
         this.addToFormEnvironment(Lit134, this.ListPicker1$AfterPicking);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         EventDispatcher.registerEventForDelegation((HandlesEventDispatching)runtime.$Stthis$Mnform$St, "ListPicker1", "AfterPicking");
      } else {
         this.addToEvents(Lit114, Lit135);
      }

      this.Web1 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit136, Lit8, Boolean.FALSE), var2);
      } else {
         this.addToComponents(Lit0, Lit137, Lit8, Boolean.FALSE);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         runtime.addToCurrentFormEnvironment(Lit149, this.Web1$GotText);
      } else {
         this.addToFormEnvironment(Lit149, this.Web1$GotText);
      }

      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         EventDispatcher.registerEventForDelegation((HandlesEventDispatching)runtime.$Stthis$Mnform$St, "Web1", "GotText");
      } else {
         this.addToEvents(Lit8, Lit150);
      }

      this.Notifier1 = null;
      if(runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
         Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit151, Lit105, Boolean.FALSE), var2);
      } else {
         this.addToComponents(Lit0, Lit152, Lit105, Boolean.FALSE);
      }

      runtime.initRuntime();
   }

   public void sendError(Object var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      RetValManager.sendError(var2);
   }

   public Object update_emp$Click() {
      runtime.setThisForm();
      if(runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St), Lit6), Lit102, "not =") != Boolean.FALSE) {
         SimpleSymbol var1 = Lit4;
         ModuleMethod var2 = strings.string$Mnappend;
         Pair var3 = LList.list1("http://payroll-assignment.esy.es/update.php?emp_id=");
         LList.chain1(LList.chain4(LList.chain4(var3, runtime.getProperty$1(Lit47, Lit27), "&emp_address=", runtime.getProperty$1(Lit65, Lit27), "&hourly_rate="), runtime.getProperty$1(Lit76, Lit27), "member_id", runtime.getProperty$1(Lit86, Lit27), runtime.getProperty$1(Lit36, Lit27)), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St));
         runtime.addGlobalVarToCurrentFormEnvironment(var1, runtime.callYailPrimitive(var2, var3, Lit103, "join"));
         runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), " ", "%20"), Lit104, "replace all"));
         runtime.setAndCoerceProperty$Ex(Lit8, Lit9, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit10);
         return runtime.callComponentMethod(Lit8, Lit11, LList.Empty, LList.Empty);
      } else {
         return runtime.callComponentMethod(Lit105, Lit106, LList.list3("Customer Data Error", "Error", "ok"), Lit107);
      }
   }
}
