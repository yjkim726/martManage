package intern;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import intern.dialog.BookingInfoDialog;
import intern.dialog.CalendarDialog;
import intern.dialog.InternDialog;
import intern.provider.BookingTableLabelProvider;
import intern.provider.SearchTableLabelProvider;
import intern.util.FrequentlyUsedMethodsUtil;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.mihalis.opal.brushedMetalComposite.BrushedMetalComposite;

import com.sinsiway.petra.commons.log.PetraLogger;
import com.sinsiway.petra.commons.sql.dto.BookingDto;
import com.sinsiway.petra.commons.sql.dto.ConsumerBookingDto;
import com.sinsiway.petra.commons.sql.dto.ConsumerDto;
import com.sinsiway.petra.commons.sql.dto.CoordinateDto;
import com.sinsiway.petra.commons.sql.dto.DepartureDto;
import com.sinsiway.petra.commons.sql.dto.FlightDto;
import com.sinsiway.petra.commons.sql.dto.InternDto;
import com.sinsiway.petra.commons.sql.dto.PlaneDto;
import com.sinsiway.petra.commons.sql.dto.SearchDto;
import com.sinsiway.petra.commons.sql.util.IBatisAdapter;
import com.sinsiway.petra.commons.ui.control.TableFilterComposite;
import com.sinsiway.petra.commons.ui.util.PetraFont;
import com.sinsiway.petra.commons.ui.util.PetraGridData;
import com.sinsiway.petra.commons.ui.util.PetraGridLayout;
import com.sinsiway.petra.commons.ui.util.TableUtil;
import com.sinsiway.petra.commons.util.DialogReturn;

public class BookAFlightView extends ViewPart{

	public static final String ID = "Intern.bookAFlightView";
	private Label separatorLabel;
	private Label vacuumLabel;
	private Logger logger = PetraLogger.getLogger(View.class);
	private LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
	//private Text messageText;
	private IPreferenceStore iPreferenceStore = PlatformUI.getPreferenceStore();
	private Button oneWay,roundTrip, generalSeat, businessClass, firstClass ;
	private StackLayout stackLayout, placeStackLayout, checkInStackLayout;  
	//화면상에는 두개가 쌓여있는데, 어떤 창을 선택할지 정하는 것
	private Composite stackComposite, stackComposite1, stackComposite2;
	private Composite placeStackComposite, airlineTicketBook,bookingListCheckIn,allCheckList,placeStackComposite2;
	//나중에 어떤 이벤트가 발생했을 때, 멤버선언보단 전체선언을 통해서
	private DateTime departureDate, arriveDate;
	private SashForm placeSashForm, checkInSashForm, allCheckSashForm, bookingListSashForm, checkInSeatConfirmationSashForm,  reservationConfirmationSashForm;
	private Combo departureCombo , destinationCombo;
	private Integer dataValidation = 0; //편도로 초기화
	private Integer seatType = 0;
	private Calendar departureCalendar,arriveCalendar;
	private Spinner adultSpinner, youngChildSpinner, kidSpinner;
	private ConsumerDto modifyData ;
	private String departureStringDate, arriveStringDate;
	private Date date = new Date();
	private SimpleDateFormat smpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 
	//////////////////////////////////SearchView
	private TableViewer searchTableViewer, bookingTableViewer, allBookingTableViewer;
	private Table searchTable,bookingTable,allBookingTable;
	//항공
	private FlightDto flightDto1 = new FlightDto();
	private FlightDto flightDto2 = new FlightDto();
	private PlaneDto planeDto1 = new PlaneDto();
	private PlaneDto planeDto2 = new PlaneDto();
	private DepartureDto departureDto1 = new DepartureDto();
	private DepartureDto departureDto2 = new DepartureDto();
	/*private List<FlightDto> flightList = new ArrayList<FlightDto>();
	private List<PlaneDto> planeList = new ArrayList<PlaneDto>();
	private List<DepartureDto> departureList = new ArrayList<DepartureDto>();*/
	private List<SearchDto> searchList;
	private ConsumerDto data = new ConsumerDto();
	private Integer bookingListTableIndex,allBookingListTableIndex, searchPlaneTableIndex;
	private ConsumerBookingDto consumerBookingDto = new ConsumerBookingDto(); 
	private ConsumerBookingDto consumerBookingData = new  ConsumerBookingDto(); 
	
	/////예약목록 체크인
	private List <ConsumerBookingDto> bookingList, allBookingList;
	private CLabel clbel;
	
	//private Composite seatConfirmationGroupComposite2;
	private int[] count = new int[66]; 
	private int allCount= 1 ;
	private int cancle = 0;
	private int again = 0;
	
	//예약목록/체크인 > 테이블클릭시> 목적지 __________ 예약번호____뜨는 라벨 
	private Label insertCheckInLabel2,insertCheckInLabel4,
	//전체확인시 뒤에 예약정보 쭉
	insertReservationConfirmationLabel2,insertReservationConfirmationLabel4,
	insertReservationConfirmationLabel6,insertReservationConfirmationLabel8,
	insertReservationConfirmationLabel10,insertReservationConfirmationLabel12,
	insertReservationConfirmationLabel14,insertReservationConfirmationLabel16;
	
	 List<CLabel> listLabel = new ArrayList<CLabel>();
	 List<CLabel> selectLabel = new ArrayList<CLabel>();
	
	private Font font = null;
	private Color color = new Color(null,0, 0, 0);
	BookingTableLabelProvider bookingTableLabelProvider = new BookingTableLabelProvider();  
	
	
	//NavigationView 에서 선택되게합니다.
	NavigationView navigationView = (NavigationView) FrequentlyUsedMethodsUtil.getView(NavigationView.ID);
	
	private List<CoordinateDto> selectedCoordinateList;
	private Label informationLabel;
	
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
				PetraGridLayout.defaults().applyTo(parent);
				PetraGridData.defaults().applyTo(parent);
				
				///항공권예매 stackLayout
				placeStackLayout = new StackLayout();
				placeStackComposite = new Composite(parent, SWT.BORDER);
				placeStackComposite.setLayout(placeStackLayout);
				PetraGridData.defaults().applyTo(placeStackComposite);

				///////////////스택나누기
				//airlineTicketBook
				//항공권예매
				airlineTicketBook = new Composite(placeStackComposite, SWT.None);
				PetraGridLayout.defaults().margin(0,0).spacing(0, 5).columns(4).applyTo(airlineTicketBook);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(airlineTicketBook);
				
				//bookingListCheckIn
				//예약목록&체크인
				bookingListCheckIn = new Composite(placeStackComposite, SWT.None);
				PetraGridLayout.defaults().margin(0,0).spacing(0, 5).columns(4).applyTo(bookingListCheckIn);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(bookingListCheckIn);
				
				//전체확인
				//allCheckList
				allCheckList = new Composite(placeStackComposite, SWT.None);
				PetraGridLayout.defaults().margin(0,0).spacing(0, 5).columns(4).applyTo(allCheckList);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(allCheckList);
				
