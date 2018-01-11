package com.zice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class GenerateValueDimensFiles {
	
	private String dirStr = "./res";
	
	private final static String DP_Template = "    <dimen name=\"_{0}px\">{1}dp</dimen>\n";
	private final static String SP_Template = "    <dimen name=\"_font_{0}px\">{1}sp</dimen>\n";

	private final static String VALUE_TEMPLATE = "values-{dpi}";
	
	private static final String SUPPORT_DIMESION = "mdpi;hdpi;xhdpi;xxhdpi;xxxhdpi;";

	private String supportStr = SUPPORT_DIMESION;
	
	private int maxSize = 1920;
	
	public GenerateValueDimensFiles(int maxSize) {
		if (maxSize > 0) {
			this.maxSize = maxSize;
		}
		File dir = new File(dirStr);
		if (!dir.exists()) {
			dir.mkdir();
		}
		System.out.println(dir.getAbsoluteFile());
	}
	
	public void generate() {
		String[] vals = supportStr.split(";");
		for (String val : vals) {
			generateXmlFile(val);
		}
	}
	
	private void generateXmlFile(String val) {
		StringBuffer sbForWidth = new StringBuffer();
		sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForWidth.append("<resources>\n");
		
		float i = 0;
		
		if (val.equals("mdpi")) {
			i = 1f;
		}else if (val.equals("hdpi")) {
			i = 1.5f;
		}else if (val.equals("xhdpi")) {
			i = 2f;
		}else if (val.equals("xxhdpi")) {
			i = 3f;
		}else if (val.equals("xxxhdpi")) {
			i = 4f;
		}
		
		sbForWidth.append("\n");
		sbForWidth.append("    <!-- dp -->\n");
		
		for (int j = 1; j <= maxSize; j++) {
			int dpNum = Math.round(j/i);
			sbForWidth.append(DP_Template.replace("{0}", j + "").replace("{1}", dpNum + ""));
		}
		
		sbForWidth.append("\n\n");
		sbForWidth.append("    <!-- sp -->\n");
				
		for (int j = 1; j <= maxSize; j++) {
			int dpNum = Math.round(j/i);
			sbForWidth.append(SP_Template.replace("{0}", j + "").replace("{1}", dpNum + ""));
		}
		
		sbForWidth.append("\n</resources>");

		File fileDir = new File(dirStr + File.separator
				+ VALUE_TEMPLATE.replace("{dpi}", val));
		fileDir.mkdir();

		File dimensFile = new File(fileDir.getAbsolutePath(), "dimens.xml");
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(dimensFile));
			pw.print(sbForWidth.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		int maxSize = 0;
		try {
			if (args.length>=1) {
				maxSize = Integer.parseInt(args[0]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			// 终止程序
			System.exit(-1);
		}
		new GenerateValueDimensFiles(maxSize).generate();
		
	}
}
