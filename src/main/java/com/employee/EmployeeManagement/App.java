package com.employee.EmployeeManagement;

import java.util.Scanner;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 */
public class App {
	static SessionFactory factory=null;
	static Scanner scan=new Scanner(System.in);
    public static void main(String[] args) {
        factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        while (true) {
			System.out.println("WELCOME TO EMPLOYEE MANAGEMENT SYSTEM");
			System.out.println("PRESS 1 for Fetching the employee");
			System.out.println("PRESS 2 for Adding new employee");
			System.out.println("PRESS 3 for Updating exisiting employee");
			System.out.println("PRESS 4 for Removing the employee");
			System.out.println("PRESS any other number to STOP!");
			int choice=scan.nextInt();
			switch (choice) {
			case 1 -> getEmployee();
			case 2 -> insertEmployee();
			case 3 -> updateEmployee();
			case 4 -> deleteEmployee();
			default -> {
				factory.close();
				scan.close();
				System.out.println("THANK YOU FOR USING EMPLOYEE MANAGEMENT SYSTEM");
				return;
			}
			
			}
		}
    }
    public static void getEmployee() {
    	System.out.println("Enter employee id to fetch the employee");
    	int id=scan.nextInt();
    	Session session=factory.openSession();
    	Transaction transaction=session.beginTransaction();
    	Employee obj=session.get(Employee.class, id);
    	if(obj!=null) {
    		System.out.println("ID: "+obj.getId()+" Name: "+obj.getName()+" Salary: "+obj.getSalary()+" Email: "+obj.getEmail()+" Phone: "+obj.getPhone());
    	}else {
    		System.out.println("Employee with id "+id+" doesn't exist");
    	}
    }
    
    public static void updateEmployee() {
    	System.out.println("Enter the Employee id to updated");
    	int id=scan.nextInt();
    	Session session=factory.openSession();
    	Transaction transaction=session.beginTransaction();
    	Employee obj=session.get(Employee.class, id);
    	if(obj!=null) {
    		System.out.println("Current Details");
    		System.out.println("ID: "+obj.getId()+" Name: "+obj.getName()+" Salary: "+obj.getSalary()+" Email: "+obj.getEmail()+" Phone: "+obj.getPhone());
    		System.out.println("Select one of the below to change");
    		System.out.println("================================");
    		System.out.println("name | salary | email | phone");
    		String choice=scan.next();
    		switch (choice) {
    		case "name" ->{
    			System.out.println("Enter the New name");
    			scan.nextLine();
    			String newName=scan.nextLine();
    			obj.setName(newName);
    		}
    		case "salary"->{
    			System.out.println("Enter the New salary");
    			int newSalary=scan.nextInt();
    			obj.setSalary(newSalary);
    		}
    		case "email" ->{
    			System.out.println("Enter the New email");
    			String newEmail=scan.next();
    			obj.setEmail(newEmail);
    		}
    		case "phone"->{
    			System.out.println("Enter the New phonenumber");
    			String newphone=scan.next();
    			obj.setPhone(newphone);
    		}

    		default ->{
    			System.out.println("No proper attribute selected hence no change can be made");
    			String name=scan.next();
    			obj.setName(name);
    		}
				
			}
    		Employee updated=session.merge(obj);
    		transaction.commit();
    		System.out.println("Updated Employee Details");
    		System.out.println("ID: "+updated.getId()+" Name: "+updated.getName()+" Salary: "+updated.getSalary()+" Email: "+updated.getEmail()+" Phone: "+updated.getPhone());
    		session.close();
    	}else {
    		System.out.println("Employee with id "+id+" doesn't exist");
    	}
    }
    
    public static void insertEmployee() {
    	System.out.println("Enter Employee Name,Salary,email,phone");
    	scan.nextLine();
    	Employee employee=new Employee(scan.nextLine(), scan.nextInt(),scan.next(),scan.next());
    	Session session=factory.openSession();
    	Transaction transaction=session.beginTransaction();
    	session.persist(employee);
    	transaction.commit();
    	session.close();
    }
    
    public static void deleteEmployee() {
    	System.out.println("Enter the Employee id to Deleted");
    	int id=scan.nextInt();
    	Session session=factory.openSession();
    	Transaction transaction=session.beginTransaction();
    	Employee obj=session.get(Employee.class, id);
    	if(obj!=null) {
    		session.remove(obj);
    		transaction.commit();
    		session.close();
    		System.out.println("Employee with id "+id+" removed");
    	}else {
    		System.out.println("Employee with id "+id+" doesn't exist");
    	}
	}
}
