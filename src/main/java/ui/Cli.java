package ui;

import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import domain.Course;

import java.util.List;
import java.util.Scanner;

public class Cli {
    Scanner scan;
    MyCourseRepository repo;

    public Cli(MyCourseRepository   repo) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() {
        String input = "-";
        while (!input.equals("x")) {
            showMenue();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Kurseingabe");
                    break;
                case "2":
                    showAllCourses();
                    break;
                case "x":
                    System.out.println("Programm wird beendet");
                    break;
                default:
                    inputError("");
                    break;
            }
        }
        scan.close();
    }

    private void showAllCourses() {
        List<Course> list = null;
        try{
        list = repo.getAll();

        if (list.size()>0){
            for (Course course : list) {
                System.out.println(course);
            }
        } else {
            System.out.println("Keine Kurse gefunden");
        }} catch(DatabaseException database) {
        System.out.println("Datenbankfehler bei Anzeige aller Kurse");
        } catch (Exception exception){
            System.out.println("Ein unbekannter Fehler ist aufgetreten");
        }
    }

    private void showMenue() {
        System.out.println("-------Kursmanagement-------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t");
        System.out.println("(x) ENDE");
    }
    
    private void inputError(String message) {
        System.out.println("Bitte nur die Zahlen oder x eingeben");
    }
}