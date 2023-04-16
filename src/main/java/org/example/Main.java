package org.example;

import org.example.enums.Education;
import org.example.enums.Sex;

import java.util.*;

import static org.example.enums.Sex.MAN;
import static org.example.enums.Sex.WOMAN;

public class Main {
    public static void main(String[] args) {
        Collection<Person> persons = generatePersons();

        long underAgedAmount = persons.stream().filter(person -> person.getAge() < 18).count();
        System.out.println("Underaged amount = " + underAgedAmount);

        List<String> conscriptNames = persons.stream()
                .filter(person -> person.getSex().equals(MAN))
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .toList();
        System.out.println("Conscript names:");
        for (String personFamily : conscriptNames) {
            System.out.println(personFamily);
        }

        List<Person> employable = persons.stream()
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getSex().equals(MAN) && person.getAge() <= 65 ||
                        person.getSex().equals(WOMAN) && person.getAge() <= 60)
                .sorted(new FamilyComparator())
                .toList();
        System.out.println("Employable:");
        for (Person person : employable) {
            System.out.println(person);
        }
    }

    private static Collection<Person> generatePersons() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10000000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        return persons;
    }
}
