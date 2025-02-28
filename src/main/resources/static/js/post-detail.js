// 댓글 등록
async function submitAnswer()
{
    event.preventDefault();

    const totalPage = document.getElementById('total-page');
    const form = document.getElementById('answer-form');
    const content = tinymce.get('medium-editor').getContent();
    const anonymous = document.getElementById('answer-anonymous').checked;
    const authorId = document.getElementById('answer-author-id').value;

    const formData = new FormData();
    formData.append('content', content);
    formData.append('anonymous', anonymous);
    formData.append('authorId', authorId);

    try
    {
        const response = await fetch(form.action, {
            method: 'POST',
            body: formData
        });

        if (!response.ok)
        {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        totalPage.outerHTML = await response.text();

        tinymce.get('medium-editor').setContent('');
        document.getElementById('answer-anonymous').checked = false;
    }
    catch (error)
    {
        console.error('Error', error);
    }
}

// 좋아요 버튼
async function addLike(postType, postId)
{
    const likeButton = document.getElementById("like-button");
    const likeCountElement = document.getElementById("like-count");
    let likeCount = parseInt(likeCountElement.textContent, 10) || 0;

    const response = await fetch(`/api/${postType}/${postId}/likes`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });

    switch (response.status)
    {
        case 200:
            likeCount++;
            likeCountElement.textContent = likeCount;
            likeButton.disabled = true;
            break;
        case 409:
            const modal = new bootstrap.Modal(document.getElementById("addLikeConflictModal"));
            modal.show();
            const closeModalButton = document.getElementById("closeModalButton");
            closeModalButton.addEventListener("click", () =>
            {
                modal.hide();
            });
            break;
        default:
            alert('좋아요 처리 중 오류가 발생했습니다.');
            break;
    }
}

// 북마크 토글 버튼
async function toggleBookmark(bookmarkButton)
{
    const postId = bookmarkButton.getAttribute('postId');
    const bookmarked = bookmarkButton.getAttribute('bookmarked') === 'true';
    const bookmarkIcon = document.getElementById('bookmark-icon');
    const bookmarkText = document.getElementById('bookmark-text');
    const requestUrl = `/api/bookmark/${postId}`;
    const requestMethod = bookmarked ? 'DELETE' : 'POST';

    const response = await fetch(requestUrl,
        {
            method: requestMethod
        })

    switch (response.status)
    {
        case 200:
            const newBookmarked = !bookmarked;
            bookmarkButton.setAttribute('bookmarked', newBookmarked);
            bookmarkText.textContent = newBookmarked ? '북마크 취소' : '북마크';
            bookmarkIcon.className = newBookmarked ? 'bi bi-bookmark-x-fill' : 'bi bi-bookmark-fill';
            break;
        default:
            alert('북마크 처리 중 오류가 발생했습니다.');
            bookmarkText.textContent = bookmarked ? '북마크 취소' : '북마크';
            break;
    }
}

// 게시글 삭제
async function deletePost(postType, pluralPostType, postId)
{
    try
    {
        const response = await fetch(`/api/${postType}/${postId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok)
        {
            throw new Error('삭제에 실패했습니다.');
        }
        window.location.replace(`/${pluralPostType}?page=1`);

    }
    catch (error)
    {
        console.error('Error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

// 답글 삭제
async function deleteAnswer(answerId)
{
    try
    {
        const response = await fetch(`/api/answers/${answerId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok)
        {
            throw new Error('삭제에 실패했습니다.');
        }

        window.location.reload();
    }
    catch (error)
    {
        console.error('Error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

async function deleteComment(commentId)
{
    try
    {
        const response = await fetch(`/api/comments/${commentId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok)
        {
            throw new Error('삭제에 실패했습니다.');
        }

        window.location.reload();
    }
    catch (error)
    {
        console.error('Error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

// 댓글 토글 기능
document.getElementById('toggle-answer-form').addEventListener('click', function ()
{
    const card = document.getElementById('answer-form-card');
    card.classList.toggle('d-none');
});

// 대댓글 토글 기능
document.querySelectorAll('.toggle-comment-form').forEach(element =>
{
    element.addEventListener('click', function ()
    {
        const commentForm = this.nextElementSibling;
        commentForm.classList.toggle('d-none');
    });
});