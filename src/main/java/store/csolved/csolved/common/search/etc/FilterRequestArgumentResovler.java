package store.csolved.csolved.common.search.etc;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.csolved.csolved.common.search.FilterRequest;

@Component
public class FilterRequestArgumentResovler implements HandlerMethodArgumentResolver
{
    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        boolean hasFilterInfoAnnotation = parameter.hasParameterAnnotation(FilterInfo.class);
        boolean isFilterRequestType = parameter.getParameterType().equals(FilterRequest.class);
        return hasFilterInfoAnnotation && isFilterRequestType;
    }

    @Override
    public FilterRequest resolveArgument(MethodParameter parameter,
                                         ModelAndViewContainer mavContainer,
                                         NativeWebRequest webRequest,
                                         WebDataBinderFactory binderFactory)
    {
        String filterType = webRequest.getParameter("filterType");
        String filterValue = webRequest.getParameter("filterValue");
        
        if (filterValue == null) return FilterRequest.create(filterType, 0L);
        return FilterRequest.create(filterType, Long.parseLong(filterValue));
    }
}
