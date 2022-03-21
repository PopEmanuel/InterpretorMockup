package Repo;
import Exceptions.EmptyRepoException;
import Model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);

    void logPrgStateExec(PrgState prg) throws IOException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> lst);
    }
