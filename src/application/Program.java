package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();
			List<Employee> list = new ArrayList<>();

			while (line != null) {
				String[] fields = line.split(",");

				String name = fields[0];
				String email = fields[1];
				Double salary = Double.parseDouble(fields[2]);

				list.add(new Employee(name, email, salary));

				line = br.readLine();
			}

			System.out.print("Enter salary: ");
			double salaryFilter = sc.nextDouble();

			Comparator<Employee> comp = (s1, s2) -> s1.getEmail().toUpperCase().compareTo(s2.getName());

			List<Employee> ordedList = list.stream().sorted(comp).filter(e -> e.getSalary() > salaryFilter)
					.collect(Collectors.toList());

			double sumSalary = list.stream().filter(e -> e.getName().toUpperCase().charAt(0) == 'M')
					.map(e -> e.getSalary()).reduce(0.0, (x, y) -> x + y);

			System.out.println("Email of people whose salary is more than " + String.format("%.2f", salaryFilter));

			ordedList.forEach(System.out::println);

			System.out
					.println("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", sumSalary));

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		sc.close();
	}

}
