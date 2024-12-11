package ui;

import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import domain.Course;

import java.util.List;
import java.util.Optional;
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
                case "3":
                    showCourseDetails();
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

    private void showCourseDetails() {
    System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen?");
    Long courseId = Long.parseLong(scan.nextLine());
    try {
        Optional<Course> courseOptional = repo.getById(courseId);
        if (courseOptional.isPresent()) {
            System.out.println(courseOptional.get());
        } else {
            System.out.println("Kurs mit der ID " + courseId + " wurde nicht gefunden");
        }
    }
    catch(DatabaseException databaseException){
    System.out.println("Datenbankfehler bei Kurs-Detailsanzeige: " + databaseException.getMessage());
    }
    catch (Exception exception){
    System.out.println("Ein unbekannter Fehler ist aufgetreten: " + exception.getMessage());
    }
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
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t" +" (3) Kursdetails anzeigen");
        System.out.println("(x) ENDE");
    }
    
    private void inputError(String message) {
        System.out.println("Bitte nur die Zahlen oder x eingeben");
    }
}