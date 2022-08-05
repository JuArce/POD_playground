package ar.edu.itba.pod.j8.tp.tp1.ej2;

import ar.edu.itba.pod.j8.tp.tp1.ej2.model.Continent;
import ar.edu.itba.pod.j8.tp.tp1.ej2.model.Country;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Collection<Country> countries = Arrays.asList(
                new Country("Argentina", Continent.AMERICA, 43590400, LocalDate.parse("1816-07-09")),
                new Country("China", Continent.ASIA, 1377939144, null),
                new Country("Brazil", Continent.AMERICA, 206284591, LocalDate.parse("1822-09-07")),
                new Country("Nigeria", Continent.AFRICA, 189636000, LocalDate.parse("1960-10-01")),
                new Country("Japón", Continent.ASIA, 126675000, null),
                new Country("México", Continent.AMERICA, 122273000, LocalDate.parse("1810-09-16")),
                new Country("Turquía", Continent.ASIA, 79265000, LocalDate.parse("1923-10-29")),
                new Country("Australia", Continent.OCEANIA, 24117000, null),
                new Country("Chile", Continent.AMERICA, 18192000, LocalDate.parse("1810-09-18")),
                new Country("Bélgica", Continent.EUROPE, 11338000, LocalDate.parse("1930-10-04")),
                new Country("Portugal", Continent.EUROPE, 10262000, LocalDate.parse("1640-12-01")),
                new Country("Bolivia", Continent.AMERICA, 10985000, LocalDate.parse("1825-08-06")),
                new Country("Suecia", Continent.EUROPE, 9824000, LocalDate.parse("1523-06-06")),
                new Country("Israel", Continent.ASIA, 8391000, LocalDate.parse("1948-05-15")),
                new Country("Inglaterra", Continent.EUROPE, 53010000, null),
                new Country("Serbia", Continent.EUROPE, 7071000, LocalDate.parse("1804-02-15"))
        );

        /**
         * 1. Imprimir en pantalla todos los países
         */
        countries.forEach(System.out::println);

        /**
         * 2. Imprimir en pantalla a los países de América
         */
        countries.stream().filter(c -> c.getContinent().equals(Continent.AMERICA)).forEach(System.out::println);

        /**
         * 3. Crear una lista con los países de América
         */
        List<Country> america = countries.stream().filter(c -> c.getContinent().equals(Continent.AMERICA)).collect(Collectors.toList());

        /**
         * 4. Crear un set con los países que contengan en su nombre la letra ‘a’
         */
        countries.stream().filter(c -> c.getName().contains("a")).collect(Collectors.toSet());

        /**
         * 5. Crear una lista con los nombres de los países que contengan en su nombre la letra ‘a’
         */
        countries.stream().filter(c -> c.getName().contains("a")).collect(Collectors.toList());

        /**
         * 6. Crear un mapa (K, V) con K: continente y V: lista de países
         */
        Map<Continent, List<Country>> m = countries.stream().collect(Collectors.groupingBy(Country::getContinent));

        /**
         * 7. Crear un mapa (K, V) con K: continente y V: total de habitantes
         */
        Map<Continent, Long> m2 = countries.stream().collect(Collectors.groupingBy(Country::getContinent, Collectors.summingLong(Country::getPopulation)));

        /**
         * 8. Crear un mapa (K, V) con K: continente y V: promedio de habitantes
         */
        Map<Continent, Double> m3 = countries.stream().collect(Collectors.groupingBy(Country::getContinent, Collectors.averagingLong(Country::getPopulation)));


        /**
         * 9. Obtener un país que tenga una ‘a’ en su nombre y la cantidad de habitantes sea mayor a 1.000.000.000.
         *    En caso de no encontrar uno, devolver null
         */
        Country c1 = countries.stream().filter(c -> c.getName().contains("a") && c.getPopulation() > 1000000000).findFirst().orElse(null);

        /**
         * 10. Obtener el país con el mayor número de habitantes
         */
        Country max = countries.stream().max(Comparator.comparingLong(Country::getPopulation)).orElse(null);

        /**
         * 11. Listar todos los países cuya independencia fue en un año bisiesto
         */

        /**
         * 12. Armar un mapa que muestre la cantidad de países que se independizaron por siglo.
         *     Donde el siglo se puede escribir como 1800 o 19 (para toda independencia ocurrida en un año 18XX)
         */

    }

}
