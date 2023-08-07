package pdfa3;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
import org.apache.pdfbox.pdmodel.graphics.color.PDICCBased;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;


public class PDFA3Controller {

	private PDDocument document;
	private List<String> _pdfFileList, _attachmentFileList;
	
	/**
	 * Class constructor
	 * @param inputPDFFilePath A URL or path to input PDF file
	 * @param outputPDFFilePath A URL or path to save new PDF file
	 * @param metaDataFilePath A URL or path to JSON file contains PDF metadata
	 * @param embeddedFilePath A URL or path to embedded file
	 * @param embedType A type of embedded file to be embedded
	 * @param colorProfilePath A URL or path to color profile file
	 * @throws Exception
	 */
	public PDFA3Controller(String inputPDFFilePath, String outputPDFFilePath, String metaDataFilePath, String[] embeddedFilePath, EmbedType emdedType, String colorProfilePath, String fontFilePath) throws Exception {
		/*
		validateFolderAndFile(inputPDFFilePath, outputPDFFilePath, embeddedFilePath);
		
		for (int i=0; i<_pdfFileList.size(); i++) {
			
			String fileName = FilenameUtils.getName(_pdfFileList.get(i));
			if (_attachmentFileList.size() > 0) {
				convertToPDFA3(_pdfFileList.get(i), outputPDFFilePath + "\\" + fileName, metaDataFilePath, _attachmentFileList.get(i), emdedType, colorProfilePath, fontFilePath);
			} else {
				convertToPDFA3(_pdfFileList.get(i), outputPDFFilePath + "\\" + fileName, metaDataFilePath, null, emdedType, colorProfilePath, fontFilePath);
			}
		}		
		*/
		
//		validateFolderAndFile(inputPDFFilePath, outputPDFFilePath, embeddedFilePath);
//		
//		if (_pdfFileList.size() > 0) {
//			for (int i=0; i<_pdfFileList.size(); i++) {
//				
//				String fileName = FilenameUtils.getName(_pdfFileList.get(i));
//				
//				if (_attachmentFileList.size() > 0) {
//					convertToPDFA3(_pdfFileList.get(i), outputPDFFilePath + "\\" + fileName, metaDataFilePath, _attachmentFileList.get(i), emdedType, colorProfilePath, fontFilePath);
//				} else {
//					convertToPDFA3(_pdfFileList.get(i), outputPDFFilePath + "\\" + fileName, metaDataFilePath, null, emdedType, colorProfilePath, fontFilePath);
//				}
//			}
//		} else {
			if (embeddedFilePath != null) {
//				if (!embeddedFilePath.trim().equals("")) {
					convertToPDFA3(inputPDFFilePath, outputPDFFilePath, metaDataFilePath, embeddedFilePath, emdedType, colorProfilePath, fontFilePath);
//				} else {
//					convertToPDFA3(inputPDFFilePath, outputPDFFilePath, metaDataFilePath, null, emdedType, colorProfilePath, fontFilePath);
//				}
				
			} else {
				convertToPDFA3(inputPDFFilePath, outputPDFFilePath, metaDataFilePath, null, emdedType, colorProfilePath, fontFilePath);
			}
//		}
	}
	
	/**
	 * 
	 * @param inputPDFFilePath A URL or path to input PDF file
	 * @param outputPDFFilePath A URL or path to save new PDF file
	 * @param metaData A document metadata as MetaData instance
	 * @param embeddedFilePath A URL or path to embedded file
	 * @param embedType A type of embedded file to be embedded
	 * @param colorProfilePath A URL or path to color profile file
	 * @throws Exception
	 */
	public PDFA3Controller(String inputPDFFilePath, String outputPDFFilePath, MetaData metaData, String[] embeddedFilePath, EmbedType emdedType, String colorProfilePath, String fontFilePath) throws Exception {
		
//		validateFolderAndFile(inputPDFFilePath, outputPDFFilePath, embeddedFilePath);
		
//		if (_pdfFileList.size() > 0) {
//			for (int i=0; i<_pdfFileList.size(); i++) {
//				
//				String fileName = FilenameUtils.getName(_pdfFileList.get(i));
//				
//				if (_attachmentFileList.size() > 0) {
//					convertToPDFA3(_pdfFileList.get(i), outputPDFFilePath + "\\" + fileName, metaData, _attachmentFileList.get(i), emdedType, colorProfilePath, fontFilePath);
//				} else {
//					convertToPDFA3(_pdfFileList.get(i), outputPDFFilePath + "\\" + fileName, metaData, null, emdedType, colorProfilePath, fontFilePath);
//				}
//			}
//		} else {
			if (embeddedFilePath != null) {
//				if (!embeddedFilePath.trim().equals("")) {
					convertToPDFA3(inputPDFFilePath, outputPDFFilePath, metaData, embeddedFilePath, emdedType, colorProfilePath, fontFilePath);
//				} else {
//					convertToPDFA3(inputPDFFilePath, outputPDFFilePath, metaData, null, emdedType, colorProfilePath, fontFilePath);
//				}
				
			} else {
				convertToPDFA3(inputPDFFilePath, outputPDFFilePath, metaData, null, emdedType, colorProfilePath, fontFilePath);
			}
//		}
				
	}
	