				/////////////나눈 스택에서 SashForm 깔기
				//비행기 예매시스템 SashForm
				placeSashForm = new SashForm(airlineTicketBook, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(placeSashForm);
				PetraGridData.defaults().applyTo(placeSashForm);
				placeSashForm.setSashWidth(0);
				
				
				/*///출발지 도착지 콤보를 누를 경우 뜨는 이미지 sashForm
				departureArriveComboSashForm = new SashForm(placeSashForm, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(departureArriveComboSashForm);
				PetraGridData.defaults().applyTo(departureArriveComboSashForm);
				departureArriveComboSashForm.setSashWidth(0);*/
				
				//예약확인&체크인 SashForm
				checkInSashForm = new SashForm(bookingListCheckIn, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(checkInSashForm);
				PetraGridData.defaults().applyTo(checkInSashForm);
				checkInSashForm.setSashWidth(0);
				
				
				//전체확인
				allCheckSashForm = new SashForm(allCheckList, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(allCheckSashForm);
				PetraGridData.defaults().applyTo(allCheckSashForm);
				allCheckSashForm.setSashWidth(0);
				
				
				//////////////////////
				//airlineTicketBook 위에 topComposite을 깜
				final Composite topComposite = new Composite(placeSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 20).applyTo(topComposite);
				PetraGridData.defaults().grab(true, false).applyTo(topComposite);
				
				///출발지 목적지 콤보 선택시 이미지
			/*	final Composite comboImageComposite = new Composite(topComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).applyTo(comboImageComposite);
				PetraGridData.defaults().grab(true, true).hint(20, 30).applyTo(comboImageComposite);
				
				final Composite comboImageUpComposite = new Composite(comboImageComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).applyTo(comboImageUpComposite);
				PetraGridData.defaults().grab(true, false).hint(100, 500).applyTo(comboImageUpComposite);
				
				final Composite comboImageUnderComposite = new Composite(comboImageComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).applyTo(comboImageUnderComposite);
				PetraGridData.defaults().grab(true, false).hint(100, 100).applyTo(comboImageUnderComposite);*/
				
				final Composite groupComposite = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(30,10).spacing(0, 10).columns(4).applyTo(groupComposite);
				PetraGridData.defaults().grab(false, false).applyTo(groupComposite);			
				
				oneWay = new Button(groupComposite, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(65, 20).applyTo(oneWay);
				oneWay.setText("편도");
				//oneWay.setSelection(true); 
				
				vacuumLabel  = new Label(groupComposite, SWT.None);
				/*
				//라디오박스_그림_편도
				ToolBar oneWaytoolBar = new ToolBar (groupComposite, SWT.NONE);
				//테이블 바로 위에 + 수정 X
				//툴바를 사용하게 되면 마우스를 가까이 대면 자연스러운 효과를 준다.
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(oneWaytoolBar);
				PetraGridData.defaults().grab(false, false).applyTo(oneWaytoolBar);
										//여기서 최대로 해야지 <전체 > 이부분이 크게 안잡혀서 디자인이 이쁘게 나온다.
				final ToolItem oneWaycalendarToolItem = new ToolItem(oneWaytoolBar, SWT.NONE);
													//부모를 툴바로 둔다.
				//oneWaycalendarToolItem.setToolTipText("편도");
				oneWaycalendarToolItem.setImage(InternUIResources.getImage("oneWay.png")); */
				
				
				roundTrip = new Button(groupComposite, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(65, 20).applyTo(roundTrip);
				roundTrip.setText("왕복");
				
				oneWay.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						oneWay.setFont(PetraFont.S9_B_FONT);
						roundTrip.setFont(PetraFont.S9_FONT);
						
						dataValidation = 0; //편도를 선택할 경우
					
						stackLayout.topControl = stackComposite1;
						stackComposite.layout();
						
					}});
				
				roundTrip.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						roundTrip.setFont(PetraFont.S9_B_FONT);
						oneWay.setFont(PetraFont.S9_FONT);
						
						dataValidation = 1; //왕복을 선택할 경우
						
						stackLayout.topControl = stackComposite2;
						stackComposite.layout();
					}});
			
				
				vacuumLabel  = new Label(groupComposite, SWT.None);
				/*
				//라디오박스_그림_편도
				ToolBar roundTriptoolBar = new ToolBar (groupComposite, SWT.NONE);
				//테이블 바로 위에 + 수정 X
				//툴바를 사용하게 되면 마우스를 가까이 대면 자연스러운 효과를 준다.
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(roundTriptoolBar);
				PetraGridData.defaults().grab(false, false).applyTo(roundTriptoolBar);
										//여기서 최대로 해야지 <전체 > 이부분이 크게 안잡혀서 디자인이 이쁘게 나온다.
				final ToolItem roundTripcalendarToolItem = new ToolItem(roundTriptoolBar, SWT.NONE);
													//부모를 툴바로 둔다.
				//roundTripcalendarToolItem.setToolTipText("왕복");
				roundTripcalendarToolItem.setImage(InternUIResources.getImage("roundTrip.png"));
				
				*/
				
				final Composite groupComposite2 = new Composite(topComposite, SWT.NONE);
				//PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(6).applyTo(groupComposite2);
				PetraGridLayout.defaults().margin(30,10).spacing(0, 10).columns(4).applyTo(groupComposite2);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite2);
				
				Label departureLabel = new Label(groupComposite2, SWT.NONE);
				departureLabel.setText("출발지 ");
				departureLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(departureLabel);
					
				
				//출발지
				departureCombo = new Combo(groupComposite2, SWT.READ_ONLY);
				departureCombo.setText("list");
				String[] departureBundle = {"인천", "김포","대구","제주"};
				departureCombo.setItems(departureBundle);
				//PetraGridData.defaults().grab(false, false).hint(60, 10).applyTo(departureCombo);
				PetraGridData.defaults().grab(false, false).hint(100, 10).applyTo(departureCombo);
				departureCombo.select(0);
				departureCombo.addSelectionListener(new SelectionAdapter(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
				
				//목적지
				Label destinationLabel = new Label(groupComposite2, SWT.NONE);
				destinationLabel.setText("목적지 ");
				destinationLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(destinationLabel);		
				
				destinationCombo = new Combo(groupComposite2, SWT.READ_ONLY);
				String[] destinationBundle = {"도쿄", "베이징","뉴욕","파리"};
				destinationCombo.setItems(destinationBundle);
				destinationCombo.select(0);
				//PetraGridData.defaults().grab(false, false).hint(60, 10).applyTo(destinationCombo);
				PetraGridData.defaults().grab(false, false).hint(100, 10).applyTo(destinationCombo);
				
				
				destinationCombo.addSelectionListener(new SelectionAdapter(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
				////새롭게 추가 깔음
				final Composite groupComposite2_1 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite2_1);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite2_1);
				
				////////////////백업	
				stackLayout = new StackLayout();
				stackComposite = new Composite(groupComposite2_1, SWT.None);
				stackComposite.setLayout(stackLayout);
				
				//stackComposite1
				stackComposite1 = new Composite(stackComposite, SWT.None);
				PetraGridLayout.defaults().margin(20,10).spacing(0, 10).columns(4).applyTo(stackComposite1);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(stackComposite1);
				
				///추가
				
				Label departureDateLabel = new Label(stackComposite1, SWT.NONE);
				departureDateLabel.setText("출발일 ");
				departureDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(departureDateLabel);
				
				
				departureCalendar = Calendar.getInstance();
				departureCalendar.add(Calendar.DATE, 0);
				departureDate = new DateTime(stackComposite1, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130,30).applyTo(departureDate);
				departureDate.setDate(departureCalendar.get(Calendar.YEAR),0,1);
				departureStringDate = smpleDateFormat.format(date);  //현재날짜출력
				departureDate.addSelectionListener(new SelectionAdapter(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
						String allDate = e.widget.toString();
						int startCut = allDate.indexOf("{");
						int endCut = allDate.indexOf("}");
						
						
						String dateCutting = allDate.substring(startCut+1, endCut);
						String arrayCut[] = dateCutting.split("/");
						
						if(departureStringDate!=null){
							departureStringDate = arrayCut[2]+"-"+arrayCut[0]+"-"+arrayCut[1];
							//departureStringDate = departureDate.getYear()+ (departureDate.getMonth()+1) + departureDate.getDay() 
						}else{
							
						}
					
					}
				});
				
				
				Label arriveDateLabel = new Label(stackComposite1, SWT.NONE);
				arriveDateLabel.setText("도착일 ");
				arriveDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(arriveDateLabel);
				
				arriveCalendar = Calendar.getInstance();
				arriveCalendar.add(Calendar.DATE, 0);
				arriveDate = new DateTime(stackComposite1, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130, 30).applyTo(arriveDate);
				arriveDate.setDate(arriveCalendar.get(Calendar.YEAR),0,1);
				arriveDate.setEnabled(false);
				
				
				//stackComposite2
				stackComposite2 = new Composite(stackComposite, SWT.None);
				PetraGridLayout.defaults().margin(20,10).spacing(0, 10).columns(4).applyTo(stackComposite2);
												//0,0              5
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(stackComposite2);
				
				
				
				//버튼_그림_왕복
				departureDateLabel = new Label(stackComposite2, SWT.NONE);
				departureDateLabel.setText("출발일 ");
				departureDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(departureDateLabel);
				
				departureCalendar = Calendar.getInstance();
				departureCalendar.add(Calendar.DATE, 0);
				departureDate = new DateTime(stackComposite2, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN | SWT.READ_ONLY);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130, 30).applyTo(departureDate);
				departureDate.setDate(departureCalendar.get(Calendar.YEAR),0,1);		
				departureStringDate = smpleDateFormat.format(date);  //현재날짜출력 //현재날짜출력
				departureDate.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						String allDate = e.widget.toString();	//DateTime {월/일/년도} 라고 출력
						int startCut = allDate.indexOf("{"); 
						int endCut = allDate.indexOf("}");  // 월/일/년도 만 짤라옴
						String dateCutting = allDate.substring(startCut+1, endCut);
						String arrayCut[] = dateCutting.split("/");
						if(departureStringDate!=null){
							departureStringDate = arrayCut[2]+"-"+arrayCut[0]+"-"+arrayCut[1];
						}else{
							
						}
					}
				});
				
				//도착일 달력/캘린더
				arriveDateLabel = new Label(stackComposite2, SWT.NONE);
				arriveDateLabel.setText("도착일 ");
				arriveDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(arriveDateLabel);
				
				arriveCalendar = Calendar.getInstance();
				arriveCalendar.add(Calendar.DATE, 0);
				arriveDate = new DateTime(stackComposite2, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN | SWT.READ_ONLY);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130, 30).applyTo(arriveDate);
				arriveDate.setDate(arriveCalendar.get(Calendar.YEAR),0,1);
				arriveStringDate =smpleDateFormat.format(date);  //현재날짜출력 //현재날짜출력
				arriveDate.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						String allDate = e.widget.toString();	//DateTime {월/일/년도} 라고 출력
						int startCut = allDate.indexOf("{"); 
						int endCut = allDate.indexOf("}");  // 월/일/년도 만 짤라옴
						String dateCutting = allDate.substring(startCut+1, endCut);
						String arrayCut[] = dateCutting.split("/");
						if(arriveStringDate!=null){
							arriveStringDate = arrayCut[2]+"-"+arrayCut[0]+"-"+arrayCut[1];
						}else{
							
						}
					}
				});

				
				
				// 실선
				final Composite groupComposite3 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite3);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite3);
				
				separatorLabel = new Label(groupComposite3, SWT.SEPARATOR | SWT.HORIZONTAL);
				PetraGridData.defaults().grab(true, false).applyTo(separatorLabel);		
				
				
				//일반석 비즈니스 석.. 등등
				final Composite groupComposite4 = new Composite(topComposite, SWT.NONE);
												//10,10
				PetraGridLayout.defaults().margin(30,10).spacing(0, 0).columns(4).applyTo(groupComposite4);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite4);
				
				Label flightClass = new Label(groupComposite4, SWT.NONE);
				flightClass.setText("좌석 등급 ");
				flightClass.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(100, 30).applyTo(flightClass);
				
				generalSeat = new Button(groupComposite4, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(90, 30).applyTo(generalSeat);
				generalSeat.setText("일반석");
				generalSeat.setFont(PetraFont.S8_FONT);
				//oneWay.setSelection(true); 
				
				generalSeat.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						//일반석 누를시 받아오는 것
						seatType = 0; 
						generalSeat.setFont(PetraFont.S8_B_FONT);
						businessClass.setFont(PetraFont.S8_FONT);
						firstClass.setFont(PetraFont.S8_FONT);
					}});
				
				businessClass = new Button(groupComposite4, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(130, 30).applyTo(businessClass);
				businessClass.setText("비즈니스석");
				businessClass.setFont(PetraFont.S8_FONT);
				businessClass.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						//비즈니스석 누를시 받아오는 것
						seatType = 1; 
						generalSeat.setFont(PetraFont.S8_FONT);
						businessClass.setFont(PetraFont.S8_B_FONT);
						firstClass.setFont(PetraFont.S8_FONT);
					}});
				
				firstClass = new Button(groupComposite4, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(100, 30).applyTo(firstClass);
				firstClass.setText("일등석");
				firstClass.setFont(PetraFont.S8_FONT);
				firstClass.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						//일등석 누를시 받아오는 것
						seatType = 2;
						generalSeat.setFont(PetraFont.S8_FONT);
						businessClass.setFont(PetraFont.S8_FONT);
						firstClass.setFont(PetraFont.S8_B_FONT);
					}});
				
				// 실선
				final Composite groupComposite5 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite5);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite5);
				
				separatorLabel = new Label(groupComposite5, SWT.SEPARATOR | SWT.HORIZONTAL);
				PetraGridData.defaults().grab(true, false).applyTo(separatorLabel);		
				
				
				//성인 소아 어린이
				
				final Composite groupComposite6 = new Composite(topComposite, SWT.None);
				PetraGridLayout.defaults().margin(20,0).spacing(0, 20).columns(3).applyTo(groupComposite6);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite6);
				
				///////성인
				final Composite adultComposite = new Composite(groupComposite6, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(2).applyTo(adultComposite);
				
				Label adultLabel = new Label(adultComposite, SWT.NONE);
				adultLabel.setText("성인");
				adultLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).applyTo(adultLabel);	
				
				//성인 부가 설명
				adultLabel = new Label(adultComposite, SWT.NONE);
				adultLabel.setText("만12세이상");
				adultLabel.setFont(PetraFont.S8_FONT);
				PetraGridData.defaults().grab(false, true)./*hint(85, 20).*/applyTo(adultLabel);
				
				
				/////////소아
				final Composite youngChildComposite = new Composite(groupComposite6, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(3).applyTo(youngChildComposite);
				
				Label youngChildLabel = new Label(youngChildComposite, SWT.NONE);
				youngChildLabel.setText("소아");
				youngChildLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).applyTo(youngChildLabel);	
				
				//소아 부가 설명
				youngChildLabel = new Label(youngChildComposite, SWT.NONE);
				youngChildLabel.setText("만12세미만");
				youngChildLabel.setFont(PetraFont.S8_FONT);
				PetraGridData.defaults().grab(false, true).hint(85, 20).applyTo(youngChildLabel);
				
				//소아 인포 그림
				youngChildLabel = new Label(youngChildComposite, SWT.NONE);
				youngChildLabel.setImage(InternUIResources.getImage("information.png"));
				final ToolTip youngChildTip = new ToolTip(getSite().getShell(), SWT.BALLOON);
				youngChildTip.setMessage("승객구분은 출발일의 나이 기준으로 적용됩니다.\n"+
		        		"만 12세 미만(국내선은 만 13세)의 소아 승객은 만 18세 이상의 보호자와 동일 클래스에\n"+
		        		"동반 탑승하여야 하며, 불가 시 비동반 소아 서비스를 신청해야 합니다.\n"+
		        		"자세한 내용은 FAQ 또는 서비스센터로 문의하여 주시기 바랍니다.");
		        youngChildTip.setAutoHide(true);
		        youngChildLabel.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						youngChildTip.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						youngChildTip.setVisible(false);
					}
				});
