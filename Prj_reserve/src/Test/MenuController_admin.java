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
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		else {
			String menuname = menuField.getText();
			if (mDao.menuVerify(menuField.getText())==1) {
				alert.setTitle("�޴��̸� �ߺ�");
			    alert.setHeaderText("��� ���� �޴��̸��Դϴ�.\n�̴��̸��� �ٽ� Ȯ���� �ּ���.");
			    alert.show(); 
			}
			else {
				String price = priceField.getText();
				Menu menu = new Menu(menuname, price);
				mDao.menuInsert(menu);
				alert.setTitle("�޴� �߰�");
			    alert.setHeaderText("�޴� �߰� �Ϸ�");         
			    alert.show();
			    menuField.setText("");
			    priceField.setText("");
			    photoField.setText("");
			}
		}
	}
	
	public void updateMenu(ActionEvent event) {
		if(menuNameField.getText().equals("") || newMenuNameField.getText().equals("") || newMenuPriceField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		else if(mDao.menuUpdate(menuNameField.getText(), newMenuNameField.getText(), newMenuPriceField.getText()) == 1) {
			alert.setTitle("�޴� ����");
		    alert.setHeaderText("�޴� ���� �Ϸ�");         
		    alert.show();
		    menuNameField.setText("");
		    newMenuNameField.setText("");
		    newMenuPriceField.setText("");
		}
		else {
			alert.setTitle("�޴� ����");
		    alert.setHeaderText("�޴� ���� ����\n�ش� �޴��� �����ϴ�.");         
		    alert.show();
		}
	}

	public void deleteMenu(ActionEvent event) {
		if(menudeleteField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("�޴� �̸��� �Է��� �ּ���.");
		    alert.show();
		}
		else if(mDao.menuDelete(menudeleteField.getText())==1) {
			alert.setTitle("�޴� ����");
		    alert.setHeaderText("�޴� ���� �Ϸ�");         
		    alert.show();
		    menudeleteField.setText("");
		}
		else {
			alert.setTitle("�޴� ����");
		    alert.setHeaderText("�޴� ���� ����\n�ش� �޴��� �����ϴ�.");         
		    alert.show();
		}
	}
	
	public void fileChoose() {
        FileChooser fc = new FileChooser();
        fc.setTitle("�̹��� ����");
        fc.setInitialDirectory(new File("C:/"));
        ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png");
        fc.getExtensionFilters().add(imgType);
        File selectedFile =  fc.showOpenDialog(null);
        photoField.setText(selectedFile.getAbsolutePath());
    }
}