package com.fit.iuh.controllers;

import com.fit.iuh.entites.*;
import com.fit.iuh.services.*;
import com.fit.iuh.utilities.CommentUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

// Inner class để lưu thống kê của mỗi topic
@Data
@AllArgsConstructor
class TopicStats {
    private String name;
    private int count;
    private double percentage;
}

@Data
@AllArgsConstructor
public class DataInsight {
    private String icon;
    private String title;
    private String description;
    private String colorClass;
}