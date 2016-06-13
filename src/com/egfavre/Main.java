package com.egfavre;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //**add database functionallity
    public static void instertTodo(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos VALUES (NULL, ?, FALSE)");
        stmt.setString(1, text);
        stmt.execute();
    }

    public static ArrayList<ToDoItem> selectToDos (Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos");
        ResultSet results = stmt.executeQuery();
        ArrayList<ToDoItem> items = new ArrayList<>();
        while (results.next()){
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            ToDoItem item = new ToDoItem(id, text, isDone);
            items.add(item);
        }
        return items;
    }

    public static void toggleToDo(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE todos SET is_done = NOT is_done WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    //make methods for refactoring
    public static void createItem(Scanner scanner, Connection conn) throws SQLException {




        System.out.println("Enter your to do item:");
        String text = scanner.nextLine();
        //ToDoItem item = new ToDoItem(text, false);
        //items.add(item);
        instertTodo(conn, text);
    }

    public static void toggleItem(Scanner scanner, Connection conn) throws SQLException {
        System.out.println("Enter the number of the item you wish to toggle");
        String numStr = scanner.nextLine();
        try {
            int num = Integer.valueOf(numStr);
            //ToDoItem tempItem = items.get(num - 1);
            //tempItem.isDone = !tempItem.isDone;
            toggleToDo(conn, num);
        } catch (NumberFormatException e) {
            System.out.println("You didn't type a number.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Number isn't valid.");
        }
        //already in array list, do not need to add back
    }

    public static void listItems(Connection conn) throws SQLException {
        //int i = 1;
        //List items
        ArrayList<ToDoItem> items = selectToDos(conn);
        for (ToDoItem toDoItem : items) {
            String checkbox = "[ ]";
            if (toDoItem.isDone) {
                checkbox = "[x]";
            }
            //simplest way:System.out.println(toDoItem.text);
            //but we want index
           // System.out.println(checkbox + " " + i + ". " + toDoItem.text);
            System.out.printf("%s %s. %s\n", checkbox, toDoItem.id, toDoItem.text);
            //i++;
        }
    }

    public static void main(String[] args) throws SQLException {
        //**
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS todos (id IDENTITY, text VARCHAR, is_done BOOLEAN)");

        // no longer need this since made in set methods ArrayList<ToDoItem> items = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //create new item
        //toggle done
        //print current list

        //infinite loop
        while (true) {
            System.out.println("1. Create new Item");
            System.out.println("2. Toggle Item");
            System.out.println("3. List Items");

            //read
            String option = scanner.nextLine();

            //if or switch statment
            switch (option) {
                case "1":
                    //create new item
                    createItem(scanner, conn);
                    break;
                case "2":
                    //toggle item
                    toggleItem(scanner, conn);
                    break;
                case "3":
                    listItems(conn);
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
