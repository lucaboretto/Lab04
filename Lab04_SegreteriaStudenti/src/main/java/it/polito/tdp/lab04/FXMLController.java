/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="combo"
    private ComboBox<String> combo; // Value injected by FXMLLoader

    @FXML // fx:id="txtStudente"
    private TextField txtStudente; // Value injected by FXMLLoader

    @FXML // fx:id="boxCheck"
    private CheckBox boxCheck; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader
    
    @FXML
    private Button btnCerca;

    
    @FXML
    void doReset(ActionEvent event) {
    	//TODO
    	this.txtResult.clear();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	  assert combo != null : "fx:id=\"combo\" was not injected: check your FXML file 'Scene.fxml'.";
          assert txtStudente != null : "fx:id=\"txtStudente\" was not injected: check your FXML file 'Scene.fxml'.";
          assert boxCheck != null : "fx:id=\"boxCheck\" was not injected: check your FXML file 'Scene.fxml'.";
          assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
          assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
          assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
          assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
          assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
          assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;
		ObservableList<String> comboItems = FXCollections.observableArrayList(model.getNomiCorsi());
		comboItems.add(null);
	        combo.setItems(comboItems);
	}
	
	@FXML
	void doAutocompletamento(ActionEvent event) {
		int matricola = Integer.parseInt(this.txtStudente.getText());
		this.txtNome.setText(this.model.getStudenteByMatricola(matricola).getNome());
		this.txtCognome.setText(this.model.getStudenteByMatricola(matricola).getCognome());
	}
	
	@FXML
    void doCercaCorsi(ActionEvent event) {
		this.txtResult.clear();
		if(this.model.getCorsiByMatricola(Integer.parseInt(this.txtStudente.getText()))!=null) {
			for(Corso c: this.model.getCorsiByMatricola(Integer.parseInt(this.txtStudente.getText())))
				this.txtResult.appendText(c.toString());
		}
		else
			this.txtResult.appendText("Matricola non presente");
	}
	
	@FXML
    void doCercaIscrittiCorso(ActionEvent event) {
		this.txtResult.clear();
		Corso corso = new Corso(null, null, this.combo.getValue(), null);
		if(this.model.getStudentiIscrittiAlCorso(corso) != null) {
			if(this.model.getStudentiIscrittiAlCorso(corso).size()==0)
				this.txtResult.appendText("Corso senza iscritti");
			for(Studente s: this.model.getStudentiIscrittiAlCorso(corso))
				this.txtResult.appendText(s.toString()+"\n");
		}
		else
			this.txtResult.appendText("Nessun corso selezionato");
    }
	
	@FXML
    void doCerca(ActionEvent event) {
		if(this.model.isIscrittoAlCorso(Integer.parseInt(this.txtStudente.getText()), this.combo.getValue()))
			this.txtResult.appendText("Studente iscritto al corso");
		else
			this.txtResult.appendText("Studente non iscritto al corso");
    }
	
	@FXML
    void doIscrivi(ActionEvent event) {
		int matricola = Integer.parseInt(this.txtStudente.getText());
		String nomeCorso = this.combo.getValue();
		if(this.model.doIscrivi(matricola, nomeCorso) == 2)
			this.txtResult.appendText("Studente iscritto correttamente al corso\n");
		else if(this.model.doIscrivi(matricola, nomeCorso) == 1)
			this.txtResult.appendText("Studente già iscritto al corso\n");
		else if(this.model.doIscrivi(matricola, nomeCorso) == 3)
			this.txtResult.appendText("Studente inesistente\n");
	}
}