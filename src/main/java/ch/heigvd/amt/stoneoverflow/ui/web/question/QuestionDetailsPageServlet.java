package ch.heigvd.amt.StoneOverflow.ui.web.Question;

import ch.heigvd.amt.StoneOverflow.application.Question.QuestionFacade;
import ch.heigvd.amt.StoneOverflow.application.Question.QuestionsDTO;
import ch.heigvd.amt.StoneOverflow.application.ServiceRegistry;
import ch.heigvd.amt.StoneOverflow.application.answer.AnswerFacade;
import ch.heigvd.amt.StoneOverflow.application.answer.AnswersDTO;
import ch.heigvd.amt.StoneOverflow.application.comment.CommentFacade;
import ch.heigvd.amt.StoneOverflow.domain.Question.QuestionId;
import ch.heigvd.amt.StoneOverflow.domain.answer.AnswerId;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "questionDetailsServlet", urlPatterns = "/questionDetails")
public class QuestionDetailsPageServlet extends HttpServlet {
    @Inject
    ServiceRegistry serviceRegistry;

    private QuestionFacade questionFacade;
    private AnswerFacade   answerFacade;
    private CommentFacade  commentFacade;

    @Override
    public void init() throws ServletException {
        super.init();
        questionFacade = serviceRegistry.getQuestionFacade();
        answerFacade   = serviceRegistry.getAnswerFacade();
        commentFacade  = serviceRegistry.getCommentFacade();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String questionUUID = req.getParameter("questionUUID");

        if (questionUUID == null)
            resp.sendRedirect(req.getContextPath() + "/");

        // Get question from repository
        QuestionsDTO.QuestionDTO questionDTO = questionFacade.getQuestion(new QuestionId(questionUUID));
        // Get answers & comments of the question
        questionDTO.setAnswers(answerFacade.getAnswersFromQuestion(new QuestionId(questionDTO.getUuid())).getAnswers());
        questionDTO.setComments(commentFacade.getCommentsFromQuestion(new QuestionId(questionDTO.getUuid())).getComments());

        // Get comments of each answers
        for (AnswersDTO.AnswerDTO answer : questionDTO.getAnswers()) {
            answer.setComments(commentFacade.getCommentsFromAnswer(new AnswerId(answer.getUuid())).getComments());

        }

        req.setAttribute("question", questionDTO);
        req.getRequestDispatcher("WEB-INF/views/questionDetails.jsp").forward(req, resp);
    }
}