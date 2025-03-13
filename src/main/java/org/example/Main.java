package org.example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("books.json")) {
            Type visitorListType = new TypeToken<List<Visitor>>() {}.getType();
            List<Visitor> visitors = gson.fromJson(reader, visitorListType);

            System.out.println("Задание 1");
            visitors.forEach(visitor -> System.out.println(visitor.getName() + " " + visitor.getSurname()));
            System.out.println("Общее количество поситителей: " + visitors.size());

            System.out.println("Задание 2");
            List<Book> uniqueBook = visitors.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .distinct()
                    .collect(Collectors.toList());
            uniqueBook.forEach(book -> System.out.println(" - " + book.getName()));
            System.out.println(("Общее количество уникальных книг: " + uniqueBook.size()));

            System.out.println("Задание 3");
            List<Book> sortBook = visitors.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .distinct()
                    .sorted(Comparator.comparing((Book::getPublishingYear))).toList();
            sortBook.forEach(book -> System.out.println(" - " + book.getName() +  "  (" + book.getAuthor() + ") " + book.getPublishingYear()));

            System.out.println("Задание 4");
            boolean isAuthorFavorite = visitors.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream()).anyMatch(book -> book.getAuthor().equalsIgnoreCase("Jane Austen"));
            System.out.println(isAuthorFavorite ? "Автор 'Jane Austen' есть!" : "Такого автора нет!");

            System.out.println("Задание 5");
            OptionalInt  maxFavoriteBooks = visitors.stream()
                    .mapToInt(visitor -> visitor.getFavoriteBooks().size()).max();
            maxFavoriteBooks.ifPresent(max -> System.out.println("Максимальное количество книг в избранных:  " + max));

            System.out.println("Задание 6");
            List<Visitor> subVisitors = visitors.stream()
                    .filter(Visitor::getSubscribed)
                    .collect(Collectors.toUnmodifiableList());
            double avgFavoriteBooks = subVisitors.stream()
                    .mapToInt(visitor -> visitor.getFavoriteBooks().size()).sum() / subVisitors.size();

            System.out.println("Среднее кол-во избранных книг:" + avgFavoriteBooks);

            List<SmsMessage> smsMessages = subVisitors.stream()
                    .map(visitor -> {
                        int bookcount = visitor.getFavoriteBooks().size();
                        String message = "";
                        if (bookcount > avgFavoriteBooks) {
                            message = "you are a bookworm";
                        } else if (bookcount < avgFavoriteBooks) {
                            message = "read more";
                        } else {
                            message = "fine";
                        }
                        return new SmsMessage(visitor.getPhone(), message);
                    }).collect(Collectors.toList());
            smsMessages.forEach(smsMessage -> System.out.println(" - " + smsMessage.toString()));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}