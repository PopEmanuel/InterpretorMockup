package Model.stmt;

import Exceptions.ArtihmeticException;
import Exceptions.AssignOperationException;
import Exceptions.InvalidIdException;
import Exceptions.LogicOperationException;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws Exception;
    String toString();
    IDict<String,IType> typecheck(IDict<String, IType> typeEnv) throws Exception;
}
