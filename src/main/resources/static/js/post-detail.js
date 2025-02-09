// 좋아요 버튼
async function addLike(postType, postId) {
    const likeButton = document.getElementById("like-button");
    const likeCountElement = document.getElementById("like-count");
    let likeCount = parseInt(likeCountElement.textContent, 10) || 0;

    const response = await fetch(`/api/${postType}/${postId}/likes`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
    });

    switch (response.status) {
        case 200:
            likeCount++;
            likeCountElement.textContent = likeCount;
            likeButton.disabled = true;
            break;
        case 409:
            const modal = new bootstrap.Modal(document.getElementById("addLikeConflictModal"));
            modal.show();
            const closeModalButton = document.getElementById("closeModalButton");
            closeModalButton.addEventListener("click", () => {
                modal.hide();
            });
            break;
        default:
            alert('작업 중 오류가 발생했습니다.');
    }
}

// 게시글 삭제
async function deletePost(postType, postId) {
    try {
        const response = await fetch(`/api/${postType}/${postId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('삭제에 실패했습니다.');
        }
        handleRedirect(postType);

    } catch (error) {
        console.error('Error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

function handleRedirect(postType) {
    switch (postType) {
        case 'community':
            window.location.replace('/communities?page=1');
            break;
        case 'questions':
            window.location.replace('/questions?page=1');
            break;
        case 'code-review':
            window.location.replace('/code-reviews?page=1');
            break;
        default:
            window.location.replace('/');
    }
}


// 답글 삭제
async function deleteAnswer(answerId) {
    try {
        const response = await fetch(`/api/answers/${answerId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('삭제에 실패했습니다.');
        }

        window.location.reload();
    } catch (error) {
        console.error('Error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}

async function deleteComment(commentId) {
    try {
        const response = await fetch(`/api/comments/${commentId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('삭제에 실패했습니다.');
        }

        window.location.reload();
    } catch (error) {
        console.error('Error:', error);
        alert('삭제 중 오류가 발생했습니다.');
    }
}