package ch.heigvd.amt.StoneOverflow.application.comment;

import ch.heigvd.amt.StoneOverflow.domain.Id;
import ch.heigvd.amt.StoneOverflow.domain.Question.QuestionId;
import ch.heigvd.amt.StoneOverflow.domain.answer.AnswerId;
import ch.heigvd.amt.StoneOverflow.domain.comment.Comment;
import ch.heigvd.amt.StoneOverflow.domain.comment.ICommentRepository;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CommentFacade {
    private ICommentRepository commentRepository;

    public CommentFacade(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(AddCommentCommand command) {
        Comment addComment = Comment.builder()
                .commentTo(command.getCommentTo())
                .creatorId(command.getCreatorId())
                .creator(command.getCreator())
                .content(command.getContent())
                .date(command.getDate()).build();
        commentRepository.save(addComment);
    }

    private CommentsDTO getCommentsFromId(Id commentTo) {
        Collection<Comment> commentsByCommentToId = commentRepository.findByCommentToId(commentTo);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        List<CommentsDTO.CommentDTO> commentsByCommentToIdDTO = commentsByCommentToId.stream().map(
                comment -> CommentsDTO.CommentDTO.builder()
                        .creator(comment.getCreator())
                        .content(comment.getContent())
                        .date(formatter.format(comment.getDate())).build()).collect(Collectors.toList());

        return CommentsDTO.builder().comments(commentsByCommentToIdDTO).build();
    }

    public CommentsDTO getCommentsFromQuestion(QuestionId commentTo) {
        return getCommentsFromId(commentTo);
    }

    public CommentsDTO getCommentsFromAnswer(AnswerId commentTo) {
        return getCommentsFromId(commentTo);
    }
}