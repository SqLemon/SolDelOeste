package melamed.soldeloesteapp;

import java.text.DecimalFormat;

public class CostFormatter {
	static String format(double v){
		return '$' + (new DecimalFormat("#,##0.00").format(v));
	}
}
