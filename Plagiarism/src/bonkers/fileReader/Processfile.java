package bonkers.fileReader;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Processfile
{
    private static File
            fileInA,
            fileInB,
            fileOut;
    private static ArrayList<StringBuilder>
            fileAInArray = new ArrayList<>(),
            fileBInArray = new ArrayList<>(),
            matchingArray = new ArrayList<>();
    private static int[]
            fileAOccurrences,
            fileBOccurrences;
    private static int
            fileALength,
            fileBLength;
    public static boolean
            outputCreated = false;

// ------------------

    public static void setFileALength(int l)
    {
        fileALength = l;
    }

    public static void setFileBLength(int l)
    {
        fileBLength = l;
    }

    public static StringBuilder processLine(String line, StringBuilder linex, int index)
    {
        if (line.length() == 0)
        {
            return linex;
        }

        if (index == line.length() - 1)
        {
            if (Character.isLetter(line.charAt(index)))
            {
                linex.append(Character.toLowerCase(line.charAt(index)));
            }
            else if (Character.isDigit(line.charAt(index)))
            {
                linex.append(line.charAt(index));
            }
            linex.append(System.getProperty("line.separator"));
            return linex;
        }

        if (line.charAt(index + 1) == ' ' && line.charAt(index) == ' ')
        {
            return processLine(line, linex, index + 1);
        }

        else if (Character.isUpperCase(line.charAt(index)))
        {
            linex.append(Character.toLowerCase(line.charAt(index)));
        }

        else if (Character.isLetter(line.charAt(index)) ||
                Character.isDigit(line.charAt(index)) ||
                Character.isSpaceChar(line.charAt(index)))
        {
            linex.append(line.charAt(index));
        }

        return processLine(line, linex, index + 1);
    }

    public static int indexedArrCheck (ArrayList<StringBuilder> ax, StringBuilder lP)
    {
        int index = 0;
        for (StringBuilder it : ax)
        {
            if ((lP.toString()).equals(it.toString()))
            {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public static boolean arrCheck (ArrayList<StringBuilder> ax, StringBuilder lP)
    {
        for (StringBuilder it : ax)
        {
            if ((lP.toString()).equals(it.toString()))
            {
                return false;
            }
        }
        return true;
    }

    public static void readFile(String fna, String fnb)
    {
        fileInA = new File(fna);
        fileInB = new File(fnb);

        try
        {
            fileAOccurrences = new int[fragmentRead(fileInA, fileAInArray, false)];
            fileBOccurrences = new int[fragmentRead(fileInB, fileBInArray, true)];
            Collections.sort(fileAInArray);
            Collections.sort(fileBInArray);
        } catch (Exception ex)
        {
            System.out.println("An I/O exception was thrown.");
            ex.printStackTrace();
        }
    }

    public static void fragmentRead (File fileIn, ArrayList<StringBuilder> fileInArray, int[] fileOccurrences) throws IOException
    {
        String lReader;
        BufferedReader Reader = new BufferedReader(new FileReader(fileIn));
        while ((lReader = Reader.readLine()) != null)
        {
            StringBuilder lProcessor = new StringBuilder();
            lProcessor = processLine(lReader, lProcessor, 0);
            StringTokenizer st = new StringTokenizer(lProcessor.toString());

            // Split every line into tokens and add to unique word array
            while (st.hasMoreTokens())
            {
                StringBuilder lx = new StringBuilder(st.nextToken());
                if (lx.length() >= 3)
                {
                    int token = indexedArrCheck(fileInArray, lx);
                    if (token != -1)
                    {
                        ++fileOccurrences[token];
                    }
                }
            }
        }
        Reader.close();
    }

    public static int fragmentRead (File fileIn, ArrayList<StringBuilder> fileInArray, boolean x) throws IOException
    {
        String lReader;
        BufferedReader Reader = new BufferedReader(new FileReader(fileIn));
        int wordCount = 0;

        while ((lReader = Reader.readLine()) != null)
        {
            StringBuilder lProcessor = new StringBuilder();
            lProcessor = processLine(lReader, lProcessor, 0);
            StringTokenizer st = new StringTokenizer(lProcessor.toString());

            // Split every line into tokens and add to unique word array
            while (st.hasMoreTokens())
            {
                StringBuilder lx = new StringBuilder(st.nextToken());
                if (lx.length() >= 3)
                {
                    ++wordCount; // only words with 3-3+ characters are counted
                    if (arrCheck(fileInArray, lx))
                    {
                        fileInArray.add(lx);
                    }
                }
            }
        }

        if (x)
        {
            setFileALength(wordCount);
        }
        else
        {
            setFileBLength(wordCount);
        }

        Reader.close();
        return fileInArray.size();
    }

    public static void compUniqueWords()
    {
        try
        {
            fragmentRead(fileInA, fileAInArray, fileAOccurrences);
            fragmentRead(fileInB, fileBInArray, fileBOccurrences);

            for (int andex = 0; andex < fileAInArray.size(); ++andex)
            {
                for (int bndex = 0; bndex < fileBInArray.size(); ++bndex)
                {
                    if ((fileAInArray.get(andex).toString()).equals(fileBInArray.get(bndex).toString()))
                    {
                        matchingArray.add(fileAInArray.get(andex));
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("An I/O exception was thrown.");
            ex.printStackTrace();
        }
    }

    public static void showStatistics()
    {
        try
        {
            int aTotal = 0, bTotal = 0;
            float percentage;

            System.out.print(String.format("%-22s%-22s%-22s%-22s\n", "Word", fileInA.getName(), fileInB.getName(), "Cumulative"));
            System.out.print("----------------------------------------------------------------------------------\n");

            for (StringBuilder it : matchingArray)
            {
                int aIndex = 0, bIndex = 0;
                for (int index = 0; index < fileAInArray.size(); ++index)
                {
                    if ((it.toString()).equals(fileAInArray.get(index).toString()))
                    {
                        aTotal = aTotal + fileAOccurrences[index];
                        aIndex = index;
                        break;
                    }
                }
                for (int index = 0; index < fileBInArray.size(); ++index)
                {
                    if ((it.toString()).equals((fileBInArray.get(index)).toString()))
                    {
                        bTotal = bTotal + fileBOccurrences[index];
                        bIndex = index;
                        break;
                    }
                }
            System.out.print(String.format("%-22s%-22d%-22d%-22d\n", it.toString(), fileAOccurrences[aIndex], fileBOccurrences[bIndex], (fileAOccurrences[aIndex]+fileBOccurrences[bIndex])));
        }
        percentage = (float)(aTotal+bTotal)/(float)(fileALength+fileBLength) * 100;
        System.out.print("----------------------------------------------------------------------------------\n");
        System.out.print(percentage + "% of the words are shared between the files. " + "(" + (aTotal+bTotal) + "/" + (fileALength+fileBLength) + ")");
        outputCreated = true;
    }
    catch (Exception ex)
    {
        System.out.println("An I/O exception was thrown.");
        ex.printStackTrace();
    }
}

    public static void writeStatistics(String filePath)
    {
        BufferedWriter bBuffer = null;
        try
        {
            int aTotal = 0, bTotal = 0;
            float percentage;

            fileOut = new File(filePath);
            if (!fileOut.exists())
            {
                fileOut.createNewFile();
            }

            FileWriter fWriter = new FileWriter(fileOut);
            bBuffer = new BufferedWriter(fWriter); // buffer is formatted below
            bBuffer.write(String.format("%-22s%-22s%-22s%-22s\n", "Word", fileInA.getName(), fileInB.getName(), "Cumulative"));
            bBuffer.write("----------------------------------------------------------------------------------\n");

            for (StringBuilder it : matchingArray)
            {
                int aIndex = 0, bIndex = 0;
                for (int index = 0; index < fileAInArray.size(); ++index)
                {
                    if ((it.toString()).equals(fileAInArray.get(index).toString()))
                    {
                        aTotal = aTotal + fileAOccurrences[index];
                        aIndex = index;
                        break;
                    }
                }
                for (int index = 0; index < fileBInArray.size(); ++index)
                {
                    if ((it.toString()).equals((fileBInArray.get(index)).toString()))
                    {
                        bTotal = bTotal + fileBOccurrences[index];
                        bIndex = index;
                        break;
                    }
                }
                bBuffer.write(String.format("%-22s%-22d%-22d%-22d\n", it.toString(), fileAOccurrences[aIndex], fileBOccurrences[bIndex], (fileAOccurrences[aIndex]+fileBOccurrences[bIndex])));
            }
            percentage = (float)(aTotal+bTotal)/(float)(fileALength+fileBLength) * 100;
            bBuffer.write("----------------------------------------------------------------------------------\n");
            bBuffer.write(percentage + "% of the words are shared between the files." + "(" + (aTotal+bTotal) + "/" + (fileALength+fileBLength) + ")");
            outputCreated = true;
        }
        catch (Exception ex)
        {
            System.out.println("An I/O exception was thrown.");
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (bBuffer != null)
                    bBuffer.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static void run(String fna, String fnb)
    {
        readFile(fna, fnb);
        compUniqueWords();
        showStatistics();
    }
}

