import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JPaneDate {
	 public static final String DATE_FORMAT_NOW = "MM/dd/yyyy";
		public static String gimmieTimeNow() {
			// TODO Auto-generated method stub
			Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		    return sdf.format(cal.getTime());
		}
}
