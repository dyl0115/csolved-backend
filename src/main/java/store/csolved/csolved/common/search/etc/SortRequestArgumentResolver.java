package store.csolved.csolved.common.search.etc;

import jakarta.annotation.PostConstruct;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.csolved.csolved.common.search.SortType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class SortRequestArgumentResolver implements HandlerMethodArgumentResolver
{
    private final static Map<String, SortType> SORT_TYPE_MAP = new HashMap<>();
    private final static String SORT_PARAMETER_NAME = "sort";

    @PostConstruct
    public void init()
    {
        Arrays.stream(SortType.values())
                .forEach(e -> SORT_TYPE_MAP.put(e.name().toLowerCase(), e));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        boolean hasSortInfoAnnotation = parameter.hasParameterAnnotation(SortInfo.class);
        boolean isSortType = parameter.getParameterType().equals(SortType.class);
        return hasSortInfoAnnotation && isSortType;
    }

    @Override
    public SortType resolveArgument(MethodParameter parameter,
                                    ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory)
    {
        String sortString = webRequest.getParameter(SORT_PARAMETER_NAME);
        if (sortString == null) return SortType.RECENT;
        return SORT_TYPE_MAP.getOrDefault(sortString, SortType.RECENT);
    }
}
