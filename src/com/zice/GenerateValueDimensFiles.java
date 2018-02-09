package com.zice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class GenerateValueDimensFiles {

	private String dirStr = "./res";
	
	private final static String LAND_DP_WIDTH_TEMPLATE = "    <dimen name=\"w_{0}px_land\">{1}dp</dimen>\n";
	private final static String LAND_DP_HEIGHT_TEMPLATE = "    <dimen name=\"h_{0}px_land\">{1}dp</dimen>\n";
	private final static String LAND_SP_TEMPLATE = "    <dimen name=\"_font_{0}px_land\">{1}sp</dimen>\n";
	
	
	private final static String PORT_DP_WIDTH_TEMPLATE = "    <dimen name=\"w_{0}px_port\">{1}dp</dimen>\n";
	private final static String PORT_DP_HEIGHT_TEMPLATE = "    <dimen name=\"h_{0}px_port\">{1}dp</dimen>\n";
	private final static String PORT_SP_TEMPLATE = "    <dimen name=\"_font_{0}px_port\">{1}sp</dimen>\n";


	private final static String VALUE_TEMPLATE = "values-{dpi}";

	private static final String SUPPORT_DIMESION = "mdpi;hdpi;sw800dp-hdpi;xhdpi;xxhdpi;xxxhdpi;";

	private String supportStr = SUPPORT_DIMESION;

	
	private static boolean isHasTablet = false;
	
	/**
	 * 320x480 480x800 720x1280 1080x1920 1440x2560?(不稳定) mdpi hdpi xhdpi xxhdpi
	 * xxxhdpi 1 1.5 2 3 4
	 */
	int[] mdpi = { 320, 480 };
	int[] hdpi = { 480, 800 };
	int[] xhdpi = { 720, 1280 };
	int[] xxhdpi = { 1080, 1920 };
	int[] xxxhdpi = { 1440, 2560 };

	int[] hdpi_1920x1200 = { 1920, 1200 };

	int targetWidth = 0;
	int targetHeight = 0;

	public GenerateValueDimensFiles() {
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

		// 若设置 isHasTablet = true 则输出sw800dp-hdpi文件夹
		if (val.equals("sw800dp-hdpi") && !isHasTablet) {
			return;
		}

		StringBuffer sbForWidth = new StringBuffer();
		sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForWidth.append("<resources>\n");

		float i = 0;

		if (val.equals("mdpi")) {
			targetWidth = mdpi[0];
			targetHeight = mdpi[1];
			i = 1f;
		} else if (val.equals("hdpi")) {
			targetWidth = hdpi[0];
			targetHeight = hdpi[1];
			i = 1.5f;
		} else if (val.equals("xhdpi")) {
			targetWidth = xhdpi[0];
			targetHeight = xhdpi[1];
			i = 2f;
		} else if (val.equals("xxhdpi")) {
			targetWidth = xxhdpi[0];
			targetHeight = xxhdpi[1];
			i = 3f;
		} else if (val.equals("xxxhdpi")) {
			targetWidth = xxxhdpi[0];
			targetHeight = xxxhdpi[0];
			i = 4f;
		} else if (val.equals("sw800dp-hdpi")) {
			targetWidth = hdpi_1920x1200[0];
			targetHeight = hdpi_1920x1200[1];
			i = 1.5f;
		}

		// 竖屏 values文件夹
		if (!val.equals("sw800dp-hdpi")) {
			
			float width = 1080f;
			float height = 1920f;

			sbForWidth.append("\n");
			sbForWidth.append("    <!-- =====  ===== -->\n");

			for (int j = 1; j <= width; j++) {
				// 百分比
				float percent = j / width;
				// 百分比后像素值
				float percentPx = targetWidth * percent;
				// dp值
				int dpNumber = Math.round(percentPx / i);

				sbForWidth.append(PORT_DP_WIDTH_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
			}

			sbForWidth.append("\n");
			sbForWidth.append("    <!-- ===== Phone height_dp ===== -->\n");

			for (int j = 1; j <= height; j++) {
				// 百分比
				float percent = j / height;
				// 百分比后像素值
				float percentPx = targetHeight * percent;
				// dp值
				int dpNumber = Math.round(percentPx / i);

				sbForWidth.append(PORT_DP_HEIGHT_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
			}

			sbForWidth.append("\n\n");
			sbForWidth.append("    <!-- ===== Phone sp ===== -->\n");

			for (int j = 1; j <= height; j++) {
				// 百分比
				float percent = j / height;
				// 百分比后像素值
				float percentPx = targetHeight * percent;
				// dp值
				int dpNumber = Math.round(percentPx / i);

				sbForWidth.append(PORT_SP_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
			}
		}
			
		// 设置 isHasTablet true 输出 land values
		if (isHasTablet) {
			if (val.equals("sw800dp-hdpi")) {
				// Tablet dp sp
				sbForWidth.append("\n");
				sbForWidth.append("    <!-- =====  ===== -->\n");
				
				float tabletWidth = 1920f;
				float tabletHeight = 1200f;

				for (int j = 1; j <= tabletWidth; j++) {
					// 百分比
					float percent = j / tabletWidth;
					// 百分比后像素值
					float percentPx = targetWidth * percent;
					// dp值
					int dpNumber = Math.round(percentPx / i);					
					sbForWidth.append(LAND_DP_WIDTH_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
				}
				
				for (int j = 1; j <= tabletHeight; j++) {
					// 百分比
					float percent = j / tabletHeight;
					// 百分比后像素值
					float percentPx = tabletHeight * percent;
					// dp值
					int dpNumber = Math.round(percentPx / i);		
					sbForWidth.append(LAND_DP_HEIGHT_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
				}
				
				sbForWidth.append("\n\n");
				sbForWidth.append("    <!-- ===== Table sp ===== -->\n");

				for (int j = 1; j <= tabletHeight; j++) {
					// 百分比
					float percent = j / tabletHeight;
					// 百分比后像素值
					float percentPx = tabletHeight * percent;
					// sp值
					int dpNumber = Math.round(percentPx / i);
					sbForWidth.append(LAND_SP_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
				}
			}else {
				float width = 1920f;
				float height = 1080f;

				sbForWidth.append("\n");
				sbForWidth.append("    <!-- ===== Phone width_dp ===== -->\n");

				for (int j = 1; j <= width; j++) {
					// 百分比
					float percent = j / width;
					// 百分比后像素值
					float percentPx = targetWidth * percent;
					// dp值
					int dpNumber = Math.round(percentPx / i);

					sbForWidth.append(LAND_DP_WIDTH_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
				}

				sbForWidth.append("\n");
				sbForWidth.append("    <!-- ===== Phone height_dp ===== -->\n");

				for (int j = 1; j <= height; j++) {
					// 百分比
					float percent = j / height;
					// 百分比后像素值
					float percentPx = targetHeight * percent;
					// dp值
					int dpNumber = Math.round(percentPx / i);

					sbForWidth.append(LAND_DP_HEIGHT_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
				}

				sbForWidth.append("\n\n");
				sbForWidth.append("    <!-- ===== Phone sp ===== -->\n");

				for (int j = 1; j <= height; j++) {
					// 百分比
					float percent = j / height;
					// 百分比后像素值
					float percentPx = targetHeight * percent;
					// dp值
					int dpNumber = Math.round(percentPx / i);

					sbForWidth.append(LAND_SP_TEMPLATE.replace("{0}", j + "").replace("{1}", dpNumber + ""));
				}
			}
				
		}

		sbForWidth.append("\n</resources>");

		File fileDir = new File(dirStr + File.separator + VALUE_TEMPLATE.replace("{dpi}", val));
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

		try {
			if (args.length != 0) {
				switch (args.length) {
				case 1:
					isHasTablet = Boolean.parseBoolean(args[1]);
					break;
				default:
					break;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			// 终止程序
			System.exit(-1);
		}
		new GenerateValueDimensFiles().generate();

	}
}
