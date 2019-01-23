package com.open.boot.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.open.boot.common.enumset.Constant;
import com.open.boot.pdf.model.Rock;

//利用itextpdf+adobe acrobat+pdf模板生成pdf文件
public class PdfTEmplate {

	/**
	 * 由模板生成对应的pdf文件
	 * 
	 * @param pdfTemplatePath 模板路径
	 * @param outPath         输出路径
	 * @param data            模板数据
	 * @throws Exception 异常
	 */
	public static void writePdfTemplate(String pdfTemplatePath, String outPath, Map<String, Object> data)
			throws Exception {
		// pdf模板
		String fileName = pdfTemplatePath;
		PdfReader reader = new PdfReader(fileName);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		/* 将要生成的目标PDF文件名称 */
		PdfStamper pdfStamper = new PdfStamper(reader, bos);
		/* 使用中文字体 */
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
		fontList.add(bfChinese);
		/* 取出报表模板中的所有字段 */
		AcroFields fields = pdfStamper.getAcroFields();
		fields.setSubstitutionFonts(fontList);
		// fillData(fields, data());
		fillData(fields, data);
		/* 必须要调用这个，否则文档不会生成的 */
		pdfStamper.setFormFlattening(true);
		pdfStamper.close();
		// 检测是否存在目录
		File dest = new File(outPath);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();// 新建文件夹
		}
		// 生成pdf路径
		FileOutputStream outputStream = new FileOutputStream(outPath);
		outputStream.write(bos.toByteArray());
		outputStream.flush();
		outputStream.close();
		bos.close();
	}

	/**
	 * 生成最终的pdf文件
	 * 
	 * @param newPath    传入pdf路径
	 * @param finalPath  最终路径
	 * @param lTs        数据
	 * @param newPage    是否新建页面
	 * @param pdfType    文档类型
	 * @param pageEndMap 页尾信息
	 * @throws Exception Exception
	 */
	public static <T> void generateFinalPdf(String newPath, String finalPath, List<T> lTs, boolean newPage,
			Constant.PdfEnum pdfType, Map<String, String> pageEndMap) throws Exception {
		File dest = new File(finalPath);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();// 新建文件夹
		}
		FileOutputStream outputStream = new FileOutputStream(finalPath);
		// 读取pdf模板
		PdfReader reader = new PdfReader(newPath);
		Rectangle pageSize = reader.getPageSize(1);
		// 默认PageSize.A4, 36, 36, 36, 36
		Document document = new Document(pageSize);
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);

		document.open();
		// 希望添加内容在图象或文本的背后
		PdfContentByte cbUnder = writer.getDirectContentUnder();
		// 使用此方法从其他PDF文档获取页面，the page number. The first page is 1
		PdfImportedPage pageTemplate = writer.getImportedPage(reader, 1);
		// the x,y location of this template
		cbUnder.addTemplate(pageTemplate, 0, 0);
		if (newPage) {
			document.newPage();// 新创建一页来存放后面生成的表格
		}
		Paragraph paragraph = generatePdfTable(lTs, pdfType);
		paragraph.setAlignment(Element.ALIGN_BOTTOM);
		document.add(paragraph);
		Phrase phrase = getPhrase(pageEndMap);
		Paragraph paragraph2 = new Paragraph();
		paragraph2.add(phrase);
		paragraph2.setFirstLineIndent(50);// 左边距
		paragraph2.setLeading(15);// 高度
		document.add(paragraph2);
		// 获得总页数
		// int pages = reader.getNumberOfPages();
		// 设置pdf属性
		// 标题
		document.addTitle("this is a title");
		// 作者
		document.addAuthor("actort");
		// 主题
		document.addSubject("this is subject");
		// 关键字
		document.addKeywords("Keywords");
		// 创建时间
		document.addCreationDate();
		// 应用程序
		document.addCreator("hd.com");

		// 关闭文档
		document.close();
		// 关闭书写器
		writer.close();
		reader.close();
	}

	private static Phrase getPhrase(Map<String, String> pageEndMap) throws Exception, IOException {
		if (StringUtils.isEmpty(pageEndMap.get("captainName"))) {
			pageEndMap.put("captainName", "无");
		}
		if (StringUtils.isEmpty(pageEndMap.get("staffName"))) {
			pageEndMap.put("staffName", "无");
		}
		if (StringUtils.isEmpty(pageEndMap.get("shrName"))) {
			pageEndMap.put("shrName", "无");
		}
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font fontChinese = new Font(bfChinese, 10.5F, Font.NORMAL);// 五号
		Phrase phrase = new Phrase();
		phrase.setLeading(30);
		Chunk chunk1 = new Chunk("            技术员：", fontChinese);
		Chunk chunk2 = new Chunk(pageEndMap.get("captainName"), new Font(bfChinese, 10.5F, Font.UNDERLINE));
		Chunk chunk3 = new Chunk("                                                                   技术员：",
				fontChinese);
		Chunk chunk4 = new Chunk(pageEndMap.get("staffName"), new Font(bfChinese, 10.5F, Font.UNDERLINE));
		Chunk chunk5 = new Chunk("                                                                   检查验收：",
				fontChinese);
		Chunk chunk6 = new Chunk(pageEndMap.get("shrName"), new Font(bfChinese, 10.5F, Font.UNDERLINE));
		phrase.add(chunk1);
		phrase.add(chunk2);
		phrase.add(chunk3);
		phrase.add(chunk4);
		phrase.add(chunk5);
		phrase.add(chunk6);
		return phrase;
	}

	/**
	 * 新增表格段落
	 * 
	 * @param lTs     list数据
	 * @param pdfType pdf类型
	 * @return paragraph
	 * @throws Exception 异常
	 */
	private static <T> Paragraph generatePdfTable(List<T> lTs, Constant.PdfEnum pdfType) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font fontChinese = new Font(bfChinese, 10.5F, Font.NORMAL);// 五号
		Paragraph paragraph = null;
		PdfPTable pdfTable = null;
		switch (pdfType) {
		case RECORD:
			paragraph = new Paragraph("钻探现场记录附表", fontChinese);
			// pdfTable = getRecordsPdfTable(fontChinese, lTs);
			break;
		case ROCK:
			paragraph = new Paragraph("岩芯钻探地质编录附表", fontChinese);
			pdfTable = getRockPdfTable(fontChinese, lTs);
			break;
		case GEOLOGY:
			paragraph = new Paragraph("岩土工程地质钻探编录附表", fontChinese);
			// pdfTable = getGeologyPdfTable(fontChinese, lTs);
			break;
		default:
			break;
		}
		paragraph.add(pdfTable);
		return paragraph;
	}

	// 得到岩土岩心的pdftable
	private static <T> PdfPTable getRockPdfTable(Font fontChinese, List<T> lTs) throws DocumentException, Exception {
		PdfPTable pdfPTable = new PdfPTable(13);
		// 跨页处理
		pdfPTable.setSplitLate(false);
		pdfPTable.setSplitRows(true);
		pdfPTable.setWidthPercentage(100); // 宽度100%填充
		pdfPTable.setSpacingBefore(126f); // 前间距170f
		pdfPTable.setSpacingAfter(20f); // 后间距
		// 设置列宽
		pdfPTable.setWidths(
				new float[] { 8.2f, 8.1f, 8.1f, 8.1f, 8.1f, 8.1f, 40.1f, 8.1f, 8.1f, 8.1f, 8.1f, 8.1f, 10.0f });// 每个单元格占多宽
		// 设置描述字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font descFont = new Font(bfChinese, 7.5F, Font.NORMAL);
		int index = 1;
		String startDepth = null;
		for (T t : lTs) {
			pdfPTable.addCell(getCell(new Phrase((String.valueOf(index)), fontChinese), false, 1, 1));
			if (index == 1) { // 判断起止深度
				pdfPTable.addCell(getCell(
						new Phrase(0 + "~" + integerToString(((Rock) t).getStratumDepth()), fontChinese), false, 1, 1));
			} else {
				pdfPTable.addCell(getCell(
						new Phrase(startDepth + "~" + integerToString(((Rock) t).getStratumDepth()), fontChinese),
						false, 1, 1));
			}
			startDepth = integerToString(((Rock) t).getStratumDepth());
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getStratumName(), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getWeathering(), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getCompletent(), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getHard(), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getStratumDesc(), descFont), false, 1, 1));
			if ("1".equals(((Rock) t).getIsChange())) {
				pdfPTable.addCell(
						getCell(new Phrase(integerToString(((Rock) t).getStratumDepth()), fontChinese), false, 1, 1));
			} else {
				pdfPTable.addCell(getCell(new Phrase("无变层", fontChinese), false, 1, 1));
			}
			pdfPTable.addCell(getCell(new Phrase((String.valueOf(index)), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getRockLength(), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getRqd(), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getAdoption(), fontChinese), false, 1, 1));
			pdfPTable.addCell(getCell(new Phrase(((Rock) t).getPitch(), fontChinese), false, 1, 1));
			index++;
		}
		return pdfPTable;
	}

	// 获取表cell
	private static PdfPCell getCell(Phrase phrase, boolean yellowFlag, int colSpan, int rowSpan) {
		PdfPCell cells = new PdfPCell(phrase);
		cells.setUseAscender(true);
		cells.setMinimumHeight(25);// 单元格的最小高度
		cells.setColspan(colSpan);
		cells.setRowspan(rowSpan);
		cells.setNoWrap(false);
		// cells.setPaddingLeft(20);// 左填充20
		// cells.setFixedHeight(100);// 是用来设置每一行的高度，可以自己设置
		cells.setHorizontalAlignment(Element.ALIGN_CENTER);// 水平居中
		cells.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		return cells;
	}

	/**
	 * 填充模板
	 * 
	 * @param fields 文本域
	 * @param data   填充数据
	 */
	public static void fillData(AcroFields fields, Map<String, Object> data) {
		for (String key : data.keySet()) {
			String value = data.get(key).toString();
			try {
				fields.setField(key, value);
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// integer类型转换为字符串
	public static String integerToString(Object value) {
		if (value != null) {
			return value.toString();
		} else {
			return "-";
		}
	}
}
