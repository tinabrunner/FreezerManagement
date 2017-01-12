package pdfcreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author Marius Koch
 *
 */
public class ParseHtml {
	public static final String DEST = "results/xmlworker/invoice";

	public ParseHtml() {
	}

	/**
	 * Creates a PDF
	 * 
	 * @param file
	 * @throws IOException
	 * @throws DocumentException
	 */

	public String createPdf(String htmlFilePath, String invoiceId) throws IOException, DocumentException {
		String dest = DEST.concat(invoiceId).concat(".pdf");
		File file = new File(dest);
		file.getParentFile().mkdirs();

		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		// step 3
		document.open();
		// step 4
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(htmlFilePath));
		// step 5
		document.close();

		return DEST;
	}
}
