package org.acra.collector;

import java.util.EnumMap;
import org.acra.ReportField;

public final class CrashReportData extends EnumMap {

   private static final long serialVersionUID = 4112578634029874840L;


   public CrashReportData() {
      super(ReportField.class);
   }

   public String getProperty(ReportField var1) {
      return (String)super.get(var1);
   }
}
