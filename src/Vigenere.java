import java.io.*;

/**
 * Created by aris on 7/1/2016.
 */
public class Vigenere
{
    static String key = "crypto";

    final static String eol = System.getProperty("line.separator");

    public static void main(String[] args)
    {
        try
        {
            char[] fileContents = processFile("deciphered.txt");

            char[] keyContents = processKey(key);

            int[] plaintextInts = convertStringToStreamOfInts(fileContents);

            int[] keyInts = convertStringToStreamOfInts(keyContents);

            System.out.println();

            int[] vigener = Vigenere(keyInts, plaintextInts);

            System.out.println(convertStreamOfIntsToString(vigener));


            //     Vigenere(plaintextInts);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected static char[] processFile(String filename)
    {
        try
        {
            String fileContents = fileToString("deciphered.txt");

            fileContents = capitaliseFile(fileContents);

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

    protected static char[] processKey(String key)
    {
        key = capitaliseFile(key);

        key = removeNonAlphabetical(key);

        char[] keyContents = key.toCharArray();

        return keyContents;
    }

    protected static String fileToString(String filename)
    {
        try
        {
            FileReader input = new FileReader("deciphered.txt");

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

    protected static String removeNonAlphabetical(String unprocessedString)
    {
        unprocessedString = unprocessedString.replaceAll("\\W", "");

        unprocessedString = unprocessedString.replaceAll("[0-9]", "");

        return unprocessedString;
    }

    protected static String capitaliseFile(String line)
    {
        return line.toUpperCase();
    }

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

                    if(i<plaintext.length)
                    {
                        i++;
                    }
                    else
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
     * Converts the alphabetical characters of an array to their corresponding numerical represenation
     *
     * @param plaintext
     * @return
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

    protected static char[] convertStreamOfIntsToString(int[] ciphertext)
    {
        char[] chararray = new char[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++)
        {
            chararray[i] = (char) (ciphertext[i] + 65);
        }
        return chararray;
    }

    protected static void processVigenereOutput()
    {

    }

}
