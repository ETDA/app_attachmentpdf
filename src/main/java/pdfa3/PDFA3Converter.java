package pdfa3;

import java.io.File;
import java.io.FileNotFoundException;

public class PDFA3Converter {
	
	private String _inputPDFFilePath, _outputPDFFilePath, _colorProfilePath, _metaDataFilePath, _fontFilePath;
	private String[] _embedFilePath;
	private MetaData _metaData;
	private EmbedType _embedType;
	
	/*
	 * Default constructor
	 */
	public PDFA3Converter() {
		
	}
	
	/**
	 * Overload constructor
	 * @param inputPDFFilePath A URL or path to input PDF file
	 * @param outputPDFFilePath A URL or path to save output PDF file
	 * @param metaData A document metadata as MetaData instance
	 * @param embedFilePath A URL or path to embedded file
	 * @param emdType A type of embedded file to be embedded
	 * @param embedType A type of embedded as instance of EmbedType
	 * @param colorProfilePath A URL or path to color profile file
	 * @throws Exception
	 */
	public PDFA3Converter(String inputPDFFilePath, String outputPDFFilePath, MetaData metaData, String[] embedFilePath, EmbedType embedType, String colorProfilePath, String fontFilePath)  throws Exception {
		setInputPDFFile(inputPDFFilePath);
		setOutputPDFFile(outputPDFFilePath);
		setMetaData(metaData);
		setEmbedFile(embedFilePath, embedType);
		setColorProfile(colorProfilePath);
		setFontFilePath(fontFilePath);
	}
	
	/**
	 * 
	 * @param inputPDFFilePath A URL or path to input PDF file
	 * @param outputPDFFilePath A URL or path to save output PDF file
	 * @param metaDataFilePath A URL or path to JSON file contains document metadata
	 * @param embedFilePath A URL or path to embedded file
	 * @param emdType A type of embedded file to be embedded
	 * @param embedType A type of embedded as instance of EmbedType
	 * @param colorProfilePath A URL or path to color profile file
	 * @throws Exception
	 */
	public PDFA3Converter(String inputPDFFilePath, String outputPDFFilePath, String metaDataFilePath, String[] embedFilePath, EmbedType embedType, String colorProfilePath, String fontFilePath) throws Exception {
		setInputPDFFile(inputPDFFilePath);
		setOutputPDFFile(outputPDFFilePath);
		setMetaData(metaDataFilePath);
		setEmbedFile(embedFilePath, embedType);
		setColorProfile(colorProfilePath);
		setFontFilePath(fontFilePath);
	}
	
	/**
	 * Convert PDF file to PDF/A-3 compliance
	 * @return Result of operation as Boolean
	 * @throws Exception
	 */
	public void convert() throws Exception {
		if (_metaData != null) {
			new PDFA3Controller(
					_inputPDFFilePath, 
					_outputPDFFilePath, 
					_metaData, 
					_embedFilePath,
					_embedType,
					_colorProfilePath,
					_fontFilePath
					);
			//return true;
		} else if (_metaDataFilePath != null) {
			new PDFA3Controller(
					_inputPDFFilePath, 
					_outputPDFFilePath, 
					_metaDataFilePath, 
					_embedFilePath,
					_embedType,
					_colorProfilePath,
					_fontFilePath
					);
			//return true;
		} else {
			new PDFA3Controller(
					_inputPDFFilePath, 
					_outputPDFFilePath, 
					"", 
					_embedFilePath,
					_embedType,
					_colorProfilePath,
					_fontFilePath
					);
		}
	}
	
	/*
	 * @exception FileNotFoundException
	 * @exception UnrecognizedEmbedType
	 */
	public void setEmbedFile(String[] embedfile, EmbedType embedType) throws Exception {
		
		// Throw FileNotFoundException when input file does not exist
		if (embedfile == null) {
			_embedFilePath = null;
		} else {
			for (String fileName : embedfile) {
				if(!(new File(fileName)).exists()) {
					throw new FileNotFoundException("Embed file: " + fileName + " was not found.");
				}
			}
		}
		
		this._embedFilePath = embedfile;
		
		if (embedType == null) {
			setEmbedType(EmbedType.ADD);
		} else if (embedType == EmbedType.REPLACE || embedType == EmbedType.ADD) {
			setEmbedType(embedType);
		} else {
			throw new Exception("Unrecognized Embed Type.");
		}
	}

	/**
	 * @return the _inputPDFFilePath
	 */
	public String getInputPDFFile() {
		return _inputPDFFilePath;
	}

	/**
	 * @param _inputPDFFilePath the inputPDFFilePath to set
	 * @throws FileNotFoundException 
	 */
	public void setInputPDFFile(String inputPDFFilePath) throws FileNotFoundException {
		if (!(new File(inputPDFFilePath)).exists()) {
			throw new FileNotFoundException("Input file: " + inputPDFFilePath + " was not found.");
		}
		this._inputPDFFilePath = inputPDFFilePath;
	}

	/**
	 * @return the _outputPDFFilePath
	 */
	public String getOutputPDFFile() {
		return _outputPDFFilePath;
	}

	/**
	 * @param _outputPDFFilePath the _outputPDFFilePath to set
	 * @throws FileNotFoundException 
	 */
	public void setOutputPDFFile(String outputPDFFilePath) throws FileNotFoundException {
		if ((new File(outputPDFFilePath)).isDirectory()) {
			if (!(new File(outputPDFFilePath)).exists()) {
				throw new FileNotFoundException();
			}
		}
		
		this._outputPDFFilePath = outputPDFFilePath;
	}

	/**
	 * @return the _colorProfilePath
	 */
	public String getColorProfile() {
		return _colorProfilePath;
	}

	/**
	 * @param _colorProfilePath the _colorProfilePath to set
	 */
	public void setColorProfile(String colorProfilePath) {
		this._colorProfilePath = colorProfilePath;
	}

	/**
	 * @return the _metaData
	 */
	public MetaData getMetaData() {
		return _metaData;
	}
	
	/**
	 * @return the _metaData
	 */
	public String getMetaDataFilePath() {
		return _metaDataFilePath;
	}

	/**
	 * @param _metaData the _metaData to set
	 */
	public void setMetaData(MetaData metaData) {
		this._metaData = metaData;
		this._metaDataFilePath = null;
	}

	/**
	 * @param _metaData the _metaData to set
	 */
	public void setMetaData(String metaDataFilePath) {
		this._metaDataFilePath = metaDataFilePath;
		this._metaData = null;
	}

	/**
	 * @return the _fontFilePath
	 */
	public String getFontFilePath() {
		return _fontFilePath;
	}

	/**
	 * @param _fontFilePath the _fontFilePath to set
	 */
	public void setFontFilePath(String _fontFilePath) {
		this._fontFilePath = _fontFilePath;
	}

	/**
	 * @return the _embedType
	 */
	public EmbedType getEmbedType() {
		return _embedType;
	}

	/**
	 * @param _embedType the _embedType to set
	 */
	public void setEmbedType(EmbedType _embedType) {
		this._embedType = _embedType;
	}
}
