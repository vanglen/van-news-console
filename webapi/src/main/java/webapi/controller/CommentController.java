package webapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @ResponseBody
    @RequestMapping("publish")
    public String Add() {
        return "";
    }

    @ResponseBody
    @RequestMapping("list")
    public String ListById(int id) {
        return "";
    }
}
