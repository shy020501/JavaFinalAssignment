import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        File userInfo = new File("src/userInfo/");

        if(!userInfo.exists())
        {
            try {
                Files.createDirectories(Paths.get("src/userInfo/"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int[] size = {600, 800};
        String title = "Financial Ledger";
        new LogInFrame(size, title);
    }
}