	/**
	 * Convert input PDF file to PDF as PDF/A-3 compliance
	 * @param inputPDFFilePath A URL or path to input PDF file
	 * @param outputPDFFilePath A URL or path to save new PDF file
	 * @param metaDataFilePath A URL or path to JSON file contains PDF metadata
	 * @param embeddedFilePath A URL or path to embedded file
	 * @param embedType A type of embedded file to be embedded
	 * @param colorProfilePath A URL or path to color profile file
	 * @throws Exception
	 */
	public void convertToPDFA3(String inputPDFFilePath, String outputPDFFilePath, Object metaData, String[] embeddedFilePath, EmbedType embedType, String colorProfilePath, String fontFilePath) throws Exception {
		//Load PDF file to PDDocument
		
		document = PDDocument.load(new File(inputPDFFilePath));
		
		 if (document.isEncrypted()) {
			 throw new Exception("Input PDF file must not be encrypted");
		 }
		
		PDMetadata metadataStream=new PDMetadata(document);
		
		if (embeddedFilePath != null) {
			FileAttachmentController fileAttachment = new FileAttachmentController();
			fileAttachment.embedFile(document, embeddedFilePath ,embedType);
		}
		
		/*
		 * Embed font
		 */
		FontController fontController = new FontController();
		
		if (fontFilePath != null) {
			if (!fontFilePath.trim().equals("")) {
				fontController.embededFont(document, fontFilePath);
			}
		}
		
		PDOutputIntent outputIntent = null;
		
		if (colorProfilePath != null) {
			
			InputStream colorProfile = new FileInputStream(new File(colorProfilePath));
			outputIntent = new PDOutputIntent(document, colorProfile);
			outputIntent.setInfo("sRGB IEC61966-2.1");
			outputIntent.setOutputCondition("sRGB IEC61966-2.1");
			outputIntent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
			outputIntent.setRegistryName("http://www.color.org");
			
		}
		else
		{
//			try (InputStream colorProfile = PDFA3Controller.class.getResourceAsStream("/resources/sRGB Color Space Profile.icm")) {
			try (InputStream colorProfile = new FileInputStream(new File("./resources/sRGB Color Space Profile.icm"))) {
				outputIntent = new PDOutputIntent(document, colorProfile);
				outputIntent.setInfo("sRGB IEC61966-2.1");
				outputIntent.setOutputCondition("sRGB IEC61966-2.1");
				outputIntent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
				outputIntent.setRegistryName("http://www.color.org");
			}
		}

		PDDocumentCatalog catalog=document.getDocumentCatalog();
		
		/*
		 * If PDF has already contained Output intent then replace it,
		 * otherwise add new output intent to PDF
		 */
		if (outputIntent != null) {
			List<PDOutputIntent> outputIntentList = catalog.getOutputIntents();
			if (outputIntentList != null) {
				if (outputIntentList.size() > 0) {
					outputIntentList.set(0, outputIntent);
					catalog.setOutputIntents(outputIntentList);
				} else {
					catalog.addOutputIntent(outputIntent);
				}
				
			} else {
				catalog.addOutputIntent(outputIntent);
			}
		}
		
		MetaDataController metaDataController = new MetaDataController();
		if (metaData instanceof String) {
			metadataStream.importXMPMetadata(metaDataController.getMetaData((String)metaData, document).asByteArray());
		} else if (metaData instanceof MetaData) {
			metadataStream.importXMPMetadata(metaDataController.getMetaData((MetaData)metaData, document).asByteArray());
		}
		
		/*
		if (!metaData.toString().trim().equals("")) {
			MetaDataController metaDataController = new MetaDataController();
			if (metaData instanceof String) {
				metadataStream.importXMPMetadata(metaDataController.getMetaData((String)metaData, document).asByteArray());
			} else if (metaData instanceof MetaData) {
				metadataStream.importXMPMetadata(metaDataController.getMetaData((MetaData)metaData, document).asByteArray());
			}
		}
		*/
		
		
		catalog.setMetadata(metadataStream);
		
        File outputFile = new File(outputPDFFilePath);
        document.save( outputFile );
        document.close();
	}
	
	
	/**
	 * Get all PDF file from specific path if input is folder
	 * @param pdfFilePath The URL or path to input PDF file
	 */
	private void getPDFFileList(final File pdfFilePath) {
		 for (final File fileEntry : pdfFilePath.listFiles()) {
			 if (fileEntry.isDirectory()) {
	            	getPDFFileList(fileEntry);
	            } else {
	            	if (fileEntry.getName().endsWith(".pdf")) {
		                _pdfFileList.add(fileEntry.getAbsolutePath());
	            	}
	            }
		 }
	}
	
