package edu.java.bot.dto.response;

import java.util.List;

public class StackOverflowInfoResponse {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "StackOverflowInfoResponse{"
            +
            "questions=" + questions
            +
            '}';
    }

    public static class Question {
        private String title;
        private String link;
        private int score;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Question{"
                +
                "title='" + title + '\''
                +
                ", link='" + link + '\''
                +
                ", score=" + score
                +
                '}';
        }
    }
}
