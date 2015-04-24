package org.acra;

import org.acra.ACRAConstants;

final class CrashReportFileNameParser {

   public boolean isApproved(String var1) {
      return this.isSilent(var1) || var1.contains("-approved");
   }

   public boolean isSilent(String var1) {
      return var1.contains(ACRAConstants.SILENT_SUFFIX);
   }
}
