package com.example.codeneuron.Controller.Comment;

import com.example.codeneuron.Service.Comment.CommentService;
import com.example.codeneuron.VO.CommentForm;
import com.example.codeneuron.VO.CommentVO;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    /**
     * 获取评论
     * @param projectId
     * @param type 有效类型包括 node,edge,domain
     * @return
     */
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResponseVO getComment(@RequestParam("projectId") int projectId, @RequestParam("type") String type){
        return commentService.getComment(projectId,type);
    }

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public ResponseVO getOneComment(@RequestParam("type") String type, @RequestParam("relatedId") int relatedId){
        return commentService.getOneComment(type,relatedId);
    }
    /**
     * 增加评论
     * @param commentForm
     * commentForm中 type 有效类型同上
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod .POST)
    public ResponseVO addComment(@RequestBody CommentForm commentForm){
        return commentService.addComment(commentForm.getTitle(),commentForm.getContent(),commentForm.getUserId(),commentForm.getUserName(),commentForm.getType(), commentForm.getRelatedId());
    }

    /**
     * 更新评论
     * @param commentVO
     * commentVO中 type 有效类型同上
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseVO updateComment(@RequestBody CommentVO commentVO){
        return commentService.updateComment(commentVO.getId(),commentVO.getTitle(),commentVO.getContent());
    }

    /**
     * 删除评论
     * @param commentId
     * @param type 有效类型同上
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResponseVO deleteComment(@RequestParam("commentId") int commentId,@RequestParam("relatedId")int relatedId, @RequestParam("type") String type){
        return commentService.deleteComment(commentId,relatedId,type);
    }

//    /**
//     * 根据定点函数名获取评论
//     * @param nodeName
//     * @return
//     */
//    @RequestMapping(value = "/name/get",method = RequestMethod.GET)
//    public ResponseVO getNodeCommentByName(@RequestParam("name") String nodeName){
//        return commentService.getNodeCommentByName(nodeName);
//    }
}
