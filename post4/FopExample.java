import java.io.File;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.*;
import org.apache.xmlgraphics.util.MimeConstants;

/**
 * Simple example that will read an XSL-FO file, transform it, and send the results to standard out. This application
 * requires the fop and barcode4j-fop-ext jars, plus any of their dependencies (unfortunately there are quite a few), to
 * be on the classpath. If I were to do this again I would probably use maven. Lesson learned.
 */
public class FopExample {

    /**
     * Main method.
     * @param args command-line arguments
     * @throws Exception Ok, so I'm a slob.
     */
	public static void main(String... args) throws Exception {
		FopFactory fopFactory = FopFactory.newInstance();
		Fop fop = fopFactory.newFop(MimeConstants.MIME_POSTSCRIPT, System.out);
		Source source = new StreamSource(new File("./example.fo"));
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		transformer.transform(source, new SAXResult(fop.getDefaultHandler()));
	}

}
