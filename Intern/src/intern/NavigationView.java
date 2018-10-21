package intern;

import intern.define.ContainerTreeItemIdDefine;
import intern.provider.NavigationTableProvider;
import intern.provider.SearchTableLabelProvider;
import intern.provider.TreeLabelProvider;
import intern.util.FrequentlyUsedMethodsUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.sinsiway.petra.commons.log.PetraLogger;
import com.sinsiway.petra.commons.sql.connection.ConnectionChangeEvent;
import com.sinsiway.petra.commons.sql.connection.ConnectionChangeEvent.TYPE;
import com.sinsiway.petra.commons.sql.connection.ConnectionManager;
import com.sinsiway.petra.commons.sql.connection.PetraConnection;
import com.sinsiway.petra.commons.sql.define.DBDefine;
import com.sinsiway.petra.commons.sql.define.DBInfo;
import com.sinsiway.petra.commons.sql.define.SiteInformation;
import com.sinsiway.petra.commons.sql.dto.InternDto;
import com.sinsiway.petra.commons.sql.dto.SearchDto;
import com.sinsiway.petra.commons.sql.util.IBatisAdapter;
import com.sinsiway.petra.commons.ui.control.TableFilterComposite;
import com.sinsiway.petra.commons.ui.treeView.ContainerTreeItem;
import com.sinsiway.petra.commons.ui.treeView.TreeContentProvider;
import com.sinsiway.petra.commons.ui.util.PetraGridData;
import com.sinsiway.petra.commons.ui.util.PetraGridLayout;
import com.sinsiway.petra.commons.ui.util.TableUtil;

public class NavigationView extends ViewPart {
	public static final String ID = "Intern.navigationView";
	
	private Logger logger = PetraLogger.getLogger(NavigationView.class);
	//실질적인 데이터값을 콘솔에 한번 찍을 경우   메소드 initialize() 에서 확인
	private LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private Table table1;
	private ContainerTreeItem rootContainer;
	private SiteInformation siteInfo;
	private IPreferenceStore store = PlatformUI.getPreferenceStore();

	public void createPartControl(Composite parent) {
		//view.setInternList(internList);
		final String[] menuList = {"비행기예매시스템","예약목록/체크인","예약정보/취소"};
		
		PetraGridLayout.defaults().applyTo(parent);
		PetraGridData.defaults().applyTo(parent);
		
		
		Composite tableComposite = new Composite(parent, SWT.NONE);
		PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).columns(1).applyTo(tableComposite);
		PetraGridData.defaults().applyTo(tableComposite);
		String[] columnHeader = {"사용자예약목록"};
		int[] columnWidth = {200};
		tableViewer = new TableViewer(tableComposite, SWT.None/*SWT.FULL_SELECTION SWT.MULTI*/);
		table1 = tableViewer.getTable();
		
		PetraGridLayout.defaults().applyTo(table1);
		PetraGridData.defaults().span(1, 1).applyTo(table1);
		
		TableUtil.makeTableColumns(table1, columnHeader, columnWidth);
		table1.setHeaderVisible(false);
		table1.setLinesVisible(false);
		tableViewer.setContentProvider(new ArrayContentProvider());
		//tableViewer.setLabelProvider(new NavigationTableProvider());
		
		table1.addListener (SWT.Selection, new Listener () {
			@Override
			public void handleEvent (Event event) {
				String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
				System.out.println (event.item + " " + string);
				
				System.out.println("getData : "+event.item.getData());
				BookAFlightView view = (BookAFlightView) FrequentlyUsedMethodsUtil.getView(BookAFlightView.ID);
				
				if(event.item.getData().equals(menuList[0])){
					//BookAFlightView를 불러옴
					//비행기 예매 시스템
					view.getPlaceStackLayout().topControl = view.getPlaceStackComposite1();
					view.getPlaceStackComposite().layout();
					//예약 & 항공권 비율 1:0
					view.getPlaceSashForm().setWeights(new int[]{1,0});
					//비행기예매시스템 -> 테이블/신청
					view.getBookingListSashForm().setWeights(new int[]{1,0});
					
					view.getCheckInSashForm().setWeights(new int[]{1,0});
					view.getCheckInSeatConfirmationSashForm().setWeights(new int[]{1,0});
					
					
				}else if(event.item.getData().equals(menuList[1])){
					//예약목록/체크인
					view.getPlaceStackLayout().topControl = view.getPlaceStackComposite2();
					view.getPlaceStackComposite().layout();
					view.getCheckInSashForm().setWeights(new int[]{1,0});
					
					view.getConsumerBooking();
					view.getBookingTableViewer().refresh();
				}else if(event.item.getData().equals(menuList[2])){
					//전체목록
					view.getPlaceStackLayout().topControl = view.getAllCheckList();
					view.getPlaceStackComposite().layout();
					view.getAllCheckSashForm().setWeights(new int[]{1,0});
					
					view.getConsumerChecking();
					view.getAllBookingTableViewer().refresh();
				}
				else{
				}
				
			
			}
		});
//		table1.addSelectionListener(new SelectionListener(){
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
	
