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