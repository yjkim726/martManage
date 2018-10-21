package intern.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sinsiway.petra.commons.sql.dto.ConsumerBookingDto;
import com.sinsiway.petra.commons.sql.dto.CoordinateDto;
import com.sinsiway.petra.commons.ui.util.DialogLocationUtill;
import com.sinsiway.petra.commons.ui.util.PetraGridData;
import com.sinsiway.petra.commons.ui.util.PetraGridLayout;
import com.sinsiway.petra.commons.util.DialogReturn;

public class BookingInfoDialog extends Dialog {
	private ConsumerBookingDto consumerBookingData;
	private Object returnData;
	private int[] array;
	
	private Text bookingText,conSumerText,departuerPlaceText,
	arrivePlaceText,seatTypeText,seatNumberText;

	public BookingInfoDialog(Shell parentShell, ConsumerBookingDto consumerBookingData, int array[]) {
		super(parentShell);
		this.consumerBookingData = consumerBookingData;
		this.array = array;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void configureShell(Shell newShell){
		//Shell에 대한 title, size, 
		super.configureShell(newShell);
		
		newShell.setText("예약 정보 확인");
		
		newShell.setSize(340, 450);
		
		DialogLocationUtill.MonitorCenter(newShell);
			//MonitorCenter을 누를 것 인지
		
	}
	
	@Override
	protected Control createDialogArea(Composite parent){
		//기본 스페이싱,마진을 어느정도 주겠다. 회사에서 만든 사이즈 메소드 같은것
		PetraGridLayout.defaults().setDialog().applyTo(parent);
		PetraGridData.defaults().applyTo(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		PetraGridLayout.defaults().setDialog().applyTo(composite);
		PetraGridData.defaults().applyTo(composite);
		
		Group group = new Group(composite, SWT.NONE);
		//정보 추가할 때   이름, 성별, 휴대전화번호를 감싼 선 	 옵션을 주지 않아도 테두리 생성
		PetraGridLayout.defaults().setDialog().columns(2).applyTo(group);
												//원래   3 이였음
		PetraGridData.defaults().grab(true, false).span(2, 1).applyTo(group);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("예약번호 ");
		
		bookingText = new Text(group, SWT.BORDER);
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).span(1, 1).applyTo(bookingText);
																		//이름 : [                ] 이렇게 됬었음
																		//span(2,1)로 설정해뒀었음  2칸을 잡아먹는다는 뜻
																		//바로 밑 new lebel도 없었음
	//	new Label(group, SWT.NONE);
		
		label = new Label(group, SWT.NONE);
		label.setText("사용자 ");
		
		conSumerText = new Text(group, SWT.BORDER);
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).span(1, 1).applyTo(conSumerText);
		
		
		label = new Label(group, SWT.NONE);
		label.setText("출발지 ");
		
		departuerPlaceText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(departuerPlaceText);
		
		label = new Label(group, SWT.NONE);
		label.setText("목적지 ");
		
		arrivePlaceText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(arrivePlaceText);
		
		label = new Label(group, SWT.NONE);
		label.setText("좌석 종류 ");
		
		seatTypeText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(seatTypeText);
		
		label = new Label(group, SWT.NONE);
		label.setText("좌석 갯수 ");
		
		seatNumberText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(seatNumberText);
		
		//초기화
		initialize();
		return parent;
	}
	
		private void initialize() {
			//셋팅부분
			if((array != null) | (consumerBookingData!=null)){ //수정일때
				
				bookingText.setText(String.valueOf(consumerBookingData.BOOKING_NUMBER)); //예약번호
				bookingText.setEditable(false);
				
				conSumerText.setText(String.valueOf(consumerBookingData.CONSUMER_ID));
				conSumerText.setEditable(false);
				
				System.out.print("얍..!"+consumerBookingData.DEPARTURE_PLACE);
				
				if(consumerBookingData.DEPARTURE_PLACE==0)
					departuerPlaceText.setText("인천");
				else if(consumerBookingData.DEPARTURE_PLACE==1)
					departuerPlaceText.setText("김포");
				else if(consumerBookingData.DEPARTURE_PLACE==2)	
					departuerPlaceText.setText("대구");
				else if(consumerBookingData.DEPARTURE_PLACE==3)
					departuerPlaceText.setText("제주");
				departuerPlaceText.setEditable(false);	
				
				
				if(consumerBookingData.ARRIVE_PLACE==0)
					arrivePlaceText.setText("도쿄");
				else if(consumerBookingData.ARRIVE_PLACE==1)
					arrivePlaceText.setText("베이징");
				else if(consumerBookingData.ARRIVE_PLACE==2)	
					arrivePlaceText.setText("뉴욕");
				else if(consumerBookingData.ARRIVE_PLACE==3)
					arrivePlaceText.setText("파리");
				arrivePlaceText.setEditable(false);	
				
				if(consumerBookingData.SEAT_TYPE==0)
					seatTypeText.setText("일반석");
				else if(consumerBookingData.SEAT_TYPE==1)
					seatTypeText.setText("비즈니스석");
				else if(consumerBookingData.SEAT_TYPE==2)	
					seatTypeText.setText("일등석");
				seatTypeText.setEditable(false);	
				
				seatNumberText.setText(String.valueOf(consumerBookingData.SEATNUMBER));
				seatNumberText.setEditable(false);
				
			}
		}
		public DialogReturn<Object> openWithResult() {
			int code = open();
						//open()
			return new DialogReturn<Object>(returnData, code);
		}			

}
