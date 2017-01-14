package pdfcreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

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

	public void createPdf(File newHtmlFile, String htmlFileAsString, String htmlFilePath, String invoiceId)
			throws IOException, DocumentException {
		try {
			System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
			String dest = DEST.concat(invoiceId).concat(".pdf");
			

			
			ITextRenderer renderer = new ITextRenderer();

			// if you have html source in hand, use it to generate document
			// object
			renderer.setDocument(newHtmlFile);
			renderer.layout();

			String fileNameWithPath = dest;
			FileOutputStream fos = new FileOutputStream(fileNameWithPath);
			renderer.createPDF(fos);
			fos.close();

			System.out.println("File 2: '" + fileNameWithPath + "' created.");

		} catch (Exception e) {
			System.out.println("------------------------------------------------------");
			e.printStackTrace();
		}

	}
}