//				ToolBar youngChildtoolBar = new ToolBar (youngChildComposite, SWT.NONE);
//				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(youngChildtoolBar);											
//				PetraGridData.defaults().grab(true, false).hint(10, 10).applyTo(youngChildtoolBar);
//				final ToolItem youngChildToolItem = new ToolItem(youngChildtoolBar, SWT.NONE);
//				youngChildToolItem.setToolTipText("이 정보가 표시 된 이유");
//				youngChildToolItem.setImage(InternUIResources.getImage("information.png"));
				
				///////어린이
				final Composite kidComposite = new Composite(groupComposite6, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(3).applyTo(kidComposite);
			
				Label kidLabel = new Label(kidComposite, SWT.NONE);
				kidLabel.setText("어린이");
				kidLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).applyTo(kidLabel);	
				
				//어린이 부가 설명
				kidLabel = new Label(kidComposite, SWT.NONE);
				kidLabel.setText("24개월미만");
				kidLabel.setFont(PetraFont.S8_FONT);
				PetraGridData.defaults().grab(false, true).hint(85, 20).applyTo(kidLabel);
				
				//어린이 인포 그림
				kidLabel = new Label(kidComposite, SWT.NONE);
				kidLabel.setImage(InternUIResources.getImage("information2.png"));
				final ToolTip kidTip = new ToolTip(getSite().getShell(), SWT.BALLOON);
				kidTip.setMessage("승객구분은 출발일의 나이 기준으로 적용됩니다.\n"+
		        		"유아는 반드시 성인과 함께 예약하셔야 합니다. 만 2세 미만 유아 승객은 좌석을 점유하지 않는"+
		        		"경우 유아 요금으로 인터넷 예약 및 구매가 가능합니다.\n"+
		        		"유아 승객의 좌석 점유를 원하시는 경우 서비스 센터를 통하여 소아요금으로 예약 및 구매하시기 바랍니다.");
				kidTip.setAutoHide(true);
				kidLabel.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						kidTip.setVisible(true);
					}
					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						kidTip.setVisible(false);
					}
				});
		        
		        //성인 소아 어린이 스피너
		        
		        final Composite groupComposite7 = new Composite(topComposite, SWT.None);
				PetraGridLayout.defaults().margin(50,0).spacing(0, 100).columns(3).applyTo(groupComposite7);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite7);
				
				//성인 스피너
				adultSpinner = new Spinner(groupComposite7, SWT.BORDER | SWT.READ_ONLY);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 40).applyTo(adultSpinner);
				PetraGridData.defaults().grab(false, false).hint(40,25).applyTo(adultSpinner);
				adultSpinner.setMinimum(1);
				adultSpinner.setMaximum(9);
				adultSpinner.setSelection(1);
				adultSpinner.setIncrement(1);
				adultSpinner.setPageIncrement(1);
		        
				//소아 스피너
				youngChildSpinner = new Spinner(groupComposite7, SWT.BORDER | SWT.READ_ONLY);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 40).applyTo(youngChildSpinner);
				PetraGridData.defaults().grab(false, false).hint(40,25).applyTo(youngChildSpinner);
				youngChildSpinner.setMinimum(0);
				youngChildSpinner.setMaximum(9);
				youngChildSpinner.setSelection(0);
				youngChildSpinner.setIncrement(1);
				youngChildSpinner.setPageIncrement(1);
				
			
				//유아 스피너
				kidSpinner = new Spinner(groupComposite7, SWT.BORDER | SWT.READ_ONLY);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 40).applyTo(kidSpinner);
				PetraGridData.defaults().grab(false, false).hint(40,25).applyTo(kidSpinner);
				kidSpinner.setMinimum(0);
				kidSpinner.setMaximum(9);
				kidSpinner.setSelection(0);
				kidSpinner.setIncrement(1);
				kidSpinner.setPageIncrement(1);
				
				// 실선
				final Composite groupComposite8 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite8);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite8);
				
				separatorLabel = new Label(groupComposite8, SWT.SEPARATOR | SWT.HORIZONTAL);
				PetraGridData.defaults().grab(true, false).applyTo(separatorLabel);		
		        
				
				//항공편 조회 및 확인
			
				final Composite groupComposite9 = new Composite(topComposite, SWT.None);
				PetraGridLayout.defaults().margin(200,0).spacing(0, 100).columns(1).applyTo(groupComposite9);
				PetraGridData.defaults().grab(false, false).applyTo(groupComposite9);			
					
			
				ToolBar aviationWayCheckToolBar = new ToolBar (groupComposite9, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 100).applyTo(aviationWayCheckToolBar);
				PetraGridData.defaults().grab(false, false).applyTo(aviationWayCheckToolBar);
				final ToolItem aviationWayCheckToolItem = new ToolItem(aviationWayCheckToolBar, SWT.NONE);
													
				aviationWayCheckToolItem.setImage(InternUIResources.getImage("aviationWayCheck.png"));
			
				aviationWayCheckToolItem.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//조회를 누르면 데이터가 넘어가게 처리
						
						//항공편 조회를 누를 경우
						if(!dataValidation()){
							//빈공백이 있는지
							return ;	
							//무조건 이부분을 넘어가야 함
						}
							//CONSUMER_ID를 일단 사용자 모드이므로 Default로 설정 (Long)
							data.CONSUMER_ID = (long) 2859693;
							data.CONSUMER_NUMBER = (long) IBatisAdapter.queryForObject("intern_getSeq");
							data.AIRLINETICKETTYPE = dataValidation; //편도 왕복 구분 데이터
							data.DEPARTURE_PLACE = departureCombo.getSelectionIndex();	//출발지
							data.ARRIVE_PLACE = destinationCombo.getSelectionIndex();	//도착지
							data.DEPARTURE_DATE = departureStringDate;
							data.ARRIVE_DATE = arriveStringDate;
							if(data.ARRIVE_DATE==null){
								data.ARRIVE_DATE=  "0000-00-00";
							}
							data.SEAT_TYPE = seatType;
							data.SEATNUMBER = kidSpinner.getSelection() + adultSpinner.getSelection() + youngChildSpinner.getSelection();
							data.CHECKIN = 1;  //1이 false
			
							//trim은 공백제거
