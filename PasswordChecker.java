import java.util.Scanner;

public class PasswordChecker {

    public static void main(String[] args) {
        int c = 0;
        Scanner input = new Scanner(System.in);
        String pass;

        do {
            System.out.println("Password must have at least 8 characters.");
            System.out.println("Enter the password:");
            pass = input.nextLine();
        } while (pass.length() < 8);

        boolean u = pass.chars().anyMatch(Character::isUpperCase);
        if (u) c++;

        boolean l = pass.chars().anyMatch(Character::isLowerCase);
        if (l) c++;

        boolean d = pass.chars().anyMatch(Character::isDigit);
        if (d) c++;

        boolean sc = pass.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        if (sc) c++;

        if (c <= 1) System.out.println("weak");
        if (c == 2 || c == 3) System.out.println("medium");
        if (c == 4) System.out.println("Strong");

        input.close();
    }
}