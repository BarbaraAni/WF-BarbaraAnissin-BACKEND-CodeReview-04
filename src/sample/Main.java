package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //ICON
        InputStream input = this.getClass().getResourceAsStream("/images/price.png" );
        Image image = new Image(input);
        primaryStage.getIcons().add(image);

        // INITIALIZING //
        BorderPane main = new BorderPane();
        primaryStage.setTitle("Set action prices");
        primaryStage.setScene(new Scene(main, 600, 600));
        primaryStage.setMinWidth(590);
        primaryStage.setMinHeight(550);
        primaryStage.setMaxWidth(700);
        primaryStage.show();

        // Structure //
        VBox left = new VBox();
        GridPane txtAndLbl = new GridPane();
        txtAndLbl.setHgap(10);
        txtAndLbl.setVgap(10);

        Label lblProduct = new Label("Prod. Name");
        TextField txtProduct = new TextField(); //NON EDITABLE

        Label lblQuantity = new Label("Quantity");
        TextField txtQuantity = new TextField(); //NON EDITABLE

        Label oldPrice = new Label("old price");
        TextField txtOldPrice = new TextField();
        Label lblEurOld = new Label("EUR");

        Label newPrice = new Label("new price");
        TextField txtNewPrice = new TextField();
        Label lblEurNew = new Label("EUR");

        ObservableList<String> options = FXCollections.observableArrayList(
                "pfeffer",
                "cheese_salakis",
                "zucker",
                "voslauer"
        );
        final ComboBox comboBox = new ComboBox(options);
        comboBox.setVisible(false);
        comboBox.setManaged(false);

        txtAndLbl.add(lblProduct,0,0);
        txtAndLbl.add(txtProduct,1,0);
        txtAndLbl.add(lblQuantity,0,1);
        txtAndLbl.add(txtQuantity,1,1);
        txtAndLbl.add(oldPrice,0,2);
        txtAndLbl.add(txtOldPrice,1,2);
        txtAndLbl.add(lblEurOld,2,2);
        txtAndLbl.add(newPrice,0,3);
        txtAndLbl.add(txtNewPrice,1,3);
        txtAndLbl.add(lblEurNew,2,3);
        txtAndLbl.add(comboBox,1,4);
        txtAndLbl.setAlignment(Pos.TOP_CENTER);

        //IMAGES
        ImageView img = new ImageView();
        img.setFitWidth(250);
        img.setFitHeight(300);
        img.setManaged(false);

        VBox descr = new VBox();
        Label description = new Label("Description: ");
        Label lblDesc = new Label("");
        lblDesc.setWrapText(true);
        descr.getChildren().addAll(description,lblDesc);
        lblDesc.prefWidthProperty().bind(left.widthProperty());
        descr.setAlignment(Pos.CENTER);

        //ADD
        Button btnAdd = new Button("Add");
        Button btnFinished = new Button("Finished");
        TextArea descText = new TextArea();
        descText.setWrapText(true);
        descText.setVisible(false);
        descText.setManaged(false);
        btnFinished.setVisible(false);
        btnFinished.setManaged(false);
        descText.prefWidthProperty().bind(left.widthProperty());

        //UPDATE
        Button btnUpdate = new Button("Update");

        //REPORT
        File newFile = new File("report.txt");
        Button btnReport = new Button("Report");

        HBox buttons = new HBox();
        buttons.getChildren().addAll(btnAdd,btnFinished,btnUpdate,btnReport);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        left.getChildren().addAll(txtAndLbl,descText,img,descr,buttons);
        left.setAlignment(Pos.TOP_CENTER);
        left.setSpacing(10);

        ObservableList<Product> items = FXCollections.observableArrayList(
                new Product("Schafmilchkäse","200 Gramm Packung", "Hier gibt es keine Beschreibung",2.59,1.99,"cheese_salakis"),
                new Product("Vöslauer", "1.5 Liter Flasche","Spritziges Vöslauer Mineralwasser",0.75,0.49,"voslauer"),
                //new Product("Pfeffer","1 Stück","Gewürz, gemahlen",2.49,2.79,"pfeffer"),
                new Product("Zucker","1 Packung","zum Kochen und Backen",3.49,2.79,"zucker")
        );

        ListView<Product> right = new ListView<>();
        right.getItems().addAll(items);
        right.setMinWidth(300);
        main.setLeft(left);
        main.setRight(right);
        main.setStyle("-fx-padding:10");

        //ACTION
        btnAdd.setOnAction(actionEvent -> {
            txtProduct.setDisable(false);
            txtQuantity.setDisable(false);
            btnAdd.setDisable(true);
            btnFinished.setVisible(true);
            btnFinished.setManaged(true);
            comboBox.setVisible(true);
            comboBox.setManaged(true);
            descText.setVisible(true);
            descText.setManaged(true);
            descr.setVisible(false);
            descr.setManaged(false);
            img.setVisible(false);
            img.setManaged(false);
        });
        btnFinished.setOnAction(actionEvent -> {
            String product = txtProduct.getText();
            String quanti = txtQuantity.getText();
            String oPrice = txtOldPrice.getText();
            String nPrice = txtNewPrice.getText();
            Product item = new Product(product,quanti,descText.getText(),Double.parseDouble(oPrice),Double.parseDouble(nPrice),String.valueOf(comboBox.getSelectionModel().getSelectedItem()));
            items.add(item);
            right.getItems().add(item);
            System.out.println("Added");
            btnAdd.setDisable(false);
            btnFinished.setManaged(false);
            btnFinished.setVisible(false);
            descText.setVisible(false);
            descText.setManaged(false);
            comboBox.setVisible(false);
            comboBox.setManaged(false);
        });
        btnUpdate.setOnAction(actionEvent ->  {
            System.out.println("Updated");
            int selectedIdx = right.getSelectionModel().getSelectedIndex();
            if(selectedIdx != -1) {
                // Get values
                String product = txtProduct.getText();
                String quanti = txtQuantity.getText();
                String oPrice = txtOldPrice.getText();
                String nPrice = txtNewPrice.getText();
                // Set values
                if (oPrice.isEmpty()||nPrice.isBlank()||product.isBlank()||quanti.isEmpty()){
                    System.out.println("one or more fields are empty");
                } else {
                    right.getItems().get(selectedIdx).setNewPrice(Double.parseDouble(nPrice));
                    right.getItems().get(selectedIdx).setOldPrice(Double.parseDouble(oPrice));
                }
                right.refresh();
            }
        });
        btnReport.setOnAction(actionEvent ->{
            Writer finalRep;
            try {
                finalRep = new BufferedWriter(new FileWriter(newFile));
                for (Product item : items) {
                    finalRep.write(item.toString() + "\n");
                }
                finalRep.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Your file has been written");
        });

        //CONTROLS
        right.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            img.setManaged(true);
            txtProduct.setText(newValue.getTitle());
            txtQuantity.setText(newValue.getQuantity());
            txtOldPrice.setText(String.valueOf(newValue.getOldPrice()));
            txtNewPrice.setText(String.valueOf(newValue.getNewPrice()));
            img.setImage(newValue.getImage());
            lblDesc.setText(newValue.getDescription());
            txtProduct.setDisable(true);
            txtQuantity.setDisable(true);
            descr.setVisible(true);
            descr.setManaged(true);
            img.setVisible(true);
            img.setManaged(true);
            btnAdd.setDisable(false);
            btnFinished.setManaged(false);
            btnFinished.setVisible(false);
            comboBox.setVisible(false);
            comboBox.setManaged(false);
            descText.setVisible(false);
            descText.setManaged(false);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}