package Model;

import java.util.List;

public interface StudentService {
    void add();
    void commit();
    void commitTransition();
    List<Student> readFileToStudentList();
    void showList();
    void clearData();
    void search();
    void searchByID();
    void searchByName();
    void updateByID();
    void updateName();
    void updateBirth();
    void updateClass();
    void updateSubject();
    void generateData();
    void deleted();
}
