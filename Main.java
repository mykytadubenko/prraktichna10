import java.util.Scanner;

class Main {
    static String[] usernames = new String[15];
    static String[] passwords = new String[15];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1 - Додати користувача\n2 - Видалити користувача\n3 - Аутентифікація користувача\n0 - Вийти");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addUser(scanner);
                case 2 -> removeUser(scanner);
                case 3 -> authUser(scanner);
                case 0 -> {
                    System.out.println("Вихід");
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз");
            }
        }
    }

    static void addUser(Scanner scanner) {
        try {
            System.out.print("Введіть ім'я користувача:");
            String username = scanner.nextLine();
            validUsername(username);

            System.out.print("Введіть пароль:");
            String password = scanner.nextLine();
            validPassword(password);

            for (int i = 0; i < usernames.length; i++) {
                if (usernames[i] == null) {
                    usernames[i] = username;
                    passwords[i] = password;
                    System.out.println("Користувача додано.");
                    return;
                }
            }
            throw new IllegalStateException("Перевищено максимальну кількість користувачів.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    static void removeUser(Scanner scanner) {
        System.out.print("Введіть ім'я користувача для видалення:");
        String username = scanner.nextLine();
        for (int i = 0; i < usernames.length; i++) {
            if (username.equals(usernames[i])) {
                usernames[i] = null;
                passwords[i] = null;
                System.out.println("Користувача видалено");
                return;
            }
        }
        System.out.println("Користувача не знайдено");
    }

    static void authUser(Scanner scanner) {
        System.out.print("Введіть ім'я користувача:");
        String username = scanner.nextLine();
        System.out.print("Введіть пароль:");
        String password = scanner.nextLine();

        for (int i = 0; i < usernames.length; i++) {
            if (username.equals(usernames[i]) && password.equals(passwords[i])) {
                System.out.println("Користувача успішно аутентифіковано");
                return;
            }
        }
        System.out.println("Невірне ім'я користувача або пароль");
    }

    static void validUsername(String username) {
        if (username.length() < 5 || username.contains(" ")) {
            throw new IllegalArgumentException("Ім'я має бути не менше 5 символів і без пробілів");
        }
    }

    static void validPassword(String password) {
        if (password.length() < 10 || password.contains(" ")) {
            throw new IllegalArgumentException("Пароль має містити щонайменше 10 символів і не мати пробілів");
        }
        int digitCount = 0;
        boolean specialsymbols = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
            if (!Character.isLetterOrDigit(c)) specialsymbols = true;
        }
        if (digitCount < 3 || !specialsymbols) {
            throw new IllegalArgumentException("Пароль має містити щонайменше 3 цифри та один спеціальний символ");
        }
        if (password.contains("admin") || password.contains("pass") ||
                password.contains("password") || password.contains("qwerty") ||
                password.contains("ytrewq")) {
            throw new IllegalArgumentException("Пароль містить заборонене слово");
        }
    }
}
