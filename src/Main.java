import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        File userInfo = new File("src/userInfo/");

        if(!userInfo.exists()) // For the first launching of the program, userInfo directory wouldn't exist
        {
            try {
                Files.createDirectories(Paths.get("src/userInfo/")); // Create userInfo file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int[] size = {600, 800};
        String title = "Financial Ledger";
        new LogInFrame(size, title);
    }
}