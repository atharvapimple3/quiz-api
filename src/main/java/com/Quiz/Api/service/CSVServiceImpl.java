package com.Quiz.Api.service;

import com.Quiz.Api.csv_helper.CSVHelper;
import com.Quiz.Api.entities.Question;
import com.Quiz.Api.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVServiceImpl implements CSVService {

    QuestionRepo questionRepo;
    CSVHelper csvHelper;

    @Autowired
    public CSVServiceImpl(QuestionRepo questionRepo, CSVHelper csvHelper) {
        this.questionRepo = questionRepo;
        this.csvHelper = csvHelper;
    }

    @Override
    public String saveFile(MultipartFile multipartFile, Integer quizId) {

        try {
            if (csvHelper.isCsvFormat(multipartFile)) {
                List<Question> questions = csvHelper.csvToQuestions(multipartFile.getInputStream(), quizId);
                questionRepo.saveAll(questions);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store csv data : " + e.getMessage());
        }

        return "File saved";
    }
}