		tableViewer.setInput(menuList);
		tableViewer.refresh();
	
	    
		//초기화
		initialize();
	}

	private void initialize() {
		connectionSoha(); 
		//커넥션을 맺는다.
		
		String connectionTest = (String) IBatisAdapter.queryForObject("intern_selectConnectionTest");
						//long으로 할 경우 petra4Access.xml 에서  데이터 타입을 String으로 해줬기 때문에 안됨   / 테이블명
		//IBatisAdapter = IBatis.jar 파일을 분석만 뽑아서 쓸거만 모아서 만든거
		if(connectionTest.equals("OK")){
			logger.info("접속 성공");
			//INFO - NavigationView : initialize 접속 성공  이라고 console 창에 뜸 
			//insert, update, delete 는 모두 execute() 로 사용 
		}else{
			logger.info("접속 실패");
			return;
		}
	}

	public void setInternList(List<InternDto> internList){
		//public 선언 해서 view의 getInternList()에서 메소드 실행이 됨
		//view에서 테이블이 추가하게 되면 옆에 인턴목록- ~ 그부분에 동시에 추가 되도록 하는 메소드
		
		rootContainer.clearChild();
		//rootContainer를 초기화
		//Message를 추가하게 되면 인턴목록 밑에 있는 것도 같이 추가가 된다. test*2 
		//동기화를 거는 목적
		
		
		
		ContainerTreeItem childContainer = null;
		ContainerTreeItem littleContainer = null;
		for(InternDto data : internList){
			childContainer = new ContainerTreeItem(rootContainer, data.INTERN_NAME+"("+data.INTERN_ID+")", ContainerTreeItemIdDefine.CONTAINER_CHILD);
											// 부모 설정을 잘 해줘야 한다 .	 +"("+data.INTERN_ID+")"라고 하면 NAME이 겹쳐도 가능				타입을 정해준다. => 찾기도 쉽고, 아이콘 지정도 쉬워짐
			childContainer.setData(data);
			rootContainer.addChild(childContainer);
			//////////////////////////////////
			//for(List data1 : data){
			//for문을 안쓰면 하나의 childContainer에 한개의 littleContainer만 생성
			//이부분을 알고리즘 적으로 잘 생각해봐야합니다.
			
			/* 내 생각 : View에서 처럼
			 * DB select 받아온 data의 id를 이용해서 하위단의 데이터들을 list 배열로 묶음
			 *  for(데이터타입 데이터이름 : list이름) 으로 해서 반복
			 */
				littleContainer = new ContainerTreeItem(childContainer, data.INTERN_NAME+"("+data.INTERN_ID+")", ContainerTreeItemIdDefine.CONTAINER_LITTLECHILD);
				littleContainer.setData(data);
				childContainer.addChild(littleContainer);
				
			//}
		}
		
//		treeViewer.refresh();
		//이걸 안해주면 화면이 그대로 입니다.
		//treeViewer.expandAll();	// 몇 레벨까지 열까? 인턴 목록 밑에 몇 단계까지 열까?
//		treeViewer.expandToLevel(rootContainer, store.getInt("tree_level"));
		//expandToLevel하면 그 레벨까지면 펼치겠다.
		//treeViewer.collapseToLevel(rootContainer, level);
		// 트리뷰어 닫기 
	}
	
	public void expandTreeViewer(){
		treeViewer.refresh();
		if(store.getInt("tree_level")!=0){
			treeViewer.collapseAll();
			treeViewer.expandToLevel(rootContainer, store.getInt("tree_level"));
		}else if(store.getInt("tree_level")==0){
			treeViewer.collapseAll();
		}
		
	}
	
	

	private void connectionSoha() {
		// SOHA DB 접속 - 시작
		/*
		siteInfo = new SiteInformation();
		siteInfo.title 			= "";
		siteInfo.ip 			= "192.168.1.177";
		siteInfo.port 			= "10002";
		siteInfo.serviceName 	= "cuwon10002";
		siteInfo.user 			= "dgadmin";
		siteInfo.password 		= "";
		siteInfo.charEncoding	= "utf8";*/
		
		siteInfo = new SiteInformation();
		siteInfo.title 			= "";
		siteInfo.ip 			= "sinsiway-jeju.iptime.org";
		siteInfo.port 			= "10002";
		siteInfo.serviceName 	= "cuwon10002";
		siteInfo.user 			= "dgadmin";
		siteInfo.password 		= "";
		siteInfo.charEncoding	= "utf8";

		String url = siteInfo.toJDBCString();
		DBInfo dbInfo = new DBInfo(DBDefine.PETRA_PRODUCT_MANAGER, url,siteInfo.user, siteInfo.password);
		PetraConnection petraConnection = new PetraConnection(dbInfo,siteInfo.title, siteInfo);
		
		ConnectionChangeEvent ce = new ConnectionChangeEvent(petraConnection, getClass(), TYPE.CONNECT);
		ConnectionManager.getInstance().fireConnectionChange(ce);

//		Soha 테이블 생성,드랍 인덱스 생성 sql 예제
//		drop table intern;
//		create table intern(
//		    intern_id sb8,    
//		    intern_name schr(33),
//		    sex sb1,
//		    phone schr(33),
//		    speaking_level sb1,
//		    portfolio_flag sb1
//		)
//		perm '0001111111100'
//		extent(0,1,0);
//		create unique index intern_idx1 on intern(intern_id);
		
		//sb8은 Long으로 
	}
	
	

	public Table getTable1() {
		return table1;
	}

	public void setTable1(Table table1) {
		this.table1 = table1;
	}

	public void setFocus() {
		//tree.setFocus();
	}
}