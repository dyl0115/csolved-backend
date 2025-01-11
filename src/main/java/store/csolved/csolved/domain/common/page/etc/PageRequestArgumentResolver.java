package store.csolved.csolved.domain.common.page.etc;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import store.csolved.csolved.domain.question.service.QuestionService;

@RequiredArgsConstructor
@Component
public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver
{
    private final QuestionService questionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        boolean hasPageInfoAnnotation = parameter.hasParameterAnnotation(PageInfo.class);
        boolean isPageClass = parameter.getParameterType().equals(store.csolved.csolved.domain.common.page.Page.class);
        return hasPageInfoAnnotation && isPageClass;
    }

    @Override
    public store.csolved.csolved.domain.common.page.Page resolveArgument(MethodParameter parameter,
                                                                         ModelAndViewContainer mavContainer,
                                                                         NativeWebRequest webRequest,
                                                                         WebDataBinderFactory binderFactory)
    {
        String pageString = webRequest.getParameter("page");
        store.csolved.csolved.domain.common.page.Page page = store.csolved.csolved.domain.common.page.Page.validateAndCreate(pageString, questionService.getAllQuestionCount());
        if (mavContainer != null) mavContainer.addAttribute("page", page);
        return page;
    }
}
