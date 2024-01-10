package edu.course2.job4.modelclasses;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public List<ModelStructure> fLine;
    public Model() {
        this.fLine=new ArrayList<>();
    }
    @Override public String toString() {
        StringBuilder str=new StringBuilder();
        for(ModelStructure mod:this.fLine) {
            str.append(mod.toString()).append("\n");
        }
        return str.toString();
    }
}
