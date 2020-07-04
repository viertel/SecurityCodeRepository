class GhC672c137469fb763812dc6ac08edd56c2a06fef0a_4{
void vulnerable(){
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			// Use the factory to create a template containing the xsl file
			Templates template = factory.newTemplates(new StreamSource(
					new FileInputStream(xslfile)));
 
 			// Use the template to create a transformer
 			Transformer xformer = template.newTransformer();
}
}