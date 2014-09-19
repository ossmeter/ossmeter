package org.ossmeter.contentclassifier.opennlptartarus.libsvm;

import java.util.List;

import uk.ac.nactem.posstemmer.OpenNlpTartarusSingleton;
import uk.ac.nactem.posstemmer.Token;

public class ClassificationInstance {
	
	private String url;

	private String product;
	private String component;
	private String bugId;
	private String commentId;
	
	private int articleNumber;
	private int threadId;
	private int positionFromThreadBeginning;
//	private int positionFromThreadEnd;
	private String subject;
	private List<List<Token>> tokenSentences;
	private List<List<Token>> cleanTokenSentences;

	private String text;
	private String cleanText;
	private String composedId;
	
	public  ClassificationInstance() {	}
	
	public String getComposedId() {
		if (composedId==null) setComposedId();
		return composedId;
	}

	private void setComposedId() {
		if ((url!=null)&&(product!=null)&&(component!=null)&&(bugId!=null)&&(commentId!=null))
			composedId = product+"#"+component+"#"+bugId+"#"+commentId;
		else if ((url!=null)&&(articleNumber!=0)) 
			composedId = url+"#"+articleNumber;
		else {
			System.err.println("Unable to compose ID");
		}
		toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		if (composedId!=null) setComposedId();
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
		if (composedId!=null) setComposedId();
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
		if (composedId!=null) setComposedId();
	}

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
		if (composedId!=null) setComposedId();
	}


	public String getCommentId() {
		return commentId;
	}


	public void setCommentId(String commentId) {
		this.commentId = commentId;
		if (composedId!=null) setComposedId();
	}


	public int getArticleNumber() {
		return articleNumber;
	}
	
	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
		if (composedId!=null) setComposedId();
	}
	
	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	
	public int getPositionFromThreadBeginning() {
		return positionFromThreadBeginning;
	}

	public void setPositionFromThreadBeginning(int positionFromThreadBeginning) {
		this.positionFromThreadBeginning = positionFromThreadBeginning;
	}

//	public int getPositionFromThreadEnd() {
//		return positionFromThreadEnd;
//	}

//	public void setPositionFromThreadEnd(int positionFromThreadEnd) {
//		this.positionFromThreadEnd = positionFromThreadEnd;
//	}

	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
		setCleanText();
	}
	
	public void setCleanText() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String line: text.split("\n")) {
			String trimmedLine = line.trim();
			if ( (!trimmedLine.startsWith("<")) && (!trimmedLine.startsWith(">")) ) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		}
		cleanText = stringBuilder.toString();
	}
	
	public String getCleanText() {
		return cleanText;
	}

	public List<List<Token>> getTokenSentences() {
		if (tokenSentences==null) {
			OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
			tokenSentences = tartarus.getTagger().tag(text);
//			outputTag(tokenSentences);
		}
		return tokenSentences;
	}

	public List<List<Token>> getCleanTokenSentences() {
		if (cleanTokenSentences==null) {
			OpenNlpTartarusSingleton tartarus = OpenNlpTartarusSingleton.getInstance();
			cleanTokenSentences = tartarus.getTagger().tag(getCleanText());
//			outputTag(cleanTokenSentences);
		}
		return cleanTokenSentences;
	}

//	private void outputTag(List<List<Token>> tokenSentences) {
//	for (List<Token> tokens: tokenSentences)
//		for (Token token: tokens)
//			System.out.println(token.toString());
//		System.out.println();
//}

	@Override
	public String toString() {
		return "ClassificationInstance [url=" + url + ", product=" + product
				+ ", component=" + component + ", bugId=" + bugId
				+ ", commentId=" + commentId + ", articleNumber="
				+ articleNumber + ", subject=" + subject + "]";
	}

}
