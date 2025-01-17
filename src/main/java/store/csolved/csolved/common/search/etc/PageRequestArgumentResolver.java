package store.csolved.csolved.common.search.etc;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.csolved.csolved.common.search.PageRequest;
import store.csolved.csolved.domain.question.service.QuestionService;

@RequiredArgsConstructor
@Component
public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver
{
    private static final String PAGE_PARAMETER_NAME = "page";
    private final QuestionService questionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        boolean hasPageInfoAnnotation = parameter.hasParameterAnnotation(PageInfo.class);
        boolean isPageClass = parameter.getParameterType().equals(PageRequest.class);
        return hasPageInfoAnnotation && isPageClass;
    }

    @Override
    public PageRequest resolveArgument(MethodParameter parameter,
                                       ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest,
                                       WebDataBinderFactory binderFactory)
    {
        String pageString = webRequest.getParameter(PAGE_PARAMETER_NAME);
        PageRequest page = PageRequest.validateAndCreate(pageString, questionService.getAllQuestionCount());
        if (mavContainer != null) mavContainer.addAttribute(PAGE_PARAMETER_NAME, page);
        return page;
    }
}
