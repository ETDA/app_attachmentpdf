package pdfa3;

import java.util.Calendar;

public class MetaData {
	private String _title, _subject, _author, _creator, _producer, _keywords, _trapped;
	private Calendar _creationDate, _modificationDate;
	
	/**
	 * Default constructor
	 */
	public MetaData() {
		
	}
	
	/**
	 * Overload constructor
	 * @param title The document title
	 * @param subject The document subject
	 * @param author The author of the document
	 * @param creator The creator of the document
	 * @param producer The producer of the document
	 * @param keywords The document keywords
	 * @param trapped The document trapped
	 * @param creationDate The document create date
	 * @param modificationDate The document modification date
	 */
	public MetaData(String title, String subject, String author, String creator, String producer, String keywords, String trapped, Calendar creationDate, Calendar modificationDate) {
		setTitle(title);
		setSubject(subject);
		setAuthor(author);
		setCreator(creator);
		setProducer(producer);
		setKeywords(keywords);
		setTrapped(trapped);
		setCreationDate(creationDate);
		setModificationDate(creationDate);
	}

	/**
	 * Get document title
	 * @return Document title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Set document title
	 * @param title The document title
	 */
	public void setTitle(String title) {
		this._title = title;
	}

	/**
	 * Get the document subject
	 * @return Document subject
	 */
	public String getSubject() {
		return _subject;
	}

	/**
	 * Set document subject
	 * @param subject The document subject
	 */
	public void setSubject(String subject) {
		this._subject = subject;
	}

	/**
	 * Get the document author
	 * @return Document author
	 */
	public String getAuthor() {
		return _author;
	}

	/**
	 * Set the document author
	 * @param author The document author
	 */
	public void setAuthor(String author) {
		this._author = author;
	}

	/**
	 * Get the document author
	 * @return The document creator
	 */
	public String getCreator() {
		return _creator;
	}

	/**
	 * Set the document creator
	 * @param creator The document creator
	 */
	public void setCreator(String creator) {
		this._creator = creator;
	}

	/**
	 * Get the document producer
	 * @return the _producer
	 */
	public String getProducer() {
		return _producer;
	}

	/**
	 * Set the document producer
	 * @param _producer the _producer to set
	 */
	public void setProducer(String producer) {
		this._producer = producer;
	}

	/**
	 * Get the document keywords
	 * @return the document keywords
	 */
	public String getKeywords() {
		return _keywords;
	}

	/** 
	 * Set the document keywords
	 * @param keywords The document keywords
	 */
	public void setKeywords(String keywords) {
		this._keywords = keywords;
	}

	/**
	 * Get the document trapped
	 * @return The document trapped
	 */
	public String getTrapped() {
		return _trapped;
	}

	/**
	 * Set the document trapped
	 * @param trapped the document trapped
	 */
	public void setTrapped(String trapped) {
		this._trapped = trapped;
	}

	/**
	 * Get the document creation date
	 * @return The document creation date
	 */
	public Calendar getCreationDate() {
		return _creationDate;
	}

	/**
	 * Set the document creation date
	 * @param calendar the document creation date
	 */
	public void setCreationDate(Calendar calendar) {
		this._creationDate = calendar;
	}

	/**
	 * Get the document modification date
	 * @return The document modification date
	 */
	public Calendar getModificationDate() {
		return _modificationDate;
	}

	/**
	 * Set The document modification date
	 * @param modificationDate The document modification date
	 */
	public void setModificationDate(Calendar modificationDate) {
		this._modificationDate = modificationDate;
	}
	
}

