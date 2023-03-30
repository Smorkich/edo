package com.education.util;


import com.education.entity.Appeal;
import com.education.entity.Question;
import model.dto.AppealDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Проверяет Appeal
 */
public class Validator {
    // Проверка поля appeal на наличие question
    public static boolean validateAppeal(AppealDto appeal) {
        List<String> err = new ArrayList<>();
        if (appeal.getQuestions().size() > 0) {
            /*appeal.getQuestions().stream()
                    .filter(x -> x.getSummary() != null && x.getTheme() != null)
                    .filter(x -> x.getSummary().length() <= 200)
                    .collect(Collectors.toList());*/
            appeal.getQuestions().forEach(question -> {
                if (question.getTheme() == null) {
                    err.add(String.format("Appeal with ID %s has question with ID %s where Theme is null",
                            appeal.getId(), question.getId()));
                }
                if (question.getSummary() == null) {
                    err.add(String.format("Appeal with ID %s has question with ID %s where Summary is null",
                            appeal.getId(), question.getId()));
                } else if (question.getSummary().length() > 200) {
                    err.add(String.format("Appeal with ID %s has question with ID %s where Summary has length more then 200 symbols",
                            appeal.getId(), question.getId()));
                }
            });
        } else {
            err.add(String.format("Appeal with ID %s has not question", appeal.getId()));
        }
        if (appeal.getSendingMethod() == null) {
            err.add(String.format("Appeal with ID %s has null SendingMethod", appeal.getId()));
        }
        if (appeal.getAuthors() != null) {
            appeal.getAuthors().forEach(author -> {
                if (author.getFirstName() == null) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where firstName is null",
                            appeal.getId(), author.getId()));
                } else if (author.getFirstName().length() > 60) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where firstName has length more then 60 symbols",
                            appeal.getId(), author.getId()));
                }
                if (author.getLastName() == null) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where lastName is null",
                            appeal.getId(), author.getId()));
                } else if (author.getLastName().length() > 60) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where lastName has length more then 60 symbols",
                            appeal.getId(), author.getId()));
                }
                if (author.getMobilePhone() == null) {
                    err.add(String.format("Appeal with ID %s has author with ID %s where mobilePhone is null",
                            appeal.getId(), author.getId()));
                }
            });
        }
        if (!err.isEmpty()) {
            System.out.println(err);
        }
        return err.isEmpty();
    }
}
