package com.Quiz.Api.csv_helper;


import com.Quiz.Api.entities.Question;
import com.Quiz.Api.entities.Quiz;
import com.Quiz.Api.exceptions.QuizNotFoundException;
import com.Quiz.Api.repository.QuizRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHelper {

    QuizRepo quizRepo;

    @Autowired
    public CSVHelper(QuizRepo quizRepo) {
        this.quizRepo = quizRepo;
    }

    public String format = "text/csv";
    String[] headers = {"quiz", "question", "options", "correctAnswer", "isDeleted"};


    public boolean isCsvFormat(MultipartFile multipartFile) {
        if(format.equals(multipartFile.getContentType())){
            return true;
        }else{
            throw new IllegalArgumentException("Incorrect format");
        }
    }

    public List<Question> csvToQuestions(InputStream inputStream, Integer quizId) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withAllowMissingColumnNames()
                     .withTrim());) {

            List<String> requiredHeaders = List.of(headers);
            if (!csvParser.getHeaderMap().keySet().containsAll(requiredHeaders)) {
                throw new IllegalArgumentException("CSV headers do not match required format. Required: " + requiredHeaders);
            }


            List<Question> questions = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord record : csvRecords) {

                if(record.stream().allMatch(String::isBlank)){
                    continue;
                }

                Quiz quiz = quizRepo.findById(quizId)
                        .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

                List<String> options = List.of(record.get("options").split(","));


                Question question = new Question(
                        quiz,
                        record.get("question"),
                        options,
                        record.get("correctAnswer"),
                        Boolean.parseBoolean(record.get("isDeleted")));

                questions.add(question);
            }
            return questions;
        } catch (IOException e) {
            throw new IOException("Failed to parse CSV file :" + e.getMessage());
        }
    }


}
