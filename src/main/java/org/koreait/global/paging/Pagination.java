package org.koreait.global.paging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class Pagination {

    private int page;
    private int total;
    private int ranges;
    private int limit;
    private int totalPages; // 전체 페이지
    private int firstRangePage; // 현재 구간에서 시작 Page 번호
    private int lastRangePage; // 현재 구간에서 종료 Page 번호
    private int prevRangeLastPage; // 이전 구간 종료 Page
    private int nextRangeFirstPage; // 다음 구간 시작 Page

    private String baseUrl;

    /**
     *
     * @param page : 현재 페이지 번호
     * @param total : 총 레코드 갯수
     * @param ranges : 페이지 구간 갯수
     * @param limit : 한 페이지당 출력될 레코드 갯수
     */
    public Pagination(int page, int total, int ranges, int limit) {
        this(page, total, ranges, limit, null);
    }

    public Pagination(int page, int total, int ranges, int limit, HttpServletRequest request) {
//        페이징 기본값 처리
        page = Math.max(page, 1);
        total = Math.max(total, 0);
        ranges = ranges < 1 ? 10 : ranges;
        limit = limit < 1 ? 20 : limit;

        if (total == 0) {
            return;
        }

//        전체 페이지 갯수
        int totalPages  = (int) Math.ceil(total / (double) limit);

//        구간 번호 - 0, 1 ,2
        int rangeCnt = (page - 1) / ranges;

        // 현재 구간의 시작 Page 번호
        int firstRangePage = rangeCnt * ranges + 1;

        // 현재 구간의 종료 Page 번호
        int lastRangePage = firstRangePage + ranges - 1;

        // 마지막 구간의 마지막 Page 번호 처리, 둘 중 작은 것으로 대입
        lastRangePage = Math.min(lastRangePage, totalPages);

        // 값이 0이 아닐 경우 버튼 노출
        // 이전 구간 마지막 Page 번호, 다음 구간 시작 Page 번호
        int prevRangeLastPage = 0, nextRangeFirstPage = 0;

        // 2번째 구간 이상 마지막 구간 미만일 경우에만 이전구간버튼 노출
        if (rangeCnt > 0) {

            prevRangeLastPage = firstRangePage - 1;
        }

        // 마지막 구간
        int lastRangeCnt = (totalPages - 1) / ranges;

        // 마지막 구간 이전까지만 다음구간버튼 노출
        if (rangeCnt < lastRangeCnt) {

            nextRangeFirstPage = (rangeCnt + 1) * ranges + 1;
        }

        /* 쿼리스트링 값 처리 S */

        String qs = request == null ? "" : request.getQueryString();
        if (request != null) {
            baseUrl = "?";
        } else {
            int port = request.getServerPort();
            String _port = port == 80 || port == 443 ? "" : "" + ":" + port;
            baseUrl = String.format("%s://%s%s%s?", request.getScheme(), request.getServerName(), _port, StringUtils.hasText(request.getContextPath()) ? request.getContextPath() : "/");
        }


        if (StringUtils.hasText(qs)) {
            // "?page=" 제거(필터)하고 다시 모아서 가공
            baseUrl += Arrays.stream(qs.split("&"))
                    .filter(s -> !s.contains("page=")).collect(Collectors.joining("&")) + "&";
        }

        baseUrl += "page=";

        /* 쿼리스트링 값 처리 E */

        this.page = page;
        this.total = total;
        this.ranges = ranges;
        this.limit = limit;
        this.totalPages = totalPages;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.prevRangeLastPage = prevRangeLastPage;
        this.nextRangeFirstPage = nextRangeFirstPage;
    }

    /**
     * String 배열
     * 0번째 - Page 번호 숫자
     * 1번째 - Page URL
     *
     * 현재 구간의 Page 가져와 구간별 Page 이동 버튼
     *
     * @return
     */
    public List<String[]> getPages() {

        if (total == 0) {

            return Collections.EMPTY_LIST;
        }

        List<String[]> pages = new ArrayList<>();

        for (int i = firstRangePage; i <= lastRangePage; i++) {

            String url = baseUrl + i;

            pages.add(new String[] {"" + i, url});
        }

        return pages;
    }

}
