// create.html 의 input 에 multiple accept 추가헌 뒤
const fileInput = document.querySelector("[name='file']");

const showUploadImages = (files) => {
  const output = document.querySelector(".uploadResult ul");

  let tags = "";

  files.forEach((file) => {
    tags += `<li data-name="${file.imgName}" data-path="${file.path}" data-uuid="${file.uuid}">`;
    tags += `<a href="${file.imageURL}">`;
    tags += `<img src="/upload/display?fileName=${file.thumbnailURL}" class="block">`;
    tags += "</a>";
    tags += `<span class="text-sm d-inline-block mx-1">${file.imgName}</span>`;
    tags += `<a href="${file.imageURL}" data-file=""><i class="fa-solid fa-xmark"></i></a>`;
    tags += "</li>";
  });
  output.insertAdjacentHTML("beforeend", tags);
};

// x 를 클릭 시 파일 삭제
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  console.log("이벤트 대상 ", e.target);
  // closese() : 가장가까운 부모 요소 찾기
  e.preventDefault();

  const aTag = e.target.closest("a");
  const li = e.target.closest("li");

  console.log("속성 값 ", aTag.getAttribute("href"));
  const href = aTag.getAttribute("href");
  // 컨트롤러로 요청 보내기

  const formData = new FormData();
  formData.append("fileName", href);
  fetch("/upload/remove", {
    method: "post",
    body: formData,
  })
    .then((res) => res.text())
    .then((data) => {
      console.log(data);
      // 화면에서 이미지 제거
      li.remove();
    });
});

fileInput.addEventListener("change", (e) => {
  const files = fileInput.files;
  const formData = new FormData();
  for (let idx = 0; idx < files.length; idx++) {
    formData.append("uploadFiles", files[idx]);
  }
  fetch("/upload/upload", {
    method: "post",
    body: formData,
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);
      showUploadImages(data);
    });
});
