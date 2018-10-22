package JDBCProject;

public class MessageVO {
	
	// messageNumber number,
	// sender varchar2(15),
	// receiver varchar2(15),
	// messageTitle varchar2(20),
	// messageContent varchar2(100),
	private int messageNumber;
	private String sender;
	private String receiver;
	private String messageTitle;
	private String messageContent;
	
	MessageVO( String sender, String receiver ,String messageTitle, String messageContent ){
		this.sender = sender;
		this.receiver =receiver;
		this.messageTitle = messageTitle;
		this.messageContent = messageContent;
	}//end of MessageVO
	
	
	MessageVO(int messageNumber, String sender, String receiver ,String messageTitle, String messageContent ){
		this.messageNumber = messageNumber;
		this.sender = sender;
		this.receiver =receiver;
		this.messageTitle = messageTitle;
		this.messageContent = messageContent;
	}//end of MessageVO
	
	public int getMessageNumber() {
		return messageNumber;
	}
	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	
	
}//end of MessageVO
