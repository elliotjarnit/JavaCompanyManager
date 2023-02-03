import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

// Need to finish
public class SaveManager {
    public static void saveCompany(Team[] companies, String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (int i = 0; i < companies.length; i++) {
                objectOut.writeObject(companies[i]);
            }
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Team[] loadCompany(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Team> companies = new ArrayList<Team>();
            // Get companies from file
            while (true) {
                try {
                    Team company = (Team) objectIn.readObject();
                    companies.add(company);
                } catch (Exception ex) {
                    break;
                }
            }
            objectIn.close();
            System.out.println("The Object has been read from the file");

            // Convert ArrayList to array
            Team[] companiesArray = new Team[companies.size()];
            for (int i = 0; i < companies.size(); i++) {
                companiesArray[i] = companies.get(i);
            }
            return companiesArray;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
