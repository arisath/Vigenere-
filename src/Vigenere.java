import java.io.*;
public class Vigenere
{
    //Key used to encrypt the plaintext
    static String key = "crypto";

    //Line separator depends on the platform the program is running
    final static String eol = System.getProperty("line.separator");

    public static void main(String[] args)
    {
        try
        {
            char[] fileContents = processFile("deciphered.txt");
            char[] keyContents = processKey(key);
            int[] plaintextInts = convertStringToStreamOfInts(fileContents);
            int[] keyInts = convertStringToStreamOfInts(keyContents);
            int[] vigener = Vigenere(keyInts, plaintextInts);
            char[] ciph = convertStreamOfIntsToString(vigener);
            printCiphertext(ciph,"ciphertext.txt");
///////////////////////////////////////////////
            char[] ciphertextContents = processFile("ciphertext.txt");
            int[] ciphertextInts = convertStringToStreamOfInts(ciphertextContents);
            int[] vigenerCiphertext = vigenereDecrytpt(keyInts, ciphertextInts);
            char[] plain = convertStreamOfIntsToString(vigenerCiphertext);
            printPlaintext(plain,"plaintext2.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Transforms the input file to the appropriate format required for the Vigenere algorithm
     * Capitalises all the alphabetical characters
     *  Removes all the non alphabetical characters
     * @param filename The name of the text file to be encrypted
     * @return An array of characters containing the alphabetical characters of the text file
     */
    protected static char[] processFile(String filename)
    {
        try
        {
            String fileContents = fileToString(filename);

            fileContents = fileContents.toUpperCase();

            fileContents = removeNonAlphabetical(fileContents);

            char[] plain = fileContents.toCharArray();

            return plain;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method converting the input encryption key to the appropriate format required for the Vigenere algorithm
     * Capitalises all the alphabetical characters
     * Removes all the non alphabetical characters
     * @param key The input key
     * @return The input key in the appropriate format required for the Vigenere algorithm
     */
    protected static char[] processKey(String key)
    {
        key = key.toUpperCase();

        key = removeNonAlphabetical(key);

        char[] keyContents = key.toCharArray();

        return keyContents;
    }

    /**
     * Reading a file from the disc and storing its contents into a String
     * Removes the byte order mark (BOM) character if exists
     * @param filename The name of the file to be read
     * @return A string containing the contents of the file
     */
    protected static String fileToString(String filename)
    {
        try
        {
            FileReader input = new FileReader(filename);

            BufferedReader br = new BufferedReader(input);

            String fileContents = "";

            String line;

            while ((line = br.readLine()) != null)
            {
                fileContents = fileContents + line + eol;
            }

            br.close();

            fileContents = fileContents.replace("\uFEFF", "");

            return fileContents;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File " + filename + " not found");
        }
        catch (IOException e)
        {
            System.out.println("IO Exception");
        }

        return null;
    }

    /**
     * Process a String, replacing all non alphabetical characters
     *
     * @param unprocessedString The String to be processed
     * @return The input String stipped off any non-alphabetical characters
     */
    protected static String removeNonAlphabetical(String unprocessedString)
    {
        unprocessedString = unprocessedString.replaceAll("\\W", "");

        unprocessedString = unprocessedString.replaceAll("[0-9]", "");

        return unprocessedString;
    }

    /**
     * Process a String, capitalising all the included characters
     *
     * @param line The String to be capitalised
     * @return The input String capitalised
     */
    protected static String capitaliseFile(String line)
    {
        return line.toUpperCase();
    }

    /**
     * Encrypts a plaintext using the Vigenere crypto-algorithm
     *
     * @param key An array of ints representing they key to be used
     * @param plaintext A array of ints containing the plaintext to be encrypted
     * @return An array of ints containing the ciphertext
     */
    protected static int[] Vigenere(int[] key, int[] plaintext)
    {
        int[] ciphertext = new int[plaintext.length];

        try
        {
            int i = 0;

            while (i < plaintext.length)
            {
                for (int j = 0; j < key.length; j++)
                {
                    ciphertext[i] = plaintext[i] + key[j];

                    if (ciphertext[i] > 25)
                    {
                        ciphertext[i] = ciphertext[i] % 26;
                    }

                    i++;

                    if (i == (plaintext.length))
                    {
                        break;
                    }
                }
            }
            return (ciphertext);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts the alphabetical characters of an array to their corresponding numerical representation
     * The transformation is performed by mapping a to 0, b to 1,..., z to 25
     * @param plaintext The array of alphabetical to be converted to their numerical representation 
     * @return The array containing the numerical represantations of the input array with alphabetical values
     */
    protected static int[] convertStringToStreamOfInts(char[] plaintext)
    {
        int[] streamOfInts = new int[plaintext.length];

        int correspondingInt;

        for (int i = 0; i < plaintext.length; i++)
        {
            if (Character.isLetter(plaintext[i]))
            {
                correspondingInt = ((int) plaintext[i]) - 65;

                streamOfInts[i] = correspondingInt;
            }
            else
            {
                streamOfInts[i] = plaintext[i];
            }
        }
        return streamOfInts;
    }

    /**
     * Converts a an array of ints to an array of characters by replacing its int with its corresponding character
     * The transformation is performed by mapping 0 to a, 1 to b,..., 25 to z
     * @param ciphertext
     * @return
     */
    protected static char[] convertStreamOfIntsToString(int[] ciphertext)
    {
        char[] chararray = new char[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++)
        {
            chararray[i] = (char) (ciphertext[i] + 65);
        }
        return chararray;
    }
    
    protected static void printCiphertext(char[] ciphertextChars, String filename)
    {
        try
        {
            PrintWriter writer = new PrintWriter(filename);
            
            String plaintext = fileToString("deciphered.txt");
            
            String ciphertext = "";
            
            int j = 0;
            
            for (int i = 0; i < plaintext.length(); i++)
            {
                if (Character.isLetter(plaintext.charAt(i)))
                {
                    ciphertext = ciphertext + ciphertextChars[j];
                    
                    j++;
                }
                else
                {
                    ciphertext = ciphertext + plaintext.charAt(i);
                }
            }
            writer.print(ciphertext);
            
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
    protected static int[] vigenereDecrytpt(int[] key, int[] ciphertext)
    {
        int[] plaintext = new int[ciphertext.length];
        
        try
        {
            int i = 0;
            
            while (i < ciphertext.length)
            {
                for (int j = 0; j < key.length; j++)
                {
                    plaintext[i] = ciphertext[i] - key[j];
                    
                    plaintext[i] = (plaintext[i] + 26) % 26;
                    
                    i++;
                    
                    if (i == (plaintext.length))
                    {
                        break;
                    }
                }
            }
            return (plaintext);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    protected static void printPlaintext(char[] plaintextChars, String filename)
    {
        try
        {
            PrintWriter writer = new PrintWriter(filename);
            
            String ciphertext = fileToString("ciphertext.txt");
            
            String plaintext = "";
            
            int j = 0;
            
            for (int i = 0; i < ciphertext.length(); i++)
            {
                if (Character.isLetter(ciphertext.charAt(i)))
                {
                    plaintext = plaintext + plaintextChars[j];
                    
                    j++;
                }
                else
                {
                    plaintext = plaintext + ciphertext.charAt(i);
                }
            }
            
            writer.print(plaintext);
            
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
}