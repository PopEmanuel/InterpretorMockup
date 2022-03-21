package Repo;
import Exceptions.EmptyRepoException;
import Model.PrgState;
import Model.adt.MyList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo {

    List<PrgState> myPrgStates;
    String logFilePath;
    public static final String RED = "\033[0;31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Repo(String txt) {
        myPrgStates = new ArrayList<PrgState>();
        logFilePath = txt;
    }

    public Repo(PrgState state, String txt)
    {
        myPrgStates = new ArrayList<PrgState>();
        myPrgStates.add(state);
        logFilePath = txt;
    }


    @Override
    public void logPrgStateExec(PrgState prg) throws IOException {

        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.append(prg.toString());

        logFile.close();
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.myPrgStates;
    }

    @Override
    public void setPrgList(List<PrgState> lst) {
        this.myPrgStates = lst;
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }
}
