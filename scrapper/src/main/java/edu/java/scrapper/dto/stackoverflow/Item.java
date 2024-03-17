package edu.java.scrapper.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record Item(
    @JsonProperty("owner") StackOverflowOwner owner,
    @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
    @JsonProperty("question_id") Long questionId,
    @JsonProperty("title") String title,
    @JsonProperty("answer_count") long answerCount
) {}
