package com.egfavre;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //make methods for refactoring
    public static void createItem(Scanner scanner, ArrayList<ToDoItem> items) {
        System.out.println("Enter your to do item:");
        String text = scanner.nextLine();
        ToDoItem item = new ToDoItem(text, false);
        items.add(item);
    }

    public static void toggleItem(Scanner scanner, ArrayList<ToDoItem> items) {
        System.out.println("Enter the number of the item you wish to toggle");
        String numStr = scanner.nextLine();
        try {
            int num = Integer.valueOf(numStr);
            ToDoItem tempItem = items.get(num - 1);
            tempItem.isDone = !tempItem.isDone;
        } catch (NumberFormatException e) {
            System.out.println("You didn't type a number.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Number isn't valid.");
        }
        //already in array list, do not need to add back
    }

    public static void listItems(ArrayList<ToDoItem> items) {
        int i = 1;
        //List items
        for (ToDoItem toDoItem : items) {
            String checkbox = "[ ]";
            if (toDoItem.isDone) {
                checkbox = "[x]";
            }
            //simplest way:System.out.println(toDoItem.text);
            //but we want index
           // System.out.println(checkbox + " " + i + ". " + toDoItem.text);
            System.out.printf("%s %s. %s\n", checkbox, i, toDoItem.text);
            i++;
        }
    }

    public static void main(String[] args) {
        ArrayList<ToDoItem> items = new ArrayList<>();
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
                    createItem(scanner, items);
                    break;
                case "2":
                    //toggle item
                    toggleItem(scanner, items);
                    break;
                case "3":
                    listItems(items);
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
