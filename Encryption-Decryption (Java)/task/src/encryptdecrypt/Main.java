package encryptdecrypt;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // decrypt("we found a treasure!");
        Scanner scanner = new Scanner(System.in);

        // String operation_type = scanner.nextLine();

        String mode = "enc";
        int key = 0;
        String data = "";
        String in = "";
        String out = "";
        String alg = "";

        // System.out.println(args.length);

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-mode")) {
                mode = args[i + 1];
            } else if (arg.equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
            } else if (arg.equals("-in")) {
                in = args[i + 1];
            } else if (arg.equals("-data")) {
                data = args[i + 1];
            }else if (arg.equals("-out")) {
                out = args[i + 1];
            }else if (arg.equals("-alg")) {
                alg = args[i + 1];
            }
        }

        if (!mode.equals("enc") && !mode.equals("dec")) {
            //System.out.println("Invalid mode. Mode should be 'enc' or 'dec'. Defaulting to 'enc'.");
            mode = "enc";
        }

        if (!alg.equals("shift") && !alg.equals("unicode")) {
            //System.out.println("Invalid mode. Mode should be 'enc' or 'dec'. Defaulting to 'enc'.");
            alg = "shift";
        }



        if (data.isEmpty()){
            try {
                File file = new File(in);
                Scanner file_input = new Scanner(file);

                while (file_input.hasNextLine()) {
                    data = file_input.nextLine();
                }

                file_input.close();

            } catch (Exception e) {
                System.out.println("Error");
                return;
            }
        }


        //Cipher caesar = new CipherFactory()
        if (alg.equals("shift")){
            Cipher caesar = CipherFactory.create('a', data, out, key);
            if (mode.equals("enc")) {
                //encrypt(data, key, out);
                caesar.encrypt(key, out);
            } else if (mode.equals("dec")) {
                caesar.decrypt(key, out);
            }
        }else if(alg.equals("unicode")){
            Cipher caesar = CipherFactory.create('c', data, out, key);
            if (mode.equals("enc")) {
                caesar.encrypt(key, out);
            } else if (mode.equals("dec")) {
                caesar.decrypt(key, out);
            }
        }


        // if (mode.equals("enc")) {
        //     //encrypt(data, key, out);
        //     caesar.encrypt(key, out);
        // } else if (mode.equals("dec")) {
        //     caesar.decrypt(key, out);
        // }




    }





}


/**
 *  CipherText
 */
abstract class  Cipher{

    String text;

    Cipher(String text) {
        this.text = text;
    }

    String getText() {
        return text;
    }

    abstract void encrypt(int shift, String out);
    abstract void decrypt(int shift, String out);

    public static String arrayListToString(ArrayList<Character> charList) {
        // Create a StringBuilder to efficiently concatenate characters
        StringBuilder sb = new StringBuilder(charList.size());

        // Append each character from the ArrayList to the StringBuilder
        for (char c : charList) {
            sb.append(c);
        }

        // Convert StringBuilder to String and return
        return sb.toString();
    }

    public static void file_output ( String out, String new_char) {
        File file = new File(out);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(new_char);
        } catch (IOException e) {
            System.out.println("Error");
            return;
        }
    }
}




class CaesarCipher extends Cipher {
    String out;
    int shift;


    CaesarCipher(String name, String out, int shift){
        super(name);
        this.out = out;
        this.shift = shift;
    }


    @Override
    void encrypt( int shift, String out) {
        String s = getText();
        // TODO Auto-generated method stub
        char [] clear_text_array = new char[s.length()];
        //System.out.println(s.length());

        ArrayList <Character> encoList = new ArrayList<>();


        for (int i = 0; i < s.length(); i++) {
            clear_text_array[i] = s.charAt(i);
            int ascii_value = (int) clear_text_array[i];

            if (ascii_value <= 126 && ascii_value >= 32 && out.isEmpty()){

                int old_position = ascii_value - 32;
                int new_position = (old_position + shift) % 95;
                char new_char = (char) (new_position + 32);
                System.out.print(new_char);
            }else if(ascii_value <= 126 && ascii_value >= 32 && !out.isEmpty()){
                int old_position = ascii_value - 32;
                int new_position = (old_position + shift) % 95;
                char new_char = (char) (new_position + 32);
                encoList.add(new_char);
            }

        }

        if(!out.isEmpty()){
            String encode_String = arrayListToString(encoList);
            file_output(out, encode_String);
        }

    }

