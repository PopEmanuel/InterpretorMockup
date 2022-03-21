package com.example.javainterpretorgui;

import Model.adt.*;
import Model.exp.ValueExp;
import Model.stmt.AssignStmt;
import Model.stmt.CompStmt;
import Model.stmt.IStmt;
import Model.stmt.VarDeclStmt;
import Model.types.IntType;
import Model.types.StringType;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import Controller.Controller;
import Model.PrgState;
import Model.exp.*;
import Model.stmt.*;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.IRepo;
import Repo.Repo;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
    private Button btnChoseProgram;
    private ListView<String> lstPrograms;
    private int selectedProgram;
    private int currentProgramState;
    private TextField txtNrStates;
    private TableView<heapItem> tblHeapTable;
    private ListView<String> lstOut;
    private ListView<String> lstFileTable;
    private ListView<String> lstPrgStates;
    private TableView<symItem> tblSymTable;
    private ListView<String> lstExeStack;
    private Button btnOneStep;
    private Label lblNrStates;
    private Label lblHeapTable;
    private Label lblOut;
    private Label lblFileTable;
    private Label lblPrgStates;
    private Label lblStack;
    private Label lblSymTable;
    private Controller controller;
    private IRepo repository;



    @Override
    public void start(Stage stage) throws IOException {
        Pane layout = new Pane();


        InitializeComponents(layout);

        Scene scene = new Scene(layout, 700, 750);

        PopulateListView();


        stage.setTitle("Interpretor");
        stage.setScene(scene);
        stage.show();
    }

    public void PopulateListView()
    {
        lstPrograms.getItems().add("Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)");
        lstPrograms.getItems().add("Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);");
        lstPrograms.getItems().add("Ref int v;Ref Ref int u;new(v,20); new(u,v); new(v,30);print(rH(rH(u)))");
        lstPrograms.getItems().add("int a; a = 3; While(a > 0) a--; print(a)");
        lstPrograms.getItems().add("int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))");
        lstPrograms.getItems().add("files");

    }


    public void InitializeComponents(Pane layout)
    {
        this.btnChoseProgram = new Button("Chose program");
        this.btnChoseProgram.setMinSize(100, 100);
        this.btnChoseProgram.setMaxSize(100, 100);
       // this.btnChoseProgram.setLayoutX(layout.getWidth() - 150);
       // this.btnChoseProgram.setLayoutY(layout.getHeight() / 2);

        lstPrograms = new ListView<>();
        this.lstPrograms.setMinSize(500, 500);
        this.lstPrograms.setMaxSize(500, 500);


        lstPrograms.setLayoutX(50);
        lstPrograms.setLayoutY(50);
        btnChoseProgram.setLayoutX(580);
        btnChoseProgram.setLayoutY(200);
        layout.getChildren().add(btnChoseProgram);
        layout.getChildren().add(lstPrograms);

        btnChoseProgram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                selectedProgram = lstPrograms.getSelectionModel().getSelectedIndex();

                ChangeView(2);

            }
        });


        tblHeapTable = new TableView<>();
        this.tblHeapTable.setMinSize(200, 200);
        this.tblHeapTable.setMaxSize(200, 200);

        tblHeapTable.setLayoutX(70);
        tblHeapTable.setLayoutY(70);

        txtNrStates = new TextField("1");
        txtNrStates.setLayoutX(30);
        txtNrStates.setLayoutY(30);

        lstOut = new ListView<>();
        lstOut.setLayoutX(300);
        lstOut.setLayoutY(70);
        lstOut.setMinSize(200, 200);
        lstOut.setMaxSize(200, 200);

        lstFileTable = new ListView<>();
        lstFileTable.setLayoutX(70);
        lstFileTable.setLayoutY(300);
        lstFileTable.setMinSize(200, 200);
        lstFileTable.setMaxSize(200, 200);

        lstPrgStates = new ListView<>();
        lstPrgStates.setLayoutX(300);
        lstPrgStates.setLayoutY(300);
        lstPrgStates.setMinSize(200, 200);
        lstPrgStates.setMaxSize(200, 200);

        lstPrgStates.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Your action here

                System.out.println("Selected item: " + lstPrgStates.getSelectionModel().getSelectedIndex());
                if(lstPrgStates.getSelectionModel().getSelectedIndex() != -1) {
                    try{
                        currentProgramState = lstPrgStates.getSelectionModel().getSelectedIndex();
                        //PopulateProgramTables();
                    }
                    catch (Exception ignored)
                    {

                    }



                }

            }
        });

        tblSymTable = new TableView<>();
        this.tblSymTable.setMinSize(200, 200);
        this.tblSymTable.setMaxSize(200, 200);

        tblSymTable.setLayoutX(70);
        tblSymTable.setLayoutY(530);

        TableColumn <symItem, String> address1 = new TableColumn<>();
        TableColumn <symItem, String> value1 = new TableColumn<>();
        address1.setCellValueFactory(new PropertyValueFactory<symItem, String>("varname"));
        value1.setCellValueFactory(new PropertyValueFactory<symItem, String>("value"));
        tblSymTable.getColumns().add(address1);
        tblSymTable.getColumns().add(value1);

        lstExeStack = new ListView<>();
        lstExeStack.setLayoutX(300);
        lstExeStack.setLayoutY(530);
        lstExeStack.setMinSize(200, 200);
        lstExeStack.setMaxSize(200, 200);

        this.btnOneStep = new Button("OneStep");
        this.btnOneStep.setMinSize(100, 100);
        this.btnOneStep.setMaxSize(100, 100);
        btnOneStep.setLayoutX(580);
        btnOneStep.setLayoutY(200);

        btnOneStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    controller.oneStepForAllPrg(repository.getPrgList());
                    PopulateProgramTables();
                } catch (Exception ex) {

                }

            }
        });
        TableColumn <heapItem, Integer> address = new TableColumn<>();
        TableColumn <heapItem, String> value = new TableColumn<>();
        address.setCellValueFactory(new PropertyValueFactory<heapItem, Integer>("adress"));
        value.setCellValueFactory(new PropertyValueFactory<heapItem, String>("value"));
        tblHeapTable.getColumns().add(address);
        tblHeapTable.getColumns().add(value);


        layout.getChildren().add(tblHeapTable);
        layout.getChildren().add(txtNrStates);
        layout.getChildren().add(lstOut);
        layout.getChildren().add(lstFileTable);
        layout.getChildren().add(lstPrgStates);
        layout.getChildren().add(tblSymTable);
        layout.getChildren().add(lstExeStack);
        layout.getChildren().add(btnOneStep);




        lblNrStates = new Label("Number of prg states : ");
        lblNrStates.setLayoutX(txtNrStates.getLayoutX());
        lblNrStates.setLayoutY(txtNrStates.getLayoutY() - 20);
        layout.getChildren().add(lblNrStates);

        lblHeapTable = new Label("Heap Table : ");
        lblHeapTable.setLayoutX(tblHeapTable.getLayoutX());
        lblHeapTable.setLayoutY(tblHeapTable.getLayoutY() - 18);
        layout.getChildren().add(lblHeapTable);

        lblOut = new Label("Output : ");
        lblOut.setLayoutX(lstOut.getLayoutX());
        lblOut.setLayoutY(lstOut.getLayoutY() - 18);
        layout.getChildren().add(lblOut);

        lblFileTable = new Label("File Table : ");
        lblFileTable.setLayoutX(lstFileTable.getLayoutX());
        lblFileTable.setLayoutY(lstFileTable.getLayoutY() - 18);
        layout.getChildren().add(lblFileTable);

        lblPrgStates = new Label("Program states : ");
        lblPrgStates.setLayoutX(lstPrgStates.getLayoutX());
        lblPrgStates.setLayoutY(lstPrgStates.getLayoutY() - 18);
        layout.getChildren().add(lblPrgStates);

        lblStack = new Label("ExeStack : ");
        lblStack.setLayoutX(lstExeStack.getLayoutX());
        lblStack.setLayoutY(lstExeStack.getLayoutY() - 18);
        layout.getChildren().add(lblStack);

        lblSymTable = new Label("SymTable : ");
        lblSymTable.setLayoutX(tblSymTable.getLayoutX());
        lblSymTable.setLayoutY(tblSymTable.getLayoutY() - 18);
        layout.getChildren().add(lblSymTable);

        tblHeapTable.setVisible(false);
        txtNrStates.setVisible(false);
        lstOut.setVisible(false);
        lstFileTable.setVisible(false);
        lstPrgStates.setVisible(false);
        tblSymTable.setVisible(false);
        lstExeStack.setVisible(false);
        btnOneStep.setVisible(false);

        lblSymTable.setVisible(false);
        lblStack.setVisible(false);
        lblPrgStates.setVisible(false);
        lblFileTable.setVisible(false);
        lblOut.setVisible(false);
        lblHeapTable.setVisible(false);
        lblNrStates.setVisible(false);

    }

    public void ChangeView(int viewNumber)
    {
        if(viewNumber == 1)
        {
            btnChoseProgram.setVisible(true);
            lstPrograms.setVisible(true);

            tblHeapTable.setVisible(false);
            txtNrStates.setVisible(false);
            lstOut.setVisible(false);
            lstFileTable.setVisible(false);
            lstPrgStates.setVisible(false);
            tblSymTable.setVisible(false);
            lstExeStack.setVisible(false);
            btnOneStep.setVisible(false);
            lblSymTable.setVisible(false);
            lblStack.setVisible(false);
            lblPrgStates.setVisible(false);
            lblFileTable.setVisible(false);
            lblOut.setVisible(false);
            lblHeapTable.setVisible(false);
            lblNrStates.setVisible(false);

            viewNumber = 2;
        }
        else
            if(viewNumber == 2)
            {
                btnChoseProgram.setVisible(false);
                lstPrograms.setVisible(false);

                tblHeapTable.setVisible(true);
                txtNrStates.setVisible(true);
                lstOut.setVisible(true);
                lstFileTable.setVisible(true);
                lstPrgStates.setVisible(true);
                tblSymTable.setVisible(true);
                lstExeStack.setVisible(true);
                btnOneStep.setVisible(true);
                lblSymTable.setVisible(true);
                lblStack.setVisible(true);
                lblPrgStates.setVisible(true);
                lblFileTable.setVisible(true);
                lblOut.setVisible(true);
                lblHeapTable.setVisible(true);
                lblNrStates.setVisible(true);

                viewNumber = 1;

                switch (selectedProgram) {
                    case 0:
                        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
                        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                                new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                                        new newStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new VarExp("a"))
                                        , new PrintStmt(new VarExp("v"))))
                                )
                                ));
                        try{
                            ex5.typecheck(new Dict<>());
                        }
                        catch(Exception e)
                        {

                        }
                        PrgState prg5 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex5);
                        IRepo repo5 = new Repo(prg5, "log5.txt");
                        repository = repo5;
                        controller = new Controller(repo5);
                        currentProgramState = 0;

                        PopulateProgramTables();
                        break;
                    case 1:
                        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
                        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new newStmt("v",
                                new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new rHeap(new VarExp("v"))),
                                new CompStmt(new wHeap("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp('+',
                                        new rHeap(new VarExp("v")), new ValueExp(new IntValue(5))))))));
                        try{
                            ex6.typecheck(new Dict<>());
                        }
                        catch(Exception e)
                        {

                        }

                        PrgState prg6 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex6);
                        IRepo repo6 = new Repo(prg6, "log6.txt");

                        Controller ctr6 = new Controller(repo6);
                        currentProgramState = 0;
                        repository = repo6;
                        controller = ctr6;
                        PopulateProgramTables();
                        break;
                    case 2:
                        //Ref int v;Ref Ref int u;new(v,20); new(u,v); new(v,30);print(rH(rH(u)))
                        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new VarDeclStmt(
                                "u", new RefType(new RefType(new IntType()))), new CompStmt(new newStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(new newStmt("u", new VarExp("v") ), new CompStmt(new newStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new rHeap(new rHeap(new VarExp("u")))))
                                ))));
                        try{
                            ex7.typecheck(new Dict<>());
                        }
                        catch(Exception e)
                        {

                        }
                        PrgState prg7 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex7);
                        IRepo repo7 = new Repo(prg7, "log7.txt");
                        Controller ctr7 = new Controller(repo7);
                        currentProgramState = 0;
                        repository = repo7;
                        controller = ctr7;
                        PopulateProgramTables();
                        break;
                    case 3:
                        //int a; a = 3; While(a > 0) a--;print(a)
                        IStmt ex8 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(3))),
                                new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("a"), new ValueExp(new IntValue(0))), new AssignStmt("a",
                                        new ArithExp('-', new VarExp("a"), new ValueExp(new IntValue(1))))),
                                        new PrintStmt(new VarExp("a")))));
                        try{
                            ex8.typecheck(new Dict<>());
                        }
                        catch(Exception e)
                        {

                        }
                        PrgState prg8 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex8);
                        IRepo repo8 = new Repo(prg8, "log8.txt");
                        Controller ctr8 = new Controller(repo8);
                        currentProgramState = 0;
                        repository = repo8;
                        controller = ctr8;
                        PopulateProgramTables();
                        break;
                    case 4:
                        //int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a)));
                        //   print(v);print(rH(a))
                        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new newStmt("a", new ValueExp(
                                        new IntValue(22)
                                )), new CompStmt(new forkStmt(new CompStmt(new wHeap("a", new ValueExp(new IntValue(30))), new CompStmt(
                                        new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarExp("v")),
                                        new PrintStmt(new rHeap(new VarExp("a"))))))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(
                                        new rHeap(new VarExp("a"))
                                )))))));
                        try{
                            ex9.typecheck(new Dict<>());
                        }
                        catch(Exception e)
                        {

                        }
                        PrgState prg9 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex9);
                        IRepo repo9 = new Repo(prg9, "log9.txt");
                        Controller ctr9 = new Controller(repo9);
                        currentProgramState = 0;
                        repository = repo9;
                        controller = ctr9;
                        PopulateProgramTables();
                        break;
                    case 5:
                        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                                new CompStmt(new openRFileStmt(new ValueExp(new StringValue("varf"))), new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("varf")), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                                new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("varf")), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),  new closeRFileStmt(new ValueExp(new StringValue("varf"))) ))))))));
                        try{
                            ex4.typecheck(new Dict<>());
                        }
                        catch(Exception e)
                        {

                        }
                        PrgState prg4 = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue >(), new MyList<String>(), ex4);
                        IRepo repo4 = new Repo(prg4, "log4.txt");
                        Controller ctr4 = new Controller(repo4);

                        currentProgramState = 0;
                        repository = repo4;
                        controller = ctr4;
                        PopulateProgramTables();
                }


            }
    }

    public void PopulateProgramTables()
    {
//        private TextField txtNrStates;
//        private TableView<String> tblHeapTable;
//        private ListView<String> lstOut;
//        private ListView<String> lstFileTable;
//        private ListView<String> lstPrgStates;
//        private TableView<String> tblSymTable;
//        private ListView<String> lstExeStack;

        txtNrStates.setText(String.valueOf(repository.getPrgList().size()));

        PrgState state = repository.getPrgList().get(currentProgramState);
        IHeap<IValue> heap = state.getHeapTable();
        Map<Integer, IValue> dictionary = heap.getContent();

        tblHeapTable.getItems().clear();

        ObservableList<heapItem> lst = FXCollections.observableArrayList();

        for (Map.Entry<Integer, IValue> entry : dictionary.entrySet())
        {
            lst.add(new heapItem(entry.getKey(), entry.getValue().toString()));
        }
        tblHeapTable.setItems(lst);

        IStack<IStmt> stack = state.getStack();
        lstExeStack.getItems().clear();
        lstExeStack.getItems().add(stack.toString());

        IList<String> out = state.getOutput();
        lstOut.getItems().clear();
        lstOut.getItems().add(out.toString());

        IDict<StringValue, BufferedReader> filetbl = state.getFileTable();
        lstFileTable.getItems().clear();
        lstFileTable.getItems().add(filetbl.toString());

        List<PrgState> states = repository.getPrgList();
        lstPrgStates.getItems().clear();
        for(PrgState state1 : states)
        {
            lstPrgStates.getItems().add(String.valueOf(state1.getId()));
        }


        IDict<String, IValue> symtbl = state.getSymTable();
        Map<String, IValue> dict = symtbl.getContent();
        tblSymTable.getItems().clear();


        ObservableList<symItem> lst1 = FXCollections.observableArrayList();

        for (Map.Entry<String, IValue> entry : dict.entrySet())
        {
            lst1.add(new symItem(entry.getKey(), entry.getValue().toString()));
        }
        tblSymTable.setItems(lst1);

    }

    public static void main(String[] args) {
        launch();
    }
}