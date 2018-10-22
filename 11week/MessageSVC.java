package JDBCProject;

import static JDBCProject.JdbcUtil.getConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;

public class MessageSVC {

	Scanner sc = new Scanner(System.in);
	Connection con = null;
	ArrayList<MessageVO> senderMessageBox = new ArrayList<MessageVO>();
	ArrayList<MessageVO> receiverMessageBox = new ArrayList<MessageVO>();
	ArrayList<MessageVO> MessageBox = new ArrayList<MessageVO>();

		public void addMessage(String sender,MartJoinSVC martJoinSVC){
			MartMemberVO martMemberVO =null;
			System.out.println("*****MESSEGE����*****");
			System.out.println("�޴��� : ");
			//id �˻�
			String receiver = sc.next();
			if(receiver.equals("������")){
				martMemberVO= martJoinSVC.overLapJoinIdCheck("SYS");
			}
			else{
				martMemberVO= martJoinSVC.overLapJoinIdCheck(receiver);
			}
			
			if(martMemberVO!=null){
				System.out.println("���� : ");
				String messageTitle = sc.next();
				System.out.println("���� : ");
				String messageContent = sc.next();
			
				MessageVO messageVO = new MessageVO(sender,receiver,messageTitle,messageContent);
				
				//db�� �Է�
				dbAddMessage(messageVO);
			}
			else{
				System.out.println("���� ����� �Դϴ�");
			}
		}//end of addSubclassCode
		
		public void dbAddMessage(MessageVO messageVO){
			con = getConnect();
			PreparedStatement pstmt = null;
			String sql = "insert into Message values(messeageN.nextval,?,?,?,?)";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, messageVO.getSender());
				pstmt.setString(2, messageVO.getReceiver());
				pstmt.setString(3, messageVO.getMessageTitle());
				pstmt.setString(4, messageVO.getMessageContent());
				
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("--�޼��� ���� �Ϸ�");
				}else{
					System.out.println("--�޼��� ���� ����");
				}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally	
		}//end of dbAddMessage(MessageVO messageVO)
	
	//����������
		public void receiveMessage(String receiver){
			int i =0;
			MessageVO message,detailMessage = null;
			dbreceiveMessage(receiver);
			System.out.println("*****���Ÿ޽�����*****");
			for(i = 0; i<receiverMessageBox.size();i++){
				message = receiverMessageBox.get(i);
				System.out.print("["+i+"]  ");
				System.out.print("������ : "+message.getSender());
				System.out.println("���� : "+message.getMessageTitle());
			}
			System.out.println("******************");
			if(i!=0){
				System.out.println("������ �� �޽����� �����ϼ���.");
				int selectMessage = sc.nextInt();
				detailMessage = receiverMessageBox.get(selectMessage);
				System.out.println("=========================");
				System.out.println(" �޴��� : "+detailMessage.getReceiver());
				System.out.println(" ������ : "+detailMessage.getSender());
				System.out.println(" ���� : "+detailMessage.getMessageTitle());
				System.out.println(" ���� : "+detailMessage.getMessageContent());
				System.out.println("=========================");
				
				//receiverMessageBox.removeAll(receiverMessageBox);
				receiverMessageBox.clear();
			}
			else{
				System.out.println("���ŵ� �޼����� �����ϴ�.");
			}
		}
		
		public void dbreceiveMessage(String receiver){
			con = getConnect();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int i = 1;
			String sql = "select * from Message where receiver =?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, receiver);
				
				int count = pstmt.executeUpdate();
				if(count>0){
				rs = pstmt.executeQuery();
				while(rs.next()){
						String messageSender= rs.getString("sender");
						String messageReceiver= rs.getString("receiver");
						String messageTitle= rs.getString("messageTitle");
						String messageContent= rs.getString("messageContent");
						
						MessageVO message = new MessageVO(i,messageSender,messageReceiver,messageTitle,messageContent);
						receiverMessageBox.add(message);
						i++;
					}//end of while
				}
				else{	}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally	
		}//end of dbreceiveMessage(String receiver)
		
		
	//�߽�������
		public void senderMessage(String sender){
			MessageVO message,detailMessage = null;
			dbsenderMessage(sender);
			System.out.println("*****�߽Ÿ޽�����*****");
			for(int i = 0; i<senderMessageBox.size();i++){
				message = senderMessageBox.get(i);
				System.out.print("["+i+"]  ");
				System.out.print("�޴��� : "+message.getReceiver());
				System.out.println("���� : "+message.getMessageTitle());
			}
			System.out.println("**********");
			System.out.println("������ �� �޽����� �����ϼ���.");
			int selectMessage = sc.nextInt();
			detailMessage = senderMessageBox.get(selectMessage);
			System.out.println("=========================");
			System.out.println(" �޴��� : "+detailMessage.getReceiver());
			System.out.println(" ������ : "+detailMessage.getSender());
			System.out.println(" ���� : "+detailMessage.getMessageTitle());
			System.out.println(" ���� : "+detailMessage.getMessageContent());
			System.out.println("=========================");
			
			//senderMessageBox.removeAll(senderMessageBox);
			senderMessageBox.clear();
		}
		
		public void dbsenderMessage(String sender){
			con = getConnect();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int i = 1;
			String sql = "select * from Message where sender =?";
			try{
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, sender);
				
				int count = pstmt.executeUpdate();
				if(count>0){
					rs = pstmt.executeQuery();
					while(rs.next()){
						String messageSender= rs.getString("sender");
						String messageReceiver= rs.getString("receiver");
						String messageTitle= rs.getString("messageTitle");
						String messageContent= rs.getString("messageContent");
						
						MessageVO message = new MessageVO(i,messageSender,messageReceiver,messageTitle,messageContent);
						senderMessageBox.add(message);
						i++;
					}//end of while
				}
				else {}
			}catch(SQLException cne){
				cne.printStackTrace();
			}finally{
				try{
					con.close();
					pstmt.close();
				}catch(Exception e){	}
			}//end of finally	
		}//end of dbreceiveMessage(String receiver)
	
	
		///////////////manager
		//������ ����
		
		
		
}// end of MessageSVC
