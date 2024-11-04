package sk.uniza.fri.aplikacia;

import java.util.ArrayList;
import java.util.Random;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class IDGenerator {
    private ArrayList<String> generatedIDS;

    public IDGenerator() {
        this.generatedIDS = new ArrayList<>();
    }

    public String generateUniqueID() {
        Random rand = new Random();
        char[] ID = new char[10];
        String generatedID = "";
        for (int i = 0; i < 10; i++) {
            generatedID += this.generujRandomChar();
        }


        while (this.generatedIDS.contains(generatedID)) {
            generatedID = "";
            for (int i = 0; i < 10; i++) {
                generatedID += this.generujRandomChar();
            }
        }
        return generatedID;

    }

    private char generujRandomChar () {
        Random rand = new Random();
        return (char) (rand.nextInt(25) + 65);
    }

}
