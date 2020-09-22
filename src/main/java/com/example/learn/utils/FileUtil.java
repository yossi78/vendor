package com.example.learn.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;





public class FileUtil {


    public static <T> List<T> fileToListOfObjects(String filePath,Class T) throws IOException {
        String fileInStr=fileToString(filePath);
        List<T> results = parseStringToListOfObjects(fileInStr,T);
        return results;
    }


    public static String fileToString(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> lines = fileToLines(filePath);
        lines.stream().forEach(c->sb.append(c));
        return sb.toString();
    }


    public static List<String> fileToLines(String filePath) throws IOException {
        File file = new File(filePath);
        List<String> lines = fetchLines(file);
        return lines;
    }


    public static List<String> fileToWords(String filePath) throws IOException {
        File file = new File(filePath);
        List<String> words = fetchWords(file);
        return words;
    }


    public static void writeLinesToFile(String filePath,List<String> lines) throws  IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        for(String line:lines){
            if(line!=null && !line.isEmpty()){
                fileWriter.write(line+"\n");
            }
        }
        fileWriter.close();
        System.out.println("Successfully wrote to the file.");

    }


    //------------------  PRIVATE ---------------------------------------------------


    private static List<String> fetchLines(File file) throws IOException {
        List<String> list = new ArrayList<>();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }



    private static List<String> fetchWords(File file) throws IOException {
        List<String> list = new ArrayList<>();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                addWordsFromLine(line,list);
            }
        }
        return list;
    }


    private static void addWordsFromLine(String line,List<String> list){
        List<String> temp = List.of(line.split(" "));
        temp.stream()
                .filter(current->current!=null && !current.isEmpty())
                .forEach(current-> list.add(current));
    }



    public static  <T> List<T> parseStringToListOfObjects(String fileAsString , Class T) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        List<T> results = mapper.readValue(fileAsString, typeFactory.constructCollectionType(ArrayList.class,T));
        return results;
    }



    public Object parseStringToObject(String str,Class T) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object result = mapper.readValue(str,T);
        return result;
    }


    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtil.fileToLines("src\\main\\resources\\stocks.csv");
        System.out.println("*************   LINES ************************ ");
        lines.stream().forEach(System.out::println);
        System.out.println("*************  END OF LINES ************************ \n\n");


    }


}

