package ch.heigvd.amt.stoneoverflow.application.statistics;

import ch.heigvd.amt.stoneoverflow.domain.answer.IAnswerRepository;
import ch.heigvd.amt.stoneoverflow.domain.comment.ICommentRepository;
import ch.heigvd.amt.stoneoverflow.domain.question.IQuestionRepository;
import ch.heigvd.amt.stoneoverflow.domain.user.IUserRepository;

public class StatisticsFacade {
    private IQuestionRepository questionRepository;
    private IUserRepository userRepository;
    private ICommentRepository commentRepository;
    private IAnswerRepository answerRepository;

    public StatisticsFacade(IQuestionRepository questionRepository, IUserRepository userRepository, ICommentRepository commentRepository, IAnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
    }
}
