package pdfcreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ParseHtml {
	public static final String DEST = "results/xmlworker/invoice.pdf";
	public static final String HTML = "src/main/webapp/app/invoice/invoice.html";

	public ParseHtml() {
		this.init();
	}

	public void init() {
		File file = new File(DEST);
		file.getParentFile().mkdirs();
	}

	/**
	 * Creates a PDF with the words "Hello World"
	 * 
	 * @param file
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void createPdf(String file) throws IOException, DocumentException {
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		// step 3
		document.open();
		// step 4
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(HTML));
		// step 5
		document.close();
	}
}
