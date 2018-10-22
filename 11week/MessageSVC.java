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
			System.out.println("*****MESSEGE전송*****");
			System.out.println("받는이 : ");
			//id 검색
			String receiver = sc.next();
			if(receiver.equals("관리자")){
				martMemberVO= martJoinSVC.overLapJoinIdCheck("SYS");
			}
			else{
				martMemberVO= martJoinSVC.overLapJoinIdCheck(receiver);
			}
			
			if(martMemberVO!=null){
				System.out.println("제목 : ");
				String messageTitle = sc.next();
				System.out.println("내용 : ");
				String messageContent = sc.next();
			
				MessageVO messageVO = new MessageVO(sender,receiver,messageTitle,messageContent);
				
				//db에 입력
				dbAddMessage(messageVO);
			}
			else{
				System.out.println("없는 사용자 입니다");
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
					System.out.println("--메세지 전송 완료");
				}else{
					System.out.println("--메세지 전송 실패");
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
	
	//수신쪽지함
		public void receiveMessage(String receiver){
			int i =0;
			MessageVO message,detailMessage = null;
			dbreceiveMessage(receiver);
			System.out.println("*****수신메시지함*****");
			for(i = 0; i<receiverMessageBox.size();i++){
				message = receiverMessageBox.get(i);
				System.out.print("["+i+"]  ");
				System.out.print("보낸이 : "+message.getSender());
				System.out.println("제목 : "+message.getMessageTitle());
			}
			System.out.println("******************");
			if(i!=0){
				System.out.println("내용을 볼 메시지를 선택하세요.");
				int selectMessage = sc.nextInt();
				detailMessage = receiverMessageBox.get(selectMessage);
				System.out.println("=========================");
				System.out.println(" 받는이 : "+detailMessage.getReceiver());
				System.out.println(" 보낸이 : "+detailMessage.getSender());
				System.out.println(" 제목 : "+detailMessage.getMessageTitle());
				System.out.println(" 내용 : "+detailMessage.getMessageContent());
				System.out.println("=========================");
				
				//receiverMessageBox.removeAll(receiverMessageBox);
				receiverMessageBox.clear();
			}
			else{
				System.out.println("수신된 메세지가 없습니다.");
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
		
		
	//발신쪽지함
		public void senderMessage(String sender){
			MessageVO message,detailMessage = null;
			dbsenderMessage(sender);
			System.out.println("*****발신메시지함*****");
			for(int i = 0; i<senderMessageBox.size();i++){
				message = senderMessageBox.get(i);
				System.out.print("["+i+"]  ");
				System.out.print("받는이 : "+message.getReceiver());
				System.out.println("제목 : "+message.getMessageTitle());
			}
			System.out.println("**********");
			System.out.println("내용을 볼 메시지를 선택하세요.");
			int selectMessage = sc.nextInt();
			detailMessage = senderMessageBox.get(selectMessage);
			System.out.println("=========================");
			System.out.println(" 받는이 : "+detailMessage.getReceiver());
			System.out.println(" 보낸이 : "+detailMessage.getSender());
			System.out.println(" 제목 : "+detailMessage.getMessageTitle());
			System.out.println(" 내용 : "+detailMessage.getMessageContent());
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
		//쪽지함 관리
		
		
		
}// end of MessageSVC