	/**
	 * Get all file from specific path if input is folder
	 * @param attachedFilePath The URL or path to embedded file
	 */
	private void getAttachmentFileList(final File attachedFilePath) {
		 for (final File fileEntry : attachedFilePath.listFiles()) {
			 if (fileEntry.isDirectory()) {
				 getAttachmentFileList(fileEntry);
	            } else {
	            	_attachmentFileList.add(fileEntry.getAbsolutePath());
	            }
		 }
	}
	
	/**
	 * Validate and classify input as string or folder
	 * @param inputPDFFilePath A URL or path to input PDF file
	 * @param outputPDFFilePath A URL or path to save new PDF file
	 * @param embeddedFilePath A URL or path to embedded file
	 * @throws Exception
	 */
	private void validateFolderAndFile(String inputPDFFilePath, String outputPDFFilePath, String embeddedFilePath) throws Exception {
		
		_pdfFileList = new ArrayList<String>();
		_attachmentFileList = new ArrayList<String>();
		
		File inputPath = new File(inputPDFFilePath);
		File outputPath = new File(outputPDFFilePath);
		
		if (inputPath.isDirectory()) {
			getPDFFileList(new File(inputPDFFilePath));
		}
		
		outputPath.createNewFile();
		
		/*
		 * Embedded file is not null
		 */
		if (embeddedFilePath != null) {
			
			File attachmentPath = new File(embeddedFilePath);
			if (attachmentPath.isDirectory()) {
				getAttachmentFileList(new File(embeddedFilePath));
			}
			
			if (inputPath.isFile() && outputPath.isFile() && attachmentPath.isFile()) {
				if (outputPath.exists()) {
					outputPath.delete();
				}
				return;
			} else if (inputPath.isDirectory() && outputPath.isDirectory() && attachmentPath.isDirectory()) {
				if (_pdfFileList.size() ==  _attachmentFileList.size()) {
					for (int i=0; i<_pdfFileList.size(); i++) {
						String pdfFileNameWithOutExt = FilenameUtils.removeExtension(FilenameUtils.getName(_pdfFileList.get(i)));
						for (int j=0; j<_attachmentFileList.size(); j++) {
							String attachmentFileNameWithOutExt = FilenameUtils.removeExtension(FilenameUtils.getName(_attachmentFileList.get(i)));
							
							if (pdfFileNameWithOutExt.equalsIgnoreCase(attachmentFileNameWithOutExt)) {
								//System.out.println(pdfFileNameWithOutExt + ":" + attachmentFileNameWithOutExt);
								break;
							} else if (i == _pdfFileList.size()-1) {
								throw new Exception("PDF and Attachment file must be the same name (Exclude extension)");
							}
						}
					}
				} else {
					throw new Exception("If input PDF is directory, "
							+ "number of attachment file must equal to number of PDF");
				}
			} else {
				throw new Exception("Input parameters must be files or folder all the same");
			}
		} 
		/*
		 * Embedded file is null
		 */
		else {
			if (inputPath.isFile() && outputPath.isFile()) {
				return;
			} else if (inputPath.isDirectory() && outputPath.isDirectory()) {
				return;
			} else {
				throw new Exception("Input parameters must be files or folder all the same");
			}
		}
	
	}

}
