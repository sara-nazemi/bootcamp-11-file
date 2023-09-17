package org.example;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();

        Consumer<String> consumer = (l) -> {
            List<String> split = Stream.of(l.split(",")).map(String::trim).toList();
            Person person = new Person(Integer.parseInt(split.get(0)), split.get(1), split.get(2),
                    Integer.parseInt(split.get(3)), split.get(4), split.get(5),
                    split.get(6), split.get(7), Long.parseLong(split.get(8)), Boolean.parseBoolean(split.get(9)));
            persons.add(person);
        };

        readList("C:\\Users\\Windows 11\\Desktop\\My BootCamp Examples\\bootcamp-11-file\\file.csv", consumer);


        // total number of persons in the list

        System.out.println("count of persons: " + persons.size());

        System.out.println("//////////////////////////////////////");

        // list of person objects include only those where the age is between 25 and 32

        int sizeOfAge = persons.stream()
                .filter((p) -> p.getAge() <= 25 && p.getAge() <= 32)
                .toList()
                .size();
        System.out.println("size of person objects list include only those where the age is between 25 and 32 is : " + sizeOfAge);

        System.out.println("//////////////////////////////////////");

        // map the filtered list to a new list containing only the email addresses

        List<String> listOfEmails = persons.stream()
                .map((p) -> p.getEmail())
                .sorted()
                .toList();
        writeFile(listOfEmails);

        System.out.println("//////////////////////////////////////");

        // average salary of all person objects in the list

        double averageOfSalary = persons.stream()
                .mapToLong((p) -> p.getSalary())
                .average()
                .getAsDouble();
        System.out.println("average salary of all person objects is : " + averageOfSalary);

        System.out.println("//////////////////////////////////////");

        // person object with highest salary

        long maxSalary = persons.stream()
                .mapToLong((p) -> p.getSalary())
                .max()
                .getAsLong();


        Person objectMaxSalary = persons.stream()
                .filter((p) -> p.getSalary().equals(maxSalary))
                .findFirst()
                .get();
        System.out.println("person object with highest salary is : " + objectMaxSalary);

        System.out.println("//////////////////////////////////////");

        // count of person objects for each unique geography value

        Map<String, List<Person>> personsByGeography = persons.stream()
                .collect(Collectors.groupingBy(p -> p.getGeography()));

        Set<Map.Entry<String, List<Person>>> entries = personsByGeography.entrySet();
        for (Map.Entry<String, List<Person>> entry : entries) {
            System.out.println(entry.getKey() + " - person count is: " + entry.getValue().size());
        }

        System.out.println("//////////////////////////////////////");

        // number of males and females

        Map<String, Long> countByGender = persons.stream()
                .collect(Collectors.groupingBy(p -> p.getGender(), Collectors.counting()));
        Set<Map.Entry<String, Long>> entries2 = countByGender.entrySet();
        for (Map.Entry<String, Long> stringLongEntry : entries2) {
            System.out.println("gender : " + stringLongEntry.getKey() + " , count is : " + stringLongEntry.getValue());
        }


        System.out.println("//////////////////////////////////////");

        //only alive persons and calculate the total salary of those

        long sumOfAlivePersonSalary = persons.stream()
                .filter(p -> p.getIsAlive()==true)
                .mapToLong(p -> p.getSalary())
                .sum();

        System.out.println("total salary is : " + sumOfAlivePersonSalary);


        System.out.println("//////////////////////////////////////");

        // total salary for each unique profession

        Map<String, Long> sumSalaryByProfessions = persons.stream()
                .collect(Collectors.groupingBy(p -> p.getProfession(), Collectors.summingLong(p -> p.getSalary())));

        Set<Map.Entry<String, Long>> entries1 = sumSalaryByProfessions.entrySet();
        for (Map.Entry<String, Long> stringLongEntry : entries1) {
            System.out.println("profession : " + stringLongEntry.getKey() + " , sum salary is : " + stringLongEntry.getValue());
        }

    }

    public static void readList(String path, Consumer<String> consumer) {
        try (FileReader fileReader = new FileReader(path);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                consumer.accept(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void writeFile(List<String> emailList) {
        String path = "C:\\Users\\Windows 11\\Desktop\\My BootCamp Examples\\bootcamp-11-file\\PersonsEmailAddresses.txt";
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String email : emailList) {
                bufferedWriter.write(email);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();

        } catch (IOException e) {

            throw new RuntimeException();
        }

    }
}