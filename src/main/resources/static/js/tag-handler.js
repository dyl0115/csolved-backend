document.addEventListener('DOMContentLoaded', function () {
    const tagContainer = document.getElementById('tags-container');
    const tagInput = document.getElementById('tagInput');
    const tagsHidden = document.getElementById('tagsHidden');

    if (!tagContainer || !tagInput || !tagsHidden) return;

    // 태그 목록을 관리할 Set (중복 방지)
    const tags = new Set();

    // hidden input 업데이트 함수
    function updateHiddenInput() {
        tagsHidden.value = Array.from(tags).join(',');
    }

    tagInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter' && tagInput.value.trim() !== '') {
            event.preventDefault();
            const tagText = tagInput.value.trim();

            // 중복 체크
            if (tags.has(tagText)) {
                alert('이미 존재하는 태그입니다.');
                return;
            }

            addTag(tagText);
        }
    });

    tagContainer.addEventListener('click', () => {
        tagInput.focus();
    });

    function addTag(text) {
        // Set에 태그 추가
        tags.add(text);

        // 태그 엘리먼트 생성
        const tag = document.createElement('span');
        tag.classList.add('badge', 'bg-primary', 'me-1', 'mb-1', 'd-flex', 'align-items-center');
        tag.textContent = text;

        // 삭제 버튼 생성
        const removeButton = document.createElement('button');
        removeButton.type = 'button';
        removeButton.classList.add('btn-close', 'btn-close-white', 'ms-2');
        removeButton.setAttribute('aria-label', 'Remove');
        removeButton.style.fontSize = '0.7em';

        // 삭제 버튼 클릭 시
        removeButton.onclick = () => {
            tags.delete(text);  // Set에서 제거
            tag.remove();       // DOM에서 제거
            updateHiddenInput();// hidden input 업데이트
        };

        tag.appendChild(removeButton);
        tagContainer.insertBefore(tag, tagInput);
        tagInput.value = '';

        // hidden input 업데이트
        updateHiddenInput();
    }
});