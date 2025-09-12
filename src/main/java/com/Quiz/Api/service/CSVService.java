package com.Quiz.Api.service;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {

    String saveFile(MultipartFile multipartFile, Integer quizId);

}
