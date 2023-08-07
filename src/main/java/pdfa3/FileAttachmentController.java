package pdfa3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;

public class FileAttachmentController {

	private List<PDComplexFileSpecification> oldFileSpecList;
	private COSArray cosArray;
	public String[] emfile;
	/**
	 * Default constructor
	 */
	public FileAttachmentController() {
		cosArray = new COSArray();
	}
	
	/**
	 * Embed target file into PDF document
	 * @param document A PDF document as PDDocument instance
	 * @param embeddedFilePath A URL or path to target file
	 * @return A PDF document with embedded file
	 * @exception IOException Exceptions occur during embed process
	 */
	public PDDocument embedFile(PDDocument document, String[] embeddedFilePath, EmbedType emdedType) throws IOException {
		oldFileSpecList = new ArrayList<PDComplexFileSpecification>();
		String[] inboundFileNamesList = embeddedFilePath;
		PDDocumentNameDictionary namesDictionary = new PDDocumentNameDictionary(document.getDocumentCatalog());
		
		PDEmbeddedFilesNameTreeNode efTree = null;
		if (namesDictionary.getEmbeddedFiles() != null) {
			efTree = namesDictionary.getEmbeddedFiles();
			extractFilesFromEFTree(efTree);
		} else {
			efTree = new PDEmbeddedFilesNameTreeNode();
		}
		int i = 0;
		List<PDEmbeddedFilesNameTreeNode> kids = new ArrayList<PDEmbeddedFilesNameTreeNode>();

		if (emdedType == EmbedType.ADD) {
			for (PDComplexFileSpecification oldFileSpec : oldFileSpecList) {
				PDEmbeddedFile embeddedFile = getEmbeddedFile(oldFileSpec);
				i++;
				embedOldFile(document, kids, oldFileSpec, embeddedFile, i);
			}
		}
		
		for (String fileName : inboundFileNamesList) {
			i++;
			attachFileNewFile(document, kids, fileName, i);
		}
		efTree.setKids(kids);
		// add the tree to the document catalog
		PDDocumentNameDictionary names = new PDDocumentNameDictionary(document.getDocumentCatalog());
		names.setEmbeddedFiles(efTree);
		document.getDocumentCatalog().getCOSObject().setItem("AF", cosArray);
		document.getDocumentCatalog().setNames(names);
		// document.save("test_out.pdf");
		return document;
	}

	/**
	 * Embed new file into PDF document
	 * @param pDDocument A PDF document as PDDocument instance
	 * @param kids A list of PDEmbeddedFilesNameTreeNode
	 * @param fileName A file name to be embed
	 * @param fileIndex An index of embedded file
	 * @exception IOException Exceptions occur during embed process
	 */
	private void attachFileNewFile(PDDocument pdDocument, List<PDEmbeddedFilesNameTreeNode> kids,
			String fileName, int fileIndex) throws IOException {
		try {
			
			File file = new File(fileName);
			
			//find mimetype
			URLConnection connection = file.toURL().openConnection();
		    String mimeType = connection.getContentType();

			PDComplexFileSpecification complexFileSpec = new PDComplexFileSpecification();
			complexFileSpec.getCOSObject().setName("AFRelationship", "Alternative");
			complexFileSpec.getCOSObject().setString("UF", file.getName());
			complexFileSpec.getCOSObject().setString("F", file.getName());
			complexFileSpec.setFileUnicode(fileName);

			// now lets some of the optional parameters
			PDEmbeddedFile embededFile = new PDEmbeddedFile(pdDocument, new FileInputStream(file));
			embededFile.setSize((int) file.length());
			embededFile.setCreationDate(Calendar.getInstance());
			embededFile.setModDate(new GregorianCalendar());
			embededFile.setSubtype(mimeType);
			complexFileSpec.setEmbeddedFile(embededFile);
			// create a new tree node and add the embedded file
			PDEmbeddedFilesNameTreeNode treeNode = new PDEmbeddedFilesNameTreeNode();
			treeNode.setNames(Collections.singletonMap("File Index" + fileIndex, complexFileSpec));
			kids.add(treeNode);
			
			cosArray.add(complexFileSpec);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Embed old file into PDF document
	 * @param pdDocument A PDF document as PDDocument instance
	 * @param kids A list of PDEmbeddedFilesNameTreeNode
	 * @param fileSpecification A complex specification for embed file as PDComplexFileSpecification instance
	 * @param embeddedFile An actual embed file from input PDF file 
	 * @param fileIndex An index of embedded file
	 * @exception IOException Exceptions occur during embed process
	 */
	private void embedOldFile(PDDocument pdDocument, List<PDEmbeddedFilesNameTreeNode> kids,
			PDComplexFileSpecification fileSpecification, PDEmbeddedFile embeddedFile, int fileIndex) throws IOException {
		try {
			fileSpecification.setEmbeddedFile(embeddedFile);
			// create a new tree node and add the embedded file
			PDEmbeddedFilesNameTreeNode treeNode = new PDEmbeddedFilesNameTreeNode();
			treeNode.setNames(Collections.singletonMap("File Index" + fileIndex, fileSpecification));

			kids.add(treeNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Extract list of file specification from input PDF
	 * @param efTree An embedded file specification list from input PDF
	 * @exception IOException Exceptions occur during embed process
	 */
	private void extractFilesFromEFTree(PDEmbeddedFilesNameTreeNode efTree) throws IOException {
		Map<String, PDComplexFileSpecification> names = efTree.getNames();
		if (names != null) {
			extractFiles(names);
		} else {
			List<PDNameTreeNode<PDComplexFileSpecification>> kids = efTree.getKids();
			for (PDNameTreeNode<PDComplexFileSpecification> node : kids) {
				names = node.getNames();
				extractFiles(names);
			}
		}
	}
	
	/**
	 * Extract embedded file from input PDF
	 * @param names A key-value pair contains name and file specification
	 * @exception IOException Exceptions occur during embed process
	 */
	private void extractFiles(Map<String, PDComplexFileSpecification> names) throws IOException {
		for (Entry<String, PDComplexFileSpecification> entry : names.entrySet()) {
			PDComplexFileSpecification fileSpec = entry.getValue();
			PDEmbeddedFile embeddedFile = getEmbeddedFile(fileSpec);
			if (embeddedFile != null) {
				// extractFile(filePath, fileSpec.getFilename(), embeddedFile);
				oldFileSpecList.add(fileSpec);
				cosArray.add(fileSpec);
			}
		}
	}

	/**
	 * Get embedded file type from input PDF
	 * @param fileSpecification A file specification for embedded file
	 * @return An embedded file as PDEmbeddedFile instance
	 * @exception IOException Exceptions occur during embed process
	 */
	private PDEmbeddedFile getEmbeddedFile(PDComplexFileSpecification fileSpecification) {
		// search for the first available alternative of the embedded file
		PDEmbeddedFile embeddedFile = null;
		if (fileSpecification != null) {
			embeddedFile = fileSpecification.getEmbeddedFileUnicode();
			if (embeddedFile == null) {
				embeddedFile = fileSpecification.getEmbeddedFileDos();
			}
			if (embeddedFile == null) {
				embeddedFile = fileSpecification.getEmbeddedFileMac();
			}
			if (embeddedFile == null) {
				embeddedFile = fileSpecification.getEmbeddedFileUnix();
			}
			if (embeddedFile == null) {
				embeddedFile = fileSpecification.getEmbeddedFile();
			}
		}
		return embeddedFile;
	}

	public String[] getEmfile() {
		return emfile;
	}

	public void setEmfile(String[] emfile) {
		this.emfile = emfile;
	}

}
