import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Need to finish
public class SaveManager {
//    public static void saveCompany(Team[] companies, String filepath) {
//        try {
//            File file = new File(filepath);
//            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//            FileWriter filewriter = new FileWriter(filepath);
//            String data = "[";
//            for (int i = 0; i < companies.length; i++) {
//                data += "{";
//                data += "\"name\":\"" + companies[i].getName() + "\",";
//                data += "\"id\":" + companies[i].getId() + ",";
//                data += "\"members\":[";
//                Member[] members = companies[i].getAllMembers();
//                for (int j = 0; j < members.length; j++) {
//                    data += "{";
//                    data += "\"name\":\"" + members[j].getName() + "\",";
//                    data += "\"id\":" + members[j].getId() + ",";
//                    data += "\"role\":\"" + members[j].getRole() + "\"";
//                    data += "}";
//                    if (j != members.length - 1) {
//                        data += ",";
//                    }
//                }
//                data += "]";
//                data += "}";
//                if (i != companies.length - 1) {
//                    data += ",";
//                }
//            }
//            data += "]";
//            filewriter.write(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void loadCompany(String filepath) {
//        try {
//            File file = new File(filepath);
//            Scanner filescanner = new Scanner(file);
//            String data = "";
//            while (filescanner.hasNextLine()) {
//                data += filescanner.nextLine();
//            }
//            filescanner.close();
//            for ()
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
