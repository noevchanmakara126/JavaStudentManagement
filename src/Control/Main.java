package Control;

import Model.StudentServiceImp;
import static View.Menu.*;

public class Main {
    public static void main(String[] args) {
        StudentServiceImp imp = new StudentServiceImp();
        logo();
        imp.commitTransition();
        imp.readFileToStudentList();
        while (true) {
            menu();
        }
    }
}