package web.controller;

import UtilsGeneral.ConfiguracionControl;
import control.ControlVentana;
import ejb.services.ConfiguracionBean;
import ejb.services.UrgenciaBean;
import entities.persistence.entities.Configuracion;
import entities.persistence.entities.Urgencia;
import exceptions.ServiceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import web.animations.FadeInUpTransition;


public class urgenciaController implements Initializable {
  
    @FXML
    private Button btnBack;

    @FXML
    private AnchorPane paneCrud;

    @FXML
    private AnchorPane paneTabel;

    @FXML
    private TableView<Urgencia> tableData;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TextField txtNombre;

    @FXML
    private ProgressBar bar;

    @FXML
    private TableColumn<?, ?> colDescripcion;

    @FXML
    private TextArea TxtDescripcion;
    
    @FXML
    private CheckBox ChkActivo;
    
    @FXML
    private Label LblNombre;

    @FXML
    private TableColumn<?, ?> colActivo;
    /*@FXML
    private TableView<?> tableData;
    @FXML
    private TableColumn colAction;
    @FXML
    private Button btnNew;
    @FXML
    private AnchorPane paneTabel;  
    @FXML
    private AnchorPane paneCrud;
    @FXML
    private TextField txtId;
    @FXML
    private ComboBox cbDiscount,cbZip;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtAddress1;
    @FXML
    private TextArea txtAddress2;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtFax;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCredit;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnBack;
    Integer status;
    @FXML
    private ImageView imgLoad;
    @FXML
    private ProgressBar bar;
    private ObservableList<?> listData;*/
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
        UrgenciaBean ub=new UrgenciaBean();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aksiNew(null);
        
