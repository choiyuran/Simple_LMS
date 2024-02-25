package com.itbank.simpleboard.component;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PagingComponent {
    public static final int MAX_PAGE = 5;

    public int calculateStart(int pageNumber) {
        return (pageNumber / MAX_PAGE) * MAX_PAGE + 1;
    }

    public int calculateEnd(int totalPages, int start) {
        return (totalPages == 0) ? 1 : (Math.min(start + (MAX_PAGE - 1), totalPages));
    }
}
