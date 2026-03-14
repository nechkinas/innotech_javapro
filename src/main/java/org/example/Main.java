package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static class Emploer {
        private String name;
        private int age;
        private Worker worker;

        public Emploer(String name, int age, Worker worker) {
            this.name   = name;
            this.age    = age;
            this.worker = worker;
        }

        public int getAge() {
            return age;
        }
    }
    
    enum Worker{
        Ingener, GlavBuh
    }

    public static void main(String[] args) {
        //Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
        Stream<Integer> integerStream = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        System.out.println(integerStream.sorted(Comparator.reverseOrder()).limit(3).toList().getLast());

        //Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9, в отличие от прошлой задачи здесь разные 10 считает за одно число)
        Stream<Integer> integerStream2 = Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        System.out.println(integerStream2.distinct().sorted(Comparator.reverseOrder()).limit(3).toList().getLast());

        //Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
        Stream<Emploer> empStream = Stream.of(
                new Emploer("e1",20, Worker.Ingener),
                new Emploer("e2",22, Worker.Ingener),
                new Emploer("e3",23, Worker.Ingener),
                new Emploer("e4",24, Worker.GlavBuh),
                new Emploer("e5",24, Worker.Ingener)
        );
        System.out.println(empStream
                .filter(emploer -> emploer.worker == Worker.Ingener)
                .sorted(Comparator.comparing(Emploer::getAge ).reversed() )
                .limit(3)
                .map(emploer -> emploer.name)
                .toList());


        //Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер»
        Stream<Emploer> empStream2 = Stream.of(
                new Emploer("e1",20, Worker.Ingener),
                new Emploer("e2",22, Worker.Ingener),
                new Emploer("e3",23, Worker.Ingener),
                new Emploer("e4",24, Worker.GlavBuh),
                new Emploer("e5",24, Worker.Ingener)
        );
        System.out.println(empStream2
                .filter(emploer -> emploer.worker == Worker.Ingener)
                .mapToInt(emploer -> emploer.age)
                .average()
                .orElse(0.0));

        // Найдите в списке слов самое длинное
        Stream<String> strStream = Stream.of("Hellow", "World","I","See","World");
        System.out.println(strStream.max(Comparator.comparing(String::length)).orElse(""));


        //  · Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
        String str = "hellow world i see world";
        Stream<String> strStream2 = Stream.of(str.split(" "));
        HashMap<String, Long> map = (HashMap<String, Long>) strStream2.collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println(map);

        // Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
        Stream<String> strStream3 = Stream.of("hellow world","i see world", "and", "sky");
        System.out.println(strStream3
                .sorted()
                .sorted(Comparator.comparing(String::length) )
                .toList()
        );

        // Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом, найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
        Stream<String> strStream4 = Stream.of("11 22 33 44 55","111 222 333 444 555", "1111 2222 longstring 4444 5555", "11111 22222 33333 44444 55555");
        System.out.println(strStream4
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .sorted(Comparator.comparing(String::length))
                .toList()
                .getLast()
        );

    }
}