        try {
            List<Urgencia> lista=ub.traerTodos();
            ObservableList<Urgencia> listaUrgencia = FXCollections.observableList(lista);
            TableColumn id = new TableColumn("# Id");
            TableColumn Nombre = new TableColumn("# Nombre");
            TableColumn Descripcion = new TableColumn("# Descripcion");
            
            id.setMinWidth(100);
            id.setCellValueFactory(new PropertyValueFactory<>("idurgencia"));
   
            Nombre.setMinWidth(100);
            Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            Descripcion.setMinWidth(100);
            Descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tableData.getColumns().addAll(id,Nombre,Descripcion);
            tableData.setItems(listaUrgencia);
            /* Platform.runLater(() -> {
            ApplicationContext ctx = config.getInstance().getApplicationContext();
            crud = ctx.getBean(interCustomer.class);
            listData = FXCollections.observableArrayList();
            status = 0;
            UtilsVentanas.setModelColumn(colZip, "zip");
            colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>,ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
            return new SimpleBooleanProperty(p.getValue() != null);
            }
            });
            colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
            return new ButtonCell(tableData);
            }
            });
            selectWithService();
            displayDiscountCode();
            displayZip();
            });*/
// TODO
        } catch (ServiceException ex) {
            Logger.getLogger(urgenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    private void clear(){
        txtNombre.clear();
        TxtDescripcion.clear();
        
    }
    
    private void displayDiscountCode(){
       /* Service<ObservableList<DiscountCode>> service = new Service<ObservableList<DiscountCode>>() {
            @Override
            protected Task<ObservableList<DiscountCode>> createTask() {
                return new Task<ObservableList<DiscountCode>>() {           
                    @Override
                    protected ObservableList<DiscountCode> call() throws Exception {
                        ObservableList<DiscountCode> listTask = FXCollections.observableArrayList();
                        if(listTask == null){
                            listTask = FXCollections.observableArrayList(crud.selectCode());
                        }else {
                            listTask.clear();
                            listTask.addAll(crud.selectCode());
                        }
                        cbDiscount.setItems(listTask);
                        return listTask;
                    }
                };
            }
        };
        service.start();*/
    }
    
    private void displayZip(){
      /*  Service<ObservableList<MicroMarket>> service = new Service<ObservableList<MicroMarket>>() {
            @Override
            protected Task<ObservableList<MicroMarket>> createTask() {
                return new Task<ObservableList<MicroMarket>>() {           
                    @Override
                    protected ObservableList<MicroMarket> call() throws Exception {
                        ObservableList<MicroMarket> listTask = FXCollections.observableArrayList();
                        if(listTask == null){
                            listTask = FXCollections.observableArrayList(crud.selectZip());
                        }else {
                            listTask.clear();
                            listTask.addAll(crud.selectZip());
                        }
                        cbZip.setItems(listTask);
                        return listTask;
                    }
                };
            }
        };
        service.start();*/
    }
    
    private void auto(){
      /*  if (crud.select().isEmpty()) {
            txtId.setText("1");
        }else{
            txtId.setText(String.valueOf(crud.auto()));
        }*/
    }
    
    private void selectData(){
       /* if(listData == null){
            listData = FXCollections.observableArrayList(crud.select());
        }else {
            listData.clear();
            listData.addAll(crud.select());
        }
        tableData.setItems(listData);*/
    }
    
    private void selectWithService(){
      /*  Service<Integer> service = new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                selectData();
                return new Task<Integer>() {           
                    @Override
                    protected Integer call() throws Exception {
                        Integer max = crud.select().size();
                        if (max > 35) {
                            max = 30;
                        }
                        updateProgress(0, max);
                        for (int k = 0; k < max; k++) {
                            Thread.sleep(40);
                            updateProgress(k+1, max);
                        }
                        return max;
                    }
                };
            }
        };
        service.start();
        bar.progressProperty().bind(service.progressProperty());
        service.setOnRunning((WorkerStateEvent event) -> {
            imgLoad.setVisible(true);
        });
        service.setOnSucceeded((WorkerStateEvent event) -> {
            imgLoad.setVisible(false);
            new FadeInUpTransition(paneTabel).play();
        });*/
    }
    
    
    
    @FXML
    private void keyState(KeyEvent e){
       /* if (txtState.getText().length() > 2) {
            UtilsVentanas.dialog(Alert.AlertType.INFORMATION, "State Must 2 Char");
            txtState.clear();
        }*/
    }

    @FXML
    private void aksiKlikTableData(MouseEvent event) {
       /* if (status==1) {
            try {
                ? klik = tableData.getSelectionModel().getSelectedItem();
                txtAddress1.setText(klik.getAddressline1());
                txtAddress2.setText(klik.getAddressline2());
                txtCity.setText(klik.getCity());
                txtCredit.setText(klik.getCreditLimit().toString());
                txtEmail.setText(klik.getEmail());
                txtFax.setText(klik.getFax());
                txtId.setText(klik.getCustomerId().toString());
                txtName.setText(klik.getName());
                txtPhone.setText(klik.getPhone());
                txtState.setText(klik.getState());
                cbDiscount.setValue(klik.getDiscountCode());
                cbZip.setValue(klik.getZip());
            } catch (Exception e) {
            }
        }*/
    }

    @FXML
    private void aksiNew(ActionEvent event) {
        paneTabel.setOpacity(0);
        new FadeInUpTransition(paneCrud).play();
        Platform.runLater(() -> {
            clear();
            auto();
        });
    }
    
    @FXML
    private void aksiSave(ActionEvent event){
        LblNombre.setText("");
        ControlVentana cv=new ControlVentana();
        if(txtNombre.getText().isEmpty()){
            LblNombre.setText("El campo nombre no puede estar vacio");
        }
        else{
            try{
                Urgencia urgencia=new Urgencia();
                int ind=ConfiguracionControl.traeUltimoId("Urgencia");
                urgencia.setIdurgencia(ind);
                urgencia.setActivo(ChkActivo.isSelected());
                urgencia.setNombre(txtNombre.getText());
                urgencia.setDescripcion(TxtDescripcion.getText());
                UrgenciaBean ub=new UrgenciaBean();
                ub.guardar(urgencia);
                ConfiguracionControl.ActualizaId("Urgencia");
                cv.creaVentanaNotificacionCorrecto();
                clear();
            }
            catch(Exception ex){
                cv.creaVentanaNotificacionError(ex.getMessage());
            }       
        }
        
        
       
            
       /*     ? a = new ?();
            a.setAddressline1(txtAddress1.getText());
            a.setAddressline2(txtAddress2.getText());
            a.setCity(txtCity.getText());
            a.setCreditLimit(Integer.valueOf(txtCredit.getText()));
            a.setCustomerId(Integer.valueOf(txtId.getText()));
            a.setDiscountCode(cbDiscount.getValue().toString());
            a.setEmail(txtEmail.getText());
            a.setFax(txtFax.getText());
            a.setName(txtName.getText());
            a.setPhone(txtPhone.getText());
            a.setState(txtState.getText());
            a.setZip(cbZip.getValue().toString());
            crud.saveOrUpdate(a);
            clear();
            selectData();
            auto();
            config2.dialog(Alert.AlertType.INFORMATION, "Success Save Data. . .");
            }*/
        
    }

    @FXML
    private void aksiBack(ActionEvent event) throws ServiceException {
        paneCrud.setOpacity(0);
        new FadeInUpTransition(paneTabel).play();       
    }
    
    private class ButtonCell extends TableCell<Object, Boolean> {
       /* final Hyperlink cellButtonDelete = new Hyperlink("Delete");
        final Hyperlink cellButtonEdit = new Hyperlink("Edit");
        final HBox hb = new HBox(cellButtonDelete,cellButtonEdit);
        ButtonCell(final TableView tblView){
            hb.setSpacing(4);
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                status = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Delete Data "+txtName.getText()+" ?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ? p = new ?();
                    p.setCustomerId(Integer.valueOf(txtId.getText()));
                    crud.delete(p);
                    clear();
                    selectData();
                }else{
                    clear();
                    selectData();
                    auto();
                }
                status = 0;
            });
            cellButtonEdit.setOnAction((ActionEvent event) -> {
                status = 1;
                int row = getTableRow().getIndex();
                tableData.getSelectionModel().select(row);
                aksiKlikTableData(null);
                paneTabel.setOpacity(0);
                new FadeInUpTransition(paneCrud).play();
                status = 0;
            });*/
        }

        protected void updateItem(Boolean t, boolean empty) {
          /*  super.updateItem(t, empty);
            if(!empty){
                setGraphic(hb);
            }else{
                setGraphic(null);
            }*/
        }
    }