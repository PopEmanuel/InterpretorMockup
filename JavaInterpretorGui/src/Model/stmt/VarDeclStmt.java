package Model.stmt;


import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;
import Model.value.IValue;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String name, IType type){
        this.name = name;
        this.type = type;
    }


    @Override
    public PrgState execute(PrgState state) {
        IStack<IStmt> stack = state.getStack();
        IDict<String, IValue> symTbl = state.getSymTable();

        if(!symTbl.isDefined(name))
            symTbl.add(name, type.defaultValue());
        return null;
    }

    public String toString(){
        return type.toString() + ' ' + name;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        typeEnv.add(name, type);
        return typeEnv;
    }
}
