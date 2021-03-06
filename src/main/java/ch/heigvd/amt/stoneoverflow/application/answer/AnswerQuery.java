package ch.heigvd.amt.stoneoverflow.application.answer;

import ch.heigvd.amt.stoneoverflow.domain.question.QuestionId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class AnswerQuery {
    @Builder.Default
    private QuestionId answerTo = null;

    @Builder.Default
    private AnswerQuerySortBy sortBy = AnswerQuerySortBy.DATE;

    @Builder.Default
    private boolean sortDescending = true;
}
