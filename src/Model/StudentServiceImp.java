package Model;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class StudentServiceImp implements StudentService {
    static List<Student> studentList = new ArrayList<>();
    static List<Student> list1 = new ArrayList<>();
    static Path pathD = Paths.get("dataFile.dat");
    static Path pathT = Paths.get("transitionFile.dat");
    static String[] filePath = {"transitionFile.dat", "dataFile.dat"};
    @Override
    public void add() {
        int month, year, day;
        Random random = new Random();
        String id = random.nextInt(1000) + "CSTAD";
        Scanner scanner = new Scanner(System.in);
        System.out.println("..........................");
        System.out.println("> INSERT STUDENT'S INFO");
        System.out.print("[+] Insert student's name: ");
        String name = scanner.nextLine();
        System.out.println("[+] STUDENT DATE OF BIRTH");
        do {
            System.out.print("> Year (number): ");
            year = scanner.nextInt();
            if (year < 1900 || year > LocalDate.now().getYear()) {
                System.out.println("[+] Invalid year");
            }
        } while (year < 1900 || year > LocalDate.now().getYear());
        do {
            System.out.print("> Month (number): ");
            month = scanner.nextInt();
            if (month < 1 || month > 12) {
                System.out.println("[+] Invalid month");
            }
        } while (month < 1 || month > 12);
        do {
            System.out.print("> Day (number): ");
            day = scanner.nextInt();
            if (day < 1 || day > 31) {
                System.out.println("[+] Invalid day");
            }
        } while (day < 1 || day > 31);
        System.out.println("[!] YOU CAN INSERT MULTI CLASSES BY SPLITTING [,] SYMBOL (C1,C2).");
        System.out.print("[+] Student's class: ");
        String[] className = new Scanner(System.in).nextLine().trim().split(",");
        System.out.println("[!] YOU CAN INSERT MULTI SUBJECT BY SPLITTING [,] SYMBOL (S1,S2).");
        System.out.print("[+] Subject studied: ");
        String[] subjects = new Scanner(System.in).nextLine().trim().split(",");
        System.out.println("[!] TO STORE DATA PERMANENTLY, PLEASE COMMIT IT (START OPTION 3).");
        Student students = new  Student(id, name, year, month, day, className, subjects);
        list1.add(students);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath[0], true));
            for (Student student : list1) {
                    bufferedWriter.write(student.getId()
                            + "/" + student.getName()
                            + "/" + student.getYear()
                            + "/" + student.getMonth()
                            + "/" + student.getDay()
                            + "/" + Arrays.toString(student.getClasses())
                            + "/" + Arrays.toString(student.getSubjects())
                            + "\n");
                studentList.add(students);
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            list1.clear();
        } catch (Exception ignore) {}
    }
    @Override
    public void commitTransition() {
        try {
            if (!(Files.readAllLines(pathT).isEmpty())) {
                System.out.print("> Commit your [" + Files.readAllLines(pathT).size() + "] data record before hand [Y/N]: ");
                String commitT = new Scanner(System.in).nextLine();
                if (commitT.equalsIgnoreCase("y") || commitT.equalsIgnoreCase("yes")) {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath[1], true));
                    List<String> test = Files.readAllLines(pathT);
                    for (String test1 : test) {
                        bufferedWriter.write(test1 + "\n");
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    FileWriter fileWriter = new FileWriter(filePath[0]);
                    fileWriter.write("");
                } else {
                    if (commitT.equalsIgnoreCase("n") || commitT.equalsIgnoreCase("no")) {
                        FileWriter fileWriter = new FileWriter(filePath[0]);
                        fileWriter.write("");
                    } else {
                        System.out.println("Fail to commit data !");
                    }
                }
            }
        } catch (Exception ignore) {
        }
    }
    @Override
    public void showList() {
        int pageSize = 4;
        int currentPage = 1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int totalRecords = studentList.size();
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalRecords);
            Table table = new Table(6, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
            for (int i = 0; i < 6; i++) {
                table.setColumnWidth(i, 22, 22);
            }
            table.setColumnWidth(2, 25, 25);
            if (studentList.isEmpty()) {
                System.out.println("[!] NO DATA");
            } else {
                System.out.println("..........................");
                if (currentPage == totalPages) {
                    System.out.println("[!] LAST PAGE <<");
                }
                System.out.println("[*] STUDENT'S DATA");
                table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S DATE OF BIRTH", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S CLASS", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S SUBJECT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("CREATED/UPDATED AT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                for (int i = start; i < end; i++) {
                    Student list = studentList.get(i);
                    table.addCell(list.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(list.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(list.getYear() + "-" + list.getMonth() + "-" + list.getDay(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(Arrays.toString(list.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(Arrays.toString(list.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                    table.addCell(LocalDate.now().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                }
                System.out.println(table.render());
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("[*] Page Number : " + currentPage);
            System.out.print("       [*] Actual Record: " + (end - start));
            System.out.print("       [*] All Record: " + totalRecords);
            System.out.println("            [+] Previous (P/p) - [+] Next (N/n) - [+] Back (B/b)");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("[+] Insert to Navigate [p/N/b]: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("n") && currentPage < totalPages) {
                currentPage++;
            } else if (input.equals("p") && currentPage > 1) {
                currentPage--;
            } else if (input.equals("b")) {
                break;
            }
        }
    }
    @Override
    public void commit() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath[1], true));
            List<String> test = Files.readAllLines(pathT);
            for (String test1 : test) {
                bufferedWriter.write(test1 + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            FileWriter fileWriter = new FileWriter(filePath[0]);
            fileWriter.write("");
        } catch (Exception ignore) {
        }
    }
    @Override
    public void clearData() {
        try {
            FileWriter fileData = new FileWriter(filePath[1]);
            fileData.write("");
            FileWriter fileTransition = new FileWriter(filePath[0]);
            fileTransition.write("");
            list1.clear();
            studentList.clear();
            System.out.println(">> Clear data successfully !");
        } catch (Exception ignore) {
        }
    }
    @Override
    public void search() {
        System.out.println("......................................");
        System.out.println("[+] SEARCHING STUDENT");
        System.out.println("......................................");
        System.out.println("1. SEARCH BY NAME");
        System.out.println("2. SEARCH BY ID");
        System.out.println("- (BACK/B) TO BACK");
        System.out.println("......................................");
        System.out.print(">> Insert option: ");
        int option = new Scanner(System.in).nextInt();
        switch (option) {
            case 1 -> searchByName();
            case 2 -> searchByID();
        }
    }

    @Override
    public void searchByID() {
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        for (int i = 0; i < 2; i++) {
            table.setColumnWidth(i, 30, 30);
        }
        System.out.print("Searching by ID: ");
        String id = new Scanner(System.in).nextLine().trim();
        for(Student list:studentList) {
            if(id.equals(list.getId())) {
                table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(list.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(list.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S DATE OF BIRTH", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(list.getYear() + "-" + list.getMonth() + "-" + list.getDay(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S CLASS", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Arrays.toString(list.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S SUBJECT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Arrays.toString(list.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("CREATED/UPDATED AT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(LocalDate.now().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }else{
                System.out.println("ID not found!");
            }
            System.out.println(table.render());
        }
    }
    @Override
    public void searchByName() {
        Table table = new Table(6, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        for (int i = 0; i < 6; i++) {
            table.setColumnWidth(i, 30, 30);
        }
        boolean found = false;
        System.out.print("Student Name: ");
        String name = new Scanner(System.in).nextLine().toLowerCase().trim();
        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("STUDENT'S NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("STUDENT'S DATE OF BIRTH", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("STUDENT'S CLASS", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("STUDENT'S SUBJECT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("CREATED/UPDATED AT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        for(Student list:studentList) {
            if (list.getName().toLowerCase().contains(name))  {
                found = true;
                table.addCell(list.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(list.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(list.getYear() + "-" + list.getMonth() + "-" + list.getDay(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Arrays.toString(list.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Arrays.toString(list.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(LocalDate.now().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
            }
        }
        if(found){
            System.out.println(table.render());
        }else{
            System.out.println("Name not found !");
        }
    }
    @Override
    public void updateByID() {
         System.out.println("================================================");
         System.out.println("[+] UPDATE STUDENT'S INFORMATION !");
         System.out.println("------------------------------------------------");
         System.out.println("1. UPDATE STUDENT'S NAME");
         System.out.println("2. UPDATE STUDENT'S DATE OF BIRTH");
         System.out.println("3. UPDATE STUDENT'S CLASS");
         System.out.println("4. UPDATE STUDENT'S SUBJECT");
         System.out.println("................................................");
         System.out.print(">> Insert option: ");
         int option = new Scanner(System.in).nextInt();
         switch (option) {
             case 1 -> updateName();
             case 2 -> updateBirth();
             case 3 -> updateClass();
             case 4 -> updateSubject();
         }
    }

    @Override
    public void updateClass() {
        System.out.print("Searching by ID: ");
        String id = new Scanner(System.in).nextLine().trim();
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(filePath[0], true));
            for (Student list : studentList) {
                if (id.equals(list.getId())) {
                    System.out.print(">>Insert New Class: ");
                    list.setClasses(new Scanner(System.in).nextLine().trim().split(","));
                    printWriter.println(list.getId()
                            + "/" + list.getName()
                            + "/" + list.getYear()
                            + "/" + list.getMonth()
                            + "/" + list.getDay()
                            + "/" + Arrays.toString(list.getClasses())
                            + "/" + Arrays.toString(list.getSubjects())
                    );
                }
            }
            printWriter.close();
        }catch (Exception ignore){}
    }

    @Override
    public void updateSubject() {
        System.out.print("Searching by ID: ");
        String id = new Scanner(System.in).nextLine().trim();
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(filePath[0], true));
            for (Student list : studentList) {
                if (id.equals(list.getId())) {
                    System.out.print(">>Insert New Subject: ");
                    list.setClasses(new Scanner(System.in).nextLine().trim().split(","));
                    printWriter.println(list.getId()
                            + "/" + list.getName()
                            + "/" + list.getYear()
                            + "/" + list.getMonth()
                            + "/" + list.getDay()
                            + "/" + Arrays.toString(list.getClasses())
                            + "/" + Arrays.toString(list.getSubjects())
                    );
                }
            }
            printWriter.close();
        }catch (Exception  ignore) {}
    }

    @Override
    public void updateBirth() {
        System.out.print("Searching by ID: ");
        String id = new Scanner(System.in).nextLine().trim();
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(filePath[0], true));
            for (Student list : studentList) {
                if (id.equals(list.getId())) {
                    System.out.print(">>Insert New Year: ");
                    list.setYear(new Scanner(System.in).nextInt());
                    System.out.print(">>Insert New Month: ");
                    list.setMonth(new Scanner(System.in).nextInt());
                    System.out.print(">>Insert New Day: ");
                    list.setDay(new Scanner(System.in).nextInt());
                    printWriter.println(list.getId()
                            + "/" + list.getName()
                            + "/" + list.getYear()
                            + "/" + list.getMonth()
                            + "/" + list.getDay()
                            + "/" + Arrays.toString(list.getClasses())
                            + "/" + Arrays.toString(list.getSubjects())
                    );
                }
            }
            printWriter.close();
        }catch (Exception ignore) {}
    }

    @Override
    public void updateName(){
        System.out.print("Searching by ID: ");
        String id = new Scanner(System.in).nextLine().trim();
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(filePath[0], true));
            for(Student list : studentList) {
                if (id.equals(list.getId())) {
                    System.out.print(">>Insert New Name: ");
                    list.setName(new Scanner(System.in).nextLine().trim());
                    printWriter.println(list.getId()
                            +"/"+list.getName()
                            +"/"+list.getYear()
                            +"/"+list.getMonth()
                            +"/"+list.getDay()
                            +"/"+ Arrays.toString(list.getClasses())
                                +"/"+ Arrays.toString(list.getSubjects())
                    );
                }
            }
            printWriter.close();
        }catch (Exception ignore) {}
    }

    public void generateData() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("[+] How many records do you want to record: ");
        long num = scanner.nextLong();
        long start = System.currentTimeMillis();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath[0], true))) {
            for (long i = 0; i < num; i++) {
                String studentData = "100CSTAD"
                        + "/" + "Smos"
                        + "/" + "2004"
                        + "/" + "12"
                        + "/" + "1"
                        + "/" + Arrays.toString(new String[]{"DevOps", "Spring"})
                        + "/" + Arrays.toString(new String[]{"Java", "Python"})
                        + "/" + LocalDate.now()
                        + "\n";
                bufferedWriter.write(studentData);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        long finish = System.currentTimeMillis();
        double timeElapsed = (finish - start) / 1000.0;
        System.out.println("To generate " + num + " records took " + timeElapsed + "s");
        readFileToStudentList();
    }

    @Override
    public void deleted() {
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        for (int i = 0; i < 2; i++) {
            table.setColumnWidth(i, 30, 30);
        }
        System.out.print("Enter the ID to delete: ");
        String id = new Scanner(System.in).nextLine().trim();
        Student studentToDelete = null;
        for (Student student : studentList) {
            if (id.equals(student.getId())) {
                studentToDelete = student;
                table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(student.getId(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S NAME", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(student.getName(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S DATE OF BIRTH", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(student.getYear() + "-" + student.getMonth() + "-" + student.getDay(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S CLASS", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Arrays.toString(student.getClasses()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("STUDENT'S SUBJECT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(Arrays.toString(student.getSubjects()), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell("CREATED/UPDATED AT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
                table.addCell(LocalDate.now().toString(), new CellStyle(CellStyle.HorizontalAlign.CENTER));
                break;
            }
        }
        System.out.println(table.render());
        System.out.print("Are you sure you want to delete the data? [Y/n]: ");
        String option = new Scanner(System.in).nextLine().trim();
        if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("YES")) {
            studentList.remove(studentToDelete);
            System.out.println("Student deleted!");
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(filePath[0]));
                for (Student student : studentList) {
                    printWriter.println(student.getId()
                            +"/"+student.getName()
                            +"/"+student.getYear()
                            +"/"+student.getMonth()
                            +"/"+student.getDay()
                            +"/"+ Arrays.toString(student.getClasses())
                            +"/"+ Arrays.toString(student.getSubjects())
                    );
                }
                printWriter.close();
            }catch (Exception e) {}

        } else {
            System.out.println("Student has not been deleted!");
        }
    }

    public List<Student> readFileToStudentList() {
        try {
            long startTime = System.currentTimeMillis();
            for (String file:filePath) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("/");
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    Integer year = Integer.parseInt(parts[2].trim());
                    Integer month = Integer.parseInt(parts[3].trim());
                    Integer day = Integer.parseInt(parts[4].trim());
                    String[] classes = new String[]{parts[5]};
                    String[] subjects = new String[]{parts[6]};
                    Student student = new Student(id, name, year, month, day, classes, subjects);
                    studentList.add(student);
                }
            }
            long endTime = System.currentTimeMillis();
            double renderTime = (endTime - startTime) / 1000.0;
            System.out.println("[!] TOTAL TIME: " + renderTime + "s");
        } catch (Exception ignore) {}
        return studentList;
    }
}

