package intern.action;

import intern.Activator;
import intern.define.CommandIdDefine;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class ThisweekTravelAction  extends Action{
	 private final IWorkbenchWindow window;

	    public ThisweekTravelAction(String text, IWorkbenchWindow window) {
	        super(text);
	        this.window = window;
	        // The id is used to refer to the action in a menu or toolbar
	        setId(CommandIdDefine.CMD_WEEK_TRAVEL);
	        // Associate the action with a pre-defined command, to allow key bindings.
	        setActionDefinitionId(CommandIdDefine.CMD_WEEK_TRAVEL);
	        setImageDescriptor(Activator.getImageDescriptor("/icons/travel.png"));
	    }

	    public void run() {
	        MessageDialog.openInformation(window.getShell(), "금주의 여행지", "금주의 여행지는 ○○입니다");
	        //어디 위치에서 뜰건지 정함 
	        // EX) 큰 바탕=> 위에 쿨창 => 그 위에 메세지창->그 위에 팝업 
	        //openInformation는 느낌표(shell, 제목, 내용)
	        //권한 같은데나 경고 및 확인 용으로 사용하기 좋음
	    }
	}