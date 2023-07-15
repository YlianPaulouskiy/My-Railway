package by.itacademy.railway.dto.pageable;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {

    List<T> content;
    int page;
    boolean hasNext;
    boolean hasPrevious;

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(page.getContent(), page.getNumber(), page.hasNext(), page.hasPrevious());
    }

}
