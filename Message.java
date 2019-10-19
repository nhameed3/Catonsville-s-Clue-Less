import java.io.Serializable;

public class Message implements Serializable {
	// three private instance variables all strings. content (message), source (where'd it come from), destination (where is it going)
	private String content;
	private String source;
	private String destination;
	
	//make a default constructor
	public Message() {
		this.content = null;
		this.source = null;
		this.destination = null;
	}
	
	// constructor, three arguments to fill in three Strings
	public Message(String newText, String newSource, String newDestination) {
		this.content = newText;
		this.source = newSource;
		this.destination = newDestination;
		
	}
	// get methods to return attributes
	public String getContent() {
		return content;
	}
	
	public String getSource() {
		return source;
	}
	
	public String getDesintation() {
		return destination;
	}
	
	// set methods
	public void setContent(String newContent) {
		content = newContent;
	}
	
	public void setSource(String newSource) {
		this.source = newSource;
	}
	
	public void setDestination(String newDestination) {
		this.destination = newDestination;
	}
}
