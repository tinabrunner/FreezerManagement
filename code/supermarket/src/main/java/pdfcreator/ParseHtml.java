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
	public static final String DEST = "C:/Program Files/glassfish4.0/glassfish4/glassfish/domains/supermarket/docroot/";

	public ParseHtml() {
	}

	/**
	 * Creates a PDF
	 * 
	 * @param file
	 * @throws IOException
	 * @throws DocumentException
	 */

	public void createPdf(String htmlFilePath, String invoiceId) throws IOException, DocumentException {
		try {
			System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
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
		} catch (Exception e) {
			System.out.println("------------------------------------------------------");
			e.printStackTrace();
		}

	}
}