//							if(manBtn.getSelection()){
//								data.SEX = 0;
//							}else{
//								data.SEX = 1;
//							}
							if(modifyData == null){
								//새로 검색할 경우
								//출발지 검색
								//data.CONSUMER_ID = (long) IBatisAdapter.queryForObject("intern_getSeq");
													//Consumer 에다가 넣기
								getSearchDto(data);
								setConsumerDtoDATA(data);
								
							}else{
								//기존 데이터가 있는 경우
						/*		if(type){
									data.INTERN_ID = (long) IBatisAdapter.queryForObject("intern_getSeq");
																//시퀀스
									IBatisAdapter.execute("intern_insertIntern",data);
								}else{
									//기존의 아이디를 사용
									data.INTERN_ID = modifyData.INTERN_ID;
									IBatisAdapter.execute("intern_updateIntern",data);
								
								}*/
							}
							
						
						placeSashForm.setWeights(new int[]{0,1}); //항공 테이블이 나오게
						bookingListSashForm.setWeights(new int[]{1,0}); //항공 테이블이 나오게
						
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
								
				//////////////////////////////////SearchView
				//비행기 예매시스템 우측
				
				//SashForm 설정
				bookingListSashForm = new SashForm(placeSashForm, SWT.VERTICAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(bookingListSashForm);
				PetraGridData.defaults().applyTo(bookingListSashForm);
				bookingListSashForm.setSashWidth(0);
				
				
				
				
				//SashForm 위에 한개
				Composite tableComposite = new Composite(bookingListSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(40, 40).spacing(10, 0).applyTo(tableComposite);
				PetraGridData.defaults().applyTo(tableComposite);
				
				Composite tableFilterComposite = new Composite(tableComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(315, 0).spacing(0, 0).applyTo(tableFilterComposite);
				PetraGridData.defaults().grab(true,false).hint(30,35).applyTo(tableFilterComposite);
				
				Composite tableUnderComposite = new Composite(tableComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(tableUnderComposite);
				PetraGridData.defaults().applyTo(tableUnderComposite);
				
				String[] columnHeader = {"항공","좌석타입","출발 시간","도착 시간","남은 좌석"};
				//int[] columnWidth = {70,70,60,70,20};
				int[] columnWidth = {150,150,150,150,150};
				final TableFilterComposite filterComposite = new TableFilterComposite(tableFilterComposite, columnHeader, false, SWT.RIGHT);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).applyTo(filterComposite);
				searchTableViewer = new TableViewer(tableUnderComposite, SWT.FULL_SELECTION | SWT.MULTI );
				searchTable = searchTableViewer.getTable();	
				
				PetraGridLayout.defaults().applyTo(searchTable);
				PetraGridData.defaults().span(2, 1).applyTo(searchTable);
										//이렇게 해놓으면 테이블이 툴바에 맞춰진다. 고로 span(3,1)로 해야함
				TableUtil.makeTableColumns(searchTable, columnHeader, columnWidth);
				// util  프로젝트 할 때도 설정하면됨
				
				searchTable.setHeaderVisible(true);
				//false 일 경우 : 이름,제목 등등 사라짐 
				searchTable.setLinesVisible(true);
					//false 일 경우 : 수평 라인이 사라짐
				
				searchTableViewer.setContentProvider(new ArrayContentProvider());
				searchTableViewer.setLabelProvider(new SearchTableLabelProvider());
				
				searchTable.addFocusListener(new FocusListener(){
					@Override
					public void focusGained(FocusEvent e) {
						// TODO Auto-generated method stub
						bookingListSashForm.setWeights(new int[]{9,1}); //항공 테이블이 나오게
					}

					@Override
					public void focusLost(FocusEvent e) {
						// TODO Auto-generated method stub
						bookingListSashForm.setWeights(new int[]{1,0}); //항공 테이블이 나오게
					}
					
				});
				
				//1. 비행기예매시스템 -> 항공편 조회 -> 테이블 더블클릭시 실행되는 창
				Composite tableBookingListComposite = new Composite(bookingListSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(200, 0).spacing(10, 60).columns(2).applyTo(tableBookingListComposite);
				PetraGridData.defaults().applyTo(tableBookingListComposite);
				

				ToolBar reSearchToolBar = new ToolBar (tableBookingListComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 100).applyTo(reSearchToolBar);
				PetraGridData.defaults().grab(false, false).applyTo(reSearchToolBar);
				final ToolItem reSearchToolItem = new ToolItem(reSearchToolBar, SWT.NONE);
													
				reSearchToolItem.setImage(InternUIResources.getImage("reSearch.png"));
																//재검색
				reSearchToolItem.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						placeSashForm.setWeights(new int[]{1,0}); //항공 테이블이 나오게
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
			
				ToolBar requestToolBar = new ToolBar (tableBookingListComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 100).applyTo(requestToolBar);
				//PetraGridData.defaults().grab(false,false).hint(100,40).applyTo(button2);
				PetraGridData.defaults().grab(false, false).applyTo(requestToolBar);
				final ToolItem requestToolItem = new ToolItem(requestToolBar, SWT.NONE);
													
				requestToolItem.setImage(InternUIResources.getImage("reQuest.png"));
																//신청
				requestToolItem.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//NavigationView 에서 선택되게합니다.
						navigationView.getTable1().select(1);
						
						//searchTable에서 selection
						searchPlaneTableIndex = searchTable.getSelectionIndex();
						placeStackLayout.topControl = bookingListCheckIn;
						placeStackComposite.layout(); 
						checkInSashForm.setWeights(new int[]{1,0});
						getRequest(data,searchPlaneTableIndex);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
		
				///예약목록 체크인  
				//placeStackComposite 2예약목록 체크인  클릭시 나오는 좌측 화면
				//☆
				Composite bookingListComposite = new Composite(checkInSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(30, 30).spacing(10, 10).applyTo(bookingListComposite);
				PetraGridData.defaults().applyTo(bookingListComposite);
				
				Composite bookingListComposite1 = new Composite(bookingListComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).columns(2).applyTo(bookingListComposite1);
				PetraGridData.defaults().applyTo(bookingListComposite1);
				String[] bookingListColumnHeader = {"예약번호","항공권종류","출발지","도착지","출발일","좌석등급","체크인여부"};
				int[] bookingListColumnWidth = {90,105,70,70,100,90,105};
				//int[] bookingListColumnWidth = {90,150,150,150,150,150,150};
				bookingTableViewer = new TableViewer(bookingListComposite1, SWT.FULL_SELECTION | SWT.MULTI);
				bookingTable = bookingTableViewer.getTable();	
				
				PetraGridLayout.defaults().applyTo(bookingTable);
				PetraGridData.defaults().span(2, 1).applyTo(bookingTable);
										//이렇게 해놓으면 테이블이 툴바에 맞춰진다. 고로 span(3,1)로 해야함
				TableUtil.makeTableColumns(bookingTable, bookingListColumnHeader, bookingListColumnWidth);
				
				bookingTable.setHeaderVisible(true); 
				bookingTable.setLinesVisible(true);
					//false 일 경우 : 수평 라인이 사라짐
				bookingTableViewer.setContentProvider(new ArrayContentProvider());
				bookingTableViewer.setLabelProvider(bookingTableLabelProvider);
				//bookingTableViewer.setLabelProvider(new BookingTableLabelProvider());
				
				bookingTableViewer.setInput(bookingList);
				bookingTableViewer.refresh();

				bookingTable.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						String[] arrive= {"도쿄", "베이징","뉴욕","파리"}; 
						bookingListTableIndex = bookingTable.getSelectionIndex();
						consumerBookingData = bookingList.get(bookingListTableIndex); 
						
						//데이터 저장
						setConsumerBookingData(consumerBookingData);
						
						//체크인에 입력 목적지/예약번호 라벨 입력
						for(int i=0; i<arrive.length; i++){
							if(consumerBookingData.ARRIVE_PLACE==i){
								insertCheckInLabel2.setText(arrive[i]);
							}
						}
						
						insertCheckInLabel4.setText(Long.toString(consumerBookingData.BOOKING_NUMBER));
						//consumerBookingData.BOOKING_NUMBER
						//★  (long) 2859693
						
						if(consumerBookingData.CHECKIN==1){
							//체크인이 되지 않은 경우
							checkInSashForm.setWeights(new int[]{0,1}); //항공 테이블이 나오게
							checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
						}else if(consumerBookingData.CHECKIN==0){
							//체크인이 된 경우
							MessageDialog.openWarning(null, "체크인완료", "이미 체크인이 되었습니다");
						}
					}//end of mouseDoubleClick 
				});
				
				//placeStackComposite 2예약목록 체크인  클릭시 나오는 우측 화면
				//SashForm 설정
				checkInSeatConfirmationSashForm = new SashForm(checkInSashForm, SWT.HORIZONTAL); 
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(checkInSeatConfirmationSashForm);
				PetraGridData.defaults().applyTo(checkInSeatConfirmationSashForm);
				checkInSeatConfirmationSashForm.setSashWidth(0);
				
				final Composite checkInTopComposite = new Composite(checkInSeatConfirmationSashForm, SWT.NONE );
				PetraGridLayout.defaults().margin(0, 0).applyTo(checkInTopComposite);
				PetraGridData.defaults().grab(false, true).hint(780,40).applyTo(checkInTopComposite);
				
				final Composite checkInGroupComposite1 = new Composite(checkInTopComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(20,0).spacing(0, 0).applyTo(checkInGroupComposite1);
				PetraGridData.defaults().grab(false, false).hint(780,260).applyTo(checkInGroupComposite1);	
				
				final Group checkInGroup = new Group(checkInGroupComposite1,  SWT.NONE );
				PetraGridLayout.defaults().margin(70,0).spacing(0, 0).applyTo(checkInGroup);
				PetraGridData.defaults().grab(false, true).hint(640,260).applyTo(checkInGroup);	
															//
				
				Label explanationCheckInLabel1 = new Label(checkInGroup, SWT.NONE| SWT.CENTER);
				explanationCheckInLabel1.setText("다음 고객은 웹 CHECK-IN 사용이 불가능합니다");
				explanationCheckInLabel1.setFont(PetraFont.S13_B_FONT);
				
				Label explanationCheckInLabel2 = new Label(checkInGroup, SWT.NONE| SWT.CENTER);
				explanationCheckInLabel2.setText(
				"● 임신한 여성\n"+
				"● 유아동반 고객\n"+
				"● 거동이 불편한 고객, 특별한 도움을 필요로 하는 고객\n"+
				"● 16세 미만으로 혼자 여행하는 고객\n"+
				"● 치료가 필요하거나 환자인 고객\n"+
				"● 다 구간 이용 고객\n"
						);
				explanationCheckInLabel2.setFont(PetraFont.S10_FONT);
				
				
				final Composite checkInGroupComposite2 = new Composite(checkInTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(40,40).spacing(10, 5).columns(3).applyTo(checkInGroupComposite2);
				PetraGridData.defaults().grab(false, false).applyTo(checkInGroupComposite2);	
				
				//체크인 - ARRIVE표시
				Label insertCheckInLabel1 = new Label(checkInGroupComposite2, SWT.NONE);
				insertCheckInLabel1.setText("도착 ");
				insertCheckInLabel1.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertCheckInLabel1);
				
				insertCheckInLabel2 = new Label(checkInGroupComposite2,  SWT.BORDER);
				insertCheckInLabel2.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertCheckInLabel2);
				
				Label insertCheckInLabel2_1 = new Label(checkInGroupComposite2, SWT.NONE);
				insertCheckInLabel2_1.setText("");
				
				//체크인 - 예약번호 
				Label insertCheckInLabel3 = new Label(checkInGroupComposite2, SWT.NONE);
				insertCheckInLabel3.setText("예약번호 ");
				insertCheckInLabel3.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertCheckInLabel3);
				insertCheckInLabel4= new Label(checkInGroupComposite2, SWT.BORDER );
				insertCheckInLabel4.setFont(PetraFont.S9_FONT);
				
			//	insertCheckInLabel4.setText(Long.toString(getBookingNumber()));
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertCheckInLabel4);
				vacuumLabel = new Label(checkInGroupComposite2, SWT.NONE);
				
				//재선택   체크인
				final Composite checkInGroupComposite3 = new Composite(checkInTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(130,20).spacing(20, 20).columns(2).applyTo(checkInGroupComposite3);
				PetraGridData.defaults().grab(false, false).applyTo(checkInGroupComposite3);	
				
				ToolBar insertCheckInToolBar = new ToolBar (checkInGroupComposite3, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(insertCheckInToolBar);
				PetraGridData.defaults().grab(true, false).hint(100,50).applyTo(insertCheckInToolBar);
				final ToolItem insertRechoiceToolItem = new ToolItem(insertCheckInToolBar, SWT.NONE);
				
				ToolBar insertCheckInToolBar2 = new ToolBar (checkInGroupComposite3, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 30).applyTo(insertCheckInToolBar2);
				PetraGridData.defaults().grab(true, false).hint(100,60).applyTo(insertCheckInToolBar2);
				final ToolItem insertCheckInToolItem1 = new ToolItem(insertCheckInToolBar2, SWT.NONE);
					
				insertRechoiceToolItem.setImage(InternUIResources.getImage("reSelected.png"));
				insertCheckInToolItem1.setImage(InternUIResources.getImage("checkIn.png"));
			
				//재선택
				insertRechoiceToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//조회를 누르면 데이터가 넘어가게 처리
						//재선택 버튼을 누를 경우
						checkInSashForm.setWeights(new int[]{1,0});
					}
				});
				
				//체크인
				insertCheckInToolItem1.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//조회를 누르면 데이터가 넘어가게 처리
						
						//전체 초기화
						CLabelReset(listLabel);
						
						//count 초기화
						for(int i = 0; i<3;i++){  
							for(int j = 0 ;j<22; j++){
								count[(22*i)+j] = 0;
							} 
						}
						selectedCoordinateList = (List<CoordinateDto>) IBatisAdapter.queryForList("coordinate_selectCoordinateOne",consumerBookingData.PLANE_ID);
						//클릭한 비행기 자리를 모두 불러온다
						for(int a= 0; a< selectedCoordinateList.size() ; a++){
							CoordinateDto coordinateDto = selectedCoordinateList.get(a);
							System.out.println("야아아아");
							System.out.println(coordinateDto.PLANE_ID);
							System.out.println(coordinateDto.COUNT);
							count[coordinateDto.COUNT] = 1;
							//클릭해둔 비행기 자리를 모두 1로 설정
							final CLabel seatSelectionCLabel = listLabel.get(coordinateDto.COUNT);
							if(seatSelectionCLabel.getData().equals(coordinateDto.COUNT)){
																				//220,020,060
								seatSelectionCLabel.setBackground(new Color(null,211,211,211));}						}
																			//빨강
						/**/
						//체크인 버튼을 누를 경우
						checkInSeatConfirmationSashForm.setWeights(new int[]{0,1});
					}
				});
				
				//체크인 누르고 넘어가서 좌석 설정
				final Composite seatConfirmationTopComposite = new Composite(checkInSeatConfirmationSashForm, SWT.NONE );
				PetraGridLayout.defaults().margin(0, 0).applyTo(seatConfirmationTopComposite);
				PetraGridData.defaults().grab(false, true).hint(780,40).applyTo(seatConfirmationTopComposite);
				
				final Composite seatConfirmationGroupComposite1 = new Composite(seatConfirmationTopComposite, SWT.BORDER );
				PetraGridLayout.defaults().margin(155,129).spacing(10, 6).columns(22).applyTo(seatConfirmationGroupComposite1);
				PetraGridData.defaults().grab(false, true).hint(780,260).applyTo(seatConfirmationGroupComposite1);
				seatConfirmationGroupComposite1.setBackgroundImage(InternUIResources.getImage("plane.png"));
				
				
				for(int i = 0; i<3;i++){  
					for(int j = 0 ;j<22; j++){
						clbel = new CLabel(seatConfirmationGroupComposite1, SWT.NONE);
						PetraGridData.defaults().grab(false, false).hint(17,18).applyTo(clbel);
						//listLabel에 모두 넣는다(처음에는 모두 선택됨)
						listLabel.add(clbel);
						//clbel.setBackground(new Color(null,245,245,220));
						
						clbel.setText(Integer.toString((22*i)+j));
						clbel.setFont(PetraFont.S6_FONT);
						clbel.setData((22*i)+j);
						
						//clbel.setData(new int[]{i,j});
						 
						final CLabel seatSelectionCLabel = listLabel.get(((22*i)+j));
														//전체 listLabel를 불러와 각자 이벤트 처리
//						if(seatSelectionCLabel.getData().equals(1)){
//							seatSelectionCLabel.setBackground(new Color(null,220,020,060));}
								
						seatSelectionCLabel.addMouseListener(new MouseAdapter(){
							@Override
							public void mouseUp(MouseEvent e) {
								// TODO Auto-generated method stub
								
								//listLabel.remove(seatSelectionCLabel);
								//선택 안된 곳
								
								CLabelCount(seatSelectionCLabel);
								//선택된 라벨을 불러와 CLabelCount에 넣는다
							}//end of MouseUp
							
							@Override
							public void mouseDown(MouseEvent e) {
								// TODO Auto-generated method stub
								
								selectLabel.remove(seatSelectionCLabel);
								//listLabel.remove(seatSelectionCLabel);
							}
						});
					}//end of Inner For
				}//end of OutFor
				
				//비행기 그림 아래 깔 컴포짓
				final Composite seatConfirmationGroupComposite2 = new Composite(seatConfirmationTopComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(0,0).spacing(0,0).applyTo(seatConfirmationGroupComposite2);
				PetraGridData.defaults().grab(false, false).hint(780, 120).applyTo(seatConfirmationGroupComposite2);
				
				//예약정보보기, 좌석선택 다시하기, 좌석선택 도움말
				final Composite seatConfirmationInnerGroupComposite1 = new Composite(seatConfirmationGroupComposite2, SWT.NONE );
				PetraGridLayout.defaults().margin(40,0).spacing(0, 0).columns(3).applyTo(seatConfirmationInnerGroupComposite1);
				PetraGridData.defaults().grab(false, true).hint(780,50).applyTo(seatConfirmationInnerGroupComposite1);
				
				ToolBar seatConfirmationToolBar = new ToolBar (seatConfirmationInnerGroupComposite1, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(seatConfirmationToolBar);
				PetraGridData.defaults().grab(true, false).hint(0, 50).applyTo(seatConfirmationToolBar);
				final ToolItem bookingDataExampleToolItem = new ToolItem(seatConfirmationToolBar, SWT.NONE);
				final ToolItem seatSelectionAgainToolItem = new ToolItem(seatConfirmationToolBar, SWT.NONE);
				final ToolItem seatSelectionHelpToolItem = new ToolItem(seatConfirmationToolBar, SWT.NONE);	

				bookingDataExampleToolItem.setImage(InternUIResources.getImage("bookingDataExample.png"));
				seatSelectionAgainToolItem.setImage(InternUIResources.getImage("seatSelectionAgain.png"));
				seatSelectionHelpToolItem.setImage(InternUIResources.getImage("seatSelectionHelp.png"));
				
				final Composite seatConfirmationInnerGroupComposite2 = new Composite(seatConfirmationGroupComposite2, SWT.NONE );
				PetraGridLayout.defaults().margin(540,0).spacing(0, 0).applyTo(seatConfirmationInnerGroupComposite2);
				PetraGridData.defaults().grab(false, false).hint(780,50).applyTo(seatConfirmationInnerGroupComposite2);
				
				ToolBar seatConfirmationToolBar2 = new ToolBar (seatConfirmationInnerGroupComposite2, SWT.NONE);
				PetraGridLayout.defaults().margin(540, 0).spacing(0, 0).applyTo(seatConfirmationToolBar2);
				PetraGridData.defaults().grab(false, false).hint(780, 50).applyTo(seatConfirmationToolBar2);
				final ToolItem seatSelectionOKToolItem = new ToolItem(seatConfirmationToolBar2, SWT.NONE);	
				seatSelectionOKToolItem.setImage(InternUIResources.getImage("ok.png"));
				
				//예약정보보기
				bookingDataExampleToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//consumerBookingData
					}
				});
				
				//좌석선택 다시하기
				seatSelectionAgainToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
						CLabelSelected();
					}
				});
				
				//좌석선택 도움말
				seatSelectionHelpToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
				//확인버튼 
				seatSelectionOKToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						int[] array = null;
					/*	
						for(int i=0 ; i<selectLabel.size(); i++){
							CLabel seatSelectionCLabel = selectLabel.get(i);
							array[i] = (int) seatSelectionCLabel.getData();
						}*/
						
						DialogReturn<Object> returnData = new BookingInfoDialog(getSite().getShell(),consumerBookingData,array).openWithResult();
						if(returnData.retCode == Window.OK){
							//NavigationView 에서 선택되게합니다.
							navigationView.getTable1().select(2);
							
							
							//좌석 신청 클릭을 한경우
							for(int i=0 ; i<selectLabel.size(); i++){
								CLabel seatSelectionCLabel = selectLabel.get(i);
								CoordinateDto coordinateDto = new CoordinateDto();
								//coordinateDto.setCOORDINATE_NUMBER((long) IBatisAdapter.queryForObject("intern_getSeq"));
								coordinateDto.setPLANE_ID(consumerBookingData.PLANE_ID);
								coordinateDto.setCOUNT(Integer.parseInt(seatSelectionCLabel.getText()));
								IBatisAdapter.execute("coordinate_insertCoordinate",coordinateDto);
							}
							CLabelReset(listLabel);
							//여기에 select.. 그거
							
							//★
							//checkin 1->0 으로 변경
							consumerBookingData.CHECKIN = 0;
							IBatisAdapter.execute("consumerBooking_updateConsumerBooking",consumerBookingData);
							getConsumerChecking();
							placeStackLayout.topControl = allCheckList;
							placeStackComposite.layout(); 
							allCheckSashForm.setWeights(new int[]{1,0});
						}//end of OK
						
						
					}
				});
				
				
				//☆
				///3. 전체목록
				///예약목록 체크인  
				//placeStackComposite 2예약목록 체크인  클릭시 나오는 좌측 화면
				
				Composite allBookingListComposite = new Composite(allCheckSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(30, 30).spacing(10, 10).applyTo(allBookingListComposite);
				PetraGridData.defaults().applyTo(allBookingListComposite);
				
				Composite allBookingListComposite1 = new Composite(allBookingListComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).columns(2).applyTo(allBookingListComposite1);
				PetraGridData.defaults().applyTo(allBookingListComposite1);
				String[] allBookingListColumnHeader = {"예약번호","항공권종류","출발지","도착지","출발일","좌석등급","체크인여부"};
				int[] allBookingListColumnWidth = {90,105,70,70,110,90,105};
				allBookingTableViewer = new TableViewer(allBookingListComposite1, SWT.FULL_SELECTION | SWT.MULTI);
				allBookingTable = allBookingTableViewer.getTable();	
				
				PetraGridLayout.defaults().applyTo(allBookingTable);
				PetraGridData.defaults().span(2, 1).applyTo(allBookingTable);
										//이렇게 해놓으면 테이블이 툴바에 맞춰진다. 고로 span(3,1)로 해야함
				TableUtil.makeTableColumns(allBookingTable, allBookingListColumnHeader, allBookingListColumnWidth);
				
				allBookingTable.setHeaderVisible(true); 
				allBookingTable.setLinesVisible(true);
					//false 일 경우 : 수평 라인이 사라짐
				allBookingTableViewer.setContentProvider(new ArrayContentProvider());
				allBookingTableViewer.setLabelProvider(bookingTableLabelProvider);
				
				allBookingTableViewer.setInput(allBookingList);
				allBookingTableViewer.refresh();

				allBookingTable.addMouseListener(new MouseAdapter(){

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						//★
						String[] departure= {"인천", "김포","대구","제주"}; 
						String[] arrive = {"도쿄","베이징","뉴욕","파리"};
						allBookingListTableIndex = allBookingTable.getSelectionIndex();
						consumerBookingData = allBookingList.get(allBookingListTableIndex); 
						
						//데이터 저장
						setConsumerBookingData(consumerBookingData);

						insertReservationConfirmationLabel2.setText(Long.toString(consumerBookingData.BOOKING_NUMBER));
						
						//출발지
						for(int i=0; i<departure.length; i++){
							if(consumerBookingData.DEPARTURE_PLACE==i){
								insertReservationConfirmationLabel4.setText(departure[i]);
							}
						}
						insertReservationConfirmationLabel6.setText(consumerBookingData.DEPARTURE_DATE);
	
						//도착지
						for(int i=0; i<arrive.length; i++){
							if(consumerBookingData.ARRIVE_PLACE==i){
								insertReservationConfirmationLabel8.setText(arrive[i]);
							}
						}
						
						
						//좌석 종류
						if(consumerBookingData.SEAT_TYPE==0){
							insertReservationConfirmationLabel12.setText("일반석");
						}else if(consumerBookingData.SEAT_TYPE==1){
							insertReservationConfirmationLabel12.setText("비즈니스석");
						}else if(consumerBookingData.SEAT_TYPE==2){
							insertReservationConfirmationLabel12.setText("일등석");
						}else{	}
						
						
						insertReservationConfirmationLabel14.setText(consumerBookingData.FLIGHT_NAME);
						
						//consumerBookingData.BOOKING_NUMBER
						// (long) 2859693
						consumerBookingData = (ConsumerBookingDto) IBatisAdapter.queryForObject("consumerBookingNumber_selectConsumerBookingNumber",consumerBookingData);
						
						if(consumerBookingData.CHECKIN==1){
							//체크인이 되지 않은 경우
							MessageDialog.openWarning(null, "체크인필수", "체크인 해주시기 바랍니다.");
								//메세지 다이어로그가 아닌 다이어로그를 따로 만들어서 넘어가보게 해보자
							//체크인에 입력 목적지/예약번호 라벨 입력
							for(int i=0; i<arrive.length; i++){
								if(consumerBookingData.ARRIVE_PLACE==i){
									insertCheckInLabel2.setText(arrive[i]);
								}
							}
							navigationView.getTable1().select(1);
							insertCheckInLabel4.setText(Long.toString(consumerBookingData.BOOKING_NUMBER));
							placeStackLayout.topControl = bookingListCheckIn;
							placeStackComposite.layout(); 
							checkInSashForm.setWeights(new int[]{0,1}); //항공 테이블이 나오게
							checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
							
						}else if(consumerBookingData.CHECKIN==0){
							//체크인이 된 경우
							//테이블 컬럼 클릭시 SashForm 이동
							allCheckSashForm.setWeights(new int[]{0,1}); 
							//checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
						}
					
					}
				});
				
				
				//3 전체확인 클릭시 나오는 sashform 오른쪽부분
				//SashForm 설정

				reservationConfirmationSashForm = new SashForm(allCheckSashForm, SWT.HORIZONTAL); 
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(reservationConfirmationSashForm);
				PetraGridData.defaults().applyTo(reservationConfirmationSashForm);
				reservationConfirmationSashForm.setSashWidth(0);
				
				final ScrolledComposite reservationConfirmationTopScrolledComposite = new ScrolledComposite(reservationConfirmationSashForm, SWT.H_SCROLL | SWT.V_SCROLL  );
				PetraGridLayout.defaults().margin(0, 0).applyTo(reservationConfirmationTopScrolledComposite);
				PetraGridData.defaults().grab(false, true).hint(620,40).applyTo(reservationConfirmationTopScrolledComposite);
				
				final Composite reservationConfirmationTopComposite = new Composite(reservationConfirmationTopScrolledComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(0, 0).applyTo(reservationConfirmationTopComposite);
				PetraGridData.defaults().grab(false, true).hint(620,40).applyTo(reservationConfirmationTopComposite);
															//780,40
				
				reservationConfirmationTopScrolledComposite.setContent(reservationConfirmationTopComposite);
				reservationConfirmationTopScrolledComposite.setMinSize(600, 600);
				reservationConfirmationTopScrolledComposite.setExpandHorizontal(true);
				reservationConfirmationTopScrolledComposite.setExpandVertical(true);
				reservationConfirmationTopScrolledComposite.addListener(SWT.Activate, new Listener() {

						@Override
						public void handleEvent(Event event) {
							// TODO Auto-generated method stub
							
						}
			            });
				
				
				final Composite reservationConfirmationGroupComposite1 = new Composite(reservationConfirmationTopComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(20,0).spacing(0, 0).applyTo(reservationConfirmationGroupComposite1);
				PetraGridData.defaults().grab(false, false).hint(620,150).applyTo(reservationConfirmationGroupComposite1);	
														  //780,150
				
				
				
				final Group reservationConfirmationGroup = new Group(reservationConfirmationGroupComposite1,   SWT.COLOR_GRAY | SWT.BOLD);
											//원래 70,120
				PetraGridLayout.defaults().margin(200,0).spacing(0, 70).applyTo(reservationConfirmationGroup);
				PetraGridData.defaults().grab(false, true).hint(620,150).applyTo(reservationConfirmationGroup);	
															//750,150
				
				Label explanationReservationConfirmationLabel1 = new Label(reservationConfirmationGroup, SWT.NONE);
				explanationReservationConfirmationLabel1.setText("예약 정보 확인");
				explanationReservationConfirmationLabel1.setFont(PetraFont.S15_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(400,50).applyTo(explanationReservationConfirmationLabel1);
																//550
				
				Label explanationReservationConfirmationLabel2 = new Label(reservationConfirmationGroup, SWT.NONE);
				explanationReservationConfirmationLabel2.setText(
				"● 체크-인이 된 상태\n"+
				"● 좌석 교체가 불 가능합니다.\n");
				explanationReservationConfirmationLabel2.setFont(PetraFont.S10_FONT);
				PetraGridData.defaults().grab(false, false).hint(400,60).applyTo(explanationReservationConfirmationLabel2);
															//ㅇ590
				
				final Composite reservationConfirmationGroupComposite2 = new Composite(reservationConfirmationTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(85,10).spacing(10, 5).columns(3).applyTo(reservationConfirmationGroupComposite2);
												//40
				PetraGridData.defaults().grab(false, false).applyTo(reservationConfirmationGroupComposite2);	
				
				//예약확인 - Booking Number표시
				Label insertReservationConfirmationLabel1 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel1.setText("예약 번호 ");
				insertReservationConfirmationLabel1.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel1);
				
				insertReservationConfirmationLabel2 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel2.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel2);
				
				// Booking Number 인포메이션 사진
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				//informationLabel1.setImage(InternUIResources.getImage("information2.png"));
				
				/*final ToolTip informationTip1 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip1.setMessage("선택하신 예약번호입니다");
				informationTip1.setAutoHide(true);
				informationLabel1.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip1.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip1.setVisible(false);
					}});*/
				
				//예약확인 - Departure표시
				Label insertReservationConfirmationLabel3 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel3.setText("출발지 ");
				insertReservationConfirmationLabel3.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel3);
				
				insertReservationConfirmationLabel4 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel4.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel4);
				
				// Departure 인포메이션 사진
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				/*informationLabel2.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip2 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip2.setMessage("선택하신 출발지입니다");
				informationTip2.setAutoHide(true);
				informationLabel2.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip2.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip2.setVisible(false);
					}
					});*/
				
				//예약확인 - Departure Date표시
				Label insertReservationConfirmationLabel5 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel5.setText("출발날짜 ");
				insertReservationConfirmationLabel5.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel5);
				
				insertReservationConfirmationLabel6 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel6.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel6);
				
				// Departure Date 인포메이션 사진
				Label informationLabel3 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel3.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip3 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip3.setMessage("출발지 기준 비행기 탑승 날짜입니다");
				informationTip3.setAutoHide(true);
				informationLabel3.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip3.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip3.setVisible(false);
					}});
				
				
				//예약확인 - AARIVE 표시
				Label insertReservationConfirmationLabel7 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel7.setText("도착지 ");
				insertReservationConfirmationLabel7.setFont(PetraFont.S10_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel7);
				
				insertReservationConfirmationLabel8 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel8.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel8);
				
				// AARIVE 인포메이션 사진
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				/*informationLabel4.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip4 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip4.setMessage("선택하신 도착지입니다");
				informationTip4.setAutoHide(true);
				informationLabel4.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip4.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip4.setVisible(false);
					}});
				*/
				
				//예약확인 - AARIVE DATE표시
				Label insertReservationConfirmationLabel9 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel9.setText("도착날짜 ");
				insertReservationConfirmationLabel9.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel9);
				
				insertReservationConfirmationLabel10 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel10.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel10);
				
				// AARIVE DATE 인포메이션 사진
				Label informationLabel5 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel5.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip5 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip5.setMessage("도착장소 기준 도착지 날짜입니다");
				informationTip5.setAutoHide(true);
				informationLabel5.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip5.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip5.setVisible(false);
					}});
				
				
				//예약확인 - SEAT TYPE표시
				Label insertReservationConfirmationLabel11 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel11.setText("좌석 종류 ");
				insertReservationConfirmationLabel11.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel11);
				
				insertReservationConfirmationLabel12 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel12.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel12);
				
				// SEAT TYPE 인포메이션 사진
				Label informationLabel6 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel6.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip6 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip6.setMessage("일반석/비즈니스석/일등석");
				informationTip6.setAutoHide(true);
				informationLabel6.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip6.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip6.setVisible(false);
					}});
				
				
				//예약확인 - FLIGHT NAME표시
				Label insertReservationConfirmationLabel13 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel13.setText("항공 이름 ");
				insertReservationConfirmationLabel13.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel13);
				
				insertReservationConfirmationLabel14 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel14.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel14);
				
				// FLIGHT NAME 인포메이션 사진
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				/*informationLabel7.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip7 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip7.setMessage("선택하신 항공 이름입니다");
				informationTip7.setAutoHide(true);
				informationLabel7.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip7.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip7.setVisible(false);
					}});*/
				
				
				//예약확인 - SEAT NUMBER 표시
				Label insertReservationConfirmationLabel15 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel15.setText("좌석 갯수");
				insertReservationConfirmationLabel15.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel15);
				
				insertReservationConfirmationLabel16 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel16.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel16);
				
				// SEAT NUMBER 인포메이션 사진
				Label informationLabel8 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel8.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip8 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip8.setMessage("신청하신 좌석 갯수");
				informationTip8.setAutoHide(true);
				informationLabel8.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip8.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip8.setVisible(false);
					}});
				
				
				//프린트 되돌아가기
				final Composite reservationConfirmationGroupComposite3 = new Composite(reservationConfirmationTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(120,10).spacing(0, 10).columns(2).applyTo(reservationConfirmationGroupComposite3);
				PetraGridData.defaults().grab(false, false).applyTo(reservationConfirmationGroupComposite3);	
				
				ToolBar insertReservationConfirmationToolBar = new ToolBar (reservationConfirmationGroupComposite3, SWT.NONE);
				PetraGridData.defaults().grab(true, true).applyTo(insertReservationConfirmationToolBar);
				final ToolItem reservationConfirmationRebackToolItem = new ToolItem(insertReservationConfirmationToolBar, SWT.NONE);
				final ToolItem reservationConfirmationPrintToolItem = new ToolItem(insertReservationConfirmationToolBar, SWT.NONE);
				final ToolItem reservationConfirmationOKToolItem = new ToolItem(insertReservationConfirmationToolBar, SWT.NONE);	
				
				reservationConfirmationRebackToolItem.setImage(InternUIResources.getImage("reSelected.png"));
				reservationConfirmationPrintToolItem.setImage(InternUIResources.getImage("reprint.png"));
				reservationConfirmationOKToolItem.setImage(InternUIResources.getImage("ok.png"));
				
				//재선택
				reservationConfirmationRebackToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						allCheckSashForm.setWeights(new int[]{1,0});
					}
				});
				
				//프린트하기
				reservationConfirmationPrintToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("프린트입니다");
					}
				});
				
				//예약취소
				reservationConfirmationOKToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("예약취소입니다");
					}
				});
				
				initialize();
			}
	
		///항공편 조회를 누를 경우 빈 공백이 있으면 에러
		public boolean dataValidation(){
				//빈 공백만 못 넣게 처리하는 것
				if(departureCombo.getText().trim().equals("")){
					//출발지 선택안한경우
					MessageDialog.openInformation(null, "알림", "출발지를 입력 해주세요.");
					departureCombo.setFocus();
					//그부분을 다시 입력할 수 있게 포커스를 맞춰주는 것
					return false;
				}
				if(destinationCombo.getText().trim().equals("")){
					MessageDialog.openInformation(null, "알림", "도착지를 입력 해주세요.");
					destinationCombo.setFocus();
					return false;
				}
		
				if(departureDate.toString().equals(smpleDateFormat.format(date))){
					MessageDialog.openInformation(null, "알림", "출발날짜를 입력 해주세요.");
					departureDate.setFocus();
					return false;
				}else {
					if(dataValidation==0){
						//편도일 경우
					}else if(dataValidation==1){
						//왕복일 경우	
						if(arriveDate.toString().equals(smpleDateFormat.format(date))){
							MessageDialog.openInformation(null, "알림", "도착날짜를 입력 해주세요.");
							arriveDate.setFocus();
							return false;
						}
					}	
				}
			return true;
		}//end of dataValidation() 메소드
	
			private void initialize() {
				
				placeStackLayout.topControl = airlineTicketBook;
				placeStackComposite.layout(); 
				
				navigationView.getTable1().select(0); //네비게이션 뷰에서 0번째 클릭되게 초기화
				
				//비행기예매시스템
				placeSashForm.setWeights(new int[]{1,0});
				//예약목록&체크인확인
				checkInSashForm.setWeights(new int[]{1,0});
				//비행기예매시스템 -> 테이블/신청
				bookingListSashForm.setWeights(new int[]{1,0});
				//sashForm 설정
				checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
				
				oneWay.setSelection(true);
				generalSeat.setSelection(true);
				/////2
				stackLayout.topControl = stackComposite1;
				//맨위에 무엇을 올려놓을지, 기본설정
				stackComposite.layout(); //업데이트 하라
	
				searchInitialize();  //DB초기화
				
				//삭제요망
				//IBatisAdapter.queryForObject("consumerBooking_select");
				
				//2. 예약목록/체크인 테이블에 나타나기
				getConsumerBooking();
				//3. 전체목록 테이블에 나타나기
				getConsumerChecking();
				
				
			}
			
			
			//////////////////////////////////SearchView
			private void searchInitialize() {
				//초기화
				////////항공 등록
				flightDto1.FLIGHT_ID =  (long) IBatisAdapter.queryForObject("flight_getSeq");
				flightDto1.FLIGHT_NAME = "대한항공";
				IBatisAdapter.execute("flight_insertFlight",flightDto1);
				
				flightDto2.FLIGHT_ID =  (long) IBatisAdapter.queryForObject("flight_getSeq");
				flightDto2.FLIGHT_NAME = "아시아나항공";
				IBatisAdapter.execute("flight_insertFlight",flightDto2);
				
				//비행기 등록
				planeDto1.PLANE_ID = (long) IBatisAdapter.queryForObject("plane_getSeq");
				planeDto1.FLIGHT_ID = flightDto1.FLIGHT_ID;
				planeDto1.SEAT_TYPE = 0;
				//0이면 일반석 1이면 비즈니스 2이면 일등석
				//planeDto1.ALL_SEAT = 25; //대한항공 이 비행기 좌석은 10개
				planeDto1.X_SEAT = 24;
				planeDto1.Y_SEAT = 3;
				IBatisAdapter.execute("plane_insertPlane",planeDto1);
				
				planeDto2.PLANE_ID = (long) IBatisAdapter.queryForObject("plane_getSeq");
				planeDto2.FLIGHT_ID = flightDto2.FLIGHT_ID;
				planeDto2.SEAT_TYPE = 1;
				//0이면 일반석 1이면 비즈니스 2이면 일등석
				//planeDto2.ALL_SEAT = 15; //대한항공 이 비행기 좌석은 10개
				planeDto2.X_SEAT = 24;
				planeDto2.Y_SEAT = 3;
				IBatisAdapter.execute("plane_insertPlane",planeDto2);
				
				
				//출항 등록
				departureDto1.DEPARTURE_ID = (long) IBatisAdapter.queryForObject("departure_getSeq");
				departureDto1.PLANE_ID = planeDto1.PLANE_ID;
				departureDto1.DEPARTURE_TIME = "11:00AM";
				departureDto1.ARRIVE_TIME = "12:10PM";
				departureDto1.DEPARTURE_DATE = "2016-1-28";
				departureDto1.ARRIVE_DATE = "2016-1-31";
				departureDto1.DEPARTURE_PLACE = 0; //0은 인천
				departureDto1.ARRIVE_PLACE = 0; //0 도쿄
				IBatisAdapter.execute("departure_insertDeparture",departureDto1);
				
				departureDto2.DEPARTURE_ID = (long) IBatisAdapter.queryForObject("departure_getSeq");
				departureDto2.PLANE_ID = planeDto2.PLANE_ID;
				departureDto2.DEPARTURE_TIME = "11:00AM";
				departureDto2.ARRIVE_TIME = "12:10PM";
				departureDto2.DEPARTURE_DATE = "2016-2-1";
				departureDto2.ARRIVE_DATE = "2016-2-3";
				departureDto2.DEPARTURE_PLACE = 3; //3 제주
				departureDto2.ARRIVE_PLACE = 2; //2 뉴욕
				IBatisAdapter.execute("departure_insertDeparture",departureDto2);
				
			}
			
			public void getSearchDto(ConsumerDto data){
				//IBatisAdapter.execute("consumer_insertConsumer",data);
				
				//출항 검색 
				searchList = IBatisAdapter.queryForList("departure_selectConditionDepartures",data);
				searchTableViewer.setInput(searchList);
				searchTableViewer.refresh();
			}
			
			//사용자 정보 넣기
			public void setConsumerDtoDATA(ConsumerDto data){
				//insert  
				IBatisAdapter.execute("consumer_insertConsumer",data);
				this.data = data;
			}
			
			// 예약목록/체크인에 뿌리기
			public void getConsumerBooking(){
				//bookingList = IBatisAdapter.queryForList("consumerBooking_selectConsumerBooking",(long) 2859693);
				bookingList = IBatisAdapter.queryForList("consumerBooking_selectConsumerCheckingBooking",(long) 2859693);
				bookingTableViewer.setInput(bookingList);
				bookingTableViewer.refresh();
			}
			
			// 3.전체확인에 뿌리기
			public void getConsumerChecking(){
				allBookingList = IBatisAdapter.queryForList("consumerBooking_selectConsumerAllCheckingBooking",(long) 2859693);
				allBookingTableViewer.setInput(allBookingList);
				allBookingTableViewer.refresh();
			}
						
			
			public void getRequest(ConsumerDto data,int index){
				
				//원래 여기서 ConsumerDto selected 해서 consumer_number을 가지고와야함
				//consumer_selectConsumer
				//data=(ConsumerDto) IBatisAdapter.queryForObject("consumer_selectConsumer",data);
				
				consumerBookingDto.BOOKING_NUMBER = (long) IBatisAdapter.queryForObject("intern_getSeq");
				SearchDto searchRequestData = searchList.get(index); //여기
				consumerBookingDto.setBOOKING_NUMBER((long) IBatisAdapter.queryForObject("departure_getSeq"));
				consumerBookingDto.setFLIGHT_ID(searchRequestData.FLIGHT_ID);
				consumerBookingDto.setPLANE_ID(searchRequestData.PLANE_ID);
				consumerBookingDto.setDEPARTURE_ID(searchRequestData.DEPARTURE_ID);
				consumerBookingDto.setCONSUMER_ID((long) 2859693);
				consumerBookingDto.setFLIGHT_NAME(searchRequestData.FLIGHT_NAME);
				consumerBookingDto.setDEPARTURE_PLACE(searchRequestData.DEPARTURE_PLACE);
				consumerBookingDto.setARRIVE_PLACE(searchRequestData.ARRIVE_PLACE);
				consumerBookingDto.setDEPARTURE_DATE(searchRequestData.DEPARTURE_DATE);
				consumerBookingDto.setSEAT_TYPE(searchRequestData.SEAT_TYPE);
				consumerBookingDto.setSEATNUMBER(data.SEATNUMBER);
				consumerBookingDto.setCHECKIN(data.CHECKIN);
				
				IBatisAdapter.execute("consumerBooking_insertBooking",consumerBookingDto);
				getConsumerBooking();
			}
			
			public void CLabelSelected(){
				
				CLabelReset(listLabel);
				
				selectedCoordinateList = (List<CoordinateDto>) IBatisAdapter.queryForList("coordinate_selectCoordinateOne",consumerBookingData.PLANE_ID);
				//클릭한 비행기 자리를 모두 불러온다
				for(int a= 0; a< selectedCoordinateList.size() ; a++){
					CoordinateDto coordinateDto = selectedCoordinateList.get(a);
					System.out.println("좌석 다시 선택");
					System.out.println(coordinateDto.PLANE_ID);
					System.out.println(coordinateDto.COUNT);
					count[coordinateDto.COUNT] = 1;
					//클릭해둔 비행기 자리를 모두 1로 설정
					final CLabel seatSelectionCLabel = listLabel.get(coordinateDto.COUNT);
						
					if(seatSelectionCLabel.getData().equals(coordinateDto.COUNT)){
																		//220,020,060
						seatSelectionCLabel.setBackground(new Color(null,211,211,211));}
																	//가져온 데이터를 모두 넣는다
				
					allCount=1;		//모든 신청 초기화
					cancle = 0;		//선택 개수 초기화
					again = 1;
					selectLabel.clear();
					//CLabelCount(seatSelectionCLabel);
				}
			}
			
			public void CLabelReset(List<CLabel> listLabel){
				for(int i=0; i<listLabel.size();i++){
					final CLabel seatSelectionCLabel = listLabel.get(i);
					seatSelectionCLabel.setBackground(new Color(null,245,245,220));
					for(int j=0; j<count.length;j++){
						count[j] = 0; 	//신청 초기화
					}
					allCount=1;		//모든 신청 초기화
					cancle = 0;		//선택 개수 초기화
					again = 1;
				}
			}//end of CLabelReset()
			
			
			//좌석을 설정했을 때, 좌석이 클릭되어 색 변경
			public void CLabelCount(CLabel seatSelectionCLabel){
				
				if(count[Integer.parseInt(seatSelectionCLabel.getText())]==0 && cancle==1){
					//더 신청할려고 하는데 MAX를 초과했을 경우
					MessageDialog.openConfirm(null, "신청갯수", "신청갯수는 "+consumerBookingData.SEATNUMBER+"개입니다.");
					return ;
				}
				if(count[Integer.parseInt(seatSelectionCLabel.getText())]==1){
					count[Integer.parseInt(seatSelectionCLabel.getText())]  = 0;
					seatSelectionCLabel.setBackground(new Color(null,245,245,220));
																//베이지
					allCount--;
					
					selectLabel.remove(seatSelectionCLabel);
					//신청 클릭을 취소한 경우
					if(allCount<=consumerBookingData.SEATNUMBER){
						//MAX인원보다 작을경우 추가 가능
						cancle = 0;
					}
				}
				else if(count[Integer.parseInt(seatSelectionCLabel.getText())]==0 && cancle ==0){
					//신청 클릭을 하는 경우
					if(allCount>consumerBookingData.SEATNUMBER){
						MessageDialog.openConfirm(null, "신청갯수", "신청갯수는 "+consumerBookingData.SEATNUMBER+"개입니다.");
						cancle=1;
						return ;
					}
					if(again==1){
						//좌석 선택 다시하기를 누른경우
						again =0;
					}else{
						count[Integer.parseInt(seatSelectionCLabel.getText())]  = 1;
						seatSelectionCLabel.setBackground(new Color(null,220,020,060));
																//null,211,211,211
						selectLabel.add(seatSelectionCLabel);
						//선택된 곳
						allCount++;
					}
				}//end of 신청 클릭을 하는 경우
			}
			
			public void setFont(){
				font = new Font(null, "font", iPreferenceStore.getInt("text_font_size"), SWT.None);
				//messageText.setFont(font);
				 
				bookingTableLabelProvider.setFont(font);
				
			}
			
			public void setColor(int i){
				Color Fontcolor = setFontColor(i);
				logger.info("Fontcolor는 :"+Fontcolor);
				
				bookingTableLabelProvider.setForeground(Fontcolor);
				//messageText.setForeground(Fontcolor);
			}
			
			public Color setFontColor(int i){
				logger.info("i는? "+i);
				switch(i){
					case 0:
						//black
						return color = new Color(null, 0, 0, 0);
					case 1:
						//blue
						return color = new Color(null,0, 0, 255);
					case 2:
						//GREEN
						return color = new Color(null,0, 255, 0);
					case 3:
						return color = new Color(null,255, 0, 0);
					default :
						return color = new Color(null,0, 0, 0);
				}
				
			}
			
			@Override
			public void setFocus() {
				// TODO Auto-generated method stub
			}
			//placeStackLayout 꺼내오는 메소드
			public StackLayout getPlaceStackLayout() {
				return placeStackLayout;
			}
			public void setPlaceStackLayout(StackLayout placeStackLayout) {
				this.placeStackLayout = placeStackLayout;
			}
			//getPlaceStackComposite
			public Composite getPlaceStackComposite() {
				return placeStackComposite;
			}
			public void setPlaceStackComposite(Composite placeStackComposite) {
				this.placeStackComposite = placeStackComposite;
			}
			
			
			
			//getPlaceStackComposite1
			public Composite getPlaceStackComposite1() {
				return airlineTicketBook;
			}
			public void setPlaceStackComposite1(Composite placeStackComposite1) {
				this.airlineTicketBook = placeStackComposite1;
			}
			
			//getPlaceStackComposite2
			public Composite getPlaceStackComposite2() {
				return bookingListCheckIn;
			}
			public void setPlaceStackComposite2(Composite placeStackComposite2) {
				this.bookingListCheckIn = placeStackComposite2;
			}
			
			//placeSashForm
			public SashForm getPlaceSashForm() {
				return placeSashForm;
			}
			public void setPlaceSashForm(SashForm placeSashForm) {
				this.placeSashForm = placeSashForm;
			}
			
			//checkInSashForm
			public SashForm getCheckInSashForm() {
				return checkInSashForm;
			}
			public void setCheckInSashForm(SashForm checkInSashForm) {
				this.checkInSashForm = checkInSashForm;
			}
			
			//bookingListSashForm
			public SashForm getBookingListSashForm() {
				return bookingListSashForm;
			}
			public void setBookingListSashForm(SashForm bookingListSashForm) {
				this.bookingListSashForm = bookingListSashForm;
			}

			//getCheckInSeatConfirmationSashForm
			public SashForm getCheckInSeatConfirmationSashForm() {
				return checkInSeatConfirmationSashForm;
			}

			public void setCheckInSeatConfirmationSashForm(
					SashForm checkInSeatConfirmationSashForm) {
				this.checkInSeatConfirmationSashForm = checkInSeatConfirmationSashForm;
			}
			
			
		
			
			//getAllCheckList
			public Composite getAllCheckList() {
				return allCheckList;
			}

			public void setAllCheckList(Composite allCheckList) {
				this.allCheckList = allCheckList;
			}

			//getAllCheckSashForm
			public SashForm getAllCheckSashForm() {
				return allCheckSashForm;
			}

			public void setAllCheckSashForm(SashForm allCheckSashForm) {
				this.allCheckSashForm = allCheckSashForm;
			}

			//예약 체크인 할때 테이블에서 클릭된 데이터 저장하는 곳
			public ConsumerBookingDto getConsumerBookingData() {
				return consumerBookingData;
			}

			public void setConsumerBookingData(ConsumerBookingDto consumerBookingData) {
				
				this.consumerBookingData = consumerBookingData;
			}

			
			//getBookingTableViewer
			public TableViewer getBookingTableViewer() {
				return bookingTableViewer;
			}

			//getAllBookingTableViewer
			public TableViewer getAllBookingTableViewer() {
				return allBookingTableViewer;
			}

		

			
			
			

}
