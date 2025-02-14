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

// function searchPosts(postType) {
//     const urlParams = new URLSearchParams('');
//     const page = 'page';
//     const searchType = 'searchType';
//     const searchKeyword = 'searchKeyword';
//
//     const searchTypeValue = document.getElementById('search-select').value;
//     const searchKeywordValue = document.getElementById('search-input').value;
//
// // Change their values as needed
//     urlParams.set(page, 1);
//     urlParams.set(searchType, searchTypeValue); // update with the select box value
//     urlParams.set(searchKeyword, searchKeywordValue); // update with the input box value
//
// // Reflect the changes in the browser's URL
//     const newUrl = `/${postType}` + '?' + urlParams.toString();
//     window.history.replaceState({}, '', newUrl);
//
// // Send a GET request to the updated URL
//     fetch(newUrl, {
//         method: 'GET',
//         headers: {
//             'Accept': 'text/html'
//         }
//     })
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok');
//             }
//             return response.text();
//         })
//         .then(html => {
//             document.documentElement.innerHTML = html;
//         })
//         .catch(error => {
//             console.error('There was a problem with the fetch operation:', error);
//         });
// }