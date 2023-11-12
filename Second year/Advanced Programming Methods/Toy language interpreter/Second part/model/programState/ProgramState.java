package model.programState;

import exceptions.MyException;
import model.statement.IStatement;
import model.statement.IfStatement;
import model.utils.MyIDictionary;
import model.utils.MyIHeap;
import model.utils.MyIList;
import model.utils.MyIStack;
import model.value.Value;

import java.io.BufferedReader;
import java.util.List;

public class ProgramState {

        private MyIStack<IStatement> exeStack;
        private MyIDictionary<String, Value> symTable;
        private MyIList<Value> out;
        private IStatement originalProgram;

         MyIDictionary<String, BufferedReader> fileTable;



        public ProgramState(MyIStack<IStatement> stack, MyIDictionary<String,Value> symTable, MyIList<Value> out,  MyIDictionary<String, BufferedReader> fileTable, IStatement program) {
                this.exeStack = stack;
                this.symTable = symTable;
                this.out = out;
                this.originalProgram = program.deepCopy();
                this.exeStack.push(this.originalProgram);

                this.fileTable = fileTable;


        }

        public void setExeStack(MyIStack<IStatement> newStack) {
                this.exeStack = newStack;
        }

        public void setSymTable(MyIDictionary<String, Value> newSymTable) {
                this.symTable = newSymTable;
        }

        public void setOut(MyIList<Value> newOut) {
                this.out = newOut;
        }

        public void setFileTable(MyIDictionary<String, BufferedReader> newFileTable) {this.fileTable = newFileTable;}



        public MyIStack<IStatement> getExeStack() {
                return exeStack;
        }

        public MyIDictionary<String, Value> getSymTable() {
                return symTable;
        }

        public MyIList<Value> getOut() {
                return out;
        }

        public MyIDictionary<String, BufferedReader> getFileTable() {return fileTable;}



        public String exeStackToString() {
                StringBuilder exeStackStringBuilder = new StringBuilder();
                List<IStatement> stack = exeStack.getReversed();
                for (IStatement statement: stack) {
                        exeStackStringBuilder.append(statement.toString()).append("\n");
                }
                return exeStackStringBuilder.toString();
        }

        public String symTableToString() throws MyException {
                StringBuilder symTableStringBuilder = new StringBuilder();
                for (String key: symTable.keySet()) {
                        symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
                }
                return symTableStringBuilder.toString();
        }

        public String outToString() {
                StringBuilder outStringBuilder = new StringBuilder();
                for (Value elem: out.getList()) {
                        outStringBuilder.append(String.format("%s\n", elem.toString()));
                }
                return outStringBuilder.toString();
        }

        public String fileTableToString() {
                StringBuilder fileTableStringBuilder = new StringBuilder();
                for (String key: fileTable.keySet()) {
                        fileTableStringBuilder.append(String.format("%s\n", key));
                }
                return fileTableStringBuilder.toString();
        }



        @Override
        public String toString() {
                return "Execution stack: \n" + exeStack.getReversed() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString() +  "\nFile table:\n" + fileTableToString() + "\n";
        }

        public String programStateToString() throws MyException {
                return "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n" + outToString() + "File table:\n" + fileTableToString() ;
        }


}