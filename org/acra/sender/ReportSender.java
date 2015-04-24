package org.acra.sender;

import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSenderException;

public interface ReportSender {

   void send(CrashReportData var1) throws ReportSenderException;
}
