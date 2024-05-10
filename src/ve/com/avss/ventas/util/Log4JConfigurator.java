package ve.com.avss.ventas.util;

import java.io.File;
import java.util.Enumeration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4JConfigurator {
	private static boolean notConfigured = true;

	public static synchronized void configure(String log4jFileName) {
		if (notConfigured) {
			File f = new File(log4jFileName);
			if (f.exists() && f.isFile() && f.canRead()
					&& isNotConfiguredFromAnOutsiderComponent()) {
				DOMConfigurator.configure(log4jFileName);
			}
			notConfigured = false;
		}
	}

	/**
	 * Returns true if it appears that log4j have NOT been previously
	 * configured. This code checks to see if there are any appenders defined
	 * for log4j which is the definitive way to tell if log4j is already
	 * initialized.
	 */
	private static boolean isNotConfiguredFromAnOutsiderComponent() {
		Enumeration appenders = Logger.getRootLogger().getAllAppenders();
		if (appenders.hasMoreElements()) {
			return false;
		} else {
			Enumeration loggers = LogManager.getCurrentLoggers();
			while (loggers.hasMoreElements()) {
				Logger c = (Logger) loggers.nextElement();
				if (c.getAllAppenders().hasMoreElements())
					return false;
			}
		}
		return true;
	}
}
