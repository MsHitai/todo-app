package com.example.todoapplication;

import com.example.todoapplication.interfaces.Menu;
import com.example.todoapplication.menu.ShowMenu;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);

        Menu menu = new ShowMenu();

        menu.display(scanner);

    }
}
