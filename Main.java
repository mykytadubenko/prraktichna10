import java.util.Scanner;

class Main {
    static String[] usernames = new String[15];
    static String[] passwords = new String[15];
    static int userCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1 - Додати користувача");
            System.out.println("2 - Видалити користувача");
            System.out.println("3 - Аутентифікація користувача");
            System.out.println("0 - Вихід");
            System.out.print("Оберіть дію: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: addUser(scanner); break;
                    case 2: deleteUser(scanner); break;
                    case 3: authUser(scanner); break;
                    case 0: running = false; break;
                    default: System.out.println("Невірний вибір, спробуйте ще раз");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть число від 1 до 4");
            } catch (NullPointerException e) {
                System.out.println("Спробуйте ще раз");
            }
        }
        scanner.close();
    }

    static void addUser(Scanner scanner) {
        if (userCount >= 15) {
            System.out.println("Максимальна кількість користувачів досягнута");
            return;
        }

        try {
            System.out.print("Введіть ім'я користувача: ");
            String username = scanner.nextLine();
            if (!ValidUsername(username)) throw new IllegalArgumentException("Ім'я користувача не відповідає вимогам");

            System.out.print("Введіть пароль: ");
            String password = scanner.nextLine();
            if (!ValidPassword(password)) throw new IllegalArgumentException("Пароль не відповідає вимогам");

            usernames[userCount] = username;
            passwords[userCount] = password;
            userCount++;
            System.out.println("Користувача успішно додано");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    static void deleteUser(Scanner scanner) {
        System.out.print("Введіть ім'я користувача для видалення: ");
        String username = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                usernames[i] = usernames[userCount - 1];
                passwords[i] = passwords[userCount - 1];
                usernames[userCount - 1] = null;
                passwords[userCount - 1] = null;
                userCount--;
                System.out.println("Користувача видалено");
                return;
            }
        }
        System.out.println("Помилка: користувача не знайдено");
    }

    static void authUser(Scanner scanner) {
        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                System.out.println("Користувача аутентифіковано");
                return;
            }
        }
        System.out.println("Помилка: невірне ім'я користувача або пароль");
    }

    static boolean ValidUsername(String username) {
        if (username.length() < 5 || username.contains(" ")) return false;
        return true;
    }

    static boolean ValidPassword(String password) {
        if (password.length() < 10 || password.contains(" ")) return false;

        int digitCount = 0;
        boolean SpecialChar = false;
        String[] Words = {"admin", "pass", "password", "qwerty", "ytrewq"};

        for (String word : Words) {
            if (password.toLowerCase().contains(word)) return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isDigit(ch)) digitCount++;
            if (!Character.isLetterOrDigit(ch)) SpecialChar = true;
        }

        return digitCount >= 3 && SpecialChar;
    }
}
