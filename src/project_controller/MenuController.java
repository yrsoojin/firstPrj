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
	
	//�޴��߰�
	@FXML private TextField menuField;	
	@FXML private TextField priceField;
	@FXML private TextField photoField;
	//�޴�����
	@FXML private TextField newMenuNameField;	
	@FXML private TextField newMenuPriceField;
	@FXML private Button addMenuBtn;
	@FXML private Button updateMenuBtn;

	MenuDao mDao = new MenuDao();
	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}	

	public void menuInsert(ActionEvent event) {   //�޴�����
		if(menuField.getText().equals("") || priceField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		else {
			String menuname = menuField.getText();
			if (mDao.menuVerify(menuField.getText())==1) {  //MenuDao���� �ߺ�Ȯ�� menuVerify
				alert.setTitle("�޴��̸� �ߺ�");
			    alert.setHeaderText("��� ���� �޴��̸��Դϴ�.\n�޴��̸��� �ٽ� Ȯ���� �ּ���.");
			    alert.show(); 
			}
			else {
				String price = priceField.getText();
				Menu menu = new Menu(menuname, price);
				mDao.menuInsert(menu);
				alert.setTitle("�޴� �߰�");
			    alert.setHeaderText("�޴� �߰� �Ϸ�");         
			    alert.show();
			    Stage root = (Stage)addMenuBtn.getScene().getWindow();
				root.close();
			}
		}
	}
	
	public void updateMenu(ActionEvent event) {   //�޴�����
		if(newMenuNameField.getText().equals("") || newMenuPriceField.getText().equals("")) {
			alert.setTitle("�� �׸�");
		    alert.setHeaderText("��� �׸��� �Է��� �ּ���.");
		    alert.show();
		}
		else if(mDao.menuUpdate(selectedMenu, newMenuNameField.getText(), newMenuPriceField.getText()) == 1) {
			File file = new File("../Project/src/images/"+selectedMenu+".jpg");
			File fileNew = new File("../Project/src/images/"+newMenuNameField.getText()+".jpg");
			file.renameTo(fileNew);
			alert.setTitle("�޴� ����");
		    alert.setHeaderText("�޴� ���� �Ϸ�");         
		    alert.show();
		    Stage root = (Stage)updateMenuBtn.getScene().getWindow();
			root.close();
		}
	}
	
	public void fileChoose() throws Exception{   //�̹����ҷ�����
        FileChooser fc = new FileChooser();
        fc.setTitle("�̹��� ����");
        fc.setInitialDirectory(new File("C:/")); //����ã�� �Ҷ� �ߴ� path
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
			int result = saveImg.saveImgFromUrl(file, path);  //����Ǹ� 1, �ƴϸ� 0 ��ȯ
			if (result == 1) {
				System.out.println("����Ȱ�� : " + saveImg.getPath());
				System.out.println("����������̸� : " + saveImg.getSavedFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        }
    }
}