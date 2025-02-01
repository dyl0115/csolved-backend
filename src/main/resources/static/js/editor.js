const scriptEl = document.currentScript;
const uploadUrl = scriptEl.dataset.uploadImageUrl;

tinymce.init({
    selector: '#editor',
    height: 500,
    plugins: 'code codesample link image table lists advlist autolink',
    toolbar: 'undo redo | blocks | bold italic | alignleft aligncenter alignright | indent outdent | bullist numlist | codesample | link image',
    codesample_languages: [
        {text: 'HTML/XML', value: 'markup'},
        {text: 'JavaScript', value: 'javascript'},
        {text: 'CSS', value: 'css'},
        {text: 'PHP', value: 'php'},
        {text: 'Python', value: 'python'},
        {text: 'Java', value: 'java'},
        {text: 'SQL', value: 'sql'}
    ],
    codesample_global_prismjs: true,
    forced_root_block: 'p',
    remove_trailing_brs: false,
    br_in_pre: false,
    keep_styles: true,
    entity_encoding: 'raw',
    paste_enable_default_filters: false,
    paste_word_valid_elements: '*[*]',
    paste_webkit_styles: 'all',
    paste_merge_formats: true,
    browser_spellcheck: true,

    // 코드블록 하이라이팅 이후, 한글 입력이 안되는 문제 해결
    input_ime: true,
    toolbar_sticky: false,
    invalid_elements: '',
    extended_valid_elements: '*[*]',

    // 이미지 업로드 설정
    images_upload_url: uploadUrl || '',
    images_upload_handler: function (blobInfo, progress) {
        return new Promise(async (resolve, reject) => {
            try {
                // 1. 이미지 압축
                const compressedBlob = await new Promise((resolve, reject) => {
                    new Compressor(blobInfo.blob(), {
                        quality: 0.6, // 화질 (0 ~ 1)
                        maxWidth: 1920, // 최대 너비
                        maxHeight: 1080, // 최대 높이
                        mimeType: 'image/jpeg', // JPEG로 변환 (용량 70% 감소)
                        convertSize: 1000000, // 1MB 이상 이미지만 변환
                        success(result) {
                            resolve(result);
                        },
                        error(err) {
                            reject(err);
                        }
                    });
                });

                // 2. 압축된 이미지 업로드
                const xhr = new XMLHttpRequest();
                xhr.withCredentials = false;
                xhr.open('POST', uploadUrl);

                xhr.upload.onprogress = (e) => {
                    progress(e.loaded / e.total * 100);
                };

                xhr.onload = () => {
                    if (xhr.status < 200 || xhr.status >= 300) {
                        reject('HTTP Error: ' + xhr.status);
                        return;
                    }
                    const json = JSON.parse(xhr.responseText);
                    resolve(json.location);
                };

                xhr.onerror = () => {
                    reject('Upload failed');
                };

                const formData = new FormData();
                formData.append('file', compressedBlob, blobInfo.filename()); // 압축된 Blob 사용
                xhr.send(formData);

            } catch (err) {
                reject('Compression failed: ' + err.message);
            }
        });
    },

    content_style: `
                body {
                    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
                    padding: 10px;
                }
                .mce-content-body pre[class*="language-"] {
                    margin: 15px 0;
                }
                .mce-content-body code[class*="language-"] {
                    font-family: Consolas, Monaco, 'Andale Mono', 'Ubuntu Mono', monospace;
                }
                p {
                    margin: 0;
                    padding: 8px 0;
                    min-height: 20px;
                }
                img {
                    max-width: 100%;
                    height: auto;
                }
            `,
    setup: function (editor) {
        editor.on('change keyup', function () {
            Prism.highlightAll();
        });
    },
    images_reuse_filename: true, // 원본 파일명 재사용
    images_upload_credentials: true, // 쿠키 전송 허용
});

// 폼 제출 처리
document.getElementById('createForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const content = tinymce.get('editor').getContent();
    document.getElementById('editor').value = content;
    this.submit();
});

// 페이지 로드 시 코드 하이라이팅 적용
document.addEventListener('DOMContentLoaded', (event) => {
    Prism.highlightAll();
});