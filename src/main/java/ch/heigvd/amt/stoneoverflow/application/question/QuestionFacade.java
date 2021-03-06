package ch.heigvd.amt.stoneoverflow.application.question;

import ch.heigvd.amt.stoneoverflow.application.date.DateDTO;
import ch.heigvd.amt.stoneoverflow.domain.question.IQuestionRepository;
import ch.heigvd.amt.stoneoverflow.domain.question.Question;
import ch.heigvd.amt.stoneoverflow.domain.question.QuestionId;
import ch.heigvd.amt.stoneoverflow.domain.user.UserId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class QuestionFacade {
    private IQuestionRepository questionRepository;

    public QuestionFacade(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestion(AddQuestionCommand command){
        Question addedQuestion = Question.builder()
                .title(command.getTitle())
                .description(command.getDescription())
                .creatorId(command.getCreatorId())
                .creator(command.getCreator())
                .nbViews(new AtomicInteger(command.getNbViews()))
                .date(command.getDate())
                .questionType(command.getType()).build();
        questionRepository.save(addedQuestion);
    }

    public QuestionsDTO getQuestions(QuestionQuery query, int offset, int limit) {
        Collection<Question> allQuestions = questionRepository.find(query, offset, limit);
        List<QuestionsDTO.QuestionDTO> allQuestionsDTO = allQuestions.stream()
        .map(question -> QuestionsDTO.QuestionDTO.builder()
                .uuid(question.getId().asString())
                .title(question.getTitle())
                .creator(question.getCreator())
                .description(question.getDescription())
                .nbViews(question.getNbViewsAsInt())
                .date(new DateDTO(question.getDate()))
                .type(question.getQuestionType().name()).build())
        .collect(Collectors.toList());

        return QuestionsDTO.builder().questions(allQuestionsDTO).build();
    }

    public QuestionsDTO.QuestionDTO getQuestion(QuestionId id) {
        Optional<Question> question = questionRepository.findById(id);

        question.ifPresent(Question::addView);
        question.ifPresent(value -> questionRepository.update(value));

        return question.map(value -> QuestionsDTO.QuestionDTO.builder()
                .uuid(value.getId().asString())
                .title(value.getTitle())
                .description(value.getDescription())
                .creator(value.getCreator())
                .nbViews(value.getNbViewsAsInt())
                .date(new DateDTO(value.getDate()))
                .type(value.getQuestionType().name()).build())
            .orElse(null);
    }

    public int getNumberOfQuestions() {
        return questionRepository.getRepositorySize();
    }
}
