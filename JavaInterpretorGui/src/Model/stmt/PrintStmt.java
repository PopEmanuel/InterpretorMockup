package Model.stmt;


import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IList;
import Model.exp.Exp;
import Model.types.IType;

public class PrintStmt implements IStmt{

    Exp expression;

    public PrintStmt(Exp exp){
        this.expression = exp;
    }

    public String toString(){
        return "print("+expression.toString()+");";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws Exception {
        expression.typecheck(typeEnv);
        return typeEnv;
    }

    public PrgState execute(PrgState state) throws Exception {
        IList<String> output = state.getOutput();

        //output.add(state.getSymTable().lookup(expression.toString()).toString());
        output.add(expression.eval(state.getSymTable(), state.getHeapTable()).toString());
        state.setOutput(output);
        return null;
    }
}
