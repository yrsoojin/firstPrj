package project_controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import net.halowd.saveImg.SaveImg;
import project_constructor.Menu;
import project_dao.MenuDao;

public class MenuController extends AdminController implements Initializable {
	
	//메뉴추가
	@FXML private TextField menuField;	
	@FXML private TextField priceField;
	@FXML private TextField photoField;
	//메뉴수정
	@FXML private TextField newMenuNameField;	
	@FXML private TextField newMenuPriceField;
	@FXML private Button addMenuBtn;
	@FXML private Button updateMenuBtn;

	MenuDao mDao = new MenuDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	

	public void menuInsert(ActionEvent event) {   //메뉴생성
		if(menuField.getText().equals("") || priceField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		else {
			String menuname = menuField.getText();
			if (mDao.menuVerify(menuField.getText())==1) {  //MenuDao에서 중복확인 menuVerify
				alert.setTitle("메뉴이름 중복");
			    alert.setHeaderText("사용 중인 메뉴이름입니다.\n메뉴이름을 다시 확인해 주세요.");
			    alert.show(); 
			}
			else {
				String price = priceField.getText();
				Menu menu = new Menu(menuname, price);
				mDao.menuInsert(menu);
				alert.setTitle("메뉴 추가");
			    alert.setHeaderText("메뉴 추가 완료");         
			    alert.show();
			    Stage root = (Stage)addMenuBtn.getScene().getWindow();
				root.close();
			}
		}
	}
	
	public void updateMenu(ActionEvent event) {   //메뉴수정
		if(newMenuNameField.getText().equals("") || newMenuPriceField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		else if(mDao.menuUpdate(selectedMenu, newMenuNameField.getText(), newMenuPriceField.getText()) == 1) {
			File file = new File("../Project/src/images/"+selectedMenu+".jpg");
			File fileNew = new File("../Project/src/images/"+newMenuNameField.getText()+".jpg");
			file.renameTo(fileNew);
			alert.setTitle("메뉴 수정");
		    alert.setHeaderText("메뉴 수정 완료");         
		    alert.show();
		    Stage root = (Stage)updateMenuBtn.getScene().getWindow();
			root.close();
		}
	}
	
	public void fileChoose() throws Exception{   //이미지불러오기
        FileChooser fc = new FileChooser();
        fc.setTitle("이미지 선택");
        fc.setInitialDirectory(new File("C:/")); //파일찾기 할때 뜨는 path
        ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png");
        fc.getExtensionFilters().add(imgType);
        File selectedFile =  fc.showOpenDialog(null);
       
        if(selectedFile!=null) {
        photoField.setText(selectedFile.toURI().toString());
         
        SaveImg saveImg = new SaveImg();
		
        String file = selectedFile.toURI().toString();
       // System.out.println(file);
        String path = "../Project/src/images";
      
		try {
			int result = saveImg.saveImgFromUrl(file, path);  //저장되면 1, 아니면 0 반환
			if (result == 1) {
				System.out.println("저장된경로 : " + saveImg.getPath());
				System.out.println("저장된파일이름 : " + saveImg.getSavedFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        }
    }
}