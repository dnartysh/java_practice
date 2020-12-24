import core.BankStatement;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        BankStatement st = new BankStatement("/home/denis/Documents/JAVA/"
                + "SKILLBOX/java_basics/09_FilesAndNetwork/files/movementList.csv");

        st.printStatements();
    }

}
