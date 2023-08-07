package pdfa3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.jempbox.xmp.XMPMetadata;
import org.apache.jempbox.xmp.XMPSchemaBasic;
import org.apache.jempbox.xmp.XMPSchemaDublinCore;
import org.apache.jempbox.xmp.XMPSchemaPDF;
import org.apache.jempbox.xmp.pdfa.XMPMetadataPDFA;
import org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAId;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MetaDataController {
	
	/*
	 * An MetaDataController's default constructor
	 */
	public MetaDataController() {
	}
	
	/**
	 * Set and Get document's metadata
	 * @param metaDataFilePath A URL represents relative or absolute path to JSON file
	 * @param document A PDF document as PDDocument instance
	 * @return A document's metadata as XMPMetadata instance
	 * @exception IOException Exceptions occur during read metadata process
	 */
	public XMPMetadata getMetaData(String metaDataFilePath, PDDocument document) throws IOException {
		return generateOutputMetaData(document, readInputFileMetadata(document), readMetaDataConfigFile(metaDataFilePath));
	}
	
	/**
	 * Set and Get document's metadata
	 * @param metaData A MetaData object contains document metadata
	 * @param document A PDF document as PDDocument instance
	 * @return A document's metadata as XMPMetadata instance
	 * @exception IOException Exceptions occur during read metadata process
	 */
	public XMPMetadata getMetaData(MetaData metaData, PDDocument document) throws IOException {
		return generateOutputMetaData(document, readInputFileMetadata(document), metaData);
	}
	
	/**
	 * Read metadata from input PDF file
	 * @param document A PDF document as PDDocument instance
	 * @return A document's metadata as MetaData instance
	 * @exception IOException Exceptions occur during read metadata process
	 */
	private MetaData readInputFileMetadata(PDDocument document) {
		MetaData metaData = new MetaData();
		
		PDDocumentInformation currentDocumentInfo = document.getDocumentInformation();
		metaData.setTitle(currentDocumentInfo.getTitle());
		metaData.setSubject(currentDocumentInfo.getSubject());
		metaData.setAuthor(currentDocumentInfo.getAuthor());
		metaData.setCreator(currentDocumentInfo.getCreator());
		metaData.setProducer(currentDocumentInfo.getProducer());
		metaData.setKeywords(currentDocumentInfo.getKeywords());
		metaData.setCreationDate(currentDocumentInfo.getCreationDate());
		metaData.setModificationDate(currentDocumentInfo.getModificationDate());
		metaData.setTrapped(currentDocumentInfo.getTrapped());
		
		return metaData;
	}
	
	/**
	 * Set metadata into PDF document
	 * @param document A PDF document as PDDocument instance
	 * @param inputPDFA3MetaData A metadata from input PDf File
	 * @param outputPDFA3MetaData A metadata for output PDF file
	 * @return A document's metadata as XMPMetadata instance
	 * @exception IOException Exceptions occur during read metadata process
	 */
	private XMPMetadataPDFA generateOutputMetaData(PDDocument document, MetaData inputPDFA3MetaData, MetaData outputPDFA3MetaData) throws IOException {
			
		PDDocumentInformation outputDocumentInfo = new PDDocumentInformation();
		outputDocumentInfo.setTitle(outputPDFA3MetaData.getTitle() != null ? outputPDFA3MetaData.getTitle() : inputPDFA3MetaData.getTitle());
		outputDocumentInfo.setSubject(outputPDFA3MetaData.getSubject() != null ? outputPDFA3MetaData.getSubject() : inputPDFA3MetaData.getSubject());
		// Author
		outputDocumentInfo.setCreator(outputPDFA3MetaData.getAuthor() != null ? outputPDFA3MetaData.getAuthor() : inputPDFA3MetaData.getAuthor());
		outputDocumentInfo.setProducer(outputPDFA3MetaData.getProducer() != null ? outputPDFA3MetaData.getProducer() : inputPDFA3MetaData.getProducer());
		outputDocumentInfo.setKeywords(outputPDFA3MetaData.getKeywords() != null ? outputPDFA3MetaData.getKeywords() : inputPDFA3MetaData.getKeywords());
		outputDocumentInfo.setCreationDate(inputPDFA3MetaData.getCreationDate());
		outputDocumentInfo.setModificationDate(Calendar.getInstance());
		
		
        XMPMetadataPDFA metadata = new XMPMetadataPDFA();

        XMPSchemaPDFAId pdfaID = metadata.addPDFAIdSchema();
        pdfaID.setPart(3);
	    pdfaID.setConformance("U");
	    pdfaID.setAbout("");
	      
	    XMPSchemaPDF pdfSchema=metadata.addPDFSchema();
	    pdfSchema.setKeywords(outputPDFA3MetaData.getKeywords() != null ? outputPDFA3MetaData.getKeywords() : inputPDFA3MetaData.getKeywords());
		pdfSchema.setAbout("");
		    
		XMPSchemaBasic basicSchema=metadata.addBasicSchema();
		basicSchema.setModifyDate(Calendar.getInstance());
		basicSchema.setCreateDate(outputDocumentInfo.getCreationDate());
		basicSchema.setMetadataDate(new GregorianCalendar());
		basicSchema.setAbout("");
		  
		XMPSchemaDublinCore dcSchema=metadata.addDublinCoreSchema();
		dcSchema.setTitle(outputDocumentInfo.getTitle());
		dcSchema.addCreator(outputDocumentInfo.getCreator());
		dcSchema.setDescription(outputDocumentInfo.getSubject());
		dcSchema.setAbout("");
        
        return metadata;

	}
	
	/**
	 * Read metadata from JSON file
	 * @param metaDataFilePath A URL or path to JSON file
	 * @return A document's metadata as MetaData instance
	 * @throws FileNotFoundException 
	 * @exception FileNotFoundException Exceptions occur when specific JSON file is not found
	 */
	private MetaData readMetaDataConfigFile(String metaDataFilePath) throws FileNotFoundException {
		if (!metaDataFilePath.trim().equals("")) {
			if (!(new File(metaDataFilePath)).exists()) {
				throw new FileNotFoundException();
			}
			
			MetaData metaData = new MetaData();
			
			try {
				JSONParser parser = new JSONParser();
				Object object = parser.parse(new FileReader(metaDataFilePath));
				JSONObject jsonObject = (JSONObject) object;
				
				metaData.setTitle(jsonObject.get("Title") != null ? jsonObject.get("Title").toString().trim() : "");
				metaData.setSubject(jsonObject.get("Subject") != null ? jsonObject.get("Subject").toString().trim() : "");
				metaData.setAuthor(jsonObject.get("Author") != null ? jsonObject.get("Author").toString().trim() : "");
				metaData.setCreator(jsonObject.get("Creator") != null ? jsonObject.get("Creator").toString().trim() : "");
				metaData.setProducer(jsonObject.get("Producer") != null ? jsonObject.get("Producer").toString().trim() : "");
				metaData.setKeywords(jsonObject.get("Keywords") != null ? jsonObject.get("Keywords").toString().trim() : "");
				metaData.setTrapped(jsonObject.get("Trapped") != null ? jsonObject.get("Trapped").toString().trim() : "");
				
			} catch (Exception ex) {
				metaData.setTitle("");
				metaData.setSubject("");
				metaData.setAuthor("");
				metaData.setCreator("");
				metaData.setProducer("");
				metaData.setKeywords("");
				metaData.setTrapped("");
			}	
			return metaData;
		} else {
			MetaData metaData = new MetaData();
			metaData.setTitle("");
			metaData.setSubject("");
			metaData.setAuthor("");
			metaData.setCreator("");
			metaData.setProducer("");
			metaData.setKeywords("");
			metaData.setTrapped("");
			return metaData;
		}
		
		
	}
}
