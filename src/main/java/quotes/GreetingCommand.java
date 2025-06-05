package quotes;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@QuarkusMain // The @QuarkusMain annotation tells Quarkus that this is the main entry point
public class GreetingCommand implements QuarkusApplication {

    // https://myfear.substack.com/p/quarkus-native-cli-java-quotes?utm_source=post-email-title&publication_id=4194688&post_id=164473326&utm_campaign=email-post-title&isFreemail=true&r=1om7hi&triedRedirect=true&utm_medium=email

    @Override
    public int run(String... args) throws Exception {
        List<String> quotes = readQuotesFromCSV("quotes.csv"); // Loads the quotes.csv file from the classpath

        // Chooses a random quote
        if (!quotes.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(quotes.size());
            System.out.println("\n💬 " + quotes.get(randomIndex) + "\n");
        } else {
            System.out.println("No quotes found.");
        }

        return 0;
    }

    private List<String> readQuotesFromCSV(String fileName) {
        List<String> quotes = new ArrayList<>();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;

            while ((line = reader.readLine()) != null) {
                quotes.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return quotes;
    }
}

