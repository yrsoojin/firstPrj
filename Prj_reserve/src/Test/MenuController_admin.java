package Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

@SuppressWarnings("unused")
public class MenuController_admin implements Initializable {
	
	@FXML private TextField menuField;	
	@FXML private TextField priceField;
	@FXML private TextField photoField;
	@FXML private TextField menudeleteField;
	@FXML private TextField menuNameField;	
	@FXML private TextField newMenuNameField;	
	@FXML private TextField newMenuPriceField;

	MenuDao mDao = new MenuDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	

	public void menuInsert(ActionEvent event) {
		if(menuField.getText().equals("") || priceField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		else {
			String menuname = menuField.getText();
			if (mDao.menuVerify(menuField.getText())==1) {
				alert.setTitle("메뉴이름 중복");
			    alert.setHeaderText("사용 중인 메뉴이름입니다.\n미뉴이름을 다시 확인해 주세요.");
			    alert.show(); 
			}
			else {
				String price = priceField.getText();
				Menu menu = new Menu(menuname, price);
				mDao.menuInsert(menu);
				alert.setTitle("메뉴 추가");
			    alert.setHeaderText("메뉴 추가 완료");         
			    alert.show();
			    menuField.setText("");
			    priceField.setText("");
			    photoField.setText("");
			}
		}
	}
	
	public void updateMenu(ActionEvent event) {
		if(menuNameField.getText().equals("") || newMenuNameField.getText().equals("") || newMenuPriceField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("모든 항목을 입력해 주세요.");
		    alert.show();
		}
		else if(mDao.menuUpdate(menuNameField.getText(), newMenuNameField.getText(), newMenuPriceField.getText()) == 1) {
			alert.setTitle("메뉴 수정");
		    alert.setHeaderText("메뉴 수정 완료");         
		    alert.show();
		    menuNameField.setText("");
		    newMenuNameField.setText("");
		    newMenuPriceField.setText("");
		}
		else {
			alert.setTitle("메뉴 수정");
		    alert.setHeaderText("메뉴 수정 실패\n해당 메뉴가 없습니다.");         
		    alert.show();
		}
	}

	public void deleteMenu(ActionEvent event) {
		if(menudeleteField.getText().equals("")) {
			alert.setTitle("빈 항목");
		    alert.setHeaderText("메뉴 이름을 입력해 주세요.");
		    alert.show();
		}
		else if(mDao.menuDelete(menudeleteField.getText())==1) {
			alert.setTitle("메뉴 삭제");
		    alert.setHeaderText("메뉴 삭제 완료");         
		    alert.show();
		    menudeleteField.setText("");
		}
		else {
			alert.setTitle("메뉴 삭제");
		    alert.setHeaderText("메뉴 삭제 실패\n해당 메뉴가 없습니다.");         
		    alert.show();
		}
	}
	
	public void fileChoose() {
        FileChooser fc = new FileChooser();
        fc.setTitle("이미지 선택");
        fc.setInitialDirectory(new File("C:/"));
        ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png");
        fc.getExtensionFilters().add(imgType);
        File selectedFile =  fc.showOpenDialog(null);
        photoField.setText(selectedFile.getAbsolutePath());
    }
}