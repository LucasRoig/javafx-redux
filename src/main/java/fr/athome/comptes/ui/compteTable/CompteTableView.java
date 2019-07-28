package fr.athome.comptes.ui.compteTable;

import fr.athome.comptes.models.pojo.Compte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompteTableView {

    @FXML
    private TableColumn<CompteRowViewModel, Boolean> checkboxColumn;
    @FXML
    private TableView<CompteRowViewModel> tableView;
    @FXML
    private TableColumn<CompteRowViewModel,String> usernameColumn;
    @FXML
    private TableColumn<CompteRowViewModel,String> passwordColumn;
    @FXML
    private TableColumn<CompteRowViewModel,String> editButtonColumn;

    private CompteTablePresenter presenter;

    @FXML
    private void initialize(){
        tableView.setEditable(true);
        usernameColumn.setCellValueFactory(cell -> cell.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(cell -> cell.getValue().passwordProperty());
        checkboxColumn.setCellValueFactory(cell -> cell.getValue().selectedProperty());
        editButtonColumn.setCellValueFactory(new PropertyValueFactory<>("dummy"));
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        editButtonColumn.setCellFactory(column -> {
            TableCell cell = new TableCell<CompteRowViewModel,String>(){
                final Button button = new Button("éditer");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.setOnAction(e -> {
                            CompteRowViewModel compte = getTableView().getItems().get(getIndex());
                            onEditClicked(compte);
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
    }

    public void onEditClicked(CompteRowViewModel compteRowViewModel){
        presenter.edit(compteRowViewModel.getCompte());
    }

    public void onDeleteClicked(ActionEvent e){
        presenter.delete();
    }

    public void setPresenter(CompteTablePresenter presenter) {
        this.presenter = presenter;
        tableView.setItems(presenter.getCompteRows());
    }

    public void newCompte(ActionEvent actionEvent) {
        presenter.openNewCompteView();
    }
}
