/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremanagement;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class BookStoreManagement {
    private ArrayList<Book> books;
    private ArrayList<Author> authors;
    private Scanner scanner;

    public BookStoreManagement() {
        books = loadData("book.dat");
        authors = loadData("author.dat");
        scanner = new Scanner(System.in);
    }

    private <T> ArrayList<T> loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private <T> void saveData(String filename, ArrayList<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayMenu() {
        System.out.println("HKT Book Store Management");
        System.out.println("1. Show the book list");
        System.out.println("2. Add new book");
        System.out.println("3. Update book");
        System.out.println("4. Delete book");
        System.out.println("5. Search book");
        System.out.println("6. Store data to file");
        System.out.println("7. Add new author");
        System.out.println("8. Update author");
        System.out.println("9. Delete author");
        System.out.println("10. Quit");
        System.out.print("Choose an option: ");
        
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    buildDataStructure();
                    break;
                case "1":
                    showBookList();
                    break;
                case "2":
                    addNewBook();
                    break;
                case "3":
                    updateBook();
                    break;
                case "4":
                    deleteBook();
                    break;
                case "5":
                    searchBook();
                    break;
                case "6":
                    storeData();
                    break;
                case "7":
                    addNewAuthor();
                    break;
                case "8":
                    updateAuthor();
                    break;
                case "9":
                    deleteAuthor();
                    break;
                case "10":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }           
    }

    private void buildDataStructure() {
        System.out.println("Data structure is built with classes Book and Author");
    }

    private void showBookList() {
        for (Book book : books) {
            System.out.printf("ISBN: %s, Title: %s, Price: %.2f, AuthorID: %s%n",
                    book.getISBN(), book.getTitle(), book.getPrice(), book.getAuthorID());
        }
    }

    private void addNewBook() {
        while (true) {
            System.out.print("Enter ISBN: ");
            String ISBN = scanner.nextLine();
            if (books.stream().anyMatch(b -> b.getISBN().equals(ISBN))) {
                System.out.println("Book ID already exists.");
                continue;
            }

            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter authorID: ");
            String authorID = scanner.nextLine();
            if (authors.stream().noneMatch(a -> a.getAuthorID().equals(authorID))) {
                System.out.println("Author does not exist.");
                continue;
            }

            books.add(new Book(ISBN, title, price, authorID));
            System.out.println("Book added successfully.");

            System.out.print("Do you want to add another book? (y/n): ");
            if (!scanner.nextLine().equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    private void updateBook() {
        System.out.print("Enter ISBN of the book to update: ");
        String ISBN = scanner.nextLine();
        Book book = books.stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElse(null);
        if (book == null) {
            System.out.println("Book does not exist.");
            return;
        }

        System.out.printf("Enter new title (leave blank to keep '%s'): ", book.getTitle());
        String title = scanner.nextLine();
        System.out.printf("Enter new price (leave blank to keep '%.2f'): ", book.getPrice());
        String price = scanner.nextLine();
        System.out.printf("Enter new authorID (leave blank to keep '%s'): ", book.getAuthorID());
        String authorID = scanner.nextLine();

        if (!title.isEmpty()) {
            book.setTitle(title);
        }
        if (!price.isEmpty()) {
            book.setPrice(Double.parseDouble(price));
        }
        if (!authorID.isEmpty() && authors.stream().anyMatch(a -> a.getAuthorID().equals(authorID))) {
            book.setAuthorID(authorID);
        } else if (!authorID.isEmpty()) {
            System.out.println("Author does not exist. Keeping old authorID.");
        }

        System.out.println("Book updated successfully.");
    }

    private void deleteBook() {
        System.out.print("Enter ISBN of the book to delete: ");
        String ISBN = scanner.nextLine();
        Book book = books.stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElse(null);
        if (book == null) {
            System.out.println("Book does not exist.");
            return;
        }

        System.out.printf("Are you sure you want to delete '%s'? (y/n): ", book.getTitle());
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            books.remove(book);
            System.out.println("Book deleted successfully.");
        }
    }

    private void searchBook() {
        System.out.print("Enter search text: ");
        String searchText = scanner.nextLine().toLowerCase();
        books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchText))
                .forEach(book -> System.out.printf("ISBN: %s, Title: %s, Price: %.2f, AuthorID: %s%n",
                        book.getISBN(), book.getTitle(), book.getPrice(), book.getAuthorID()));
    }

    private void storeData() {
        saveData("book.dat", books);
        saveData("author.dat", authors);
        System.out.println("Data stored successfully.");
    }
    
    private void addNewAuthor() {
        System.out.print("Enter author ID: ");
        String authorID = scanner.nextLine();
        if (authors.stream().anyMatch(a -> a.getAuthorID().equals(authorID))) {
            System.out.println("Author ID already exists.");
            return;
        }

        System.out.print("Enter author name: ");
        String name = scanner.nextLine();

        authors.add(new Author(authorID, name));
        System.out.println("Author added successfully.");
    }
    private void updateAuthor() {
        System.out.print("Enter author ID to update: ");
        String authorID = scanner.nextLine();

        Author author = authors.stream().filter(a -> a.getAuthorID().equals(authorID)).findFirst().orElse(null);
        if (author == null) {
            System.out.println("Author does not exist.");
            return;
        }

        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            author.setName(name);
        }

        System.out.println("Author updated successfully.");
    }

    private void deleteAuthor() {
        System.out.print("Enter author ID to delete: ");
        String authorID = scanner.nextLine();

        Author author = authors.stream().filter(a -> a.getAuthorID().equals(authorID)).findFirst().orElse(null);
        if (author == null) {
            System.out.println("Author does not exist.");
            return;
        }

        System.out.print("Are you sure you want to delete this author? (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            authors.remove(author);
            System.out.println("Author deleted successfully.");
        } else {
            System.out.println("Author deletion cancelled.");
        }
    }
    public static void main(String[] args) {
        new BookStoreManagement().run();
    }  
}

    
