import java.util.Scanner;

abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    double area() {
        return length * width;
    }
}

class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    double area() {
        return 0.5 * base * height;
    }
}

public class ShapeDemo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of shapes: ");
        int n = sc.nextInt();

        Shape[] shapes = new Shape[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nChoose Shape " + (i + 1));
            System.out.println("1. Circle");
            System.out.println("2. Rectangle");
            System.out.println("3. Triangle");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter radius: ");
                    double radius = sc.nextDouble();
                    shapes[i] = new Circle(radius);
                    break;

                case 2:
                    System.out.print("Enter length: ");
                    double length = sc.nextDouble();

                    System.out.print("Enter width: ");
                    double width = sc.nextDouble();

                    shapes[i] = new Rectangle(length, width);
                    break;

                case 3:
                    System.out.print("Enter base: ");
                    double base = sc.nextDouble();

                    System.out.print("Enter height: ");
                    double height = sc.nextDouble();

                    shapes[i] = new Triangle(base, height);
                    break;

                default:
                    System.out.println("Invalid choice! Circle selected by default.");
                    System.out.print("Enter radius: ");
                    radius = sc.nextDouble();
                    shapes[i] = new Circle(radius);
            }
        }

        double totalArea = 0;
        double largestArea = 0;

        System.out.println("\n----- Areas of Shapes -----");

        for (Shape s : shapes) {

            double area = s.area();

            System.out.printf("%s Area = %.2f%n",
                    s.getClass().getSimpleName(), area);

            totalArea += area;

            if (area > largestArea) {
                largestArea = area;
            }
        }

        System.out.printf("%nTotal Area = %.2f%n", totalArea);
        System.out.printf("Largest Area = %.2f%n", largestArea);

        sc.close();
    }
}