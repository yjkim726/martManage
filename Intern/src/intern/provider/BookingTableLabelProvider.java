package intern.provider;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.sinsiway.petra.commons.sql.dto.BookingDto;
import com.sinsiway.petra.commons.sql.dto.ConsumerBookingDto;
import com.sinsiway.petra.commons.sql.dto.SearchDto;
import com.sinsiway.petra.commons.ui.util.PetraFont;

public class BookingTableLabelProvider extends LabelProvider implements ITableLabelProvider,ITableFontProvider,ITableColorProvider{
	private Font font = null;
	Color color = new Color(null,0, 0, 0);
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		switch (columnIndex) {
//			case 0:
//				return InternUIResources.getImage("sample.gif");
			/*case 2:
				return InternUIResources.getImage("sample1.gif");
			case 3:
				return InternUIResources.getImage("sample1.gif");*/
			default:	
				return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// String[] bookingListColumnHeader = {"예약번호","항공권종류","출발지","도착지","출발일","좌석등급","체크인여부"};중요해요!
		ConsumerBookingDto data = (ConsumerBookingDto) element;
		String SEAT_TYPES,DEPARTURE_PLACE,ARRIVE_PLACE;
		switch (columnIndex) {
		case 0: //예약번호
			return String.valueOf(data.BOOKING_NUMBER);
		case 1: //항공권이름
			return	data.FLIGHT_NAME;
		case 2: //출발지
			if(data.DEPARTURE_PLACE==0){
				DEPARTURE_PLACE="인천";
				return DEPARTURE_PLACE;
			}else if(data.DEPARTURE_PLACE==1){
				DEPARTURE_PLACE="김포";
				return DEPARTURE_PLACE;
			}else if(data.DEPARTURE_PLACE==2){
				DEPARTURE_PLACE="대구";
				return DEPARTURE_PLACE;
			}else if(data.DEPARTURE_PLACE==3){
				DEPARTURE_PLACE="제주";
				return DEPARTURE_PLACE;
			}
			//return String.valueOf(data.DEPARTURE_PLACE);
		case 3: //도착지
			if(data.ARRIVE_PLACE==0){
				ARRIVE_PLACE="도쿄";
				return ARRIVE_PLACE;
			}else if(data.ARRIVE_PLACE==1){
				ARRIVE_PLACE="베이징";
				return ARRIVE_PLACE;
			}else if(data.ARRIVE_PLACE==2){
				ARRIVE_PLACE="뉴욕";
				return ARRIVE_PLACE;
			}else if(data.ARRIVE_PLACE==3){
				ARRIVE_PLACE="파리";
				return ARRIVE_PLACE;
			}
			//return String.valueOf(data.ARRIVE_PLACE);
		case 4: //출발일
			return String.valueOf(data.DEPARTURE_DATE);
		case 5: //좌석등급
			if(data.SEAT_TYPE==0){
				SEAT_TYPES="일반석";
				return SEAT_TYPES;
			}else if(data.SEAT_TYPE==1){
				SEAT_TYPES="비즈니스석";
				return SEAT_TYPES;
			}else if(data.SEAT_TYPE==2){
				SEAT_TYPES="일등석";
				return SEAT_TYPES;
			}
		case 6: //체크인여부
			if(data.CHECKIN==0){
				return "O";
			}else if(data.CHECKIN==1){
				return "X";
			}
		default:
			return "미정의";
		}
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		//PetraColor.RED_80;
		//네이버에 치면 다 나옵니다.
		getForeground();
		return color;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
//		case 2:
//			return PetraFont.S8_B_FONT;
		/*case 4:
			return PetraFont.S8_B_FONT;*/
		default:	
			return font;
		}
	}
	
	public Font getFont(){
		return font;
	}
	
	public void setFont(Font font){
		this.font = font;
	}
	
	public Color getForeground(){
		return color;
	}
	
	public void setForeground(Color color){
		this.color = color;
	}
}