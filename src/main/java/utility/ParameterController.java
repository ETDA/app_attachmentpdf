package utility;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

import pdfa3.EmbedType;
import pdfa3.MetaData;

public class ParameterController {
	
	private List<String> parameterList;
	private HashMap<String, String> libraryParameter;
	private MetaData pdfA3MetaData;
	
	
	public ParameterController(String[] args) throws Exception {
		System.out.println("Intializing library parameter...");
		libraryParameter = new HashMap<String, String>();
		pdfA3MetaData = new MetaData();
		parameterList = Arrays.asList(new String[] { "-inputFile", "-outputFile" });
		generateParameter(args);
	}
	
	private void generateParameter(String[] args) throws Exception {
		if (!validateRequireParameter(args)) {
			throw new Exception("Required parameter is missing");
		}
		
		if (args.length == 0) {
			throw new Exception("Parameter cannot be blank");
		}
		
		System.out.println("\tParameter list:");
		for(int i=0; i<args.length; i+=2) {
			if (args[i].startsWith("-")) {
				System.out.println("\t\t" + args[i].toString().trim() + ": " + args[i+1].toString().trim());
				libraryParameter.put(args[i], args[i+1]);
			} else {
				throw new Exception("Unrecognized paramater type");
			}
		}
		
	}
	
	private boolean validateRequireParameter(String[] args) {
		System.out.println("\tValidating required parameter...");
		
		List<String> argsList = Arrays.asList(args);
		
		if (argsList.containsAll(parameterList)) {
			System.out.println("\tRequired parameter complete.");
			return true;
		}
		else
		{
			System.out.println("\tRequired parameter incomplete.");
			return false;
		}
	}
	
	/**
	 * Get library parameter from external input
	 * @param key Parameter key name
	 * @return
	 */
	public String getParameterValue(String key) {
		return libraryParameter.get(key);
	}


	/**
	 * @return the pdfA3MetaData
	 */
//	//public MetaData getPdfA3MetaData() {
//		return pdfA3MetaData;
//	}
	
	public EmbedType getEmdedType() throws Exception {
		if (libraryParameter.get("-embedType") == null) {
			return EmbedType.ADD;
		}
		else if (libraryParameter.get("-embedType").equalsIgnoreCase("ADD")) {
			return EmbedType.ADD;
		} else if (libraryParameter.get("-embedType").equalsIgnoreCase("REPLACE")) {
			return EmbedType.REPLACE;
		} else {
			throw new Exception("Unrecognized Embed type");
		}
	}


}
