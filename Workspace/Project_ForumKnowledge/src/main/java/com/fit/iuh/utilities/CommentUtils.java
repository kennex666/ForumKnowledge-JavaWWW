package com.fit.iuh.utilities;

import java.util.Arrays;
import java.util.List;

public class CommentUtils {
    /*
        * Danh sách các từ cấm - tục tĩu
        * Lý do: do 1 số hạn chế nên không thể sử dụng API kiểm tra từ cấm nên phải tự tạo
        * Nguồn: tự tạo
        * Cách thức: kiểm tra xem bình luận có chứa từ cấm không
     */
    private static final List<String> badWords = Arrays.asList(
        "địt mẹ","Đjt", "đi chết", "khốn", "đồ ngu", "đồ chó", "con mẹ mày", "cút đi", "đồ khốn", "đm", "fuck",
        "đần", "đồ khốn", "địt", "lũ chó", "vô dụng", "rác rưởi", "bắc kì", "nam kì",
        "thằng khốn", "mẹ mày", "chết đi", "khốn nạn", "vô dụng",
        "đồ gay", "đồ đàn bà", "đồng tính", "lũ phụ nữ", "đồ bẩn",
        "đồ giả dối", "đồ xấu", "thằng đực", "láo", "lgbt", "vl", "lồn",
        "yếu đuối", "con gái vô dụng", "thằng","đập", "đấm", "ngu", "óc chó",
        "lừa đảo", "tội phạm", "cướp", "băng nhóm", "đánh bạc", "ma túy",
        "cướp bóc", "tội phạm", "làm giả", "tống tiền", "đánh nhau", "xâm hại",
        "đồ miền Bắc", "đồ miền Nam", "đồ miền Trung", "thằng Bắc Kỳ", "con Nam Bộ",
        "mày xấu xí", "mày không ra gì", "mày không có gì", "mày không đáng gì", "đồ hèn",
        "mày là thằng thất bại", "mày là đồ khốn", "lũ vô học", "mày là đồ xấu",
        "mày đáng bị đập", "đồ xấu xí", "mày chẳng có gì", "đồ ăn hại", "vô học", "fck"
    );

    // Hàm kiểm tra bình luận
    public static int checkComment(String comment) {
        String[] words = comment.toLowerCase().split("\\s+");
        for (String word : words) {
            if (badWords.contains(word)) {
                return 0;
            }
        }

        return 1; // Bình luận hợp lệ
    }

//    public static void main(String[] args) {
//        //check
//        System.out.println(checkComment("chúng m ngu"));
//    }
}