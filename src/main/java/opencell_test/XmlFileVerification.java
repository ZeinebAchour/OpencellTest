package opencell_test;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

public class XmlFileVerification {

	public static final String DIRECTORY = "D:\\";

	private static void moveToRejectDirectory(File file) throws IOException {
		if (file != null) {
			System.out.println("file rejected +++++");
			File targetDirectory = new File(DIRECTORY + "reject/");

			File targetFile = new File(DIRECTORY + "reject/" + file.getName());

			
			if (!targetDirectory.exists()) {
				targetDirectory.mkdir();
			}

			targetFile.createNewFile();

		}
	}

	public static void main(String argv[]) {
		try {

			File file = new File(DIRECTORY + "Enedis_C15_17X000000946304K_GRD-F104_0327_00005_00001_00001.xml");

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

			String[] elements = file.getName().split("_");

			System.out.println(Arrays.toString(elements));
			// Condition 1
			if (elements.length < 7) {
				System.err.println("ERR_CHAR_001");
				moveToRejectDirectory(file);
				throw new CustomException(toStringException("Le nom du fichier doit contenir 8 éléments.",
						file.getName(), "rejected", "ERR_CHAR_001", new Date(file.lastModified())));
			}

			// condition 2
			if (Integer.valueOf(elements[5]) < 1) {
				System.err.println("ERR_CHAR_002");
				moveToRejectDirectory(file);
				throw new CustomException(toStringException("Num-seq doit être supérieur à 00001 .", file.getName(),
						"rejected", "ERR_CHAR_002", new Date(file.lastModified())));

			}
			// condition 3
			int yyyy = Integer.valueOf(elements[7].substring(0, 5));
			System.out.println("Div de yyyyy , " + yyyy);

			// int yyyy=Integer.valueOf(elements[7]);
			int xxxx = Integer.valueOf(elements[6]);
			if (xxxx < 1 && xxxx > yyyy)

			{
				System.err.println("ERR_CHAR_003");
				moveToRejectDirectory(file);
				throw new CustomException(
						toStringException("XXXXX doit être supérieur à 00001 et inférieur ou égale à YYYYY.",
								file.getName(), "rejected", "ERR_CHAR_003", new Date(file.lastModified())));
			}
			// condition 4
			if (yyyy < 1)

			{
				System.err.println("ERR_CHAR_004");
				throw new Exception("ERR_CHAR_004");
			}

			
			

			
			
			
			NodeList nodeList = doc.getElementsByTagName("En_Tete_Flux");
			// nodeList is not iterable, so we are using for loop
			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				Node node = nodeList.item(itr);
				System.out.println("\nNode Name :" + node.getNodeName());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					//for(int i=0; i<eElement.getChildNodes().getLength();i++)
					//{System.out.println( eElement.getChildNodes().item(i));}
					
					
					
		                
		                
		                
					System.out.println(
							"Version_XSD: " + eElement.getElementsByTagName("Version_XSD").item(0).getTextContent());
					if (eElement.getElementsByTagName("Version_XSD").item(0).getTextContent() == null) {

						System.err.println("Balise XSD introuvable");
						moveToRejectDirectory(file);

					}

				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class CustomException extends Exception {
		private static String flux;
		private static String filename;
		private static String status;
		private static String errorCode;
		private static Date date;

		public String getFlux() {
			return flux;
		}

		public void setFlux(String flux) {
			this.flux = flux;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public CustomException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			// TODO Auto-generated constructor stub
		}

		public CustomException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public CustomException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public CustomException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

	}

	public static String toStringException(String flux, String filename, String status, String errorCode, Date date) {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomException [flux=");
		builder.append(flux);
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", status=");
		builder.append(status);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}

	
	
	
	
}
