package View;

import Model.StudentServiceImp;
import java.util.Scanner;

public class Menu {
    public static void logo() {
                System.out.println(
                                "                                        ██████╗███████╗████████╗ █████╗ ██████╗ \n" +
                                "                                       ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗\n" +
                                "                                       ██║     ███████╗   ██║   ███████║██║  ██║\n" +
                                "                                       ██║     ╚════██║   ██║   ██╔══██║██║  ██║\n" +
                                "                                       ╚██████╗███████║   ██║   ██║  ██║██████╔╝\n" +
                                "                                        ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝ ");
    }
    public static void menu() {
        StudentServiceImp imp = new StudentServiceImp();
        System.out.println("=================================================================================================================");
        System.out.println("            1. ADD NEW STUDENT            2. LIST ALL STUDENTS            3. COMMIT DATA TO FILE");
        System.out.println("            4. SEARCH FOR STUDENT         5. UPDATE STUDENTS' INFO BY ID  6. DELETE STUDENT'S DATA");
        System.out.println("            7. GENERATE DATA TO FILE      8. DELETE/CLEAR ALL DATA FROM DATA STORE");
        System.out.println("            0, 99. EXIT");
        System.out.println("                                                                                                 @CopyRight-CSTAD");
        System.out.println("=================================================================================================================");
        System.out.print("> Insert option: ");
        int op = new Scanner(System.in).nextInt();
        StudentServiceImp m = new StudentServiceImp();
        switch (op) {
            case 1 -> m.add();
            case 2 -> m.showList();
            case 3 -> m.commit();
            case 4 -> m.search();
            case 5 -> m.updateByID();
            case 6 -> m.deleted();
            case 7 -> m.generateData();
            case 8 -> m.clearData();
            case 9 -> m.readFileToStudentList();
            case 0, 99 -> System.exit(0);
        }
    }
}
