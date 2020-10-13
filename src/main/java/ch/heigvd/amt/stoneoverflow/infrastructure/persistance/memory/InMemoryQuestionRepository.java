package ch.heigvd.amt.stoneoverflow.infrastructure.persistance.memory;


import ch.heigvd.amt.stoneoverflow.application.question.QuestionQuery;
import ch.heigvd.amt.stoneoverflow.domain.question.IQuestionRepository;
import ch.heigvd.amt.stoneoverflow.domain.question.Question;
import ch.heigvd.amt.stoneoverflow.domain.question.QuestionId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("InMemoryQuestionRepository")
public class InMemoryQuestionRepository extends InMemoryRepository<Question, QuestionId> implements IQuestionRepository {
    @Override
    public Collection<Question> find(QuestionQuery questionQuery) {
        return super.findAll().stream().sorted(Comparator.comparing(Question::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Question> findByVotes(QuestionQuery questionQuery) {
        return super.findAll().stream().sorted(Comparator.comparing(Question::getNbVotes).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Question> findByViews(QuestionQuery questionQuery) {
        return super.findAll().stream().sorted(Comparator.comparing(Question::getNbViews).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Question> findByType(QuestionQuery questionQuery) {
        Collection<Question> questions = super.findAll();
        ArrayList<Question> queredQuestion = new ArrayList<>();
        for (Question question : questions) {
            if (question.getQuestionType() == questionQuery.getType()) {
                queredQuestion.add(question);
            }
        }
        return queredQuestion;
    }
}
