package Controller;
import Model.PrgState;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.MyStack;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.Repo;
import Repo.IRepo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepo Repo;

    private boolean allsteps = true;

    private ExecutorService executor;

    public Controller(IRepo repository) {Repo = repository;}

    public void addProgram(PrgState newPrg) {
        Repo.addPrg(newPrg);
    }

    Map<Integer, IValue> unsafeGarbageCollector(List<PrgState> prgStates, Map<Integer, IValue> heap){
        List<Integer> adresses = prgStates.stream()
                .map(program -> getAddrFromSymTable(program.getSymTable().getValues()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<Integer> adresses2 = prgStates.stream()
                .map(program -> getAddrFromHeapTable(program.getHeapTable().getContent().values()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        adresses.addAll(adresses2);

        return heap.entrySet().stream()
                .filter(e->adresses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    java.util.List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return (java.util.List<Integer>) symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    java.util.List<Integer> getAddrFromHeapTable(Collection<IValue> heapTableValues)
    {
        return (java.util.List<Integer>) heapTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    java.util.List<Integer> getAllRefAdresses(Collection<Integer> symTableValues, Collection<Integer> heapTableValues)
    {
        List<Integer> referencedAdresses = new ArrayList<>();
        referencedAdresses.addAll(heapTableValues);
        referencedAdresses.addAll(symTableValues);
        return referencedAdresses;
    }


    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws Exception{

        executor = Executors.newFixedThreadPool(2);

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {try { return future.get();}
                catch (Exception e) {
                   // System.out.print(e.toString());
                }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                Repo.logPrgStateExec(prg);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });

        Repo.setPrgList(prgList);
    }



    public void allStep() throws Exception {

        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(Repo.getPrgList());

        while(prgList.size() > 0) {
            System.out.print(prgList.size());
            IHeap<IValue> heap = prgList.get(0).getHeapTable();

            prgList.forEach(prg -> {
                try {
                    Repo.logPrgStateExec(prg);
                } catch (IOException e)
                {
                    //System.out.print(e.toString());
                }
            });

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(Repo.getPrgList());
            heap.setContent(unsafeGarbageCollector(prgList, heap.getContent()));
        }

        executor.shutdownNow();

        Repo.setPrgList(prgList);

}


}
