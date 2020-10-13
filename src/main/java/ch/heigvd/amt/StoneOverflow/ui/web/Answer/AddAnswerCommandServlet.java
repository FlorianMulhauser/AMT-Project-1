package ch.heigvd.amt.StoneOverflow.ui.web.Answer;

import ch.heigvd.amt.StoneOverflow.application.Question.AddQuestionCommand;
import ch.heigvd.amt.StoneOverflow.application.ServiceRegistry;
import ch.heigvd.amt.StoneOverflow.application.answer.AddAnswerCommand;
import ch.heigvd.amt.StoneOverflow.application.answer.AnswerFacade;
import ch.heigvd.amt.StoneOverflow.application.identitymgmt.login.AuthenticatedUserDTO;
import ch.heigvd.amt.StoneOverflow.domain.Question.QuestionId;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SubmitAnswerCommandServlet", urlPatterns = "/submitAnswer.do")
public class AddAnswerCommandServlet extends HttpServlet {
    @Inject
    ServiceRegistry serviceRegistry;
    AnswerFacade answerFacade;

    @Override
    public void init() throws ServletException {
        super.init();
        answerFacade = serviceRegistry.getAnswerFacade();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo: verify getSession ?
        AuthenticatedUserDTO user = (AuthenticatedUserDTO)req.getSession().getAttribute("authenticatedUser");
        AddAnswerCommand command = AddAnswerCommand.builder()
                .answerTo(new QuestionId(req.getParameter("questionUUID")))
                .description(req.getParameter("description"))
                .creatorId(user.getId())
                .creator(user.getUsername()).build();
        answerFacade.addAnswer(command);


        //resp.sendRedirect(req.getContextPath() + "/questionDetails");
        req.getRequestDispatcher("/questionDetails").forward(req, resp);
    }
}
