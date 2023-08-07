package pdfa3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

public class FontController {
	private List<String> _fontFilePathList;
	
	/**
	 * Class constructor
	 */
	public FontController() {
		_fontFilePathList = new ArrayList<String>();
	}
	
	/**
	 * Embed font into PDF File
	 * @param document PDF document as PDDocument instance
	 * @param fontPathList A URL or path to font file
	 * @return PDF document with embedded font as PDDocument Instance
	 * @throws IOException
	 */
	public PDDocument embededFont(PDDocument document, String fontPathList) throws IOException {
		
		File file = new File(fontPathList);
		
		if (file.isDirectory()) {
			getFontFile(file);
		} else if (file.isFile()) {
			_fontFilePathList.add(fontPathList);
		}
		
		setResource(document);
		
		return document;
	}
	
	/**
	 * Loop through folder to get font file
	 * @param fontFilePath
	 */
	private void getFontFile(final File fontFilePath) {
		 for (final File fileEntry : fontFilePath.listFiles()) {
			 if (fileEntry.isDirectory()) {
				 getFontFile(fileEntry);
	            } else {
	            	if (fileEntry.getName().toLowerCase().endsWith(".ttf")) {
	            		_fontFilePathList.add(fileEntry.getAbsolutePath());
	            	}
	            }
		 }
	}
	
	/**
	 * Set font to PDF file as a resource
	 * @param document A PDF document as PDDocument instance
	 * @throws IOException
	 */
	public void setResource(PDDocument document) throws IOException {
		for (int i = 0; i < document.getNumberOfPages(); ++i)
		{
		    PDPage page = document.getPage(i);
		    PDResources res = page.getResources();
		    
		    for (String fontPath : _fontFilePathList) {
				File fontFile = new File(fontPath);
				res.add(PDTrueTypeFont.load(document, fontFile, WinAnsiEncoding.INSTANCE));
		    }
		}
	}
}
