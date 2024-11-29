package com.fit.iuh.controllers;

import com.fit.iuh.entites.*;
import com.fit.iuh.enums.PostReportState;
import com.fit.iuh.services.*;
import com.fit.iuh.utilities.CommentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    // Service
    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private BookMarkService bookmarkService;

    @Autowired
    private FollowingService followingService;

    @Autowired
    private PostReportService postReportService;
    /*
      ** Topic
      * 1. Hiển thị danh sách topic
      * 2. Thêm topic
      *     2.1 Hiển thị form thêm topic,
      *     2.2 Thêm topic
      * 3. Xem chi tiết topic
      * 4. Sửa topic
      * 5. Xóa topic
     */

    // 1. Hiển thị danh sách topic
    @GetMapping("/topic")
    public String topic(Model model,
                        @RequestParam(defaultValue = "0") int numberPage,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false) String key) {

        Page<Topic> page;
        if (key != null && !key.trim().isEmpty()) {
            // Nếu có từ khóa tìm kiếm
            page = topicService.searchByKeywordWithPaging(key, numberPage, size);
            model.addAttribute("key", key);
        } else {
            page = topicService.getPage(numberPage, size);
        }

        // Các attribute khác giữ nguyên
        model.addAttribute("topics", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("start", numberPage * size + 1);
        model.addAttribute("end", numberPage * size + page.getNumberOfElements());
        model.addAttribute("quantityTopic", page.getTotalElements());
        model.addAttribute("size", size);

        return "views_admin/topic";
    }
    // 2. Thêm topic
    @GetMapping("/topic/addform")
    public String addTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "views_admin/add-topic";
    }

    // 2.1 Hiển thị form thêm topic
    @PostMapping("/topic/add")
    public String addTopic(Model model,Topic topic) {
        topic.setCreatedAt(new Date(System.currentTimeMillis()));
        topic.setUpdatedAt(new Date(System.currentTimeMillis()));
        // Kiểm tra xem topic đã tồn tại chưa, nếu topic đã tồn { Name , Hashtag } thì thông báo ra
        if (topicService.isExist(topic.getName(), topic.getHashtag())) {
            model.addAttribute("error", "Topic already exists with the same name and hashtag.");
            return "views_admin/add-topic";
        }
        topicService.add(topic);
        return "redirect:/admin/topic";
    }

    // 3. Xem chi tiết topic
    @GetMapping("/topic/detail/{id}")
    public String viewTopic(@PathVariable int id, Model model) {

        Topic topic = topicService.getById(id);
        model.addAttribute("topic", topic);

        List<Post> posts = topic.getPosts();
        model.addAttribute("posts", posts);

        return "views_admin/view-topic";
    }

    // 4. Sửa topic
    @PostMapping("/topic/edit")
    @ResponseBody
    public Map<String, Object> editTopic(@RequestParam int id, @RequestParam String name, @RequestParam String hashtag) {
        Map<String, Object> response = new HashMap<>();
        try {
            Topic topic = topicService.getById(id);
            topic.setName(name.trim());
            topic.setHashtag(hashtag.trim());
            topic.setUpdatedAt(new Date(System.currentTimeMillis()));

            topicService.update(topic);

            response.put("status", "success");
            response.put("message", "Cập nhật thành công!");

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
        }
        return response;
    }

    // 5. Xóa topic
    @GetMapping("/topic/delete")
    public String deleteTopic(@RequestParam int id) {
        topicService.delete(id);
        return "redirect:/admin/topic";
    }

    /*
    * Comment
    * 1. Hiển thị danh sách comment
    */
    @GetMapping("/comment")
    public String comment(Model model,
                          @RequestParam(defaultValue = "0") int numberPage,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(required = false) String key) {

        Page<Post> page;
        if (key != null && !key.trim().isEmpty()) {
            // Nếu có từ khóa tìm kiếm
            page = postService.searchByKeywordWithPaging(key, numberPage, size);
            model.addAttribute("key", key);
        } else {
            page = postService.getPage(numberPage, size);
        }
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("start", numberPage * size + 1);
        model.addAttribute("end", numberPage * size + page.getNumberOfElements());
        model.addAttribute("quantityPost", page.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("posts", page.getContent());


        List<Comment> comments = new ArrayList<>();
        for (Post post : page.getContent()) {
            comments.addAll(post.getComments());
        }
        // check comment
        int numberNegative = 0;
        for (Comment comment : comments) {
            int check = CommentUtils.checkComment(comment.getContent());
            if (check == 0) {
                numberNegative++;
            }
        }
        model.addAttribute("numberNegative", numberNegative);
        model.addAttribute("comments", comments);

        List<Post> posts = page.getContent();
        Map<Integer, Integer> negativeCommentCounts = new HashMap<>();
        for (Post post : posts) {
            int countNegative = 0;
            for (Comment comment : post.getComments()) {
                int check = CommentUtils.checkComment(comment.getContent());
                if (check == 0) {
                    countNegative++;
                }
            }
            negativeCommentCounts.put(post.getPostId(), countNegative);
        }
        model.addAttribute("negativeCommentCounts", negativeCommentCounts);

        return "views_admin/comment";
    }

    @GetMapping("/view-comment/{id}")
    public String viewComment(@PathVariable int id, Model model) {
        Post post = postService.findByID(id);
        model.addAttribute("post", post);
        return "views_admin/view-comment";
    }

    @GetMapping("/comment/negative")
    public String negativeComment(Model model) {
        List<Comment> negativeComments = new ArrayList<>();
        List<Post> posts = postService.findAll();
        for (Post post : posts) {
            for (Comment comment : post.getComments()) {
                int check = CommentUtils.checkComment(comment.getContent());
                if (check == 0) {
                    negativeComments.add(comment);
                }
            }
        }
        model.addAttribute("negativeComments", negativeComments);
        return "views_admin/view-negative-comment";
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable int id) {
        commentService.remove(id);
        return "redirect:/admin/comment";
    }

    /*
    * Dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        
        // Thong ke so luong post 
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        // Thong ke so luong post duoc tao trong tuan
        List<Post> postsInWeek = postService.getPostsCreatedInWeek();
        model.addAttribute("postsInWeek", postsInWeek);

        // Thong ke so luong comment, reaction, bookmark, follow trong tuan de lam tong tuong tac
        List<Comment> comments = commentService.findAll();
        List<Reaction> reactions = reactionService.findAll();
        List<BookMark> bookMarks = bookmarkService.findAll();
        List<Following> follows = followingService.findAll();

        // Tong tuong tac
        int totalInteractions = comments.size() + reactions.size() + bookMarks.size() + follows.size();
        model.addAttribute("totalInteractions", totalInteractions);

        // Thong ke so luong comment, reaction, bookmark, follow trong tuan
        List<Comment> commentsInWeek = commentService.getCommentsCreatedInWeek();
        List<Reaction> reactionsInWeek = reactionService.getReactionsCreatedInWeek();
        List<BookMark> bookMarksInWeek = bookmarkService.getBookMarksCreatedInWeek();
        List<Following> followsInWeek = followingService.getFollowingsCreatedInWeek();

        // Tong tuong tac trong tuan
        int totalInteractionsInWeek = commentsInWeek.size() + reactionsInWeek.size() + bookMarksInWeek.size() + followsInWeek.size();
        model.addAttribute("totalInteractionsInWeek", totalInteractionsInWeek);

        // Thong ke so luong thanh vien
        model.addAttribute("follows", follows);
        // Thong ke thanh vien duoc tao trong tuan
        model.addAttribute("followsInWeek", followsInWeek);

        // Thong ke so luong bai viet bi bao cao
        List<PostReport> postReports = postReportService.findAll();
        model.addAttribute("postReports", postReports);

        // Thong ke so luong bai viet bi bao cao trong tuan
        List<PostReport> postReportsInWeek = postReportService.getPostReportsCreatedInWeek();
        model.addAttribute("postReportsInWeek", postReportsInWeek);

        // Tính toán tương tác cho 4 tháng gần nhất
        int[] monthlyInteractions = new int[4];
        String[] monthLabels = new String[4];
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        
        // Lùi về 3 tháng trước để bắt đầu tính (vì tháng hiện tại là tháng thứ 4)
        cal.add(Calendar.MONTH, -3);
        
        for (int i = 0; i < 4; i++) {
            // Set ngày đầu tháng
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date startDate = cal.getTime();
            
            // Set ngày cuối tháng
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            Date endDate = cal.getTime();
            
            // Lưu label tháng
            monthLabels[i] = monthFormat.format(cal.getTime());
            
            // Lấy tất cả tương tác trong tháng
            List<Comment> monthComments = commentService.getCommentsBetweenDates(startDate, endDate);
            List<Reaction> monthReactions = reactionService.getReactionsBetweenDates(startDate, endDate);
            List<BookMark> monthBookmarks = bookmarkService.getBookMarksBetweenDates(startDate, endDate);
            List<Following> monthFollows = followingService.getFollowingsBetweenDates(startDate, endDate);
            
            // Tính tổng tương tác
            monthlyInteractions[i] = monthComments.size() + 
                                    monthReactions.size() + 
                                    monthBookmarks.size() + 
                                    monthFollows.size();
            
            // Chuyển sang tháng tiếp theo
            cal.add(Calendar.MONTH, 1);
        }
        System.out.println(monthlyInteractions);    
        
        model.addAttribute("monthLabels", monthLabels);
        model.addAttribute("monthlyInteractions", monthlyInteractions);

        // Thống kê trạng thái báo cáo bài viết
        int acceptedReports = 0;
        int processingReports = 0;
        int rejectedReports = 0;

        for (PostReport report : postReports) {
            switch (report.getState()) {
                case ACCEPTED:
                    acceptedReports++;
                    break;
                case PROCESSING:
                    processingReports++;
                    break;
                case REJECTED:
                    rejectedReports++;
                    break;
            }
        }

        int[] reportStats = {acceptedReports, processingReports, rejectedReports};
        model.addAttribute("reportStats", reportStats);

        // Lấy tất cả topics và posts
        List<Topic> allTopics = topicService.findAll();
        List<Post> allPosts = postService.findAll();
        int totalPosts = allPosts.size();
        
        // Tạo map để đếm số bài viết cho mỗi topic
        Map<Topic, Long> topicPostCounts = allPosts.stream()
            .filter(post -> post.getTopic() != null)
            .collect(Collectors.groupingBy(Post::getTopic, Collectors.counting()));

        // Sắp xếp topics theo số lượng bài viết (giảm dần)
        List<Map.Entry<Topic, Long>> sortedTopics = topicPostCounts.entrySet()
            .stream()
            .sorted(Map.Entry.<Topic, Long>comparingByValue().reversed())
            .collect(Collectors.toList());

        // Lấy 5 topics có nhiều bài viết nhất
        List<TopicStats> topicStats = new ArrayList<>();
        
        // Tính tổng bài viết của top 5 topics
        long top5PostsCount = sortedTopics.stream()
            .limit(5)
            .mapToLong(Map.Entry::getValue)
            .sum();
        
        // Tính số bài viết còn lại cho Others
        long othersCount = totalPosts - top5PostsCount;

        // Thêm top 5 topics
        sortedTopics.stream().limit(5).forEach(entry -> {
            double percentage = (entry.getValue() * 100.0) / totalPosts;
            topicStats.add(new TopicStats(
                entry.getKey().getName(),
                entry.getValue().intValue(),
                Math.round(percentage * 10.0) / 10.0
            ));
        });

        // Thêm Others nếu có
        if (othersCount > 0) {
            double othersPercentage = (othersCount * 100.0) / totalPosts;
            topicStats.add(new TopicStats(
                "Others",
                (int) othersCount,
                Math.round(othersPercentage * 10.0) / 10.0
            ));
        }

        model.addAttribute("topicStats", topicStats);
        
        // Thêm phân tích insights
        List<DataInsight> insights = analyzeData(
            totalInteractions, 
            totalInteractionsInWeek,
            postReports,
            processingReports,
            topicStats,
            monthlyInteractions
        );
        
        model.addAttribute("insights", insights);
        
        return "views_admin/index";
    }

    private List<DataInsight> analyzeData(
        int totalInteractions,
        int totalInteractionsInWeek,
        List<PostReport> postReports,
        int processingReports,
        List<TopicStats> topicStats,
        int[] monthlyInteractions
    ) {
        List<DataInsight> insights = new ArrayList<>();
        
        // 1. Phân tích tương tác người dùng
        String userInteractionDesc = analyzeUserInteractions(totalInteractions, totalInteractionsInWeek);
        insights.add(new DataInsight(
            "fas fa-users",
            "Tương tác người dùng",
            userInteractionDesc,
            "bg-primary"
        ));

        // 2. Phân tích báo cáo vi phạm
        String reportDesc = analyzeReports(postReports);
        insights.add(new DataInsight(
            "fas fa-flag",
            "Báo cáo vi phạm",
            reportDesc,
            "bg-success"
        ));

        // 3. Phân tích phân bố chủ đề
        String topicDesc = analyzeTopicDistribution(topicStats);
        insights.add(new DataInsight(
            "fas fa-tags",
            "Phân bố chủ đề",
            topicDesc,
            "bg-info"
        ));

        // 4. Phân tích điểm cần chú ý
        String warningDesc = analyzeWarningPoints(processingReports, postReports.size());
        insights.add(new DataInsight(
            "fas fa-exclamation-triangle",
            "Điểm cần chú ý",
            warningDesc,
            "bg-warning"
        ));

        // 5. Phân tích xu hướng
        String trendDesc = analyzeTrend(monthlyInteractions);
        insights.add(new DataInsight(
            "fas fa-chart-line",
            "Xu hướng",
            trendDesc,
            "bg-danger"
        ));

        return insights;
    }

    private String analyzeUserInteractions(int total, int weekly) {
        double weeklyPercentage = (weekly * 100.0) / total;
        if (weeklyPercentage > 20) {
            return "Hệ thống đang hoạt động tốt với lượng tương tác đáng kể từ người dùng";
        }
        return "Lượng tương tác người dùng ở mức trung bình";
    }

    private String analyzeReports(List<PostReport> reports) {
        if (reports.isEmpty()) {
            return "Chưa có bài viết nào bị báo cáo";
        }

        PostReportState[] states = PostReportState.values();

        long resolved = reports.stream()
                .filter(r -> r.getState() == PostReportState.ACCEPTED || r.getState() == PostReportState.REJECTED)
                .count();
        double resolveRate = (resolved * 100.0) / reports.size();
        
        if (resolveRate > 70) {
            return "Các báo cáo vi phạm đang được xử lý kịp thời và hiệu quả";
        } else if (resolveRate > 40) {
            return "Tốc độ xử lý báo cáo vi phạm ở mức trung bình";
        }
        return "Cần cải thiện tốc độ xử lý báo cáo vi phạm";
    }

    private String analyzeTopicDistribution(List<TopicStats> topicStats) {
        boolean isWellDistributed = topicStats.stream()
            .noneMatch(stat -> stat.getPercentage() > 50) &&
            topicStats.stream()
            .filter(stat -> stat.getPercentage() > 10)
            .count() >= 3;
            
        if (isWellDistributed) {
            return "Các bài viết được phân bố đều, đến trên nhiều chủ đề khác nhau";
        }
        return "Phân bố bài viết đang tập trung vào một số chủ đề chính";
    }

    private String analyzeWarningPoints(int processing, int total) {
        double processingRate = (processing * 100.0) / total;
        if (processingRate > 30) {
            return "Cần theo dõi và xử lý các báo cáo vi phạm đang trong trạng thái chờ";
        }
        return "Số lượng báo cáo chờ xử lý ở mức chấp nhận được";
    }

    private String analyzeTrend(int[] monthlyInteractions) {
        if (monthlyInteractions.length < 2) return "Chưa đủ dữ liệu để phân tích xu hướng";
        
        int lastMonth = monthlyInteractions[monthlyInteractions.length - 1];
        int previousMonth = monthlyInteractions[monthlyInteractions.length - 2];
        
        if (lastMonth > previousMonth) {
            return "Lượng tương tác có xu hướng tăng trong những ngày gần đây";
        }
        return "Lượng tương tác đang ổn định";
    }

    /*
    * Comment different
     */
    @GetMapping("/table")
    public String table() { return "views_admin/table"; }

    @GetMapping("/login")
    public String login() {
        return "views_admin/login";
    }

    @GetMapping("/register")
    public String register() {
        return "views_admin/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "views_admin/forgot-password";
    }

    @GetMapping("/404")
    public String error404() {
        return "views_admin/404";
    }

    @GetMapping("/blank")
    public String blank() {
        return "views_admin/blank";
    }

    @GetMapping("/profile")
    public String profile() {
        return "views_admin/profile";
    }
}
