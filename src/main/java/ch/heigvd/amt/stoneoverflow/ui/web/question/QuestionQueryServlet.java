package ch.heigvd.amt.stoneoverflow.ui.web.question;

import ch.heigvd.amt.stoneoverflow.application.pagination.PaginationDTO;
import ch.heigvd.amt.stoneoverflow.application.pagination.PaginationFacade;
import ch.heigvd.amt.stoneoverflow.application.question.QuestionFacade;
import ch.heigvd.amt.stoneoverflow.application.question.QuestionQuery;
import ch.heigvd.amt.stoneoverflow.application.question.QuestionQuerySortBy;
import ch.heigvd.amt.stoneoverflow.application.question.QuestionsDTO;
import ch.heigvd.amt.stoneoverflow.application.ServiceRegistry;
import ch.heigvd.amt.stoneoverflow.application.vote.VoteFacade;
import ch.heigvd.amt.stoneoverflow.domain.question.QuestionId;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="QuestionsPageServlet", urlPatterns =  {"", "/home"})
public class QuestionQueryServlet extends HttpServlet {
    @Inject
    ServiceRegistry serviceRegistry;

    private QuestionFacade   questionFacade;
    private VoteFacade       voteFacade;
    private PaginationFacade paginationFacade;

    @Override
    public void init() throws ServletException {
        super.init();
        questionFacade   = serviceRegistry.getQuestionFacade();
        voteFacade       = serviceRegistry.getVoteFacade();
        paginationFacade = serviceRegistry.getPaginationFacade();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QuestionQuery query = QuestionQuery.builder()
                .sortBy(QuestionQuerySortBy.VOTES)
                .build();

        String searchQuery = req.getParameter("s");
        if (searchQuery != null)
            query.setSearchCondition(searchQuery);

        PaginationDTO paginationDTO = paginationFacade.settingQuestionPagination(req.getParameter("page"));

        // Get questions from repo
        QuestionsDTO questionsDTO = questionFacade.getQuestions(query, paginationDTO.getStartItem(), paginationDTO.getLimit());
        for (QuestionsDTO.QuestionDTO questionDTO : questionsDTO.getQuestions())
            questionDTO.setNbVotes(voteFacade.getNumberOfVotes(new QuestionId(questionDTO.getUuid())));


        req.setAttribute("questions", questionsDTO);
        req.setAttribute("pagination", paginationDTO);
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}