    @Override
    void decrypt( int shift, String out) {
        String s = getText();

        char [] clear_text_array = new char[s.length()];

        ArrayList <Character> decoList = new ArrayList<>();


        for (int i = 0; i < s.length(); i++) {
            clear_text_array[i] = s.charAt(i);
            int ascii_value = (int) clear_text_array[i];

            if (ascii_value <= 126 && ascii_value >= 32 && out.isEmpty() ){
                int old_position = ascii_value - 32;
                int new_position = (95 + old_position - shift) % 95;
                char new_char = (char) (new_position + 32);
                System.out.print(new_char);
            }else if (ascii_value <= 126 && ascii_value >= 32 && !out.isEmpty()){
                int old_position = ascii_value - 32;
                int new_position = (95 + old_position - shift) % 95;
                char new_char = (char) (new_position + 32);
                decoList.add(new_char);
                //file_output(out, new_char);
                //file_output(out, new_char);
            }
        }

        if(!out.isEmpty()){
            String decod_String = arrayListToString(decoList);
            file_output(out, decod_String);
        }

    }
}








class CaesarCipherAlphabets extends Cipher {
    String out;
    int shift;


    CaesarCipherAlphabets(String name, String out, int shift){
        super(name);
        this.out = out;
        this.shift = shift;
    }


    @Override
    void encrypt( int shift, String out) {
        String s = getText();
        // TODO Auto-generated method stub
        char [] clear_text_array = new char[s.length()];
        //System.out.println(s.length());

        ArrayList <Character> encoList = new ArrayList<>();


        for (int i = 0; i < s.length(); i++) {
            clear_text_array[i] = s.charAt(i);
            int ascii_value = (int) clear_text_array[i];


            if (ascii_value <= 122 && ascii_value >= 97 && out.isEmpty()){
                int old_position = ascii_value - 97;
                int new_position = (old_position + shift) % 26;
                char new_char = (char) (new_position + 97);
                System.out.print(new_char);
            }else if(ascii_value <= 122 && ascii_value >= 97 && !out.isEmpty()){
                int old_position = ascii_value - 97;
                int new_position = (old_position + shift) % 26;
                char new_char = (char) (new_position +  97);
                encoList.add(new_char);
            }else if (ascii_value <= 90 && ascii_value >= 65 && out.isEmpty()){
                int old_position = ascii_value - 65;
                int new_position = (old_position + shift) % 26;
                char new_char = (char) (new_position + 65);
                System.out.print(new_char);
            }else if(ascii_value <= 90 && ascii_value >= 65 && !out.isEmpty()){
                int old_position = ascii_value - 65;
                int new_position = (old_position + shift) % 26;
                char new_char = (char) (new_position +  65);
                encoList.add(new_char);
            } else if(out.isEmpty()){
                System.out.print(s.charAt(i));
            }else if(!out.isEmpty()){
                encoList.add(s.charAt(i));
            }

        }

        if(!out.isEmpty()){
            String encode_String = arrayListToString(encoList);
            file_output(out, encode_String);
        }

    }

    @Override
    void decrypt( int shift, String out) {
        String s = getText();

        char [] clear_text_array = new char[s.length()];

        ArrayList <Character> decoList = new ArrayList<>();


        for (int i = 0; i < s.length(); i++) {
            clear_text_array[i] = s.charAt(i);
            int ascii_value = (int) clear_text_array[i];

            if (ascii_value <= 122 && ascii_value >= 97  && out.isEmpty() ){
                int old_position = ascii_value - 97;
                int new_position = (26 + old_position - shift) % 26;
                char new_char = (char) (new_position + 97);
                System.out.print(new_char);
            }else if (ascii_value <= 122 && ascii_value >= 97 && !out.isEmpty()){
                int old_position = ascii_value - 97;
                int new_position = (26 + old_position - shift) % 26;
                char new_char = (char) (new_position + 97);
                decoList.add(new_char);
                //file_output(out, new_char);
                //file_output(out, new_char);
            }else if (ascii_value <= 90 && ascii_value >= 65  && out.isEmpty() ){
                int old_position = ascii_value - 65;
                int new_position = (26 + old_position - shift) % 26;
                char new_char = (char) (new_position + 65);
                System.out.print(new_char);
            }else if (ascii_value <= 90 && ascii_value >= 65 && !out.isEmpty()){
                int old_position = ascii_value - 65;
                int new_position = (26 + old_position - shift) % 26;
                char new_char = (char) (new_position + 65);
                decoList.add(new_char);
                //file_output(out, new_char);
                //file_output(out, new_char);
            }
        }

        if(!out.isEmpty()){
            String decod_String = arrayListToString(decoList);
            file_output(out, decod_String);
        }

    }
}

/**
 * CipherFactory
 */


class CipherFactory {

    public static Cipher create(char type, String name,  String out, int shift) {
        // write your code here ...
        if (type == 'C' || type == 'c'){
            return new CaesarCipher(name, out, shift );
        } else if ( type == 'A' || type == 'a'){
            return new CaesarCipherAlphabets(name, out, shift);
        }
        return null;
    }
}